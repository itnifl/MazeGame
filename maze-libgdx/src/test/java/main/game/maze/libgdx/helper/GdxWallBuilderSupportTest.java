package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.EnemyAnimationSpec;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX wall builders: walk to the corridors into the heart block, wall them up, harmless, stay dead")
class GdxWallBuilderSupportTest {

    private static final WallBuilderSpec SPEC = LevelCatalogLoader.loadDefault().level(5).wallBuilder();
    private static final float W = 780f;
    private static final float H = 660f;

    private GameMazeWorld world;
    private GdxWallBuilderSupport support;
    private final List<String> built = new ArrayList<>();
    private final GdxWallBuilderSupport.Host host = new GdxWallBuilderSupport.Host() {
        @Override public List<Vector2D> walls() { return world.getMazeVectors(); }
        @Override public void build(String builderId, Vector2D piece) {
            built.add(builderId);
            world.addBuiltWall(piece, LevelSpec.wallMaterial(SPEC.wallMaterial()));
        }
    };

    @BeforeEach
    void setUp() {
        world = new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig((int) W, (int) H,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
        support = new GdxWallBuilderSupport();
        support.reset(SPEC, W, H);
    }

    private static GdxEnemyRuntime builder(int index, float x, float y) {
        EnemySpawn spawn = new EnemySpawn(WallBuilderSpec.SPAWN_ID_PREFIX + "-" + index, SPEC.sprite(), x, y, 40f,
                1f, 0, 0, "", BehaviorType.PASSIVE, 1f, 0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE, 0f, 0f, 0f, 0, 0f, 0, SPEC.health(), EnemyAnimationSpec.defaults(), 0);
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 30f, 8);
    }

    private void run(double seconds, List<GdxEnemyRuntime> crew) {
        for (int i = 0; i < Math.round(seconds / 0.05); i++) {
            support.tick(host, crew, -500f, -500f, 0.05f);
        }
    }

    @Test
    @DisplayName("a builder is recognised by its spawn id, is harmless and is not moved by the enemy AI")
    void recognised() {
        GdxEnemyRuntime b = builder(0, W / 2, H / 2);
        assertTrue(b.isWallBuilder());
        assertTrue(b.isStationary(), "the enemy AI leaves it alone");
        assertTrue(b.isHarmless(), "no contact damage");
        assertFalse(b.isGuardTower());
        assertFalse(b.isSergeant());
        for (int i = 0; i < 60; i++) {
            b.advance(openWorld(), null, null, null, null, 0.05f);
        }
        assertEquals(W / 2, b.x(), 1e-6);
    }

    @Test
    @DisplayName("the builders walk to the corridors into the heart block and put up wood walls (screen space is converted)")
    void buildWalls() {
        List<GdxEnemyRuntime> crew = new ArrayList<>(List.of(builder(0, W / 2, H / 2), builder(1, W / 2 + 10, H / 2 + 10)));
        float startX = crew.get(0).x();
        int before = world.getMazeVectors().size();
        run(60, crew);
        assertFalse(built.isEmpty(), "walls went up");
        assertEquals(before + built.size(), world.getMazeVectors().size());
        assertTrue(crew.get(0).x() != startX || crew.get(0).y() != H / 2, "the builder walked");
        assertEquals(2, built.stream().distinct().count());
    }

    @Test
    @DisplayName("a dead builder builds no more; other enemies and a level without builders are left alone")
    void deadAndNone() {
        GdxEnemyRuntime dead = builder(0, W / 2, H / 2);
        dead.subtractHitPoints(SPEC.health(), DeathCause.SHOT, "player");
        run(20, new ArrayList<>(List.of(dead)));
        assertTrue(built.isEmpty());

        support.reset(null, W, H);
        run(20, new ArrayList<>(List.of(builder(1, W / 2, H / 2))));
        assertTrue(built.isEmpty());
    }

    private static WorldView openWorld() {
        return new WorldView() {
            @Override public double playerX() { return 0; }
            @Override public double playerY() { return 0; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return W; }
            @Override public double maxY() { return H; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        };
    }
}
