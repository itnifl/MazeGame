package main.game.maze.areas;

import javafx.geometry.BoundingBox;
import javafx.scene.shape.Rectangle;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.FxPositionBounds;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.PositionBounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WinArea position evaluation and subscription management.
 */
public class WinAreaTest {

    private Rectangle areaGraphics;
    private WinArea winArea;

    private static class StubPositionSubscriber implements ICanSubscribeAndNotifyPosition {
        @Override
        public void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {}

        @Override
        public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {}

        @Override
        public void removePositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {}

        @Override
        public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return List.of(); }
    }

    @BeforeEach
    void setUp() {
        areaGraphics = new Rectangle(50, 50, 100, 100);
        winArea = new WinArea(areaGraphics);
    }

    @Test
    @DisplayName("addPositionSubscriber adds subscriber to list")
    void addPositionSubscriberAddsToList() {
        ICanSubscribeAndNotifyPosition subscriber = new StubPositionSubscriber();
        winArea.addPositionSubscriber(subscriber);
        assertTrue(winArea.getPositionSubscribers().contains(subscriber));
    }

    @Test
    @DisplayName("getPositionSubscribers returns all added subscribers")
    void getPositionSubscribersReturnsAll() {
        winArea.addPositionSubscriber(new StubPositionSubscriber());
        winArea.addPositionSubscriber(new StubPositionSubscriber());
        assertEquals(2, winArea.getPositionSubscribers().size());
    }

    @Test
    @DisplayName("WinGame throws exception when action not set")
    void winGameThrowsWhenActionNotSet() {
        Exception exception = assertThrows(Exception.class, () -> winArea.WinGame());
        assertEquals("WinGameAction is not defined", exception.getMessage());
    }

    @Test
    @DisplayName("doPositionEvaluation ignores non-player entities")
    void doPositionEvaluationIgnoresNonPlayer() {
        ICanSubscribeAndNotifyPosition nonPlayer = new StubPositionSubscriber();
        PositionBounds intersectingBounds = new FxPositionBounds(new BoundingBox(80, 80, 20, 20));

        // Should not throw even without action set because non-player is ignored
        assertDoesNotThrow(() -> winArea.doPositionEvaluation(intersectingBounds, nonPlayer));
    }

    @Test
    @DisplayName("getPositionSubscribers returns empty list initially")
    void getPositionSubscribersEmptyInitially() {
        assertTrue(winArea.getPositionSubscribers().isEmpty());
    }

    @Test
    @DisplayName("multiple subscribers can be added")
    void multipleSubscribersCanBeAdded() {
        for (int i = 0; i < 5; i++) {
            winArea.addPositionSubscriber(new StubPositionSubscriber());
        }
        assertEquals(5, winArea.getPositionSubscribers().size());
    }

    @Test
    @DisplayName("removePositionSubscriber removes the subscriber from the list")
    void removePositionSubscriber_removesSubscriberFromList() {
        ICanSubscribeAndNotifyPosition subscriber = new StubPositionSubscriber();
        winArea.addPositionSubscriber(subscriber);
        assertFalse(winArea.getPositionSubscribers().isEmpty(), "subscriber must be present after add");

        winArea.removePositionSubscriber(subscriber);

        assertFalse(winArea.getPositionSubscribers().contains(subscriber),
                "subscriber must not be present after remove");
    }

    @Test
    @DisplayName("remove a non-existent subscriber is a no-op")
    void removeNonExistentSubscriber_isNoOp() {
        ICanSubscribeAndNotifyPosition phantom = new StubPositionSubscriber();
        assertDoesNotThrow(() -> winArea.removePositionSubscriber(phantom),
                "removing a subscriber that was never added must not throw");
        assertTrue(winArea.getPositionSubscribers().isEmpty());
    }
}
