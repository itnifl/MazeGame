/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.HealthEvent;
import main.game.maze.behaviour.MovementBehavior;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Health Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.HealthEventImpl#getHealthAmount <em>Health Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.HealthEventImpl#getHealthPercentage <em>Health Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HealthEventImpl extends CharacterEventImpl implements HealthEvent {
	/**
	 * The default value of the '{@link #getHealthAmount() <em>Health Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthAmount()
	 * @generated
	 * @ordered
	 */
	protected static final int HEALTH_AMOUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHealthAmount() <em>Health Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthAmount()
	 * @generated
	 * @ordered
	 */
	protected int healthAmount = HEALTH_AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getHealthPercentage() <em>Health Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final int HEALTH_PERCENTAGE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHealthPercentage() <em>Health Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealthPercentage()
	 * @generated
	 * @ordered
	 */
	protected int healthPercentage = HEALTH_PERCENTAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HealthEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.HEALTH_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHealthAmount() {
		return healthAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthAmount(int newHealthAmount) {
		int oldHealthAmount = healthAmount;
		healthAmount = newHealthAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.HEALTH_EVENT__HEALTH_AMOUNT, oldHealthAmount, healthAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHealthPercentage() {
		return healthPercentage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealthPercentage(int newHealthPercentage) {
		int oldHealthPercentage = healthPercentage;
		healthPercentage = newHealthPercentage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.HEALTH_EVENT__HEALTH_PERCENTAGE, oldHealthPercentage, healthPercentage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.HEALTH_EVENT__HEALTH_AMOUNT:
				return getHealthAmount();
			case BehaviourPackage.HEALTH_EVENT__HEALTH_PERCENTAGE:
				return getHealthPercentage();
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
			case BehaviourPackage.HEALTH_EVENT__HEALTH_AMOUNT:
				setHealthAmount((Integer)newValue);
				return;
			case BehaviourPackage.HEALTH_EVENT__HEALTH_PERCENTAGE:
				setHealthPercentage((Integer)newValue);
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
			case BehaviourPackage.HEALTH_EVENT__HEALTH_AMOUNT:
				setHealthAmount(HEALTH_AMOUNT_EDEFAULT);
				return;
			case BehaviourPackage.HEALTH_EVENT__HEALTH_PERCENTAGE:
				setHealthPercentage(HEALTH_PERCENTAGE_EDEFAULT);
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
			case BehaviourPackage.HEALTH_EVENT__HEALTH_AMOUNT:
				return healthAmount != HEALTH_AMOUNT_EDEFAULT;
			case BehaviourPackage.HEALTH_EVENT__HEALTH_PERCENTAGE:
				return healthPercentage != HEALTH_PERCENTAGE_EDEFAULT;
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
		result.append(" (healthAmount: ");
		result.append(healthAmount);
		result.append(", healthPercentage: ");
		result.append(healthPercentage);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void notifySubscribers() {
		if (! shouldFire()) return;
		MovementBehavior sub = getSubscriber();
		if (sub != null) {
			try {
				sub. update(this);
			} catch (Exception ignore) {}
		}
	}

} //HealthEventImpl
