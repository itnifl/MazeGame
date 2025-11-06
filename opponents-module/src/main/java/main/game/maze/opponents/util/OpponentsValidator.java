/**
 */
package main.game.maze.opponents.util;

import java.util.Map;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.opponents.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.opponents.OpponentsPackage
 * @generated
 */
public class OpponentsValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OpponentsValidator INSTANCE = new OpponentsValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "main.game.maze.opponents";

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
	public OpponentsValidator() {
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
	  return OpponentsPackage.eINSTANCE;
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
			case OpponentsPackage.OPPONENT_MODEL:
				return validateOpponentModel((OpponentModel)value, diagnostics, context);
			case OpponentsPackage.CHARACTER_TYPE:
				return validateCharacterType((CharacterType)value, diagnostics, context);
			case OpponentsPackage.ZOMBIE:
				return validateZombie((Zombie)value, diagnostics, context);
			case OpponentsPackage.LOOT_TABLE:
				return validateLootTable((LootTable)value, diagnostics, context);
			case OpponentsPackage.LOOT_ITEM:
				return validateLootItem((LootItem)value, diagnostics, context);
			case OpponentsPackage.GHOST:
				return validateGhost((Ghost)value, diagnostics, context);
			case OpponentsPackage.RANGED_ENEMY:
				return validateRangedEnemy((RangedEnemy)value, diagnostics, context);
			case OpponentsPackage.PUMPKIN_BOMBER:
				return validatePumpkinBomber((PumpkinBomber)value, diagnostics, context);
			case OpponentsPackage.BEHAVIOR_TYPE:
				return validateBehaviorType((BehaviorType)value, diagnostics, context);
			case OpponentsPackage.LOOT_ITEM_TYPE:
				return validateLootItemType((LootItemType)value, diagnostics, context);
			case OpponentsPackage.PROJECTILE_TYPE:
				return validateProjectileType((ProjectileType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOpponentModel(OpponentModel opponentModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(opponentModel, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(opponentModel, diagnostics, context);
		if (result || diagnostics != null) result &= validateOpponentModel_validateMaxThreat(opponentModel, diagnostics, context);
		return result;
	}

	/**
	 * Validates the validateMaxThreat constraint of 'OpponentModel'.
	 * @generated NOT
	 */
	public boolean validateOpponentModel_validateMaxThreat(
		OpponentModel opponentModel, DiagnosticChain diagnostics, Map<Object,Object> context) {

		// 1) Read maxThreat from the selected difficulty (or 0 if none)
		int maxThreat = 0;
		Difficulty d = opponentModel.getSelectedDifficulty();
		if (d != null) {
			maxThreat = d.getMaxThreat();
		}

		// 2) Sum effective threat of all character types
		var currentThreatLevel = opponentModel.getGameSetCurrentThreatLevel();		
		if(currentThreatLevel == 0) {
			int sum = 0;
			for (CharacterType ct : opponentModel.getCharacterTypes()) {
				// If you have ct.getEffectiveThreat(), use that; otherwise use threatLevel
				int t;
				try {
					t = ct.getEffectiveThreat(); // comment this out if you do not have it
				} catch (Throwable ignore) {
					t = (int) ct.getThreatLevel();     // fallback
				}
				if (t > 0) {
					sum += t;
					if (sum > maxThreat) break;
				}
			}
			opponentModel.setGameSetCurrentThreatLevel(sum);
		}
		
		// 3) Pass or report a diagnostic
		if (opponentModel.getGameSetCurrentThreatLevel() <= maxThreat) return true;

		if (diagnostics != null) {
			diagnostics.add(
				createDiagnostic(
					Diagnostic.ERROR,
					DIAGNOSTIC_SOURCE,
					0, // or a specific constant if you keep one
					"_UI_GenericConstraint_diagnostic",
					new Object[] { "validateMaxThreat", getObjectLabel(opponentModel, context) },
					new Object[] { opponentModel },
					context));

		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCharacterType(CharacterType characterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(characterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateZombie(Zombie zombie, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(zombie, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLootTable(LootTable lootTable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(lootTable, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLootItem(LootItem lootItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(lootItem, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGhost(Ghost ghost, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(ghost, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRangedEnemy(RangedEnemy rangedEnemy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(rangedEnemy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePumpkinBomber(PumpkinBomber pumpkinBomber, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(pumpkinBomber, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBehaviorType(BehaviorType behaviorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLootItemType(LootItemType lootItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProjectileType(ProjectileType projectileType, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

} //OpponentsValidator
