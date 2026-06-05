/**
 */
package main.game.maze.behaviour.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import main.game.maze.behaviour.AttackEvent;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.Direction;
import main.game.maze.behaviour.HealthEvent;
import main.game.maze.behaviour.MovementBehavior;

import main.game.maze.behaviour.Position;
import main.game.maze.behaviour.SpeedEvent;
import main.game.maze.behaviour.TimeEvent;
import main.game.maze.behaviour.VisionEvent;
import main.game.maze.opponents.CharacterType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Movement Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getCharactertype <em>Charactertype</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#isIgnoreWalls <em>Ignore Walls</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#isInstantKillOnCollision <em>Instant Kill On Collision</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getBaseVisionRange <em>Base Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getAdditionalVisionRange <em>Additional Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getVisionRangeMultiplier <em>Vision Range Multiplier</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getVisionRange <em>Vision Range</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getNextPositions <em>Next Positions</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.MovementBehaviorImpl#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MovementBehaviorImpl extends MinimalEObjectImpl.Container implements MovementBehavior {
	/**
	 * The cached value of the '{@link #getCharactertype() <em>Charactertype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharactertype()
	 * @generated
	 * @ordered
	 */
	protected CharacterType charactertype;

	/**
	 * The cached setting delegate for the '{@link #isIgnoreWalls() <em>Ignore Walls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreWalls()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate IGNORE_WALLS__ESETTING_DELEGATE = ((EStructuralFeature.Internal)BehaviourPackage.Literals.MOVEMENT_BEHAVIOR__IGNORE_WALLS).getSettingDelegate();

	/**
	 * The cached setting delegate for the '{@link #isInstantKillOnCollision() <em>Instant Kill On Collision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInstantKillOnCollision()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate INSTANT_KILL_ON_COLLISION__ESETTING_DELEGATE = ((EStructuralFeature.Internal)BehaviourPackage.Literals.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION).getSettingDelegate();

	/**
	 * The default value of the '{@link #getBaseVisionRange() <em>Base Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseVisionRange()
	 * @generated
	 * @ordered
	 */
	protected static final double BASE_VISION_RANGE_EDEFAULT = 100.0;

	/**
	 * The cached value of the '{@link #getBaseVisionRange() <em>Base Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseVisionRange()
	 * @generated
	 * @ordered
	 */
	protected double baseVisionRange = BASE_VISION_RANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdditionalVisionRange() <em>Additional Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalVisionRange()
	 * @generated
	 * @ordered
	 */
	protected static final double ADDITIONAL_VISION_RANGE_EDEFAULT = 100.0;

	/**
	 * The cached value of the '{@link #getAdditionalVisionRange() <em>Additional Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalVisionRange()
	 * @generated
	 * @ordered
	 */
	protected double additionalVisionRange = ADDITIONAL_VISION_RANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisionRangeMultiplier() <em>Vision Range Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisionRangeMultiplier()
	 * @generated
	 * @ordered
	 */
	protected static final double VISION_RANGE_MULTIPLIER_EDEFAULT = 1.0;

	/**
	 * The cached value of the '{@link #getVisionRangeMultiplier() <em>Vision Range Multiplier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisionRangeMultiplier()
	 * @generated
	 * @ordered
	 */
	protected double visionRangeMultiplier = VISION_RANGE_MULTIPLIER_EDEFAULT;

	/**
	 * The cached setting delegate for the '{@link #getVisionRange() <em>Vision Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisionRange()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate VISION_RANGE__ESETTING_DELEGATE = ((EStructuralFeature.Internal)BehaviourPackage.Literals.MOVEMENT_BEHAVIOR__VISION_RANGE).getSettingDelegate();

	/**
	 * The cached value of the '{@link #getNextPositions() <em>Next Positions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextPositions()
	 * @generated
	 * @ordered
	 */
	protected EList<Position> nextPositions;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected Direction direction;

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
	public CharacterType getCharactertype() {
		if (charactertype != null && charactertype.eIsProxy()) {
			InternalEObject oldCharactertype = (InternalEObject)charactertype;
			charactertype = (CharacterType)eResolveProxy(oldCharactertype);
			if (charactertype != oldCharactertype) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE, oldCharactertype, charactertype));
			}
		}
		return charactertype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CharacterType basicGetCharactertype() {
		return charactertype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCharactertype(CharacterType newCharactertype) {
		CharacterType oldCharactertype = charactertype;
		charactertype = newCharactertype;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE, oldCharactertype, charactertype));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIgnoreWalls() {
		return (Boolean)IGNORE_WALLS__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIgnoreWalls(boolean newIgnoreWalls) {
		IGNORE_WALLS__ESETTING_DELEGATE.dynamicSet(this, null, 0, newIgnoreWalls);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInstantKillOnCollision() {
		return (Boolean)INSTANT_KILL_ON_COLLISION__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInstantKillOnCollision(boolean newInstantKillOnCollision) {
		INSTANT_KILL_ON_COLLISION__ESETTING_DELEGATE.dynamicSet(this, null, 0, newInstantKillOnCollision);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getBaseVisionRange() {
		return baseVisionRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBaseVisionRange(double newBaseVisionRange) {
		double oldBaseVisionRange = baseVisionRange;
		baseVisionRange = newBaseVisionRange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__BASE_VISION_RANGE, oldBaseVisionRange, baseVisionRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAdditionalVisionRange() {
		return additionalVisionRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAdditionalVisionRange(double newAdditionalVisionRange) {
		double oldAdditionalVisionRange = additionalVisionRange;
		additionalVisionRange = newAdditionalVisionRange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE, oldAdditionalVisionRange, additionalVisionRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getVisionRangeMultiplier() {
		return visionRangeMultiplier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisionRangeMultiplier(double newVisionRangeMultiplier) {
		double oldVisionRangeMultiplier = visionRangeMultiplier;
		visionRangeMultiplier = newVisionRangeMultiplier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER, oldVisionRangeMultiplier, visionRangeMultiplier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getVisionRange() {
		return (Double)VISION_RANGE__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisionRange(double newVisionRange) {
		VISION_RANGE__ESETTING_DELEGATE.dynamicSet(this, null, 0, newVisionRange);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Position> getNextPositions() {
		if (nextPositions == null) {
			nextPositions = new EObjectResolvingEList<Position>(Position.class, this, BehaviourPackage.MOVEMENT_BEHAVIOR__NEXT_POSITIONS);
		}
		return nextPositions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPosition() {
		if (position != null && position.eIsProxy()) {
			InternalEObject oldPosition = (InternalEObject)position;
			position = (Position)eResolveProxy(oldPosition);
			if (position != oldPosition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION, oldPosition, position));
			}
		}
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Position newPosition) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION, oldPosition, position));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Direction getDirection() {
		if (direction != null && direction.eIsProxy()) {
			InternalEObject oldDirection = (InternalEObject)direction;
			direction = (Direction)eResolveProxy(oldDirection);
			if (direction != oldDirection) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION, oldDirection, direction));
			}
		}
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Direction basicGetDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDirection(Direction newDirection) {
		Direction oldDirection = direction;
		direction = newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void move() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
		//Overwritten in PartolBehaviorImpl
	}

	/**
	 * <!-- begin-user-doc -->
	 * Applies a HealthEvent to the character.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void update(HealthEvent healthEvent) {
	    if (healthEvent == null) return;
	    
	    CharacterType ct = getCharactertype();
	    if (ct == null) return;
	    
	    try {
	        int amount = healthEvent.getHealthAmount();
	        double percentage = healthEvent.getHealthPercentage();
	        
	        int currentHp = ct.getHealth();
	        int newHp = currentHp;
	        
	        // Apply flat amount first
	        if (amount != 0) {
	            newHp += amount;
	        }
	        
	        // Apply percentage based on current HP (no maxHealth available in model)
	        if (percentage != 0) {
	            int delta = (int) (currentHp * (percentage / 100.0));
	            newHp += delta;
	        }
	        
	        ct.setHealth(Math.max(0, newHp));
	    } catch (Exception ignore) {}
	}

	/**
	 * <!-- begin-user-doc -->
	 * Applies a SpeedEvent to the character. 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void update(SpeedEvent speedEvent) {
	    if (speedEvent == null) return;
	    
	    CharacterType ct = getCharactertype();
	    if (ct == null) return;
	    
	    try {
	        double amount = speedEvent.getSpeedAmount();
	        double percentage = speedEvent.getSpeedPercentage();
	        
	        double newSpeed = ct.getSpeed();
	        
	        // Apply flat amount first
	        if (amount != 0) {
	            newSpeed += amount;
	        }
	        
	        // Apply percentage multiplier to the updated speed
	        if (percentage != 0) {
	            newSpeed *= (1 + (percentage / 100.0));
	        }
	        
	        ct.setSpeed(newSpeed);
	    } catch (Exception ignore) {}
	}

	/**
	 * <!-- begin-user-doc -->
	 * Applies a TimeEvent — placeholder: could pause movement for a duration.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void update(TimeEvent timeEvent) {
	    if (timeEvent == null) return;
	    // TODO: implement time-based effect (e.g., stun, slow, pause)
	    // For now, no-op placeholder
	}

	/**
	 * <!-- begin-user-doc -->
	 * Applies an AttackEvent — placeholder: could deal damage or modify attack. 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void update(AttackEvent attackEvent) {
	    if (attackEvent == null) return;
	    // TODO: implement attack effect
	    // For now, no-op placeholder
	}

	/**
	 * <!-- begin-user-doc -->
	 * Applies a VisionEvent to modify vision range.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void update(VisionEvent visionEvent) {
	    if (visionEvent == null) return;
	    
	    try {
	        double amount = visionEvent. getRadiusAmount();
	        double percentage = visionEvent.getRadiusPercentage();
	        
	        // Apply flat amount to additional vision range
	        if (amount != 0) {
	            setAdditionalVisionRange(getAdditionalVisionRange() + amount);
	        }
	        
        // Apply percentage to multiplier (percentage is 0-100 scale)
        if (percentage != 0) {
            setVisionRangeMultiplier(getVisionRangeMultiplier() * (1 + percentage / 100.0));
	        }
	    } catch (Exception ignore) {}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE:
				if (resolve) return getCharactertype();
				return basicGetCharactertype();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				return isIgnoreWalls();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				return isInstantKillOnCollision();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__BASE_VISION_RANGE:
				return getBaseVisionRange();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE:
				return getAdditionalVisionRange();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER:
				return getVisionRangeMultiplier();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE:
				return getVisionRange();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__NEXT_POSITIONS:
				return getNextPositions();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION:
				if (resolve) return getPosition();
				return basicGetPosition();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION:
				if (resolve) return getDirection();
				return basicGetDirection();
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE:
				setCharactertype((CharacterType)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				setIgnoreWalls((Boolean)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				setInstantKillOnCollision((Boolean)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__BASE_VISION_RANGE:
				setBaseVisionRange((Double)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE:
				setAdditionalVisionRange((Double)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER:
				setVisionRangeMultiplier((Double)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE:
				setVisionRange((Double)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__NEXT_POSITIONS:
				getNextPositions().clear();
				getNextPositions().addAll((Collection<? extends Position>)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION:
				setPosition((Position)newValue);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION:
				setDirection((Direction)newValue);
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE:
				setCharactertype((CharacterType)null);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				IGNORE_WALLS__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				INSTANT_KILL_ON_COLLISION__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__BASE_VISION_RANGE:
				setBaseVisionRange(BASE_VISION_RANGE_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE:
				setAdditionalVisionRange(ADDITIONAL_VISION_RANGE_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER:
				setVisionRangeMultiplier(VISION_RANGE_MULTIPLIER_EDEFAULT);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE:
				VISION_RANGE__ESETTING_DELEGATE.dynamicUnset(this, null, 0);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__NEXT_POSITIONS:
				getNextPositions().clear();
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION:
				setPosition((Position)null);
				return;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION:
				setDirection((Direction)null);
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR__CHARACTERTYPE:
				return charactertype != null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__IGNORE_WALLS:
				return IGNORE_WALLS__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case BehaviourPackage.MOVEMENT_BEHAVIOR__INSTANT_KILL_ON_COLLISION:
				return INSTANT_KILL_ON_COLLISION__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case BehaviourPackage.MOVEMENT_BEHAVIOR__BASE_VISION_RANGE:
				return baseVisionRange != BASE_VISION_RANGE_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__ADDITIONAL_VISION_RANGE:
				return additionalVisionRange != ADDITIONAL_VISION_RANGE_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE_MULTIPLIER:
				return visionRangeMultiplier != VISION_RANGE_MULTIPLIER_EDEFAULT;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__VISION_RANGE:
				return VISION_RANGE__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case BehaviourPackage.MOVEMENT_BEHAVIOR__NEXT_POSITIONS:
				return nextPositions != null && !nextPositions.isEmpty();
			case BehaviourPackage.MOVEMENT_BEHAVIOR__POSITION:
				return position != null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR__DIRECTION:
				return direction != null;
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
			case BehaviourPackage.MOVEMENT_BEHAVIOR___MOVE:
				move();
				return null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR___UPDATE__HEALTHEVENT:
				update((HealthEvent)arguments.get(0));
				return null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR___UPDATE__SPEEDEVENT:
				update((SpeedEvent)arguments.get(0));
				return null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR___UPDATE__TIMEEVENT:
				update((TimeEvent)arguments.get(0));
				return null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR___UPDATE__ATTACKEVENT:
				update((AttackEvent)arguments.get(0));
				return null;
			case BehaviourPackage.MOVEMENT_BEHAVIOR___UPDATE__VISIONEVENT:
				update((VisionEvent)arguments.get(0));
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
		result.append(" (baseVisionRange: ");
		result.append(baseVisionRange);
		result.append(", additionalVisionRange: ");
		result.append(additionalVisionRange);
		result.append(", visionRangeMultiplier: ");
		result.append(visionRangeMultiplier);
		result.append(')');
		return result.toString();
	}

} //MovementBehaviorImpl


