package main.game.maze.libgdx.helper;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.game.level.SergeantGunner;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX side of the zombie sergeants. The firing rules are the shared {@link SergeantGunner}; this
 * class only feeds it the frame (sight, walls), hurts the player, shows the tracers and, when a
 * sergeant has been killed, drops its gatling gun where it fell. Screen space is y-up, the shared
 * pickup world is y-down.
 */
public final class GdxSergeantSupport {

    private static final long FIRE_SOUND_COOLDOWN_MS = 500L;

    private final Map<GdxEnemyRuntime, SergeantGunner> gunners = new IdentityHashMap<>();

    /** New level: forget the sergeants of the last one. */
    public void reset() {
        gunners.clear();
    }

    /**
     * Advances every sergeant one frame and drops the guns of the ones killed since the last frame.
     *
     * @return the rounds fired this frame
     */
    public int tick(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze, PlayerState player,
                    PlayerCombatStateService combat, SergeantSpec spec, float dt) {
        if (maze == null || player == null || spec == null) {
            return 0;
        }
        dropGunsOfTheKilled(model, enemies, maze);
        int fired = 0;
        for (GdxEnemyRuntime enemy : enemies) {
            if (!enemy.isSergeant() || !enemy.isAlive()) {
                continue;
            }
            SergeantGunner gunner = gunners.computeIfAbsent(enemy, ignored -> new SergeantGunner(spec));
            boolean wall = WallCollisionUtil.wallBetween(enemy.x(), enemy.y(), player.x(), player.y(), maze.walls());
            boolean sees = gunner.canSee(enemy.x(), enemy.y(), player.x(), player.y(), wall);
            int rounds = gunner.tick(dt, sees);
            for (int round = 0; round < rounds; round++) {
                model.effects().add(new GameWorldModel.ActiveEffect(
                        main.game.maze.game.items.ShotEffects.enemyRound(enemy.x(), enemy.y(), player.x(), player.y())));
                combat.applyDirectDamage(spec.roundDamage());
            }
            if (rounds > 0 && spec.fireSound() != null) {
                AudioEngine.get().playRateLimited(spec.fireSound(),
                        "sergeant.fire." + System.identityHashCode(enemy), FIRE_SOUND_COOLDOWN_MS);
            }
            fired += rounds;
        }
        return fired;
    }

    /** A sergeant that was seen alive and is now dead or gone from the level leaves its gun. */
    private void dropGunsOfTheKilled(GameWorldModel model, List<GdxEnemyRuntime> enemies, MazeArena maze) {
        var iterator = gunners.entrySet().iterator();
        while (iterator.hasNext()) {
            GdxEnemyRuntime sergeant = iterator.next().getKey();
            if (sergeant.isAlive() && enemies.contains(sergeant)) {
                continue;
            }
            model.pickupField().dropAt(main.game.maze.game.level.DropWeapon.GATLING_GUN.pickup(),
                    sergeant.x(), maze.heightPx() - sergeant.y());
            iterator.remove();
        }
    }
}
