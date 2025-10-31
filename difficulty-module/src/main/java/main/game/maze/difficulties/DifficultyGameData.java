/**
 */
package main.game.maze.difficulties;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Difficulty Game Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.DifficultyGameData#getDifficulties <em>Difficulties</em>}</li>
 *   <li>{@link main.game.maze.difficulties.DifficultyGameData#getCurrentDifficulty <em>Current Difficulty</em>}</li>
 * </ul>
 *
 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficultyGameData()
 * @model
 * @generated
 */
public interface DifficultyGameData extends EObject {
	/**
	 * Returns the value of the '<em><b>Difficulties</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.difficulties.Difficulty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Difficulties</em>' containment reference list.
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficultyGameData_Difficulties()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Difficulty> getDifficulties();

	/**
	 * Returns the value of the '<em><b>Current Difficulty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Difficulty</em>' reference.
	 * @see #setCurrentDifficulty(Difficulty)
	 * @see main.game.maze.difficulties.DifficultiesPackage#getDifficultyGameData_CurrentDifficulty()
	 * @model
	 * @generated
	 */
	Difficulty getCurrentDifficulty();

	/**
	 * Sets the value of the '{@link main.game.maze.difficulties.DifficultyGameData#getCurrentDifficulty <em>Current Difficulty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Difficulty</em>' reference.
	 * @see #getCurrentDifficulty()
	 * @generated
	 */
	void setCurrentDifficulty(Difficulty value);

} // DifficultyGameData
