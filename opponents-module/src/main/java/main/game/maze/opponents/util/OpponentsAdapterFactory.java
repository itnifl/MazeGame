/**
 */
package main.game.maze.opponents.util;

import main.game.maze.opponents.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.opponents.OpponentsPackage
 * @generated
 */
public class OpponentsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OpponentsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpponentsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OpponentsPackage.eINSTANCE;
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
	protected OpponentsSwitch<Adapter> modelSwitch =
		new OpponentsSwitch<Adapter>() {
			@Override
			public Adapter caseOpponentModel(OpponentModel object) {
				return createOpponentModelAdapter();
			}
			@Override
			public Adapter caseCharacterType(CharacterType object) {
				return createCharacterTypeAdapter();
			}
			@Override
			public Adapter caseZombie(Zombie object) {
				return createZombieAdapter();
			}
			@Override
			public Adapter caseLootTable(LootTable object) {
				return createLootTableAdapter();
			}
			@Override
			public Adapter caseLootItem(LootItem object) {
				return createLootItemAdapter();
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
	 * Creates a new adapter for an object of class '{@link main.game.maze.opponents.OpponentModel <em>Opponent Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.opponents.OpponentModel
	 * @generated
	 */
	public Adapter createOpponentModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.opponents.CharacterType <em>Character Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.opponents.CharacterType
	 * @generated
	 */
	public Adapter createCharacterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.opponents.Zombie <em>Zombie</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.opponents.Zombie
	 * @generated
	 */
	public Adapter createZombieAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.opponents.LootTable <em>Loot Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.opponents.LootTable
	 * @generated
	 */
	public Adapter createLootTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link main.game.maze.opponents.LootItem <em>Loot Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see main.game.maze.opponents.LootItem
	 * @generated
	 */
	public Adapter createLootItemAdapter() {
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

} //OpponentsAdapterFactory
