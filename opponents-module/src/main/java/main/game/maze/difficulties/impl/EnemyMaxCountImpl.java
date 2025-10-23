/**
 */
package main.game.maze.difficulties.impl;

import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.EnemyMaxCount;

import main.game.maze.opponents.EnemyTypes;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enemy Max Count</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.impl.EnemyMaxCountImpl#getType <em>Type</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.EnemyMaxCountImpl#getMaxCount <em>Max Count</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnemyMaxCountImpl extends MinimalEObjectImpl.Container implements EnemyMaxCount {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final EnemyTypes TYPE_EDEFAULT = EnemyTypes.ZOMBIE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EnemyTypes type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxCount() <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCount()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxCount() <em>Max Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCount()
	 * @generated
	 * @ordered
	 */
	protected int maxCount = MAX_COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnemyMaxCountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifficultiesPackage.Literals.ENEMY_MAX_COUNT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnemyTypes getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(EnemyTypes newType) {
		EnemyTypes oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.ENEMY_MAX_COUNT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxCount(int newMaxCount) {
		int oldMaxCount = maxCount;
		maxCount = newMaxCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.ENEMY_MAX_COUNT__MAX_COUNT, oldMaxCount, maxCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DifficultiesPackage.ENEMY_MAX_COUNT__TYPE:
				return getType();
			case DifficultiesPackage.ENEMY_MAX_COUNT__MAX_COUNT:
				return getMaxCount();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DifficultiesPackage.ENEMY_MAX_COUNT__TYPE:
				setType((EnemyTypes)newValue);
				return;
			case DifficultiesPackage.ENEMY_MAX_COUNT__MAX_COUNT:
				setMaxCount((Integer)newValue);
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
			case DifficultiesPackage.ENEMY_MAX_COUNT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case DifficultiesPackage.ENEMY_MAX_COUNT__MAX_COUNT:
				setMaxCount(MAX_COUNT_EDEFAULT);
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
			case DifficultiesPackage.ENEMY_MAX_COUNT__TYPE:
				return type != TYPE_EDEFAULT;
			case DifficultiesPackage.ENEMY_MAX_COUNT__MAX_COUNT:
				return maxCount != MAX_COUNT_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", maxCount: ");
		result.append(maxCount);
		result.append(')');
		return result.toString();
	}

} //EnemyMaxCountImpl
