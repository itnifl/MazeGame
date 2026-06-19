package main.game.maze.characters;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.PositionBounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCharacterTest {

    private Node mockCharacterGraphics;
    private ProgressBar mockHpBar;
    private PlayerCharacter player;

    @BeforeEach
    void setUp() {
        player = new PlayerCharacter(mockCharacterGraphics, 0, 0, mockHpBar);
    }

    @Test
    void testGetHitPoints() {
        assertEquals(100, player.getHitPoints());
    }

    @Test
    void testSetHitPoints() {
        player.setHitPoints(50);
        assertEquals(50, player.getHitPoints());
    }

    @Test
    void testSubtractHitPoints() {
        player.subtractHitPoints(20);
        assertEquals(80, player.getHitPoints());
    }

    @Test
    void testAddHitPoints() {
        player.addHitPoints(20);
        assertEquals(120, player.getHitPoints());
    }

    @Test
    void subtractHitPoints_toZero_callsDeathSubscriber() {
        AtomicBoolean notified = new AtomicBoolean(false);
        player.addDeathNotificationSubscriber(entity -> notified.set(true));

        player.subtractHitPoints(100); // drains full HP

        assertTrue(notified.get(),
                "Death subscriber must be called when hitPoints reach 0");
    }

    @Test
    void subtractHitPoints_partialDamage_doesNotCallDeathSubscriber() {
        AtomicBoolean notified = new AtomicBoolean(false);
        player.addDeathNotificationSubscriber(entity -> notified.set(true));

        player.subtractHitPoints(1); // only partial damage

        assertFalse(notified.get(),
                "Death subscriber must NOT be called while hitPoints remain positive");
    }

    @Test
    void addPositionSubscriber_addsToSubscriberList() {
        ICanSubscribeAndNotifyPosition stub = makeStubSubscriber();
        player.addPositionSubscriber(stub);

        assertTrue(player.getPositionSubscribers().contains(stub),
                "getPositionSubscribers must return subscribers added via addPositionSubscriber");
    }

    @Test
    void removePositionSubscriber_removesFromList() {
        ICanSubscribeAndNotifyPosition stub = makeStubSubscriber();
        player.addPositionSubscriber(stub);
        player.removePositionSubscriber(stub);

        assertFalse(player.getPositionSubscribers().contains(stub),
                "subscriber must not be present after removePositionSubscriber");
    }

    @Test
    void getPositionSubscribers_returnsEmptyListInitially() {
        assertTrue(player.getPositionSubscribers().isEmpty(),
                "position-subscriber list must be empty before any subscriber is added");
    }

    @Test
    void getMaxHitPoints_returnsDefaultPlayerMax() {
        assertEquals(100, player.getMaxHitPoints(),
                "default player config must set maxHitPoints to 100");
    }

    // Minimal stub so tests don't need a real character implementation.
    private static ICanSubscribeAndNotifyPosition makeStubSubscriber() {
        return new ICanSubscribeAndNotifyPosition() {
            public void doPositionEvaluation(PositionBounds b, ICanSubscribeAndNotifyPosition e) {}
            public void addPositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public void removePositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return List.of(); }
        };
    }
}
