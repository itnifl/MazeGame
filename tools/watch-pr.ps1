param(
    [string]$Repository = 'itnifl/MazeGame',
    [int]$PullRequestNumber = 55,
    [int]$IntervalMinutes = 10,
    [int]$Cycles = 6,
    [string]$StateFile = (Join-Path $PSScriptRoot '.watch-pr-state.json'),
    [string]$LlmCommand = '',
    [string]$LlmPromptFile = (Join-Path $PSScriptRoot '.watch-pr-llm-prompt.txt'),
    [switch]$InvokeLlmAlways
)

$ErrorActionPreference = 'Stop'

function Invoke-GhApiJson {
    param(
        [Parameter(Mandatory = $true)][string]$Path
    )

    $stderrFile = [System.IO.Path]::GetTempFileName()
    $json = $null
    try {
        $json = & gh api $Path 2> $stderrFile
    } finally {
        $errorOutput = ''
        if (Test-Path $stderrFile) {
            $errorOutput = Get-Content -Raw -Path $stderrFile
            Remove-Item -Path $stderrFile -Force -ErrorAction SilentlyContinue
        }
    }

    if ($LASTEXITCODE -ne 0) {
        if ([string]::IsNullOrWhiteSpace($errorOutput)) {
            throw "gh api $Path failed"
        }
        throw "gh api $Path failed: $errorOutput"
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
        $ReviewComments = @(),
        $IssueComments = @(),
        $CheckRuns = @()
    )

    if (-not $ReviewComments) { $ReviewComments = @() }
    if (-not $IssueComments) { $IssueComments = @() }
    if (-not $CheckRuns) { $CheckRuns = @() }

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

function Get-FailingChecks {
    param(
        $CheckRuns
    )

    if (-not $CheckRuns) {
        return @()
    }

    return @($CheckRuns | Where-Object {
        $state = if ($_.conclusion) { [string]$_.conclusion } else { [string]$_.status }
        $normalized = $state.ToLowerInvariant()
        $normalized -in @('failure', 'failed', 'timed_out', 'cancelled', 'action_required')
    })
}

function Build-LlmPrompt {
    param(
        [Parameter(Mandatory = $true)]$PullRequest,
        $ReviewComments,
        $IssueComments,
        $FailingChecks
    )

    $reviewLines = @($ReviewComments | ForEach-Object {
        "- Review by @$($_.user.login): $((($_.body -replace '\s+', ' ').Trim()))"
    })
    $issueLines = @($IssueComments | ForEach-Object {
        "- Comment by @$($_.user.login): $((($_.body -replace '\s+', ' ').Trim()))"
    })
    $checkLines = @($FailingChecks | ForEach-Object {
        $state = if ($_.conclusion) { $_.conclusion } else { $_.status }
        "- $($_.name): $state"
    })

    if ($reviewLines.Count -eq 0) { $reviewLines = @('- None') }
    if ($issueLines.Count -eq 0) { $issueLines = @('- None') }
    if ($checkLines.Count -eq 0) { $checkLines = @('- None') }

    return @"
Repository: $Repository
PR: #$($PullRequest.number) $($PullRequest.title)
State: $($PullRequest.state)
Head SHA: $($PullRequest.head.sha)

Failing checks:
$($checkLines -join [Environment]::NewLine)

New review comments:
$($reviewLines -join [Environment]::NewLine)

New issue comments:
$($issueLines -join [Environment]::NewLine)

Instruction:
Check the new comments and CI status, then make the smallest safe code change needed, run focused validation, and keep the branch ready to push.
"@
}

function Invoke-LlmIfConfigured {
    param(
        [Parameter(Mandatory = $true)]$Prompt
    )

    if ([string]::IsNullOrWhiteSpace($LlmCommand)) {
        return
    }

    $promptDirectory = Split-Path -Parent $LlmPromptFile
    if ($promptDirectory -and -not (Test-Path $promptDirectory)) {
        New-Item -ItemType Directory -Path $promptDirectory -Force | Out-Null
    }

    $Prompt | Set-Content -Path $LlmPromptFile -Encoding UTF8

    $commandToRun = $LlmCommand.Replace('{PROMPT_FILE}', $LlmPromptFile)
    Write-Host "Invoking LLM command: $commandToRun" -ForegroundColor Magenta

    if ($commandToRun -match '[|;&><]') {
        & pwsh -NoLogo -NoProfile -Command $commandToRun
        return
    }

    $tokenMatches = [regex]::Matches($commandToRun, '"[^"]*"|\S+')
    if ($tokenMatches.Count -eq 0) {
        throw 'LlmCommand is empty after tokenization.'
    }

    $tokens = @()
    foreach ($tokenMatch in $tokenMatches) {
        $token = $tokenMatch.Value
        if ($token.StartsWith('"') -and $token.EndsWith('"')) {
            $token = $token.Substring(1, $token.Length - 2)
        }
        $tokens += $token
    }

    $exe = $tokens[0]
    $args = @()
    if ($tokens.Count -gt 1) {
        $args = @($tokens[1..($tokens.Count - 1)])
    }
    & $exe @args
}

Write-Host "Watching $Repository PR #$PullRequestNumber every $IntervalMinutes minute(s) for $Cycles cycle(s)." -ForegroundColor Green

for ($cycle = 1; $cycle -le $Cycles; $cycle++) {
    $state = Read-State

    $pullRequest = Invoke-GhApiJson -Path "repos/$Repository/pulls/$PullRequestNumber"
    if (-not $pullRequest -or -not $pullRequest.head -or [string]::IsNullOrWhiteSpace([string]$pullRequest.head.sha)) {
        Write-Host "Failed to fetch valid PR payload for #$PullRequestNumber. Skipping cycle." -ForegroundColor Red
        if ($cycle -lt $Cycles) {
            Start-Sleep -Seconds ($IntervalMinutes * 60)
        }
        continue
    }
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

    if (-not $newReviewComments) { $newReviewComments = @() }
    if (-not $newIssueComments) { $newIssueComments = @() }
    if (-not $checkRuns) { $checkRuns = @() }

    $failingChecks = Get-FailingChecks -CheckRuns $checkRuns
    Show-WorkPrompt -PullRequest $pullRequest -ReviewComments $newReviewComments -IssueComments $newIssueComments -CheckRuns $checkRuns

    $hasActionableWork = ($newReviewComments.Count -gt 0) -or ($newIssueComments.Count -gt 0) -or ($failingChecks.Count -gt 0)
    if ($InvokeLlmAlways -or $hasActionableWork) {
        $llmPrompt = Build-LlmPrompt -PullRequest $pullRequest -ReviewComments $newReviewComments -IssueComments $newIssueComments -FailingChecks $failingChecks
        Invoke-LlmIfConfigured -Prompt $llmPrompt
    }

    $state.ReviewCommentIds = @($reviewComments | ForEach-Object { [string]$_.id })
    $state.IssueCommentIds = @($issueComments | ForEach-Object { [string]$_.id })
    Write-State -State $state

    if ($cycle -lt $Cycles) {
        Start-Sleep -Seconds ($IntervalMinutes * 60)
    }
}