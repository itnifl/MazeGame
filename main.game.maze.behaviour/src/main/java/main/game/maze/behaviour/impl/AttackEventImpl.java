/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.AttackEvent;
import main.game.maze.behaviour.BehaviourPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attack Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.AttackEventImpl#getRadiusPercentage <em>Radius Percentage</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.AttackEventImpl#getRadiusAmount <em>Radius Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.AttackEventImpl#getDamageAmount <em>Damage Amount</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.AttackEventImpl#getDamagePercentage <em>Damage Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttackEventImpl extends CharacterEventImpl implements AttackEvent {
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
	 * The default value of the '{@link #getDamageAmount() <em>Damage Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDamageAmount()
	 * @generated
	 * @ordered
	 */
	protected static final int DAMAGE_AMOUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDamageAmount() <em>Damage Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDamageAmount()
	 * @generated
	 * @ordered
	 */
	protected int damageAmount = DAMAGE_AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDamagePercentage() <em>Damage Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDamagePercentage()
	 * @generated
	 * @ordered
	 */
	protected static final int DAMAGE_PERCENTAGE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDamagePercentage() <em>Damage Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDamagePercentage()
	 * @generated
	 * @ordered
	 */
	protected int damagePercentage = DAMAGE_PERCENTAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttackEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.ATTACK_EVENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ATTACK_EVENT__RADIUS_PERCENTAGE, oldRadiusPercentage, radiusPercentage));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ATTACK_EVENT__RADIUS_AMOUNT, oldRadiusAmount, radiusAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDamageAmount() {
		return damageAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDamageAmount(int newDamageAmount) {
		int oldDamageAmount = damageAmount;
		damageAmount = newDamageAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ATTACK_EVENT__DAMAGE_AMOUNT, oldDamageAmount, damageAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDamagePercentage() {
		return damagePercentage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDamagePercentage(int newDamagePercentage) {
		int oldDamagePercentage = damagePercentage;
		damagePercentage = newDamagePercentage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.ATTACK_EVENT__DAMAGE_PERCENTAGE, oldDamagePercentage, damagePercentage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.ATTACK_EVENT__RADIUS_PERCENTAGE:
				return getRadiusPercentage();
			case BehaviourPackage.ATTACK_EVENT__RADIUS_AMOUNT:
				return getRadiusAmount();
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_AMOUNT:
				return getDamageAmount();
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_PERCENTAGE:
				return getDamagePercentage();
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
			case BehaviourPackage.ATTACK_EVENT__RADIUS_PERCENTAGE:
				setRadiusPercentage((Double)newValue);
				return;
			case BehaviourPackage.ATTACK_EVENT__RADIUS_AMOUNT:
				setRadiusAmount((Double)newValue);
				return;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_AMOUNT:
				setDamageAmount((Integer)newValue);
				return;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_PERCENTAGE:
				setDamagePercentage((Integer)newValue);
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
			case BehaviourPackage.ATTACK_EVENT__RADIUS_PERCENTAGE:
				setRadiusPercentage(RADIUS_PERCENTAGE_EDEFAULT);
				return;
			case BehaviourPackage.ATTACK_EVENT__RADIUS_AMOUNT:
				setRadiusAmount(RADIUS_AMOUNT_EDEFAULT);
				return;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_AMOUNT:
				setDamageAmount(DAMAGE_AMOUNT_EDEFAULT);
				return;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_PERCENTAGE:
				setDamagePercentage(DAMAGE_PERCENTAGE_EDEFAULT);
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
			case BehaviourPackage.ATTACK_EVENT__RADIUS_PERCENTAGE:
				return radiusPercentage != RADIUS_PERCENTAGE_EDEFAULT;
			case BehaviourPackage.ATTACK_EVENT__RADIUS_AMOUNT:
				return radiusAmount != RADIUS_AMOUNT_EDEFAULT;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_AMOUNT:
				return damageAmount != DAMAGE_AMOUNT_EDEFAULT;
			case BehaviourPackage.ATTACK_EVENT__DAMAGE_PERCENTAGE:
				return damagePercentage != DAMAGE_PERCENTAGE_EDEFAULT;
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
		result.append(" (radiusPercentage: ");
		result.append(radiusPercentage);
		result.append(", radiusAmount: ");
		result.append(radiusAmount);
		result.append(", damageAmount: ");
		result.append(damageAmount);
		result.append(", damagePercentage: ");
		result.append(damagePercentage);
		result.append(')');
		return result.toString();
	}

} //AttackEventImpl
