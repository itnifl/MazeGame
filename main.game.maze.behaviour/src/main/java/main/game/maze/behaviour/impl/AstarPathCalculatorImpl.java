/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.AstarPathCalculator;
import main.game.maze.behaviour.BehaviourPackage;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Astar Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.AstarPathCalculatorImpl#getMaxPathLength <em>Max Path Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstarPathCalculatorImpl extends PathCalculatorImpl implements AstarPathCalculator {
	/**
	 * The default value of the '{@link #getMaxPathLength() <em>Max Path Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxPathLength()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_PATH_LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxPathLength() <em>Max Path Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxPathLength()
	 * @generated
	 * @ordered
	 */
	protected int maxPathLength = MAX_PATH_LENGTH_EDEFAULT;
	protected DistanceMethod heuristicMethod = DistanceMethod.MANHATTAN;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AstarPathCalculatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.ASTAR_PATH_CALCULATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMaxPathLength() {
		return maxPathLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxPathLength(int newMaxPathLength) {
		int oldMaxPathLength = maxPathLength;
		maxPathLength = newMaxPathLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH, oldMaxPathLength, maxPathLength));
	}

	@Override
	public DistanceMethod getHeuristicMethod() {
		return heuristicMethod;
	}

	@Override
	public void setHeuristicMethod(DistanceMethod value) {
		heuristicMethod = value;
	}

	@Override
	public double heuristicDistance(MazeNavigationGraph.Node node, MazeNavigationGraph.Node target) {
		switch (this.heuristicMethod) {
			case MANHATTAN -> {
				int dx = Math.abs(node.getCol() - target.getCol());
				int dy = Math.abs(node.getRow() - target.getRow());
				return dx + dy;
			}
			case EUCLIDEAN -> {
				double distance = Math.hypot(node.getCol() - target.getCol(), node.getRow() - target.getRow());
				return distance;
			}
			default -> throw new AssertionError();
		}
	}

	@Override
	public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node start, MazeNavigationGraph.Node target) {
		// Initialize cost and origin tracking structures
		MazeNavigationGraph graph = GameMazeWorld.GetWorld().getNavigationGraph();
		double[][] accumulatedCosts = new double[graph.getGrid().length][graph.getGrid()[0].length];
		MazeNavigationGraph.Node[][] originsNodes = new MazeNavigationGraph.Node[graph.getGrid().length][graph.getGrid()[0].length];
		List<MazeNavigationGraph.Node> endNodes = new LinkedList<>();
		for (int x=0; x < graph.getGrid().length; x++) {
			for (int y=0; y < graph.getGrid()[0].length; y++) {
				accumulatedCosts[x][y] = Integer.MAX_VALUE;
				originsNodes[x][y] = null;
			}
		}

		// Compute nodes costs
		Queue<MazeNavigationGraph.Node> queue = new LinkedList<>();
		queue.add(start);
		while (queue.isEmpty() == false) {
			var current = queue.poll();
			for (var node : current.getNeighbors()) {
				double newCost = accumulatedCosts[current.getCol()][current.getRow()] + 1 + heuristicDistance(node, target);
				if (newCost < accumulatedCosts[node.getCol()][node.getRow()]) {
					if (newCost < this.getMaxPathLength()) {
						accumulatedCosts[node.getCol()][node.getRow()] = newCost;
						originsNodes[node.getCol()][node.getRow()] = current;
						queue.add(node);
					}
					else {
						endNodes.add(current);
					}
				}
			}
		}

		// Reconstruct path
		var targetNode = nearestNode(endNodes, target);
		return reconstructPath(originsNodes, targetNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH:
				return getMaxPathLength();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH:
				setMaxPathLength((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH:
				setMaxPathLength(MAX_PATH_LENGTH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH:
				return maxPathLength != MAX_PATH_LENGTH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (maxPathLength: ");
		result.append(maxPathLength);
		result.append(')');
		return result.toString();
	}

} //AstarPathCalculatorImpl
