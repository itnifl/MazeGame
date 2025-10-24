package main.game.maze.ai;

import main.game.maze.config.PatrolHelper.PatrolDefinition;
import mazeidea.movements.PatrolPoint;
import mazeidea.movements.Position;
import java.util.List;

public class PatrolFollower {
    private final PatrolDefinition patrol;
    private int currentIndex = 0;
    private long holdEndTime = 0;
    private boolean holding = false;

    private final double reachTolerance;
    private final double patrolSpeed;

    public PatrolFollower(PatrolDefinition patrol, double reachTolerance, double patrolSpeed) {
        this.patrol = patrol;
        this.reachTolerance = reachTolerance;
        this.patrolSpeed = patrolSpeed;
    }


    public Position getCurrentTarget() {
        List<PatrolPoint> path = patrol.getPath();
        if (path == null || path.isEmpty()) return null;
        return path.get(currentIndex).getPoint();
    }

    public boolean isHolding() {
        return holding;
    }

    public void update(double x, double y, long currentTimeMs) {
        List<PatrolPoint> path = patrol.getPath();
        if (path == null || path.isEmpty()) return;

        if (holding) {
            if (currentTimeMs >= holdEndTime) {
                holding = false;
                advanceWaypoint();
            }
            return;
        }

        if (reachedWaypoint(x, y)) {
            PatrolPoint wp = path.get(currentIndex);
            long holdMs = (long) wp.getTime();

            if (holdMs > 0) {
                holding = true;
                holdEndTime = currentTimeMs + holdMs;
            } else {
                advanceWaypoint();
            }
        }
    }

    private boolean reachedWaypoint(double x, double y) {
        Position target = getCurrentTarget();
        if (target == null) return false;

        double dx = target.getPosX() - x;
        double dy = target.getPosY() - y;
        double distSq = dx * dx + dy * dy;
        return distSq <= reachTolerance * reachTolerance;
    }

    private void advanceWaypoint() {
        List<PatrolPoint> path = patrol.getPath();
        if (path == null || path.isEmpty()) return;

        currentIndex = (currentIndex + 1) % path.size();
    }

}
