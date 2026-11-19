package main.game.maze.libgdx.model;

import java.util.List;
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.common.graphics.fire.WallRubbleModel;

/**
 * A collapsing wall to draw this frame (libGDX screen space, y-up).
 *
 * @param rubble   collapse geometry and material
 * @param progress life progress, 0 at collapse, 1 when gone
 */
public record WallRubbleVisual(WallRubble rubble, float progress) implements ExplosionVisual {

    @Override
    public List<FirePuff> puffs(float clockSeconds) {
        return WallRubbleModel.puffs(rubble, progress, clockSeconds);
    }
}
