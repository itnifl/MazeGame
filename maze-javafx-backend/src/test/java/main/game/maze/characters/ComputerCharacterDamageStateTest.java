package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import main.game.maze.common.graphics.DamageState;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ComputerCharacterDamageStateTest {

    @BeforeAll
    static void initFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // Toolkit already started
        }
    }

    private final List<ComputerCharacter> created = new ArrayList<>();

    @AfterEach
    void disposeCharacters() {
        onFx(() -> created.forEach(ComputerCharacter::dispose));
        created.clear();
    }

    private <T extends ComputerCharacter> T track(T character) {
        created.add(character);
        return character;
    }

    /** Runs {@code action} on the FX thread, waits for it, and rethrows any failure. */
    private static void onFx(Runnable action) {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                latch.countDown();
            }
        });
        try {
            assertTrue(latch.await(5, TimeUnit.SECONDS), "FX action timed out");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted waiting for FX action");
        }
        if (failure.get() instanceof AssertionError ae) {
            throw ae;
        }
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }
    }

    private static PumpkinBomber createBomberModel(boolean damageStatesEnabled) {
        PumpkinBomber model = OpponentsFactory.eINSTANCE.createPumpkinBomber();
        model.setHealth(100);
        model.setSpeed(1.0);
        model.setImageBase("/main/game/maze/pumpkinbomber.png");
        model.setImageTurnLeft("/main/game/maze/pumpkinbomber.png");
        model.setImageTurnRight("/main/game/maze/pumpkinbomber.png");
        model.setImageTurnUp("/main/game/maze/pumpkinbomber.png");
        model.setImageTurnDown("/main/game/maze/pumpkinbomber.png");
        model.setDamageStatesEnabled(damageStatesEnabled);
        return model;
    }

    /** True when {@code a} is the horizontal mirror of {@code b} and not identical to it. */
    private static boolean isMirrorOf(Image a, Image b) {
        int w = (int) b.getWidth();
        int h = (int) b.getHeight();
        if ((int) a.getWidth() != w || (int) a.getHeight() != h || samePixels(a, b)) {
            return false;
        }
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (a.getPixelReader().getArgb(x, y) != b.getPixelReader().getArgb(w - 1 - x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean samePixels(Image a, Image b) {
        int w = (int) b.getWidth();
        int h = (int) b.getHeight();
        if ((int) a.getWidth() != w || (int) a.getHeight() != h) {
            return false;
        }
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (a.getPixelReader().getArgb(x, y) != b.getPixelReader().getArgb(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void waitForFxEvents() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        try {
            assertTrue(latch.await(5, TimeUnit.SECONDS), "FX event processing timed out");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted waiting for FX events");
        }
    }

    private static Zombie createZombieModel(boolean damageStatesEnabled, int frameCount) {
        Zombie z = OpponentsFactory.eINSTANCE.createZombie();
        z.setHealth(100);
        z.setSpeed(1.0);
        z.setImageBase("/main/game/maze/zombie1.png");
        z.setImageTurnLeft("/main/game/maze/zombie1-left.png");
        z.setImageTurnRight("/main/game/maze/zombie1-right.png");
        z.setImageTurnUp("/main/game/maze/zombie1-up.png");
        z.setImageTurnDown("/main/game/maze/zombie1-down.png");
        z.setAnimationFrameCount(frameCount);
        z.setDamageStatesEnabled(damageStatesEnabled);
        return z;
    }

    @Test
    void damageStatesDisabled_hpChangeDoesNotChangeHealthyImage() {
        Zombie model = createZombieModel(false, 1);
        ImageView view = new ImageView();
        ComputerCharacter character = track(new ComputerCharacter(view, model, 0, 0, 1.0));

        character.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();
        Image healthyImage = view.getImage();
        assertNotNull(healthyImage, "Healthy image must be set initially");

        // HP drops to DAMAGED, BLEEDING, TIRED
        character.onHpChanged(60, 100);
        waitForFxEvents();
        assertSame(healthyImage, view.getImage(), "Image must remain healthy when damageStatesEnabled=false");
        assertEquals(DamageState.DAMAGED, character.getDamageState());

        character.onHpChanged(30, 100);
        waitForFxEvents();
        assertSame(healthyImage, view.getImage(), "Image must remain healthy when damageStatesEnabled=false");

        character.onHpChanged(10, 100);
        waitForFxEvents();
        assertSame(healthyImage, view.getImage(), "Image must remain healthy when damageStatesEnabled=false");
    }

    @Test
    void damageStatesEnabled_missingAssets_fallsBackToHealthySprite_withoutWarning() {
        // zombieGameOverBackground1.png exists on classpath but has no damage variants
        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setHealth(100);
        model.setSpeed(1.0);
        model.setImageBase("/main/game/maze/zombieGameOverBackground1.png");
        model.setImageTurnLeft("/main/game/maze/zombieGameOverBackground1.png");
        model.setImageTurnRight("/main/game/maze/zombieGameOverBackground1.png");
        model.setImageTurnUp("/main/game/maze/zombieGameOverBackground1.png");
        model.setImageTurnDown("/main/game/maze/zombieGameOverBackground1.png");
        model.setAnimationFrameCount(1);
        model.setDamageStatesEnabled(true);

        ImageView view = new ImageView();
        ComputerCharacter character = track(new ComputerCharacter(view, model, 0, 0, 1.0));

        character.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();
        Image healthyImage = view.getImage();
        assertNotNull(healthyImage);

        // Capture log messages from ComputerCharacter logger during HP change
        Logger ccLogger = Logger.getLogger(ComputerCharacter.class.getName());
        AtomicInteger warningCount = new AtomicInteger(0);
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                    warningCount.incrementAndGet();
                }
            }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        ccLogger.addHandler(handler);

        try {
            // Drop HP to 50 (DAMAGED); missing asset on classpath causes fallback to healthy path
            character.onHpChanged(50, 100);
            waitForFxEvents();
            assertSame(healthyImage, view.getImage(),
                    "Missing damage asset must fall back cleanly to healthy image");
            assertEquals(0, warningCount.get(),
                    "Fallback on missing damage asset must not log WARNING");
        } finally {
            ccLogger.removeHandler(handler);
        }
    }

    @Test
    void damageStatesEnabled_realClasspathAssets_updatesSprite() {
        Zombie model = createZombieModel(true, 1);
        ImageView view = new ImageView();
        ComputerCharacter character = track(new ComputerCharacter(view, model, 0, 0, 1.0));

        character.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();
        Image healthyImage = view.getImage();
        assertNotNull(healthyImage);

        // zombie1 has real damaged sprite on classpath: /main/game/maze/zombie1-damaged-right.png
        character.onHpChanged(50, 100);
        waitForFxEvents();
        Image damagedImage = view.getImage();
        assertNotNull(damagedImage);
        assertNotSame(healthyImage, damagedImage,
                "Real damage asset on classpath should update image to damaged variant");
    }

    @Test
    void damageStatesEnabled_mockResolver_updatesPerHpTierAndDirection() {
        Zombie model = createZombieModel(true, 1);
        ImageView view = new ImageView();
        ComputerCharacter character = track(new ComputerCharacter(view, model, 0, 0, 1.0));

        Image mockDamagedRight = new WritableImage(10, 10);
        Image mockBleedingRight = new WritableImage(20, 20);
        Image mockTiredRight = new WritableImage(30, 30);
        Image mockDamagedLeft = new WritableImage(40, 40);

        character.registerImageForPath("/main/game/maze/zombie1-damaged-right.png", mockDamagedRight);
        character.registerImageForPath("/main/game/maze/zombie1-bleeding-right.png", mockBleedingRight);
        character.registerImageForPath("/main/game/maze/zombie1-tired-right.png", mockTiredRight);
        character.registerImageForPath("/main/game/maze/zombie1-damaged-left.png", mockDamagedLeft);

        AtomicReference<DamageState> lastResolvedState = new AtomicReference<>();
        character.setDamagePathResolver((basePath, state) -> {
            lastResolvedState.set(state);
            return switch (state) {
                case DAMAGED -> basePath.replace("zombie1", "zombie1-damaged");
                case BLEEDING -> basePath.replace("zombie1", "zombie1-bleeding");
                case TIRED -> basePath.replace("zombie1", "zombie1-tired");
                default -> basePath;
            };
        });

        character.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();

        // 100 HP -> HEALTHY
        character.onHpChanged(100, 100);
        waitForFxEvents();
        assertEquals(DamageState.HEALTHY, character.getDamageState());

        // 70 HP -> DAMAGED
        character.onHpChanged(70, 100);
        waitForFxEvents();
        assertEquals(DamageState.DAMAGED, character.getDamageState());
        assertEquals(DamageState.DAMAGED, lastResolvedState.get());
        assertSame(mockDamagedRight, view.getImage(), "View should display mock damaged right image");

        // 40 HP -> BLEEDING
        character.onHpChanged(40, 100);
        waitForFxEvents();
        assertEquals(DamageState.BLEEDING, character.getDamageState());
        assertSame(mockBleedingRight, view.getImage(), "View should display mock bleeding right image");

        // 15 HP -> TIRED
        character.onHpChanged(15, 100);
        waitForFxEvents();
        assertEquals(DamageState.TIRED, character.getDamageState());
        assertSame(mockTiredRight, view.getImage(), "View should display mock tired right image");

        // Turning left while damaged
        character.onHpChanged(70, 100);
        waitForFxEvents();
        character.directionSubscriber.notifyCurrentDirection(VectorFacing.LEFT);
        waitForFxEvents();
        assertSame(mockDamagedLeft, view.getImage(), "View should display mock damaged left image after turning left");
    }

    @Test
    void damageStatesEnabled_walkCycleAnimationComposition() {
        Zombie model = createZombieModel(true, 3);
        ImageView view = new ImageView();
        ComputerCharacter character = track(new ComputerCharacter(view, model, 0, 0, 1.0));
        // Freeze the 250 ms walk timer so a tick cannot change the frame between step and assert.
        onFx(character::pauseWalkAnimation);

        Image mockDamagedFrame0 = new WritableImage(11, 11);
        Image mockDamagedFrame1 = new WritableImage(22, 22);
        Image mockDamagedFrame2 = new WritableImage(33, 33);
        Image mockBleedingFrame2 = new WritableImage(44, 44);

        character.registerImageForPath("/main/game/maze/zombie1-damaged-right.png", mockDamagedFrame0);
        character.registerImageForPath("/main/game/maze/zombie2-damaged-right.png", mockDamagedFrame1);
        character.registerImageForPath("/main/game/maze/zombie3-damaged-right.png", mockDamagedFrame2);
        character.registerImageForPath("/main/game/maze/zombie3-bleeding-right.png", mockBleedingFrame2);

        character.setDamagePathResolver((basePath, state) -> {
            return switch (state) {
                case DAMAGED -> basePath.replaceFirst("zombie(\\d+)", "zombie$1-damaged");
                case BLEEDING -> basePath.replaceFirst("zombie(\\d+)", "zombie$1-bleeding");
                default -> basePath;
            };
        });

        character.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();

        // Drop HP to 65 (DAMAGED tier)
        character.onHpChanged(65, 100);
        waitForFxEvents();
        assertEquals(DamageState.DAMAGED, character.getDamageState());

        // Each frame step and its assertion run together on the FX thread.
        onFx(() -> {
            character.setCurrentWalkFrame(0);
            assertSame(mockDamagedFrame0, view.getImage(), "Frame 0 should display damaged variant");
        });
        onFx(() -> {
            character.setCurrentWalkFrame(1);
            assertSame(mockDamagedFrame1, view.getImage(), "Frame 1 should display damaged variant");
        });
        onFx(() -> {
            character.setCurrentWalkFrame(2);
            assertSame(mockDamagedFrame2, view.getImage(), "Frame 2 should display damaged variant");
        });
        // While at frame 2, HP drops to BLEEDING
        onFx(() -> {
            character.onHpChanged(35, 100);
            assertSame(mockBleedingFrame2, view.getImage(),
                    "Frame 2 should re-resolve to bleeding variant on HP change");
        });
    }

    @Test
    void zombieCharacter_subtractHitPoints_triggersDamageState() {
        Zombie model = createZombieModel(true, 1);
        ImageView view = new ImageView();
        ZombieCharacter zombie = track(new ZombieCharacter(view, 0, 0, model));

        Image mockDamaged = new WritableImage(15, 15);
        zombie.registerImageForPath("/main/game/maze/zombie1-damaged-right.png", mockDamaged);
        zombie.setDamagePathResolver((basePath, state) ->
                state == DamageState.DAMAGED ? basePath.replace("zombie1", "zombie1-damaged") : basePath);

        zombie.directionSubscriber.notifyCurrentDirection(VectorFacing.RIGHT);
        waitForFxEvents();

        // 100 -> 60 (DAMAGED)
        zombie.subtractHitPoints(40);
        waitForFxEvents();
        assertEquals(60, zombie.getHitPoints());
        assertEquals(DamageState.DAMAGED, zombie.getDamageState());
        assertSame(mockDamaged, view.getImage());
    }

    @Test
    void pumpkinBomberCharacter_subtractHitPoints_triggersDamageState() {
        PumpkinBomber model = createBomberModel(true);

        ImageView view = new ImageView();
        PumpkinBomberCharacter bomber = track(new PumpkinBomberCharacter(view, 0, 0, model));

        Image mockBleeding = new WritableImage(50, 50);
        bomber.registerImageForPath("/main/game/maze/pumpkinbomber-bleeding.png", mockBleeding);
        bomber.setDamagePathResolver((basePath, state) ->
                state == DamageState.BLEEDING ? "/main/game/maze/pumpkinbomber-bleeding.png" : basePath);

        // 100 -> 35 (BLEEDING)
        bomber.subtractHitPoints(65);
        waitForFxEvents();
        assertEquals(35, bomber.getHitPoints());
        assertEquals(DamageState.BLEEDING, bomber.getDamageState());
        assertSame(mockBleeding, view.getImage());
    }

    @Test
    void pumpkinBomber_damageStatesDisabled_hpChangeKeepsGeneratedLeftMirror() {
        ImageView view = new ImageView();
        PumpkinBomberCharacter bomber = track(new PumpkinBomberCharacter(view, 0, 0, createBomberModel(false)));

        onFx(() -> {
            bomber.directionSubscriber.notifyCurrentDirection(VectorFacing.LEFT);
            Image mirrored = view.getImage();
            assertNotNull(mirrored);
            assertTrue(isMirrorOf(mirrored, bomber.imageRight), "LEFT must show the generated mirror");

            bomber.subtractHitPoints(50);
            assertSame(mirrored, view.getImage(),
                    "HP change must not replace the mirror when damageStatesEnabled=false");
        });
    }

    @Test
    void pumpkinBomber_damageStatesEnabled_usesGeneratedImagesWhenHealthyOrAssetMissing() {
        ImageView view = new ImageView();
        PumpkinBomberCharacter bomber = track(new PumpkinBomberCharacter(view, 0, 0, createBomberModel(true)));
        // Simulate a missing damage asset: the resolver falls back to the healthy path.
        bomber.setDamagePathResolver((basePath, state) -> basePath);

        onFx(() -> {
            bomber.directionSubscriber.notifyCurrentDirection(VectorFacing.LEFT);
            Image mirrored = view.getImage();
            assertTrue(isMirrorOf(mirrored, bomber.imageRight), "Healthy LEFT must show the generated mirror");

            bomber.directionSubscriber.notifyCurrentDirection(VectorFacing.UP);
            Image tinted = view.getImage();
            assertFalse(samePixels(tinted, bomber.imageRight), "Healthy UP must show the generated tint");

            bomber.subtractHitPoints(30); // 70 HP -> DAMAGED
            assertEquals(DamageState.DAMAGED, bomber.getDamageState());
            assertSame(tinted, view.getImage(), "Missing damage asset must fall back to the generated UP tint");

            bomber.directionSubscriber.notifyCurrentDirection(VectorFacing.LEFT);
            assertSame(mirrored, view.getImage(), "Missing damage asset must fall back to the generated LEFT mirror");
        });
    }

    @Test
    void pumpkinBomber_directionalAssets_damagedLeftUsesMirroredDamageSprite() {
        PumpkinBomber model = createBomberModel(true);
        model.setImageTurnLeft("/main/game/maze/pumpkinbomber-left.png");
        model.setImageTurnUp("/main/game/maze/pumpkinbomber-up.png");
        ImageView view = new ImageView();
        PumpkinBomberCharacter bomber = track(new PumpkinBomberCharacter(view, 0, 0, model));
        // Real classpath resolver; only the returned image is stubbed so the test can identify it.
        Image damagedLeft = new WritableImage(7, 7);
        bomber.registerImageForPath("/main/game/maze/pumpkinbomber-damaged-left.png", damagedLeft);

        onFx(() -> {
            bomber.directionSubscriber.notifyCurrentDirection(VectorFacing.LEFT);
            assertSame(bomber.imageLeft, view.getImage(), "Healthy LEFT uses the pumpkinbomber-left.png asset");

            bomber.subtractHitPoints(30); // 70 HP -> DAMAGED
            assertSame(damagedLeft, view.getImage(), "Damaged LEFT uses pumpkinbomber-damaged-left.png");
        });
    }
}
