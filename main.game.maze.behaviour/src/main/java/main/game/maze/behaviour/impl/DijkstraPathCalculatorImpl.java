/**
 */
package main.game.maze.behaviour.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.DijkstraPathCalculator;
import main.game.maze.behaviour.Position;
import main.game.maze.mazeworld.GameMazeWorld;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dijkstra Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl#getMaxPathLength <em>Max Path Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DijkstraPathCalculatorImpl extends PathCalculatorImpl implements DijkstraPathCalculator {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DijkstraPathCalculatorImpl() {
		super();
	}

	@Override
	public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node start, MazeNavigationGraph.Node target) {
		MazeNavigationGraph graph = GameMazeWorld.GetWorld().getNavigationGraph();
	
		int width  = graph.getGrid().length;
		int height = graph.getGrid()[0].length;
	
		int[][] accumulatedCosts = new int[width][height];
		MazeNavigationGraph.Node[][] originsNodes = new MazeNavigationGraph.Node[width][height];
		List<MazeNavigationGraph.Node> endNodes = new LinkedList<>();
	
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
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
				double newCost = accumulatedCosts[current.getCol()][current.getRow()] + 1;
				if (newCost < accumulatedCosts[node.getCol()][node.getRow()]) {
					if (newCost < this.getMaxPathLength()) {
						accumulatedCosts[node.getCol()][node.getRow()] = (int)newCost;
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
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.DIJKSTRA_PATH_CALCULATOR;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH, oldMaxPathLength, maxPathLength));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH:
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
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH:
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
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH:
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
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH:
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

} //DijkstraPathCalculatorImpl
