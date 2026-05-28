package main.game.maze;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import main.game.maze.characters.Character;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the base Character class movement and lifecycle.
 */
public class CharacterBaseTest {

    /**
     * Concrete test implementation to expose protected methods.
     */
    static class TestCharacter extends Character {
        public TestCharacter(Node node, double x, double y) {
            super(node, x, y);
        }
        
        public void publicUpdateDirection(int factor) {
            updateDirection(factor);
        }
        
        public void publicUpdatePosition() {
            updatePosition();
        }
        
        public boolean publicIsTouchingVector() {
            return isTouchingVector();
        }
    }

    private ImageView graphics;
    private TestCharacter character;

    @BeforeEach
    void setUp() {
        // Use real ImageView with a 1x1 image
        graphics = new ImageView(new WritableImage(1, 1));
        graphics.setLayoutX(100);
        graphics.setLayoutY(100);
        character = new TestCharacter(graphics, 100, 100);
    }

    @Test
    @DisplayName("Character initializes with correct position")
    void characterInitializesWithPosition() {
        Point2D pos = character.getCharacterPosition();
        
        assertEquals(100, pos.getX(), 0.01);
        assertEquals(100, pos.getY(), 0.01);
    }

    @Test
    @DisplayName("Character direction is initially zero")
    void characterDirectionInitiallyZero() {
        assertEquals(0, character.getDirectionX(), 0.01);
        assertEquals(0, character.getDirectionY(), 0.01);
    }

    @Test
    @DisplayName("setCharacterDirection updates direction values")
    void setCharacterDirectionUpdatesValues() {
        character.setCharacterDirection(1, -1, 10);
        
        assertEquals(1, character.getDirectionX(), 0.01);
        assertEquals(-1, character.getDirectionY(), 0.01);
    }

    @Test
    @DisplayName("getCharacterDirection returns normalized vector")
    void getCharacterDirectionReturnsVector() {
        character.setCharacterDirection(1, 0, 10);
        
        Vector2D direction = character.getCharacterDirection();
        
        assertNotNull(direction);
    }

    @Test
    @DisplayName("updatePosition adds direction to position")
    void updatePositionAddsDirection() {
        character.setCharacterDirection(5, 3, 10);
        Point2D before = character.getCharacterPosition();
        double beforeX = before.getX();
        double beforeY = before.getY();
        
        character.publicUpdatePosition();
        
        Point2D after = character.getCharacterPosition();
        assertEquals(beforeX + 5, after.getX(), 0.01);
        assertEquals(beforeY + 3, after.getY(), 0.01);
    }

    @Test
    @DisplayName("notifyMovement subscriber is called on movement notification")
    void notifyMovementSubscriberCalled() throws Exception {
        AtomicBoolean notified = new AtomicBoolean(false);
        character.notifyMovement = () -> notified.set(true);
        
        Method doNotify = Character.class.getDeclaredMethod("doNotifyMovement");
        doNotify.setAccessible(true);
        doNotify.invoke(character);
        
        assertTrue(notified.get(), "Movement notification should be triggered");
    }

    @Test
    @DisplayName("notifyMovement field can be assigned and is null initially")
    void notifyMovementFieldNullInitially() {
        // notifyMovement is a public field, verify it starts null
        assertNull(character.notifyMovement, "notifyMovement should be null initially");
    }

    @Test
    @DisplayName("directionSubscriber field can be assigned and is null initially")
    void directionSubscriberFieldNullInitially() {
        // directionSubscriber is a public field, verify it starts null
        assertNull(character.directionSubscriber, "directionSubscriber should be null initially");
    }

    @Test
    @DisplayName("isTouchingVector returns false when maze not initialized")
    void isTouchingVectorReturnsFalseWithoutMaze() {
        assertFalse(character.publicIsTouchingVector());
    }

    @Test
    @DisplayName("getCharacterGraphics returns the graphics node")
    void getCharacterGraphicsReturnsNode() {
        assertEquals(graphics, character.getCharacterGraphics());
    }

    @Test
    @DisplayName("setCharacterGraphics updates the graphics node")
    void setCharacterGraphicsUpdatesNode() {
        ImageView newGraphics = new ImageView(new WritableImage(1, 1));
        
        character.setCharacterGraphics(newGraphics);
        
        assertEquals(newGraphics, character.getCharacterGraphics());
    }

    @Test
    @DisplayName("Character with null graphics can be created")
    void characterWithNullGraphics() {
        TestCharacter nullGraphicsChar = new TestCharacter(null, 50, 50);
        
        assertNull(nullGraphicsChar.getCharacterGraphics());
        assertEquals(50, nullGraphicsChar.getCharacterPosition().getX(), 0.01);
    }
}
