package main.game.maze.game.score;

import main.game.maze.opponents.CharacterType;

/**
 * Points for killing an enemy, in proportion to how dangerous it is: the opponent model's
 * effective threat (falling back to its threat level). The same in both frontends.
 */
public final class KillScoring {

    /** Points per unit of threat (a threat-1 ghost is worth 100, a threat-8 elite bomber 800). */
    public static final int POINTS_PER_THREAT = 100;
    /** Even a harmless enemy is worth something. */
    public static final int MIN_KILL_POINTS = 10;

    private KillScoring() {
    }

    public static int pointsForThreat(double threat) {
        if (!(threat > 0)) {
            return MIN_KILL_POINTS;
        }
        return Math.max(MIN_KILL_POINTS, (int) Math.round(threat * POINTS_PER_THREAT));
    }

    /** Kill points for an enemy of this model type. */
    public static int pointsFor(CharacterType type) {
        if (type == null) {
            return MIN_KILL_POINTS;
        }
        double threat = type.getEffectiveThreat() > 0 ? type.getEffectiveThreat() : type.getThreatLevel();
        return pointsForThreat(threat);
    }
}
