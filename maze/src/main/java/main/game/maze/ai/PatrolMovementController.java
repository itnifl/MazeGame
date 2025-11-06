package main.game.maze.ai;

import main.game.maze.behaviour.Position;
import main.game.maze.config.PatrolHelper;
import java.util.logging.Logger;

/**
 * PatrolMovementController
 * -------------------------
 * Runtime controller responsible for moving a patrolling enemy
 * toward its current waypoint target as provided by PatrolFollower.
 *
 * Responsibilities:
 *  - Retrieve target positions from PatrolFollower.
 *  - Move smoothly toward the target, respecting acceleration and turn limits.
 *  - Clamp velocity to patrolSpeed.
 *  - Notify PatrolFollower when a waypoint is reached (within reachTolerance).
 *  - Apply rate-limited replanning (future integration).
 *
 * Note: This class controls the "how" (movement), while
 * PatrolFollower controls the "where" and "when" (waypoint order & hold timing).
 * 
 * --------------------------
 * 
 * this controller can be called in the game loop like this:
 * follower.update(enemy.getX(), enemy.getY(), System.currentTimeMillis());
 * controller.update(enemy.getX(), enemy.getY(), System.currentTimeMillis(), deltaTimeMs); 
 */
public class PatrolMovementController {

    private static final Logger LOG = Logger.getLogger(PatrolMovementController.class.getName());

    
    private final PatrolFollower follower;

    
    private final double patrolSpeed;        // max movement speed
    private final double accelMax;           // max acceleration (per update)
    private final double turnRateMax;        // max change in direction (radians/sec)
    private final double replanIntervalMs;   // time between path replans
    private final double reachTolerance;     // distance tolerance to consider waypoint reached

    
    private double velX = 0.0;
    private double velY = 0.0;
    private double headingRadians = 0.0;
    private long lastReplanTime = 0L;

    public PatrolMovementController(
            PatrolFollower follower,
            double patrolSpeed,
            double accelMax,
            double turnRateMax,
            double replanIntervalMs,
            double reachTolerance
    ) {
        this.follower = follower;
        this.patrolSpeed = patrolSpeed;
        this.accelMax = accelMax;
        this.turnRateMax = turnRateMax;
        this.replanIntervalMs = replanIntervalMs;
        this.reachTolerance = reachTolerance;
    }

    public void update(double x, double y, long currentTimeMs, double deltaTimeMs) {
        // Step 1: Update follower (may advance waypoint if reached)
        follower.update(x, y, currentTimeMs);

        // Step 2: If follower is holding, skip movement
        if (follower.isHolding()) {
            velX = 0;
            velY = 0;
            return;
        }

        // Step 3: Get current target
        Position target = follower.getCurrentTarget();
        if (target == null) {
            LOG.warning("No patrol target available — skipping movement.");
            return;
        }

        // Step 4: Compute desired direction to target
        double dx = target.getPosX() - x;
        double dy = target.getPosY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist < reachTolerance) {
            // reached — let follower handle it on next update
            velX = 0;
            velY = 0;
            return;
        }

        // Step 5: Normalize direction
        double dirX = dx / dist;
        double dirY = dy / dist;

        // Step 6: Adjust velocity toward desired direction (respect accel/turn caps)
        applySteering(dirX, dirY, deltaTimeMs);

        // Step 7: Apply patrol speed clamp
        double speed = Math.sqrt(velX * velX + velY * velY);
        if (speed > patrolSpeed) {
            double scale = patrolSpeed / speed;
            velX *= scale;
            velY *= scale;
            LOG.fine(() -> String.format("[PatrolMovementController] Speed clamped to %.2f", patrolSpeed));
        }

        // Step 8: Move enemy (pseudo — depends on integration)
        double newX = x + velX * deltaTimeMs / 1000.0;
        double newY = y + velY * deltaTimeMs / 1000.0;

        LOG.fine(() -> String.format("[PatrolMovementController] Moving to (%.2f, %.2f)", newX, newY));

        // TODO: Integrate this position update with your actual game object
        // Example:
        // enemy.setPosition(newX, newY);
    }

    /**
     * Adjusts velocity direction smoothly toward desired target direction.
     */
    private void applySteering(double targetDirX, double targetDirY, double deltaTimeMs) {
        double desiredVelX = targetDirX * patrolSpeed;
        double desiredVelY = targetDirY * patrolSpeed;

        // Apply acceleration limit
        double diffX = desiredVelX - velX;
        double diffY = desiredVelY - velY;
        double diffMag = Math.sqrt(diffX * diffX + diffY * diffY);

        double accelLimit = accelMax * (deltaTimeMs / 1000.0);
        if (diffMag > accelLimit) {
            double scale = accelLimit / diffMag;
            diffX *= scale;
            diffY *= scale;
        }

        velX += diffX;
        velY += diffY;
    }

    //Optional getter — used for testing.
    public double getVelocityMagnitude() {
        return Math.sqrt(velX * velX + velY * velY);
    }
    
    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

}
