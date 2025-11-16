/**
 */
package main.game.maze.opponents.impl;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Character Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getHealth <em>Health</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getSpeed <em>Speed</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getThreatLevel <em>Threat Level</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getEffectiveThreat <em>Effective Threat</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getImageBase <em>Image Base</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getImageTurnLeft <em>Image Turn Left</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getImageTurnRight <em>Image Turn Right</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getImageTurnUp <em>Image Turn Up</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getImageTurnDown <em>Image Turn Down</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.CharacterTypeImpl#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CharacterTypeImpl extends MinimalEObjectImpl.Container implements CharacterType {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getHealth() <em>Health</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealth()
	 * @generated
	 * @ordered
	 */
	protected static final int HEALTH_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getHealth() <em>Health</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHealth()
	 * @generated
	 * @ordered
	 */
	protected int health = HEALTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEED_EDEFAULT = 1.0;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected double speed = SPEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getThreatLevel() <em>Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreatLevel()
	 * @generated
	 * @ordered
	 */
	protected static final double THREAT_LEVEL_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getThreatLevel() <em>Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreatLevel()
	 * @generated
	 * @ordered
	 */
	protected double threatLevel = THREAT_LEVEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getEffectiveThreat() <em>Effective Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffectiveThreat()
	 * @generated
	 * @ordered
	 */
	protected static final int EFFECTIVE_THREAT_EDEFAULT = 1;

	/**
	 * The default value of the '{@link #getImageBase() <em>Image Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageBase()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_BASE_EDEFAULT = "/main/game/maze/zombie.png";

	/**
	 * The cached value of the '{@link #getImageBase() <em>Image Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageBase()
	 * @generated
	 * @ordered
	 */
	protected String imageBase = IMAGE_BASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImageTurnLeft() <em>Image Turn Left</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnLeft()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_TURN_LEFT_EDEFAULT = "/main/game/maze/zombie-left.png";

	/**
	 * The cached value of the '{@link #getImageTurnLeft() <em>Image Turn Left</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnLeft()
	 * @generated
	 * @ordered
	 */
	protected String imageTurnLeft = IMAGE_TURN_LEFT_EDEFAULT;

	/**
	 * The default value of the '{@link #getImageTurnRight() <em>Image Turn Right</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnRight()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_TURN_RIGHT_EDEFAULT = "/main/game/maze/zombie-right.png";

	/**
	 * The cached value of the '{@link #getImageTurnRight() <em>Image Turn Right</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnRight()
	 * @generated
	 * @ordered
	 */
	protected String imageTurnRight = IMAGE_TURN_RIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getImageTurnUp() <em>Image Turn Up</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnUp()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_TURN_UP_EDEFAULT = "/main/game/maze/zombie-up.png";

	/**
	 * The cached value of the '{@link #getImageTurnUp() <em>Image Turn Up</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnUp()
	 * @generated
	 * @ordered
	 */
	protected String imageTurnUp = IMAGE_TURN_UP_EDEFAULT;

	/**
	 * The default value of the '{@link #getImageTurnDown() <em>Image Turn Down</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnDown()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_TURN_DOWN_EDEFAULT = "/main/game/maze/zombie-up.png";

	/**
	 * The cached value of the '{@link #getImageTurnDown() <em>Image Turn Down</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageTurnDown()
	 * @generated
	 * @ordered
	 */
	protected String imageTurnDown = IMAGE_TURN_DOWN_EDEFAULT;

	/**
	 * The default value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected static final BehaviorType BEHAVIOR_EDEFAULT = BehaviorType.WANDER;

	/**
	 * The cached value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected BehaviorType behavior = BEHAVIOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CharacterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.CHARACTER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHealth() {
		return health;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHealth(int newHealth) {
		int oldHealth = health;
		health = newHealth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__HEALTH, oldHealth, health));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSpeed() {
		return speed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpeed(double newSpeed) {
		double oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__SPEED, oldSpeed, speed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getThreatLevel() {
		return threatLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setThreatLevel(double newThreatLevel) {
		double oldThreatLevel = threatLevel;
		threatLevel = newThreatLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__THREAT_LEVEL, oldThreatLevel, threatLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getEffectiveThreat() {
		// Defensive defaults
		int baseThreatLevel = (int) Math.round(this.getThreatLevel());
		int healthPercent = Math.max(0, Math.min(getHealth(), 100));
		double behaviorMultiplier = 1.0; //There is no implementation of behavior here.		
		double computed = (baseThreatLevel * (healthPercent / 100.0)) * behaviorMultiplier;
		return Math.max(0, (int)Math.round(computed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImageBase() {
		return imageBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImageBase(String newImageBase) {
		String oldImageBase = imageBase;
		imageBase = newImageBase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__IMAGE_BASE, oldImageBase, imageBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImageTurnLeft() {
		return imageTurnLeft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImageTurnLeft(String newImageTurnLeft) {
		String oldImageTurnLeft = imageTurnLeft;
		imageTurnLeft = newImageTurnLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_LEFT, oldImageTurnLeft, imageTurnLeft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImageTurnRight() {
		return imageTurnRight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImageTurnRight(String newImageTurnRight) {
		String oldImageTurnRight = imageTurnRight;
		imageTurnRight = newImageTurnRight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_RIGHT, oldImageTurnRight, imageTurnRight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImageTurnUp() {
		return imageTurnUp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImageTurnUp(String newImageTurnUp) {
		String oldImageTurnUp = imageTurnUp;
		imageTurnUp = newImageTurnUp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_UP, oldImageTurnUp, imageTurnUp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImageTurnDown() {
		return imageTurnDown;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImageTurnDown(String newImageTurnDown) {
		String oldImageTurnDown = imageTurnDown;
		imageTurnDown = newImageTurnDown;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_DOWN, oldImageTurnDown, imageTurnDown));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BehaviorType getBehavior() {
		return behavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBehavior(BehaviorType newBehavior) {
		BehaviorType oldBehavior = behavior;
		behavior = newBehavior == null ? BEHAVIOR_EDEFAULT : newBehavior;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.CHARACTER_TYPE__BEHAVIOR, oldBehavior, behavior));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OpponentsPackage.CHARACTER_TYPE__ID:
				return getId();
			case OpponentsPackage.CHARACTER_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case OpponentsPackage.CHARACTER_TYPE__ENABLED:
				return isEnabled();
			case OpponentsPackage.CHARACTER_TYPE__HEALTH:
				return getHealth();
			case OpponentsPackage.CHARACTER_TYPE__SPEED:
				return getSpeed();
			case OpponentsPackage.CHARACTER_TYPE__THREAT_LEVEL:
				return getThreatLevel();
			case OpponentsPackage.CHARACTER_TYPE__EFFECTIVE_THREAT:
				return getEffectiveThreat();
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_BASE:
				return getImageBase();
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_LEFT:
				return getImageTurnLeft();
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_RIGHT:
				return getImageTurnRight();
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_UP:
				return getImageTurnUp();
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_DOWN:
				return getImageTurnDown();
			case OpponentsPackage.CHARACTER_TYPE__BEHAVIOR:
				return getBehavior();
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
			case OpponentsPackage.CHARACTER_TYPE__ID:
				setId((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__HEALTH:
				setHealth((Integer)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__SPEED:
				setSpeed((Double)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__THREAT_LEVEL:
				setThreatLevel((Double)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_BASE:
				setImageBase((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_LEFT:
				setImageTurnLeft((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_RIGHT:
				setImageTurnRight((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_UP:
				setImageTurnUp((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_DOWN:
				setImageTurnDown((String)newValue);
				return;
			case OpponentsPackage.CHARACTER_TYPE__BEHAVIOR:
				setBehavior((BehaviorType)newValue);
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
			case OpponentsPackage.CHARACTER_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__HEALTH:
				setHealth(HEALTH_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__SPEED:
				setSpeed(SPEED_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__THREAT_LEVEL:
				setThreatLevel(THREAT_LEVEL_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_BASE:
				setImageBase(IMAGE_BASE_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_LEFT:
				setImageTurnLeft(IMAGE_TURN_LEFT_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_RIGHT:
				setImageTurnRight(IMAGE_TURN_RIGHT_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_UP:
				setImageTurnUp(IMAGE_TURN_UP_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_DOWN:
				setImageTurnDown(IMAGE_TURN_DOWN_EDEFAULT);
				return;
			case OpponentsPackage.CHARACTER_TYPE__BEHAVIOR:
				setBehavior(BEHAVIOR_EDEFAULT);
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
			case OpponentsPackage.CHARACTER_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case OpponentsPackage.CHARACTER_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case OpponentsPackage.CHARACTER_TYPE__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case OpponentsPackage.CHARACTER_TYPE__HEALTH:
				return health != HEALTH_EDEFAULT;
			case OpponentsPackage.CHARACTER_TYPE__SPEED:
				return speed != SPEED_EDEFAULT;
			case OpponentsPackage.CHARACTER_TYPE__THREAT_LEVEL:
				return threatLevel != THREAT_LEVEL_EDEFAULT;
			case OpponentsPackage.CHARACTER_TYPE__EFFECTIVE_THREAT:
				return getEffectiveThreat() != EFFECTIVE_THREAT_EDEFAULT;
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_BASE:
				return IMAGE_BASE_EDEFAULT == null ? imageBase != null : !IMAGE_BASE_EDEFAULT.equals(imageBase);
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_LEFT:
				return IMAGE_TURN_LEFT_EDEFAULT == null ? imageTurnLeft != null : !IMAGE_TURN_LEFT_EDEFAULT.equals(imageTurnLeft);
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_RIGHT:
				return IMAGE_TURN_RIGHT_EDEFAULT == null ? imageTurnRight != null : !IMAGE_TURN_RIGHT_EDEFAULT.equals(imageTurnRight);
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_UP:
				return IMAGE_TURN_UP_EDEFAULT == null ? imageTurnUp != null : !IMAGE_TURN_UP_EDEFAULT.equals(imageTurnUp);
			case OpponentsPackage.CHARACTER_TYPE__IMAGE_TURN_DOWN:
				return IMAGE_TURN_DOWN_EDEFAULT == null ? imageTurnDown != null : !IMAGE_TURN_DOWN_EDEFAULT.equals(imageTurnDown);
			case OpponentsPackage.CHARACTER_TYPE__BEHAVIOR:
				return behavior != BEHAVIOR_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", health: ");
		result.append(health);
		result.append(", speed: ");
		result.append(speed);
		result.append(", threatLevel: ");
		result.append(threatLevel);
		result.append(", ImageBase: ");
		result.append(imageBase);
		result.append(", ImageTurnLeft: ");
		result.append(imageTurnLeft);
		result.append(", ImageTurnRight: ");
		result.append(imageTurnRight);
		result.append(", ImageTurnUp: ");
		result.append(imageTurnUp);
		result.append(", ImageTurnDown: ");
		result.append(imageTurnDown);
		result.append(", behavior: ");
		result.append(behavior);
		result.append(')');
		return result.toString();
	}

} //CharacterTypeImpl
