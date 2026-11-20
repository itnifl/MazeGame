package main.game.maze.game.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.PumpkinBomber;
import main.game.maze.opponents.Zombie;
import main.game.maze.service.EnemyVitals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Kill scoring (who killed what, points by danger)")
class KillScoringTest {

    /** An enemy backed by the shared EnemyVitals, like both frontends. */
    private static final class Enemy implements ICanDie {
        final EnemyVitals vitals = new EnemyVitals(this, 50, null);

        @Override public int getHitPoints() { return vitals.getHitPoints(); }
        @Override public void setHitPoints(int hp) { vitals.setHitPoints(hp); }
        @Override public void subtractHitPoints(int hp) { vitals.subtractHitPoints(hp, DeathCause.OTHER, null); }
        @Override public void subtractHitPoints(int hp, DeathCause cause) { vitals.subtractHitPoints(hp, cause, null); }
        @Override public void subtractHitPoints(int hp, DeathCause cause, String attackerId) {
            vitals.subtractHitPoints(hp, cause, attackerId);
        }
        @Override public void addHitPoints(int hp) { vitals.addHitPoints(hp); }
        @Override public void addDeathNotificationSubscriber(IDeathSubscriber s) { vitals.addDeathSubscriber(s); }
        @Override public DeathCause deathCause() { return vitals.deathCause(); }
        @Override public String killerId() { return vitals.killerId(); }
    }

    @Test
    @DisplayName("the player who lands the killing blow is the killer, not whoever hit first")
    void killingBlowDecidesTheKiller() {
        Enemy enemy = new Enemy();
        enemy.subtractHitPoints(40, DeathCause.SHOT, "player-2");
        assertNull(enemy.killerId(), "nobody has killed it yet");
        enemy.subtractHitPoints(40, DeathCause.CRUSHED, PlayerIds.LOCAL_PLAYER);
        assertEquals(PlayerIds.LOCAL_PLAYER, enemy.killerId());
        assertEquals(DeathCause.CRUSHED, enemy.deathCause());

        enemy.vitals.respawn(0);
        assertNull(enemy.killerId(), "a respawned enemy has no killer");
        enemy.setHitPoints(0);
        assertNull(enemy.killerId(), "deaths without an attacker credit nobody");
    }

    @Test
    @DisplayName("kill points follow the enemy's danger (model threat level)")
    void pointsFollowDanger() {
        Zombie weak = OpponentsFactory.eINSTANCE.createZombie();
        weak.setThreatLevel(1.0);
        PumpkinBomber elite = OpponentsFactory.eINSTANCE.createPumpkinBomber();
        elite.setThreatLevel(8.0);

        assertEquals(KillScoring.POINTS_PER_THREAT, KillScoring.pointsFor(weak));
        assertEquals(8 * KillScoring.POINTS_PER_THREAT, KillScoring.pointsFor(elite));
        assertEquals(KillScoring.MIN_KILL_POINTS, KillScoring.pointsForThreat(0));
        assertEquals(KillScoring.MIN_KILL_POINTS, KillScoring.pointsFor(null));
    }

    @Test
    @DisplayName("the ledger keeps every kill per player; kills without a killer score nothing")
    void ledgerPerPlayer() {
        KillLedger ledger = new KillLedger();
        Enemy byMe = new Enemy();
        byMe.subtractHitPoints(50, DeathCause.FIRE, PlayerIds.LOCAL_PLAYER);
        Enemy byOther = new Enemy();
        byOther.subtractHitPoints(50, DeathCause.SHOT, "player-2");
        Enemy byNobody = new Enemy();
        byNobody.subtractHitPoints(50, DeathCause.CRUSHED, null);

        assertTrue(ledger.record(byMe, "zombie_weak_1", 100).isPresent());
        assertTrue(ledger.record(byOther, "pb_hard", 800).isPresent());
        assertTrue(ledger.record(byNobody, "ghost_weak_1", 100).isEmpty());

        assertEquals(100, ledger.pointsFor(PlayerIds.LOCAL_PLAYER));
        assertEquals(800, ledger.pointsFor("player-2"));
        assertEquals(0, ledger.pointsFor("player-3"));
        List<KillLedger.Kill> mine = ledger.killsBy(PlayerIds.LOCAL_PLAYER);
        assertEquals(1, mine.size());
        assertEquals(new KillLedger.Kill(PlayerIds.LOCAL_PLAYER, "zombie_weak_1", DeathCause.FIRE, 100), mine.get(0));
        assertEquals(2, ledger.kills().size());
        ledger.clear();
        assertEquals(0, ledger.pointsFor(PlayerIds.LOCAL_PLAYER));
    }

    @Test
    @DisplayName("kill points raise the live score and appear in the end-screen breakdown")
    void scoringIncludesKills() {
        ScoringEngine engine = new ScoringEngine();
        assertEquals(engine.gameplayScore(1_000, 10, 0) + 300, engine.gameplayScore(1_000, 10, 0, 300));
        ScoringEngine.ScoreBreakdown breakdown = engine.breakdown(1_000, 10, 100, 100, 0, false, 300);
        assertEquals(300, breakdown.killBonus());
        assertEquals(engine.breakdown(1_000, 10, 100, 100, 0, false).total() + 300, breakdown.total());
        assertEquals(0, engine.breakdown(1_000, 10, 100, 100, 0, false).killBonus());
    }
}
