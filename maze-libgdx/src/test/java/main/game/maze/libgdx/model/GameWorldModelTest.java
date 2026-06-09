package main.game.maze.libgdx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class GameWorldModelTest {

    @Test
    void initializesMutableCollectionsAndDefaults() {
        GameWorldModel model = new GameWorldModel();

        assertNotNull(model.animatedEnemies());
        assertNotNull(model.activePathPoints());
        assertNotNull(model.highScoreRows());
        assertNotNull(model.pathHintBudget());
        assertEquals(1f, model.currentHpRatio(), 0.0001f);
    }
}
