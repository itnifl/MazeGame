/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Health Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.HealthEvent#getHealthAmount <em>Health Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.HealthEvent#getHealthPercentage <em>Health Percentage</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getHealthEvent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositiveHealthAmount ValidHealthPercentage'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositiveHealthAmount='self.healthAmount &gt;= 0' ValidHealthPercentage='self.healthPercentage &gt;= 0 and self.healthPercentage &lt;= 100'"
 * @generated
 */
public interface HealthEvent extends CharacterEvent {
	/**
	 * Returns the value of the '<em><b>Health Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Health Amount</em>' attribute.
	 * @see #setHealthAmount(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getHealthEvent_HealthAmount()
	 * @model required="true"
	 * @generated
	 */
	int getHealthAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.HealthEvent#getHealthAmount <em>Health Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Health Amount</em>' attribute.
	 * @see #getHealthAmount()
	 * @generated
	 */
	void setHealthAmount(int value);

	/**
	 * Returns the value of the '<em><b>Health Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Health Percentage</em>' attribute.
	 * @see #setHealthPercentage(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getHealthEvent_HealthPercentage()
	 * @model required="true"
	 * @generated
	 */
	int getHealthPercentage();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.HealthEvent#getHealthPercentage <em>Health Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Health Percentage</em>' attribute.
	 * @see #getHealthPercentage()
	 * @generated
	 */
	void setHealthPercentage(int value);

} // HealthEvent
