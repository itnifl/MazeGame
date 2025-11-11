/**
 */
package main.game.maze.difficulties.util;

import java.util.Map;

import main.game.maze.difficulties.*;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.difficulties.DifficultiesPackage
 * @generated
 */
public class DifficultiesValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final DifficultiesValidator INSTANCE = new DifficultiesValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "main.game.maze.difficulties";

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
	public DifficultiesValidator() {
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
	  return DifficultiesPackage.eINSTANCE;
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
			case DifficultiesPackage.DIFFICULTY_GAME_DATA:
				return validateDifficultyGameData((DifficultyGameData)value, diagnostics, context);
			case DifficultiesPackage.EASY_DIFFICULTY:
				return validateEasyDifficulty((EasyDifficulty)value, diagnostics, context);
			case DifficultiesPackage.DIFFICULTY:
				return validateDifficulty((Difficulty)value, diagnostics, context);
			case DifficultiesPackage.NORMAL_DIFFICULTY:
				return validateNormalDifficulty((NormalDifficulty)value, diagnostics, context);
			case DifficultiesPackage.HARD_DIFFICULTY:
				return validateHardDifficulty((HardDifficulty)value, diagnostics, context);
			case DifficultiesPackage.ENEMY_MAX_COUNT:
				return validateEnemyMaxCount((EnemyMaxCount)value, diagnostics, context);
			case DifficultiesPackage.ENEMY_TYPES:
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
	public boolean validateDifficultyGameData(DifficultyGameData difficultyGameData, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(difficultyGameData, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEasyDifficulty(EasyDifficulty easyDifficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(easyDifficulty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(easyDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validateDifficulty_MaxThreatNonNegative(easyDifficulty, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDifficulty(Difficulty difficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(difficulty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(difficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validateDifficulty_MaxThreatNonNegative(difficulty, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MaxThreatNonNegative constraint of '<em>Difficulty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean validateDifficulty_MaxThreatNonNegative(Difficulty difficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (difficulty.getMaxThreat() >= 0) return true;

		if (diagnostics != null) {
			diagnostics.add(new org.eclipse.emf.common.util.BasicDiagnostic(
				org.eclipse.emf.common.util.Diagnostic.ERROR,
				DIAGNOSTIC_SOURCE,
				0,
				"maxThreat must be ≥ 0",
				new Object[] { difficulty }));
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNormalDifficulty(NormalDifficulty normalDifficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(normalDifficulty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(normalDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validateDifficulty_MaxThreatNonNegative(normalDifficulty, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHardDifficulty(HardDifficulty hardDifficulty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(hardDifficulty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(hardDifficulty, diagnostics, context);
		if (result || diagnostics != null) result &= validateDifficulty_MaxThreatNonNegative(hardDifficulty, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnemyMaxCount(EnemyMaxCount enemyMaxCount, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(enemyMaxCount, diagnostics, context);
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

} //DifficultiesValidator
