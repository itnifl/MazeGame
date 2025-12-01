/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.LocalPathCalculator;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class LocalPathCalculatorImpl extends PathCalculatorImpl implements LocalPathCalculator {
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

} //LocalPathCalculatorImpl
