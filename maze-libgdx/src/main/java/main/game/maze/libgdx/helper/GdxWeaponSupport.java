package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.fire.HealFeedback;
import main.game.maze.common.graphics.fire.HealFlashEffect;
import main.game.maze.common.graphics.fire.HitReactionEffect;
import main.game.maze.common.graphics.fire.PickupSparkleEffect;
import main.game.maze.common.graphics.fire.SledgeSwingEffect;
import main.game.maze.common.remains.CrushZone;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.ArsenalHud;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupEffects;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.Weapon;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.ExplosionVisual;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.SpriteVisual;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.mazeworld.flame.LaserBeamEngine;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.service.MortalityService;

/**
 * libGDX side of pickups and the picked-up weapons. All rules come from the shared backend
 * (pickup field, arsenal, shield, laser engine, sledgehammer strike, kill attribution); this class
 * only converts between libGDX screen space (y-up) and the shared world space (y-down) and starts
 * the shared visual effects and sounds.
 */
public final class GdxWeaponSupport {

    private GdxWeaponSupport() {
    }

    /**
     * Spawns items on their timer and picks up what the player touches: applies the item (bombs,
     * shots, swings, shield, health), plays its sound and shows a sparkle.
     *
     * @return the items picked up this frame
     */
    public static List<Pickup> updatePickups(GameWorldModel model, MazeArena maze, PlayerState player,
                                             PlayerCombatStateService combat, List<Point2D> spawnPoints, float dt) {
        if (maze == null || player == null) {
            return List.of();
        }
        float height = maze.heightPx();
        double worldPlayerY = height - player.y();
        model.pickupField().tick(dt, (int) maze.widthPx(), spawnPoints, player.x(), worldPlayerY);
        List<Pickup> taken = model.pickupField().collect(player.x(), worldPlayerY, player.halfSize());
        PickupCatalog catalog = model.pickupField().catalog();
        for (Pickup pickup : taken) {
            PickupSpec spec = catalog.spec(pickup.kind());
            PickupEffects.apply(spec, model.arsenal(), model.shield(), combat::heal, combat.maxHitPoints(), model.lives());
            AudioEngine.get().play(spec.pickupSound());
            if (pickup.kind() == PickupKind.HEART) {
                model.healFeedback().trigger();   // flash + arrow over the head (healFlash, healArrow)
            }
            float[] colour = ArsenalHud.sparkleColour(pickup.kind());
            model.effects().add(new GameWorldModel.ActiveEffect(new PickupSparkleEffect(
                    pickup.x(), height - pickup.y(), colour[0], colour[1], colour[2])));
        }
        return taken;
    }

    /** Advances combat effects and enemy hit reactions; drops finished effects. */
    public static void tickEffects(GameWorldModel model, List<GdxEnemyRuntime> enemies, float dt) {
        for (GameWorldModel.ActiveEffect effect : model.effects()) {
            effect.tick(dt);
        }
        model.effects().removeIf(GameWorldModel.ActiveEffect::finished);
        model.healFeedback().tick(dt);
        for (GdxEnemyRuntime enemy : enemies) {
            enemy.tickReaction(dt);
        }
    }

    /**
     * Fires one laser shot along the player's facing (shared {@link LaserBeamEngine}: stops at the
     * first enemy or wall, cannot harm steel). Damage and wall collapses are credited to
     * {@code attackerId}; the hit enemy reacts like it was shot.
     */
    public static LaserBeamEngine.Shot fireLaser(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze,
                                                 PlayerState player, PickupSpec laser, String attackerId) {
        float height = maze.heightPx();
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        int dirX = player.facingX();
        int dirY = -player.facingY();                      // screen y-up -> world y-down
        GdxEnemyRuntime[] hit = new GdxEnemyRuntime[1];
        int[] dealt = new int[1];
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
                    enemy.subtractHitPoints(amount, DeathCause.SHOT, attackerId);
                    hit[0] = enemy;
                    dealt[0] = amount;
                }
                @Override public boolean isPassThrough() { return false; }
            });
        }
        LaserBeamEngine.Shot[] shot = new LaserBeamEngine.Shot[1];
        GameMazeWorld.attributeDamageTo(attackerId, () -> shot[0] = LaserBeamEngine.fire(targets, world,
                world != null ? world.getMazeVectors() : worldWalls(maze), player.x(), height - player.y(),
                dirX, dirY, laser.damage(), laser.range(), LaserBeamEngine.PLAYER_BEAM_HALF_WIDTH));

        float endX = (float) (player.x() + player.facingX() * shot[0].reach());
        float endY = (float) (player.y() + player.facingY() * shot[0].reach());
        model.effects().add(new GameWorldModel.ActiveEffect(
                main.game.maze.game.items.ShotEffects.forWeapon(laser.kind(), player.x(), player.y(), endX, endY)));
        if (hit[0] != null) {
            HitReactionEffect reaction = new HitReactionEffect(HitReactionEffect.Kind.SHOT, hit[0].x(), hit[0].y(),
                    player.facingX(), player.facingY(),
                    HitReactionEffect.severity(dealt[0], hit[0].getMaxHitPoints()), System.nanoTime());
            hit[0].react(reaction);
            model.effects().add(new GameWorldModel.ActiveEffect(reaction));
        }
        if (laser.useSound() != null) {
            AudioEngine.get().play(laser.useSound());
        }
        return shot[0];
    }

    /**
     * Swings the sledgehammer: every enemy within reach (and not behind a wall) takes a crushing
     * blow credited to {@code attackerId}. Walls are never harmed. Shows the giant swing and a
     * stagger on each enemy hit.
     *
     * @return the enemies struck
     */
    public static List<ICanDie> swingSledgehammer(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze,
                                                  PlayerState player, PickupSpec sledge, String attackerId) {
        List<ICanDie> struck = MortalityService.strikeAround(enemies, GdxWeaponSupport::bodyOf,
                player.x(), player.y(), sledge.range(), sledge.damage(), DeathCause.CRUSHED, attackerId,
                (x, y) -> maze != null && WallCollisionUtil.wallBetween(player.x(), player.y(), x, y, maze.walls()));
        model.effects().add(new GameWorldModel.ActiveEffect(new SledgeSwingEffect(player.x(), player.y(),
                player.facingX(), player.facingY(), sledge.range())));
        for (ICanDie mortal : struck) {
            GdxEnemyRuntime enemy = (GdxEnemyRuntime) mortal;
            HitReactionEffect reaction = new HitReactionEffect(HitReactionEffect.Kind.BLOW, enemy.x(), enemy.y(),
                    enemy.x() - player.x(), enemy.y() - player.y(),
                    HitReactionEffect.severity(sledge.damage(), enemy.getMaxHitPoints()), System.nanoTime());
            enemy.react(reaction);
            model.effects().add(new GameWorldModel.ActiveEffect(reaction));
        }
        if (sledge.useSound() != null) {
            AudioEngine.get().play(sledge.useSound());
        }
        return struck;
    }

    /** The arena walls in world space (y-down), for arenas without a maze world (e.g. the sample maze). */
    static List<Vector2D> worldWalls(MazeArena maze) {
        float height = maze.heightPx();
        List<Vector2D> walls = new ArrayList<>(maze.walls().size());
        for (WallSegment wall : maze.walls()) {
            walls.add(new Vector2D(wall.x1, height - wall.y1, wall.x2, height - wall.y2));
        }
        return walls;
    }

    /** Enemy body on screen: {@code x()/y()} is the sprite centre. */
    static MortalityService.Body bodyOf(GdxEnemyRuntime enemy) {
        float size = enemy.size() * enemy.spriteScale();
        return new MortalityService.Body(enemy.x(), enemy.y(), size * CrushZone.BODY_RADIUS_FACTOR);
    }

    /** Items lying on the floor, gently bobbing so they catch the eye. */
    public static List<SpriteVisual> pickupSprites(GameWorldModel model, MazeArena maze, float clock) {
        if (maze == null) {
            return List.of();
        }
        List<SpriteVisual> sprites = new ArrayList<>();
        PickupCatalog catalog = model.pickupField().catalog();
        for (Pickup pickup : model.pickupField().items()) {
            float bob = (float) Math.sin(clock * 4 + pickup.id()) * 2f;
            sprites.add(new SpriteVisual((float) pickup.x(), (float) (maze.heightPx() - pickup.y()) + bob,
                    (float) catalog.itemSize(), 0f, false, catalog.spec(pickup.kind()).sprite()));
        }
        return sprites;
    }

    /** After a heart: the green arrow over the player's head, rising (screen y-up); null otherwise. */
    public static SpriteVisual healArrow(GameWorldModel model, PlayerState player) {
        HealFeedback feedback = model.healFeedback();
        if (player == null || !feedback.active()) {
            return null;
        }
        float size = player.halfSize() * 2f;
        return new SpriteVisual(player.x(), player.y() + (float) feedback.arrowLift(size),
                size * (float) HealFeedback.ARROW_SIZE_FACTOR, 0f, false, HealFeedback.ARROW_SPRITE);
    }

    /** After a heart: the flash over the player, following them; null otherwise. */
    public static ExplosionVisual healFlash(GameWorldModel model, PlayerState player) {
        HealFeedback feedback = model.healFeedback();
        if (player == null || !feedback.active()) {
            return null;
        }
        HealFlashEffect flash = new HealFlashEffect(player.x(), player.y(), player.halfSize() * 2f);
        double progress = feedback.progress();
        return clock -> flash.puffs(progress, clock);
    }

    /** The weapon in the player's hands, held towards the facing; null for bombs (planted, not held). */
    public static SpriteVisual heldWeapon(GameWorldModel model, PlayerState player) {
        Weapon weapon = model.arsenal().selected();
        if (player == null || weapon == Weapon.BOMB || model.arsenal().count(weapon) <= 0) {
            return null;
        }
        PickupKind kind = weapon.pickup();
        float reach = player.halfSize() * 0.8f;
        float x = player.x() + player.facingX() * reach;
        float y = player.y() + player.facingY() * reach - player.halfSize() * 0.2f;
        float rotation = player.facingY() > 0 ? 90f : player.facingY() < 0 ? -90f : 0f;
        return new SpriteVisual(x, y, player.halfSize() * 1.5f, rotation, player.facingX() < 0,
                model.pickupField().catalog().spec(kind).sprite());
    }
}
