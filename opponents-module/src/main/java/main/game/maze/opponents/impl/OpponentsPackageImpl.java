/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.LootItem;
import main.game.maze.opponents.LootItemType;
import main.game.maze.opponents.LootTable;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

import main.game.maze.opponents.util.OpponentsValidator;
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
	private EClass lootTableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lootItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ghostEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum behaviorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lootItemTypeEEnum = null;

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

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theOpponentsPackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return OpponentsValidator.INSTANCE;
				 }
			 });

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
	public EAttribute getCharacterType_ImageBase() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_ImageTurnLeft() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_ImageTurnRight() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_ImageTurnUp() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_ImageTurnDown() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCharacterType_Behavior() {
		return (EAttribute)characterTypeEClass.getEStructuralFeatures().get(12);
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
	public EReference getZombie_ZombieLootTable() {
		return (EReference)zombieEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getZombie_InfectionLevel() {
		return (EAttribute)zombieEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getZombie_ResurrectionTime() {
		return (EAttribute)zombieEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getZombie_TouchSound() {
		return (EAttribute)zombieEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLootTable() {
		return lootTableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLootTable_Items() {
		return (EReference)lootTableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootTable_WeightCapacity() {
		return (EAttribute)lootTableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLootItem() {
		return lootItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootItem_Name() {
		return (EAttribute)lootItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootItem_Type() {
		return (EAttribute)lootItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootItem_Value() {
		return (EAttribute)lootItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootItem_Weight() {
		return (EAttribute)lootItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLootItem_GraphicBase() {
		return (EAttribute)lootItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGhost() {
		return ghostEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGhost_AttackDamage() {
		return (EAttribute)ghostEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGhost_VisibilityLevel() {
		return (EAttribute)ghostEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGhost_NonTangibilityEnergy() {
		return (EAttribute)ghostEClass.getEStructuralFeatures().get(2);
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
	public EEnum getLootItemType() {
		return lootItemTypeEEnum;
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
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__IMAGE_BASE);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__IMAGE_TURN_LEFT);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__IMAGE_TURN_RIGHT);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__IMAGE_TURN_UP);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__IMAGE_TURN_DOWN);
		createEAttribute(characterTypeEClass, CHARACTER_TYPE__BEHAVIOR);

		zombieEClass = createEClass(ZOMBIE);
		createEAttribute(zombieEClass, ZOMBIE__ATTACK_DAMAGE);
		createEReference(zombieEClass, ZOMBIE__ZOMBIE_LOOT_TABLE);
		createEAttribute(zombieEClass, ZOMBIE__INFECTION_LEVEL);
		createEAttribute(zombieEClass, ZOMBIE__RESURRECTION_TIME);
		createEAttribute(zombieEClass, ZOMBIE__TOUCH_SOUND);

		lootTableEClass = createEClass(LOOT_TABLE);
		createEReference(lootTableEClass, LOOT_TABLE__ITEMS);
		createEAttribute(lootTableEClass, LOOT_TABLE__WEIGHT_CAPACITY);

		lootItemEClass = createEClass(LOOT_ITEM);
		createEAttribute(lootItemEClass, LOOT_ITEM__NAME);
		createEAttribute(lootItemEClass, LOOT_ITEM__TYPE);
		createEAttribute(lootItemEClass, LOOT_ITEM__VALUE);
		createEAttribute(lootItemEClass, LOOT_ITEM__WEIGHT);
		createEAttribute(lootItemEClass, LOOT_ITEM__GRAPHIC_BASE);

		ghostEClass = createEClass(GHOST);
		createEAttribute(ghostEClass, GHOST__ATTACK_DAMAGE);
		createEAttribute(ghostEClass, GHOST__VISIBILITY_LEVEL);
		createEAttribute(ghostEClass, GHOST__NON_TANGIBILITY_ENERGY);

		// Create enums
		behaviorTypeEEnum = createEEnum(BEHAVIOR_TYPE);
		lootItemTypeEEnum = createEEnum(LOOT_ITEM_TYPE);
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
		ghostEClass.getESuperTypes().add(this.getCharacterType());

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
		initEAttribute(getCharacterType_ImageBase(), ecorePackage.getEString(), "ImageBase", "/main/game/maze/zombie.png", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_ImageTurnLeft(), ecorePackage.getEString(), "ImageTurnLeft", "/main/game/maze/zombie-left.png", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_ImageTurnRight(), ecorePackage.getEString(), "ImageTurnRight", "/main/game/maze/zombie-right.png", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_ImageTurnUp(), ecorePackage.getEString(), "ImageTurnUp", "/main/game/maze/zombie-up.png", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_ImageTurnDown(), ecorePackage.getEString(), "ImageTurnDown", "/main/game/maze/zombie-up.png", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCharacterType_Behavior(), this.getBehaviorType(), "behavior", "WANDER", 0, 1, CharacterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(zombieEClass, Zombie.class, "Zombie", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getZombie_AttackDamage(), ecorePackage.getEInt(), "attackDamage", "10", 0, 1, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getZombie_ZombieLootTable(), this.getLootTable(), null, "zombieLootTable", null, 0, 1, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZombie_InfectionLevel(), ecorePackage.getEInt(), "infectionLevel", "1", 0, 10, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZombie_ResurrectionTime(), ecorePackage.getEInt(), "resurrectionTime", "0", 0, 180, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZombie_TouchSound(), ecorePackage.getEString(), "touchSound", "/main/game/maze/zombieScream.mp3", 0, 1, Zombie.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lootTableEClass, LootTable.class, "LootTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLootTable_Items(), this.getLootItem(), null, "items", null, 0, -1, LootTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLootTable_WeightCapacity(), ecorePackage.getEInt(), "weightCapacity", "1", 1, 100, LootTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lootItemEClass, LootItem.class, "LootItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLootItem_Name(), ecorePackage.getEString(), "name", null, 0, 1, LootItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLootItem_Type(), this.getLootItemType(), "type", "FOOD", 1, 1, LootItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLootItem_Value(), ecorePackage.getEInt(), "value", null, 0, 1, LootItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLootItem_Weight(), ecorePackage.getEInt(), "weight", null, 1, 10, LootItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLootItem_GraphicBase(), ecorePackage.getEString(), "graphicBase", null, 0, 1, LootItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ghostEClass, Ghost.class, "Ghost", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGhost_AttackDamage(), ecorePackage.getEInt(), "attackDamage", "1", 0, 1, Ghost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGhost_VisibilityLevel(), ecorePackage.getEInt(), "visibilityLevel", "100", 0, 1, Ghost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGhost_NonTangibilityEnergy(), ecorePackage.getEDouble(), "nonTangibilityEnergy", "100", 0, 1, Ghost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(behaviorTypeEEnum, BehaviorType.class, "BehaviorType");
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.PASSIVE);
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.WANDER);
		addEEnumLiteral(behaviorTypeEEnum, BehaviorType.AGGRESSIVE);

		initEEnum(lootItemTypeEEnum, LootItemType.class, "LootItemType");
		addEEnumLiteral(lootItemTypeEEnum, LootItemType.FOOD);
		addEEnumLiteral(lootItemTypeEEnum, LootItemType.BOMB);
		addEEnumLiteral(lootItemTypeEEnum, LootItemType.TRAP);
		addEEnumLiteral(lootItemTypeEEnum, LootItemType.WEAPON);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL
		createOCLAnnotations();
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
			   "invocationDelegates", "",
			   "settingDelegates", "",
			   "validationDelegates", ""
		   });
		addAnnotation
		  (opponentModelEClass,
		   source,
		   new String[] {
			   "constraints", "validateMaxThreat"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createOCLAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL";
		addAnnotation
		  (opponentModelEClass,
		   source,
		   new String[] {
			   "validateMaxThreat", "self.characterTypes->collect(ct | ct.effectiveThreat)->sum() <= self.maxThreat"
		   });
	}

} //OpponentsPackageImpl
