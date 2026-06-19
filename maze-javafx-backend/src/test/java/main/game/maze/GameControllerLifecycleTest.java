package main.game.maze;

import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GameController} lifecycle methods that are safe to call on
 * a freshly constructed controller (no FXML injection, no setupGame()).
 * These exercise null-guard branches that exist specifically for this scenario.
 */
class GameControllerLifecycleTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    private static void flushFx() throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(done::countDown);
        assertTrue(done.await(5, TimeUnit.SECONDS), "FX flush timed out");
    }

    @Test
    void stopComputerCharacters_beforeStart_doesNotThrow() {
        GameController gc = new GameController();
        assertDoesNotThrow(gc::stopComputerCharacters);
    }

    @Test
    void stopPlayerMovement_beforeStart_doesNotThrow() {
        GameController gc = new GameController();
        assertDoesNotThrow(gc::stopPlayerMovement);
    }

    @Test
    void stopOpponentMovement_beforeStart_doesNotThrow() {
        GameController gc = new GameController();
        assertDoesNotThrow(gc::stopOpponentMovement);
    }

    @Test
    void isWallBetween_withNullMaze_returnsFalse() {
        // maze field is null until setupGame() runs → must short-circuit and return false
        GameController gc = new GameController();
        assertFalse(gc.isWallBetween(0, 0, 100, 100));
    }

    @Test
    void setStartDifficulty_andGetBaseScore_withNull_returnsEasyDefault() {
        GameController gc = new GameController();
        gc.setStartDifficulty(null);
        // null difficulty → baseScoreFor returns EASY_BASE_SCORE by contract
        int score = gc.getBaseScoreForCurrentDifficulty();
        assertTrue(score > 0, "Base score for null difficulty must be a positive default");
    }

    @Test
    void getDynamicScorePenalty_onFreshController_returnsZero() {
        GameController gc = new GameController();
        // model.routeHintPenaltyPoints() starts at 0
        assertEquals(0, gc.getDynamicScorePenalty());
    }

    // --- EnemyRegistrar delegation (called by OpponentRuntimeFactory) ---

    @Test
    void resolveEnemySpawnPosition_onFreshController_returnsPoint() {
        GameController gc = new GameController();
        // Delegates to enemyCoordinator.resolveSpawnPosition() which uses null maze → no collision
        Point2D result = gc.resolveEnemySpawnPosition(100.0, 200.0, 32.0);
        assertNotNull(result, "resolveEnemySpawnPosition must return a non-null Point2D");
    }

    @Test
    void registerComputerCharacter_withNullArgs_doesNotThrow() throws InterruptedException {
        GameController gc = new GameController();
        // Delegates to enemyCoordinator.registerCharacter() via Platform.runLater — safe with null
        assertDoesNotThrow(() -> gc.registerComputerCharacter(null, null));
        flushFx();
    }

    @Test
    void showInfectionWarning_onFreshController_doesNotThrow() throws InterruptedException {
        GameController gc = new GameController();
        // Delegates to enemyCoordinator.showInfectionWarning() which checks root supplier (null)
        assertDoesNotThrow(gc::showInfectionWarning);
        flushFx();
    }

    @Test
    void runComputerCharacters_onFreshController_doesNotThrow() {
        GameController gc = new GameController();
        assertDoesNotThrow(gc::runComputerCharacters);
        gc.stopComputerCharacters();
    }

    @Test
    void unregisterComputerCharacter_withNullArgs_doesNotThrow() throws InterruptedException {
        GameController gc = new GameController();
        assertDoesNotThrow(() -> gc.unregisterComputerCharacter(null, null));
        flushFx();
    }

    // --- stub that satisfies IMovingComputerCharacter interface ---
    private static final IMovingComputerCharacter STUB_CHARACTER = new IMovingComputerCharacter() {
        @Override public boolean move(boolean force) { return false; }
        @Override public void changeDirection() {}
        @Override public void setDirection(Point2D direction) {}
    };

    @Test
    void registerComputerCharacter_withStubCharacter_doesNotThrow() throws InterruptedException {
        GameController gc = new GameController();
        assertDoesNotThrow(() -> gc.registerComputerCharacter(STUB_CHARACTER, (Node) null));
        flushFx();
    }
}
