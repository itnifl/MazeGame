/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.LootTable;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
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
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getZombieLootTable <em>Zombie Loot Table</em>}</li>
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
	 * The cached value of the '{@link #getZombieLootTable() <em>Zombie Loot Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZombieLootTable()
	 * @generated
	 * @ordered
	 */
	protected LootTable zombieLootTable;

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
	public LootTable getZombieLootTable() {
		if (zombieLootTable != null && zombieLootTable.eIsProxy()) {
			InternalEObject oldZombieLootTable = (InternalEObject)zombieLootTable;
			zombieLootTable = (LootTable)eResolveProxy(oldZombieLootTable);
			if (zombieLootTable != oldZombieLootTable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE, oldZombieLootTable, zombieLootTable));
			}
		}
		return zombieLootTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LootTable basicGetZombieLootTable() {
		return zombieLootTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setZombieLootTable(LootTable newZombieLootTable) {
		LootTable oldZombieLootTable = zombieLootTable;
		zombieLootTable = newZombieLootTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE, oldZombieLootTable, zombieLootTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getEffectiveThreat() {
		// Defensive defaults
		int baseThreatLevel = (int) Math.round(this.getThreatLevel());
		int healthPercent = Math.clamp(getHealth(), 0, 100);

		int behaviorMultiplier = 1;
		var currentBehavior = this.getBehavior();
		behaviorMultiplier = currentBehavior.getValue();
		double computed = (baseThreatLevel * (healthPercent / 100.0)) * behaviorMultiplier;
		return Math.max(0, (int)Math.round(computed));
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				if (resolve) return getZombieLootTable();
				return basicGetZombieLootTable();
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				setZombieLootTable((LootTable)newValue);
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				setZombieLootTable((LootTable)null);
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				return zombieLootTable != null;
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
