package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.App;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.level.SergeantGunner;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.game.targeting.RangedTargeting;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.opponents.Zombie;

/**
 * The zombie sergeant (JavaFX): a zombie that never moves and fires its gatling gun on sight, at the
 * soldiers of the infantry strike first and at the player otherwise. The firing rules are the shared {@link SergeantGunner}; killing, remains and kill points come
 * from {@link ComputerCharacter} as for every enemy. A killed sergeant drops its gun where it fell and
 * never comes back.
 */
public class SergeantCharacter extends ZombieCharacter {

    /** What the sergeant needs from the game to shoot: walls, the player and the tracer effect. */
    public interface Range {
        boolean wallBetween(double fromX, double fromY, double toX, double toY);

        /** One round hit the player (on the JavaFX thread). */
        void hitPlayer(int damage);

        /** Draws the tracer of one round (on the JavaFX thread). */
        void tracer(double fromX, double fromY, double toX, double toY);

        /** The soldiers of the infantry strike, shot before the player (centres; none by default). */
        default SoldierTargets soldiers() {
            return SoldierTargets.NONE;
        }
    }

    private static final long FIRE_SOUND_COOLDOWN_MS = 500L;

    private final SergeantSpec spec;
    private final SergeantGunner gunner;
    private long lastTickNanos = -1;

    public SergeantCharacter(Node characterGraphics, double x, double y, Zombie model, SergeantSpec spec) {
        super(characterGraphics, x, y, model);
        this.spec = spec;
        this.gunner = new SergeantGunner(spec);
    }

    public SergeantSpec getSpec() {
        return spec;
    }

    /** Never moves; the movement loop is not asked to move a sergeant. */
    @Override
    public boolean move(boolean force) {
        return false;
    }

    /**
     * One step of the AI loop: look for the player and fire.
     *
     * @return the rounds fired in this step
     */
    public int tickGun(double playerX, double playerY, long nowNanos, Range range) {
        double dt = lastTickNanos < 0 ? 0 : (nowNanos - lastTickNanos) / 1e9;
        lastTickNanos = nowNanos;
        if (!isAlive()) {
            return 0;
        }
        double[] at = centerPoint();
        SoldierTargets soldiers = range.soldiers();
        SergeantGunner.Volley volley = gunner.fire(dt, at[0], at[1], playerX, playerY, soldiers, range::wallBetween);
        int rounds = volley.rounds();
        RangedTargeting.Target target = volley.target();
        for (int round = 0; round < rounds; round++) {
            range.tracer(at[0], at[1], target.x(), target.y());
            if (target.isSoldier()) {
                soldiers.hit(target.soldier(), spec.roundDamage());
            } else {
                range.hitPlayer(spec.roundDamage());
            }
        }
        if (rounds > 0 && spec.fireSound() != null) {
            AudioEngine.get().playRateLimited(spec.fireSound(), "sergeant.fire." + System.identityHashCode(this),
                    FIRE_SOUND_COOLDOWN_MS);
        }
        return rounds;
    }

    private double[] centerPoint() {
        var bounds = getCharacterGraphics().getBoundsInParent();
        return new double[] {bounds.getCenterX(), bounds.getCenterY()};
    }

    /** The gun lies where the sergeant fell. */
    @Override
    protected void handleDeath(DeathCause cause) {
        double[] at = getCharacterGraphics() != null ? centerPoint() : null;
        super.handleDeath(cause);
        if (at != null && App.gameController != null) {
            App.gameController.dropWeapon(spec.dropWeapon().pickup(), at[0], at[1]);
        }
    }

    /** A killed sergeant stays dead. */
    @Override
    protected void resurrect() {
        // no-op: sergeants do not resurrect
    }
}
