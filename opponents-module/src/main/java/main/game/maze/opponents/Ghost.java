/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ghost</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.Ghost#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.Ghost#getBehavior <em>Behavior</em>}</li>
 *   <li>{@link main.game.maze.opponents.Ghost#getVisibilityLevel <em>Visibility Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.Ghost#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getGhost()
 * @model
 * @generated
 */
public interface Ghost extends CharacterType {
	/**
	 * Returns the value of the '<em><b>Attack Damage</b></em>' attribute.
	 * The default value is <code>"10"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Damage</em>' attribute.
	 * @see #setAttackDamage(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_AttackDamage()
	 * @model default="10"
	 * @generated
	 */
	int getAttackDamage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Ghost#getAttackDamage <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Damage</em>' attribute.
	 * @see #getAttackDamage()
	 * @generated
	 */
	void setAttackDamage(int value);

	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * The default value is <code>"WANDER"</code>.
	 * The literals are from the enumeration {@link main.game.maze.opponents.BehaviorType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.opponents.BehaviorType
	 * @see #setBehavior(BehaviorType)
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_Behavior()
	 * @model default="WANDER"
	 * @generated
	 */
	BehaviorType getBehavior();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Ghost#getBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.opponents.BehaviorType
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(BehaviorType value);

	/**
	 * Returns the value of the '<em><b>Visibility Level</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility Level</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_VisibilityLevel()
	 * @model default="100" upper="100"
	 * @generated
	 */
	EList<Integer> getVisibilityLevel();

	/**
	 * Returns the value of the '<em><b>Non Tangibility Energy</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Tangibility Energy</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_NonTangibilityEnergy()
	 * @model default="100" upper="100"
	 * @generated
	 */
	EList<Integer> getNonTangibilityEnergy();

} // Ghost
