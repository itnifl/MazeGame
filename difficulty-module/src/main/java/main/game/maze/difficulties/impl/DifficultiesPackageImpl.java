/**
 */
package main.game.maze.difficulties.impl;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DifficultiesPackageImpl extends EPackageImpl implements DifficultiesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass difficultyGameDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass easyDifficultyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass difficultyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass normalDifficultyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hardDifficultyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enemyMaxCountEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum enemyTypesEEnum = null;

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
	 * @see main.game.maze.difficulties.DifficultiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DifficultiesPackageImpl() {
		super(eNS_URI, DifficultiesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DifficultiesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DifficultiesPackage init() {
		if (isInited) return (DifficultiesPackage)EPackage.Registry.INSTANCE.getEPackage(DifficultiesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDifficultiesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DifficultiesPackageImpl theDifficultiesPackage = registeredDifficultiesPackage instanceof DifficultiesPackageImpl ? (DifficultiesPackageImpl)registeredDifficultiesPackage : new DifficultiesPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theDifficultiesPackage.createPackageContents();

		// Initialize created meta-data
		theDifficultiesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDifficultiesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DifficultiesPackage.eNS_URI, theDifficultiesPackage);
		return theDifficultiesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDifficultyGameData() {
		return difficultyGameDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDifficultyGameData_Difficulties() {
		return (EReference)difficultyGameDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDifficultyGameData_CurrentDifficulty() {
		return (EReference)difficultyGameDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEasyDifficulty() {
		return easyDifficultyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDifficulty() {
		return difficultyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficulty_InstantDeath() {
		return (EAttribute)difficultyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDifficulty_EnemyMaxCount() {
		return (EReference)difficultyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficulty_MonstersMovementSpeedMultiplier() {
		return (EAttribute)difficultyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficulty_MonstersDamageMultiplier() {
		return (EAttribute)difficultyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDifficulty_MaxThreat() {
		return (EAttribute)difficultyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNormalDifficulty() {
		return normalDifficultyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHardDifficulty() {
		return hardDifficultyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnemyMaxCount() {
		return enemyMaxCountEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnemyMaxCount_Type() {
		return (EAttribute)enemyMaxCountEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnemyMaxCount_MaxCount() {
		return (EAttribute)enemyMaxCountEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getEnemyTypes() {
		return enemyTypesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultiesFactory getDifficultiesFactory() {
		return (DifficultiesFactory)getEFactoryInstance();
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
		difficultyGameDataEClass = createEClass(DIFFICULTY_GAME_DATA);
		createEReference(difficultyGameDataEClass, DIFFICULTY_GAME_DATA__DIFFICULTIES);
		createEReference(difficultyGameDataEClass, DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY);

		easyDifficultyEClass = createEClass(EASY_DIFFICULTY);

		difficultyEClass = createEClass(DIFFICULTY);
		createEAttribute(difficultyEClass, DIFFICULTY__INSTANT_DEATH);
		createEReference(difficultyEClass, DIFFICULTY__ENEMY_MAX_COUNT);
		createEAttribute(difficultyEClass, DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER);
		createEAttribute(difficultyEClass, DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER);
		createEAttribute(difficultyEClass, DIFFICULTY__MAX_THREAT);

		normalDifficultyEClass = createEClass(NORMAL_DIFFICULTY);

		hardDifficultyEClass = createEClass(HARD_DIFFICULTY);

		enemyMaxCountEClass = createEClass(ENEMY_MAX_COUNT);
		createEAttribute(enemyMaxCountEClass, ENEMY_MAX_COUNT__TYPE);
		createEAttribute(enemyMaxCountEClass, ENEMY_MAX_COUNT__MAX_COUNT);

		// Create enums
		enemyTypesEEnum = createEEnum(ENEMY_TYPES);
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
		easyDifficultyEClass.getESuperTypes().add(this.getDifficulty());
		normalDifficultyEClass.getESuperTypes().add(this.getDifficulty());
		hardDifficultyEClass.getESuperTypes().add(this.getDifficulty());

		// Initialize classes, features, and operations; add parameters
		initEClass(difficultyGameDataEClass, DifficultyGameData.class, "DifficultyGameData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDifficultyGameData_Difficulties(), this.getDifficulty(), null, "difficulties", null, 1, -1, DifficultyGameData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDifficultyGameData_CurrentDifficulty(), this.getDifficulty(), null, "currentDifficulty", null, 0, 1, DifficultyGameData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(easyDifficultyEClass, EasyDifficulty.class, "EasyDifficulty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(difficultyEClass, Difficulty.class, "Difficulty", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDifficulty_InstantDeath(), ecorePackage.getEBoolean(), "instantDeath", null, 0, 1, Difficulty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDifficulty_EnemyMaxCount(), this.getEnemyMaxCount(), null, "enemyMaxCount", null, 0, -1, Difficulty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getDifficulty_MonstersMovementSpeedMultiplier(), ecorePackage.getEDouble(), "monstersMovementSpeedMultiplier", null, 0, 1, Difficulty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDifficulty_MonstersDamageMultiplier(), ecorePackage.getEDouble(), "monstersDamageMultiplier", null, 0, 1, Difficulty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDifficulty_MaxThreat(), ecorePackage.getEInt(), "maxThreat", null, 0, 1, Difficulty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(normalDifficultyEClass, NormalDifficulty.class, "NormalDifficulty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hardDifficultyEClass, HardDifficulty.class, "HardDifficulty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(enemyMaxCountEClass, EnemyMaxCount.class, "EnemyMaxCount", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnemyMaxCount_Type(), this.getEnemyTypes(), "type", null, 0, 1, EnemyMaxCount.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnemyMaxCount_MaxCount(), ecorePackage.getEInt(), "maxCount", null, 0, 1, EnemyMaxCount.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(enemyTypesEEnum, EnemyTypes.class, "EnemyTypes");
		addEEnumLiteral(enemyTypesEEnum, EnemyTypes.ZOMBIE);
		addEEnumLiteral(enemyTypesEEnum, EnemyTypes.GHOST);
		addEEnumLiteral(enemyTypesEEnum, EnemyTypes.PUMPKINBOMBER);

		// Create resource
		createResource(eNS_URI);
	}

} //DifficultiesPackageImpl
