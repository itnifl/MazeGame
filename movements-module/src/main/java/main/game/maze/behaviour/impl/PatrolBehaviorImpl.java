/**
 */
package main.game.maze.behaviour.impl;

import java.util.Collection;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPoint;

import main.game.maze.behaviour.PatrolZone;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Patrol Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getPath <em>Path</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getCurrentIndex <em>Current Index</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getPathcalculator <em>Pathcalculator</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getPatrolZone <em>Patrol Zone</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatrolBehaviorImpl extends MovementBehaviorImpl implements PatrolBehavior {
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected EList<PatrolPoint> path;

	/**
	 * The default value of the '{@link #getCurrentIndex() <em>Current Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int CURRENT_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCurrentIndex() <em>Current Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentIndex()
	 * @generated
	 * @ordered
	 */
	protected int currentIndex = CURRENT_INDEX_EDEFAULT;

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
	 * The cached value of the '{@link #getPatrolZone() <em>Patrol Zone</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatrolZone()
	 * @generated
	 * @ordered
	 */
	protected PatrolZone patrolZone;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatrolBehaviorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.PATROL_BEHAVIOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PatrolPoint> getPath() {
		if (path == null) {
			path = new EObjectResolvingEList<PatrolPoint>(PatrolPoint.class, this, BehaviourPackage.PATROL_BEHAVIOR__PATH);
		}
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCurrentIndex(int newCurrentIndex) {
		int oldCurrentIndex = currentIndex;
		currentIndex = newCurrentIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX, oldCurrentIndex, currentIndex));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, pathcalculator));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, pathcalculator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PatrolZone getPatrolZone() {
		if (patrolZone != null && patrolZone.eIsProxy()) {
			InternalEObject oldPatrolZone = (InternalEObject)patrolZone;
			patrolZone = (PatrolZone)eResolveProxy(oldPatrolZone);
			if (patrolZone != oldPatrolZone) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE, oldPatrolZone, patrolZone));
			}
		}
		return patrolZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PatrolZone basicGetPatrolZone() {
		return patrolZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPatrolZone(PatrolZone newPatrolZone) {
		PatrolZone oldPatrolZone = patrolZone;
		patrolZone = newPatrolZone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE, oldPatrolZone, patrolZone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				return getPath();
			case BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX:
				return getCurrentIndex();
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
				if (resolve) return getPathcalculator();
				return basicGetPathcalculator();
			case BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE:
				if (resolve) return getPatrolZone();
				return basicGetPatrolZone();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				getPath().clear();
				getPath().addAll((Collection<? extends PatrolPoint>)newValue);
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX:
				setCurrentIndex((Integer)newValue);
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
				setPathcalculator((PathCalculator)newValue);
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE:
				setPatrolZone((PatrolZone)newValue);
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
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				getPath().clear();
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX:
				setCurrentIndex(CURRENT_INDEX_EDEFAULT);
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
				setPathcalculator((PathCalculator)null);
				return;
			case BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE:
				setPatrolZone((PatrolZone)null);
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
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				return path != null && !path.isEmpty();
			case BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX:
				return currentIndex != CURRENT_INDEX_EDEFAULT;
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
				return pathcalculator != null;
			case BehaviourPackage.PATROL_BEHAVIOR__PATROL_ZONE:
				return patrolZone != null;
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
		result.append(" (currentIndex: ");
		result.append(currentIndex);
		result.append(')');
		return result.toString();
	}

} //PatrolBehaviorImpl
