package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.mazeworld.WallZoneSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Level catalogue (loaded from levels.ecore / levels.xmi)")
class LevelCatalogTest {

    private static final LevelCatalog CATALOG = LevelCatalogLoader.loadDefault();

    private static LevelCatalog test(String name) {
        return LevelCatalogLoader.load(LevelCatalogLoader.DEFAULT_ECORE, "/xmi/levels/" + name);
    }

    @Test
    @DisplayName("the shipped model has the classic first level and a second level")
    void shippedLevels() {
        assertEquals(3, CATALOG.count());
        LevelSpec first = CATALOG.level(1);
        assertFalse(first.hasZones(), "level 1 keeps the classic random walls");
        assertFalse(first.hasSergeants());
        assertEquals(1.0, first.threatCapacityFactor(), 1e-9);
        assertTrue(CATALOG.hasNext(1));
        assertTrue(CATALOG.hasNext(2));
        assertFalse(CATALOG.hasNext(3), "level 3 is the last one for now");
    }

    @Test
    @DisplayName("level 2 has a larger threat capacity than level 1")
    void higherThreatCapacity() {
        LevelSpec first = CATALOG.level(1);
        LevelSpec second = CATALOG.level(2);
        assertTrue(second.threatCapacityFactor() > first.threatCapacityFactor());
        assertEquals(30, first.maxThreat(30));
        assertEquals(45, second.maxThreat(30), "normal difficulty: 30 x 1.5");
        assertEquals(129, second.maxThreat(86), "hard difficulty: 86 x 1.5, rounded");
    }

    @Test
    @DisplayName("level 2 has four zones: the outer zone is the strongest, the inner zone the weakest")
    void fourZonesWeakerInside() {
        LevelSpec second = CATALOG.level(2);
        assertEquals(4, second.zones().size());
        List<WallZoneSpec> walls = second.wallZones();
        assertEquals(4, walls.size());
        for (int zone = 1; zone < walls.size(); zone++) {
            assertTrue(LevelSpec.strengthOf(second.zones().get(zone - 1)) > LevelSpec.strengthOf(second.zones().get(zone)),
                    "zone " + zone + " is weaker than the zone outside it");
        }
    }

    @Test
    @DisplayName("zombie sergeants: one on easy, four on normal, six on hard")
    void sergeantsPerDifficulty() {
        LevelSpec second = CATALOG.level(2);
        assertTrue(second.hasSergeants());
        assertEquals(1, second.sergeantCount(DifficultyTier.EASY));
        assertEquals(4, second.sergeantCount(DifficultyTier.NORMAL));
        assertEquals(6, second.sergeantCount(DifficultyTier.HARD));
        assertEquals(0, CATALOG.level(1).sergeantCount(DifficultyTier.HARD));
    }

    @Test
    @DisplayName("the sergeant stands still, fires bursts and drops a gatling gun")
    void sergeantSpec() {
        SergeantSpec sergeant = CATALOG.level(2).sergeant();
        assertEquals("Zombie sergeant", sergeant.displayName());
        assertTrue(sergeant.health() > 0);
        assertTrue(sergeant.sightRange() > 0);
        assertTrue(sergeant.roundsPerBurst() > 1, "a burst is several rounds");
        assertTrue(sergeant.burstCooldownSeconds() > sergeant.roundIntervalSeconds());
        assertEquals(DropWeapon.GATLING_GUN, sergeant.dropWeapon());
    }

    @Test
    @DisplayName("difficulty class names map to tiers")
    void tiers() {
        assertEquals(DifficultyTier.EASY, DifficultyTier.fromDifficultyName("EasyDifficulty"));
        assertEquals(DifficultyTier.NORMAL, DifficultyTier.fromDifficultyName("NormalDifficulty"));
        assertEquals(DifficultyTier.HARD, DifficultyTier.fromDifficultyName("HardDifficulty"));
        assertEquals(DifficultyTier.NORMAL, DifficultyTier.fromDifficultyName(null), "unknown falls back to normal");
    }

    @Test
    @DisplayName("an unknown level number is rejected")
    void unknownLevel() {
        assertThrows(IllegalArgumentException.class, () -> CATALOG.level(0));
        assertThrows(IllegalArgumentException.class, () -> CATALOG.level(4));
    }

    @Test
    @DisplayName("a valid test model loads")
    void validModelLoads() {
        assertEquals(2, test("valid.xmi").count());
    }

    @Test
    @DisplayName("a level can have any number of zones")
    void anyNumberOfZones() {
        assertEquals(3, test("three-zones.xmi").level(2).zones().size());
        assertEquals(3, test("three-zones.xmi").level(2).wallZones().size());
        assertEquals(0, CATALOG.level(1).zones().size());
    }

    @Test
    @DisplayName("level 2 shows its wall materials; level 1 keeps the classic look")
    void wallMaterialsShown() {
        assertTrue(CATALOG.level(2).showWallMaterials());
        assertFalse(CATALOG.level(1).showWallMaterials());
        assertTrue(CATALOG.level(2).inheritWeapons());
    }

    @Test
    @DisplayName("an inner zone may not be stronger than the zone outside it")
    void weakOutsideRejected() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("weak-outside.xmi"));
        assertTrue(error.getMessage().contains("weaker"), error.getMessage());
    }

    @Test
    @DisplayName("a zone material must exist in the walls model")
    void unknownMaterialRejected() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("unknown-material.xmi"));
        assertTrue(error.getMessage().contains("UNOBTAINIUM"), error.getMessage());
    }

    @Test
    @DisplayName("every difficulty needs a sergeant count when the level has a sergeant")
    void missingTierRejected() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("missing-tier.xmi"));
        assertTrue(error.getMessage().contains("HARD"), error.getMessage());
    }

    @Test
    @DisplayName("applyWalls zones the walls of the level-2 maze and leaves level 1 alone")
    void applyWalls() {
        java.util.function.Supplier<main.game.maze.mazeworld.GameMazeWorld> world = () ->
                new main.game.maze.mazeworld.GameMazeWorld(() -> {
                    java.util.List<main.game.maze.mazeworld.Vector2D> walls = new java.util.ArrayList<>();
                    for (int x = 10; x < 800; x += 20) {
                        for (int y = 10; y < 600; y += 20) {
                            walls.add(new main.game.maze.mazeworld.Vector2D(x, y, x + 10, y));
                        }
                    }
                    return walls;
                });
        main.game.maze.mazeworld.GameMazeWorld classic = world.get();
        int before = classic.getBreakableWalls().size();
        CATALOG.level(1).applyWalls(classic, 800, 600);
        assertEquals(before, classic.getBreakableWalls().size(), "level 1 keeps the classic walls");

        main.game.maze.mazeworld.GameMazeWorld zoned = world.get();
        CATALOG.level(2).applyWalls(zoned, 800, 600);
        assertTrue(zoned.getBreakableWalls().size() != before, "level 2 assigns the walls by zone");
        for (main.game.maze.mazeworld.BreakableWall wall : zoned.getBreakableWalls()) {
            double x = (wall.geometry.getStart().getX() + wall.geometry.getEnd().getX()) / 2;
            double y = (wall.geometry.getStart().getY() + wall.geometry.getEnd().getY()) / 2;
            if (main.game.maze.mazeworld.ZoneGeometry.forBoard(800, 600, 4).zoneOf(x, y) == 0) {
                assertEquals("STEEL_BASIC", wall.material.id(), "the outer zone is steel");
            }
        }
    }

    @Test
    @DisplayName("level 3 is level 2 plus: the zones, sergeants, wall look and cascade limit come from level 2")
    void levelThreeBuildsOnLevelTwo() {
        LevelSpec two = CATALOG.level(2);
        LevelSpec three = CATALOG.level(3);
        assertEquals(two.zones(), three.zones());
        assertEquals(two.sergeant(), three.sergeant());
        assertEquals(two.sergeantPosts(), three.sergeantPosts());
        assertEquals(two.showWallMaterials(), three.showWallMaterials());
        assertEquals(two.maxCascadeDepth(), three.maxCascadeDepth());
        assertEquals(two.threatCapacityFactor(), three.threatCapacityFactor(), 1e-9);
        assertEquals(two.sergeantCount(DifficultyTier.HARD), three.sergeantCount(DifficultyTier.HARD));
    }

    @Test
    @DisplayName("level 3 adds pulsating mines in a circle round the sergeants; levels 1 and 2 have none")
    void levelThreeHasMines() {
        assertFalse(CATALOG.level(1).hasMines());
        assertFalse(CATALOG.level(2).hasMines());
        LevelSpec three = CATALOG.level(3);
        assertTrue(three.hasMines());
        MineSpec mines = three.mines();
        assertEquals(3, mines.count(), "three mines per sergeant");
        assertTrue(mines.playerDamage() > 0 && mines.playerDamage() <= 30, "a little damage to the player");
        assertTrue(mines.blastDamage() > 0 && mines.blastDamage() < mines.playerDamage(),
                "a little less to whatever else it blows up against");
        assertTrue(mines.triggerRadius() < mines.ringRadius(), "the ring is wider than the trigger");
    }

    @Test
    @DisplayName("level 3 adds the teleporting ghost: 3 on easy, 6 on normal, 10 on hard")
    void levelThreeHasTeleportGhosts() {
        assertFalse(CATALOG.level(2).hasTeleportGhosts());
        LevelSpec three = CATALOG.level(3);
        assertTrue(three.hasTeleportGhosts());
        assertEquals(3, three.ghostCount(DifficultyTier.EASY));
        assertEquals(6, three.ghostCount(DifficultyTier.NORMAL));
        assertEquals(10, three.ghostCount(DifficultyTier.HARD));
        assertEquals(0, CATALOG.level(2).ghostCount(DifficultyTier.HARD));
        TeleportGhostSpec ghost = three.teleportGhost();
        assertTrue(TeleportGhostSpec.isPortalGhostId(TeleportGhostSpec.SPAWN_ID_PREFIX + "_3"));
        assertTrue(three.teleport().cooldownSeconds() > 0, "no instant bounce-back");
    }

    @Test
    @DisplayName("a level can override what it takes from its base level")
    void overridesTheBase() {
        LevelCatalog custom = test("based.xmi");
        LevelSpec three = custom.level(3);
        assertEquals(custom.level(2).zones(), three.zones(), "zones come from the base");
        assertEquals(0.5, three.threatCapacityFactor(), 1e-9, "its own factor wins");
        assertFalse(three.showWallMaterials(), "its own look wins");
        assertEquals(3, three.maxCascadeDepth(), "its own cascade limit wins");
    }

    @Test
    @DisplayName("a base level must be an earlier level")
    void baseMustBeEarlier() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("base-later.xmi"));
        assertTrue(error.getMessage().contains("earlier"), error.getMessage());
    }

    @Test
    @DisplayName("a ghost needs a count for every difficulty")
    void ghostNeedsAllTiers() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("ghost-missing-tier.xmi"));
        assertTrue(error.getMessage().contains("HARD"), error.getMessage());
    }
}
