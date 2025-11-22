package main.game.maze;

import java.util.ArrayList;
import java.util.List;

import main.game.maze.constants.StageConstants;
import main.game.maze.runtime.generators.DfsMazeGenerator;
import main.game.maze.runtime.generators.IMazeGenerator;
import main.game.maze.runtime.generators.MazeGeneratorConfig;

public class MazeWorld {
    private static MazeWorld world;
    private final IMazeGenerator mazeGenerator;
    private List<Vector2D> mazeVectors;

    /*
     * Factory method that creates a new world, or returns an existing one if one
     * already exists:
     */
    public static MazeWorld GetWorld() {
        if (world == null) {
            MazeGeneratorConfig cfg = new MazeGeneratorConfig(
                        StageConstants.BoardMaxX,
                        StageConstants.BoardMaxY,
                        20,
                        60,
                        60,
                        40
                );
            world = new MazeWorld(new DfsMazeGenerator(cfg));
        }
        return world;
    }

    public MazeWorld(IMazeGenerator generator) {
        this.mazeGenerator = generator;
        this.mazeVectors = new ArrayList<>();
        if (this.mazeGenerator != null) {
            this.mazeVectors.addAll(this.mazeGenerator.generateMaze());
        }
    }
    /*
     * Gives us a predefined standard map
     */
    public MazeWorld() {
        mazeGenerator = null;
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
        mazeGenerator = null;
        mazeVectors = new ArrayList<>();
    }

    public void GenerateMaze() throws Exception {
        if (mazeGenerator != null) {
            mazeVectors.clear();
            mazeVectors.addAll(mazeGenerator.generateMaze());
        }
    }

    public List<Vector2D> getMazeVectors() {
        return mazeVectors;
    }
}
