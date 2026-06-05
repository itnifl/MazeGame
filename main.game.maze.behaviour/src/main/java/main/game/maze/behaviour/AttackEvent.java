/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attack Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.AttackEvent#getRadiusPercentage <em>Radius Percentage</em>}</li>
 *   <li>{@link main.game.maze.behaviour.AttackEvent#getRadiusAmount <em>Radius Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.AttackEvent#getDamageAmount <em>Damage Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.AttackEvent#getDamagePercentage <em>Damage Percentage</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getAttackEvent()
 * @model
 * @generated
 */
public interface AttackEvent extends CharacterEvent {
	/**
	 * Returns the value of the '<em><b>Radius Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Radius Percentage</em>' attribute.
	 * @see #setRadiusPercentage(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getAttackEvent_RadiusPercentage()
	 * @model required="true"
	 * @generated
	 */
	double getRadiusPercentage();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.AttackEvent#getRadiusPercentage <em>Radius Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Radius Percentage</em>' attribute.
	 * @see #getRadiusPercentage()
	 * @generated
	 */
	void setRadiusPercentage(double value);

	/**
	 * Returns the value of the '<em><b>Radius Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Radius Amount</em>' attribute.
	 * @see #setRadiusAmount(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getAttackEvent_RadiusAmount()
	 * @model required="true"
	 * @generated
	 */
	double getRadiusAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.AttackEvent#getRadiusAmount <em>Radius Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Radius Amount</em>' attribute.
	 * @see #getRadiusAmount()
	 * @generated
	 */
	void setRadiusAmount(double value);

	/**
	 * Returns the value of the '<em><b>Damage Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Damage Amount</em>' attribute.
	 * @see #setDamageAmount(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getAttackEvent_DamageAmount()
	 * @model required="true"
	 * @generated
	 */
	int getDamageAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.AttackEvent#getDamageAmount <em>Damage Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Damage Amount</em>' attribute.
	 * @see #getDamageAmount()
	 * @generated
	 */
	void setDamageAmount(int value);

	/**
	 * Returns the value of the '<em><b>Damage Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Damage Percentage</em>' attribute.
	 * @see #setDamagePercentage(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getAttackEvent_DamagePercentage()
	 * @model required="true"
	 * @generated
	 */
	int getDamagePercentage();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.AttackEvent#getDamagePercentage <em>Damage Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Damage Percentage</em>' attribute.
	 * @see #getDamagePercentage()
	 * @generated
	 */
	void setDamagePercentage(int value);

} // AttackEvent


