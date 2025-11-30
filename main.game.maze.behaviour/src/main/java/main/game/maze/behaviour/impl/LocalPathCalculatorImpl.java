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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.LOCAL_PATH_CALCULATOR;
	}

	@Override
	public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node origin, MazeNavigationGraph.Node target) {
		
		return null;
	}

} //LocalPathCalculatorImpl
