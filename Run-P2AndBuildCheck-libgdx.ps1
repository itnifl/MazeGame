param(
    [string]$LogDirectory = (Join-Path 'releng' 'test-results-libgdx'),
    [ValidateSet('full','fast','fastest')]
    [string]$BuildMode = 'full'
)

# Always run from the script directory (assume repo root)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$logFile   = Join-Path $LogDirectory "libgdx-build-check_$timestamp.log"

Write-Host "=== Run-P2AndBuildCheck-libgdx quick usage ===" -ForegroundColor Cyan
Write-Host "Full clean verify : .\Run-P2AndBuildCheck-libgdx.ps1"
Write-Host "Verify only       : .\Run-P2AndBuildCheck-libgdx.ps1 -BuildMode fast"
Write-Host "Verify skip tests : .\Run-P2AndBuildCheck-libgdx.ps1 -BuildMode fastest"
Write-Host "Logs              : $logFile"
Write-Host "==============================================" -ForegroundColor Cyan

# Reuse Java 21 detection from make-libgdx.ps1 (which in turn dot-sources make-javafx.ps1).
. (Join-Path $scriptRoot 'make-libgdx.ps1') -Target help *> $null 2>&1

$stepSummaries = New-Object System.Collections.Generic.List[object]

function Add-Summary($Name, $Outcome, $Detail) {
    $stepSummaries.Add([PSCustomObject]@{
        Step    = $Name
        Outcome = $Outcome
        Detail  = $Detail
    }) | Out-Null
}

function Invoke-Step([string]$Name, [scriptblock]$Action) {
    Write-Host ""
    Write-Host "=== Step: $Name ===" -ForegroundColor Cyan
    $started = Get-Date
    try {
        & $Action
        $elapsed = (Get-Date) - $started
        Add-Summary $Name 'OK' ("elapsed {0:N1}s" -f $elapsed.TotalSeconds)
        Write-Host "OK ($([Math]::Round($elapsed.TotalSeconds,1))s)" -ForegroundColor Green
    } catch {
        Add-Summary $Name 'FAILED' $_.Exception.Message
        Write-Host "FAILED: $($_.Exception.Message)" -ForegroundColor Red
        throw
    }
}

try {
    Start-Transcript -Path $logFile -Force | Out-Null

    Invoke-Step 'Toolchain (Java 21 + Maven)' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
    }

    switch ($BuildMode) {
        'full' {
            Invoke-Step 'Maven clean verify (maze-common-frontend + maze-libgdx)' {
                Invoke-LibgdxFullBuild
            }
        }
        'fast' {
            Invoke-Step 'Maven verify (tests on, no clean)' {
                Invoke-LibgdxQuickBuild
            }
        }
        'fastest' {
            Invoke-Step 'Maven verify (skip tests, no clean)' {
                Invoke-LibgdxQuickBuild -SkipTests
            }
        }
    }

    Invoke-Step 'Verify launch artifacts' {
        Ensure-LibgdxLaunchClasses
    }

    Write-Host ""
    Write-Host "=== Summary ===" -ForegroundColor Cyan
    $stepSummaries | Format-Table -AutoSize | Out-String | Write-Host
    Write-Host "Logs: $logFile" -ForegroundColor Cyan
}
finally {
    try { Stop-Transcript | Out-Null } catch { }
}
