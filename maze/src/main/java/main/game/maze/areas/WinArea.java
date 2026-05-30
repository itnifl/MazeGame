package main.game.maze.areas;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import main.game.maze.actions.WinGameAction;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;

public class WinArea implements ICanSubscribeAndNotifyPosition, ICanLetYouWin {
    private static final Logger LOGGER = Logger.getLogger(WinArea.class.getName());
    private static final double INNER_HITBOX_SIZE_RATIO = 1.0 / 3.0;
    private static final double CENTER_OFFSET_RATIO = 0.5;
    private static final String WIN_ACTION_MISSING_MESSAGE = "WinGameAction is not defined";
    private static final String WIN_GAME_ERROR_LOG_MESSAGE = "Error during WinGame";
    private Node areaGraphics;
    private List<ICanSubscribeAndNotifyPosition> winTargets = new ArrayList<ICanSubscribeAndNotifyPosition>();
    private WinGameAction winGameAction;

    public WinArea(Node areaGraphics) {

        this.areaGraphics = areaGraphics;

    }

    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {

        // full bounds of the heart image (areaGraphics)
        Bounds full = this.areaGraphics.getBoundsInParent();

        // compute a centered inner box from a configurable hitbox ratio
        double innerWidth = full.getWidth() * INNER_HITBOX_SIZE_RATIO;
        double innerHeight = full.getHeight() * INNER_HITBOX_SIZE_RATIO;

        double innerMinX = full.getMinX() + (full.getWidth() - innerWidth) * CENTER_OFFSET_RATIO;
        double innerMinY = full.getMinY() + (full.getHeight() - innerHeight) * CENTER_OFFSET_RATIO;

        Bounds inner = new javafx.geometry.BoundingBox(
                innerMinX,
                innerMinY,
                innerWidth,
                innerHeight
        );

        // intersection now only checks the inner third
        if (nodeBounds.intersects(inner)) {
            if (entity instanceof PlayerCharacter) {
                try {
                    this.WinGame();
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, WIN_GAME_ERROR_LOG_MESSAGE, e);
                }
            }
        }
    }


    @Override
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        winTargets.add(touchEntity);
    }

        @Override
    public void removePositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
            winTargets.remove(touchEntity);
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return winTargets;
    }

    @Override
    public void WinGame() throws Exception {
        if (winGameAction == null) {
            throw new Exception(WIN_ACTION_MISSING_MESSAGE);
        }
        winGameAction.WinGame();
    }

    public void AddWinGameAction(WinGameAction winGameAction) {
        this.winGameAction = winGameAction;
    }
}
