package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.libgdx.model.GameWorldModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX death sequence: respawn while lives are left, game over on the last life")
class GdxDeathSequenceRespawnTest {

    private static final float DELAY = 3f;

    @Test
    @DisplayName("lives left: after the death delay the player respawns and the game goes on")
    void respawnsAfterTheDelay() {
        GameWorldModel world = new GameWorldModel();
        AtomicInteger respawns = new AtomicInteger();
        AtomicInteger gameOvers = new AtomicInteger();

        GdxGameUpdateFlowSupport.applyDeathSequence(true, world, 1f, DELAY,
                () -> { respawns.incrementAndGet(); return true; }, gameOvers::incrementAndGet, () -> { });
        assertEquals(0, respawns.get(), "the dead player is shown first");
        GdxGameUpdateFlowSupport.applyDeathSequence(true, world, 2.5f, DELAY,
                () -> { respawns.incrementAndGet(); return true; }, gameOvers::incrementAndGet, () -> { });

        assertEquals(1, respawns.get());
        assertEquals(0, gameOvers.get(), "no game over while lives are left");
        assertFalse(world.deathSequenceStarted(), "ready for the next death");
    }

    @Test
    @DisplayName("last life: game over after the delay, with the game-over music once")
    void gameOverOnTheLastLife() {
        GameWorldModel world = new GameWorldModel();
        AtomicInteger gameOvers = new AtomicInteger();
        AtomicInteger music = new AtomicInteger();

        GdxGameUpdateFlowSupport.applyDeathSequence(true, world, DELAY + 0.1f, DELAY,
                () -> false, gameOvers::incrementAndGet, music::incrementAndGet);
        GdxGameUpdateFlowSupport.applyDeathSequence(true, world, 0.1f, DELAY,
                () -> false, gameOvers::incrementAndGet, music::incrementAndGet);

        assertTrue(gameOvers.get() >= 1);
        assertEquals(1, music.get());
    }

    @Test
    @DisplayName("alive: nothing happens")
    void aliveDoesNothing() {
        GameWorldModel world = new GameWorldModel();
        GdxGameUpdateFlowSupport.applyDeathSequence(false, world, 10f, DELAY,
                () -> { throw new AssertionError("no respawn"); }, () -> { throw new AssertionError("no game over"); },
                () -> { });
        assertFalse(world.deathSequenceStarted());
    }
}
