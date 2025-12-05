/**
 */
package main.game.maze.behaviour.impl;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import javafx.geometry.Point2D;
import main.game.maze.MazeWorld;
import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.LocalPathCalculator;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import main.game.maze.behaviour.Position;
import org.eclipse.emf.common.util.EList;
import main.game.maze.service.MazeNavigationGraph;
import main.game.maze.service.MazeNavigationGraphService;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Path Calculator</b></em>'.
 * Uses the MazeNavigationGraph for pathfinding through the maze corridors.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class LocalPathCalculatorImpl extends PathCalculatorImpl implements LocalPathCalculator {

	/**
	 * The current position of the character.
	 * Must be set before calling compute().
	 * @generated NOT
	 */
	private Position currentPosition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalPathCalculatorImpl() {
		super();
	}


	@Override
	public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node origin, MazeNavigationGraph.Node target) {
		EList<MazeNavigationGraph.Node> path = new org.eclipse.emf.common.util.BasicEList<>();
		if (origin.getCol() == target.getCol() && origin.getRow() == target.getRow()) {
			path.add(origin);
			return path;
		}
		path.add(nearestNode(origin.getNeighbors(), target));
		return path;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.LOCAL_PATH_CALCULATOR;
	}

	/**
	 * Sets the current position of the character.
	 * Must be called before compute() to provide the starting point.
	 *
	 * @param position the current position
	 * @generated NOT
	 */
	public void setCurrentPosition(Position position) {
		this.currentPosition = position;
	}

	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 * @generated NOT
	 */
	public Position getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Computes a path from the current position to the target position.
	 * Uses the MazeNavigationGraph to find a path that avoids walls.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<Position> compute(Position target) {
		EList<Position> result = new BasicEList<>();

		// Validate inputs
		if (target == null) {
			return result;
		}
		if (currentPosition == null) {
			// No start position: just return the target as a single node path
			result.add(copyPosition(target));
			return result;
		}

		// Get the navigation graph from the MazeWorld
		MazeNavigationGraph graph = null;
		try {
			MazeWorld world = MazeWorld.GetWorld();
			if (world != null) {
				graph = world.getNavigationGraph();
			}
		} catch (Exception e) {
			// MazeWorld not initialized or other error
		}

		if (graph == null) {
			// No graph available, fall back to direct path (may hit walls)
			result.add(copyPosition(target));
			return result;
		}

		// Convert EMF Position to JavaFX Point2D
		Point2D startPoint = new Point2D(currentPosition.getPosX(), currentPosition.getPosY());
		Point2D goalPoint  = new Point2D(target.getPosX(),         target.getPosY());

		// Use existing BFS pathfinding on the navigation graph
		List<Point2D> path = MazeNavigationGraphService.findPath(graph, startPoint, goalPoint);

		// If no path found, return empty list
		if (path == null || path.isEmpty()) {
			return result;
		}

		// Convert List<Point2D> to EList<Position> for the EMF model
		for (Point2D pt : path) {
			Position p = BehaviourFactory.eINSTANCE.createPosition();
			p.setPosX(pt.getX());
			p.setPosY(pt.getY());
			result.add(p);
		}

		return result;
	}

	/**
	 * Creates a copy of a Position.
	 * @generated NOT
	 */
	private Position copyPosition(Position src) {
		Position p = BehaviourFactory.eINSTANCE.createPosition();
		p.setPosX(src.getPosX());
		p.setPosY(src.getPosY());
		return p;
	}
} //LocalPathCalculatorImpl