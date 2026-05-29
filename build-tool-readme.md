# Build Tool Readme

This guide collects the fastest build paths in one place. MazeGame ships
two backend toolchains; each has its own make wrapper.

## Goal

Use the quickest command for the task. Avoid mirror rebuilds when you only
need local compile and test feedback.

## Toolchain layout

| Backend | PowerShell script | GNU make wrapper | Build diagnostics |
| --- | --- | --- | --- |
| JavaFX (full game + Tycho Eclipse plugins) | [make-javafx.ps1](make-javafx.ps1) | [make-javafx](make-javafx) | [Run-P2AndBuildCheck-javafx.ps1](Run-P2AndBuildCheck-javafx.ps1) |
| libGDX (LWJGL3 launcher + headless gameplay core) | [make-libgdx.ps1](make-libgdx.ps1) | [make-libgdx](make-libgdx) | [Run-P2AndBuildCheck-libgdx.ps1](Run-P2AndBuildCheck-libgdx.ps1) |

The libGDX chain is plain Maven (no Tycho, no p2 mirror) and only builds
`maze-common-frontend` and `maze-libgdx`. The JavaFX chain drives the full
reactor including the Eclipse-plugin and Xtext modules.

## JavaFX quick command matrix

```powershell
# Show all targets
.\make-javafx.ps1 help

# Fast path, tests enabled, no mirror rebuild step
.\make-javafx.ps1 quick

# Fastest path, tests skipped, no mirror rebuild step
.\make-javafx.ps1 quick-no-tests

# Full path, mirror check, cache clear, clean verify with tests
.\make-javafx.ps1 all
```

### Run P2 and build diagnostics (JavaFX)

```powershell
# Full run
.\Run-P2AndBuildCheck-javafx.ps1

# Skip mirror rebuild
.\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror

# Skip mirror rebuild and run fast build with tests
.\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror -BuildMode fast

# Skip mirror rebuild and run fastest build with tests skipped
.\Run-P2AndBuildCheck-javafx.ps1 -SkipMirror -BuildMode fastest

# Equivalent mirror skip syntax
.\Run-P2AndBuildCheck-javafx.ps1 -StartAt 2
```

### make-javafx wrapper

```powershell
.\make-javafx help
.\make-javafx quick
.\make-javafx quick-no-tests
.\make-javafx all
```

## libGDX quick command matrix

```powershell
# Show all targets
.\make-libgdx.ps1 help

# Full clean verify of maze-common-frontend + maze-libgdx
.\make-libgdx.ps1 build

# Fast path, tests enabled
.\make-libgdx.ps1 quick

# Fastest path, tests skipped
.\make-libgdx.ps1 quick-no-tests

# Package + run the libGDX launcher
.\make-libgdx.ps1 run
```

### Run P2 and build diagnostics (libGDX)

```powershell
# Full build with logs in releng\test-results-libgdx\
.\Run-P2AndBuildCheck-libgdx.ps1

# Fast build with tests
.\Run-P2AndBuildCheck-libgdx.ps1 -BuildMode fast

# Fastest build, tests skipped
.\Run-P2AndBuildCheck-libgdx.ps1 -BuildMode fastest
```

### make-libgdx wrapper

```powershell
.\make-libgdx help
.\make-libgdx build
.\make-libgdx quick
.\make-libgdx run
```

## Which command should I use

1. Daily JavaFX work: `.\make-javafx.ps1 quick`.
2. Fast compile check before a small commit: `.\make-javafx.ps1 quick-no-tests`.
3. CI style confidence before pushing: `.\make-javafx.ps1 all`.
4. Deep JavaFX diagnostics with step logs: `.\Run-P2AndBuildCheck-javafx.ps1`.
5. Iterating on the libGDX gameplay subset: `.\make-libgdx.ps1 quick` or `.\make-libgdx.ps1 run`.
6. CI smoke test for the libGDX backend: `.\Run-P2AndBuildCheck-libgdx.ps1 -BuildMode fast`.

## Notes

1. Java 21 is required for all reactor workflows.
2. Fast paths use `verify` instead of `clean verify` for speed.
3. Mirror rebuild (JavaFX chain only) is expensive. Use it when target
   platform inputs changed, or when dependencies look stale.
4. The libGDX scripts do not touch Tycho or the p2 mirror; they only need
   Maven Central reachability for the libGDX 1.12.1 dependencies.
