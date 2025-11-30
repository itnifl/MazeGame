package main.game.maze;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.Character;
import main.game.maze.characters.PlayerCharacter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import main.game.maze.mazeworld.Point2D;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;

import static org.junit.jupiter.api.Assertions.*;

public class MazeCoreBehaviorTest {

    // ---------- JavaFX bootstrap for headless CI ----------
    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    // ---------- Helpers ----------
    private static void setPrivate(Object target, String field, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(target, value);
    }

    /** Minimal concrete subclass to expose Character#dispose() if protected */
    static class TestCharacter extends Character {
        public TestCharacter(Node node, double x, double y) { super(node, x, y); }
        public void callDispose() { super.dispose(); }
    }

    // ========== 1) Vector2D.normalize keeps direction and sets magnitude ==========
    @Test
    @DisplayName("Vector2D.normalize(k) preserves direction and sets magnitude")
    void vectorNormalizeMagnitudeAndDirection() {
        Vector2D v = new Vector2D(new Point2D(10, 10),
                                  new Point2D(13, 14)); // dx=3, dy=4, mag=5
        Vector2D n = v.normalize(20);

        double dx = n.getEnd().getX() - n.getStart().getX();
        double dy = n.getEnd().getY() - n.getStart().getY();
        assertEquals(20.0, Math.hypot(dx, dy), 1e-6, "Magnitude must equal requested scale");
        assertEquals(3.0 / 5.0, dx / 20.0, 1e-6, "X direction preserved");
        assertEquals(4.0 / 5.0, dy / 20.0, 1e-6, "Y direction preserved");
    }

    // ========== 2) Vector2D.doIntersect edge cases ==========
    @Test
    @DisplayName("Vector2D.doIntersect: collinear overlap, endpoint touch, parallel no-hit")
    void vectorIntersectionEdgeCases() {
        Vector2D a = new Vector2D(new Point2D(0, 0), new Point2D(10, 0));
        Vector2D bOverlap = new Vector2D(new Point2D(5, 0), new Point2D(15, 0));
        assertTrue(a.doIntersect(bOverlap, 0), "Collinear overlapping segments must intersect");

        Vector2D bEndpoint = new Vector2D(new Point2D(10, 0), new Point2D(10, 10));
        assertTrue(a.doIntersect(bEndpoint, 0), "Touching at an endpoint counts as intersection");

        Vector2D v1 = new Vector2D(new Point2D(0, 0), new Point2D(0, 10));
        Vector2D v2 = new Vector2D(new Point2D(5, 0), new Point2D(5, 10));
        assertFalse(v1.doIntersect(v2, 1), "Parallel with gap should not intersect even with small offset");
    }

    // ========== 3) Vector2D.getFacingFromVector covers all facings ==========
    @Test
    @DisplayName("Vector2D.getFacingFromVector: RIGHT, LEFT, UP, DOWN, IDLE")
    void vectorFacingAllDirections() {
        assertEquals(Vector2D.VectorFacing.RIGHT,
                new Vector2D(new Point2D(0,0), new Point2D(5,0)).getFacingFromVector());
        assertEquals(Vector2D.VectorFacing.LEFT,
                new Vector2D(new Point2D(5,0), new Point2D(0,0)).getFacingFromVector());
        assertEquals(Vector2D.VectorFacing.DOWN,
                new Vector2D(new Point2D(0,0), new Point2D(0,7)).getFacingFromVector());
        assertEquals(Vector2D.VectorFacing.UP,
                new Vector2D(new Point2D(0,7), new Point2D(0,0)).getFacingFromVector());
        assertEquals(Vector2D.VectorFacing.IDLE,
                new Vector2D(new Point2D(2,2), new Point2D(2,2)).getFacingFromVector());
    }

    // ========== 4) MazeWorld singleton + axis-aligned segments ==========
    @Test
    @DisplayName("MazeWorld is singleton and segments are axis-aligned")
    void mazeWorldSingletonAndOrthogonalSegments() {
        GameMazeWorld w1 = GameMazeWorld.GetWorld(App.getBoardMaxX(), App.getBoardMaxY());
        GameMazeWorld w2 = GameMazeWorld.GetWorld(App.getBoardMaxX(), App.getBoardMaxY());
        assertSame(w1, w2, "GetWorld must return the same instance");

        assertFalse(w1.getMazeVectors().isEmpty(), "Maze should contain segments");
        for (Vector2D v : w1.getMazeVectors()) {
            double dx = v.getEnd().getX() - v.getStart().getX();
            double dy = v.getEnd().getY() - v.getStart().getY();
            assertTrue(dx == 0 || dy == 0, "Every segment should be horizontal or vertical: " + v);
        }
    }

    // ========== 5) GameController.dispose cancels/interrupts/unsubscribes ==========
    @Test
    @DisplayName("GameController.dispose cancels task, interrupts thread, unsubscribes WinArea")
    void gameControllerDisposeCancelsAndUnsubscribes() throws Exception {
        GameController gc = new GameController();

        // cancellable Task
        Task<Void> bgTask = new Task<>() {
            @Override protected Void call() throws Exception { Thread.sleep(1000); return null; }
        };

        // interruptible thread (not started; interrupt flag should still flip)
        Thread worker = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        });

        // set up a player & win area subscription to verify unsubscribe
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        WinArea winArea = new WinArea(null);
        player.addPositionSubscriber(winArea);
        assertEquals(1, player.getPositionSubscribers().size(), "Precondition: win area subscribed");

        // inject fields
        setPrivate(gc, "runComputerCharacters", bgTask);
        setPrivate(gc, "runComputerCharactersThread", worker);
        setPrivate(gc, "playerCharacter", player);
        setPrivate(gc, "winarea", winArea);

        // act
        gc.dispose();

        // assert
        assertTrue(bgTask.isCancelled(), "Background Task should be cancelled");
        assertTrue(worker.isInterrupted(), "Worker thread should be interrupted");
        assertTrue(player.getPositionSubscribers().isEmpty(), "WinArea should be unsubscribed on dispose()");
    }
}
