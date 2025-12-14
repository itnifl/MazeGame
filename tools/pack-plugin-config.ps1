# Packs only plugin/config/build-related files in a zip for minimal distribution size.
# Includes ONLY:
#   - Eclipse plugin files: MANIFEST.MF, plugin.xml, feature.xml, *.product, *.target (incl. maze.target)
#   - pom.xml
#   - RunAcceleo.java
#   - HeadlessGenerator.java / HeadlessGenmerator.java
#   - *.properties
#   - *.xml
#
# Still excludes:
#   - .git folder
#   - temporary-workarea folder
#   - releng/local-p2/features and releng/local-p2/plugins
#   - any first-level subfolder 'target' folders
#   - maze/src/main/resources/main/game/maze and its contents
#
# Example:
#   .\pack-plugin-config.ps1 -Root "D:\Source\MazeGame" -OutZip "D:\Source\MazeGame-plugin-config.zip"

param(
  [string]$Root = (Get-Location).Path,
  [string]$OutZip
)

# Normalize inputs
$Root = (Resolve-Path $Root).Path
if (-not $OutZip) {
  $OutZip = Join-Path (Split-Path -Parent $Root) ("{0}.zip" -f (Split-Path -Leaf $Root))
}

# Ensure Zip types are available
try { Add-Type -AssemblyName System.IO.Compression.FileSystem } catch {}
Add-Type -AssemblyName System.IO.Compression | Out-Null

# Collect files while applying exclusions and inclusions
$files = Get-ChildItem -Path $Root -Recurse -File -Force | Where-Object {
  $full = $_.FullName
  $rel  = $full.Substring($Root.Length).TrimStart('\','/')
  $parts = $rel -split '[\\/]+'
  $relNorm  = ($rel -replace '[\\/]+','/')
  $relLower = $relNorm.ToLowerInvariant()

  # Exclude the output zip if it resides inside $Root
  if ($full -ieq $OutZip) { return $false }

  # 1) Exclude root-level .git
  if ($parts.Length -ge 1 -and $parts[0] -ieq '.git') { return $false }

  # 2) Exclude root-level temporary-workarea
  if ($parts.Length -ge 1 -and $parts[0] -ieq 'temporary-workarea') { return $false }

  # 3) Exclude releng\local-p2\features\... and releng\local-p2\plugins\...
  if ($parts.Length -ge 3 -and $parts[0] -ieq 'releng' -and $parts[1] -ieq 'local-p2' -and
      ($parts[2] -ieq 'features' -or $parts[2] -ieq 'plugins')) { return $false }

  # 4) Exclude first-level subfolder targets: .\<sub>\target\...
  if ($parts.Length -ge 2 -and $parts[1] -ieq 'target') { return $false }

  # 5) Exclude maze\src\main\resources\main\game\maze\...
  if ($relLower -like 'maze/src/main/resources/main/game/maze/*' -or
      $relLower -eq   'maze/src/main/resources/main/game/maze') { return $false }

  # ────────────────────────────────────────────────
  # Inclusion filter: only keep wanted files
  # ────────────────────────────────────────────────
  $name      = $_.Name
  $lowerName = $name.ToLowerInvariant()
  $ext       = ($_.Extension).ToLowerInvariant()

  $include = $false

  # pom.xml
  if ($lowerName -eq 'pom.xml') { $include = $true }

  # Specific Java files
  elseif ($lowerName -eq 'runacceleo.java') { $include = $true }
  elseif ($lowerName -eq 'headlessgenerator.java') { $include = $true }
  elseif ($lowerName -eq 'headlessgeneratorapp.java') { $include = $true } 

  # maze.target and other *.target files
  elseif ($lowerName -eq 'maze.target') { $include = $true }
  elseif ($ext -eq '.target') { $include = $true }

  # Eclipse plugin files
  elseif ($lowerName -eq 'manifest.mf') { $include = $true }
  elseif ($lowerName -eq 'plugin.xml') { $include = $true }
  elseif ($lowerName -eq 'feature.xml') { $include = $true }
  elseif ($ext -eq '.product') { $include = $true }

  # Properties and XML files
  elseif ($ext -eq '.properties') { $include = $true }
  elseif ($ext -eq '.xml') { $include = $true }

  return $include
}

# Create archive (preserve relative paths)
$zipDir = Split-Path -Parent $OutZip
if ($zipDir -and -not (Test-Path $zipDir)) { New-Item -ItemType Directory -Path $zipDir | Out-Null }
if (Test-Path -LiteralPath $OutZip) { Remove-Item -LiteralPath $OutZip -Force }

$fs  = [System.IO.File]::Open($OutZip, [System.IO.FileMode]::Create)
$zip = New-Object System.IO.Compression.ZipArchive($fs, [System.IO.Compression.ZipArchiveMode]::Create, $true)

foreach ($f in $files) {
  $rel = $f.FullName.Substring($Root.Length).TrimStart('\','/')
  $rel = $rel -replace '\\','/'     # ZIP uses forward slashes
  [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile(
    $zip, $f.FullName, $rel, [System.IO.Compression.CompressionLevel]::Optimal
  ) | Out-Null
}

$zip.Dispose()
$fs.Close()

Write-Host ("[{0}] Created: {1}" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss"), $OutZip)
Write-Host ("[{0}] Files included: {1}" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss"), $files.Count)