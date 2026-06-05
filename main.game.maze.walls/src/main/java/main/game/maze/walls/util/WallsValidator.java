/**
 */
package main.game.maze.walls.util;

import java.util.Map;

import main.game.maze.walls.*;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.walls.WallsPackage
 * @generated
 */
public class WallsValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final WallsValidator INSTANCE = new WallsValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "main.game.maze.walls";

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
	public WallsValidator() {
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
		return WallsPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		switch (classifierID) {
		case WallsPackage.WALL_MATERIAL:
			return validateWallMaterial((WallMaterial) value, diagnostics, context);
		case WallsPackage.WALL_MODEL:
			return validateWallModel((WallModel) value, diagnostics, context);
		case WallsPackage.WALL_MATERIAL_BASE_TYPE:
			return validateWallMaterialBaseType((WallMaterialBaseType)value, diagnostics, context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWallMaterial(WallMaterial wallMaterial, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(wallMaterial, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(wallMaterial, diagnostics, context);
		if (result || diagnostics != null)
			result &= validateWallMaterial_ValidHitPoints(wallMaterial, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ValidHitPoints constraint of '<em>Wall Material</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWallMaterial_ValidHitPoints(WallMaterial wallMaterial, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		// TODO implement constraint validation logic.
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWallModel(WallModel wallModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(wallModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWallMaterialBaseType(WallMaterialBaseType wallMaterialBaseType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
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

} //WallsValidator


