package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import main.game.maze.App;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.TeleportGhostSpec;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Ghost;
import main.game.maze.runtime.opponents.PortalGhostSpawner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Portal ghost (JavaFX): never hurts")
class PortalGhostCharacterTest {

    private static final TeleportGhostSpec SPEC = LevelCatalogLoader.loadDefault().level(3).teleportGhost();

    @BeforeAll
    static void startToolkit() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException alreadyStarted) {
            // fine
        }
    }

    @AfterEach
    void cleanup() {
        App.gameController = null;
    }

    private static PortalGhostCharacter ghost() {
        Ghost model = PortalGhostSpawner.modelOf(SPEC);
        ImageView graphics = new ImageView();
        graphics.setFitWidth(40);
        graphics.setFitHeight(40);
        return new PortalGhostCharacter(graphics, 100, 100, model, SPEC);
    }

    @Test
    @DisplayName("it does no damage")
    void noDamage() {
        assertEquals(0, ghost().getDamage());
    }

    @Test
    @DisplayName("it is built from the levels model: health, speed, sprite, wandering and solid")
    void modelFollowsTheLevelsModel() {
        Ghost model = PortalGhostSpawner.modelOf(SPEC);
        assertEquals(SPEC.health(), model.getHealth());
        assertEquals(SPEC.speed(), model.getSpeed(), 1e-9);
        assertEquals(SPEC.sprite(), model.getImageBase());
        assertEquals(BehaviorType.WANDER, model.getBehavior());
        assertEquals(0, model.getAttackDamage());
        assertEquals(0.0, model.getNonTangibilityEnergy(), 1e-9, "solid: it does not phase through walls");
    }

    @Test
    @DisplayName("touching something that is not the player, or with no game running, does nothing")
    void ignoresOthers() {
        PortalGhostCharacter ghost = ghost();
        ICanSubscribeAndNotifyPosition notPlayer = null;
        assertDoesNotThrow(() -> ghost.doPositionEvaluation(null, notPlayer));
    }
}
