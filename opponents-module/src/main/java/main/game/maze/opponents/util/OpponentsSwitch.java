/**
 */
package main.game.maze.opponents.util;

import main.game.maze.opponents.*;

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
 * @see main.game.maze.opponents.OpponentsPackage
 * @generated
 */
public class OpponentsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OpponentsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpponentsSwitch() {
		if (modelPackage == null) {
			modelPackage = OpponentsPackage.eINSTANCE;
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
			case OpponentsPackage.OPPONENT_MODEL: {
				OpponentModel opponentModel = (OpponentModel)theEObject;
				T result = caseOpponentModel(opponentModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.CHARACTER_TYPE: {
				CharacterType characterType = (CharacterType)theEObject;
				T result = caseCharacterType(characterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.ZOMBIE: {
				Zombie zombie = (Zombie)theEObject;
				T result = caseZombie(zombie);
				if (result == null) result = caseCharacterType(zombie);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.LOOT_TABLE: {
				LootTable lootTable = (LootTable)theEObject;
				T result = caseLootTable(lootTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.LOOT_ITEM: {
				LootItem lootItem = (LootItem)theEObject;
				T result = caseLootItem(lootItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.GHOST: {
				Ghost ghost = (Ghost)theEObject;
				T result = caseGhost(ghost);
				if (result == null) result = caseCharacterType(ghost);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.RANGED_ENEMY: {
				RangedEnemy rangedEnemy = (RangedEnemy)theEObject;
				T result = caseRangedEnemy(rangedEnemy);
				if (result == null) result = caseCharacterType(rangedEnemy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OpponentsPackage.PUMPKIN_BOMBER: {
				PumpkinBomber pumpkinBomber = (PumpkinBomber)theEObject;
				T result = casePumpkinBomber(pumpkinBomber);
				if (result == null) result = caseRangedEnemy(pumpkinBomber);
				if (result == null) result = caseCharacterType(pumpkinBomber);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Opponent Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Opponent Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOpponentModel(OpponentModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Character Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Character Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharacterType(CharacterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Zombie</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Zombie</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseZombie(Zombie object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Loot Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Loot Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLootTable(LootTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Loot Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Loot Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLootItem(LootItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ghost</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ghost</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGhost(Ghost object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ranged Enemy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ranged Enemy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangedEnemy(RangedEnemy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pumpkin Bomber</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pumpkin Bomber</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePumpkinBomber(PumpkinBomber object) {
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

} //OpponentsSwitch
