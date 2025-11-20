/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Movement Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The MovementBehavior contains the positions of the character in the maze and methods to update the position.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#isIgnoreWalls <em>Ignore Walls</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getAttackRadius <em>Attack Radius</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#isInstantKillOnCollision <em>Instant Kill On Collision</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getMovementSpeed <em>Movement Speed</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositiveMovementSpeed'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositiveMovementSpeed='self.movementSpeed &gt; 0'"
 * @generated
 */
public interface MovementBehavior extends EObject {
	/**
	 * Returns the value of the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates if the movements should ignore walls
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ignore Walls</em>' attribute.
	 * @see #setIgnoreWalls(boolean)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_IgnoreWalls()
	 * @model required="true"
	 * @generated
	 */
	boolean isIgnoreWalls();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#isIgnoreWalls <em>Ignore Walls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ignore Walls</em>' attribute.
	 * @see #isIgnoreWalls()
	 * @generated
	 */
	void setIgnoreWalls(boolean value);

	/**
	 * Returns the value of the '<em><b>Attack Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Radius from which the character can attack (for long range distance enemies).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attack Radius</em>' attribute.
	 * @see #setAttackRadius(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_AttackRadius()
	 * @model required="true"
	 * @generated
	 */
	double getAttackRadius();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getAttackRadius <em>Attack Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Radius</em>' attribute.
	 * @see #getAttackRadius()
	 * @generated
	 */
	void setAttackRadius(double value);

	/**
	 * Returns the value of the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates if the player should be killed when it collides this character.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Instant Kill On Collision</em>' attribute.
	 * @see #setInstantKillOnCollision(boolean)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_InstantKillOnCollision()
	 * @model required="true"
	 * @generated
	 */
	boolean isInstantKillOnCollision();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#isInstantKillOnCollision <em>Instant Kill On Collision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instant Kill On Collision</em>' attribute.
	 * @see #isInstantKillOnCollision()
	 * @generated
	 */
	void setInstantKillOnCollision(boolean value);

	/**
	 * Returns the value of the '<em><b>Movement Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Movement Speed</em>' attribute.
	 * @see #setMovementSpeed(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_MovementSpeed()
	 * @model required="true"
	 * @generated
	 */
	double getMovementSpeed();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getMovementSpeed <em>Movement Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Movement Speed</em>' attribute.
	 * @see #getMovementSpeed()
	 * @generated
	 */
	void setMovementSpeed(double value);

} // MovementBehavior
