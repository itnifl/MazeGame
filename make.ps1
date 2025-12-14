param(
    [ValidateSet('all','mirror','force-mirror','build','clear-cache','toolchain')]
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
        Show-ToolchainInfo
    }

    'mirror' {
        Show-ToolchainInfo
        Invoke-Mirror
    }

    'force-mirror' {
        Show-ToolchainInfo
        Invoke-Mirror -Force
    }

    'clear-cache' {
        Clear-TychoCache
    }

    'build' {
        Show-ToolchainInfo
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }

    'all' {
        Show-ToolchainInfo
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }
}
