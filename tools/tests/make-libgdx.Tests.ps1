#Requires -Modules @{ ModuleName = 'Pester'; ModuleVersion = '5.0' }

BeforeAll {
    $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '../..')
    $originalLocation = Get-Location
    . (Join-Path $repoRoot 'make-libgdx.ps1') -Target help *> $null
    Set-Location $originalLocation
}

Describe 'libGDX mirror preparation' {
    BeforeEach {
        Mock Invoke-Mirror { throw 'mirror-called' }
        Mock Ensure-GeneratedSources {}
        Mock Test-Path { $true }
        Mock Get-ChildItem { [pscustomobject]@{ Name = 'gdx-test.jar' } }
    }

    It 'launches current artifacts without checking the mirror' {
        Mock Test-LaunchClassesStale { @{ Stale = $false } }
        { Ensure-LibgdxLaunchClasses } | Should -Not -Throw
        Should -Invoke Invoke-Mirror -Times 0 -Exactly
    }

    It 'checks the mirror before building stale launch artifacts' {
        Mock Test-LaunchClassesStale { @{ Stale = $true; Reason = 'changed' } }
        { Ensure-LibgdxLaunchClasses } | Should -Throw '*mirror-called*'
        Should -Invoke Invoke-Mirror -Times 1 -Exactly
    }

    It 'checks the mirror before a full build' {
        { Invoke-LibgdxFullBuild } | Should -Throw '*mirror-called*'
    }

    It 'checks the mirror before a quick build' {
        { Invoke-LibgdxQuickBuild } | Should -Throw '*mirror-called*'
    }

    It 'checks the mirror before regenerating Xtext sources' {
        Mock Test-XtextGeneratedSourcesPresent { $false }
        { Ensure-XtextGeneratedSources } | Should -Throw '*mirror-called*'
    }
}
