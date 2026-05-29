param(
    [ValidateSet('all','help','build','quick','quick-no-tests','toolchain','write-launch-env','prepare-run','run')]
    [string]$Target = 'all'
)

# Always run from script directory (assume repo root)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Config
$Mvn               = 'mvn'
$LibgdxModules     = 'maze-common-frontend,maze-libgdx'
$RequiredJavaMajor = 21

# Reuse the shared Java 21 helpers from make-javafx.ps1. They live in the same file in this repo,
# so we dot-source it but suppress its top-level switch by stripping the argument.
. "$scriptRoot\make-javafx.ps1" -Target help *> $null 2>&1

# The dot-source above evaluates make-javafx.ps1 with -Target help (which only prints usage and
# defines all functions). That is enough to expose Get-Java21Home / Use-Java21OrFail / etc. for us.

function Show-LibgdxUsage {
    Write-Host "=== MazeGame make-libgdx.ps1 targets ===" -ForegroundColor Cyan
    Write-Host "all             : toolchain info + clean verify on $LibgdxModules with tests."
    Write-Host "build           : same as all."
    Write-Host "quick           : verify without clean, tests on."
    Write-Host "quick-no-tests  : verify without clean, tests off."
    Write-Host "toolchain       : show Maven and Java versions."
    Write-Host "write-launch-env: write .vscode/maze.launch.env with discovered Java 21."
    Write-Host "prepare-run     : write launch env, then build the libGDX runtime jar + libs."
    Write-Host "run             : after prepare-run, launch the libGDX backend (GdxAppLauncher)."
    Write-Host ""
    Write-Host "Note: the libGDX backend is a plain Maven build. No Tycho mirror or p2 cache is involved." -ForegroundColor Yellow
    Write-Host "=========================================" -ForegroundColor Cyan
}

function Invoke-LibgdxFullBuild {
    $cmd = "$Mvn -pl $LibgdxModules -am -U -DskipTests=false clean verify"
    Write-Host "=== Running libGDX full build: $cmd ===" -ForegroundColor Cyan
    & $Mvn -pl $LibgdxModules -am -U -DskipTests=false clean verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "libGDX full build failed with exit code $exit."
    }
    Write-Host "=== libGDX full build finished successfully ===" -ForegroundColor Green
}

function Invoke-LibgdxQuickBuild {
    param([switch]$SkipTests)
    $skipValue = if ($SkipTests) { 'true' } else { 'false' }
    Write-Host "=== Running libGDX quick build (skipTests=$skipValue) ===" -ForegroundColor Cyan
    & $Mvn -pl $LibgdxModules -am "-DskipTests=$skipValue" verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "libGDX quick build failed with exit code $exit."
    }
    Write-Host "=== libGDX quick build finished successfully ===" -ForegroundColor Green
}

function Ensure-LibgdxLaunchClasses {
    $launcherClass = Join-Path 'maze-libgdx' 'target/classes/main/game/maze/libgdx/GdxAppLauncher.class'
    $libsDir       = Join-Path 'maze-libgdx' 'target/libs'

    $needsBuild = (-not (Test-Path $launcherClass)) -or (-not (Test-Path $libsDir)) -or
                  (-not (Get-ChildItem -Path $libsDir -Filter 'gdx-*.jar' -ErrorAction SilentlyContinue | Select-Object -First 1))

    if (-not $needsBuild) {
        Write-Host 'libGDX launch artifacts already present.' -ForegroundColor Green
        return
    }

    Write-Host 'Building libGDX runtime jar + copying dependencies...' -ForegroundColor Yellow
    & $Mvn -pl $LibgdxModules -am -DskipTests=true package
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "libGDX prepare-run build failed with exit code $exit."
    }

    if (-not (Test-Path $launcherClass)) {
        throw "GdxAppLauncher.class still missing after build at $launcherClass"
    }
    if (-not (Get-ChildItem -Path $libsDir -Filter 'gdx-*.jar' -ErrorAction SilentlyContinue | Select-Object -First 1)) {
        throw "gdx-*.jar still missing in $libsDir after build."
    }
    Write-Host 'libGDX launch artifacts ready.' -ForegroundColor Green
}

function Invoke-LibgdxRun {
    Ensure-LibgdxLaunchClasses
    $sep = [IO.Path]::PathSeparator
    $classpath = @(
        'maze-libgdx/target/classes',
        'maze-libgdx/target/libs/*',
        'maze-common-frontend/target/classes'
    ) -join $sep
    $javaExe = if ($env:JAVA_HOME) {
        Get-JavaExecutablePathForHome $env:JAVA_HOME
    } else {
        'java'
    }

    $platformVmArgs = @()
    if ((Get-OSKind) -eq 'macos') {
        # LWJGL requires first-thread startup on macOS.
        $platformVmArgs += '-XstartOnFirstThread'
    }

    $vmArgsText = if ($platformVmArgs.Count -gt 0) { ($platformVmArgs -join ' ') + ' ' } else { '' }
    Write-Host "=== Launching libGDX backend: $javaExe $vmArgsText-cp `"$classpath`" main.game.maze.libgdx.GdxAppLauncher ===" -ForegroundColor Cyan
    & $javaExe @platformVmArgs -cp $classpath main.game.maze.libgdx.GdxAppLauncher
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "libGDX backend exited with code $exit."
    }
}

switch ($Target) {
    'help' {
        Show-LibgdxUsage
    }
    'toolchain' {
        Use-Java21OrFail
        Show-ToolchainInfo
    }
    'write-launch-env' {
        Use-Java21OrFail
        Write-LaunchEnvFile
    }
    'prepare-run' {
        Use-Java21OrFail
        Write-LaunchEnvFile
        Ensure-LibgdxLaunchClasses
    }
    'build' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-LibgdxFullBuild
    }
    'all' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-LibgdxFullBuild
    }
    'quick' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-LibgdxQuickBuild
    }
    'quick-no-tests' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-LibgdxQuickBuild -SkipTests
    }
    'run' {
        Use-Java21OrFail
        Write-LaunchEnvFile
        Invoke-LibgdxRun
    }
}
