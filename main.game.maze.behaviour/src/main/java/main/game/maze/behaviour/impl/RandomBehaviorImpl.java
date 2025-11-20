/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.RandomBehavior;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Random Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.RandomBehaviorImpl#getRegenPerSecond <em>Regen Per Second</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RandomBehaviorImpl extends MovementBehaviorImpl implements RandomBehavior {
	/**
	 * The default value of the '{@link #getRegenPerSecond() <em>Regen Per Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegenPerSecond()
	 * @generated
	 * @ordered
	 */
	protected static final double REGEN_PER_SECOND_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRegenPerSecond() <em>Regen Per Second</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegenPerSecond()
	 * @generated
	 * @ordered
	 */
	protected double regenPerSecond = REGEN_PER_SECOND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RandomBehaviorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.RANDOM_BEHAVIOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getRegenPerSecond() {
		return regenPerSecond;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRegenPerSecond(double newRegenPerSecond) {
		double oldRegenPerSecond = regenPerSecond;
		regenPerSecond = newRegenPerSecond;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.RANDOM_BEHAVIOR__REGEN_PER_SECOND, oldRegenPerSecond, regenPerSecond));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.RANDOM_BEHAVIOR__REGEN_PER_SECOND:
				return getRegenPerSecond();
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
			case BehaviourPackage.RANDOM_BEHAVIOR__REGEN_PER_SECOND:
				setRegenPerSecond((Double)newValue);
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
			case BehaviourPackage.RANDOM_BEHAVIOR__REGEN_PER_SECOND:
				setRegenPerSecond(REGEN_PER_SECOND_EDEFAULT);
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
			case BehaviourPackage.RANDOM_BEHAVIOR__REGEN_PER_SECOND:
				return regenPerSecond != REGEN_PER_SECOND_EDEFAULT;
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
		result.append(" (regenPerSecond: ");
		result.append(regenPerSecond);
		result.append(')');
		return result.toString();
	}

} //RandomBehaviorImpl
