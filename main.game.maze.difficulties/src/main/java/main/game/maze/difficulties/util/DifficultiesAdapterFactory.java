/**
 */
package main.game.maze.difficulties.util;

import main.game.maze.difficulties.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.difficulties.DifficultiesPackage
 * @generated
 */
public class DifficultiesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DifficultiesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifficultiesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DifficultiesPackage.eINSTANCE;
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
	protected DifficultiesSwitch<Adapter> modelSwitch =
		new DifficultiesSwitch<Adapter>() {
			@Override
			public Adapter caseDifficultyGameData(DifficultyGameData object) {
				return createDifficultyGameDataAdapter();
			}
			@Override
			public Adapter caseEasyDifficulty(EasyDifficulty object) {
				return createEasyDifficultyAdapter();
			}
			@Override
			public Adapter caseDifficulty(Difficulty object) {
				return createDifficultyAdapter();
			}
			@Override
			public Adapter caseNormalDifficulty(NormalDifficulty object) {
				return createNormalDifficultyAdapter();
			}
			@Override
			public Adapter caseHardDifficulty(HardDifficulty object) {
				return createHardDifficultyAdapter();
			}
			@Override
			public Adapter caseEnemyMaxCount(EnemyMaxCount object) {
				return createEnemyMaxCountAdapter();
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
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.DifficultyGameData <em>Difficulty Game Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.DifficultyGameData
	 * @generated
	 */
	public Adapter createDifficultyGameDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.EasyDifficulty <em>Easy Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.EasyDifficulty
	 * @generated
	 */
	public Adapter createEasyDifficultyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.Difficulty <em>Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.Difficulty
	 * @generated
	 */
	public Adapter createDifficultyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.NormalDifficulty <em>Normal Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.NormalDifficulty
	 * @generated
	 */
	public Adapter createNormalDifficultyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.HardDifficulty <em>Hard Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.HardDifficulty
	 * @generated
	 */
	public Adapter createHardDifficultyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.difficulties.EnemyMaxCount <em>Enemy Max Count</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.difficulties.EnemyMaxCount
	 * @generated
	 */
	public Adapter createEnemyMaxCountAdapter() {
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

} //DifficultiesAdapterFactory


