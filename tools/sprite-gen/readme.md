# sprite-gen

Asset tooling for enemy sprites.

## GhostDirectionalSpriteGenerator

Creates `ghost2-{left,right,up,down}.png` and `ghost3-{left,right,up,down}.png` from the
single front-facing `ghost2.png` / `ghost3.png`, in the style of the hand-drawn
`ghost1-left/right.png`:

| Direction | Look |
|-----------|------|
| left | face slid towards the travel direction, lower body sheared back as a trailing tail |
| right | mirror of left |
| up | back view: face removed, sheet texture continued over it |
| down | face shifted down, towards the viewer |

Face boxes are measured per sprite and listed in `GHOSTS`, with a flag for sheets that
carry a texture (ghost2's grid) so erased areas copy the texture instead of a flat fill.

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/GhostDirectionalSpriteGenerator.java
```

Run it **before** `DamageSpriteGenerator`, which also derives damage states for these
directional sprites.

Full order: `GhostDirectionalSpriteGenerator` → `DamageSpriteGenerator` →
`PumpkinBomberDirectionalSpriteGenerator` → `RemainsSpriteGenerator`.

## PumpkinBomberDirectionalSpriteGenerator

Creates the pumpkin bomber's turning sprites as asset files, with the transforms the
JavaFX frontend used to apply at runtime:

| File | Look |
|------|------|
| `pumpkinbomber-left.png` | horizontal mirror of `pumpkinbomber.png` |
| `pumpkinbomber-up.png` | brightened, more saturated tint (JavaFX `brighter().saturate()`) |
| `pumpkinbomber-{damaged,bleeding,tired}-{left,up}.png` | the same transforms applied to each damage sprite, so the marks match the facing |

Right and down keep using `pumpkinbomber.png`. Run it **after** `DamageSpriteGenerator`
(it reads `pumpkinbomber-{damaged,bleeding,tired}.png`):

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/PumpkinBomberDirectionalSpriteGenerator.java
```

Output is deterministic and overwrites existing files.

## BackgroundGenerator

Draws the backgrounds of the difficulties as seamless 1024 x 1024 tiles (everything wraps at the edges):

- `gameBackGround1.png` (easy): friendly light woods, a bright forest floor with dappled sunlight, soft moss, ferns,
  fallen leaves, twigs, pebbles, tiny red mushrooms and flowers.
- `gameBackGround2.png` (normal): a lush meadow with seven patches of burning grass (scorched earth, charred blades,
  embers, flame tongues, smoke).

Deterministic: change `WOODS_SEED`, `SEED` or the `patches` list in the tool for a different arrangement.

```
"$JAVA_HOME/bin/java" tools/sprite-gen/BackgroundGenerator.java
```

## PortalGhostSpriteGenerator

Draws `ghost-portal.png`, the ghost that teleports the player, from `ghost1.png`: a shimmering violet and cyan body, glowing
magenta eyes and a spinning portal swirl with sparkles.

```
"$JAVA_HOME/bin/java" tools/sprite-gen/PortalGhostSpriteGenerator.java
```

## SergeantSpriteGenerator

Draws `zombie-sergeant.png` (the zombie sprite with a cap, stripes and a gatling gun) from `zombie1-down.png`:

```
"$JAVA_HOME/bin/java" tools/sprite-gen/SergeantSpriteGenerator.java
```

The gatling gun pickup sprite (`pickups/gatling-gun.png`) comes from `PickupSpriteGenerator` below.

## PickupSpriteGenerator

Creates the pickup sprites named in `xmi/items/items.xmi` in `main/game/maze/pickups/` (64x64,
transparent, drawn on a 32x32 grid and scaled 2x): `bomb.png`, `laser-rifle.png`, `sledgehammer.png`,
`shield.png`, `heart.png`, plus `heal-arrow.png` (shown over the player after a heart) and `extra-life.png` (a gold "+1" medallion, also the lives icon in the weapons HUD). The art fills each frame,
because items are drawn as large as the player. The rifle and sledgehammer are also drawn in the player's hands.

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/PickupSpriteGenerator.java
```

Output is deterministic and overwrites existing files.

## RemainsSpriteGenerator

Creates the enemy death remains in `main/game/maze/remains/` (all 256x256, transparent):

| File | Look |
|------|------|
| `burned-{1,2,3}.png` | pile of coal lumps with glowing cracks on an ash ring (fire / bomb deaths, any enemy) |
| `<variant>-shot.png` | the bleeding enemy sprite lying on its side in a blood pool, chunks torn out, splashes around |
| `<variant>-crushed.png` | the enemy flattened onto a large blood splat with gore |
| `generic-shot.png`, `generic-crushed.png` | blood only: the fallback for enemies without their own remains |

Variants: zombie1-3, ghost1-3, pumpkinbomber. Ground marks are drawn on a 64x64 grid and scaled up 4x to match
the pixel-art sprites. Needs the `*-bleeding*` damage sprites, so run it **after** `DamageSpriteGenerator`:

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/RemainsSpriteGenerator.java
```

Output is deterministic and overwrites existing files.

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

## GuardTowerSpriteGenerator

Draws the guard tower seen from above (`guard-tower.png`: a round stone tower with battlements, sandbags, a gatling gun
and an rpg launcher) and its three damaged looks the game picks by the tower's health: `guard-tower-damaged.png` (cracks and
soot), `guard-tower-bleeding.png` (chunks blown out, fires) and `guard-tower-tired.png` (half collapsed, charred,
smoking).

```
"$JAVA_HOME/bin/java" tools/sprite-gen/GuardTowerSpriteGenerator.java
```

## WallBuilderSpriteGenerator

Draws `zombie-builder.png`, the construction worker zombie of level 5, from `zombie1-down.png`: a yellow hard hat, an orange hi-vis
vest with reflective stripes and a hammer.

```
"$JAVA_HOME/bin/java" tools/sprite-gen/WallBuilderSpriteGenerator.java
```

## Pulse beam cannon pickup

`PickupSpriteGenerator` also draws `pickups/pulse-cannon.png` (the weapon HUD icon and the weapon in hand): a dark-steel barrel with glowing blue coils and a charged blue-white muzzle.

## Shop weapons and the soldier

`PickupSpriteGenerator` also draws the icons of the weapons sold in the shop between levels (#164), used in the shop, the
weapons HUD and the player's hands: `grenade.png` (a pineapple grenade), `plasma-grenade.png` (purple with a glow),
`nuke-grenade.png` (a yellow canister with the radiation sign), `bunker-buster.png` (an olive rocket with a steel nose),
`fat-bomb.png` (a big bomb with a red band), `airstrike.png` (a bomber dropping bombs), `infantry-strike.png` (a helmet
over crossed rifles), `life-refill.png` (a medkit). The single `soldier.png` of the first version (helmet, green
uniform, rifle) was replaced by the animated soldier of `SoldierSpriteGenerator` (below). The existing sprites come out unchanged.

`PickupSpriteGenerator` also draws what an airstrike drops into `strike/`: `aerial-bomb.png` (an aircraft bomb falling nose down) and `bomb-shadow.png` (the shadow it casts), and the dropped weapons armed, two frames each: `armed-bomb-*`, `armed-fat-bomb-*` (burning fuse), `armed-grenade-*`, `armed-plasma-grenade-*` (pin pulled, smoking), `armed-nuke-grenade-*` (blinking warning light). `MenuThemeSpriteGenerator` draws the keyboard keys of the help screen (`key.png`, `key-up/down/left/right.png`).

## MenuThemeSpriteGenerator

Draws the textures of the screens between levels (win screen and shop) into `ui/`: a riveted gunmetal `panel.png` (9-slice 30), the olive-drab title plate `banner.png` (14), the tiling `hazard-strip.png`, the ammo-crate item cards `card.png`, `card-selected.png` (amber glow) and `card-locked.png` (12), the icon well `slot.png` (10), the metal-plate buttons `button-go.png`, `button-buy.png`, `button-steel.png`, `button-danger.png` (10), the rank chevrons `tier-cheap.png`, `tier-medium.png`, `tier-expensive.png`, the skull coin `token.png`, the padlock `lock.png` and the edge-darkening `vignette.png`. Icons are drawn at twice the size they are shown at. Where each is used is the menu theme model (`menutheme.mazegame`).

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/MenuThemeSpriteGenerator.java
```

## SoldierSpriteGenerator

Draws the soldier of the infantry strike into `soldier/`: `soldier-side-*` (facing right; mirrored in the game for left), `soldier-down-*` (front) and `soldier-up-*` (back, with the backpack), each `stand`, `walk1`, `walk2` and `fire` (muzzle flash), and `soldier-fallen.png`. Camouflage uniform, webbing and pouches, boots, a helmet with a net and a rifle, a soft shadow; 32x32 grid scaled 2x. Replaces the single `pickups/soldier.png`.

```sh
"$JAVA_HOME/bin/java" tools/sprite-gen/SoldierSpriteGenerator.java
```

## RedSoldierSpriteGenerator and UfoSpriteGenerator (level 7)

`RedSoldierSpriteGenerator` turns every sprite of `soldier/` (run `SoldierSpriteGenerator` first) into a crimson `soldier-red/`
twin: the red soldiers the UFOs drop. `UfoSpriteGenerator` writes `ufo/ufo.png` (the saucer) and `ufo/ufo-beam.png` (the glowing
disc a beam makes on the ground, also stacked along the beam to draw its column of light).

```sh
java tools/sprite-gen/RedSoldierSpriteGenerator.java
java tools/sprite-gen/UfoSpriteGenerator.java
```
