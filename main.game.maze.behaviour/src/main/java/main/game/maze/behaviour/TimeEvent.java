/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.TimeEvent#getTime <em>Time</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getTimeEvent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositivePatrolTime'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositivePatrolTime='self.time &gt;= 0'"
 * @generated
 */
public interface TimeEvent extends CharacterEvent {
	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getTimeEvent_Time()
	 * @model required="true"
	 * @generated
	 */
	int getTime();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.TimeEvent#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(int value);

} // TimeEvent
