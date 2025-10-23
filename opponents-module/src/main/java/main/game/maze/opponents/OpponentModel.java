/**
 */
package main.game.maze.opponents;

import java.util.Map;
import main.game.maze.difficulties.DifficultyGameData;
import org.eclipse.emf.common.util.DiagnosticChain;
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
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore"
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
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getMaxThreat();

	/**
	 * Returns the value of the '<em><b>Selected Difficulty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Difficulty</em>' reference.
	 * @see #setSelectedDifficulty(DifficultyGameData)
	 * @see main.game.maze.opponents.OpponentsPackage#getOpponentModel_SelectedDifficulty()
	 * @model
	 * @generated
	 */
	DifficultyGameData getSelectedDifficulty();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.OpponentModel#getSelectedDifficulty <em>Selected Difficulty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selected Difficulty</em>' reference.
	 * @see #getSelectedDifficulty()
	 * @generated
	 */
	void setSelectedDifficulty(DifficultyGameData value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='self.characterTypes-&gt;collect(ct | ct.effectiveThreat)-&gt;sum() &lt;= self.maxThreat'"
	 * @generated
	 */
	boolean validateMaxThreat(DiagnosticChain diagnostics, Map<Object, Object> context);

} // OpponentModel
