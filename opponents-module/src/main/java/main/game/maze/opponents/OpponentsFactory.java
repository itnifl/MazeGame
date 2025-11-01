/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.opponents.OpponentsPackage
 * @generated
 */
public interface OpponentsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OpponentsFactory eINSTANCE = main.game.maze.opponents.impl.OpponentsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Opponent Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Opponent Model</em>'.
	 * @generated
	 */
	OpponentModel createOpponentModel();

	/**
	 * Returns a new object of class '<em>Zombie</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Zombie</em>'.
	 * @generated
	 */
	Zombie createZombie();

	/**
	 * Returns a new object of class '<em>Loot Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Loot Table</em>'.
	 * @generated
	 */
	LootTable createLootTable();

	/**
	 * Returns a new object of class '<em>Loot Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Loot Item</em>'.
	 * @generated
	 */
	LootItem createLootItem();

	/**
	 * Returns a new object of class '<em>Ghost</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ghost</em>'.
	 * @generated
	 */
	Ghost createGhost();

	/**
	 * Returns a new object of class '<em>Pumpkin Bomber</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pumpkin Bomber</em>'.
	 * @generated
	 */
	PumpkinBomber createPumpkinBomber();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	OpponentsPackage getOpponentsPackage();

} //OpponentsFactory
