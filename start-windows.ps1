#!/usr/bin/env pwsh
#Requires -Version 7
<#
.SYNOPSIS
    Check the Windows setup and launch the libGDX game.

.DESCRIPTION
    Windows counterpart of start-linux. Runs install.ps1 when the launch
    environment file, Java 21 or Maven is missing, then starts the game with
    make-libgdx.ps1 -Target run. Run from a normal (non-elevated) shell; if the
    installer needs elevation, run install.ps1 from an elevated shell first.
#>

$ErrorActionPreference = 'Stop'
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

if (-not $IsWindows) {
    Write-Error 'This launcher is intended for Windows. Use ./start-linux on Linux.'
    exit 1
}

function Test-Java21 {
    $candidates = @()
    if ($env:JAVA_HOME) { $candidates += (Join-Path $env:JAVA_HOME 'bin' 'javac.exe') }
    $onPath = Get-Command javac -ErrorAction SilentlyContinue
    if ($onPath) { $candidates += $onPath.Source }
    foreach ($javac in $candidates) {
        if (Test-Path $javac) {
            $version = (& $javac -version 2>&1) -join ' '
            if ($version -match '^javac 21\.') { return $true }
        }
    }
    return $false
}

$launchEnv = Join-Path $scriptRoot '.vscode' 'maze.launch.env'
$setupNeeded = (-not (Test-Path $launchEnv)) -or
    (-not (Get-Command mvn -ErrorAction SilentlyContinue)) -or
    (-not (Test-Java21))

if ($setupNeeded) {
    Write-Host 'MazeGame setup is missing or incomplete. Running install.ps1.'
    & (Join-Path $scriptRoot 'install.ps1')
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    # Pick up user-scope variables written by the installer in this session.
    foreach ($name in 'JAVA_HOME', 'PATH_TO_FX') {
        $value = [Environment]::GetEnvironmentVariable($name, 'User')
        if ($value) { Set-Item -Path "Env:$name" -Value $value }
    }
    $env:Path = [Environment]::GetEnvironmentVariable('Path', 'Machine') + ';' +
        [Environment]::GetEnvironmentVariable('Path', 'User')
}

& (Join-Path $scriptRoot 'make-libgdx.ps1') -Target run
exit $LASTEXITCODE
