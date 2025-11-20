/**
 */
package main.game.maze.behaviour.util;

import main.game.maze.behaviour.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.behaviour.BehaviourPackage
 * @generated
 */
public class BehaviourAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BehaviourPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BehaviourPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BehaviourSwitch<Adapter> modelSwitch =
		new BehaviourSwitch<Adapter>() {
			@Override
			public Adapter caseRandomBehavior(RandomBehavior object) {
				return createRandomBehaviorAdapter();
			}
			@Override
			public Adapter casePatrolBehavior(PatrolBehavior object) {
				return createPatrolBehaviorAdapter();
			}
			@Override
			public Adapter caseChaseBehavior(ChaseBehavior object) {
				return createChaseBehaviorAdapter();
			}
			@Override
			public Adapter caseMovementBehavior(MovementBehavior object) {
				return createMovementBehaviorAdapter();
			}
			@Override
			public Adapter casePosition(Position object) {
				return createPositionAdapter();
			}
			@Override
			public Adapter casePatrolPoint(PatrolPoint object) {
				return createPatrolPointAdapter();
			}
			@Override
			public Adapter casePathCalculator(PathCalculator object) {
				return createPathCalculatorAdapter();
			}
			@Override
			public Adapter caseDijkstraPathCalculator(DijkstraPathCalculator object) {
				return createDijkstraPathCalculatorAdapter();
			}
			@Override
			public Adapter caseAstarPathCalculator(AstarPathCalculator object) {
				return createAstarPathCalculatorAdapter();
			}
			@Override
			public Adapter caseLocalPathCalculator(LocalPathCalculator object) {
				return createLocalPathCalculatorAdapter();
			}
			@Override
			public Adapter casePatrolZone(PatrolZone object) {
				return createPatrolZoneAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.RandomBehavior <em>Random Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.RandomBehavior
	 * @generated
	 */
	public Adapter createRandomBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.PatrolBehavior <em>Patrol Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.PatrolBehavior
	 * @generated
	 */
	public Adapter createPatrolBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.ChaseBehavior <em>Chase Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.ChaseBehavior
	 * @generated
	 */
	public Adapter createChaseBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.MovementBehavior <em>Movement Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.MovementBehavior
	 * @generated
	 */
	public Adapter createMovementBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.Position
	 * @generated
	 */
	public Adapter createPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.PatrolPoint <em>Patrol Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.PatrolPoint
	 * @generated
	 */
	public Adapter createPatrolPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.PathCalculator <em>Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.PathCalculator
	 * @generated
	 */
	public Adapter createPathCalculatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.DijkstraPathCalculator <em>Dijkstra Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.DijkstraPathCalculator
	 * @generated
	 */
	public Adapter createDijkstraPathCalculatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.AstarPathCalculator <em>Astar Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.AstarPathCalculator
	 * @generated
	 */
	public Adapter createAstarPathCalculatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.LocalPathCalculator <em>Local Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.LocalPathCalculator
	 * @generated
	 */
	public Adapter createLocalPathCalculatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.behaviour.PatrolZone <em>Patrol Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.behaviour.PatrolZone
	 * @generated
	 */
	public Adapter createPatrolZoneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BehaviourAdapterFactory
