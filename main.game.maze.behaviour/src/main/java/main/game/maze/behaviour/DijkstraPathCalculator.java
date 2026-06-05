/**
 */
package main.game.maze.behaviour;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dijkstra Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.DijkstraPathCalculator#getMaxPathLength <em>Max Path Length</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getDijkstraPathCalculator()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidDijsktraPath'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ValidDijsktraPath='self.maxPathLength &gt; 0'"
 * @generated
 */
public interface DijkstraPathCalculator extends PathCalculator {
	/**
	 * Returns the value of the '<em><b>Max Path Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When computing the Dijkstra algorithm, a maximum of maxIterations iterations are performed. If the character has not been found, the character will take the direction of the nearest of the last iterations points.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Path Length</em>' attribute.
	 * @see #setMaxPathLength(int)
	 * @see main.game.maze.behaviour.BehaviourPackage#getDijkstraPathCalculator_MaxPathLength()
	 * @model required="true"
	 * @generated
	 */
	int getMaxPathLength();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.DijkstraPathCalculator#getMaxPathLength <em>Max Path Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Path Length</em>' attribute.
	 * @see #getMaxPathLength()
	 * @generated
	 */
	void setMaxPathLength(int value);

} // DijkstraPathCalculator


