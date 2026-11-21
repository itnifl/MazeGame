package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.MineSpec;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mines and holes: pulsating mines round the sergeants, a hole where one blows")
class MineFieldTest {

    private static final MineSpec SPEC = LevelCatalogLoader.loadDefault().level(3).mines();
    private static final double PLAYER_RADIUS = 15;
    private MineField field;

    @BeforeEach
    void setUp() {
        field = new MineField(SPEC);
    }

    private void placeAround(Point2D... posts) {
        field.place(List.of(posts), point -> true, (from, to) -> true);
    }

    @Test
    @DisplayName("a full ring of mines surrounds a sergeant, all at the ring radius")
    void ringAroundASergeant() {
        placeAround(new Point2D(400, 300));
        assertEquals(SPEC.count(), field.mines().size());
        for (MineField.Mine mine : field.mines()) {
            assertEquals(SPEC.ringRadius(), Math.hypot(mine.x() - 400, mine.y() - 300), 1e-6);
        }
    }

    @Test
    @DisplayName("the mines are spread evenly round the circle and pulse out of step")
    void spreadAndPhases() {
        placeAround(new Point2D(400, 300));
        double first = Math.atan2(field.mines().get(0).y() - 300, field.mines().get(0).x() - 400);
        double second = Math.atan2(field.mines().get(1).y() - 300, field.mines().get(1).x() - 400);
        assertEquals(2 * Math.PI / SPEC.count(), (second - first + 2 * Math.PI) % (2 * Math.PI), 1e-6);
        long distinctPhases = field.mines().stream().map(MineField.Mine::phase).distinct().count();
        assertTrue(distinctPhases > 1, "they do not all pulse together");
    }

    @Test
    @DisplayName("every sergeant gets its own ring")
    void aRingPerSergeant() {
        placeAround(new Point2D(200, 200), new Point2D(600, 400));
        assertEquals(SPEC.count() * 2, field.mines().size());
    }

    @Test
    @DisplayName("a mine where the spot is not free slides to a free radius on the same line, or is left out")
    void blockedSpots() {
        Point2D post = new Point2D(400, 300);
        // everything beyond the ring radius is a wall: mines slide inwards
        field.place(List.of(post), point -> Math.hypot(point.getX() - 400, point.getY() - 300) < SPEC.ringRadius() - 5,
                (from, to) -> true);
        assertEquals(SPEC.count(), field.mines().size());
        for (MineField.Mine mine : field.mines()) {
            assertTrue(Math.hypot(mine.x() - 400, mine.y() - 300) < SPEC.ringRadius() - 5);
        }

        MineField walled = new MineField(SPEC);
        walled.place(List.of(post), point -> false, (from, to) -> true);
        assertTrue(walled.mines().isEmpty(), "no free spot, no mines");

        MineField hidden = new MineField(SPEC);
        hidden.place(List.of(post), point -> true, (from, to) -> false);
        assertTrue(hidden.mines().isEmpty(), "a mine behind a wall from its sergeant is left out");
    }

    @Test
    @DisplayName("a player far from every mine sets none off, and falls into no hole")
    void nothingHappensFarAway() {
        placeAround(new Point2D(400, 300));
        MineField.Update update = field.update(0.1, 50, 50, PLAYER_RADIUS);
        assertTrue(update.detonations().isEmpty());
        assertNull(update.fellInto());
        assertEquals(SPEC.count(), field.mines().size());
    }

    @Test
    @DisplayName("touching a mine sets it off: the mine is gone and a hole of the specified size is left, with the player in it")
    void touchingAMine() {
        placeAround(new Point2D(400, 300));
        MineField.Mine mine = field.mines().get(0);

        MineField.Update update = field.update(0.016, mine.x() + 3, mine.y(), PLAYER_RADIUS);

        assertEquals(1, update.detonations().size());
        MineField.Detonation detonation = update.detonations().get(0);
        assertEquals(mine, detonation.mine());
        assertEquals(SPEC.count() - 1, field.mines().size(), "the mine is used up");
        assertEquals(1, field.holes().size());
        MineField.Hole hole = field.holes().get(0);
        assertEquals(mine.x(), hole.x(), 1e-9);
        assertEquals(mine.y(), hole.y(), 1e-9);
        assertEquals(SPEC.holeRadius(), hole.radius(), 1e-9);
        assertNotNull(update.fellInto(), "the player stands on the mine, so falls into the hole");
        assertEquals(hole, update.fellInto());
    }

    @Test
    @DisplayName("a hole stays: a player who walks into it later falls in too, one who passes by does not")
    void holeStays() {
        placeAround(new Point2D(400, 300));
        MineField.Mine mine = field.mines().get(0);
        field.update(0.016, mine.x(), mine.y(), PLAYER_RADIUS);

        assertNull(field.update(0.016, mine.x() + 200, mine.y(), PLAYER_RADIUS).fellInto(), "far away");
        assertNull(field.update(0.016, mine.x() + SPEC.holeRadius() + PLAYER_RADIUS + 20, mine.y(), PLAYER_RADIUS).fellInto(),
                "beside the hole");
        assertNotNull(field.update(0.016, mine.x() + SPEC.holeRadius() * 0.5, mine.y(), PLAYER_RADIUS).fellInto(),
                "walked into the hole");
    }

    @Test
    @DisplayName("every mine set off in one step makes its own hole")
    void severalAtOnce() {
        // two rings whose circles meet: one player spot is within reach of a mine of each ring
        MineField close = new MineField(SPEC);
        close.place(List.of(new Point2D(400, 300), new Point2D(400 + SPEC.ringRadius() * 2, 300)),
                point -> true, (from, to) -> true);
        MineField.Update update = close.update(0.016, 400 + SPEC.ringRadius(), 300, PLAYER_RADIUS);
        assertTrue(update.detonations().size() >= 1);
        assertEquals(update.detonations().size(), close.holes().size());
    }

    @Test
    @DisplayName("the pulse beats in time: a mine glows brightest and dimmest once per beat")
    void pulse() {
        placeAround(new Point2D(400, 300));
        MineField.Mine mine = field.mines().get(0);
        double min = 1;
        double max = 0;
        for (double t = 0; t < SPEC.pulseSeconds(); t += 0.01) {
            double glow = MineField.glow(mine, t, SPEC.pulseSeconds());
            assertTrue(glow >= 0 && glow <= 1);
            min = Math.min(min, glow);
            max = Math.max(max, glow);
        }
        assertTrue(max > 0.95 && min < 0.1, "a full beat: " + min + ".." + max);
        assertEquals(MineField.glow(mine, 0.3, SPEC.pulseSeconds()),
                MineField.glow(mine, 0.3 + SPEC.pulseSeconds(), SPEC.pulseSeconds()), 1e-9, "periodic");
    }

    @Test
    @DisplayName("a new level starts without mines or holes")
    void clear() {
        placeAround(new Point2D(400, 300));
        field.update(0.016, field.mines().get(0).x(), field.mines().get(0).y(), PLAYER_RADIUS);
        field.clear();
        assertTrue(field.mines().isEmpty());
        assertTrue(field.holes().isEmpty());
        assertTrue(field.update(1, 0, 0, PLAYER_RADIUS).detonations().isEmpty());
    }
}
