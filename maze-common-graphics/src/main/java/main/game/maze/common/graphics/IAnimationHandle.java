package main.game.maze.common.graphics;

/** Handle to a running or scheduled animation. */
public interface IAnimationHandle {
    void stop();
    boolean isRunning();
}