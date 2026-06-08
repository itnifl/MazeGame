/**
 */
package main.game.maze.difficulties;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Difficulty</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.Difficulty#isInstantDeath <em>Instant Death</em>}</li>
 *   <li>{@link main.game.maze.difficulties.Difficulty#getEnemyMaxCount <em>Enemy Max Count</em>}</li>
 *   <li>{@link main.game.maze.difficulties.Difficulty#getMonstersMovementSpeedMultiplier <em>Monsters Movement Speed Multiplier</em>}</li>
 *   <li>{@link main.game.maze.difficulties.Difficulty#getMonstersDamageMultiplier <em>Monsters Damage Multiplier</em>}</li>
 *   <li>{@link main.game.maze.difficulties.Difficulty#getMaxThreat <em>Max Threat</em>}</li>
 * </ul>
 *
 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='MaxThreatNonNegative'"
 * @generated
 */
public interface Difficulty extends EObject {
	/**
	 * Returns the value of the '<em><b>Instant Death</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instant Death</em>' attribute.
	 * @see #setInstantDeath(boolean)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty_InstantDeath()
	 * @model
	 * @generated
	 */
	boolean isInstantDeath();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.Difficulty#isInstantDeath <em>Instant Death</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instant Death</em>' attribute.
	 * @see #isInstantDeath()
	 * @generated
	 */
	void setInstantDeath(boolean value);

	/**
	 * Returns the value of the '<em><b>Enemy Max Count</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.difficulties.EnemyMaxCount}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enemy Max Count</em>' containment reference list.
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty_EnemyMaxCount()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<EnemyMaxCount> getEnemyMaxCount();

	/**
	 * Returns the value of the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Monsters Movement Speed Multiplier</em>' attribute.
	 * @see #setMonstersMovementSpeedMultiplier(double)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty_MonstersMovementSpeedMultiplier()
	 * @model
	 * @generated
	 */
	double getMonstersMovementSpeedMultiplier();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.Difficulty#getMonstersMovementSpeedMultiplier <em>Monsters Movement Speed Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Monsters Movement Speed Multiplier</em>' attribute.
	 * @see #getMonstersMovementSpeedMultiplier()
	 * @generated
	 */
	void setMonstersMovementSpeedMultiplier(double value);

	/**
	 * Returns the value of the '<em><b>Monsters Damage Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Monsters Damage Multiplier</em>' attribute.
	 * @see #setMonstersDamageMultiplier(double)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty_MonstersDamageMultiplier()
	 * @model
	 * @generated
	 */
	double getMonstersDamageMultiplier();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.Difficulty#getMonstersDamageMultiplier <em>Monsters Damage Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Monsters Damage Multiplier</em>' attribute.
	 * @see #getMonstersDamageMultiplier()
	 * @generated
	 */
	void setMonstersDamageMultiplier(double value);

	/**
	 * Returns the value of the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Threat</em>' attribute.
	 * @see #setMaxThreat(int)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficulty_MaxThreat()
	 * @model
	 * @generated
	 */
	int getMaxThreat();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.Difficulty#getMaxThreat <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Threat</em>' attribute.
	 * @see #getMaxThreat()
	 * @generated
	 */
	void setMaxThreat(int value);

} // Difficulty
