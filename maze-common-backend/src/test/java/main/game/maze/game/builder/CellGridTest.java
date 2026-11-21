package main.game.maze.game.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.builder.CellGrid.Cell;
import main.game.maze.game.builder.CellGrid.Edge;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Cell grid: cells one corridor wide, edges closed by three pieces of wall")
class CellGridTest {

    private static final int W = 780;
    private static final int H = 660;

    @Test
    @DisplayName("cells, centres and neighbours")
    void cells() {
        CellGrid grid = new CellGrid(List.of(), W, H);
        assertEquals(13, grid.cols());
        assertEquals(11, grid.rows());
        assertEquals(new Cell(5, 6), grid.cellOf(390, 330));
        assertEquals(30, grid.centerOf(new Cell(0, 0)).getX(), 1e-9);
        assertEquals(2, grid.neighbours(new Cell(0, 0)).size());
        assertEquals(4, grid.neighbours(new Cell(5, 6)).size());
        assertEquals(new Cell(0, 12), grid.cellOf(5000, -5));
    }

    @Test
    @DisplayName("an edge is open with no pieces, closed with three, and its pieces are one piece long")
    void edges() {
        Edge east = new Edge(new Cell(2, 2), new Cell(2, 3));
        Edge south = new Edge(new Cell(2, 2), new Cell(3, 2));
        CellGrid empty = new CellGrid(List.of(), W, H);
        assertTrue(empty.isOpen(east));
        List<Vector2D> pieces = empty.pieces(east);
        assertEquals(3, pieces.size());
        for (Vector2D piece : pieces) {
            assertEquals(CellGrid.PIECE, piece.magnitude(), 1e-9);
            assertEquals(180.0, piece.getStart().getX(), 1e-9, "on the line between column 2 and 3");
        }
        CellGrid walled = new CellGrid(pieces, W, H);
        assertTrue(walled.isClosed(east));
        assertTrue(walled.isClosed(new Edge(east.b(), east.a())), "whichever way round");
        assertTrue(walled.isOpen(south));
        assertEquals(1, new CellGrid(pieces.subList(0, 1), W, H).piecesStanding(east));
        assertFalse(new CellGrid(pieces.subList(0, 2), W, H).isClosed(east));
        Point2D mid = empty.midpointOf(east);
        assertEquals(180, mid.getX(), 1e-9);
        assertEquals(150, mid.getY(), 1e-9);
        assertEquals(180, empty.pieces(south).get(0).getStart().getY(), 1e-9, "a horizontal wall under row 2");
    }

    @Test
    @DisplayName("a wall built into a world is breakable, closes the edge and the navigation graph edges across it")
    void builtWallClosesTheNavigation() {
        GameMazeWorld world = new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(W, H, 20, 60, 60, 40)));
        CellGrid grid = new CellGrid(world.getMazeVectors(), W, H);
        Edge open = null;
        for (int r = 1; r < grid.rows() - 1 && open == null; r++) {
            for (int c = 1; c < grid.cols() - 1 && open == null; c++) {
                Edge e = new Edge(new Cell(r, c), new Cell(r, c + 1));
                if (grid.isOpen(e)) {
                    open = e;
                }
            }
        }
        assertTrue(open != null);
        int walls = world.getMazeVectors().size();
        int breakable = world.getBreakableWalls().size();
        List<Vector2D> pieces = grid.pieces(open);
        for (Vector2D piece : pieces) {
            world.addBuiltWall(piece, new WallMaterialSpec("WOOD_BASIC", "Wood", 20));
        }
        assertEquals(walls + 3, world.getMazeVectors().size());
        assertEquals(breakable + 3, world.getBreakableWalls().size(), "built walls can be broken");
        assertTrue(new CellGrid(world.getMazeVectors(), W, H).isClosed(open));
        for (Vector2D piece : pieces) {
            assertTrue(world.findBreakableWall(piece) != null);
        }
        // no navigation edge crosses the new wall any more
        var graph = world.getNavigationGraph();
        for (var column : graph.getGrid()) {
            for (var node : column) {
                for (var neighbour : node.getNeighbors()) {
                    Vector2D edge = new Vector2D(node.toPoint2D(), neighbour.toPoint2D());
                    for (Vector2D piece : pieces) {
                        assertFalse(edge.doIntersect(piece, 0), "an edge crosses a built wall");
                    }
                }
            }
        }
    }
}
