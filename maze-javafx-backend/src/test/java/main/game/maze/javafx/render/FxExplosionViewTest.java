package main.game.maze.javafx.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.FireBlastModel;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.common.graphics.fire.PumpkinBurst;
import main.game.maze.common.graphics.fire.PumpkinBurstModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FxExplosionView")
class FxExplosionViewTest {

    private static final FireBlast BLAST = new FireBlast(300, 200, 150, 60, 90, 0, 45, 11L);
    private static final PumpkinBurst BURST = new PumpkinBurst(120, 90, 52, 5L);

    @BeforeAll
    static void initFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX toolkit must start within 5 s");
    }

    @Test
    @DisplayName("canvas covers every puff over the whole blast life")
    void canvasCoversAllPuffs() {
        Canvas canvas = FxExplosionView.of(BLAST).canvas();
        double minX = canvas.getLayoutX();
        double minY = canvas.getLayoutY();
        double maxX = minX + canvas.getWidth();
        double maxY = minY + canvas.getHeight();
        for (double p = 0; p < 1.0; p += 0.02) {
            for (FirePuff puff : FireBlastModel.puffs(BLAST, p, p * 2)) {
                assertTrue(puff.x() - puff.radius() >= minX && puff.x() + puff.radius() <= maxX
                        && puff.y() - puff.radius() >= minY && puff.y() + puff.radius() <= maxY,
                        "puff outside canvas at progress " + p + ": " + puff);
            }
        }
    }

    @Test
    @DisplayName("progress runs from 0 to 1 over the lifetime and clamps")
    void progressAt() {
        assertEquals(0.0, FxExplosionView.progressAt(-1, 0.75));
        assertEquals(0.5, FxExplosionView.progressAt(0.375, 0.75), 1e-9);
        assertEquals(1.0, FxExplosionView.progressAt(2, 0.75));
        assertEquals(1.0, FxExplosionView.progressAt(0.1, 0));
    }

    @Test
    @DisplayName("a frame paints fire at the blast centre and leaves far corners transparent")
    void renderFramePaintsFire() throws Exception {
        AtomicReference<WritableImage> snapshot = new AtomicReference<>();
        AtomicReference<double[]> origin = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FxExplosionView view = FxExplosionView.of(BLAST);
                view.renderFrame(0.3, 0.0);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                snapshot.set(view.canvas().snapshot(params, null));
                origin.set(new double[] {view.canvas().getLayoutX(), view.canvas().getLayoutY()});
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));

        WritableImage image = snapshot.get();
        int cx = (int) (BLAST.centerX() - origin.get()[0]);
        int cy = (int) (BLAST.centerY() - origin.get()[1]);
        Color centre = image.getPixelReader().getColor(cx, cy);
        assertTrue(centre.getOpacity() > 0.5, "centre should be painted");
        assertTrue(centre.getRed() > 0.8, "centre should be fiery, got " + centre);
        assertEquals(0.0, image.getPixelReader().getColor(0, 0).getOpacity(), 1e-6);
    }

    @Test
    @DisplayName("pumpkin burst canvas covers every puff over the whole life")
    void burstCanvasCoversAllPuffs() {
        Canvas canvas = FxExplosionView.of(BURST).canvas();
        double minX = canvas.getLayoutX();
        double minY = canvas.getLayoutY();
        for (double p = 0; p < 1.0; p += 0.02) {
            for (FirePuff puff : PumpkinBurstModel.puffs(BURST, p, p * 2)) {
                assertTrue(puff.x() - puff.radius() >= minX && puff.x() + puff.radius() <= minX + canvas.getWidth()
                        && puff.y() - puff.radius() >= minY && puff.y() + puff.radius() <= minY + canvas.getHeight(),
                        "puff outside canvas at progress " + p + ": " + puff);
            }
        }
    }

    @Test
    @DisplayName("a pumpkin burst frame paints pumpkin debris that is not glowing white")
    void burstFramePaintsDebris() throws Exception {
        AtomicReference<WritableImage> snapshot = new AtomicReference<>();
        AtomicReference<double[]> origin = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FxExplosionView view = FxExplosionView.of(BURST);
                view.renderFrame(0.4, 0.0);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                snapshot.set(view.canvas().snapshot(params, null));
                origin.set(new double[] {view.canvas().getLayoutX(), view.canvas().getLayoutY()});
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));

        // the debris chunk furthest from the centre is drawn outside the fireball, so its colour shows as-is
        FirePuff chunk = PumpkinBurstModel.puffs(BURST, 0.4, 0.0).stream()
                .filter(p -> p.layer() == FirePuff.Layer.DEBRIS && p.red() < 0.75f && p.green() < 0.35f)
                .max(java.util.Comparator.comparingDouble(
                        p -> Math.hypot(p.x() - BURST.centerX(), p.y() - BURST.centerY())))
                .orElseThrow();
        Color pixel = snapshot.get().getPixelReader().getColor(
                (int) (chunk.x() - origin.get()[0]), (int) (chunk.y() - origin.get()[1]));
        assertTrue(pixel.getOpacity() > 0.5, "debris should be painted, got " + pixel);
        assertTrue(pixel.getRed() > pixel.getBlue() + 0.3, "debris should be pumpkin-coloured, got " + pixel);
    }
}
