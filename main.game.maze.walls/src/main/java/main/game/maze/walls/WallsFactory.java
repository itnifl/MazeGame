/**
 */
package main.game.maze.walls;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see main.game.maze.walls.WallsPackage
 * @generated
 */
public interface WallsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WallsFactory eINSTANCE = main.game.maze.walls.impl.WallsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Wall Material</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wall Material</em>'.
	 * @generated
	 */
	WallMaterial createWallMaterial();

	/**
	 * Returns a new object of class '<em>Wall Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wall Model</em>'.
	 * @generated
	 */
	WallModel createWallModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	WallsPackage getWallsPackage();

} //WallsFactory


