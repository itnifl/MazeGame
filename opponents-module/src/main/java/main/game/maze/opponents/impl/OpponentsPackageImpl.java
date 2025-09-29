/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

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
public class OpponentsPackageImpl extends EPackageImpl implements OpponentsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass opponentModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass characterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass zombieEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum behaviorTypeEEnum = null;

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
	 * @see main.game.maze.opponents.OpponentsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OpponentsPackageImpl() {
		super(eNS_URI, OpponentsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OpponentsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OpponentsPackage init() {
		if (isInited) return (OpponentsPackage)EPackage.Registry.INSTANCE.getEPackage(OpponentsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredOpponentsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		OpponentsPackageImpl theOpponentsPackage = registeredOpponentsPackage instanceof OpponentsPackageImpl ? (OpponentsPackageImpl)registeredOpponentsPackage : new OpponentsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theOpponentsPackage.createPackageContents();

		// Initialize created meta-data
		theOpponentsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOpponentsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OpponentsPackage.eNS_URI, theOpponentsPackage);
		return theOpponentsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOpponentModel() {
		return opponentModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOpponentModel_Name() {
		return (EAttribute)opponentModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOpponentModel_CharacterTypes() {
		return (EReference)opponentModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOpponentModel_MaxThreat() {
		return (EAttribute)opponentModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCharacterType() {
		return characterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_Id() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_DisplayName() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_Enabled() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_Health() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_Speed() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_ThreatLevel() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_EffectiveThreat() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getZombie() {
		return zombieEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getZombie_AttackDamage() {
		return (EAttribute)zombieEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getZombie_Behavior() {
		return (EAttribute)zombieEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBehaviorType() {
		return behaviorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpponentsFactory getOpponentsFactory() {
		return (OpponentsFactory)getEFactoryInstance();
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
		opponentModelEClass = createEClass(OPPONENT_MODEL);
		createEAttribute(opponentModelEClass, OPPONENT_MODEL__NAME);
		createEReference(opponentModelEClass, OPPONENT_MODEL__CHARACTER_TYPES);
		createEAttribute(opponentModelEClass, OPPONENT_MODEL__MAX_THREAT);

		characterTypeEClass = createEClass(CHARACTER_TYPE);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__ID);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__DISPLAY_NAME);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__ENABLED);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__HEALTH);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__SPEED);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__THREAT_LEVEL);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__EFFECTIVE_THREAT);

		zombieEClass = createEClass(ZOMBIE);
		createEAttribute(zombieEClass, ZOMBIE__ATTACK_DAMAGE);
		createEAttribute(zombieEClass, ZOMBIE__BEHAVIOR);

		// Create enums
		behaviorTypeEEnum = createEEnum(BEHAVIOR_TYPE);
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
		zombieEClass.getESuperTypes().add(this.getCharacterType());

		// Initialize classes, features, and operations; add parameters
		initEClass(opponentModelEClass, OpponentModel.class, "OpponentModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOpponentModel_Name(), ecorePackage.getEString(), "name", null, 0, 1, OpponentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOpponentModel_CharacterTypes(), this.getCharacterType(), null, "characterTypes", null, 1, -1, OpponentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOpponentModel_MaxThreat(), ecorePackage.getEDouble(), "maxThreat", null, 0, 1, OpponentModel.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(characterTypeEClass, CharacterType.class, "CharacterType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCharacterType_Id(), ecorePackage.getEString(), "id", null, 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_Enabled(), ecorePackage.getEBoolean(), "enabled", "true", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_Health(), ecorePackage.getEInt(), "health", "100", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_Speed(), ecorePackage.getEDouble(), "speed", "1.0", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_ThreatLevel(), ecorePackage.getEDouble(), "threatLevel", null, 0, 1, CharacterType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_EffectiveThreat(), ecorePackage.getEInt(), "effectiveThreat", "1", 0, 1, CharacterType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(zombieEClass, Zombie.class, "Zombie", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getZombie_AttackDamage(), ecorePackage.getEInt(), "attackDamage", "10", 0, 1, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZombie_Behavior(), this.getBehaviorType(), "behavior", "WANDER", 0, 1, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(behaviorTypeEEnum, BehaviorType.class, "BehaviorType");
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.PASSIVE);
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.WANDER);
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.AGGRESSIVE);

		// Create resource
		createResource(eNS_URI);
	}

} //OpponentsPackageImpl
