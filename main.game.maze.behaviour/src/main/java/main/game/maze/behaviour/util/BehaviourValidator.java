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
			case BehaviourPackage.DIRECTION:
				return validateDirection((Direction)value, diagnostics, context);
			case BehaviourPackage.POSITION:
				return validatePosition((Position)value, diagnostics, context);
			case BehaviourPackage.MOVEMENT_BEHAVIOR:
				return validateMovementBehavior((MovementBehavior)value, diagnostics, context);
			case BehaviourPackage.RANDOM_BEHAVIOR:
				return validateRandomBehavior((RandomBehavior)value, diagnostics, context);
			case BehaviourPackage.PATROL_BEHAVIOR:
				return validatePatrolBehavior((PatrolBehavior)value, diagnostics, context);
			case BehaviourPackage.CHASE_BEHAVIOR:
				return validateChaseBehavior((ChaseBehavior)value, diagnostics, context);
			case BehaviourPackage.PATROL_POINT:
				return validatePatrolPoint((PatrolPoint)value, diagnostics, context);
			case BehaviourPackage.PATROL_ZONE:
				return validatePatrolZone((PatrolZone)value, diagnostics, context);
			case BehaviourPackage.PATH_CALCULATOR:
				return validatePathCalculator((PathCalculator)value, diagnostics, context);
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR:
				return validateDijkstraPathCalculator((DijkstraPathCalculator)value, diagnostics, context);
			case BehaviourPackage.ASTAR_PATH_CALCULATOR:
				return validateAstarPathCalculator((AstarPathCalculator)value, diagnostics, context);
			case BehaviourPackage.LOCAL_PATH_CALCULATOR:
				return validateLocalPathCalculator((LocalPathCalculator)value, diagnostics, context);
			case BehaviourPackage.CHARACTER_EVENT:
				return validateCharacterEvent((CharacterEvent)value, diagnostics, context);
			case BehaviourPackage.HEALTH_EVENT:
				return validateHealthEvent((HealthEvent)value, diagnostics, context);
			case BehaviourPackage.SPEED_EVENT:
				return validateSpeedEvent((SpeedEvent)value, diagnostics, context);
			case BehaviourPackage.TIME_EVENT:
				return validateTimeEvent((TimeEvent)value, diagnostics, context);
			case BehaviourPackage.VISION_EVENT:
				return validateVisionEvent((VisionEvent)value, diagnostics, context);
			case BehaviourPackage.ATTACK_EVENT:
				return validateAttackEvent((AttackEvent)value, diagnostics, context);
			case BehaviourPackage.DISTANCE_METHOD:
				return validateDistanceMethod((DistanceMethod)value, diagnostics, context);
			case BehaviourPackage.PATROL_PATH_BEHAVIOR:
				return validatePatrolPathBehavior((PatrolPathBehavior)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDirection(Direction direction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(direction, diagnostics, context);
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
		if (result || diagnostics != null) result &= validateMovementBehavior_ValidVisionRange(randomBehavior, diagnostics, context);
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
		if (result || diagnostics != null) result &= validateMovementBehavior_ValidVisionRange(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolBehavior_RequiresOnePatrolPoint(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolBehavior_ValidStardIndex(patrolBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolBehavior_ValidPatrolZone(patrolBehavior, diagnostics, context);
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
	protected static final String PATROL_BEHAVIOR__VALID_STARD_INDEX__EEXPRESSION = "self.currentIndex >= 0 and self.currentIndex < self.path->size()";

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
	 * The cached validation expression for the ValidPatrolZone constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_BEHAVIOR__VALID_PATROL_ZONE__EEXPRESSION = "self.patrolZone->size() = 0 or\n" +
		"\t\t\tlet width : ecore::EDouble = self.patrolZone->collect(z | z.width)->any(true),\n" +
		"\t\t\t    height : ecore::EDouble = self.patrolZone->collect(z | z.height)->any(true),\n" +
		"\t\t\t    topLeft : Position = self.patrolZone->collect(z | z.topLeft)->any(true)\n" +
		"\t\t\tin self.path->forAll(pp | pp.point->any(p | \n" +
		"\t\t\t\tp.posX >= topLeft.posX and\n" +
		"\t\t\t\tp.posY >= topLeft.posY and\n" +
		"\t\t\t\tp.posX <= topLeft.posX + width and\n" +
		"\t\t\t\tp.posY <= topLeft.posY + height\n" +
		"\t\t))";

	/**
	 * Validates the ValidPatrolZone constraint of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolBehavior_ValidPatrolZone(PatrolBehavior patrolBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_BEHAVIOR,
				 patrolBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidPatrolZone",
				 PATROL_BEHAVIOR__VALID_PATROL_ZONE__EEXPRESSION,
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
		if (result || diagnostics != null) result &= validateMovementBehavior_ValidVisionRange(chaseBehavior, diagnostics, context);
		if (result || diagnostics != null) result &= validateChaseBehavior_NotTooLargeAttackRadius(chaseBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the NotTooLargeAttackRadius constraint of '<em>Chase Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CHASE_BEHAVIOR__NOT_TOO_LARGE_ATTACK_RADIUS__EEXPRESSION = "self.charactertype->select(p | p.oclIsKindOf(opp::RangedEnemy))->isEmpty() or\n" +
		"\t\t\tlet rangedEnemy : opp::RangedEnemy = self.charactertype->any(true).oclAsType(opp::RangedEnemy),\n" +
		"\t\t\t\trelativeTarget : Position = self.relativePositionTarget->any(true)\n" +
		"\t\t\tin rangedEnemy.attackRange >= relativeTarget.posX and rangedEnemy.attackRange >= relativeTarget.posY";

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
		if (result || diagnostics != null) result &= validateMovementBehavior_ValidVisionRange(movementBehavior, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidVisionRange constraint of '<em>Movement Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MOVEMENT_BEHAVIOR__VALID_VISION_RANGE__EEXPRESSION = "self.visionRange > 0 and\n" +
		"\t\t\tif self.charactertype->any(true).oclIsKindOf(opp::RangedEnemy) then\n" +
		"\t\t\t \tlet rangedCharacter : opp::RangedEnemy = self.charactertype->any(true).oclAsType(opp::RangedEnemy)\n" +
		"\t\t\t    in rangedCharacter.attackRange <= self.visionRange\n" +
		"\t\t\telse\n" +
		"\t\t\t\ttrue\n" +
		"\t\t\tendif";

	/**
	 * Validates the ValidVisionRange constraint of '<em>Movement Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMovementBehavior_ValidVisionRange(MovementBehavior movementBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.MOVEMENT_BEHAVIOR,
				 movementBehavior,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidVisionRange",
				 MOVEMENT_BEHAVIOR__VALID_VISION_RANGE__EEXPRESSION,
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
		if (result || diagnostics != null) result &= validatePatrolPoint_PositivePatrolPointCoords(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_ValidHealthEventsProbability(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_ValidSpeedEventsProbability(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_ValidTimeEventsProbability(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_ValidVisionEventsProbability(patrolPoint, diagnostics, context);
		if (result || diagnostics != null) result &= validatePatrolPoint_ValidAttackEventsProbability(patrolPoint, diagnostics, context);
		return result;
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
	 * The cached validation expression for the ValidHealthEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__VALID_HEALTH_EVENTS_PROBABILITY__EEXPRESSION = "self.events->select(p | p.oclIsKindOf(HealthEvent))->collect(p | p.probability)->sum() <= 100";

	/**
	 * Validates the ValidHealthEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_ValidHealthEventsProbability(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidHealthEventsProbability",
				 PATROL_POINT__VALID_HEALTH_EVENTS_PROBABILITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidSpeedEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__VALID_SPEED_EVENTS_PROBABILITY__EEXPRESSION = "self.events->select(p | p.oclIsKindOf(SpeedEvent))->collect(p | p.probability)->sum() <= 100";

	/**
	 * Validates the ValidSpeedEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_ValidSpeedEventsProbability(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidSpeedEventsProbability",
				 PATROL_POINT__VALID_SPEED_EVENTS_PROBABILITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidTimeEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__VALID_TIME_EVENTS_PROBABILITY__EEXPRESSION = "self.events->select(p | p.oclIsKindOf(TimeEvent))->collect(p | p.probability)->sum() <= 100";

	/**
	 * Validates the ValidTimeEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_ValidTimeEventsProbability(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidTimeEventsProbability",
				 PATROL_POINT__VALID_TIME_EVENTS_PROBABILITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidVisionEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__VALID_VISION_EVENTS_PROBABILITY__EEXPRESSION = "self.events->select(p | p.oclIsKindOf(VisionEvent))->collect(p | p.probability)->sum() <= 100";

	/**
	 * Validates the ValidVisionEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_ValidVisionEventsProbability(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidVisionEventsProbability",
				 PATROL_POINT__VALID_VISION_EVENTS_PROBABILITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidAttackEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PATROL_POINT__VALID_ATTACK_EVENTS_PROBABILITY__EEXPRESSION = "self.events->select(p | p.oclIsKindOf(AttackEvent))->collect(p | p.probability)->sum() <= 100";

	/**
	 * Validates the ValidAttackEventsProbability constraint of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPoint_ValidAttackEventsProbability(PatrolPoint patrolPoint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.PATROL_POINT,
				 patrolPoint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidAttackEventsProbability",
				 PATROL_POINT__VALID_ATTACK_EVENTS_PROBABILITY__EEXPRESSION,
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
		if (result || diagnostics != null) result &= validateDijkstraPathCalculator_ValidDijsktraPath(dijkstraPathCalculator, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidDijsktraPath constraint of '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DIJKSTRA_PATH_CALCULATOR__VALID_DIJSKTRA_PATH__EEXPRESSION = "self.maxPathLength > 0";

	/**
	 * Validates the ValidDijsktraPath constraint of '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDijkstraPathCalculator_ValidDijsktraPath(DijkstraPathCalculator dijkstraPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.DIJKSTRA_PATH_CALCULATOR,
				 dijkstraPathCalculator,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidDijsktraPath",
				 DIJKSTRA_PATH_CALCULATOR__VALID_DIJSKTRA_PATH__EEXPRESSION,
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
		if (result || diagnostics != null) result &= validateAstarPathCalculator_ValidAStartPath(astarPathCalculator, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidAStartPath constraint of '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ASTAR_PATH_CALCULATOR__VALID_ASTART_PATH__EEXPRESSION = "self.maxPathLength > 0";

	/**
	 * Validates the ValidAStartPath constraint of '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAstarPathCalculator_ValidAStartPath(AstarPathCalculator astarPathCalculator, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.ASTAR_PATH_CALCULATOR,
				 astarPathCalculator,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidAStartPath",
				 ASTAR_PATH_CALCULATOR__VALID_ASTART_PATH__EEXPRESSION,
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
	public boolean validateCharacterEvent(CharacterEvent characterEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(characterEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(characterEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(characterEvent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidProbability constraint of '<em>Character Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CHARACTER_EVENT__VALID_PROBABILITY__EEXPRESSION = "self.probability >= 0 and self.probability <= 100";

	/**
	 * Validates the ValidProbability constraint of '<em>Character Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCharacterEvent_ValidProbability(CharacterEvent characterEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.CHARACTER_EVENT,
				 characterEvent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidProbability",
				 CHARACTER_EVENT__VALID_PROBABILITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHealthEvent(HealthEvent healthEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(healthEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateHealthEvent_PositiveHealthAmount(healthEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateHealthEvent_ValidHealthPercentage(healthEvent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositiveHealthAmount constraint of '<em>Health Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String HEALTH_EVENT__POSITIVE_HEALTH_AMOUNT__EEXPRESSION = "self.healthAmount >= 0";

	/**
	 * Validates the PositiveHealthAmount constraint of '<em>Health Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHealthEvent_PositiveHealthAmount(HealthEvent healthEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.HEALTH_EVENT,
				 healthEvent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositiveHealthAmount",
				 HEALTH_EVENT__POSITIVE_HEALTH_AMOUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ValidHealthPercentage constraint of '<em>Health Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String HEALTH_EVENT__VALID_HEALTH_PERCENTAGE__EEXPRESSION = "self.healthPercentage >= 0 and self.healthPercentage <= 100";

	/**
	 * Validates the ValidHealthPercentage constraint of '<em>Health Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHealthEvent_ValidHealthPercentage(HealthEvent healthEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.HEALTH_EVENT,
				 healthEvent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidHealthPercentage",
				 HEALTH_EVENT__VALID_HEALTH_PERCENTAGE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSpeedEvent(SpeedEvent speedEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(speedEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(speedEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateSpeedEvent_ValidSpeedPercentage(speedEvent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValidSpeedPercentage constraint of '<em>Speed Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SPEED_EVENT__VALID_SPEED_PERCENTAGE__EEXPRESSION = "self.speedPercentage >= -100 and self.speedPercentage <= 100";

	/**
	 * Validates the ValidSpeedPercentage constraint of '<em>Speed Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSpeedEvent_ValidSpeedPercentage(SpeedEvent speedEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.SPEED_EVENT,
				 speedEvent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValidSpeedPercentage",
				 SPEED_EVENT__VALID_SPEED_PERCENTAGE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeEvent(TimeEvent timeEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(timeEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(timeEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateTimeEvent_PositivePatrolTime(timeEvent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the PositivePatrolTime constraint of '<em>Time Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String TIME_EVENT__POSITIVE_PATROL_TIME__EEXPRESSION = "self.time >= 0";

	/**
	 * Validates the PositivePatrolTime constraint of '<em>Time Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeEvent_PositivePatrolTime(TimeEvent timeEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(BehaviourPackage.Literals.TIME_EVENT,
				 timeEvent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "PositivePatrolTime",
				 TIME_EVENT__POSITIVE_PATROL_TIME__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVisionEvent(VisionEvent visionEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(visionEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(visionEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(visionEvent, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttackEvent(AttackEvent attackEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(attackEvent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attackEvent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCharacterEvent_ValidProbability(attackEvent, diagnostics, context);
		return result;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatrolPathBehavior(PatrolPathBehavior patrolPathBehavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
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
