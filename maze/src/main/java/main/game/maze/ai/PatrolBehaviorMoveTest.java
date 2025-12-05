// package main.game.maze.ai;

// import main.game.maze.behaviour.BehaviourFactory;
// import main.game.maze.behaviour.PatrolBehavior;
// import main.game.maze.behaviour.PatrolPathBehavior;
// import main.game.maze.behaviour.PatrolPoint;
// import main.game.maze.behaviour.Position;
// import main.game.maze.behaviour.impl.PathCalculatorImpl;

// import org.eclipse.emf.common.util.BasicEList;
// import org.eclipse.emf.common.util.EList;

// import javafx.scene.Node;
// import main.game.maze.mazeworld.GameMazeWorld;
// import main.game.maze.mazeworld.service.MazeNavigationGraph;

// /**
//  * Test harness for PatrolBehavior. move() logic.
//  * 
//  * Simulates patrol movement by:
//  * 1. Creating a PatrolBehavior with patrol points
//  * 2. Using a mock PathCalculator that returns a straight-line path
//  * 3. Calling move() repeatedly and printing position each tick
//  * 
//  * Run this class directly (has a main method). 
//  */
// public class PatrolBehaviorMoveTest {

//     // ========== Configuration ==========
//     private static final int TOTAL_TICKS = 300;
//     private static final long TICK_DELAY_MS = 100; // delay between ticks for readability
//     private static final double MOVEMENT_SPEED = 5.0; // distance units per tick

//     public static void main(String[] args) {
//         System.out.println("=== PatrolBehavior Move Test ===\n");

//         // Create patrol points: a triangle path
//         // Point 0: (10, 10)
//         // Point 1: (100, 10)
//         // Point 2: (55, 80)
//         Position p0 = createPosition(10, 10);
//         Position p1 = createPosition(100, 10);
//         Position p2 = createPosition(55, 80);
//         Position p3 = createPosition(100, 50);
//         Position p4 = createPosition(20, 80);

//         PatrolPoint pp0 = createPatrolPoint(p0);
//         PatrolPoint pp1 = createPatrolPoint(p1);
//         PatrolPoint pp2 = createPatrolPoint(p2);
//         PatrolPoint pp3 = createPatrolPoint(p3);
//         PatrolPoint pp4 = createPatrolPoint(p4);

//         // Create PatrolBehavior
//         PatrolBehavior patrol = BehaviourFactory.eINSTANCE.createPatrolBehavior();
//         patrol.getPath().add(pp0);
//         patrol.getPath().add(pp1);
//         patrol.getPath().add(pp2);
//         patrol.getPath().add(pp3);
//         patrol.getPath().add(pp4);
//         patrol.setCurrentIndex(0);
//         patrol.setBehavior(PatrolPathBehavior.LOOP); // Change to LOOP, BACKWARD or RANDOM to test other modes

//         // Set initial position (start at first patrol point)
//         Position currentPos = createPosition(10, 10);
//         patrol.setPosition(currentPos);

//         // Create and set mock PathCalculator (extends PathCalculatorImpl for EMF compatibility)
//         MockPathCalculator mockCalc = new MockPathCalculator();
//         patrol.setPathcalculator(mockCalc);

//         System.out.println("Patrol Mode: " + patrol.getBehavior());
//         System.out.println("Patrol Points:");
//         for (int i = 0; i < patrol.getPath().size(); i++) {
//             Position pt = patrol.getPath().get(i).getPoint();
//             System.out.printf("  [%d] (%.1f, %.1f)%n", i, pt.getPosX(), pt.getPosY());
//         }
//         System.out.println("\nStarting simulation...\n");
//         System.out.println("Tick | CurrentIndex | Position (x, y)      | Status");
//         System.out.println("-----|--------------|----------------------|--------");

//         // Create a testable patrol controller that we can step through
//         TestablePatrolBehavior testable = new TestablePatrolBehavior(patrol, MOVEMENT_SPEED, mockCalc);

//         for (int tick = 1; tick <= TOTAL_TICKS; tick++) {
//             String status = testable.tick();

//             Position pos = patrol.getPosition();
//             int idx = patrol.getCurrentIndex();

//             System.out.printf("%4d | %12d | (%7.2f, %7.2f)   | %s%n",
//                     tick, idx, pos.getPosX(), pos.getPosY(), status);

//             // Small delay for readability
//             try {
//                 Thread.sleep(TICK_DELAY_MS);
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//                 break;
//             }
//         }

//         System.out.println("\n=== Simulation Complete ===");
//     }

//     // ========== Helper Methods ==========

//     private static Position createPosition(double x, double y) {
//         Position p = BehaviourFactory.eINSTANCE.createPosition();
//         p.setPosX(x);
//         p.setPosY(y);
//         return p;
//     }

//     private static PatrolPoint createPatrolPoint(Position pos) {
//         PatrolPoint pp = BehaviourFactory.eINSTANCE.createPatrolPoint();
//         pp.setPoint(pos);
//         return pp;
//     }

//     // ========== Mock PathCalculator ==========

//     /**
//      * Mock PathCalculator that returns a straight-line path from current position to target.
//      * Extends PathCalculatorImpl so it is compatible with EMF's InternalEObject requirements.
//      */
//     static class MockPathCalculator extends PathCalculatorImpl {

//         private Position lastKnownPosition = null;

//         public void setLastKnownPosition(Position pos) {
//             this.lastKnownPosition = pos;
//         }

//         @Override
//         public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node origin, MazeNavigationGraph.Node target) {
//             EList<MazeNavigationGraph.Node> path = new BasicEList<>();

//             if (target == null) {
//                 return path;
//             }

//             // Use last known position as start
//             MazeNavigationGraph.Node current = origin;
//             if (current == null) {
//                 // Fallback: just return target
//                 path.add(current);
//                 return path;
//             }

//             double dx = target.getX() - current.getX();
//             double dy = target.getY() - current.getY();
//             double dist = Math.hypot(dx, dy);

//             if (dist < 1.0) {
//                 // Already at target
//                 path.add(target);
//                 return path;
//             }

//             // Generate intermediate points every ~5 units
//             double stepSize = 5.0;
//             int steps = (int) Math.ceil(dist / stepSize);

//             for (int i = 1; i <= steps; i++) {
//                 double frac = (double) i / steps;
//                 double x = current.getX() + dx * frac;
//                 double y = current.getY() + dy * frac;
//                 path.add(new MazeNavigationGraph.Node());
//             }

//             return path;
//         }

//         private Position createPos(double x, double y) {
//             Position p = BehaviourFactory.eINSTANCE.createPosition();
//             p.setPosX(x);
//             p.setPosY(y);
//             return p;
//         }

//         private Position copyPosition(Position src) {
//             Position p = BehaviourFactory.eINSTANCE.createPosition();
//             p.setPosX(src.getPosX());
//             p.setPosY(src.getPosY());
//             return p;
//         }
//     }

//     // ========== Testable Patrol Behavior (simulates move() logic) ==========

//     /**
//      * Encapsulates the move() logic for testing without modifying PatrolBehaviorImpl.
//      * This mirrors the logic that would go in PatrolBehaviorImpl.move(). 
//      */
//     static class TestablePatrolBehavior {
//         private final PatrolBehavior model;
//         private final double movementSpeed;
//         private final MockPathCalculator mockCalc;
//         private final EList<Position> nextPositions = new BasicEList<>();
//         private final java.util.Random rng = new java.util.Random();

//         private static final double EPSILON = 0.5;
//         private static final long PLACEHOLDER_WAIT_MS = 800L;
//         private static final long TICK_MS = 60L; // simulated tick duration

//         private long waitRemainingMs = 0L;

//         public TestablePatrolBehavior(PatrolBehavior model, double movementSpeed, MockPathCalculator mockCalc) {
//             this.model = model;
//             this.movementSpeed = movementSpeed;
//             this.mockCalc = mockCalc;
//         }

//         /**
//          * Perform one tick.  Returns a status string for display.
//          */
//         public String tick() {
//             // Sanity checks
//             if (model.getPath() == null || model.getPath().isEmpty()) {
//                 return "NO_PATH";
//             }
//             if (model.getPosition() == null) {
//                 return "NO_POSITION";
//             }

//             // Handle waiting at patrol point
//             if (waitRemainingMs > 0) {
//                 waitRemainingMs = Math.max(0, waitRemainingMs - TICK_MS);
//                 if (waitRemainingMs > 0) {
//                     return "WAITING (" + waitRemainingMs + "ms left)";
//                 } else {
//                     nextPositions.clear();
//                     return "WAIT_DONE";
//                 }
//             }

//             // Single-point path: stay in place
//             if (model.getPath().size() == 1) {
//                 PatrolPoint single = model.getPath().get(0);
//                 Position target = single.getPoint();
//                 Position currentPos = model.getPosition();
//                 if (target != null && distance(currentPos, target) > EPSILON) {
//                     currentPos.setPosX(target.getPosX());
//                     currentPos.setPosY(target.getPosY());
//                 }
//                 waitRemainingMs = PLACEHOLDER_WAIT_MS;
//                 return "SINGLE_POINT (waiting)";
//             }

//             // Get current target patrol point
//             int idx = model.getCurrentIndex();
//             if (idx < 0 || idx >= model.getPath().size()) {
//                 model.setCurrentIndex(0);
//                 idx = 0;
//             }
//             PatrolPoint targetPoint = model.getPath().get(idx);
//             Position target = targetPoint.getPoint();

//             if (target == null) {
//                 nextIndex();
//                 return "NULL_TARGET (skipped)";
//             }

//             // If nextPositions is empty, compute path to target
//             if (nextPositions.isEmpty()) {
//                 // Update mock calculator with current position before computing
//                 mockCalc.setLastKnownPosition(model.getPosition());

//                 try {
//                     EList<Position> computed = mockCalc.compute(target);
//                     if (computed == null || computed.isEmpty()) {
//                         nextIndex();
//                         return "EMPTY_PATH (skipped)";
//                     }
//                     nextPositions.clear();
//                     for (Position p : computed) {
//                         Position copy = BehaviourFactory.eINSTANCE.createPosition();
//                         copy.setPosX(p.getPosX());
//                         copy.setPosY(p.getPosY());
//                         nextPositions.add(copy);
//                     }
//                     // Drop first node if equals current position
//                     if (!nextPositions.isEmpty() &&
//                             distance(model.getPosition(), nextPositions.get(0)) <= EPSILON) {
//                         nextPositions.remove(0);
//                     }
//                 } catch (Exception ex) {
//                     nextIndex();
//                     return "PATH_ERROR: " + ex.getMessage();
//                 }
//             }

//             // If still no path, skip to next index
//             if (nextPositions.isEmpty()) {
//                 nextIndex();
//                 return "NO_PATH_NODES (skipped)";
//             }

//             // Consume movement along the path
//             double allowed = movementSpeed;
//             Position currentPos = model.getPosition();
//             boolean moved = false;

//             while (allowed > 0 && ! nextPositions.isEmpty()) {
//                 Position nextNode = nextPositions.get(0);
//                 double seg = distance(currentPos, nextNode);

//                 if (seg <= allowed + EPSILON) {
//                     // Snap to node
//                     currentPos.setPosX(nextNode.getPosX());
//                     currentPos.setPosY(nextNode.getPosY());
//                     nextPositions.remove(0);
//                     allowed -= seg;
//                     moved = true;
//                 } else {
//                     // Interpolate toward nextNode
//                     double frac = allowed / seg;
//                     double nx = currentPos.getPosX() + (nextNode.getPosX() - currentPos.getPosX()) * frac;
//                     double ny = currentPos.getPosY() + (nextNode.getPosY() - currentPos.getPosY()) * frac;
//                     currentPos.setPosX(nx);
//                     currentPos.setPosY(ny);
//                     allowed = 0;
//                     moved = true;
//                 }
//             }

//             // Check arrival at target patrol point
//             if (nextPositions.isEmpty() && distance(currentPos, target) <= EPSILON) {
//                 // Snap exactly to target
//                 currentPos.setPosX(target.getPosX());
//                 currentPos.setPosY(target.getPosY());

//                 // Set wait timer
//                 waitRemainingMs = PLACEHOLDER_WAIT_MS;

//                 // Advance to next patrol point
//                 int prevIdx = model.getCurrentIndex();
//                 nextIndex();
//                 int newIdx = model.getCurrentIndex();

//                 return "ARRIVED at point " + prevIdx + " -> next " + newIdx;
//             }

//             return moved ? "MOVING" : "IDLE";
//         }

//         /**
//          * Advances currentIndex based on patrol mode. 
//          */
//         private void nextIndex() {
//             int size = model.getPath().size();
//             if (size == 0) return;

//             int cur = model.getCurrentIndex();
//             int next;

//             PatrolPathBehavior mode = model.getBehavior();
//             if (mode == null) mode = PatrolPathBehavior.LOOP;

//             switch (mode) {
//                 case LOOP:
//                     next = (cur + 1) % size;
//                     break;
//                 case RANDOM:
//                     if (size == 1) {
//                         next = 0;
//                     } else {
//                         next = rng.nextInt(size);
//                         while (next == cur) {
//                             next = rng.nextInt(size);
//                         }
//                     }
//                     break;
//                 case BACKWARD:
//                     // cyclic decrement: 2 -> 1 -> 0 -> 2 -> 1 -> ...
//                     next = (cur - 1 + size) % size;
//                     break;
//                 default:
//                     next = (cur + 1) % size;
//                     break;
//             }

//             model.setCurrentIndex(next);
//         }

//         private double distance(Position a, Position b) {
//             if (a == null || b == null) return Double.MAX_VALUE;
//             double dx = a.getPosX() - b.getPosX();
//             double dy = a.getPosY() - b.getPosY();
//             return Math.hypot(dx, dy);
//         }
//     }
// }