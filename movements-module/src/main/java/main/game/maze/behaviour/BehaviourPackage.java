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
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.MovementBehaviorImpl <em>Movement Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.MovementBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getMovementBehavior()
	 * @generated
	 */
	int MOVEMENT_BEHAVIOR = 3;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__IGNORE_WALLS = 0;

	/**
	 * The feature id for the '<em><b>Attack Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__ATTACK_RADIUS = 1;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION = 2;

	/**
	 * The number of structural features of the '<em>Movement Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Next</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR___NEXT = 0;

	/**
	 * The number of operations of the '<em>Movement Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVEMENT_BEHAVIOR_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.RandomBehaviorImpl <em>Random Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.RandomBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getRandomBehavior()
	 * @generated
	 */
	int RANDOM_BEHAVIOR = 0;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Attack Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__ATTACK_RADIUS = MOVEMENT_BEHAVIOR__ATTACK_RADIUS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

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
	 * The operation id for the '<em>Next</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR___NEXT = MOVEMENT_BEHAVIOR___NEXT;

	/**
	 * The number of operations of the '<em>Random Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl <em>Patrol Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolBehavior()
	 * @generated
	 */
	int PATROL_BEHAVIOR = 1;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Attack Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__ATTACK_RADIUS = MOVEMENT_BEHAVIOR__ATTACK_RADIUS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

	/**
	 * The feature id for the '<em><b>Path</b></em>' reference list.
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
	 * The feature id for the '<em><b>Pathcalculator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__PATHCALCULATOR = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Patrol Zone</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR__PATROL_ZONE = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Patrol Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR_FEATURE_COUNT = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Next</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR___NEXT = MOVEMENT_BEHAVIOR___NEXT;

	/**
	 * The number of operations of the '<em>Patrol Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.ChaseBehaviorImpl <em>Chase Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.ChaseBehaviorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getChaseBehavior()
	 * @generated
	 */
	int CHASE_BEHAVIOR = 2;

	/**
	 * The feature id for the '<em><b>Ignore Walls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__IGNORE_WALLS = MOVEMENT_BEHAVIOR__IGNORE_WALLS;

	/**
	 * The feature id for the '<em><b>Attack Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__ATTACK_RADIUS = MOVEMENT_BEHAVIOR__ATTACK_RADIUS;

	/**
	 * The feature id for the '<em><b>Instant Kill On Collision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__INSTANT_KILL_ON_COLLISION = MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION;

	/**
	 * The feature id for the '<em><b>Relative Position Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET = MOVEMENT_BEHAVIOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pathcalculator</b></em>' reference.
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
	 * The operation id for the '<em>Next</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR___NEXT = MOVEMENT_BEHAVIOR___NEXT;

	/**
	 * The number of operations of the '<em>Chase Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHASE_BEHAVIOR_OPERATION_COUNT = MOVEMENT_BEHAVIOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PositionImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 4;

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
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolPointImpl <em>Patrol Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolPointImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolPoint()
	 * @generated
	 */
	int PATROL_POINT = 5;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT__TIME = 0;

	/**
	 * The feature id for the '<em><b>Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT__POINT = 1;

	/**
	 * The feature id for the '<em><b>Regen Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT__REGEN_AMOUNT = 2;

	/**
	 * The number of structural features of the '<em>Patrol Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Patrol Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATROL_POINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PathCalculatorImpl <em>Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPathCalculator()
	 * @generated
	 */
	int PATH_CALCULATOR = 6;

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
	 * The number of operations of the '<em>Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_CALCULATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl <em>Dijkstra Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDijkstraPathCalculator()
	 * @generated
	 */
	int DIJKSTRA_PATH_CALCULATOR = 7;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR__DISTANCE_METHOD = PATH_CALCULATOR__DISTANCE_METHOD;

	/**
	 * The feature id for the '<em><b>Max Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR__MAX_ITERATIONS = PATH_CALCULATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dijkstra Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR_FEATURE_COUNT = PATH_CALCULATOR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dijkstra Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIJKSTRA_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.AstarPathCalculatorImpl <em>Astar Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.AstarPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getAstarPathCalculator()
	 * @generated
	 */
	int ASTAR_PATH_CALCULATOR = 8;

	/**
	 * The feature id for the '<em><b>Distance Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR__DISTANCE_METHOD = PATH_CALCULATOR__DISTANCE_METHOD;

	/**
	 * The feature id for the '<em><b>Max Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR__MAX_ITERATIONS = PATH_CALCULATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Astar Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR_FEATURE_COUNT = PATH_CALCULATOR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Astar Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASTAR_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.LocalPathCalculatorImpl <em>Local Path Calculator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.LocalPathCalculatorImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getLocalPathCalculator()
	 * @generated
	 */
	int LOCAL_PATH_CALCULATOR = 9;

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
	 * The number of operations of the '<em>Local Path Calculator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_PATH_CALCULATOR_OPERATION_COUNT = PATH_CALCULATOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.behaviour.impl.PatrolZoneImpl <em>Patrol Zone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.impl.PatrolZoneImpl
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getPatrolZone()
	 * @generated
	 */
	int PATROL_ZONE = 10;

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
	 * The meta object id for the '{@link main.game.maze.behaviour.DistanceMethod <em>Distance Method</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.behaviour.DistanceMethod
	 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDistanceMethod()
	 * @generated
	 */
	int DISTANCE_METHOD = 11;


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
	 * Returns the meta object for the reference list '{@link main.game.maze.behaviour.PatrolBehavior#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Path</em>'.
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
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.PatrolBehavior#getPathcalculator <em>Pathcalculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pathcalculator</em>'.
	 * @see main.game.maze.behaviour.PatrolBehavior#getPathcalculator()
	 * @see #getPatrolBehavior()
	 * @generated
	 */
	EReference getPatrolBehavior_Pathcalculator();

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
	 * Returns the meta object for class '{@link main.game.maze.behaviour.ChaseBehavior <em>Chase Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chase Behavior</em>'.
	 * @see main.game.maze.behaviour.ChaseBehavior
	 * @generated
	 */
	EClass getChaseBehavior();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget <em>Relative Position Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Relative Position Target</em>'.
	 * @see main.game.maze.behaviour.ChaseBehavior#getRelativePositionTarget()
	 * @see #getChaseBehavior()
	 * @generated
	 */
	EReference getChaseBehavior_RelativePositionTarget();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.ChaseBehavior#getPathcalculator <em>Pathcalculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pathcalculator</em>'.
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
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.MovementBehavior#getAttackRadius <em>Attack Radius</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Radius</em>'.
	 * @see main.game.maze.behaviour.MovementBehavior#getAttackRadius()
	 * @see #getMovementBehavior()
	 * @generated
	 */
	EAttribute getMovementBehavior_AttackRadius();

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
	 * Returns the meta object for the '{@link main.game.maze.behaviour.MovementBehavior#next() <em>Next</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Next</em>' operation.
	 * @see main.game.maze.behaviour.MovementBehavior#next()
	 * @generated
	 */
	EOperation getMovementBehavior__Next();

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
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolPoint#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint#getTime()
	 * @see #getPatrolPoint()
	 * @generated
	 */
	EAttribute getPatrolPoint_Time();

	/**
	 * Returns the meta object for the reference '{@link main.game.maze.behaviour.PatrolPoint#getPoint <em>Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Point</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint#getPoint()
	 * @see #getPatrolPoint()
	 * @generated
	 */
	EReference getPatrolPoint_Point();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.PatrolPoint#getRegenAmount <em>Regen Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Regen Amount</em>'.
	 * @see main.game.maze.behaviour.PatrolPoint#getRegenAmount()
	 * @see #getPatrolPoint()
	 * @generated
	 */
	EAttribute getPatrolPoint_RegenAmount();

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
	 * Returns the meta object for class '{@link main.game.maze.behaviour.DijkstraPathCalculator <em>Dijkstra Path Calculator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dijkstra Path Calculator</em>'.
	 * @see main.game.maze.behaviour.DijkstraPathCalculator
	 * @generated
	 */
	EClass getDijkstraPathCalculator();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.DijkstraPathCalculator#getMaxIterations <em>Max Iterations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Iterations</em>'.
	 * @see main.game.maze.behaviour.DijkstraPathCalculator#getMaxIterations()
	 * @see #getDijkstraPathCalculator()
	 * @generated
	 */
	EAttribute getDijkstraPathCalculator_MaxIterations();

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
	 * Returns the meta object for the attribute '{@link main.game.maze.behaviour.AstarPathCalculator#getMaxIterations <em>Max Iterations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Iterations</em>'.
	 * @see main.game.maze.behaviour.AstarPathCalculator#getMaxIterations()
	 * @see #getAstarPathCalculator()
	 * @generated
	 */
	EAttribute getAstarPathCalculator_MaxIterations();

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
		 * The meta object literal for the '<em><b>Path</b></em>' reference list feature.
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
		 * The meta object literal for the '<em><b>Pathcalculator</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_BEHAVIOR__PATHCALCULATOR = eINSTANCE.getPatrolBehavior_Pathcalculator();

		/**
		 * The meta object literal for the '<em><b>Patrol Zone</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_BEHAVIOR__PATROL_ZONE = eINSTANCE.getPatrolBehavior_PatrolZone();

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
		 * The meta object literal for the '<em><b>Relative Position Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET = eINSTANCE.getChaseBehavior_RelativePositionTarget();

		/**
		 * The meta object literal for the '<em><b>Pathcalculator</b></em>' reference feature.
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
		 * The meta object literal for the '<em><b>Ignore Walls</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__IGNORE_WALLS = eINSTANCE.getMovementBehavior_IgnoreWalls();

		/**
		 * The meta object literal for the '<em><b>Attack Radius</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__ATTACK_RADIUS = eINSTANCE.getMovementBehavior_AttackRadius();

		/**
		 * The meta object literal for the '<em><b>Instant Kill On Collision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION = eINSTANCE.getMovementBehavior_InstantKillOnCollision();

		/**
		 * The meta object literal for the '<em><b>Next</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVEMENT_BEHAVIOR___NEXT = eINSTANCE.getMovementBehavior__Next();

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
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_POINT__TIME = eINSTANCE.getPatrolPoint_Time();

		/**
		 * The meta object literal for the '<em><b>Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATROL_POINT__POINT = eINSTANCE.getPatrolPoint_Point();

		/**
		 * The meta object literal for the '<em><b>Regen Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATROL_POINT__REGEN_AMOUNT = eINSTANCE.getPatrolPoint_RegenAmount();

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
		 * The meta object literal for the '{@link main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl <em>Dijkstra Path Calculator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.behaviour.impl.DijkstraPathCalculatorImpl
		 * @see main.game.maze.behaviour.impl.BehaviourPackageImpl#getDijkstraPathCalculator()
		 * @generated
		 */
		EClass DIJKSTRA_PATH_CALCULATOR = eINSTANCE.getDijkstraPathCalculator();

		/**
		 * The meta object literal for the '<em><b>Max Iterations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIJKSTRA_PATH_CALCULATOR__MAX_ITERATIONS = eINSTANCE.getDijkstraPathCalculator_MaxIterations();

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
		 * The meta object literal for the '<em><b>Max Iterations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASTAR_PATH_CALCULATOR__MAX_ITERATIONS = eINSTANCE.getAstarPathCalculator_MaxIterations();

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

	}

} //BehaviourPackage
