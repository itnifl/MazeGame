param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$Args
)

# Compatibility wrapper for users typing makep.ps1.
# Forward all arguments to make.ps1.

$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

if (-not (Test-Path '.\make.ps1')) {
    Write-Error 'make.ps1 was not found in the repository root.'
    exit 1
}

Write-Host 'makep.ps1 wrapper: forwarding arguments to make.ps1' -ForegroundColor Cyan
Write-Host 'Tip: use .\make.ps1 help for quick no-mirror targets.' -ForegroundColor Yellow

& .\make.ps1 @Args
exit $LASTEXITCODE
