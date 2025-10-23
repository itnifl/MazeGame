/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chase Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget <em>Relative Position Target</em>}</li>
 *   <li>{@link main.game.maze.behaviour.ChaseBehavior#getPathcalculator <em>Pathcalculator</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getChaseBehavior()
 * @model
 * @generated
 */
public interface ChaseBehavior extends MovementBehavior {
	/**
	 * Returns the value of the '<em><b>Relative Position Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Position relative to the player to chase.
	 * If set to Position{posX=x,posY=y}, then the character will target the target is Position(Player.x+x, Player.y+y). 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relative Position Target</em>' reference.
	 * @see #setRelativePositionTarget(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getChaseBehavior_RelativePositionTarget()
	 * @model required="true"
	 * @generated
	 */
	Position getRelativePositionTarget();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget <em>Relative Position Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relative Position Target</em>' reference.
	 * @see #getRelativePositionTarget()
	 * @generated
	 */
	void setRelativePositionTarget(Position value);

	/**
	 * Returns the value of the '<em><b>Pathcalculator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pathcalculator</em>' reference.
	 * @see #setPathcalculator(PathCalculator)
	 * @see main.game.maze.behaviour.BehaviourPackage#getChaseBehavior_Pathcalculator()
	 * @model required="true"
	 * @generated
	 */
	PathCalculator getPathcalculator();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.ChaseBehavior#getPathcalculator <em>Pathcalculator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pathcalculator</em>' reference.
	 * @see #getPathcalculator()
	 * @generated
	 */
	void setPathcalculator(PathCalculator value);

} // ChaseBehavior
