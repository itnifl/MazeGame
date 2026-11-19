package main.game.maze.libgdx.model;

import main.game.maze.common.graphics.fire.FireBlast;

/**
 * One bomb fire blast to draw this frame (libGDX screen space, y-up).
 *
 * @param blast    blast geometry; {@code armPlusY} points up the screen
 * @param progress life progress, 0 at detonation, 1 when gone
 */
public record FireBlastVisual(FireBlast blast, float progress) {
}
