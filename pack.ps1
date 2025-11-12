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

# Collect files while applying exclusions
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

  return $true
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

Write-Host ("Created: {0}" -f $OutZip)
Write-Host ("Files included: {0}" -f $files.Count)