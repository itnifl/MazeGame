package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import main.game.maze.App;
import main.game.maze.GameController;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.PositionBounds;
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
        ImageView graphics = new ImageView(new javafx.scene.image.WritableImage(40, 40));
        graphics.setLayoutX(100);
        graphics.setLayoutY(100);
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

    @Test
    @DisplayName("stationary player is teleported when portal ghost moves into it")
    void stationaryPlayerTouchedByPortalGhostIsTeleported() {
        AtomicBoolean touchCalled = new AtomicBoolean(false);
        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return false;
            }
            @Override
            public void onPortalGhostTouch() {
                touchCalled.set(true);
            }
        };

        PortalGhostCharacter ghost = ghost();
        Rectangle playerGfx = new Rectangle(0, 0, 30, 30);
        playerGfx.setLayoutX(100);
        playerGfx.setLayoutY(100);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 100, 100, null);
        int hpBefore = player.getHitPoints();

        // Ghost moves into player -> notifies player.doPositionEvaluation(ghostBounds, ghost)
        PositionBounds ghostBounds = new FxPositionBounds(ghost.getCharacterGraphics().getBoundsInParent());
        player.doPositionEvaluation(ghostBounds, ghost);

        assertTrue(touchCalled.get(), "Portal ghost entering stationary player must trigger onPortalGhostTouch");
        assertEquals(hpBefore, player.getHitPoints(), "Portal ghost must not deal damage");
    }

    @Test
    @DisplayName("moving player into portal ghost teleports and keeps exact destination")
    void movingPlayerIntoPortalGhostKeepsExactDestination() {
        PortalGhostCharacter ghost = ghost(); // at (100, 100), 40x40
        Rectangle playerGfx = new Rectangle(0, 0, 30, 30);
        playerGfx.setLayoutX(75);
        playerGfx.setLayoutY(100);
        PlayerCharacter player = new PlayerCharacter(playerGfx, 75, 100, null);

        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return false;
            }
            @Override
            public void onPortalGhostTouch() {
                // Simulate teleport destination at (500, 300)
                player.teleportTo(500, 300);
            }
        };

        // Player subscribes ghost to position notifications
        player.addPositionSubscriber(ghost);

        // Player moves right towards ghost (75 + 10 = 85, overlapping [100, 100])
        boolean moved = player.moveRight(10, true);
        assertTrue(moved);
        assertEquals(500.0, player.getCharacterPosition().getX(), 1e-6,
                "Final logical X position must match exact teleport destination, not destination + step");
        assertEquals(300.0, player.getCharacterPosition().getY(), 1e-6,
                "Final logical Y position must match exact teleport destination");
    }
}
