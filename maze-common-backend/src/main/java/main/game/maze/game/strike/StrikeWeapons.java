package main.game.maze.game.strike;

import java.util.List;
import java.util.Random;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.flame.FlameTarget;

/**
 * The shop weapons that do not fire at once, the same in both frontends: thrown grenades, the fat bomb, the airstrike and
 * the infantry strike. {@link #use} plans what the weapon does; {@link #tick} says, frame by frame, which blasts go off
 * (the frontend sets them off with its own flames, sound and shake) and which rounds the soldiers fire. World space
 * (y down). Not thread-safe: use it from the game loop.
 *
 * <p>The bunker buster (a piercing shot with a blast at the end, {@link StrikePlanner#bunkerBusterImpact}) and the extra
 * life (it heals the player) need the frontend's own shot and player, so they are not handled here.
 */
public final class StrikeWeapons {

    /** Where the weapon is used from, and the maze around it. */
    public record Launch(double x, double y, int dirX, int dirY, List<Vector2D> walls, double boardWidth,
                         double boardHeight, List<Point2D> openGround, LevelBoosts boosts) {
        public Launch {
            walls = walls == null ? List.of() : walls;
            openGround = openGround == null ? List.of() : openGround;
            boosts = boosts == null ? LevelBoosts.NONE : boosts;
        }
    }

    /** What using the weapon did: its spec (for its sound) and the status line to show. */
    public record Use(PickupSpec spec, String message) {
    }

    /** One frame: the blasts that go off now, the rounds the soldiers fired and the soldiers who fell. */
    public record Frame(List<ScheduledBlast> blasts, List<InfantrySquad.Round> rounds,
                        List<InfantrySquad.SoldierView> fallen) {
        public Frame {
            blasts = List.copyOf(blasts);
            rounds = List.copyOf(rounds);
            fallen = List.copyOf(fallen);
        }
    }

    private final PickupCatalog catalog;
    private final Random random;
    private final BlastSchedule schedule = new BlastSchedule();
    private final InfantrySquad squad;

    public StrikeWeapons(PickupCatalog catalog, Random random) {
        this.catalog = catalog;
        this.random = random;
        this.squad = new InfantrySquad(random);
    }

    /** Whether {@link #use} handles this weapon. */
    public static boolean handles(Weapon weapon) {
        return weapon != null && (weapon.isThrown() || weapon == Weapon.FAT_BOMB || weapon == Weapon.AIRSTRIKE
                || weapon == Weapon.INFANTRY_STRIKE);
    }

    /**
     * Uses one of the weapons {@link #handles} covers (the use has already been spent from the arsenal).
     *
     * @throws IllegalArgumentException for any other weapon
     */
    public Use use(Weapon weapon, Launch launch) {
        PickupSpec spec = catalog.spec(weapon.pickup());
        if (weapon.isThrown()) {
            schedule.add(StrikePlanner.thrownGrenade(spec, launch.x(), launch.y(), launch.dirX(), launch.dirY(), launch.walls()));
            return new Use(spec, spec.displayName() + " thrown");
        }
        return switch (weapon) {
            case FAT_BOMB -> {
                schedule.add(StrikePlanner.fatBomb(spec, launch.x(), launch.y(), launch.boosts()));
                yield new Use(spec, spec.displayName() + " planted - run!");
            }
            case AIRSTRIKE -> {
                schedule.addAll(StrikePlanner.airstrike(spec, launch.boardWidth(), launch.boardHeight(), random));
                yield new Use(spec, "Airstrike incoming on the centre of the maze!");
            }
            case INFANTRY_STRIKE -> {
                boolean arrived = squad.deploy(launch.openGround(),
                        new InfantrySquad.Orders(spec.units(), spec.durationSeconds(), spec.damage(), spec.range(),
                                spec.unitHealth()));
                yield new Use(spec, arrived ? spec.units() + " soldiers have arrived and are hunting the enemy"
                        : "The soldiers found nowhere to land");
            }
            default -> throw new IllegalArgumentException("not a strike weapon: " + weapon);
        };
    }

    /** Puts a blast on its way that another weapon made (the bunker buster's impact). */
    public void schedule(ScheduledBlast blast) {
        schedule.add(blast);
    }

    /**
     * Plants the player's bomb at (x, y) (world space) with the player's bomb damage; it lies there as a bomb with a
     * burning fuse and {@link #tick} sets it off, like the grenades and the fat bomb.
     */
    public ScheduledBlast plantBomb(double x, double y, int damage, LevelBoosts boosts) {
        ScheduledBlast bomb = StrikePlanner.plantedBomb(catalog.spec(Weapon.BOMB.pickup()), x, y, damage, boosts);
        schedule.add(bomb);
        return bomb;
    }

    /** One frame of the fuses and of the soldiers. */
    public Frame tick(double dt, List<FlameTarget> enemies, InfantrySquad.Terrain terrain) {
        List<ScheduledBlast> due = schedule.tick(dt);
        InfantrySquad.Report squadFrame = terrain == null ? InfantrySquad.Report.NOTHING : squad.tick(dt, enemies, terrain);
        return new Frame(due, squadFrame.rounds(), squadFrame.fallen());
    }

    /** The grenades and bombs lying on the floor and the bombs on their way, for drawing. */
    public List<ScheduledBlast> pendingBlasts() {
        return schedule.pending();
    }

    public List<InfantrySquad.SoldierView> soldiers() {
        return squad.soldiers();
    }

    /**
     * Where the living soldiers are (world space), safe to read from the enemy AI thread: enemies within
     * {@link #lureRadius()} of one go for it ({@link DecoyTargeting}).
     */
    public List<Point2D> soldierPositions() {
        return squad.livingPositions();
    }

    /**
     * The soldiers for the ranged enemies to shoot at (world space). Hitting one is game-loop work: a frontend whose
     * enemies shoot from another thread passes the hits on to the game loop ({@link SoldierTargets#of}).
     */
    public SoldierTargets soldierTargets() {
        return SoldierTargets.of(squad::livingPositions, (soldier, damage) -> squad.hit(soldier.getX(), soldier.getY(), damage));
    }

    /** Enemies this close to a soldier go for it instead of the player (from the items model). */
    public double lureRadius() {
        return catalog.spec(Weapon.INFANTRY_STRIKE.pickup()).lureRadius();
    }

    /** Whether the infantry squad is in the maze now. */
    public boolean squadActive() {
        return squad.active();
    }

    /** New level or teardown: no blasts on their way and no soldiers. */
    public void reset() {
        schedule.clear();
        squad.dismiss();
    }
}
