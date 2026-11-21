package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.common.graphics.fire.PuffEffect;
import main.game.maze.common.graphics.fire.RocketEffect;
import main.game.maze.game.items.ShotEffects;
import main.game.maze.game.level.GuardTowerSession;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;

/**
 * libGDX side of the guard towers: the counterpart of JavaFX {@code FxGuardTowerController}. All decisions (the rpg
 * every 3 seconds, the gatling in between, the walls a tower may shoot over, what hits the player, hits and
 * destruction) come from the shared {@link GuardTowerSession}; this class feeds it the tower enemies and the player's
 * place and hands what it decides to the {@link Host}. Screen space is y-up throughout (the session does not care
 * which way y points).
 */
public final class GdxGuardTowerSupport {

    /** What the support needs from the game screen. */
    public interface Host {
        /** Walls between two points (screen space, the live walls of the maze). */
        int wallsBetween(float fromX, float fromY, float toX, float toY);

        /** An explosion at (x, y): flames, walls and enemies take {@code damage} within {@code range}. */
        void blast(float x, float y, int damage, float range, String sound, boolean shake);

        /** The player takes damage (the shield still blocks it). */
        void hurtPlayer(int damage);

        /** A short visual effect (a gatling round, a rocket in flight). */
        void showEffect(PuffEffect effect);

        /** Plays a sound, at most once per {@code coolDownMillis} for the same key (0: always). */
        void play(String path, String key, long coolDownMillis);
    }

    private static final long GATLING_SOUND_COOLDOWN_MS = 550L;
    private static final long HIT_SOUND_COOLDOWN_MS = 250L;

    private final Map<GdxEnemyRuntime, String> known = new LinkedHashMap<>();
    private GuardTowerSession session;

    /** New level: forget the towers of the last one. */
    public void reset(GuardTowerSpec spec) {
        known.clear();
        session = spec == null ? null : new GuardTowerSession(spec);
    }

    /**
     * Advances the towers one frame.
     *
     * @param enemies the enemies in play; a tower that has left the list (killed) is still reported, once, as destroyed
     */
    public void tick(Host host, List<GdxEnemyRuntime> enemies, float playerX, float playerY, float dt) {
        if (session == null) {
            return;
        }
        for (GdxEnemyRuntime enemy : enemies) {
            if (enemy.isGuardTower() && !known.containsKey(enemy)) {
                known.put(enemy, enemy.originalSpawn().id());
            }
        }
        if (known.isEmpty()) {
            return;
        }
        List<GuardTowerSession.Tower> views = new ArrayList<>();
        for (Map.Entry<GdxEnemyRuntime, String> entry : known.entrySet()) {
            GdxEnemyRuntime tower = entry.getKey();
            boolean standing = tower.isAlive() && enemies.contains(tower);
            views.add(new GuardTowerSession.Tower(entry.getValue(), tower.x(), tower.y(),
                    standing ? Math.max(1, tower.getHitPoints()) : 0));
        }
        GuardTowerSession.Result result = session.tick(dt, views, playerX, playerY,
                (fromX, fromY, toX, toY) -> host.wallsBetween((float) fromX, (float) fromY, (float) toX, (float) toY));
        apply(host, result, session.spec());
        for (GuardTowerSession.Rocket rocket : session.rockets()) {
            host.showEffect(new RocketEffect(rocket.x(), rocket.y(), rocket.angleRadians()));
        }
    }

    private static void apply(Host host, GuardTowerSession.Result result, GuardTowerSpec spec) {
        for (GuardTowerSession.Round round : result.rounds()) {
            host.showEffect(ShotEffects.enemyRound(round.fromX(), round.fromY(), round.toX(), round.toY()));
            play(host, spec.gatlingSound(), "tower.gatling." + round.towerId(), GATLING_SOUND_COOLDOWN_MS);
        }
        for (GuardTowerSession.Rocket launch : result.launches()) {
            play(host, spec.rpgLaunchSound(), "tower.rpg." + launch.id(), 0L);
        }
        for (GuardTowerSession.Impact impact : result.impacts()) {
            host.blast((float) impact.x(), (float) impact.y(), spec.rpgWallDamage(), (float) spec.rpgBlastRadius(),
                    spec.rpgExplosionSound(), false);
        }
        for (GuardTowerSession.TowerEvent event : result.events()) {
            if (event.event() == GuardTowerSession.Event.HIT) {
                play(host, spec.hitSound(), "tower.hit." + event.towerId(), HIT_SOUND_COOLDOWN_MS);
            } else {
                play(host, spec.destroyedSound(), "tower.destroyed." + event.towerId(), 0L);
                host.blast((float) event.x(), (float) event.y(), spec.deathBlastDamage(),
                        (float) spec.deathBlastRadius(), null, true);
            }
        }
        if (result.playerDamage() > 0) {
            host.hurtPlayer(result.playerDamage());
        }
    }

    private static void play(Host host, String path, String key, long coolDownMillis) {
        if (path != null) {
            host.play(path, key, coolDownMillis);
        }
    }
}
