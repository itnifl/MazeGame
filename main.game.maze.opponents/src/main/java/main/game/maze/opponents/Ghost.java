/**
 */
package main.game.maze.opponents;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ghost</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.Ghost#getAttackDamage <em>Attack Damage</em>}</li>
 *   <li>{@link main.game.maze.opponents.Ghost#getVisibilityLevel <em>Visibility Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.Ghost#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getGhost()
 * @model
 * @generated
 */
public interface Ghost extends CharacterType {
	/**
	 * Returns the value of the '<em><b>Attack Damage</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attack Damage</em>' attribute.
	 * @see #setAttackDamage(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_AttackDamage()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getAttackDamage();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Ghost#getAttackDamage <em>Attack Damage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attack Damage</em>' attribute.
	 * @see #getAttackDamage()
	 * @generated
	 */
	void setAttackDamage(int value);

	/**
	 * Returns the value of the '<em><b>Visibility Level</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility Level</em>' attribute.
	 * @see #setVisibilityLevel(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_VisibilityLevel()
	 * @model default="100" required="true"
	 * @generated
	 */
	int getVisibilityLevel();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Ghost#getVisibilityLevel <em>Visibility Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility Level</em>' attribute.
	 * @see #getVisibilityLevel()
	 * @generated
	 */
	void setVisibilityLevel(int value);

	/**
	 * Returns the value of the '<em><b>Non Tangibility Energy</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Tangibility Energy</em>' attribute.
	 * @see #setNonTangibilityEnergy(double)
	 * @see main.game.maze.opponents.OpponentsPackage#getGhost_NonTangibilityEnergy()
	 * @model default="100" required="true"
	 * @generated
	 */
	double getNonTangibilityEnergy();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.Ghost#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Tangibility Energy</em>' attribute.
	 * @see #getNonTangibilityEnergy()
	 * @generated
	 */
	void setNonTangibilityEnergy(double value);

} // Ghost


