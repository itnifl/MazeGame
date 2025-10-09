package main.game.maze;

import javafx.scene.Node;
import main.game.maze.characters.Character;
import main.game.maze.characters.interfaces.ISubscribeOnDirection;
import main.game.maze.interfaces.INotifyMovement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
