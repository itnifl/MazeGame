package main.game.maze.libgdx.flow;

import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.game.runtime.EnemyDirectorService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GdxGameCombatAndEnemyFlowSupportTest {

    private static MazeArena openMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx()  { return 400f; }
            @Override public float heightPx() { return 400f; }
            @Override public float startX()   { return 8f; }
            @Override public float startY()   { return 8f; }
            @Override public float goalX()    { return 380f; }
            @Override public float goalY()    { return 380f; }
        };
    }

    // advanceEnemies: empty enemy list is a no-op (must not throw).
    @Test
    void advanceEnemies_emptyList_doesNotThrow() {
        MazeArena maze = openMaze();
        PlayerState player = new PlayerState(100f, 100f, 16f);
        EnemyDirectorService director = new EnemyDirectorService();

        assertDoesNotThrow(() ->
                GdxGameCombatAndEnemyFlowSupport.advanceEnemies(
                        List.of(), maze, player, director, 0.016f));
    }

    // advanceEnemies: null maze is a no-op (must not throw).
    @Test
    void advanceEnemies_nullMaze_doesNotThrow() {
        EnemyDirectorService director = new EnemyDirectorService();
        assertDoesNotThrow(() ->
                GdxGameCombatAndEnemyFlowSupport.advanceEnemies(
                        List.of(), null, new PlayerState(0f, 0f, 16f), director, 0.016f));
    }

    // advanceEnemies: null player is a no-op (must not throw).
    @Test
    void advanceEnemies_nullPlayer_doesNotThrow() {
        EnemyDirectorService director = new EnemyDirectorService();
        assertDoesNotThrow(() ->
                GdxGameCombatAndEnemyFlowSupport.advanceEnemies(
                        List.of(), openMaze(), null, director, 0.016f));
    }

    // shouldTriggerWin: returns false when mode is not PLAYING.
    @Test
    void shouldTriggerWin_notPlayingMode_returnsFalse() {
        GameSession session = new GameSession();
        session.setMode(GameMode.START_MENU);
        GameWorldModel worldModel = new GameWorldModel();
        worldModel.setActiveGoalX(380f);
        worldModel.setActiveGoalY(380f);
        worldModel.setActiveGoalSize(32f);
        PlayerState player = new PlayerState(380f, 380f, 16f); // at goal

        assertFalse(GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, false, player, worldModel),
                "shouldTriggerWin must return false when game mode is not PLAYING");
    }

    // shouldTriggerWin: returns false when combatFrameDead is true.
    @Test
    void shouldTriggerWin_playerDead_returnsFalse() {
        GameSession session = new GameSession();
        session.setMode(GameMode.PLAYING);
        GameWorldModel worldModel = new GameWorldModel();
        worldModel.setActiveGoalX(380f);
        worldModel.setActiveGoalY(380f);
        worldModel.setActiveGoalSize(32f);
        PlayerState player = new PlayerState(380f, 380f, 16f);

        assertFalse(GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, true, player, worldModel),
                "shouldTriggerWin must return false when player is dead");
    }

    // shouldTriggerWin: returns false when player is far from goal.
    @Test
    void shouldTriggerWin_playerFarFromGoal_returnsFalse() {
        GameSession session = new GameSession();
        session.setMode(GameMode.PLAYING);
        GameWorldModel worldModel = new GameWorldModel();
        worldModel.setActiveGoalX(380f);
        worldModel.setActiveGoalY(380f);
        worldModel.setActiveGoalSize(32f);
        PlayerState player = new PlayerState(50f, 50f, 16f); // far from goal

        assertFalse(GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, false, player, worldModel));
    }

    // triggerWin: win sound is only played once even if triggerWin is called twice.
    @Test
    void triggerWin_calledTwice_winSoundPlayedOnlyOnce() {
        AtomicInteger musicSwitchCalls = new AtomicInteger();
        GameSession session = new GameSession();
        session.setMode(GameMode.PLAYING);
        GameWorldModel worldModel = new GameWorldModel();
        // stub WinOverlayController that does nothing
        main.game.maze.libgdx.controller.GdxWinOverlayController winOverlay =
                new main.game.maze.libgdx.controller.GdxWinOverlayController();

        GdxGameCombatAndEnemyFlowSupport.triggerWin(session, winOverlay, worldModel, musicSwitchCalls::incrementAndGet);
        GdxGameCombatAndEnemyFlowSupport.triggerWin(session, winOverlay, worldModel, musicSwitchCalls::incrementAndGet);

        assertEquals(1, musicSwitchCalls.get(),
                "Win music switch must be triggered only once across repeated triggerWin calls");
    }

    // triggerWin: sets game mode to WON.
    @Test
    void triggerWin_setsGameModeToWon() {
        GameSession session = new GameSession();
        session.setMode(GameMode.PLAYING);
        GameWorldModel worldModel = new GameWorldModel();
        main.game.maze.libgdx.controller.GdxWinOverlayController winOverlay =
                new main.game.maze.libgdx.controller.GdxWinOverlayController();

        GdxGameCombatAndEnemyFlowSupport.triggerWin(session, winOverlay, worldModel, () -> {});

        assertEquals(GameMode.WON, session.mode(), "triggerWin must set game mode to WON");
    }
}
