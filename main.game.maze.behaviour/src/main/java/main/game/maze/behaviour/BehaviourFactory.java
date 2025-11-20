/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.behaviour.BehaviourPackage
 * @generated
 */
public interface BehaviourFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BehaviourFactory eINSTANCE = main.game.maze.behaviour.impl.BehaviourFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Random Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Random Behavior</em>'.
	 * @generated
	 */
	RandomBehavior createRandomBehavior();

	/**
	 * Returns a new object of class '<em>Patrol Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Patrol Behavior</em>'.
	 * @generated
	 */
	PatrolBehavior createPatrolBehavior();

	/**
	 * Returns a new object of class '<em>Chase Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Chase Behavior</em>'.
	 * @generated
	 */
	ChaseBehavior createChaseBehavior();

	/**
	 * Returns a new object of class '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Position</em>'.
	 * @generated
	 */
	Position createPosition();

	/**
	 * Returns a new object of class '<em>Patrol Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Patrol Point</em>'.
	 * @generated
	 */
	PatrolPoint createPatrolPoint();

	/**
	 * Returns a new object of class '<em>Dijkstra Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dijkstra Path Calculator</em>'.
	 * @generated
	 */
	DijkstraPathCalculator createDijkstraPathCalculator();

	/**
	 * Returns a new object of class '<em>Astar Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Astar Path Calculator</em>'.
	 * @generated
	 */
	AstarPathCalculator createAstarPathCalculator();

	/**
	 * Returns a new object of class '<em>Local Path Calculator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Path Calculator</em>'.
	 * @generated
	 */
	LocalPathCalculator createLocalPathCalculator();

	/**
	 * Returns a new object of class '<em>Patrol Zone</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Patrol Zone</em>'.
	 * @generated
	 */
	PatrolZone createPatrolZone();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BehaviourPackage getBehaviourPackage();

} //BehaviourFactory
