/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.common.util.EList;
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
 *   <li>{@link main.game.maze.behaviour.PatrolPoint#getPoint <em>Point</em>}</li>
 *   <li>{@link main.game.maze.behaviour.PatrolPoint#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositivePatrolPointCoords ValidHealthEventsProbability ValidSpeedEventsProbability ValidTimeEventsProbability ValidVisionEventsProbability ValidAttackEventsProbability'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositivePatrolPointCoords='self.point-&gt;forAll(p | p.posX &gt; 0 and p.posY &gt; 0)' ValidHealthEventsProbability='self.events-&gt;select(p | p.oclIsKindOf(HealthEvent))-&gt;collect(p | p.probability)-&gt;sum() &lt;= 100' ValidSpeedEventsProbability='self.events-&gt;select(p | p.oclIsKindOf(SpeedEvent))-&gt;collect(p | p.probability)-&gt;sum() &lt;= 100' ValidTimeEventsProbability='self.events-&gt;select(p | p.oclIsKindOf(TimeEvent))-&gt;collect(p | p.probability)-&gt;sum() &lt;= 100' ValidVisionEventsProbability='self.events-&gt;select(p | p.oclIsKindOf(VisionEvent))-&gt;collect(p | p.probability)-&gt;sum() &lt;= 100' ValidAttackEventsProbability='self.events-&gt;select(p | p.oclIsKindOf(AttackEvent))-&gt;collect(p | p.probability)-&gt;sum() &lt;= 100'"
 * @generated
 */
public interface PatrolPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Coordinates of the patrol step
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
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.behaviour.CharacterEvent}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * List of events that can occur when the enemy reaches this point.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see main.game.maze.behaviour.BehaviourPackage#getPatrolPoint_Events()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<CharacterEvent> getEvents();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void triggerEvents();

} // PatrolPoint


