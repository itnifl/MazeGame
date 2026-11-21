# sound-gen

Synthesises the weapon and pickup sound effects named in
`maze-common-backend/src/main/resources/xmi/items/items.xmi` into
`maze-common-frontend/src/main/resources/main/game/maze/sounds/` as 16-bit mono 22 050 Hz WAV files.
No external tools or samples; output is deterministic (seeded noise) and overwrites existing files.

| File | Sound |
|------|-------|
| `pickup-bomb.wav` | low thunk with a short fuse hiss |
| `pickup-laser.wav` | rising electronic chirp |
| `pickup-sledgehammer.wav` | metallic clank |
| `pickup-shield.wav` | shimmering rising chord |
| `pickup-gatling.wav` | two metallic clacks (a magazine slapped in) |
| `gatling-burst.wav` | a gatling gun: a motor spinning up under a stream of 20 sharp shots a second, then spinning down; used by the sergeants' guns and the gatling gun the player picks up |
| `bomb-explosion.wav` | a deep bomb: a crack, a sub-bass boom sliding from 90 to 28 Hz and a low rumble that dies over 1.5 s; played when the player's bomb (or a mine) goes off |
| `teleport.wav` | a magical zap: a shimmering tone sweeping up and then down over an airy whoosh; played when the player is teleported |
| `teleported.wav` | a deep spoken "Teleported". Not generated from nothing: `RecordVoice.ps1` records the word with the Windows speech synthesizer (Microsoft David), then `DeepVoiceGenerator.java` trims it, lowers its pitch by a quarter, adds a sub-harmonic layer an octave down, rolls off the top and adds a short room echo. Needs Windows to record; the generator step runs anywhere |
| `wall-build.wav` | two wooden hammer knocks with a rattle of nails; played when a wall builder puts up a piece of wall |
| `rpg-launch.wav` | an rpg leaving its tube: a hollow thump, then a rocket motor that ignites with a roaring hiss and fades as the rocket flies away; played when a guard tower fires |
| `rpg-explosion.wav` | a rocket going off: a sharp crack, a short punchy boom and a fast-dying rumble (quicker and lighter than the bomb); played where a rocket lands |
| `tower-hit.wav` | a dull stone knock with a ring of metal and a few chips; played when a guard tower takes damage |
| `tower-destroyed.wav` | a tower collapsing: a huge low boom, a long rumble of falling masonry and a clatter of rubble; played when a guard tower is destroyed (with the deep bomb boom) |
| `pickup-heart.wav` | 8-bit "extra life" jingle: six quick square-wave notes rising over two octaves (G-B-D), the last one ringing with vibrato. Written for this game, not a copy of any other game's sound |
| `pickup-extra-life.wav` | bell fanfare: a quick C-E-G-C climb in a triangle wave ending on a shimmering major chord |
| `laser-fire.wav` | laser "pew" |
| `sledgehammer-swing.wav` | whoosh ending in a heavy thud |
| `weapon-switch.wav` | short click (also played when out of ammo) |

## Run

From the repository root, with Java 21:

```sh
"$JAVA_HOME/bin/java" tools/sound-gen/SoundEffectGenerator.java
```

An optional argument sets another output directory.

## Verification

`PickupsTest.catalogueFromModel` (maze-common-backend) fails if a sound named in the items model is
missing from the classpath. The sounds were generated, not listened to in game by a person.
