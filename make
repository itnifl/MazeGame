# ─────────────────────────────────────────────────────────
# MazeGame Makefile (Windows-friendly, uses PowerShell)
#
# Modules built:
#   - main.game.maze.behaviour, difficulties, opponents, walls, mazeworld
#   - main.game.maze.dsl, dsl.ide, dsl.ui, dsl.tests (Xtext DSL)
#   - maze-generator.acceleo, maze-generator.acceleo-runner
#   - maze-module-generator, maze-feature, maze-module-repository
#   - maze (JavaFX game client)
#
# Default:
#   make          → toolchain info, refresh mirror if needed, clear Tycho cache, full build
# ─────────────────────────────────────────────────────────

# Tools (override with: make MVN=mvnw)
MVN ?= mvn

# Paths
LOCAL_P2_DIR      := releng\local-p2
MIRROR_STAMP      := $(LOCAL_P2_DIR)\.mirror.stamp
TYCHO_CACHE_WIN   := $(USERPROFILE)\.m2\repository\.cache\tycho

# Prefer JDK 21 if installed (Windows)
JAVA21_SELECT = $$javaHomeCandidates = @(); \
	if ($$env:JAVA_HOME) { $$javaHomeCandidates += $$env:JAVA_HOME }; \
	$$javaHomeCandidates += @('C:\Program Files\Java\jdk-21','C:\Program Files\Eclipse Adoptium\jdk-21*','C:\Program Files\Microsoft\jdk-21*'); \
	$$resolvedHomes = @(); \
	foreach ($$candidate in $$javaHomeCandidates) { \
		if ($$candidate.Contains('*')) { $$resolvedHomes += (Get-ChildItem -Path $$candidate -Directory -ErrorAction SilentlyContinue | Sort-Object Name -Descending | Select-Object -ExpandProperty FullName) } \
		elseif (Test-Path $$candidate) { $$resolvedHomes += $$candidate } \
	}; \
	$$selected = $$null; \
	foreach ($$javaHomePath in ($$resolvedHomes | Select-Object -Unique)) { \
		$$javaExe = Join-Path $$javaHomePath 'bin\\java.exe'; \
		if (Test-Path $$javaExe) { \
			$$javaOut = & $$javaExe -version 2>&1 | Out-String; \
			if ($$javaOut -match 'version\s+"?(21)\.?' -or $$javaOut -match 'version\s+"?(21)"') { $$selected = $$javaHomePath; break } \
		} \
	}; \
	if ($$selected) { \
		$$env:JAVA_HOME = $$selected; \
		$$javaBin = Join-Path $$selected 'bin'; \
		if (-not (($$env:Path -split ';') -contains $$javaBin)) { $$env:Path = "$$javaBin;$$env:Path" }; \
		Write-Host "Using Java 21 from $$selected" \
	} else { \
		Write-Warning 'Java 21 not found in common install locations. Using current PATH java.' \
	}

# Files that, when changed, should trigger a mirror rebuild
MIRROR_INPUTS := releng/mirror/pom.xml

.PHONY: all default toolchain-info mirror force-mirror clear-tycho-cache build clean-mirror

default: all
all: toolchain-info mirror clear-tycho-cache build

# ─────────────────────────────────────────────────────────
# Step 0 – Toolchain info
# ─────────────────────────────────────────────────────────

toolchain-info:
	@echo === Toolchain versions ===
	@powershell -NoLogo -NoProfile -Command "$(JAVA21_SELECT); & $(MVN) -version"
	@echo.
	@powershell -NoLogo -NoProfile -Command "$(JAVA21_SELECT); & java -version"
	@echo ==========================

# ─────────────────────────────────────────────────────────
# Step 1 – Local p2 mirror
# ─────────────────────────────────────────────────────────

mirror: $(MIRROR_STAMP)

$(MIRROR_STAMP): $(MIRROR_INPUTS)
	@echo === Rebuilding local p2 mirror ===
	@powershell -NoLogo -NoProfile -Command "if (Test-Path '$(LOCAL_P2_DIR)') { Remove-Item -Recurse -Force '$(LOCAL_P2_DIR)' }"
	@powershell -NoLogo -NoProfile -Command "$(JAVA21_SELECT); & $(MVN) -f releng/mirror/pom.xml -U verify"
	@powershell -NoLogo -NoProfile -Command "if (-not (Test-Path '$(LOCAL_P2_DIR)')) { New-Item -ItemType Directory -Path '$(LOCAL_P2_DIR)' | Out-Null } ; New-Item -ItemType File -Path '$(MIRROR_STAMP)' -Force | Out-Null"
	@echo === Mirror built and stamp updated ===

force-mirror:
	@echo === Force rebuild of local p2 mirror ===
	@powershell -NoLogo -NoProfile -Command "if (Test-Path '$(LOCAL_P2_DIR)') { Remove-Item -Recurse -Force '$(LOCAL_P2_DIR)' }"
	@powershell -NoLogo -NoProfile -Command "$(JAVA21_SELECT); & $(MVN) -f releng/mirror/pom.xml -U verify"
	@powershell -NoLogo -NoProfile -Command "if (-not (Test-Path '$(LOCAL_P2_DIR)')) { New-Item -ItemType Directory -Path '$(LOCAL_P2_DIR)' | Out-Null } ; New-Item -ItemType File -Path '$(MIRROR_STAMP)' -Force | Out-Null"
	@echo === Force mirror rebuild completed ===

clean-mirror:
	@echo === Removing local p2 mirror and stamp ===
	@powershell -NoLogo -NoProfile -Command "if (Test-Path '$(LOCAL_P2_DIR)') { Remove-Item -Recurse -Force '$(LOCAL_P2_DIR)' }"
	@echo Done.

# ─────────────────────────────────────────────────────────
# Step 3 – Clear Tycho p2 cache
# ─────────────────────────────────────────────────────────

clear-tycho-cache:
	@echo === Clearing Tycho p2 cache (if present) ===
	@powershell -NoLogo -NoProfile -Command "if (Test-Path '$(TYCHO_CACHE_WIN)') { Remove-Item -Recurse -Force '$(TYCHO_CACHE_WIN)' }"
	@echo Tycho cache cleared (if it existed).

# ─────────────────────────────────────────────────────────
# Step 4 – Full build (Tycho + app)
# ─────────────────────────────────────────────────────────

build:
	@echo === Running full build: mvn -U -DskipTests=false clean verify ===
	@powershell -NoLogo -NoProfile -Command "$(JAVA21_SELECT); & $(MVN) -U -DskipTests=false clean verify"
	@echo === Build finished ===
