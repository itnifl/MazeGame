/**
 */
package main.game.maze.behaviour.impl;

import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.MovementBehavior;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Movement Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#isIgnoreWalls <em>Ignore Walls</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getAttackRadius <em>Attack Radius</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#isInstantKillOnCollision <em>Instant Kill On Collision</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getMovementSpeed <em>Movement Speed</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MovementBehaviorImpl extends MinimalEObjectImpl.Container implements MovementBehavior {
	/**
	 * The default value of the '{@link #isIgnoreWalls() <em>Ignore Walls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreWalls()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_WALLS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreWalls() <em>Ignore Walls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreWalls()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreWalls = IGNORE_WALLS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAttackRadius() <em>Attack Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackRadius()
	 * @generated
	 * @ordered
	 */
	protected static final double ATTACK_RADIUS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAttackRadius() <em>Attack Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttackRadius()
	 * @generated
	 * @ordered
	 */
	protected double attackRadius = ATTACK_RADIUS_EDEFAULT;

	/**
	 * The default value of the '{@link #isInstantKillOnCollision() <em>Instant Kill On Collision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInstantKillOnCollision()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INSTANT_KILL_ON_COLLISION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInstantKillOnCollision() <em>Instant Kill On Collision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInstantKillOnCollision()
	 * @generated
	 * @ordered
	 */
	protected boolean instantKillOnCollision = INSTANT_KILL_ON_COLLISION_EDEFAULT;

	/**
	 * The default value of the '{@link #getMovementSpeed() <em>Movement Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMovementSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final double MOVEMENT_SPEED_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMovementSpeed() <em>Movement Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMovementSpeed()
	 * @generated
	 * @ordered
	 */
	protected double movementSpeed = MOVEMENT_SPEED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MovementBehaviorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BehaviourPackage.Literals.MOVEMENT_BEHAVIOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIgnoreWalls() {
		return ignoreWalls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIgnoreWalls(boolean newIgnoreWalls) {
		boolean oldIgnoreWalls = ignoreWalls;
		ignoreWalls = newIgnoreWalls;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS, oldIgnoreWalls, ignoreWalls));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAttackRadius() {
		return attackRadius;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttackRadius(double newAttackRadius) {
		double oldAttackRadius = attackRadius;
		attackRadius = newAttackRadius;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__ATTACK_RADIUS, oldAttackRadius, attackRadius));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInstantKillOnCollision() {
		return instantKillOnCollision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInstantKillOnCollision(boolean newInstantKillOnCollision) {
		boolean oldInstantKillOnCollision = instantKillOnCollision;
		instantKillOnCollision = newInstantKillOnCollision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION, oldInstantKillOnCollision, instantKillOnCollision));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMovementSpeed(double newMovementSpeed) {
		double oldMovementSpeed = movementSpeed;
		movementSpeed = newMovementSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__MOVEMENT_SPEED, oldMovementSpeed, movementSpeed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				return isIgnoreWalls();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ATTACK_RADIUS:
				return getAttackRadius();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				return isInstantKillOnCollision();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__MOVEMENT_SPEED:
				return getMovementSpeed();
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				setIgnoreWalls((Boolean)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ATTACK_RADIUS:
				setAttackRadius((Double)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				setInstantKillOnCollision((Boolean)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__MOVEMENT_SPEED:
				setMovementSpeed((Double)newValue);
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				setIgnoreWalls(IGNORE_WALLS_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ATTACK_RADIUS:
				setAttackRadius(ATTACK_RADIUS_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				setInstantKillOnCollision(INSTANT_KILL_ON_COLLISION_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__MOVEMENT_SPEED:
				setMovementSpeed(MOVEMENT_SPEED_EDEFAULT);
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				return ignoreWalls != IGNORE_WALLS_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ATTACK_RADIUS:
				return attackRadius != ATTACK_RADIUS_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				return instantKillOnCollision != INSTANT_KILL_ON_COLLISION_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__MOVEMENT_SPEED:
				return movementSpeed != MOVEMENT_SPEED_EDEFAULT;
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
		result.append(" (ignoreWalls: ");
		result.append(ignoreWalls);
		result.append(", attackRadius: ");
		result.append(attackRadius);
		result.append(", instantKillOnCollision: ");
		result.append(instantKillOnCollision);
		result.append(", movementSpeed: ");
		result.append(movementSpeed);
		result.append(')');
		return result.toString();
	}

} //MovementBehaviorImpl
