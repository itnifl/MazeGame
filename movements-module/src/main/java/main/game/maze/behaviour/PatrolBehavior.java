/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Patrol Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getPath <em>Path</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getCurrentIndex <em>Current Index</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getPathcalculator <em>Pathcalculator</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getPatrolZone <em>Patrol Zone</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior()
 * @model
 * @generated
 */
public interface PatrolBehavior extends MovementBehavior {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' reference list.
	 * The list contents are of type {@link main.game.maze.behaviour.PatrolPoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of patrol steps.
	 * If only one is present, the character should stay at this step and not move at all.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' reference list.
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_Path()
	 * @model required="true"
	 * @generated
	 */
	EList<PatrolPoint> getPath();

	/**
	 * Returns the value of the '<em><b>Current Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Index</em>' attribute.
	 * @see #setCurrentIndex(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_CurrentIndex()
	 * @model required="true"
	 * @generated
	 */
	int getCurrentIndex();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolBehavior#getCurrentIndex <em>Current Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Index</em>' attribute.
	 * @see #getCurrentIndex()
	 * @generated
	 */
	void setCurrentIndex(int value);

	/**
	 * Returns the value of the '<em><b>Pathcalculator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pathcalculator</em>' reference.
	 * @see #setPathcalculator(PathCalculator)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_Pathcalculator()
	 * @model required="true"
	 * @generated
	 */
	PathCalculator getPathcalculator();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolBehavior#getPathcalculator <em>Pathcalculator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pathcalculator</em>' reference.
	 * @see #getPathcalculator()
	 * @generated
	 */
	void setPathcalculator(PathCalculator value);

	/**
	 * Returns the value of the '<em><b>Patrol Zone</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of 1 or 0 patrol zone
	 * It is optional
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Patrol Zone</em>' reference.
	 * @see #setPatrolZone(PatrolZone)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_PatrolZone()
	 * @model
	 * @generated
	 */
	PatrolZone getPatrolZone();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolBehavior#getPatrolZone <em>Patrol Zone</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Patrol Zone</em>' reference.
	 * @see #getPatrolZone()
	 * @generated
	 */
	void setPatrolZone(PatrolZone value);

} // PatrolBehavior
