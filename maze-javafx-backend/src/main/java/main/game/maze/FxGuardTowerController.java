package main.game.maze;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.graphics.fire.RocketEffect;
import main.game.maze.game.level.GuardTowerSession;
import main.game.maze.game.level.GuardTowerSpec;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * JavaFX adapter for the guard towers: the counterpart of libGDX {@code GdxGuardTowerSupport}. All decisions (the
 * rpg every 3 seconds, the gatling in between, the walls a tower may shoot over, what hits the player, hits and
 * destruction) come from the shared {@link GuardTowerSession}; this class feeds it the towers and the player's
 * place and hands what it decides to the {@link Host}, so it can be tested without the FXML game screen. Coordinates
 * are world space (y-down), as the board is. Use on the JavaFX Application Thread.
 */
public final class FxGuardTowerController {

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        /** Walls between two points (the live walls of the maze). */
        int wallsBetween(double fromX, double fromY, double toX, double toY);

        /** An explosion at (x, y): flames, walls and enemies take {@code damage} within {@code range}. */
        void blast(double x, double y, int damage, double range, String sound, boolean shake);

        /** The player takes damage (the shield still blocks it). */
        void hurtPlayer(int damage);

        /** A round of the gatling gun on its way. */
        void tracer(double fromX, double fromY, double toX, double toY);

        /** Draws the rockets in the air now (an empty list clears them). */
        void drawRockets(List<RocketEffect> rockets, double clockSeconds);

        /** Plays a sound, at most once per {@code coolDownMillis} for the same key. */
        void play(String path, String key, long coolDownMillis);

        /** The soldiers of the infantry strike (centres), shot before the player; none by default. */
        default SoldierTargets soldiers() {
            return SoldierTargets.NONE;
        }
    }

    /** A tower as the controller needs it: where it stands and how healthy it is (0 when destroyed). */
    public interface Body {
        double centerX();

        double centerY();

        int health();
    }

    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;
    private static final long GATLING_SOUND_COOLDOWN_MS = 550L;
    private static final long HIT_SOUND_COOLDOWN_MS = 250L;

    private final Host host;
    private final Map<String, Body> towers = new LinkedHashMap<>();
    private GuardTowerSession session;
    private long lastNanos = -1;

    public FxGuardTowerController(Host host) {
        this.host = host;
    }

    /** New level: no towers yet (they register as they are placed); a level without towers has none. */
    public void startGame(GuardTowerSpec spec) {
        towers.clear();
        session = spec == null ? null : new GuardTowerSession(spec);
        lastNanos = -1;
    }

    /** A tower has been placed. */
    public void register(String id, Body tower) {
        towers.put(id, tower);
    }

    public int towerCount() {
        return towers.size();
    }

    /** Per frame: the towers look for a soldier or the player, shoot, the rockets fly and land. */
    public void tick(long nowNanos) {
        double dt = lastNanos < 0 ? 0 : (nowNanos - lastNanos) / 1e9;
        lastNanos = nowNanos;
        PlayerCharacter player = host.player();
        if (session == null || player == null || towers.isEmpty()) {
            return;
        }
        double px = player.getCharacterPosition().getX() + HALF_PLAYER;
        double py = player.getCharacterPosition().getY() + HALF_PLAYER;
        List<GuardTowerSession.Tower> views = new ArrayList<>();
        for (Map.Entry<String, Body> entry : towers.entrySet()) {
            Body tower = entry.getValue();
            views.add(new GuardTowerSession.Tower(entry.getKey(), tower.centerX(), tower.centerY(), tower.health()));
        }
        SoldierTargets soldiers = host.soldiers();
        GuardTowerSession.Result result = session.tick(dt, views, px, py, soldiers.positions(), host::wallsBetween);
        GuardTowerSpec spec = session.spec();
        apply(result, spec);
        result.hitSoldiers(soldiers);
        List<RocketEffect> rockets = new ArrayList<>();
        for (GuardTowerSession.Rocket rocket : session.rockets()) {
            rockets.add(new RocketEffect(rocket.x(), rocket.y(), rocket.angleRadians()));
        }
        host.drawRockets(rockets, nowNanos / 1e9);
    }

    private void apply(GuardTowerSession.Result result, GuardTowerSpec spec) {
        for (GuardTowerSession.Round round : result.rounds()) {
            host.tracer(round.fromX(), round.fromY(), round.toX(), round.toY());
            play(spec.gatlingSound(), "tower.gatling." + round.towerId(), GATLING_SOUND_COOLDOWN_MS);
        }
        for (GuardTowerSession.Rocket launch : result.launches()) {
            play(spec.rpgLaunchSound(), "tower.rpg." + launch.id(), 0L);
        }
        for (GuardTowerSession.Impact impact : result.impacts()) {
            host.blast(impact.x(), impact.y(), spec.rpgWallDamage(), spec.rpgBlastRadius(), spec.rpgExplosionSound(), false);
        }
        for (GuardTowerSession.TowerEvent event : result.events()) {
            if (event.event() == GuardTowerSession.Event.HIT) {
                play(spec.hitSound(), "tower.hit." + event.towerId(), HIT_SOUND_COOLDOWN_MS);
            } else {
                play(spec.destroyedSound(), "tower.destroyed." + event.towerId(), 0L);
                host.blast(event.x(), event.y(), spec.deathBlastDamage(), spec.deathBlastRadius(), null, true);
            }
        }
        if (result.playerDamage() > 0) {
            host.hurtPlayer(result.playerDamage());
        }
    }

    private void play(String path, String key, long coolDownMillis) {
        if (path != null) {
            host.play(path, key, coolDownMillis);
        }
    }

    /** Level teardown. */
    public void dispose() {
        towers.clear();
        session = null;
    }
}
