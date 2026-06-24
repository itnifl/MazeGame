package main.game.maze.characters;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ComputerCharacterAnimationTest {

    @BeforeAll
    static void startFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // Already started
        }
    }

    private static Zombie zombieWithFrameCount(int frameCount) {
        Zombie z = OpponentsFactory.eINSTANCE.createZombie();
        z.setHealth(10);
        z.setSpeed(2.0);
        z.setAttackDamage(3);
        z.setThreatLevel(50.0);
        z.setAnimationFrameCount(frameCount);
        return z;
    }

    private static Timeline walkAnimation(ComputerCharacter cc) throws ReflectiveOperationException {
        Field f = ComputerCharacter.class.getDeclaredField("walkAnimation");
        f.setAccessible(true);
        return (Timeline) f.get(cc);
    }

    /** ANIMATION_FRAME_DURATION_MS constant is exactly 250 ms. */
    @Test
    void animationFrameDurationConstant_is250ms() throws ReflectiveOperationException {
        Field f = ComputerCharacter.class.getDeclaredField("ANIMATION_FRAME_DURATION_MS");
        f.setAccessible(true);
        double value = (double) f.get(null);
        assertEquals(250.0, value, 0.001, "Frame duration must be 250 ms");
    }

    /** With animationFrameCount == 1 (default), no Timeline is created. */
    @Test
    void singleFrame_walkAnimationIsNull() throws ReflectiveOperationException {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieWithFrameCount(1));
        assertNull(walkAnimation(zombie), "walkAnimation must be null when frameCount == 1");
    }

    /** With animationFrameCount > 1, a Timeline is created and set to INDEFINITE. */
    @Test
    void multiFrame_walkAnimationIsIndefinite() throws ReflectiveOperationException {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieWithFrameCount(3));
        Timeline tl = walkAnimation(zombie);
        assertNotNull(tl, "walkAnimation must not be null when frameCount > 1");
        assertEquals(Animation.INDEFINITE, tl.getCycleCount(),
                "walkAnimation cycle count must be INDEFINITE");
    }

    /** dispose() stops the Timeline without throwing. */
    @Test
    void dispose_stopsTimelineWithoutThrowing() throws ReflectiveOperationException {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieWithFrameCount(3));
        Timeline tl = walkAnimation(zombie);
        assertNotNull(tl, "pre-condition: Timeline must exist");
        assertDoesNotThrow(zombie::dispose, "dispose() must not throw");
        assertEquals(Animation.Status.STOPPED, tl.getStatus(),
                "Timeline must be stopped after dispose()");
    }

    /** animationFrames map is populated per direction when frameCount > 1. */
    @Test
    void multiFrame_animationFramesPopulatedPerDirection() throws ReflectiveOperationException {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieWithFrameCount(3));
        Field f = ComputerCharacter.class.getDeclaredField("animationFrames");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        var frames = (java.util.Map<?, java.util.List<?>>) f.get(zombie);
        assertNotNull(frames, "animationFrames must be populated when frameCount > 1");
        assertFalse(frames.isEmpty(), "animationFrames must have at least one direction entry");
        frames.values().forEach(list ->
            assertFalse(list.isEmpty(), "Each direction must have at least one frame image"));
    }

    /** animationFrames is null when frameCount == 1 (static image path). */
    @Test
    void singleFrame_animationFramesIsNull() throws ReflectiveOperationException {
        ImageView gfx = new ImageView();
        ZombieCharacter zombie = new ZombieCharacter(gfx, 0, 0, zombieWithFrameCount(1));
        Field f = ComputerCharacter.class.getDeclaredField("animationFrames");
        f.setAccessible(true);
        assertNull(f.get(zombie), "animationFrames must be null when frameCount == 1");
    }
}
