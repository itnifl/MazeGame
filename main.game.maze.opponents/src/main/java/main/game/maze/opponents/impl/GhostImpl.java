/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ghost</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.GhostImpl#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.GhostImpl#getVisibilityLevel <em>Visibility Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.GhostImpl#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GhostImpl extends CharacterTypeImpl implements Ghost {
	/**
	 * The default value of the '{@link #getAttackDamage() <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackDamage()
	 * @generated
	 * @ordered
	 */
	protected static final int ATTACK_DAMAGE_EDEFAULT = 1;

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
	 * The default value of the '{@link #getVisibilityLevel() <em>Visibility Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibilityLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int VISIBILITY_LEVEL_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getVisibilityLevel() <em>Visibility Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibilityLevel()
	 * @generated
	 * @ordered
	 */
	protected int visibilityLevel = VISIBILITY_LEVEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNonTangibilityEnergy() <em>Non Tangibility Energy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonTangibilityEnergy()
	 * @generated
	 * @ordered
	 */
	protected static final double NON_TANGIBILITY_ENERGY_EDEFAULT = 100.0;

	/**
	 * The cached value of the '{@link #getNonTangibilityEnergy() <em>Non Tangibility Energy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonTangibilityEnergy()
	 * @generated
	 * @ordered
	 */
	protected double nonTangibilityEnergy = NON_TANGIBILITY_ENERGY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GhostImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.GHOST;
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
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.GHOST__ATTACK_DAMAGE, oldAttackDamage, attackDamage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVisibilityLevel() {
		return visibilityLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisibilityLevel(int newVisibilityLevel) {
		int oldVisibilityLevel = visibilityLevel;
		visibilityLevel = newVisibilityLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.GHOST__VISIBILITY_LEVEL, oldVisibilityLevel, visibilityLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getNonTangibilityEnergy() {
		return nonTangibilityEnergy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNonTangibilityEnergy(double newNonTangibilityEnergy) {
		double oldNonTangibilityEnergy = nonTangibilityEnergy;
		nonTangibilityEnergy = newNonTangibilityEnergy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY, oldNonTangibilityEnergy, nonTangibilityEnergy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.GHOST__ATTACK_DAMAGE:
				return getAttackDamage();
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				return getVisibilityLevel();
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				return getNonTangibilityEnergy();
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
			case OpponentsPackage.GHOST__ATTACK_DAMAGE:
				setAttackDamage((Integer)newValue);
				return;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				setVisibilityLevel((Integer)newValue);
				return;
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				setNonTangibilityEnergy((Double)newValue);
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
			case OpponentsPackage.GHOST__ATTACK_DAMAGE:
				setAttackDamage(ATTACK_DAMAGE_EDEFAULT);
				return;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				setVisibilityLevel(VISIBILITY_LEVEL_EDEFAULT);
				return;
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				setNonTangibilityEnergy(NON_TANGIBILITY_ENERGY_EDEFAULT);
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
			case OpponentsPackage.GHOST__ATTACK_DAMAGE:
				return attackDamage != ATTACK_DAMAGE_EDEFAULT;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				return visibilityLevel != VISIBILITY_LEVEL_EDEFAULT;
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				return nonTangibilityEnergy != NON_TANGIBILITY_ENERGY_EDEFAULT;
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
		result.append(", visibilityLevel: ");
		result.append(visibilityLevel);
		result.append(", nonTangibilityEnergy: ");
		result.append(nonTangibilityEnergy);
		result.append(')');
		return result.toString();
	}

} //GhostImpl
