package main.game.maze;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for the enemy-spawning bug.
 *
 * <p>Root cause: {@link FxEnemyCoordinator} was constructed in
 * {@link GameController#GameController()} with literal {@code null} for {@code gameBoard}
 * and {@code root}.  These are {@code @FXML} fields that are only set by the
 * FXMLLoader when {@code initialize()} runs — after construction.  So when
 * {@code registerCharacter()} ran its {@code Platform.runLater()} lambda,
 * {@code gameBoard == null} and the character was silently dropped.</p>
 *
 * <p>Fix: the coordinator accepts {@code Supplier<Pane>} and
 * {@code Supplier<AnchorPane>} instead of direct references.  The supplier is
 * evaluated <em>inside</em> the {@code Platform.runLater()} lambda — by which
 * time {@code initialize()} has set the FXML fields.</p>
 */
class FxEnemyCoordinatorRegistrationTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(3, TimeUnit.SECONDS), "JavaFX toolkit must start within 3 s");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /** Flush the JavaFX application thread queue, then wait for it. */
    private static void flushFxThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        assertTrue(latch.await(3, TimeUnit.SECONDS), "FX queue flush timed out");
    }

    @SuppressWarnings("unchecked")
    private static List<IMovingComputerCharacter> registeredCharacters(FxEnemyCoordinator coordinator)
            throws Exception {
        Field f = FxEnemyCoordinator.class.getDeclaredField("allComputerCharacters");
        f.setAccessible(true);
        return (List<IMovingComputerCharacter>) f.get(coordinator);
    }

    private static IMovingComputerCharacter stubCharacter() {
        return new IMovingComputerCharacter() {
            @Override public boolean move(boolean force) { return false; }
            @Override public void changeDirection() {}
            @Override public void setDirection(Point2D direction) {}
        };
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    /**
     * Reproduces the original bug: when the gameBoard supplier returns null the
     * character must be silently dropped — the old code passed literal null and
     * this was the behaviour that caused enemies never to appear.
     */
    @Test
    void registerCharacterIsIgnoredWhenGameBoardSupplierReturnsNull() throws Exception {
        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                () -> null, () -> null,
                new FxGameWorldModel(),
                () -> null, () -> null, null);

        coordinator.registerCharacter(stubCharacter(), new Pane());
        flushFxThread(); // let the Platform.runLater lambda execute

        assertTrue(registeredCharacters(coordinator).isEmpty(),
                "Character must be silently dropped when gameBoard supplier returns null");
    }

    /**
     * Core regression: the supplier is evaluated lazily, not at construction time.
     * Simulates the real scenario where the Pane is null in the constructor but
     * non-null by the time registerCharacter() is called (after initialize()).
     */
    @Test
    void gameBoardSupplierIsEvaluatedLazilyNotAtConstructionTime() throws Exception {
        AtomicReference<Pane> boardRef = new AtomicReference<>(null);

        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                boardRef::get, () -> null,
                new FxGameWorldModel(),
                () -> null, () -> null, null);

        // Simulate initialize() setting the FXML field.
        boardRef.set(new Pane());

        IMovingComputerCharacter character = stubCharacter();
        coordinator.registerCharacter(character, new Pane());
        flushFxThread();

        assertTrue(registeredCharacters(coordinator).contains(character),
                "Character must be registered when supplier provides the board lazily");
    }

    /**
     * Happy-path: supplier immediately returns a valid Pane.
     */
    @Test
    void registerCharacterSucceedsWhenGameBoardSupplierReturnsValidPane() throws Exception {
        Pane board = new Pane();
        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                () -> board, () -> null,
                new FxGameWorldModel(),
                () -> null, () -> null, null);

        IMovingComputerCharacter character = stubCharacter();
        Node node = new Pane();
        coordinator.registerCharacter(character, node);
        flushFxThread();

        List<IMovingComputerCharacter> chars = registeredCharacters(coordinator);
        assertTrue(chars.contains(character),
                "Character must be in allComputerCharacters after successful registration");
        assertTrue(board.getChildren().contains(node),
                "Node must be added to the gameBoard Pane");
    }

    /**
     * Registering the same character twice must not add it twice (idempotent).
     */
    @Test
    void registerCharacterIsIdempotent() throws Exception {
        Pane board = new Pane();
        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                () -> board, () -> null,
                new FxGameWorldModel(),
                () -> null, () -> null, null);

        IMovingComputerCharacter character = stubCharacter();
        Node node = new Pane();
        coordinator.registerCharacter(character, node);
        coordinator.registerCharacter(character, node);
        flushFxThread();
        // second Platform.runLater may not have executed yet — flush again
        flushFxThread();

        long count = registeredCharacters(coordinator).stream()
                .filter(c -> c == character).count();
        assertEquals(1, count, "Duplicate registration must be silently ignored");
    }
}
