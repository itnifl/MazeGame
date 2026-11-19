package main.game.maze.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.libgdx.model.ExplosionVisual;

/**
 * Draws explosions (player bomb fire blasts, pumpkin bomber bursts) with a filled
 * {@link ShapeRenderer}: smoke and debris with normal alpha blending, flame layers additively
 * so overlapping puffs glow. Puffs from all explosions are drawn together, back to front by
 * {@link FirePuff.Layer}, which also keeps blend-mode switches to a minimum. Restores the
 * previous GL blend state afterwards. Puff geometry comes from the shared models.
 */
public final class GdxExplosionRenderer {

    private static final int SEGMENTS = 18;

    /** Seam over the few GL calls used, so the renderer can be tested without a GL context. */
    public interface Blending {
        boolean isEnabled();

        void setEnabled(boolean enabled);

        void setAdditive(boolean additive);
    }

    private final Blending blending;

    public GdxExplosionRenderer() {
        this(new GlBlending());
    }

    GdxExplosionRenderer(Blending blending) {
        this.blending = blending;
    }

    /**
     * @param shapes       a ShapeRenderer that has been begun in Filled mode
     * @param explosions   explosions to draw
     * @param clockSeconds running clock for flicker
     */
    public void draw(ShapeRenderer shapes, List<? extends ExplosionVisual> explosions, float clockSeconds) {
        if (explosions == null || explosions.isEmpty()) {
            return;
        }
        List<FirePuff> puffs = new ArrayList<>();
        for (ExplosionVisual explosion : explosions) {
            puffs.addAll(explosion.puffs(clockSeconds));
        }
        if (puffs.isEmpty()) {
            return;
        }
        puffs.sort(Comparator.comparing(FirePuff::layer)); // stable: keeps each model's order within a layer

        boolean wasEnabled = blending.isEnabled();
        shapes.flush();
        blending.setEnabled(true);
        Boolean additive = null;
        for (FirePuff puff : puffs) {
            boolean wantAdditive = puff.layer().additive();
            if (additive == null || additive != wantAdditive) {
                shapes.flush();
                blending.setAdditive(wantAdditive);
                additive = wantAdditive;
            }
            shapes.setColor(puff.red(), puff.green(), puff.blue(), puff.alpha());
            shapes.circle((float) puff.x(), (float) puff.y(), (float) puff.radius(), SEGMENTS);
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
