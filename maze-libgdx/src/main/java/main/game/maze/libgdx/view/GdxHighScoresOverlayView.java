package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;
import java.util.Locale;
import main.game.maze.dto.Score;

/**
 * View renderer for the high scores overlay.
 */
public final class GdxHighScoresOverlayView {

    public void render(RenderContext context, List<Score> highScoreRows) {
        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        OrthographicCamera hudCamera = context.hudCamera();

        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.03f, 0.04f, 0.08f, 0.18f);
        shapes.rect(0f, 0f, w, h);
        float panelW = Math.min(560f, w - 70f);
        float panelH = Math.min(460f, h - 90f);
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;
        shapes.setColor(0f, 0f, 0f, 0.35f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.getData().setScale(1.7f);
        font.draw(batch, "High Scores", panelX + 22f, panelY + panelH - 24f);
        font.getData().setScale(1.0f);

        if (highScoreRows.isEmpty()) {
            font.setColor(new Color(0.62f, 0.73f, 0.83f, 1f));
            font.draw(batch, "No saved scores yet", panelX + 22f, panelY + panelH - 68f);
        } else {
            float y = panelY + panelH - 66f;
            int max = Math.min(10, highScoreRows.size());
            for (int i = 0; i < max; i++) {
                Score row = highScoreRows.get(i);
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch,
                        String.format(Locale.ROOT, "%d. %s: %d", i + 1, row.getName(), row.getTheScore()),
                        panelX + 22f,
                        y);
                y -= 28f;
            }
        }

        font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
        font.draw(batch, "Press ESC to continue", panelX + 22f, panelY + 24f);
        batch.end();
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            OrthographicCamera hudCamera) {
    }
}
