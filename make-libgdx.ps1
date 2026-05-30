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
$GeneratedSourceDir = Join-Path 'maze-module-generator' 'src-gen/main/game/maze/generated'
$XtextGeneratedDirs = @(
    (Join-Path 'main.game.maze.dsl' 'src/main/xtext-gen'),
    (Join-Path 'main.game.maze.dsl.ide' 'src/main/xtext-gen'),
    (Join-Path 'main.game.maze.dsl.ui' 'src/main/xtext-gen'),
    (Join-Path 'main.game.maze.dsl.tests' 'src/test/xtext-gen')
)

# Reuse the shared Java 21 helpers from make-javafx.ps1. They live in the same file in this repo,
# so we dot-source it but suppress its top-level switch by stripping the argument.
$requestedTarget = $Target
. (Join-Path $scriptRoot 'make-javafx.ps1') -Target help *> $null 2>&1
$Target = $requestedTarget

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
    Write-Host "If generated Xtext or FreeMarker sources are missing, this script regenerates them automatically." -ForegroundColor Yellow
    Write-Host "Note: the libGDX backend is a plain Maven build. No Tycho mirror or p2 cache is involved." -ForegroundColor Yellow
    Write-Host "=========================================" -ForegroundColor Cyan
}

function Test-GeneratedSourcesPresent {
    if (-not (Test-Path $GeneratedSourceDir)) {
        return $false
    }

    $anyJava = Get-ChildItem -Path $GeneratedSourceDir -File -Filter '*.java' -ErrorAction SilentlyContinue |
        Select-Object -First 1
    return $null -ne $anyJava
}

function Test-XtextGeneratedSourcesPresent {
    foreach ($dir in $XtextGeneratedDirs) {
        if (-not (Test-Path $dir)) {
            return $false
        }

        $anyFile = Get-ChildItem -Path $dir -File -Recurse -ErrorAction SilentlyContinue |
            Select-Object -First 1
        if ($null -eq $anyFile) {
            return $false
        }
    }

    return $true
}

function Ensure-XtextGeneratedSources {
    if (Test-XtextGeneratedSourcesPresent) {
        Write-Host 'Generated Xtext sources already present.' -ForegroundColor Green
        return
    }

    Write-Host 'Generated Xtext sources are missing, regenerating...' -ForegroundColor Yellow
    & $Mvn -pl main.game.maze.dsl,main.game.maze.dsl.ide,main.game.maze.dsl.ui,main.game.maze.dsl.tests -am generate-sources
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Xtext generated source recovery failed with exit code $exit."
    }

    if (-not (Test-XtextGeneratedSourcesPresent)) {
        throw 'Xtext generated source recovery completed, but xtext-gen output is still missing.'
    }

    Write-Host 'Generated Xtext sources restored.' -ForegroundColor Green
}

function Ensure-GeneratedSources {
    Ensure-XtextGeneratedSources

    if (Test-GeneratedSourcesPresent) {
        Write-Host 'Generated FreeMarker sources already present.' -ForegroundColor Green
        return
    }

    Write-Host 'Generated FreeMarker sources are missing, regenerating via runner...' -ForegroundColor Yellow
    & $Mvn -pl maze-generator.freemarker-runner,maze-module-generator -am -Dskip.codegen=false generate-sources
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Generated source recovery failed with exit code $exit."
    }

    if (-not (Test-GeneratedSourcesPresent)) {
        throw "Generated source recovery completed, but no .java files were found in $GeneratedSourceDir."
    }

    Write-Host 'Generated FreeMarker sources restored.' -ForegroundColor Green
}

function Invoke-LibgdxFullBuild {
    Ensure-GeneratedSources
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
    Ensure-GeneratedSources
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
    Ensure-GeneratedSources
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
