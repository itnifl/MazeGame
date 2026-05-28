param(
    [ValidateSet('all','help','mirror','force-mirror','build','build-with-cache','quick','quick-no-tests','clear-cache','toolchain')]
    [string]$Target = 'all'
)

# Always run from script directory (assume repo root)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Config
$Mvn            = 'mvn'   # change to 'mvnw' if you prefer wrapper
$LocalP2Dir     = 'releng\local-p2'
$MirrorStamp    = Join-Path $LocalP2Dir '.mirror.stamp'
$MirrorInputs   = @(
    'releng\mirror\pom.xml'
    # add more files here if you want them to trigger a mirror rebuild
)
$TychoCache     = Join-Path $env:USERPROFILE '.m2\repository\.cache\tycho'
$RequiredJavaMajor = 21

function Get-OSKind {
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Windows)) { return 'windows' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Linux)) { return 'linux' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::OSX)) { return 'macos' }
    return 'unknown'
}

function Get-JavaExecutablePathForHome([string]$javaHome) {
    if (-not $javaHome) { return $null }
    $name = if ((Get-OSKind) -eq 'windows') { 'java.exe' } else { 'java' }
    return Join-Path (Join-Path $javaHome 'bin') $name
}

function Get-JavaHomeFromJavaExecutable([string]$javaExe) {
    if (-not $javaExe) { return $null }
    $resolved = $null
    try { $resolved = (Resolve-Path $javaExe -ErrorAction Stop).Path } catch { $resolved = $javaExe }
    $binDir = Split-Path -Parent $resolved
    if (-not $binDir) { return $null }
    return Split-Path -Parent $binDir
}

function Add-ChildJavaHomes([System.Collections.Generic.List[string]]$bucket, [string]$parentDir) {
    if (-not $parentDir -or -not (Test-Path $parentDir)) { return }
    Get-ChildItem -Path $parentDir -Directory -ErrorAction SilentlyContinue |
        ForEach-Object {
            $exe = Get-JavaExecutablePathForHome $_.FullName
            if (Test-Path $exe) { $bucket.Add($_.FullName) }
        }
}

function Get-Java21SetupGuidance {
    return @"
Java 21 SDK is required but was not found.

Windows install options:
  winget install --id Microsoft.OpenJDK.21 -e
  winget install --id EclipseAdoptium.Temurin.21.JDK -e
  choco install temurin21 -y

macOS install options:
  brew install openjdk@21
  SDKMAN: sdk install java 21-tem

Linux install options:
  Ubuntu or Debian: sudo apt-get update; sudo apt-get install -y openjdk-21-jdk
  Fedora: sudo dnf install -y java-21-openjdk-devel
  Arch: sudo pacman -S jdk21-openjdk
  SDKMAN: sdk install java 21-tem

Setup:
  1. Set JAVA_HOME to your Java 21 home directory.
  2. Add JAVA_HOME/bin to PATH.
  3. Re-open your terminal and rerun this script.

Download URLs:
  Oracle JDK 21: https://www.oracle.com/java/technologies/downloads/#java21
  Eclipse Temurin 21: https://adoptium.net/temurin/releases/?version=21
  Microsoft Build of OpenJDK 21: https://learn.microsoft.com/java/openjdk/download
"@
}

function Get-Java21Home {
    $candidates = New-Object System.Collections.Generic.List[string]

    if ($env:JAVA_HOME) {
        $candidates.Add($env:JAVA_HOME)
        $parent = Split-Path -Parent $env:JAVA_HOME
        Add-ChildJavaHomes -bucket $candidates -parentDir $parent
        $grandParent = if ($parent) { Split-Path -Parent $parent } else { $null }
        Add-ChildJavaHomes -bucket $candidates -parentDir $grandParent
    }

    $javaCmd = Get-Command java -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($javaCmd) {
        $javaHome = Get-JavaHomeFromJavaExecutable $javaCmd.Source
        if ($javaHome) {
            $candidates.Add($javaHome)
            Add-ChildJavaHomes -bucket $candidates -parentDir (Split-Path -Parent $javaHome)
        }
    }

    $osKind = Get-OSKind
    if ($osKind -eq 'windows') {
        $whereExe = Get-Command where.exe -ErrorAction SilentlyContinue
        if ($whereExe) {
            (& where.exe java 2>$null) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable $_
                if ($h) { $candidates.Add($h) }
            }
        }
    } else {
        $whichCmd = Get-Command which -ErrorAction SilentlyContinue
        if ($whichCmd) {
            (& which -a java 2>$null | Select-Object -Unique) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable $_
                if ($h) { $candidates.Add($h) }
            }
        }
    }

    if ($osKind -eq 'macos' -and (Test-Path '/usr/libexec/java_home')) {
        $macHome = & /usr/libexec/java_home -v 21 2>$null
        if ($macHome) { $candidates.Add(($macHome | Select-Object -First 1).Trim()) }
    }

    if ($osKind -eq 'linux') {
        $ua = Get-Command update-alternatives -ErrorAction SilentlyContinue
        if ($ua) {
            (& update-alternatives --list java 2>$null) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable $_
                if ($h) { $candidates.Add($h) }
            }
        }
    }

    $sdkmanDir = Join-Path $HOME '.sdkman/candidates/java'
    Add-ChildJavaHomes -bucket $candidates -parentDir $sdkmanDir
    $asdfDir = Join-Path $HOME '.asdf/installs/java'
    Add-ChildJavaHomes -bucket $candidates -parentDir $asdfDir

    foreach ($candidateHome in ($candidates | Where-Object { $_ } | Select-Object -Unique)) {
        $javaExe = Get-JavaExecutablePathForHome $candidateHome
        $major = Get-JavaMajorFromExecutable $javaExe
        if ($major -eq $RequiredJavaMajor) {
            return $candidateHome
        }
    }

    return $null
}

function Get-JavaMajorFromExecutable([string]$javaExe) {
    if (-not (Test-Path $javaExe)) { return -1 }
    $javaOutput = & $javaExe -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') {
        return [int]$Matches[1]
    }
    if ($javaOutput -match 'version\s+"?(\d+)"') {
        return [int]$Matches[1]
    }
    return -1
}

function Use-Java21OrFail {
    $javaHomePath = Get-Java21Home
    if (-not $javaHomePath) {
        $guidance = Get-Java21SetupGuidance
        throw "Java $RequiredJavaMajor not found. `n`n$guidance"
    }

    $env:JAVA_HOME = $javaHomePath
    $javaBinPath = Join-Path $javaHomePath 'bin'
    $pathSeparator = [IO.Path]::PathSeparator
    if (-not (($env:Path -split [regex]::Escape($pathSeparator)) -contains $javaBinPath)) {
        $env:Path = "$javaBinPath$pathSeparator$env:Path"
    }

    $currentMajor = Test-JavaVersion
    if ($currentMajor -ne $RequiredJavaMajor) {
        $guidance = Get-Java21SetupGuidance
        throw "Java $RequiredJavaMajor is required, but active Java is $currentMajor. `n`n$guidance"
    }

    Write-Host "Using Java $RequiredJavaMajor from $javaHomePath" -ForegroundColor Green
}

function Test-JavaVersion {
    $javaOutput = & java -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') {
        $majorVersion = [int]$Matches[1]
        return $majorVersion
    }
    if ($javaOutput -match 'version\s+"?(\d+)"') {
        $majorVersion = [int]$Matches[1]
        return $majorVersion
    }
    return -1
}

function Assert-JavaVersion {
    $currentMajor = Test-JavaVersion
    if ($currentMajor -eq -1) {
        throw "Could not determine Java version. Ensure Java is installed and in PATH."
    }
    if ($currentMajor -ne $RequiredJavaMajor) {
        $guidance = Get-Java21SetupGuidance
        throw "Java version mismatch: found $currentMajor, required $RequiredJavaMajor. `n`n$guidance"
    }
    Write-Host "Java version check passed: Java $currentMajor" -ForegroundColor Green
}

function Show-ToolchainInfo {
    Write-Host "=== Toolchain versions ==="
    try {
        & $Mvn -version
    } catch {
        Write-Warning "Failed to run '$Mvn -version': $_"
    }
    Write-Host ""
    try {
        & java -version
    } catch {
        Write-Warning "Failed to run 'java -version': $_"
    }
    Write-Host "=========================="
}

function Show-Usage {
    Write-Host "=== MazeGame make.ps1 targets ===" -ForegroundColor Cyan
    Write-Host "all             : Full path. Mirror check/rebuild, clear Tycho cache, clean verify with tests."
    Write-Host "build           : Same as all."
    Write-Host "build-with-cache: Mirror check/rebuild, keep Tycho cache, clean verify with tests."
    Write-Host "quick           : Fast path. Skip mirror step, keep cache, verify with tests."
    Write-Host "quick-no-tests  : Fastest path. Skip mirror step, keep cache, verify with skipTests=true."
    Write-Host "mirror          : Mirror only, rebuild only if inputs changed."
    Write-Host "force-mirror    : Always rebuild mirror."
    Write-Host "clear-cache     : Remove Tycho cache only."
    Write-Host "toolchain       : Show Maven and Java versions."
    Write-Host ""
    Write-Host "To avoid mirror rebuilds, use: .\make.ps1 quick or .\make.ps1 quick-no-tests" -ForegroundColor Yellow
    Write-Host "=================================" -ForegroundColor Cyan
}

function Test-MirrorOutdated {
    if (-not (Test-Path $LocalP2Dir)) { return $true }
    if (-not (Test-Path $MirrorStamp)) { return $true }

    $stampTime = (Get-Item $MirrorStamp).LastWriteTime

    foreach ($path in $MirrorInputs) {
        if (-not (Test-Path $path)) { 
            # missing input → be safe and rebuild
            return $true 
        }
        $inputTime = (Get-Item $path).LastWriteTime
        if ($inputTime -gt $stampTime) {
            return $true
        }
    }

    return $false
}

function Invoke-Mirror([switch]$Force) {
    $rebuild = $Force -or (Test-MirrorOutdated)

    if (-not $rebuild) {
        Write-Host "=== Mirror is up-to-date, skipping rebuild ==="
        return
    }

    if ($Force) {
        Write-Host "=== Force rebuild of local p2 mirror ==="
    } else {
        Write-Host "=== Rebuilding local p2 mirror (inputs changed or missing) ==="
    }

    if (Test-Path $LocalP2Dir) {
        Write-Host "Removing $LocalP2Dir"
        Remove-Item -Recurse -Force $LocalP2Dir
    }

    $mirrorCmd = "$Mvn -f releng/mirror/pom.xml -U verify"
    Write-Host "Running: $mirrorCmd"
    & $Mvn -f releng/mirror/pom.xml -U verify
    $exit = $LASTEXITCODE

    if ($exit -ne 0) {
        throw "Mirror build failed with exit code $exit."
    }

    if (-not (Test-Path $LocalP2Dir)) {
        New-Item -ItemType Directory -Path $LocalP2Dir | Out-Null
    }
    New-Item -ItemType File -Path $MirrorStamp -Force | Out-Null
    Write-Host "=== Mirror built and stamp updated: $MirrorStamp ==="
}

function Clear-TychoCache {
    Write-Host "=== Clearing Tycho p2 cache (if present) ==="
    if (Test-Path $TychoCache) {
        Remove-Item -Recurse -Force $TychoCache
        Write-Host "Removed $TychoCache"
    } else {
        Write-Host "No Tycho cache folder found at $TychoCache"
    }
    Write-Host "Tycho cache cleared (if it existed)."
}

function Invoke-Build {
    $cmd = "$Mvn -U -DskipTests=false clean verify"
    Write-Host "=== Running full build: $cmd ==="
    & $Mvn -U -DskipTests=false clean verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Build failed with exit code $exit."
    }
    Write-Host "=== Build finished successfully ==="
}

function Invoke-QuickBuild {
    param([switch]$SkipTests)

    $skipValue = if ($SkipTests) { 'true' } else { 'false' }
    $cmd = "$Mvn -DskipTests=$skipValue verify"
    Write-Host "=== Running quick build without mirror rebuild: $cmd ==="
    & $Mvn "-DskipTests=$skipValue" verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Quick build failed with exit code $exit."
    }
    Write-Host "=== Quick build finished successfully ==="
}

switch ($Target) {
    'help' {
        Show-Usage
    }

    'toolchain' {
        Use-Java21OrFail
        Show-ToolchainInfo
    }

    'mirror' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Invoke-Mirror
    }

    'force-mirror' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Invoke-Mirror -Force
    }

    'clear-cache' {
        Clear-TychoCache
    }

    'build' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }

    'build-with-cache' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Invoke-Build
    }

    'quick' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-QuickBuild
    }

    'quick-no-tests' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-QuickBuild -SkipTests
    }

    'all' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }
}
