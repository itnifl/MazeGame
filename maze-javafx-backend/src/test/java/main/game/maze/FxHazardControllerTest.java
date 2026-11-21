package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.hazard.HazardSession;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.hazard.MineField;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX mines, holes and teleports")
class FxHazardControllerTest {

    private static final double HALF = StageConstants.PlayerCharacterXYSize * 0.5d;
    private static final LevelSpec LEVEL_3 = LevelCatalogLoader.loadDefault().level(3);

    /** Records what the controller asks the game screen to do. */
    private static final class RecordingHost implements FxHazardController.Host {
        final PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        final List<double[]> blasts = new ArrayList<>();
        final List<double[]> teleports = new ArrayList<>();
        final List<Integer> playerHurts = new ArrayList<>();
        final List<HazardSession.Cause> causes = new ArrayList<>();
        int draws;

        @Override public PlayerCharacter player() { return player; }
        @Override public void blast(double x, double y, int damage, double range) { blasts.add(new double[] {x, y, damage, range}); }
        @Override public void hurtPlayer(int damage) { playerHurts.add(damage); }
        @Override public void teleportPlayer(double x, double y, HazardSession.Cause cause) {
            teleports.add(new double[] {x, y});
            causes.add(cause);
        }
        @Override public void draw(MineField field) { draws++; }
    }

    private RecordingHost host;
    private FxHazardController hazards;
    private GameMazeWorld world;
    private List<Point2D> free;

    private static GameMazeWorld freshMaze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(800, 665,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    @BeforeEach
    void setUp() {
        host = new RecordingHost();
        hazards = new FxHazardController(host, new Random(3));
        // find a maze where the sergeant's circle really gets mines
        for (int attempt = 0; attempt < 20; attempt++) {
            world = freshMaze();
            free = MazeHazards.freePoints(world);
            Point2D post = free.stream().min((a, b) -> Double.compare(
                    Math.hypot(a.getX() - 400, a.getY() - 332), Math.hypot(b.getX() - 400, b.getY() - 332))).orElseThrow();
            hazards.startGame(world, LEVEL_3, List.of(post), 400, 332);
            if (hazards.field() != null && !hazards.field().mines().isEmpty()) {
                return;
            }
        }
        throw new AssertionError("no maze with mines in 20 tries");
    }

    @Test
    @DisplayName("level 3 starts with mines and draws them")
    void minesFromTheLevel() {
        assertFalse(hazards.field().mines().isEmpty());
        assertTrue(host.draws > 0);
    }

    @Test
    @DisplayName("a level without mines has none")
    void noMinesOnLevelTwo() {
        hazards.startGame(world, LevelCatalogLoader.loadDefault().level(2), List.of(new Point2D(400, 332)), 400, 332);
        assertNull(hazards.field());
    }

    @Test
    @DisplayName("walking onto a mine: a small blast, the player hurt a little, and the player falls through the hole to the opposite edge")
    void touchingAMine() {
        MineField.Mine mine = hazards.field().mines().get(0);
        host.player.teleportTo(mine.x() - HALF, mine.y() - HALF);

        hazards.tick(1_000_000_000L);

        assertEquals(1, host.blasts.size());
        double[] blast = host.blasts.get(0);
        assertEquals(mine.x(), blast[0], 1e-9);
        assertEquals(mine.y(), blast[1], 1e-9);
        assertEquals(LEVEL_3.mines().blastDamage(), blast[2], 1e-9, "walls and enemies take a little");
        assertEquals(LEVEL_3.mines().blastRadius(), blast[3], 1e-9);
        assertEquals(List.of(LEVEL_3.mines().playerDamage()), host.playerHurts, "the player takes a little more");
        assertTrue(LEVEL_3.mines().blastDamage() < LEVEL_3.mines().playerDamage(), "a little less to whatever else it blows up against");
        assertEquals(1, host.teleports.size());
        assertEquals(HazardSession.Cause.HOLE, host.causes.get(0));
        double[] exit = host.teleports.get(0);
        assertTrue(free.contains(new Point2D(exit[0], exit[1])), "the exit is free ground, never a wall");
    }

    @Test
    @DisplayName("right after a teleport, a second one waits for the cooldown")
    void cooldown() {
        MineField.Mine mine = hazards.field().mines().get(0);
        host.player.teleportTo(mine.x() - HALF, mine.y() - HALF);
        hazards.tick(1_000_000_000L);
        assertEquals(1, host.teleports.size());

        // still standing on the hole a moment later
        host.player.teleportTo(mine.x() - HALF, mine.y() - HALF);
        hazards.tick(1_500_000_000L);
        assertEquals(1, host.teleports.size(), "cooling down");

        host.player.teleportTo(mine.x() - HALF, mine.y() - HALF);
        hazards.tick(1_000_000_000L + (long) (LEVEL_3.teleport().cooldownSeconds() * 1e9) + 100_000_000L);
        assertEquals(2, host.teleports.size(), "falls in again once ready");
    }

    @Test
    @DisplayName("a portal ghost touch sends the player to a random free spot at the edge of the map, once per cooldown")
    void portalGhostTouch() {
        hazards.onPortalGhostTouch(5_000_000_000L);
        assertEquals(1, host.teleports.size());
        assertEquals(HazardSession.Cause.PORTAL_GHOST, host.causes.get(0));
        double[] exit = host.teleports.get(0);
        assertTrue(free.contains(new Point2D(exit[0], exit[1])), "free ground");
        double minX = free.stream().mapToDouble(Point2D::getX).min().orElse(0);
        double maxX = free.stream().mapToDouble(Point2D::getX).max().orElse(0);
        double minY = free.stream().mapToDouble(Point2D::getY).min().orElse(0);
        double maxY = free.stream().mapToDouble(Point2D::getY).max().orElse(0);
        double band = 0.12 * Math.min(maxX - minX, maxY - minY) + 1e-6;
        assertTrue(exit[0] <= minX + band || exit[0] >= maxX - band || exit[1] <= minY + band || exit[1] >= maxY - band,
                "at the edge: " + exit[0] + "," + exit[1]);

        hazards.onPortalGhostTouch(5_500_000_000L);
        assertEquals(1, host.teleports.size(), "no bounce straight back");
        assertNotNull(hazards.field());
    }

    @Test
    @DisplayName("a player far from every mine is left alone")
    void farAway() {
        host.player.teleportTo(5, 5);
        hazards.tick(1_000_000_000L);
        assertTrue(host.blasts.isEmpty());
        assertTrue(host.teleports.isEmpty());
    }

    @Test
    @DisplayName("tearing the level down clears the mines")
    void dispose() {
        hazards.dispose();
        assertNull(hazards.field());
    }
}
