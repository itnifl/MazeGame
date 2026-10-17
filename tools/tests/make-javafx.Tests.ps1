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
