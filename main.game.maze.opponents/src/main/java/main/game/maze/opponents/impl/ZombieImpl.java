/**
 */
package main.game.maze.opponents.impl;

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
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getZombieLootTable <em>Zombie Loot Table</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getInfectionLevel <em>Infection Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getResurrectionTime <em>Resurrection Time</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.ZombieImpl#getTouchSound <em>Touch Sound</em>}</li>
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
	 * The cached value of the '{@link #getZombieLootTable() <em>Zombie Loot Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZombieLootTable()
	 * @generated
	 * @ordered
	 */
	protected LootTable zombieLootTable;

	/**
	 * The default value of the '{@link #getInfectionLevel() <em>Infection Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfectionLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int INFECTION_LEVEL_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getInfectionLevel() <em>Infection Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfectionLevel()
	 * @generated
	 * @ordered
	 */
	protected int infectionLevel = INFECTION_LEVEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getResurrectionTime() <em>Resurrection Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResurrectionTime()
	 * @generated
	 * @ordered
	 */
	protected static final int RESURRECTION_TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getResurrectionTime() <em>Resurrection Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResurrectionTime()
	 * @generated
	 * @ordered
	 */
	protected int resurrectionTime = RESURRECTION_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTouchSound() <em>Touch Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTouchSound()
	 * @generated
	 * @ordered
	 */
	protected static final String TOUCH_SOUND_EDEFAULT = "/main/game/maze/zombieScream.mp3";

	/**
	 * The cached value of the '{@link #getTouchSound() <em>Touch Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTouchSound()
	 * @generated
	 * @ordered
	 */
	protected String touchSound = TOUCH_SOUND_EDEFAULT;

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
	public int getInfectionLevel() {
		return infectionLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInfectionLevel(int newInfectionLevel) {
		int oldInfectionLevel = infectionLevel;
		infectionLevel = newInfectionLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__INFECTION_LEVEL, oldInfectionLevel, infectionLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getResurrectionTime() {
		return resurrectionTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResurrectionTime(int newResurrectionTime) {
		int oldResurrectionTime = resurrectionTime;
		resurrectionTime = newResurrectionTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__RESURRECTION_TIME, oldResurrectionTime, resurrectionTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTouchSound() {
		return touchSound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTouchSound(String newTouchSound) {
		String oldTouchSound = touchSound;
		touchSound = newTouchSound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.ZOMBIE__TOUCH_SOUND, oldTouchSound, touchSound));
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				if (resolve) return getZombieLootTable();
				return basicGetZombieLootTable();
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				return getInfectionLevel();
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				return getResurrectionTime();
			case OpponentsPackage.ZOMBIE__TOUCH_SOUND:
				return getTouchSound();
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				setZombieLootTable((LootTable)newValue);
				return;
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				setInfectionLevel((Integer)newValue);
				return;
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				setResurrectionTime((Integer)newValue);
				return;
			case OpponentsPackage.ZOMBIE__TOUCH_SOUND:
				setTouchSound((String)newValue);
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				setZombieLootTable((LootTable)null);
				return;
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				setInfectionLevel(INFECTION_LEVEL_EDEFAULT);
				return;
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				setResurrectionTime(RESURRECTION_TIME_EDEFAULT);
				return;
			case OpponentsPackage.ZOMBIE__TOUCH_SOUND:
				setTouchSound(TOUCH_SOUND_EDEFAULT);
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
			case OpponentsPackage.ZOMBIE__ZOMBIE_LOOT_TABLE:
				return zombieLootTable != null;
			case OpponentsPackage.ZOMBIE__INFECTION_LEVEL:
				return infectionLevel != INFECTION_LEVEL_EDEFAULT;
			case OpponentsPackage.ZOMBIE__RESURRECTION_TIME:
				return resurrectionTime != RESURRECTION_TIME_EDEFAULT;
			case OpponentsPackage.ZOMBIE__TOUCH_SOUND:
				return TOUCH_SOUND_EDEFAULT == null ? touchSound != null : !TOUCH_SOUND_EDEFAULT.equals(touchSound);
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
		result.append(", infectionLevel: ");
		result.append(infectionLevel);
		result.append(", resurrectionTime: ");
		result.append(resurrectionTime);
		result.append(", touchSound: ");
		result.append(touchSound);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public double getEffectiveThreat() {
		// Defensive defaults
		double baseThreatLevel = this.getThreatLevel();
		int healthPercent = Math.max(0, Math.min(getHealth(), 100));
		double computed = (baseThreatLevel * (healthPercent / 100.0));
		return Math.max(0, computed);
	}

} //ZombieImpl


