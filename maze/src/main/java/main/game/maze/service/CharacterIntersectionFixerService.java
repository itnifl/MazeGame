package main.game.maze.service;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.MazeWorld;
import main.game.maze.Vector2D;

public class CharacterIntersectionFixerService {
    
    private Pane gameBoard;
    private MazeWorld maze;

    public CharacterIntersectionFixerService(Pane gameBoard, MazeWorld maze) {
        this.gameBoard = gameBoard;
        this.maze = maze;
    }

    public void fixInitialSpriteMazeIntersections() {
        if (maze == null) return;
        List<Vector2D> walls = maze.getMazeVectors();
        if (walls == null || walls.isEmpty()) return;

        // collect all image sprites currently on the board
        List<Node> sprites = new ArrayList<>();
        for (Node n : gameBoard.getChildren()) {
            if (n instanceof ImageView) {
                sprites.add(n);
            }
        }

        for (Node sprite : sprites) {
            resolveSpriteWallIntersections(sprite, walls);
        }
    }

    private void resolveSpriteWallIntersections(Node sprite, List<Vector2D> walls) {
        // nudge at most this many times to avoid infinite loops
        final int maxIterations = 200;

        for (int i = 0; i < maxIterations; i++) {
            Vector2D intersecting = findFirstIntersectingWall(sprite, walls);
            if (intersecting == null) {
                break; // sprite is free of wall intersections
            }
            nudgeSpriteOffWall(sprite, intersecting);
        }
    }

    private Vector2D findFirstIntersectingWall(Node sprite, List<Vector2D> walls) {
        for (Vector2D wall : walls) {
            if (spriteIntersectsWall(sprite, wall)) {
                return wall;
            }
        }
        return null;
    }

    private boolean spriteIntersectsWall(Node sprite, Vector2D wall) {
        Bounds b = sprite.getBoundsInParent();
        double rx1 = b.getMinX();
        double ry1 = b.getMinY();
        double rx2 = b.getMaxX();
        double ry2 = b.getMaxY();

        double x1 = wall.getStart().getX();
        double y1 = wall.getStart().getY();
        double x2 = wall.getEnd().getX();
        double y2 = wall.getEnd().getY();

        // quick reject by bounding boxes
        double wx1 = Math.min(x1, x2);
        double wy1 = Math.min(y1, y2);
        double wx2 = Math.max(x1, x2);
        double wy2 = Math.max(y1, y2);

        if (rx2 < wx1 || rx1 > wx2 || ry2 < wy1 || ry1 > wy2) {
            return false;
        }

        // segment endpoint inside rectangle
        if (pointInRect(x1, y1, rx1, ry1, rx2, ry2) ||
            pointInRect(x2, y2, rx1, ry1, rx2, ry2)) {
            return true;
        }

        // segment intersects any rect edge
        if (segmentsIntersect(x1, y1, x2, y2, rx1, ry1, rx2, ry1)) return true; // top
        if (segmentsIntersect(x1, y1, x2, y2, rx2, ry1, rx2, ry2)) return true; // right
        if (segmentsIntersect(x1, y1, x2, y2, rx2, ry2, rx1, ry2)) return true; // bottom
        if (segmentsIntersect(x1, y1, x2, y2, rx1, ry2, rx1, ry1)) return true; // left

        return false;
    }

    private boolean pointInRect(double x, double y,
                                double rx1, double ry1,
                                double rx2, double ry2) {
        return x >= rx1 && x <= rx2 && y >= ry1 && y <= ry2;
    }

    private boolean segmentsIntersect(double x1, double y1, double x2, double y2,
                                      double x3, double y3, double x4, double y4) {
        double d1 = direction(x3, y3, x4, y4, x1, y1);
        double d2 = direction(x3, y3, x4, y4, x2, y2);
        double d3 = direction(x1, y1, x2, y2, x3, y3);
        double d4 = direction(x1, y1, x2, y2, x4, y4);

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }

        if (d1 == 0 && onSegment(x3, y3, x4, y4, x1, y1)) return true;
        if (d2 == 0 && onSegment(x3, y3, x4, y4, x2, y2)) return true;
        if (d3 == 0 && onSegment(x1, y1, x2, y2, x3, y3)) return true;
        if (d4 == 0 && onSegment(x1, y1, x2, y2, x4, y4)) return true;

        return false;
    }

    private double direction(double xi, double yi, double xj, double yj, double xk, double yk) {
        return (xk - xi) * (yj - yi) - (xj - xi) * (yk - yi);
    }

    private boolean onSegment(double xi, double yi, double xj, double yj, double xk, double yk) {
        return Math.min(xi, xj) <= xk && xk <= Math.max(xi, xj) &&
               Math.min(yi, yj) <= yk && yk <= Math.max(yi, yj);
    }

    private void nudgeSpriteOffWall(Node sprite, Vector2D wall) {
        double x1 = wall.getStart().getX();
        double y1 = wall.getStart().getY();
        double x2 = wall.getEnd().getX();
        double y2 = wall.getEnd().getY();

        Bounds b = sprite.getBoundsInParent();
        double cx = (b.getMinX() + b.getMaxX()) / 2.0;
        double cy = (b.getMinY() + b.getMaxY()) / 2.0;

        // horizontal vs vertical wall
        if (Math.abs(y1 - y2) < 0.0001) {
            double wy = y1;
            if (cy >= wy) {
                sprite.setLayoutY(sprite.getLayoutY() + 1);
            } else {
                sprite.setLayoutY(sprite.getLayoutY() - 1);
            }
        } else if (Math.abs(x1 - x2) < 0.0001) {
            double wx = x1;
            if (cx >= wx) {
                sprite.setLayoutX(sprite.getLayoutX() + 1);
            } else {
                sprite.setLayoutX(sprite.getLayoutX() - 1);
            }
        } else {
            // fallback: small diagonal nudge away from wall midpoint
            double mx = (x1 + x2) / 2.0;
            double my = (y1 + y2) / 2.0;
            double dx = cx - mx;
            double dy = cy - my;
            double len = Math.hypot(dx, dy);
            if (len == 0) len = 1;
            sprite.setLayoutX(sprite.getLayoutX() + dx / len);
            sprite.setLayoutY(sprite.getLayoutY() + dy / len);
        }
    }    
}
