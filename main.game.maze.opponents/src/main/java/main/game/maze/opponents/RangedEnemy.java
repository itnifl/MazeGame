/**
 */
package main.game.maze.opponents;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ranged Enemy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getAttackRange <em>Attack Range</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getAttackCooldownMs <em>Attack Cooldown Ms</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getProjectileSpeed <em>Projectile Speed</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getProjectileType <em>Projectile Type</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getSplashRadius <em>Splash Radius</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getArcHeight <em>Arc Height</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getProjectileImage <em>Projectile Image</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getExplosionImage <em>Explosion Image</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getExplosionSound <em>Explosion Sound</em>}</li>
 *   <li>{@link main.game.maze.opponents.RangedEnemy#getThrowSound <em>Throw Sound</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy()
 * @model abstract="true"
 * @generated
 */
public interface RangedEnemy extends CharacterType {
	/**
	 * Returns the value of the '<em><b>Attack Range</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Range</em>' attribute.
	 * @see #setAttackRange(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_AttackRange()
	 * @model default="50" required="true"
	 * @generated
	 */
	double getAttackRange();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getAttackRange <em>Attack Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Range</em>' attribute.
	 * @see #getAttackRange()
	 * @generated
	 */
	void setAttackRange(double value);

	/**
	 * Returns the value of the '<em><b>Attack Cooldown Ms</b></em>' attribute.
	 * The default value is <code>"10000"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Cooldown Ms</em>' attribute.
	 * @see #setAttackCooldownMs(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_AttackCooldownMs()
	 * @model default="10000" required="true"
	 * @generated
	 */
	int getAttackCooldownMs();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getAttackCooldownMs <em>Attack Cooldown Ms</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Cooldown Ms</em>' attribute.
	 * @see #getAttackCooldownMs()
	 * @generated
	 */
	void setAttackCooldownMs(int value);

	/**
	 * Returns the value of the '<em><b>Attack Damage</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Damage</em>' attribute.
	 * @see #setAttackDamage(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_AttackDamage()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getAttackDamage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getAttackDamage <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Damage</em>' attribute.
	 * @see #getAttackDamage()
	 * @generated
	 */
	void setAttackDamage(int value);

	/**
	 * Returns the value of the '<em><b>Projectile Speed</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projectile Speed</em>' attribute.
	 * @see #setProjectileSpeed(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ProjectileSpeed()
	 * @model default="0" required="true"
	 * @generated
	 */
	double getProjectileSpeed();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getProjectileSpeed <em>Projectile Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projectile Speed</em>' attribute.
	 * @see #getProjectileSpeed()
	 * @generated
	 */
	void setProjectileSpeed(double value);

	/**
	 * Returns the value of the '<em><b>Projectile Type</b></em>' attribute.
	 * The literals are from the enumeration {@link main.game.maze.opponents.ProjectileType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projectile Type</em>' attribute.
	 * @see main.game.maze.opponents.ProjectileType
	 * @see #setProjectileType(ProjectileType)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ProjectileType()
	 * @model required="true"
	 * @generated
	 */
	ProjectileType getProjectileType();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getProjectileType <em>Projectile Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projectile Type</em>' attribute.
	 * @see main.game.maze.opponents.ProjectileType
	 * @see #getProjectileType()
	 * @generated
	 */
	void setProjectileType(ProjectileType value);

	/**
	 * Returns the value of the '<em><b>Splash Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Splash Radius</em>' attribute.
	 * @see #setSplashRadius(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_SplashRadius()
	 * @model required="true"
	 * @generated
	 */
	double getSplashRadius();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getSplashRadius <em>Splash Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Splash Radius</em>' attribute.
	 * @see #getSplashRadius()
	 * @generated
	 */
	void setSplashRadius(double value);

	/**
	 * Returns the value of the '<em><b>Arc Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arc Height</em>' attribute.
	 * @see #setArcHeight(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ArcHeight()
	 * @model required="true"
	 * @generated
	 */
	double getArcHeight();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getArcHeight <em>Arc Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arc Height</em>' attribute.
	 * @see #getArcHeight()
	 * @generated
	 */
	void setArcHeight(double value);

	/**
	 * Returns the value of the '<em><b>Projectile Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projectile Image</em>' attribute.
	 * @see #setProjectileImage(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ProjectileImage()
	 * @model
	 * @generated
	 */
	String getProjectileImage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getProjectileImage <em>Projectile Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projectile Image</em>' attribute.
	 * @see #getProjectileImage()
	 * @generated
	 */
	void setProjectileImage(String value);

	/**
	 * Returns the value of the '<em><b>Explosion Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Explosion Image</em>' attribute.
	 * @see #setExplosionImage(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ExplosionImage()
	 * @model
	 * @generated
	 */
	String getExplosionImage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getExplosionImage <em>Explosion Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Explosion Image</em>' attribute.
	 * @see #getExplosionImage()
	 * @generated
	 */
	void setExplosionImage(String value);

	/**
	 * Returns the value of the '<em><b>Explosion Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Explosion Sound</em>' attribute.
	 * @see #setExplosionSound(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ExplosionSound()
	 * @model
	 * @generated
	 */
	String getExplosionSound();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getExplosionSound <em>Explosion Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Explosion Sound</em>' attribute.
	 * @see #getExplosionSound()
	 * @generated
	 */
	void setExplosionSound(String value);

	/**
	 * Returns the value of the '<em><b>Throw Sound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Throw Sound</em>' attribute.
	 * @see #setThrowSound(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getRangedEnemy_ThrowSound()
	 * @model
	 * @generated
	 */
	String getThrowSound();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.RangedEnemy#getThrowSound <em>Throw Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Throw Sound</em>' attribute.
	 * @see #getThrowSound()
	 * @generated
	 */
	void setThrowSound(String value);

} // RangedEnemy


