package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.strike.StrikeVisuals;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.SpriteVisual;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX UFO retaliation (level 7): the same rules as JavaFX, in screen space")
class GdxUfoSupportTest {

    private final GameWorldModel world = new GameWorldModel();
    private final PlayerCombatStateService combat = new PlayerCombatStateService();
    private final PlayerState player = new PlayerState(300, 200, 24);

    private static List<Point2D> ground() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 13; y++) {
                points.add(new Point2D(x * 30, y * 30));
            }
        }
        return points;
    }

    private static MazeArena maze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx() { return 600; }
            @Override public float heightPx() { return 400; }
            @Override public float startX() { return 8; }
            @Override public float startY() { return 8; }
            @Override public float goalX() { return 380; }
            @Override public float goalY() { return 380; }
        };
    }

    @Test
    @DisplayName("an expensive weapon brings the UFOs on a level with a retaliation, a cheap one does not")
    void onlyExpensiveWeaponsBringThem() {
        world.strikes().setRaid(LevelCatalogLoader.loadDefault().level(7).ufoRaid());
        combat.reset(100);
        assertNull(GdxUfoSupport.retaliateAfter(world, maze(), player, combat, Weapon.GRENADE, ground()));
        assertFalse(world.strikes().raid().active());
        String banner = GdxUfoSupport.retaliateAfter(world, maze(), player, combat, Weapon.AIRSTRIKE, ground());
        assertNotNull(banner);
        assertTrue(world.strikes().raid().ufosActive());
        assertEquals(10, world.strikes().raid().soldiers().size());
    }

    @Test
    @DisplayName("a level without a retaliation brings nothing, whatever weapon is used")
    void otherLevels() {
        world.strikes().setRaid(LevelCatalogLoader.loadDefault().level(6).ufoRaid());
        assertNull(GdxUfoSupport.retaliateAfter(world, maze(), player, combat, Weapon.AIRSTRIKE, ground()));
        assertFalse(world.strikes().raid().active());
    }

    @Test
    @DisplayName("the UFOs and the red soldiers are drawn in screen space (y up) with the red soldier sprites")
    void sprites() {
        world.strikes().setRaid(LevelCatalogLoader.loadDefault().level(7).ufoRaid());
        combat.reset(100);
        GdxUfoSupport.retaliateAfter(world, maze(), player, combat, Weapon.AIRSTRIKE, ground());
        List<SpriteVisual> floor = GdxUfoSupport.floorSprites(world, maze(), 24f, 0.0);
        assertEquals(10, floor.stream().filter(s -> s.spritePath().startsWith(StrikeVisuals.RED_SOLDIER_DIR)).count());
        List<SpriteVisual> air = GdxUfoSupport.airSprites(world, maze(), 24f, 0.0);
        assertEquals(2, air.stream().filter(s -> s.spritePath().endsWith("ufo.png")).count(), "two UFOs");
        for (SpriteVisual sprite : air) {
            assertTrue(sprite.y() >= 0 && sprite.y() <= 440, "inside the screen: " + sprite.y());
        }
        // later a beam marks the ground: a disc on the floor
        for (int i = 0; i < 400 && GdxUfoSupport.floorSprites(world, maze(), 24f, 0.0).stream()
                .noneMatch(s -> s.spritePath().endsWith("ufo-beam.png")); i++) {
            GdxUfoSupport.tick(world, maze(), player, combat, 0.05f);
        }
        assertTrue(GdxUfoSupport.floorSprites(world, maze(), 24f, 0.0).stream()
                .anyMatch(s -> s.spritePath().endsWith("ufo-beam.png")), "a beam marks the ground");
    }
}
