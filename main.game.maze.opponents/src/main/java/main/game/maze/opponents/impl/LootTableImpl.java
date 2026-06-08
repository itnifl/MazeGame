/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;

import main.game.maze.opponents.LootItem;
import main.game.maze.opponents.LootTable;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loot Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.LootTableImpl#getItems <em>Items</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.LootTableImpl#getWeightCapacity <em>Weight Capacity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LootTableImpl extends MinimalEObjectImpl.Container implements LootTable {
	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<LootItem> items;

	/**
	 * The cached value of the '{@link #getWeightCapacity() <em>Weight Capacity</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWeightCapacity()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> weightCapacity;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LootTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.LOOT_TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LootItem> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<LootItem>(LootItem.class, this, OpponentsPackage.LOOT_TABLE__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getWeightCapacity() {
		if (weightCapacity == null) {
			weightCapacity = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.LOOT_TABLE__WEIGHT_CAPACITY);
		}
		return weightCapacity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpponentsPackage.LOOT_TABLE__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.LOOT_TABLE__ITEMS:
				return getItems();
			case OpponentsPackage.LOOT_TABLE__WEIGHT_CAPACITY:
				return getWeightCapacity();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OpponentsPackage.LOOT_TABLE__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends LootItem>)newValue);
				return;
			case OpponentsPackage.LOOT_TABLE__WEIGHT_CAPACITY:
				getWeightCapacity().clear();
				getWeightCapacity().addAll((Collection<? extends Integer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OpponentsPackage.LOOT_TABLE__ITEMS:
				getItems().clear();
				return;
			case OpponentsPackage.LOOT_TABLE__WEIGHT_CAPACITY:
				getWeightCapacity().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OpponentsPackage.LOOT_TABLE__ITEMS:
				return items != null && !items.isEmpty();
			case OpponentsPackage.LOOT_TABLE__WEIGHT_CAPACITY:
				return weightCapacity != null && !weightCapacity.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (weightCapacity: ");
		result.append(weightCapacity);
		result.append(')');
		return result.toString();
	}

} //LootTableImpl
