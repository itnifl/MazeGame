/**
 */
package main.game.maze.opponents;

import main.game.maze.difficulties.Difficulty;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Opponent Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.OpponentModel#getName <em>Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.OpponentModel#getCharacterTypes <em>Character Types</em>}</li>
 *   <li>{@link main.game.maze.opponents.OpponentModel#getMaxThreat <em>Max Threat</em>}</li>
 *   <li>{@link main.game.maze.opponents.OpponentModel#getSelectedDifficulty <em>Selected Difficulty</em>}</li>
 *   <li>{@link main.game.maze.opponents.OpponentModel#getGameSetCurrentThreatLevel <em>Game Set Current Threat Level</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='validateMaxThreat'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL validateMaxThreat='self.characterTypes.threatLevel-&gt;sum() &lt;= self.maxThreat.oclAsType(Real)'"
 * @generated
 */
public interface OpponentModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.OpponentModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Character Types</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.opponents.CharacterType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Character Types</em>' containment reference list.
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_CharacterTypes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<CharacterType> getCharacterTypes();

	/**
	 * Returns the value of the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Threat</em>' attribute.
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_MaxThreat()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL derivation='if self.selectedDifficulty = null then 20 else self.selectedDifficulty.maxThreat endif'"
	 * @generated
	 */
	int getMaxThreat();

	/**
	 * Returns the value of the '<em><b>Selected Difficulty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Difficulty</em>' reference.
	 * @see #setSelectedDifficulty(Difficulty)
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_SelectedDifficulty()
	 * @model
	 * @generated
	 */
	Difficulty getSelectedDifficulty();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.OpponentModel#getSelectedDifficulty <em>Selected Difficulty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selected Difficulty</em>' reference.
	 * @see #getSelectedDifficulty()
	 * @generated
	 */
	void setSelectedDifficulty(Difficulty value);

	/**
	 * Returns the value of the '<em><b>Game Set Current Threat Level</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Game Set Current Threat Level</em>' attribute.
	 * @see #setGameSetCurrentThreatLevel(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_GameSetCurrentThreatLevel()
	 * @model default="0" required="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL derivation='self.characterTypes.threatLevel-&gt;sum()'"
	 * @generated
	 */
	double getGameSetCurrentThreatLevel();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.OpponentModel#getGameSetCurrentThreatLevel <em>Game Set Current Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Game Set Current Threat Level</em>' attribute.
	 * @see #getGameSetCurrentThreatLevel()
	 * @generated
	 */
	void setGameSetCurrentThreatLevel(double value);

} // OpponentModel
