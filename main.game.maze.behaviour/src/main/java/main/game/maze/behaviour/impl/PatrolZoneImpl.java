/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.PatrolZone;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Patrol Zone</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolZoneImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolZoneImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolZoneImpl#getTopLeft <em>Top Left</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatrolZoneImpl extends MinimalEObjectImpl.Container implements PatrolZone {
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final double WIDTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected double width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double HEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected double height = HEIGHT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTopLeft() <em>Top Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopLeft()
	 * @generated
	 * @ordered
	 */
	protected Position topLeft;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatrolZoneImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.PATROL_ZONE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(double newWidth) {
		double oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_ZONE__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeight(double newHeight) {
		double oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_ZONE__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getTopLeft() {
		if (topLeft != null && topLeft.eIsProxy()) {
			InternalEObject oldTopLeft = (InternalEObject)topLeft;
			topLeft = (Position)eResolveProxy(oldTopLeft);
			if (topLeft != oldTopLeft) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.PATROL_ZONE__TOP_LEFT, oldTopLeft, topLeft));
			}
		}
		return topLeft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetTopLeft() {
		return topLeft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTopLeft(Position newTopLeft) {
		Position oldTopLeft = topLeft;
		topLeft = newTopLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_ZONE__TOP_LEFT, oldTopLeft, topLeft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.PATROL_ZONE__WIDTH:
				return getWidth();
			case BehaviourPackage.PATROL_ZONE__HEIGHT:
				return getHeight();
			case BehaviourPackage.PATROL_ZONE__TOP_LEFT:
				if (resolve) return getTopLeft();
				return basicGetTopLeft();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BehaviourPackage.PATROL_ZONE__WIDTH:
				setWidth((Double)newValue);
				return;
			case BehaviourPackage.PATROL_ZONE__HEIGHT:
				setHeight((Double)newValue);
				return;
			case BehaviourPackage.PATROL_ZONE__TOP_LEFT:
				setTopLeft((Position)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BehaviourPackage.PATROL_ZONE__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case BehaviourPackage.PATROL_ZONE__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case BehaviourPackage.PATROL_ZONE__TOP_LEFT:
				setTopLeft((Position)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BehaviourPackage.PATROL_ZONE__WIDTH:
				return width != WIDTH_EDEFAULT;
			case BehaviourPackage.PATROL_ZONE__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case BehaviourPackage.PATROL_ZONE__TOP_LEFT:
				return topLeft != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(')');
		return result.toString();
	}

} //PatrolZoneImpl


