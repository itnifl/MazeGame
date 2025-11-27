/**
 */
package main.game.maze.opponents;

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
 * @see main.game.maze.opponents.OpponentsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import diff='platform:/resource/main.game.maze.difficulties/src/main/resources/difficulty-module.ecore#/' ecore='http://www.eclipse.org/emf/2002/Ecore'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL'"
 * @generated
 */
public interface OpponentsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "opponents";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://main.game.maze/opponents";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "opp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OpponentsPackage eINSTANCE = main.game.maze.opponents.impl.OpponentsPackageImpl.init();

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.OpponentModelImpl <em>Opponent Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.OpponentModelImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getOpponentModel()
	 * @generated
	 */
	int OPPONENT_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Character Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__CHARACTER_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__MAX_THREAT = 2;

	/**
	 * The feature id for the '<em><b>Selected Difficulty</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__SELECTED_DIFFICULTY = 3;

	/**
	 * The feature id for the '<em><b>Game Set Current Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL = 4;

	/**
	 * The number of structural features of the '<em>Opponent Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Opponent Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.CharacterTypeImpl <em>Character Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.CharacterTypeImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getCharacterType()
	 * @generated
	 */
	int CHARACTER_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__ID = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__DISPLAY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__ENABLED = 2;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__HEALTH = 3;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__SPEED = 4;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__THREAT_LEVEL = 5;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__EFFECTIVE_THREAT = 6;

	/**
	 * The feature id for the '<em><b>Image Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__IMAGE_BASE = 7;

	/**
	 * The feature id for the '<em><b>Image Turn Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__IMAGE_TURN_LEFT = 8;

	/**
	 * The feature id for the '<em><b>Image Turn Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__IMAGE_TURN_RIGHT = 9;

	/**
	 * The feature id for the '<em><b>Image Turn Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__IMAGE_TURN_UP = 10;

	/**
	 * The feature id for the '<em><b>Image Turn Down</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__IMAGE_TURN_DOWN = 11;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__BEHAVIOR = 12;

	/**
	 * The number of structural features of the '<em>Character Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Character Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.ZombieImpl <em>Zombie</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.ZombieImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getZombie()
	 * @generated
	 */
	int ZOMBIE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ID = CHARACTER_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__DISPLAY_NAME = CHARACTER_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ENABLED = CHARACTER_TYPE__ENABLED;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__HEALTH = CHARACTER_TYPE__HEALTH;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__SPEED = CHARACTER_TYPE__SPEED;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__THREAT_LEVEL = CHARACTER_TYPE__THREAT_LEVEL;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__EFFECTIVE_THREAT = CHARACTER_TYPE__EFFECTIVE_THREAT;

	/**
	 * The feature id for the '<em><b>Image Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__IMAGE_BASE = CHARACTER_TYPE__IMAGE_BASE;

	/**
	 * The feature id for the '<em><b>Image Turn Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__IMAGE_TURN_LEFT = CHARACTER_TYPE__IMAGE_TURN_LEFT;

	/**
	 * The feature id for the '<em><b>Image Turn Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__IMAGE_TURN_RIGHT = CHARACTER_TYPE__IMAGE_TURN_RIGHT;

	/**
	 * The feature id for the '<em><b>Image Turn Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__IMAGE_TURN_UP = CHARACTER_TYPE__IMAGE_TURN_UP;

	/**
	 * The feature id for the '<em><b>Image Turn Down</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__IMAGE_TURN_DOWN = CHARACTER_TYPE__IMAGE_TURN_DOWN;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__BEHAVIOR = CHARACTER_TYPE__BEHAVIOR;

	/**
	 * The feature id for the '<em><b>Attack Damage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ATTACK_DAMAGE = CHARACTER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zombie Loot Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ZOMBIE_LOOT_TABLE = CHARACTER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Infection Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__INFECTION_LEVEL = CHARACTER_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resurrection Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__RESURRECTION_TIME = CHARACTER_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Touch Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__TOUCH_SOUND = CHARACTER_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Zombie</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE_FEATURE_COUNT = CHARACTER_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Zombie</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE_OPERATION_COUNT = CHARACTER_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.LootTableImpl <em>Loot Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.LootTableImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootTable()
	 * @generated
	 */
	int LOOT_TABLE = 3;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_TABLE__ITEMS = 0;

	/**
	 * The feature id for the '<em><b>Weight Capacity</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_TABLE__WEIGHT_CAPACITY = 1;

	/**
	 * The number of structural features of the '<em>Loot Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_TABLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Loot Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_TABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.LootItemImpl <em>Loot Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.LootItemImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootItem()
	 * @generated
	 */
	int LOOT_ITEM = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM__VALUE = 2;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM__WEIGHT = 3;

	/**
	 * The feature id for the '<em><b>Graphic Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM__GRAPHIC_BASE = 4;

	/**
	 * The number of structural features of the '<em>Loot Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Loot Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOT_ITEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.GhostImpl <em>Ghost</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.GhostImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getGhost()
	 * @generated
	 */
	int GHOST = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__ID = CHARACTER_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__DISPLAY_NAME = CHARACTER_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__ENABLED = CHARACTER_TYPE__ENABLED;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__HEALTH = CHARACTER_TYPE__HEALTH;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__SPEED = CHARACTER_TYPE__SPEED;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__THREAT_LEVEL = CHARACTER_TYPE__THREAT_LEVEL;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__EFFECTIVE_THREAT = CHARACTER_TYPE__EFFECTIVE_THREAT;

	/**
	 * The feature id for the '<em><b>Image Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__IMAGE_BASE = CHARACTER_TYPE__IMAGE_BASE;

	/**
	 * The feature id for the '<em><b>Image Turn Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__IMAGE_TURN_LEFT = CHARACTER_TYPE__IMAGE_TURN_LEFT;

	/**
	 * The feature id for the '<em><b>Image Turn Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__IMAGE_TURN_RIGHT = CHARACTER_TYPE__IMAGE_TURN_RIGHT;

	/**
	 * The feature id for the '<em><b>Image Turn Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__IMAGE_TURN_UP = CHARACTER_TYPE__IMAGE_TURN_UP;

	/**
	 * The feature id for the '<em><b>Image Turn Down</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__IMAGE_TURN_DOWN = CHARACTER_TYPE__IMAGE_TURN_DOWN;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__BEHAVIOR = CHARACTER_TYPE__BEHAVIOR;

	/**
	 * The feature id for the '<em><b>Attack Damage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__ATTACK_DAMAGE = CHARACTER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Visibility Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__VISIBILITY_LEVEL = CHARACTER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Non Tangibility Energy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST__NON_TANGIBILITY_ENERGY = CHARACTER_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Ghost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST_FEATURE_COUNT = CHARACTER_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Ghost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GHOST_OPERATION_COUNT = CHARACTER_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.RangedEnemyImpl <em>Ranged Enemy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.RangedEnemyImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getRangedEnemy()
	 * @generated
	 */
	int RANGED_ENEMY = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ID = CHARACTER_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__DISPLAY_NAME = CHARACTER_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ENABLED = CHARACTER_TYPE__ENABLED;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__HEALTH = CHARACTER_TYPE__HEALTH;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__SPEED = CHARACTER_TYPE__SPEED;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__THREAT_LEVEL = CHARACTER_TYPE__THREAT_LEVEL;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__EFFECTIVE_THREAT = CHARACTER_TYPE__EFFECTIVE_THREAT;

	/**
	 * The feature id for the '<em><b>Image Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__IMAGE_BASE = CHARACTER_TYPE__IMAGE_BASE;

	/**
	 * The feature id for the '<em><b>Image Turn Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__IMAGE_TURN_LEFT = CHARACTER_TYPE__IMAGE_TURN_LEFT;

	/**
	 * The feature id for the '<em><b>Image Turn Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__IMAGE_TURN_RIGHT = CHARACTER_TYPE__IMAGE_TURN_RIGHT;

	/**
	 * The feature id for the '<em><b>Image Turn Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__IMAGE_TURN_UP = CHARACTER_TYPE__IMAGE_TURN_UP;

	/**
	 * The feature id for the '<em><b>Image Turn Down</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__IMAGE_TURN_DOWN = CHARACTER_TYPE__IMAGE_TURN_DOWN;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__BEHAVIOR = CHARACTER_TYPE__BEHAVIOR;

	/**
	 * The feature id for the '<em><b>Attack Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ATTACK_RANGE = CHARACTER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attack Cooldown Ms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ATTACK_COOLDOWN_MS = CHARACTER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attack Damage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ATTACK_DAMAGE = CHARACTER_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Projectile Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__PROJECTILE_SPEED = CHARACTER_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Projectile Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__PROJECTILE_TYPE = CHARACTER_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Splash Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__SPLASH_RADIUS = CHARACTER_TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Arc Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__ARC_HEIGHT = CHARACTER_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Projectile Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__PROJECTILE_IMAGE = CHARACTER_TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Explosion Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__EXPLOSION_IMAGE = CHARACTER_TYPE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Explosion Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__EXPLOSION_SOUND = CHARACTER_TYPE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Throw Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY__THROW_SOUND = CHARACTER_TYPE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Ranged Enemy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY_FEATURE_COUNT = CHARACTER_TYPE_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Ranged Enemy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGED_ENEMY_OPERATION_COUNT = CHARACTER_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.PumpkinBomberImpl <em>Pumpkin Bomber</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.PumpkinBomberImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getPumpkinBomber()
	 * @generated
	 */
	int PUMPKIN_BOMBER = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ID = RANGED_ENEMY__ID;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__DISPLAY_NAME = RANGED_ENEMY__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ENABLED = RANGED_ENEMY__ENABLED;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__HEALTH = RANGED_ENEMY__HEALTH;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__SPEED = RANGED_ENEMY__SPEED;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__THREAT_LEVEL = RANGED_ENEMY__THREAT_LEVEL;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__EFFECTIVE_THREAT = RANGED_ENEMY__EFFECTIVE_THREAT;

	/**
	 * The feature id for the '<em><b>Image Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__IMAGE_BASE = RANGED_ENEMY__IMAGE_BASE;

	/**
	 * The feature id for the '<em><b>Image Turn Left</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__IMAGE_TURN_LEFT = RANGED_ENEMY__IMAGE_TURN_LEFT;

	/**
	 * The feature id for the '<em><b>Image Turn Right</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__IMAGE_TURN_RIGHT = RANGED_ENEMY__IMAGE_TURN_RIGHT;

	/**
	 * The feature id for the '<em><b>Image Turn Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__IMAGE_TURN_UP = RANGED_ENEMY__IMAGE_TURN_UP;

	/**
	 * The feature id for the '<em><b>Image Turn Down</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__IMAGE_TURN_DOWN = RANGED_ENEMY__IMAGE_TURN_DOWN;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__BEHAVIOR = RANGED_ENEMY__BEHAVIOR;

	/**
	 * The feature id for the '<em><b>Attack Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ATTACK_RANGE = RANGED_ENEMY__ATTACK_RANGE;

	/**
	 * The feature id for the '<em><b>Attack Cooldown Ms</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ATTACK_COOLDOWN_MS = RANGED_ENEMY__ATTACK_COOLDOWN_MS;

	/**
	 * The feature id for the '<em><b>Attack Damage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ATTACK_DAMAGE = RANGED_ENEMY__ATTACK_DAMAGE;

	/**
	 * The feature id for the '<em><b>Projectile Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__PROJECTILE_SPEED = RANGED_ENEMY__PROJECTILE_SPEED;

	/**
	 * The feature id for the '<em><b>Projectile Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__PROJECTILE_TYPE = RANGED_ENEMY__PROJECTILE_TYPE;

	/**
	 * The feature id for the '<em><b>Splash Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__SPLASH_RADIUS = RANGED_ENEMY__SPLASH_RADIUS;

	/**
	 * The feature id for the '<em><b>Arc Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__ARC_HEIGHT = RANGED_ENEMY__ARC_HEIGHT;

	/**
	 * The feature id for the '<em><b>Projectile Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__PROJECTILE_IMAGE = RANGED_ENEMY__PROJECTILE_IMAGE;

	/**
	 * The feature id for the '<em><b>Explosion Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__EXPLOSION_IMAGE = RANGED_ENEMY__EXPLOSION_IMAGE;

	/**
	 * The feature id for the '<em><b>Explosion Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__EXPLOSION_SOUND = RANGED_ENEMY__EXPLOSION_SOUND;

	/**
	 * The feature id for the '<em><b>Throw Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER__THROW_SOUND = RANGED_ENEMY__THROW_SOUND;

	/**
	 * The number of structural features of the '<em>Pumpkin Bomber</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER_FEATURE_COUNT = RANGED_ENEMY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Pumpkin Bomber</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUMPKIN_BOMBER_OPERATION_COUNT = RANGED_ENEMY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.BehaviorType
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getBehaviorType()
	 * @generated
	 */
	int BEHAVIOR_TYPE = 8;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.LootItemType <em>Loot Item Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.LootItemType
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootItemType()
	 * @generated
	 */
	int LOOT_ITEM_TYPE = 9;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.ProjectileType <em>Projectile Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.ProjectileType
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getProjectileType()
	 * @generated
	 */
	int PROJECTILE_TYPE = 10;


	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.OpponentModel <em>Opponent Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Opponent Model</em>'.
	 * @see main.game.maze.opponents.OpponentModel
	 * @generated
	 */
	EClass getOpponentModel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.OpponentModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getName()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EAttribute getOpponentModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.opponents.OpponentModel#getCharacterTypes <em>Character Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Character Types</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getCharacterTypes()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EReference getOpponentModel_CharacterTypes();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.OpponentModel#getMaxThreat <em>Max Threat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Threat</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getMaxThreat()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EAttribute getOpponentModel_MaxThreat();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.opponents.OpponentModel#getSelectedDifficulty <em>Selected Difficulty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Selected Difficulty</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getSelectedDifficulty()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EReference getOpponentModel_SelectedDifficulty();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.OpponentModel#getGameSetCurrentThreatLevel <em>Game Set Current Threat Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Game Set Current Threat Level</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getGameSetCurrentThreatLevel()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EAttribute getOpponentModel_GameSetCurrentThreatLevel();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.CharacterType <em>Character Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Character Type</em>'.
	 * @see main.game.maze.opponents.CharacterType
	 * @generated
	 */
	EClass getCharacterType();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see main.game.maze.opponents.CharacterType#getId()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Id();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see main.game.maze.opponents.CharacterType#getDisplayName()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see main.game.maze.opponents.CharacterType#isEnabled()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getHealth <em>Health</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Health</em>'.
	 * @see main.game.maze.opponents.CharacterType#getHealth()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Health();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getSpeed <em>Speed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Speed</em>'.
	 * @see main.game.maze.opponents.CharacterType#getSpeed()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Speed();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getThreatLevel <em>Threat Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threat Level</em>'.
	 * @see main.game.maze.opponents.CharacterType#getThreatLevel()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ThreatLevel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getEffectiveThreat <em>Effective Threat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effective Threat</em>'.
	 * @see main.game.maze.opponents.CharacterType#getEffectiveThreat()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_EffectiveThreat();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getImageBase <em>Image Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Base</em>'.
	 * @see main.game.maze.opponents.CharacterType#getImageBase()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ImageBase();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getImageTurnLeft <em>Image Turn Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Turn Left</em>'.
	 * @see main.game.maze.opponents.CharacterType#getImageTurnLeft()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ImageTurnLeft();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getImageTurnRight <em>Image Turn Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Turn Right</em>'.
	 * @see main.game.maze.opponents.CharacterType#getImageTurnRight()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ImageTurnRight();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getImageTurnUp <em>Image Turn Up</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Turn Up</em>'.
	 * @see main.game.maze.opponents.CharacterType#getImageTurnUp()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ImageTurnUp();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getImageTurnDown <em>Image Turn Down</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Turn Down</em>'.
	 * @see main.game.maze.opponents.CharacterType#getImageTurnDown()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ImageTurnDown();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Behavior</em>'.
	 * @see main.game.maze.opponents.CharacterType#getBehavior()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Behavior();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.Zombie <em>Zombie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Zombie</em>'.
	 * @see main.game.maze.opponents.Zombie
	 * @generated
	 */
	EClass getZombie();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getAttackDamage <em>Attack Damage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Damage</em>'.
	 * @see main.game.maze.opponents.Zombie#getAttackDamage()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_AttackDamage();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.opponents.Zombie#getZombieLootTable <em>Zombie Loot Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Zombie Loot Table</em>'.
	 * @see main.game.maze.opponents.Zombie#getZombieLootTable()
	 * @see #getZombie()
	 * @generated
	 */
	EReference getZombie_ZombieLootTable();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getInfectionLevel <em>Infection Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Infection Level</em>'.
	 * @see main.game.maze.opponents.Zombie#getInfectionLevel()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_InfectionLevel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getResurrectionTime <em>Resurrection Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resurrection Time</em>'.
	 * @see main.game.maze.opponents.Zombie#getResurrectionTime()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_ResurrectionTime();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getTouchSound <em>Touch Sound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Touch Sound</em>'.
	 * @see main.game.maze.opponents.Zombie#getTouchSound()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_TouchSound();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.LootTable <em>Loot Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loot Table</em>'.
	 * @see main.game.maze.opponents.LootTable
	 * @generated
	 */
	EClass getLootTable();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.opponents.LootTable#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see main.game.maze.opponents.LootTable#getItems()
	 * @see #getLootTable()
	 * @generated
	 */
	EReference getLootTable_Items();

	/**
	 * Returns the meta object for the attribute list '{@link main.game.maze.opponents.LootTable#getWeightCapacity <em>Weight Capacity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Weight Capacity</em>'.
	 * @see main.game.maze.opponents.LootTable#getWeightCapacity()
	 * @see #getLootTable()
	 * @generated
	 */
	EAttribute getLootTable_WeightCapacity();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.LootItem <em>Loot Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loot Item</em>'.
	 * @see main.game.maze.opponents.LootItem
	 * @generated
	 */
	EClass getLootItem();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.LootItem#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see main.game.maze.opponents.LootItem#getName()
	 * @see #getLootItem()
	 * @generated
	 */
	EAttribute getLootItem_Name();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.LootItem#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see main.game.maze.opponents.LootItem#getType()
	 * @see #getLootItem()
	 * @generated
	 */
	EAttribute getLootItem_Type();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.LootItem#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see main.game.maze.opponents.LootItem#getValue()
	 * @see #getLootItem()
	 * @generated
	 */
	EAttribute getLootItem_Value();

	/**
	 * Returns the meta object for the attribute list '{@link main.game.maze.opponents.LootItem#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Weight</em>'.
	 * @see main.game.maze.opponents.LootItem#getWeight()
	 * @see #getLootItem()
	 * @generated
	 */
	EAttribute getLootItem_Weight();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.LootItem#getGraphicBase <em>Graphic Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Graphic Base</em>'.
	 * @see main.game.maze.opponents.LootItem#getGraphicBase()
	 * @see #getLootItem()
	 * @generated
	 */
	EAttribute getLootItem_GraphicBase();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.Ghost <em>Ghost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ghost</em>'.
	 * @see main.game.maze.opponents.Ghost
	 * @generated
	 */
	EClass getGhost();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Ghost#getAttackDamage <em>Attack Damage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Damage</em>'.
	 * @see main.game.maze.opponents.Ghost#getAttackDamage()
	 * @see #getGhost()
	 * @generated
	 */
	EAttribute getGhost_AttackDamage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Ghost#getVisibilityLevel <em>Visibility Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility Level</em>'.
	 * @see main.game.maze.opponents.Ghost#getVisibilityLevel()
	 * @see #getGhost()
	 * @generated
	 */
	EAttribute getGhost_VisibilityLevel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Ghost#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Non Tangibility Energy</em>'.
	 * @see main.game.maze.opponents.Ghost#getNonTangibilityEnergy()
	 * @see #getGhost()
	 * @generated
	 */
	EAttribute getGhost_NonTangibilityEnergy();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.RangedEnemy <em>Ranged Enemy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ranged Enemy</em>'.
	 * @see main.game.maze.opponents.RangedEnemy
	 * @generated
	 */
	EClass getRangedEnemy();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getAttackRange <em>Attack Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Range</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getAttackRange()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_AttackRange();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getAttackCooldownMs <em>Attack Cooldown Ms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Cooldown Ms</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getAttackCooldownMs()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_AttackCooldownMs();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getAttackDamage <em>Attack Damage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Damage</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getAttackDamage()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_AttackDamage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getProjectileSpeed <em>Projectile Speed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Projectile Speed</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getProjectileSpeed()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ProjectileSpeed();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getProjectileType <em>Projectile Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Projectile Type</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getProjectileType()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ProjectileType();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getSplashRadius <em>Splash Radius</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Splash Radius</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getSplashRadius()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_SplashRadius();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getArcHeight <em>Arc Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arc Height</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getArcHeight()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ArcHeight();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getProjectileImage <em>Projectile Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Projectile Image</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getProjectileImage()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ProjectileImage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getExplosionImage <em>Explosion Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Explosion Image</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getExplosionImage()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ExplosionImage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getExplosionSound <em>Explosion Sound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Explosion Sound</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getExplosionSound()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ExplosionSound();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.RangedEnemy#getThrowSound <em>Throw Sound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Throw Sound</em>'.
	 * @see main.game.maze.opponents.RangedEnemy#getThrowSound()
	 * @see #getRangedEnemy()
	 * @generated
	 */
	EAttribute getRangedEnemy_ThrowSound();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.PumpkinBomber <em>Pumpkin Bomber</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pumpkin Bomber</em>'.
	 * @see main.game.maze.opponents.PumpkinBomber
	 * @generated
	 */
	EClass getPumpkinBomber();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Behavior Type</em>'.
	 * @see main.game.maze.opponents.BehaviorType
	 * @generated
	 */
	EEnum getBehaviorType();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.opponents.LootItemType <em>Loot Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Loot Item Type</em>'.
	 * @see main.game.maze.opponents.LootItemType
	 * @generated
	 */
	EEnum getLootItemType();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.opponents.ProjectileType <em>Projectile Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Projectile Type</em>'.
	 * @see main.game.maze.opponents.ProjectileType
	 * @generated
	 */
	EEnum getProjectileType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OpponentsFactory getOpponentsFactory();

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
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.OpponentModelImpl <em>Opponent Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.OpponentModelImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getOpponentModel()
		 * @generated
		 */
		EClass OPPONENT_MODEL = eINSTANCE.getOpponentModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPPONENT_MODEL__NAME = eINSTANCE.getOpponentModel_Name();

		/**
		 * The meta object literal for the '<em><b>Character Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPPONENT_MODEL__CHARACTER_TYPES = eINSTANCE.getOpponentModel_CharacterTypes();

		/**
		 * The meta object literal for the '<em><b>Max Threat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPPONENT_MODEL__MAX_THREAT = eINSTANCE.getOpponentModel_MaxThreat();

		/**
		 * The meta object literal for the '<em><b>Selected Difficulty</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPPONENT_MODEL__SELECTED_DIFFICULTY = eINSTANCE.getOpponentModel_SelectedDifficulty();

		/**
		 * The meta object literal for the '<em><b>Game Set Current Threat Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL = eINSTANCE.getOpponentModel_GameSetCurrentThreatLevel();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.CharacterTypeImpl <em>Character Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.CharacterTypeImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getCharacterType()
		 * @generated
		 */
		EClass CHARACTER_TYPE = eINSTANCE.getCharacterType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__ID = eINSTANCE.getCharacterType_Id();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__DISPLAY_NAME = eINSTANCE.getCharacterType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__ENABLED = eINSTANCE.getCharacterType_Enabled();

		/**
		 * The meta object literal for the '<em><b>Health</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__HEALTH = eINSTANCE.getCharacterType_Health();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__SPEED = eINSTANCE.getCharacterType_Speed();

		/**
		 * The meta object literal for the '<em><b>Threat Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__THREAT_LEVEL = eINSTANCE.getCharacterType_ThreatLevel();

		/**
		 * The meta object literal for the '<em><b>Effective Threat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__EFFECTIVE_THREAT = eINSTANCE.getCharacterType_EffectiveThreat();

		/**
		 * The meta object literal for the '<em><b>Image Base</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__IMAGE_BASE = eINSTANCE.getCharacterType_ImageBase();

		/**
		 * The meta object literal for the '<em><b>Image Turn Left</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__IMAGE_TURN_LEFT = eINSTANCE.getCharacterType_ImageTurnLeft();

		/**
		 * The meta object literal for the '<em><b>Image Turn Right</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__IMAGE_TURN_RIGHT = eINSTANCE.getCharacterType_ImageTurnRight();

		/**
		 * The meta object literal for the '<em><b>Image Turn Up</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__IMAGE_TURN_UP = eINSTANCE.getCharacterType_ImageTurnUp();

		/**
		 * The meta object literal for the '<em><b>Image Turn Down</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__IMAGE_TURN_DOWN = eINSTANCE.getCharacterType_ImageTurnDown();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__BEHAVIOR = eINSTANCE.getCharacterType_Behavior();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.ZombieImpl <em>Zombie</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.ZombieImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getZombie()
		 * @generated
		 */
		EClass ZOMBIE = eINSTANCE.getZombie();

		/**
		 * The meta object literal for the '<em><b>Attack Damage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__ATTACK_DAMAGE = eINSTANCE.getZombie_AttackDamage();

		/**
		 * The meta object literal for the '<em><b>Zombie Loot Table</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ZOMBIE__ZOMBIE_LOOT_TABLE = eINSTANCE.getZombie_ZombieLootTable();

		/**
		 * The meta object literal for the '<em><b>Infection Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__INFECTION_LEVEL = eINSTANCE.getZombie_InfectionLevel();

		/**
		 * The meta object literal for the '<em><b>Resurrection Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__RESURRECTION_TIME = eINSTANCE.getZombie_ResurrectionTime();

		/**
		 * The meta object literal for the '<em><b>Touch Sound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__TOUCH_SOUND = eINSTANCE.getZombie_TouchSound();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.LootTableImpl <em>Loot Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.LootTableImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootTable()
		 * @generated
		 */
		EClass LOOT_TABLE = eINSTANCE.getLootTable();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOT_TABLE__ITEMS = eINSTANCE.getLootTable_Items();

		/**
		 * The meta object literal for the '<em><b>Weight Capacity</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_TABLE__WEIGHT_CAPACITY = eINSTANCE.getLootTable_WeightCapacity();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.LootItemImpl <em>Loot Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.LootItemImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootItem()
		 * @generated
		 */
		EClass LOOT_ITEM = eINSTANCE.getLootItem();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_ITEM__NAME = eINSTANCE.getLootItem_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_ITEM__TYPE = eINSTANCE.getLootItem_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_ITEM__VALUE = eINSTANCE.getLootItem_Value();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_ITEM__WEIGHT = eINSTANCE.getLootItem_Weight();

		/**
		 * The meta object literal for the '<em><b>Graphic Base</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOT_ITEM__GRAPHIC_BASE = eINSTANCE.getLootItem_GraphicBase();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.GhostImpl <em>Ghost</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.GhostImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getGhost()
		 * @generated
		 */
		EClass GHOST = eINSTANCE.getGhost();

		/**
		 * The meta object literal for the '<em><b>Attack Damage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GHOST__ATTACK_DAMAGE = eINSTANCE.getGhost_AttackDamage();

		/**
		 * The meta object literal for the '<em><b>Visibility Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GHOST__VISIBILITY_LEVEL = eINSTANCE.getGhost_VisibilityLevel();

		/**
		 * The meta object literal for the '<em><b>Non Tangibility Energy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GHOST__NON_TANGIBILITY_ENERGY = eINSTANCE.getGhost_NonTangibilityEnergy();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.RangedEnemyImpl <em>Ranged Enemy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.RangedEnemyImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getRangedEnemy()
		 * @generated
		 */
		EClass RANGED_ENEMY = eINSTANCE.getRangedEnemy();

		/**
		 * The meta object literal for the '<em><b>Attack Range</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__ATTACK_RANGE = eINSTANCE.getRangedEnemy_AttackRange();

		/**
		 * The meta object literal for the '<em><b>Attack Cooldown Ms</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__ATTACK_COOLDOWN_MS = eINSTANCE.getRangedEnemy_AttackCooldownMs();

		/**
		 * The meta object literal for the '<em><b>Attack Damage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__ATTACK_DAMAGE = eINSTANCE.getRangedEnemy_AttackDamage();

		/**
		 * The meta object literal for the '<em><b>Projectile Speed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__PROJECTILE_SPEED = eINSTANCE.getRangedEnemy_ProjectileSpeed();

		/**
		 * The meta object literal for the '<em><b>Projectile Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__PROJECTILE_TYPE = eINSTANCE.getRangedEnemy_ProjectileType();

		/**
		 * The meta object literal for the '<em><b>Splash Radius</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__SPLASH_RADIUS = eINSTANCE.getRangedEnemy_SplashRadius();

		/**
		 * The meta object literal for the '<em><b>Arc Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__ARC_HEIGHT = eINSTANCE.getRangedEnemy_ArcHeight();

		/**
		 * The meta object literal for the '<em><b>Projectile Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__PROJECTILE_IMAGE = eINSTANCE.getRangedEnemy_ProjectileImage();

		/**
		 * The meta object literal for the '<em><b>Explosion Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__EXPLOSION_IMAGE = eINSTANCE.getRangedEnemy_ExplosionImage();

		/**
		 * The meta object literal for the '<em><b>Explosion Sound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__EXPLOSION_SOUND = eINSTANCE.getRangedEnemy_ExplosionSound();

		/**
		 * The meta object literal for the '<em><b>Throw Sound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGED_ENEMY__THROW_SOUND = eINSTANCE.getRangedEnemy_ThrowSound();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.PumpkinBomberImpl <em>Pumpkin Bomber</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.PumpkinBomberImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getPumpkinBomber()
		 * @generated
		 */
		EClass PUMPKIN_BOMBER = eINSTANCE.getPumpkinBomber();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.BehaviorType
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getBehaviorType()
		 * @generated
		 */
		EEnum BEHAVIOR_TYPE = eINSTANCE.getBehaviorType();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.LootItemType <em>Loot Item Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.LootItemType
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getLootItemType()
		 * @generated
		 */
		EEnum LOOT_ITEM_TYPE = eINSTANCE.getLootItemType();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.ProjectileType <em>Projectile Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.ProjectileType
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getProjectileType()
		 * @generated
		 */
		EEnum PROJECTILE_TYPE = eINSTANCE.getProjectileType();

	}

} //OpponentsPackage
