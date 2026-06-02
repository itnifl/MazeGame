package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCharacterDisposeTest {

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

    @Test
    void disposeClearsEffectsAndEmptiesSubscribers() throws Exception {
        Rectangle sprite = new Rectangle(10, 10, 20, 20);
        sprite.setEffect(new ColorAdjust()); // ensure there is an effect to clear

        ProgressBar hp = new ProgressBar();
        PlayerCharacter pc = new PlayerCharacter(sprite, 0, 0, hp);

        // Add a dummy subscriber to verify it gets cleared
        ICanSubscribeAndNotifyPosition dummy = new ICanSubscribeAndNotifyPosition() {
            public void doPositionEvaluation(javafx.geometry.Bounds b, ICanSubscribeAndNotifyPosition e) {}
            public void addPositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public void removePositionSubscriber(ICanSubscribeAndNotifyPosition e) {}
            public java.util.List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() { return java.util.Collections.emptyList(); }
        };
        pc.addPositionSubscriber(dummy);
        assertEquals(1, pc.getPositionSubscribers().size(), "Precondition: one subscriber");

        pc.dispose();

        // PlayerCharacter.dispose may clear effect via runLater; allow a tick
        Thread.sleep(100);

        assertTrue(pc.getPositionSubscribers().isEmpty(), "Subscribers should be cleared on dispose()");
        assertNull(sprite.getEffect(), "Sprite visual effect should be cleared on dispose()");
    }
}
