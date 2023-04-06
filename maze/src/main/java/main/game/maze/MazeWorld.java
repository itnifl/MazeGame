package main.game.maze;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

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

    public MazeWorld(String svgPath) {
        //Generate a maze based on a SVG image.
        //One can be created in Adobe Illustrator, Inkscape, Sketch, or Figma.
    }

    public void GenerateMaze() {
        int addingCounter = 0;
        List<Vector2D> unconnectedMazeVectors;
        do
        {
            unconnectedMazeVectors = getUnconnectedVectors();
            

            for(Vector2D vector : unconnectedMazeVectors) {
                addingCounter++;
                boolean shouldAddDoor = (addingCounter % 2) == 0;
                connectNewVector(vector, shouldAddDoor);
            }

        } while(unconnectedMazeVectors == null || unconnectedMazeVectors.size() != 0);
    }

    public void connectNewVector(Vector2D vector, boolean forceAddDoor) {
        //Create a new Vector2D that uses a unconnected point from this vector as starting point. 
        Point2D connectionStartingPoint = getVectorUnconnectedPoint(vector);
        //Make sure the length is a factor of the cellSize variable and that it does not cross outside the board.
        //Make sure the length is no longer than 4x the cellSize variable.
        //Make sure the length is no longer than crossing point of the nearest vector.
        Point2D connectionEndingPoint = getNewVectorEndPoint(connectionStartingPoint);
        //If we are within the distance defined in cellSize from a door entrance, 
        //      we mark the end as a door entrance and make sure the end is no closer than cellSize away from the entrance
        //If the variable forceAddDoor is true, then make sure the vector ends at least cellSize away from the nearest vector.
        //If the variable forceAddDoor is true, then mark the end as a door entrance
        var createADoor = forceAddDoor || getShouldAddDoor();

        //We are ready to create our vector
        Vector2D newConnectedVector = getNewVector(connectionStartingPoint, connectionEndingPoint, createADoor);

        //We add our connected vector the the MazeWorld
        mazeVectors.add(newConnectedVector);
    }

    private Vector2D getNewVector(Point2D connectionStartingPoint, Point2D connectionEndingPoint, boolean createADoor) {
        return null;
    }

    private boolean getShouldAddDoor() {
        return false;
    }

    private Point2D getNewVectorEndPoint(Point2D connectionStartingPoint) {
        return null;
    }

    private Point2D getVectorUnconnectedPoint(Vector2D vector) {
        return null;
    }

    public List<Vector2D> getUnconnectedVectors() {
        //Get vectors that do not have ends that either connect to the border or to other vectors
        return null;
    }

    public List<Vector2D> getMazeVectors() {
        return mazeVectors;
    }
}
