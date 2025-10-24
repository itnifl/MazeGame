package main.game.maze.config;

import movements.PatrolBehavior;
import mazeidea.movements.PatrolPoint;
import mazeidea.movements.PatrolZone;
import mazeidea.movements.Position;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


/**
 * PatrolHelper
 * -------------
 * Provides:
 * - Runtime data structure (PatrolDefinition)
 * - Validation and loading logic for PatrolBehavior models
 */
public class PatrolHelper {
    private static final Logger LOG = Logger.getLogger(PatrolHelper.class.getName());

    //Definition
    public static class PatrolDefinition {
        private final List<PatrolPoint> path;
        private final PatrolZone zone;

        public PatrolDefinition(List<PatrolPoint> path, PatrolZone zone) {
            this.path = path;
            this.zone = zone;
        }

        public List<PatrolPoint> getPath() { return path; }
        public PatrolZone getZone() { return zone; }

        public boolean hasPath() { return path != null && !path.isEmpty(); }
        public boolean hasZone() { return zone != null; }
    }

    // Validation + Loading
    public static PatrolDefinition fromModel(PatrolBehavior patrol, double mapWidth, double mapHeight) {
        if (patrol == null)
            throw new IllegalArgumentException("PatrolBehavior cannot be null");

        List<PatrolPoint> path = patrol.getPath();
        PatrolZone zone = patrol.getPatrolZone();

        boolean hasPath = path != null && !path.isEmpty();
        boolean hasZone = zone != null;

        if (!hasPath && !hasZone)
            throw new IllegalArgumentException(
                "Invalid PatrolBehavior: both patrolPath and patrolZone missing.");

        if (hasPath) validatePath(path, mapWidth, mapHeight);
        if (hasZone) validateZone(zone, mapWidth, mapHeight);

        LOG.info(() ->
            "[PatrolHelper] Loaded: " +
            (hasPath ? "path(" + path.size() + ")" : "") +
            (hasZone ? " zone[" + zone.getWidth() + "x" + zone.getHeight() + "]" : "")
        );

        return new PatrolDefinition(path, zone);
    }

    //Validation methods
    private static void validatePath(List<PatrolPoint> path, double mapWidth, double mapHeight) {
        PatrolPoint last = null;
        for (int i = 0; i < path.size(); i++) {
            PatrolPoint p = path.get(i);
            Objects.requireNonNull(p.getPoint(), "PatrolPoint.point is null");
            Position pos = p.getPoint();

            if (Double.isNaN(pos.getPosX()) || Double.isNaN(pos.getPosY()))
                throw new IllegalArgumentException("Waypoint has NaN coordinates");

            if (pos.getPosX() < 0 || pos.getPosY() < 0 ||
                pos.getPosX() >= mapWidth || pos.getPosY() >= mapHeight)
                throw new IllegalArgumentException(
                    "Waypoint out of bounds: (" + pos.getPosX() + "," + pos.getPosY() + ")");

            if (p.getTime() < 0) {
                LOG.warning(() -> "Negative holdMsPerWaypoint clamped to 0 at (" +
                        pos.getPosX() + "," + pos.getPosY() + ")");
                p.setTime(0);
            }

            // collapse adjacent duplicates
            if (last != null && samePos(last.getPoint(), pos)) {
                LOG.warning(() -> "Collapsed duplicate waypoint at (" +
                        pos.getPosX() + "," + pos.getPosY() + ")");
                path.remove(last);
                i--;	// recheck current index after removal
            }
            last = p;
        }
    }

    private static void validateZone(PatrolZone z, double mapWidth, double mapHeight) {
        Objects.requireNonNull(z.getTopLeft(), "PatrolZone.topLeft missing");
        if (z.getWidth() < 1.0 || z.getHeight() < 1.0)
            throw new IllegalArgumentException(
                "Invalid zone size: width=" + z.getWidth() + ", height=" + z.getHeight());

        double x = z.getTopLeft().getPosX();
        double y = z.getTopLeft().getPosY();
        if (x < 0 || y < 0 || x + z.getWidth() > mapWidth || y + z.getHeight() > mapHeight)
            throw new IllegalArgumentException(
                "Zone out of bounds: (" + x + "," + y + ") size[" +
                        z.getWidth() + "x" + z.getHeight() + "]");
    }

    private static boolean samePos(Position a, Position b) {
        return a.getPosX() == b.getPosX() && a.getPosY() == b.getPosY();
    }
}
