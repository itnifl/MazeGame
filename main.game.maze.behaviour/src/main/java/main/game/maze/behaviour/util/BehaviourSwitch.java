/**
 */
package main.game.maze.behaviour.util;

import main.game.maze.behaviour.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see main.game.maze.behaviour.BehaviourPackage
 * @generated
 */
public class BehaviourSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BehaviourPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourSwitch() {
		if (modelPackage == null) {
			modelPackage = BehaviourPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case BehaviourPackage.DIRECTION: {
				Direction direction = (Direction)theEObject;
				T result = caseDirection(direction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.POSITION: {
				Position position = (Position)theEObject;
				T result = casePosition(position);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.MOVEMENT_BEHAVIOR: {
				MovementBehavior movementBehavior = (MovementBehavior)theEObject;
				T result = caseMovementBehavior(movementBehavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.RANDOM_BEHAVIOR: {
				RandomBehavior randomBehavior = (RandomBehavior)theEObject;
				T result = caseRandomBehavior(randomBehavior);
				if (result == null) result = caseMovementBehavior(randomBehavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.PATROL_BEHAVIOR: {
				PatrolBehavior patrolBehavior = (PatrolBehavior)theEObject;
				T result = casePatrolBehavior(patrolBehavior);
				if (result == null) result = caseMovementBehavior(patrolBehavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.CHASE_BEHAVIOR: {
				ChaseBehavior chaseBehavior = (ChaseBehavior)theEObject;
				T result = caseChaseBehavior(chaseBehavior);
				if (result == null) result = caseMovementBehavior(chaseBehavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.PATROL_POINT: {
				PatrolPoint patrolPoint = (PatrolPoint)theEObject;
				T result = casePatrolPoint(patrolPoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.PATROL_ZONE: {
				PatrolZone patrolZone = (PatrolZone)theEObject;
				T result = casePatrolZone(patrolZone);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.PATH_CALCULATOR: {
				PathCalculator pathCalculator = (PathCalculator)theEObject;
				T result = casePathCalculator(pathCalculator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.DIJKSTRA_PATH_CALCULATOR: {
				DijkstraPathCalculator dijkstraPathCalculator = (DijkstraPathCalculator)theEObject;
				T result = caseDijkstraPathCalculator(dijkstraPathCalculator);
				if (result == null) result = casePathCalculator(dijkstraPathCalculator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.ASTAR_PATH_CALCULATOR: {
				AstarPathCalculator astarPathCalculator = (AstarPathCalculator)theEObject;
				T result = caseAstarPathCalculator(astarPathCalculator);
				if (result == null) result = casePathCalculator(astarPathCalculator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.LOCAL_PATH_CALCULATOR: {
				LocalPathCalculator localPathCalculator = (LocalPathCalculator)theEObject;
				T result = caseLocalPathCalculator(localPathCalculator);
				if (result == null) result = casePathCalculator(localPathCalculator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.CHARACTER_EVENT: {
				CharacterEvent characterEvent = (CharacterEvent)theEObject;
				T result = caseCharacterEvent(characterEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.HEALTH_EVENT: {
				HealthEvent healthEvent = (HealthEvent)theEObject;
				T result = caseHealthEvent(healthEvent);
				if (result == null) result = caseCharacterEvent(healthEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.SPEED_EVENT: {
				SpeedEvent speedEvent = (SpeedEvent)theEObject;
				T result = caseSpeedEvent(speedEvent);
				if (result == null) result = caseCharacterEvent(speedEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.TIME_EVENT: {
				TimeEvent timeEvent = (TimeEvent)theEObject;
				T result = caseTimeEvent(timeEvent);
				if (result == null) result = caseCharacterEvent(timeEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.VISION_EVENT: {
				VisionEvent visionEvent = (VisionEvent)theEObject;
				T result = caseVisionEvent(visionEvent);
				if (result == null) result = caseCharacterEvent(visionEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BehaviourPackage.ATTACK_EVENT: {
				AttackEvent attackEvent = (AttackEvent)theEObject;
				T result = caseAttackEvent(attackEvent);
				if (result == null) result = caseCharacterEvent(attackEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Direction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Direction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDirection(Direction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Random Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Random Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRandomBehavior(RandomBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Patrol Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatrolBehavior(PatrolBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chase Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chase Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChaseBehavior(ChaseBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Movement Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Movement Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMovementBehavior(MovementBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePosition(Position object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Patrol Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatrolPoint(PatrolPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Path Calculator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePathCalculator(PathCalculator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dijkstra Path Calculator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDijkstraPathCalculator(DijkstraPathCalculator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Astar Path Calculator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAstarPathCalculator(AstarPathCalculator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Path Calculator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalPathCalculator(LocalPathCalculator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Character Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Character Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharacterEvent(CharacterEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Health Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Health Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHealthEvent(HealthEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Speed Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Speed Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSpeedEvent(SpeedEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimeEvent(TimeEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vision Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vision Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisionEvent(VisionEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attack Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attack Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttackEvent(AttackEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Patrol Zone</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Patrol Zone</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatrolZone(PatrolZone object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //BehaviourSwitch
