/*******************************************************************************
 *************************************************************************
 * This code is 100% auto-generated
 * from:
 *   /opponents-module/src/main/resources/opponents/difficulty-module.ecore
 *   /opponents-module/src/main/resources/opponents/opponents.ecore
 * using:
 *   /opponents-module/src/main/resources/opponents/opponents.genmodel
 *   org.eclipse.ocl.examples.codegen.oclinecore.OCLinEcoreTables
 *
 * Do not edit it.
 *******************************************************************************/
package main.game.maze.difficulties;

// import main.game.maze.difficulties.DifficultiesPackage;
// import main.game.maze.difficulties.DifficultiesTables;
import main.game.maze.opponents.OpponentsPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.pivot.ids.ClassId;
import org.eclipse.ocl.pivot.ids.CollectionTypeId;
import org.eclipse.ocl.pivot.ids.DataTypeId;
import org.eclipse.ocl.pivot.ids.EnumerationId;
import org.eclipse.ocl.pivot.ids.IdManager;
import org.eclipse.ocl.pivot.ids.NsURIPackageId;
import org.eclipse.ocl.pivot.ids.TypeId;
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

/**
 * DifficultiesTables provides the dispatch tables for the difficulties for use by the OCL dispatcher.
 *
 * In order to ensure correct static initialization, a top level class element must be accessed
 * before any nested class element. Therefore an access to PACKAGE.getClass() is recommended.
 */
public class DifficultiesTables extends AbstractTables
{
	static {
		Init.initStart();
	}

	/**
	 *	The package descriptor for the package.
	 */
	public static final EcoreExecutorPackage PACKAGE = new EcoreExecutorPackage(DifficultiesPackage.eINSTANCE);

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
	public static final /*@NonInvalid*/ ClassId CLSSid_Difficulty = DifficultiesTables.PACKid_http_c_s_s_main_game_maze_s_difficulty.getClassId("Difficulty", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_DifficultyGameData = DifficultiesTables.PACKid_http_c_s_s_main_game_maze_s_difficulty.getClassId("DifficultyGameData", 0);
	public static final /*@NonInvalid*/ ClassId CLSSid_EnemyMaxCount = DifficultiesTables.PACKid_http_c_s_s_main_game_maze_s_difficulty.getClassId("EnemyMaxCount", 0);
	public static final /*@NonInvalid*/ DataTypeId DATAid_EDouble = DifficultiesTables.PACKid_http_c_s_s_www_eclipse_org_s_emf_s_2002_s_Ecore.getDataTypeId("EDouble", 0);
	public static final /*@NonInvalid*/ DataTypeId DATAid_EInt = DifficultiesTables.PACKid_http_c_s_s_www_eclipse_org_s_emf_s_2002_s_Ecore.getDataTypeId("EInt", 0);
	public static final /*@NonInvalid*/ EnumerationId ENUMid_EnemyTypes = DifficultiesTables.PACKid_http_c_s_s_main_game_maze_s_opponents.getEnumerationId("EnemyTypes");
	public static final /*@NonInvalid*/ CollectionTypeId BAG_CLSSid_Difficulty = TypeId.BAG.getSpecializedId(DifficultiesTables.CLSSid_Difficulty, false, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId BAG_CLSSid_DifficultyGameData = TypeId.BAG.getSpecializedId(DifficultiesTables.CLSSid_DifficultyGameData, false, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId ORD_CLSSid_Difficulty = TypeId.ORDERED_SET.getSpecializedId(DifficultiesTables.CLSSid_Difficulty, true, ValueUtil.ONE_VALUE, ValueUtil.UNLIMITED_VALUE);
	public static final /*@NonInvalid*/ CollectionTypeId SET_CLSSid_EnemyMaxCount = TypeId.SET.getSpecializedId(DifficultiesTables.CLSSid_EnemyMaxCount, true, ValueUtil.ZERO_VALUE, ValueUtil.UNLIMITED_VALUE);

	/**
	 *	The type parameters for templated types and operations.
	 */
	public static class TypeParameters {
		static {
			Init.initStart();
			DifficultiesTables.init();
		}

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::TypeParameters and all preceding sub-packages.
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

		public static final EcoreExecutorType _Difficulty = new EcoreExecutorType(DifficultiesPackage.Literals.DIFFICULTY, PACKAGE, 0 | ExecutorType.ABSTRACT);
		public static final EcoreExecutorType _DifficultyGameData = new EcoreExecutorType(DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA, PACKAGE, 0);
		public static final EcoreExecutorType _EasyDifficulty = new EcoreExecutorType(DifficultiesPackage.Literals.EASY_DIFFICULTY, PACKAGE, 0);
		public static final EcoreExecutorType _EnemyMaxCount = new EcoreExecutorType(DifficultiesPackage.Literals.ENEMY_MAX_COUNT, PACKAGE, 0);
		public static final EcoreExecutorType _HardDifficulty = new EcoreExecutorType(DifficultiesPackage.Literals.HARD_DIFFICULTY, PACKAGE, 0);
		public static final EcoreExecutorType _NormalDifficulty = new EcoreExecutorType(DifficultiesPackage.Literals.NORMAL_DIFFICULTY, PACKAGE, 0);

		private static final EcoreExecutorType /*@NonNull*/ [] types = {
			_Difficulty,
			_DifficultyGameData,
			_EasyDifficulty,
			_EnemyMaxCount,
			_HardDifficulty,
			_NormalDifficulty
		};

		/*
		 *	Install the type descriptors in the package descriptor.
		 */
		static {
			PACKAGE.init(LIBRARY, types);
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::Types and all preceding sub-packages.
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

		private static final ExecutorFragment _Difficulty__Difficulty = new ExecutorFragment(Types._Difficulty, DifficultiesTables.Types._Difficulty);
		private static final ExecutorFragment _Difficulty__OclAny = new ExecutorFragment(Types._Difficulty, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _Difficulty__OclElement = new ExecutorFragment(Types._Difficulty, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _DifficultyGameData__DifficultyGameData = new ExecutorFragment(Types._DifficultyGameData, DifficultiesTables.Types._DifficultyGameData);
		private static final ExecutorFragment _DifficultyGameData__OclAny = new ExecutorFragment(Types._DifficultyGameData, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _DifficultyGameData__OclElement = new ExecutorFragment(Types._DifficultyGameData, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _EasyDifficulty__Difficulty = new ExecutorFragment(Types._EasyDifficulty, DifficultiesTables.Types._Difficulty);
		private static final ExecutorFragment _EasyDifficulty__EasyDifficulty = new ExecutorFragment(Types._EasyDifficulty, DifficultiesTables.Types._EasyDifficulty);
		private static final ExecutorFragment _EasyDifficulty__OclAny = new ExecutorFragment(Types._EasyDifficulty, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _EasyDifficulty__OclElement = new ExecutorFragment(Types._EasyDifficulty, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _EnemyMaxCount__EnemyMaxCount = new ExecutorFragment(Types._EnemyMaxCount, DifficultiesTables.Types._EnemyMaxCount);
		private static final ExecutorFragment _EnemyMaxCount__OclAny = new ExecutorFragment(Types._EnemyMaxCount, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _EnemyMaxCount__OclElement = new ExecutorFragment(Types._EnemyMaxCount, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _HardDifficulty__Difficulty = new ExecutorFragment(Types._HardDifficulty, DifficultiesTables.Types._Difficulty);
		private static final ExecutorFragment _HardDifficulty__HardDifficulty = new ExecutorFragment(Types._HardDifficulty, DifficultiesTables.Types._HardDifficulty);
		private static final ExecutorFragment _HardDifficulty__OclAny = new ExecutorFragment(Types._HardDifficulty, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _HardDifficulty__OclElement = new ExecutorFragment(Types._HardDifficulty, OCLstdlibTables.Types._OclElement);

		private static final ExecutorFragment _NormalDifficulty__Difficulty = new ExecutorFragment(Types._NormalDifficulty, DifficultiesTables.Types._Difficulty);
		private static final ExecutorFragment _NormalDifficulty__NormalDifficulty = new ExecutorFragment(Types._NormalDifficulty, DifficultiesTables.Types._NormalDifficulty);
		private static final ExecutorFragment _NormalDifficulty__OclAny = new ExecutorFragment(Types._NormalDifficulty, OCLstdlibTables.Types._OclAny);
		private static final ExecutorFragment _NormalDifficulty__OclElement = new ExecutorFragment(Types._NormalDifficulty, OCLstdlibTables.Types._OclElement);

		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::Fragments and all preceding sub-packages.
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
		 * Force initialization of the fields of DifficultiesTables::Parameters and all preceding sub-packages.
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
		 * Force initialization of the fields of DifficultiesTables::Operations and all preceding sub-packages.
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

		public static final ExecutorProperty _Difficulty__enemyMaxCount = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY__ENEMY_MAX_COUNT, Types._Difficulty, 0);
		public static final ExecutorProperty _Difficulty__instantDeath = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY__INSTANT_DEATH, Types._Difficulty, 1);
		public static final ExecutorProperty _Difficulty__maxThreat = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY__MAX_THREAT, Types._Difficulty, 2);
		public static final ExecutorProperty _Difficulty__monstersDamageMultiplier = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER, Types._Difficulty, 3);
		public static final ExecutorProperty _Difficulty__monstersMovementSpeedMultiplier = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER, Types._Difficulty, 4);
		public static final ExecutorProperty _Difficulty__DifficultyGameData__currentDifficulty = new ExecutorPropertyWithImplementation("DifficultyGameData", Types._Difficulty, 5, new EcoreLibraryOppositeProperty(DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY));
		public static final ExecutorProperty _Difficulty__DifficultyGameData__difficulties = new ExecutorPropertyWithImplementation("DifficultyGameData", Types._Difficulty, 6, new EcoreLibraryOppositeProperty(DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA__DIFFICULTIES));

		public static final ExecutorProperty _DifficultyGameData__currentDifficulty = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY, Types._DifficultyGameData, 0);
		public static final ExecutorProperty _DifficultyGameData__difficulties = new EcoreExecutorProperty(DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA__DIFFICULTIES, Types._DifficultyGameData, 1);
		public static final ExecutorProperty _DifficultyGameData__OpponentModel__selectedDifficulty = new ExecutorPropertyWithImplementation("OpponentModel", Types._DifficultyGameData, 2, new EcoreLibraryOppositeProperty(OpponentsPackage.Literals.OPPONENT_MODEL__SELECTED_DIFFICULTY));

		public static final ExecutorProperty _EnemyMaxCount__maxCount = new EcoreExecutorProperty(DifficultiesPackage.Literals.ENEMY_MAX_COUNT__MAX_COUNT, Types._EnemyMaxCount, 0);
		public static final ExecutorProperty _EnemyMaxCount__type = new EcoreExecutorProperty(DifficultiesPackage.Literals.ENEMY_MAX_COUNT__TYPE, Types._EnemyMaxCount, 1);
		public static final ExecutorProperty _EnemyMaxCount__Difficulty__enemyMaxCount = new ExecutorPropertyWithImplementation("Difficulty", Types._EnemyMaxCount, 2, new EcoreLibraryOppositeProperty(DifficultiesPackage.Literals.DIFFICULTY__ENEMY_MAX_COUNT));
		static {
			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::Properties and all preceding sub-packages.
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

		private static final ExecutorFragment /*@NonNull*/ [] _Difficulty =
			{
				Fragments._Difficulty__OclAny /* 0 */,
				Fragments._Difficulty__OclElement /* 1 */,
				Fragments._Difficulty__Difficulty /* 2 */
			};
		private static final int /*@NonNull*/ [] __Difficulty = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _DifficultyGameData =
			{
				Fragments._DifficultyGameData__OclAny /* 0 */,
				Fragments._DifficultyGameData__OclElement /* 1 */,
				Fragments._DifficultyGameData__DifficultyGameData /* 2 */
			};
		private static final int /*@NonNull*/ [] __DifficultyGameData = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _EasyDifficulty =
			{
				Fragments._EasyDifficulty__OclAny /* 0 */,
				Fragments._EasyDifficulty__OclElement /* 1 */,
				Fragments._EasyDifficulty__Difficulty /* 2 */,
				Fragments._EasyDifficulty__EasyDifficulty /* 3 */
			};
		private static final int /*@NonNull*/ [] __EasyDifficulty = { 1,1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _EnemyMaxCount =
			{
				Fragments._EnemyMaxCount__OclAny /* 0 */,
				Fragments._EnemyMaxCount__OclElement /* 1 */,
				Fragments._EnemyMaxCount__EnemyMaxCount /* 2 */
			};
		private static final int /*@NonNull*/ [] __EnemyMaxCount = { 1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _HardDifficulty =
			{
				Fragments._HardDifficulty__OclAny /* 0 */,
				Fragments._HardDifficulty__OclElement /* 1 */,
				Fragments._HardDifficulty__Difficulty /* 2 */,
				Fragments._HardDifficulty__HardDifficulty /* 3 */
			};
		private static final int /*@NonNull*/ [] __HardDifficulty = { 1,1,1,1 };

		private static final ExecutorFragment /*@NonNull*/ [] _NormalDifficulty =
			{
				Fragments._NormalDifficulty__OclAny /* 0 */,
				Fragments._NormalDifficulty__OclElement /* 1 */,
				Fragments._NormalDifficulty__Difficulty /* 2 */,
				Fragments._NormalDifficulty__NormalDifficulty /* 3 */
			};
		private static final int /*@NonNull*/ [] __NormalDifficulty = { 1,1,1,1 };

		/**
		 *	Install the fragment descriptors in the class descriptors.
		 */
		static {
			Types._Difficulty.initFragments(_Difficulty, __Difficulty);
			Types._DifficultyGameData.initFragments(_DifficultyGameData, __DifficultyGameData);
			Types._EasyDifficulty.initFragments(_EasyDifficulty, __EasyDifficulty);
			Types._EnemyMaxCount.initFragments(_EnemyMaxCount, __EnemyMaxCount);
			Types._HardDifficulty.initFragments(_HardDifficulty, __HardDifficulty);
			Types._NormalDifficulty.initFragments(_NormalDifficulty, __NormalDifficulty);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::TypeFragments and all preceding sub-packages.
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

		private static final ExecutorOperation /*@NonNull*/ [] _Difficulty__Difficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _Difficulty__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _Difficulty__OclElement = {
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

		private static final ExecutorOperation /*@NonNull*/ [] _DifficultyGameData__DifficultyGameData = {};
		private static final ExecutorOperation /*@NonNull*/ [] _DifficultyGameData__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _DifficultyGameData__OclElement = {
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

		private static final ExecutorOperation /*@NonNull*/ [] _EasyDifficulty__EasyDifficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _EasyDifficulty__Difficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _EasyDifficulty__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _EasyDifficulty__OclElement = {
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

		private static final ExecutorOperation /*@NonNull*/ [] _EnemyMaxCount__EnemyMaxCount = {};
		private static final ExecutorOperation /*@NonNull*/ [] _EnemyMaxCount__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _EnemyMaxCount__OclElement = {
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

		private static final ExecutorOperation /*@NonNull*/ [] _HardDifficulty__HardDifficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _HardDifficulty__Difficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _HardDifficulty__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _HardDifficulty__OclElement = {
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

		private static final ExecutorOperation /*@NonNull*/ [] _NormalDifficulty__NormalDifficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _NormalDifficulty__Difficulty = {};
		private static final ExecutorOperation /*@NonNull*/ [] _NormalDifficulty__OclAny = {
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
		private static final ExecutorOperation /*@NonNull*/ [] _NormalDifficulty__OclElement = {
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
			Fragments._Difficulty__Difficulty.initOperations(_Difficulty__Difficulty);
			Fragments._Difficulty__OclAny.initOperations(_Difficulty__OclAny);
			Fragments._Difficulty__OclElement.initOperations(_Difficulty__OclElement);

			Fragments._DifficultyGameData__DifficultyGameData.initOperations(_DifficultyGameData__DifficultyGameData);
			Fragments._DifficultyGameData__OclAny.initOperations(_DifficultyGameData__OclAny);
			Fragments._DifficultyGameData__OclElement.initOperations(_DifficultyGameData__OclElement);

			Fragments._EasyDifficulty__Difficulty.initOperations(_EasyDifficulty__Difficulty);
			Fragments._EasyDifficulty__EasyDifficulty.initOperations(_EasyDifficulty__EasyDifficulty);
			Fragments._EasyDifficulty__OclAny.initOperations(_EasyDifficulty__OclAny);
			Fragments._EasyDifficulty__OclElement.initOperations(_EasyDifficulty__OclElement);

			Fragments._EnemyMaxCount__EnemyMaxCount.initOperations(_EnemyMaxCount__EnemyMaxCount);
			Fragments._EnemyMaxCount__OclAny.initOperations(_EnemyMaxCount__OclAny);
			Fragments._EnemyMaxCount__OclElement.initOperations(_EnemyMaxCount__OclElement);

			Fragments._HardDifficulty__Difficulty.initOperations(_HardDifficulty__Difficulty);
			Fragments._HardDifficulty__HardDifficulty.initOperations(_HardDifficulty__HardDifficulty);
			Fragments._HardDifficulty__OclAny.initOperations(_HardDifficulty__OclAny);
			Fragments._HardDifficulty__OclElement.initOperations(_HardDifficulty__OclElement);

			Fragments._NormalDifficulty__Difficulty.initOperations(_NormalDifficulty__Difficulty);
			Fragments._NormalDifficulty__NormalDifficulty.initOperations(_NormalDifficulty__NormalDifficulty);
			Fragments._NormalDifficulty__OclAny.initOperations(_NormalDifficulty__OclAny);
			Fragments._NormalDifficulty__OclElement.initOperations(_NormalDifficulty__OclElement);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::FragmentOperations and all preceding sub-packages.
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

		private static final ExecutorProperty /*@NonNull*/ [] _Difficulty = {
			DifficultiesTables.Properties._Difficulty__enemyMaxCount,
			DifficultiesTables.Properties._Difficulty__instantDeath,
			DifficultiesTables.Properties._Difficulty__maxThreat,
			DifficultiesTables.Properties._Difficulty__monstersDamageMultiplier,
			DifficultiesTables.Properties._Difficulty__monstersMovementSpeedMultiplier,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _DifficultyGameData = {
			DifficultiesTables.Properties._DifficultyGameData__currentDifficulty,
			DifficultiesTables.Properties._DifficultyGameData__difficulties,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _EasyDifficulty = {
			DifficultiesTables.Properties._Difficulty__enemyMaxCount,
			DifficultiesTables.Properties._Difficulty__instantDeath,
			DifficultiesTables.Properties._Difficulty__maxThreat,
			DifficultiesTables.Properties._Difficulty__monstersDamageMultiplier,
			DifficultiesTables.Properties._Difficulty__monstersMovementSpeedMultiplier,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _EnemyMaxCount = {
			DifficultiesTables.Properties._EnemyMaxCount__maxCount,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents,
			DifficultiesTables.Properties._EnemyMaxCount__type
		};

		private static final ExecutorProperty /*@NonNull*/ [] _HardDifficulty = {
			DifficultiesTables.Properties._Difficulty__enemyMaxCount,
			DifficultiesTables.Properties._Difficulty__instantDeath,
			DifficultiesTables.Properties._Difficulty__maxThreat,
			DifficultiesTables.Properties._Difficulty__monstersDamageMultiplier,
			DifficultiesTables.Properties._Difficulty__monstersMovementSpeedMultiplier,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		private static final ExecutorProperty /*@NonNull*/ [] _NormalDifficulty = {
			DifficultiesTables.Properties._Difficulty__enemyMaxCount,
			DifficultiesTables.Properties._Difficulty__instantDeath,
			DifficultiesTables.Properties._Difficulty__maxThreat,
			DifficultiesTables.Properties._Difficulty__monstersDamageMultiplier,
			DifficultiesTables.Properties._Difficulty__monstersMovementSpeedMultiplier,
			OCLstdlibTables.Properties._OclElement__oclContainer,
			OCLstdlibTables.Properties._OclElement__oclContents
		};

		/**
		 *	Install the property descriptors in the fragment descriptors.
		 */
		static {
			Fragments._Difficulty__Difficulty.initProperties(_Difficulty);
			Fragments._DifficultyGameData__DifficultyGameData.initProperties(_DifficultyGameData);
			Fragments._EasyDifficulty__EasyDifficulty.initProperties(_EasyDifficulty);
			Fragments._EnemyMaxCount__EnemyMaxCount.initProperties(_EnemyMaxCount);
			Fragments._HardDifficulty__HardDifficulty.initProperties(_HardDifficulty);
			Fragments._NormalDifficulty__NormalDifficulty.initProperties(_NormalDifficulty);

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::FragmentProperties and all preceding sub-packages.
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

		/**
		 *	Install the enumeration literals in the enumerations.
		 */
		static {

			Init.initEnd();
		}

		/**
		 * Force initialization of the fields of DifficultiesTables::EnumerationLiterals and all preceding sub-packages.
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
		new DifficultiesTables();
	}

	private DifficultiesTables() {
		super(DifficultiesPackage.eNS_URI);
	}
}
