package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Hazard session: the level's mines, holes and teleports, decided once for both frontends")
class HazardSessionTest {

    private static final LevelSpec LEVEL_3 = LevelCatalogLoader.loadDefault().level(3);
    private static final double PLAYER_RADIUS = 15;

    private HazardSession session;

    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 15; x < 800; x += 30) {
            for (int y = 15; y < 600; y += 30) {
                points.add(new Point2D(x, y));
            }
        }
        return points;
    }

    @BeforeEach
    void setUp() {
        session = new HazardSession(new Random(5));
        session.start(LEVEL_3, List.of(new Point2D(400, 300)), grid(), spot -> true, (from, to) -> true);
    }

    @Test
    @DisplayName("level 3 gets three mines round the sergeant and its teleport rules")
    void startsFromTheLevel() {
        assertEquals(3, session.field().mines().size());
        assertEquals(LEVEL_3.teleport(), session.rules());
        assertEquals(LEVEL_3.mines(), session.mineSpec());
    }

    @Test
    @DisplayName("a level without mines has none, but still teleports (a portal ghost can)")
    void levelWithoutMines() {
        HazardSession plain = new HazardSession(new Random(1));
        plain.start(LevelCatalogLoader.loadDefault().level(2), List.of(new Point2D(400, 300)), grid(),
                spot -> true, (from, to) -> true);
        assertNull(plain.field());
        assertNull(plain.mineSpec());
        assertEquals(HazardSession.Result.NONE, plain.tick(0.1, 400, 300, PLAYER_RADIUS, 1.0));
        assertNotNull(plain.portalGhostTouched(2.0));
    }

    @Test
    @DisplayName("touching a mine: it goes off, the player takes a little damage, falls through the hole to the opposite edge")
    void touchingAMine() {
        MineField.Mine mine = session.field().mines().get(0);

        HazardSession.Result result = session.tick(0.016, mine.x(), mine.y(), PLAYER_RADIUS, 10.0);

        assertEquals(1, result.detonations().size());
        assertEquals(LEVEL_3.mines().playerDamage(), result.playerDamage());
        assertTrue(LEVEL_3.mines().blastDamage() < result.playerDamage(), "a little less to everything else");
        assertNotNull(result.teleport());
        assertEquals(HazardSession.Cause.HOLE, result.teleport().cause());
        Point2D exit = result.teleport().exit();
        assertTrue(grid().contains(exit), "free ground");
        assertTrue(exit.getX() < 100 || exit.getX() > 700 || exit.getY() < 100 || exit.getY() > 500, "an edge of the map");
    }

    @Test
    @DisplayName("nothing happens far from the mines")
    void farAway() {
        HazardSession.Result result = session.tick(0.1, 20, 20, PLAYER_RADIUS, 10.0);
        assertTrue(result.detonations().isEmpty());
        assertEquals(0, result.playerDamage());
        assertNull(result.teleport());
    }

    @Test
    @DisplayName("the cooldown stops a second teleport, a hole or a ghost, until it has run out")
    void cooldown() {
        MineField.Mine first = session.field().mines().get(0);
        MineField.Mine second = session.field().mines().get(1);
        assertNotNull(session.tick(0.016, first.x(), first.y(), PLAYER_RADIUS, 10.0).teleport());

        HazardSession.Result cooling = session.tick(0.016, second.x(), second.y(), PLAYER_RADIUS, 10.5);
        assertEquals(1, cooling.detonations().size(), "the mine still goes off");
        assertEquals(LEVEL_3.mines().playerDamage(), cooling.playerDamage());
        assertNull(cooling.teleport(), "but no teleport yet");
        assertNull(session.portalGhostTouched(11.0), "a ghost cannot teleport either");

        double ready = 10.0 + LEVEL_3.teleport().cooldownSeconds() + 0.1;
        assertNotNull(session.tick(0.016, second.x(), second.y(), PLAYER_RADIUS, ready).teleport(),
                "still over the hole: falls in once ready");
    }

    @Test
    @DisplayName("a portal ghost touch sends the player to a random free spot at the edge of the map")
    void portalGhost() {
        HazardSession.Teleport teleport = session.portalGhostTouched(5.0);
        assertNotNull(teleport);
        assertEquals(HazardSession.Cause.PORTAL_GHOST, teleport.cause());
        Point2D exit = teleport.exit();
        assertTrue(grid().contains(exit));
        assertTrue(exit.getX() < 100 || exit.getX() > 700 || exit.getY() < 100 || exit.getY() > 500);
    }

    @Test
    @DisplayName("clearing forgets the mines, holes and cooldown")
    void clear() {
        MineField.Mine mine = session.field().mines().get(0);
        session.tick(0.016, mine.x(), mine.y(), PLAYER_RADIUS, 10.0);
        session.clear();
        assertNull(session.field());
        assertNull(session.mineSpec());
        assertEquals(HazardSession.Result.NONE, session.tick(0.1, mine.x(), mine.y(), PLAYER_RADIUS, 10.1));
    }
}
