/**
 */
package main.game.maze.difficulties.impl;

import java.util.Collection;

import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Difficulty</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyImpl#isInstantDeath <em>Instant Death</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyImpl#getEnemyMaxCount <em>Enemy Max Count</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyImpl#getMonstersMovementSpeedMultiplier <em>Monsters Movement Speed Multiplier</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyImpl#getMonstersDamageMultiplier <em>Monsters Damage Multiplier</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyImpl#getMaxThreat <em>Max Threat</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DifficultyImpl extends MinimalEObjectImpl.Container implements Difficulty {
	/**
	 * The default value of the '{@link #isInstantDeath() <em>Instant Death</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInstantDeath()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INSTANT_DEATH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInstantDeath() <em>Instant Death</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInstantDeath()
	 * @generated
	 * @ordered
	 */
	protected boolean instantDeath = INSTANT_DEATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEnemyMaxCount() <em>Enemy Max Count</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnemyMaxCount()
	 * @generated
	 * @ordered
	 */
	protected EList<EnemyMaxCount> enemyMaxCount;

	/**
	 * The default value of the '{@link #getMonstersMovementSpeedMultiplier() <em>Monsters Movement Speed Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonstersMovementSpeedMultiplier()
	 * @generated
	 * @ordered
	 */
	protected static final double MONSTERS_MOVEMENT_SPEED_MULTIPLIER_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMonstersMovementSpeedMultiplier() <em>Monsters Movement Speed Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonstersMovementSpeedMultiplier()
	 * @generated
	 * @ordered
	 */
	protected double monstersMovementSpeedMultiplier = MONSTERS_MOVEMENT_SPEED_MULTIPLIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMonstersDamageMultiplier() <em>Monsters Damage Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonstersDamageMultiplier()
	 * @generated
	 * @ordered
	 */
	protected static final double MONSTERS_DAMAGE_MULTIPLIER_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMonstersDamageMultiplier() <em>Monsters Damage Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonstersDamageMultiplier()
	 * @generated
	 * @ordered
	 */
	protected double monstersDamageMultiplier = MONSTERS_DAMAGE_MULTIPLIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxThreat() <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxThreat()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_THREAT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxThreat() <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxThreat()
	 * @generated
	 * @ordered
	 */
	protected int maxThreat = MAX_THREAT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DifficultyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifficultiesPackage.Literals.DIFFICULTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInstantDeath() {
		return instantDeath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInstantDeath(boolean newInstantDeath) {
		boolean oldInstantDeath = instantDeath;
		instantDeath = newInstantDeath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.DIFFICULTY__INSTANT_DEATH, oldInstantDeath, instantDeath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EnemyMaxCount> getEnemyMaxCount() {
		if (enemyMaxCount == null) {
			enemyMaxCount = new EObjectContainmentEList<EnemyMaxCount>(EnemyMaxCount.class, this, DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT);
		}
		return enemyMaxCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMonstersMovementSpeedMultiplier() {
		return monstersMovementSpeedMultiplier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMonstersMovementSpeedMultiplier(double newMonstersMovementSpeedMultiplier) {
		double oldMonstersMovementSpeedMultiplier = monstersMovementSpeedMultiplier;
		monstersMovementSpeedMultiplier = newMonstersMovementSpeedMultiplier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER, oldMonstersMovementSpeedMultiplier, monstersMovementSpeedMultiplier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMonstersDamageMultiplier() {
		return monstersDamageMultiplier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMonstersDamageMultiplier(double newMonstersDamageMultiplier) {
		double oldMonstersDamageMultiplier = monstersDamageMultiplier;
		monstersDamageMultiplier = newMonstersDamageMultiplier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER, oldMonstersDamageMultiplier, monstersDamageMultiplier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMaxThreat() {
		return maxThreat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxThreat(int newMaxThreat) {
		int oldMaxThreat = maxThreat;
		maxThreat = newMaxThreat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.DIFFICULTY__MAX_THREAT, oldMaxThreat, maxThreat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT:
				return ((InternalEList<?>)getEnemyMaxCount()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DifficultiesPackage.DIFFICULTY__INSTANT_DEATH:
				return isInstantDeath();
			case DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT:
				return getEnemyMaxCount();
			case DifficultiesPackage.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER:
				return getMonstersMovementSpeedMultiplier();
			case DifficultiesPackage.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER:
				return getMonstersDamageMultiplier();
			case DifficultiesPackage.DIFFICULTY__MAX_THREAT:
				return getMaxThreat();
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
			case DifficultiesPackage.DIFFICULTY__INSTANT_DEATH:
				setInstantDeath((Boolean)newValue);
				return;
			case DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT:
				getEnemyMaxCount().clear();
				getEnemyMaxCount().addAll((Collection<? extends EnemyMaxCount>)newValue);
				return;
			case DifficultiesPackage.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER:
				setMonstersMovementSpeedMultiplier((Double)newValue);
				return;
			case DifficultiesPackage.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER:
				setMonstersDamageMultiplier((Double)newValue);
				return;
			case DifficultiesPackage.DIFFICULTY__MAX_THREAT:
				setMaxThreat((Integer)newValue);
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
			case DifficultiesPackage.DIFFICULTY__INSTANT_DEATH:
				setInstantDeath(INSTANT_DEATH_EDEFAULT);
				return;
			case DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT:
				getEnemyMaxCount().clear();
				return;
			case DifficultiesPackage.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER:
				setMonstersMovementSpeedMultiplier(MONSTERS_MOVEMENT_SPEED_MULTIPLIER_EDEFAULT);
				return;
			case DifficultiesPackage.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER:
				setMonstersDamageMultiplier(MONSTERS_DAMAGE_MULTIPLIER_EDEFAULT);
				return;
			case DifficultiesPackage.DIFFICULTY__MAX_THREAT:
				setMaxThreat(MAX_THREAT_EDEFAULT);
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
			case DifficultiesPackage.DIFFICULTY__INSTANT_DEATH:
				return instantDeath != INSTANT_DEATH_EDEFAULT;
			case DifficultiesPackage.DIFFICULTY__ENEMY_MAX_COUNT:
				return enemyMaxCount != null && !enemyMaxCount.isEmpty();
			case DifficultiesPackage.DIFFICULTY__MONSTERS_MOVEMENT_SPEED_MULTIPLIER:
				return monstersMovementSpeedMultiplier != MONSTERS_MOVEMENT_SPEED_MULTIPLIER_EDEFAULT;
			case DifficultiesPackage.DIFFICULTY__MONSTERS_DAMAGE_MULTIPLIER:
				return monstersDamageMultiplier != MONSTERS_DAMAGE_MULTIPLIER_EDEFAULT;
			case DifficultiesPackage.DIFFICULTY__MAX_THREAT:
				return maxThreat != MAX_THREAT_EDEFAULT;
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
		result.append(" (instantDeath: ");
		result.append(instantDeath);
		result.append(", monstersMovementSpeedMultiplier: ");
		result.append(monstersMovementSpeedMultiplier);
		result.append(", monstersDamageMultiplier: ");
		result.append(monstersDamageMultiplier);
		result.append(", maxThreat: ");
		result.append(maxThreat);
		result.append(')');
		return result.toString();
	}

} //DifficultyImpl
