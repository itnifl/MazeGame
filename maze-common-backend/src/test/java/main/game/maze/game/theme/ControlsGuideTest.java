package main.game.maze.game.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.Set;
import main.game.maze.common.input.GameAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controls guide: the help screen of the main menu")
class ControlsGuideTest {

    @Test
    @DisplayName("every action of the game is explained (the developer terminal aside), each with its keys")
    void everyActionExplained() {
        Set<GameAction> explained = EnumSet.noneOf(GameAction.class);
        for (ControlsGuide.Entry entry : ControlsGuide.entries()) {
            assertTrue(explained.add(entry.action()), entry.action() + " once");
            assertFalse(entry.keys().isEmpty());
            assertFalse(entry.title().isBlank() || entry.text().isBlank());
            if (entry.icon() != null) {
                assertNotNull(ControlsGuide.class.getResource(entry.icon()), entry.icon());
            }
        }
        Set<GameAction> expected = EnumSet.allOf(GameAction.class);
        expected.remove(GameAction.TOGGLE_TERMINAL);
        assertEquals(expected, explained);
        assertTrue(ControlsGuide.entries().get(0).arrows(), "moving is drawn as the four arrow keys");
        ControlsGuide.tips().forEach(tip -> assertNotNull(ControlsGuide.class.getResource(tip.icon()), tip.icon()));
    }

    @Test
    @DisplayName("the arrow keys and the plain key are textures of the menu theme")
    void keysAreThemed() {
        MenuTheme theme = MenuThemeLoader.loadDefault();
        assertTrue(theme.image(MenuTheme.Art.KEY).slice() > 0, "the key stretches round its label");
        for (MenuTheme.Art art : new MenuTheme.Art[] {MenuTheme.Art.KEY_UP, MenuTheme.Art.KEY_DOWN, MenuTheme.Art.KEY_LEFT,
                MenuTheme.Art.KEY_RIGHT}) {
            assertNotNull(ControlsGuide.class.getResource(theme.image(art).path()), art.name());
        }
        assertEquals("Help & Controls", MenuButton.HELP.label());
    }
}
