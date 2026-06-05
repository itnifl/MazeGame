package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.Character;
import main.game.maze.characters.interfaces.ISubscribeOnDirection;
import main.game.maze.interfaces.INotifyMovement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import main.game.maze.mazeworld.Vector2D;

public class CharacterDisposeTest {

    private static class TestCharacter extends Character {
        public TestCharacter(Node node, double x, double y) { super(node, x, y); }
        public void callDispose() { super.dispose(); }
    }

    @Test
    void disposeClearsObserversAndStateEvenWithoutGraphics() {
        TestCharacter c = new TestCharacter(null, 10, 10);
        c.notifyMovement = new INotifyMovement() { public void doNotifyCharacterMovement() {} };
        c.directionSubscriber = new ISubscribeOnDirection() { public void notifyCurrentDirection(Vector2D.VectorFacing d) {} };

        c.callDispose();

        assertNull(c.notifyMovement, "notifyMovement should be nulled");
        assertNull(c.directionSubscriber, "directionSubscriber should be nulled");
        assertNull(c.getCharacterGraphics(), "graphics should be nulled");
    }    

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            // FX already initialized — fine
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    void disposeRemovesGraphicsFromParentAndNullsReferences() throws Exception {
        Pane parent = new Pane();
        Rectangle sprite = new Rectangle(10, 10, 20, 20);
        sprite.setEffect(new ColorAdjust());
        parent.getChildren().add(sprite);

        TestCharacter c = new TestCharacter(sprite, 5, 5);
        // set public observer fields so we can verify they’re nulled
        c.notifyMovement = new INotifyMovement() { public void doNotifyCharacterMovement() {} };
        c.directionSubscriber = new ISubscribeOnDirection() { public void notifyCurrentDirection(Vector2D.VectorFacing d) {} };

        c.callDispose();

        // Character.dispose() may remove node via Platform.runLater — wait briefly.
        Thread.sleep(100); // lightweight wait; the queued task is tiny

        assertNull(c.notifyMovement, "notifyMovement must be nulled");
        assertNull(c.directionSubscriber, "directionSubscriber must be nulled");
        assertNull(c.getCharacterGraphics(), "characterGraphics must be nulled");
        assertFalse(parent.getChildren().contains(sprite), "Sprite must be removed from its parent");
        assertNull(sprite.getEffect(), "Any lingering visual effect should be cleared");
    }
}


