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
	private static final double EPSILON = 0.5; // arrival tolerance
	private static final long PLACEHOLDER_WAIT_MS = 800L; // placeholder wait time
	
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
	        	// random point but not the current
	            if (size == 1) {
	                next = 0;
	            } else {
	                next = rng.nextInt(size);
	                while (next == cur) {
	                    next = rng.nextInt(size);
	                }
	            }
	            break;
	        case BACKWARD:
	            // cyclic decrement: 2 -> 1 -> 0 -> 2 -> 1 -> ... 
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
	 * Performs one movement tick: computes path to current target, moves along it,
	 * detects arrival, triggers events, and advances to next patrol point.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void move() {
    	System.out.println("DEBUG: move() called");

		if (getPath() == null || getPath().isEmpty()) {
	        System.out.println("DEBUG: path is null or empty");
			return;
		}
		if (getPosition() == null) {
        	System.out.println("DEBUG: position is null");
			return;
		}

		// Tick timing
		long now = System.currentTimeMillis();
		long deltaMs = (lastTickTime == 0L) ? 60L : (now - lastTickTime);
		lastTickTime = now;

		// Waiting at patrol point
		if (waitRemainingMs > 0) {
			waitRemainingMs = Math.max(0L, waitRemainingMs - deltaMs);
			return;
		}

		// Single-point path: jump to it, trigger events, wait, repeat
		if (getPath().size() == 1) {
			PatrolPoint single = getPath().get(0);
			Position targetPos = single.getPoint();
			
			if (targetPos != null && distance(getPosition(), targetPos) > EPSILON) {
				getPosition().setPosX(targetPos.getPosX());
				getPosition().setPosY(targetPos.getPosY());
			}
			
			try {
				//single.triggerEvents();
			} catch (Exception ignore) {
				// Event trigger failure should not stop movement
			}
			
			waitRemainingMs = PLACEHOLDER_WAIT_MS;
			return;
		}

		// Resolve current patrol target
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

		// If we don't have next positions, compute using the navigation graph + calculator
		if (getNextPositions().isEmpty()) {
			try {
				MazeNavigationGraph graph = null;
				try {
					graph = GameMazeWorld.GetWorld().getNavigationGraph();
				} catch (Throwable t) {
				System.out.println("DEBUG: Failed to get GameMazeWorld: " + t.getMessage());
			}

				if (graph == null) {
            		System.out.println("DEBUG: graph is null");
					nextIndex();
					return;
				}

				// Snap start and goal to nodes
				Point2D startPt = new Point2D(getPosition().getPosX(), getPosition().getPosY());
				Point2D goalPt = new Point2D(target.getPosX(), target.getPosY());

				System.out.println("DEBUG: startPt = " + startPt + ", goalPt = " + goalPt);

				MazeNavigationGraph.Node startNode = graph.snapToNode(startPt);
				MazeNavigationGraph.Node goalNode = graph.snapToNode(goalPt);

				System.out.println("DEBUG: startNode = " + startNode + ", goalNode = " + goalNode);

				if (startNode == null || goalNode == null) {
					nextIndex();
					return;
				}

				// Get calculator
				PathCalculator pc = getPathcalculator();
        		System.out.println("DEBUG: PathCalculator = " + pc);


				if (pc == null) {
            		System.out.println("DEBUG: PathCalculator is null");
					nextIndex();
					return;
				}

				// Compute node path
				@SuppressWarnings("unchecked")
				EList<MazeNavigationGraph.Node> nodePath = 
					(EList<MazeNavigationGraph.Node>) pc.compute(startNode, goalNode);

				if (nodePath == null || nodePath.isEmpty()) {
            		System.out.println("DEBUG: nodePath is null or empty");
					nextIndex();
					return;
				}

				// Convert nodePath -> getNextPositions()
				getNextPositions().clear();
				for (MazeNavigationGraph.Node node : nodePath) {
					Position p = BehaviourFactory.eINSTANCE.createPosition();
					p.setPosX(node.getX());
					p.setPosY(node.getY());
					getNextPositions().add(p);
				}

				// Append exact target as final waypoint (patrol point may not be exactly on a node)
				Position lastWaypoint = getNextPositions().isEmpty() ? null : 
					getNextPositions().get(getNextPositions().size() - 1);
				if (lastWaypoint == null || distance(lastWaypoint, target) > EPSILON) {
					Position finalPos = BehaviourFactory.eINSTANCE.createPosition();
					finalPos.setPosX(target.getPosX());
					finalPos.setPosY(target.getPosY());
					getNextPositions().add(finalPos);
				}

				// Remove first node if it equals current position
				if (! getNextPositions().isEmpty() && 
					distance(getPosition(), getNextPositions().get(0)) <= EPSILON) {
					getNextPositions().remove(0);
				}

			} catch (Exception ex) {
				// Pathfinding failed — skip this target to prevent lockups
				try { 
					nextIndex(); 
				} catch (Exception ignore) {
					// Should not happen
				}
				return;
			}
		}

		// If still empty after attempt -> skip target
		if (getNextPositions().isEmpty()) {
			nextIndex();
			return;
		}

		// Movement speed from CharacterType, fallback to default
		double movementSpeed = 2.0;
		try {
			if (getCharactertype() != null) {
				movementSpeed = getCharactertype().getSpeed();
			}
		} catch (Exception ignore) {
			// Use default speed
		}

		// Consume movement along the path
		double allowed = movementSpeed;
		Position currentPos = getPosition();

		while (allowed > 0 && !getNextPositions().isEmpty()) {
			Position nextWaypoint = getNextPositions().get(0);
			double dx = nextWaypoint.getPosX() - currentPos.getPosX();
			double dy = nextWaypoint.getPosY() - currentPos.getPosY();
			double seg = Math.hypot(dx, dy);

			if (seg <= allowed + EPSILON) {
				// Snap to waypoint
				currentPos.setPosX(nextWaypoint.getPosX());
				currentPos.setPosY(nextWaypoint.getPosY());
				getNextPositions().remove(0);
				allowed -= seg;
			} else {
				// Move partially toward waypoint
				double frac = allowed / seg;
				double nx = currentPos.getPosX() + dx * frac;
				double ny = currentPos.getPosY() + dy * frac;
				currentPos.setPosX(nx);
				currentPos.setPosY(ny);
				allowed = 0;
			}
		}

		// Arrival: consumed all waypoints and within EPSILON of target
		if (getNextPositions().isEmpty() && distance(currentPos, target) <= EPSILON) {
			// Snap to exact target
			currentPos.setPosX(target.getPosX());
			currentPos.setPosY(target.getPosY());

			try {
				//targetPoint.triggerEvents();
			} catch (Exception ignore) {
				// Event trigger failure should not stop movement
			}

			waitRemainingMs = PLACEHOLDER_WAIT_MS;
			nextIndex();
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
