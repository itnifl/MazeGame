package main.game.maze.game.ufo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.shop.ShopCatalog;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopItem;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.flame.FlameTarget;

/**
 * The retaliation of a level, shared by both frontends: when the player uses a weapon of the trigger tier of the shop,
 * UFOs cross the map back and forth for a few seconds and beam the ground close to the player (never straight at them:
 * the spot is marked first, then the beam comes and stays a while), and a squad of red soldiers is dropped to hunt them.
 *
 * <p>A beam the player is in (bumps into) takes a fraction of their maximum health, once per beam; a shield blocks it. The
 * red soldiers are an {@link InfantrySquad} that hunts the player instead of the enemies; the player's own weapons can kill
 * them ({@link #soldierTargets()}). World space (y down). Not thread-safe: use it from the game loop.
 */
public final class UfoRaid {

    /** The player, as the raid needs to know them (world space). */
    public interface Target {
        double x();

        double y();

        int maxHitPoints();

        /** Whether a shield is up: it blocks every beam and round. */
        boolean shielded();

        /** Takes {@code damage} hit points off the player. */
        void hurt(int damage);
    }

    /** How far from the edge of the map a UFO turns round, and a beam may land. */
    static final double MARGIN = 40.0;
    /** The player is "in" a beam when closer than its radius plus this (the size of the player). */
    static final double PLAYER_REACH = 14.0;

    /** A beam: from the UFO (ux, uy) to the spot (x, y); {@code charging} while the spot is only marked. */
    public record BeamView(double ufoX, double ufoY, double x, double y, double radius, boolean charging,
                           double progress) {
    }

    /** A UFO as it is drawn, with its beam when it has one. */
    public record UfoView(double x, double y, boolean movingRight, BeamView beam) {
    }

    /** What happened in a frame: beams that started, hits and blocked hits on the player, the soldiers' rounds. */
    public record Frame(int beamsFired, int hits, int blocked, List<InfantrySquad.Round> rounds,
                        List<InfantrySquad.SoldierView> fallen) {
        public Frame {
            rounds = List.copyOf(rounds);
            fallen = List.copyOf(fallen);
        }

        /** A frame in which nothing happened. */
        public static final Frame NOTHING_HAPPENED = new Frame(0, 0, 0, List.of(), List.of());
    }

    private enum Phase { FLYING, CHARGING, BEAMING }

    private static final class Ufo {
        double x;
        double y;
        double direction;
        Phase phase = Phase.FLYING;
        double nextBeamIn;
        double phaseLeft;
        double aimX;
        double aimY;
        boolean hit;
    }

    private final Random random;
    private final InfantrySquad redSquad;
    private final List<Ufo> ufos = new ArrayList<>();
    private UfoRaidSpec spec;
    private double timeLeft;
    private double width;
    private double height;

    public UfoRaid(Random random) {
        this.random = random;
        this.redSquad = InfantrySquad.hostile(random);
    }

    /** Whether using {@code weapon} brings the UFOs of a level with this raid: the weapon is one of the trigger tier. */
    public static boolean triggeredBy(UfoRaidSpec spec, Weapon weapon) {
        if (spec == null || weapon == null) {
            return false;
        }
        ShopCatalog shop = ShopCatalogLoader.loadDefault();
        for (ShopItem item : shop.items()) {
            if (item.tier().name().equals(spec.triggerTier()) && item.kind().weapon() == weapon) {
                return true;
            }
        }
        return false;
    }

    /**
     * The UFOs arrive and the red soldiers are dropped (a raid that is already on starts over).
     *
     * @param openGround walkable points of the maze, where the soldiers can land
     * @return false when the soldiers found nowhere to land (the UFOs come anyway)
     */
    public boolean trigger(UfoRaidSpec raid, double boardWidth, double boardHeight, Target player,
                           List<Point2D> openGround) {
        reset();
        this.spec = raid;
        this.width = boardWidth;
        this.height = boardHeight;
        this.timeLeft = raid.seconds();
        for (int i = 0; i < raid.ufos(); i++) {
            Ufo ufo = new Ufo();
            ufo.direction = i % 2 == 0 ? 1 : -1;                       // one comes from each side
            ufo.x = ufo.direction > 0 ? MARGIN : boardWidth - MARGIN;
            ufo.y = lane();
            ufo.nextBeamIn = 0.4 + random.nextDouble() * raid.beamEverySeconds();
            ufos.add(ufo);
        }
        return dropSoldiers(raid, player, openGround);
    }

    private boolean dropSoldiers(UfoRaidSpec raid, Target player, List<Point2D> openGround) {
        if (raid.soldiers() <= 0 || openGround == null || openGround.isEmpty()) {
            return false;
        }
        // airdropped some way off the player, not on top of them
        List<Point2D> far = new ArrayList<>();
        Point2D here = new Point2D(player.x(), player.y());
        for (Point2D point : openGround) {
            double distance = point.distance(here);
            if (distance >= 220 && distance <= 520) {
                far.add(point);
            }
        }
        List<Point2D> pool = far.isEmpty() ? openGround : far;
        Point2D anchor = pool.get(random.nextInt(pool.size()));
        return redSquad.deploy(openGround,
                new InfantrySquad.Orders(raid.soldiers(), raid.soldierSeconds(), raid.soldierDamage(), raid.soldierSight(),
                        raid.soldierHealth()), anchor);
    }

    private double lane() {
        return MARGIN + random.nextDouble() * Math.max(1.0, height - 2 * MARGIN);
    }

    /** Whether the UFOs are over the maze now. */
    public boolean ufosActive() {
        return !ufos.isEmpty();
    }

    /** Whether the UFOs or the red soldiers are in the maze now. */
    public boolean active() {
        return ufosActive() || redSquad.active();
    }

    /** Seconds until the UFOs leave. */
    public double timeLeft() {
        return ufosActive() ? timeLeft : 0.0;
    }

    public UfoRaidSpec spec() {
        return spec;
    }

    /** One frame: the UFOs fly and beam, the player in a beam is hurt (a shield blocks it), the red soldiers hunt. */
    public Frame tick(double dt, Target player, InfantrySquad.Terrain terrain) {
        if (spec == null || dt <= 0) {
            return Frame.NOTHING_HAPPENED;
        }
        int fired = 0;
        int hits = 0;
        int blocked = 0;
        if (ufosActive()) {
            timeLeft -= dt;
            if (timeLeft <= 0) {
                ufos.clear();      // the UFOs leave (their beams with them)
            }
            for (Ufo ufo : ufos) {
                int[] result = tickUfo(ufo, dt, player);
                fired += result[0];
                hits += result[1];
                blocked += result[2];
            }
        }
        InfantrySquad.Report squadFrame = InfantrySquad.Report.NOTHING;
        if (redSquad.active() && terrain != null) {
            squadFrame = redSquad.tick(dt, List.of(playerTarget(player)), terrain);
        }
        return new Frame(fired, hits, blocked, squadFrame.rounds(), squadFrame.fallen());
    }

    /** {first: beams fired, second: hits, third: blocked}. */
    private int[] tickUfo(Ufo ufo, double dt, Target player) {
        int[] result = new int[3];
        // flying: back and forth, a new lane at every turn
        ufo.x += ufo.direction * spec.ufoSpeed() * dt;
        if (ufo.x > width - MARGIN) {
            ufo.x = width - MARGIN;
            ufo.direction = -1;
            ufo.y = lane();
        } else if (ufo.x < MARGIN) {
            ufo.x = MARGIN;
            ufo.direction = 1;
            ufo.y = lane();
        }
        switch (ufo.phase) {
            case FLYING -> {
                ufo.nextBeamIn -= dt;
                if (ufo.nextBeamIn <= 0) {
                    aim(ufo, player);
                    ufo.phase = Phase.CHARGING;
                    ufo.phaseLeft = spec.chargeSeconds();
                    ufo.hit = false;
                    result[0] = 1;
                }
            }
            case CHARGING -> {
                ufo.phaseLeft -= dt;
                if (ufo.phaseLeft <= 0) {
                    ufo.phase = Phase.BEAMING;
                    ufo.phaseLeft = spec.beamSeconds();
                }
            }
            case BEAMING -> {
                ufo.phaseLeft -= dt;
                if (!ufo.hit && Math.hypot(player.x() - ufo.aimX, player.y() - ufo.aimY) <= spec.beamRadius() + PLAYER_REACH) {
                    ufo.hit = true;       // one hit per beam
                    if (player.shielded()) {
                        result[2] = 1;
                    } else {
                        player.hurt(Math.max(1, (int) Math.round(player.maxHitPoints() * spec.damageFraction())));
                        result[1] = 1;
                    }
                }
                if (ufo.phaseLeft <= 0) {
                    ufo.phase = Phase.FLYING;
                    ufo.nextBeamIn = spec.beamEverySeconds() * (0.6 + random.nextDouble() * 0.8);
                }
            }
        }
        return result;
    }

    /** The spot to beam: close to the player, never straight at them, inside the map. */
    private void aim(Ufo ufo, Target player) {
        double distance = spec.aimMinDistance() + random.nextDouble() * (spec.aimMaxDistance() - spec.aimMinDistance());
        double angle = random.nextDouble() * Math.PI * 2;
        double x = player.x() + Math.cos(angle) * distance;
        double y = player.y() + Math.sin(angle) * distance;
        ufo.aimX = Math.max(spec.beamRadius(), Math.min(width - spec.beamRadius(), x));
        ufo.aimY = Math.max(spec.beamRadius(), Math.min(height - spec.beamRadius(), y));
    }

    /** The UFOs, for drawing. */
    public List<UfoView> ufos() {
        List<UfoView> views = new ArrayList<>(ufos.size());
        for (Ufo ufo : ufos) {
            BeamView beam = null;
            if (ufo.phase == Phase.CHARGING) {
                double total = Math.max(1e-6, spec.chargeSeconds());
                beam = new BeamView(ufo.x, ufo.y, ufo.aimX, ufo.aimY, spec.beamRadius(), true, 1 - ufo.phaseLeft / total);
            } else if (ufo.phase == Phase.BEAMING) {
                beam = new BeamView(ufo.x, ufo.y, ufo.aimX, ufo.aimY, spec.beamRadius(), false,
                        1 - ufo.phaseLeft / spec.beamSeconds());
            }
            views.add(new UfoView(ufo.x, ufo.y, ufo.direction > 0, beam));
        }
        return views;
    }

    /** The red soldiers, for drawing. */
    public List<InfantrySquad.SoldierView> soldiers() {
        return redSquad.soldiers();
    }

    /** The red soldiers as targets for the player's weapons (flames, shots, blasts); they fall when their health is gone. */
    public List<FlameTarget> soldierTargets() {
        return redSquad.asTargets();
    }

    /** Whether any red soldier is in the maze. */
    public boolean soldiersActive() {
        return redSquad.active();
    }

    /** New level or teardown: no UFOs and no red soldiers. */
    public void reset() {
        ufos.clear();
        redSquad.dismiss();
        timeLeft = 0;
        spec = null;
    }

    private static FlameTarget playerTarget(Target player) {
        return new FlameTarget() {
            @Override public double centerX() { return player.x(); }
            @Override public double centerY() { return player.y(); }
            @Override public int hitPoints() { return Math.max(1, player.maxHitPoints()); }
            @Override public void applyDamage(int amount) {
                if (!player.shielded()) {
                    player.hurt(amount);
                }
            }
            @Override public boolean isPassThrough() { return false; }
        };
    }
}
