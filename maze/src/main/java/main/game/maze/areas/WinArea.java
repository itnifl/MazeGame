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

        // compute a centered inner box of width/3 and height/3
        double innerWidth = full.getWidth() / 3.0;
        double innerHeight = full.getHeight() / 3.0;

        double innerMinX = full.getMinX() + (full.getWidth() - innerWidth) / 2.0;
        double innerMinY = full.getMinY() + (full.getHeight() - innerHeight) / 2.0;

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
                    LOGGER.log(Level.SEVERE, "Error during WinGame", e);
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
        winTargets.add(touchEntity);
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return winTargets;
    }

    @Override
    public void WinGame() throws Exception {
        if (winGameAction == null) {
            throw new Exception("WinGameAction is not defined");
        }
        winGameAction.WinGame();
    }

    public void AddWinGameAction(WinGameAction winGameAction) {
        this.winGameAction = winGameAction;
    }
}
