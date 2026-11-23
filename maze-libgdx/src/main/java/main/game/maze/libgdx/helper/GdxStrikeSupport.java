package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.PickupEffects;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.ShotEffects;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.ScheduledBlast;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.game.strike.StrikePlanner;
import main.game.maze.game.strike.StrikeVisuals;
import main.game.maze.game.strike.StrikeWeapons;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.BombFuseGlow;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.SpriteVisual;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.mazeworld.flame.LaserBeamEngine;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX side of the shop weapons (#164). All rules come from the shared backend ({@link StrikeWeapons},
 * {@link StrikePlanner}, {@link InfantrySquad}); like {@link GdxWeaponSupport} this class only converts between libGDX
 * screen space (y up) and the shared world space (y down), and starts the shared visuals and sounds. The counterpart of
 * the JavaFX {@code FxWeaponController} strike code.
 */
public final class GdxStrikeSupport {


    /** A blast of a shop weapon, in screen space, for the game screen to set off: flames, or a nuclear blast. */
    public record Blast(float x, float y, int damage, float radius, boolean hurtsPlayer, String label, boolean nuclear) {
    }

    private GdxStrikeSupport() {
    }

    /**
     * Uses one of the weapons {@link StrikeWeapons#handles} covers (the use is already spent).
     *
     * @param openGround walkable points of the level in world space (where soldiers can land)
     * @return the status line to show
     */
    public static String use(GameWorldModel model, MazeArena maze, PlayerState player, Weapon weapon,
                             List<Point2D> openGround) {
        float height = maze.heightPx();
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        StrikeWeapons.Use use = model.strikes().use(weapon, new StrikeWeapons.Launch(player.x(), height - player.y(),
                player.facingX(), -player.facingY(),   // screen y-up -> world y-down
                world != null ? world.getMazeVectors() : GdxWeaponSupport.worldWalls(maze),
                maze.widthPx(), height, openGround, model.boosts()));
        playUseSound(use.spec());
        return use.message();
    }

    /**
     * The bunker buster: a rocket that goes through the walls it is strong enough for (the shared piercing shot of
     * {@link GdxWeaponSupport#fireLaser}), and its blast where it stopped.
     */
    public static Blast fireBunkerBuster(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze,
                                         PlayerState player, PickupSpec buster, String attackerId) {
        LaserBeamEngine.Shot shot = GdxWeaponSupport.fireLaser(model, enemies, maze, player, buster, attackerId);
        float height = maze.heightPx();
        ScheduledBlast impact = StrikePlanner.bunkerBusterImpact(buster, player.x(), height - player.y(),
                player.facingX(), -player.facingY(), shot.reach());
        return toScreen(impact, height);
    }

    /**
     * The extra life of the shop: fills the health bar.
     *
     * @return false when health is already full (the extra life is then not used)
     */
    public static boolean canRefillLife(PlayerCombatStateService combat) {
        return PickupEffects.lifeRefillAmount(combat.currentHitPointsAsInt(), combat.maxHitPoints()) > 0;
    }

    public static void refillLife(GameWorldModel model, PlayerCombatStateService combat) {
        combat.heal(PickupEffects.lifeRefillAmount(combat.currentHitPointsAsInt(), combat.maxHitPoints()));
        model.healFeedback().trigger();
        playUseSound(model.pickupField().catalog().spec(PickupKind.LIFE_REFILL));
    }

    /**
     * One frame of the strikes: the fuses burn down and the soldiers fire (their rounds are shown and credited to
     * {@code attackerId}).
     *
     * @return the blasts that go off now, in screen space
     */
    public static List<Blast> tick(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze, float dt,
                                   String attackerId) {
        if (maze == null) {
            return List.of();
        }
        float height = maze.heightPx();
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        InfantrySquad.Terrain terrain = world != null && world.getNavigationGraph() != null
                ? InfantrySquad.Terrain.of(world) : null;
        List<FlameTarget> targets = model.strikes().squadActive() ? livingTargets(enemies, height, attackerId) : List.of();
        StrikeWeapons.Frame frame = model.strikes().tick(dt, targets, terrain);
        if (!frame.fallen().isEmpty()) {
            AudioEngine.get().play(StrikeVisuals.SOLDIER_DOWN_SOUND);
        }
        for (InfantrySquad.Round round : frame.rounds()) {
            model.effects().add(new GameWorldModel.ActiveEffect(ShotEffects.enemyRound(
                    round.fromX(), height - round.fromY(), round.toX(), height - round.toY())));
            AudioEngine.get().playRateLimited(StrikeVisuals.SOLDIER_FIRE_SOUND, StrikeVisuals.SOLDIER_FIRE_SOUND_KEY,
                    StrikeVisuals.SOLDIER_FIRE_COOLDOWN_MILLIS);
        }
        List<Blast> due = new ArrayList<>(frame.blasts().size());
        for (ScheduledBlast blast : frame.blasts()) {
            due.add(toScreen(blast, height));
        }
        return due;
    }

    /** The living enemies as targets for the soldiers, in world space; each round is a shot credited to the attacker. */
    static List<FlameTarget> livingTargets(List<GdxEnemyRuntime> enemies, float height, String attackerId) {
        return livingTargets(enemies, height, attackerId, DeathCause.SHOT);
    }

    /** As above, with the cause of death the damage is credited as (the nuclear blast burns). */
    public static List<FlameTarget> livingTargets(List<GdxEnemyRuntime> enemies, float height, String attackerId,
                                                  DeathCause cause) {
        List<FlameTarget> targets = new ArrayList<>();
        for (GdxEnemyRuntime enemy : enemies) {
            if (!enemy.isAlive() || enemy.isInvulnerable()) {
                continue;
            }
            targets.add(new FlameTarget() {
                @Override public double centerX() { return enemy.x(); }
                @Override public double centerY() { return height - enemy.y(); }
                @Override public int hitPoints() { return enemy.getHitPoints(); }
                @Override public void applyDamage(int amount) {
                    enemy.subtractHitPoints(amount, cause, attackerId);
                }
                @Override public boolean isPassThrough() { return false; }
            });
        }
        return targets;
    }

    /** The soldiers of the infantry strike, as sprites in screen space (mirrored when they face left). */
    public static List<SpriteVisual> soldierSprites(GameWorldModel model, MazeArena maze, float playerSize) {
        if (maze == null || !model.strikes().squadActive()) {
            return List.of();
        }
        float height = maze.heightPx();
        float size = (float) (playerSize * StrikeVisuals.SOLDIER_SIZE_FACTOR);
        List<SpriteVisual> sprites = new ArrayList<>();
        for (InfantrySquad.SoldierView soldier : model.strikes().soldiers()) {
            sprites.add(new SpriteVisual((float) soldier.x(), height - (float) soldier.y(), size, 0f,
                    StrikeVisuals.soldierFlipX(soldier), StrikeVisuals.soldierSprite(soldier)));
        }
        return sprites;
    }

    /** A flickering glow on each grenade or fat bomb lying on the floor (added to the model's fuse glows). */
    public static void addFuseGlows(GameWorldModel model, MazeArena maze, double clockSeconds) {
        if (maze == null) {
            return;
        }
        float height = maze.heightPx();
        var items = model.pickupField().catalog();
        for (ScheduledBlast blast : model.strikes().pendingBlasts()) {
            if (!blast.showsFuse()) {
                continue;
            }
            float[] colour = StrikeVisuals.fuseColour(blast);
            boolean drawnAsSprite = StrikeVisuals.lyingSprite(blast, items) != null;
            // a grenade drawn as itself gets a halo round it; a bomb without a sprite the glow with its hot core
            double radius = drawnAsSprite
                    ? Math.max(StrikeVisuals.fuseRadius(blast), StrikeVisuals.lyingSize(blast, items) * 0.65)
                    : StrikeVisuals.fuseRadius(blast);
            model.bombFuseGlows().add(new BombFuseGlow((float) blast.x(), height - (float) blast.y(), (float) radius,
                    (float) StrikeVisuals.fuseAlpha(blast, clockSeconds), colour[0], colour[1], colour[2], drawnAsSprite));
        }
    }

    /** Each grenade or bomb lying on the floor, drawn as the weapon itself (the nuclear grenade larger), in screen space. */
    public static List<SpriteVisual> lyingSprites(GameWorldModel model, MazeArena maze) {
        if (maze == null) {
            return List.of();
        }
        float height = maze.heightPx();
        var items = model.pickupField().catalog();
        List<SpriteVisual> sprites = new ArrayList<>();
        for (ScheduledBlast blast : model.strikes().pendingBlasts()) {
            String sprite = StrikeVisuals.lyingSprite(blast, items);
            if (sprite != null) {
                sprites.add(new SpriteVisual((float) blast.x(), height - (float) blast.y(),
                        (float) StrikeVisuals.lyingSize(blast, items), 0f, false, sprite));
            }
        }
        return sprites;
    }

    /**
     * Where the living soldiers are, in screen space (y up, centres, like the enemies' positions), for the enemies near
     * one to go for it ({@code DecoyTargeting}).
     */
    public static List<Point2D> soldierPositionsOnScreen(GameWorldModel model, MazeArena maze) {
        return soldierTargets(model, maze).positions();
    }

    /**
     * The soldiers for the ranged enemies to shoot at (sergeants, towers, pumpkin bombers), in screen space (y up,
     * centres). Hitting one hurts it at once: libGDX runs the enemies and the squad on the render thread.
     */
    public static SoldierTargets soldierTargets(GameWorldModel model, MazeArena maze) {
        if (maze == null) {
            return SoldierTargets.NONE;
        }
        float height = maze.heightPx();
        java.util.function.UnaryOperator<Point2D> flip = p -> new Point2D(p.getX(), height - p.getY());
        return model.strikes().soldierTargets().mapped(flip, flip);
    }

    static Blast toScreen(ScheduledBlast blast, float height) {
        return new Blast((float) blast.x(), height - (float) blast.y(), blast.damage(), (float) blast.radius(),
                blast.hurtsPlayer(), blast.label(), blast.nuclear());
    }

    private static void playUseSound(PickupSpec spec) {
        if (spec.useSound() != null) {
            AudioEngine.get().play(spec.useSound());
        }
    }
}
