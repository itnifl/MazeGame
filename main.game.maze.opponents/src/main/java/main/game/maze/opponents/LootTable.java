/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loot Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.LootTable#getItems <em>Items</em>}</li>
 *   <li>{@link main.game.maze.opponents.LootTable#getWeightCapacity <em>Weight Capacity</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getLootTable()
 * @model
 * @generated
 */
public interface LootTable extends EObject {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.opponents.LootItem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see main.game.maze.opponents.OpponentsPackage#getLootTable_Items()
	 * @model containment="true"
	 * @generated
	 */
	EList<LootItem> getItems();

	/**
	 * Returns the value of the '<em><b>Weight Capacity</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight Capacity</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getLootTable_WeightCapacity()
	 * @model default="1" required="true" upper="100"
	 * @generated
	 */
	EList<Integer> getWeightCapacity();

} // LootTable
