package main.game.maze.game.hazard;

import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.graphics.fire.TeleportFlashEffect;

/**
 * What the player sees and hears when they are teleported, the same in both frontends: they flash in at the new
 * place, the screen briefly says a word ({@code TeleportRulesSpec#bannerText}) and a deep voice says it too. The word,
 * its time and the sounds come from the level's rules; this class makes the visual.
 */
public final class TeleportFeedback {

    private TeleportFeedback() {
    }

    /** The flash at the place the player appears; {@code yUp} is true on a screen whose y axis points up. */
    public static PuffEffect flashAt(double x, double y, boolean yUp) {
        return new TeleportFlashEffect(x, y, yUp);
    }
}
