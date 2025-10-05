/**
 */
package main.game.maze.opponents;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>INon Tangient Character</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.INonTangientCharacter#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}</li>
 * </ul>
 *
 * @see main.game.maze.opponents.OpponentsPackage#getINonTangientCharacter()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface INonTangientCharacter extends EObject {
	/**
	 * Returns the value of the '<em><b>Non Tangibility Energy</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Tangibility Energy</em>' attribute.
	 * @see #setNonTangibilityEnergy(int)
	 * @see main.game.maze.opponents.OpponentsPackage#getINonTangientCharacter_NonTangibilityEnergy()
	 * @model default="100"
	 * @generated
	 */
	int getNonTangibilityEnergy();

	/**
	 * Sets the value of the '{@link main.game.maze.opponents.INonTangientCharacter#getNonTangibilityEnergy <em>Non Tangibility Energy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Tangibility Energy</em>' attribute.
	 * @see #getNonTangibilityEnergy()
	 * @generated
	 */
	void setNonTangibilityEnergy(int value);

} // INonTangientCharacter
