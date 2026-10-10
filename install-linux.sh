#!/usr/bin/env bash
# Install MazeGame build and runtime prerequisites on Ubuntu 24.04.
#
# Usage:
#   sudo ./install-linux.sh
#
# The installer is idempotent: every run audits every prerequisite and installs
# anything missing or at the wrong version. Exact verified installations are
# reused. Downloads are cached so interrupted transfers resume on the next run.

set -Eeuo pipefail

MAVEN_VERSION="3.9.16"
MAVEN_SHA512="831a8591fe20c8243b1dbe7d71e3244f31d1665b0804b2e825e38cbbe5ce0cafb8338851f90780735568773e0a6cd07bbec107cda0b896b008b861075358b6f6"
POWERSHELL_VERSION="7.6.6"
POWERSHELL_SHA256="ddbc4a2d113bbd46d283cfedcbcd117a70caefd7673f41f2b4e0000badf103bc"
JAVAFX_VERSION="21.0.9"
JAVAFX_SHA256="2eefbf4731b5596cdeb41aa5adac38cf4a278d1b74dc56eb75b6100b9fa86cc8"

# System locations may be overridden without editing this script. Repository
# and user locations are always discovered at runtime.
INSTALL_ROOT="${MAZEGAME_INSTALL_ROOT:-/opt}"
BIN_DIR="${MAZEGAME_BIN_DIR:-/usr/local/bin}"
CACHE_DIR="${MAZEGAME_CACHE_DIR:-/var/cache/mazegame-installer}"
PROFILE_FILE="${MAZEGAME_PROFILE_FILE:-/etc/profile.d/mazegame.sh}"

require_safe_absolute_path() {
  local variable_name="$1"
  local value="$2"

  if [[ -z "$value" || "$value" != /* || "$value" == "/" ]]; then
    echo "$variable_name must be a non-root absolute path; received: $value" >&2
    exit 2
  fi
}

require_safe_absolute_path MAZEGAME_INSTALL_ROOT "$INSTALL_ROOT"
require_safe_absolute_path MAZEGAME_BIN_DIR "$BIN_DIR"
require_safe_absolute_path MAZEGAME_CACHE_DIR "$CACHE_DIR"
require_safe_absolute_path MAZEGAME_PROFILE_FILE "$PROFILE_FILE"

if [[ $# -gt 0 ]]; then
  echo "Usage: sudo $0" >&2
  exit 2
fi

if [[ $EUID -ne 0 ]]; then
  echo "Run this installer with sudo: sudo $0" >&2
  exit 1
fi

if [[ "$(uname -m)" != "x86_64" ]]; then
  echo "This installer currently supports Linux x86_64 only." >&2
  exit 1
fi

if [[ -z "${SUDO_USER:-}" || "$SUDO_USER" == "root" ]]; then
  echo "Run the script through sudo from your normal user account." >&2
  exit 1
fi

TARGET_USER="$SUDO_USER"
TARGET_HOME="$(getent passwd "$TARGET_USER" | cut -d: -f6)"
SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
MAVEN_HOME_LINK="$INSTALL_ROOT/maven"
JAVAFX_HOME_LINK="$INSTALL_ROOT/javafx-sdk"

if [[ -z "$TARGET_HOME" || ! -d "$TARGET_HOME" ]]; then
  echo "Could not determine the invoking user's home directory." >&2
  exit 1
fi

install -d -m 0755 \
  "$INSTALL_ROOT" \
  "$BIN_DIR" \
  "$CACHE_DIR" \
  "$(dirname -- "$PROFILE_FILE")"

step() {
  printf '\n==> %s\n' "$1"
}

download() {
  local url="$1"
  local destination="$2"
  local attempt
  local curl_status

  for attempt in 1 2 3 4 5; do
    curl_status=0
    curl --fail --location \
      --continue-at - \
      --connect-timeout 30 \
      --retry 3 \
      --retry-all-errors \
      --retry-delay 2 \
      --progress-bar \
      --output "$destination" "$url" || curl_status=$?

    if [[ $curl_status -eq 0 ]]; then
      return 0
    fi

    # curl returns 33 when a byte-range resume cannot be satisfied. Let the
    # checksum-aware caller replace that cache entry instead of retrying the
    # same impossible range request.
    if [[ $curl_status -eq 33 ]]; then
      return 33
    fi

    echo "Download attempt $attempt failed; retrying with the partial file..." >&2
    sleep $((attempt * 2))
  done

  echo "Download failed after 5 attempts: $url" >&2
  return 1
}

verify_sha256() {
  local expected="$1"
  local file="$2"
  printf '%s  %s\n' "$expected" "$file" | sha256sum --check --status
}

verify_sha512() {
  local expected="$1"
  local file="$2"
  printf '%s  %s\n' "$expected" "$file" | sha512sum --check --status
}

download_verified() {
  local algorithm="$1"
  local expected="$2"
  local url="$3"
  local destination="$4"
  local verified=false

  if [[ -f "$destination" ]]; then
    if [[ "$algorithm" == "sha256" ]] && verify_sha256 "$expected" "$destination"; then
      verified=true
    elif [[ "$algorithm" == "sha512" ]] && verify_sha512 "$expected" "$destination"; then
      verified=true
    fi

    if [[ "$verified" == true ]]; then
      echo "Using verified cached download: $destination"
      return 0
    fi

    echo "Resuming incomplete cached download: $destination"
  fi

  local download_status=0
  download "$url" "$destination" || download_status=$?
  if [[ $download_status -eq 33 ]]; then
    echo "Cached download could not be resumed; downloading it again from scratch." >&2
    rm -f -- "$destination"
    download "$url" "$destination"
  elif [[ $download_status -ne 0 ]]; then
    # Preserve the partial file so a later invocation can continue it.
    return "$download_status"
  fi

  if [[ "$algorithm" == "sha256" ]] && verify_sha256 "$expected" "$destination"; then
    return 0
  fi
  if [[ "$algorithm" == "sha512" ]] && verify_sha512 "$expected" "$destination"; then
    return 0
  fi

  echo "Cached download failed checksum verification; downloading it again from scratch." >&2
  rm -f -- "$destination"
  download "$url" "$destination"

  if [[ "$algorithm" == "sha256" ]]; then
    verify_sha256 "$expected" "$destination"
  else
    verify_sha512 "$expected" "$destination"
  fi
}

if [[ ! -r /etc/os-release ]]; then
  echo "Cannot determine the Linux distribution." >&2
  exit 1
fi

# shellcheck disable=SC1091
source /etc/os-release
if [[ "${ID:-}" != "ubuntu" || "${VERSION_ID:-}" != "24.04" ]]; then
  echo "This script is tested on Ubuntu 24.04; detected ${PRETTY_NAME:-unknown}." >&2
  echo "Refusing to guess package names on a different distribution." >&2
  exit 1
fi

step "Installing Ubuntu packages"
export DEBIAN_FRONTEND=noninteractive
apt-get update
apt-get install --yes --no-install-recommends \
  ca-certificates \
  curl \
  git \
  make \
  openjdk-21-jdk \
  tar \
  unzip \
  xauth \
  xvfb \
  libasound2t64 \
  libgl1 \
  libgl1-mesa-dri \
  libgtk-3-0t64 \
  libgstreamer1.0-0 \
  gstreamer1.0-plugins-base \
  libicu74 \
  libssl3 \
  libx11-6 \
  libxcursor1 \
  libxi6 \
  libxinerama1 \
  libxrandr2 \
  libxtst6 \
  zlib1g

JAVAC_EXECUTABLE="$(dpkg -L openjdk-21-jdk-headless | awk '/\/bin\/javac$/ { print; exit }')"
if [[ -z "$JAVAC_EXECUTABLE" ]]; then
  echo "Could not locate the installed JDK 21." >&2
  exit 1
fi
JAVA_HOME="${JAVAC_EXECUTABLE%/bin/javac}"
if [[ ! -x "$JAVA_HOME/bin/java" ]]; then
  echo "The detected JDK home does not contain bin/java: $JAVA_HOME" >&2
  exit 1
fi
if [[ "$("$JAVA_HOME/bin/javac" -version 2>&1)" != javac\ 21.* ]]; then
  echo "The detected compiler is not JDK 21: $JAVA_HOME/bin/javac" >&2
  exit 1
fi

for java_tool in java javac jar javadoc jshell; do
  if [[ -x "$JAVA_HOME/bin/$java_tool" ]]; then
    update-alternatives --set "$java_tool" "$JAVA_HOME/bin/$java_tool" >/dev/null 2>&1 || true
  fi
done

step "Installing Apache Maven $MAVEN_VERSION"
MAVEN_ARCHIVE="$CACHE_DIR/apache-maven-$MAVEN_VERSION-bin.tar.gz"
MAVEN_DESTINATION="$INSTALL_ROOT/apache-maven-$MAVEN_VERSION"
if [[ ! -x "$MAVEN_DESTINATION/bin/mvn" ]] || \
  ! "$MAVEN_DESTINATION/bin/mvn" --version 2>/dev/null | head -n 1 | grep -Fqx "Apache Maven $MAVEN_VERSION"; then
  download_verified sha512 "$MAVEN_SHA512" \
    "https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" \
    "$MAVEN_ARCHIVE"
  rm -rf -- "$MAVEN_DESTINATION"
  tar -xzf "$MAVEN_ARCHIVE" -C "$INSTALL_ROOT"
else
  echo "Apache Maven $MAVEN_VERSION is already installed."
fi
ln -sfn "$MAVEN_DESTINATION" "$MAVEN_HOME_LINK"
ln -sfn "$MAVEN_HOME_LINK/bin/mvn" "$BIN_DIR/mvn"
ln -sfn "$MAVEN_HOME_LINK/bin/mvnDebug" "$BIN_DIR/mvnDebug"

step "Installing PowerShell $POWERSHELL_VERSION"
POWERSHELL_ARCHIVE="$CACHE_DIR/powershell-$POWERSHELL_VERSION-linux-x64.tar.gz"
POWERSHELL_DESTINATION="$INSTALL_ROOT/microsoft/powershell/$POWERSHELL_VERSION"
if [[ ! -x "$POWERSHELL_DESTINATION/pwsh" ]] || \
  [[ "$("$POWERSHELL_DESTINATION/pwsh" -NoLogo -NoProfile -Command '$PSVersionTable.PSVersion.ToString()' 2>/dev/null)" != "$POWERSHELL_VERSION" ]]; then
  download_verified sha256 "$POWERSHELL_SHA256" \
    "https://github.com/PowerShell/PowerShell/releases/download/v$POWERSHELL_VERSION/powershell-$POWERSHELL_VERSION-linux-x64.tar.gz" \
    "$POWERSHELL_ARCHIVE"
  rm -rf -- "$POWERSHELL_DESTINATION"
  install -d -m 0755 "$POWERSHELL_DESTINATION"
  tar -xzf "$POWERSHELL_ARCHIVE" -C "$POWERSHELL_DESTINATION"
  chmod 0755 "$POWERSHELL_DESTINATION/pwsh"
else
  echo "PowerShell $POWERSHELL_VERSION is already installed."
fi
ln -sfn "$POWERSHELL_DESTINATION/pwsh" "$BIN_DIR/pwsh"

step "Installing JavaFX SDK $JAVAFX_VERSION"
JAVAFX_ARCHIVE="$CACHE_DIR/openjfx-$JAVAFX_VERSION-linux-x64-bin-sdk.zip"
JAVAFX_DESTINATION="$INSTALL_ROOT/javafx-sdk-$JAVAFX_VERSION"
JAVAFX_INSTALL_VALID=true
for javafx_file in \
  javafx.base.jar \
  javafx.controls.jar \
  javafx.fxml.jar \
  javafx.graphics.jar \
  javafx.media.jar \
  javafx.swing.jar \
  javafx.web.jar \
  libglass.so \
  libprism_es2.so; do
  if [[ ! -f "$JAVAFX_DESTINATION/lib/$javafx_file" ]]; then
    JAVAFX_INSTALL_VALID=false
    break
  fi
done
if [[ "$JAVAFX_INSTALL_VALID" == true ]] && \
  ! grep -Fqx "javafx.version=$JAVAFX_VERSION" "$JAVAFX_DESTINATION/lib/javafx.properties" 2>/dev/null; then
  JAVAFX_INSTALL_VALID=false
fi

if [[ "$JAVAFX_INSTALL_VALID" != true ]]; then
  download_verified sha256 "$JAVAFX_SHA256" \
    "https://download2.gluonhq.com/openjfx/$JAVAFX_VERSION/openjfx-${JAVAFX_VERSION}_linux-x64_bin-sdk.zip" \
    "$JAVAFX_ARCHIVE"
  rm -rf -- "$JAVAFX_DESTINATION"
  unzip -q "$JAVAFX_ARCHIVE" -d "$INSTALL_ROOT"
else
  echo "JavaFX SDK $JAVAFX_VERSION is already installed."
fi
ln -sfn "$JAVAFX_DESTINATION" "$JAVAFX_HOME_LINK"

step "Writing system-wide MazeGame environment"
cat >"$PROFILE_FILE" <<EOF
# MazeGame build and runtime environment
export JAVA_HOME="$JAVA_HOME"
export MAVEN_HOME="$MAVEN_HOME_LINK"
export PATH_TO_FX="$JAVAFX_HOME_LINK"
export PATH="\$JAVA_HOME/bin:\$MAVEN_HOME/bin:\$PATH"
EOF
chmod 0644 "$PROFILE_FILE"

step "Running the existing MazeGame user setup"
if [[ -f "$SCRIPT_DIR/install.ps1" ]]; then
  USER_SETUP_PATH="$JAVA_HOME/bin:$MAVEN_HOME_LINK/bin:$BIN_DIR:${PATH:-}"
  runuser -u "$TARGET_USER" -- env \
    HOME="$TARGET_HOME" \
    JAVA_HOME="$JAVA_HOME" \
    MAVEN_HOME="$MAVEN_HOME_LINK" \
    PATH_TO_FX="$JAVAFX_HOME_LINK" \
    PATH="$USER_SETUP_PATH" \
    "$BIN_DIR/pwsh" -NoLogo -NoProfile -File "$SCRIPT_DIR/install.ps1" -SkipEnvSetup
else
  echo "Missing repository installer: $SCRIPT_DIR/install.ps1" >&2
  exit 1
fi

step "Verifying installed tools"
"$JAVA_HOME/bin/java" -version
"$BIN_DIR/mvn" --version
"$BIN_DIR/pwsh" --version
test -d "$JAVAFX_HOME_LINK/lib"
xvfb-run --help >/dev/null

cat <<EOF

MazeGame prerequisites are installed.

Open a new terminal so the system environment is loaded. Then return to the
repository as your normal user and follow the Quick start section in readme.md.
Build and launch commands are maintained in build-tool-readme.md.
EOF
