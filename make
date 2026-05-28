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
#   make                    -> toolchain info, refresh mirror if needed, clear Tycho cache, full build
#
# Fast paths:
#   make help               -> show all targets including no-mirror options
#   make quick              -> fastest safe path, skip mirror rebuild, keep cache, run tests
#   make quick-no-tests     -> absolute fastest path, skip mirror rebuild, keep cache, skip tests
# ─────────────────────────────────────────────────────────

# PowerShell host (override with: make PSH=powershell)
PSH ?= pwsh

.PHONY: all default help toolchain-info mirror force-mirror clear-tycho-cache build quick quick-no-tests clean-mirror

default: all
all: toolchain-info mirror clear-tycho-cache build

help:
	@echo === MazeGame build targets ===
	@echo make all                : Full path. Mirror check or rebuild, clear Tycho cache, full build with tests.
	@echo make build              : Build only. Uses mvn -U -DskipTests=false clean verify.
	@echo make quick              : Fast path. No mirror target, no cache clear, tests on. Uses mvn -DskipTests=false verify.
	@echo make quick-no-tests     : Fastest path. No mirror target, no cache clear, tests off. Uses mvn -DskipTests=true verify.
	@echo make mirror             : Build or refresh local mirror if needed.
	@echo make force-mirror       : Always rebuild local mirror.
	@echo make clean-mirror       : Remove local mirror directory and stamp.
	@echo
	@echo To avoid mirror rebuilds, use make quick or make quick-no-tests.
	@echo =================================

# ─────────────────────────────────────────────────────────
# Step 0 – Toolchain info
# ─────────────────────────────────────────────────────────

toolchain-info:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target toolchain

# ─────────────────────────────────────────────────────────
# Step 1 – Local p2 mirror
# ─────────────────────────────────────────────────────────

mirror:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target mirror

force-mirror:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target force-mirror

clean-mirror:
	@$(PSH) -NoLogo -NoProfile -Command "if (Test-Path 'releng/local-p2') { Remove-Item -Recurse -Force 'releng/local-p2' }; if (Test-Path 'releng/local-p2/.mirror.stamp') { Remove-Item -Force 'releng/local-p2/.mirror.stamp' }; Write-Host 'Done.'"

# ─────────────────────────────────────────────────────────
# Step 3 – Clear Tycho p2 cache
# ─────────────────────────────────────────────────────────

clear-tycho-cache:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target clear-cache

# ─────────────────────────────────────────────────────────
# Step 4 – Full build (Tycho + app)
# ─────────────────────────────────────────────────────────

build:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target build

quick:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target quick

quick-no-tests:
	@$(PSH) -NoLogo -NoProfile -File ./make.ps1 -Target quick-no-tests
