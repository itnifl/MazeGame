package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX wall builders: walk to the corridors into the heart block and wall them up")
class FxWallBuilderControllerTest {

    private static final WallBuilderSpec SPEC = LevelCatalogLoader.loadDefault().level(5).wallBuilder();
    private static final int W = 780;
    private static final int H = 660;
    private static final long TICK = 50_000_000L;

    private final class Recording implements FxWallBuilderController.Host {
        final PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        final List<String> builtBy = new ArrayList<>();

        @Override public PlayerCharacter player() { return player; }
        @Override public List<Vector2D> walls() { return world.getMazeVectors(); }
        @Override public void build(String builderId, Vector2D piece) {
            builtBy.add(builderId);
            world.addBuiltWall(piece, LevelSpec.wallMaterial(SPEC.wallMaterial()));
        }
    }

    private static final class Worker implements FxWallBuilderController.Body {
        double x;
        double y;
        boolean alive = true;
        Worker(double x, double y) { this.x = x; this.y = y; }
        @Override public double centerX() { return x; }
        @Override public double centerY() { return y; }
        @Override public boolean alive() { return alive; }
        @Override public void moveTo(double newX, double newY) { x = newX; y = newY; }
    }

    private GameMazeWorld world;
    private Recording host;
    private FxWallBuilderController builders;
    private long now = 1_000_000_000L;

    @BeforeEach
    void setUp() {
        world = new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(W, H,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
        host = new Recording();
        host.player.teleportTo(-500, -500);
        builders = new FxWallBuilderController(host);
        builders.startGame(SPEC, W, H);
    }

    private void run(double seconds) {
        for (int i = 0; i < Math.round(seconds / 0.05); i++) {
            now += TICK;
            builders.tick(now);
        }
    }

    @Test
    @DisplayName("the builders move to their corridors and put up wood walls, which are added to the world")
    void buildWalls() {
        Worker a = new Worker(W / 2.0, H / 2.0);
        Worker b = new Worker(W / 2.0 + 10, H / 2.0 + 10);
        builders.register("builder_0", a);
        builders.register("builder_1", b);
        int wallsBefore = world.getMazeVectors().size();
        double startX = a.x;
        run(400);
        assertFalse(host.builtBy.isEmpty(), "walls went up");
        assertEquals(wallsBefore + host.builtBy.size(), world.getMazeVectors().size());
        assertTrue(a.x != startX || a.y != H / 2.0, "the builder walked");
        assertEquals(2, host.builtBy.stream().distinct().count(), "both builders built");
    }

    @Test
    @DisplayName("a dead builder is left where it fell and builds no more; a level without builders does nothing")
    void deadAndNone() {
        Worker a = new Worker(W / 2.0, H / 2.0);
        builders.register("builder_0", a);
        a.alive = false;
        double x = a.x;
        run(20);
        assertTrue(host.builtBy.isEmpty());
        assertEquals(x, a.x, 1e-9);

        builders.startGame(null, W, H);
        builders.register("builder_1", new Worker(W / 2.0, H / 2.0));
        run(20);
        assertTrue(host.builtBy.isEmpty());
    }
}
