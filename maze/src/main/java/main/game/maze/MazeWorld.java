package main.game.maze;

import java.util.ArrayList;
import java.util.List;

public class MazeWorld {
    private static MazeWorld world;
    private List<Vector2D> mazeVectors;
    /*
     * Factory method that creates a new world, or returns an existing one if one already exists:
     */
    public static MazeWorld GetWorld() {
        if(world == null) {
            world = new MazeWorld();
        }
        return world;
    }

    public MazeWorld() {
        mazeVectors = new ArrayList<Vector2D>();
        mazeVectors.add(new Vector2D(150, 150, 350, 150));
        mazeVectors.add(new Vector2D(350, 150, 350, 350));
    } 

    public List<Vector2D> getVectors() {
        return mazeVectors;
    }
}
