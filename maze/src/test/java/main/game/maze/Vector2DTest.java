package main.game.maze;

import javafx.geometry.Point2D;
import main.game.maze.Vector2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

public class Vector2DTest {
    @Test
    public void TwoVectorsIntersect_true() {
        Vector2D vector1 = new Vector2D(0, 0, 100, 100);
        Vector2D vector2 = new Vector2D(100, 0, 0, 100);
        
        var intersect = vector1.doIntersect(vector2, 1);

        Assertions.assertEquals(intersect, true);
    }

    @Test
    public void TwoVectorsIntersect_false() {
        Vector2D vector1 = new Vector2D(0, 150, 0, 160);
        Vector2D vector2 = new Vector2D(5, 155, 5, 165);
        
        var intersect = vector1.doIntersect(vector2, 1);

        Assertions.assertEquals(intersect, false);
    }
}
