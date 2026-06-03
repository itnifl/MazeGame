package main.game.maze.libgdx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.controller.GdxStartMenuInputController;

class GdxStartMenuInputControllerTest {

    @Test
    void keyboardUpWrapsSelection() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();

        GdxStartMenuInputController.KeyboardResult result =
                controller.handleKeyboard(true, false, false, 0, 3);

        assertEquals(2, result.selectedDifficultyIndex());
        assertTrue(result.playSelectSound());
        assertFalse(result.startRequested());
    }

    @Test
    void keyboardLatchPreventsRepeatedAdvanceWhileHeld() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();

        GdxStartMenuInputController.KeyboardResult first =
                controller.handleKeyboard(false, true, false, 0, 3);
        GdxStartMenuInputController.KeyboardResult second =
                controller.handleKeyboard(false, true, false, first.selectedDifficultyIndex(), 3);

        assertEquals(1, first.selectedDifficultyIndex());
        assertEquals(1, second.selectedDifficultyIndex());
        assertTrue(first.playSelectSound());
        assertFalse(second.playSelectSound());
    }

    @Test
    void enterRequestsStartAndSound() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();

        GdxStartMenuInputController.KeyboardResult result =
                controller.handleKeyboard(false, false, true, 1, 3);

        assertEquals(1, result.selectedDifficultyIndex());
        assertTrue(result.playSelectSound());
        assertTrue(result.startRequested());
    }

    @Test
    void mouseClickInDropdownSelectsDifficultyAndClosesDropdown() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();
        GdxStartMenuInputController.MenuLayoutValues layout = layout();

        GdxStartMenuInputController.MouseResult result = controller.handleLeftClick(
                110f,
                145f,
                layout,
                3,
                0,
                true);

        assertEquals(1, result.selectedDifficultyIndex());
        assertFalse(result.dropdownOpen());
        assertTrue(result.playSelectSound());
        assertFalse(result.startRequested());
    }

    @Test
    void mouseClickComboTogglesDropdown() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();
        GdxStartMenuInputController.MenuLayoutValues layout = layout();

        GdxStartMenuInputController.MouseResult result = controller.handleLeftClick(
                120f,
                205f,
                layout,
                3,
                0,
                false);

        assertTrue(result.dropdownOpen());
        assertTrue(result.playSelectSound());
        assertFalse(result.startRequested());
    }

    @Test
    void mouseClickStartButtonRequestsStart() {
        GdxStartMenuInputController controller = new GdxStartMenuInputController();
        GdxStartMenuInputController.MenuLayoutValues layout = layout();

        GdxStartMenuInputController.MouseResult result = controller.handleLeftClick(
                130f,
                95f,
                layout,
                3,
                2,
                true);

        assertEquals(2, result.selectedDifficultyIndex());
        assertFalse(result.dropdownOpen());
        assertTrue(result.playSelectSound());
        assertTrue(result.startRequested());
    }

    private static GdxStartMenuInputController.MenuLayoutValues layout() {
        return new GdxStartMenuInputController.MenuLayoutValues(
                100f,
                200f,
                180f,
                30f,
                100f,
                80f,
                200f,
                36f,
                100f,
                30f,
                200f,
                36f);
    }
}
