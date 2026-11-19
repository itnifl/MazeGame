# sprite-gen

Asset tooling for enemy sprites.

## DamageSpriteGenerator

Generates the enemy damage-state sprites from issue #80 out of the existing healthy
sprites in `maze-common-frontend/src/main/resources/main/game/maze/`.

For each healthy sprite it writes three progressive variants:

| State | HP | Look |
|-------|----|------|
| `damaged` | 51–75 % | pixel-grid scratches and red nicks, slightly darker |
| `bleeding` | 26–50 % | the damaged look plus blood splatter and drips, red tint |
| `tired` | ≤ 25 % | the bleeding look, desaturated, darker and slumped about 6 % towards the feet |

File names follow `DamageStateResolver.deriveStatePath`, for example
`zombie1-right.png` → `zombie1-damaged-right.png` and `ghost2.png` → `ghost2-damaged.png`.
Marks stay inside the original silhouette, the image size never changes, and
output is deterministic (seeded from the base file name).

### Run

The generator is a single Java 21 source file, so it does not need a build step.
Run it from the repository root:

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/DamageSpriteGenerator.java
```

```powershell
& "$env:JAVA_HOME\bin\java" tools/sprite-gen/DamageSpriteGenerator.java
```

You can pass a different sprite directory as the first argument. The generator
overwrites existing damage-state files. To keep hand-drawn art, remove its base
sprite from `baseSprites()` or commit only the files you want.

### Verification

`DamageStateSpriteAssetsTest` in `maze-common-frontend` checks that every
generated sprite resolves through `DamageStateResolver` and has the same
dimensions as its healthy sprite.
