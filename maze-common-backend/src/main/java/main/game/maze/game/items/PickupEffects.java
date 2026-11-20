package main.game.maze.game.items;

import java.util.function.IntConsumer;

/** What picking up an item does, the same in both frontends. */
public final class PickupEffects {

    private PickupEffects() {
    }

    /**
     * @param heal         restores health (the frontend caps it at the player's maximum)
     * @param maxHitPoints the player's maximum health, for the heart's percentage
     */
    public static void apply(PickupSpec spec, PlayerArsenal arsenal, PowerShield shield,
                             IntConsumer heal, int maxHitPoints) {
        switch (spec.kind()) {
            case BOMB, LASER_RIFLE, SLEDGEHAMMER -> arsenal.add(spec.kind().weapon(), spec.amount());
            case SHIELD -> shield.activate(spec.durationSeconds());
            case HEART -> heal.accept(Math.max(1, Math.round(maxHitPoints * spec.amount() / 100f)));
        }
    }
}
