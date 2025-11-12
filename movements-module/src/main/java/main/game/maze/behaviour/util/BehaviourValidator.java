/**
 */
package main.game.maze.behaviour.util;

import java.util.Map;

import main.game.maze.behaviour.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.behaviour.BehaviourPackage
 * @generated
 */
public class BehaviourValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final BehaviourValidator INSTANCE = new BehaviourValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "main.game.maze.behaviour";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return BehaviourPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case BehaviourPackage.RANDOM_BEHAVIOR:
				return validateRandomBehavior((RandomBehavior)value, diagnostics, context);
			case BehaviourPackage.PATROL_BEHAVIOR:
				return validatePatrolBehavior((PatrolBehavior)value, diagnostics, context);
			case BehaviourPackage.CHASE_BEHAVIOR:
				return validateChaseBehavior((ChaseBehavior)value, diagnostics, context);
			case BehaviourPackage.MOVEMENT_BEHAVIOR:
				return validateMovementBehavior((MovementBehavior)value, diagnostics, context);
			case BehaviourPackage.POSITION:
				return validatePosition((Position)value, diagnostics, context);
			case BehaviourPackage.PATROL_POINT:
				return validatePatrolPoint((PatrolPoint)value, diagnostics, context);
			case BehaviourPackage.PATH_CALCULATOR:
				return validatePathCalculator((PathCalculator)value, diagnostics, context);
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR:
				return validateDijkstraPathCalculator((DijkstraPathCalculator)value, diagnostics, context);
			case BehaviourPackage.ASTAR_PATH_CALCULATOR:
				return validateAstarPathCalculator((AstarPathCalculator)value, diagnostics, context);
			case BehaviourPackage.LOCAL_PATH_CALCULATOR:
				return validateLocalPathCalculator((LocalPathCalculator)value, diagnostics, context);
			case BehaviourPackage.PATROL_ZONE:
				return validatePatrolZone((PatrolZone)value, diagnostics, context);
			case BehaviourPackage.DISTANCE_METHOD:
				return validateDistanceMethod((DistanceMethod)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRandomBehavior(RandomBehavior randomBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(randomBehavior, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateMovementBehavior_PositiveMovementSpeed(randomBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateRandomBehavior_PositiveRegenPerSecond(randomBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositiveRegenPerSecond constraint of '<em>Random Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANDOM_BEHAVIOR__POSITIVE_REGEN_PER_SECOND__EEXPRESSION = "self.regenPerSecond >= 0";

	/**
	 * Validates the PositiveRegenPerSecond constraint of '<em>Random Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRandomBehavior_PositiveRegenPerSecond(RandomBehavior randomBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.RANDOM_BEHAVIOR,
				 randomBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositiveRegenPerSecond",
				 RANDOM_BEHAVIOR__POSITIVE_REGEN_PER_SECOND__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolBehavior(PatrolBehavior patrolBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(patrolBehavior, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateMovementBehavior_PositiveMovementSpeed(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolBehavior_RequiresOnePatrolPoint(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolBehavior_ValidStardIndex(patrolBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RequiresOnePatrolPoint constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_BEHAVIOR__REQUIRES_ONE_PATROL_POINT__EEXPRESSION = "self.path->size() > 0";

	/**
	 * Validates the RequiresOnePatrolPoint constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolBehavior_RequiresOnePatrolPoint(PatrolBehavior patrolBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_BEHAVIOR,
				 patrolBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RequiresOnePatrolPoint",
				 PATROL_BEHAVIOR__REQUIRES_ONE_PATROL_POINT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidStardIndex constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_BEHAVIOR__VALID_STARD_INDEX__EEXPRESSION = "self.startIndex >= 0 and self.startIndex < self.path->size()";

	/**
	 * Validates the ValidStardIndex constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolBehavior_ValidStardIndex(PatrolBehavior patrolBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_BEHAVIOR,
				 patrolBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidStardIndex",
				 PATROL_BEHAVIOR__VALID_STARD_INDEX__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateChaseBehavior(ChaseBehavior chaseBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(chaseBehavior, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateMovementBehavior_PositiveMovementSpeed(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateChaseBehavior_NotTooLargeAttackRadius(chaseBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the NotTooLargeAttackRadius constraint of '<em>Chase Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CHASE_BEHAVIOR__NOT_TOO_LARGE_ATTACK_RADIUS__EEXPRESSION = "self.relativePositionTarget->forAll(p | self.attackRadius > p.posX and self.attackRadius > p.posY)";

	/**
	 * Validates the NotTooLargeAttackRadius constraint of '<em>Chase Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateChaseBehavior_NotTooLargeAttackRadius(ChaseBehavior chaseBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.CHASE_BEHAVIOR,
				 chaseBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "NotTooLargeAttackRadius",
				 CHASE_BEHAVIOR__NOT_TOO_LARGE_ATTACK_RADIUS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMovementBehavior(MovementBehavior movementBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(movementBehavior, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(movementBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateMovementBehavior_PositiveMovementSpeed(movementBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositiveMovementSpeed constraint of '<em>Movement Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MOVEMENT_BEHAVIOR__POSITIVE_MOVEMENT_SPEED__EEXPRESSION = "self.movementSpeed > 0";

	/**
	 * Validates the PositiveMovementSpeed constraint of '<em>Movement Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMovementBehavior_PositiveMovementSpeed(MovementBehavior movementBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.MOVEMENT_BEHAVIOR,
				 movementBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositiveMovementSpeed",
				 MOVEMENT_BEHAVIOR__POSITIVE_MOVEMENT_SPEED__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePosition(Position position, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(position, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(position, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(position, diagnostics, context);
		if (result || diagnostics != null) result &= validatePosition_PositivePositions(position, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositivePositions constraint of '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String POSITION__POSITIVE_POSITIONS__EEXPRESSION = "self.posX >= 0 and self.posY >= 0";

	/**
	 * Validates the PositivePositions constraint of '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePosition_PositivePositions(Position position, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.POSITION,
				 position,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositivePositions",
				 POSITION__POSITIVE_POSITIONS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(patrolPoint, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_PositiveRegenAmount(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_PositivePatrolPointTime(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_PositivePatrolPointCoords(patrolPoint, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositiveRegenAmount constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__POSITIVE_REGEN_AMOUNT__EEXPRESSION = "self.regenAmount >= 0";

	/**
	 * Validates the PositiveRegenAmount constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_PositiveRegenAmount(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositiveRegenAmount",
				 PATROL_POINT__POSITIVE_REGEN_AMOUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the PositivePatrolPointTime constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__POSITIVE_PATROL_POINT_TIME__EEXPRESSION = "self.time >= 0";

	/**
	 * Validates the PositivePatrolPointTime constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_PositivePatrolPointTime(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositivePatrolPointTime",
				 PATROL_POINT__POSITIVE_PATROL_POINT_TIME__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the PositivePatrolPointCoords constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__POSITIVE_PATROL_POINT_COORDS__EEXPRESSION = "self.point->forAll(p | p.posX > 0 and p.posY > 0)";

	/**
	 * Validates the PositivePatrolPointCoords constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_PositivePatrolPointCoords(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositivePatrolPointCoords",
				 PATROL_POINT__POSITIVE_PATROL_POINT_COORDS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePathCalculator(PathCalculator pathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(pathCalculator, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDijkstraPathCalculator(DijkstraPathCalculator dijkstraPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dijkstraPathCalculator, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(dijkstraPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validateDijkstraPathCalculator_ValidDijsktraIterationsCount(dijkstraPathCalculator, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidDijsktraIterationsCount constraint of '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DIJKSTRA_PATH_CALCULATOR__VALID_DIJSKTRA_ITERATIONS_COUNT__EEXPRESSION = "self.maxIterations > 0 and self.maxIterations < 50";

	/**
	 * Validates the ValidDijsktraIterationsCount constraint of '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDijkstraPathCalculator_ValidDijsktraIterationsCount(DijkstraPathCalculator dijkstraPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.DIJKSTRA_PATH_CALCULATOR,
				 dijkstraPathCalculator,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidDijsktraIterationsCount",
				 DIJKSTRA_PATH_CALCULATOR__VALID_DIJSKTRA_ITERATIONS_COUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAstarPathCalculator(AstarPathCalculator astarPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(astarPathCalculator, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(astarPathCalculator, diagnostics, context);
		if (result || diagnostics != null) result &= validateAstarPathCalculator_ValidAstarIterationsCount(astarPathCalculator, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidAstarIterationsCount constraint of '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ASTAR_PATH_CALCULATOR__VALID_ASTAR_ITERATIONS_COUNT__EEXPRESSION = "self.maxIterations > 0 and self.maxIterations < 50";

	/**
	 * Validates the ValidAstarIterationsCount constraint of '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAstarPathCalculator_ValidAstarIterationsCount(AstarPathCalculator astarPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.ASTAR_PATH_CALCULATOR,
				 astarPathCalculator,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidAstarIterationsCount",
				 ASTAR_PATH_CALCULATOR__VALID_ASTAR_ITERATIONS_COUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocalPathCalculator(LocalPathCalculator localPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(localPathCalculator, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolZone(PatrolZone patrolZone, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(patrolZone, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDistanceMethod(DistanceMethod distanceMethod, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //BehaviourValidator
