package main.game.maze.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;
import main.game.maze.common.graphics.fire.FireBlastModel;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.libgdx.model.FireBlastVisual;

/**
 * Draws bomb fire blasts with a filled {@link ShapeRenderer}: smoke with normal alpha
 * blending, flame layers additively so overlapping puffs glow. Restores the previous
 * GL blend state afterwards. The puff geometry comes from the shared {@link FireBlastModel}.
 */
public final class GdxFireBlastRenderer {

    private static final int SEGMENTS = 18;

    /** Seam over the few GL calls used, so the renderer can be tested without a GL context. */
    public interface Blending {
        boolean isEnabled();

        void setEnabled(boolean enabled);

        void setAdditive(boolean additive);
    }

    private final Blending blending;

    public GdxFireBlastRenderer() {
        this(new GlBlending());
    }

    GdxFireBlastRenderer(Blending blending) {
        this.blending = blending;
    }

    /**
     * @param shapes       a ShapeRenderer that has been begun in Filled mode
     * @param blasts       blasts to draw
     * @param clockSeconds running clock for flicker
     */
    public void draw(ShapeRenderer shapes, List<FireBlastVisual> blasts, float clockSeconds) {
        if (blasts == null || blasts.isEmpty()) {
            return;
        }
        boolean wasEnabled = blending.isEnabled();
        shapes.flush();
        blending.setEnabled(true);
        Boolean additive = null;
        for (FireBlastVisual visual : blasts) {
            for (FirePuff puff : FireBlastModel.puffs(visual.blast(), visual.progress(), clockSeconds)) {
                boolean wantAdditive = puff.layer().additive();
                if (additive == null || additive != wantAdditive) {
                    shapes.flush();
                    blending.setAdditive(wantAdditive);
                    additive = wantAdditive;
                }
                shapes.setColor(puff.red(), puff.green(), puff.blue(), puff.alpha());
                shapes.circle((float) puff.x(), (float) puff.y(), (float) puff.radius(), SEGMENTS);
            }
        }
        shapes.flush();
        blending.setAdditive(false);
        blending.setEnabled(wasEnabled);
    }

    private static final class GlBlending implements Blending {
        @Override
        public boolean isEnabled() {
            return Gdx.gl.glIsEnabled(GL20.GL_BLEND);
        }

        @Override
        public void setEnabled(boolean enabled) {
            if (enabled) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
            } else {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
        }

        @Override
        public void setAdditive(boolean additive) {
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, additive ? GL20.GL_ONE : GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }
}
