package main.game.maze.libgdx.controller;

/**
 * Owns start menu keyboard and mouse input transitions.
 */
public final class GdxStartMenuInputController {

    private boolean upLatch;
    private boolean downLatch;
    private boolean enterLatch;

    public KeyboardResult handleKeyboard(
            boolean upPressed,
            boolean downPressed,
            boolean enterPressed,
            int selectedDifficultyIndex,
            int difficultyCount) {
        int nextIndex = selectedDifficultyIndex;
        boolean playSelectSound = false;
        boolean startRequested = false;

        if (upPressed && !upLatch && difficultyCount > 0) {
            nextIndex = (selectedDifficultyIndex - 1 + difficultyCount) % difficultyCount;
            playSelectSound = true;
        }
        if (downPressed && !downLatch && difficultyCount > 0) {
            nextIndex = (selectedDifficultyIndex + 1) % difficultyCount;
            playSelectSound = true;
        }
        if (enterPressed && !enterLatch) {
            startRequested = true;
            playSelectSound = true;
        }

        upLatch = upPressed;
        downLatch = downPressed;
        enterLatch = enterPressed;
        return new KeyboardResult(nextIndex, startRequested, playSelectSound);
    }

    public MouseResult handleLeftClick(
            float mouseX,
            float mouseY,
            MenuLayoutValues layout,
            int difficultyCount,
            int selectedDifficultyIndex,
            boolean dropdownOpen) {
        int nextIndex = selectedDifficultyIndex;
        boolean nextDropdownOpen = dropdownOpen;
        boolean playSelectSound = false;
        boolean startRequested = false;
        boolean highScoresRequested = false;

        if (dropdownOpen && difficultyCount > 0) {
            float optionHeight = layout.comboH();
            for (int i = 0; i < difficultyCount; i++) {
                float optionY = layout.comboY() - (i + 1) * optionHeight;
                if (contains(mouseX, mouseY, layout.comboX(), optionY, layout.comboW(), optionHeight)) {
                    nextIndex = i;
                    nextDropdownOpen = false;
                    playSelectSound = true;
                    return new MouseResult(nextIndex, nextDropdownOpen, startRequested, highScoresRequested, playSelectSound);
                }
            }
            if (!contains(mouseX, mouseY, layout.comboX(), layout.comboY(), layout.comboW(), layout.comboH())) {
                nextDropdownOpen = false;
            }
        }

        if (contains(mouseX, mouseY, layout.comboX(), layout.comboY(), layout.comboW(), layout.comboH()) && difficultyCount > 0) {
            nextDropdownOpen = !nextDropdownOpen;
            playSelectSound = true;
            return new MouseResult(nextIndex, nextDropdownOpen, startRequested, highScoresRequested, playSelectSound);
        }

        if (contains(mouseX, mouseY, layout.buttonX(), layout.buttonY(), layout.buttonW(), layout.buttonH())) {
            nextDropdownOpen = false;
            startRequested = true;
            playSelectSound = true;
            return new MouseResult(nextIndex, nextDropdownOpen, startRequested, highScoresRequested, playSelectSound);
        }

        if (contains(mouseX, mouseY, layout.highScoresButtonX(), layout.highScoresButtonY(), layout.highScoresButtonW(), layout.highScoresButtonH())) {
            nextDropdownOpen = false;
            highScoresRequested = true;
            playSelectSound = true;
        }

        return new MouseResult(nextIndex, nextDropdownOpen, startRequested, highScoresRequested, playSelectSound);
    }

    private static boolean contains(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    public record KeyboardResult(int selectedDifficultyIndex, boolean startRequested, boolean playSelectSound) {
    }

    public record MouseResult(int selectedDifficultyIndex, boolean dropdownOpen, boolean startRequested, boolean highScoresRequested, boolean playSelectSound) {
    }

    public record MenuLayoutValues(
            float comboX,
            float comboY,
            float comboW,
            float comboH,
            float buttonX,
            float buttonY,
            float buttonW,
            float buttonH,
            float highScoresButtonX,
            float highScoresButtonY,
            float highScoresButtonW,
            float highScoresButtonH) {
    }
}


