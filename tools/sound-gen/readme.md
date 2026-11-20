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
| `pickup-heart.wav` | soft two-note chime |
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
