/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Patrol Zone</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.PatrolZone#getWidth <em>Width</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolZone#getHeight <em>Height</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolZone#getTopLeft <em>Top Left</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolZone()
 * @model
 * @generated
 */
public interface PatrolZone extends EObject {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolZone_Width()
	 * @model required="true"
	 * @generated
	 */
	double getWidth();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolZone#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(double value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolZone_Height()
	 * @model required="true"
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolZone#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

	/**
	 * Returns the value of the '<em><b>Top Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Top left corner of the rectangular patrol zone
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Top Left</em>' reference.
	 * @see #setTopLeft(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolZone_TopLeft()
	 * @model required="true"
	 * @generated
	 */
	Position getTopLeft();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolZone#getTopLeft <em>Top Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Left</em>' reference.
	 * @see #getTopLeft()
	 * @generated
	 */
	void setTopLeft(Position value);

} // PatrolZone
