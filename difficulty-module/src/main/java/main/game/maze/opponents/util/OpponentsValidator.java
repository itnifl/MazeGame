/**
 */
package main.game.maze.opponents.util;

import java.util.Map;

import main.game.maze.opponents.*;

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
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Validate Max Threat' of 'Opponent Model'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int OPPONENT_MODEL__VALIDATE_MAX_THREAT = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

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
			case OpponentsPackage.BEHAVIOR_TYPE:
				return validateBehaviorType((BehaviorType)value, diagnostics, context);
			case OpponentsPackage.LOOT_ITEM_TYPE:
				return validateLootItemType((LootItemType)value, diagnostics, context);
			case OpponentsPackage.ENEMY_TYPES:
				return validateEnemyTypes((EnemyTypes)value, diagnostics, context);
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
	 * Validates the validateMaxThreat constraint of '<em>Opponent Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOpponentModel_validateMaxThreat(OpponentModel opponentModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return opponentModel.validateMaxThreat(diagnostics, context);
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
	public boolean validateEnemyTypes(EnemyTypes enemyTypes, DiagnosticChain diagnostics, Map<Object, Object> context) {
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
