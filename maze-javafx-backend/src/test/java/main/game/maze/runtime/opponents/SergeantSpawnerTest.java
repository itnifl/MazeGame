package main.game.maze.runtime.opponents;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Sergeant spawner (JavaFX)")
class SergeantSpawnerTest {

    private static final SergeantSpec SPEC = LevelCatalogLoader.loadDefault().level(2).sergeant();

    @Test
    @DisplayName("the sergeant's zombie model carries the values of the levels model")
    void modelFollowsTheLevelsModel() {
        Zombie model = SergeantSpawner.modelOf(SPEC);
        assertEquals(SPEC.health(), model.getHealth());
        assertEquals(SPEC.sprite(), model.getImageBase());
        assertEquals(SPEC.sprite(), model.getImageTurnLeft());
        assertEquals(BehaviorType.PASSIVE, model.getBehavior());
        assertEquals(0, model.getResurrectionTime(), "sergeants do not come back");
        assertEquals(0, model.getAttackDamage(), "the gun is the danger, not the touch");
    }

    @Test
    @DisplayName("nothing is placed for a level without sergeants")
    void levelWithoutSergeants() {
        assertEquals(0, SergeantSpawner.spawn(null, LevelCatalogLoader.loadDefault().level(1), null, null));
    }
}
