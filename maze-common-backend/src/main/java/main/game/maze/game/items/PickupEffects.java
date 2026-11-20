package main.game.maze.game.items;

import java.util.function.IntConsumer;
import main.game.maze.game.session.PlayerLives;

/** What picking up an item does, the same in both frontends. */
public final class PickupEffects {

    private PickupEffects() {
    }

    /**
     * @param heal         restores health (the frontend caps it at the player's maximum)
     * @param maxHitPoints the player's maximum health, for the heart's percentage
     * @param lives        the player's lives (extra life)
     */
    public static void apply(PickupSpec spec, PlayerArsenal arsenal, PowerShield shield,
                             IntConsumer heal, int maxHitPoints, PlayerLives lives) {
        switch (spec.kind()) {
            case BOMB, LASER_RIFLE, SLEDGEHAMMER, GATLING_GUN -> arsenal.add(spec.kind().weapon(), spec.amount());
            case SHIELD -> shield.activate(spec.durationSeconds());
            case HEART -> heal.accept(Math.max(1, Math.round(maxHitPoints * spec.amount() / 100f)));
            case EXTRA_LIFE -> {
                for (int i = 0; i < Math.max(1, spec.amount()); i++) {
                    lives.gain();
                }
            }
        }
    }
}
