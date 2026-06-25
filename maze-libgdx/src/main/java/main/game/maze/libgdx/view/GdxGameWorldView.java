package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import java.util.List;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

/**
 * Renders the game world, including maze geometry, entities, and world-space overlays.
 */
public final class GdxGameWorldView {

    public void render(RenderContext context) {
        context.viewport().apply();
        float originalCameraX = context.camera().position.x;
        float originalCameraY = context.camera().position.y;
        context.camera().position.set(
            originalCameraX + context.shakeOffsetX(),
            originalCameraY + context.shakeOffsetY(),
            0f);
        context.camera().update();

        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        OrthographicCamera camera = context.camera();
        MazeArena maze = context.maze();
        PlayerState player = context.player();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackground(context, batch, camera, maze);
        drawGoalTexture(context, batch);
        drawWallsTexture(context, batch, maze);
        drawEnemies(context, batch, font);
        drawPlayerTexture(context, batch, player);

        batch.end();

        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        drawGoalFallback(context, shapes);
        drawWallsFallback(context, shapes, maze);
        drawEnemyFallback(context, shapes);
        drawEnemyProjectiles(context, shapes);
        drawEnemyBeams(context, shapes);
        drawEnemyImpacts(context, shapes);
        drawPlayerFallback(context, shapes, player);
        drawHintPath(context, shapes, maze);
        if (context.showEnemyPathSeconds() > 0f) {
            drawEnemyPathOverlay(context, shapes, maze);
        }
        drawSpanningTreeOverlay(context, shapes, maze, player);

        shapes.end();
        context.camera().position.set(originalCameraX, originalCameraY, 0f);
        context.camera().update();
    }

    private void drawBackground(RenderContext context, SpriteBatch batch, OrthographicCamera camera, MazeArena maze) {
        Texture backgroundTexture = context.backgroundTexture();
        if (backgroundTexture == null) {
            return;
        }

        float tileW = Math.max(1f, backgroundTexture.getWidth());
        float tileH = Math.max(1f, backgroundTexture.getHeight());
        float viewMinX = camera.position.x - context.viewport().getWorldWidth() * 0.5f;
        float viewMinY = camera.position.y - context.viewport().getWorldHeight() * 0.5f;
        float viewMaxX = camera.position.x + context.viewport().getWorldWidth() * 0.5f;
        float viewMaxY = camera.position.y + context.viewport().getWorldHeight() * 0.5f;

        float minX = Math.min(0f, viewMinX);
        float minY = Math.min(0f, viewMinY);
        float maxX = Math.max(maze.widthPx(), viewMaxX);
        float maxY = Math.max(maze.heightPx(), viewMaxY);

        float startX = (float) Math.floor(minX / tileW) * tileW;
        float startY = (float) Math.floor(minY / tileH) * tileH;

        for (float x = startX; x < maxX; x += tileW) {
            for (float y = startY; y < maxY; y += tileH) {
                batch.draw(backgroundTexture, x, y, tileW, tileH);
            }
        }
    }

    private void drawGoalTexture(RenderContext context, SpriteBatch batch) {
        if (context.goalTexture() == null) {
            return;
        }
        float hs = context.activeGoalSize() * 0.5f;
        batch.draw(
                context.goalTexture(),
                context.activeGoalX() - hs,
                context.activeGoalY() - hs,
                context.activeGoalSize(),
                context.activeGoalSize());
    }

    private void drawWallsTexture(RenderContext context, SpriteBatch batch, MazeArena maze) {
        if (context.wallTexture() == null) {
            return;
        }
        float thickness = context.wallThickness();
        for (WallSegment wall : maze.walls()) {
            if (wall.isHorizontal()) {
                float x = Math.min(wall.x1, wall.x2);
                float len = Math.abs(wall.x2 - wall.x1);
                batch.draw(context.wallTexture(), x - thickness * 0.5f, wall.y1 - thickness * 0.5f, len + thickness, thickness);
            } else {
                float y = Math.min(wall.y1, wall.y2);
                float len = Math.abs(wall.y2 - wall.y1);
                batch.draw(context.wallTexture(), wall.x1 - thickness * 0.5f, y - thickness * 0.5f, thickness, len + thickness);
            }
        }
    }

    private void drawEnemies(RenderContext context, SpriteBatch batch, BitmapFont font) {
        for (EnemyViewModel enemy : context.enemies()) {
            if (enemy.texture() == null) {
                continue;
            }
            if (enemy.infectious()) {
                drawInfectiousEdgeMist(batch, enemy, context.infectionEdgeLayers(), context.halfRatio(), context.enemyAnimationClock());
            }
            float half = enemy.size() * 0.5f;
            if (enemy.opacity() < 1.0f) {
                batch.setColor(1f, 1f, 1f, enemy.opacity());
            }
            batch.draw(enemy.texture(), enemy.x() - half, enemy.y() - half, enemy.size(), enemy.size());
            if (enemy.opacity() < 1.0f) {
                batch.setColor(Color.WHITE);
            }

            if (enemy.label() != null) {
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, enemy.label(), enemy.x() - enemy.size() * 0.5f, enemy.y() + enemy.size() * 0.75f);
            }
        }
    }

    private void drawPlayerTexture(RenderContext context, SpriteBatch batch, PlayerState player) {
        Texture activePlayerTexture = context.playerDead() ? context.playerDeathTexture() : context.playerTexture();
        float playerDrawSize = player.halfSize() * 2f;
        batch.setColor(context.playerTintRed(), context.playerTintGreen(), context.playerTintBlue(), 1f);
        if (activePlayerTexture != null) {
            float scale = context.playerDead() ? context.playerDeadScale() : context.playerAliveScale();
            float drawSize = playerDrawSize * scale;
            float halfDraw = drawSize * context.halfRatio();
            batch.draw(activePlayerTexture, player.x() - halfDraw, player.y() - halfDraw, drawSize, drawSize);
        }
        batch.setColor(Color.WHITE);
    }

    private void drawGoalFallback(RenderContext context, ShapeRenderer shapes) {
        if (context.goalTexture() != null) {
            return;
        }
        float hs = context.activeGoalSize() * 0.5f;
        shapes.setColor(0.15f, 0.55f, 0.2f, 1f);
        shapes.rect(context.activeGoalX() - hs, context.activeGoalY() - hs, context.activeGoalSize(), context.activeGoalSize());
    }

    private void drawWallsFallback(RenderContext context, ShapeRenderer shapes, MazeArena maze) {
        if (context.wallTexture() != null) {
            return;
        }
        float thickness = context.wallThickness();
        shapes.setColor(0.85f, 0.85f, 0.9f, 1f);
        for (WallSegment wall : maze.walls()) {
            if (wall.isHorizontal()) {
                float x = Math.min(wall.x1, wall.x2);
                float len = Math.abs(wall.x2 - wall.x1);
                shapes.rect(x - thickness * 0.5f, wall.y1 - thickness * 0.5f, len + thickness, thickness);
            } else {
                float y = Math.min(wall.y1, wall.y2);
                float len = Math.abs(wall.y2 - wall.y1);
                shapes.rect(wall.x1 - thickness * 0.5f, y - thickness * 0.5f, thickness, len + thickness);
            }
        }
    }

    private void drawEnemyFallback(RenderContext context, ShapeRenderer shapes) {
        for (EnemyViewModel enemy : context.enemies()) {
            if (enemy.texture() != null) {
                continue;
            }
            if (enemy.infectious()) {
                drawInfectiousMist(shapes, enemy, context.enemyAnimationClock());
            }
            shapes.setColor(0.65f, 0.2f, 0.2f, 1f);
            float half = enemy.size() * 0.5f;
            shapes.rect(enemy.x() - half, enemy.y() - half, enemy.size(), enemy.size());
        }
    }

    private void drawPlayerFallback(RenderContext context, ShapeRenderer shapes, PlayerState player) {
        Texture activePlayerTexture = context.playerDead() ? context.playerDeathTexture() : context.playerTexture();
        if (activePlayerTexture != null) {
            return;
        }
        float playerDrawSize = player.halfSize() * 2f;
        float scale = context.playerDead() ? context.playerDeadScale() : context.playerAliveScale();
        float drawSize = playerDrawSize * scale;
        float halfDraw = drawSize * context.halfRatio();
        shapes.setColor(context.playerTintRed(), context.playerTintGreen(), context.playerTintBlue(), 1f);
        shapes.rect(player.x() - halfDraw, player.y() - halfDraw, drawSize, drawSize);
    }

    private void drawEnemyProjectiles(RenderContext context, ShapeRenderer shapes) {
        for (GdxEnemyRuntime.ProjectileVisual projectile : context.enemyProjectiles()) {
            if (projectile.lob() && projectile.shadowRadius() > 0f) {
                shapes.setColor(0f, 0f, 0f, 0.22f);
                shapes.circle(projectile.x(), projectile.y() - projectile.shadowRadius(), projectile.shadowRadius(), 20);
            }
            if (projectile.lob()) {
                shapes.setColor(1f, 0.66f, 0.18f, 0.95f);
            } else {
                shapes.setColor(1f, 0.80f, 0.32f, 0.95f);
            }
            shapes.circle(projectile.x(), projectile.y(), projectile.radius(), 20);
        }
    }

    private void drawEnemyBeams(RenderContext context, ShapeRenderer shapes) {
        for (GdxEnemyRuntime.BeamVisual beam : context.enemyBeams()) {
            float alpha = Math.max(0f, Math.min(1f, beam.alpha()));
            if (alpha <= 0f) {
                continue;
            }
            if (beam.blocked()) {
                shapes.setColor(1f, 0.42f, 0.30f, 0.85f * alpha);
            } else {
                shapes.setColor(0.42f, 1f, 0.96f, 0.9f * alpha);
            }
            shapes.rectLine(beam.x1(), beam.y1(), beam.x2(), beam.y2(), beam.width());
        }
    }

    private void drawEnemyImpacts(RenderContext context, ShapeRenderer shapes) {
        for (GdxEnemyRuntime.ImpactVisual impact : context.enemyImpacts()) {
            float alpha = Math.max(0f, Math.min(1f, impact.alpha()));
            if (alpha <= 0f) {
                continue;
            }
            shapes.setColor(1f, 0.72f, 0.18f, 0.28f * alpha);
            shapes.circle(impact.x(), impact.y(), impact.radius(), 28);
            shapes.setColor(1f, 0.40f, 0.18f, 0.78f * alpha);
            shapes.circle(impact.x(), impact.y(), Math.max(3f, impact.radius() * 0.55f), 24);
        }
    }

    private void drawHintPath(RenderContext context, ShapeRenderer shapes, MazeArena maze) {
        if (context.activePathPoints().isEmpty()) {
            return;
        }
        shapes.setColor(0.12f, 0.58f, 0.95f, 0.72f);
        for (int i = 1; i < context.activePathPoints().size(); i++) {
            Point2D a = context.activePathPoints().get(i - 1);
            Point2D b = context.activePathPoints().get(i);
            float ax = (float) a.getX();
            float ay = maze.heightPx() - (float) a.getY();
            float bx = (float) b.getX();
            float by = maze.heightPx() - (float) b.getY();
            drawPathSegment(shapes, ax, ay, bx, by, 8f);
        }
    }

    private void drawEnemyPathOverlay(RenderContext context, ShapeRenderer shapes, MazeArena maze) {
        shapes.setColor(1f, 0.72f, 0.2f, 0.7f);
        for (EnemyViewModel enemy : context.enemies()) {
            if (enemy.activePathPoints().isEmpty()) {
                continue;
            }
            for (int i = 1; i < enemy.activePathPoints().size(); i++) {
                ActivePathPoint a = enemy.activePathPoints().get(i - 1);
                ActivePathPoint b = enemy.activePathPoints().get(i);
                float ax = (float) a.x();
                float ay = maze.heightPx() - (float) a.y();
                float bx = (float) b.x();
                float by = maze.heightPx() - (float) b.y();
                drawPathSegment(shapes, ax, ay, bx, by, 5f);
            }
        }
    }

    private void drawSpanningTreeOverlay(RenderContext context, ShapeRenderer shapes, MazeArena maze, PlayerState player) {
        if (!context.showSpanningTreeInfo() || !(maze instanceof RealMaze realMaze) || realMaze.navigationGraph() == null) {
            return;
        }
        Point2D playerPos = new Point2D(player.x(), maze.heightPx() - player.y());
        MazeNavigationGraphService.rebuildSpanningTreeFrom(realMaze.navigationGraph(), playerPos);
        var grid = realMaze.navigationGraph().getGrid();
        int cols = realMaze.navigationGraph().getCols();
        int rows = realMaze.navigationGraph().getRows();
        shapes.setColor(1f, 0.23f, 0.20f, 0.58f);
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                var node = grid[c][r];
                if (node == null || node.getTreeParent() == null) {
                    continue;
                }
                float x1 = (float) node.getX();
                float y1 = maze.heightPx() - (float) node.getY();
                float x2 = (float) node.getTreeParent().getX();
                float y2 = maze.heightPx() - (float) node.getTreeParent().getY();
                drawPathSegment(shapes, x1, y1, x2, y2, 4f);
            }
        }
    }

    private void drawInfectiousMist(ShapeRenderer renderer, EnemyViewModel enemy, float animationClock) {
        float pulse = 0.5f + 0.5f * (float) Math.sin(animationClock * (1.8f + enemy.infectionStrength()) + enemy.phase());
        float centerX = enemy.x();
        float centerY = enemy.y();
        float baseRadius = enemy.size() * (0.65f + 0.14f * enemy.infectionStrength());
        float outerRadius = baseRadius + pulse * enemy.size() * 0.42f;
        float midRadius = baseRadius * 0.78f + pulse * enemy.size() * 0.22f;

        renderer.setColor(0.20f, 1.0f, 0.46f, 0.10f + 0.10f * pulse * enemy.infectionStrength());
        renderer.circle(centerX, centerY, outerRadius, 28);
        renderer.setColor(0.56f, 1.0f, 0.72f, 0.08f + 0.10f * pulse * enemy.infectionStrength());
        renderer.circle(centerX, centerY, midRadius, 24);
    }

    private void drawInfectiousEdgeMist(
            SpriteBatch batch,
            EnemyViewModel enemy,
            int infectionEdgeLayers,
            float halfRatio,
            float animationClock) {
        float pulse = 0.5f + 0.5f * (float) Math.sin(animationClock * (1.8f + enemy.infectionStrength()) + enemy.phase());
        float intensity = Math.max(0.35f, Math.min(1f, enemy.infectionStrength()));

        int prevSrcBlend = batch.getBlendSrcFunc();
        int prevDstBlend = batch.getBlendDstFunc();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        int layers = Math.max(1, infectionEdgeLayers);
        for (int i = 0; i < layers; i++) {
            float layerFraction = layers == 1 ? 0f : i / (float) (layers - 1);
            float scale = 1.04f + layerFraction * (0.22f + 0.08f * pulse) + 0.06f * pulse * intensity;
            float alpha = (0.34f - layerFraction * 0.07f) * (0.78f + 0.22f * pulse) * intensity;

            float drawSize = enemy.size() * scale;
            float halfDraw = drawSize * halfRatio;

            float edgeOffset = (1.5f + 2.3f * layerFraction) * (0.95f + 1.05f * pulse);
            batch.setColor(0.30f, 1.0f, 0.52f, alpha);
            batch.draw(enemy.texture(), enemy.x() - halfDraw - edgeOffset, enemy.y() - halfDraw, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw + edgeOffset, enemy.y() - halfDraw, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw, enemy.y() - halfDraw - edgeOffset, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw, enemy.y() - halfDraw + edgeOffset, drawSize, drawSize);

            float diagonalOffset = edgeOffset * 0.75f;
            float diagonalAlpha = alpha * 0.9f;
            batch.setColor(0.30f, 1.0f, 0.52f, diagonalAlpha);
            batch.draw(enemy.texture(), enemy.x() - halfDraw - diagonalOffset, enemy.y() - halfDraw - diagonalOffset, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw + diagonalOffset, enemy.y() - halfDraw - diagonalOffset, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw - diagonalOffset, enemy.y() - halfDraw + diagonalOffset, drawSize, drawSize);
            batch.draw(enemy.texture(), enemy.x() - halfDraw + diagonalOffset, enemy.y() - halfDraw + diagonalOffset, drawSize, drawSize);
        }

        batch.setBlendFunction(prevSrcBlend, prevDstBlend);
        batch.setColor(Color.WHITE);
    }

    private static void drawPathSegment(ShapeRenderer renderer, float x1, float y1, float x2, float y2, float thickness) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len <= 0.001f) {
            return;
        }
        float nx = -dy / len * (thickness * 0.5f);
        float ny = dx / len * (thickness * 0.5f);
        renderer.triangle(x1 - nx, y1 - ny, x1 + nx, y1 + ny, x2 + nx, y2 + ny);
        renderer.triangle(x1 - nx, y1 - ny, x2 + nx, y2 + ny, x2 - nx, y2 - ny);
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            OrthographicCamera camera,
            com.badlogic.gdx.utils.viewport.Viewport viewport,
            MazeArena maze,
            PlayerState player,
            Texture backgroundTexture,
            Texture goalTexture,
            Texture wallTexture,
            Texture playerTexture,
            Texture playerDeathTexture,
            float activeGoalX,
            float activeGoalY,
            float activeGoalSize,
            float wallThickness,
            float playerTintRed,
            float playerTintGreen,
            float playerTintBlue,
            boolean playerDead,
            float playerAliveScale,
            float playerDeadScale,
            float halfRatio,
            float enemyAnimationClock,
            int infectionEdgeLayers,
            List<EnemyViewModel> enemies,
            List<Point2D> activePathPoints,
            List<GdxEnemyRuntime.ProjectileVisual> enemyProjectiles,
            List<GdxEnemyRuntime.BeamVisual> enemyBeams,
            List<GdxEnemyRuntime.ImpactVisual> enemyImpacts,
            float shakeOffsetX,
            float shakeOffsetY,
            float showEnemyPathSeconds,
            boolean showSpanningTreeInfo) {
    }

    public record EnemyViewModel(
            Texture texture,
            float x,
            float y,
            float size,
            float opacity,
            boolean infectious,
            float infectionStrength,
            float phase,
            String label,
            List<ActivePathPoint> activePathPoints) {
    }
}
