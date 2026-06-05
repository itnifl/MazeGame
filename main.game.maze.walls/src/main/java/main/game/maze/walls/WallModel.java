/**
 */
package main.game.maze.walls;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wall Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.walls.WallModel#getMaterials <em>Materials</em>}</li>
 * </ul>
 *
 * @see main.game.maze.walls.WallsPackage#getWallModel()
 * @model
 * @generated
 */
public interface WallModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Materials</b></em>' containment reference list.
	 * The list contents are of type {@link main.game.maze.walls.WallMaterial}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Materials</em>' containment reference list.
	 * @see main.game.maze.walls.WallsPackage#getWallModel_Materials()
	 * @model containment="true"
	 * @generated
	 */
	EList<WallMaterial> getMaterials();

} // WallModel


