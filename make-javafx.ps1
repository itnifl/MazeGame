param(
    [ValidateSet('all','help','mirror','force-mirror','build','build-with-cache','quick','quick-no-tests','clear-cache','toolchain','write-launch-env','prepare-run')]
    [string]$Target = 'all'
)

# Always run from script directory (assume repo root)
$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptRoot

# Config
$Mvn            = 'mvn'   # change to 'mvnw' if you prefer wrapper
$LocalP2Dir     = 'releng\local-p2'
$MirrorStamp    = Join-Path $LocalP2Dir '.mirror.stamp'
$MirrorInputStateFile = Join-Path $LocalP2Dir '.mirror.inputs'
$MirrorInputs   = @(
    'releng\mirror\pom.xml'
    # add more files here if you want them to trigger a mirror rebuild
)
$EclipseRepositoryBase = if ($env:MAZEGAME_ECLIPSE_REPOSITORY_BASE) {
    $env:MAZEGAME_ECLIPSE_REPOSITORY_BASE.Trim().TrimEnd('/')
} else {
    $null
}
$UserHome       = if ($env:USERPROFILE) { $env:USERPROFILE } elseif ($env:HOME) { $env:HOME } else { [Environment]::GetFolderPath([Environment+SpecialFolder]::UserProfile) }
$TychoCache     = Join-Path $UserHome '.m2\repository\.cache\tycho'
$RequiredJavaMajor = 21

function Get-OSKind {
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Windows)) { return 'windows' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::Linux)) { return 'linux' }
    if ([System.Runtime.InteropServices.RuntimeInformation]::IsOSPlatform([System.Runtime.InteropServices.OSPlatform]::OSX)) { return 'macos' }
    return 'unknown'
}

function Get-JavaExecutablePathForHome([string]$javaHome) {
    if (-not $javaHome) { return $null }
    $name = if ((Get-OSKind) -eq 'windows') { 'java.exe' } else { 'java' }
    return Join-Path (Join-Path $javaHome 'bin') $name
}

function Get-JavaHomeFromJavaExecutable([string]$javaExe) {
    if (-not $javaExe) { return $null }

    # Ask the executable for java.home first, this avoids symlink alias issues such as /usr/bin/java.
    $javaSettings = & $javaExe -XshowSettings:properties -version 2>&1 | Out-String
    if ($javaSettings -match '(?m)^\s*java\.home\s*=\s*(.+)\s*$') {
        $reportedHome = $Matches[1].Trim()
        if ($reportedHome) { return $reportedHome }
    }

    $resolved = $null
    try { $resolved = (Resolve-Path $javaExe -ErrorAction Stop).Path } catch { $resolved = $javaExe }
    $binDir = Split-Path -Parent $resolved
    if (-not $binDir) { return $null }
    return Split-Path -Parent $binDir
}

function Test-IsJdkHome([string]$javaHome) {
    if (-not $javaHome -or -not (Test-Path $javaHome)) { return $false }

    $javaExe = Get-JavaExecutablePathForHome $javaHome
    if (-not (Test-Path $javaExe)) { return $false }

    $javacName = if ((Get-OSKind) -eq 'windows') { 'javac.exe' } else { 'javac' }
    $javacExe = Join-Path (Join-Path $javaHome 'bin') $javacName
    if (-not (Test-Path $javacExe)) { return $false }

    $releaseFile = Join-Path $javaHome 'release'
    if (-not (Test-Path $releaseFile)) { return $false }

    return $true
}

function Add-ChildJavaHomes([System.Collections.Generic.List[string]]$bucket, [string]$parentDir) {
    if (-not $parentDir -or -not (Test-Path $parentDir)) { return }
    Get-ChildItem -Path $parentDir -Directory -ErrorAction SilentlyContinue |
        ForEach-Object {
            $exe = Get-JavaExecutablePathForHome $_.FullName
            if (Test-Path $exe) { $bucket.Add($_.FullName) }
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
    $candidates = New-Object System.Collections.Generic.List[string]

    if ($env:JAVA_HOME) {
        $candidates.Add($env:JAVA_HOME)
        $parent = Split-Path -Parent $env:JAVA_HOME
        Add-ChildJavaHomes -bucket $candidates -parentDir $parent
        $grandParent = if ($parent) { Split-Path -Parent $parent } else { $null }
        Add-ChildJavaHomes -bucket $candidates -parentDir $grandParent
    }

    $javaCmd = Get-Command java -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($javaCmd) {
        $javaHome = Get-JavaHomeFromJavaExecutable $javaCmd.Source
        if ($javaHome) {
            $candidates.Add($javaHome)
            Add-ChildJavaHomes -bucket $candidates -parentDir (Split-Path -Parent $javaHome)
        }
    }

    $osKind = Get-OSKind
    if ($osKind -eq 'windows') {
        $whereExe = Get-Command where.exe -ErrorAction SilentlyContinue
        if ($whereExe) {
            (& where.exe java 2>$null) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable $_
                if ($h) { $candidates.Add($h) }
            }
        }
    } else {
        $whichCmd = Get-Command which -ErrorAction SilentlyContinue
        if ($whichCmd) {
            (& which -a java 2>$null | Select-Object -Unique) | ForEach-Object {
                $h = Get-JavaHomeFromJavaExecutable $_
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
                $h = Get-JavaHomeFromJavaExecutable $_
                if ($h) { $candidates.Add($h) }
            }
        }
    }

    $sdkmanDir = Join-Path $HOME '.sdkman/candidates/java'
    Add-ChildJavaHomes -bucket $candidates -parentDir $sdkmanDir
    $asdfDir = Join-Path $HOME '.asdf/installs/java'
    Add-ChildJavaHomes -bucket $candidates -parentDir $asdfDir

    foreach ($candidateHome in ($candidates | Where-Object { $_ } | Select-Object -Unique)) {
        if (-not (Test-IsJdkHome $candidateHome)) { continue }
        $javaExe = Get-JavaExecutablePathForHome $candidateHome
        $major = Get-JavaMajorFromExecutable $javaExe
        if ($major -eq $RequiredJavaMajor) {
            return $candidateHome
        }
    }

    return $null
}

function Get-JavaMajorFromExecutable([string]$javaExe) {
    if (-not (Test-Path $javaExe)) { return -1 }
    $javaOutput = & $javaExe -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') {
        return [int]$Matches[1]
    }
    if ($javaOutput -match 'version\s+"?(\d+)"') {
        return [int]$Matches[1]
    }
    return -1
}

<#
.SYNOPSIS
    Returns a PATH-style string with Entry first and any other occurrences removed.
.DESCRIPTION
    Only prepending when Entry is missing is not enough: a caller such as install.ps1
    may already have appended it after another Java, which would then stay active.
    Comparison ignores case on Windows and is case-sensitive elsewhere.
.PARAMETER PathValue
    The current PATH value; may be empty.
.PARAMETER Entry
    The directory to put first, e.g. the Java 21 bin folder.
.PARAMETER Separator
    The PATH separator; defaults to the current OS separator (';' or ':').
#>
function Get-PathWithEntryFirst([string]$PathValue, [string]$Entry, [string]$Separator = [IO.Path]::PathSeparator) {
    $ignoreCase = (Get-OSKind) -eq 'windows'
    $rest = @($PathValue -split [regex]::Escape($Separator) | Where-Object {
        $_ -and -not [string]::Equals($_, $Entry, $(if ($ignoreCase) { 'OrdinalIgnoreCase' } else { 'Ordinal' }))
    })
    return (@($Entry) + $rest) -join $Separator
}

<#
.SYNOPSIS
    Activates Java 21 for this process or throws with setup guidance.
.DESCRIPTION
    Finds a Java 21 JDK (JAVA_HOME first, then common install locations), sets
    JAVA_HOME, puts its bin folder first on PATH and confirms that java now reports
    version 21. Throws if no Java 21 is found or another version is still active.
#>
function Use-Java21OrFail {
    $javaHomePath = Get-Java21Home
    if (-not $javaHomePath) {
        $guidance = Get-Java21SetupGuidance
        throw "Java $RequiredJavaMajor not found. `n`n$guidance"
    }

    $env:JAVA_HOME = $javaHomePath
    # $env:PATH, not $env:Path: environment names are case-sensitive on Linux/macOS.
    $env:PATH = Get-PathWithEntryFirst -PathValue $env:PATH -Entry (Join-Path $javaHomePath 'bin')

    $currentMajor = Test-JavaVersion
    if ($currentMajor -ne $RequiredJavaMajor) {
        $guidance = Get-Java21SetupGuidance
        throw "Java $RequiredJavaMajor is required, but active Java is $currentMajor. `n`n$guidance"
    }

    Write-Host "Using Java $RequiredJavaMajor from $javaHomePath" -ForegroundColor Green
}

function Test-JavaVersion {
    $javaOutput = & java -version 2>&1 | Out-String
    if ($javaOutput -match 'version\s+"?(\d+)\.') {
        $majorVersion = [int]$Matches[1]
        return $majorVersion
    }
    if ($javaOutput -match 'version\s+"?(\d+)"') {
        $majorVersion = [int]$Matches[1]
        return $majorVersion
    }
    return -1
}

function Assert-JavaVersion {
    $currentMajor = Test-JavaVersion
    if ($currentMajor -eq -1) {
        throw "Could not determine Java version. Ensure Java is installed and in PATH."
    }
    if ($currentMajor -ne $RequiredJavaMajor) {
        $guidance = Get-Java21SetupGuidance
        throw "Java version mismatch: found $currentMajor, required $RequiredJavaMajor. `n`n$guidance"
    }
    Write-Host "Java version check passed: Java $currentMajor" -ForegroundColor Green
}

function Show-ToolchainInfo {
    Write-Host "=== Toolchain versions ==="
    try {
        & $Mvn -version
    } catch {
        Write-Warning "Failed to run '$Mvn -version': $_"
    }
    Write-Host ""
    try {
        & java -version
    } catch {
        Write-Warning "Failed to run 'java -version': $_"
    }
    Write-Host "=========================="
}

function Show-Usage {
    Write-Host "=== MazeGame make-javafx.ps1 targets ===" -ForegroundColor Cyan
    Write-Host "all             : Full path. Mirror check/rebuild, clear Tycho cache, clean verify with tests."
    Write-Host "build           : Same as all."
    Write-Host "build-with-cache: Mirror check/rebuild, keep Tycho cache, clean verify with tests."
    Write-Host "quick           : Fast path. Skip mirror step, keep cache, verify with tests."
    Write-Host "quick-no-tests  : Fastest path. Skip mirror step, keep cache, verify with skipTests=true."
    Write-Host "mirror          : Mirror only, rebuild only if inputs changed."
    Write-Host "force-mirror    : Always rebuild mirror."
    Write-Host "clear-cache     : Remove Tycho cache only."
    Write-Host "toolchain       : Show Maven and Java versions."
    Write-Host "write-launch-env: Write .vscode/maze.launch.env with discovered Java 21 for debug launch."
    Write-Host "prepare-run     : Write launch env, verify runtime modules are compiled and fresh, then ensure maze classes are Java 21 bytecode."
    Write-Host ""
    Write-Host "To avoid mirror rebuilds, use: .\make-javafx.ps1 quick or .\make-javafx.ps1 quick-no-tests" -ForegroundColor Yellow
    Write-Host "=================================" -ForegroundColor Cyan
}

<#
.SYNOPSIS
    Writes .vscode/maze.launch.env for the VS Code launch configurations.
.DESCRIPTION
    Records the detected Java 21 home as JAVA_HOME and a PATH with its bin folder
    first. Throws if no Java 21 is found.
#>
function Write-LaunchEnvFile {
    $javaHomePath = Get-Java21Home
    if (-not $javaHomePath) {
        $guidance = Get-Java21SetupGuidance
        throw "Java $RequiredJavaMajor not found. `n`n$guidance"
    }

    $launchPath = Get-PathWithEntryFirst -PathValue $env:PATH -Entry (Join-Path $javaHomePath 'bin')

    $launchEnvFile = Join-Path '.vscode' 'maze.launch.env'
    New-Item -ItemType Directory -Path '.vscode' -Force | Out-Null
    @(
        "JAVA_HOME=$javaHomePath"
        "PATH=$launchPath"
    ) | Set-Content -Path $launchEnvFile -Encoding UTF8

    Write-Host "Wrote launch environment: $launchEnvFile" -ForegroundColor Green
}

function Get-ClassMajorVersion([string]$classFilePath) {
    if (-not (Test-Path $classFilePath)) { return -1 }
    $javapName = if ((Get-OSKind) -eq 'windows') { 'javap.exe' } else { 'javap' }
    $javapExe = Join-Path (Join-Path $env:JAVA_HOME 'bin') $javapName
    if (-not (Test-Path $javapExe)) { return -1 }

    $majorLine = (& $javapExe -verbose $classFilePath 2>$null | Select-String 'major version' | Select-Object -First 1)
    if (-not $majorLine) { return -1 }

    if ($majorLine.Line -match '(\d+)') {
        return [int]$Matches[1]
    }
    return -1
}

function Ensure-LaunchBytecodeLevel {
    $criticalClass = Join-Path 'main.game.maze.mazeworld' 'target/classes/main/game/maze/mazeworld/GameMazeWorld.class'
    $major = Get-ClassMajorVersion $criticalClass

    if ($major -eq 65) {
        Write-Host 'Launch bytecode check passed: GameMazeWorld.class is Java 21 compatible.' -ForegroundColor Green
        return
    }

    Write-Host "Detected stale or incompatible bytecode (major=$major). Rebuilding JavaFX runtime dependency graph with Java 21..." -ForegroundColor Yellow
    & $Mvn -pl maze-javafx-backend -am -DskipTests=true clean compile
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Failed to prepare launch classes with Java 21. Maven exited with code $exit."
    }

    $majorAfter = Get-ClassMajorVersion $criticalClass
    if ($majorAfter -ne 65) {
        throw "Prepared build completed but GameMazeWorld.class is still major=$majorAfter. Expected 65 for Java 21."
    }

    Write-Host 'Prepared launch classes are now Java 21 compatible.' -ForegroundColor Green
}

function Ensure-LaunchJavaFxLibs {
    $libsDir = Join-Path 'maze-javafx-backend' 'target/libs'
    $modulePathKeepPatterns = @(
        'javafx-*.jar',
        'jna-*.jar',
        'jna-platform-*.jar'
    )

    function Prune-ModulePathLibs([string]$dirPath, [string[]]$keepPatterns) {
        $allJars = Get-ChildItem -Path $dirPath -File -Filter '*.jar' -ErrorAction SilentlyContinue
        foreach ($jar in $allJars) {
            $keep = $false
            foreach ($pattern in $keepPatterns) {
                if ($jar.Name -like $pattern) {
                    $keep = $true
                    break
                }
            }
            if (-not $keep) {
                Remove-Item $jar.FullName -Force -ErrorAction SilentlyContinue
            }
        }
    }

    $requiredPatterns = @(
        'javafx-controls*.jar',
        'javafx-fxml*.jar',
        'javafx-media*.jar',
        'javafx-graphics*.jar',
        'javafx-base*.jar'
    )

    $missing = @()
    foreach ($pattern in $requiredPatterns) {
        $match = Get-ChildItem -Path $libsDir -Filter $pattern -ErrorAction SilentlyContinue | Select-Object -First 1
        if (-not $match) {
            $missing += $pattern
        }
    }

    if ($missing.Count -eq 0) {
        Prune-ModulePathLibs -dirPath $libsDir -keepPatterns $modulePathKeepPatterns
        Write-Host 'Launch JavaFX libs check passed: required modules exist in maze-javafx-backend/target/libs.' -ForegroundColor Green
        return
    }

    Write-Host ("Missing JavaFX launch libs ({0}). Restoring runtime dependencies..." -f ($missing -join ', ')) -ForegroundColor Yellow
    & $Mvn -pl maze-javafx-backend '-DskipTests=true' dependency:copy-dependencies '-DincludeScope=runtime' '-DoutputDirectory=target/libs' '-DexcludeGroupIds=p2.osgi.bundle' '-DexcludeArtifactIds=com.sun.jna'
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Failed to restore JavaFX runtime dependencies for launch. Maven exited with code $exit."
    }

    foreach ($pattern in $requiredPatterns) {
        $match = Get-ChildItem -Path $libsDir -Filter $pattern -ErrorAction SilentlyContinue | Select-Object -First 1
        if (-not $match) {
            throw "JavaFX launch dependency still missing after restore: $pattern"
        }
    }

    Prune-ModulePathLibs -dirPath $libsDir -keepPatterns $modulePathKeepPatterns

    Write-Host 'JavaFX runtime dependencies restored for launch.' -ForegroundColor Green
}

function Test-JarContainsClasses([string]$jarPath) {
    if (-not (Test-Path $jarPath)) { return $false }
    try {
        Add-Type -AssemblyName 'System.IO.Compression' -ErrorAction SilentlyContinue | Out-Null
        Add-Type -AssemblyName 'System.IO.Compression.FileSystem' -ErrorAction SilentlyContinue | Out-Null
        $zip = [System.IO.Compression.ZipFile]::OpenRead($jarPath)
        try {
            foreach ($entry in $zip.Entries) {
                if ($entry.FullName.EndsWith('.class')) { return $true }
            }
            return $false
        } finally {
            $zip.Dispose()
        }
    } catch {
        # If we cannot read the jar, treat as missing classes so it gets rebuilt.
        return $false
    }
}

function Get-NewestSourceWriteTime([string]$moduleDir) {
    $candidateDirs = @(
        (Join-Path $moduleDir 'src/main/java'),
        (Join-Path $moduleDir 'src/main/xtend'),
        (Join-Path $moduleDir 'src/main/resources'),
        (Join-Path $moduleDir 'xtend-gen'),
        (Join-Path $moduleDir 'src-gen'),
        (Join-Path $moduleDir 'model')
    )
    $candidatePatterns = @('*.java', '*.xtend', '*.ecore', '*.genmodel', '*.fxml', '*.properties', '*.xmi')

    $newest = [DateTime]::MinValue
    foreach ($dir in $candidateDirs) {
        if (-not (Test-Path $dir)) { continue }
        foreach ($pattern in $candidatePatterns) {
            $latest = Get-ChildItem -Path $dir -Recurse -File -Filter $pattern -ErrorAction SilentlyContinue |
                Sort-Object LastWriteTimeUtc -Descending |
                Select-Object -First 1
            if ($latest -and $latest.LastWriteTimeUtc -gt $newest) {
                $newest = $latest.LastWriteTimeUtc
            }
        }
    }

    # Include the module's own pom.xml and MANIFEST.MF so descriptor edits trigger a rebuild.
    foreach ($descriptor in @((Join-Path $moduleDir 'pom.xml'), (Join-Path $moduleDir 'META-INF/MANIFEST.MF'), (Join-Path $moduleDir 'plugin.xml'), (Join-Path $moduleDir 'build.properties'))) {
        if (Test-Path $descriptor) {
            $time = (Get-Item $descriptor).LastWriteTimeUtc
            if ($time -gt $newest) { $newest = $time }
        }
    }

    return $newest
}

function Test-PluginModuleStale([string]$moduleDir) {
    $artifactId = Split-Path -Leaf $moduleDir
    $targetDir  = Join-Path $moduleDir 'target'
    $jar = $null
    if (Test-Path $targetDir) {
        $jar = Get-ChildItem -Path $targetDir -File -Filter "$artifactId-*.jar" -ErrorAction SilentlyContinue |
            Sort-Object LastWriteTimeUtc -Descending | Select-Object -First 1
    }

    if (-not $jar) {
        return @{ Stale = $true; Reason = "jar missing under $targetDir" }
    }
    if (-not (Test-JarContainsClasses $jar.FullName)) {
        return @{ Stale = $true; Reason = "jar $($jar.Name) has no .class entries" }
    }

    $sourceTime = Get-NewestSourceWriteTime $moduleDir
    if ($sourceTime -gt $jar.LastWriteTimeUtc) {
        return @{ Stale = $true; Reason = "source newer than jar ($sourceTime > $($jar.LastWriteTimeUtc))" }
    }

    return @{ Stale = $false; Reason = '' }
}

function Test-MazeAppModuleStale([string]$moduleDir) {
    # Launcher.java moved to maze-javafx-backend; check for Launcher.class there.
    $classesDir = Join-Path $moduleDir 'target/classes'
    $launcherClass = Join-Path $classesDir 'main/game/maze/Launcher.class'
    if (-not (Test-Path $launcherClass)) {
        return @{ Stale = $true; Reason = "Launcher.class missing under $classesDir" }
    }

    $sourceTime = Get-NewestSourceWriteTime $moduleDir
    $classTime  = (Get-Item $launcherClass).LastWriteTimeUtc
    if ($sourceTime -gt $classTime) {
        return @{ Stale = $true; Reason = "source newer than Launcher.class ($sourceTime > $classTime)" }
    }

    return @{ Stale = $false; Reason = '' }
}

function Ensure-RuntimeModulesCompiled {
    # Runtime closure rooted at maze-javafx-backend/pom.xml. Order matters: producers before consumers.
    $pluginModules = @(
        'main.game.maze.difficulties',
        'main.game.maze.opponents',
        'main.game.maze.mazeworld',
        'main.game.maze.behaviour'
    )

    $stale = New-Object System.Collections.Generic.List[string]
    foreach ($moduleName in $pluginModules) {
        $moduleDir = Join-Path $scriptRoot $moduleName
        if (-not (Test-Path $moduleDir)) {
            Write-Host "Skipping missing module: $moduleName" -ForegroundColor DarkYellow
            continue
        }
        $check = Test-PluginModuleStale $moduleDir
        if ($check.Stale) {
            Write-Host ("Module '{0}' needs rebuild: {1}" -f $moduleName, $check.Reason) -ForegroundColor Yellow
            $stale.Add($moduleName)
        } else {
            Write-Host "Module '$moduleName' up to date." -ForegroundColor DarkGreen
        }
    }

    # Launcher is in maze-javafx-backend.
    $mazeBackendDir = Join-Path $scriptRoot 'maze-javafx-backend'
    $mazeCheck = Test-MazeAppModuleStale $mazeBackendDir
    $mazeStale = $mazeCheck.Stale
    if ($mazeStale) {
        Write-Host ("Module 'maze-javafx-backend' needs rebuild: {0}" -f $mazeCheck.Reason) -ForegroundColor Yellow
    } else {
        Write-Host "Module 'maze-javafx-backend' up to date." -ForegroundColor DarkGreen
    }

    if ($stale.Count -eq 0 -and -not $mazeStale) {
        Write-Host 'Compiled artifacts are up to date with sources.' -ForegroundColor Green
        return
    }

    $rebuildList = @($stale)
    if ($mazeStale) { $rebuildList += 'maze-javafx-backend' }
    $plArg = ($rebuildList -join ',')

    Write-Host "=== Rebuilding stale modules: $plArg ===" -ForegroundColor Cyan
    & $Mvn -pl $plArg -am -DskipTests=true clean install
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Auto-rebuild of stale modules failed. Maven exited with code $exit."
    }

    # Verify all targeted modules are now fresh.
    foreach ($moduleName in $stale) {
        $moduleDir = Join-Path $scriptRoot $moduleName
        $recheck = Test-PluginModuleStale $moduleDir
        if ($recheck.Stale) {
            throw "Module '$moduleName' is still stale after rebuild: $($recheck.Reason)"
        }
    }
    if ($mazeStale) {
        $recheck = Test-MazeAppModuleStale $mazeBackendDir
        if ($recheck.Stale) {
            throw "Module 'maze-javafx-backend' is still stale after rebuild: $($recheck.Reason)"
        }
    }

    Write-Host '=== Stale modules rebuilt successfully ===' -ForegroundColor Green
}

<#
.SYNOPSIS
    Returns $true when releng/local-p2 must be (re)built.
.DESCRIPTION
    True if the mirror folder, its completion stamp or its saved input state is
    missing (e.g. an interrupted run), or if the inputs changed since the last build.
#>
function Test-MirrorOutdated {
    if (-not (Test-Path $LocalP2Dir)) { return $true }
    if (-not (Test-Path $MirrorStamp)) { return $true }
    if (-not (Test-Path $MirrorInputStateFile)) { return $true }

    $previousInputState = Get-Content -Raw -Path $MirrorInputStateFile
    return $previousInputState -ne (Get-MirrorInputState)
}

<#
.SYNOPSIS
    Returns a text fingerprint of everything that determines the mirror contents.
.DESCRIPTION
    One line for the Eclipse repository override (MAZEGAME_ECLIPSE_REPOSITORY_BASE)
    plus one SHA-256 line per file in $MirrorInputs. Saved next to the mirror and
    compared by Test-MirrorOutdated.
#>
function Get-MirrorInputState {
    $repositoryBaseState = if ($EclipseRepositoryBase) { $EclipseRepositoryBase } else { '<default>' }
    $entries = @(
        "eclipse.repository.base=$repositoryBaseState"
        foreach ($path in $MirrorInputs) {
            if (Test-Path $path) {
                "$path=$((Get-FileHash -Algorithm SHA256 -Path $path).Hash)"
            } else {
                "$path=<missing>"
            }
        }
    )
    return $entries -join "`n"
}

<#
.SYNOPSIS
    Returns the installable unit ids that releng/mirror/pom.xml asks the mirror to contain.
.DESCRIPTION
    The mirror pom is the single list of required units; checks derive from it instead of
    keeping their own copy that can drift.
.PARAMETER MirrorPomPath
    Path to the mirror pom (normally releng/mirror/pom.xml).
#>
function Get-MirrorRequiredUnitIds([string]$MirrorPomPath) {
    [xml]$pom = Get-Content -Raw -Path $MirrorPomPath
    # local-name() matches with or without the Maven POM namespace declaration.
    return @($pom.SelectNodes("//*[local-name()='iu']/*[local-name()='id']") | ForEach-Object { $_.InnerText.Trim() })
}

<#
.SYNOPSIS
    Returns the installable unit ids listed in a p2 repository's content.jar.
.PARAMETER MirrorDir
    The p2 repository folder (normally releng/local-p2).
#>
function Get-MirrorAvailableUnitIds([string]$MirrorDir) {
    $tmp = Join-Path ([System.IO.Path]::GetTempPath()) ("p2units_{0}" -f [guid]::NewGuid())
    try {
        Expand-Archive -Path (Join-Path $MirrorDir 'content.jar') -DestinationPath $tmp
        [xml]$content = Get-Content -Raw -Path (Join-Path $tmp 'content.xml')
        return @($content.SelectNodes('//units/unit') | ForEach-Object { $_.GetAttribute('id') })
    }
    finally {
        Remove-Item -Recurse -Force -Path $tmp -ErrorAction SilentlyContinue
    }
}

<#
.SYNOPSIS
    Checks each required unit against the mirror metadata and its artifact file.
.DESCRIPTION
    Returns one object per required id with Id, InMetadata and HasArtifact. Feature groups
    ("x.feature.group") are expected as features/x_*.jar, bundles as plugins/<id>_*.jar.
.PARAMETER MirrorDir
    The p2 repository folder.
.PARAMETER RequiredIds
    Unit ids that must be present (see Get-MirrorRequiredUnitIds).
.PARAMETER AvailableUnitIds
    Unit ids found in the mirror metadata (see Get-MirrorAvailableUnitIds).
#>
function Test-MirrorUnits([string]$MirrorDir, [string[]]$RequiredIds, [string[]]$AvailableUnitIds) {
    $available = [System.Collections.Generic.HashSet[string]]::new([string[]]@($AvailableUnitIds))
    foreach ($id in $RequiredIds) {
        $artifactPattern = if ($id -like '*.feature.group') {
            Join-Path (Join-Path $MirrorDir 'features') ("{0}_*.jar" -f ($id -replace '\.feature\.group$', ''))
        } else {
            Join-Path (Join-Path $MirrorDir 'plugins') ("{0}_*.jar" -f $id)
        }
        [pscustomobject]@{
            Id          = $id
            InMetadata  = $available.Contains($id)
            HasArtifact = [bool](Get-ChildItem -Path $artifactPattern -ErrorAction SilentlyContinue)
        }
    }
}

<#
.SYNOPSIS
    Builds releng/local-p2 from releng/mirror/pom.xml when needed.
.DESCRIPTION
    Skips the build when Test-MirrorOutdated says the mirror is current. Otherwise
    deletes any existing (possibly partial) mirror, runs the mirror build and writes
    the input state and completion stamp. Throws if Maven fails.
.PARAMETER Force
    Rebuild even when the mirror is up to date.
#>
function Invoke-Mirror([switch]$Force) {
    $rebuild = $Force -or (Test-MirrorOutdated)

    if (-not $rebuild) {
        Write-Host "=== Mirror is up-to-date, skipping rebuild ==="
        return
    }

    if ($Force) {
        Write-Host "=== Force rebuild of local p2 mirror ==="
    } else {
        Write-Host "=== Rebuilding local p2 mirror (inputs changed or missing) ==="
    }

    $hasMirrorDirectory = Test-Path $LocalP2Dir
    $currentInputState = Get-MirrorInputState

    # Tycho's mirror goal cannot safely append an IU set to a partially written
    # destination. Reuse only completed mirrors; restart interrupted runs cleanly.
    if ($hasMirrorDirectory) {
        Write-Host "Removing $LocalP2Dir"
        Remove-Item -Recurse -Force $LocalP2Dir
    }

    if (-not (Test-Path $LocalP2Dir)) {
        New-Item -ItemType Directory -Path $LocalP2Dir | Out-Null
    }
    Set-Content -NoNewline -Path $MirrorInputStateFile -Value $currentInputState

    $mirrorArgs = @('-f', 'releng/mirror/pom.xml', '-U')
    if ($EclipseRepositoryBase) {
        Write-Host "Using Eclipse repository mirror: $EclipseRepositoryBase"
        $mirrorArgs += "-Declipse.repository.base=$EclipseRepositoryBase"
        # A selected mirror may still advertise the global Eclipse mirror list.
        # Disable that redirect so all artifacts stay on the requested endpoint.
        $mirrorArgs += '-Declipse.p2.mirrors=false'
    }
    $mirrorArgs += 'verify'

    Write-Host "Running: $Mvn $($mirrorArgs -join ' ')"
    & $Mvn @mirrorArgs
    $exit = $LASTEXITCODE

    if ($exit -ne 0) {
        throw "Mirror build failed with exit code $exit."
    }

    New-Item -ItemType File -Path $MirrorStamp -Force | Out-Null
    Write-Host "=== Mirror built and stamp updated: $MirrorStamp ==="
}

function Clear-TychoCache {
    Write-Host "=== Clearing Tycho p2 cache (if present) ==="
    if (Test-Path $TychoCache) {
        Remove-Item -Recurse -Force $TychoCache
        Write-Host "Removed $TychoCache"
    } else {
        Write-Host "No Tycho cache folder found at $TychoCache"
    }
    Write-Host "Tycho cache cleared (if it existed)."
}

<#
.SYNOPSIS
    Runs the full clean verify of the reactor with tests, excluding releng/mirror.
.DESCRIPTION
    Expects Invoke-Mirror to have populated releng/local-p2 first. Throws if Maven fails.
#>
function Invoke-Build {
    # The mirror is managed by Invoke-Mirror; do not execute its Maven module again.
    $reactorWithoutMirror = '!releng/mirror'
    $cmd = "$Mvn -pl $reactorWithoutMirror -U -DskipTests=false clean verify"
    Write-Host "=== Running full build: $cmd ==="
    & $Mvn -pl $reactorWithoutMirror -U -DskipTests=false clean verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Build failed with exit code $exit."
    }
    Write-Host "=== Build finished successfully ==="
}

<#
.SYNOPSIS
    Runs an incremental verify (no clean, no mirror rebuild), excluding releng/mirror.
.PARAMETER SkipTests
    Skip test execution.
#>
function Invoke-QuickBuild {
    param([switch]$SkipTests)

    $skipValue = if ($SkipTests) { 'true' } else { 'false' }
    # Quick builds reuse the existing mirror and exclude its Maven module.
    $reactorWithoutMirror = '!releng/mirror'
    $cmd = "$Mvn -pl $reactorWithoutMirror -DskipTests=$skipValue verify"
    Write-Host "=== Running quick build without mirror rebuild: $cmd ==="
    & $Mvn -pl $reactorWithoutMirror "-DskipTests=$skipValue" verify
    $exit = $LASTEXITCODE
    if ($exit -ne 0) {
        throw "Quick build failed with exit code $exit."
    }
    Write-Host "=== Quick build finished successfully ==="
}

switch ($Target) {
    'help' {
        Show-Usage
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
        Ensure-RuntimeModulesCompiled
        Ensure-LaunchBytecodeLevel
        Ensure-LaunchJavaFxLibs
    }

    'mirror' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Invoke-Mirror
    }

    'force-mirror' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Invoke-Mirror -Force
    }

    'clear-cache' {
        Clear-TychoCache
    }

    'build' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }

    'build-with-cache' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Invoke-Build
    }

    'quick' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-QuickBuild
    }

    'quick-no-tests' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-QuickBuild -SkipTests
    }

    'all' {
        Use-Java21OrFail
        Show-ToolchainInfo
        Assert-JavaVersion
        Invoke-Mirror           # only rebuild if needed
        Clear-TychoCache
        Invoke-Build
    }
}
