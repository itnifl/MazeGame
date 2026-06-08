/**
 */
package main.game.maze.walls.impl;

import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallMaterialBaseType;
import main.game.maze.walls.WallModel;
import main.game.maze.walls.WallsFactory;
import main.game.maze.walls.WallsPackage;

import main.game.maze.walls.util.WallsValidator;

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
public class WallsPackageImpl extends EPackageImpl implements WallsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass wallMaterialEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass wallModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum wallMaterialBaseTypeEEnum = null;

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
	 * @see main.game.maze.walls.WallsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private WallsPackageImpl() {
		super(eNS_URI, WallsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link WallsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static WallsPackage init() {
		if (isInited)
			return (WallsPackage) EPackage.Registry.INSTANCE.getEPackage(WallsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredWallsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		WallsPackageImpl theWallsPackage = registeredWallsPackage instanceof WallsPackageImpl
				? (WallsPackageImpl) registeredWallsPackage
				: new WallsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theWallsPackage.createPackageContents();

		// Initialize created meta-data
		theWallsPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theWallsPackage, new EValidator.Descriptor() {
			@Override
			public EValidator getEValidator() {
				return WallsValidator.INSTANCE;
			}
		});

		// Mark meta-data to indicate it can't be changed
		theWallsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(WallsPackage.eNS_URI, theWallsPackage);
		return theWallsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWallMaterial() {
		return wallMaterialEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_WallBaseType() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_DisplayName() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_Breakable() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_HitPoints() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_Id() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWallMaterial_BaseImage() {
		return (EAttribute) wallMaterialEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWallModel() {
		return wallModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWallModel_Materials() {
		return (EReference) wallModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getWallMaterialBaseType() {
		return wallMaterialBaseTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WallsFactory getWallsFactory() {
		return (WallsFactory) getEFactoryInstance();
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		wallMaterialEClass = createEClass(WALL_MATERIAL);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__WALL_BASE_TYPE);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__DISPLAY_NAME);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__BREAKABLE);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__HIT_POINTS);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__ID);
		createEAttribute(wallMaterialEClass, WALL_MATERIAL__BASE_IMAGE);

		wallModelEClass = createEClass(WALL_MODEL);
		createEReference(wallModelEClass, WALL_MODEL__MATERIALS);

		// Create enums
		wallMaterialBaseTypeEEnum = createEEnum(WALL_MATERIAL_BASE_TYPE);
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
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(wallMaterialEClass, WallMaterial.class, "WallMaterial", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWallMaterial_WallBaseType(), this.getWallMaterialBaseType(), "wallBaseType", "STEEL", 1, 1,
				WallMaterial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getWallMaterial_DisplayName(), ecorePackage.getEString(), "displayName", null, 1, 1,
				WallMaterial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getWallMaterial_Breakable(), ecorePackage.getEBoolean(), "breakable", "false", 1, 1,
				WallMaterial.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getWallMaterial_HitPoints(), ecorePackage.getEInt(), "hitPoints", "0", 1, 1, WallMaterial.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWallMaterial_Id(), ecorePackage.getEString(), "id", null, 1, 1, WallMaterial.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getWallMaterial_BaseImage(), ecorePackage.getEString(), "baseImage",
				"/main/game/maze/baseWall.png", 0, 1, WallMaterial.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(wallModelEClass, WallModel.class, "WallModel", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWallModel_Materials(), this.getWallMaterial(), null, "materials", null, 0, -1,
				WallModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(wallMaterialBaseTypeEEnum, WallMaterialBaseType.class, "WallMaterialBaseType");
		addEEnumLiteral(wallMaterialBaseTypeEEnum, WallMaterialBaseType.GLASS);
		addEEnumLiteral(wallMaterialBaseTypeEEnum, WallMaterialBaseType.DIRT);
		addEEnumLiteral(wallMaterialBaseTypeEEnum, WallMaterialBaseType.WOOD);
		addEEnumLiteral(wallMaterialBaseTypeEEnum, WallMaterialBaseType.STONE);
		addEEnumLiteral(wallMaterialBaseTypeEEnum, WallMaterialBaseType.STEEL);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore/OCL
		createOCLAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createOCLAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL";
		addAnnotation(wallMaterialEClass, source, new String[] { "ValidHitPoints",
				"(breakable = false implies hitPoints = 0) and\n(breakable = true implies hitPoints > 0)" });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation(wallMaterialEClass, source, new String[] { "constraints", "ValidHitPoints" });
	}

} //WallsPackageImpl
