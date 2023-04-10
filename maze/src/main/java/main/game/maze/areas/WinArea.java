package main.game.maze.areas;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import main.game.maze.actions.WinGameAction;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;

public class WinArea implements ICanSubscribeAndNotifyPosition, ICanLetYouWin {
    private Node areaGraphics;
    private List<ICanSubscribeAndNotifyPosition> winTargets = new ArrayList<ICanSubscribeAndNotifyPosition>();
    private WinGameAction winGameAction;

    public WinArea(Node areaGraphics) {

        this.areaGraphics = areaGraphics;

    }

    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if (nodeBounds.intersects(this.areaGraphics.getBoundsInParent())) {
            if (entity instanceof PlayerCharacter) {
                try {
                    this.WinGame();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    @Override
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
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
