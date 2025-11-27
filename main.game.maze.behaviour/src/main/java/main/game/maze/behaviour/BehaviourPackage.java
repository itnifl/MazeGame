/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
 * @see main.game.maze.behaviour.BehaviourFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore' opp='../../../../../main.game.maze.opponents/src/main/resources/opponents.ecore#/'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface BehaviourPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "behaviour";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://main.game.maze/behaviour";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "behaviour";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BehaviourPackage eINSTANCE = main.game.maze.behaviour.impl.BehaviourPackageImpl.init();

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.DirectionImpl <em>Direction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.DirectionImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDirection()
	 * @generated
	 */
	int DIRECTION = 0;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTION__START_POSITION = 0;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTION__END_POSITION = 1;

	/**
	 * The number of structural features of the '<em>Direction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Direction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.MovementBehaviorImpl <em>Movement Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.MovementBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getMovementBehavior()
	 * @generated
	 */
	int MOVEMENT_BEHAVIOR = 2;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.RandomBehaviorImpl <em>Random Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.RandomBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getRandomBehavior()
	 * @generated
	 */
	int RANDOM_BEHAVIOR = 3;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl <em>Patrol Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolBehavior()
	 * @generated
	 */
	int PATROL_BEHAVIOR = 4;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.ChaseBehaviorImpl <em>Chase Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.ChaseBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getChaseBehavior()
	 * @generated
	 */
	int CHASE_BEHAVIOR = 5;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PositionImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 1;

	/**
	 * The feature id for the '<em><b>Pos X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__POS_X = 0;

	/**
	 * The feature id for the '<em><b>Pos Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__POS_Y = 1;

	/**
	 * The number of structural features of the '<em>Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Charactertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__CHARACTERTYPE = 0;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__IGNORE_WALLS = 1;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION = 2;

	/**
	 * The feature id for the '<em><b>Base Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__BASE_VISION_RANGE = 3;

	/**
	 * The feature id for the '<em><b>Additional Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE = 4;

	/**
	 * The feature id for the '<em><b>Vision Range Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER = 5;

	/**
	 * The feature id for the '<em><b>Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__VISION_RANGE = 6;

	/**
	 * The feature id for the '<em><b>Next Positions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__NEXT_POSITIONS = 7;

	/**
	 * The feature id for the '<em><b>Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__POSITION = 8;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__DIRECTION = 9;

	/**
	 * The number of structural features of the '<em>Movement Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR_FEATURE_COUNT = 10;

	/**
	 * The operation id for the '<em>Move</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___MOVE = 0;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT = 1;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT = 2;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT = 3;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT = 4;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT = 5;

	/**
	 * The number of operations of the '<em>Movement Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR_OPERATION_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Charactertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__CHARACTERTYPE = MOVEMENT_BEHAVIOR__CHARACTERTYPE;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

	/**
	 * The feature id for the '<em><b>Base Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__BASE_VISION_RANGE = MOVEMENT_BEHAVIOR__BASE_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Additional Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__ADDITIONAL_VISION_RANGE = MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Vision Range Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__VISION_RANGE_MULTIPLIER = MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__VISION_RANGE = MOVEMENT_BEHAVIOR__VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Next Positions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__NEXT_POSITIONS = MOVEMENT_BEHAVIOR__NEXT_POSITIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__POSITION = MOVEMENT_BEHAVIOR__POSITION;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__DIRECTION = MOVEMENT_BEHAVIOR__DIRECTION;

	/**
	 * The feature id for the '<em><b>Regen Per Second</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__REGEN_PER_SECOND = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Random Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR_FEATURE_COUNT = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Move</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___MOVE = MOVEMENT_BEHAVIOR___MOVE;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___UPDATE__HEALTHEVENT = MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___UPDATE__SPEEDEVENT = MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___UPDATE__TIMEEVENT = MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___UPDATE__ATTACKEVENT = MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___UPDATE__VISIONEVENT = MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT;

	/**
	 * The number of operations of the '<em>Random Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Charactertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__CHARACTERTYPE = MOVEMENT_BEHAVIOR__CHARACTERTYPE;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

	/**
	 * The feature id for the '<em><b>Base Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__BASE_VISION_RANGE = MOVEMENT_BEHAVIOR__BASE_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Additional Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__ADDITIONAL_VISION_RANGE = MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Vision Range Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__VISION_RANGE_MULTIPLIER = MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__VISION_RANGE = MOVEMENT_BEHAVIOR__VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Next Positions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__NEXT_POSITIONS = MOVEMENT_BEHAVIOR__NEXT_POSITIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__POSITION = MOVEMENT_BEHAVIOR__POSITION;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__DIRECTION = MOVEMENT_BEHAVIOR__DIRECTION;

	/**
	 * The feature id for the '<em><b>Path</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__PATH = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Current Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__CURRENT_INDEX = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pathcalculator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__PATHCALCULATOR = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__BEHAVIOR = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Patrol Zone</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__PATROL_ZONE = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Patrol Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR_FEATURE_COUNT = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Move</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___MOVE = MOVEMENT_BEHAVIOR___MOVE;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___UPDATE__HEALTHEVENT = MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___UPDATE__SPEEDEVENT = MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___UPDATE__TIMEEVENT = MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___UPDATE__ATTACKEVENT = MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___UPDATE__VISIONEVENT = MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT;

	/**
	 * The operation id for the '<em>Next Index</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___NEXT_INDEX = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Patrol Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Charactertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__CHARACTERTYPE = MOVEMENT_BEHAVIOR__CHARACTERTYPE;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

	/**
	 * The feature id for the '<em><b>Base Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__BASE_VISION_RANGE = MOVEMENT_BEHAVIOR__BASE_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Additional Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__ADDITIONAL_VISION_RANGE = MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Vision Range Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__VISION_RANGE_MULTIPLIER = MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER;

	/**
	 * The feature id for the '<em><b>Vision Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__VISION_RANGE = MOVEMENT_BEHAVIOR__VISION_RANGE;

	/**
	 * The feature id for the '<em><b>Next Positions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__NEXT_POSITIONS = MOVEMENT_BEHAVIOR__NEXT_POSITIONS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__POSITION = MOVEMENT_BEHAVIOR__POSITION;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__DIRECTION = MOVEMENT_BEHAVIOR__DIRECTION;

	/**
	 * The feature id for the '<em><b>Relative Position Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pathcalculator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__PATHCALCULATOR = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Chase Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR_FEATURE_COUNT = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Move</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___MOVE = MOVEMENT_BEHAVIOR___MOVE;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___UPDATE__HEALTHEVENT = MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___UPDATE__SPEEDEVENT = MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___UPDATE__TIMEEVENT = MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___UPDATE__ATTACKEVENT = MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT;

	/**
	 * The operation id for the '<em>Update</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___UPDATE__VISIONEVENT = MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT;

	/**
	 * The number of operations of the '<em>Chase Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolPointImpl <em>Patrol Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolPointImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolPoint()
	 * @generated
	 */
	int PATROL_POINT = 6;

	/**
	 * The feature id for the '<em><b>Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT__POINT = 0;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT__EVENTS = 1;

	/**
	 * The number of structural features of the '<em>Patrol Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Trigger Events</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT___TRIGGER_EVENTS = 0;

	/**
	 * The number of operations of the '<em>Patrol Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PathCalculatorImpl <em>Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPathCalculator()
	 * @generated
	 */
	int PATH_CALCULATOR = 8;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl <em>Dijkstra Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDijkstraPathCalculator()
	 * @generated
	 */
	int DIJKSTRA_PATH_CALCULATOR = 9;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.AstarPathCalculatorImpl <em>Astar Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.AstarPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getAstarPathCalculator()
	 * @generated
	 */
	int ASTAR_PATH_CALCULATOR = 10;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.LocalPathCalculatorImpl <em>Local Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.LocalPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getLocalPathCalculator()
	 * @generated
	 */
	int LOCAL_PATH_CALCULATOR = 11;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.CharacterEventImpl <em>Character Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.CharacterEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getCharacterEvent()
	 * @generated
	 */
	int CHARACTER_EVENT = 12;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.HealthEventImpl <em>Health Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.HealthEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getHealthEvent()
	 * @generated
	 */
	int HEALTH_EVENT = 13;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.SpeedEventImpl <em>Speed Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.SpeedEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getSpeedEvent()
	 * @generated
	 */
	int SPEED_EVENT = 14;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.TimeEventImpl <em>Time Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.TimeEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getTimeEvent()
	 * @generated
	 */
	int TIME_EVENT = 15;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.VisionEventImpl <em>Vision Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.VisionEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getVisionEvent()
	 * @generated
	 */
	int VISION_EVENT = 16;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.AttackEventImpl <em>Attack Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.AttackEventImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getAttackEvent()
	 * @generated
	 */
	int ATTACK_EVENT = 17;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolZoneImpl <em>Patrol Zone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolZoneImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolZone()
	 * @generated
	 */
	int PATROL_ZONE = 7;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_ZONE__WIDTH = 0;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_ZONE__HEIGHT = 1;

	/**
	 * The feature id for the '<em><b>Top Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_ZONE__TOP_LEFT = 2;

	/**
	 * The number of structural features of the '<em>Patrol Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_ZONE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Patrol Zone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_ZONE_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_CALCULATOR__DISTANCE_METHOD = 0;

	/**
	 * The number of structural features of the '<em>Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_CALCULATOR_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Compute</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_CALCULATOR___COMPUTE__POSITION = 0;

	/**
	 * The number of operations of the '<em>Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_CALCULATOR_OPERATION_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR__DISTANCE_METHOD = PATH_CALCULATOR__DISTANCE_METHOD;

	/**
	 * The feature id for the '<em><b>Max Path Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH = PATH_CALCULATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dijkstra Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR_FEATURE_COUNT = PATH_CALCULATOR_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Compute</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR___COMPUTE__POSITION = PATH_CALCULATOR___COMPUTE__POSITION;

	/**
	 * The number of operations of the '<em>Dijkstra Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR__DISTANCE_METHOD = PATH_CALCULATOR__DISTANCE_METHOD;

	/**
	 * The feature id for the '<em><b>Max Path Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH = PATH_CALCULATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Astar Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR_FEATURE_COUNT = PATH_CALCULATOR_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Compute</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR___COMPUTE__POSITION = PATH_CALCULATOR___COMPUTE__POSITION;

	/**
	 * The number of operations of the '<em>Astar Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_CALCULATOR__DISTANCE_METHOD = PATH_CALCULATOR__DISTANCE_METHOD;

	/**
	 * The number of structural features of the '<em>Local Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_CALCULATOR_FEATURE_COUNT = PATH_CALCULATOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Compute</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_CALCULATOR___COMPUTE__POSITION = PATH_CALCULATOR___COMPUTE__POSITION;

	/**
	 * The number of operations of the '<em>Local Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_EVENT__PROBABILITY = 0;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_EVENT__SUBSCRIBER = 1;

	/**
	 * The number of structural features of the '<em>Character Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_EVENT_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_EVENT___NOTIFY_SUBSCRIBERS = 0;

	/**
	 * The number of operations of the '<em>Character Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_EVENT_OPERATION_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT__PROBABILITY = CHARACTER_EVENT__PROBABILITY;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT__SUBSCRIBER = CHARACTER_EVENT__SUBSCRIBER;

	/**
	 * The feature id for the '<em><b>Health Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT__HEALTH_AMOUNT = CHARACTER_EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Health Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT__HEALTH_PERCENTAGE = CHARACTER_EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Health Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT_FEATURE_COUNT = CHARACTER_EVENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT___NOTIFY_SUBSCRIBERS = CHARACTER_EVENT___NOTIFY_SUBSCRIBERS;

	/**
	 * The number of operations of the '<em>Health Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEALTH_EVENT_OPERATION_COUNT = CHARACTER_EVENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT__PROBABILITY = CHARACTER_EVENT__PROBABILITY;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT__SUBSCRIBER = CHARACTER_EVENT__SUBSCRIBER;

	/**
	 * The feature id for the '<em><b>Speed Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT__SPEED_AMOUNT = CHARACTER_EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Speed Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT__SPEED_PERCENTAGE = CHARACTER_EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Speed Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT_FEATURE_COUNT = CHARACTER_EVENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT___NOTIFY_SUBSCRIBERS = CHARACTER_EVENT___NOTIFY_SUBSCRIBERS;

	/**
	 * The number of operations of the '<em>Speed Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEED_EVENT_OPERATION_COUNT = CHARACTER_EVENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__PROBABILITY = CHARACTER_EVENT__PROBABILITY;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__SUBSCRIBER = CHARACTER_EVENT__SUBSCRIBER;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT__TIME = CHARACTER_EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Time Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT_FEATURE_COUNT = CHARACTER_EVENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT___NOTIFY_SUBSCRIBERS = CHARACTER_EVENT___NOTIFY_SUBSCRIBERS;

	/**
	 * The number of operations of the '<em>Time Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_EVENT_OPERATION_COUNT = CHARACTER_EVENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT__PROBABILITY = CHARACTER_EVENT__PROBABILITY;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT__SUBSCRIBER = CHARACTER_EVENT__SUBSCRIBER;

	/**
	 * The feature id for the '<em><b>Radius Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT__RADIUS_AMOUNT = CHARACTER_EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Radius Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT__RADIUS_PERCENTAGE = CHARACTER_EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Vision Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT_FEATURE_COUNT = CHARACTER_EVENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT___NOTIFY_SUBSCRIBERS = CHARACTER_EVENT___NOTIFY_SUBSCRIBERS;

	/**
	 * The number of operations of the '<em>Vision Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISION_EVENT_OPERATION_COUNT = CHARACTER_EVENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__PROBABILITY = CHARACTER_EVENT__PROBABILITY;

	/**
	 * The feature id for the '<em><b>Subscriber</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__SUBSCRIBER = CHARACTER_EVENT__SUBSCRIBER;

	/**
	 * The feature id for the '<em><b>Radius Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__RADIUS_PERCENTAGE = CHARACTER_EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Radius Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__RADIUS_AMOUNT = CHARACTER_EVENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Damage Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__DAMAGE_AMOUNT = CHARACTER_EVENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Damage Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT__DAMAGE_PERCENTAGE = CHARACTER_EVENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Attack Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT_FEATURE_COUNT = CHARACTER_EVENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Notify Subscribers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT___NOTIFY_SUBSCRIBERS = CHARACTER_EVENT___NOTIFY_SUBSCRIBERS;

	/**
	 * The number of operations of the '<em>Attack Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACK_EVENT_OPERATION_COUNT = CHARACTER_EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.DistanceMethod <em>Distance Method</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.DistanceMethod
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDistanceMethod()
	 * @generated
	 */
	int DISTANCE_METHOD = 18;


	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.PatrolPathBehavior <em>Patrol Path Behavior</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.PatrolPathBehavior
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolPathBehavior()
	 * @generated
	 */
	int PATROL_PATH_BEHAVIOR = 19;


	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.Direction <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Direction</em>'.
	 * @see main.game.maze.behaviour.Direction
	 * @generated
	 */
	EClass getDirection();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.Direction#getStartPosition <em>Start Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Position</em>'.
	 * @see main.game.maze.behaviour.Direction#getStartPosition()
	 * @see #getDirection()
	 * @generated
	 */
	EReference getDirection_StartPosition();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.Direction#getEndPosition <em>End Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>End Position</em>'.
	 * @see main.game.maze.behaviour.Direction#getEndPosition()
	 * @see #getDirection()
	 * @generated
	 */
	EReference getDirection_EndPosition();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.RandomBehavior <em>Random Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Random Behavior</em>'.
	 * @see main.game.maze.behaviour.RandomBehavior
	 * @generated
	 */
	EClass getRandomBehavior();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.RandomBehavior#getRegenPerSecond <em>Regen Per Second</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Regen Per Second</em>'.
	 * @see main.game.maze.behaviour.RandomBehavior#getRegenPerSecond()
	 * @see #getRandomBehavior()
	 * @generated
	 */
	EAttribute getRandomBehavior_RegenPerSecond();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.PatrolBehavior <em>Patrol Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patrol Behavior</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior
	 * @generated
	 */
	EClass getPatrolBehavior();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.behaviour.PatrolBehavior#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Path</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getPath()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EReference getPatrolBehavior_Path();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolBehavior#getCurrentIndex <em>Current Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Index</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getCurrentIndex()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EAttribute getPatrolBehavior_CurrentIndex();

	/**
	 * Returns the meta object for the containment reference '{@link main.game.maze.behaviour.PatrolBehavior#getPathcalculator <em>Pathcalculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pathcalculator</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getPathcalculator()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EReference getPatrolBehavior_Pathcalculator();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolBehavior#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Behavior</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getBehavior()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EAttribute getPatrolBehavior_Behavior();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.PatrolBehavior#getPatrolZone <em>Patrol Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Patrol Zone</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getPatrolZone()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EReference getPatrolBehavior_PatrolZone();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.PatrolBehavior#nextIndex() <em>Next Index</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Next Index</em>' operation.
	 * @see main.game.maze.behaviour.PatrolBehavior#nextIndex()
	 * @generated
	 */
	EOperation getPatrolBehavior__NextIndex();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.ChaseBehavior <em>Chase Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chase Behavior</em>'.
	 * @see main.game.maze.behaviour.ChaseBehavior
	 * @generated
	 */
	EClass getChaseBehavior();

	/**
	 * Returns the meta object for the containment reference '{@link main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget <em>Relative Position Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relative Position Target</em>'.
	 * @see main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget()
	 * @see #getChaseBehavior()
	 * @generated
	 */
	EReference getChaseBehavior_RelativePositionTarget();

	/**
	 * Returns the meta object for the containment reference '{@link main.game.maze.behaviour.ChaseBehavior#getPathcalculator <em>Pathcalculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pathcalculator</em>'.
	 * @see main.game.maze.behaviour.ChaseBehavior#getPathcalculator()
	 * @see #getChaseBehavior()
	 * @generated
	 */
	EReference getChaseBehavior_Pathcalculator();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.MovementBehavior <em>Movement Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Movement Behavior</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior
	 * @generated
	 */
	EClass getMovementBehavior();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.MovementBehavior#getCharactertype <em>Charactertype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Charactertype</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getCharactertype()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EReference getMovementBehavior_Charactertype();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#isIgnoreWalls <em>Ignore Walls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore Walls</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#isIgnoreWalls()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_IgnoreWalls();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#isInstantKillOnCollision <em>Instant Kill On Collision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instant Kill On Collision</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#isInstantKillOnCollision()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_InstantKillOnCollision();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#getBaseVisionRange <em>Base Vision Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Vision Range</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getBaseVisionRange()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_BaseVisionRange();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#getAdditionalVisionRange <em>Additional Vision Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Additional Vision Range</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getAdditionalVisionRange()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_AdditionalVisionRange();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#getVisionRangeMultiplier <em>Vision Range Multiplier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vision Range Multiplier</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getVisionRangeMultiplier()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_VisionRangeMultiplier();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#getVisionRange <em>Vision Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vision Range</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getVisionRange()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_VisionRange();

	/**
	 * Returns the meta object for the reference list '{@link main.game.maze.behaviour.MovementBehavior#getNextPositions <em>Next Positions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Next Positions</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getNextPositions()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EReference getMovementBehavior_NextPositions();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.MovementBehavior#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Position</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getPosition()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EReference getMovementBehavior_Position();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.MovementBehavior#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Direction</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getDirection()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EReference getMovementBehavior_Direction();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#move() <em>Move</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Move</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#move()
	 * @generated
	 */
	EOperation getMovementBehavior__Move();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.HealthEvent) <em>Update</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.HealthEvent)
	 * @generated
	 */
	EOperation getMovementBehavior__Update__HealthEvent();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.SpeedEvent) <em>Update</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.SpeedEvent)
	 * @generated
	 */
	EOperation getMovementBehavior__Update__SpeedEvent();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.TimeEvent) <em>Update</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.TimeEvent)
	 * @generated
	 */
	EOperation getMovementBehavior__Update__TimeEvent();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.AttackEvent) <em>Update</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.AttackEvent)
	 * @generated
	 */
	EOperation getMovementBehavior__Update__AttackEvent();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.VisionEvent) <em>Update</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#update(main.game.maze.behaviour.VisionEvent)
	 * @generated
	 */
	EOperation getMovementBehavior__Update__VisionEvent();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position</em>'.
	 * @see main.game.maze.behaviour.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.Position#getPosX <em>Pos X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pos X</em>'.
	 * @see main.game.maze.behaviour.Position#getPosX()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_PosX();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.Position#getPosY <em>Pos Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pos Y</em>'.
	 * @see main.game.maze.behaviour.Position#getPosY()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_PosY();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.PatrolPoint <em>Patrol Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patrol Point</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint
	 * @generated
	 */
	EClass getPatrolPoint();

	/**
	 * Returns the meta object for the containment reference '{@link main.game.maze.behaviour.PatrolPoint#getPoint <em>Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Point</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint#getPoint()
	 * @see #getPatrolPoint()
	 * @generated
	 */
	EReference getPatrolPoint_Point();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.behaviour.PatrolPoint#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint#getEvents()
	 * @see #getPatrolPoint()
	 * @generated
	 */
	EReference getPatrolPoint_Events();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.PatrolPoint#triggerEvents() <em>Trigger Events</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Trigger Events</em>' operation.
	 * @see main.game.maze.behaviour.PatrolPoint#triggerEvents()
	 * @generated
	 */
	EOperation getPatrolPoint__TriggerEvents();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.PathCalculator <em>Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Path Calculator</em>'.
	 * @see main.game.maze.behaviour.PathCalculator
	 * @generated
	 */
	EClass getPathCalculator();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PathCalculator#getDistanceMethod <em>Distance Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Distance Method</em>'.
	 * @see main.game.maze.behaviour.PathCalculator#getDistanceMethod()
	 * @see #getPathCalculator()
	 * @generated
	 */
	EAttribute getPathCalculator_DistanceMethod();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.PathCalculator#compute(main.game.maze.behaviour.Position) <em>Compute</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compute</em>' operation.
	 * @see main.game.maze.behaviour.PathCalculator#compute(main.game.maze.behaviour.Position)
	 * @generated
	 */
	EOperation getPathCalculator__Compute__Position();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.DijkstraPathCalculator <em>Dijkstra Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dijkstra Path Calculator</em>'.
	 * @see main.game.maze.behaviour.DijkstraPathCalculator
	 * @generated
	 */
	EClass getDijkstraPathCalculator();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.DijkstraPathCalculator#getMaxPathLength <em>Max Path Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Path Length</em>'.
	 * @see main.game.maze.behaviour.DijkstraPathCalculator#getMaxPathLength()
	 * @see #getDijkstraPathCalculator()
	 * @generated
	 */
	EAttribute getDijkstraPathCalculator_MaxPathLength();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.AstarPathCalculator <em>Astar Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Astar Path Calculator</em>'.
	 * @see main.game.maze.behaviour.AstarPathCalculator
	 * @generated
	 */
	EClass getAstarPathCalculator();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AstarPathCalculator#getMaxPathLength <em>Max Path Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Path Length</em>'.
	 * @see main.game.maze.behaviour.AstarPathCalculator#getMaxPathLength()
	 * @see #getAstarPathCalculator()
	 * @generated
	 */
	EAttribute getAstarPathCalculator_MaxPathLength();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.LocalPathCalculator <em>Local Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Path Calculator</em>'.
	 * @see main.game.maze.behaviour.LocalPathCalculator
	 * @generated
	 */
	EClass getLocalPathCalculator();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.CharacterEvent <em>Character Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Character Event</em>'.
	 * @see main.game.maze.behaviour.CharacterEvent
	 * @generated
	 */
	EClass getCharacterEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.CharacterEvent#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see main.game.maze.behaviour.CharacterEvent#getProbability()
	 * @see #getCharacterEvent()
	 * @generated
	 */
	EAttribute getCharacterEvent_Probability();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.CharacterEvent#getSubscriber <em>Subscriber</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subscriber</em>'.
	 * @see main.game.maze.behaviour.CharacterEvent#getSubscriber()
	 * @see #getCharacterEvent()
	 * @generated
	 */
	EReference getCharacterEvent_Subscriber();

	/**
	 * Returns the meta object for the '{@link main.game.maze.behaviour.CharacterEvent#notifySubscribers() <em>Notify Subscribers</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Notify Subscribers</em>' operation.
	 * @see main.game.maze.behaviour.CharacterEvent#notifySubscribers()
	 * @generated
	 */
	EOperation getCharacterEvent__NotifySubscribers();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.HealthEvent <em>Health Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Health Event</em>'.
	 * @see main.game.maze.behaviour.HealthEvent
	 * @generated
	 */
	EClass getHealthEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.HealthEvent#getHealthAmount <em>Health Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Health Amount</em>'.
	 * @see main.game.maze.behaviour.HealthEvent#getHealthAmount()
	 * @see #getHealthEvent()
	 * @generated
	 */
	EAttribute getHealthEvent_HealthAmount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.HealthEvent#getHealthPercentage <em>Health Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Health Percentage</em>'.
	 * @see main.game.maze.behaviour.HealthEvent#getHealthPercentage()
	 * @see #getHealthEvent()
	 * @generated
	 */
	EAttribute getHealthEvent_HealthPercentage();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.SpeedEvent <em>Speed Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Speed Event</em>'.
	 * @see main.game.maze.behaviour.SpeedEvent
	 * @generated
	 */
	EClass getSpeedEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.SpeedEvent#getSpeedAmount <em>Speed Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Speed Amount</em>'.
	 * @see main.game.maze.behaviour.SpeedEvent#getSpeedAmount()
	 * @see #getSpeedEvent()
	 * @generated
	 */
	EAttribute getSpeedEvent_SpeedAmount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.SpeedEvent#getSpeedPercentage <em>Speed Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Speed Percentage</em>'.
	 * @see main.game.maze.behaviour.SpeedEvent#getSpeedPercentage()
	 * @see #getSpeedEvent()
	 * @generated
	 */
	EAttribute getSpeedEvent_SpeedPercentage();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.TimeEvent <em>Time Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Event</em>'.
	 * @see main.game.maze.behaviour.TimeEvent
	 * @generated
	 */
	EClass getTimeEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.TimeEvent#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see main.game.maze.behaviour.TimeEvent#getTime()
	 * @see #getTimeEvent()
	 * @generated
	 */
	EAttribute getTimeEvent_Time();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.VisionEvent <em>Vision Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vision Event</em>'.
	 * @see main.game.maze.behaviour.VisionEvent
	 * @generated
	 */
	EClass getVisionEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.VisionEvent#getRadiusAmount <em>Radius Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Radius Amount</em>'.
	 * @see main.game.maze.behaviour.VisionEvent#getRadiusAmount()
	 * @see #getVisionEvent()
	 * @generated
	 */
	EAttribute getVisionEvent_RadiusAmount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.VisionEvent#getRadiusPercentage <em>Radius Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Radius Percentage</em>'.
	 * @see main.game.maze.behaviour.VisionEvent#getRadiusPercentage()
	 * @see #getVisionEvent()
	 * @generated
	 */
	EAttribute getVisionEvent_RadiusPercentage();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.AttackEvent <em>Attack Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attack Event</em>'.
	 * @see main.game.maze.behaviour.AttackEvent
	 * @generated
	 */
	EClass getAttackEvent();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AttackEvent#getRadiusPercentage <em>Radius Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Radius Percentage</em>'.
	 * @see main.game.maze.behaviour.AttackEvent#getRadiusPercentage()
	 * @see #getAttackEvent()
	 * @generated
	 */
	EAttribute getAttackEvent_RadiusPercentage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AttackEvent#getRadiusAmount <em>Radius Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Radius Amount</em>'.
	 * @see main.game.maze.behaviour.AttackEvent#getRadiusAmount()
	 * @see #getAttackEvent()
	 * @generated
	 */
	EAttribute getAttackEvent_RadiusAmount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AttackEvent#getDamageAmount <em>Damage Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Damage Amount</em>'.
	 * @see main.game.maze.behaviour.AttackEvent#getDamageAmount()
	 * @see #getAttackEvent()
	 * @generated
	 */
	EAttribute getAttackEvent_DamageAmount();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AttackEvent#getDamagePercentage <em>Damage Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Damage Percentage</em>'.
	 * @see main.game.maze.behaviour.AttackEvent#getDamagePercentage()
	 * @see #getAttackEvent()
	 * @generated
	 */
	EAttribute getAttackEvent_DamagePercentage();

	/**
	 * Returns the meta object for class '{@link main.game.maze.behaviour.PatrolZone <em>Patrol Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patrol Zone</em>'.
	 * @see main.game.maze.behaviour.PatrolZone
	 * @generated
	 */
	EClass getPatrolZone();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolZone#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see main.game.maze.behaviour.PatrolZone#getWidth()
	 * @see #getPatrolZone()
	 * @generated
	 */
	EAttribute getPatrolZone_Width();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolZone#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see main.game.maze.behaviour.PatrolZone#getHeight()
	 * @see #getPatrolZone()
	 * @generated
	 */
	EAttribute getPatrolZone_Height();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.PatrolZone#getTopLeft <em>Top Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Top Left</em>'.
	 * @see main.game.maze.behaviour.PatrolZone#getTopLeft()
	 * @see #getPatrolZone()
	 * @generated
	 */
	EReference getPatrolZone_TopLeft();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.behaviour.DistanceMethod <em>Distance Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Distance Method</em>'.
	 * @see main.game.maze.behaviour.DistanceMethod
	 * @generated
	 */
	EEnum getDistanceMethod();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.behaviour.PatrolPathBehavior <em>Patrol Path Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Patrol Path Behavior</em>'.
	 * @see main.game.maze.behaviour.PatrolPathBehavior
	 * @generated
	 */
	EEnum getPatrolPathBehavior();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BehaviourFactory getBehaviourFactory();

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
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.DirectionImpl <em>Direction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.DirectionImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDirection()
		 * @generated
		 */
		EClass DIRECTION = eINSTANCE.getDirection();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIRECTION__START_POSITION = eINSTANCE.getDirection_StartPosition();

		/**
		 * The meta object literal for the '<em><b>End Position</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIRECTION__END_POSITION = eINSTANCE.getDirection_EndPosition();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.RandomBehaviorImpl <em>Random Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.RandomBehaviorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getRandomBehavior()
		 * @generated
		 */
		EClass RANDOM_BEHAVIOR = eINSTANCE.getRandomBehavior();

		/**
		 * The meta object literal for the '<em><b>Regen Per Second</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANDOM_BEHAVIOR__REGEN_PER_SECOND = eINSTANCE.getRandomBehavior_RegenPerSecond();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl <em>Patrol Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.PatrolBehaviorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolBehavior()
		 * @generated
		 */
		EClass PATROL_BEHAVIOR = eINSTANCE.getPatrolBehavior();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_BEHAVIOR__PATH = eINSTANCE.getPatrolBehavior_Path();

		/**
		 * The meta object literal for the '<em><b>Current Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_BEHAVIOR__CURRENT_INDEX = eINSTANCE.getPatrolBehavior_CurrentIndex();

		/**
		 * The meta object literal for the '<em><b>Pathcalculator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_BEHAVIOR__PATHCALCULATOR = eINSTANCE.getPatrolBehavior_Pathcalculator();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_BEHAVIOR__BEHAVIOR = eINSTANCE.getPatrolBehavior_Behavior();

		/**
		 * The meta object literal for the '<em><b>Patrol Zone</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_BEHAVIOR__PATROL_ZONE = eINSTANCE.getPatrolBehavior_PatrolZone();

		/**
		 * The meta object literal for the '<em><b>Next Index</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PATROL_BEHAVIOR___NEXT_INDEX = eINSTANCE.getPatrolBehavior__NextIndex();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.ChaseBehaviorImpl <em>Chase Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.ChaseBehaviorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getChaseBehavior()
		 * @generated
		 */
		EClass CHASE_BEHAVIOR = eINSTANCE.getChaseBehavior();

		/**
		 * The meta object literal for the '<em><b>Relative Position Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET = eINSTANCE.getChaseBehavior_RelativePositionTarget();

		/**
		 * The meta object literal for the '<em><b>Pathcalculator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHASE_BEHAVIOR__PATHCALCULATOR = eINSTANCE.getChaseBehavior_Pathcalculator();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.MovementBehaviorImpl <em>Movement Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.MovementBehaviorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getMovementBehavior()
		 * @generated
		 */
		EClass MOVEMENT_BEHAVIOR = eINSTANCE.getMovementBehavior();

		/**
		 * The meta object literal for the '<em><b>Charactertype</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVEMENT_BEHAVIOR__CHARACTERTYPE = eINSTANCE.getMovementBehavior_Charactertype();

		/**
		 * The meta object literal for the '<em><b>Ignore Walls</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__IGNORE_WALLS = eINSTANCE.getMovementBehavior_IgnoreWalls();

		/**
		 * The meta object literal for the '<em><b>Instant Kill On Collision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION = eINSTANCE.getMovementBehavior_InstantKillOnCollision();

		/**
		 * The meta object literal for the '<em><b>Base Vision Range</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__BASE_VISION_RANGE = eINSTANCE.getMovementBehavior_BaseVisionRange();

		/**
		 * The meta object literal for the '<em><b>Additional Vision Range</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE = eINSTANCE.getMovementBehavior_AdditionalVisionRange();

		/**
		 * The meta object literal for the '<em><b>Vision Range Multiplier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER = eINSTANCE.getMovementBehavior_VisionRangeMultiplier();

		/**
		 * The meta object literal for the '<em><b>Vision Range</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__VISION_RANGE = eINSTANCE.getMovementBehavior_VisionRange();

		/**
		 * The meta object literal for the '<em><b>Next Positions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVEMENT_BEHAVIOR__NEXT_POSITIONS = eINSTANCE.getMovementBehavior_NextPositions();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVEMENT_BEHAVIOR__POSITION = eINSTANCE.getMovementBehavior_Position();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVEMENT_BEHAVIOR__DIRECTION = eINSTANCE.getMovementBehavior_Direction();

		/**
		 * The meta object literal for the '<em><b>Move</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___MOVE = eINSTANCE.getMovementBehavior__Move();

		/**
		 * The meta object literal for the '<em><b>Update</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT = eINSTANCE.getMovementBehavior__Update__HealthEvent();

		/**
		 * The meta object literal for the '<em><b>Update</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT = eINSTANCE.getMovementBehavior__Update__SpeedEvent();

		/**
		 * The meta object literal for the '<em><b>Update</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT = eINSTANCE.getMovementBehavior__Update__TimeEvent();

		/**
		 * The meta object literal for the '<em><b>Update</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT = eINSTANCE.getMovementBehavior__Update__AttackEvent();

		/**
		 * The meta object literal for the '<em><b>Update</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT = eINSTANCE.getMovementBehavior__Update__VisionEvent();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.PositionImpl <em>Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.PositionImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPosition()
		 * @generated
		 */
		EClass POSITION = eINSTANCE.getPosition();

		/**
		 * The meta object literal for the '<em><b>Pos X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__POS_X = eINSTANCE.getPosition_PosX();

		/**
		 * The meta object literal for the '<em><b>Pos Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__POS_Y = eINSTANCE.getPosition_PosY();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.PatrolPointImpl <em>Patrol Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.PatrolPointImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolPoint()
		 * @generated
		 */
		EClass PATROL_POINT = eINSTANCE.getPatrolPoint();

		/**
		 * The meta object literal for the '<em><b>Point</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_POINT__POINT = eINSTANCE.getPatrolPoint_Point();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_POINT__EVENTS = eINSTANCE.getPatrolPoint_Events();

		/**
		 * The meta object literal for the '<em><b>Trigger Events</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PATROL_POINT___TRIGGER_EVENTS = eINSTANCE.getPatrolPoint__TriggerEvents();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.PathCalculatorImpl <em>Path Calculator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.PathCalculatorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPathCalculator()
		 * @generated
		 */
		EClass PATH_CALCULATOR = eINSTANCE.getPathCalculator();

		/**
		 * The meta object literal for the '<em><b>Distance Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATH_CALCULATOR__DISTANCE_METHOD = eINSTANCE.getPathCalculator_DistanceMethod();

		/**
		 * The meta object literal for the '<em><b>Compute</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PATH_CALCULATOR___COMPUTE__POSITION = eINSTANCE.getPathCalculator__Compute__Position();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl <em>Dijkstra Path Calculator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDijkstraPathCalculator()
		 * @generated
		 */
		EClass DIJKSTRA_PATH_CALCULATOR = eINSTANCE.getDijkstraPathCalculator();

		/**
		 * The meta object literal for the '<em><b>Max Path Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH = eINSTANCE.getDijkstraPathCalculator_MaxPathLength();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.AstarPathCalculatorImpl <em>Astar Path Calculator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.AstarPathCalculatorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getAstarPathCalculator()
		 * @generated
		 */
		EClass ASTAR_PATH_CALCULATOR = eINSTANCE.getAstarPathCalculator();

		/**
		 * The meta object literal for the '<em><b>Max Path Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH = eINSTANCE.getAstarPathCalculator_MaxPathLength();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.LocalPathCalculatorImpl <em>Local Path Calculator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.LocalPathCalculatorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getLocalPathCalculator()
		 * @generated
		 */
		EClass LOCAL_PATH_CALCULATOR = eINSTANCE.getLocalPathCalculator();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.CharacterEventImpl <em>Character Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.CharacterEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getCharacterEvent()
		 * @generated
		 */
		EClass CHARACTER_EVENT = eINSTANCE.getCharacterEvent();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_EVENT__PROBABILITY = eINSTANCE.getCharacterEvent_Probability();

		/**
		 * The meta object literal for the '<em><b>Subscriber</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHARACTER_EVENT__SUBSCRIBER = eINSTANCE.getCharacterEvent_Subscriber();

		/**
		 * The meta object literal for the '<em><b>Notify Subscribers</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CHARACTER_EVENT___NOTIFY_SUBSCRIBERS = eINSTANCE.getCharacterEvent__NotifySubscribers();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.HealthEventImpl <em>Health Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.HealthEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getHealthEvent()
		 * @generated
		 */
		EClass HEALTH_EVENT = eINSTANCE.getHealthEvent();

		/**
		 * The meta object literal for the '<em><b>Health Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTH_EVENT__HEALTH_AMOUNT = eINSTANCE.getHealthEvent_HealthAmount();

		/**
		 * The meta object literal for the '<em><b>Health Percentage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEALTH_EVENT__HEALTH_PERCENTAGE = eINSTANCE.getHealthEvent_HealthPercentage();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.SpeedEventImpl <em>Speed Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.SpeedEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getSpeedEvent()
		 * @generated
		 */
		EClass SPEED_EVENT = eINSTANCE.getSpeedEvent();

		/**
		 * The meta object literal for the '<em><b>Speed Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPEED_EVENT__SPEED_AMOUNT = eINSTANCE.getSpeedEvent_SpeedAmount();

		/**
		 * The meta object literal for the '<em><b>Speed Percentage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPEED_EVENT__SPEED_PERCENTAGE = eINSTANCE.getSpeedEvent_SpeedPercentage();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.TimeEventImpl <em>Time Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.TimeEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getTimeEvent()
		 * @generated
		 */
		EClass TIME_EVENT = eINSTANCE.getTimeEvent();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_EVENT__TIME = eINSTANCE.getTimeEvent_Time();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.VisionEventImpl <em>Vision Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.VisionEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getVisionEvent()
		 * @generated
		 */
		EClass VISION_EVENT = eINSTANCE.getVisionEvent();

		/**
		 * The meta object literal for the '<em><b>Radius Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VISION_EVENT__RADIUS_AMOUNT = eINSTANCE.getVisionEvent_RadiusAmount();

		/**
		 * The meta object literal for the '<em><b>Radius Percentage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VISION_EVENT__RADIUS_PERCENTAGE = eINSTANCE.getVisionEvent_RadiusPercentage();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.AttackEventImpl <em>Attack Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.AttackEventImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getAttackEvent()
		 * @generated
		 */
		EClass ATTACK_EVENT = eINSTANCE.getAttackEvent();

		/**
		 * The meta object literal for the '<em><b>Radius Percentage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTACK_EVENT__RADIUS_PERCENTAGE = eINSTANCE.getAttackEvent_RadiusPercentage();

		/**
		 * The meta object literal for the '<em><b>Radius Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTACK_EVENT__RADIUS_AMOUNT = eINSTANCE.getAttackEvent_RadiusAmount();

		/**
		 * The meta object literal for the '<em><b>Damage Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTACK_EVENT__DAMAGE_AMOUNT = eINSTANCE.getAttackEvent_DamageAmount();

		/**
		 * The meta object literal for the '<em><b>Damage Percentage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTACK_EVENT__DAMAGE_PERCENTAGE = eINSTANCE.getAttackEvent_DamagePercentage();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.PatrolZoneImpl <em>Patrol Zone</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.PatrolZoneImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolZone()
		 * @generated
		 */
		EClass PATROL_ZONE = eINSTANCE.getPatrolZone();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_ZONE__WIDTH = eINSTANCE.getPatrolZone_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_ZONE__HEIGHT = eINSTANCE.getPatrolZone_Height();

		/**
		 * The meta object literal for the '<em><b>Top Left</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_ZONE__TOP_LEFT = eINSTANCE.getPatrolZone_TopLeft();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.DistanceMethod <em>Distance Method</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.DistanceMethod
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDistanceMethod()
		 * @generated
		 */
		EEnum DISTANCE_METHOD = eINSTANCE.getDistanceMethod();

		/**
		 * The meta object literal for the '{@link main.game.maze.behaviour.PatrolPathBehavior <em>Patrol Path Behavior</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.PatrolPathBehavior
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolPathBehavior()
		 * @generated
		 */
		EEnum PATROL_PATH_BEHAVIOR = eINSTANCE.getPatrolPathBehavior();

	}

} //BehaviourPackage
