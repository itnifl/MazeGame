/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.AstarPathCalculator;
import main.game.maze.behaviour.BehaviourPackage;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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

	/**
	 * The default value of the '{@link #getHeuristicMethod() <em>Heuristic Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeuristicMethod()
	 * @generated
	 * @ordered
	 */
	protected static final DistanceMethod HEURISTIC_METHOD_EDEFAULT = DistanceMethod.MANHATTAN;

	/**
	 * The cached value of the '{@link #getHeuristicMethod() <em>Heuristic Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeuristicMethod()
	 * @generated
	 * @ordered
	 */
	protected DistanceMethod heuristicMethod = HEURISTIC_METHOD_EDEFAULT;

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setHeuristicMethod(DistanceMethod value) {
		DistanceMethod newHeuristicMethod = value == null ? HEURISTIC_METHOD_EDEFAULT : value;
		DistanceMethod oldHeuristicMethod = heuristicMethod;
		heuristicMethod = newHeuristicMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ASTAR_PATH_CALCULATOR__HEURISTIC_METHOD, oldHeuristicMethod, heuristicMethod));
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
		if (start == null || target == null) {
			return new org.eclipse.emf.common.util.BasicEList<>();
		}

		// Initialize cost and origin tracking structures
		MazeNavigationGraph graph = GameMazeWorld.GetWorld().getNavigationGraph();
		double[][] accumulatedCosts = new double[graph.getGrid().length][graph.getGrid()[0].length];
		MazeNavigationGraph.Node[][] originsNodes = new MazeNavigationGraph.Node[graph.getGrid().length][graph.getGrid()[0].length];
		List<MazeNavigationGraph.Node> endNodes = new LinkedList<>();
		for (int x=0; x < graph.getGrid().length; x++) {
			for (int y=0; y < graph.getGrid()[0].length; y++) {
				accumulatedCosts[x][y] = Double.MAX_VALUE;
				originsNodes[x][y] = null;
			}
		}
		accumulatedCosts[start.getCol()][start.getRow()] = 0;

		// Use priority queue ordered by f(n) = g(n) + h(n) for A* search
		PriorityQueue<MazeNavigationGraph.Node> openSet = new PriorityQueue<>(
			Comparator.comparingDouble(node -> 
				accumulatedCosts[node.getCol()][node.getRow()] + heuristicDistance(node, target))
		);
		openSet.add(start);
		
		while (!openSet.isEmpty()) {
			var current = openSet.poll();
			
			// Target reached
			if (current == target) {
				return reconstructPath(originsNodes, target);
			}
			
			for (var neighbor : current.getNeighbors()) {
				double tentativeG = accumulatedCosts[current.getCol()][current.getRow()] + 1;
				if (tentativeG < accumulatedCosts[neighbor.getCol()][neighbor.getRow()]) {
					if (tentativeG < this.getMaxPathLength()) {
						originsNodes[neighbor.getCol()][neighbor.getRow()] = current;
						accumulatedCosts[neighbor.getCol()][neighbor.getRow()] = tentativeG;
						// Re-add to update priority (standard A* approach)
						openSet.remove(neighbor);
						openSet.add(neighbor);
					} else {
						endNodes.add(current);
					}
				}
			}
		}

		// Reconstruct path to nearest reachable node if target not found
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
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__HEURISTIC_METHOD:
				return getHeuristicMethod();
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
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__HEURISTIC_METHOD:
				setHeuristicMethod((DistanceMethod)newValue);
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
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__HEURISTIC_METHOD:
				setHeuristicMethod(HEURISTIC_METHOD_EDEFAULT);
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
			case BehaviourPackage.ASTAR_PATH_CALCULATOR__HEURISTIC_METHOD:
				return heuristicMethod != HEURISTIC_METHOD_EDEFAULT;
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
		result.append(", heuristicMethod: ");
		result.append(heuristicMethod);
		result.append(')');
		return result.toString();
	}

} //AstarPathCalculatorImpl
