param(
    [Parameter(Mandatory=$false)]
    [switch]$TextOutput  # Switches output from Zip to text
)

# Configuration
$rootFolder = Get-Location
$zipFileName = "ProjectExport.zip"
$txtFileName = "ProjectExport_Combined.txt"
$tempFolderName = "Temp_Staging_Area"

# 1. Folders to include fully
$fullyIncludedFolders = @(
    "maze\src",
    "main.game.maze.dsl\src",
    "main.game.maze.dsl.tests\src"
)

# 2. Search patterns
$handlerPattern = "*Handler.java"
$connectorContentRegex = "implements\s+\w*Connector(\b|<)"

# 3. Global exclusions
$excludeRegex = "[\\/](bin|obj|\.vs|\.git|\.idea)[\\/]"

# Helper function
function Test-IsNotExcludedPath {
    param($Path)
    return ($Path -notmatch $excludeRegex)
}

Write-Host "Starting file collection..." -ForegroundColor Cyan
$allFilesList = New-Object System.Collections.Generic.List[string]

# Step 1: Collect all files from specific folders
foreach ($relativePath in $fullyIncludedFolders) {
    $fullPath = Join-Path $rootFolder $relativePath
    if (Test-Path $fullPath) {
        Write-Host "  [1/3] Collecting content from '$relativePath'..." -NoNewline
        $files = Get-ChildItem -Path $fullPath -Recurse -File -ErrorAction SilentlyContinue | 
                 Where-Object { Test-IsNotExcludedPath $_.FullName }
        foreach ($file in $files) { $allFilesList.Add($file.FullName) }
        Write-Host " Found $($files.Count) files." -ForegroundColor Green
    }
}

# Step 2: Find *Handler.java
Write-Host "  [2/3] Searching for '$handlerPattern'..." -NoNewline
$handlerFiles = Get-ChildItem -Path $rootFolder -Recurse -Filter $handlerPattern -File -ErrorAction SilentlyContinue | 
                Where-Object { Test-IsNotExcludedPath $_.FullName }
foreach ($file in $handlerFiles) { $allFilesList.Add($file.FullName) }
Write-Host " Found $($handlerFiles.Count) files." -ForegroundColor Green

# Step 3: Find .java files implementing Connector
Write-Host "  [3/3] Scanning .java files for Connector implementation..." -NoNewline
$candidates = Get-ChildItem -Path $rootFolder -Recurse -Filter "*.java" -File -ErrorAction SilentlyContinue | 
              Where-Object { Test-IsNotExcludedPath $_.FullName }
$connectorCount = 0
foreach ($file in $candidates) {
    if (Select-String -Path $file.FullName -Pattern $connectorContentRegex -Quiet) {
        $allFilesList.Add($file.FullName)
        $connectorCount++
    }
}
Write-Host " Found $connectorCount files." -ForegroundColor Green

# Step 4: Remove duplicates
$uniqueFiles = $allFilesList | Select-Object -Unique
if ($uniqueFiles.Count -eq 0) { Write-Host "No files found." -ForegroundColor Red; exit 1 }

Write-Host "`nTotal unique files: $($uniqueFiles.Count)" -ForegroundColor Cyan

# Output format selection (ZIP or TXT)

$escapedRoot = [regex]::Escape($rootFolder.Path)

if ($TextOutput) {
    # Option A: Generate text file
    Write-Host "Mode: Text file (-TextOutput is set)" -ForegroundColor Yellow
    if (Test-Path $txtFileName) { Remove-Item $txtFileName -Force }

    # Create output file
    New-Item -Path $txtFileName -ItemType File | Out-Null
    
    foreach ($filePath in $uniqueFiles) {
        # Compute relative path
        $relativePath = $filePath -replace "^$escapedRoot\\", ""
        
        # Build section header
        $header  = "`n" + ("=" * 80) + "`n"
        $header += "File: $relativePath`n"
        $header += ("=" * 80) + "`n"
        
        # Write header and file content
        Add-Content -Path $txtFileName -Value $header -Encoding UTF8
        
        # Check if file type looks textual before reading full content
        # Since broad source trees are included, binary files may appear
        $ext = [System.IO.Path]::GetExtension($filePath).ToLower()
        if ($ext -match "\.(java|json|xml|txt|md|config|properties|html|js|css|gradle|pom)$") {
            $content = Get-Content -Path $filePath -Raw
            Add-Content -Path $txtFileName -Value $content -Encoding UTF8
        }
        else {
            Add-Content -Path $txtFileName -Value "[BINARY OR UNSUPPORTED FILE CONTENT SKIPPED]" -Encoding UTF8
        }
    }
    Write-Host "Done! Combined text file saved: $txtFileName" -ForegroundColor Yellow
}
else {
    # Option B: Generate zip archive (default)
    Write-Host "Mode: Zip archive (default)" -ForegroundColor Yellow
    
    $tempFolderPath = Join-Path $rootFolder $tempFolderName
    if (Test-Path $tempFolderPath) { Remove-Item $tempFolderPath -Recurse -Force }
    New-Item -Path $tempFolderPath -ItemType Directory | Out-Null

    Write-Host "Building folder structure..." -ForegroundColor Gray
    foreach ($filePath in $uniqueFiles) {
        $relativePath = $filePath -replace "^$escapedRoot\\", ""
        $destinationFile = Join-Path $tempFolderPath $relativePath
        $destinationFolder = Split-Path $destinationFile -Parent
        
        if (-not (Test-Path $destinationFolder)) {
            New-Item -Path $destinationFolder -ItemType Directory -Force | Out-Null
        }
        Copy-Item -Path $filePath -Destination $destinationFile
    }

    if (Test-Path $zipFileName) { Remove-Item $zipFileName -Force }
    Compress-Archive -Path "$tempFolderPath\*" -DestinationPath $zipFileName
    
    Remove-Item $tempFolderPath -Recurse -Force
    Write-Host "Done! Zip saved: $zipFileName" -ForegroundColor Yellow
}