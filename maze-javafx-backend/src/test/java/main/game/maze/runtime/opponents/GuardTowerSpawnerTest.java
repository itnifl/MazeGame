package main.game.maze.runtime.opponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Guard tower spawner (JavaFX)")
class GuardTowerSpawnerTest {

    private static final GuardTowerSpec SPEC = LevelCatalogLoader.loadDefault().level(4).guardTower();

    @Test
    @DisplayName("the tower's enemy model carries the values of the levels model and shows its damage")
    void modelFollowsTheLevelsModel() {
        Zombie model = GuardTowerSpawner.modelOf(SPEC, "tower_0");
        assertEquals("tower_0", model.getId());
        assertEquals(SPEC.health(), model.getHealth());
        assertEquals(SPEC.sprite(), model.getImageBase());
        assertEquals(BehaviorType.PASSIVE, model.getBehavior());
        assertEquals(0, model.getResurrectionTime(), "towers do not come back");
        assertTrue(model.isDamageStatesEnabled(), "a damaged tower looks damaged");
        assertTrue(GuardTowerSpec.isTowerId(model.getId()));
    }

    @Test
    @DisplayName("nothing is placed for a level without towers")
    void levelWithoutTowers() {
        assertEquals(0, GuardTowerSpawner.spawn(null, LevelCatalogLoader.loadDefault().level(3), null, null, 0, 0));
        assertTrue(GuardTowerSpawner.postsFor(LevelCatalogLoader.loadDefault().level(3), null, null, 0, 0).isEmpty());
    }
}
