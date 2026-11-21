package main.game.maze.runtime.opponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Wall builder spawner (JavaFX)")
class WallBuilderSpawnerTest {

    private static final WallBuilderSpec SPEC = LevelCatalogLoader.loadDefault().level(5).wallBuilder();

    @Test
    @DisplayName("the builder zombie model carries the levels model values: harmless, killable, never resurrecting")
    void modelFollowsTheLevelsModel() {
        Zombie model = WallBuilderSpawner.modelOf(SPEC, "builder_0");
        assertEquals(SPEC.health(), model.getHealth());
        assertEquals(SPEC.sprite(), model.getImageBase());
        assertEquals(BehaviorType.PASSIVE, model.getBehavior());
        assertEquals(0, model.getResurrectionTime(), "builders do not come back");
        assertEquals(0, model.getAttackDamage(), "builders cannot harm the player");
        assertTrue(WallBuilderSpec.isBuilderId(model.getId()));
        assertFalse(model.isDamageStatesEnabled());
    }

    @Test
    @DisplayName("nothing is placed for a level without builders")
    void levelWithoutBuilders() {
        assertEquals(0, WallBuilderSpawner.spawn(null, LevelCatalogLoader.loadDefault().level(4), null, null));
        assertTrue(WallBuilderSpawner.spotsFor(LevelCatalogLoader.loadDefault().level(4), null, null).isEmpty());
    }
}
