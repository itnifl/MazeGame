param(
    [string]$LogDirectory = "releng\test-results",
    [ValidateSet(1,2,3,4)]
    [int]$StartAt = 1,   # 1=run all, 2=skip mirror rebuild, 3=skip mirror+verify, 4=skip mirror+verify+cache clear
    [switch]$SkipMirror,
    [ValidateSet('full','fast','fastest')]
    [string]$BuildMode = 'full',
    # Opt-in: step 1 also deletes the whole ~/.m2/repository (shared with every other
    # Maven project on the machine). Off by default; Tycho's cache is cleared in step 3.
    [switch]$PurgeMavenRepository
)

# Ensure we run from the script directory (repo root assumed)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Reuse the shared Java 21 and mirror helpers from make-javafx.ps1 (same approach as
# make-libgdx.ps1). Loaded before this script's own variables so those keep their values.
. (Join-Path $scriptRoot 'make-javafx.ps1') -Target help *> $null

# Prepare log file
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$logFile   = Join-Path $LogDirectory "p2-and-build-check_$timestamp.log"
$UserHome  = if ($env:USERPROFILE) { $env:USERPROFILE } elseif ($env:HOME) { $env:HOME } else { [Environment]::GetFolderPath([Environment+SpecialFolder]::UserProfile) }
$M2RepoDir = Join-Path $UserHome '.m2/repository'
$TychoCacheDir = Join-Path $M2RepoDir '.cache/tycho'
$LocalP2Dir = Join-Path 'releng' 'local-p2'

$stepSummaries = New-Object System.Collections.Generic.List[object]

if ($SkipMirror -and $StartAt -lt 2) {
    $StartAt = 2
}

Write-Host "=== Run-P2AndBuildCheck-javafx quick usage ===" -ForegroundColor Cyan
Write-Host "Avoid mirror rebuild: .\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror"
Write-Host "Avoid mirror rebuild (equivalent): .\Run-P2AndBuildCheck-javafx.ps1 -StartAt 2"
Write-Host "Fast build with tests: .\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror -BuildMode fast"
Write-Host "Fastest build, skip tests: .\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror -BuildMode fastest"
Write-Host "Also wipe ~/.m2/repository in step 1 (opt-in): .\Run-P2AndBuildCheck-javafx.ps1 -PurgeMavenRepository"
Write-Host "=======================================" -ForegroundColor Cyan

function Write-StepResult {
    param(
        [string]$Step,
        [string]$Status,
        [string]$CommandText,
        [string]$Summary,
        [object]$Output
    )
    $time = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    "================================================================" | Out-File -FilePath $logFile -Append
    "Step     : $Step"        | Out-File -FilePath $logFile -Append
    "Time     : $time"        | Out-File -FilePath $logFile -Append
    "Status   : $Status"      | Out-File -FilePath $logFile -Append
    "Command  :"              | Out-File -FilePath $logFile -Append
    $CommandText.Trim()       | Out-File -FilePath $logFile -Append
    if ($Summary) {
        "Summary  : $Summary" | Out-File -FilePath $logFile -Append
    }
    "Output  :"               | Out-File -FilePath $logFile -Append
    ($Output | Out-String).TrimEnd() | Out-File -FilePath $logFile -Append
    ""                        | Out-File -FilePath $logFile -Append

    $stepSummaries.Add([pscustomobject]@{
        Step    = $Step
        Status  = $Status
        Summary = $Summary
    }) | Out-Null

    Write-Host ("[{0}] {1} - {2}" -f $Status, $Step, $Summary)
}

function Skip-Step {
    param([string]$Step,[string]$Cmd)
    $reason = "Skipped by -StartAt=$StartAt."
    Write-StepResult -Step $Step -Status "SKIPPED" -CommandText $Cmd -Summary $reason -Output ""
}

# make-javafx.ps1's Use-Java21OrFail throws; keep this script's exit-code contract.
try { Use-Java21OrFail } catch { Write-Error $_; exit 1 }

$_activeJavaExe = if ($env:JAVA_HOME) { Get-JavaExecutablePathForHome -JavaHome $env:JAVA_HOME } else { (Get-Command java -ErrorAction SilentlyContinue).Source }
$_activeJavaMajor = Get-JavaMajorFromExecutable -JavaExe $_activeJavaExe
if ($_activeJavaMajor -ne 21) {
    Write-Error "Java 21 is required but active Java is version $_activeJavaMajor. Aborting."
    exit 1
}

# ------------------------------------------------------------------------------
# Step 0 - environment info (always run; cheap and useful)
$step0 = "0. Toolchain versions"
$cmdText0 = @'
mvn -version
java -version
'@
$output0 = & {
    & mvn -version
    & java -version
} 2>&1
Write-StepResult -Step $step0 -Status "OK" -CommandText $cmdText0 -Summary "Recorded Maven/Java versions." -Output $output0

# Step 1 - rebuild local p2 mirror
$step1   = "1. Rebuild local p2 mirror"
$cmdText1 = @'
Remove-Item -Recurse -Force releng/local-p2 -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path releng/local-p2
mvn -f releng/mirror/pom.xml -U verify
'@
if ($PurgeMavenRepository) {
    $cmdText1 = "Remove-Item -Recurse -Force ~/.m2/repository -ErrorAction SilentlyContinue`n" + $cmdText1
}
if ($StartAt -gt 1) {
    Skip-Step -Step $step1 -Cmd $cmdText1
} else {
    Write-Host "Starting step 1: Rebuild local p2 mirror..."
    $output1 = & {
        if ($PurgeMavenRepository) {
            Write-Host "Purging $M2RepoDir (-PurgeMavenRepository)" -ForegroundColor Yellow
            Remove-Item -Recurse -Force $M2RepoDir -ErrorAction SilentlyContinue
        }
        Remove-Item -Recurse -Force $LocalP2Dir -ErrorAction SilentlyContinue
        # New-Item, not mkdir: in pwsh on Linux/macOS mkdir is the native binary,
        # which would create a directory literally named 'releng\local-p2'.
        New-Item -ItemType Directory -Force -Path $LocalP2Dir | Out-Null
        & mvn -f releng/mirror/pom.xml -U verify
    } 2>&1
    if ($LASTEXITCODE -eq 0) {
        $status1  = "OK"
        $summary1 = "Mirror built successfully."
    } else {
        $status1  = "FAIL (exit code $LASTEXITCODE)"
        $summary1 = "Mirror build failed."
    }
    Write-Host "Completed step 1, status: $status1"
    Write-Host "Summary: $summary1"
    Write-StepResult -Step $step1 -Status $status1 -CommandText $cmdText1 -Summary $summary1 -Output $output1
}

# Step 2 - verify required bundles and features in local mirror
$step2   = "2. Verify required Eclipse bundles and features in local mirror"
$cmdText2 = @'
For every <iu><id> in releng/mirror/pom.xml (the single list of required units):
  - the unit id must be listed in releng/local-p2/content.jar
  - its artifact must exist: plugins/<id>_*.jar, or features/<name>_*.jar for <name>.feature.group
'@
if ($StartAt -gt 2) {
    Skip-Step -Step $step2 -Cmd $cmdText2
} else {
    Write-Host "Starting step 2: Verify required Eclipse bundles and features in local mirror..."
    try {
        # Derived from the mirror pom (shared helpers in make-javafx.ps1), so this check
        # cannot drift from what the mirror is actually built from.
        $requiredIds = Get-MirrorRequiredUnitIds -MirrorPomPath (Join-Path (Join-Path 'releng' 'mirror') 'pom.xml')
        $availableIds = Get-MirrorAvailableUnitIds -MirrorDir $LocalP2Dir
        $unitResults = @(Test-MirrorUnits -MirrorDir $LocalP2Dir -RequiredIds $requiredIds -AvailableUnitIds $availableIds)

        $missingMetadata = @($unitResults | Where-Object { -not $_.InMetadata } | ForEach-Object Id)
        $missingArtifacts = @($unitResults | Where-Object { -not $_.HasArtifact } | ForEach-Object Id)

        $summaryParts = @()
        if ($missingMetadata.Count -eq 0 -and $missingArtifacts.Count -eq 0) {
            $status2 = "OK"
            $summaryParts += "All $($requiredIds.Count) units required by releng/mirror/pom.xml are in the mirror metadata and have artifacts."
        } else {
            $status2 = "FAIL (see summary)"
            if ($missingMetadata.Count -gt 0) { $summaryParts += "Missing from content.jar: $($missingMetadata -join ', ')." }
            if ($missingArtifacts.Count -gt 0) { $summaryParts += "Missing artifacts: $($missingArtifacts -join ', ')." }
        }
        $summary2 = $summaryParts -join " "

        $combinedOutput2 = @("Unit check (id, in content.jar, artifact present):")
        $combinedOutput2 += $unitResults | Sort-Object Id | ForEach-Object {
            "{0} = metadata:{1} artifact:{2}" -f $_.Id, $_.InMetadata, $_.HasArtifact
        }

        Write-Host "Completed step 2, status: $status2"
        Write-Host "Summary: $summary2"
        Write-StepResult -Step $step2 -Status $status2 -CommandText $cmdText2 -Summary $summary2 -Output $combinedOutput2
    }
    catch {
        $status2  = "FAIL (exception while checking mirror)"
        $summary2 = $_.Exception.Message
        Write-Host "Completed step 2, status: $status2"
        Write-Host "Summary: $summary2"
        Write-StepResult -Step $step2 -Status $status2 -CommandText $cmdText2 -Summary $summary2 -Output $_
    }
}

# Step 3 - clear Tycho p2 cache
$step3   = "3. Clear Tycho p2 cache"
$cmdText3 = @'
Remove-Item -Recurse -Force ~/.m2/repository/.cache/tycho -ErrorAction SilentlyContinue
'@
if ($StartAt -gt 3) {
    Skip-Step -Step $step3 -Cmd $cmdText3
} else {
    Write-Host "Starting step 3: Clear Tycho p2 cache..."
    $output3 = & {
        # USERPROFILE is unset on Linux/macOS; $TychoCacheDir derives from the portable $UserHome.
        Remove-Item -Recurse -Force $TychoCacheDir -ErrorAction SilentlyContinue
    } 2>&1
    $status3  = "OK"
    $summary3 = "Tycho cache folder removed if it existed."
    Write-Host "Completed step 3, status: $status3"
    Write-Host "Summary: $summary3"
    Write-StepResult -Step $step3 -Status $status3 -CommandText $cmdText3 -Summary $summary3 -Output $output3
}

# Step 4 - build
# The mirror module is handled by step 1; re-running it against a populated
# releng/local-p2 fails because Tycho's mirror goal appends by default.
$step4 = "4. Build"
switch ($BuildMode) {
    'full' {
        $cmdText4 = @'
mvn -pl '!releng/mirror' -U -DskipTests=false clean verify -e -X
'@
        $buildLabel = "Full build (Tycho + app)"
    }
    'fast' {
        $cmdText4 = @'
mvn -pl '!releng/mirror' -DskipTests=false verify
'@
        $buildLabel = "Fast build with tests"
    }
    'fastest' {
        $cmdText4 = @'
mvn -pl '!releng/mirror' -DskipTests=true verify
'@
        $buildLabel = "Fastest build without tests"
    }
}

Write-Host "Starting step 4: $buildLabel..."
$output4 = & {
    switch ($BuildMode) {
        'full'    { & mvn -pl '!releng/mirror' -U -DskipTests=false clean verify -e -X }
        'fast'    { & mvn -pl '!releng/mirror' -DskipTests=false verify }
        'fastest' { & mvn -pl '!releng/mirror' -DskipTests=true verify }
    }
} 2>&1
if ($LASTEXITCODE -eq 0) {
    $status4  = "OK"
    switch ($BuildMode) {
        'full'    { $summary4 = "Full build including tests completed successfully." }
        'fast'    { $summary4 = "Fast build including tests completed successfully." }
        'fastest' { $summary4 = "Fastest build without tests completed successfully." }
    }
} else {
    $status4  = "FAIL (exit code $LASTEXITCODE)"
    switch ($BuildMode) {
        'full'    { $summary4 = "Full build including tests failed." }
        'fast'    { $summary4 = "Fast build including tests failed." }
        'fastest' { $summary4 = "Fastest build without tests failed." }
    }
}
Write-Host "Completed step 4 ($buildLabel), status: $status4"
Write-Host "Summary: $summary4"
Write-StepResult -Step $step4 -Status $status4 -CommandText $cmdText4 -Summary $summary4 -Output $output4

# Overall summary
"================================================================" | Out-File -FilePath $logFile -Append
"Overall summary:" | Out-File -FilePath $logFile -Append
foreach ($s in $stepSummaries) {
    "Step: {0} | Status: {1} | Summary: {2}" -f $s.Step, $s.Status, $s.Summary | Out-File -FilePath $logFile -Append
}
Write-Host ""
Write-Host "Overall summary:"
foreach ($s in $stepSummaries) {
    Write-Host ("* {0} - {1} - {2}" -f $s.Step, $s.Status, $s.Summary)
}
Write-Host ""
Write-Host "Test run finished. Log written to:"
Write-Host "  $logFile"
