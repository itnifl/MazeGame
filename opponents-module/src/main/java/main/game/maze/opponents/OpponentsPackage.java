/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see main.game.maze.opponents.OpponentsFactory
 * @model kind="package"
 * @generated
 */
public interface OpponentsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "opponents";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://main.game.maze/opponents";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "opp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OpponentsPackage eINSTANCE = main.game.maze.opponents.impl.OpponentsPackageImpl.init();

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.OpponentModelImpl <em>Opponent Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.OpponentModelImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getOpponentModel()
	 * @generated
	 */
	int OPPONENT_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Character Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__CHARACTER_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Max Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL__MAX_THREAT = 2;

	/**
	 * The number of structural features of the '<em>Opponent Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Opponent Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPPONENT_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.CharacterTypeImpl <em>Character Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.CharacterTypeImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getCharacterType()
	 * @generated
	 */
	int CHARACTER_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__ID = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__DISPLAY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__ENABLED = 2;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__HEALTH = 3;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__SPEED = 4;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__THREAT_LEVEL = 5;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE__EFFECTIVE_THREAT = 6;

	/**
	 * The number of structural features of the '<em>Character Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Character Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTER_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.impl.ZombieImpl <em>Zombie</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.impl.ZombieImpl
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getZombie()
	 * @generated
	 */
	int ZOMBIE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ID = CHARACTER_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__DISPLAY_NAME = CHARACTER_TYPE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ENABLED = CHARACTER_TYPE__ENABLED;

	/**
	 * The feature id for the '<em><b>Health</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__HEALTH = CHARACTER_TYPE__HEALTH;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__SPEED = CHARACTER_TYPE__SPEED;

	/**
	 * The feature id for the '<em><b>Threat Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__THREAT_LEVEL = CHARACTER_TYPE__THREAT_LEVEL;

	/**
	 * The feature id for the '<em><b>Effective Threat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__EFFECTIVE_THREAT = CHARACTER_TYPE__EFFECTIVE_THREAT;

	/**
	 * The feature id for the '<em><b>Attack Damage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__ATTACK_DAMAGE = CHARACTER_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE__BEHAVIOR = CHARACTER_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Zombie</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE_FEATURE_COUNT = CHARACTER_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Zombie</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZOMBIE_OPERATION_COUNT = CHARACTER_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.opponents.BehaviorType
	 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getBehaviorType()
	 * @generated
	 */
	int BEHAVIOR_TYPE = 3;


	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.OpponentModel <em>Opponent Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Opponent Model</em>'.
	 * @see main.game.maze.opponents.OpponentModel
	 * @generated
	 */
	EClass getOpponentModel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.OpponentModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getName()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EAttribute getOpponentModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.opponents.OpponentModel#getCharacterTypes <em>Character Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Character Types</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getCharacterTypes()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EReference getOpponentModel_CharacterTypes();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.OpponentModel#getMaxThreat <em>Max Threat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Threat</em>'.
	 * @see main.game.maze.opponents.OpponentModel#getMaxThreat()
	 * @see #getOpponentModel()
	 * @generated
	 */
	EAttribute getOpponentModel_MaxThreat();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.CharacterType <em>Character Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Character Type</em>'.
	 * @see main.game.maze.opponents.CharacterType
	 * @generated
	 */
	EClass getCharacterType();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see main.game.maze.opponents.CharacterType#getId()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Id();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see main.game.maze.opponents.CharacterType#getDisplayName()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see main.game.maze.opponents.CharacterType#isEnabled()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getHealth <em>Health</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Health</em>'.
	 * @see main.game.maze.opponents.CharacterType#getHealth()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Health();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getSpeed <em>Speed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Speed</em>'.
	 * @see main.game.maze.opponents.CharacterType#getSpeed()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_Speed();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getThreatLevel <em>Threat Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threat Level</em>'.
	 * @see main.game.maze.opponents.CharacterType#getThreatLevel()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_ThreatLevel();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.CharacterType#getEffectiveThreat <em>Effective Threat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effective Threat</em>'.
	 * @see main.game.maze.opponents.CharacterType#getEffectiveThreat()
	 * @see #getCharacterType()
	 * @generated
	 */
	EAttribute getCharacterType_EffectiveThreat();

	/**
	 * Returns the meta object for class '{@link main.game.maze.opponents.Zombie <em>Zombie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Zombie</em>'.
	 * @see main.game.maze.opponents.Zombie
	 * @generated
	 */
	EClass getZombie();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getAttackDamage <em>Attack Damage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attack Damage</em>'.
	 * @see main.game.maze.opponents.Zombie#getAttackDamage()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_AttackDamage();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.opponents.Zombie#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Behavior</em>'.
	 * @see main.game.maze.opponents.Zombie#getBehavior()
	 * @see #getZombie()
	 * @generated
	 */
	EAttribute getZombie_Behavior();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Behavior Type</em>'.
	 * @see main.game.maze.opponents.BehaviorType
	 * @generated
	 */
	EEnum getBehaviorType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OpponentsFactory getOpponentsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.OpponentModelImpl <em>Opponent Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.OpponentModelImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getOpponentModel()
		 * @generated
		 */
		EClass OPPONENT_MODEL = eINSTANCE.getOpponentModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPPONENT_MODEL__NAME = eINSTANCE.getOpponentModel_Name();

		/**
		 * The meta object literal for the '<em><b>Character Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPPONENT_MODEL__CHARACTER_TYPES = eINSTANCE.getOpponentModel_CharacterTypes();

		/**
		 * The meta object literal for the '<em><b>Max Threat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPPONENT_MODEL__MAX_THREAT = eINSTANCE.getOpponentModel_MaxThreat();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.CharacterTypeImpl <em>Character Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.CharacterTypeImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getCharacterType()
		 * @generated
		 */
		EClass CHARACTER_TYPE = eINSTANCE.getCharacterType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__ID = eINSTANCE.getCharacterType_Id();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__DISPLAY_NAME = eINSTANCE.getCharacterType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__ENABLED = eINSTANCE.getCharacterType_Enabled();

		/**
		 * The meta object literal for the '<em><b>Health</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__HEALTH = eINSTANCE.getCharacterType_Health();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__SPEED = eINSTANCE.getCharacterType_Speed();

		/**
		 * The meta object literal for the '<em><b>Threat Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__THREAT_LEVEL = eINSTANCE.getCharacterType_ThreatLevel();

		/**
		 * The meta object literal for the '<em><b>Effective Threat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTER_TYPE__EFFECTIVE_THREAT = eINSTANCE.getCharacterType_EffectiveThreat();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.impl.ZombieImpl <em>Zombie</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.impl.ZombieImpl
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getZombie()
		 * @generated
		 */
		EClass ZOMBIE = eINSTANCE.getZombie();

		/**
		 * The meta object literal for the '<em><b>Attack Damage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__ATTACK_DAMAGE = eINSTANCE.getZombie_AttackDamage();

		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZOMBIE__BEHAVIOR = eINSTANCE.getZombie_Behavior();

		/**
		 * The meta object literal for the '{@link main.game.maze.opponents.BehaviorType <em>Behavior Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.opponents.BehaviorType
		 * @see main.game.maze.opponents.impl.OpponentsPackageImpl#getBehaviorType()
		 * @generated
		 */
		EEnum BEHAVIOR_TYPE = eINSTANCE.getBehaviorType();

	}

} //OpponentsPackage
