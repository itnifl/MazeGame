/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Random Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.RandomBehavior#getRegenPerSecond <em>Regen Per Second</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getRandomBehavior()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositiveRegenPerSecond'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositiveRegenPerSecond='self.regenPerSecond &gt;= 0'"
 * @generated
 */
public interface RandomBehavior extends MovementBehavior {
	/**
	 * Returns the value of the '<em><b>Regen Per Second</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Amount of HP when regained per second.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Regen Per Second</em>' attribute.
	 * @see #setRegenPerSecond(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getRandomBehavior_RegenPerSecond()
	 * @model required="true"
	 * @generated
	 */
	double getRegenPerSecond();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.RandomBehavior#getRegenPerSecond <em>Regen Per Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Regen Per Second</em>' attribute.
	 * @see #getRegenPerSecond()
	 * @generated
	 */
	void setRegenPerSecond(double value);

} // RandomBehavior


