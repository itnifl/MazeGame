# Build Tool Readme

This guide collects the fastest build paths in one place.

## Goal

Use the quickest command for the task.
Avoid mirror rebuilds when you only need local compile and test feedback.

## Quick command matrix

### PowerShell entry point

Use [make.ps1](make.ps1).

```powershell
# Show all targets
.\make.ps1 help

# Fast path, tests enabled, no mirror rebuild step
.\make.ps1 quick

# Fastest path, tests skipped, no mirror rebuild step
.\make.ps1 quick-no-tests

# Full path, mirror check, cache clear, clean verify with tests
.\make.ps1 all
```

### Run P2 and build diagnostics entry point

Use [Run-P2AndBuildCheck.ps1](Run-P2AndBuildCheck.ps1).

```powershell
# Full run
.\Run-P2AndBuildCheck.ps1

# Skip mirror rebuild
.\Run-P2AndBuildCheck.ps1 -SkipMirror

# Skip mirror rebuild and run fast build with tests
.\Run-P2AndBuildCheck.ps1 -SkipMirror -BuildMode fast

# Skip mirror rebuild and run fastest build with tests skipped
.\Run-P2AndBuildCheck.ps1 -SkipMirror -BuildMode fastest

# Equivalent mirror skip syntax
.\Run-P2AndBuildCheck.ps1 -StartAt 2
```

### make script entry point

Use [make](make) from PowerShell.

```powershell
# Show targets
.\make help

# Fast path, tests enabled, no mirror rebuild step
.\make quick

# Fastest path, tests skipped, no mirror rebuild step
.\make quick-no-tests

# Full path
.\make all
```

## Which command should I use

1. Most daily work: run .\make.ps1 quick.
2. Fast compile check before a small commit: run .\make.ps1 quick-no-tests.
3. CI style confidence before pushing: run .\make.ps1 all.
4. Deep diagnostics with step logs: run .\Run-P2AndBuildCheck.ps1.

## Notes

1. Java 21 is required for the full reactor workflows.
2. Fast paths use verify instead of clean verify for speed.
3. Mirror rebuild is expensive. Use it when target platform inputs changed, or when dependencies look stale.
