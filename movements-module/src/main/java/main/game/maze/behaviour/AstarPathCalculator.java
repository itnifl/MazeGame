/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Astar Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.AstarPathCalculator#getMaxIterations <em>Max Iterations</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getAstarPathCalculator()
 * @model
 * @generated
 */
public interface AstarPathCalculator extends PathCalculator {
	/**
	 * Returns the value of the '<em><b>Max Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When computing the A* algorithm, a maximum of maxIterations iterations are performed. If the character has not been found, the character will take the direction of the nearest of the last iterations points.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Iterations</em>' attribute.
	 * @see #setMaxIterations(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getAstarPathCalculator_MaxIterations()
	 * @model required="true"
	 * @generated
	 */
	int getMaxIterations();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.AstarPathCalculator#getMaxIterations <em>Max Iterations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Iterations</em>' attribute.
	 * @see #getMaxIterations()
	 * @generated
	 */
	void setMaxIterations(int value);

} // AstarPathCalculator
