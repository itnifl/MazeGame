/*
 * MazeGame DSL - Validator
 * 
 * This class defines validation rules for the MazeDsl language.
 * These rules ensure that game configurations are semantically correct.
 */
package main.game.maze.dsl.validation;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import main.game.maze.dsl.mazeDsl.*;

/**
 * Custom validation rules for MazeDsl.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class MazeDslValidator extends AbstractMazeDslValidator {

    public static final String INVALID_THREAT_LEVEL = "invalidThreatLevel";
    public static final String INVALID_HEALTH = "invalidHealth";
    public static final String INVALID_SPEED = "invalidSpeed";
    public static final String INSUFFICIENT_WAYPOINTS = "insufficientWaypoints";
    public static final String INVALID_MAX_COUNT = "invalidMaxCount";
    public static final String INVALID_MAX_THREAT = "invalidMaxThreat";
    public static final String DUPLICATE_OPPONENT_NAME = "duplicateOpponentName";
    public static final String DUPLICATE_PATROL_NAME = "duplicatePatrolName";
    public static final String WAYPOINT_OUTSIDE_ZONE = "waypointOutsideZone";
    public static final String MISMATCHED_SPECIFICS = "mismatchedSpecifics";
    public static final String PATROL_REF_REQUIRED = "patrolRefRequired";

    /**
     * Validates that threat level is within acceptable bounds (0-100).
     */
    @Check
    public void checkThreatLevel(OpponentConfig opponent) {
        if (opponent.getThreatLevel() > 100) {
            error("Threat level cannot exceed 100",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__THREAT_LEVEL,
                  INVALID_THREAT_LEVEL);
        }
        if (opponent.getThreatLevel() < 0) {
            error("Threat level cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__THREAT_LEVEL,
                  INVALID_THREAT_LEVEL);
        }
    }

    /**
     * Validates that health is positive (0 means use default).
     */
    @Check
    public void checkHealth(OpponentConfig opponent) {
        if (opponent.getHealth() < 0) {
            error("Health cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__HEALTH,
                  INVALID_HEALTH);
        }
    }

    /**
     * Validates that speed is positive.
     */
    @Check
    public void checkSpeed(OpponentConfig opponent) {
        if (opponent.getSpeed() < 0) {
            error("Speed cannot be negative",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__SPEED,
                  INVALID_SPEED);
        }
    }

    /**
     * Validates that patrol paths have at least 2 waypoints.
     */
    @Check
    public void checkPatrolWaypoints(PatrolConfig patrol) {
        if (patrol.getWaypoints().size() < 2) {
            var feature = patrol.getWaypoints().isEmpty()
                ? MazeDslPackage.Literals.PATROL_CONFIG__NAME
                : MazeDslPackage.Literals.PATROL_CONFIG__WAYPOINTS;
            warning("Patrol should have at least 2 waypoints for meaningful movement",
                    feature,
                    INSUFFICIENT_WAYPOINTS);
        }
    }

    /**
     * Validates that max threat in difficulty is reasonable.
     */
    @Check
    public void checkMaxThreat(DifficultyConfig difficulty) {
        if (difficulty.getMaxThreat() < 0) {
            error("Max threat cannot be negative",
                  MazeDslPackage.Literals.DIFFICULTY_CONFIG__MAX_THREAT,
                  INVALID_MAX_THREAT);
        }
    }

    /**
     * Validates that enemy limits have positive max count.
     */
    @Check
    public void checkEnemyLimit(EnemyLimit limit) {
        if (limit.getMaxCount() < 0) {
            error("Max enemy count cannot be negative",
                  MazeDslPackage.Literals.ENEMY_LIMIT__MAX_COUNT,
                  INVALID_MAX_COUNT);
        }
    }

    /**
     * Validates that waypoints are within the patrol zone if defined.
     */
    @Check
    public void checkWaypointsInZone(PatrolConfig patrol) {
        PatrolZoneConfig zone = patrol.getZone();
        if (zone == null) {
            return;
        }

        double minX = zone.getTopLeftX();
        double minY = zone.getTopLeftY();
        double maxX = minX + zone.getWidth();
        double maxY = minY + zone.getHeight();

        for (Waypoint wp : patrol.getWaypoints()) {
            if (wp.getX() < minX || wp.getX() > maxX ||
                wp.getY() < minY || wp.getY() > maxY) {
                warning("Waypoint (" + wp.getX() + ", " + wp.getY() + ") is outside patrol zone",
                        MazeDslPackage.Literals.PATROL_CONFIG__WAYPOINTS,
                        WAYPOINT_OUTSIDE_ZONE);
            }
        }
    }

    /**
     * Validates that character-specific blocks match the character type.
     */
    @Check
    public void checkCharacterSpecificsMatch(OpponentConfig opponent) {
        CharacterSpecifics specifics = opponent.getCharacterSpecifics();
        if (specifics == null) {
            return;
        }

        CharacterTypeEnum type = opponent.getType();
        
        if (specifics instanceof ZombieSpecifics && type != CharacterTypeEnum.ZOMBIE) {
            warning("zombie-stats block should only be used with zombie type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
        
        if (specifics instanceof GhostSpecifics && type != CharacterTypeEnum.GHOST) {
            warning("ghost-stats block should only be used with ghost type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
        
        if (specifics instanceof RangedSpecifics && type != CharacterTypeEnum.PUMPKINBOMBER) {
            warning("ranged-stats block should only be used with pumpkinbomber type opponents",
                    MazeDslPackage.Literals.OPPONENT_CONFIG__CHARACTER_SPECIFICS,
                    MISMATCHED_SPECIFICS);
        }
    }

    /**
     * Validates that opponent names are unique within a game configuration.
     */
    @Check(CheckType.NORMAL)
    public void checkUniqueOpponentNames(GameConfiguration game) {
        java.util.Set<String> names = new java.util.HashSet<>();
        for (OpponentConfig opponent : game.getOpponents()) {
            if (!names.add(opponent.getName())) {
                error("Duplicate opponent name: " + opponent.getName(),
                      opponent,
                      MazeDslPackage.Literals.OPPONENT_CONFIG__NAME,
                      DUPLICATE_OPPONENT_NAME);
            }
        }
    }

    /**
     * Validates that patrol names are unique within a game configuration.
     */
    @Check(CheckType.NORMAL)
    public void checkUniquePatrolNames(GameConfiguration game) {
        java.util.Set<String> names = new java.util.HashSet<>();
        for (PatrolConfig patrol : game.getPatrols()) {
            if (!names.add(patrol.getName())) {
                error("Duplicate patrol name: " + patrol.getName(),
                      patrol,
                      MazeDslPackage.Literals.PATROL_CONFIG__NAME,
                      DUPLICATE_PATROL_NAME);
            }
        }
    }

    /**
     * Validates that opponents with patrol behavior have a patrol reference.
     */
    @Check
    public void checkPatrolBehaviorHasRef(OpponentConfig opponent) {
        if (opponent.getBehavior() == BehaviorTypeEnum.PATROL && opponent.getPatrolRef() == null) {
            error("Opponents with patrol behavior must have a patrolRef",
                  MazeDslPackage.Literals.OPPONENT_CONFIG__PATROL_REF,
                  PATROL_REF_REQUIRED);
        }
    }

    /**
     * Validates total threat level doesn't exceed max threat.
     */
    @Check(CheckType.EXPENSIVE)
    public void checkTotalThreatVsMaxThreat(GameConfiguration game) {
        DifficultyConfig diff = game.getDifficulty();
        if (diff == null) {
            return;
        }

        double totalThreat = 0;
        for (OpponentConfig opponent : game.getOpponents()) {
            totalThreat += opponent.getThreatLevel();
        }

        if (totalThreat > diff.getMaxThreat()) {
            warning("Total threat level (" + totalThreat + 
                    ") exceeds max threat (" + diff.getMaxThreat() + ")",
                    MazeDslPackage.Literals.GAME_CONFIGURATION__OPPONENTS);
        }
    }
}


