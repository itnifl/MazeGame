/**
 */
package main.game.maze.behaviour.impl;

import java.lang.reflect.InvocationTargetException;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.CharacterEvent;
import main.game.maze.behaviour.MovementBehavior;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Character Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.CharacterEventImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.CharacterEventImpl#getSubscriber <em>Subscriber</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CharacterEventImpl extends MinimalEObjectImpl.Container implements CharacterEvent {
	/**
	 * The default value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected static final double PROBABILITY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected double probability = PROBABILITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubscriber() <em>Subscriber</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubscriber()
	 * @generated
	 * @ordered
	 */
	protected MovementBehavior subscriber;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CharacterEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.CHARACTER_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProbability(double newProbability) {
		double oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHARACTER_EVENT__PROBABILITY, oldProbability, probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MovementBehavior getSubscriber() {
		if (subscriber != null && subscriber.eIsProxy()) {
			InternalEObject oldSubscriber = (InternalEObject)subscriber;
			subscriber = (MovementBehavior)eResolveProxy(oldSubscriber);
			if (subscriber != oldSubscriber) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER, oldSubscriber, subscriber));
			}
		}
		return subscriber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MovementBehavior basicGetSubscriber() {
		return subscriber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubscriber(MovementBehavior newSubscriber) {
		MovementBehavior oldSubscriber = subscriber;
		subscriber = newSubscriber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER, oldSubscriber, subscriber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifySubscribers() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * Helper to check if the event should fire based on probability. 
	 * @return true if the event should fire
	 */
	protected boolean shouldFire() {
	    double prob = getProbability();
	    if (prob <= 0) return false;
	    if (prob >= 1) return true;
	    return Math.random() < prob;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.CHARACTER_EVENT__PROBABILITY:
				return getProbability();
			case BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER:
				if (resolve) return getSubscriber();
				return basicGetSubscriber();
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
			case BehaviourPackage.CHARACTER_EVENT__PROBABILITY:
				setProbability((Double)newValue);
				return;
			case BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER:
				setSubscriber((MovementBehavior)newValue);
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
			case BehaviourPackage.CHARACTER_EVENT__PROBABILITY:
				setProbability(PROBABILITY_EDEFAULT);
				return;
			case BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER:
				setSubscriber((MovementBehavior)null);
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
			case BehaviourPackage.CHARACTER_EVENT__PROBABILITY:
				return probability != PROBABILITY_EDEFAULT;
			case BehaviourPackage.CHARACTER_EVENT__SUBSCRIBER:
				return subscriber != null;
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
			case BehaviourPackage.CHARACTER_EVENT___NOTIFY_SUBSCRIBERS:
				notifySubscribers();
				return null;
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
		result.append(" (probability: ");
		result.append(probability);
		result.append(')');
		return result.toString();
	}

} //CharacterEventImpl
