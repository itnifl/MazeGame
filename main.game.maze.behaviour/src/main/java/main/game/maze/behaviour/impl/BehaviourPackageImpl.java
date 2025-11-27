/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.AstarPathCalculator;
import main.game.maze.behaviour.AttackEvent;
import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.CharacterEvent;
import main.game.maze.behaviour.ChaseBehavior;
import main.game.maze.behaviour.DijkstraPathCalculator;
import main.game.maze.behaviour.Direction;
import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.behaviour.HealthEvent;
import main.game.maze.behaviour.LocalPathCalculator;
import main.game.maze.behaviour.MovementBehavior;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPathBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.PatrolZone;
import main.game.maze.behaviour.Position;
import main.game.maze.behaviour.RandomBehavior;

import main.game.maze.behaviour.SpeedEvent;
import main.game.maze.behaviour.TimeEvent;
import main.game.maze.behaviour.VisionEvent;
import main.game.maze.behaviour.util.BehaviourValidator;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.impl.DifficultiesPackageImpl;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.impl.OpponentsPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
	private EClass directionEClass = null;

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
	private EClass characterEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass healthEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass speedEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visionEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attackEventEClass = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum patrolPathBehaviorEEnum = null;

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

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(OpponentsPackage.eNS_URI);
		OpponentsPackageImpl theOpponentsPackage = (OpponentsPackageImpl)(registeredPackage instanceof OpponentsPackageImpl ? registeredPackage : OpponentsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DifficultiesPackage.eNS_URI);
		DifficultiesPackageImpl theDifficultiesPackage = (DifficultiesPackageImpl)(registeredPackage instanceof DifficultiesPackageImpl ? registeredPackage : DifficultiesPackage.eINSTANCE);

		// Create package meta-data objects
		theBehaviourPackage.createPackageContents();
		theOpponentsPackage.createPackageContents();
		theDifficultiesPackage.createPackageContents();

		// Initialize created meta-data
		theBehaviourPackage.initializePackageContents();
		theOpponentsPackage.initializePackageContents();
		theDifficultiesPackage.initializePackageContents();

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
	public EClass getDirection() {
		return directionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDirection_StartPosition() {
		return (EReference)directionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDirection_EndPosition() {
		return (EReference)directionEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getPatrolBehavior_CurrentIndex() {
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
	public EAttribute getPatrolBehavior_Behavior() {
		return (EAttribute)patrolBehaviorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolBehavior_PatrolZone() {
		return (EReference)patrolBehaviorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPatrolBehavior__NextIndex() {
		return patrolBehaviorEClass.getEOperations().get(0);
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
	public EReference getMovementBehavior_Charactertype() {
		return (EReference)movementBehaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_IgnoreWalls() {
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
	public EAttribute getMovementBehavior_BaseVisionRange() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_AdditionalVisionRange() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_VisionRangeMultiplier() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMovementBehavior_VisionRange() {
		return (EAttribute)movementBehaviorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMovementBehavior_NextPositions() {
		return (EReference)movementBehaviorEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMovementBehavior_Position() {
		return (EReference)movementBehaviorEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMovementBehavior_Direction() {
		return (EReference)movementBehaviorEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Move() {
		return movementBehaviorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Update__HealthEvent() {
		return movementBehaviorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Update__SpeedEvent() {
		return movementBehaviorEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Update__TimeEvent() {
		return movementBehaviorEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Update__AttackEvent() {
		return movementBehaviorEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMovementBehavior__Update__VisionEvent() {
		return movementBehaviorEClass.getEOperations().get(5);
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
	public EReference getPatrolPoint_Point() {
		return (EReference)patrolPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPatrolPoint_Events() {
		return (EReference)patrolPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPatrolPoint__TriggerEvents() {
		return patrolPointEClass.getEOperations().get(0);
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
	public EOperation getPathCalculator__Compute__Position() {
		return pathCalculatorEClass.getEOperations().get(0);
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
	public EAttribute getDijkstraPathCalculator_MaxPathLength() {
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
	public EAttribute getAstarPathCalculator_MaxPathLength() {
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
	public EClass getCharacterEvent() {
		return characterEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterEvent_Probability() {
		return (EAttribute)characterEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCharacterEvent_Subscriber() {
		return (EReference)characterEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getCharacterEvent__NotifySubscribers() {
		return characterEventEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHealthEvent() {
		return healthEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthEvent_HealthAmount() {
		return (EAttribute)healthEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHealthEvent_HealthPercentage() {
		return (EAttribute)healthEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSpeedEvent() {
		return speedEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSpeedEvent_SpeedAmount() {
		return (EAttribute)speedEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSpeedEvent_SpeedPercentage() {
		return (EAttribute)speedEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTimeEvent() {
		return timeEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTimeEvent_Time() {
		return (EAttribute)timeEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVisionEvent() {
		return visionEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVisionEvent_RadiusAmount() {
		return (EAttribute)visionEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVisionEvent_RadiusPercentage() {
		return (EAttribute)visionEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttackEvent() {
		return attackEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttackEvent_RadiusPercentage() {
		return (EAttribute)attackEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttackEvent_RadiusAmount() {
		return (EAttribute)attackEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttackEvent_DamageAmount() {
		return (EAttribute)attackEventEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttackEvent_DamagePercentage() {
		return (EAttribute)attackEventEClass.getEStructuralFeatures().get(3);
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
	public EEnum getPatrolPathBehavior() {
		return patrolPathBehaviorEEnum;
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
		directionEClass = createEClass(DIRECTION);
		createEReference(directionEClass, DIRECTION__START_POSITION);
		createEReference(directionEClass, DIRECTION__END_POSITION);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__POS_X);
		createEAttribute(positionEClass, POSITION__POS_Y);

		movementBehaviorEClass = createEClass(MOVEMENT_BEHAVIOR);
		createEReference(movementBehaviorEClass, MOVEMENT_BEHAVIOR__CHARACTERTYPE);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__IGNORE_WALLS);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__BASE_VISION_RANGE);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER);
		createEAttribute(movementBehaviorEClass, MOVEMENT_BEHAVIOR__VISION_RANGE);
		createEReference(movementBehaviorEClass, MOVEMENT_BEHAVIOR__NEXT_POSITIONS);
		createEReference(movementBehaviorEClass, MOVEMENT_BEHAVIOR__POSITION);
		createEReference(movementBehaviorEClass, MOVEMENT_BEHAVIOR__DIRECTION);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___MOVE);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT);
		createEOperation(movementBehaviorEClass, MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT);

		randomBehaviorEClass = createEClass(RANDOM_BEHAVIOR);
		createEAttribute(randomBehaviorEClass, RANDOM_BEHAVIOR__REGEN_PER_SECOND);

		patrolBehaviorEClass = createEClass(PATROL_BEHAVIOR);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATH);
		createEAttribute(patrolBehaviorEClass, PATROL_BEHAVIOR__CURRENT_INDEX);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATHCALCULATOR);
		createEAttribute(patrolBehaviorEClass, PATROL_BEHAVIOR__BEHAVIOR);
		createEReference(patrolBehaviorEClass, PATROL_BEHAVIOR__PATROL_ZONE);
		createEOperation(patrolBehaviorEClass, PATROL_BEHAVIOR___NEXT_INDEX);

		chaseBehaviorEClass = createEClass(CHASE_BEHAVIOR);
		createEReference(chaseBehaviorEClass, CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET);
		createEReference(chaseBehaviorEClass, CHASE_BEHAVIOR__PATHCALCULATOR);

		patrolPointEClass = createEClass(PATROL_POINT);
		createEReference(patrolPointEClass, PATROL_POINT__POINT);
		createEReference(patrolPointEClass, PATROL_POINT__EVENTS);
		createEOperation(patrolPointEClass, PATROL_POINT___TRIGGER_EVENTS);

		patrolZoneEClass = createEClass(PATROL_ZONE);
		createEAttribute(patrolZoneEClass, PATROL_ZONE__WIDTH);
		createEAttribute(patrolZoneEClass, PATROL_ZONE__HEIGHT);
		createEReference(patrolZoneEClass, PATROL_ZONE__TOP_LEFT);

		pathCalculatorEClass = createEClass(PATH_CALCULATOR);
		createEAttribute(pathCalculatorEClass, PATH_CALCULATOR__DISTANCE_METHOD);
		createEOperation(pathCalculatorEClass, PATH_CALCULATOR___COMPUTE__POSITION);

		dijkstraPathCalculatorEClass = createEClass(DIJKSTRA_PATH_CALCULATOR);
		createEAttribute(dijkstraPathCalculatorEClass, DIJKSTRA_PATH_CALCULATOR__MAX_PATH_LENGTH);

		astarPathCalculatorEClass = createEClass(ASTAR_PATH_CALCULATOR);
		createEAttribute(astarPathCalculatorEClass, ASTAR_PATH_CALCULATOR__MAX_PATH_LENGTH);

		localPathCalculatorEClass = createEClass(LOCAL_PATH_CALCULATOR);

		characterEventEClass = createEClass(CHARACTER_EVENT);
		createEAttribute(characterEventEClass, CHARACTER_EVENT__PROBABILITY);
		createEReference(characterEventEClass, CHARACTER_EVENT__SUBSCRIBER);
		createEOperation(characterEventEClass, CHARACTER_EVENT___NOTIFY_SUBSCRIBERS);

		healthEventEClass = createEClass(HEALTH_EVENT);
		createEAttribute(healthEventEClass, HEALTH_EVENT__HEALTH_AMOUNT);
		createEAttribute(healthEventEClass, HEALTH_EVENT__HEALTH_PERCENTAGE);

		speedEventEClass = createEClass(SPEED_EVENT);
		createEAttribute(speedEventEClass, SPEED_EVENT__SPEED_AMOUNT);
		createEAttribute(speedEventEClass, SPEED_EVENT__SPEED_PERCENTAGE);

		timeEventEClass = createEClass(TIME_EVENT);
		createEAttribute(timeEventEClass, TIME_EVENT__TIME);

		visionEventEClass = createEClass(VISION_EVENT);
		createEAttribute(visionEventEClass, VISION_EVENT__RADIUS_AMOUNT);
		createEAttribute(visionEventEClass, VISION_EVENT__RADIUS_PERCENTAGE);

		attackEventEClass = createEClass(ATTACK_EVENT);
		createEAttribute(attackEventEClass, ATTACK_EVENT__RADIUS_PERCENTAGE);
		createEAttribute(attackEventEClass, ATTACK_EVENT__RADIUS_AMOUNT);
		createEAttribute(attackEventEClass, ATTACK_EVENT__DAMAGE_AMOUNT);
		createEAttribute(attackEventEClass, ATTACK_EVENT__DAMAGE_PERCENTAGE);

		// Create enums
		distanceMethodEEnum = createEEnum(DISTANCE_METHOD);
		patrolPathBehaviorEEnum = createEEnum(PATROL_PATH_BEHAVIOR);
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

		// Obtain other dependent packages
		OpponentsPackage theOpponentsPackage = (OpponentsPackage)EPackage.Registry.INSTANCE.getEPackage(OpponentsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		randomBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		patrolBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		chaseBehaviorEClass.getESuperTypes().add(this.getMovementBehavior());
		dijkstraPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());
		astarPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());
		localPathCalculatorEClass.getESuperTypes().add(this.getPathCalculator());
		healthEventEClass.getESuperTypes().add(this.getCharacterEvent());
		speedEventEClass.getESuperTypes().add(this.getCharacterEvent());
		timeEventEClass.getESuperTypes().add(this.getCharacterEvent());
		visionEventEClass.getESuperTypes().add(this.getCharacterEvent());
		attackEventEClass.getESuperTypes().add(this.getCharacterEvent());

		// Initialize classes, features, and operations; add parameters
		initEClass(directionEClass, Direction.class, "Direction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDirection_StartPosition(), this.getPosition(), null, "startPosition", null, 1, 1, Direction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDirection_EndPosition(), this.getPosition(), null, "endPosition", null, 1, 1, Direction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_PosX(), ecorePackage.getEDouble(), "posX", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPosition_PosY(), ecorePackage.getEDouble(), "posY", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(movementBehaviorEClass, MovementBehavior.class, "MovementBehavior", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMovementBehavior_Charactertype(), theOpponentsPackage.getCharacterType(), null, "charactertype", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_IgnoreWalls(), ecorePackage.getEBoolean(), "ignoreWalls", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_InstantKillOnCollision(), ecorePackage.getEBoolean(), "instantKillOnCollision", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_BaseVisionRange(), ecorePackage.getEDouble(), "baseVisionRange", "100", 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_AdditionalVisionRange(), ecorePackage.getEDouble(), "additionalVisionRange", "100", 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_VisionRangeMultiplier(), ecorePackage.getEDouble(), "visionRangeMultiplier", "1", 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMovementBehavior_VisionRange(), ecorePackage.getEDouble(), "visionRange", null, 1, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMovementBehavior_NextPositions(), this.getPosition(), null, "nextPositions", null, 0, -1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMovementBehavior_Position(), this.getPosition(), null, "position", null, 0, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMovementBehavior_Direction(), this.getDirection(), null, "direction", null, 0, 1, MovementBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getMovementBehavior__Move(), null, "move", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getMovementBehavior__Update__HealthEvent(), null, "update", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getHealthEvent(), "healthEvent", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMovementBehavior__Update__SpeedEvent(), null, "update", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSpeedEvent(), "speedEvent", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMovementBehavior__Update__TimeEvent(), null, "update", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTimeEvent(), "timeEvent", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMovementBehavior__Update__AttackEvent(), null, "update", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAttackEvent(), "attackEvent", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMovementBehavior__Update__VisionEvent(), null, "update", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVisionEvent(), "visionEvent", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(randomBehaviorEClass, RandomBehavior.class, "RandomBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRandomBehavior_RegenPerSecond(), ecorePackage.getEDouble(), "regenPerSecond", null, 1, 1, RandomBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patrolBehaviorEClass, PatrolBehavior.class, "PatrolBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatrolBehavior_Path(), this.getPatrolPoint(), null, "path", null, 1, -1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolBehavior_CurrentIndex(), ecorePackage.getEInt(), "currentIndex", "0", 1, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolBehavior_Pathcalculator(), this.getPathCalculator(), null, "pathcalculator", null, 1, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolBehavior_Behavior(), this.getPatrolPathBehavior(), "behavior", null, 0, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolBehavior_PatrolZone(), this.getPatrolZone(), null, "patrolZone", null, 0, 1, PatrolBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getPatrolBehavior__NextIndex(), null, "nextIndex", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(chaseBehaviorEClass, ChaseBehavior.class, "ChaseBehavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChaseBehavior_RelativePositionTarget(), this.getPosition(), null, "relativePositionTarget", null, 1, 1, ChaseBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChaseBehavior_Pathcalculator(), this.getPathCalculator(), null, "pathcalculator", null, 1, 1, ChaseBehavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patrolPointEClass, PatrolPoint.class, "PatrolPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatrolPoint_Point(), this.getPosition(), null, "point", null, 1, 1, PatrolPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolPoint_Events(), this.getCharacterEvent(), null, "events", null, 0, -1, PatrolPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEOperation(getPatrolPoint__TriggerEvents(), null, "triggerEvents", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(patrolZoneEClass, PatrolZone.class, "PatrolZone", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPatrolZone_Width(), ecorePackage.getEDouble(), "width", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPatrolZone_Height(), ecorePackage.getEDouble(), "height", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatrolZone_TopLeft(), this.getPosition(), null, "topLeft", null, 1, 1, PatrolZone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pathCalculatorEClass, PathCalculator.class, "PathCalculator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPathCalculator_DistanceMethod(), this.getDistanceMethod(), "distanceMethod", null, 1, 1, PathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getPathCalculator__Compute__Position(), this.getPosition(), "compute", 1, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPosition(), "target", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(dijkstraPathCalculatorEClass, DijkstraPathCalculator.class, "DijkstraPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDijkstraPathCalculator_MaxPathLength(), ecorePackage.getEInt(), "maxPathLength", null, 1, 1, DijkstraPathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(astarPathCalculatorEClass, AstarPathCalculator.class, "AstarPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAstarPathCalculator_MaxPathLength(), ecorePackage.getEInt(), "maxPathLength", null, 1, 1, AstarPathCalculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localPathCalculatorEClass, LocalPathCalculator.class, "LocalPathCalculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(characterEventEClass, CharacterEvent.class, "CharacterEvent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCharacterEvent_Probability(), ecorePackage.getEDouble(), "probability", null, 1, 1, CharacterEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCharacterEvent_Subscriber(), this.getMovementBehavior(), null, "subscriber", null, 1, 1, CharacterEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCharacterEvent__NotifySubscribers(), null, "notifySubscribers", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(healthEventEClass, HealthEvent.class, "HealthEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHealthEvent_HealthAmount(), ecorePackage.getEInt(), "healthAmount", null, 1, 1, HealthEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHealthEvent_HealthPercentage(), ecorePackage.getEInt(), "healthPercentage", null, 1, 1, HealthEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(speedEventEClass, SpeedEvent.class, "SpeedEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSpeedEvent_SpeedAmount(), ecorePackage.getEDouble(), "speedAmount", null, 1, 1, SpeedEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSpeedEvent_SpeedPercentage(), ecorePackage.getEDouble(), "speedPercentage", null, 1, 1, SpeedEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timeEventEClass, TimeEvent.class, "TimeEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTimeEvent_Time(), ecorePackage.getEInt(), "time", null, 1, 1, TimeEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(visionEventEClass, VisionEvent.class, "VisionEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVisionEvent_RadiusAmount(), ecorePackage.getEDouble(), "radiusAmount", null, 1, 1, VisionEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisionEvent_RadiusPercentage(), ecorePackage.getEDouble(), "radiusPercentage", null, 1, 1, VisionEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attackEventEClass, AttackEvent.class, "AttackEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttackEvent_RadiusPercentage(), ecorePackage.getEDouble(), "radiusPercentage", null, 1, 1, AttackEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttackEvent_RadiusAmount(), ecorePackage.getEDouble(), "radiusAmount", null, 1, 1, AttackEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttackEvent_DamageAmount(), ecorePackage.getEInt(), "damageAmount", null, 1, 1, AttackEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttackEvent_DamagePercentage(), ecorePackage.getEInt(), "damagePercentage", null, 1, 1, AttackEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(distanceMethodEEnum, DistanceMethod.class, "DistanceMethod");
		addEEnumLiteral(distanceMethodEEnum, DistanceMethod.MANHATTAN);
		addEEnumLiteral(distanceMethodEEnum, DistanceMethod.EUCLIDEAN);

		initEEnum(patrolPathBehaviorEEnum, PatrolPathBehavior.class, "PatrolPathBehavior");
		addEEnumLiteral(patrolPathBehaviorEEnum, PatrolPathBehavior.LOOP);
		addEEnumLiteral(patrolPathBehaviorEEnum, PatrolPathBehavior.BACKWARD);
		addEEnumLiteral(patrolPathBehaviorEEnum, PatrolPathBehavior.RANDOM);

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
			   "ecore", "http://www.eclipse.org/emf/2002/Ecore",
			   "opp", "../../../../../main.game.maze.opponents/src/main/resources/opponents.ecore#/"
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
		  (positionEClass,
		   source,
		   new String[] {
			   "constraints", "PositivePositions"
		   });
		addAnnotation
		  (movementBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "ValidVisionRange"
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
			   "constraints", "RequiresOnePatrolPoint ValidStardIndex ValidPatrolZone"
		   });
		addAnnotation
		  (chaseBehaviorEClass,
		   source,
		   new String[] {
			   "constraints", "NotTooLargeAttackRadius"
		   });
		addAnnotation
		  (patrolPointEClass,
		   source,
		   new String[] {
			   "constraints", "PositivePatrolPointCoords ValidHealthEventsProbability ValidSpeedEventsProbability ValidTimeEventsProbability ValidVisionEventsProbability ValidAttackEventsProbability"
		   });
		addAnnotation
		  (dijkstraPathCalculatorEClass,
		   source,
		   new String[] {
			   "constraints", "ValidDijsktraPath"
		   });
		addAnnotation
		  (astarPathCalculatorEClass,
		   source,
		   new String[] {
			   "constraints", "ValidAStartPath"
		   });
		addAnnotation
		  (characterEventEClass,
		   source,
		   new String[] {
			   "constraints", "ValidProbability"
		   });
		addAnnotation
		  (healthEventEClass,
		   source,
		   new String[] {
			   "constraints", "PositiveHealthAmount ValidHealthPercentage"
		   });
		addAnnotation
		  (speedEventEClass,
		   source,
		   new String[] {
			   "constraints", "ValidSpeedPercentage"
		   });
		addAnnotation
		  (timeEventEClass,
		   source,
		   new String[] {
			   "constraints", "PositivePatrolTime"
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
		  (positionEClass,
		   source,
		   new String[] {
			   "PositivePositions", "self.posX >= 0 and self.posY >= 0"
		   });
		addAnnotation
		  (movementBehaviorEClass,
		   source,
		   new String[] {
			   "ValidVisionRange", "self.visionRange > 0 and\n\t\t\tif self.charactertype->any(true).oclIsKindOf(opp::RangedEnemy) then\n\t\t\t \tlet rangedCharacter : opp::RangedEnemy = self.charactertype->any(true).oclAsType(opp::RangedEnemy)\n\t\t\t    in rangedCharacter.attackRange <= self.visionRange\n\t\t\telse\n\t\t\t\ttrue\n\t\t\tendif"
		   });
		addAnnotation
		  (getMovementBehavior_IgnoreWalls(),
		   source,
		   new String[] {
			   "derivation", "\n\t\t\t\tif self.charactertype->any(true).oclIsKindOf(opp::Ghost) then\n\t\t\t\t\tlet ghostCharacter : opp::Ghost = self.charactertype->any(true).oclAsType(opp::Ghost)\n\t\t\t\t\tin ghostCharacter.nonTangibilityEnergy >= 0\n\t\t\t\telse\n\t\t\t\t\tfalse\n\t\t\t\tendif"
		   });
		addAnnotation
		  (getMovementBehavior_InstantKillOnCollision(),
		   source,
		   new String[] {
			   "derivation", "self.charactertype->any(true).threatLevel > 100"
		   });
		addAnnotation
		  (getMovementBehavior_VisionRange(),
		   source,
		   new String[] {
			   "derivation", "self.baseVisionRange * self.visionRangeMultiplier + self.additionalVisionRange"
		   });
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
			   "ValidStardIndex", "self.currentIndex >= 0 and self.currentIndex < self.path->size()",
			   "ValidPatrolZone", "self.patrolZone->size() = 0 or\n\t\t\tlet width : ecore::EDouble = self.patrolZone->collect(z | z.width)->any(true),\n\t\t\t    height : ecore::EDouble = self.patrolZone->collect(z | z.height)->any(true),\n\t\t\t    topLeft : Position = self.patrolZone->collect(z | z.topLeft)->any(true)\n\t\t\tin self.path->forAll(pp | pp.point->any(p | \n\t\t\t\tp.posX >= topLeft.posX and\n\t\t\t\tp.posY >= topLeft.posY and\n\t\t\t\tp.posX <= topLeft.posX + width and\n\t\t\t\tp.posY <= topLeft.posY + height\n\t\t))"
		   });
		addAnnotation
		  (chaseBehaviorEClass,
		   source,
		   new String[] {
			   "NotTooLargeAttackRadius", "self.charactertype->select(p | p.oclIsKindOf(opp::RangedEnemy))->isEmpty() or\n\t\t\tlet rangedEnemy : opp::RangedEnemy = self.charactertype->any(true).oclAsType(opp::RangedEnemy),\n\t\t\t\trelativeTarget : Position = self.relativePositionTarget->any(true)\n\t\t\tin rangedEnemy.attackRange >= relativeTarget.posX and rangedEnemy.attackRange >= relativeTarget.posY"
		   });
		addAnnotation
		  (patrolPointEClass,
		   source,
		   new String[] {
			   "PositivePatrolPointCoords", "self.point->forAll(p | p.posX > 0 and p.posY > 0)",
			   "ValidHealthEventsProbability", "self.events->select(p | p.oclIsKindOf(HealthEvent))->collect(p | p.probability)->sum() <= 100",
			   "ValidSpeedEventsProbability", "self.events->select(p | p.oclIsKindOf(SpeedEvent))->collect(p | p.probability)->sum() <= 100",
			   "ValidTimeEventsProbability", "self.events->select(p | p.oclIsKindOf(TimeEvent))->collect(p | p.probability)->sum() <= 100",
			   "ValidVisionEventsProbability", "self.events->select(p | p.oclIsKindOf(VisionEvent))->collect(p | p.probability)->sum() <= 100",
			   "ValidAttackEventsProbability", "self.events->select(p | p.oclIsKindOf(AttackEvent))->collect(p | p.probability)->sum() <= 100"
		   });
		addAnnotation
		  (dijkstraPathCalculatorEClass,
		   source,
		   new String[] {
			   "ValidDijsktraPath", "self.maxPathLength > 0"
		   });
		addAnnotation
		  (astarPathCalculatorEClass,
		   source,
		   new String[] {
			   "ValidAStartPath", "self.maxPathLength > 0"
		   });
		addAnnotation
		  (characterEventEClass,
		   source,
		   new String[] {
			   "ValidProbability", "self.probability >= 0 and self.probability <= 100"
		   });
		addAnnotation
		  (healthEventEClass,
		   source,
		   new String[] {
			   "PositiveHealthAmount", "self.healthAmount >= 0",
			   "ValidHealthPercentage", "self.healthPercentage >= 0 and self.healthPercentage <= 100"
		   });
		addAnnotation
		  (speedEventEClass,
		   source,
		   new String[] {
			   "ValidSpeedPercentage", "self.speedPercentage >= -100 and self.speedPercentage <= 100"
		   });
		addAnnotation
		  (timeEventEClass,
		   source,
		   new String[] {
			   "PositivePatrolTime", "self.time >= 0"
		   });
	}

} //BehaviourPackageImpl
