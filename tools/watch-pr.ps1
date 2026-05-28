param(
    [string]$Repository = 'itnifl/MazeGame',
    [int]$PullRequestNumber = 55,
    [int]$IntervalMinutes = 10,
    [int]$Cycles = 6,
    [string]$StateFile = (Join-Path $PSScriptRoot '.watch-pr-state.json')
)

$ErrorActionPreference = 'Stop'

function Invoke-GhApiJson {
    param(
        [Parameter(Mandatory = $true)][string]$Path
    )

    $json = & gh api $Path 2>$null
    if ($LASTEXITCODE -ne 0) {
        throw "gh api $Path failed"
    }

    if (-not $json) {
        return $null
    }

    return $json | ConvertFrom-Json
}

function Get-EmptyState {
    return [pscustomobject]@{
        ReviewCommentIds = @()
        IssueCommentIds = @()
    }
}

function Read-State {
    if (-not (Test-Path $StateFile)) {
        return Get-EmptyState
    }

    try {
        $raw = Get-Content -Raw -Path $StateFile
        if ([string]::IsNullOrWhiteSpace($raw)) {
            return Get-EmptyState
        }

        $state = $raw | ConvertFrom-Json
        if (-not $state.ReviewCommentIds) {
            $state | Add-Member -NotePropertyName ReviewCommentIds -NotePropertyValue @() -Force
        }
        if (-not $state.IssueCommentIds) {
            $state | Add-Member -NotePropertyName IssueCommentIds -NotePropertyValue @() -Force
        }

        return $state
    } catch {
        return Get-EmptyState
    }
}

function Write-State {
    param(
        [Parameter(Mandatory = $true)]$State
    )

    $directory = Split-Path -Parent $StateFile
    if ($directory -and -not (Test-Path $directory)) {
        New-Item -ItemType Directory -Path $directory -Force | Out-Null
    }

    $State | ConvertTo-Json -Depth 8 | Set-Content -Path $StateFile -Encoding UTF8
}

function Get-NewItems {
    param(
        [Parameter(Mandatory = $true)]$Items,
        [string[]]$SeenIds = @()
    )

    if (-not $SeenIds) {
        $SeenIds = @()
    }

    $newItems = @()
    foreach ($item in $Items) {
        $id = [string]$item.id
        if ($SeenIds -notcontains $id) {
            $newItems += $item
        }
    }

    return $newItems
}

function Show-WorkPrompt {
    param(
        [Parameter(Mandatory = $true)]$PullRequest,
        [Parameter(Mandatory = $true)]$ReviewComments,
        [Parameter(Mandatory = $true)]$IssueComments,
        [Parameter(Mandatory = $true)]$CheckRuns
    )

    Write-Host ''
    Write-Host "=== PR Watch Summary ===" -ForegroundColor Cyan
    Write-Host "Repository: $Repository"
    Write-Host "PR: #$($PullRequest.number) $($PullRequest.title)"
    Write-Host "State: $($PullRequest.state)"
    Write-Host "Head SHA: $($PullRequest.head.sha)"

    if ($CheckRuns.Count -gt 0) {
        Write-Host 'Checks:'
        foreach ($check in $CheckRuns) {
            $result = if ($check.conclusion) { $check.conclusion } else { $check.status }
            Write-Host "  - $($check.name): $result"
        }
    } else {
        Write-Host 'Checks: none returned yet'
    }

    if ($ReviewComments.Count -gt 0) {
        Write-Host 'New review comments:'
        foreach ($comment in $ReviewComments) {
            $body = ($comment.body -replace '\s+', ' ').Trim()
            Write-Host "  - @$($comment.user.login): $body"
        }
    } else {
        Write-Host 'New review comments: none'
    }

    if ($IssueComments.Count -gt 0) {
        Write-Host 'New issue comments:'
        foreach ($comment in $IssueComments) {
            $body = ($comment.body -replace '\s+', ' ').Trim()
            Write-Host "  - @$($comment.user.login): $body"
        }
    } else {
        Write-Host 'New issue comments: none'
    }

    Write-Host ''
    Write-Host 'Work instruction:' -ForegroundColor Yellow
    Write-Host 'Check the new comments and CI status, then make the smallest safe code change needed, run the focused validation, and keep the branch ready to push.' -ForegroundColor Yellow
}

Write-Host "Watching $Repository PR #$PullRequestNumber every $IntervalMinutes minute(s) for $Cycles cycle(s)." -ForegroundColor Green

for ($cycle = 1; $cycle -le $Cycles; $cycle++) {
    $state = Read-State

    $pullRequest = Invoke-GhApiJson -Path "repos/$Repository/pulls/$PullRequestNumber"
    $reviewComments = @()
    $issueComments = @()
    $checkRuns = @()

    $reviewComments = @(Invoke-GhApiJson -Path "repos/$Repository/pulls/$PullRequestNumber/comments")
    $issueComments = @(Invoke-GhApiJson -Path "repos/$Repository/issues/$PullRequestNumber/comments")
    $checkResponse = Invoke-GhApiJson -Path "repos/$Repository/commits/$($pullRequest.head.sha)/check-runs"
    if ($checkResponse -and $checkResponse.check_runs) {
        $checkRuns = @($checkResponse.check_runs)
    }

    $newReviewComments = Get-NewItems -Items $reviewComments -SeenIds @($state.ReviewCommentIds)
    $newIssueComments = Get-NewItems -Items $issueComments -SeenIds @($state.IssueCommentIds)

    Show-WorkPrompt -PullRequest $pullRequest -ReviewComments $newReviewComments -IssueComments $newIssueComments -CheckRuns $checkRuns

    $state.ReviewCommentIds = @($reviewComments | ForEach-Object { [string]$_.id })
    $state.IssueCommentIds = @($issueComments | ForEach-Object { [string]$_.id })
    Write-State -State $state

    if ($cycle -lt $Cycles) {
        Start-Sleep -Seconds ($IntervalMinutes * 60)
    }
}