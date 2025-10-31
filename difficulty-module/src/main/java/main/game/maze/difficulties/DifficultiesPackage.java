/**
 */
package main.game.maze.difficulties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see main.game.maze.difficulties.DifficultiesFactory
 * @model kind="package"
 * @generated
 */
public interface DifficultiesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "difficulties";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://main.game.maze/difficulty";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "diff";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DifficultiesPackage eINSTANCE = main.game.maze.difficulties.impl.DifficultiesPackageImpl.init();

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.DifficultyGameDataImpl <em>Difficulty Game Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.DifficultyGameDataImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getDifficultyGameData()
	 * @generated
	 */
	int DIFFICULTY_GAME_DATA = 0;

	/**
	 * The feature id for the '<em><b>Difficulties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_GAME_DATA__DIFFICULTIES = 0;

	/**
	 * The feature id for the '<em><b>Current Difficulty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY = 1;

	/**
	 * The number of structural features of the '<em>Difficulty Game Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_GAME_DATA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Difficulty Game Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_GAME_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.DifficultyImpl <em>Difficulty</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.DifficultyImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getDifficulty()
	 * @generated
	 */
	int DIFFICULTY = 2;

	/**
	 * The feature id for the '<em><b>Instant Death</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__INSTANT_DEATH = 0;

	/**
	 * The feature id for the '<em><b>Enemy Max Count</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__ENEMY_MAX_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER = 2;

	/**
	 * The feature id for the '<em><b>Monsters Damage Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER = 3;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY__MAX_THREAT = 4;

	/**
	 * The number of structural features of the '<em>Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFFICULTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.EasyDifficultyImpl <em>Easy Difficulty</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.EasyDifficultyImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEasyDifficulty()
	 * @generated
	 */
	int EASY_DIFFICULTY = 1;

	/**
	 * The feature id for the '<em><b>Instant Death</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY__INSTANT_DEATH = DIFFICULTY__INSTANT_DEATH;

	/**
	 * The feature id for the '<em><b>Enemy Max Count</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY__ENEMY_MAX_COUNT = DIFFICULTY__ENEMY_MAX_COUNT;

	/**
	 * The feature id for the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER = DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Monsters Damage Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER = DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY__MAX_THREAT = DIFFICULTY__MAX_THREAT;

	/**
	 * The number of structural features of the '<em>Easy Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY_FEATURE_COUNT = DIFFICULTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Easy Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EASY_DIFFICULTY_OPERATION_COUNT = DIFFICULTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.NormalDifficultyImpl <em>Normal Difficulty</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.NormalDifficultyImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getNormalDifficulty()
	 * @generated
	 */
	int NORMAL_DIFFICULTY = 3;

	/**
	 * The feature id for the '<em><b>Instant Death</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY__INSTANT_DEATH = DIFFICULTY__INSTANT_DEATH;

	/**
	 * The feature id for the '<em><b>Enemy Max Count</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY__ENEMY_MAX_COUNT = DIFFICULTY__ENEMY_MAX_COUNT;

	/**
	 * The feature id for the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER = DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Monsters Damage Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER = DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY__MAX_THREAT = DIFFICULTY__MAX_THREAT;

	/**
	 * The number of structural features of the '<em>Normal Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY_FEATURE_COUNT = DIFFICULTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Normal Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DIFFICULTY_OPERATION_COUNT = DIFFICULTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.HardDifficultyImpl <em>Hard Difficulty</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.HardDifficultyImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getHardDifficulty()
	 * @generated
	 */
	int HARD_DIFFICULTY = 4;

	/**
	 * The feature id for the '<em><b>Instant Death</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY__INSTANT_DEATH = DIFFICULTY__INSTANT_DEATH;

	/**
	 * The feature id for the '<em><b>Enemy Max Count</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY__ENEMY_MAX_COUNT = DIFFICULTY__ENEMY_MAX_COUNT;

	/**
	 * The feature id for the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER = DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Monsters Damage Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER = DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY__MAX_THREAT = DIFFICULTY__MAX_THREAT;

	/**
	 * The number of structural features of the '<em>Hard Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY_FEATURE_COUNT = DIFFICULTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Hard Difficulty</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HARD_DIFFICULTY_OPERATION_COUNT = DIFFICULTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.impl.EnemyMaxCountImpl <em>Enemy Max Count</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.impl.EnemyMaxCountImpl
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEnemyMaxCount()
	 * @generated
	 */
	int ENEMY_MAX_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENEMY_MAX_COUNT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Max Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENEMY_MAX_COUNT__MAX_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Enemy Max Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENEMY_MAX_COUNT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Enemy Max Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENEMY_MAX_COUNT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.difficulties.EnemyTypes <em>Enemy Types</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.difficulties.EnemyTypes
	 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEnemyTypes()
	 * @generated
	 */
	int ENEMY_TYPES = 6;


	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.DifficultyGameData <em>Difficulty Game Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difficulty Game Data</em>'.
	 * @see main.game.maze.difficulties.DifficultyGameData
	 * @generated
	 */
	EClass getDifficultyGameData();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.difficulties.DifficultyGameData#getDifficulties <em>Difficulties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Difficulties</em>'.
	 * @see main.game.maze.difficulties.DifficultyGameData#getDifficulties()
	 * @see #getDifficultyGameData()
	 * @generated
	 */
	EReference getDifficultyGameData_Difficulties();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.difficulties.DifficultyGameData#getCurrentDifficulty <em>Current Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Difficulty</em>'.
	 * @see main.game.maze.difficulties.DifficultyGameData#getCurrentDifficulty()
	 * @see #getDifficultyGameData()
	 * @generated
	 */
	EReference getDifficultyGameData_CurrentDifficulty();

	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.EasyDifficulty <em>Easy Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Easy Difficulty</em>'.
	 * @see main.game.maze.difficulties.EasyDifficulty
	 * @generated
	 */
	EClass getEasyDifficulty();

	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.Difficulty <em>Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difficulty</em>'.
	 * @see main.game.maze.difficulties.Difficulty
	 * @generated
	 */
	EClass getDifficulty();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.Difficulty#isInstantDeath <em>Instant Death</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instant Death</em>'.
	 * @see main.game.maze.difficulties.Difficulty#isInstantDeath()
	 * @see #getDifficulty()
	 * @generated
	 */
	EAttribute getDifficulty_InstantDeath();

	/**
	 * Returns the meta object for the reference list '{@link main.game.maze.difficulties.Difficulty#getEnemyMaxCount <em>Enemy Max Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Enemy Max Count</em>'.
	 * @see main.game.maze.difficulties.Difficulty#getEnemyMaxCount()
	 * @see #getDifficulty()
	 * @generated
	 */
	EReference getDifficulty_EnemyMaxCount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.Difficulty#getMonstersMovementSpeedMultiplier <em>Monsters Movement Speed Multiplier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Monsters Movement Speed Multiplier</em>'.
	 * @see main.game.maze.difficulties.Difficulty#getMonstersMovementSpeedMultiplier()
	 * @see #getDifficulty()
	 * @generated
	 */
	EAttribute getDifficulty_MonstersMovementSpeedMultiplier();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.Difficulty#getMonstersDamageMultiplier <em>Monsters Damage Multiplier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Monsters Damage Multiplier</em>'.
	 * @see main.game.maze.difficulties.Difficulty#getMonstersDamageMultiplier()
	 * @see #getDifficulty()
	 * @generated
	 */
	EAttribute getDifficulty_MonstersDamageMultiplier();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.Difficulty#getMaxThreat <em>Max Threat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Threat</em>'.
	 * @see main.game.maze.difficulties.Difficulty#getMaxThreat()
	 * @see #getDifficulty()
	 * @generated
	 */
	EAttribute getDifficulty_MaxThreat();

	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.NormalDifficulty <em>Normal Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Normal Difficulty</em>'.
	 * @see main.game.maze.difficulties.NormalDifficulty
	 * @generated
	 */
	EClass getNormalDifficulty();

	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.HardDifficulty <em>Hard Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hard Difficulty</em>'.
	 * @see main.game.maze.difficulties.HardDifficulty
	 * @generated
	 */
	EClass getHardDifficulty();

	/**
	 * Returns the meta object for class '{@link main.game.maze.difficulties.EnemyMaxCount <em>Enemy Max Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enemy Max Count</em>'.
	 * @see main.game.maze.difficulties.EnemyMaxCount
	 * @generated
	 */
	EClass getEnemyMaxCount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.EnemyMaxCount#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see main.game.maze.difficulties.EnemyMaxCount#getType()
	 * @see #getEnemyMaxCount()
	 * @generated
	 */
	EAttribute getEnemyMaxCount_Type();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.difficulties.EnemyMaxCount#getMaxCount <em>Max Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Count</em>'.
	 * @see main.game.maze.difficulties.EnemyMaxCount#getMaxCount()
	 * @see #getEnemyMaxCount()
	 * @generated
	 */
	EAttribute getEnemyMaxCount_MaxCount();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.difficulties.EnemyTypes <em>Enemy Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enemy Types</em>'.
	 * @see main.game.maze.difficulties.EnemyTypes
	 * @generated
	 */
	EEnum getEnemyTypes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DifficultiesFactory getDifficultiesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.DifficultyGameDataImpl <em>Difficulty Game Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.DifficultyGameDataImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getDifficultyGameData()
		 * @generated
		 */
		EClass DIFFICULTY_GAME_DATA = eINSTANCE.getDifficultyGameData();

		/**
		 * The meta object literal for the '<em><b>Difficulties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFICULTY_GAME_DATA__DIFFICULTIES = eINSTANCE.getDifficultyGameData_Difficulties();

		/**
		 * The meta object literal for the '<em><b>Current Difficulty</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY = eINSTANCE.getDifficultyGameData_CurrentDifficulty();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.EasyDifficultyImpl <em>Easy Difficulty</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.EasyDifficultyImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEasyDifficulty()
		 * @generated
		 */
		EClass EASY_DIFFICULTY = eINSTANCE.getEasyDifficulty();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.DifficultyImpl <em>Difficulty</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.DifficultyImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getDifficulty()
		 * @generated
		 */
		EClass DIFFICULTY = eINSTANCE.getDifficulty();

		/**
		 * The meta object literal for the '<em><b>Instant Death</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFICULTY__INSTANT_DEATH = eINSTANCE.getDifficulty_InstantDeath();

		/**
		 * The meta object literal for the '<em><b>Enemy Max Count</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFFICULTY__ENEMY_MAX_COUNT = eINSTANCE.getDifficulty_EnemyMaxCount();

		/**
		 * The meta object literal for the '<em><b>Monsters Movement Speed Multiplier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER = eINSTANCE.getDifficulty_MonstersMovementSpeedMultiplier();

		/**
		 * The meta object literal for the '<em><b>Monsters Damage Multiplier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER = eINSTANCE.getDifficulty_MonstersDamageMultiplier();

		/**
		 * The meta object literal for the '<em><b>Max Threat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFFICULTY__MAX_THREAT = eINSTANCE.getDifficulty_MaxThreat();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.NormalDifficultyImpl <em>Normal Difficulty</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.NormalDifficultyImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getNormalDifficulty()
		 * @generated
		 */
		EClass NORMAL_DIFFICULTY = eINSTANCE.getNormalDifficulty();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.HardDifficultyImpl <em>Hard Difficulty</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.HardDifficultyImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getHardDifficulty()
		 * @generated
		 */
		EClass HARD_DIFFICULTY = eINSTANCE.getHardDifficulty();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.impl.EnemyMaxCountImpl <em>Enemy Max Count</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.impl.EnemyMaxCountImpl
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEnemyMaxCount()
		 * @generated
		 */
		EClass ENEMY_MAX_COUNT = eINSTANCE.getEnemyMaxCount();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENEMY_MAX_COUNT__TYPE = eINSTANCE.getEnemyMaxCount_Type();

		/**
		 * The meta object literal for the '<em><b>Max Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENEMY_MAX_COUNT__MAX_COUNT = eINSTANCE.getEnemyMaxCount_MaxCount();

		/**
		 * The meta object literal for the '{@link main.game.maze.difficulties.EnemyTypes <em>Enemy Types</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.difficulties.EnemyTypes
		 * @see main.game.maze.difficulties.impl.DifficultiesPackageImpl#getEnemyTypes()
		 * @generated
		 */
		EEnum ENEMY_TYPES = eINSTANCE.getEnemyTypes();

	}

} //DifficultiesPackage
