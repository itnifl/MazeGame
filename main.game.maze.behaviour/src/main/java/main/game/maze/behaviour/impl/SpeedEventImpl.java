/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.MovementBehavior;
import main.game.maze.behaviour.SpeedEvent;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Speed Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.SpeedEventImpl#getSpeedAmount <em>Speed Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.SpeedEventImpl#getSpeedPercentage <em>Speed Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SpeedEventImpl extends CharacterEventImpl implements SpeedEvent {
	/**
	 * The default value of the '{@link #getSpeedAmount() <em>Speed Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeedAmount()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEED_AMOUNT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSpeedAmount() <em>Speed Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeedAmount()
	 * @generated
	 * @ordered
	 */
	protected double speedAmount = SPEED_AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeedPercentage() <em>Speed Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeedPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEED_PERCENTAGE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSpeedPercentage() <em>Speed Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeedPercentage()
	 * @generated
	 * @ordered
	 */
	protected double speedPercentage = SPEED_PERCENTAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpeedEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.SPEED_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSpeedAmount() {
		return speedAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpeedAmount(double newSpeedAmount) {
		double oldSpeedAmount = speedAmount;
		speedAmount = newSpeedAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.SPEED_EVENT__SPEED_AMOUNT, oldSpeedAmount, speedAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSpeedPercentage() {
		return speedPercentage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpeedPercentage(double newSpeedPercentage) {
		double oldSpeedPercentage = speedPercentage;
		speedPercentage = newSpeedPercentage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.SPEED_EVENT__SPEED_PERCENTAGE, oldSpeedPercentage, speedPercentage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.SPEED_EVENT__SPEED_AMOUNT:
				return getSpeedAmount();
			case BehaviourPackage.SPEED_EVENT__SPEED_PERCENTAGE:
				return getSpeedPercentage();
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
			case BehaviourPackage.SPEED_EVENT__SPEED_AMOUNT:
				setSpeedAmount((Double)newValue);
				return;
			case BehaviourPackage.SPEED_EVENT__SPEED_PERCENTAGE:
				setSpeedPercentage((Double)newValue);
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
			case BehaviourPackage.SPEED_EVENT__SPEED_AMOUNT:
				setSpeedAmount(SPEED_AMOUNT_EDEFAULT);
				return;
			case BehaviourPackage.SPEED_EVENT__SPEED_PERCENTAGE:
				setSpeedPercentage(SPEED_PERCENTAGE_EDEFAULT);
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
			case BehaviourPackage.SPEED_EVENT__SPEED_AMOUNT:
				return speedAmount != SPEED_AMOUNT_EDEFAULT;
			case BehaviourPackage.SPEED_EVENT__SPEED_PERCENTAGE:
				return speedPercentage != SPEED_PERCENTAGE_EDEFAULT;
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
		result.append(" (speedAmount: ");
		result.append(speedAmount);
		result.append(", speedPercentage: ");
		result.append(speedPercentage);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void notifySubscribers() {
		if (!shouldFire()) return;
		MovementBehavior sub = getSubscriber();
		if (sub != null) {
			try {
				sub.update(this);
			} catch (Exception ignore) {}
		}
	}

} //SpeedEventImpl
