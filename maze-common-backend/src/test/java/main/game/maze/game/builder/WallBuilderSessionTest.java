package main.game.maze.game.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.game.maze.game.builder.CellGrid.Cell;
import main.game.maze.game.builder.CellGrid.Edge;
import main.game.maze.game.builder.WallBuilderSession.Builder;
import main.game.maze.game.builder.WallBuilderSession.Result;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Wall builders: look for corridors into the block round the heart, walk there, build a corridor width per 3 s")
class WallBuilderSessionTest {

    private static final WallBuilderSpec SPEC = LevelCatalogLoader.loadDefault().level(5).wallBuilder();
    private static final int W = 780;
    private static final int H = 660;
    private static final double HEART_X = 390;
    private static final double HEART_Y = 330;

    private GameMazeWorld world;
    private WallBuilderSession session;

    @BeforeEach
    void setUp() {
        world = freshWorld();
        session = new WallBuilderSession(SPEC, W, H, HEART_X, HEART_Y);
    }

    private static GameMazeWorld freshWorld() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(W, H,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    private void put(Vector2D wall) {
        world.addBuiltWall(wall, LevelSpec.wallMaterial(SPEC.wallMaterial()));
    }

    /** Runs the crew for {@code seconds}, putting every piece into the world; returns the pieces built. */
    private List<WallBuilderSession.Piece> run(double seconds, List<Builder> builders, double playerX, double playerY) {
        List<WallBuilderSession.Piece> built = new ArrayList<>();
        for (int i = 0; i < Math.round(seconds / 0.05); i++) {
            Result result = session.tick(0.05, builders, world.getMazeVectors(), playerX, playerY);
            for (WallBuilderSession.Piece piece : result.pieces()) {
                put(piece.wall());
                built.add(piece);
            }
            builders = follow(builders, result);
        }
        return built;
    }

    private static List<Builder> follow(List<Builder> builders, Result result) {
        List<Builder> next = new ArrayList<>();
        for (Builder b : builders) {
            WallBuilderSession.Move move = result.moves().stream().filter(m -> m.id().equals(b.id())).findFirst().orElse(null);
            next.add(move == null ? b : new Builder(b.id(), move.x(), move.y(), b.alive()));
        }
        return next;
    }

    private static List<Builder> crew(String prefix, int count) {
        List<Builder> builders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            builders.add(new Builder(prefix + i, HEART_X + 5 * i, HEART_Y + 5 * i, true));
        }
        return builders;
    }

    private boolean heartReachableFromOutside() {
        CellGrid grid = new CellGrid(world.getMazeVectors(), W, H);
        Cell heart = grid.cellOf(HEART_X, HEART_Y);
        Set<Cell> seen = new HashSet<>();
        Deque<Cell> queue = new ArrayDeque<>();
        queue.add(heart);
        seen.add(heart);
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (Cell next : grid.neighbours(cell)) {
                if (!seen.contains(next) && grid.isOpen(new Edge(cell, next))) {
                    seen.add(next);
                    queue.add(next);
                }
            }
        }
        return seen.stream().anyMatch(c -> Math.max(Math.abs(c.row() - heart.row()), Math.abs(c.col() - heart.col()))
                > SPEC.blockRadiusCells());
    }

    @Test
    @DisplayName("in the end every corridor into the block round the heart is closed and the heart cannot be reached")
    void sealsTheHeart() {
        assertFalse(session.openingsLeft(world.getMazeVectors()).isEmpty(), "the maze starts with the heart open to the outside");
        assertTrue(heartReachableFromOutside());
        run(500, crew("b", 5), 0, 0);
        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty(), "no entrance left open");
        assertFalse(heartReachableFromOutside(), "access to the heart is blocked");
    }

    @Test
    @DisplayName("a corridor width of wall takes a builder 3 seconds: three pieces, a second apart")
    void buildRate() {
        List<Builder> one = crew("b", 1);
        double t = 0;
        double first = -1;
        double last = -1;
        int pieces = 0;
        for (int i = 0; i < 4000 && pieces < 3; i++) {
            Result r = session.tick(0.05, one, world.getMazeVectors(), 0, 0);
            t += 0.05;
            for (WallBuilderSession.Piece piece : r.pieces()) {
                put(piece.wall());
                pieces++;
                if (first < 0) {
                    first = t;
                }
                last = t;
            }
            one = follow(one, r);
        }
        assertEquals(3, pieces);
        assertEquals(2 * SPEC.secondsPerSegment(), last - first, 0.2);
        assertEquals(SPEC.buildSecondsPerCorridor(), SPEC.secondsPerSegment() * WallBuilderSpec.SEGMENTS_PER_CORRIDOR, 1e-9);
    }

    @Test
    @DisplayName("a builder walks to its site at its speed, never faster, through the middle of the corridors")
    void walksAtItsSpeed() {
        List<Builder> one = crew("b", 1);
        Builder before = one.get(0);
        Result r = session.tick(0.05, one, world.getMazeVectors(), 0, 0);   // plans
        r = session.tick(0.05, follow(one, r), world.getMazeVectors(), 0, 0);
        double moved = Math.hypot(r.moves().get(0).x() - before.x(), r.moves().get(0).y() - before.y());
        assertTrue(moved <= SPEC.speed() * 0.1 + 1e-6, "no faster than its speed: " + moved);
    }

    @Test
    @DisplayName("walls are built in whole corridors of pieces one piece long, and each piece only once")
    void piecesAreWholeAndUnique() {
        List<WallBuilderSession.Piece> built = run(300, crew("b", 4), 0, 0);
        assertFalse(built.isEmpty());
        Set<String> distinct = new HashSet<>();
        for (WallBuilderSession.Piece piece : built) {
            Vector2D w = piece.wall();
            assertTrue(distinct.add(w.getStart() + "-" + w.getEnd()), "a piece is built once");
            assertEquals(StageConstants.WallSegmentLengthPx, w.magnitude(), 1e-6);
        }
    }

    @Test
    @DisplayName("a wall the player breaks is built again")
    void rebuildsBrokenWalls() {
        run(500, crew("b", 5), 0, 0);
        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty());
        CellGrid grid = new CellGrid(world.getMazeVectors(), W, H);
        Cell heart = grid.cellOf(HEART_X, HEART_Y);
        int radius = SPEC.blockRadiusCells();
        boolean tried = false;
        for (int row = heart.row() - radius; row <= heart.row() + radius && !tried; row++) {
            for (int col = heart.col() - radius; col <= heart.col() + radius && !tried; col++) {
                Cell inside = new Cell(row, col);
                if (!grid.contains(inside) || Math.max(Math.abs(row - heart.row()), Math.abs(col - heart.col())) != radius) {
                    continue;
                }
                for (Cell outside : grid.neighbours(inside)) {
                    Edge edge = new Edge(inside, outside);
                    if (Math.max(Math.abs(outside.row() - heart.row()), Math.abs(outside.col() - heart.col())) != radius + 1
                            || !grid.isClosed(edge)) {
                        continue;
                    }
                    List<Vector2D> removed = new ArrayList<>();
                    for (Vector2D piece : grid.pieces(edge)) {
                        for (Vector2D v : new ArrayList<>(world.getMazeVectors())) {
                            if (Math.round(v.getStart().getX()) == Math.round(piece.getStart().getX())
                                    && Math.round(v.getStart().getY()) == Math.round(piece.getStart().getY())
                                    && Math.round(v.getEnd().getX()) == Math.round(piece.getEnd().getX())
                                    && Math.round(v.getEnd().getY()) == Math.round(piece.getEnd().getY())) {
                                world.getMazeVectors().remove(v);
                                removed.add(v);
                            }
                        }
                    }
                    if (session.openingsLeft(world.getMazeVectors()).size() == 1) {
                        run(250, crew("b", 5), 0, 0);
                        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty(), "built again");
                        tried = true;
                        break;
                    }
                    world.getMazeVectors().addAll(removed);   // that one did not matter for the heart; try another
                }
            }
        }
        assertTrue(tried, "some entrance of the sealed block could be broken");
    }

    @Test
    @DisplayName("a killed builder is forgotten and another finishes its half-built wall")
    void deadBuildersLeaveTheirWorkToOthers() {
        List<Builder> two = crew("old", 2);
        List<WallBuilderSession.Piece> some = run(240, two, 0, 0);
        assertFalse(some.isEmpty());
        List<Builder> dead = new ArrayList<>();
        for (Builder b : two) {
            dead.add(new Builder(b.id(), b.x(), b.y(), false));
        }
        session.tick(0.05, dead, world.getMazeVectors(), 0, 0);
        assertTrue(session.siteOf("old0").isEmpty() && session.siteOf("old1").isEmpty(), "their sites are free again");
        run(500, crew("new", 1), 0, 0);
        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty(), "the new builder finished the job");
    }

    @Test
    @DisplayName("no piece of wall goes up on top of the player: the builder waits until they have moved")
    void notOnThePlayer() {
        List<Builder> one = crew("b", 1);
        // find the corridor the builder works on, then stand in it
        Edge site = null;
        for (int i = 0; i < 8000 && site == null; i++) {
            Result r = session.tick(0.05, one, world.getMazeVectors(), -1000, -1000);
            one = follow(one, r);
            site = session.siteOf("b0").orElse(null);
        }
        assertTrue(site != null);
        Point2D mid = new CellGrid(world.getMazeVectors(), W, H).midpointOf(site);
        int pieces = 0;
        for (int i = 0; i < 200; i++) {
            Result r = session.tick(0.05, one, world.getMazeVectors(), mid.getX(), mid.getY());
            for (WallBuilderSession.Piece piece : r.pieces()) {
                put(piece.wall());
                pieces++;
            }
            one = follow(one, r);
        }
        assertEquals(0, pieces, "the player stands in the doorway");
        pieces = run(90, one, -1000, -1000).size();
        assertTrue(pieces > 0, "once the player has moved the wall goes up");
    }

    @Test
    @DisplayName("a pause does not make the crew jump ahead: a tick advances at most a quarter second")
    void pauseIsCapped() {
        List<Builder> one = crew("b", 1);
        session.tick(0.05, one, world.getMazeVectors(), 0, 0);
        Result r = session.tick(30, one, world.getMazeVectors(), 0, 0);
        double moved = Math.hypot(r.moves().get(0).x() - one.get(0).x(), r.moves().get(0).y() - one.get(0).y());
        assertTrue(moved <= SPEC.speed() * WallBuilderSession.MAX_TICK_SECONDS + 1e-6, "moved " + moved);
        assertTrue(r.pieces().isEmpty());
    }

    private static int ringOf(Cell cell, Cell heart) {
        return Math.max(Math.abs(cell.row() - heart.row()), Math.abs(cell.col() - heart.col()));
    }

    @Test
    @DisplayName("the builders wander outwards from the heart until the first ring, and build their first wall there")
    void wanderOutwardsThenBuildAtTheFirstRing() {
        CellGrid grid = new CellGrid(world.getMazeVectors(), W, H);
        Cell heart = grid.cellOf(HEART_X, HEART_Y);
        List<Builder> crew = crew("b", 3);
        double reached = 0;
        Edge firstSite = null;
        for (int i = 0; i < 6000 && firstSite == null; i++) {
            Result r = session.tick(0.05, crew, world.getMazeVectors(), -1000, -1000);
            crew = follow(crew, r);
            for (Builder b : crew) {
                reached = Math.max(reached, ringOf(grid.cellOf(b.x(), b.y()), heart));
                if (firstSite == null && session.siteOf(b.id()).isPresent()) {
                    firstSite = session.siteOf(b.id()).get();
                }
            }
        }
        assertTrue(firstSite != null, "a builder found a corridor to wall up");
        assertEquals(SPEC.startRadiusCells(), ringOf(firstSite.a(), heart), "the first wall is at the first ring");
        assertTrue(reached >= SPEC.startRadiusCells(), "they walked out to the ring first: " + reached);
        assertEquals(SPEC.startRadiusCells(), session.ringInProgress(world.getMazeVectors()));
    }

    @Test
    @DisplayName("when a ring is closed the builders move one corridor width closer: rings are worked outermost first, one by one")
    void movesOneRingCloserAtATime() {
        List<Integer> rings = new ArrayList<>();
        List<Builder> crew = crew("b", 5);
        for (int i = 0; i < 12000; i++) {
            Result r = session.tick(0.05, crew, world.getMazeVectors(), -1000, -1000);
            for (WallBuilderSession.Piece piece : r.pieces()) {
                put(piece.wall());
            }
            crew = follow(crew, r);
            int ring = session.ringInProgress(world.getMazeVectors());
            if (ring >= 0 && (rings.isEmpty() || rings.get(rings.size() - 1) != ring)) {
                rings.add(ring);
            }
            if (ring < 0) {
                break;
            }
        }
        assertFalse(rings.isEmpty());
        assertEquals(SPEC.startRadiusCells(), rings.get(0), "starts at the outermost ring");
        for (int i = 1; i < rings.size(); i++) {
            assertEquals(rings.get(i - 1) - 1, rings.get(i), "one corridor width closer each time: " + rings);
        }
        assertEquals(SPEC.blockRadiusCells(), rings.get(rings.size() - 1), "down to the innermost ring");
        assertEquals(-1, session.ringInProgress(world.getMazeVectors()), "all rings closed in the end");
    }

    @Test
    @DisplayName("a builder does not walk through a wall that goes up across its route")
    void doesNotWalkThroughANewWall() {
        CellGrid grid = new CellGrid(world.getMazeVectors(), W, H);
        List<Builder> one = crew("b", 1);
        Cell blockedCell = null;
        // run until the builder is on its way between two cells, then wall that corridor up in front of it
        for (int i = 0; i < 4000 && blockedCell == null; i++) {
            Builder before = one.get(0);
            Result r = session.tick(0.05, one, world.getMazeVectors(), -1000, -1000);
            one = follow(one, r);
            Builder after = one.get(0);
            double dx = after.x() - before.x();
            double dy = after.y() - before.y();
            Cell from = grid.cellOf(before.x(), before.y());
            if (Math.hypot(dx, dy) > 0.1 && grid.cellOf(after.x(), after.y()).equals(from)) {
                Cell next = Math.abs(dx) > Math.abs(dy) ? new Cell(from.row(), from.col() + (int) Math.signum(dx))
                        : new Cell(from.row() + (int) Math.signum(dy), from.col());
                if (grid.contains(next) && grid.isOpen(new Edge(from, next))) {
                    for (Vector2D piece : grid.pieces(new Edge(from, next))) {
                        put(piece);
                    }
                    blockedCell = next;
                }
            }
        }
        assertTrue(blockedCell != null, "the builder was seen walking");
        CellGrid walled = new CellGrid(world.getMazeVectors(), W, H);
        boolean startedOutside = !walled.cellOf(one.get(0).x(), one.get(0).y()).equals(blockedCell);
        assertTrue(startedOutside);
        for (int i = 0; i < 400; i++) {
            Result r = session.tick(0.05, one, world.getMazeVectors(), -1000, -1000);
            for (WallBuilderSession.Piece piece : r.pieces()) {
                put(piece.wall());
            }
            one = follow(one, r);
            assertFalse(walled.cellOf(one.get(0).x(), one.get(0).y()).equals(blockedCell),
                    "walked through the wall into " + blockedCell);
        }
    }

    @Test
    @DisplayName("a wall the player breaks only in part is repaired, but a corridor the maze itself walls is not taken for a builder wall")
    void repairsPartlyBrokenWalls() {
        List<WallBuilderSession.Piece> built = run(500, crew("b", 5), 0, 0);
        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty());
        Vector2D broken = built.get(built.size() - 1).wall();   // the innermost ring is the last one closed
        world.getMazeVectors().removeIf(v -> Math.round(v.getStart().getX()) == Math.round(broken.getStart().getX())
                && Math.round(v.getStart().getY()) == Math.round(broken.getStart().getY())
                && Math.round(v.getEnd().getX()) == Math.round(broken.getEnd().getX())
                && Math.round(v.getEnd().getY()) == Math.round(broken.getEnd().getY()));
        assertFalse(session.openingsLeft(world.getMazeVectors()).isEmpty(), "one piece is missing");
        run(300, crew("b", 5), 0, 0);
        assertTrue(session.openingsLeft(world.getMazeVectors()).isEmpty(), "the gap was filled");
    }

    @Test
    @DisplayName("builders keep out of lava: they never stand in deadly lava, and walk over the bridges")
    void buildersAvoidLava() {
        main.game.maze.mazeworld.LavaField lava = main.game.maze.game.lava.LavaGenerator.generate(
                new main.game.maze.game.level.LavaSpec(2, 46.0, 2, 56.0, null), world, W, H,
                new Point2D(30, 90), new Point2D(HEART_X, HEART_Y), new java.util.Random());
        session.setLava(lava);
        List<Builder> crew = crew("b", 4);
        for (int i = 0; i < 4000; i++) {
            Result r = session.tick(0.05, crew, world.getMazeVectors(), -1000, -1000);
            for (WallBuilderSession.Piece piece : r.pieces()) {
                put(piece.wall());
            }
            crew = follow(crew, r);
            for (Builder b : crew) {
                assertFalse(lava.isDeadly(b.x(), b.y()), "a builder walked into lava at " + b.x() + "," + b.y());
            }
        }
    }
}
