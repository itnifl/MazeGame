/**
 */
package main.game.maze.difficulties;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enemy Max Count</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.EnemyMaxCount#getType <em>Type</em>}</li>
 *   <li>{@link main.game.maze.difficulties.EnemyMaxCount#getMaxCount <em>Max Count</em>}</li>
 * </ul>
 *
 * @see main.game.maze.difficulties.DifficultiesPackage#getEnemyMaxCount()
 * @model
 * @generated
 */
public interface EnemyMaxCount extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link main.game.maze.difficulties.EnemyTypes}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see main.game.maze.difficulties.EnemyTypes
	 * @see #setType(EnemyTypes)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getEnemyMaxCount_Type()
	 * @model
	 * @generated
	 */
	EnemyTypes getType();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.EnemyMaxCount#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see main.game.maze.difficulties.EnemyTypes
	 * @see #getType()
	 * @generated
	 */
	void setType(EnemyTypes value);

	/**
	 * Returns the value of the '<em><b>Max Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Count</em>' attribute.
	 * @see #setMaxCount(int)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getEnemyMaxCount_MaxCount()
	 * @model
	 * @generated
	 */
	int getMaxCount();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.EnemyMaxCount#getMaxCount <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Count</em>' attribute.
	 * @see #getMaxCount()
	 * @generated
	 */
	void setMaxCount(int value);

} // EnemyMaxCount
