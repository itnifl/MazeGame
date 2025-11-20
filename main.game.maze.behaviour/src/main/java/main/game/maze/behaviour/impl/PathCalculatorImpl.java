/**
 */
package main.game.maze.behaviour.impl;

import java.lang.reflect.InvocationTargetException;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.behaviour.PathCalculator;

import main.game.maze.behaviour.Position;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Path Calculator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.PathCalculatorImpl#getDistanceMethod <em>Distance Method</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PathCalculatorImpl extends MinimalEObjectImpl.Container implements PathCalculator {
	/**
	 * The default value of the '{@link #getDistanceMethod() <em>Distance Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistanceMethod()
	 * @generated
	 * @ordered
	 */
	protected static final DistanceMethod DISTANCE_METHOD_EDEFAULT = DistanceMethod.MANHATTAN;

	/**
	 * The cached value of the '{@link #getDistanceMethod() <em>Distance Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistanceMethod()
	 * @generated
	 * @ordered
	 */
	protected DistanceMethod distanceMethod = DISTANCE_METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PathCalculatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.PATH_CALCULATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DistanceMethod getDistanceMethod() {
		return distanceMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDistanceMethod(DistanceMethod newDistanceMethod) {
		DistanceMethod oldDistanceMethod = distanceMethod;
		distanceMethod = newDistanceMethod == null ? DISTANCE_METHOD_EDEFAULT : newDistanceMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD, oldDistanceMethod, distanceMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Position> compute(Position target) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				return getDistanceMethod();
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
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				setDistanceMethod((DistanceMethod)newValue);
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
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				setDistanceMethod(DISTANCE_METHOD_EDEFAULT);
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
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				return distanceMethod != DISTANCE_METHOD_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case BehaviourPackage.PATH_CALCULATOR___COMPUTE__POSITION:
				return compute((Position)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (distanceMethod: ");
		result.append(distanceMethod);
		result.append(')');
		return result.toString();
	}

} //PathCalculatorImpl
