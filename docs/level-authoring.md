# Making a level

Levels are data. A new level is one `<levels>` element in
[`levels.xmi`](../maze-common-backend/src/main/resources/xmi/levels/levels.xmi); no Java is needed. The
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
| `<mines>` | a circle of pulsating mines round every sergeant: a touched mine blasts (`blastDamage`, `blastRadius`) and leaves a hole (`holeRadius`) that sends the player to the opposite edge of the map | none |
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

## Building on another level

`baseLevel="2"` makes a level "level 2 plus": it takes the zones, wall look, cascade limit, sergeants and the rest from
level 2 and only needs to say what is new. Level 3 is just this:

```xml
<levels number="3" name="The Minefield" baseLevel="2">
  <mines count="8" ringRadius="62.0" triggerRadius="22.0" pulseSeconds="0.9"
      blastRadius="300.0" blastDamage="250" holeRadius="30.0"/>
  <teleportGhost displayName="Portal ghost" health="60" speed="1.6"
      sprite="/main/game/maze/ghost-portal.png" teleportSound="/main/game/maze/sounds/teleport.wav" cooldownSeconds="2.5"/>
  <ghostCounts difficulty="EASY" count="3"/>
  <ghostCounts difficulty="NORMAL" count="6"/>
  <ghostCounts difficulty="HARD" count="10"/>
</levels>
```

A level can also override what it takes (`threatCapacityFactor`, `showWallMaterials`, `maxCascadeDepth`, `inheritWeapons`,
zones, sergeants). The base level must be an earlier one.

## Checking a new level

1. Add the `<levels>` element.
2. Run `mvn -o test -pl maze-common-backend`: `LevelCatalogTest` loads the shipped file, so a mistake fails there with
   a message that names the level.
3. Play it: win the level before it. With the last level won, *Next level* is no longer offered.

To try a variation without touching the shipped file, copy it under `src/test/resources/xmi/levels/` and load it with
`LevelCatalogLoader.load(LevelCatalogLoader.DEFAULT_ECORE, "/xmi/levels/yours.xmi")` (see `no-inherit.xmi` and `LevelProgressTest`).

## Adding something new (a new kind of level feature)

Add the attribute to `levels.ecore` (with a default and a `documentation` annotation), read it in `LevelCatalogLoader`,
put it on `LevelSpec`, and use it from the shared code both frontends call. Keep per-frontend code to wiring.
