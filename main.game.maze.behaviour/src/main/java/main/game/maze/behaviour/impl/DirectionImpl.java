/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.Direction;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Direction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.DirectionImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.DirectionImpl#getEndPosition <em>End Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DirectionImpl extends MinimalEObjectImpl.Container implements Direction {
	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected Position startPosition;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected Position endPosition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DirectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.DIRECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getStartPosition() {
		if (startPosition != null && startPosition.eIsProxy()) {
			InternalEObject oldStartPosition = (InternalEObject)startPosition;
			startPosition = (Position)eResolveProxy(oldStartPosition);
			if (startPosition != oldStartPosition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.DIRECTION__START_POSITION, oldStartPosition, startPosition));
			}
		}
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartPosition(Position newStartPosition) {
		Position oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.DIRECTION__START_POSITION, oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getEndPosition() {
		if (endPosition != null && endPosition.eIsProxy()) {
			InternalEObject oldEndPosition = (InternalEObject)endPosition;
			endPosition = (Position)eResolveProxy(oldEndPosition);
			if (endPosition != oldEndPosition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.DIRECTION__END_POSITION, oldEndPosition, endPosition));
			}
		}
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndPosition(Position newEndPosition) {
		Position oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.DIRECTION__END_POSITION, oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.DIRECTION__START_POSITION:
				if (resolve) return getStartPosition();
				return basicGetStartPosition();
			case BehaviourPackage.DIRECTION__END_POSITION:
				if (resolve) return getEndPosition();
				return basicGetEndPosition();
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
			case BehaviourPackage.DIRECTION__START_POSITION:
				setStartPosition((Position)newValue);
				return;
			case BehaviourPackage.DIRECTION__END_POSITION:
				setEndPosition((Position)newValue);
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
			case BehaviourPackage.DIRECTION__START_POSITION:
				setStartPosition((Position)null);
				return;
			case BehaviourPackage.DIRECTION__END_POSITION:
				setEndPosition((Position)null);
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
			case BehaviourPackage.DIRECTION__START_POSITION:
				return startPosition != null;
			case BehaviourPackage.DIRECTION__END_POSITION:
				return endPosition != null;
		}
		return super.eIsSet(featureID);
	}

} //DirectionImpl
