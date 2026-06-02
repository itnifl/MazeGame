package main.game.maze.service;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.SpriteWallNudger;
import main.game.maze.mazeworld.generators.WallSegment;

/**
 * Thin JavaFX adapter that hands sprite nodes to the shared
 * {@link SpriteWallNudger} geometry. The wall-vs-AABB rules live in the
 * shared module so the libGDX frontend applies exactly the same logic.
 */
public class CharacterIntersectionFixerService {

    private static final int MAX_ITERATIONS = 200;

    private final Pane gameBoard;
    private final GameMazeWorld maze;

    public CharacterIntersectionFixerService(Pane gameBoard, GameMazeWorld maze) {
        this.gameBoard = gameBoard;
        this.maze = maze;
    }

    public void fixInitialSpriteMazeIntersections() {
        if (maze == null) return;
        List<WallSegment> walls = toWallSegments(maze.getMazeVectors());
        if (walls.isEmpty()) return;

        for (Node n : new ArrayList<>(gameBoard.getChildren())) {
            if (n instanceof ImageView) {
                SpriteWallNudger.nudgeOffWalls(new NodeAabb(n), walls, MAX_ITERATIONS);
            }
        }
    }

    private static List<WallSegment> toWallSegments(List<Vector2D> walls) {
        List<WallSegment> out = new ArrayList<>();
        if (walls == null) return out;
        for (Vector2D w : walls) {
            out.add(new WallSegment(
                    (float) w.getStart().getX(), (float) w.getStart().getY(),
                    (float) w.getEnd().getX(), (float) w.getEnd().getY()));
        }
        return out;
    }

    private static final class NodeAabb implements SpriteWallNudger.MovableAabb {
        private final Node node;
        NodeAabb(Node node) { this.node = node; }
        @Override public float minX() { return (float) node.getBoundsInParent().getMinX(); }
        @Override public float minY() { return (float) node.getBoundsInParent().getMinY(); }
        @Override public float maxX() { return (float) node.getBoundsInParent().getMaxX(); }
        @Override public float maxY() {
            Bounds b = node.getBoundsInParent();
            return (float) b.getMaxY();
        }
        @Override public void offset(float dx, float dy) {
            node.setLayoutX(node.getLayoutX() + dx);
            node.setLayoutY(node.getLayoutY() + dy);
        }
    }
}
