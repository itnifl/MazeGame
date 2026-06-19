package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;

/**
 * View renderer for the game over overlay.
 */
public final class GdxGameOverOverlayView {

    private static final Color COLOR_PENALTY = Color.RED;
    private static final Color COLOR_SCORE   = new Color(1f, 0.90f, 0.43f, 1f);   // gold

    public void render(RenderContext context) {
        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        GlyphLayout glyphLayout = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        batch.setProjectionMatrix(hudCamera.combined);
        if (context.backdrop() != null) {
            batch.begin();
            batch.setColor(Color.WHITE);
            batch.draw(context.backdrop(), 0f, 0f, w, h);
            batch.end();
        }

        int breakdownLines = countBreakdownLines(context.scoreBreakdown());
        float panelH = 140f + breakdownLines * 20f;
        float panelW = Math.min(600f, w - 80f);
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.88f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(Color.RED);
        font.getData().setScale(2.0f);
        font.draw(batch, "GAME OVER", panelX + 34f, panelY + panelH - 20f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        font.draw(batch, "Press ESC to return to start menu", panelX + 34f, panelY + panelH - 62f);

        font.setColor(COLOR_SCORE);
        glyphLayout.setText(font, "Score: " + context.score());
        font.draw(batch, "Score: " + context.score(), panelX + 34f, panelY + panelH - 88f);

        drawBreakdown(batch, font, context.scoreBreakdown(), panelX + 34f, panelY + panelH - 110f);
        batch.end();
    }

    private static int countBreakdownLines(ScoreBreakdown bd) {
        if (bd == null) return 0;
        int n = 0;
        if (bd.deathPenalty()   > 0) n++;
        if (bd.damagePenalty()  > 0) n++;
        if (bd.dynamicPenalty() > 0) n++;
        return n;
    }

    private static void drawBreakdown(SpriteBatch batch, BitmapFont font,
            ScoreBreakdown bd, float x, float startY) {
        if (bd == null) return;
        float y = startY;
        font.setColor(COLOR_PENALTY);
        if (bd.deathPenalty() > 0) {
            font.draw(batch, "- Death penalty: -" + bd.deathPenalty(), x, y);
            y -= 20f;
        }
        if (bd.damagePenalty() > 0) {
            font.draw(batch, "- Damage penalty: -" + bd.damagePenalty(), x, y);
            y -= 20f;
        }
        if (bd.dynamicPenalty() > 0) {
            font.draw(batch, "- Path hint penalty: -" + bd.dynamicPenalty(), x, y);
        }
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Texture backdrop,
            int score,
            ScoreBreakdown scoreBreakdown) {
    }
}
