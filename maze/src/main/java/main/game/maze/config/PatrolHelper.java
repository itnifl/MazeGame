package main.game.maze.config;

import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.PatrolZone;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import main.game.maze.behaviour.BehaviourPackage;




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
    
    //Model loading
    public static PatrolBehavior loadPatrolModel(String filePath) throws Exception {
    	BehaviourPackage.eINSTANCE.eClass();
    	
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        reg.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        URI fileURI = URI.createFileURI(new File(filePath).getAbsolutePath());
        Resource resource = resSet.getResource(fileURI, true);
        resource.load(Collections.emptyMap());

        if (resource.getContents().isEmpty()) {
            throw new IllegalArgumentException("No contents found in patrol model: " + filePath);
        }

        Object root = resource.getContents().get(0);
        if (!(root instanceof PatrolBehavior)) {
            throw new IllegalArgumentException("Root object is not a PatrolBehavior: " + root.getClass().getName());
        }

        LOG.info(() -> "[PatrolHelper] Patrol model loaded from " + filePath);
        return (PatrolBehavior) root;
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
