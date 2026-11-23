# Making a level

Levels are data. A new level is one `level` block in the DSL file
[`levels.mazegame`](../maze-common-backend/src/main/resources/xmi/levels/levels.mazegame) (Xtext, the `level` section of
`MazeDsl.xtext`); [`levels.xmi`](../maze-common-backend/src/main/resources/xmi/levels/levels.xmi), which the game loads, is
generated from it. No Java is needed.

```
level 3 "The Minefield" {
    basedOn 2
    mines { count 3 ringRadius 62.0 triggerRadius 22.0 pulse 0.9 blastRadius 150.0 blastDamage 15 playerDamage 20 holeRadius 30.0 }
    portalGhost "Portal ghost" { health 60 speed 1.6 sprite "/main/game/maze/ghost-portal.png" }
    portalGhosts easy 3
    portalGhosts normal 6
    portalGhosts hard 10
}
```

After editing `levels.mazegame`, regenerate the XMI:

```
MAZE_LEVELS_REGENERATE=true mvn -o test -pl main.game.maze.dsl.tests
```

(`MazeDslLevelsTest` rewrites `levels.xmi` from the DSL and checks it is valid against `levels.ecore`; without the variable it
fails when the two are out of step.) The attribute names below are the model's (`levels.xmi`); the DSL keywords are
`threatCapacity`, `wallMaterials`, `inheritWeapons`, `cascadeDepth`, `basedOn`, `ring`, `sergeant`, `sergeants`, `mines`,
`portalGhost`, `portalGhosts`, `teleport`, `secondRing` (inside `mines`), `guardTower` and `towers`. The
structure is defined by the metamodel [`levels.ecore`](../maze-common-backend/src/main/resources/xmi/levels/levels.ecore),
and the game checks the file when it loads and refuses to start with a broken level.

Both frontends play the same levels: after a win, the win screen offers *Next level* while there is one.

## What a level can set

| Attribute / element | Meaning | Default |
|---|---|---|
| `number` | 1, 2, 3 ... in order, no gaps | required |
| `name` | shown in the HUD when the level starts (levels after the first) | required |
| `threatCapacityFactor` | multiplies the difficulty's maximum threat: how many / how dangerous enemies may be | `1.0` |
| `showWallMaterials` | draw every wall with the sprite of its material (glass, dirt, wood, stone, steel) | `false` |
| `maxCascadeDepth` | how far a collapse spreads: breakable walls that touch a broken wall fall with it, up to this many steps away. `0` = only the wall hit; `-1` = no limit (the classic rule) | `-1` |
| `baseLevel` | number of an earlier level this one is built on: whatever the level does not set itself (zones, wall look, cascade depth, weapon inheritance, threat capacity, sergeants, mines, ghosts) is taken from that level. `0` = none | `0` |
| `inheritWeapons` | start with the weapons the player had at the end of the previous level | `true` |
| `<zones>` | concentric zones around the heart, outer zone first, **any number**; see below | none = the classic random walls |
| `<mines>` | a circle of anti-personnel mines round every sergeant (`count` of them): a touched mine hurts the player a little (`playerDamage`), walls and enemies a little less (`blastDamage` within `blastRadius`) and leaves a hole (`holeRadius`) that sends the player to the opposite edge of the map | none |
| `<mines>` `secondRingRadius` | a second circle of mines round every sergeant with the same number of mines, staggered half a step from the first and further out (`secondRing` in the DSL), so the level has up to twice the mines (a position blocked by a wall, hidden from the sergeant or crowded by another mine is left out, so the count placed can be lower); `0` = no second circle | `0` |
| `<guardTower>` + `<towerCounts>` | guard towers on the outer parts of the map and how many per difficulty; see [Guard towers](#guard-towers) | none |
| `<teleport>` | what a teleport does: `cooldownSeconds`, the word on the screen and in the voice (`bannerText`, `bannerSeconds`) and the sounds (`zapSound`, `voiceSound`); a level without it uses the defaults | defaults |
| `<teleportGhost>` + `<ghostCounts>` | a ghost that does not hurt but teleports the player to a random spot at the edge of the map; `cooldownSeconds` is the pause before the player can be teleported again | none |
| `<sergeant>` + `<sergeantPosts>` | stationary zombie sergeants guarding the heart, and how many per difficulty | none |

The maze is always generated as usual. A level only changes which walls are strong and how they look.

## Zones

Each `<zones>` element is one ring, listed from the outside in. The board is divided into as many equal rings
as there are elements, scaled to the board, with the heart in the middle.

```xml
<zones displayName="Outer wall"  materialIds="STEEL_BASIC"/>
<zones displayName="Second ring" materialIds="STONE_BASIC" breakableShare="0.8"/>
<zones displayName="Inner ring"  materialIds="DIRT_BASIC GLASS_BASIC"/>
```

- `materialIds`: the wall materials of the ring, mixed at random. Valid ids: `GLASS_BASIC` (5 HP), `DIRT_BASIC` (10),
  `WOOD_BASIC` (20), `STONE_BASIC` (40), `STEEL_BASIC` (80; lasers cannot hurt steel). Other variants such as
  `STONE_REINFORCED` look like their base material (`WallLook`).
- `breakableShare` (0 to 1, default 1): the share of the ring's walls that can be broken. The rest are solid
  and keep the classic wall look.
- **Bound the cascade.** Breakable walls that touch fall together (GR-36), so with every wall breakable one break
  can bring down a whole connected part of the maze. Set the level's `maxCascadeDepth` (level 2 uses 2: the wall hit
  and its neighbours up to two steps away) rather than making walls solid, which stops bombs from breaking anything.
  `CascadeBoundTest` checks every shipped level: no single broken wall may bring down more than 10 % of the maze,
  and `CascadeDepthTest` checks that a bomb on level 2 still breaks walls.
- **Each ring must be weaker than the one outside it** (average hit points, solid walls counting as 200 HP). The
  loader rejects a level where an inner ring is as strong as, or stronger than, the ring around it.

## Sergeants

```xml
<sergeant displayName="Zombie sergeant" health="200" sightRange="380.0"
    roundsPerBurst="5" roundDamage="3" roundIntervalSeconds="0.12" burstCooldownSeconds="1.2"
    sprite="/main/game/maze/zombie-sergeant.png" fireSound="/main/game/maze/sounds/gatling-burst.wav"
    dropWeapon="GATLING_GUN" postDistanceFromHeart="110.0"/>
<sergeantPosts difficulty="EASY" count="1"/>
<sergeantPosts difficulty="NORMAL" count="4"/>
<sergeantPosts difficulty="HARD" count="4"/>
```

A level with a `<sergeant>` needs a `<sergeantPosts>` for all three difficulties. Sergeants never move, fire a burst
at a player they can see (in `sightRange`, no wall between), rest for `burstCooldownSeconds`, and drop `dropWeapon` when
killed. The sprite and sound paths are classpath paths; `tools/sprite-gen` and `tools/sound-gen` make the shipped ones.

## The gift of the last life

```xml
<lastLifeGift weapon="PULSE_CANNON" shots="6"/>
```

In the DSL: `lastLifeWeapon PULSE_CANNON shots 6`. When the player starts their last life (respawns with one life left, or starts the
level with one life) the weapon and its shots are put in their hands; it stays the weapon in hand until the player chooses another
(Tab) and the shots stay in stock. `weapon` is a name of the `Weapon` enum. Level 6 has it, and because every level after it is
built on the one before (`baseLevel`), so does every later level. The pulse beam cannon is an item of `items.xmi` (kind
`PULSE_CANNON`, never spawning): `chargeSeconds="2.0"` is the time it takes to energy up after the fire key, `damage="12000"` (it breaks through every wall it is strong enough to break, taking the wall hit points from its power) and
`range="1200.0"` make the beam strong enough to blow up a tower in a single shot, `chargeSound` and `useSound` are its sounds.

## Lava

```xml
<lava streamCount="2" width="46.0" bridgesPerStream="2" bridgeLength="56.0"
    meander="95.0" wavelength="340.0" flowSpeed="34.0" glow="0.65" crust="0.45"
    deathSound="/main/game/maze/sounds/lava-death.wav"/>
```

In the DSL: `lava { streams 2 width 46.0 bridges 2 bridgeLength 56.0 meander 95.0 wavelength 340.0 flowSpeed 34.0 glow 0.65 crust 0.45 deathSound "..." }` (the five values after `bridgeLength` are optional: leave them out for the defaults). Level 6 is level 5 plus this (`baseLevel="5"`).

The streams are made when the level starts, so every game has different ones: `streamCount` random snaking rivers of lava, `width` pixels
wide, flowing under the walls (drawn beneath the maze). The player dies at once in lava (the power shield does not help) and can
cross a stream only on its `bridgesPerStream` bridges, each `bridgeLength` pixels long along the stream. The generator tries streams
until the way from the start to the heart crosses each one only where a bridge can be put, so the maze can always be solved; the
remaining bridges are put at random open places. `meander` (how far a stream swings from side to side, in pixels; 0 = straight) and `wavelength` (how long one swing is) shape the streams; `flowSpeed` (pixels per second), `glow` (0 to 1: the light it throws on the floor) and `crust` (0 to 1: how much of the surface has cooled to dark plates over glowing cracks) shape how the lava looks and moves. Enemies, the wall builders, spawns, pickups and mines keep out of lava.

## Wall builders

```xml
<wallBuilder displayName="Wall builder" health="80" speed="55.0" sprite="/main/game/maze/zombie-builder.png"
    buildSecondsPerCorridor="3.0" startRadiusCells="4" blockRadiusCells="1" spawnRadius="200.0" wallMaterial="WOOD_BASIC"
    buildSound="/main/game/maze/sounds/wall-build.wav"/>
<builderCounts difficulty="EASY" count="2"/>
<builderCounts difficulty="NORMAL" count="5"/>
<builderCounts difficulty="HARD" count="10"/>
```

In the DSL: `wallBuilder "Wall builder" { health 80 speed 55.0 sprite "..." buildSeconds 3.0 startRadius 4 blockRadius 1 spawnRadius 200.0 material WOOD_BASIC buildSound "..." }` and `builders easy 2`, `builders normal 5`, `builders hard 10`.

A level with a `<wallBuilder>` needs a `<builderCounts>` for all three difficulties (leave the whole list out to inherit it with `baseLevel`).
Wall builders are construction worker zombies: the player can kill them, they cannot harm the player and they do not resurrect.
They start within `spawnRadius` of the heart, one corridor width (60 px) apart from each other. They wander randomly outwards
through the corridors at `speed` until they are `startRadiusCells` cells from the heart (4 = the first ring, a 9 by 9 block), and
build there: each takes a corridor that leads into the block inside the ring and is still open, stands on the heart side of it and
builds a wall of `wallMaterial` (breakable: the player can blast through) across it: a piece every `buildSecondsPerCorridor / 3` seconds, a corridor
width in `buildSecondsPerCorridor`. When every corridor of the ring is closed they move one corridor width closer and wall up the
next ring (3, 2, 1 cells), down to `blockRadiusCells`. Two builders never take the same corridor, a broken wall is built again and a killed builder's
half-built wall is finished by another. Level 5 is level 4 plus this (`baseLevel="4"`).

## Savepoints and restarting

Completing a level saves a savepoint (`data/savepoint.properties`): the next level and the weapons the player takes into it. It is
kept until a new game is started. Restarting a level (the win screen's *Restart level*, the game-over screen's restart) restores the
level exactly as it was entered the first time. Level 1 has no savepoint, so restarting it starts a new game.

## Guard towers

```xml
<guardTower displayName="Guard tower" health="1500" sightRange="650.0" maxWallsOver="3"
    rpgIntervalSeconds="3.0" rpgSpeed="300.0" rpgBlastRadius="60.0" rpgPlayerDamage="25" rpgWallDamage="40"
    gatlingRounds="6" gatlingRoundIntervalSeconds="0.1" gatlingDamage="3" deathBlastDamage="60"
    sprite="/main/game/maze/guard-tower.png"
    rpgLaunchSound="/main/game/maze/sounds/rpg-launch.wav" rpgExplosionSound="/main/game/maze/sounds/rpg-explosion.wav"
    gatlingSound="/main/game/maze/sounds/gatling-burst.wav" hitSound="/main/game/maze/sounds/tower-hit.wav"
    destroyedSound="/main/game/maze/sounds/tower-destroyed.wav"/>
<towerCounts difficulty="EASY" count="2"/>
<towerCounts difficulty="NORMAL" count="3"/>
<towerCounts difficulty="HARD" count="4"/>
```

A level with a `<guardTower>` needs a `<towerCounts>` for all three difficulties. With `baseLevel`, leave out the whole
`<towerCounts>` list to inherit the base level's counts; if you write any, write all three (a list with only `hard` is a complete
replacement and is rejected for the missing `easy` and `normal`). The sound attributes are optional: leave one out to play nothing
for that event, but do not put a placeholder in it, the value is used as a classpath path. A tower never moves and takes a very
great deal of damage (`health`; bombs, lasers, sledgehammers and mine blasts all hurt it). It shoots at a player it
sees: within `sightRange`, and with no more than `maxWallsOver` walls in between (it shoots **over** walls). Its weapons
work to one steady cycle: an **rpg every `rpgIntervalSeconds`** (a rocket that flies `rpgSpeed` pixels a second to where
the player was, and explodes with `rpgBlastRadius`) and, **halfway between two rpgs, a burst of `gatlingRounds`
gatling rounds** (`gatlingDamage` each). A burst must fit between two rpgs: `gatlingRounds * gatlingRoundIntervalSeconds <= rpgIntervalSeconds / 2` (the model refuses a level where it does not; for example four rounds 0.4 s apart is 1.6 s, which is more than half of 3.0 s). A rocket that explodes with a wall between it and the player does not hurt the player.
The towers stand on free ground in the outer band of the map, as far apart as the maze allows, away from the player's
start. When a tower is damaged its sprite changes: `sprite` is the healthy look and the damaged looks come with it by
the usual name (`guard-tower-damaged.png`, `-bleeding.png`, `-tired.png`, made by `tools/sprite-gen/GuardTowerSpriteGenerator.java`).
Sounds: `rpgLaunchSound` when a rocket leaves, `rpgExplosionSound` when it lands, `gatlingSound` for a burst, `hitSound`
when the tower is hit, `destroyedSound` when it is blown up (and the deep bomb boom and the screen shake come with the explosion).

## Building on another level

`baseLevel="2"` makes a level "level 2 plus": it takes the zones, wall look, cascade limit, sergeants and the rest from
level 2 and only needs to say what is new. Level 3 is just this:

```xml
<levels number="3" name="The Minefield" baseLevel="2">
  <mines count="3" ringRadius="62.0" triggerRadius="22.0" pulseSeconds="0.9"
      blastRadius="150.0" blastDamage="15" playerDamage="20" holeRadius="30.0"/>
  <teleportGhost displayName="Portal ghost" health="60" speed="1.6" sprite="/main/game/maze/ghost-portal.png"/>
  <teleport cooldownSeconds="2.5" bannerText="Teleported" bannerSeconds="1.6"
      zapSound="/main/game/maze/sounds/teleport.wav" voiceSound="/main/game/maze/sounds/teleported.wav"/>
  <ghostCounts difficulty="EASY" count="3"/>
  <ghostCounts difficulty="NORMAL" count="6"/>
  <ghostCounts difficulty="HARD" count="10"/>
</levels>
```

Level 4 is level 3 plus a second circle of mines and the guard towers; since a level restates a whole `<mines>`
block to change it, it repeats the mine values and adds `secondRingRadius="118.0"`:

```xml
<levels number="4" name="The Guard Towers" baseLevel="3">
  <mines count="3" ringRadius="62.0" secondRingRadius="118.0" triggerRadius="22.0" pulseSeconds="0.9"
      blastRadius="150.0" blastDamage="15" playerDamage="20" holeRadius="30.0"/>
  <guardTower displayName="Guard tower" health="1500" ... />
  <towerCounts difficulty="EASY" count="2"/> <towerCounts difficulty="NORMAL" count="3"/> <towerCounts difficulty="HARD" count="4"/>
</levels>
```

A level can also override what it takes (`threatCapacityFactor`, `showWallMaterials`, `maxCascadeDepth`, `inheritWeapons`,
zones, sergeants). The base level must be an earlier one.

## Checking a new level

1. Add the `level` block to `levels.mazegame` and regenerate `levels.xmi`.
2. Run `mvn -o test -pl maze-common-backend`: `LevelCatalogTest` loads the shipped file, so a mistake fails there with
   a message that names the level.
3. Play it: win the level before it. With the last level won, *Next level* is no longer offered.

To try a variation without touching the shipped file, copy it under `src/test/resources/xmi/levels/` and load it with
`LevelCatalogLoader.load(LevelCatalogLoader.DEFAULT_ECORE, "/xmi/levels/yours.xmi")` (see `no-inherit.xmi` and `LevelProgressTest`).

## Adding something new (a new kind of level feature)

Add the attribute to `levels.ecore` (with a default and a `documentation` annotation), read it in `LevelCatalogLoader`,
put it on `LevelSpec`, and use it from the shared code both frontends call. Keep per-frontend code to wiring.

## The shop between levels

What the shop sells (issue #164) is written in the same DSL, in its own file
`maze-common-backend/src/main/resources/xmi/shop/shop.mazegame`:

```
game MazeGameShop {
    shop {
        item GRENADE "Grenades" tier CHEAP price 600 amount 5
            description "Thrown ahead of you, they go off after a moment"
        item SHIELD_BOOST "Extra long shield" tier CHEAP price 800 factor 3.0
        ...
    }
}
```

`kind` is a `ShopItemKind` of `shop.ecore` (`SHIELD_BOOST`, `FLAME_BOOST`, `GRENADE`, `PLASMA_GRENADE`, `NUKE_GRENADE`,
`BUNKER_BUSTER`, `FAT_BOMB`, `AIRSTRIKE`, `INFANTRY_STRIKE`, `EXTRA_LIFE`), `tier` is `CHEAP`, `MEDIUM` or `EXPENSIVE`.
Weapons need an `amount` (uses per purchase), boosts a `factor` above 1. The validator reports an unknown kind or tier,
a price that is not positive and an item sold twice. The generator renders the file with the FreeMarker template
`main.game.maze.dsl/.../generator/templates/shop.xmi.ftlx` into `shop.xmi`; regenerate it with

```
MAZE_SHOP_REGENERATE=true mvn -o install -pl main.game.maze.dsl,main.game.maze.dsl.ide,main.game.maze.dsl.tests -am
```

(`MazeDslShopTest` fails when `shop.xmi` and `shop.mazegame` say different things). After a grammar change add
`-Dxtext.codegen.skip=false` to regenerate the Xtext sources. The strength of each shop weapon (damage, range, blast
radius, fuse, units, pierce power, sprite, sounds) is in `xmi/items/items.xmi`.

Which tier is sold from which level is in the same file: `unlock MEDIUM fromLevel 4` sells the medium items from the shop
before level 4 (between levels 3 and 4) on. A tier without an `unlock` is always sold. The validator reports an unknown
tier, a level below 2 and a tier unlocked twice.

## The look of the screens between levels

The win screen and the shop are drawn from the menu theme, written in the same DSL in
`maze-common-backend/src/main/resources/xmi/theme/menutheme.mazegame`:

```
theme {
    font "Consolas"
    colour TITLE "#f2d98c"                                  // one colour per Swatch: TITLE, TEXT, MUTED, ACCENT, ...
    image PANEL "/main/game/maze/ui/panel.png" slice 30     // one texture per Art; slice: edge pixels kept unstretched
    badge CHEAP "/main/game/maze/ui/tier-cheap.png" colour "#cd8c4e"   // one rank badge per shop tier
}
```

Every colour, texture and tier badge must be given exactly once (the validator says which are missing). Regenerate the
model the game loads with `MAZE_THEME_REGENERATE=true mvn -o verify -pl main.game.maze.dsl.tests`; both frontends then
draw the new look. New textures are drawn with `tools/sprite-gen/MenuThemeSpriteGenerator.java`.
