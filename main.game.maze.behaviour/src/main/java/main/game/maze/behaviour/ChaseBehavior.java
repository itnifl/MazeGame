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
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='NotTooLargeAttackRadius'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot NotTooLargeAttackRadius='self.charactertype-&gt;select(p | p.oclIsKindOf(opp::RangedEnemy))-&gt;isEmpty() or\n\t\t\tlet rangedEnemy : opp::RangedEnemy = self.charactertype-&gt;any(true).oclAsType(opp::RangedEnemy),\n\t\t\t\trelativeTarget : Position = self.relativePositionTarget-&gt;any(true)\n\t\t\tin rangedEnemy.attackRange &gt;= relativeTarget.posX and rangedEnemy.attackRange &gt;= relativeTarget.posY'"
 * @generated
 */
public interface ChaseBehavior extends MovementBehavior {
	/**
	 * Returns the value of the '<em><b>Relative Position Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Position relative to the player to chase.
	 * If set to Position{posX=x,posY=y}, then the character will target the target is Position(Player.x+x, Player.y+y). 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relative Position Target</em>' containment reference.
	 * @see #setRelativePositionTarget(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getChaseBehavior_RelativePositionTarget()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Position getRelativePositionTarget();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget <em>Relative Position Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relative Position Target</em>' containment reference.
	 * @see #getRelativePositionTarget()
	 * @generated
	 */
	void setRelativePositionTarget(Position value);

	/**
	 * Returns the value of the '<em><b>Pathcalculator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pathcalculator</em>' containment reference.
	 * @see #setPathcalculator(PathCalculator)
	 * @see main.game.maze.behaviour.BehaviourPackage#getChaseBehavior_Pathcalculator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PathCalculator getPathcalculator();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.ChaseBehavior#getPathcalculator <em>Pathcalculator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pathcalculator</em>' containment reference.
	 * @see #getPathcalculator()
	 * @generated
	 */
	void setPathcalculator(PathCalculator value);

} // ChaseBehavior
