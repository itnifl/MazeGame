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
    /** Seconds between the free bombs the player is given. */
    public static final double BOMB_REGEN_SECONDS = 10.0;
    /** Free bombs given when the player has none left; otherwise one. */
    public static final int BOMB_REGEN_WHEN_EMPTY = 3;

    private Weapon selected = Weapon.BOMB;
    private double regenTimer;

    /** @param bombs bombs the player starts with (from the player model) */
    public PlayerArsenal(int bombs) {
        for (Weapon weapon : Weapon.values()) {
            counts.put(weapon, 0);
        }
        counts.put(Weapon.BOMB, Math.max(0, bombs));
    }

    /** What the player holds now, to carry into the next level. */
    public synchronized ArsenalSnapshot snapshot() {
        return new ArsenalSnapshot(counts, selected);
    }

    /** An arsenal holding what the snapshot held, in its own right (the original is not affected). */
    public static PlayerArsenal restore(ArsenalSnapshot snapshot) {
        PlayerArsenal arsenal = new PlayerArsenal(0);
        for (Weapon weapon : Weapon.values()) {
            arsenal.counts.put(weapon, Math.max(0, snapshot.counts().getOrDefault(weapon, 0)));
        }
        arsenal.selected = snapshot.selected();
        return arsenal;
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

    /**
     * Advances the free-bomb timer. Every {@link #BOMB_REGEN_SECONDS} the player gets one bomb, or
     * {@link #BOMB_REGEN_WHEN_EMPTY} when out of bombs.
     *
     * @return the number of bombs granted by this call (0 most of the time)
     */
    public synchronized int tickBombRegen(double dt) {
        if (dt <= 0) {
            return 0;
        }
        regenTimer += dt;
        int granted = 0;
        while (regenTimer >= BOMB_REGEN_SECONDS) {
            regenTimer -= BOMB_REGEN_SECONDS;   // keep the remainder so no elapsed time is lost
            int amount = counts.get(Weapon.BOMB) > 0 ? 1 : BOMB_REGEN_WHEN_EMPTY;
            add(Weapon.BOMB, amount);
            granted += amount;
        }
        return granted;
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
