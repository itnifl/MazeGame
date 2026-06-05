/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.MovementBehavior;
import main.game.maze.behaviour.VisionEvent;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vision Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.VisionEventImpl#getRadiusAmount <em>Radius Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.VisionEventImpl#getRadiusPercentage <em>Radius Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VisionEventImpl extends CharacterEventImpl implements VisionEvent {
	/**
	 * The default value of the '{@link #getRadiusAmount() <em>Radius Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadiusAmount()
	 * @generated
	 * @ordered
	 */
	protected static final double RADIUS_AMOUNT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRadiusAmount() <em>Radius Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadiusAmount()
	 * @generated
	 * @ordered
	 */
	protected double radiusAmount = RADIUS_AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getRadiusPercentage() <em>Radius Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadiusPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final double RADIUS_PERCENTAGE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRadiusPercentage() <em>Radius Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadiusPercentage()
	 * @generated
	 * @ordered
	 */
	protected double radiusPercentage = RADIUS_PERCENTAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VisionEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.VISION_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getRadiusAmount() {
		return radiusAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRadiusAmount(double newRadiusAmount) {
		double oldRadiusAmount = radiusAmount;
		radiusAmount = newRadiusAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.VISION_EVENT__RADIUS_AMOUNT, oldRadiusAmount, radiusAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getRadiusPercentage() {
		return radiusPercentage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRadiusPercentage(double newRadiusPercentage) {
		double oldRadiusPercentage = radiusPercentage;
		radiusPercentage = newRadiusPercentage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.VISION_EVENT__RADIUS_PERCENTAGE, oldRadiusPercentage, radiusPercentage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.VISION_EVENT__RADIUS_AMOUNT:
				return getRadiusAmount();
			case BehaviourPackage.VISION_EVENT__RADIUS_PERCENTAGE:
				return getRadiusPercentage();
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
			case BehaviourPackage.VISION_EVENT__RADIUS_AMOUNT:
				setRadiusAmount((Double)newValue);
				return;
			case BehaviourPackage.VISION_EVENT__RADIUS_PERCENTAGE:
				setRadiusPercentage((Double)newValue);
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
			case BehaviourPackage.VISION_EVENT__RADIUS_AMOUNT:
				setRadiusAmount(RADIUS_AMOUNT_EDEFAULT);
				return;
			case BehaviourPackage.VISION_EVENT__RADIUS_PERCENTAGE:
				setRadiusPercentage(RADIUS_PERCENTAGE_EDEFAULT);
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
			case BehaviourPackage.VISION_EVENT__RADIUS_AMOUNT:
				return radiusAmount != RADIUS_AMOUNT_EDEFAULT;
			case BehaviourPackage.VISION_EVENT__RADIUS_PERCENTAGE:
				return radiusPercentage != RADIUS_PERCENTAGE_EDEFAULT;
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
		result.append(" (radiusAmount: ");
		result.append(radiusAmount);
		result.append(", radiusPercentage: ");
		result.append(radiusPercentage);
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
				sub.update(this); // 'this' is VisionEventImpl → resolves to update(VisionEvent)
			} catch (Exception ignore) {}
		}
	}

} //VisionEventImpl


