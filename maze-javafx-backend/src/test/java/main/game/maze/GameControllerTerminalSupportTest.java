package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import main.game.maze.common.terminal.TerminalCommand;
import org.junit.jupiter.api.Test;

class GameControllerTerminalSupportTest {

    @Test
    void parseTerminalCommandRecognizesSharedVocab() {
        assertEquals(TerminalCommand.HELP, GameControllerTerminalSupport.parseTerminalCommand("/h"));
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE, GameControllerTerminalSupport.parseTerminalCommand(" /sbt "));
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE, GameControllerTerminalSupport.parseTerminalCommand("/showmovementtype"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH, GameControllerTerminalSupport.parseTerminalCommand("/sep"));
        assertEquals(TerminalCommand.KILL_ENEMIES, GameControllerTerminalSupport.parseTerminalCommand("/kill"));
        assertEquals(TerminalCommand.EMPTY, GameControllerTerminalSupport.parseTerminalCommand("   "));
        assertEquals(TerminalCommand.UNKNOWN, GameControllerTerminalSupport.parseTerminalCommand("/missing"));
    }

    @Test
    void executeTerminalCommand_killEnemies_callsSinkAndReportsCount() {
        RecordingSink sink = new RecordingSink();
        sink.killCount = 3;

        GameControllerTerminalSupport.executeTerminalCommand("/kill", sink);

        assertTrue(sink.killAllCalled, "/kill must invoke killAllEnemies() on the sink");
        assertEquals(1, sink.events.size(), "Exactly one HUD message should be produced");
        assertTrue(sink.events.get(0).contains("3"),
                "HUD message should include the kill count returned by the sink");
    }

    @Test
    void executeTerminalCommandDispatchesHelpAndPathCommands() {
        RecordingSink sink = new RecordingSink();

        GameControllerTerminalSupport.executeTerminalCommand("/h", sink);
        GameControllerTerminalSupport.executeTerminalCommand("/showenemypath", sink);

        assertEquals(List.of(
            "messageWithDuration:" + GameControllerTerminalSupport.HELP_MESSAGE + ":20.0",
                "message:" + GameControllerTerminalSupport.SHOW_ENEMY_PATH_MESSAGE,
                "showPaths"), sink.events);
        assertEquals(Duration.seconds(20), sink.lastVisibleFor);
        assertFalse(sink.behaviourLabelsShown);
    }

    @Test
    void executeTerminalCommandDispatchesBehaviourMovementAndFallbackCommands() {
        RecordingSink sink = new RecordingSink();

        GameControllerTerminalSupport.executeTerminalCommand("/sbt", sink);
        GameControllerTerminalSupport.executeTerminalCommand("/smt", sink);
        GameControllerTerminalSupport.executeTerminalCommand("", sink);
        GameControllerTerminalSupport.executeTerminalCommand("/missing", sink);

        assertTrue(sink.behaviourLabelsShown);
        assertFalse(sink.pathOverlayShown);
        assertEquals(List.of(
                "message:" + GameControllerTerminalSupport.SHOW_BEHAVIOUR_MESSAGE,
                "showLabels:true",
                "message:" + GameControllerTerminalSupport.SHOW_MOVEMENT_MESSAGE,
                "showLabels:false",
                "message:" + GameControllerTerminalSupport.EMPTY_MESSAGE,
                "message:" + GameControllerTerminalSupport.UNKNOWN_MESSAGE), sink.events);
    }

    private static final class RecordingSink implements GameControllerTerminalSupport.TerminalCommandSink {
        private final List<String> events = new ArrayList<>();
        private Duration lastVisibleFor = Duration.ZERO;
        private boolean behaviourLabelsShown;
        private boolean pathOverlayShown;
        private boolean killAllCalled;
        private int killCount;

        @Override
        public void setHudMessage(String text) {
            events.add("message:" + text);
        }

        @Override
        public void setHudMessage(String text, Duration visibleFor) {
            events.add("messageWithDuration:" + text + ":" + visibleFor.toSeconds());
            lastVisibleFor = visibleFor;
        }

        @Override
        public void showEnemyDebugLabels(boolean behaviourType) {
            behaviourLabelsShown = true;
            events.add("showLabels:" + behaviourType);
        }

        @Override
        public void showEnemyPathsOverlay() {
            pathOverlayShown = true;
            events.add("showPaths");
        }

        @Override
        public int killAllEnemies() {
            killAllCalled = true;
            return killCount;
        }
    }
}