param(
    [string]$LogDirectory = "releng\test-results",
    [ValidateSet(1,2,3,4)]
    [int]$StartAt = 1   # 1=run all, 2=skip 1, 3=skip 1-2, 4=skip 1-3
)

# Ensure we run from the script dir (repo root assumed)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Prepare log file
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$logFile   = Join-Path $LogDirectory "p2-and-build-check_$timestamp.log"

$stepSummaries = New-Object System.Collections.Generic.List[object]

function Get-JavaMajorFromExecutable {
    param([string]$JavaExe)
    if (-not (Test-Path $JavaExe)) { return -1 }
    $javaOutput = & $JavaExe -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') { return [int]$Matches[1] }
    if ($javaOutput -match 'version\s+"?(\d+)"') { return [int]$Matches[1] }
    return -1
}

function Use-Java21IfAvailable {
    $requiredJavaMajor = 21
    $javaHomeCandidates = @()

    if ($env:JAVA_HOME) {
        $javaHomeCandidates += $env:JAVA_HOME
    }

    $javaHomeCandidates += @(
        'C:\Program Files\Java\jdk-21',
        'C:\Program Files\Eclipse Adoptium\jdk-21*',
        'C:\Program Files\Microsoft\jdk-21*'
    )

    $resolvedHomes = @()
    foreach ($candidate in $javaHomeCandidates) {
        if ($candidate.Contains('*')) {
            $matched = Get-ChildItem -Path $candidate -Directory -ErrorAction SilentlyContinue |
                Sort-Object Name -Descending |
                Select-Object -ExpandProperty FullName
            $resolvedHomes += $matched
        }
        elseif (Test-Path $candidate) {
            $resolvedHomes += $candidate
        }
    }

    foreach ($javaHomePath in ($resolvedHomes | Select-Object -Unique)) {
        $javaExe = Join-Path $javaHomePath 'bin\java.exe'
        $major = Get-JavaMajorFromExecutable -JavaExe $javaExe
        if ($major -eq $requiredJavaMajor) {
            $env:JAVA_HOME = $javaHomePath
            $javaBinPath = Join-Path $javaHomePath 'bin'
            if (-not (($env:Path -split ';') -contains $javaBinPath)) {
                $env:Path = "$javaBinPath;$env:Path"
            }
            Write-Host "Using Java $requiredJavaMajor from $javaHomePath" -ForegroundColor Green
            return
        }
    }

    Write-Warning "Java $requiredJavaMajor not found in common install locations. Using current PATH java."
}

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

Use-Java21IfAvailable

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
Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
mkdir -p releng\local-p2
mvn -f releng/mirror/pom.xml -U verify
'@
if ($StartAt -gt 1) {
    Skip-Step -Step $step1 -Cmd $cmdText1
} else {
    Write-Host "Starting step 1: Rebuild local p2 mirror..."
    $output1 = & {
        Remove-Item -Recurse -Force $env:USERPROFILE\.m2\repository -ErrorAction SilentlyContinue
        Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
        & mkdir -p releng\local-p2
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
Check for these bundles/features in releng\local-p2 and content.xml:
  Bundles:
    org.eclipse.core.runtime
    org.eclipse.equinox.common
    org.eclipse.core.jobs
    org.eclipse.osgi
    org.eclipse.emf.ecore
    org.eclipse.emf.common
    org.eclipse.emf.ecore.xmi
    org.eclipse.ocl
    org.eclipse.ocl.ecore
    org.eclipse.ocl.pivot
    org.eclipse.xtext
    org.eclipse.xtext.xbase
    org.eclipse.xtext.xbase.lib
    org.eclipse.xtext.util
    org.eclipse.xtext.ide
    org.antlr.runtime
  Feature groups:
    org.eclipse.emf.sdk.feature.group
    org.eclipse.ocl.all.sdk.feature.group
    org.eclipse.emf.codegen.feature.group
    org.eclipse.xtext.sdk.feature.group
'@
if ($StartAt -gt 2) {
    Skip-Step -Step $step2 -Cmd $cmdText2
} else {
    $tmp = Join-Path $env:TEMP ("p2check_{0}" -f [guid]::NewGuid())
    $dirOutput   = $null
    $grepOutput  = $null
    $missingJars = @()

    # IU ids we expect to find in content.xml
    $namesToCheck = @(
        "org.eclipse.core.runtime",
        "org.eclipse.equinox.common",
        "org.eclipse.core.jobs",
        "org.eclipse.osgi",
        "org.eclipse.emf.ecore",
        "org.eclipse.emf.common",
        "org.eclipse.emf.ecore.xmi",
        "org.eclipse.ocl",
        "org.eclipse.ocl.ecore",
        "org.eclipse.ocl.pivot",
        "org.eclipse.emf.sdk.feature.group",
        "org.eclipse.ocl.all.sdk.feature.group",
        "org.eclipse.emf.codegen.feature.group",
        # Xtext dependencies (for DSL)
        "org.eclipse.xtext",
        "org.eclipse.xtext.xbase",
        "org.eclipse.xtext.xbase.lib",
        "org.eclipse.xtext.util",
        "org.eclipse.xtext.ide",
        "org.eclipse.xtext.sdk.feature.group",
        "org.antlr.runtime"
    )

    $foundPatterns = @{}
    foreach ($n in $namesToCheck) { $foundPatterns[$n] = $false }

    Write-Host "Starting step 2: Verify required Eclipse bundles and features in local mirror..."
    try {
        # Directory listing for jars and features
        $dirOutput = & {
            dir releng\local-p2\plugins\org.eclipse.core.runtime_*,
                releng\local-p2\plugins\org.eclipse.equinox.common_*,
                releng\local-p2\plugins\org.eclipse.core.jobs_*,
                releng\local-p2\plugins\org.eclipse.osgi_*,
                releng\local-p2\plugins\org.eclipse.emf.ecore_*,
                releng\local-p2\plugins\org.eclipse.emf.common_*,
                releng\local-p2\plugins\org.eclipse.emf.ecore.xmi_*,
                releng\local-p2\plugins\org.eclipse.ocl_*,
                releng\local-p2\plugins\org.eclipse.ocl.ecore_*,
                releng\local-p2\plugins\org.eclipse.ocl.pivot_*,
                releng\local-p2\features\org.eclipse.emf.sdk_*,
                releng\local-p2\features\org.eclipse.ocl.all.sdk_*,
                releng\local-p2\features\org.eclipse.emf.codegen_*,
                releng\local-p2\plugins\org.eclipse.xtext_*,
                releng\local-p2\plugins\org.eclipse.xtext.xbase_*,
                releng\local-p2\plugins\org.eclipse.xtext.xbase.lib_*,
                releng\local-p2\plugins\org.eclipse.xtext.util_*,
                releng\local-p2\plugins\org.eclipse.xtext.ide_*,
                releng\local-p2\features\org.eclipse.xtext.sdk_*,
                releng\local-p2\plugins\org.antlr.runtime_* -ErrorAction SilentlyContinue
        } 2>&1

        # Jar and feature patterns we expect to exist
        $jarPaths = @(
            "releng\local-p2\plugins\org.eclipse.core.runtime_*",
            "releng\local-p2\plugins\org.eclipse.equinox.common_*",
            "releng\local-p2\plugins\org.eclipse.core.jobs_*",
            "releng\local-p2\plugins\org.eclipse.osgi_*",
            "releng\local-p2\plugins\org.eclipse.emf.ecore_*",
            "releng\local-p2\plugins\org.eclipse.emf.common_*",
            "releng\local-p2\plugins\org.eclipse.emf.ecore.xmi_*",
            "releng\local-p2\plugins\org.eclipse.ocl_*",
            "releng\local-p2\plugins\org.eclipse.ocl.ecore_*",
            "releng\local-p2\plugins\org.eclipse.ocl.pivot_*",
            "releng\local-p2\features\org.eclipse.emf.sdk_*",          # org.eclipse.emf.sdk.feature.group
            "releng\local-p2\features\org.eclipse.ocl.all.sdk_*",      # org.eclipse.ocl.all.sdk.feature.group
            "releng\local-p2\features\org.eclipse.emf.codegen_*",      # org.eclipse.emf.codegen.feature.group
            # Xtext dependencies (for DSL)
            "releng\local-p2\plugins\org.eclipse.xtext_*",
            "releng\local-p2\plugins\org.eclipse.xtext.xbase_*",
            "releng\local-p2\plugins\org.eclipse.xtext.xbase.lib_*",
            "releng\local-p2\plugins\org.eclipse.xtext.util_*",
            "releng\local-p2\plugins\org.eclipse.xtext.ide_*",
            "releng\local-p2\features\org.eclipse.xtext.sdk_*",        # org.eclipse.xtext.sdk.feature.group
            "releng\local-p2\plugins\org.antlr.runtime_*"
        )
        foreach ($jp in $jarPaths) {
            if (-not (Get-ChildItem -Path $jp -ErrorAction SilentlyContinue)) { $missingJars += $jp }
        }

        # Unpack content.jar and search for IU ids
        New-Item -ItemType Directory -Path $tmp | Out-Null
        Expand-Archive -Path "releng\local-p2\content.jar" -DestinationPath $tmp

        $pattern = ($namesToCheck | ForEach-Object { [regex]::Escape($_) }) -join "|"
        $grepOutput = Select-String -Path (Join-Path $tmp "content.xml") `
            -Pattern $pattern 2>&1

        foreach ($line in $grepOutput) {
            foreach ($n in $namesToCheck) {
                if ($line.Line -match [regex]::Escape($n)) { $foundPatterns[$n] = $true }
            }
        }

        $missingPatterns = ($foundPatterns.GetEnumerator() | Where-Object { -not $_.Value } | Select-Object -ExpandProperty Name)

        $summaryParts = @()
        if ($missingJars.Count -eq 0) {
            $summaryParts += "All expected plugin and feature jar patterns present in releng\local-p2."
        } else {
            $summaryParts += "Missing jar patterns: $($missingJars -join ", ")."
        }

        if ($missingPatterns.Count -eq 0) {
            $summaryParts += "All expected IU ids present in content.xml."
        } else {
            $summaryParts += "Missing IU ids in content.xml: $($missingPatterns -join ", ")."
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
        $combinedOutput2 += "Directory listing of required bundles/features:"
        $combinedOutput2 += ($dirOutput | Out-String)
        $combinedOutput2 += ""
        $combinedOutput2 += "content.xml matches (Select-String):"
        $combinedOutput2 += ($grepOutput | Out-String)
        $combinedOutput2 += ""
        $combinedOutput2 += "Pattern summary (IU ids):"
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
        if (Test-Path $tmp) { Remove-Item -Recurse -Force $tmp -ErrorAction SilentlyContinue }
    }
}

# Step 3 - clear Tycho p2 cache
$step3   = "3. Clear Tycho p2 cache"
$cmdText3 = @'
Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
'@
if ($StartAt -gt 3) {
    Skip-Step -Step $step3 -Cmd $cmdText3
} else {
    Write-Host "Starting step 3: Clear Tycho p2 cache..."
    $output3 = & {
        Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
    } 2>&1
    $status3  = "OK"
    $summary3 = "Tycho cache folder removed if it existed."
    Write-Host "Completed step 3, status: $status3"
    Write-Host "Summary: $summary3"
    Write-StepResult -Step $step3 -Status $status3 -CommandText $cmdText3 -Summary $summary3 -Output $output3
}

# Step 4 - full Tycho + app build
$step4   = "4. Full build (Tycho + app)"
$cmdText4 = @'
mvn -U -DskipTests=false clean verify -e -X
'@
Write-Host "Starting step 4: Full build (Tycho + app)..."
$output4 = & {
    & mvn -U -DskipTests=false clean verify -e -X
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