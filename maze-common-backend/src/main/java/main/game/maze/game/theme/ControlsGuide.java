package main.game.maze.game.theme;

import java.util.List;
import main.game.maze.common.input.GameAction;

/**
 * The help screen of the main menu ("Help & Controls"), the same in both frontends: what each key does, drawn as keys
 * with a picture of what happens, and a few words on how the game is won. The keys are those of the frontends' input
 * bindings ({@code JavaFxInputBindingsSupport}, {@code GdxGameInputBindingsSupport}). The look is the {@link MenuTheme}.
 */
public final class ControlsGuide {

    public static final String TITLE = "FIELD MANUAL";
    public static final String SUBTITLE = "What every key does";

    /** The four arrow keys; drawn as arrow keycaps ({@link MenuTheme.Art#KEY_UP} ...), every other key as text on a key. */
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";

    /**
     * One key (or group of keys) and what it does: the keys, the action, a short title, a sentence, and the picture of
     * what it does (a classpath image, or null).
     */
    public record Entry(GameAction action, List<String> keys, String title, String text, String icon) {
        public Entry {
            keys = List.copyOf(keys);
        }

        /** Whether the keys are the arrow keys, drawn as the cross of four arrow keys. */
        public boolean arrows() {
            return keys.equals(List.of(UP, LEFT, DOWN, RIGHT));
        }
    }

    /** A line on how the game works, with its picture. */
    public record Tip(String text, String icon) {
    }

    private static final List<Entry> ENTRIES = List.of(
            new Entry(GameAction.MOVE_PLAYER, List.of(UP, LEFT, DOWN, RIGHT), "Move",
                    "Run through the maze. W A S D and the number pad work too.", null),
            new Entry(GameAction.FLAME_ATTACK, List.of("SPACE"), "Use weapon",
                    "Plant a bomb, throw a grenade, fire a gun, call in a strike.",
                    "/main/game/maze/pickups/bomb.png"),
            new Entry(GameAction.SWITCH_WEAPON, List.of("TAB"), "Switch weapon",
                    "Take the next weapon you carry in hand.", "/main/game/maze/pickups/laser-rifle.png"),
            new Entry(GameAction.APPLY_PATH_HINT, List.of("P"), "Path hint",
                    "Hold to see the way to the heart. It costs score.", "/main/game/maze/pickups/heal-arrow.png"),
            new Entry(GameAction.TOGGLE_SPANNING_TREE, List.of("O"), "Maze map",
                    "Show or hide the maze's spanning tree.", null),
            new Entry(GameAction.OPEN_HIGH_SCORES, List.of("H"), "High scores",
                    "See the best scores.", "/main/game/maze/ui/token.png"),
            new Entry(GameAction.RETURN_TO_MENU, List.of("ESC"), "Menu",
                    "Back to the main menu.", null));

    private static final List<Tip> TIPS = List.of(
            new Tip("Reach the heart in the middle of the maze to win the level.", "/main/game/maze/pickups/heart.png"),
            new Tip("Between levels, spend your score on weapons in the supply depot.", "/main/game/maze/ui/token.png"));

    private ControlsGuide() {
    }

    /** What the keys do, in the order the help screen shows them. */
    public static List<Entry> entries() {
        return ENTRIES;
    }

    /** How the game is won. */
    public static List<Tip> tips() {
        return TIPS;
    }
}
