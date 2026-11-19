package main.game.maze.libgdx.helper;

import java.util.List;
import java.util.function.Supplier;
import main.game.maze.common.graphics.fire.RubbleMaterial;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.common.remains.CrushZone;
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
        return collapsed -> onWallsCollapsed(worldModel, enemies, collapsed, heightPx.get());
    }

    static void onWallsCollapsed(GameWorldModel worldModel, List<GdxEnemyRuntime> enemies,
                                 List<BreakableWall> collapsed, float heightPx) {
        for (BreakableWall wall : collapsed) {
            Vector2D g = wall.geometry;
            double ax = g.getStart().getX();
            double ay = heightPx - g.getStart().getY();
            double bx = g.getEnd().getX();
            double by = heightPx - g.getEnd().getY();
            worldModel.wallRubble().add(new GameWorldModel.ActiveWallRubble(new WallRubble(ax, ay, bx, by,
                    RUBBLE_THICKNESS, RubbleMaterial.fromMaterialId(wall.material.id()),
                    Double.doubleToLongBits(ax * 31 + by))));
            crush(enemies, ax, ay, bx, by);
        }
    }

    static void crush(List<GdxEnemyRuntime> enemies, double ax, double ay, double bx, double by) {
        if (enemies != null) {
            MortalityService.crushAlong(enemies, GdxWallCollapseSupport::bodyOf, ax, ay, bx, by);
        }
    }

    /** The enemy's body on screen: centre of its drawn sprite, radius from its size. */
    static MortalityService.Body bodyOf(GdxEnemyRuntime enemy) {
        if (enemy == null) {
            return null;
        }
        float size = enemy.size() * enemy.spriteScale();
        return new MortalityService.Body(enemy.x() + size * 0.5, enemy.y() + size * 0.5,
                size * CrushZone.BODY_RADIUS_FACTOR);
    }

    /** Advances the rubble animations and drops finished ones. */
    public static void tick(GameWorldModel worldModel, float dt) {
        for (GameWorldModel.ActiveWallRubble rubble : worldModel.wallRubble()) {
            rubble.tick(dt);
        }
        worldModel.wallRubble().removeIf(GameWorldModel.ActiveWallRubble::finished);
    }
}
