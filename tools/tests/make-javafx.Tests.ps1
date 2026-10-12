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
