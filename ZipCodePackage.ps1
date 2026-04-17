param(
    [Parameter(Mandatory=$false)]
    [switch]$TextOutput  # <-- Flagget som endrer output fra Zip til Txt
)

# --- Konfigurasjon ---
$rotMappe = Get-Location
$zipFilNavn = "ProjectExport.zip"
$txtFilNavn = "ProjectExport_Combined.txt"
$tempMappeNavn = "Temp_Staging_Area"

# 1. Mapper som skal inkluderes HELT
$heltInkluderteMapper = @(
    "maze\src",
    "main.game.maze.dsl\src",
    "main.game.maze.dsl.tests\src"
)

# 2. Søkemønstre
$handlerPattern = "*Handler.java"
$connectorContentRegex = "implements\s+\w*Connector(\b|<)"

# 3. Global ekskludering
$excludeRegex = "[\\/](bin|obj|.vs|.git|.idea)[\\/]"

# --- Hjelpefunksjon ---
function Test-IsNotTempPath {
    param($Sti)
    return ($Sti -notmatch $excludeRegex)
}

Write-Host "Starter innsamling av filer..." -ForegroundColor Cyan
$alleFilerListe = New-Object System.Collections.Generic.List[string]

# ---------------------------------------------------------
# STEG 1: Hent ALT fra spesifikke mapper
# ---------------------------------------------------------
foreach ($relativSti in $heltInkluderteMapper) {
    $fullSti = Join-Path $rotMappe $relativSti
    if (Test-Path $fullSti) {
        Write-Host "  [1/3] Henter innhold fra '$relativSti'..." -NoNewline
        $filer = Get-ChildItem -Path $fullSti -Recurse -File -ErrorAction SilentlyContinue | 
                 Where-Object { Test-IsNotTempPath $_.FullName }
        foreach ($f in $filer) { $alleFilerListe.Add($f.FullName) }
        Write-Host " Fant $($filer.Count) filer." -ForegroundColor Green
    }
}

# ---------------------------------------------------------
# STEG 2: Finn *Handler.java
# ---------------------------------------------------------
Write-Host "  [2/3] Søker etter '$handlerPattern'..." -NoNewline
$handlerFiler = Get-ChildItem -Path $rotMappe -Recurse -Filter $handlerPattern -File -ErrorAction SilentlyContinue | 
                Where-Object { Test-IsNotTempPath $_.FullName }
foreach ($f in $handlerFiler) { $alleFilerListe.Add($f.FullName) }
Write-Host " Fant $($handlerFiler.Count) filer." -ForegroundColor Green

# ---------------------------------------------------------
# STEG 3: Finn .java med Connector-implementasjon
# ---------------------------------------------------------
Write-Host "  [3/3] Scanner .java filer etter Connector-implementasjon..." -NoNewline
$kandidater = Get-ChildItem -Path $rotMappe -Recurse -Filter "*.java" -File -ErrorAction SilentlyContinue | 
              Where-Object { Test-IsNotTempPath $_.FullName }
$connectorCount = 0
foreach ($fil in $kandidater) {
    if (Select-String -Path $fil.FullName -Pattern $connectorContentRegex -Quiet) {
        $alleFilerListe.Add($fil.FullName)
        $connectorCount++
    }
}
Write-Host " Fant $connectorCount filer." -ForegroundColor Green

# ---------------------------------------------------------
# STEG 4: Fjern duplikater
# ---------------------------------------------------------
$unikeFiler = $alleFilerListe | Select-Object -Unique
if ($unikeFiler.Count -eq 0) { Write-Host "Ingen filer funnet." -ForegroundColor Red; exit }

Write-Host "`nTotalt antall unike filer: $($unikeFiler.Count)" -ForegroundColor Cyan

# =========================================================
# VALG AV OUTPUT FORMAT (ZIP eller TXT)
# =========================================================

$escapedRot = [regex]::Escape($rotMappe.Path)

if ($TextOutput) {
    # -----------------------------------------------------
    # OPTION A: GENERER TEKSTFIL
    # -----------------------------------------------------
    Write-Host "Modus: Tekstfil (-TextOutput er satt)" -ForegroundColor Yellow
    if (Test-Path $txtFilNavn) { Remove-Item $txtFilNavn -Force }

    # Opprett filen
    New-Item -Path $txtFilNavn -ItemType File | Out-Null
    
    foreach ($filSti in $unikeFiler) {
        # Beregn relativ sti
        $relativSti = $filSti -replace "^$escapedRot\\", ""
        
        # Lag header
        $header  = "`n" + ("=" * 80) + "`n"
        $header += "File: $relativSti`n"
        $header += ("=" * 80) + "`n"
        
        # Hent innhold og skriv til fil
        Add-Content -Path $txtFilNavn -Value $header -Encoding UTF8
        
        # Sjekk om filen ser ut som tekst før vi leser innholdet
        # (Siden vi inkluderer alt i src/core, kan det være bilder/dll der)
        $ext = [System.IO.Path]::GetExtension($filSti).ToLower()
        if ($ext -match "\.(java|json|xml|txt|md|config|properties|html|js|css|gradle|pom)$") {
            $innhold = Get-Content -Path $filSti -Raw
            Add-Content -Path $txtFilNavn -Value $innhold -Encoding UTF8
        }
        else {
            Add-Content -Path $txtFilNavn -Value "[BINARY OR UNSUPPORTED FILE CONTENT SKIPPED]" -Encoding UTF8
        }
    }
    Write-Host "Ferdig! Samlet tekstfil lagret: $txtFilNavn" -ForegroundColor Yellow
}
else {
    # -----------------------------------------------------
    # OPTION B: GENERER ZIP (Standard)
    # -----------------------------------------------------
    Write-Host "Modus: Zip-arkiv (Standard)" -ForegroundColor Yellow
    
    $tempMappeSti = Join-Path $rotMappe $tempMappeNavn
    if (Test-Path $tempMappeSti) { Remove-Item $tempMappeSti -Recurse -Force }
    New-Item -Path $tempMappeSti -ItemType Directory | Out-Null

    Write-Host "Bygger mappestruktur..." -ForegroundColor Gray
    foreach ($filSti in $unikeFiler) {
        $relativSti = $filSti -replace "^$escapedRot\\", ""
        $destinasjonFil = Join-Path $tempMappeSti $relativSti
        $destinasjonMappe = Split-Path $destinasjonFil -Parent
        
        if (-not (Test-Path $destinasjonMappe)) {
            New-Item -Path $destinasjonMappe -ItemType Directory -Force | Out-Null
        }
        Copy-Item -Path $filSti -Destination $destinasjonFil
    }

    if (Test-Path $zipFilNavn) { Remove-Item $zipFilNavn -Force }
    Compress-Archive -Path "$tempMappeSti\*" -DestinationPath $zipFilNavn
    
    Remove-Item $tempMappeSti -Recurse -Force
    Write-Host "Ferdig! Zip lagret: $zipFilNavn" -ForegroundColor Yellow
}