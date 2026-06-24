package main.game.maze.config.model;

import main.game.maze.constants.PlayerConstants;

public record PlayerConfig(
        String id,
        String displayName,
        boolean enabled,
        int health,
        double speed,
    int bombCount,
    int bombDamage,
        String imageBase,
        String imageTurnLeft,
        String imageTurnRight,
        String imageTurnUp,
        String imageTurnDown,
        String imageDeath) {

    public static PlayerConfig defaults() {
        String base = PlayerConstants.DefaultPlayerImage;
        return new PlayerConfig(
                "player_default",
                "Player",
                true,
                PlayerConstants.DefaultHealth,
                PlayerConstants.DefaultSpeed,
                3,
                100,
                base,
                base,
                base,
                base,
                base,
                PlayerConstants.DefaultDeathImage);
    }
}
