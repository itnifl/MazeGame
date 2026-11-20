package main.game.maze.mazeworld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.mazeworld.generators.IMazeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Zoned walls: concentric zones around the heart, strongest outside")
class ZonedWallsTest {

    private static final ZoneGeometry GEOMETRY = new ZoneGeometry(400, 300, 400, 300, 4);

    @Test
    @DisplayName("zone 0 is the outer ring, the last zone holds the heart")
    void zoneOfPoints() {
        assertEquals(0, GEOMETRY.zoneOf(5, 300), "board edge is the outer zone");
        assertEquals(0, GEOMETRY.zoneOf(400, 5), "top edge is the outer zone");
        assertEquals(1, GEOMETRY.zoneOf(150, 300));
        assertEquals(2, GEOMETRY.zoneOf(250, 300));
        assertEquals(3, GEOMETRY.zoneOf(400, 300), "the heart is in the inner zone");
        assertEquals(3, GEOMETRY.zoneOf(380, 280));
        assertEquals(0, GEOMETRY.zoneOf(-50, 900), "outside the board counts as the outer zone");
    }

    @Test
    @DisplayName("zones are rectangular rings, scaled to the board, not circles")
    void zonesFollowTheBoardShape() {
        // 25 % of the half width from the centre horizontally = 25 % of the half height vertically
        assertEquals(GEOMETRY.zoneOf(400 + 90, 300), GEOMETRY.zoneOf(400, 300 + 67));
    }

    @Test
    @DisplayName("an invalid geometry is rejected")
    void invalidGeometry() {
        assertThrows(IllegalArgumentException.class, () -> new ZoneGeometry(0, 0, 0, 10, 4));
        assertThrows(IllegalArgumentException.class, () -> new ZoneGeometry(0, 0, 10, 10, 0));
    }

    private static final List<WallZoneSpec> ZONES = List.of(
            new WallZoneSpec(0.0, List.of(new WallMaterialSpec("STEEL_BASIC", "Steel", 80))),
            new WallZoneSpec(0.5, List.of(new WallMaterialSpec("STONE_BASIC", "Stone", 40))),
            new WallZoneSpec(1.0, List.of(new WallMaterialSpec("WOOD_BASIC", "Wood", 20))),
            new WallZoneSpec(1.0, List.of(new WallMaterialSpec("GLASS_BASIC", "Glass", 5))));

    private static GameMazeWorld worldWithWallsAcrossTheBoard() {
        IMazeGenerator generator = () -> {
            List<Vector2D> walls = new ArrayList<>();
            for (int x = 10; x < 800; x += 20) {
                for (int y = 10; y < 600; y += 20) {
                    walls.add(new Vector2D(x, y, x + 10, y));
                }
            }
            return walls;
        };
        return new GameMazeWorld(generator);
    }

    private static double averageHp(GameMazeWorld world, ZoneGeometry geometry, int zone) {
        int hp = 0;
        int walls = 0;
        for (Vector2D wall : world.getMazeVectors()) {
            double x = (wall.getStart().getX() + wall.getEnd().getX()) / 2;
            double y = (wall.getStart().getY() + wall.getEnd().getY()) / 2;
            if (geometry.zoneOf(x, y) != zone) {
                continue;
            }
            walls++;
            BreakableWall breakable = world.findBreakableWall(wall);
            hp += breakable == null ? 1000 : breakable.material.hitPoints();
        }
        return walls == 0 ? 0 : (double) hp / walls;
    }

    @Test
    @DisplayName("the outer zone has the strongest walls, the inner zone the weakest")
    void strongestOutsideWeakestInside() {
        GameMazeWorld world = worldWithWallsAcrossTheBoard();
        world.assignZonedWalls(7L, GEOMETRY, ZONES);

        double outer = averageHp(world, GEOMETRY, 0);
        double second = averageHp(world, GEOMETRY, 1);
        double third = averageHp(world, GEOMETRY, 2);
        double inner = averageHp(world, GEOMETRY, 3);
        assertTrue(outer > second && second > third && third > inner,
                "outer " + outer + " > " + second + " > " + third + " > inner " + inner);
    }

    @Test
    @DisplayName("breakable share and materials follow the zone: outer walls are all solid, inner ones glass")
    void zoneRulesAreApplied() {
        GameMazeWorld world = worldWithWallsAcrossTheBoard();
        world.assignZonedWalls(7L, GEOMETRY, ZONES);

        for (Vector2D wall : world.getMazeVectors()) {
            double x = (wall.getStart().getX() + wall.getEnd().getX()) / 2;
            double y = (wall.getStart().getY() + wall.getEnd().getY()) / 2;
            BreakableWall breakable = world.findBreakableWall(wall);
            switch (GEOMETRY.zoneOf(x, y)) {
                case 0 -> assertEquals(null, breakable, "outer walls cannot be broken");
                case 2 -> assertEquals("WOOD_BASIC", breakable.material.id());
                case 3 -> assertEquals("GLASS_BASIC", breakable.material.id());
                default -> { }
            }
        }
    }

    @Test
    @DisplayName("the same seed gives the same walls")
    void deterministic() {
        GameMazeWorld a = worldWithWallsAcrossTheBoard();
        GameMazeWorld b = worldWithWallsAcrossTheBoard();
        a.assignZonedWalls(11L, GEOMETRY, ZONES);
        b.assignZonedWalls(11L, GEOMETRY, ZONES);
        assertEquals(a.getBreakableWalls().size(), b.getBreakableWalls().size());
    }

    @Test
    @DisplayName("the number of zone rules must match the geometry")
    void zoneCountMustMatch() {
        GameMazeWorld world = worldWithWallsAcrossTheBoard();
        assertThrows(IllegalArgumentException.class, () -> world.assignZonedWalls(1L, GEOMETRY, ZONES.subList(0, 3)));
        assertThrows(IllegalArgumentException.class,
                () -> new WallZoneSpec(1.5, List.of(new WallMaterialSpec("A", "A", 1))));
        assertThrows(IllegalArgumentException.class, () -> new WallZoneSpec(0.5, List.of()));
    }
}
