package main.game.maze.libgdx.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.FireBlastModel;
import main.game.maze.common.graphics.fire.PumpkinBurst;
import main.game.maze.common.graphics.fire.PumpkinBurstModel;
import main.game.maze.libgdx.model.FireBlastVisual;
import main.game.maze.libgdx.model.PumpkinBurstVisual;
import org.junit.jupiter.api.Test;

class GdxExplosionRendererTest {

    private static final FireBlast BLAST = new FireBlast(50, 60, 90, 90, 90, 90, 40, 7L);
    private static final PumpkinBurst BURST = new PumpkinBurst(300, 200, 52, 3L);

    @Test
    void drawsOneCirclePerPuffFromTheSharedModel() {
        ShapeRenderer shapes = mock(ShapeRenderer.class);
        RecordingBlending blending = new RecordingBlending(false);

        new GdxExplosionRenderer(blending).draw(shapes, List.of(new FireBlastVisual(BLAST, 0.5f)), 1.0f);

        int expected = FireBlastModel.puffs(BLAST, 0.5f, 1.0f).size();
        verify(shapes, times(expected)).circle(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }

    @Test
    void smokeIsNormalBlendedAndFlameAdditiveThenStateIsRestored() {
        ShapeRenderer shapes = mock(ShapeRenderer.class);
        RecordingBlending blending = new RecordingBlending(false);

        // progress 0.5: smoke and flame both present
        new GdxExplosionRenderer(blending).draw(shapes, List.of(new FireBlastVisual(BLAST, 0.5f)), 0f);

        assertEquals(List.of("enable", "normal", "additive", "normal", "disable"), blending.calls);
        assertFalse(blending.enabled);
    }

    @Test
    void keepsBlendingEnabledIfItWasAlreadyOn() {
        RecordingBlending blending = new RecordingBlending(true);

        new GdxExplosionRenderer(blending).draw(mock(ShapeRenderer.class),
                List.of(new FireBlastVisual(BLAST, 0.1f)), 0f);

        assertEquals("enable", blending.calls.get(blending.calls.size() - 1));
    }

    @Test
    void nothingToDrawTouchesNothing() {
        ShapeRenderer shapes = mock(ShapeRenderer.class);
        RecordingBlending blending = new RecordingBlending(false);

        new GdxExplosionRenderer(blending).draw(shapes, List.of(), 0f);
        new GdxExplosionRenderer(blending).draw(shapes, null, 0f);

        verifyNoInteractions(shapes);
        assertEquals(List.of(), blending.calls);
    }

    @Test
    void finishedBlastDrawsNoCircles() {
        ShapeRenderer shapes = mock(ShapeRenderer.class);

        new GdxExplosionRenderer(new RecordingBlending(false))
                .draw(shapes, List.of(new FireBlastVisual(BLAST, 1f)), 0f);

        verify(shapes, never()).circle(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }

    @Test
    void pumpkinDebrisIsPaintedNormallyOnTopOfTheGlowingFire() {
        RecordingBlending blending = new RecordingBlending(false);

        // progress 0.4: smoke, flame, embers and debris all present
        new GdxExplosionRenderer(blending).draw(mock(ShapeRenderer.class),
                List.of(new PumpkinBurstVisual(BURST, 0.4f)), 0f);

        assertEquals(List.of("enable", "normal", "additive", "normal", "normal", "disable"), blending.calls);
    }

    @Test
    void drawsFireBlastsAndPumpkinBurstsInOnePass() {
        ShapeRenderer shapes = mock(ShapeRenderer.class);

        new GdxExplosionRenderer(new RecordingBlending(false)).draw(shapes,
                List.of(new FireBlastVisual(BLAST, 0.3f), new PumpkinBurstVisual(BURST, 0.3f)), 0.5f);

        int expected = FireBlastModel.puffs(BLAST, 0.3f, 0.5f).size()
                + PumpkinBurstModel.puffs(BURST, 0.3f, 0.5f).size();
        verify(shapes, times(expected)).circle(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }

    private static final class RecordingBlending implements GdxExplosionRenderer.Blending {
        private final List<String> calls = new ArrayList<>();
        private boolean enabled;

        RecordingBlending(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public void setEnabled(boolean value) {
            enabled = value;
            calls.add(value ? "enable" : "disable");
        }

        @Override
        public void setAdditive(boolean additive) {
            calls.add(additive ? "additive" : "normal");
        }
    }
}
