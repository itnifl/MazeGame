package main.game.maze.game.hazard;

import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.graphics.fire.TeleportFlashEffect;

/**
 * What the player sees and hears when they are teleported, the same in both frontends: they flash in at the new
 * place, the screen briefly says "Teleported" and a deep voice says it too.
 */
public final class TeleportFeedback {

    /** The word on the screen and in the voice. */
    public static final String BANNER_TEXT = "Teleported";
    /** How long the word stays on the screen, including its fades. */
    public static final float BANNER_SECONDS = 1.6f;
    /** The magical zap of the teleport. */
    public static final String ZAP_SOUND = "/main/game/maze/sounds/teleport.wav";
    /** A deep voice saying the word (made by {@code RecordVoice.ps1} and {@code DeepVoiceGenerator}). */
    public static final String VOICE_SOUND = "/main/game/maze/sounds/teleported.wav";

    private TeleportFeedback() {
    }

    /** The flash at the place the player appears; {@code yUp} is true on a screen whose y axis points up. */
    public static PuffEffect flashAt(double x, double y, boolean yUp) {
        return new TeleportFlashEffect(x, y, yUp);
    }
}
