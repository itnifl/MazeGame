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
        assertEquals(2, CATALOG.count());
        LevelSpec first = CATALOG.level(1);
        assertFalse(first.hasZones(), "level 1 keeps the classic random walls");
        assertFalse(first.hasSergeants());
        assertEquals(1.0, first.threatCapacityFactor(), 1e-9);
        assertTrue(CATALOG.hasNext(1));
        assertFalse(CATALOG.hasNext(2), "level 2 is the last one for now");
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
        assertTrue(walls.get(0).breakableShare() < walls.get(3).breakableShare(),
                "more of the inner walls can be broken");
    }

    @Test
    @DisplayName("zombie sergeants: one on easy, two on normal, four on hard")
    void sergeantsPerDifficulty() {
        LevelSpec second = CATALOG.level(2);
        assertTrue(second.hasSergeants());
        assertEquals(1, second.sergeantCount(DifficultyTier.EASY));
        assertEquals(2, second.sergeantCount(DifficultyTier.NORMAL));
        assertEquals(4, second.sergeantCount(DifficultyTier.HARD));
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
        assertThrows(IllegalArgumentException.class, () -> CATALOG.level(3));
    }

    @Test
    @DisplayName("a valid test model loads")
    void validModelLoads() {
        assertEquals(2, test("valid.xmi").count());
    }

    @Test
    @DisplayName("a level with zones needs exactly four of them")
    void threeZonesRejected() {
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> test("three-zones.xmi"));
        assertTrue(error.getMessage().contains("four zones"), error.getMessage());
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
                assertTrue(wall.material.id().equals("STEEL_BASIC") || wall.material.id().equals("STONE_BASIC"),
                        "the outer zone is steel and stone: " + wall.material.id());
            }
        }
    }
}
