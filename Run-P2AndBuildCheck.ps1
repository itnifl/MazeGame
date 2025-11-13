param(
    [string]$LogDirectory = "releng\test-results"
)
# After this script finishes you get a log file under
# releng\test-results\p2-and-build-check_yyyyMMdd_HHmmss.log
# with sections for each step, their commands, status, summary, and the captured output.
# Ensure we run from the directory where the script lives (assumed repo root)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Prepare log file
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$logFile   = Join-Path $LogDirectory "p2-and-build-check_$timestamp.log"

$stepSummaries = New-Object System.Collections.Generic.List[object]

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

    # Also print a concise summary line to the terminal
    Write-Host ("[{0}] {1} — {2}" -f $Status, $Step, $Summary)
}

# Step 0 ─ environment info
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

# Step 1 ─ rebuild local p2 mirror
$step1     = "1. Rebuild local p2 mirror"
$cmdText1 = @'
Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
mvn -f releng/mirror/pom.xml -U verify
'@

Write-Host "Starting step 1: Rebuild local p2 mirror..."
$output1 = & {
    Remove-Item -Recurse -Force $env:USERPROFILE\.m2\repository -ErrorAction SilentlyContinue
    Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
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

# Step 2 ─ verify EMF and OCL bundles in local mirror
$step2     = "2. Verify EMF and OCL bundles in local mirror"
$cmdText2 = @'
dir releng\local-p2\plugins\org.eclipse.emf.ecore_*,
    releng\local-p2\plugins\org.eclipse.emf.common_*,
    releng\local-p2\plugins\org.eclipse.emf.ecore.xmi_*,
    releng\local-p2\plugins\org.eclipse.ocl.pivot_* -ErrorAction SilentlyContinue

$tmp = Join-Path $env:TEMP "p2check_$([guid]::NewGuid())"
New-Item -ItemType Directory -Path $tmp | Out-Null
Expand-Archive -Path releng\local-p2\content.jar -DestinationPath $tmp

Select-String -Path (Join-Path $tmp "content.xml") `
    -Pattern "org\.eclipse\.emf\.ecore|org\.eclipse\.emf\.common|org\.eclipse\.emf\.ecore\.xmi|org\.eclipse\.ocl\.pivot"

Remove-Item -Recurse -Force $tmp
'@

$tmp = Join-Path $env:TEMP ("p2check_{0}" -f [guid]::NewGuid())
$dirOutput   = $null
$grepOutput  = $null
$missingJars = @()
$namesToCheck = @(
    "org.eclipse.emf.ecore",
    "org.eclipse.emf.common",
    "org.eclipse.emf.ecore.xmi",
    "org.eclipse.ocl.pivot"
)
$foundPatterns = @{}
foreach ($n in $namesToCheck) { $foundPatterns[$n] = $false }

Write-Host "Starting step 2: Verify EMF and OCL bundles in local mirror..."
try {
    # Directory listing of plugin jars
    $dirOutput = & {
        dir releng\local-p2\plugins\org.eclipse.emf.ecore_*,
            releng\local-p2\plugins\org.eclipse.emf.common_*,
            releng\local-p2\plugins\org.eclipse.emf.ecore.xmi_*,
            releng\local-p2\plugins\org.eclipse.ocl.pivot_* -ErrorAction SilentlyContinue
    } 2>&1

    # Check each expected jar pattern exists
    $jarPaths = @(
        "releng\local-p2\plugins\org.eclipse.emf.ecore_*",
        "releng\local-p2\plugins\org.eclipse.emf.common_*",
        "releng\local-p2\plugins\org.eclipse.emf.ecore.xmi_*",
        "releng\local-p2\plugins\org.eclipse.ocl.pivot_*"
    )
    foreach ($jp in $jarPaths) {
        if (-not (Get-ChildItem -Path $jp -ErrorAction SilentlyContinue)) {
            $missingJars += $jp
        }
    }

    # Extract content.jar and grep content.xml
    New-Item -ItemType Directory -Path $tmp | Out-Null
    Expand-Archive -Path "releng\local-p2\content.jar" -DestinationPath $tmp

    $grepOutput = Select-String -Path (Join-Path $tmp "content.xml") `
        -Pattern "org\.eclipse\.emf\.ecore|org\.eclipse\.emf\.common|org\.eclipse\.emf\.ecore\.xmi|org\.eclipse\.ocl\.pivot" 2>&1

    foreach ($line in $grepOutput) {
        foreach ($n in $namesToCheck) {
            if ($line.Line -match [regex]::Escape($n)) {
                $foundPatterns[$n] = $true
            }
        }
    }

    $missingPatterns = ($foundPatterns.GetEnumerator() | Where-Object { -not $_.Value } | Select-Object -ExpandProperty Name)

    $summaryParts = @()
    if ($missingJars.Count -eq 0) {
        $summaryParts += "All expected EMF and OCL jars present in plugins folder."
    } else {
        $summaryParts += "Missing jar patterns: $($missingJars -join ", ")."
    }

    if ($missingPatterns.Count -eq 0) {
        $summaryParts += "All expected IUs present in content.xml."
    } else {
        $summaryParts += "Missing IU names in content.xml: $($missingPatterns -join ", ")."
    }

    if ($missingJars.Count -eq 0 -and $missingPatterns.Count -eq 0) {
        $status2 = "OK"
    } else {
        $status2 = "FAIL (see summary)"
    }

    $summary2 = $summaryParts -join " "

    $patternSummaryLines = $foundPatterns.GetEnumerator() |
        Sort-Object Name |
        ForEach-Object { "{0} = {1}" -f $_.Name, ($(if ($_.Value) { "found" } else { "missing" })) }

    $combinedOutput2 = @()
    $combinedOutput2 += "Directory listing of EMF and OCL jars:"
    $combinedOutput2 += ($dirOutput | Out-String)
    $combinedOutput2 += ""
    $combinedOutput2 += "content.xml matches (Select-String):"
    $combinedOutput2 += ($grepOutput | Out-String)
    $combinedOutput2 += ""
    $combinedOutput2 += "Pattern summary:"
    $combinedOutput2 += $patternSummaryLines

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
finally {
    if (Test-Path $tmp) {
        Remove-Item -Recurse -Force $tmp -ErrorAction SilentlyContinue
    }
}

# Step 3 ─ clear Tycho p2 cache
$step3     = "3. Clear Tycho p2 cache"
$cmdText3 = @'
Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
'@
Write-Host "Starting step 3: Clear Tycho p2 cache..."
$output3 = & {
    Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
} 2>&1

$status3  = "OK"
$summary3 = "Tycho cache folder removed if it existed."

Write-Host "Completed step 3, status: $status3"
Write-Host "Summary: $summary3"
Write-StepResult -Step $step3 -Status $status3 -CommandText $cmdText3 -Summary $summary3 -Output $output3

# Step 4 ─ full Tycho + app build
$step4     = "4. Full build (Tycho + app)"
$cmdText4 = @'
mvn -U -DskipTests=false -Dtycho.localArtifacts=ignore clean verify -e -X
'@

Write-Host "Starting step 4: Full build (Tycho + app)..."
$output4 = & {
    & mvn -U -DskipTests=false clean verify
} 2>&1

if ($LASTEXITCODE -eq 0) {
    $status4  = "OK"
    $summary4 = "Full build including tests completed successfully."
} else {
    $status4  = "FAIL (exit code $LASTEXITCODE)"
    $summary4 = "Full build including tests failed."
}

Write-Host "Completed step 4, status: $status4"
Write-Host "Summary: $summary4"
Write-StepResult -Step $step4 -Status $status4 -CommandText $cmdText4 -Summary $summary4 -Output $output4

# Overall summary at end of file and to terminal
"================================================================" | Out-File -FilePath $logFile -Append
"Overall summary:"             | Out-File -FilePath $logFile -Append
foreach ($s in $stepSummaries) {
    "Step: {0} | Status: {1} | Summary: {2}" -f $s.Step, $s.Status, $s.Summary |
        Out-File -FilePath $logFile -Append
}

Write-Host ""
Write-Host "Overall summary:"
foreach ($s in $stepSummaries) {
    Write-Host ("• {0} — {1} — {2}" -f $s.Step, $s.Status, $s.Summary)
}
Write-Host ""

Write-Host "Test run finished. Log written to:"
Write-Host "  $logFile"