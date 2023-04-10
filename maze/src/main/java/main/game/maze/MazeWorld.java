package main.game.maze;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class MazeWorld {
    private static MazeWorld world;
    private List<Vector2D> mazeVectors;

    /*
     * Factory method that creates a new world, or returns an existing one if one
     * already exists:
     */
    public static MazeWorld GetWorld() {
        if (world == null) {
            world = new MazeWorld();
        }
        return world;
    }

    public MazeWorld() {
        mazeVectors = new ArrayList<>();
        mazeVectors.add(new Vector2D(400, 22, 400, 100)); // vertical vector
        mazeVectors.add(new Vector2D(400, 400, 400, 500)); // vertical vector
        mazeVectors.add(new Vector2D(400, 150, 600, 150)); // horizontal vector
        mazeVectors.add(new Vector2D(600, 150, 600, 250)); // vertical vector
        mazeVectors.add(new Vector2D(600, 250, 700, 250)); // horizontal vector
        mazeVectors.add(new Vector2D(700, 250, 700, 350)); // vertical vector
        mazeVectors.add(new Vector2D(700, 350, 600, 350)); // horizontal vector
        mazeVectors.add(new Vector2D(600, 350, 600, 400)); // vertical vector
        mazeVectors.add(new Vector2D(600, 450, 700, 450)); // horizontal vector
        mazeVectors.add(new Vector2D(700, 450, 700, 550)); // vertical vector
        mazeVectors.add(new Vector2D(700, 550, 600, 550)); // horizontal vector
        mazeVectors.add(new Vector2D(600, 550, 600, 600)); // vertical vector
        mazeVectors.add(new Vector2D(400, 150, 400, 250)); // vertical vector
        mazeVectors.add(new Vector2D(400, 250, 300, 250)); // horizontal vector
        mazeVectors.add(new Vector2D(300, 250, 300, 350)); // vertical vector
        mazeVectors.add(new Vector2D(350, 350, 400, 350)); // horizontal vector
        mazeVectors.add(new Vector2D(400, 350, 400, 450)); // vertical vector
        mazeVectors.add(new Vector2D(400, 450, 350, 450)); // horizontal vector
        mazeVectors.add(new Vector2D(300, 450, 300, 500)); // vertical vector
        mazeVectors.add(new Vector2D(300, 550, 400, 550)); // horizontal vector
        mazeVectors.add(new Vector2D(400, 550, 400, 600)); // vertical vector
        mazeVectors.add(new Vector2D(400, 250, 500, 250)); // horizontal vector
        mazeVectors.add(new Vector2D(500, 250, 500, 350)); // vertical vector
        mazeVectors.add(new Vector2D(500, 350, 400, 350)); // horizontal vector
        mazeVectors.add(new Vector2D(400, 350, 400, 400)); // vertical vector
        mazeVectors.add(new Vector2D(400, 400, 500, 400)); // horizontal vector
        mazeVectors.add(new Vector2D(500, 400, 500, 450)); // vertical vector
        mazeVectors.add(new Vector2D(120, 22, 120, 100)); // vertical vector
        mazeVectors.add(new Vector2D(0, 100, 80, 100)); // horizontal vector
        mazeVectors.add(new Vector2D(300, 250, 0, 250)); // horizontal vector
        mazeVectors.add(new Vector2D(400, 450, 400, 500)); // vertical vector
        mazeVectors.add(new Vector2D(400, 500, 500, 500)); // horizontal vector
        mazeVectors.add(new Vector2D(500, 550, 400, 550)); // horizontal vector
        mazeVectors.add(new Vector2D(120, 22, 120, 100)); // vertical vector
        mazeVectors.add(new Vector2D(0, 100, 80, 100)); // horizontal vector
        mazeVectors.add(new Vector2D(300, 250, 0, 250)); // horizontal vector
        mazeVectors.add(new Vector2D(120, 400, 120, 480)); // vertical vector
        mazeVectors.add(new Vector2D(0, 480, 80, 480)); // horizontal vector
        mazeVectors.add(new Vector2D(500, 100, 500, 150)); // vertical vector
        mazeVectors.add(new Vector2D(500, 150, 400, 150)); // horizontal vector
        mazeVectors.add(new Vector2D(400, 500, 300, 500)); // horizontal vector
        mazeVectors.add(new Vector2D(300, 500, 300, 400)); // vertical vector
        mazeVectors.add(new Vector2D(300, 400, 120, 400)); // horizontal vector
        mazeVectors.add(new Vector2D(800, 90, 600, 90)); // horizontal vector
        mazeVectors.add(new Vector2D(150, 300, 150, 400)); // vertical vector
        mazeVectors.add(new Vector2D(170, 20, 170, 200)); // vertical vector
        mazeVectors.add(new Vector2D(170, 200, 60, 200)); // horizontal vector
        mazeVectors.add(new Vector2D(80, 90, 80, 150)); // vertical vector
        mazeVectors.add(new Vector2D(200, 400, 200, 550)); // vertical vector
        mazeVectors.add(new Vector2D(220, 100, 360, 100)); // horizontal vector
    }

    public MazeWorld(String svgPath) {
        // Generate a maze based on a SVG image.
        // One can be created in Adobe Illustrator, Inkscape, Sketch, or Figma.
    }

    public void GenerateMaze() {
        int addingCounter = 0;
        List<Vector2D> unconnectedMazeVectors;
        do {
            unconnectedMazeVectors = getUnconnectedVectors();

            for (Vector2D vector : unconnectedMazeVectors) {
                addingCounter++;
                boolean shouldAddDoor = (addingCounter % 2) == 0;
                connectNewVector(vector, shouldAddDoor);
            }

        } while (unconnectedMazeVectors == null || unconnectedMazeVectors.size() != 0);
    }

    public void connectNewVector(Vector2D vector, boolean forceAddDoor) {
        // Create a new Vector2D that uses a unconnected point from this vector as
        // starting point.
        Point2D connectionStartingPoint = getVectorUnconnectedPoint(vector);
        // Make sure the length is a factor of the cellSize variable and that it does
        // not cross outside the board.
        // Make sure the length is no longer than 4x the cellSize variable.
        // Make sure the length is no longer than crossing point of the nearest vector.
        Point2D connectionEndingPoint = getNewVectorEndPoint(connectionStartingPoint);
        // If we are within the distance defined in cellSize from a door entrance,
        // we mark the end as a door entrance and make sure the end is no closer than
        // cellSize away from the entrance
        // If the variable forceAddDoor is true, then make sure the vector ends at least
        // cellSize away from the nearest vector.
        // If the variable forceAddDoor is true, then mark the end as a door entrance
        var createADoor = forceAddDoor || getShouldAddDoor();

        // We are ready to create our vector
        Vector2D newConnectedVector = getNewVector(connectionStartingPoint, connectionEndingPoint, createADoor);

        // We add our connected vector the the MazeWorld
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
        // Get vectors that do not have ends that either connect to the border or to
        // other vectors
        return null;
    }

    public List<Vector2D> getMazeVectors() {
        return mazeVectors;
    }
}
