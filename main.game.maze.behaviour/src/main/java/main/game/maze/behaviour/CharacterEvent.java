/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Character Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.CharacterEvent#getProbability <em>Probability</em>}</li>
 *   <li>{@link main.game.maze.behaviour.CharacterEvent#getSubscriber <em>Subscriber</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getCharacterEvent()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidProbability'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ValidProbability='self.probability &gt;= 0 and self.probability &lt;= 100'"
 * @generated
 */
public interface CharacterEvent extends EObject {
	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Probability in percentage of this event to happen.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getCharacterEvent_Probability()
	 * @model required="true"
	 * @generated
	 */
	double getProbability();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.CharacterEvent#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(double value);

	/**
	 * Returns the value of the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Set of movement behaviors to notify when this event is triggered.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Subscriber</em>' reference.
	 * @see #setSubscriber(MovementBehavior)
	 * @see main.game.maze.behaviour.BehaviourPackage#getCharacterEvent_Subscriber()
	 * @model required="true"
	 * @generated
	 */
	MovementBehavior getSubscriber();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.CharacterEvent#getSubscriber <em>Subscriber</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscriber</em>' reference.
	 * @see #getSubscriber()
	 * @generated
	 */
	void setSubscriber(MovementBehavior value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void notifySubscribers();

} // CharacterEvent
