#Requires -Modules @{ ModuleName = 'Pester'; ModuleVersion = '5.0' }
# Pester tests for helper functions in make-javafx.ps1.
# Run from the repository root: Invoke-Pester ./tools/tests

BeforeAll {
    $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '../..')
    $originalLocation = Get-Location
    # Same pattern as make-libgdx.ps1: dot-source with the side-effect-free 'help' target
    # to load the functions without running a build.
    . (Join-Path $repoRoot 'make-javafx.ps1') -Target help *> $null
    Set-Location $originalLocation
    $sep = [IO.Path]::PathSeparator
}

Describe 'Test-LaunchClassesStale' {
    # Every test builds its own fixtures, so each one passes or fails on its own
    # (also when run alone) and checks the stale reason it is named after.
    BeforeEach {
        $root = Join-Path $TestDrive ([guid]::NewGuid().ToString('N'))
        $launcher = Join-Path $root 'Launcher.class'
        $moduleA = Join-Path $root 'moduleA'
        $moduleB = Join-Path $root 'moduleB'
        foreach ($m in @($moduleA, $moduleB)) {
            New-Item -ItemType Directory -Force -Path (Join-Path $m 'src/main/java') | Out-Null
        }
        # a launcher class newer than the sources written below
        $srcA = Join-Path $moduleA 'src/main/java/A.java'
        Set-Content -Path $srcA -Value 'class A {}'
        (Get-Item $srcA).LastWriteTimeUtc = [DateTime]::UtcNow.AddMinutes(-10)
        Set-Content -Path $launcher -Value 'x'
        (Get-Item $launcher).LastWriteTimeUtc = [DateTime]::UtcNow.AddMinutes(-5)
    }

    It 'is stale when the launcher class is missing' {
        $result = Test-LaunchClassesStale (Join-Path $root 'missing.class') @($moduleA)
        $result.Stale | Should -BeTrue
        $result.Reason | Should -Match 'missing'
    }

    It 'is fresh when every module source is older than the launcher class' {
        (Test-LaunchClassesStale $launcher @($moduleA, $moduleB)).Stale | Should -BeFalse
    }

    It 'is stale when a dependency module (not the app module) has a newer source' {
        $src = Join-Path $moduleB 'src/main/java/B.java'
        Set-Content -Path $src -Value 'class B {}'
        (Get-Item $src).LastWriteTimeUtc = [DateTime]::UtcNow
        $result = Test-LaunchClassesStale $launcher @($moduleA, $moduleB)
        $result.Stale | Should -BeTrue
        $result.Reason | Should -Match 'moduleB'
    }

    It 'counts new sprites and sounds as sources' {
        $res = Join-Path $moduleB 'src/main/resources'
        New-Item -ItemType Directory -Force -Path $res | Out-Null
        $png = Join-Path $res 'sprite.png'
        Set-Content -Path $png -Value 'png'
        (Get-Item $png).LastWriteTimeUtc = [DateTime]::UtcNow
        $result = Test-LaunchClassesStale $launcher @($moduleB)
        $result.Stale | Should -BeTrue
        $result.Reason | Should -Match 'moduleB'
    }
}

Describe 'Get-PathWithEntryFirst' {
    It 'prepends the entry when it is not on PATH' {
        $result = Get-PathWithEntryFirst -PathValue "a${sep}b" -Entry 'jdk21'
        $result | Should -Be "jdk21${sep}a${sep}b"
    }

    # Regression: install.ps1 appends JAVA_HOME/bin to the end of PATH before calling
    # make-javafx.ps1. Use-Java21OrFail used to leave it there, so an earlier Java
    # (e.g. JDK 25) stayed active and write-launch-env failed with "active Java is 25".
    It 'moves the entry to the front when it is already at the end of PATH' {
        $result = Get-PathWithEntryFirst -PathValue "jdk25${sep}other${sep}jdk21" -Entry 'jdk21'
        $result | Should -Be "jdk21${sep}jdk25${sep}other"
    }

    It 'removes duplicate occurrences of the entry' {
        $result = Get-PathWithEntryFirst -PathValue "jdk21${sep}a${sep}jdk21" -Entry 'jdk21'
        $result | Should -Be "jdk21${sep}a"
    }

    It 'returns only the entry for an empty PATH' {
        Get-PathWithEntryFirst -PathValue '' -Entry 'jdk21' | Should -Be 'jdk21'
    }
}

Describe 'Get-MirrorRequiredUnitIds' {
    It 'reads the iu/id entries from a Maven pom with the POM namespace' {
        $pom = Join-Path $TestDrive 'pom.xml'
        Set-Content -Path $pom -Value @'
<project xmlns="http://maven.apache.org/POM/4.0.0">
  <build><plugins><plugin><configuration><ius>
    <iu><id>org.example.bundle</id></iu>
    <iu><id> org.example.sdk.feature.group </id></iu>
  </ius></configuration></plugin></plugins></build>
</project>
'@
        Get-MirrorRequiredUnitIds -MirrorPomPath $pom | Should -Be @('org.example.bundle', 'org.example.sdk.feature.group')
    }

    It 'also reads a pom without a namespace declaration' {
        $pom = Join-Path $TestDrive 'plain-pom.xml'
        Set-Content -Path $pom -Value '<project><ius><iu><id>org.example.plain</id></iu></ius></project>'
        Get-MirrorRequiredUnitIds -MirrorPomPath $pom | Should -Be @('org.example.plain')
    }

    # Regression: step 2 of Run-P2AndBuildCheck-javafx.ps1 used a hand-written list that
    # drifted from releng/mirror/pom.xml (it expected emf.sdk / emf.codegen / xtext.sdk
    # feature groups the mirror never contained) and always reported a false FAIL.
    It 'uses the real mirror pom as the source of truth' {
        $ids = Get-MirrorRequiredUnitIds -MirrorPomPath (Join-Path $repoRoot 'releng/mirror/pom.xml')
        $ids | Should -Contain 'org.eclipse.xtext'
        $ids | Should -Contain 'org.eclipse.ocl.all.sdk.feature.group'
        $ids | Should -Not -Contain 'org.eclipse.emf.sdk.feature.group'
        $ids | Should -Not -Contain 'org.eclipse.xtext.sdk.feature.group'
    }
}

Describe 'Get-MirrorAvailableUnitIds' {
    It 'returns the unit ids listed in content.jar' {
        $mirror = Join-Path $TestDrive 'p2'
        New-Item -ItemType Directory -Path $mirror | Out-Null
        $xml = Join-Path $TestDrive 'content.xml'
        Set-Content -Path $xml -Value "<repository><units size='2'><unit id='a.bundle' version='1.0'/><unit id='b.feature.group' version='2.0'/></units></repository>"
        Compress-Archive -Path $xml -DestinationPath (Join-Path $TestDrive 'content.zip')
        Move-Item (Join-Path $TestDrive 'content.zip') (Join-Path $mirror 'content.jar')

        Get-MirrorAvailableUnitIds -MirrorDir $mirror | Should -Be @('a.bundle', 'b.feature.group')
    }
}

Describe 'Test-MirrorUnits' {
    BeforeAll {
        $mirror = Join-Path $TestDrive 'mirror'
        New-Item -ItemType Directory -Path (Join-Path $mirror 'plugins'), (Join-Path $mirror 'features') | Out-Null
        New-Item -ItemType File -Path (Join-Path $mirror 'plugins/org.example.bundle_1.0.0.jar') | Out-Null
        New-Item -ItemType File -Path (Join-Path $mirror 'features/org.example.sdk_2.0.0.jar') | Out-Null
    }

    It 'reports nothing missing when metadata and artifacts are present' {
        $result = Test-MirrorUnits -MirrorDir $mirror `
            -RequiredIds @('org.example.bundle', 'org.example.sdk.feature.group') `
            -AvailableUnitIds @('org.example.bundle', 'org.example.sdk.feature.group')
        @($result | Where-Object { -not $_.InMetadata -or -not $_.HasArtifact }) | Should -HaveCount 0
    }

    It 'looks for feature groups under features/ without the .feature.group suffix' {
        $result = Test-MirrorUnits -MirrorDir $mirror -RequiredIds @('org.example.sdk.feature.group') -AvailableUnitIds @('org.example.sdk.feature.group')
        $result.HasArtifact | Should -BeTrue
    }

    It 'flags units missing from the metadata or without an artifact' {
        $result = Test-MirrorUnits -MirrorDir $mirror -RequiredIds @('org.example.bundle', 'org.example.missing') -AvailableUnitIds @('org.example.bundle')
        $missing = $result | Where-Object Id -eq 'org.example.missing'
        $missing.InMetadata | Should -BeFalse
        $missing.HasArtifact | Should -BeFalse
    }
}

Describe 'Test-MavenNetworkFailure' {
    It 'sees an unreachable repository in an error line' {
        Test-MavenNetworkFailure @('[ERROR] Failed to execute goal: Could not transfer artifact x from/to eclipse-releases: status code: 503, reason phrase: Service Temporarily Unavailable (503)') |
            Should -BeTrue
    }

    It 'ignores the same message logged as a warning' {
        Test-MavenNetworkFailure @('[WARNING] Could not transfer metadata org.osgi:x/maven-metadata.xml from/to eclipse-releases: status code: 503') |
            Should -BeFalse
    }

    It 'does not take a compile error for a network failure' {
        Test-MavenNetworkFailure @('[ERROR] /src/A.java:[3,1] cannot find symbol') | Should -BeFalse
    }
}

Describe 'Invoke-Maven' {
    # A fake mvn that logs its arguments; it fails with $env:FAKE_MVN_FAILURE while it is online.
    BeforeEach {
        $fakeLog = Join-Path $TestDrive "calls-$([guid]::NewGuid().ToString('N')).txt"
        $env:FAKE_MVN_LOG = $fakeLog
        $Mvn = Join-Path $TestDrive 'fake-mvn.ps1'
        Set-Content -Path $Mvn -Value @'
Add-Content -Path $env:FAKE_MVN_LOG -Value ($args -join ' ')
if ($args -notcontains '-o' -and $env:FAKE_MVN_FAILURE) { Write-Output $env:FAKE_MVN_FAILURE; exit 1 }
exit 0
'@
    }
    AfterEach {
        Remove-Item Env:FAKE_MVN_LOG, Env:FAKE_MVN_FAILURE -ErrorAction SilentlyContinue
    }

    It 'runs Maven once when it succeeds' {
        Invoke-Maven -pl a -U install *> $null
        $LASTEXITCODE | Should -Be 0
        Get-Content $fakeLog | Should -Be @('-pl a -U install')
    }

    It 'retries offline without -U when a repository is down' {
        $env:FAKE_MVN_FAILURE = '[ERROR] Could not transfer artifact x from/to eclipse-releases: status code: 503'
        Invoke-Maven -pl a -U install *> $null
        $LASTEXITCODE | Should -Be 0
        Get-Content $fakeLog | Should -Be @('-pl a -U install', '-o -pl a install')
    }

    It 'does not retry other failures' {
        $env:FAKE_MVN_FAILURE = '[ERROR] There are test failures.'
        Invoke-Maven -pl a install *> $null
        $LASTEXITCODE | Should -Be 1
        @(Get-Content $fakeLog) | Should -HaveCount 1
    }
}

Describe 'Save-PreviousMirror and Restore-PreviousMirror' {
    BeforeEach {
        $root = Join-Path $TestDrive ([guid]::NewGuid().ToString('N'))
        $LocalP2Dir = Join-Path $root 'local-p2'
        $MirrorStamp = Join-Path $LocalP2Dir '.mirror.stamp'
        $MirrorInputStateFile = Join-Path $LocalP2Dir '.mirror.inputs'
        $PreviousMirrorDir = "$LocalP2Dir.previous"
        New-Item -ItemType Directory -Force -Path (Join-Path $LocalP2Dir 'plugins') | Out-Null
        Set-Content -Path (Join-Path $LocalP2Dir 'plugins/a.jar') -Value 'jar'
        Set-Content -Path $MirrorInputStateFile -Value 'inputs'
    }

    It 'puts a completed mirror back after a failed rebuild, to be refreshed next time' {
        New-Item -ItemType File -Path $MirrorStamp | Out-Null
        $saved = Save-PreviousMirror 6> $null
        Test-Path $LocalP2Dir | Should -BeFalse
        New-Item -ItemType Directory -Path $LocalP2Dir | Out-Null    # what the failed download left
        Restore-PreviousMirror $saved | Should -BeTrue
        Test-Path (Join-Path $LocalP2Dir 'plugins/a.jar') | Should -BeTrue
        Test-Path $MirrorInputStateFile | Should -BeFalse
        Test-Path $PreviousMirrorDir | Should -BeFalse
    }

    It 'removes an interrupted mirror and has nothing to put back' {
        $saved = Save-PreviousMirror 6> $null
        $saved | Should -BeNullOrEmpty
        Test-Path $LocalP2Dir | Should -BeFalse
        Restore-PreviousMirror $saved | Should -BeFalse
    }
}
