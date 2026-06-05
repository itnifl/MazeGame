/**
 */
package main.game.maze.behaviour.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.LocalPathCalculator;
import main.game.maze.behaviour.Position;
import main.game.maze.mazeworld.service.MazeNavigationGraph;


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
		if (origin == null || target == null) {
			return path;
		}
		if (origin.getCol() == target.getCol() && origin.getRow() == target.getRow()) {
			path.add(origin);
			return path;
		}
		MazeNavigationGraph.Node nearest = nearestNode(origin.getNeighbors(), target);
		if (nearest != null) {
			path.add(nearest);
		}
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
} //LocalPathCalculatorImpl

