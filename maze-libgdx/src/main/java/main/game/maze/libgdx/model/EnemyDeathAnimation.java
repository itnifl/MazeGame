package main.game.maze.libgdx.model;

/**
 * Tracks the in-progress death animation for a killed enemy.
 * Cycles through five hurt-sprite frames at a fixed rate.
 */
public final class EnemyDeathAnimation {

    private static final int TOTAL_FRAMES = 5;
    private static final float FRAME_DURATION_SECONDS = 0.1f;
    private static final String HURT_SPRITE_PATH_FORMAT = "/main/game/maze/zombie-hurt%d.png";

    private final float x;
    private final float y;
    private final float size;
    private int currentFrame;
    private float frameTimer;
    private boolean done;

    public EnemyDeathAnimation(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.currentFrame = 0;
        this.frameTimer = FRAME_DURATION_SECONDS;
    }

    public void tick(float dt) {
        if (done) {
            return;
        }
        frameTimer -= Math.max(0f, dt);
        if (frameTimer <= 0f) {
            currentFrame++;
            if (currentFrame >= TOTAL_FRAMES) {
                done = true;
            } else {
                frameTimer = FRAME_DURATION_SECONDS;
            }
        }
    }

    public boolean isDone() {
        return done;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float size() {
        return size;
    }

    public String currentFramePath() {
        int frame = Math.min(currentFrame, TOTAL_FRAMES - 1) + 1;
        return String.format(HURT_SPRITE_PATH_FORMAT, frame);
    }
}
