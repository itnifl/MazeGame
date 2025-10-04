/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.common.util.EList;


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
 *   <li>{@link main.game.maze.opponents.Zombie#getBehavior <em>Behavior</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getZombieLootTable <em>Zombie Loot Table</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getInfectionLevel <em>Infection Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.Zombie#getResurrectionTime <em>Resurrection Time</em>}</li>
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
	 * Returns the value of the '<em><b>Behavior</b></em>' attribute.
	 * The default value is <code>"WANDER"</code>.
	 * The literals are from the enumeration {@link main.game.maze.opponents.BehaviorType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.opponents.BehaviorType
	 * @see #setBehavior(BehaviorType)
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_Behavior()
	 * @model default="WANDER"
	 * @generated
	 */
	BehaviorType getBehavior();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Zombie#getBehavior <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' attribute.
	 * @see main.game.maze.opponents.BehaviorType
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(BehaviorType value);

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
	 * Returns the value of the '<em><b>Infection Level</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Infection Level</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_InfectionLevel()
	 * @model default="1" upper="10"
	 * @generated
	 */
	EList<Integer> getInfectionLevel();

	/**
	 * Returns the value of the '<em><b>Resurrection Time</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resurrection Time</em>' attribute list.
	 * @see main.game.maze.opponents.OpponentsPackage#getZombie_ResurrectionTime()
	 * @model default="0" upper="180"
	 * @generated
	 */
	EList<Integer> getResurrectionTime();

} // Zombie
