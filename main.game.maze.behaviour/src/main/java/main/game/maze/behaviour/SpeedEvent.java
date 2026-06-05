/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Speed Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.SpeedEvent#getSpeedAmount <em>Speed Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.SpeedEvent#getSpeedPercentage <em>Speed Percentage</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getSpeedEvent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidSpeedPercentage'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ValidSpeedPercentage='self.speedPercentage &gt;= -100 and self.speedPercentage &lt;= 100'"
 * @generated
 */
public interface SpeedEvent extends CharacterEvent {
	/**
	 * Returns the value of the '<em><b>Speed Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speed Amount</em>' attribute.
	 * @see #setSpeedAmount(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getSpeedEvent_SpeedAmount()
	 * @model required="true"
	 * @generated
	 */
	double getSpeedAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.SpeedEvent#getSpeedAmount <em>Speed Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed Amount</em>' attribute.
	 * @see #getSpeedAmount()
	 * @generated
	 */
	void setSpeedAmount(double value);

	/**
	 * Returns the value of the '<em><b>Speed Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speed Percentage</em>' attribute.
	 * @see #setSpeedPercentage(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getSpeedEvent_SpeedPercentage()
	 * @model required="true"
	 * @generated
	 */
	double getSpeedPercentage();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.SpeedEvent#getSpeedPercentage <em>Speed Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed Percentage</em>' attribute.
	 * @see #getSpeedPercentage()
	 * @generated
	 */
	void setSpeedPercentage(double value);

} // SpeedEvent


