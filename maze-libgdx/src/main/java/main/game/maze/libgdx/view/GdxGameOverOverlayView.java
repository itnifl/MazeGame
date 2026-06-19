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
    private static final float LINE_SPACING  = 20f;

    public GameOverButtons render(RenderContext context) {
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
        float panelH = 210f + breakdownLines * LINE_SPACING;
        float panelW = Math.min(600f, w - 80f);
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        float restartBtnW = 160f;
        float restartBtnH = 36f;
        float backBtnW = 160f;
        float backBtnH = 36f;
        float btnGap = 16f;
        float btnRowY = panelY + 18f;
        float restartBtnX = panelX + (panelW - restartBtnW - backBtnW - btnGap) * 0.5f;
        float backBtnX = restartBtnX + restartBtnW + btnGap;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.88f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        shapes.rect(restartBtnX, btnRowY, restartBtnW, restartBtnH);
        shapes.setColor(1f, 0.90f, 0.43f, 1f);
        shapes.rect(backBtnX, btnRowY, backBtnW, backBtnH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(Color.RED);
        font.getData().setScale(2.0f);
        glyphLayout.setText(font, "GAME OVER");
        font.draw(batch, "GAME OVER", panelX + (panelW - glyphLayout.width) * 0.5f, panelY + panelH - 20f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        String message = "Press ESC to return to start menu";
        glyphLayout.setText(font, message);
        font.draw(batch, message, panelX + (panelW - glyphLayout.width) * 0.5f, panelY + panelH - 62f);

        font.setColor(COLOR_SCORE);
        String scoreText = "Score: " + context.score();
        glyphLayout.setText(font, scoreText);
        font.draw(batch, scoreText, panelX + (panelW - glyphLayout.width) * 0.5f, panelY + panelH - 88f);

        drawBreakdown(batch, font, glyphLayout, context.scoreBreakdown(), panelX, panelW, panelY + panelH - 110f);

        font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
        glyphLayout.setText(font, "Restart");
        font.draw(batch, "Restart", restartBtnX + (restartBtnW - glyphLayout.width) * 0.5f, btnRowY + 24f);
        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        glyphLayout.setText(font, "Back to Menu");
        font.draw(batch, "Back to Menu", backBtnX + (backBtnW - glyphLayout.width) * 0.5f, btnRowY + 24f);
        batch.end();

        return new GameOverButtons(
            restartBtnX, btnRowY, restartBtnW, restartBtnH,
            backBtnX, btnRowY, backBtnW, backBtnH);
    }

    private static int countBreakdownLines(ScoreBreakdown bd) {
        if (bd == null) return 0;
        int n = 0;
        if (bd.deathPenalty()   > 0) n++;
        if (bd.damagePenalty()  > 0) n++;
        if (bd.dynamicPenalty() > 0) n++;
        return n;
    }

    private static void drawBreakdown(
            SpriteBatch batch,
            BitmapFont font,
            GlyphLayout glyphLayout,
            ScoreBreakdown bd,
            float panelX,
            float panelW,
            float startY) {
        if (bd == null) return;
        float y = startY;
        font.setColor(COLOR_PENALTY);
        if (bd.deathPenalty() > 0) {
            String text = "- Death penalty: -" + bd.deathPenalty();
            glyphLayout.setText(font, text);
            font.draw(batch, text, panelX + (panelW - glyphLayout.width) * 0.5f, y);
            y -= LINE_SPACING;
        }
        if (bd.damagePenalty() > 0) {
            String text = "- Damage penalty: -" + bd.damagePenalty();
            glyphLayout.setText(font, text);
            font.draw(batch, text, panelX + (panelW - glyphLayout.width) * 0.5f, y);
            y -= LINE_SPACING;
        }
        if (bd.dynamicPenalty() > 0) {
            String text = "- Path hint penalty: -" + bd.dynamicPenalty();
            glyphLayout.setText(font, text);
            font.draw(batch, text, panelX + (panelW - glyphLayout.width) * 0.5f, y);
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

    public record GameOverButtons(
            float restartX,
            float restartY,
            float restartW,
            float restartH,
            float backX,
            float backY,
            float backW,
            float backH) {
    }
}
