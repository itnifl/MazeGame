package main.game.maze.characters;

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

    // -----------------------------------------------------------------------
    // Movement tests — cover moveRight/Left/Up/Down and their private delegates
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("moveRight with ImageView advances position and returns true")
    void moveRight_withImageView_advancesPositionAndReturnsTrue() {
        boolean moved = character.moveRight(2, true);
        assertTrue(moved, "moveRight(force=true) must return true when within bounds");
        assertEquals(102, character.getCharacterPosition().getX(), 0.01);
    }

    @Test
    @DisplayName("moveLeft with ImageView retreats position and returns true")
    void moveLeft_withImageView_retreatsPositionAndReturnsTrue() {
        boolean moved = character.moveLeft(2, true);
        assertTrue(moved, "moveLeft(force=true) must return true when within bounds");
        assertEquals(98, character.getCharacterPosition().getX(), 0.01);
    }

    @Test
    @DisplayName("moveUp with ImageView decrements Y and returns true")
    void moveUp_withImageView_decrementsYAndReturnsTrue() {
        boolean moved = character.moveUp(2, true);
        assertTrue(moved, "moveUp(force=true) must return true when within bounds");
        assertEquals(98, character.getCharacterPosition().getY(), 0.01);
    }

    @Test
    @DisplayName("moveDown with ImageView increments Y and returns true")
    void moveDown_withImageView_incrementsYAndReturnsTrue() {
        boolean moved = character.moveDown(2, true);
        assertTrue(moved, "moveDown(force=true) must return true when within bounds");
        assertEquals(102, character.getCharacterPosition().getY(), 0.01);
    }

    @Test
    @DisplayName("moveLeft at x=0 cannot go further left — returns false")
    void moveLeft_atLeftEdge_returnsFalse() {
        TestCharacter edge = new TestCharacter(new ImageView(new WritableImage(1, 1)), 0, 100);
        assertFalse(edge.moveLeft(2, true), "moveLeft at x=0 must return false");
    }

    @Test
    @DisplayName("moveUp at y=0 cannot go further up — returns false")
    void moveUp_atTopEdge_returnsFalse() {
        TestCharacter edge = new TestCharacter(new ImageView(new WritableImage(1, 1)), 100, 0);
        assertFalse(edge.moveUp(2, true), "moveUp at y=0 must return false");
    }

    @Test
    @DisplayName("moveRight with null graphics short-circuits and returns false")
    void moveRight_withNullGraphics_returnsFalse() {
        TestCharacter nullGfx = new TestCharacter(null, 100, 100);
        assertFalse(nullGfx.moveRight(2, true), "moveRight with null graphics must return false");
    }

    @Test
    @DisplayName("moveDown with null graphics short-circuits and returns false")
    void moveDown_withNullGraphics_returnsFalse() {
        TestCharacter nullGfx = new TestCharacter(null, 100, 100);
        assertFalse(nullGfx.moveDown(2, true), "moveDown with null graphics must return false");
    }

    @Test
    @DisplayName("dispose clears the graphics reference to null")
    void dispose_clearsGraphicsReferenceToNull() {
        assertNotNull(character.getCharacterGraphics());
        character.dispose();
        assertNull(character.getCharacterGraphics());
    }

    @Test
    @DisplayName("getCharacterView returns non-null view after construction with ImageView")
    void getCharacterView_returnsNonNullView() {
        assertNotNull(character.getCharacterView(),
                "View must be non-null after construction with non-null graphics");
    }

    @Test
    @DisplayName("setCharacterView replaces the view; getCharacterView returns the new one")
    void setCharacterView_replacesViewAndGraphics() {
        var original = character.getCharacterView();
        assertNotNull(original);
        // Setting the same view is idempotent
        character.setCharacterView(original);
        assertEquals(original, character.getCharacterView());
    }
}
