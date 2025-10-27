/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.PathCalculator#getDistanceMethod <em>Distance Method</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPathCalculator()
 * @model abstract="true"
 * @generated
 */
public interface PathCalculator extends EObject {
	/**
	 * Returns the value of the '<em><b>Distance Method</b></em>' attribute.
	 * The literals are from the enumeration {@link main.game.maze.behaviour.DistanceMethod}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Distance Method</em>' attribute.
	 * @see main.game.maze.behaviour.DistanceMethod
	 * @see #setDistanceMethod(DistanceMethod)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPathCalculator_DistanceMethod()
	 * @model required="true"
	 * @generated
	 */
	DistanceMethod getDistanceMethod();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.PathCalculator#getDistanceMethod <em>Distance Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Distance Method</em>' attribute.
	 * @see main.game.maze.behaviour.DistanceMethod
	 * @see #getDistanceMethod()
	 * @generated
	 */
	void setDistanceMethod(DistanceMethod value);

} // PathCalculator
