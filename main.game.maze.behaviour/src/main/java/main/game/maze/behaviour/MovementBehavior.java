/**
 */
package main.game.maze.behaviour;

import main.game.maze.opponents.CharacterType;
import org.eclipse.emf.common.util.EList;
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
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getCharactertype <em>Charactertype</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#isIgnoreWalls <em>Ignore Walls</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#isInstantKillOnCollision <em>Instant Kill On Collision</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getBaseVisionRange <em>Base Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getAdditionalVisionRange <em>Additional Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getVisionRangeMultiplier <em>Vision Range Multiplier</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getVisionRange <em>Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getNextPositions <em>Next Positions</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getPosition <em>Position</em>}</li>
 *   <li>{@link main.game.maze.behaviour.MovementBehavior#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidVisionRange'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ValidVisionRange='self.visionRange &gt; 0 and\n\t\t\tif self.charactertype-&gt;any(true).oclIsKindOf(opp::RangedEnemy) then\n\t\t\t \tlet rangedCharacter : opp::RangedEnemy = self.charactertype-&gt;any(true).oclAsType(opp::RangedEnemy)\n\t\t\t    in rangedCharacter.attackRange &lt;= self.visionRange\n\t\t\telse\n\t\t\t\ttrue\n\t\t\tendif'"
 * @generated
 */
public interface MovementBehavior extends EObject {
	/**
	 * Returns the value of the '<em><b>Charactertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Character on which those movements should apply.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Charactertype</em>' reference.
	 * @see #setCharactertype(CharacterType)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_Charactertype()
	 * @model required="true"
	 * @generated
	 */
	CharacterType getCharactertype();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getCharactertype <em>Charactertype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Charactertype</em>' reference.
	 * @see #getCharactertype()
	 * @generated
	 */
	void setCharactertype(CharacterType value);

	/**
	 * Returns the value of the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates if the movements should ignore walls.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ignore Walls</em>' attribute.
	 * @see #setIgnoreWalls(boolean)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_IgnoreWalls()
	 * @model required="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='\n\t\t\t\tif self.charactertype-&gt;any(true).oclIsKindOf(opp::Ghost) then\n\t\t\t\t\tlet ghostCharacter : opp::Ghost = self.charactertype-&gt;any(true).oclAsType(opp::Ghost)\n\t\t\t\t\tin ghostCharacter.nonTangibilityEnergy &gt;= 0\n\t\t\t\telse\n\t\t\t\t\tfalse\n\t\t\t\tendif'"
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
	 * Returns the value of the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates if the player should be killed when it collides this character.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Instant Kill On Collision</em>' attribute.
	 * @see #setInstantKillOnCollision(boolean)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_InstantKillOnCollision()
	 * @model required="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='self.charactertype-&gt;any(true).threatLevel &gt; 100'"
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
	 * Returns the value of the '<em><b>Base Vision Range</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Vision Range</em>' attribute.
	 * @see #setBaseVisionRange(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_BaseVisionRange()
	 * @model default="100" required="true"
	 * @generated
	 */
	double getBaseVisionRange();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getBaseVisionRange <em>Base Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Vision Range</em>' attribute.
	 * @see #getBaseVisionRange()
	 * @generated
	 */
	void setBaseVisionRange(double value);

	/**
	 * Returns the value of the '<em><b>Additional Vision Range</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Vision Range</em>' attribute.
	 * @see #setAdditionalVisionRange(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_AdditionalVisionRange()
	 * @model default="100" required="true"
	 * @generated
	 */
	double getAdditionalVisionRange();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getAdditionalVisionRange <em>Additional Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Vision Range</em>' attribute.
	 * @see #getAdditionalVisionRange()
	 * @generated
	 */
	void setAdditionalVisionRange(double value);

	/**
	 * Returns the value of the '<em><b>Vision Range Multiplier</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vision Range Multiplier</em>' attribute.
	 * @see #setVisionRangeMultiplier(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_VisionRangeMultiplier()
	 * @model default="1" required="true"
	 * @generated
	 */
	double getVisionRangeMultiplier();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getVisionRangeMultiplier <em>Vision Range Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vision Range Multiplier</em>' attribute.
	 * @see #getVisionRangeMultiplier()
	 * @generated
	 */
	void setVisionRangeMultiplier(double value);

	/**
	 * Returns the value of the '<em><b>Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Defines the distance the enemy can see the player.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Vision Range</em>' attribute.
	 * @see #setVisionRange(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_VisionRange()
	 * @model required="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot derivation='self.baseVisionRange * self.visionRangeMultiplier + self.additionalVisionRange'"
	 * @generated
	 */
	double getVisionRange();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getVisionRange <em>Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vision Range</em>' attribute.
	 * @see #getVisionRange()
	 * @generated
	 */
	void setVisionRange(double value);

	/**
	 * Returns the value of the '<em><b>Next Positions</b></em>' reference list.
	 * The list contents are of type {@link main.game.maze.behaviour.Position}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For dynamic purpose. Stores the list of points to the next step. Should be frequently updated.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Next Positions</em>' reference list.
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_NextPositions()
	 * @model
	 * @generated
	 */
	EList<Position> getNextPositions();

	/**
	 * Returns the value of the '<em><b>Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For dynamic purpose. Stores the current position of the enemy.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Position</em>' reference.
	 * @see #setPosition(Position)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_Position()
	 * @model
	 * @generated
	 */
	Position getPosition();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getPosition <em>Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Position value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For dynamic purpose. Stores the current direction of the enemy.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Direction</em>' reference.
	 * @see #setDirection(Direction)
	 * @see main.game.maze.behaviour.BehaviourPackage#getMovementBehavior_Direction()
	 * @model
	 * @generated
	 */
	Direction getDirection();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.MovementBehavior#getDirection <em>Direction</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' reference.
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(Direction value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void move();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model healthEventRequired="true"
	 * @generated
	 */
	void update(HealthEvent healthEvent);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model speedEventRequired="true"
	 * @generated
	 */
	void update(SpeedEvent speedEvent);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model timeEventRequired="true"
	 * @generated
	 */
	void update(TimeEvent timeEvent);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model attackEventRequired="true"
	 * @generated
	 */
	void update(AttackEvent attackEvent);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visionEventRequired="true"
	 * @generated
	 */
	void update(VisionEvent visionEvent);

} // MovementBehavior
