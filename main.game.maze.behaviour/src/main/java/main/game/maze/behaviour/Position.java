/**
 */
package main.game.maze.behaviour;

import org.eclipse.emf.ecore.EObject;

import main.game.maze.mazeworld.Point2D;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.Position#getPosX <em>Pos X</em>}</li>
 *   <li>{@link main.game.maze.behaviour.Position#getPosY <em>Pos Y</em>}</li>
 * </ul>
 *
 * @see main.game.maze.behaviour.BehaviourPackage#getPosition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositivePositions'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot PositivePositions='self.posX &gt;= 0 and self.posY &gt;= 0'"
 * @generated
 */
public interface Position extends EObject {
	/**
	 * Returns the value of the '<em><b>Pos X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pos X</em>' attribute.
	 * @see #setPosX(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPosition_PosX()
	 * @model required="true"
	 * @generated
	 */
	double getPosX();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.Position#getPosX <em>Pos X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pos X</em>' attribute.
	 * @see #getPosX()
	 * @generated
	 */
	void setPosX(double value);

	/**
	 * Returns the value of the '<em><b>Pos Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pos Y</em>' attribute.
	 * @see #setPosY(double)
	 * @see main.game.maze.behaviour.BehaviourPackage#getPosition_PosY()
	 * @model required="true"
	 * @generated
	 */
	double getPosY();

	/**
	 * Sets the value of the '{@link main.game.maze.behaviour.Position#getPosY <em>Pos Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pos Y</em>' attribute.
	 * @see #getPosY()
	 * @generated
	 */
	void setPosY(double value);

	Point2D toPoint2D();

} // Position


