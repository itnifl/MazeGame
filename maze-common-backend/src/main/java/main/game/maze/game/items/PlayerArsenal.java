package main.game.maze.game.items;

import java.util.EnumMap;
import java.util.Map;

/**
 * The player's weapons and ammunition, shared by both frontends: bombs, laser shots and
 * sledgehammer swings, plus the weapon in hand. Tab cycles through weapons in stock; using
 * spends one; an empty weapon in hand switches to the next one in stock. Thread-safe.
 */
public final class PlayerArsenal {

    private final Map<Weapon, Integer> counts = new EnumMap<>(Weapon.class);
    private Weapon selected = Weapon.BOMB;

    /** @param bombs bombs the player starts with (from the player model) */
    public PlayerArsenal(int bombs) {
        for (Weapon weapon : Weapon.values()) {
            counts.put(weapon, 0);
        }
        counts.put(Weapon.BOMB, Math.max(0, bombs));
    }

    public synchronized Weapon selected() {
        return selected;
    }

    public synchronized int count(Weapon weapon) {
        return counts.get(weapon);
    }

    /**
     * Adds ammunition (a picked-up bomb, five laser shots, five swings). When the weapon in hand is
     * empty, the picked-up weapon goes into the hands, so the attack key works without pressing Tab.
     */
    public synchronized void add(Weapon weapon, int amount) {
        counts.merge(weapon, Math.max(0, amount), Integer::sum);
        if (counts.get(selected) <= 0 && counts.get(weapon) > 0) {
            selected = weapon;
        }
    }

    /** Tab: the next weapon in stock after the one in hand (stays put when nothing else is in stock). */
    public synchronized Weapon cycle() {
        Weapon next = nextInStock(selected);
        if (next != null) {
            selected = next;
        }
        return selected;
    }

    /**
     * Attack key: spends one use of the weapon in hand.
     *
     * @return the weapon that was used, or null when it is empty (nothing happens)
     */
    public synchronized Weapon use() {
        if (counts.get(selected) <= 0) {
            return null;
        }
        Weapon used = selected;
        counts.merge(used, -1, Integer::sum);
        if (counts.get(used) == 0) {
            Weapon next = nextInStock(used);
            if (next != null) {
                selected = next;
            }
        }
        return used;
    }

    private Weapon nextInStock(Weapon from) {
        Weapon[] all = Weapon.values();
        for (int step = 1; step <= all.length; step++) {
            Weapon candidate = all[(from.ordinal() + step) % all.length];
            if (candidate != from && counts.get(candidate) > 0) {
                return candidate;
            }
        }
        return null;
    }
}
