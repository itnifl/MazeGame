package main.game.maze.libgdx.flow;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.game.maze.libgdx.helper.GdxGameStartFlowApplySupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GdxGameStartFlowApplySupportTest {

    // ensureViewportInitialized: returns the same viewport when one is already set.
    @Test
    void ensureViewportInitialized_existingViewport_returnsTheSameInstance() {
        Viewport existing = new ScreenViewport();
        Viewport result = GdxGameStartFlowApplySupport.ensureViewportInitialized(
                existing, new OrthographicCamera());

        assertSame(existing, result, "Must return the existing viewport unchanged");
    }

    // ensureViewportInitialized: creates a new ScreenViewport when null is passed.
    @Test
    void ensureViewportInitialized_nullViewport_createsNewViewport() {
        Viewport result = GdxGameStartFlowApplySupport.ensureViewportInitialized(
                null, new OrthographicCamera());

        assertNotNull(result, "Must create a non-null viewport when none is provided");
        assertInstanceOf(ScreenViewport.class, result);
    }

    // startedDifficultyText: returns a non-blank string containing the mode label.
    @Test
    void startedDifficultyText_nullDifficulty_containsDefaultLabel() {
        String text = GdxGameStartFlowApplySupport.startedDifficultyText(null);

        assertNotNull(text);
        assertFalse(text.isBlank(), "startedDifficultyText must return non-blank for null difficulty");
        assertTrue(text.contains("Default"),
                "startedDifficultyText with null difficulty must contain 'Default'");
    }

    // startedDifficultyText: always starts with "Started".
    @Test
    void startedDifficultyText_alwaysStartsWithStarted() {
        String text = GdxGameStartFlowApplySupport.startedDifficultyText(null);
        assertTrue(text.startsWith("Started"), "Text must begin with 'Started'");
    }
}
