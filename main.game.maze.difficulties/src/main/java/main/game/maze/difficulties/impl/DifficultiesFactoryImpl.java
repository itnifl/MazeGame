/**
 */
package main.game.maze.difficulties.impl;

import main.game.maze.difficulties.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DifficultiesFactoryImpl extends EFactoryImpl implements DifficultiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DifficultiesFactory init() {
		try {
			DifficultiesFactory theDifficultiesFactory = (DifficultiesFactory)EPackage.Registry.INSTANCE.getEFactory(DifficultiesPackage.eNS_URI);
			if (theDifficultiesFactory != null) {
				return theDifficultiesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DifficultiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifficultiesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DifficultiesPackage.DIFFICULTY_GAME_DATA: return createDifficultyGameData();
			case DifficultiesPackage.EASY_DIFFICULTY: return createEasyDifficulty();
			case DifficultiesPackage.NORMAL_DIFFICULTY: return createNormalDifficulty();
			case DifficultiesPackage.HARD_DIFFICULTY: return createHardDifficulty();
			case DifficultiesPackage.ENEMY_MAX_COUNT: return createEnemyMaxCount();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DifficultiesPackage.ENEMY_TYPES:
				return createEnemyTypesFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DifficultiesPackage.ENEMY_TYPES:
				return convertEnemyTypesToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultyGameData createDifficultyGameData() {
		DifficultyGameDataImpl difficultyGameData = new DifficultyGameDataImpl();
		return difficultyGameData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EasyDifficulty createEasyDifficulty() {
		EasyDifficultyImpl easyDifficulty = new EasyDifficultyImpl();
		return easyDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NormalDifficulty createNormalDifficulty() {
		NormalDifficultyImpl normalDifficulty = new NormalDifficultyImpl();
		return normalDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HardDifficulty createHardDifficulty() {
		HardDifficultyImpl hardDifficulty = new HardDifficultyImpl();
		return hardDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnemyMaxCount createEnemyMaxCount() {
		EnemyMaxCountImpl enemyMaxCount = new EnemyMaxCountImpl();
		return enemyMaxCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnemyTypes createEnemyTypesFromString(EDataType eDataType, String initialValue) {
		EnemyTypes result = EnemyTypes.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnemyTypesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultiesPackage getDifficultiesPackage() {
		return (DifficultiesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DifficultiesPackage getPackage() {
		return DifficultiesPackage.eINSTANCE;
	}

} //DifficultiesFactoryImpl
