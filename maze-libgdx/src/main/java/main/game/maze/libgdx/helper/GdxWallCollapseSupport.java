package main.game.maze.libgdx.helper;

import java.util.List;
import java.util.function.Supplier;
import main.game.maze.common.graphics.fire.RubbleMaterial;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.service.MortalityService;

/**
 * Reacts to breakable walls collapsing (libGDX): each collapsed segment falls as animated
 * rubble in its material, and crushes any living enemy touching it. Crushed enemies are only
 * marked dead here; the regular dead-enemy processing removes them and leaves their remains.
 *
 * <p>Wall geometry from {@link GameMazeWorld} is y-down; libGDX screen space is y-up, so
 * {@code y_screen = heightPx - y_world}.
 */
public final class GdxWallCollapseSupport {

    static final double RUBBLE_THICKNESS = WallRubble.visualThickness(StageConstants.WallThicknessPx);

    private GdxWallCollapseSupport() {
    }

    /**
     * @param heightPx maze height, read when a collapse happens
     */
    public static GameMazeWorld.WallCollapseListener listener(GameWorldModel worldModel,
                                                              List<GdxEnemyRuntime> enemies,
                                                              Supplier<Float> heightPx) {
        return (collapsed, attackerId) -> onWallsCollapsed(worldModel, enemies, collapsed, heightPx.get(), attackerId);
    }

    static void onWallsCollapsed(GameWorldModel worldModel, List<GdxEnemyRuntime> enemies,
                                 List<BreakableWall> collapsed, float heightPx) {
        onWallsCollapsed(worldModel, enemies, collapsed, heightPx, null);
    }

    /** As above; enemies the rubble crushes are credited to {@code attackerId} (who brought the wall down). */
    static void onWallsCollapsed(GameWorldModel worldModel, List<GdxEnemyRuntime> enemies,
                                 List<BreakableWall> collapsed, float heightPx, String attackerId) {
        for (BreakableWall wall : collapsed) {
            Vector2D g = wall.geometry;
            double ax = g.getStart().getX();
            double ay = heightPx - g.getStart().getY();
            double bx = g.getEnd().getX();
            double by = heightPx - g.getEnd().getY();
            worldModel.wallRubble().add(new GameWorldModel.ActiveWallRubble(new WallRubble(ax, ay, bx, by,
                    RUBBLE_THICKNESS, RubbleMaterial.fromMaterialId(wall.material.id()),
                    Double.doubleToLongBits(ax * 31 + by))));
            crush(enemies, ax, ay, bx, by, attackerId);
        }
    }

    static void crush(List<GdxEnemyRuntime> enemies, double ax, double ay, double bx, double by, String attackerId) {
        if (enemies != null) {
            MortalityService.crushAlong(enemies, GdxWeaponSupport::bodyOf, ax, ay, bx, by, attackerId);
        }
    }

    /** Advances the rubble animations and drops finished ones. */
    public static void tick(GameWorldModel worldModel, float dt) {
        for (GameWorldModel.ActiveWallRubble rubble : worldModel.wallRubble()) {
            rubble.tick(dt);
        }
        worldModel.wallRubble().removeIf(GameWorldModel.ActiveWallRubble::finished);
    }
}
