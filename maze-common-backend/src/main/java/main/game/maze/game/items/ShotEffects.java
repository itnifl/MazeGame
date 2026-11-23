package main.game.maze.game.items;

import main.game.maze.common.graphics.fire.BulletSprayEffect;
import main.game.maze.common.graphics.fire.LaserBeamEffect;
import main.game.maze.common.graphics.fire.PuffEffect;

/** Which visual a shot has, the same in both frontends: the gatling gun sprays bullets, other guns fire a beam. */
public final class ShotEffects {

    /** Bullets in the spray of one enemy round (the sergeant's burst is several rounds). */
    public static final int ENEMY_ROUND_BULLETS = 3;

    private ShotEffects() {
    }

    /** The effect of one use of a player weapon along (x1, y1) -> (x2, y2). */
    public static PuffEffect forWeapon(PickupKind weapon, double x1, double y1, double x2, double y2) {
        return switch (weapon) {
            case PULSE_CANNON -> new main.game.maze.common.graphics.fire.PulseBeamEffect(x1, y1, x2, y2);
            case BUNKER_BUSTER -> new main.game.maze.common.graphics.fire.RocketFlightEffect(x1, y1, x2, y2);
            case GATLING_GUN -> new BulletSprayEffect(x1, y1, x2, y2);
            default -> new LaserBeamEffect(x1, y1, x2, y2);
        };
    }

    /** One round of a sergeant's gatling gun. */
    public static PuffEffect enemyRound(double x1, double y1, double x2, double y2) {
        return new BulletSprayEffect(x1, y1, x2, y2, ENEMY_ROUND_BULLETS);
    }

    /**
     * The power a player's shot carries: the bunker buster's rocket carries its {@code piercePower} through the walls in its
     * way; every other weapon its damage.
     */
    public static int shotPower(PickupSpec weapon) {
        return weapon.piercePower() > 0 ? weapon.piercePower() : weapon.damage();
    }

    /** Whether the shot of this weapon breaks through breakable walls (pulse beam, bunker buster). */
    public static boolean pierces(PickupSpec weapon) {
        Weapon held = weapon.kind().weapon();
        return held != null && held.piercesWalls();
    }
}
