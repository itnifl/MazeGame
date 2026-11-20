package main.game.maze.game.items;

/** What the HUD shows about the player's weapons and shield; the same text in both frontends. */
public final class ArsenalHud {

    private ArsenalHud() {
    }

    /**
     * For example {@code "Bombs 3  [Laser 5]  Sledge 0  Shield 4s"}: every weapon with its stock,
     * the one in hand in brackets, and the shield time while it is up.
     */
    public static String text(PlayerArsenal arsenal, PowerShield shield) {
        StringBuilder text = new StringBuilder();
        for (Weapon weapon : Weapon.values()) {
            if (!text.isEmpty()) {
                text.append("  ");
            }
            String entry = label(weapon) + " " + arsenal.count(weapon);
            text.append(weapon == arsenal.selected() ? "[" + entry + "]" : entry);
        }
        if (shield != null && shield.isActive()) {
            text.append("  Shield ").append((int) Math.ceil(shield.remainingSeconds())).append('s');
        }
        return text.toString();
    }

    static String label(Weapon weapon) {
        return switch (weapon) {
            case BOMB -> "Bombs";
            case LASER_RIFLE -> "Laser";
            case SLEDGEHAMMER -> "Sledge";
        };
    }

    /** Sparkle colour {red, green, blue} shown when this item is picked up. */
    public static float[] sparkleColour(PickupKind kind) {
        return switch (kind) {
            case BOMB -> new float[] {1.0f, 0.6f, 0.15f};
            case LASER_RIFLE -> new float[] {1.0f, 0.25f, 0.25f};
            case SLEDGEHAMMER -> new float[] {0.8f, 0.82f, 0.9f};
            case SHIELD -> new float[] {0.35f, 0.85f, 1.0f};
            case HEART -> new float[] {1.0f, 0.4f, 0.55f};
        };
    }
}
