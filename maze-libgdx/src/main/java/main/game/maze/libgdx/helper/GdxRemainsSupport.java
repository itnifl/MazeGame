package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.common.remains.RemainsLedger;
import main.game.maze.common.remains.RemainsSpriteResolver;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RemainsVisual;

/**
 * Leaves remains where an enemy died (coal for fire, a bloody body when shot, a splat when
 * crushed), keyed by the enemy's spawn so they disappear when it respawns.
 */
public final class GdxRemainsSupport {

    private static final RemainsSpriteResolver SPRITES = RemainsSpriteResolver.classpath(GdxRemainsSupport.class);

    private GdxRemainsSupport() {
    }

    /** Records remains for a dead enemy, if its death cause leaves any. */
    public static void leaveRemains(RemainsLedger<EnemySpawn, RemainsVisual> ledger, GdxEnemyRuntime enemy) {
        if (ledger == null || enemy == null) {
            return;
        }
        String sprite = SPRITES.spriteFor(enemy.deathCause(), enemy.imagePath(), FireBlast.seedFor(enemy.x(), enemy.y()));
        if (sprite == null) {
            return;
        }
        ledger.put(enemy.originalSpawn(),
                new RemainsVisual(enemy.x(), enemy.y(), enemy.size() * enemy.spriteScale(), sprite));
    }

    /** Remains sprite paths an enemy can leave, for texture preloading. */
    public static List<String> possibleSprites(String enemyImagePath) {
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < RemainsSpriteResolver.BURNED_VARIANTS; i++) {
            paths.add(SPRITES.spriteFor(DeathCause.FIRE, enemyImagePath, i));
        }
        paths.add(SPRITES.spriteFor(DeathCause.SHOT, enemyImagePath, 0));
        paths.add(SPRITES.spriteFor(DeathCause.CRUSHED, enemyImagePath, 0));
        return paths;
    }
}
