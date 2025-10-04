/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loot Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.LootItem#getName <em>Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.LootItem#getType <em>Type</em>}</li>
 *   <li>{@link main.game.maze.opponents.LootItem#getValue <em>Value</em>}</li>
 *   <li>{@link main.game.maze.opponents.LootItem#getWeight <em>Weight</em>}</li>
 *   <li>{@link main.game.maze.opponents.LootItem#getGraphicBase <em>Graphic Base</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getLootItem()
 * @model
 * @generated
 */
public interface LootItem extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getLootItem_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.LootItem#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"FOOD"</code>.
	 * The literals are from the enumeration {@link main.game.maze.opponents.LootItemType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see main.game.maze.opponents.LootItemType
	 * @see #setType(LootItemType)
	 * @see main.game.maze.opponents.OpponentsPackage#getLootItem_Type()
	 * @model default="FOOD" required="true"
	 * @generated
	 */
	LootItemType getType();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.LootItem#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see main.game.maze.opponents.LootItemType
	 * @see #getType()
	 * @generated
	 */
	void setType(LootItemType value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getLootItem_Value()
	 * @model
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.LootItem#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

	/**
	 * Returns the value of the '<em><b>Weight</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getLootItem_Weight()
	 * @model required="true" upper="10"
	 * @generated
	 */
	EList<Integer> getWeight();

	/**
	 * Returns the value of the '<em><b>Graphic Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphic Base</em>' attribute.
	 * @see #setGraphicBase(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getLootItem_GraphicBase()
	 * @model
	 * @generated
	 */
	String getGraphicBase();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.LootItem#getGraphicBase <em>Graphic Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphic Base</em>' attribute.
	 * @see #getGraphicBase()
	 * @generated
	 */
	void setGraphicBase(String value);

} // LootItem
