/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Direction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.Direction#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link main.game.maze.behaviour.Direction#getEndPosition <em>End Position</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getDirection()
 * @model
 * @generated
 */
public interface Direction extends EObject {
	/**
	 * Returns the value of the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Position</em>' reference.
	 * @see #setStartPosition(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getDirection_StartPosition()
	 * @model required="true"
	 * @generated
	 */
	Position getStartPosition();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.Direction#getStartPosition <em>Start Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Position</em>' reference.
	 * @see #getStartPosition()
	 * @generated
	 */
	void setStartPosition(Position value);

	/**
	 * Returns the value of the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Position</em>' reference.
	 * @see #setEndPosition(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getDirection_EndPosition()
	 * @model required="true"
	 * @generated
	 */
	Position getEndPosition();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.Direction#getEndPosition <em>End Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Position</em>' reference.
	 * @see #getEndPosition()
	 * @generated
	 */
	void setEndPosition(Position value);

} // Direction


