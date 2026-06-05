package main.game.maze.libgdx.helper;

/**
 * Runtime state for temporary debug overlays shown in gameplay.
 */
public final class GdxDebugOverlayState {

    private float behaviourTypeSeconds;
    private float movementTypeSeconds;
    private float enemyPathSeconds;

    public void tick(float dt) {
        if (behaviourTypeSeconds > 0f) {
            behaviourTypeSeconds = Math.max(0f, behaviourTypeSeconds - dt);
        }
        if (movementTypeSeconds > 0f) {
            movementTypeSeconds = Math.max(0f, movementTypeSeconds - dt);
        }
        if (enemyPathSeconds > 0f) {
            enemyPathSeconds = Math.max(0f, enemyPathSeconds - dt);
        }
    }

    public void applyTerminalAction(GdxTerminalCommandSupport.Action action, float labelSeconds, float enemyPathOverlaySeconds) {
        if (action == GdxTerminalCommandSupport.Action.SHOW_BEHAVIOUR_TYPE) {
            behaviourTypeSeconds = labelSeconds;
        }
        if (action == GdxTerminalCommandSupport.Action.SHOW_MOVEMENT_TYPE) {
            movementTypeSeconds = labelSeconds;
        }
        if (action == GdxTerminalCommandSupport.Action.SHOW_ENEMY_PATH) {
            enemyPathSeconds = enemyPathOverlaySeconds;
        }
    }

    public void reset() {
        behaviourTypeSeconds = 0f;
        movementTypeSeconds = 0f;
        enemyPathSeconds = 0f;
    }

    public boolean showBehaviourType() {
        return behaviourTypeSeconds > 0f;
    }

    public boolean showMovementType() {
        return movementTypeSeconds > 0f;
    }

    public float enemyPathSecondsRemaining() {
        return enemyPathSeconds;
    }
}


