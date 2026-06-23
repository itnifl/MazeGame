package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SpriteAnimationUtilTest {

    @Test
    void frame0ReturnsOriginalPath() {
        assertEquals("/game/zombie1-right.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/game/zombie1-right.png", 0));
    }

    @Test
    void frame1ReplacesSingleDigit() {
        assertEquals("/game/zombie2-right.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/game/zombie1-right.png", 1));
    }

    @Test
    void frame2ReplacesSingleDigit() {
        assertEquals("/game/zombie3-right.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/game/zombie1-right.png", 2));
    }

    @Test
    void ghostFrameReplacesDigitInSimpleName() {
        assertEquals("/main/game/maze/ghost2.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/main/game/maze/ghost1.png", 1));
    }

    @Test
    void noDigitInFilenameReturnedUnchanged() {
        assertEquals("/game/pumpkinbomber.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/game/pumpkinbomber.png", 1));
    }

    @Test
    void directoryDigitsAreIgnored() {
        // Digit in directory component (/1/) must not be replaced — only filename
        assertEquals("/1/ghost2.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/1/ghost1.png", 1));
    }

    @Test
    void nullPathReturnsNull() {
        assertNull(SpriteAnimationUtil.deriveAnimationFramePath(null, 0));
    }

    @Test
    void blankPathReturnedUnchanged() {
        assertEquals("   ", SpriteAnimationUtil.deriveAnimationFramePath("   ", 0));
    }

    @Test
    void baseVariantDigitIsReplaced() {
        // zombie_angry_1 uses zombie2-right.png; frame-0 cycles back to zombie1-right.png
        assertEquals("/main/game/maze/zombie1-right.png",
                SpriteAnimationUtil.deriveAnimationFramePath("/main/game/maze/zombie2-right.png", 0));
    }
}
