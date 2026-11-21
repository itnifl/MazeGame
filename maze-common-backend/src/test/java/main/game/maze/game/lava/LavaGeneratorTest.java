package main.game.maze.game.lava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LavaSpec;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.spawn.PlayerSpawn;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.LavaField;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Lava: snaking streams with bridges, the maze always solvable over them")
class LavaGeneratorTest {

    private static final LavaSpec SPEC = LevelCatalogLoader.loadDefault().level(6).lava();
    private static final int W = 780;
    private static final int H = 660;

    private static GameMazeWorld maze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(W, H,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    @Test
    @DisplayName("level 6 is level 5 plus lava: two streams, two bridges over each")
    void levelSix() {
        LevelSpec five = LevelCatalogLoader.loadDefault().level(5);
        LevelSpec six = LevelCatalogLoader.loadDefault().level(6);
        assertFalse(five.hasLava());
        assertTrue(six.hasLava());
        assertEquals(2, SPEC.streamCount());
        assertEquals(2, SPEC.bridgesPerStream());
        assertEquals(five.wallBuilder(), six.wallBuilder(), "everything else is level 5");
        assertEquals(five.guardTower(), six.guardTower());
        assertEquals(five.mines(), six.mines());
    }

    @Test
    @DisplayName("every game has two streams with two bridges each, snaking across the board")
    void streamsAndBridges() {
        for (int attempt = 0; attempt < 15; attempt++) {
            GameMazeWorld world = maze();
            LavaField lava = LavaGenerator.generate(SPEC, world, W, H, new Point2D(30, 90), new Point2D(W / 2.0, H / 2.0), new Random());
            assertEquals(2, lava.streams().size());
            for (LavaField.Stream stream : lava.streams()) {
                assertEquals(2, stream.bridges().size(), "two bridges over each stream");
                // snaking: it does not run straight (it wanders across the axis it flows along)
                double minAcross = Double.MAX_VALUE;
                double maxAcross = -Double.MAX_VALUE;
                boolean horizontal = Math.abs(stream.path().get(0).getX() - stream.path().get(stream.path().size() - 1).getX()) > 300;
                for (Point2D p : stream.path()) {
                    double across = horizontal ? p.getY() : p.getX();
                    minAcross = Math.min(minAcross, across);
                    maxAcross = Math.max(maxAcross, across);
                }
                assertTrue(maxAcross - minAcross > 60, "the stream snakes: " + (maxAcross - minAcross));
                for (LavaField.Bridge bridge : stream.bridges()) {
                    assertTrue(lava.onBridge(bridge.x(), bridge.y()));
                    assertFalse(lava.isDeadly(bridge.x(), bridge.y()), "a bridge is safe");
                }
            }
        }
    }

    @Test
    @DisplayName("lava is deadly except on bridges, and the streams are different every time")
    void deadlyExceptOnBridges() {
        GameMazeWorld world = maze();
        LavaField lava = LavaGenerator.generate(SPEC, world, W, H, new Point2D(30, 90), new Point2D(W / 2.0, H / 2.0), new Random(5));
        LavaField.Stream stream = lava.streams().get(0);
        Point2D inStream = stream.path().get(stream.path().size() / 2);
        boolean onBridge = lava.onBridge(inStream.getX(), inStream.getY());
        assertTrue(lava.isLava(inStream.getX(), inStream.getY()));
        assertEquals(!onBridge, lava.isDeadly(inStream.getX(), inStream.getY()));
        assertFalse(lava.isLava(-500, -500));
        LavaField other = LavaGenerator.generate(SPEC, maze(), W, H, new Point2D(30, 90), new Point2D(W / 2.0, H / 2.0), new Random(99));
        assertFalse(lava.streams().get(0).path().equals(other.streams().get(0).path()), "random streams");
    }

    @Test
    @DisplayName("the maze can always be solved: the way from the start to the heart crosses lava only on bridges")
    void alwaysSolvable() {
        for (int attempt = 0; attempt < 25; attempt++) {
            GameMazeWorld world = maze();
            Point2D start = PlayerSpawn.find(world, 30, 60, 30, W, H);
            Point2D heart = new Point2D(W / 2.0, H / 2.0);
            LavaField lava = LavaGenerator.generate(SPEC, world, W, H, start, heart, new Random());
            List<Point2D> way = MazeNavigationGraphService.findPath(world.getNavigationGraph(), start, heart);
            assertFalse(way.isEmpty());
            assertTrue(LavaGenerator.solvable(lava, way), "a way to the heart without touching lava (attempt " + attempt + ")");
            // and in the world with the lava in it: the navigation graph still connects the start to the heart
            world.setLava(lava);
            assertFalse(MazeNavigationGraphService.findPath(world.getNavigationGraph(), start, heart).isEmpty(),
                    "enemies can still find the way, over the bridges");
        }
    }

    @Test
    @DisplayName("the start and the heart are not in lava; free points, pickups and spawns keep out of it")
    void startHeartAndFreePointsAreDry() {
        GameMazeWorld world = maze();
        Point2D start = PlayerSpawn.find(world, 30, 60, 30, W, H);
        LavaField lava = LavaGenerator.generate(SPEC, world, W, H, start, new Point2D(W / 2.0, H / 2.0), new Random());
        world.setLava(lava);
        assertFalse(lava.isLava(start.getX(), start.getY()));
        assertFalse(lava.isLava(W / 2.0, H / 2.0));
        for (Point2D free : MazeHazards.freePoints(world)) {
            assertFalse(lava.isLava(free.getX(), free.getY()), "free points are dry");
        }
        Point2D spawn = PlayerSpawn.find(world, 30, 60, 30, W, H);
        assertFalse(lava.isLava(spawn.getX(), spawn.getY()), "the player starts dry");
    }

    @Test
    @DisplayName("enemies avoid lava: the navigation graph has no edge through deadly lava")
    void navigationAvoidsLava() {
        GameMazeWorld world = maze();
        Point2D start = PlayerSpawn.find(world, 30, 60, 30, W, H);
        world.setLava(LavaGenerator.generate(SPEC, world, W, H, start, new Point2D(W / 2.0, H / 2.0), new Random()));
        LavaField lava = world.getLava();
        for (var column : world.getNavigationGraph().getGrid()) {
            for (var node : column) {
                for (var neighbour : node.getNeighbors()) {
                    assertFalse(lava.crossesDeadly(node.getX(), node.getY(), neighbour.getX(), neighbour.getY()));
                }
            }
        }
    }

    @Test
    @DisplayName("a level without lava puts none in the world")
    void noLava() {
        GameMazeWorld world = maze();
        LevelCatalogLoader.loadDefault().level(5).applyLava(world, W, H);
        assertTrue(world.getLava() == null);
        LevelCatalogLoader.loadDefault().level(6).applyLava(world, W, H);
        assertTrue(world.getLava() != null);
    }

    private static LavaField thinStream() {
        // a stream 2 px wide running straight down at x = 2
        return new LavaField(List.of(new LavaField.Stream(List.of(new Point2D(2, -50), new Point2D(2, 50)), List.of())), 2);
    }

    @Test
    @DisplayName("a thin stream cannot be jumped over between two samples of a line")
    void thinStreamIsFound() {
        LavaField lava = thinStream();
        assertTrue(lava.crossesDeadly(0, 0, 5, 0), "the line from (0,0) to (5,0) crosses the stream");
        assertFalse(lava.crossesDeadly(10, 0, 20, 0), "a line that stays dry");
        assertTrue(lava.crossesDeadly(-30, 10, 40, 10));
    }

    @Test
    @DisplayName("a builder off the middle of its cell is checked against lava along its real line")
    void lavaBetweenForBuilders() {
        main.game.maze.game.builder.CellGrid grid = new main.game.maze.game.builder.CellGrid(List.of(), 600, 600, thinStream());
        assertTrue(grid.lavaBetween(-5, 0, 10, 0));
        assertFalse(grid.lavaBetween(10, 0, 30, 0));
        assertFalse(new main.game.maze.game.builder.CellGrid(List.of(), 600, 600).lavaBetween(-5, 0, 10, 0), "no lava, no danger");
    }

    @Test
    @DisplayName("a lava spec refuses NaN and non-positive values")
    void specValidation() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> LavaSpec.basic(2, Double.NaN, 2, 56, null));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> LavaSpec.basic(2, 46, 2, Double.NaN, null));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> LavaSpec.basic(0, 46, 2, 56, null));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> LavaSpec.basic(2, 46, 0, 56, null));
    }

    @Test
    @DisplayName("mines are never laid in lava, on the world path both frontends use")
    void minesOutOfLava() {
        for (int attempt = 0; attempt < 10; attempt++) {
            GameMazeWorld world = maze();
            Point2D start = PlayerSpawn.find(world, 30, 60, 30, W, H);
            world.setLava(LavaGenerator.generate(SPEC, world, W, H, start, new Point2D(W / 2.0, H / 2.0), new Random()));
            main.game.maze.game.hazard.HazardSession session = new main.game.maze.game.hazard.HazardSession(new Random(3));
            List<Point2D> posts = MazeHazards.freePoints(world).stream().limit(6).toList();
            session.start(world, LevelCatalogLoader.loadDefault().level(6), posts, W / 2.0, H / 2.0);
            if (session.field() != null) {
                for (var mine : session.field().mines()) {
                    assertFalse(world.getLava().isLava(mine.x(), mine.y()), "a mine in the lava");
                }
            }
        }
    }

    @Test
    @DisplayName("the look and the shape of the lava come from the level model (Ecore, written in the Xtext DSL)")
    void modelDrivesTheLava() {
        LavaSpec spec = LevelCatalogLoader.loadDefault().level(6).lava();
        assertEquals(95.0, spec.meander(), 1e-9);
        assertEquals(340.0, spec.wavelength(), 1e-9);
        assertEquals(34.0, spec.flowSpeed(), 1e-9);
        assertEquals(0.65, spec.glow(), 1e-9);
        assertEquals(0.45, spec.crust(), 1e-9);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> new LavaSpec(2, 46, 2, 56, 95, 340, 34, 1.5, 0.5, null));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> new LavaSpec(2, 46, 2, 56, 95, 0, 34, 0.5, 0.5, null));
    }

    @Test
    @DisplayName("a calm river (meander 0) runs straight; a wild one swings wide")
    void meanderShapesTheStream() {
        double calmRange = 0;
        double wildRange = 0;
        for (int i = 0; i < 20; i++) {
            calmRange = Math.max(calmRange, acrossRange(LavaGenerator.snake(W, H, new LavaSpec(2, 46, 2, 56, 0, 340, 34, 0.5, 0.5, null), new Random(i))));
            wildRange = Math.max(wildRange, acrossRange(LavaGenerator.snake(W, H, new LavaSpec(2, 46, 2, 56, 200, 340, 34, 0.5, 0.5, null), new Random(i))));
        }
        assertTrue(calmRange < 1.0, "a calm river does not swing: " + calmRange);
        assertTrue(wildRange > 150, "a wild river does: " + wildRange);
    }

    private static double acrossRange(List<Point2D> path) {
        boolean horizontal = Math.abs(path.get(0).getX() - path.get(path.size() - 1).getX()) > 300;
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (Point2D p : path) {
            double across = horizontal ? p.getY() : p.getX();
            min = Math.min(min, across);
            max = Math.max(max, across);
        }
        return max - min;
    }
}
