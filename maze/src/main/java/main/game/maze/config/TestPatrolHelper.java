package main.game.maze.config;

import main.game.maze.behaviour.PatrolBehavior;

public class TestPatrolHelper {
    public static void main(String[] args) throws Exception {
        // 1. Path to your XMI file
        String filePath = "src/test/patrol_behavior_example.xmi";

        // 2. Load model
        PatrolBehavior patrolModel = PatrolHelper.loadPatrolModel(filePath);

        // 3. Validate + map
        PatrolHelper.PatrolDefinition patrol =
                PatrolHelper.fromModel(patrolModel, 20, 20);

        // 4. Print confirmation
        System.out.println("Loaded patrol with "
                + (patrol.getPath() != null ? patrol.getPath().size() : 0)
                + " waypoints"
                + (patrol.getZone() != null ? " and a patrol zone" : ""));
    }
    /*In patrol_behavior_example there are 5 waypoints, but only 4 gets loaded because 2 waypoints have 
     *the same coordinatesand duplicates are not allowed
    */
}