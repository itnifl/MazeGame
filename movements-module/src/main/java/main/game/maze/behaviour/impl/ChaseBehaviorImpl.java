/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.ChaseBehavior;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chase Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.ChaseBehaviorImpl#getRelativePositionTarget <em>Relative Position Target</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.ChaseBehaviorImpl#getPathcalculator <em>Pathcalculator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChaseBehaviorImpl extends MovementBehaviorImpl implements ChaseBehavior {
	/**
	 * The cached value of the '{@link #getRelativePositionTarget() <em>Relative Position Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePositionTarget()
	 * @generated
	 * @ordered
	 */
	protected Position relativePositionTarget;

	/**
	 * The cached value of the '{@link #getPathcalculator() <em>Pathcalculator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathcalculator()
	 * @generated
	 * @ordered
	 */
	protected PathCalculator pathcalculator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChaseBehaviorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.CHASE_BEHAVIOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getRelativePositionTarget() {
		if (relativePositionTarget != null && relativePositionTarget.eIsProxy()) {
			InternalEObject oldRelativePositionTarget = (InternalEObject)relativePositionTarget;
			relativePositionTarget = (Position)eResolveProxy(oldRelativePositionTarget);
			if (relativePositionTarget != oldRelativePositionTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, oldRelativePositionTarget, relativePositionTarget));
			}
		}
		return relativePositionTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetRelativePositionTarget() {
		return relativePositionTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelativePositionTarget(Position newRelativePositionTarget) {
		Position oldRelativePositionTarget = relativePositionTarget;
		relativePositionTarget = newRelativePositionTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, oldRelativePositionTarget, relativePositionTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PathCalculator getPathcalculator() {
		if (pathcalculator != null && pathcalculator.eIsProxy()) {
			InternalEObject oldPathcalculator = (InternalEObject)pathcalculator;
			pathcalculator = (PathCalculator)eResolveProxy(oldPathcalculator);
			if (pathcalculator != oldPathcalculator) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, pathcalculator));
			}
		}
		return pathcalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PathCalculator basicGetPathcalculator() {
		return pathcalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPathcalculator(PathCalculator newPathcalculator) {
		PathCalculator oldPathcalculator = pathcalculator;
		pathcalculator = newPathcalculator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, pathcalculator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET:
				if (resolve) return getRelativePositionTarget();
				return basicGetRelativePositionTarget();
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				if (resolve) return getPathcalculator();
				return basicGetPathcalculator();
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
			case BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET:
				setRelativePositionTarget((Position)newValue);
				return;
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				setPathcalculator((PathCalculator)newValue);
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
			case BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET:
				setRelativePositionTarget((Position)null);
				return;
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				setPathcalculator((PathCalculator)null);
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
			case BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET:
				return relativePositionTarget != null;
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				return pathcalculator != null;
		}
		return super.eIsSet(featureID);
	}

} //ChaseBehaviorImpl
