package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.Set;
import javafx.scene.input.KeyCode;
import main.game.maze.common.input.InputFrame;
import org.junit.jupiter.api.Test;

class JavaFxInputSnapshotReaderTest {

        @Test
    void readCopiesHeldAndEdgeKeysAndMouseState() {
        JavaFxInputSnapshotReader reader = new JavaFxInputSnapshotReader();
        Set<KeyCode> held = EnumSet.of(KeyCode.UP, KeyCode.LEFT);
        // EDGE keys are now computed internally by EdgeKeyTracker, the second parameter is legacy/ignored
        InputFrame<KeyCode> frame = reader.read(held, EnumSet.noneOf(KeyCode.class), 12.5, 42.25, true);

        assertTrue(frame.isHeld(KeyCode.UP));
        assertTrue(frame.isHeld(KeyCode.LEFT));
        assertTrue(frame.isEdge(KeyCode.UP));
        assertTrue(frame.isEdge(KeyCode.LEFT));
        assertEquals(12.5, frame.mouseX(), 0.0001);
        assertEquals(42.25, frame.mouseY(), 0.0001);
        assertTrue(frame.leftMouseClicked());
    }

    @Test
    void readClearsSourceEdgeSetAfterSnapshot() {
        JavaFxInputSnapshotReader reader = new JavaFxInputSnapshotReader();
        Set<KeyCode> held = EnumSet.of(KeyCode.ESCAPE);
        Set<KeyCode> edge = EnumSet.of(KeyCode.ESCAPE);

        InputFrame<KeyCode> frame = reader.read(held, edge, 0d, 0d, false);

        assertTrue(frame.isEdge(KeyCode.ESCAPE));
    }

    @Test
    void frameIsImmutableCopyOfSourceSets() {
        JavaFxInputSnapshotReader reader = new JavaFxInputSnapshotReader();
        Set<KeyCode> held = EnumSet.of(KeyCode.RIGHT);

        InputFrame<KeyCode> frame = reader.read(held, EnumSet.noneOf(KeyCode.class), 1d, 2d, false);
        held.clear();

        assertTrue(frame.isHeld(KeyCode.RIGHT));
        assertTrue(frame.isEdge(KeyCode.RIGHT));
        assertFalse(frame.isHeld(KeyCode.LEFT));
    }

}
