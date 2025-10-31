/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.*;

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
public class BehaviourFactoryImpl extends EFactoryImpl implements BehaviourFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BehaviourFactory init() {
		try {
			BehaviourFactory theBehaviourFactory = (BehaviourFactory)EPackage.Registry.INSTANCE.getEFactory(BehaviourPackage.eNS_URI);
			if (theBehaviourFactory != null) {
				return theBehaviourFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BehaviourFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourFactoryImpl() {
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
			case BehaviourPackage.RANDOM_BEHAVIOR: return createRandomBehavior();
			case BehaviourPackage.PATROL_BEHAVIOR: return createPatrolBehavior();
			case BehaviourPackage.CHASE_BEHAVIOR: return createChaseBehavior();
			case BehaviourPackage.POSITION: return createPosition();
			case BehaviourPackage.PATROL_POINT: return createPatrolPoint();
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR: return createDijkstraPathCalculator();
			case BehaviourPackage.ASTAR_PATH_CALCULATOR: return createAstarPathCalculator();
			case BehaviourPackage.LOCAL_PATH_CALCULATOR: return createLocalPathCalculator();
			case BehaviourPackage.PATROL_ZONE: return createPatrolZone();
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
			case BehaviourPackage.DISTANCE_METHOD:
				return createDistanceMethodFromString(eDataType, initialValue);
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
			case BehaviourPackage.DISTANCE_METHOD:
				return convertDistanceMethodToString(eDataType, instanceValue);
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
	public RandomBehavior createRandomBehavior() {
		RandomBehaviorImpl randomBehavior = new RandomBehaviorImpl();
		return randomBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PatrolBehavior createPatrolBehavior() {
		PatrolBehaviorImpl patrolBehavior = new PatrolBehaviorImpl();
		return patrolBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChaseBehavior createChaseBehavior() {
		ChaseBehaviorImpl chaseBehavior = new ChaseBehaviorImpl();
		return chaseBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position createPosition() {
		PositionImpl position = new PositionImpl();
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PatrolPoint createPatrolPoint() {
		PatrolPointImpl patrolPoint = new PatrolPointImpl();
		return patrolPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DijkstraPathCalculator createDijkstraPathCalculator() {
		DijkstraPathCalculatorImpl dijkstraPathCalculator = new DijkstraPathCalculatorImpl();
		return dijkstraPathCalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AstarPathCalculator createAstarPathCalculator() {
		AstarPathCalculatorImpl astarPathCalculator = new AstarPathCalculatorImpl();
		return astarPathCalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LocalPathCalculator createLocalPathCalculator() {
		LocalPathCalculatorImpl localPathCalculator = new LocalPathCalculatorImpl();
		return localPathCalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PatrolZone createPatrolZone() {
		PatrolZoneImpl patrolZone = new PatrolZoneImpl();
		return patrolZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistanceMethod createDistanceMethodFromString(EDataType eDataType, String initialValue) {
		DistanceMethod result = DistanceMethod.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDistanceMethodToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BehaviourPackage getBehaviourPackage() {
		return (BehaviourPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BehaviourPackage getPackage() {
		return BehaviourPackage.eINSTANCE;
	}

} //BehaviourFactoryImpl
