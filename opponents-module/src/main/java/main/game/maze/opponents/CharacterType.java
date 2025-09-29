/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Character Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.CharacterType#getId <em>Id</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#getHealth <em>Health</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#getSpeed <em>Speed</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#getThreatLevel <em>Threat Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.CharacterType#getEffectiveThreat <em>Effective Threat</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType()
 * @model abstract="true"
 * @generated
 */
public interface CharacterType extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_Enabled()
	 * @model default="true"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Health</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Health</em>' attribute.
	 * @see #setHealth(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_Health()
	 * @model default="100"
	 * @generated
	 */
	int getHealth();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#getHealth <em>Health</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Health</em>' attribute.
	 * @see #getHealth()
	 * @generated
	 */
	void setHealth(int value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute.
	 * The default value is <code>"1.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_Speed()
	 * @model default="1.0"
	 * @generated
	 */
	double getSpeed();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#getSpeed <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(double value);

	/**
	 * Returns the value of the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threat Level</em>' attribute.
	 * @see #setThreatLevel(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_ThreatLevel()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	double getThreatLevel();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.CharacterType#getThreatLevel <em>Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threat Level</em>' attribute.
	 * @see #getThreatLevel()
	 * @generated
	 */
	void setThreatLevel(double value);

	/**
	 * Returns the value of the '<em><b>Effective Threat</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Effective Threat</em>' attribute.
	 * @see main.game.maze.opponents.OpponentsPackage#getCharacterType_EffectiveThreat()
	 * @model default="1" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getEffectiveThreat();

} // CharacterType
