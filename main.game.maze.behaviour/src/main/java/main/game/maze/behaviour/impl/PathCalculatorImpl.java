package main.game.maze.behaviour.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.BehaviourPackage;
import main.game.maze.behaviour.DistanceMethod;
import main.game.maze.behaviour.PathCalculator;
import main.game.maze.behaviour.Position;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

public abstract class PathCalculatorImpl extends MinimalEObjectImpl.Container implements PathCalculator {

    private static final Logger LOGGER = Logger.getLogger(PathCalculatorImpl.class.getName());

    protected static final DistanceMethod DISTANCE_METHOD_EDEFAULT = DistanceMethod.MANHATTAN;
    protected DistanceMethod distanceMethod = DISTANCE_METHOD_EDEFAULT;

    protected PathCalculatorImpl() {
        super();
    }

    @Override
    protected EClass eStaticClass() {
        return BehaviourPackage.Literals.PATH_CALCULATOR;
    }

    @Override
    public DistanceMethod getDistanceMethod() {
        return distanceMethod;
    }

    @Override
    public void setDistanceMethod(DistanceMethod newDistanceMethod) {
        DistanceMethod oldDistanceMethod = distanceMethod;
        distanceMethod = newDistanceMethod == null ? DISTANCE_METHOD_EDEFAULT : newDistanceMethod;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD, oldDistanceMethod, distanceMethod));
    }

    /**
     * @generated NOT
     */
    public EList<Position> calculatePath(Position start, Position end) {
        EList<Position> resultPath = new BasicEList<>();
        try {
            GameMazeWorld world = null;
            try { 
                world = GameMazeWorld.GetWorld(); 
            } catch (Throwable t) {
                LOGGER.log(Level.FINE, "GameMazeWorld not available for path calculation", t);
                return resultPath; 
            }
            if (world == null) {
                LOGGER.fine("GameMazeWorld is null, cannot calculate path");
                return resultPath;
            }
            
            MazeNavigationGraph graph = world.getNavigationGraph();
            if (graph == null) {
                LOGGER.fine("Navigation graph is null, cannot calculate path");
                return resultPath;
            }

            MazeNavigationGraph.Node startNode = graph.snapToNode(new Point2D(start.getPosX(), start.getPosY()));
            MazeNavigationGraph.Node endNode = graph.snapToNode(new Point2D(end.getPosX(), end.getPosY()));

            if (startNode == null || endNode == null) {
                LOGGER.log(Level.FINE, "Failed to snap positions to nodes: start={0}, end={1}", 
                    new Object[]{startNode, endNode});
                return resultPath;
            }

            LOGGER.log(Level.FINER, "Computing path from ({0},{1}) to ({2},{3})",
                new Object[]{startNode.getCol(), startNode.getRow(), endNode.getCol(), endNode.getRow()});

            EList<MazeNavigationGraph.Node> nodePath = compute(startNode, endNode);

            if (nodePath != null) {
                LOGGER.log(Level.FINER, "Path computed with {0} nodes", nodePath.size());
                for (MazeNavigationGraph.Node node : nodePath) {
                    Position p = BehaviourFactory.eINSTANCE.createPosition();
                    p.setPosX(node.getX());
                    p.setPosY(node.getY());
                    resultPath.add(p);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during path calculation", e);
        }
        return resultPath;
    }

    /**
     * @generated NOT
     */
    public EList<MazeNavigationGraph.Node> reconstructPath(MazeNavigationGraph.Node[][] originNodes, MazeNavigationGraph.Node target) {
        EList<MazeNavigationGraph.Node> path = new BasicEList<>();
        if (originNodes == null || target == null) {
            LOGGER.fine("Cannot reconstruct path: originNodes or target is null");
            return path;
        }

        int cols = originNodes.length;
        if (cols == 0) return path;
        int rows = originNodes[0].length;

        // FIX: Start reconstruction from the TARGET itself
        MazeNavigationGraph.Node curr = target;
        
        // Safety loop limit
        int safety = 0;
        int max = cols * rows;

        while (curr != null && safety++ < max) {
            // Add to front of list (reversing the parent chain)
            path.add(0, curr);
            
            // Check bounds
            if (curr.getCol() >= 0 && curr.getCol() < cols && curr.getRow() >= 0 && curr.getRow() < rows) {
                MazeNavigationGraph.Node parent = originNodes[curr.getCol()][curr.getRow()];
                // Prevent self-loops
                if (parent == curr) break;
                curr = parent;
            } else {
                break;
            }
        }
        return path;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    /**
     * @generated NOT
     */
    public MazeNavigationGraph.Node nearestNode(List<MazeNavigationGraph.Node> nodes, MazeNavigationGraph.Node target) {
        if (nodes == null || target == null) {
            LOGGER.fine("Cannot find nearest node: nodes list or target is null");
            return null;
        }
        MazeNavigationGraph.Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;
        for (var node : nodes) {
            double distance = 0;
            switch (getDistanceMethod()) {
                case EUCLIDEAN:
                    distance = Math.sqrt(Math.pow(node.getCol() - target.getCol(), 2) + Math.pow(node.getRow() - target.getRow(), 2));
                    break;
                case MANHATTAN:
                default:
                    distance = Math.abs(node.getCol() - target.getCol()) + Math.abs(node.getRow() - target.getRow());
                    break;
            }
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = node;
            }
        }
        return nearestNode;
    }
    
    // ... eGet, eSet, eUnset, eInvoke ... 
    // (Keep the standard EMF methods generated at the bottom of your file)
    
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				return getDistanceMethod();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				setDistanceMethod((DistanceMethod)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				setDistanceMethod(DISTANCE_METHOD_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BehaviourPackage.PATH_CALCULATOR__DISTANCE_METHOD:
				return distanceMethod != DISTANCE_METHOD_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case BehaviourPackage.PATH_CALCULATOR___COMPUTE__POSITION:
				if (arguments.size() == 2 && arguments.get(0) instanceof MazeNavigationGraph.Node) {
					return compute((MazeNavigationGraph.Node)arguments.get(0), (MazeNavigationGraph.Node)arguments.get(1));
				}
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}
}
