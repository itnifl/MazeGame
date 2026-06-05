package main.game.maze.areas;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
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

    /**
     * Stub implementation for position subscriber.
     */
    private static class StubPositionSubscriber implements ICanSubscribeAndNotifyPosition {
        @Override
        public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {}
        
        @Override
        public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {}
        
        @Override
        public void removePositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {}
        
        @Override
        public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return List.of(); }
    }

    @BeforeEach
    void setUp() {
        // Use Rectangle so we can set bounds
        areaGraphics = new Rectangle(50, 50, 100, 100);
        winArea = new WinArea(areaGraphics);
    }

    @Test
    @DisplayName("addPositionSubscriber adds subscriber to list")
    void addPositionSubscriberAddsToList() {
        ICanSubscribeAndNotifyPosition subscriber = new StubPositionSubscriber();
        
        winArea.addPositionSubscriber(subscriber);
        
        assertTrue(winArea.getPositionSubscribers().contains(subscriber),
            "Subscriber should be added to the list");
    }

    @Test
    @DisplayName("getPositionSubscribers returns all added subscribers")
    void getPositionSubscribersReturnsAll() {
        ICanSubscribeAndNotifyPosition sub1 = new StubPositionSubscriber();
        ICanSubscribeAndNotifyPosition sub2 = new StubPositionSubscriber();
        
        winArea.addPositionSubscriber(sub1);
        winArea.addPositionSubscriber(sub2);
        
        assertEquals(2, winArea.getPositionSubscribers().size(),
            "Should return all added subscribers");
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
        Bounds intersectingBounds = new BoundingBox(80, 80, 20, 20);
        
        // Should not throw even without action set because non-player is ignored
        assertDoesNotThrow(() -> winArea.doPositionEvaluation(intersectingBounds, nonPlayer));
    }

    @Test
    @DisplayName("getPositionSubscribers returns empty list initially")
    void getPositionSubscribersEmptyInitially() {
        assertTrue(winArea.getPositionSubscribers().isEmpty(),
            "Subscribers list should be empty initially");
    }

    @Test
    @DisplayName("multiple subscribers can be added")
    void multipleSubscribersCanBeAdded() {
        for (int i = 0; i < 5; i++) {
            winArea.addPositionSubscriber(new StubPositionSubscriber());
        }
        
        assertEquals(5, winArea.getPositionSubscribers().size());
    }
}


