package main.game.maze.ai;

import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.Position;
import main.game.maze.config.PatrolHelper;

/**
 * Simple test for PatrolMovementController.
 * -------------------------------------------------------------
 * Loads a patrol model, initializes a follower and controller,
 * and simulates an enemy moving along the patrol path.
 */
public class TestPatrolMovementController {
    public static void main(String[] args) throws Exception {
        // Load model
        String modelPath = "src/test/patrol_behavior_example.xmi";
        PatrolBehavior model = PatrolHelper.loadPatrolModel(modelPath);
        PatrolHelper.PatrolDefinition def = PatrolHelper.fromModel(model, 20, 20);

        // Create runtime components 
        PatrolFollower follower = new PatrolFollower(def, 0.3, 2.0); // tolerance=0.3, speed=2
        PatrolMovementController controller =
                new PatrolMovementController(follower,
                        1.5,  // patrolSpeed
                        3.0,  // accelMax
                        2.0,  // turnRateMax
                        500,  // replanIntervalMs
                        0.3); // reachTolerance

        // Simulated enemy state 
        double x = 0.0;
        double y = 0.0;
        double deltaTimeMs = 100;
        long startTime = System.currentTimeMillis();

        System.out.println("Starting patrol simulation...");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-6s | %-18s | %-18s | %-14s | %-6s | %s%n",
                "Tick", "Position (x, y)", "Target (tx, ty)", "Velocity (vx, vy)", "Speed", "State");
        System.out.println("-------------------------------------------------------------");


        // Simulation loop
        for (int tick = 0; tick < 200; tick++) {
            long now = System.currentTimeMillis();
            controller.update(x, y, now, deltaTimeMs);

            // Retrieve the current target for debug
            Position target = follower.getCurrentTarget();
            double speed = controller.getVelocityMagnitude();

            System.out.printf("Tick %03d | Pos=(%.2f, %.2f) | Target=(%.2f, %.2f) | Vel=(%.2f, %.2f) | Speed=%.2f %s%n",
                    tick, x, y,
                    target != null ? target.getPosX() : 0,
                    target != null ? target.getPosY() : 0,
                    controller.getVelX(), controller.getVelY(),
                    speed,
                    follower.isHolding() ? "(HOLDING)" : "");


            // Integrate movement using actual controller velocity
            x += controller.getVelX() * deltaTimeMs / 1000.0;
            y += controller.getVelY() * deltaTimeMs / 1000.0;


            Thread.sleep((long) deltaTimeMs);
        }

        System.out.println("---------------------------------");
        System.out.println("Simulation ended.");
    }
}
