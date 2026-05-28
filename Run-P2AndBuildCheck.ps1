param(
    [string]$LogDirectory = "releng\test-results",
    [ValidateSet(1,2,3,4)]
    [int]$StartAt = 1,   # 1=run all, 2=skip mirror rebuild, 3=skip mirror+verify, 4=skip mirror+verify+cache clear
    [switch]$SkipMirror,
    [ValidateSet('full','fast','fastest')]
    [string]$BuildMode = 'full'
)

# Ensure we run from the script directory (repo root assumed)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Prepare log file
New-Item -ItemType Directory -Force -Path $LogDirectory | Out-Null
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$logFile   = Join-Path $LogDirectory "p2-and-build-check_$timestamp.log"
$UserHome  = if ($env:USERPROFILE) { $env:USERPROFILE } elseif ($env:HOME) { $env:HOME } else { [Environment]::GetFolderPath([Environment+SpecialFolder]::UserProfile) }
$M2RepoDir = Join-Path $UserHome '.m2\repository'

$stepSummaries = New-Object System.Collections.Generic.List[object]

if ($SkipMirror -and $StartAt -lt 2) {
    $StartAt = 2
}

Write-Host "=== Run-P2AndBuildCheck quick usage ===" -ForegroundColor Cyan
Write-Host "Avoid mirror rebuild: .\Run-P2AndBuildCheck.ps1 -SkipMirror"
Write-Host "Avoid mirror rebuild (equivalent): .\Run-P2AndBuildCheck.ps1 -StartAt 2"
Write-Host "Fast build with tests: .\Run-P2AndBuildCheck.ps1 -SkipMirror -BuildMode fast"
Write-Host "Fastest build, skip tests: .\Run-P2AndBuildCheck.ps1 -SkipMirror -BuildMode fastest"
Write-Host "=======================================" -ForegroundColor Cyan

function Get-JavaMajorFromExecutable {
    param([string]$JavaExe)
    if (-not (Test-Path $JavaExe)) { return -1 }
    $javaOutput = & $JavaExe -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') { return [int]$Matches[1] }
    if ($javaOutput -match 'version\s+"?(\d+)"') { return [int]$Matches[1] }
    return -1
}

function Get-OSKind {
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Windows)) { return 'windows' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Linux)) { return 'linux' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::OSX)) { return 'macos' }
    return 'unknown'
}

function Get-JavaExecutablePathForHome {
    param([string]$JavaHome)
    if (-not $JavaHome) { return $null }
    $name = if ((Get-OSKind) -eq 'windows') { 'java.exe' } else { 'java' }
    return Join-Path (Join-Path $JavaHome 'bin') $name
}

function Get-JavaHomeFromJavaExecutable {
    param([string]$JavaExe)
    if (-not $JavaExe) { return $null }

    # Ask the executable for java.home first, this avoids symlink alias issues such as /usr/bin/java.
    $javaSettings = & $JavaExe -XshowSettings:properties -version 2>&1 | Out-String
    if ($javaSettings -match '(?m)^\s*java\.home\s*=\s*(.+)\s*$') {
        $reportedHome = $Matches[1].Trim()
        if ($reportedHome) { return $reportedHome }
    }

    $resolved = $null
    try { $resolved = (Resolve-Path $JavaExe -ErrorAction Stop).Path } catch { $resolved = $JavaExe }
    $binDir = Split-Path -Parent $resolved
    if (-not $binDir) { return $null }
    return Split-Path -Parent $binDir
}

function Test-IsJdkHome {
    param([string]$JavaHome)
    if (-not $JavaHome -or -not (Test-Path $JavaHome)) { return $false }

    $javaExe = Get-JavaExecutablePathForHome -JavaHome $JavaHome
    if (-not (Test-Path $javaExe)) { return $false }

    $javacName = if ((Get-OSKind) -eq 'windows') { 'javac.exe' } else { 'javac' }
    $javacExe = Join-Path (Join-Path $JavaHome 'bin') $javacName
    if (-not (Test-Path $javacExe)) { return $false }

    $releaseFile = Join-Path $JavaHome 'release'
    if (-not (Test-Path $releaseFile)) { return $false }

    return $true
}

function Add-ChildJavaHomes {
    param(
        [System.Collections.Generic.List[string]]$Bucket,
        [string]$ParentDir
    )
    if (-not $ParentDir -or -not (Test-Path $ParentDir)) { return }
    Get-ChildItem -Path $ParentDir -Directory -ErrorAction SilentlyContinue |
        ForEach-Object {
            $exe = Get-JavaExecutablePathForHome -JavaHome $_.FullName
            if (Test-Path $exe) { $Bucket.Add($_.FullName) }
        }
}

function Get-Java21SetupGuidance {
    return @"
Java 21 SDK is required but was not found.

Windows install options:
  winget install --id Microsoft.OpenJDK.21 -e
  winget install --id EclipseAdoptium.Temurin.21.JDK -e
  choco install temurin21 -y

macOS install options:
  brew install openjdk@21
  SDKMAN: sdk install java 21-tem

Linux install options:
  Ubuntu or Debian: sudo apt-get update; sudo apt-get install -y openjdk-21-jdk
  Fedora: sudo dnf install -y java-21-openjdk-devel
  Arch: sudo pacman -S jdk21-openjdk
  SDKMAN: sdk install java 21-tem

Setup:
  1. Set JAVA_HOME to your Java 21 home directory.
  2. Add JAVA_HOME/bin to PATH.
  3. Re-open your terminal and rerun this script.

Download URLs:
  Oracle JDK 21: https://www.oracle.com/java/technologies/downloads/#java21
  Eclipse Temurin 21: https://adoptium.net/temurin/releases/?version=21
  Microsoft Build of OpenJDK 21: https://learn.microsoft.com/java/openjdk/download
"@
}

function Get-Java21Home {
    param([int]$RequiredJavaMajor = 21)
    $candidates = New-Object System.Collections.Generic.List[string]

    if ($env:JAVA_HOME) {
        $candidates.Add($env:JAVA_HOME)
        $parent = Split-Path -Parent $env:JAVA_HOME
        Add-ChildJavaHomes -Bucket $candidates -ParentDir $parent
        $grandParent = if ($parent) { Split-Path -Parent $parent } else { $null }
        Add-ChildJavaHomes -Bucket $candidates -ParentDir $grandParent
    }

    $javaCmd = Get-Command java -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($javaCmd) {
        $javaHome = Get-JavaHomeFromJavaExecutable -JavaExe $javaCmd.Source
        if ($javaHome) {
            $candidates.Add($javaHome)
            Add-ChildJavaHomes -Bucket $candidates -ParentDir (Split-Path -Parent $javaHome)
        }
    }

    $osKind = Get-OSKind
    if ($osKind -eq 'windows') {
        $whereExe = Get-Command where.exe -ErrorAction SilentlyContinue
        if ($whereExe) {
            (& where.exe java 2>$null) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable -JavaExe $_
                if ($h) { $candidates.Add($h) }
            }
        }
    } else {
        $whichCmd = Get-Command which -ErrorAction SilentlyContinue
        if ($whichCmd) {
            (& which -a java 2>$null | Select-Object -Unique) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable -JavaExe $_
                if ($h) { $candidates.Add($h) }
            }
        }
    }

    if ($osKind -eq 'macos' -and (Test-Path '/usr/libexec/java_home')) {
        $macHome = & /usr/libexec/java_home -v 21 2>$null
        if ($macHome) { $candidates.Add(($macHome | Select-Object -First 1).Trim()) }
    }

    if ($osKind -eq 'linux') {
        $ua = Get-Command update-alternatives -ErrorAction SilentlyContinue
        if ($ua) {
            (& update-alternatives --list java 2>$null) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable -JavaExe $_
                if ($h) { $candidates.Add($h) }
            }
        }
    }

    $sdkmanDir = Join-Path $HOME '.sdkman/candidates/java'
    Add-ChildJavaHomes -Bucket $candidates -ParentDir $sdkmanDir
    $asdfDir = Join-Path $HOME '.asdf/installs/java'
    Add-ChildJavaHomes -Bucket $candidates -ParentDir $asdfDir

    foreach ($candidateHome in ($candidates | Where-Object { $_ } | Select-Object -Unique)) {
        if (-not (Test-IsJdkHome -JavaHome $candidateHome)) { continue }
        $javaExe = Get-JavaExecutablePathForHome -JavaHome $candidateHome
        $major = Get-JavaMajorFromExecutable -JavaExe $javaExe
        if ($major -eq $RequiredJavaMajor) { return $candidateHome }
    }

    return $null
}

function Use-Java21OrFail {
    $requiredJavaMajor = 21
    $javaHome = Get-Java21Home -RequiredJavaMajor $requiredJavaMajor
    if (-not $javaHome) {
        $guidance = Get-Java21SetupGuidance
        Write-Error "Java $requiredJavaMajor is required. `n`n$guidance"
        exit 1
    }

    $env:JAVA_HOME = $javaHome
    $javaBinPath = Join-Path $javaHome 'bin'
    $pathSeparator = [IO.Path]::PathSeparator
    if (-not (($env:Path -split [regex]::Escape($pathSeparator)) -contains $javaBinPath)) {
        $env:Path = "$javaBinPath$pathSeparator$env:Path"
    }

    $activeJavaExe = Get-JavaExecutablePathForHome -JavaHome $javaHome
    $activeMajor = Get-JavaMajorFromExecutable -JavaExe $activeJavaExe
    if ($activeMajor -ne $requiredJavaMajor) {
        $guidance = Get-Java21SetupGuidance
        Write-Error "Java $requiredJavaMajor is required but active Java is version $activeMajor. `n`n$guidance"
        exit 1
    }

    Write-Host "Using Java $requiredJavaMajor from $javaHome" -ForegroundColor Green
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

Use-Java21OrFail

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
Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
mkdir -p releng\local-p2
mvn -f releng/mirror/pom.xml -U verify
'@
if ($StartAt -gt 1) {
    Skip-Step -Step $step1 -Cmd $cmdText1
} else {
    Write-Host "Starting step 1: Rebuild local p2 mirror..."
    $output1 = & {
        Remove-Item -Recurse -Force $M2RepoDir -ErrorAction SilentlyContinue
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
            Get-ChildItem releng\local-p2\plugins\org.eclipse.core.runtime_*,
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

# Step 4 - build
$step4 = "4. Build"
switch ($BuildMode) {
    'full' {
        $cmdText4 = @'
mvn -U -DskipTests=false clean verify -e -X
'@
        $buildLabel = "Full build (Tycho + app)"
    }
    'fast' {
        $cmdText4 = @'
mvn -DskipTests=false verify
'@
        $buildLabel = "Fast build with tests"
    }
    'fastest' {
        $cmdText4 = @'
mvn -DskipTests=true verify
'@
        $buildLabel = "Fastest build without tests"
    }
}

Write-Host "Starting step 4: $buildLabel..."
$output4 = & {
    switch ($BuildMode) {
        'full'    { & mvn -U -DskipTests=false clean verify -e -X }
        'fast'    { & mvn -DskipTests=false verify }
        'fastest' { & mvn -DskipTests=true verify }
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