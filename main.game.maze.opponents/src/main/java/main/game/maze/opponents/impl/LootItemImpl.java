/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;

import main.game.maze.opponents.LootItem;
import main.game.maze.opponents.LootItemType;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loot Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.LootItemImpl#getName <em>Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.LootItemImpl#getType <em>Type</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.LootItemImpl#getValue <em>Value</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.LootItemImpl#getWeight <em>Weight</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.LootItemImpl#getGraphicBase <em>Graphic Base</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LootItemImpl extends MinimalEObjectImpl.Container implements LootItem {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final LootItemType TYPE_EDEFAULT = LootItemType.FOOD;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected LootItemType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final int VALUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected int value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWeight() <em>Weight</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWeight()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> weight;

	/**
	 * The default value of the '{@link #getGraphicBase() <em>Graphic Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicBase()
	 * @generated
	 * @ordered
	 */
	protected static final String GRAPHIC_BASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGraphicBase() <em>Graphic Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicBase()
	 * @generated
	 * @ordered
	 */
	protected String graphicBase = GRAPHIC_BASE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LootItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.LOOT_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.LOOT_ITEM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LootItemType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(LootItemType newType) {
		LootItemType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.LOOT_ITEM__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(int newValue) {
		int oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.LOOT_ITEM__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getWeight() {
		if (weight == null) {
			weight = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.LOOT_ITEM__WEIGHT);
		}
		return weight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGraphicBase() {
		return graphicBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGraphicBase(String newGraphicBase) {
		String oldGraphicBase = graphicBase;
		graphicBase = newGraphicBase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.LOOT_ITEM__GRAPHIC_BASE, oldGraphicBase, graphicBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.LOOT_ITEM__NAME:
				return getName();
			case OpponentsPackage.LOOT_ITEM__TYPE:
				return getType();
			case OpponentsPackage.LOOT_ITEM__VALUE:
				return getValue();
			case OpponentsPackage.LOOT_ITEM__WEIGHT:
				return getWeight();
			case OpponentsPackage.LOOT_ITEM__GRAPHIC_BASE:
				return getGraphicBase();
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
			case OpponentsPackage.LOOT_ITEM__NAME:
				setName((String)newValue);
				return;
			case OpponentsPackage.LOOT_ITEM__TYPE:
				setType((LootItemType)newValue);
				return;
			case OpponentsPackage.LOOT_ITEM__VALUE:
				setValue((Integer)newValue);
				return;
			case OpponentsPackage.LOOT_ITEM__WEIGHT:
				getWeight().clear();
				getWeight().addAll((Collection<? extends Integer>)newValue);
				return;
			case OpponentsPackage.LOOT_ITEM__GRAPHIC_BASE:
				setGraphicBase((String)newValue);
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
			case OpponentsPackage.LOOT_ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OpponentsPackage.LOOT_ITEM__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case OpponentsPackage.LOOT_ITEM__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case OpponentsPackage.LOOT_ITEM__WEIGHT:
				getWeight().clear();
				return;
			case OpponentsPackage.LOOT_ITEM__GRAPHIC_BASE:
				setGraphicBase(GRAPHIC_BASE_EDEFAULT);
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
			case OpponentsPackage.LOOT_ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OpponentsPackage.LOOT_ITEM__TYPE:
				return type != TYPE_EDEFAULT;
			case OpponentsPackage.LOOT_ITEM__VALUE:
				return value != VALUE_EDEFAULT;
			case OpponentsPackage.LOOT_ITEM__WEIGHT:
				return weight != null && !weight.isEmpty();
			case OpponentsPackage.LOOT_ITEM__GRAPHIC_BASE:
				return GRAPHIC_BASE_EDEFAULT == null ? graphicBase != null : !GRAPHIC_BASE_EDEFAULT.equals(graphicBase);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", type: ");
		result.append(type);
		result.append(", value: ");
		result.append(value);
		result.append(", weight: ");
		result.append(weight);
		result.append(", graphicBase: ");
		result.append(graphicBase);
		result.append(')');
		return result.toString();
	}

} //LootItemImpl
