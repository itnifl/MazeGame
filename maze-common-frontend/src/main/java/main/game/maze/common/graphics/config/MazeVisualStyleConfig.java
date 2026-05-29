package main.game.maze.common.graphics.config;

import java.util.Objects;

/**
 * Shared visual and audio style contract consumed by both JavaFX and libGDX.
 */
public record MazeVisualStyleConfig(
        String easyBackgroundImagePath,
        String normalBackgroundImagePath,
        String hardBackgroundImagePath,
        String menuIconImagePath,
        String goalImagePath,
        String easyWallTypeId,
        String normalWallTypeId,
        String hardWallTypeId,
        String menuMusicPath,
        String menuSelectSoundPath,
        String inGameMusicPath,
        String winSoundPath) {

    public static final MazeVisualStyleConfig DEFAULT = new MazeVisualStyleConfig(
            "/main/game/maze/gameBackGround1.png",
            "/main/game/maze/gameBackGround2.png",
            "/main/game/maze/gameBackGround3.png",
            "/main/game/maze/ghost1.png",
            "/main/game/maze/heart2.png",
            "DIRT_BASIC",
            "WOOD_BASIC",
            "STEEL_SOLID",
            "/main/game/maze/menuMusic.mp3",
            "/main/game/maze/menuSelect.mp3",
            "/main/game/maze/backgroundMusic.mp3",
            "/main/game/maze/winGame.mp3");

    public MazeVisualStyleConfig {
        easyBackgroundImagePath = requireText(easyBackgroundImagePath, "easyBackgroundImagePath");
        normalBackgroundImagePath = requireText(normalBackgroundImagePath, "normalBackgroundImagePath");
        hardBackgroundImagePath = requireText(hardBackgroundImagePath, "hardBackgroundImagePath");
        menuIconImagePath = requireText(menuIconImagePath, "menuIconImagePath");
        goalImagePath = requireText(goalImagePath, "goalImagePath");
        easyWallTypeId = requireText(easyWallTypeId, "easyWallTypeId");
        normalWallTypeId = requireText(normalWallTypeId, "normalWallTypeId");
        hardWallTypeId = requireText(hardWallTypeId, "hardWallTypeId");
        menuMusicPath = requireText(menuMusicPath, "menuMusicPath");
        menuSelectSoundPath = requireText(menuSelectSoundPath, "menuSelectSoundPath");
        inGameMusicPath = requireText(inGameMusicPath, "inGameMusicPath");
        winSoundPath = requireText(winSoundPath, "winSoundPath");
    }

    public String backgroundImageForDifficultyName(String difficultyName) {
        String token = token(difficultyName);
        if (token.contains("hard")) {
            return hardBackgroundImagePath;
        }
        if (token.contains("normal")) {
            return normalBackgroundImagePath;
        }
        return easyBackgroundImagePath;
    }

    public String wallTypeIdForDifficultyName(String difficultyName) {
        String token = token(difficultyName);
        if (token.contains("hard")) {
            return hardWallTypeId;
        }
        if (token.contains("normal")) {
            return normalWallTypeId;
        }
        return easyWallTypeId;
    }

    private static String token(String difficultyName) {
        return difficultyName == null ? "" : difficultyName.trim().toLowerCase();
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
