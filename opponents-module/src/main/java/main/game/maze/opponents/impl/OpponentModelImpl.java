/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;

import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Opponent Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getCharacterTypes <em>Character Types</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getMaxThreat <em>Max Threat</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OpponentModelImpl extends MinimalEObjectImpl.Container implements OpponentModel {
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
	 * The cached value of the '{@link #getCharacterTypes() <em>Character Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharacterTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<CharacterType> characterTypes;

	/**
	 * The default value of the '{@link #getMaxThreat() <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxThreat()
	 * @generated
	 * @ordered
	 */
	protected static final double MAX_THREAT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMaxThreat() <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxThreat()
	 * @generated
	 * @ordered
	 */
	protected double maxThreat = MAX_THREAT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OpponentModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.OPPONENT_MODEL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.OPPONENT_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CharacterType> getCharacterTypes() {
		if (characterTypes == null) {
			characterTypes = new EObjectContainmentEList<CharacterType>(CharacterType.class, this, OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES);
		}
		return characterTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMaxThreat() {
		return maxThreat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxThreat(double newMaxThreat) {
		double oldMaxThreat = maxThreat;
		maxThreat = newMaxThreat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.OPPONENT_MODEL__MAX_THREAT, oldMaxThreat, maxThreat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return ((InternalEList<?>)getCharacterTypes()).basicRemove(otherEnd, msgs);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				return getName();
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return getCharacterTypes();
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				return getMaxThreat();
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				setName((String)newValue);
				return;
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				getCharacterTypes().clear();
				getCharacterTypes().addAll((Collection<? extends CharacterType>)newValue);
				return;
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				setMaxThreat((Double)newValue);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				getCharacterTypes().clear();
				return;
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				setMaxThreat(MAX_THREAT_EDEFAULT);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return characterTypes != null && !characterTypes.isEmpty();
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				return maxThreat != MAX_THREAT_EDEFAULT;
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
		result.append(", maxThreat: ");
		result.append(maxThreat);
		result.append(')');
		return result.toString();
	}

} //OpponentModelImpl
