package main.game.maze.config.model;

import main.game.maze.constants.PlayerConstants;

public record PlayerConfig(
        String id,
        String displayName,
        boolean enabled,
        int health,
        double speed,
        String imageBase,
        String imageTurnLeft,
        String imageTurnRight,
        String imageTurnUp,
        String imageTurnDown,
        String imageDeath) {

    public static PlayerConfig defaults() {
        return new PlayerConfig(
                "player_default",
                "Player",
                true,
                PlayerConstants.DefaultHealth,
                PlayerConstants.DefaultSpeed,
                PlayerConstants.DefaultPlayerImage,
                PlayerConstants.DefaultPlayerImage,
                PlayerConstants.DefaultPlayerImage,
                PlayerConstants.DefaultPlayerImage,
                PlayerConstants.DefaultPlayerImage,
                PlayerConstants.DefaultDeathImage);
    }
}
