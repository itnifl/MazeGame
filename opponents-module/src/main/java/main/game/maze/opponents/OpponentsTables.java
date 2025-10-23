/*******************************************************************************
 *************************************************************************
 * This code is 100% auto-generated
 * from:
 *   /opponents-module/src/main/resources/opponents/opponents.ecore
 * using:
 *   /opponents-module/src/main/resources/opponents/opponents.genmodel
 *   org.eclipse.ocl.examples.codegen.oclinecore.OCLinEcoreTables
 *
 * Do not edit it.
 *******************************************************************************/
package main.game.maze.opponents;

import main.game.maze.difficulties.DifficultiesPackage;
// import main.game.maze.opponents.OpponentsPackage;
// import main.game.maze.opponents.OpponentsTables;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.pivot.ids.ClassId;
import org.eclipse.ocl.pivot.ids.CollectionTypeId;
import org.eclipse.ocl.pivot.ids.DataTypeId;
import org.eclipse.ocl.pivot.ids.EnumerationId;
import org.eclipse.ocl.pivot.ids.IdManager;
import org.eclipse.ocl.pivot.ids.NsURIPackageId;
import org.eclipse.ocl.pivot.ids.TypeId;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreExecutorEnumeration;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreExecutorEnumerationLiteral;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreExecutorPackage;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreExecutorProperty;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreExecutorType;
import org.eclipse.ocl.pivot.internal.library.ecore.EcoreLibraryOppositeProperty;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorFragment;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorOperation;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorProperty;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorPropertyWithImplementation;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorStandardLibrary;
import org.eclipse.ocl.pivot.internal.library.executor.ExecutorType;
import org.eclipse.ocl.pivot.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.pivot.utilities.AbstractTables;
import org.eclipse.ocl.pivot.utilities.ValueUtil;
import org.eclipse.ocl.pivot.values.IntegerValue;

/**
 * OpponentsTables provides the dispatch tables for the opponents for use by the OCL dispatcher.
 *
 * In order to ensure correct static initialization, a top level class element must be accessed
 * before any nested class element. Therefore an access to PACKAGE.getClass() is recommended.
 */
public class OpponentsTables extends AbstractTables
{
	static {
		Init.initStart();
	}

	/**
	 *	The package descriptor for the package.
	 */
	public static final EcoreExecutorPackage PACKAGE = new EcoreExecutorPackage(OpponentsPackage.eINSTANCE);

	/**
	 *	The library of all packages and types.
	 */
	public static final ExecutorStandardLibrary LIBRARY = OCLstdlibTables.LIBRARY;

	/**
	 *	Constants used by auto-generated code.
	 */
	public static final /*@NonInvalid*/ NsURIPackageId PACKid_http_c_s_s_main_game_maze_s_difficulty = IdManager.getNsURIPackageId("http://main.game.maze/difficulty", null, DifficultiesPackage.eINSTANCE);
	public static final /*@NonInvalid*/ NsURIPackageId PACKid_http_c_s_s_main_game_maze_s_opponents = IdManager.getNsURIPackageId("http://main.game.maze/opponents", null, OpponentsPackage.eINSTANCE);
	public static final /*@NonInvalid*/ NsURIPackageId PACKid_http_c_s_s_www_eclipse_org_s_emf_s_2002_s_Ecore = IdManager.getNsURIPackageId("http://www.eclipse.org/emf/2002/Ecore", null, EcorePackage.eINSTANCE);
	public static final /*@NonInvalid*/ ClassId CLSSid_CharacterType = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getClassId("CharacterType", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_Difficulty = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_difficulty.getClassId("Difficulty", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_DifficultyGameData = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_difficulty.getClassId("DifficultyGameData", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_LootItem = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getClassId("LootItem", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_LootTable = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getClassId("LootTable", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_OpponentModel = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getClassId("OpponentModel", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_Zombie = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getClassId("Zombie", 0);
	public static final /*@NonInvalid*/ DataTypeId DATAid_EDouble = OpponentsTables.PACKid_http_c_s_s_www_eclipse_org_s_emf_s_2002_s_Ecore.getDataTypeId("EDouble", 0);
	public static final /*@NonInvalid*/ DataTypeId DATAid_EInt = OpponentsTables.PACKid_http_c_s_s_www_eclipse_org_s_emf_s_2002_s_Ecore.getDataTypeId("EInt", 0);
	public static final /*@NonInvalid*/ EnumerationId ENUMid_BehaviorType = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getEnumerationId("BehaviorType");
	public static final /*@NonInvalid*/ EnumerationId ENUMid_LootItemType = OpponentsTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getEnumerationId("LootItemType");
	public static final /*@NonInvalid*/ IntegerValue INT_0 = ValueUtil.integerValueOf("0");
	public static final /*@NonInvalid*/ CollectionTypeId BAG_CLSSid_Zombie = TypeId.BAG.getSpecializedId(OpponentsTables.CLSSid_Zombie, false, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId ORD_CLSSid_CharacterType = TypeId.ORDERED_SET.getSpecializedId(OpponentsTables.CLSSid_CharacterType, true, ValueUtil.ONE_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId ORD_CLSSid_LootItem = TypeId.ORDERED_SET.getSpecializedId(OpponentsTables.CLSSid_LootItem, true, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId ORD_DATAid_EInt = TypeId.ORDERED_SET.getSpecializedId(OpponentsTables.DATAid_EInt, true, ValueUtil.ONE_VALUE, ValueUtil.integerValueOf(100));
	public static final /*@NonInvalid*/ CollectionTypeId ORD_DATAid_EInt_0 = TypeId.ORDERED_SET.getSpecializedId(OpponentsTables.DATAid_EInt, true, ValueUtil.ONE_VALUE, ValueUtil.integerValueOf(10));
	public static final /*@NonInvalid*/ CollectionTypeId SEQ_DATAid_EInt = TypeId.SEQUENCE.getSpecializedId(OpponentsTables.DATAid_EInt, true, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);

	/**
	 *	The type parameters for templated types and operations.
	 */
	public static class TypeParameters {
		static {
			Init.initStart();
			OpponentsTables.init();
		}

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::TypeParameters and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The type descriptors for each type.
	 */
	public static class Types {
		static {
			Init.initStart();
			TypeParameters.init();
		}

		public static final EcoreExecutorEnumeration _BehaviorType = new EcoreExecutorEnumeration(OpponentsPackage.Literals.BEHAVIOR_TYPE, PACKAGE, 0);
		public static final EcoreExecutorType _CharacterType = new EcoreExecutorType(OpponentsPackage.Literals.CHARACTER_TYPE, PACKAGE, 0 | ExecutorType.ABSTRACT);
		public static final EcoreExecutorType _Ghost = new EcoreExecutorType(OpponentsPackage.Literals.GHOST, PACKAGE, 0);
		public static final EcoreExecutorType _LootItem = new EcoreExecutorType(OpponentsPackage.Literals.LOOT_ITEM, PACKAGE, 0);
		public static final EcoreExecutorEnumeration _LootItemType = new EcoreExecutorEnumeration(OpponentsPackage.Literals.LOOT_ITEM_TYPE, PACKAGE, 0);
		public static final EcoreExecutorType _LootTable = new EcoreExecutorType(OpponentsPackage.Literals.LOOT_TABLE, PACKAGE, 0);
		public static final EcoreExecutorType _OpponentModel = new EcoreExecutorType(OpponentsPackage.Literals.OPPONENT_MODEL, PACKAGE, 0);
		public static final EcoreExecutorType _Zombie = new EcoreExecutorType(OpponentsPackage.Literals.ZOMBIE, PACKAGE, 0);

		private static final EcoreExecutorType /*@NonNull*/ [] types = {
			_BehaviorType,
			_CharacterType,
			_Ghost,
			_LootItem,
			_LootItemType,
			_LootTable,
			_OpponentModel,
			_Zombie
		};

		/*
		 *	Install the type descriptors in the package descriptor.
		 */
		static {
			PACKAGE.init(LIBRARY, types);
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::Types and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The fragment descriptors for the local elements of each type and its supertypes.
	 */
	public static class Fragments {
		static {
			Init.initStart();
			Types.init();
		}

		private static final ExecutorFragment _BehaviorType__BehaviorType = new ExecutorFragment(Types._BehaviorType, OpponentsTables.Types._BehaviorType);
		private static final ExecutorFragment _BehaviorType__OclAny = new ExecutorFragment(Types._BehaviorType, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _BehaviorType__OclElement = new ExecutorFragment(Types._BehaviorType, OCLstdlibTables.Types._OclElement);
		private static final ExecutorFragment _BehaviorType__OclEnumeration = new ExecutorFragment(Types._BehaviorType, OCLstdlibTables.Types._OclEnumeration);
		private static final ExecutorFragment _BehaviorType__OclType = new ExecutorFragment(Types._BehaviorType, OCLstdlibTables.Types._OclType);

		private static final ExecutorFragment _CharacterType__CharacterType = new ExecutorFragment(Types._CharacterType, OpponentsTables.Types._CharacterType);
		private static final ExecutorFragment _CharacterType__OclAny = new ExecutorFragment(Types._CharacterType, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _CharacterType__OclElement = new ExecutorFragment(Types._CharacterType, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _Ghost__CharacterType = new ExecutorFragment(Types._Ghost, OpponentsTables.Types._CharacterType);
		private static final ExecutorFragment _Ghost__Ghost = new ExecutorFragment(Types._Ghost, OpponentsTables.Types._Ghost);
		private static final ExecutorFragment _Ghost__OclAny = new ExecutorFragment(Types._Ghost, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _Ghost__OclElement = new ExecutorFragment(Types._Ghost, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _LootItem__LootItem = new ExecutorFragment(Types._LootItem, OpponentsTables.Types._LootItem);
		private static final ExecutorFragment _LootItem__OclAny = new ExecutorFragment(Types._LootItem, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _LootItem__OclElement = new ExecutorFragment(Types._LootItem, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _LootItemType__LootItemType = new ExecutorFragment(Types._LootItemType, OpponentsTables.Types._LootItemType);
		private static final ExecutorFragment _LootItemType__OclAny = new ExecutorFragment(Types._LootItemType, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _LootItemType__OclElement = new ExecutorFragment(Types._LootItemType, OCLstdlibTables.Types._OclElement);
		private static final ExecutorFragment _LootItemType__OclEnumeration = new ExecutorFragment(Types._LootItemType, OCLstdlibTables.Types._OclEnumeration);
		private static final ExecutorFragment _LootItemType__OclType = new ExecutorFragment(Types._LootItemType, OCLstdlibTables.Types._OclType);

		private static final ExecutorFragment _LootTable__LootTable = new ExecutorFragment(Types._LootTable, OpponentsTables.Types._LootTable);
		private static final ExecutorFragment _LootTable__OclAny = new ExecutorFragment(Types._LootTable, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _LootTable__OclElement = new ExecutorFragment(Types._LootTable, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _OpponentModel__OclAny = new ExecutorFragment(Types._OpponentModel, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _OpponentModel__OclElement = new ExecutorFragment(Types._OpponentModel, OCLstdlibTables.Types._OclElement);
		private static final ExecutorFragment _OpponentModel__OpponentModel = new ExecutorFragment(Types._OpponentModel, OpponentsTables.Types._OpponentModel);

		private static final ExecutorFragment _Zombie__CharacterType = new ExecutorFragment(Types._Zombie, OpponentsTables.Types._CharacterType);
		private static final ExecutorFragment _Zombie__OclAny = new ExecutorFragment(Types._Zombie, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _Zombie__OclElement = new ExecutorFragment(Types._Zombie, OCLstdlibTables.Types._OclElement);
		private static final ExecutorFragment _Zombie__Zombie = new ExecutorFragment(Types._Zombie, OpponentsTables.Types._Zombie);

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::Fragments and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The parameter lists shared by operations.
	 *
	 * @noextend This class is not intended to be subclassed by clients.
	 * @noinstantiate This class is not intended to be instantiated by clients.
	 * @noreference This class is not intended to be referenced by clients.
	 */
	public static class Parameters {
		static {
			Init.initStart();
			Fragments.init();
		}

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::Parameters and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The operation descriptors for each operation of each type.
	 *
	 * @noextend This class is not intended to be subclassed by clients.
	 * @noinstantiate This class is not intended to be instantiated by clients.
	 * @noreference This class is not intended to be referenced by clients.
	 */
	public static class Operations {
		static {
			Init.initStart();
			Parameters.init();
		}

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::Operations and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The property descriptors for each property of each type.
	 *
	 * @noextend This class is not intended to be subclassed by clients.
	 * @noinstantiate This class is not intended to be instantiated by clients.
	 * @noreference This class is not intended to be referenced by clients.
	 */
	public static class Properties {
		static {
			Init.initStart();
			Operations.init();
		}


		public static final ExecutorProperty _CharacterType__ImageBase = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__IMAGE_BASE, Types._CharacterType, 0);
		public static final ExecutorProperty _CharacterType__ImageTurnDown = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__IMAGE_TURN_DOWN, Types._CharacterType, 1);
		public static final ExecutorProperty _CharacterType__ImageTurnLeft = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__IMAGE_TURN_LEFT, Types._CharacterType, 2);
		public static final ExecutorProperty _CharacterType__ImageTurnRight = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__IMAGE_TURN_RIGHT, Types._CharacterType, 3);
		public static final ExecutorProperty _CharacterType__ImageTurnUp = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__IMAGE_TURN_UP, Types._CharacterType, 4);
		public static final ExecutorProperty _CharacterType__behavior = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__BEHAVIOR, Types._CharacterType, 5);
		public static final ExecutorProperty _CharacterType__displayName = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__DISPLAY_NAME, Types._CharacterType, 6);
		public static final ExecutorProperty _CharacterType__effectiveThreat = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__EFFECTIVE_THREAT, Types._CharacterType, 7);
		public static final ExecutorProperty _CharacterType__enabled = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__ENABLED, Types._CharacterType, 8);
		public static final ExecutorProperty _CharacterType__health = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__HEALTH, Types._CharacterType, 9);
		public static final ExecutorProperty _CharacterType__id = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__ID, Types._CharacterType, 10);
		public static final ExecutorProperty _CharacterType__speed = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__SPEED, Types._CharacterType, 11);
		public static final ExecutorProperty _CharacterType__threatLevel = new EcoreExecutorProperty(OpponentsPackage.Literals.CHARACTER_TYPE__THREAT_LEVEL, Types._CharacterType, 12);
		public static final ExecutorProperty _CharacterType__OpponentModel__characterTypes = new ExecutorPropertyWithImplementation("OpponentModel", Types._CharacterType, 13, new EcoreLibraryOppositeProperty(OpponentsPackage.Literals.OPPONENT_MODEL__CHARACTER_TYPES));

		public static final ExecutorProperty _Ghost__attackDamage = new EcoreExecutorProperty(OpponentsPackage.Literals.GHOST__ATTACK_DAMAGE, Types._Ghost, 0);
		public static final ExecutorProperty _Ghost__nonTangibilityEnergy = new EcoreExecutorProperty(OpponentsPackage.Literals.GHOST__NON_TANGIBILITY_ENERGY, Types._Ghost, 1);
		public static final ExecutorProperty _Ghost__visibilityLevel = new EcoreExecutorProperty(OpponentsPackage.Literals.GHOST__VISIBILITY_LEVEL, Types._Ghost, 2);

		public static final ExecutorProperty _LootItem__graphicBase = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_ITEM__GRAPHIC_BASE, Types._LootItem, 0);
		public static final ExecutorProperty _LootItem__name = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_ITEM__NAME, Types._LootItem, 1);
		public static final ExecutorProperty _LootItem__type = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_ITEM__TYPE, Types._LootItem, 2);
		public static final ExecutorProperty _LootItem__value = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_ITEM__VALUE, Types._LootItem, 3);
		public static final ExecutorProperty _LootItem__weight = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_ITEM__WEIGHT, Types._LootItem, 4);
		public static final ExecutorProperty _LootItem__LootTable__items = new ExecutorPropertyWithImplementation("LootTable", Types._LootItem, 5, new EcoreLibraryOppositeProperty(OpponentsPackage.Literals.LOOT_TABLE__ITEMS));

		public static final ExecutorProperty _LootTable__items = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_TABLE__ITEMS, Types._LootTable, 0);
		public static final ExecutorProperty _LootTable__weightCapacity = new EcoreExecutorProperty(OpponentsPackage.Literals.LOOT_TABLE__WEIGHT_CAPACITY, Types._LootTable, 1);
		public static final ExecutorProperty _LootTable__Zombie__zombieLootTable = new ExecutorPropertyWithImplementation("Zombie", Types._LootTable, 2, new EcoreLibraryOppositeProperty(OpponentsPackage.Literals.ZOMBIE__ZOMBIE_LOOT_TABLE));

		public static final ExecutorProperty _OpponentModel__characterTypes = new EcoreExecutorProperty(OpponentsPackage.Literals.OPPONENT_MODEL__CHARACTER_TYPES, Types._OpponentModel, 0);
		public static final ExecutorProperty _OpponentModel__maxThreat = new EcoreExecutorProperty(OpponentsPackage.Literals.OPPONENT_MODEL__MAX_THREAT, Types._OpponentModel, 1);
		public static final ExecutorProperty _OpponentModel__name = new EcoreExecutorProperty(OpponentsPackage.Literals.OPPONENT_MODEL__NAME, Types._OpponentModel, 2);
		public static final ExecutorProperty _OpponentModel__selectedDifficulty = new EcoreExecutorProperty(OpponentsPackage.Literals.OPPONENT_MODEL__SELECTED_DIFFICULTY, Types._OpponentModel, 3);

		public static final ExecutorProperty _Zombie__attackDamage = new EcoreExecutorProperty(OpponentsPackage.Literals.ZOMBIE__ATTACK_DAMAGE, Types._Zombie, 0);
		public static final ExecutorProperty _Zombie__infectionLevel = new EcoreExecutorProperty(OpponentsPackage.Literals.ZOMBIE__INFECTION_LEVEL, Types._Zombie, 1);
		public static final ExecutorProperty _Zombie__resurrectionTime = new EcoreExecutorProperty(OpponentsPackage.Literals.ZOMBIE__RESURRECTION_TIME, Types._Zombie, 2);
		public static final ExecutorProperty _Zombie__touchSound = new EcoreExecutorProperty(OpponentsPackage.Literals.ZOMBIE__TOUCH_SOUND, Types._Zombie, 3);
		public static final ExecutorProperty _Zombie__zombieLootTable = new EcoreExecutorProperty(OpponentsPackage.Literals.ZOMBIE__ZOMBIE_LOOT_TABLE, Types._Zombie, 4);
		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::Properties and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The fragments for all base types in depth order: OclAny first, OclSelf last.
	 */
	public static class TypeFragments {
		static {
			Init.initStart();
			Properties.init();
		}

		private static final ExecutorFragment /*@NonNull*/ [] _BehaviorType =
			{
				Fragments._BehaviorType__OclAny /* 0 */,
				Fragments._BehaviorType__OclElement /* 1 */,
				Fragments._BehaviorType__OclType /* 2 */,
				Fragments._BehaviorType__OclEnumeration /* 3 */,
				Fragments._BehaviorType__BehaviorType /* 4 */
			};
		private static final int /*@NonNull*/ [] __BehaviorType = { 1,1,1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _CharacterType =
			{
				Fragments._CharacterType__OclAny /* 0 */,
				Fragments._CharacterType__OclElement /* 1 */,
				Fragments._CharacterType__CharacterType /* 2 */
			};
		private static final int /*@NonNull*/ [] __CharacterType = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _Ghost =
			{
				Fragments._Ghost__OclAny /* 0 */,
				Fragments._Ghost__OclElement /* 1 */,
				Fragments._Ghost__CharacterType /* 2 */,
				Fragments._Ghost__Ghost /* 3 */
			};
		private static final int /*@NonNull*/ [] __Ghost = { 1,1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _LootItem =
			{
				Fragments._LootItem__OclAny /* 0 */,
				Fragments._LootItem__OclElement /* 1 */,
				Fragments._LootItem__LootItem /* 2 */
			};
		private static final int /*@NonNull*/ [] __LootItem = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _LootItemType =
			{
				Fragments._LootItemType__OclAny /* 0 */,
				Fragments._LootItemType__OclElement /* 1 */,
				Fragments._LootItemType__OclType /* 2 */,
				Fragments._LootItemType__OclEnumeration /* 3 */,
				Fragments._LootItemType__LootItemType /* 4 */
			};
		private static final int /*@NonNull*/ [] __LootItemType = { 1,1,1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _LootTable =
			{
				Fragments._LootTable__OclAny /* 0 */,
				Fragments._LootTable__OclElement /* 1 */,
				Fragments._LootTable__LootTable /* 2 */
			};
		private static final int /*@NonNull*/ [] __LootTable = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _OpponentModel =
			{
				Fragments._OpponentModel__OclAny /* 0 */,
				Fragments._OpponentModel__OclElement /* 1 */,
				Fragments._OpponentModel__OpponentModel /* 2 */
			};
		private static final int /*@NonNull*/ [] __OpponentModel = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _Zombie =
			{
				Fragments._Zombie__OclAny /* 0 */,
				Fragments._Zombie__OclElement /* 1 */,
				Fragments._Zombie__CharacterType /* 2 */,
				Fragments._Zombie__Zombie /* 3 */
			};
		private static final int /*@NonNull*/ [] __Zombie = { 1,1,1,1 };

		/**
		 *	Install the fragment descriptors in the class descriptors.
		 */
		static {
			Types._BehaviorType.initFragments(_BehaviorType, __BehaviorType);
			Types._CharacterType.initFragments(_CharacterType, __CharacterType);
			Types._Ghost.initFragments(_Ghost, __Ghost);
			Types._LootItem.initFragments(_LootItem, __LootItem);
			Types._LootItemType.initFragments(_LootItemType, __LootItemType);
			Types._LootTable.initFragments(_LootTable, __LootTable);
			Types._OpponentModel.initFragments(_OpponentModel, __OpponentModel);
			Types._Zombie.initFragments(_Zombie, __Zombie);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::TypeFragments and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The lists of local operations or local operation overrides for each fragment of each type.
	 */
	public static class FragmentOperations {
		static {
			Init.initStart();
			TypeFragments.init();
		}

		private static final ExecutorOperation /*@NonNull*/ [] _BehaviorType__BehaviorType = {};
		private static final ExecutorOperation /*@NonNull*/ [] _BehaviorType__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _BehaviorType__OclElement = {
			OCLstdlibTables.Operations._OclEnumeration__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _BehaviorType__OclEnumeration = {
			OCLstdlibTables.Operations._OclEnumeration__allInstances /* allInstances(Integer[1]) */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _BehaviorType__OclType = {
			OCLstdlibTables.Operations._OclType__conformsTo /* conformsTo(OclType[?]) */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _CharacterType__CharacterType = {};
		private static final ExecutorOperation /*@NonNull*/ [] _CharacterType__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _CharacterType__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _Ghost__Ghost = {};
		private static final ExecutorOperation /*@NonNull*/ [] _Ghost__CharacterType = {};
		private static final ExecutorOperation /*@NonNull*/ [] _Ghost__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _Ghost__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _LootItem__LootItem = {};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItem__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItem__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _LootItemType__LootItemType = {};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItemType__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItemType__OclElement = {
			OCLstdlibTables.Operations._OclEnumeration__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItemType__OclEnumeration = {
			OCLstdlibTables.Operations._OclEnumeration__allInstances /* allInstances(Integer[1]) */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _LootItemType__OclType = {
			OCLstdlibTables.Operations._OclType__conformsTo /* conformsTo(OclType[?]) */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _LootTable__LootTable = {};
		private static final ExecutorOperation /*@NonNull*/ [] _LootTable__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _LootTable__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _OpponentModel__OpponentModel = {};
		private static final ExecutorOperation /*@NonNull*/ [] _OpponentModel__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _OpponentModel__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		private static final ExecutorOperation /*@NonNull*/ [] _Zombie__Zombie = {};
		private static final ExecutorOperation /*@NonNull*/ [] _Zombie__CharacterType = {};
		private static final ExecutorOperation /*@NonNull*/ [] _Zombie__OclAny = {
			OCLstdlibTables.Operations._OclAny___lt__gt_ /* _'<>'(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny___eq_ /* _'='(OclSelf[?]) */,
			OCLstdlibTables.Operations._OclAny__oclAsSet /* oclAsSet() */,
			OCLstdlibTables.Operations._OclAny__oclAsType /* oclAsType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInState /* oclIsInState(OclState[?]) */,
			OCLstdlibTables.Operations._OclAny__oclIsInvalid /* oclIsInvalid() */,
			OCLstdlibTables.Operations._OclAny__oclIsKindOf /* oclIsKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsNew /* oclIsNew() */,
			OCLstdlibTables.Operations._OclAny__oclIsTypeOf /* oclIsTypeOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclAny__oclIsUndefined /* oclIsUndefined() */,
			OCLstdlibTables.Operations._OclAny__0_oclLog /* oclLog() */,
			OCLstdlibTables.Operations._OclAny__1_oclLog /* oclLog(String[1]) */,
			OCLstdlibTables.Operations._OclAny__oclType /* oclType() */,
			OCLstdlibTables.Operations._OclAny__oclTypes /* oclTypes() */,
			OCLstdlibTables.Operations._OclAny__toString /* toString() */
		};
		private static final ExecutorOperation /*@NonNull*/ [] _Zombie__OclElement = {
			OCLstdlibTables.Operations._OclElement__allInstances /* allInstances(Integer[1]) */,
			OCLstdlibTables.Operations._OclElement__oclAsModelType /* oclAsModelType(TT)(TT[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclBase /* oclBase() */,
			OCLstdlibTables.Operations._OclElement__1_oclBase /* oclBase(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclContainer /* oclContainer() */,
			OCLstdlibTables.Operations._OclElement__oclContents /* oclContents() */,
			OCLstdlibTables.Operations._OclElement__oclExtension /* oclExtension(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__0_oclExtensions /* oclExtensions() */,
			OCLstdlibTables.Operations._OclElement__1_oclExtensions /* oclExtensions(OclStereotype[1]) */,
			OCLstdlibTables.Operations._OclElement__oclIsModelKindOf /* oclIsModelKindOf(OclType[1]) */,
			OCLstdlibTables.Operations._OclElement__oclModelType /* oclModelType() */,
			OCLstdlibTables.Operations._OclElement__oclModelTypes /* oclModelTypes() */
		};

		/*
		 *	Install the operation descriptors in the fragment descriptors.
		 */
		static {
			Fragments._BehaviorType__BehaviorType.initOperations(_BehaviorType__BehaviorType);
			Fragments._BehaviorType__OclAny.initOperations(_BehaviorType__OclAny);
			Fragments._BehaviorType__OclElement.initOperations(_BehaviorType__OclElement);
			Fragments._BehaviorType__OclEnumeration.initOperations(_BehaviorType__OclEnumeration);
			Fragments._BehaviorType__OclType.initOperations(_BehaviorType__OclType);

			Fragments._CharacterType__CharacterType.initOperations(_CharacterType__CharacterType);
			Fragments._CharacterType__OclAny.initOperations(_CharacterType__OclAny);
			Fragments._CharacterType__OclElement.initOperations(_CharacterType__OclElement);

			Fragments._Ghost__CharacterType.initOperations(_Ghost__CharacterType);
			Fragments._Ghost__Ghost.initOperations(_Ghost__Ghost);
			Fragments._Ghost__OclAny.initOperations(_Ghost__OclAny);
			Fragments._Ghost__OclElement.initOperations(_Ghost__OclElement);

			Fragments._LootItem__LootItem.initOperations(_LootItem__LootItem);
			Fragments._LootItem__OclAny.initOperations(_LootItem__OclAny);
			Fragments._LootItem__OclElement.initOperations(_LootItem__OclElement);

			Fragments._LootItemType__LootItemType.initOperations(_LootItemType__LootItemType);
			Fragments._LootItemType__OclAny.initOperations(_LootItemType__OclAny);
			Fragments._LootItemType__OclElement.initOperations(_LootItemType__OclElement);
			Fragments._LootItemType__OclEnumeration.initOperations(_LootItemType__OclEnumeration);
			Fragments._LootItemType__OclType.initOperations(_LootItemType__OclType);

			Fragments._LootTable__LootTable.initOperations(_LootTable__LootTable);
			Fragments._LootTable__OclAny.initOperations(_LootTable__OclAny);
			Fragments._LootTable__OclElement.initOperations(_LootTable__OclElement);

			Fragments._OpponentModel__OclAny.initOperations(_OpponentModel__OclAny);
			Fragments._OpponentModel__OclElement.initOperations(_OpponentModel__OclElement);
			Fragments._OpponentModel__OpponentModel.initOperations(_OpponentModel__OpponentModel);

			Fragments._Zombie__CharacterType.initOperations(_Zombie__CharacterType);
			Fragments._Zombie__OclAny.initOperations(_Zombie__OclAny);
			Fragments._Zombie__OclElement.initOperations(_Zombie__OclElement);
			Fragments._Zombie__Zombie.initOperations(_Zombie__Zombie);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::FragmentOperations and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The lists of local properties for the local fragment of each type.
	 */
	public static class FragmentProperties {
		static {
			Init.initStart();
			FragmentOperations.init();
		}

		private static final ExecutorProperty /*@NonNull*/ [] _BehaviorType = {
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _CharacterType = {
			OpponentsTables.Properties._CharacterType__ImageBase,
			OpponentsTables.Properties._CharacterType__ImageTurnDown,
			OpponentsTables.Properties._CharacterType__ImageTurnLeft,
			OpponentsTables.Properties._CharacterType__ImageTurnRight,
			OpponentsTables.Properties._CharacterType__ImageTurnUp,
			OpponentsTables.Properties._CharacterType__behavior,
			OpponentsTables.Properties._CharacterType__displayName,
			OpponentsTables.Properties._CharacterType__effectiveThreat,
			OpponentsTables.Properties._CharacterType__enabled,
			OpponentsTables.Properties._CharacterType__health,
			OpponentsTables.Properties._CharacterType__id,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._CharacterType__speed,
			OpponentsTables.Properties._CharacterType__threatLevel
		};

		private static final ExecutorProperty /*@NonNull*/ [] _Ghost = {
			OpponentsTables.Properties._CharacterType__ImageBase,
			OpponentsTables.Properties._CharacterType__ImageTurnDown,
			OpponentsTables.Properties._CharacterType__ImageTurnLeft,
			OpponentsTables.Properties._CharacterType__ImageTurnRight,
			OpponentsTables.Properties._CharacterType__ImageTurnUp,
			OpponentsTables.Properties._Ghost__attackDamage,
			OpponentsTables.Properties._CharacterType__behavior,
			OpponentsTables.Properties._CharacterType__displayName,
			OpponentsTables.Properties._CharacterType__effectiveThreat,
			OpponentsTables.Properties._CharacterType__enabled,
			OpponentsTables.Properties._CharacterType__health,
			OpponentsTables.Properties._CharacterType__id,
			OpponentsTables.Properties._Ghost__nonTangibilityEnergy,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._CharacterType__speed,
			OpponentsTables.Properties._CharacterType__threatLevel,
			OpponentsTables.Properties._Ghost__visibilityLevel
		};

		private static final ExecutorProperty /*@NonNull*/ [] _LootItem = {
			OpponentsTables.Properties._LootItem__graphicBase,
			OpponentsTables.Properties._LootItem__name,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._LootItem__type,
			OpponentsTables.Properties._LootItem__value,
			OpponentsTables.Properties._LootItem__weight
		};

		private static final ExecutorProperty /*@NonNull*/ [] _LootItemType = {
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _LootTable = {
			OpponentsTables.Properties._LootTable__items,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._LootTable__weightCapacity
		};

		private static final ExecutorProperty /*@NonNull*/ [] _OpponentModel = {
			OpponentsTables.Properties._OpponentModel__characterTypes,
			OpponentsTables.Properties._OpponentModel__maxThreat,
			OpponentsTables.Properties._OpponentModel__name,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._OpponentModel__selectedDifficulty
		};

		private static final ExecutorProperty /*@NonNull*/ [] _Zombie = {
			OpponentsTables.Properties._CharacterType__ImageBase,
			OpponentsTables.Properties._CharacterType__ImageTurnDown,
			OpponentsTables.Properties._CharacterType__ImageTurnLeft,
			OpponentsTables.Properties._CharacterType__ImageTurnRight,
			OpponentsTables.Properties._CharacterType__ImageTurnUp,
			OpponentsTables.Properties._Zombie__attackDamage,
			OpponentsTables.Properties._CharacterType__behavior,
			OpponentsTables.Properties._CharacterType__displayName,
			OpponentsTables.Properties._CharacterType__effectiveThreat,
			OpponentsTables.Properties._CharacterType__enabled,
			OpponentsTables.Properties._CharacterType__health,
			OpponentsTables.Properties._CharacterType__id,
			OpponentsTables.Properties._Zombie__infectionLevel,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			OpponentsTables.Properties._Zombie__resurrectionTime,
			OpponentsTables.Properties._CharacterType__speed,
			OpponentsTables.Properties._CharacterType__threatLevel,
			OpponentsTables.Properties._Zombie__touchSound,
			OpponentsTables.Properties._Zombie__zombieLootTable
		};

		/**
		 *	Install the property descriptors in the fragment descriptors.
		 */
		static {
			Fragments._BehaviorType__BehaviorType.initProperties(_BehaviorType);
			Fragments._CharacterType__CharacterType.initProperties(_CharacterType);
			Fragments._Ghost__Ghost.initProperties(_Ghost);
			Fragments._LootItem__LootItem.initProperties(_LootItem);
			Fragments._LootItemType__LootItemType.initProperties(_LootItemType);
			Fragments._LootTable__LootTable.initProperties(_LootTable);
			Fragments._OpponentModel__OpponentModel.initProperties(_OpponentModel);
			Fragments._Zombie__Zombie.initProperties(_Zombie);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::FragmentProperties and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 *	The lists of enumeration literals for each enumeration.
	 */
	public static class EnumerationLiterals {
		static {
			Init.initStart();
			FragmentProperties.init();
		}

		public static final EcoreExecutorEnumerationLiteral _BehaviorType__PASSIVE = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.BEHAVIOR_TYPE.getEEnumLiteral("PASSIVE"), Types._BehaviorType, 0);
		public static final EcoreExecutorEnumerationLiteral _BehaviorType__WANDER = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.BEHAVIOR_TYPE.getEEnumLiteral("WANDER"), Types._BehaviorType, 1);
		public static final EcoreExecutorEnumerationLiteral _BehaviorType__AGGRESSIVE = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.BEHAVIOR_TYPE.getEEnumLiteral("AGGRESSIVE"), Types._BehaviorType, 2);
		private static final EcoreExecutorEnumerationLiteral /*@NonNull*/ [] _BehaviorType = {
			_BehaviorType__PASSIVE,
			_BehaviorType__WANDER,
			_BehaviorType__AGGRESSIVE
		};

		public static final EcoreExecutorEnumerationLiteral _LootItemType__FOOD = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.LOOT_ITEM_TYPE.getEEnumLiteral("FOOD"), Types._LootItemType, 0);
		public static final EcoreExecutorEnumerationLiteral _LootItemType__BOMB = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.LOOT_ITEM_TYPE.getEEnumLiteral("BOMB"), Types._LootItemType, 1);
		public static final EcoreExecutorEnumerationLiteral _LootItemType__TRAP = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.LOOT_ITEM_TYPE.getEEnumLiteral("TRAP"), Types._LootItemType, 2);
		public static final EcoreExecutorEnumerationLiteral _LootItemType__WEAPON = new EcoreExecutorEnumerationLiteral(OpponentsPackage.Literals.LOOT_ITEM_TYPE.getEEnumLiteral("WEAPON"), Types._LootItemType, 3);
		private static final EcoreExecutorEnumerationLiteral /*@NonNull*/ [] _LootItemType = {
			_LootItemType__FOOD,
			_LootItemType__BOMB,
			_LootItemType__TRAP,
			_LootItemType__WEAPON
		};

		/**
		 *	Install the enumeration literals in the enumerations.
		 */
		static {
			Types._BehaviorType.initLiterals(_BehaviorType);
			Types._LootItemType.initLiterals(_LootItemType);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of OpponentsTables::EnumerationLiterals and all preceding sub-packages.
		 */
		public static void init() {}
	}

	/**
	 * The multiple packages above avoid problems with the Java 65536 byte limit but introduce a difficulty in ensuring that
	 * static construction occurs in the disciplined order of the packages when construction may start in any of the packages.
	 * The problem is resolved by ensuring that the static construction of each package first initializes its immediate predecessor.
	 * On completion of predecessor initialization, the residual packages are initialized by starting an initialization in the last package.
	 * This class maintains a count so that the various predecessors can distinguish whether they are the starting point and so
	 * ensure that residual construction occurs just once after all predecessors.
	 */
	private static class Init {
		/**
		 * Counter of nested static constructions. On return to zero residual construction starts. -ve once residual construction started.
		 */
		private static int initCount = 0;

		/**
		 * Invoked at the start of a static construction to defer residual construction until primary constructions complete.
		 */
		private static void initStart() {
			if (initCount >= 0) {
				initCount++;
			}
		}

		/**
		 * Invoked at the end of a static construction to activate residual construction once primary constructions complete.
		 */
		private static void initEnd() {
			if (initCount > 0) {
				if (--initCount == 0) {
					initCount = -1;
					EnumerationLiterals.init();
				}
			}
		}
	}

	static {
		Init.initEnd();
	}

	/*
	 * Force initialization of outer fields. Inner fields are lazily initialized.
	 */
	public static void init() {
		new OpponentsTables();
	}

	private OpponentsTables() {
		super(OpponentsPackage.eNS_URI);
	}
}
