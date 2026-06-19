#!/usr/bin/env pwsh
#Requires -Version 7
<#
.SYNOPSIS
    Install all prerequisites for MazeGame (Windows, Linux, macOS).

.DESCRIPTION
    Checks for and installs JDK 21, Apache Maven, and VS Code extensions using
    the system package manager. Optionally downloads the JavaFX SDK. Writes
    persistent user-scope environment variables (JAVA_HOME, optionally
    PATH_TO_FX) and generates the VS Code launch environment file
    (.vscode/maze.launch.env).

    libGDX is a Maven dependency and is fetched automatically during the first
    build — no separate installation is required.

    Requires PowerShell 7+ (pwsh). On Windows the script auto-elevates when
    needed by winget/choco. On Linux/macOS sudo prompts may appear for apt/dnf/
    pacman/brew.

.PARAMETER JavaFX
    Download and configure the JavaFX 21.0.9 SDK (sets PATH_TO_FX).

.PARAMETER SkipEnvSetup
    Skip writing persistent environment variables (use if you manage them
    yourself via .bashrc / .zshrc / etc.).

.PARAMETER SkipVSCodeExtensions
    Skip installing VS Code extensions even if the 'code' CLI is available.

.EXAMPLE
    # JDK 21 + Maven + VS Code extensions
    pwsh -File .\install.ps1

    # Also include JavaFX SDK
    pwsh -File .\install.ps1 -JavaFX
#>

param(
    [switch]$JavaFX,
    [switch]$SkipEnvSetup,
    [switch]$SkipVSCodeExtensions
)

$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

# ── Colour helpers ────────────────────────────────────────────────────────────

function Write-Step([string]$text)  { Write-Host "`n=== $text ===" -ForegroundColor Cyan }
function Write-OK([string]$text)    { Write-Host "  [OK]  $text"  -ForegroundColor Green }
function Write-Info([string]$text)  { Write-Host "  [..]  $text"  -ForegroundColor Yellow }
function Write-Err([string]$text)   { Write-Host "  [!!]  $text"  -ForegroundColor Red }

# ── OS detection ──────────────────────────────────────────────────────────────

function Get-OS {
    if ($IsWindows) { return 'windows' }
    if ($IsMacOS)   { return 'macos' }
    if ($IsLinux)   { return 'linux' }
    throw "Unsupported OS"
}

$OS   = Get-OS
$Arch = if ([System.Runtime.InteropServices.RuntimeInformation]::ProcessArchitecture -eq
            [System.Runtime.InteropServices.Architecture]::Arm64) { 'arm64' } else { 'x64' }

# ── Environment variable helpers ──────────────────────────────────────────────

function Set-UserEnv([string]$name, [string]$value) {
    if ($SkipEnvSetup) { return }
    if ($OS -eq 'windows') {
        [System.Environment]::SetEnvironmentVariable($name, $value, 'User')
    } else {
        # On Unix, write/update the export line in the user's shell profile.
        $profileFiles = @("$HOME/.bashrc", "$HOME/.zshrc") | Where-Object { Test-Path $_ }
        foreach ($f in $profileFiles) {
            $content = Get-Content $f -Raw
            $exportLine = "export $name=`"$value`""
            if ($content -match "export $name=") {
                (Get-Content $f) -replace "export $name=.*", $exportLine | Set-Content $f
            } else {
                Add-Content $f "`n$exportLine"
            }
        }
    }
    Set-Item "Env:$name" $value
    Write-Info "Set $name = $value"
}

function Add-ToUserPath([string]$dir) {
    if ($SkipEnvSetup) { return }
    $sep = [System.IO.Path]::PathSeparator
    if ($OS -eq 'windows') {
        $current = [System.Environment]::GetEnvironmentVariable('PATH', 'User') ?? ''
        if ($current -notlike "*$dir*") {
            [System.Environment]::SetEnvironmentVariable('PATH', "$current$sep$dir", 'User')
        }
    } else {
        $profileFiles = @("$HOME/.bashrc", "$HOME/.zshrc") | Where-Object { Test-Path $_ }
        $exportLine   = "export PATH=`"$dir`${PATH:+:`$PATH}`""
        foreach ($f in $profileFiles) {
            if ((Get-Content $f -Raw) -notlike "*$dir*") {
                Add-Content $f "`n$exportLine"
            }
        }
    }
    if ($env:PATH -notlike "*$dir*") { $env:PATH = "$env:PATH$sep$dir" }
    Write-Info "Added to PATH: $dir"
}

# ── JDK 21 detection ─────────────────────────────────────────────────────────

function Get-JavaMajorVersion([string]$javaExe) {
    try {
        $out = & $javaExe -version 2>&1 | Out-String
        if ($out -match 'version "(\d+)') { return [int]$Matches[1] }
    } catch {}
    return 0
}

function Find-Java21Home {
    # 1. JAVA_HOME env var
    if ($env:JAVA_HOME) {
        $javaExeName = if ($OS -eq 'windows') { 'bin\java.exe' } else { 'bin/java' }
        $exe = Join-Path $env:JAVA_HOME $javaExeName
        if ((Test-Path $exe) -and (Get-JavaMajorVersion $exe) -eq 21) { return $env:JAVA_HOME }
    }

    # 2. java on PATH
    $cmd = Get-Command java -ErrorAction SilentlyContinue
    if ($cmd -and (Get-JavaMajorVersion $cmd.Source) -eq 21) {
        $binDir = Split-Path -Parent $cmd.Source
        return Split-Path -Parent $binDir
    }

    # 3. Well-known install dirs
    $searchDirs = switch ($OS) {
        'windows' {
            @(
                'C:\Program Files\Eclipse Adoptium',
                'C:\Program Files\Java',
                'C:\Program Files\Microsoft',
                "$env:LOCALAPPDATA\Programs\Eclipse Adoptium"
            )
        }
        'macos' {
            @('/Library/Java/JavaVirtualMachines', '/opt/homebrew/opt', '/usr/local/opt')
        }
        'linux' {
            @('/usr/lib/jvm', '/usr/java', '/opt/java', '/opt/jdk')
        }
    }
    foreach ($dir in $searchDirs) {
        if (-not (Test-Path $dir)) { continue }
        foreach ($subDir in @(Get-ChildItem $dir -Directory -ErrorAction SilentlyContinue)) {
            $javaBin = switch ($OS) {
                'windows' { Join-Path $subDir.FullName 'bin\java.exe' }
                'macos'   { Join-Path $subDir.FullName 'Contents\Home\bin\java' }
                default   { Join-Path $subDir.FullName 'bin\java' }
            }
            if ((Test-Path $javaBin) -and (Get-JavaMajorVersion $javaBin) -eq 21) {
                $inner = Join-Path $subDir.FullName 'Contents\Home'
                if ($OS -eq 'macos' -and (Test-Path $inner)) { return $inner }
                return $subDir.FullName
            }
        }
    }
    return $null
}

# ── JDK 21 install ────────────────────────────────────────────────────────────

function Install-JDK21 {
    Write-Info "JDK 21 not found — installing..."
    switch ($OS) {
        'windows' {
            if (Get-Command winget -ErrorAction SilentlyContinue) {
                Write-Info "Using winget (Eclipse Temurin 21)..."
                winget install --id EclipseAdoptium.Temurin.21.JDK -e --source winget `
                    --accept-package-agreements --accept-source-agreements | Out-Host
            } elseif (Get-Command choco -ErrorAction SilentlyContinue) {
                Write-Info "Using Chocolatey (temurin21)..."
                choco install temurin21 -y | Out-Host
            } else {
                Write-Err "Neither winget nor Chocolatey found."
                Write-Err "Install JDK 21 manually: https://adoptium.net/temurin/releases/?version=21"
                Write-Err "Then set JAVA_HOME and re-run this script."
                exit 1
            }
        }
        'macos' {
            if (Get-Command brew -ErrorAction SilentlyContinue) {
                Write-Info "Using Homebrew (openjdk@21)..."
                brew install openjdk@21
                # Homebrew doesn't symlink openjdk into /usr/local by default on macOS 13+
                $brewPrefix = (brew --prefix openjdk@21).Trim()
                return $brewPrefix
            } else {
                Write-Err "Homebrew not found. Install it first: https://brew.sh"
                Write-Err "Then re-run this script."
                exit 1
            }
        }
        'linux' {
            if (Get-Command apt-get -ErrorAction SilentlyContinue) {
                Write-Info "Using apt (openjdk-21-jdk)..."
                sudo apt-get update -qq | Out-Host
                sudo apt-get install -y openjdk-21-jdk | Out-Host
            } elseif (Get-Command dnf -ErrorAction SilentlyContinue) {
                Write-Info "Using dnf (java-21-openjdk-devel)..."
                sudo dnf install -y java-21-openjdk-devel | Out-Host
            } elseif (Get-Command pacman -ErrorAction SilentlyContinue) {
                Write-Info "Using pacman (jdk21-openjdk)..."
                sudo pacman -S --noconfirm jdk21-openjdk | Out-Host
            } else {
                Write-Err "No supported package manager found (apt / dnf / pacman)."
                Write-Err "Install JDK 21 manually: https://adoptium.net/temurin/releases/?version=21"
                exit 1
            }
        }
    }
    return $null   # caller will re-detect after install
}

# ── Maven detection & install ─────────────────────────────────────────────────

function Get-MavenVersion {
    try {
        $out = & mvn --version 2>&1 | Out-String
        if ($out -match 'Apache Maven (\S+)') { return $Matches[1] }
    } catch {}
    return $null
}

function Install-MavenDirect {
    $version   = '3.9.16'
    $installDir = Join-Path $env:LOCALAPPDATA 'Programs\Apache\maven'
    $mvnHome   = Join-Path $installDir "apache-maven-$version"
    $mvnExe    = Join-Path $mvnHome 'bin\mvn.cmd'

    if (Test-Path $mvnExe) {
        Write-Info "Apache Maven $version already downloaded at $mvnHome"
        return $mvnHome
    }

    $fileName = "apache-maven-$version-bin.zip"
    $url      = "https://downloads.apache.org/maven/maven-3/$version/binaries/$fileName"
    $tmpZip   = Join-Path ([System.IO.Path]::GetTempPath()) $fileName

    Write-Info "Downloading Apache Maven $version from Apache..."
    Invoke-WebRequest -Uri $url -OutFile $tmpZip -UseBasicParsing

    if (-not (Test-Path $installDir)) {
        New-Item -ItemType Directory -Path $installDir -Force | Out-Null
    }
    Write-Info "Extracting to $installDir ..."
    Expand-Archive -Path $tmpZip -DestinationPath $installDir -Force
    Remove-Item $tmpZip -ErrorAction SilentlyContinue

    return $mvnHome
}

function Install-Maven {
    Write-Info "Maven not found — installing..."
    switch ($OS) {
        'windows' {
            $installed = $false
            if (Get-Command choco -ErrorAction SilentlyContinue) {
                choco install maven -y | Out-Host
                $installed = $true
            }
            if (-not $installed) {
                $mvnHome = Install-MavenDirect
                Add-ToUserPath (Join-Path $mvnHome 'bin')
                $env:PATH = "$env:PATH;$(Join-Path $mvnHome 'bin')"
            }
        }
        'macos' {
            if (Get-Command brew -ErrorAction SilentlyContinue) {
                brew install maven | Out-Host
            } else {
                Write-Err "Homebrew not found. Install it first: https://brew.sh"
                exit 1
            }
        }
        'linux' {
            if (Get-Command apt-get -ErrorAction SilentlyContinue) {
                sudo apt-get install -y maven | Out-Host
            } elseif (Get-Command dnf -ErrorAction SilentlyContinue) {
                sudo dnf install -y maven | Out-Host
            } elseif (Get-Command pacman -ErrorAction SilentlyContinue) {
                sudo pacman -S --noconfirm maven | Out-Host
            } else {
                Write-Err "No supported package manager found."
                Write-Err "Install Maven manually: https://maven.apache.org/install.html"
                exit 1
            }
        }
    }
}

# ── JavaFX SDK download ───────────────────────────────────────────────────────

function Install-JavaFXSDK {
    $version = '21.0.9'

    $platformSlug = switch ($OS) {
        'windows' { 'windows-x64' }
        'macos'   { if ($Arch -eq 'arm64') { 'osx-aarch64' } else { 'osx-x64' } }
        'linux'   { if ($Arch -eq 'arm64') { 'linux-aarch64' } else { 'linux-x64' } }
    }

    $installRoot = switch ($OS) {
        'windows' { 'C:\Program Files\Java' }
        'macos'   { '/usr/local/lib' }
        'linux'   { '/opt/java' }
    }

    $sdkDir = Join-Path $installRoot "javafx-sdk-$version"

    if (Test-Path (Join-Path $sdkDir 'lib')) {
        Write-OK "JavaFX SDK $version already present at $sdkDir"
        return $sdkDir
    }

    $fileName = "openjfx-${version}_${platformSlug}_bin-sdk.zip"
    $url      = "https://download2.gluonhq.com/openjfx/$version/$fileName"
    $tmpZip   = Join-Path ([System.IO.Path]::GetTempPath()) $fileName

    Write-Info "Downloading JavaFX SDK $version ($platformSlug)..."
    Invoke-WebRequest -Uri $url -OutFile $tmpZip -UseBasicParsing

    Write-Info "Extracting to $installRoot ..."
    if ($OS -ne 'windows' -and -not (Test-Path $installRoot)) {
        sudo mkdir -p $installRoot
    } elseif (-not (Test-Path $installRoot)) {
        New-Item -ItemType Directory -Path $installRoot -Force | Out-Null
    }

    Expand-Archive -Path $tmpZip -DestinationPath $installRoot -Force
    Remove-Item $tmpZip -ErrorAction SilentlyContinue

    # The zip extracts as javafx-sdk-<version>
    if (-not (Test-Path $sdkDir)) {
        # Some archives extract with a slightly different name; try to rename
        $extracted = Get-ChildItem $installRoot -Directory |
            Where-Object { $_.Name -like "javafx*$version*" } |
            Select-Object -First 1
        if ($extracted -and $extracted.FullName -ne $sdkDir) {
            Move-Item $extracted.FullName $sdkDir
        }
    }

    return $sdkDir
}

# ── Main ──────────────────────────────────────────────────────────────────────

Write-Host ""
Write-Host "  MazeGame Prerequisites Installer" -ForegroundColor Magenta
Write-Host "  OS: $OS ($Arch)  |  PowerShell $($PSVersionTable.PSVersion)" -ForegroundColor DarkGray

# ─── JDK 21 ──────────────────────────────────────────────────────────────────

Write-Step "JDK 21"
$javaHome = Find-Java21Home
if ($javaHome) {
    Write-OK "Already installed: $javaHome"
} else {
    $hint = Install-JDK21
    # Re-detect after install (package manager sets it up in a known location)
    $javaHome = if ($hint -and (Test-Path $hint)) { $hint } else { Find-Java21Home }
    if (-not $javaHome) {
        Write-Err "Install succeeded but JDK 21 home could not be detected."
        $javaHome = Read-Host "Enter JDK 21 home path"
    }
    Write-OK "Installed: $javaHome"
}

Set-UserEnv 'JAVA_HOME' $javaHome
Add-ToUserPath (Join-Path $javaHome 'bin')
$env:JAVA_HOME = $javaHome

# ─── Maven ────────────────────────────────────────────────────────────────────

Write-Step "Apache Maven"
$mavenVersion = Get-MavenVersion
if ($mavenVersion) {
    Write-OK "Already installed: Maven $mavenVersion"
} else {
    Install-Maven
    $mavenVersion = Get-MavenVersion
    if ($mavenVersion) {
        Write-OK "Installed: Maven $mavenVersion"
    } else {
        Write-Info "Maven installed but 'mvn' not yet on PATH. Restart your terminal and run 'mvn --version' to confirm."
    }
}

# ─── JavaFX SDK (optional) ────────────────────────────────────────────────────

if ($JavaFX) {
    Write-Step "JavaFX SDK"
    $jfxHome = Install-JavaFXSDK
    Set-UserEnv 'PATH_TO_FX' $jfxHome
    Write-OK "PATH_TO_FX = $jfxHome"
} else {
    Write-Info "(Skipping JavaFX SDK — pass -JavaFX to include it)"
}

# ─── VS Code extensions ───────────────────────────────────────────────────────

Write-Step "VS Code extensions"

# Required extensions — IDs match the VS Code marketplace
$requiredExtensions = @(
    'vscjava.vscode-java-pack'   # Extension Pack for Java (includes maven, debug, test)
)

# Optional but recommended extensions
$optionalExtensions = @(
    'redhat.vscode-xml'
    'redhat.vscode-yaml'
    'ms-vscode.makefile-tools'
)

$codeCmd = Get-Command code -ErrorAction SilentlyContinue
if (-not $codeCmd -and $OS -eq 'windows') {
    # VS Code on Windows sometimes installs 'code.cmd'
    $codeCmd = Get-Command code.cmd -ErrorAction SilentlyContinue
}

if ($SkipVSCodeExtensions) {
    Write-Info "Skipping VS Code extensions (-SkipVSCodeExtensions)"
} elseif (-not $codeCmd) {
    Write-Info "'code' CLI not found — VS Code may not be installed or not on PATH."
    Write-Info "Install VS Code: https://code.visualstudio.com/download"
    Write-Info "Then run:  code --install-extension vscjava.vscode-java-pack"
    if ($OS -eq 'macos') {
        Write-Info "On macOS, also run: Ctrl+Shift+P -> 'Shell Command: Install code command in PATH'"
    }
} else {
    foreach ($ext in $requiredExtensions) {
        Write-Info "Installing $ext ..."
        & $codeCmd.Source --install-extension $ext --force 2>&1 | Out-Null
        Write-OK $ext
    }
    foreach ($ext in $optionalExtensions) {
        Write-Info "Installing $ext ..."
        & $codeCmd.Source --install-extension $ext --force 2>&1 | Out-Null
        Write-OK "$ext (optional)"
    }
}

# ─── VS Code launch environment ───────────────────────────────────────────────

Write-Step "VS Code launch environment"
$makeScript = Join-Path $PSScriptRoot 'make-javafx.ps1'
if (Test-Path $makeScript) {
    try {
        & $makeScript -Target write-launch-env
        Write-OK "Generated .vscode/maze.launch.env"
    } catch {
        Write-Info "make-javafx.ps1 reported: $_"
        Write-Info "Run '.\make-javafx.ps1 -Target write-launch-env' manually after restarting your terminal."
    }
} else {
    Write-Info "make-javafx.ps1 not found — skipping launch env generation."
}

# ─── Summary ─────────────────────────────────────────────────────────────────

Write-Step "Done"
Write-OK "JAVA_HOME  = $javaHome"
if ($JavaFX -and $env:PATH_TO_FX) { Write-OK "PATH_TO_FX = $($env:PATH_TO_FX)" }
Write-Host ""
Write-Host "  Next steps:" -ForegroundColor Cyan
Write-Host "    1. Restart your terminal (or VS Code) to pick up environment changes"
Write-Host "    2. Open this repo in VS Code"
Write-Host "    3. Run:  .\make-javafx.ps1        (full build — also downloads libGDX via Maven)"
Write-Host "    4. Press Ctrl+F5 in VS Code to launch the game"
Write-Host ""
