# Releng

## Overview

This folder contains the build infrastructure for the Eclipse plug in modules and the Tycho based Maven build. It defines

* a shared target platform used by all Eclipse plug in modules
* a local p2 mirror that can be used for offline or stable builds
* log files with the result of automated mirror and build checks

The configuration is consumed by the root `pom.xml` and by the helper PowerShell script `Run-P2AndBuildCheck.ps1` in the repository root.

## Layout

* `releng/mirror/pom.xml`
  Tycho p2 extras configuration that builds the local p2 mirror into `releng/local-p2`.

* `releng/local-p2/`
  Generated local p2 repository created by the mirror. Contains `artifacts.jar`, `content.jar`, platform specific executables under `binary/`, and the mirrored plug ins and features.

* `releng/maze.target`
  Target platform definition used by Tycho through the `target-platform-configuration` plugin in the root `pom.xml`. It currently points at the public Eclipse release and Orbit repositories and lists the required units (EMF, OCL, Equinox executable and related dependencies).

* `releng/test-results/`
  Log files written by the helper script `Run-P2AndBuildCheck.ps1` when you run full mirror and build checks.

## Local p2 mirror

The local p2 mirror is built from the public Eclipse in this source code repositories using the Tycho p2 extras plugin configured in `releng/mirror/pom.xml`. It mirrors the units that are also referenced from `releng/maze.target`, such as

* core runtime and Equinox bundles
* EMF runtime and code generation
* OCL runtime and SDK
* FreeMarker for code generation (via embedded JAR)
* Equinox executable feature group

The mirror is written to `releng/local-p2` and can be used for offline builds, for diagnostics, and for verifying that all required units are available in a single place.

### Refresh the local p2 mirror

From the repository root you can rebuild the mirror with Maven:

```bash
mvn -f releng/mirror/pom.xml -U verify
```

This creates or updates `releng/local-p2` based on the IU list in `releng/mirror/pom.xml`.

### Clean the mirror and rebuild

If you want to start from a clean local mirror, remove the directory and run the mirror build again.

On Unix like systems:

```bash
rm -rf releng/local-p2
mvn -f releng/mirror/pom.xml -U verify
```

On Windows PowerShell:

```powershell
Remove-Item -Recurse -Force releng\local-p2
mvn -f releng/mirror/pom.xml -U verify
```

## Target platform and Tycho

The Tycho build uses the target file `releng/maze.target` through the `target-platform-configuration` plugin in the root `pom.xml`:

* Tycho is configured once in the root project
* All Eclipse plug in modules inherit this configuration
* There is no need to configure the target again in individual module `pom.xml` files

The target file currently points at the public Eclipse release and Orbit repositories. The local p2 mirror reproduces the same units and can be used for offline builds or for debugging dependency problems, even though Tycho itself is wired directly against `releng/maze.target`.

You can also open `releng/maze.target` inside Eclipse to use the same platform for PDE development:

1. In Eclipse, use "File → Open File…" and select `releng/maze.target`, or
2. Import it as a target definition and activate it in the Target Platform preferences.

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](../docs/technology-laymans-guide.md) | Simple explanation of the build technologies in everyday terms |
| [Eclipse Modules](../eclipse.modules.md) | Eclipse plugin architecture and build worlds |
| [Main README](../readme.md) | Project overview and module index |
| [Feature Module](../maze-feature/readme.md) | Eclipse feature packaging |
| [Repository Module](../maze-module-repository/readme.md) | P2 update site |

NB! This setup is not thoroughly tested, as we used Visual Studio Code a lot.

## Helper script `Run-P2AndBuildCheck.ps1` (Windows)

The repository root contains a helper script that drives the releng setup end to end:

* Script path in repo root
  `Run-P2AndBuildCheck.ps1`

I will clear all cache and set up the repos correctly if all setps are run. Usually this is necessary as a first time run.

### What the script does

By default the script

1. Rebuilds the local p2 mirror

   * Deletes `releng\local-p2`
   * Recreates the folder
   * Runs `mvn -f releng/mirror/pom.xml -U verify`

2. Verifies required bundles and features in `releng\local-p2`

   * Checks for expected plug in and feature jars
   * Unpacks `content.jar` and verifies that all required IU ids are present

3. Clears the Tycho p2 cache

   * Deletes `%USERPROFILE%\.m2\repository\.cache\tycho` so the next build resolves everything from scratch

4. Runs a full Maven build of the whole reactor

   * Executes `mvn -U -DskipTests=false clean verify -e -X`
   * Writes a detailed log to `releng\test-results\p2-and-build-check_YYYYMMDD_HHMMSS.log`

### Running the script
Make sure you have Powershell version 7.x installed.
Run: `$PSVersionTable`

From the repository root on Windows PowerShell:

```powershell
.\Run-P2AndBuildCheck.ps1
```

You can choose to start at a later step with the `StartAt` parameter:

```powershell
.\Run-P2AndBuildCheck.ps1 -StartAt 2   # Skip rebuilding the mirror
.\Run-P2AndBuildCheck.ps1 -StartAt 3   # Skip mirror and mirror verification
.\Run-P2AndBuildCheck.ps1 -StartAt 4   # Only run the full Maven build
```

The optional `LogDirectory` parameter controls where the log files are written. By default it uses `releng\test-results`.

## Version control notes

* Commit these files:

  * `releng/mirror/pom.xml`
  * `releng/maze.target`
  * the script `Run-P2AndBuildCheck.ps1` (in the repository root)

* Do not commit the generated mirror content under `releng/local-p2/`. If new files appear there after running the mirror, keep them untracked in Git. Git should ignore this path, as it is in the .gitignore.

## Helper commands `make.ps1` and `make`

For day to day work on Windows you can use the lightweight helpers in the repository root instead of calling Maven and the releng modules manually.

### `make.ps1` PowerShell wrapper

`make.ps1` is a PowerShell script that drives the same four steps described above:

```powershell
.\make.ps1                       # same as -Target all
.\make.ps1 -Target mirror        # refresh local p2 mirror if needed
.\make.ps1 -Target force-mirror  # always rebuild mirror
.\make.ps1 -Target clear-cache   # clear Tycho p2 cache
.\make.ps1 -Target build         # full Maven build (clears Tycho cache first)
.\make.ps1 -Target build-with-cache  # full Maven build (keeps Tycho cache)
.\make.ps1 -Target toolchain     # show Maven and Java versions
```

Behaviour:

* Always changes the current directory to the script location, so you can run it from anywhere.
* Validates that Java 21 is active before running builds (required for Xtext/MWE2 generation).
* For `mirror` it checks a stamp file in `releng\local-p2\.mirror.stamp` and only rebuilds the mirror if the inputs (currently `releng\mirror\pom.xml`) are newer or the mirror is missing.
* `force-mirror` always deletes `releng\local-p2` and reruns `mvn -f releng/mirror/pom.xml -U verify`, then updates the stamp file.
* `clear-cache` deletes the Tycho cache at `%USERPROFILE%\.m2\repository\.cache\tycho` if it exists.
* `build` clears the Tycho cache first, then runs `mvn -U -DskipTests=false clean verify` and fails the script if Maven returns a non zero exit code.
* `build-with-cache` runs `mvn -U -DskipTests=false clean verify` **without** clearing the Tycho cache, which is faster for incremental builds.
* `all` combines `toolchain`, `mirror` (with change detection), `clear-cache` and `build`.

The script uses `mvn` by default. If you prefer the Maven wrapper, edit `$Mvn = 'mvn'` in `make.ps1` and change it to `mvnw`.

### `make` Makefile front end

`make` is a small Makefile that offers the same workflow for users who have `make` installed on Windows:

```bash
make                   # same as make all
make toolchain-info    # show Maven and Java versions
make mirror            # refresh local p2 mirror if needed
make force-mirror      # always rebuild mirror
make clear-tycho-cache # clear Tycho p2 cache
make build             # full Maven build
```

Key points:

* `make` uses the `MVN` variable for the Maven command, which defaults to `mvn`. You can override it per call:

  ```bash
  make MVN=mvnw all
  ```

* `mirror` and `force-mirror` call the same Tycho mirror build as `make.ps1` and maintain the same stamp file in `releng\local-p2\.mirror.stamp`.

* `clear-tycho-cache` removes the Tycho cache folder under `%USERPROFILE%\.m2\repository\.cache\tycho` if it exists.

* The default target `all` runs `toolchain-info`, `mirror`, `clear-tycho-cache` and `build` in that order.

