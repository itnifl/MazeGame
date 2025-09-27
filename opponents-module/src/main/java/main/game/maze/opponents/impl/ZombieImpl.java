/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Zombie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ZombieImpl extends CharacterTypeImpl implements Zombie {
	/**
	 * The default value of the '{@link #getAttackDamage() <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackDamage()
	 * @generated
	 * @ordered
	 */
	protected static final int ATTACK_DAMAGE_EDEFAULT = 10;

	/**
	 * The cached value of the '{@link #getAttackDamage() <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackDamage()
	 * @generated
	 * @ordered
	 */
	protected int attackDamage = ATTACK_DAMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected static final BehaviorType BEHAVIOR_EDEFAULT = BehaviorType.WANDER;

	/**
	 * The cached value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected BehaviorType behavior = BEHAVIOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ZombieImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.ZOMBIE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getAttackDamage() {
		return attackDamage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttackDamage(int newAttackDamage) {
		int oldAttackDamage = attackDamage;
		attackDamage = newAttackDamage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__ATTACK_DAMAGE, oldAttackDamage, attackDamage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BehaviorType getBehavior() {
		return behavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBehavior(BehaviorType newBehavior) {
		BehaviorType oldBehavior = behavior;
		behavior = newBehavior == null ? BEHAVIOR_EDEFAULT : newBehavior;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__BEHAVIOR, oldBehavior, behavior));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.ZOMBIE__ATTACK_DAMAGE:
				return getAttackDamage();
			case OpponentsPackage.ZOMBIE__BEHAVIOR:
				return getBehavior();
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
			case OpponentsPackage.ZOMBIE__ATTACK_DAMAGE:
				setAttackDamage((Integer)newValue);
				return;
			case OpponentsPackage.ZOMBIE__BEHAVIOR:
				setBehavior((BehaviorType)newValue);
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
			case OpponentsPackage.ZOMBIE__ATTACK_DAMAGE:
				setAttackDamage(ATTACK_DAMAGE_EDEFAULT);
				return;
			case OpponentsPackage.ZOMBIE__BEHAVIOR:
				setBehavior(BEHAVIOR_EDEFAULT);
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
			case OpponentsPackage.ZOMBIE__ATTACK_DAMAGE:
				return attackDamage != ATTACK_DAMAGE_EDEFAULT;
			case OpponentsPackage.ZOMBIE__BEHAVIOR:
				return behavior != BEHAVIOR_EDEFAULT;
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
		result.append(" (attackDamage: ");
		result.append(attackDamage);
		result.append(", behavior: ");
		result.append(behavior);
		result.append(')');
		return result.toString();
	}

} //ZombieImpl
