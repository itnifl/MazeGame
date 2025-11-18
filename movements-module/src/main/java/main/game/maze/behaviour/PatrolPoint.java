/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Patrol Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents a patrol step
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.PatrolPoint#getTime <em>Time</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolPoint#getPoint <em>Point</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolPoint#getRegenAmount <em>Regen Amount</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositiveRegenAmount PositivePatrolPointTime PositivePatrolPointCoords'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositiveRegenAmount='self.regenAmount &gt;= 0' PositivePatrolPointTime='self.time &gt;= 0' PositivePatrolPointCoords='self.point-&gt;forAll(p | p.posX &gt; 0 and p.posY &gt; 0)'"
 * @generated
 */
public interface PatrolPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint_Time()
	 * @model required="true"
	 * @generated
	 */
	int getTime();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolPoint#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(int value);

	/**
	 * Returns the value of the '<em><b>Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Coords of the patrol step
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Point</em>' containment reference.
	 * @see #setPoint(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint_Point()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Position getPoint();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolPoint#getPoint <em>Point</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point</em>' containment reference.
	 * @see #getPoint()
	 * @generated
	 */
	void setPoint(Position value);

	/**
	 * Returns the value of the '<em><b>Regen Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Amount of HP the character will regain when reaching this step.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Regen Amount</em>' attribute.
	 * @see #setRegenAmount(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint_RegenAmount()
	 * @model required="true"
	 * @generated
	 */
	double getRegenAmount();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolPoint#getRegenAmount <em>Regen Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Regen Amount</em>' attribute.
	 * @see #getRegenAmount()
	 * @generated
	 */
	void setRegenAmount(double value);

} // PatrolPoint
