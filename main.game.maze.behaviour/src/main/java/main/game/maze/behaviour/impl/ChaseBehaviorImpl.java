/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.ChaseBehavior;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.Position;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
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
	 * The cached value of the '{@link #getRelativePositionTarget() <em>Relative Position Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePositionTarget()
	 * @generated
	 * @ordered
	 */
	protected Position relativePositionTarget;

	/**
	 * The cached value of the '{@link #getPathcalculator() <em>Pathcalculator</em>}' containment reference.
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
		return relativePositionTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelativePositionTarget(Position newRelativePositionTarget, NotificationChain msgs) {
		Position oldRelativePositionTarget = relativePositionTarget;
		relativePositionTarget = newRelativePositionTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, oldRelativePositionTarget, newRelativePositionTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelativePositionTarget(Position newRelativePositionTarget) {
		if (newRelativePositionTarget != relativePositionTarget) {
			NotificationChain msgs = null;
			if (relativePositionTarget != null)
				msgs = ((InternalEObject)relativePositionTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, null, msgs);
			if (newRelativePositionTarget != null)
				msgs = ((InternalEObject)newRelativePositionTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, null, msgs);
			msgs = basicSetRelativePositionTarget(newRelativePositionTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET, newRelativePositionTarget, newRelativePositionTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PathCalculator getPathcalculator() {
		return pathcalculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPathcalculator(PathCalculator newPathcalculator, NotificationChain msgs) {
		PathCalculator oldPathcalculator = pathcalculator;
		pathcalculator = newPathcalculator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, newPathcalculator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPathcalculator(PathCalculator newPathcalculator) {
		if (newPathcalculator != pathcalculator) {
			NotificationChain msgs = null;
			if (pathcalculator != null)
				msgs = ((InternalEObject)pathcalculator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, null, msgs);
			if (newPathcalculator != null)
				msgs = ((InternalEObject)newPathcalculator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, null, msgs);
			msgs = basicSetPathcalculator(newPathcalculator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR, newPathcalculator, newPathcalculator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BehaviourPackage.CHASE_BEHAVIOR__RELATIVE_POSITION_TARGET:
				return basicSetRelativePositionTarget(null, msgs);
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				return basicSetPathcalculator(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
				return getRelativePositionTarget();
			case BehaviourPackage.CHASE_BEHAVIOR__PATHCALCULATOR:
				return getPathcalculator();
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


