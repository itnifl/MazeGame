/**
 */
package main.game.maze.opponents;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Zombie</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.Zombie#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getZombieLootTable <em>Zombie Loot Table</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getInfectionLevel <em>Infection Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getResurrectionTime <em>Resurrection Time</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getTouchSound <em>Touch Sound</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getZombie()
 * @model
 * @generated
 */
public interface Zombie extends CharacterType {
	/**
	 * Returns the value of the '<em><b>Attack Damage</b></em>' attribute.
	 * The default value is <code>"10"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Damage</em>' attribute.
	 * @see #setAttackDamage(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_AttackDamage()
	 * @model default="10"
	 * @generated
	 */
	int getAttackDamage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getAttackDamage <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Damage</em>' attribute.
	 * @see #getAttackDamage()
	 * @generated
	 */
	void setAttackDamage(int value);

	/**
	 * Returns the value of the '<em><b>Zombie Loot Table</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zombie Loot Table</em>' reference.
	 * @see #setZombieLootTable(LootTable)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_ZombieLootTable()
	 * @model
	 * @generated
	 */
	LootTable getZombieLootTable();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getZombieLootTable <em>Zombie Loot Table</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zombie Loot Table</em>' reference.
	 * @see #getZombieLootTable()
	 * @generated
	 */
	void setZombieLootTable(LootTable value);

	/**
	 * Returns the value of the '<em><b>Infection Level</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Infection Level</em>' attribute.
	 * @see #setInfectionLevel(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_InfectionLevel()
	 * @model default="1"
	 * @generated
	 */
	int getInfectionLevel();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getInfectionLevel <em>Infection Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Infection Level</em>' attribute.
	 * @see #getInfectionLevel()
	 * @generated
	 */
	void setInfectionLevel(int value);

	/**
	 * Returns the value of the '<em><b>Resurrection Time</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resurrection Time</em>' attribute.
	 * @see #setResurrectionTime(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_ResurrectionTime()
	 * @model default="0"
	 * @generated
	 */
	int getResurrectionTime();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getResurrectionTime <em>Resurrection Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resurrection Time</em>' attribute.
	 * @see #getResurrectionTime()
	 * @generated
	 */
	void setResurrectionTime(int value);

	/**
	 * Returns the value of the '<em><b>Touch Sound</b></em>' attribute.
	 * The default value is <code>"/main/game/maze/zombieScream.mp3"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Touch Sound</em>' attribute.
	 * @see #setTouchSound(String)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_TouchSound()
	 * @model default="/main/game/maze/zombieScream.mp3"
	 * @generated
	 */
	String getTouchSound();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getTouchSound <em>Touch Sound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Touch Sound</em>' attribute.
	 * @see #getTouchSound()
	 * @generated
	 */
	void setTouchSound(String value);

} // Zombie
