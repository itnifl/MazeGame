package main.game.maze.game.items;

import java.util.ArrayList;
import java.util.List;

/**
 * The weapons HUD in the top-left corner (both frontends): one icon per weapon the player has, plus
 * the one in hand even when it is empty, with its stock; the weapon in hand is highlighted, so Tab
 * visibly moves the highlight. The player's lives are shown next to it with {@link #lifeIcon}.
 */
public final class WeaponHud {

    /** One weapon icon: its stock and whether it is in the player's hands. */
    public record Slot(Weapon weapon, int count, boolean selected, String sprite) {
    }

    /** Everything the weapons HUD shows: the weapon icons and the lives counter. */
    public record Model(List<Slot> slots, int lives, String lifeIcon) {
        public Model {
            slots = List.copyOf(slots);
        }
    }

    /** Panel geometry shared by both frontends: icon size, padding and the lives cell width. */
    public static final double ICON_SIZE = 32;
    public static final double PADDING = 6;
    public static final double LIVES_CELL_WIDTH = 64;

    /** The HUD for this arsenal and number of lives. */
    public static Model model(PlayerArsenal arsenal, PickupCatalog catalog, int lives) {
        return new Model(slots(arsenal, catalog), lives, lifeIcon(catalog));
    }

    /** Left x of the icon at {@code index}, measured from the panel's left edge. */
    public static double slotX(int index) {
        return PADDING + index * (ICON_SIZE + PADDING);
    }

    /** Left x of the lives cell, after the {@code slotCount} weapon icons. */
    public static double livesX(int slotCount) {
        return slotX(slotCount) + PADDING;
    }

    /** Panel width for this many weapon icons plus the lives cell. */
    public static double panelWidth(int slotCount) {
        return livesX(slotCount) + LIVES_CELL_WIDTH + PADDING;
    }

    /** Panel height: one row of icons. */
    public static double panelHeight() {
        return ICON_SIZE + 2 * PADDING;
    }

    private WeaponHud() {
    }

    /** The icons to show, in weapon order. */
    public static List<Slot> slots(PlayerArsenal arsenal, PickupCatalog catalog) {
        List<Slot> slots = new ArrayList<>();
        Weapon selected = arsenal.selected();
        for (Weapon weapon : Weapon.values()) {
            int count = arsenal.count(weapon);
            if (count > 0 || weapon == selected) {
                slots.add(new Slot(weapon, count, weapon == selected, catalog.spec(weapon.pickup()).sprite()));
            }
        }
        return slots;
    }

    /** Icon for the lives counter: the extra-life item's sprite. */
    public static String lifeIcon(PickupCatalog catalog) {
        return catalog.spec(PickupKind.EXTRA_LIFE).sprite();
    }
}
