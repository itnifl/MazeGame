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
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getBehavior <em>Behavior</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolBehavior#getPatrolZone <em>Patrol Zone</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RequiresOnePatrolPoint ValidStardIndex ValidPatrolZone'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot RequiresOnePatrolPoint='self.path-&gt;size() &gt; 0' ValidStardIndex='self.currentIndex &gt;= 0 and self.currentIndex &lt; self.path-&gt;size()' ValidPatrolZone='self.patrolZone-&gt;size() = 0 or\n\t\t\tlet width : ecore::EDouble = self.patrolZone-&gt;collect(z | z.width)-&gt;any(true),\n\t\t\t    height : ecore::EDouble = self.patrolZone-&gt;collect(z | z.height)-&gt;any(true),\n\t\t\t    topLeft : Position = self.patrolZone-&gt;collect(z | z.topLeft)-&gt;any(true)\n\t\t\tin self.path-&gt;forAll(pp | pp.point-&gt;any(p | \n\t\t\t\tp.posX &gt;= topLeft.posX and\n\t\t\t\tp.posY &gt;= topLeft.posY and\n\t\t\t\tp.posX &lt;= topLeft.posX + width and\n\t\t\t\tp.posY &lt;= topLeft.posY + height\n\t\t))'"
 * @generated
 */
public interface PatrolBehavior extends MovementBehavior {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.behaviour.PatrolPoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of patrol steps. If only one is present, the character should stay at this step and not move at all.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' containment reference list.
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_Path()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<PatrolPoint> getPath();

	/**
	 * Returns the value of the '<em><b>Current Index</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The index in the path describing the point the character should start at
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Current Index</em>' attribute.
	 * @see #setCurrentIndex(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_CurrentIndex()
	 * @model default="0" required="true"
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
	 * Returns the value of the '<em><b>Pathcalculator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The calculator used to compute movements to the next point
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pathcalculator</em>' containment reference.
	 * @see #setPathcalculator(PathCalculator)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_Pathcalculator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PathCalculator getPathcalculator();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolBehavior#getPathcalculator <em>Pathcalculator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pathcalculator</em>' containment reference.
	 * @see #getPathcalculator()
	 * @generated
	 */
	void setPathcalculator(PathCalculator value);

	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * The literals are from the enumeration {@link main.game.maze.behaviour.PatrolPathBehavior}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How to handle the path list
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.behaviour.PatrolPathBehavior
	 * @see #setBehavior(PatrolPathBehavior)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolBehavior_Behavior()
	 * @model
	 * @generated
	 */
	PatrolPathBehavior getBehavior();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PatrolBehavior#getBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.behaviour.PatrolPathBehavior
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(PatrolPathBehavior value);

	/**
	 * Returns the value of the '<em><b>Patrol Zone</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of 1 or 0 patrol zone. It is optional
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void nextIndex();

} // PatrolBehavior


