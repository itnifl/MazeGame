package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.game.level.TeleportGhostSpec;
import main.game.maze.opponents.Ghost;

/**
 * The ghost that teleports the player (JavaFX): it never hurts. When it touches the player (and no wall is
 * between them) the player is sent to a random spot at the edge of the map; the game decides whether the
 * cooldown allows it. Moving, being hit and dying come from {@link GhostCharacter}.
 */
public class PortalGhostCharacter extends GhostCharacter {

    private final TeleportGhostSpec spec;

    public PortalGhostCharacter(Node characterGraphics, double x, double y, Ghost model, TeleportGhostSpec spec) {
        super(characterGraphics, x, y, model);
        this.spec = spec;
    }

    public TeleportGhostSpec getSpec() {
        return spec;
    }

    /** A portal ghost does no damage. */
    @Override
    public int getDamage() {
        return 0;
    }

    public void handlePlayerContact(PositionBounds playerBounds, PlayerCharacter player) {
        var graphics = getCharacterGraphics();
        if (player == null || graphics == null || !isAlive()) {
            return;
        }
        if (playerBounds == null) {
            var playerGraphics = player.getCharacterGraphics();
            if (playerGraphics == null) {
                return;
            }
            playerBounds = new FxPositionBounds(playerGraphics.getBoundsInParent());
        }
        var myBounds = new FxPositionBounds(graphics.getBoundsInParent());
        if (!playerBounds.intersects(myBounds)) {
            return;
        }
        if (App.gameController != null
                && App.gameController.isWallBetween(myBounds.getCenterX(), myBounds.getCenterY(),
                        playerBounds.getCenterX(), playerBounds.getCenterY())) {
            return;
        }
        if (App.gameController != null) {
            App.gameController.onPortalGhostTouch();
        }
    }

    @Override
    public void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if (entity instanceof PlayerCharacter player) {
            handlePlayerContact(nodeBounds, player);
        }
    }
}
