package main.game.maze.libgdx.model;

import java.util.List;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.common.graphics.fire.PuffEffect;

/** Any shared {@link PuffEffect} (laser, swing, hit reaction, sparkle) at a point of its life. */
public record PuffEffectVisual(PuffEffect effect, float progress) implements ExplosionVisual {

    @Override
    public List<FirePuff> puffs(float clockSeconds) {
        return effect.puffs(progress, clockSeconds);
    }
}
