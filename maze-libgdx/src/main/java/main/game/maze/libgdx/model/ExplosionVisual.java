package main.game.maze.libgdx.model;

import java.util.List;
import main.game.maze.common.graphics.fire.FirePuff;

/**
 * Something explosive to draw this frame as layered {@link FirePuff}s (libGDX screen space,
 * y-up). Implemented by player bomb fire blasts and pumpkin bomber projectile bursts, so one
 * renderer draws both.
 */
public interface ExplosionVisual {

    /** @param clockSeconds running clock for flicker */
    List<FirePuff> puffs(float clockSeconds);
}
