package main.game.maze.libgdx.game;

import java.util.List;
import java.util.function.DoubleSupplier;

import main.game.maze.characters.CollisionDamage;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;

/**
 * Backend-local gameplay state for player hurt, infection, and hit feedback.
 * It intentionally has no rendering code so the screen only consumes its state.
 */
public final class PlayerCombatStateService {

    private static final long PLAYER_SOUND_COOLDOWN_MS = 250L;
    private static final long ZOMBIE_TOUCH_SOUND_COOLDOWN_MS = 600L;
    private static final float CONTACT_INTERVAL_SEC = 1f / 30f;
    private static final float FLASH_DURATION_SEC = 0.20f;
    private final DoubleSupplier randomSource;

    /** The current maze; used to test whether a wall separates enemy from player. */
    private MazeArena maze;

    private int maxHitPoints = 100;
    private float currentHitPoints = 100f;
    private float contactCooldownRemaining;
    private float flashRemaining;
    private float flashRed = 1f;
    private float flashGreen = 1f;
    private float flashBlue = 1f;
    private boolean dead;

    private float infectionDamagePerTick;
    private int infectionTicksRemaining;
    private float infectionTickCountdown;

    public PlayerCombatStateService() {
        this(() -> Math.random());
    }

    PlayerCombatStateService(DoubleSupplier randomSource) {
        this.randomSource = randomSource;
    }

    /**
     * Provides the active {@link MazeArena} so walls can be used to block
     * enemy damage.  Call this whenever a new game starts.
     */
    public void setMaze(MazeArena maze) {
        this.maze = maze;
    }

    public void reset(int maxHitPoints) {
        this.maxHitPoints = Math.max(1, maxHitPoints);
        this.currentHitPoints = this.maxHitPoints;
        this.contactCooldownRemaining = 0f;
        this.flashRemaining = 0f;
        this.flashRed = 1f;
        this.flashGreen = 1f;
        this.flashBlue = 1f;
        this.dead = false;
        this.infectionDamagePerTick = 0f;
        this.infectionTicksRemaining = 0;
        this.infectionTickCountdown = 0f;
    }

    public CombatFrame update(float dt, float playerX, float playerY, float playerRadius, List<EnemySpawn> enemies) {
        tickTimers(dt);

        if (!dead) {
            processInfection(dt);
            processEnemyContact(playerX, playerY, playerRadius, enemies);
        }

        return new CombatFrame(
                currentHitPoints / maxHitPoints,
                flashRed,
                flashGreen,
                flashBlue,
                dead,
                infectionTicksRemaining > 0);
    }

    public boolean isDead() {
        return dead;
    }

    public void applyDirectDamage(int damage) {
        if (dead) {
            return;
        }
        applyDamage(damage);
        startFlash(1f, 0.45f, 0.45f);
        AudioEngine.get().playRateLimited(
                AudioResourceConstants.PlayerScreamSound,
                "player.scream",
                PLAYER_SOUND_COOLDOWN_MS);
    }

    private void tickTimers(float dt) {
        contactCooldownRemaining = Math.max(0f, contactCooldownRemaining - dt);
        if (flashRemaining > 0f) {
            flashRemaining = Math.max(0f, flashRemaining - dt);
            if (flashRemaining == 0f) {
                flashRed = 1f;
                flashGreen = 1f;
                flashBlue = 1f;
            }
        }
    }

    private void processInfection(float dt) {
        if (infectionTicksRemaining <= 0) {
            return;
        }
        infectionTickCountdown -= dt;
        while (infectionTicksRemaining > 0 && infectionTickCountdown <= 0f) {
            applyDamage(Math.round(infectionDamagePerTick));
            startFlash(0.55f, 1f, 0.55f);
            AudioEngine.get().playRateLimited(
                    AudioResourceConstants.PlayerInfectedSound,
                    "player.infected",
                    PLAYER_SOUND_COOLDOWN_MS);
            infectionTicksRemaining--;
            infectionTickCountdown += 1.0f;
        }
    }

    private void processEnemyContact(float playerX, float playerY, float playerRadius, List<EnemySpawn> enemies) {
        if (contactCooldownRemaining > 0f) {
            return;
        }
        for (EnemySpawn enemy : enemies) {
            boolean isPhasing = GhostNonTangibilityService.isPhasing(enemy.nonTangibilityEnergy());
            // Phasing ghosts pass through walls and therefore bypass the wall-blocking check.
            // Solid enemies are blocked by any wall between them and the player.
            if (!isPhasing && maze != null && WallCollisionUtil.wallBetween(
                    enemy.x(), enemy.y(), playerX, playerY, maze.walls())) {
                continue;
            }
            float enemyRadius = enemy.size() * 0.5f;
            float allowed = playerRadius + enemyRadius + 2f;
            if (distanceSquared(playerX, playerY, enemy.x(), enemy.y()) > allowed * allowed) {
                continue;
            }

            int effectiveDamage = CollisionDamage.effectiveDamage(enemy.effectiveThreat(), enemy.attackDamage());
            applyDamage(effectiveDamage);
            startFlash(1f, 0.45f, 0.45f);

            AudioEngine.get().playRateLimited(
                    AudioResourceConstants.PlayerScreamSound,
                    "player.scream",
                    PLAYER_SOUND_COOLDOWN_MS);

            if (enemy.touchSoundPath() != null && !enemy.touchSoundPath().isBlank()) {
                AudioEngine.get().playRateLimited(
                        enemy.touchSoundPath(),
                        "zombie.touch." + enemy.id(),
                        ZOMBIE_TOUCH_SOUND_COOLDOWN_MS);
            }

            maybeStartInfection(enemy);
            contactCooldownRemaining = CONTACT_INTERVAL_SEC;
            break;
        }
    }

    private void maybeStartInfection(EnemySpawn enemy) {
        if (enemy.infectionLevel() <= 0 || infectionTicksRemaining > 0) {
            return;
        }

        double calculatedInfectionLevel = enemy.infectionLevel() / 100.0;
        if (calculatedInfectionLevel <= 0) {
            return;
        }

        final double minimalInfectionChance = 0.5;
        final double maxInfectionChance = 0.9;
        double infectionChance = Math.min(calculatedInfectionLevel + minimalInfectionChance, maxInfectionChance);
        if (randomSource.getAsDouble() >= infectionChance) {
            return;
        }

        infectionDamagePerTick = (float) (0.10 * enemy.attackDamage() * calculatedInfectionLevel);
        infectionTicksRemaining = 6;
        infectionTickCountdown = 1.0f;
    }

    private void applyDamage(int damage) {
        currentHitPoints = Math.max(0f, currentHitPoints - Math.max(0, damage));
        if (currentHitPoints <= 0f) {
            dead = true;
        }
    }

    private void startFlash(float red, float green, float blue) {
        flashRed = red;
        flashGreen = green;
        flashBlue = blue;
        flashRemaining = FLASH_DURATION_SEC;
    }

    private static float distanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public record CombatFrame(
            float hpRatio,
            float tintRed,
            float tintGreen,
            float tintBlue,
            boolean dead,
            boolean infected) {
    }
}
