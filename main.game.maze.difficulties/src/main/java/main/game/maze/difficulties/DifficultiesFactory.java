/**
 */
package main.game.maze.difficulties;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.difficulties.DifficultiesPackage
 * @generated
 */
public interface DifficultiesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DifficultiesFactory eINSTANCE = main.game.maze.difficulties.impl.DifficultiesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Difficulty Game Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Difficulty Game Data</em>'.
	 * @generated
	 */
	DifficultyGameData createDifficultyGameData();

	/**
	 * Returns a new object of class '<em>Easy Difficulty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Easy Difficulty</em>'.
	 * @generated
	 */
	EasyDifficulty createEasyDifficulty();

	/**
	 * Returns a new object of class '<em>Normal Difficulty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Normal Difficulty</em>'.
	 * @generated
	 */
	NormalDifficulty createNormalDifficulty();

	/**
	 * Returns a new object of class '<em>Hard Difficulty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hard Difficulty</em>'.
	 * @generated
	 */
	HardDifficulty createHardDifficulty();

	/**
	 * Returns a new object of class '<em>Enemy Max Count</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enemy Max Count</em>'.
	 * @generated
	 */
	EnemyMaxCount createEnemyMaxCount();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DifficultiesPackage getDifficultiesPackage();

} //DifficultiesFactory
