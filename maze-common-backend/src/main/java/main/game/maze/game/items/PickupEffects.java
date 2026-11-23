package main.game.maze.game.items;

import java.util.function.IntConsumer;
import main.game.maze.game.level.LevelBoosts;
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
        apply(spec, arsenal, shield, heal, maxHitPoints, lives, LevelBoosts.NONE);
    }

    /** Message shown when the extra life is used at full health (it is then not spent). */
    public static final String HEALTH_ALREADY_FULL = "Health is already full - the extra life is kept";

    /**
     * The extra life of the shop ({@link Weapon#LIFE_REFILL}): how much health it gives back to fill the bar; 0 when the
     * bar is full (the frontends then keep it instead of wasting it).
     */
    public static int lifeRefillAmount(int hitPoints, int maxHitPoints) {
        return Math.max(0, maxHitPoints - Math.max(0, hitPoints));
    }

    /**
     * As above, with the boosts bought in the shop for this level: a shield cube picked up while the "extra long shield"
     * is on lasts {@link LevelBoosts#shieldDurationFactor()} times as long.
     */
    public static void apply(PickupSpec spec, PlayerArsenal arsenal, PowerShield shield,
                             IntConsumer heal, int maxHitPoints, PlayerLives lives, LevelBoosts boosts) {
        LevelBoosts active = boosts == null ? LevelBoosts.NONE : boosts;
        Weapon weapon = spec.kind().weapon();
        if (weapon != null) {
            arsenal.add(weapon, spec.amount());
            return;
        }
        switch (spec.kind()) {
            case SHIELD -> shield.activate(spec.durationSeconds() * active.shieldDurationFactor());
            case HEART -> heal.accept(Math.max(1, Math.round(maxHitPoints * spec.amount() / 100f)));
            case EXTRA_LIFE -> {
                for (int i = 0; i < Math.max(1, spec.amount()); i++) {
                    lives.gain();
                }
            }
            default -> throw new IllegalStateException("no effect defined for " + spec.kind());
        }
    }
}
