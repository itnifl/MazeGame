/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ghost</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.GhostImpl#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.GhostImpl#getBehavior <em>Behavior</em>}</li>
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
	 * The cached value of the '{@link #getVisibilityLevel() <em>Visibility Level</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibilityLevel()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> visibilityLevel;

	/**
	 * The cached value of the '{@link #getNonTangibilityEnergy() <em>Non Tangibility Energy</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonTangibilityEnergy()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> nonTangibilityEnergy;

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
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.GHOST__BEHAVIOR, oldBehavior, behavior));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getVisibilityLevel() {
		if (visibilityLevel == null) {
			visibilityLevel = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.GHOST__VISIBILITY_LEVEL);
		}
		return visibilityLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getNonTangibilityEnergy() {
		if (nonTangibilityEnergy == null) {
			nonTangibilityEnergy = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY);
		}
		return nonTangibilityEnergy;
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
			case OpponentsPackage.GHOST__BEHAVIOR:
				return getBehavior();
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OpponentsPackage.GHOST__ATTACK_DAMAGE:
				setAttackDamage((Integer)newValue);
				return;
			case OpponentsPackage.GHOST__BEHAVIOR:
				setBehavior((BehaviorType)newValue);
				return;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				getVisibilityLevel().clear();
				getVisibilityLevel().addAll((Collection<? extends Integer>)newValue);
				return;
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				getNonTangibilityEnergy().clear();
				getNonTangibilityEnergy().addAll((Collection<? extends Integer>)newValue);
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
			case OpponentsPackage.GHOST__BEHAVIOR:
				setBehavior(BEHAVIOR_EDEFAULT);
				return;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				getVisibilityLevel().clear();
				return;
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				getNonTangibilityEnergy().clear();
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
			case OpponentsPackage.GHOST__BEHAVIOR:
				return behavior != BEHAVIOR_EDEFAULT;
			case OpponentsPackage.GHOST__VISIBILITY_LEVEL:
				return visibilityLevel != null && !visibilityLevel.isEmpty();
			case OpponentsPackage.GHOST__NON_TANGIBILITY_ENERGY:
				return nonTangibilityEnergy != null && !nonTangibilityEnergy.isEmpty();
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
		result.append(", visibilityLevel: ");
		result.append(visibilityLevel);
		result.append(", nonTangibilityEnergy: ");
		result.append(nonTangibilityEnergy);
		result.append(')');
		return result.toString();
	}

} //GhostImpl
