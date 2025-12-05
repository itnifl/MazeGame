/**
 */
package main.game.maze.walls;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wall Material</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.walls.WallMaterial#getWallBaseType <em>Wall Base Type</em>}</li>
 *   <li>{@link main.game.maze.walls.WallMaterial#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link main.game.maze.walls.WallMaterial#isBreakable <em>Breakable</em>}</li>
 *   <li>{@link main.game.maze.walls.WallMaterial#getHitPoints <em>Hit Points</em>}</li>
 *   <li>{@link main.game.maze.walls.WallMaterial#getId <em>Id</em>}</li>
 *   <li>{@link main.game.maze.walls.WallMaterial#getBaseImage <em>Base Image</em>}</li>
 * </ul>
 *
 * @see main.game.maze.walls.WallsPackage#getWallMaterial()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore/OCL ValidHitPoints='(breakable = false implies hitPoints = 0) and\n(breakable = true implies hitPoints &gt; 0)'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValidHitPoints'"
 * @generated
 */
public interface WallMaterial extends EObject {
	/**
	 * Returns the value of the '<em><b>Wall Base Type</b></em>' attribute.
	 * The default value is <code>"STEEL"</code>.
	 * The literals are from the enumeration {@link main.game.maze.walls.WallMaterialBaseType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wall Base Type</em>' attribute.
	 * @see main.game.maze.walls.WallMaterialBaseType
	 * @see #setWallBaseType(WallMaterialBaseType)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_WallBaseType()
	 * @model default="STEEL" dataType="main.game.maze.walls.WallMaterialBaseType" required="true"
	 * @generated
	 */
	WallMaterialBaseType getWallBaseType();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#getWallBaseType <em>Wall Base Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wall Base Type</em>' attribute.
	 * @see main.game.maze.walls.WallMaterialBaseType
	 * @see #getWallBaseType()
	 * @generated
	 */
	void setWallBaseType(WallMaterialBaseType value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_DisplayName()
	 * @model required="true"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Breakable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Breakable</em>' attribute.
	 * @see #setBreakable(boolean)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_Breakable()
	 * @model default="false" required="true" transient="true"
	 * @generated
	 */
	boolean isBreakable();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#isBreakable <em>Breakable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Breakable</em>' attribute.
	 * @see #isBreakable()
	 * @generated
	 */
	void setBreakable(boolean value);

	/**
	 * Returns the value of the '<em><b>Hit Points</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hit Points</em>' attribute.
	 * @see #setHitPoints(int)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_HitPoints()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getHitPoints();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#getHitPoints <em>Hit Points</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hit Points</em>' attribute.
	 * @see #getHitPoints()
	 * @generated
	 */
	void setHitPoints(int value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_Id()
	 * @model id="true" required="true" derived="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Base Image</b></em>' attribute.
	 * The default value is <code>"/main/game/maze/baseWall.png"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Image</em>' attribute.
	 * @see #setBaseImage(String)
	 * @see main.game.maze.walls.WallsPackage#getWallMaterial_BaseImage()
	 * @model default="/main/game/maze/baseWall.png"
	 * @generated
	 */
	String getBaseImage();

	/**
	 * Sets the value of the '{@link main.game.maze.walls.WallMaterial#getBaseImage <em>Base Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Image</em>' attribute.
	 * @see #getBaseImage()
	 * @generated
	 */
	void setBaseImage(String value);

} // WallMaterial
