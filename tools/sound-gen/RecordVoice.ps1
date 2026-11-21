<#
 Records a phrase with the Windows speech synthesizer into a WAV file, as the raw material for the spoken
 sound effects (see DeepVoiceGenerator.java, which turns it into a deep voice).

   powershell -File tools/sound-gen/RecordVoice.ps1 -Text "Teleported" -Out raw-teleported.wav
#>
param(
    [string]$Text = "Teleported",
    [string]$Out = "raw-voice.wav",
    [string]$Voice = "Microsoft David Desktop",
    [int]$Rate = -3
)
if ([System.IO.Path]::IsPathRooted($Out)) {
    $full = $Out
} else {
    $full = Join-Path (Get-Location).Path $Out
}
Add-Type -AssemblyName System.Speech
$synth = New-Object System.Speech.Synthesis.SpeechSynthesizer
$synth.SelectVoice($Voice)
$synth.Rate = $Rate
$synth.SetOutputToWaveFile($full)
$synth.Speak($Text)
$synth.Dispose()
Write-Host "recorded '$Text' with $Voice to $full"
