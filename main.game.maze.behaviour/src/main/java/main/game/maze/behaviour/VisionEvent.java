/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vision Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.VisionEvent#getRadiusAmount <em>Radius Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.VisionEvent#getRadiusPercentage <em>Radius Percentage</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getVisionEvent()
 * @model
 * @generated
 */
public interface VisionEvent extends CharacterEvent {
	/**
	 * Returns the value of the '<em><b>Radius Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Radius Amount</em>' attribute.
	 * @see #setRadiusAmount(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getVisionEvent_RadiusAmount()
	 * @model required="true"
	 * @generated
	 */
	double getRadiusAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.VisionEvent#getRadiusAmount <em>Radius Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Radius Amount</em>' attribute.
	 * @see #getRadiusAmount()
	 * @generated
	 */
	void setRadiusAmount(double value);

	/**
	 * Returns the value of the '<em><b>Radius Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Radius Percentage</em>' attribute.
	 * @see #setRadiusPercentage(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getVisionEvent_RadiusPercentage()
	 * @model required="true"
	 * @generated
	 */
	double getRadiusPercentage();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.VisionEvent#getRadiusPercentage <em>Radius Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Radius Percentage</em>' attribute.
	 * @see #getRadiusPercentage()
	 * @generated
	 */
	void setRadiusPercentage(double value);

} // VisionEvent


