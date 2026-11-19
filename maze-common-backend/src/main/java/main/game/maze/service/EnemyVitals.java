package main.game.maze.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;

/**
 * Hit points and death of one enemy: the single implementation behind {@link ICanDie} for both
 * frontends (libGDX {@code GdxEnemyRuntime} and the JavaFX enemy characters forward to it), so
 * damage, healing, death, death causes and respawn invulnerability behave the same everywhere.
 *
 * <p>Rules: damage is ignored while dead or invulnerable; the killing blow records its
 * {@link DeathCause}, then the {@link Listener} and the death subscribers are told exactly once;
 * healing is capped at the maximum and never revives; {@link #respawn} restores full HP with a
 * brief invulnerability window. Thread-safe (JavaFX projectiles hit from a background thread).
 */
public final class EnemyVitals {

    /** Frontend hooks: redraw on HP change (damage-state sprites), leave remains / unregister on death. */
    public interface Listener {
        default void onHitPointsChanged(int hitPoints, int maxHitPoints) {
        }

        default void onDied(DeathCause cause) {
        }
    }

    private static final Listener NONE = new Listener() {
    };

    private final ICanDie owner;
    private final Listener listener;
    private final List<IDeathSubscriber> subscribers = new CopyOnWriteArrayList<>();
    private final int maxHitPoints;
    private int hitPoints;
    private double invulnerableSeconds;
    private DeathCause deathCause = DeathCause.OTHER;

    /**
     * @param owner        the enemy, passed to death subscribers
     * @param maxHitPoints full health (at least 1)
     * @param listener     frontend hooks, may be null
     */
    public EnemyVitals(ICanDie owner, int maxHitPoints, Listener listener) {
        if (owner == null) {
            throw new IllegalArgumentException("owner must not be null");
        }
        this.owner = owner;
        this.listener = listener != null ? listener : NONE;
        this.maxHitPoints = Math.max(1, maxHitPoints);
        this.hitPoints = this.maxHitPoints;
    }

    public synchronized int getHitPoints() {
        return hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public synchronized DeathCause deathCause() {
        return deathCause;
    }

    public synchronized boolean isInvulnerable() {
        return invulnerableSeconds > 0;
    }

    /** Sets HP directly (at least 0); dropping to 0 from alive is a death of unknown cause. */
    public void setHitPoints(int hp) {
        boolean died;
        int now;
        synchronized (this) {
            boolean wasAlive = hitPoints > 0;
            hitPoints = Math.max(0, hp);
            died = wasAlive && hitPoints == 0;
            if (died) {
                deathCause = DeathCause.OTHER;
            }
            now = hitPoints;
        }
        changed(now, died, DeathCause.OTHER);
    }

    /** Applies damage dealt by {@code cause}; ignored while dead or invulnerable. */
    public void subtractHitPoints(int hp, DeathCause cause) {
        DeathCause effective = cause != null ? cause : DeathCause.OTHER;
        boolean died;
        int now;
        synchronized (this) {
            if (hitPoints <= 0 || invulnerableSeconds > 0) {
                return;
            }
            hitPoints = Math.max(0, hitPoints - Math.max(0, hp));
            died = hitPoints == 0;
            if (died) {
                deathCause = effective;
            }
            now = hitPoints;
        }
        changed(now, died, effective);
    }

    /** Heals a living enemy, up to its maximum. */
    public void addHitPoints(int hp) {
        int now;
        synchronized (this) {
            if (hitPoints <= 0) {
                return;
            }
            hitPoints = Math.min(maxHitPoints, hitPoints + Math.max(0, hp));
            now = hitPoints;
        }
        listener.onHitPointsChanged(now, maxHitPoints);
    }

    /** Brings the enemy back at full health, invulnerable for {@code invulnerableSeconds}. */
    public void respawn(double invulnerableSeconds) {
        synchronized (this) {
            hitPoints = maxHitPoints;
            deathCause = DeathCause.OTHER;
            this.invulnerableSeconds = Math.max(0, invulnerableSeconds);
        }
        listener.onHitPointsChanged(maxHitPoints, maxHitPoints);
    }

    /** Counts the invulnerability window down (frame-driven frontends). */
    public synchronized void tick(double dt) {
        invulnerableSeconds = Math.max(0, invulnerableSeconds - Math.max(0, dt));
    }

    /** Ends the invulnerability window now (timer-driven frontends). */
    public synchronized void clearInvulnerability() {
        invulnerableSeconds = 0;
    }

    public void addDeathSubscriber(IDeathSubscriber subscriber) {
        if (subscriber != null) {
            subscribers.add(subscriber);
        }
    }

    private void changed(int now, boolean died, DeathCause cause) {
        listener.onHitPointsChanged(now, maxHitPoints);
        if (died) {
            listener.onDied(cause);
            for (IDeathSubscriber subscriber : subscribers) {
                subscriber.AddDeathNotification(owner);
            }
        }
    }
}
