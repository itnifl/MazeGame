package main.game.maze.game.items;

/** What the HUD shows about the player's weapons and shield; the same text in both frontends. */
public final class ArsenalHud {

    private ArsenalHud() {
    }

    /** Smallest scale the HUD line is shrunk to; below this it would be unreadable. */
    public static final float MIN_FIT_SCALE = 0.5f;

    /**
     * For example {@code "Bombs 3 [Laser 5] Shield 4s"}: the weapons in stock and the one in hand
     * (in brackets, even when empty), and the shield time while it is up. Weapons the player has none
     * of are left out, so the line fits the small score panel.
     */
    public static String text(PlayerArsenal arsenal, PowerShield shield) {
        StringBuilder text = new StringBuilder();
        for (Weapon weapon : Weapon.values()) {
            int count = arsenal.count(weapon);
            boolean selected = weapon == arsenal.selected();
            if (count <= 0 && !selected) {
                continue;
            }
            if (!text.isEmpty()) {
                text.append(' ');
            }
            String entry = label(weapon) + " " + count;
            text.append(selected ? "[" + entry + "]" : entry);
        }
        if (shield != null && shield.isActive()) {
            text.append(" Shield ").append((int) Math.ceil(shield.remainingSeconds())).append('s');
        }
        return text.toString();
    }

    /**
     * Scale that makes a HUD line {@code textWidth} wide fit {@code availableWidth}: 1 when it
     * already fits, never below {@link #MIN_FIT_SCALE}. Both frontends shrink the line with it.
     */
    public static float fitScale(double textWidth, double availableWidth) {
        if (!(textWidth > availableWidth) || availableWidth <= 0) {
            return 1f;
        }
        return (float) Math.max(MIN_FIT_SCALE, availableWidth / textWidth);
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
