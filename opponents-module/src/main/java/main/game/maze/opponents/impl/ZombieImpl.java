/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.LootTable;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

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
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getInfectionLevel <em>Infection Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getResurrectionTime <em>Resurrection Time</em>}</li>
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
	 * The cached value of the '{@link #getInfectionLevel() <em>Infection Level</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfectionLevel()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> infectionLevel;

	/**
	 * The cached value of the '{@link #getResurrectionTime() <em>Resurrection Time</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResurrectionTime()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> resurrectionTime;

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
	 * @generated
	 */
	@Override
	public EList<Integer> getInfectionLevel() {
		if (infectionLevel == null) {
			infectionLevel = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.ZOMBIE__INFECTION_LEVEL);
		}
		return infectionLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getResurrectionTime() {
		if (resurrectionTime == null) {
			resurrectionTime = new EDataTypeUniqueEList<Integer>(Integer.class, this, OpponentsPackage.ZOMBIE__RESURRECTION_TIME);
		}
		return resurrectionTime;
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
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				return getInfectionLevel();
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				return getResurrectionTime();
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
			case OpponentsPackage.ZOMBIE__ATTACK_DAMAGE:
				setAttackDamage((Integer)newValue);
				return;
			case OpponentsPackage.ZOMBIE__BEHAVIOR:
				setBehavior((BehaviorType)newValue);
				return;
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				setZombieLootTable((LootTable)newValue);
				return;
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				getInfectionLevel().clear();
				getInfectionLevel().addAll((Collection<? extends Integer>)newValue);
				return;
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				getResurrectionTime().clear();
				getResurrectionTime().addAll((Collection<? extends Integer>)newValue);
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
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				getInfectionLevel().clear();
				return;
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				getResurrectionTime().clear();
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
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				return infectionLevel != null && !infectionLevel.isEmpty();
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				return resurrectionTime != null && !resurrectionTime.isEmpty();
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
		result.append(", infectionLevel: ");
		result.append(infectionLevel);
		result.append(", resurrectionTime: ");
		result.append(resurrectionTime);
		result.append(')');
		return result.toString();
	}

} //ZombieImpl
