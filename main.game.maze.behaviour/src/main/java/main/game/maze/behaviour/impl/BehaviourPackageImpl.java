/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.AstarPathCalculator;
import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.ChaseBehavior;
import main.game.maze.behaviour.DijkstraPathCalculator;
import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.behaviour.LocalPathCalculator;
import main.game.maze.behaviour.MovementBehavior;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.PatrolZone;
import main.game.maze.behaviour.Position;
import main.game.maze.behaviour.RandomBehavior;

import main.game.maze.behaviour.util.BehaviourValidator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BehaviourPackageImpl extends EPackageImpl implements BehaviourPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass randomBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patrolBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chaseBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass movementBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patrolPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pathCalculatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dijkstraPathCalculatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass astarPathCalculatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localPathCalculatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patrolZoneEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum distanceMethodEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see main.game.maze.behaviour.BehaviourPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BehaviourPackageImpl() {
		super(eNS_URI, BehaviourFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link BehaviourPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BehaviourPackage init() {
		if (isInited) return (BehaviourPackage)EPackage.Registry.INSTANCE.getEPackage(BehaviourPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBehaviourPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BehaviourPackageImpl theBehaviourPackage = registeredBehaviourPackage instanceof BehaviourPackageImpl ? (BehaviourPackageImpl)registeredBehaviourPackage : new BehaviourPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theBehaviourPackage.createPackageContents();

		// Initialize created meta-data
		theBehaviourPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theBehaviourPackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return BehaviourValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theBehaviourPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BehaviourPackage.eNS_URI, theBehaviourPackage);
		return theBehaviourPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRandomBehavior() {
		return randomBehaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRandomBehavior_RegenPerSecond() {
		return (EAttribute)randomBehaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPatrolBehavior() {
		return patrolBehaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolBehavior_Path() {
		return (EReference)patrolBehaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPatrolBehavior_StartIndex() {
		return (EAttribute)patrolBehaviorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolBehavior_Pathcalculator() {
		return (EReference)patrolBehaviorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolBehavior_PatrolZone() {
		return (EReference)patrolBehaviorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChaseBehavior() {
		return chaseBehaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChaseBehavior_RelativePositionTarget() {
		return (EReference)chaseBehaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChaseBehavior_Pathcalculator() {
		return (EReference)chaseBehaviorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMovementBehavior() {
		return movementBehaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_IgnoreWalls() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_AttackRadius() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_InstantKillOnCollision() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_MovementSpeed() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPosition() {
		return positionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPosition_PosX() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPosition_PosY() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPatrolPoint() {
		return patrolPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPatrolPoint_Time() {
		return (EAttribute)patrolPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolPoint_Point() {
		return (EReference)patrolPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPatrolPoint_RegenAmount() {
		return (EAttribute)patrolPointEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPathCalculator() {
		return pathCalculatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPathCalculator_DistanceMethod() {
		return (EAttribute)pathCalculatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDijkstraPathCalculator() {
		return dijkstraPathCalculatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDijkstraPathCalculator_MaxIterations() {
		return (EAttribute)dijkstraPathCalculatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAstarPathCalculator() {
		return astarPathCalculatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAstarPathCalculator_MaxIterations() {
		return (EAttribute)astarPathCalculatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocalPathCalculator() {
		return localPathCalculatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPatrolZone() {
		return patrolZoneEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPatrolZone_Width() {
		return (EAttribute)patrolZoneEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPatrolZone_Height() {
		return (EAttribute)patrolZoneEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolZone_TopLeft() {
		return (EReference)patrolZoneEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDistanceMethod() {
		return distanceMethodEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BehaviourFactory getBehaviourFactory() {
		return (BehaviourFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		randomBehaviorEClass = createEClass(RANDOM_BEHAVIOR);
		createEAttribute(randomBehaviorEClass, RANDOM_BEHAVIOR__REGEN_PER_SECOND);

		patrolBehaviorEClass = createEClass(PATROL_BEHAVIOR);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATH);
		createEAttribute(patrolBehaviorEClass, PATROL_BEHAVIOR__START_INDEX);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATHCALCULATOR);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATROL_ZONE);

		chaseBehaviorEClass = createEClass(CHASE_BEHAVIOR);
		createEReference(chaseBehaviorEClass, CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET);
		createEReference(chaseBehaviorEClass, CHASE_BEHAVIOR__PATHCALCULATOR);

		movementBehaviorEClass = createEClass(MOVEMENT_BEHAVIOR);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__IGNORE_WALLS);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__ATTACK_RADIUS);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__MOVEMENT_SPEED);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__POS_X);
		createEAttribute(positionEClass, POSITION__POS_Y);

		patrolPointEClass = createEClass(PATROL_POINT);
		createEAttribute(patrolPointEClass, PATROL_POINT__TIME);
		createEReference(patrolPointEClass, PATROL_POINT__POINT);
		createEAttribute(patrolPointEClass, PATROL_POINT__REGEN_AMOUNT);

		pathCalculatorEClass = createEClass(PATH_CALCULATOR);
		createEAttribute(pathCalculatorEClass, PATH_CALCULATOR__DISTANCE_METHOD);

		dijkstraPathCalculatorEClass = createEClass(DIJKSTRA_PATH_CALCULATOR);
		createEAttribute(dijkstraPathCalculatorEClass, DIJKSTRA_PATH_CALCULATOR__MAX_ITERATIONS);

		astarPathCalculatorEClass = createEClass(ASTAR_PATH_CALCULATOR);
		createEAttribute(astarPathCalculatorEClass, ASTAR_PATH_CALCULATOR__MAX_ITERATIONS);

		localPathCalculatorEClass = createEClass(LOCAL_PATH_CALCULATOR);

		patrolZoneEClass = createEClass(PATROL_ZONE);
		createEAttribute(patrolZoneEClass, PATROL_ZONE__WIDTH);
		createEAttribute(patrolZoneEClass, PATROL_ZONE__HEIGHT);
		createEReference(patrolZoneEClass, PATROL_ZONE__TOP_LEFT);

		// Create enums
		distanceMethodEEnum = createEEnum(DISTANCE_METHOD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		randomBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		patrolBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		chaseBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		dijkstraPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());
		astarPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());
		localPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());

		// Initialize classes, features, and operations; add parameters
		initEClass(randomBehaviorEClass, RandomBehavior.class, "RandomBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRandomBehavior_RegenPerSecond(), ecorePackage.getEDouble(), "regenPerSecond", null, 1, 1, RandomBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patrolBehaviorEClass, PatrolBehavior.class, "PatrolBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatrolBehavior_Path(), this.getPatrolPoint(), null, "path", null, 1, -1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolBehavior_StartIndex(), ecorePackage.getEInt(), "startIndex", null, 1, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolBehavior_Pathcalculator(), this.getPathCalculator(), null, "pathcalculator", null, 1, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolBehavior_PatrolZone(), this.getPatrolZone(), null, "patrolZone", null, 0, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(chaseBehaviorEClass, ChaseBehavior.class, "ChaseBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChaseBehavior_RelativePositionTarget(), this.getPosition(), null, "relativePositionTarget", null, 1, 1, ChaseBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChaseBehavior_Pathcalculator(), this.getPathCalculator(), null, "pathcalculator", null, 1, 1, ChaseBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(movementBehaviorEClass, MovementBehavior.class, "MovementBehavior", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMovementBehavior_IgnoreWalls(), ecorePackage.getEBoolean(), "ignoreWalls", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_AttackRadius(), ecorePackage.getEDouble(), "attackRadius", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_InstantKillOnCollision(), ecorePackage.getEBoolean(), "instantKillOnCollision", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_MovementSpeed(), ecorePackage.getEDouble(), "movementSpeed", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_PosX(), ecorePackage.getEDouble(), "posX", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPosition_PosY(), ecorePackage.getEDouble(), "posY", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patrolPointEClass, PatrolPoint.class, "PatrolPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPatrolPoint_Time(), ecorePackage.getEInt(), "time", null, 1, 1, PatrolPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolPoint_Point(), this.getPosition(), null, "point", null, 1, 1, PatrolPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolPoint_RegenAmount(), ecorePackage.getEDouble(), "regenAmount", null, 1, 1, PatrolPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pathCalculatorEClass, PathCalculator.class, "PathCalculator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPathCalculator_DistanceMethod(), this.getDistanceMethod(), "distanceMethod", null, 1, 1, PathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dijkstraPathCalculatorEClass, DijkstraPathCalculator.class, "DijkstraPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDijkstraPathCalculator_MaxIterations(), ecorePackage.getEInt(), "maxIterations", null, 1, 1, DijkstraPathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(astarPathCalculatorEClass, AstarPathCalculator.class, "AstarPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAstarPathCalculator_MaxIterations(), ecorePackage.getEInt(), "maxIterations", null, 1, 1, AstarPathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localPathCalculatorEClass, LocalPathCalculator.class, "LocalPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(patrolZoneEClass, PatrolZone.class, "PatrolZone", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPatrolZone_Width(), ecorePackage.getEDouble(), "width", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolZone_Height(), ecorePackage.getEDouble(), "height", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolZone_TopLeft(), this.getPosition(), null, "topLeft", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(distanceMethodEEnum, DistanceMethod.class, "DistanceMethod");
		addEEnumLiteral(distanceMethodEEnum, DistanceMethod.MANHATTAN);
		addEEnumLiteral(distanceMethodEEnum, DistanceMethod.EUCLIDEAN);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "ecore", "http://www.eclipse.org/emf/2002/Ecore"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "invocationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			   "settingDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			   "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot"
		   });
		addAnnotation
		  (randomBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "PositiveRegenPerSecond"
		   });
		addAnnotation
		  (patrolBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "RequiresOnePatrolPoint ValidStardIndex"
		   });
		addAnnotation
		  (chaseBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "NotTooLargeAttackRadius"
		   });
		addAnnotation
		  (movementBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "PositiveMovementSpeed"
		   });
		addAnnotation
		  (positionEClass,
		   source,
		   new String[] {
			   "constraints", "PositivePositions"
		   });
		addAnnotation
		  (patrolPointEClass,
		   source,
		   new String[] {
			   "constraints", "PositiveRegenAmount PositivePatrolPointTime PositivePatrolPointCoords"
		   });
		addAnnotation
		  (dijkstraPathCalculatorEClass,
		   source,
		   new String[] {
			   "constraints", "ValidDijsktraIterationsCount"
		   });
		addAnnotation
		  (astarPathCalculatorEClass,
		   source,
		   new String[] {
			   "constraints", "ValidAstarIterationsCount"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createPivotAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";
		addAnnotation
		  (randomBehaviorEClass,
		   source,
		   new String[] {
			   "PositiveRegenPerSecond", "self.regenPerSecond >= 0"
		   });
		addAnnotation
		  (patrolBehaviorEClass,
		   source,
		   new String[] {
			   "RequiresOnePatrolPoint", "self.path->size() > 0",
			   "ValidStardIndex", "self.startIndex >= 0 and self.startIndex < self.path->size()"
		   });
		addAnnotation
		  (chaseBehaviorEClass,
		   source,
		   new String[] {
			   "NotTooLargeAttackRadius", "self.relativePositionTarget->forAll(p | self.attackRadius > p.posX and self.attackRadius > p.posY)"
		   });
		addAnnotation
		  (movementBehaviorEClass,
		   source,
		   new String[] {
			   "PositiveMovementSpeed", "self.movementSpeed > 0"
		   });
		addAnnotation
		  (positionEClass,
		   source,
		   new String[] {
			   "PositivePositions", "self.posX >= 0 and self.posY >= 0"
		   });
		addAnnotation
		  (patrolPointEClass,
		   source,
		   new String[] {
			   "PositiveRegenAmount", "self.regenAmount >= 0",
			   "PositivePatrolPointTime", "self.time >= 0",
			   "PositivePatrolPointCoords", "self.point->forAll(p | p.posX > 0 and p.posY > 0)"
		   });
		addAnnotation
		  (dijkstraPathCalculatorEClass,
		   source,
		   new String[] {
			   "ValidDijsktraIterationsCount", "self.maxIterations > 0 and self.maxIterations < 50"
		   });
		addAnnotation
		  (astarPathCalculatorEClass,
		   source,
		   new String[] {
			   "ValidAstarIterationsCount", "self.maxIterations > 0 and self.maxIterations < 50"
		   });
	}

} //BehaviourPackageImpl
