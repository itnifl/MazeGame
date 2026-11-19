package main.game.maze.libgdx.model;

import java.util.List;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.FireBlastModel;
import main.game.maze.common.graphics.fire.FirePuff;

/**
 * One bomb fire blast to draw this frame (libGDX screen space, y-up).
 *
 * @param blast    blast geometry; {@code armPlusY} points up the screen
 * @param progress life progress, 0 at detonation, 1 when gone
 */
public record FireBlastVisual(FireBlast blast, float progress) implements ExplosionVisual {

    @Override
    public List<FirePuff> puffs(float clockSeconds) {
        return FireBlastModel.puffs(blast, progress, clockSeconds);
    }
}
