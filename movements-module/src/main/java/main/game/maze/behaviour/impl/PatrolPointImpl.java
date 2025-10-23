/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Patrol Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolPointImpl#getTime <em>Time</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolPointImpl#getPoint <em>Point</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolPointImpl#getRegenAmount <em>Regen Amount</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatrolPointImpl extends MinimalEObjectImpl.Container implements PatrolPoint {
	/**
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final int TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected int time = TIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPoint() <em>Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPoint()
	 * @generated
	 * @ordered
	 */
	protected Position point;

	/**
	 * The default value of the '{@link #getRegenAmount() <em>Regen Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegenAmount()
	 * @generated
	 * @ordered
	 */
	protected static final int REGEN_AMOUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRegenAmount() <em>Regen Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegenAmount()
	 * @generated
	 * @ordered
	 */
	protected int regenAmount = REGEN_AMOUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatrolPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.PATROL_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTime(int newTime) {
		int oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_POINT__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPoint() {
		if (point != null && point.eIsProxy()) {
			InternalEObject oldPoint = (InternalEObject)point;
			point = (Position)eResolveProxy(oldPoint);
			if (point != oldPoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.PATROL_POINT__POINT, oldPoint, point));
			}
		}
		return point;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetPoint() {
		return point;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPoint(Position newPoint) {
		Position oldPoint = point;
		point = newPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_POINT__POINT, oldPoint, point));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getRegenAmount() {
		return regenAmount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRegenAmount(int newRegenAmount) {
		int oldRegenAmount = regenAmount;
		regenAmount = newRegenAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_POINT__REGEN_AMOUNT, oldRegenAmount, regenAmount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.PATROL_POINT__TIME:
				return getTime();
			case BehaviourPackage.PATROL_POINT__POINT:
				if (resolve) return getPoint();
				return basicGetPoint();
			case BehaviourPackage.PATROL_POINT__REGEN_AMOUNT:
				return getRegenAmount();
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
			case BehaviourPackage.PATROL_POINT__TIME:
				setTime((Integer)newValue);
				return;
			case BehaviourPackage.PATROL_POINT__POINT:
				setPoint((Position)newValue);
				return;
			case BehaviourPackage.PATROL_POINT__REGEN_AMOUNT:
				setRegenAmount((Integer)newValue);
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
			case BehaviourPackage.PATROL_POINT__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case BehaviourPackage.PATROL_POINT__POINT:
				setPoint((Position)null);
				return;
			case BehaviourPackage.PATROL_POINT__REGEN_AMOUNT:
				setRegenAmount(REGEN_AMOUNT_EDEFAULT);
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
			case BehaviourPackage.PATROL_POINT__TIME:
				return time != TIME_EDEFAULT;
			case BehaviourPackage.PATROL_POINT__POINT:
				return point != null;
			case BehaviourPackage.PATROL_POINT__REGEN_AMOUNT:
				return regenAmount != REGEN_AMOUNT_EDEFAULT;
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
		result.append(" (time: ");
		result.append(time);
		result.append(", regenAmount: ");
		result.append(regenAmount);
		result.append(')');
		return result.toString();
	}

} //PatrolPointImpl
