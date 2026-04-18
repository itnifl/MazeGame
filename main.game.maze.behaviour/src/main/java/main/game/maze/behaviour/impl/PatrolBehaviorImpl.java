/**
 */
package main.game.maze.behaviour.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPathBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.PatrolZone;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import java.util.Random;

import main.game.maze.behaviour.Position;
import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

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
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getBehavior <em>Behavior</em>}</li>
 *   <li>{@link main.game.maze.behaviour.impl.PatrolBehaviorImpl#getPatrolZone <em>Patrol Zone</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatrolBehaviorImpl extends MovementBehaviorImpl implements PatrolBehavior {
	
	// @generated NOT
    private static final Random rng = new Random();
    private static final double EPSILON = 2.0; // Increased tolerance for "arrived" check
    private static final long PLACEHOLDER_WAIT_MS = 100L; // Short wait for tests
    
    private transient long waitRemainingMs = 0L;
    private transient long lastTickTime = 0L;

	
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' containment reference list.
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
	 * The cached value of the '{@link #getPathcalculator() <em>Pathcalculator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathcalculator()
	 * @generated
	 * @ordered
	 */
	protected PathCalculator pathcalculator;

	/**
	 * The default value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected static final PatrolPathBehavior BEHAVIOR_EDEFAULT = PatrolPathBehavior.LOOP;

	/**
	 * The cached value of the '{@link #getBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehavior()
	 * @generated
	 * @ordered
	 */
	protected PatrolPathBehavior behavior = BEHAVIOR_EDEFAULT;

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
			path = new EObjectContainmentEList<PatrolPoint>(PatrolPoint.class, this, BehaviourPackage.PATROL_BEHAVIOR__PATH);
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, oldPathcalculator, newPathcalculator);
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
				msgs = ((InternalEObject)pathcalculator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, null, msgs);
			if (newPathcalculator != null)
				msgs = ((InternalEObject)newPathcalculator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, null, msgs);
			msgs = basicSetPathcalculator(newPathcalculator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR, newPathcalculator, newPathcalculator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PatrolPathBehavior getBehavior() {
		return behavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBehavior(PatrolPathBehavior newBehavior) {
		PatrolPathBehavior oldBehavior = behavior;
		behavior = newBehavior == null ? BEHAVIOR_EDEFAULT : newBehavior;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATROL_BEHAVIOR__BEHAVIOR, oldBehavior, behavior));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				return ((InternalEList<?>)getPath()).basicRemove(otherEnd, msgs);
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
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
			case BehaviourPackage.PATROL_BEHAVIOR__PATH:
				return getPath();
			case BehaviourPackage.PATROL_BEHAVIOR__CURRENT_INDEX:
				return getCurrentIndex();
			case BehaviourPackage.PATROL_BEHAVIOR__PATHCALCULATOR:
				return getPathcalculator();
			case BehaviourPackage.PATROL_BEHAVIOR__BEHAVIOR:
				return getBehavior();
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
			case BehaviourPackage.PATROL_BEHAVIOR__BEHAVIOR:
				setBehavior((PatrolPathBehavior)newValue);
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
			case BehaviourPackage.PATROL_BEHAVIOR__BEHAVIOR:
				setBehavior(BEHAVIOR_EDEFAULT);
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
			case BehaviourPackage.PATROL_BEHAVIOR__BEHAVIOR:
				return behavior != BEHAVIOR_EDEFAULT;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case BehaviourPackage.PATROL_BEHAVIOR___NEXT_INDEX:
				nextIndex();
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
		result.append(" (currentIndex: ");
		result.append(currentIndex);
		result.append(", behavior: ");
		result.append(behavior);
		result.append(')');
		return result.toString();
	}
	

	/**
	 * <!-- begin-user-doc -->
	 * Advances currentIndex to the next patrol point based on the behavior mode.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void nextIndex() {
		int size = getPath().size();
		if (size == 0) return;

		int cur = getCurrentIndex();
		int next;

		PatrolPathBehavior mode = getBehavior();
		if (mode == null) mode = PatrolPathBehavior.LOOP;

		switch (mode) {
		case LOOP:
			next = (cur + 1) % size;
			break;
		case RANDOM:
			if (size <= 1) {
				next = 0;
			} else {
				next = rng.nextInt(size);
				while (next == cur) {
					next = rng.nextInt(size);
				}
			}
			break;
		case BACKWARD:
			next = (cur - 1 + size) % size;
			break;
		default:
			next = (cur + 1) % size;
			break;
		}

		setCurrentIndex(next);
	}

	/**
	 * <!-- begin-user-doc -->
	 * Performs one movement tick: computes path to current target, moves along it,
	 * detects arrival, triggers events, and advances to next patrol point.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
    public void move() {
        // 1. Basic Safety Checks
        if (getPath() == null || getPath().isEmpty()) return;
        if (getPosition() == null) return;

        // 2. Resolve Current Target
        int idx = getCurrentIndex();
        if (idx < 0 || idx >= getPath().size()) {
            setCurrentIndex(0);
            idx = 0;
        }
        PatrolPoint targetPoint = getPath().get(idx);
        Position target = targetPoint.getPoint();

        if (target == null) {
            nextIndex();
            return;
        }

        // 3. Check distance to target
        double distToTarget = distance(getPosition(), target);
        
        // If we are already at the target (within epsilon), verify if we should switch index
        if (distToTarget <= EPSILON) {
            // Check if we have "arrived" logic (e.g. wait times)
            if (waitRemainingMs > 0) {
                long now = System.currentTimeMillis();
                long deltaMs = (lastTickTime == 0L) ? 60L : (now - lastTickTime);
                lastTickTime = now;
                waitRemainingMs = Math.max(0L, waitRemainingMs - deltaMs);
                return;
            }
            
            // We arrived. Clear path, set wait, move next.
            getNextPositions().clear();
            waitRemainingMs = PLACEHOLDER_WAIT_MS;
            nextIndex();
            return;
        }

        // 4. PATH CALCULATION (If we have no path but aren't at target)
        if (getNextPositions().isEmpty()) {
            
            // Try to calculate path
            PathCalculator pc = getPathcalculator();
            if (pc != null) {
                // Use the bridge method we added to PathCalculator
                // This handles the conversion from Position -> Node -> Position
                EList<Position> calculatedPath = pc.calculatePath(getPosition(), target);
                if (calculatedPath != null && !calculatedPath.isEmpty()) {
                    getNextPositions().addAll(calculatedPath);

                    // Optimization: Remove the first point if it is the current position
                    if (!getNextPositions().isEmpty() && distance(getPosition(), getNextPositions().get(0)) <= EPSILON) {
                        getNextPositions().remove(0);
                    }
                }
            }
        }

        // 5. MOVEMENT EXECUTION
        if (getNextPositions().isEmpty()) return;

        double movementSpeed = 2.0;
        if (getCharactertype() != null) {
            movementSpeed = getCharactertype().getSpeed();
        }

        double allowedDist = movementSpeed;
        Position currentPos = getPosition();

        while (allowedDist > 0 && !getNextPositions().isEmpty()) {
            Position nextWaypoint = getNextPositions().get(0);
            double dist = distance(currentPos, nextWaypoint);

            if (dist <= allowedDist + 0.1) { // 0.1 tolerance
                currentPos.setPosX(nextWaypoint.getPosX());
                currentPos.setPosY(nextWaypoint.getPosY());
                getNextPositions().remove(0);
                allowedDist -= dist;
            } else {
                double ratio = allowedDist / dist;
                double dx = nextWaypoint.getPosX() - currentPos.getPosX();
                double dy = nextWaypoint.getPosY() - currentPos.getPosY();
                currentPos.setPosX(currentPos.getPosX() + dx * ratio);
                currentPos.setPosY(currentPos.getPosY() + dy * ratio);
                allowedDist = 0;
            }
        }
    }

	// Helper method for Euclidean distance
	private double distance(Position a, Position b) {
		if (a == null || b == null) return Double.MAX_VALUE;
		double dx = a.getPosX() - b.getPosX();
		double dy = a.getPosY() - b.getPosY();
		return Math.hypot(dx, dy);
	}
} //PatrolBehaviorImpl
