/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.ProjectileType;
import main.game.maze.opponents.RangedEnemy;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ranged Enemy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getAttackRange <em>Attack Range</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getAttackCooldownMs <em>Attack Cooldown Ms</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getProjectileSpeed <em>Projectile Speed</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getProjectileType <em>Projectile Type</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getSplashRadius <em>Splash Radius</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getArcHeight <em>Arc Height</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getProjectileImage <em>Projectile Image</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getExplosionImage <em>Explosion Image</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getExplosionSound <em>Explosion Sound</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.RangedEnemyImpl#getThrowSound <em>Throw Sound</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RangedEnemyImpl extends CharacterTypeImpl implements RangedEnemy {
	/**
	 * The default value of the '{@link #getAttackRange() <em>Attack Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackRange()
	 * @generated
	 * @ordered
	 */
	protected static final double ATTACK_RANGE_EDEFAULT = 50.0;

	/**
	 * The cached value of the '{@link #getAttackRange() <em>Attack Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackRange()
	 * @generated
	 * @ordered
	 */
	protected double attackRange = ATTACK_RANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAttackCooldownMs() <em>Attack Cooldown Ms</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackCooldownMs()
	 * @generated
	 * @ordered
	 */
	protected static final int ATTACK_COOLDOWN_MS_EDEFAULT = 10000;

	/**
	 * The cached value of the '{@link #getAttackCooldownMs() <em>Attack Cooldown Ms</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackCooldownMs()
	 * @generated
	 * @ordered
	 */
	protected int attackCooldownMs = ATTACK_COOLDOWN_MS_EDEFAULT;

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
	 * The default value of the '{@link #getProjectileSpeed() <em>Projectile Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final double PROJECTILE_SPEED_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getProjectileSpeed() <em>Projectile Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileSpeed()
	 * @generated
	 * @ordered
	 */
	protected double projectileSpeed = PROJECTILE_SPEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectileType() <em>Projectile Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileType()
	 * @generated
	 * @ordered
	 */
	protected static final ProjectileType PROJECTILE_TYPE_EDEFAULT = ProjectileType.STRAIGHT;

	/**
	 * The cached value of the '{@link #getProjectileType() <em>Projectile Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileType()
	 * @generated
	 * @ordered
	 */
	protected ProjectileType projectileType = PROJECTILE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSplashRadius() <em>Splash Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSplashRadius()
	 * @generated
	 * @ordered
	 */
	protected static final double SPLASH_RADIUS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSplashRadius() <em>Splash Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSplashRadius()
	 * @generated
	 * @ordered
	 */
	protected double splashRadius = SPLASH_RADIUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getArcHeight() <em>Arc Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double ARC_HEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getArcHeight() <em>Arc Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcHeight()
	 * @generated
	 * @ordered
	 */
	protected double arcHeight = ARC_HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectileImage() <em>Projectile Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileImage()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECTILE_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectileImage() <em>Projectile Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectileImage()
	 * @generated
	 * @ordered
	 */
	protected String projectileImage = PROJECTILE_IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getExplosionImage() <em>Explosion Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExplosionImage()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPLOSION_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExplosionImage() <em>Explosion Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExplosionImage()
	 * @generated
	 * @ordered
	 */
	protected String explosionImage = EXPLOSION_IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getExplosionSound() <em>Explosion Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExplosionSound()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPLOSION_SOUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExplosionSound() <em>Explosion Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExplosionSound()
	 * @generated
	 * @ordered
	 */
	protected String explosionSound = EXPLOSION_SOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getThrowSound() <em>Throw Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowSound()
	 * @generated
	 * @ordered
	 */
	protected static final String THROW_SOUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getThrowSound() <em>Throw Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowSound()
	 * @generated
	 * @ordered
	 */
	protected String throwSound = THROW_SOUND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangedEnemyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.RANGED_ENEMY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAttackRange() {
		return attackRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttackRange(double newAttackRange) {
		double oldAttackRange = attackRange;
		attackRange = newAttackRange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__ATTACK_RANGE, oldAttackRange, attackRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getAttackCooldownMs() {
		return attackCooldownMs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttackCooldownMs(int newAttackCooldownMs) {
		int oldAttackCooldownMs = attackCooldownMs;
		attackCooldownMs = newAttackCooldownMs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__ATTACK_COOLDOWN_MS, oldAttackCooldownMs, attackCooldownMs));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__ATTACK_DAMAGE, oldAttackDamage, attackDamage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getProjectileSpeed() {
		return projectileSpeed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProjectileSpeed(double newProjectileSpeed) {
		double oldProjectileSpeed = projectileSpeed;
		projectileSpeed = newProjectileSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__PROJECTILE_SPEED, oldProjectileSpeed, projectileSpeed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectileType getProjectileType() {
		return projectileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProjectileType(ProjectileType newProjectileType) {
		ProjectileType oldProjectileType = projectileType;
		projectileType = newProjectileType == null ? PROJECTILE_TYPE_EDEFAULT : newProjectileType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__PROJECTILE_TYPE, oldProjectileType, projectileType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSplashRadius() {
		return splashRadius;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSplashRadius(double newSplashRadius) {
		double oldSplashRadius = splashRadius;
		splashRadius = newSplashRadius;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__SPLASH_RADIUS, oldSplashRadius, splashRadius));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getArcHeight() {
		return arcHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArcHeight(double newArcHeight) {
		double oldArcHeight = arcHeight;
		arcHeight = newArcHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__ARC_HEIGHT, oldArcHeight, arcHeight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProjectileImage() {
		return projectileImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProjectileImage(String newProjectileImage) {
		String oldProjectileImage = projectileImage;
		projectileImage = newProjectileImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__PROJECTILE_IMAGE, oldProjectileImage, projectileImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExplosionImage() {
		return explosionImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExplosionImage(String newExplosionImage) {
		String oldExplosionImage = explosionImage;
		explosionImage = newExplosionImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__EXPLOSION_IMAGE, oldExplosionImage, explosionImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExplosionSound() {
		return explosionSound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExplosionSound(String newExplosionSound) {
		String oldExplosionSound = explosionSound;
		explosionSound = newExplosionSound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__EXPLOSION_SOUND, oldExplosionSound, explosionSound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getThrowSound() {
		return throwSound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setThrowSound(String newThrowSound) {
		String oldThrowSound = throwSound;
		throwSound = newThrowSound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.RANGED_ENEMY__THROW_SOUND, oldThrowSound, throwSound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.RANGED_ENEMY__ATTACK_RANGE:
				return getAttackRange();
			case OpponentsPackage.RANGED_ENEMY__ATTACK_COOLDOWN_MS:
				return getAttackCooldownMs();
			case OpponentsPackage.RANGED_ENEMY__ATTACK_DAMAGE:
				return getAttackDamage();
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_SPEED:
				return getProjectileSpeed();
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_TYPE:
				return getProjectileType();
			case OpponentsPackage.RANGED_ENEMY__SPLASH_RADIUS:
				return getSplashRadius();
			case OpponentsPackage.RANGED_ENEMY__ARC_HEIGHT:
				return getArcHeight();
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_IMAGE:
				return getProjectileImage();
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_IMAGE:
				return getExplosionImage();
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_SOUND:
				return getExplosionSound();
			case OpponentsPackage.RANGED_ENEMY__THROW_SOUND:
				return getThrowSound();
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
			case OpponentsPackage.RANGED_ENEMY__ATTACK_RANGE:
				setAttackRange((Double)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_COOLDOWN_MS:
				setAttackCooldownMs((Integer)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_DAMAGE:
				setAttackDamage((Integer)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_SPEED:
				setProjectileSpeed((Double)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_TYPE:
				setProjectileType((ProjectileType)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__SPLASH_RADIUS:
				setSplashRadius((Double)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__ARC_HEIGHT:
				setArcHeight((Double)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_IMAGE:
				setProjectileImage((String)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_IMAGE:
				setExplosionImage((String)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_SOUND:
				setExplosionSound((String)newValue);
				return;
			case OpponentsPackage.RANGED_ENEMY__THROW_SOUND:
				setThrowSound((String)newValue);
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
			case OpponentsPackage.RANGED_ENEMY__ATTACK_RANGE:
				setAttackRange(ATTACK_RANGE_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_COOLDOWN_MS:
				setAttackCooldownMs(ATTACK_COOLDOWN_MS_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_DAMAGE:
				setAttackDamage(ATTACK_DAMAGE_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_SPEED:
				setProjectileSpeed(PROJECTILE_SPEED_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_TYPE:
				setProjectileType(PROJECTILE_TYPE_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__SPLASH_RADIUS:
				setSplashRadius(SPLASH_RADIUS_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__ARC_HEIGHT:
				setArcHeight(ARC_HEIGHT_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_IMAGE:
				setProjectileImage(PROJECTILE_IMAGE_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_IMAGE:
				setExplosionImage(EXPLOSION_IMAGE_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_SOUND:
				setExplosionSound(EXPLOSION_SOUND_EDEFAULT);
				return;
			case OpponentsPackage.RANGED_ENEMY__THROW_SOUND:
				setThrowSound(THROW_SOUND_EDEFAULT);
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
			case OpponentsPackage.RANGED_ENEMY__ATTACK_RANGE:
				return attackRange != ATTACK_RANGE_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_COOLDOWN_MS:
				return attackCooldownMs != ATTACK_COOLDOWN_MS_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__ATTACK_DAMAGE:
				return attackDamage != ATTACK_DAMAGE_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_SPEED:
				return projectileSpeed != PROJECTILE_SPEED_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_TYPE:
				return projectileType != PROJECTILE_TYPE_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__SPLASH_RADIUS:
				return splashRadius != SPLASH_RADIUS_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__ARC_HEIGHT:
				return arcHeight != ARC_HEIGHT_EDEFAULT;
			case OpponentsPackage.RANGED_ENEMY__PROJECTILE_IMAGE:
				return PROJECTILE_IMAGE_EDEFAULT == null ? projectileImage != null : !PROJECTILE_IMAGE_EDEFAULT.equals(projectileImage);
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_IMAGE:
				return EXPLOSION_IMAGE_EDEFAULT == null ? explosionImage != null : !EXPLOSION_IMAGE_EDEFAULT.equals(explosionImage);
			case OpponentsPackage.RANGED_ENEMY__EXPLOSION_SOUND:
				return EXPLOSION_SOUND_EDEFAULT == null ? explosionSound != null : !EXPLOSION_SOUND_EDEFAULT.equals(explosionSound);
			case OpponentsPackage.RANGED_ENEMY__THROW_SOUND:
				return THROW_SOUND_EDEFAULT == null ? throwSound != null : !THROW_SOUND_EDEFAULT.equals(throwSound);
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
		result.append(" (attackRange: ");
		result.append(attackRange);
		result.append(", attackCooldownMs: ");
		result.append(attackCooldownMs);
		result.append(", attackDamage: ");
		result.append(attackDamage);
		result.append(", projectileSpeed: ");
		result.append(projectileSpeed);
		result.append(", projectileType: ");
		result.append(projectileType);
		result.append(", splashRadius: ");
		result.append(splashRadius);
		result.append(", arcHeight: ");
		result.append(arcHeight);
		result.append(", projectileImage: ");
		result.append(projectileImage);
		result.append(", explosionImage: ");
		result.append(explosionImage);
		result.append(", explosionSound: ");
		result.append(explosionSound);
		result.append(", throwSound: ");
		result.append(throwSound);
		result.append(')');
		return result.toString();
	}

} //RangedEnemyImpl


