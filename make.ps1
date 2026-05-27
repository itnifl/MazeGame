param(
    [ValidateSet('all','mirror','force-mirror','build','build-with-cache','clear-cache','toolchain')]
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

function Use-Java21IfAvailable {
    $javaHomeCandidates = @()

    if ($env:JAVA_HOME) {
        $javaHomeCandidates += $env:JAVA_HOME
    }

    $javaHomeCandidates += @(
        'C:\Program Files\Java\jdk-21',
        'C:\Program Files\Eclipse Adoptium\jdk-21*',
        'C:\Program Files\Microsoft\jdk-21*'
    )

    $resolvedHomes = @()
    foreach ($candidate in $javaHomeCandidates) {
        if ($candidate.Contains('*')) {
            $matched = Get-ChildItem -Path $candidate -Directory -ErrorAction SilentlyContinue |
                Sort-Object Name -Descending |
                Select-Object -ExpandProperty FullName
            $resolvedHomes += $matched
        } elseif (Test-Path $candidate) {
            $resolvedHomes += $candidate
        }
    }

    foreach ($javaHomePath in $resolvedHomes | Select-Object -Unique) {
        $javaExe = Join-Path $javaHomePath 'bin\\java.exe'
        $major = Get-JavaMajorFromExecutable $javaExe
        if ($major -eq $RequiredJavaMajor) {
            $env:JAVA_HOME = $javaHomePath
            if (-not (($env:Path -split ';') -contains (Join-Path $javaHomePath 'bin'))) {
                $env:Path = "$(Join-Path $javaHomePath 'bin');$env:Path"
            }
            Write-Host "Using Java $RequiredJavaMajor from $javaHomePath" -ForegroundColor Green
            return $true
        }
    }

    Write-Warning "Java $RequiredJavaMajor not found in common install locations. Using current PATH java."
    return $false
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
        Write-Host ""
        Write-Host "ERROR: Java $RequiredJavaMajor is required for Xtext/MWE2 generation." -ForegroundColor Red
        Write-Host "Current version: Java $currentMajor" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "To fix, set JAVA_HOME to Java 21 before running:" -ForegroundColor Cyan
        Write-Host '  $env:JAVA_HOME = "C:\Program Files\Java\jdk-21"' -ForegroundColor White
        Write-Host '  $env:Path = "$env:JAVA_HOME\bin;$env:Path"' -ForegroundColor White
        Write-Host '  .\make.ps1 build-with-cache' -ForegroundColor White
        Write-Host ""
        throw "Java version mismatch: found $currentMajor, required $RequiredJavaMajor"
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

switch ($Target) {
    'toolchain' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
    }

    'mirror' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
        Invoke-Mirror
    }

    'force-mirror' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
        Invoke-Mirror -Force
    }

    'clear-cache' {
        Clear-TychoCache
    }

    'build' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }

    'build-with-cache' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Invoke-Build
    }

    'all' {
        Use-Java21IfAvailable | Out-Null
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }
}
