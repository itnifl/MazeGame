package main.game.maze.libgdx.model;

import java.util.List;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.common.graphics.fire.PumpkinBurst;
import main.game.maze.common.graphics.fire.PumpkinBurstModel;

/**
 * One pumpkin bomber projectile explosion to draw this frame (libGDX screen space, y-up).
 *
 * @param burst    burst geometry
 * @param progress life progress, 0 at impact, 1 when gone
 */
public record PumpkinBurstVisual(PumpkinBurst burst, float progress) implements ExplosionVisual {

    @Override
    public List<FirePuff> puffs(float clockSeconds) {
        return PumpkinBurstModel.puffs(burst, progress, clockSeconds);
    }
}
