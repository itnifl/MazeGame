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
 * View renderer for the win overlay.
 */
public final class GdxWinOverlayView {

    private static final Color COLOR_PENALTY   = new Color(1f, 0.188f, 0.145f, 1f); // #ff3025, matching JavaFX
    private static final Color COLOR_WIN_BONUS = new Color(0.56f, 1.0f, 0.88f, 1f); // cyan
    private static final Color COLOR_SCORE     = new Color(1f, 0.90f, 0.43f, 1f);   // gold

    private static final float LINE_SPACING = 16f;

    public WinButtons render(WinOverlayContext context) {
        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        GlyphLayout gl = context.glyphLayout();
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
        float panelW = Math.min(600f, w - 80f);
        float panelH = 290f + breakdownLines * LINE_SPACING;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        float saveBtnW = 160f;
        float saveBtnH = 36f;
        float backBtnW = 160f;
        float backBtnH = 36f;
        float btnGap   = 16f;
        float btnRowY  = panelY + 24f;
        float saveBtnX = panelX + (panelW - saveBtnW - backBtnW - btnGap) * 0.5f;
        float backBtnX = saveBtnX + saveBtnW + btnGap;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.88f);
        shapes.rect(panelX, panelY, panelW, panelH);
        if (!context.winScoreSaved()) {
            shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        } else {
            shapes.setColor(0.30f, 0.40f, 0.38f, 1f);
        }
        shapes.rect(saveBtnX, btnRowY, saveBtnW, saveBtnH);
        shapes.setColor(1f, 0.90f, 0.43f, 1f);
        shapes.rect(backBtnX, btnRowY, backBtnW, backBtnH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();

        // "YOU WIN" header — centred
        font.setColor(Color.GREEN);
        font.getData().setScale(2.0f);
        gl.setText(font, "YOU WIN");
        font.draw(batch, "YOU WIN", panelX + (panelW - gl.width) * 0.5f, panelY + panelH - 20f);
        font.getData().setScale(1.0f);

        // Description — centred
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        String desc = "Type your name then click Save Score, or Back to Menu.";
        gl.setText(font, desc);
        font.draw(batch, desc, panelX + (panelW - gl.width) * 0.5f, panelY + panelH - 62f);

        // "Your score" label — centred
        font.setColor(COLOR_SCORE);
        gl.setText(font, "Your score");
        font.draw(batch, "Your score", panelX + (panelW - gl.width) * 0.5f, panelY + panelH - 78f);

        // Score value at 1.5× — centred
        font.getData().setScale(1.5f);
        String scoreText = String.valueOf(context.score());
        gl.setText(font, scoreText);
        font.draw(batch, scoreText, panelX + (panelW - gl.width) * 0.5f, panelY + panelH - 96f);
        font.getData().setScale(1.0f);

        // Breakdown lines (penalties in red, win bonus in cyan) — centred
        float breakdownEndY = drawBreakdown(
                batch, font, gl, context.scoreBreakdown(), panelX, panelW, panelY + panelH - 126f);

        // Name input / status — centred
        float nameY = breakdownEndY - 32f;
        if (context.winScoreSaved()) {
            String statusText = context.winScoreStatus();
            gl.setText(font, statusText);
            font.setColor(COLOR_WIN_BONUS);
            font.draw(batch, statusText, panelX + (panelW - gl.width) * 0.5f, nameY);
        } else {
            String nameText = "Name: " + context.winNameInput() + "_";
            gl.setText(font, nameText);
            font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
            font.draw(batch, nameText, panelX + (panelW - gl.width) * 0.5f, nameY);
            if (context.winScoreStatus() != null && !context.winScoreStatus().isBlank()) {
                String statusText = context.winScoreStatus();
                gl.setText(font, statusText);
                font.setColor(COLOR_PENALTY);
                font.draw(batch, statusText, panelX + (panelW - gl.width) * 0.5f, nameY - LINE_SPACING);
            }
        }

        // Button labels
        font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
        gl.setText(font, "Save Score");
        font.draw(batch, "Save Score", saveBtnX + (saveBtnW - gl.width) * 0.5f, btnRowY + 24f);
        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        gl.setText(font, "Back to Menu");
        font.draw(batch, "Back to Menu", backBtnX + (backBtnW - gl.width) * 0.5f, btnRowY + 24f);
        batch.end();

        return new WinButtons(saveBtnX, btnRowY, saveBtnW, saveBtnH, backBtnX, btnRowY, backBtnW, backBtnH);
    }

    private static int countBreakdownLines(ScoreBreakdown bd) {
        if (bd == null) return 0;
        int n = 0;
        if (bd.damagePenalty()  > 0) n++;
        if (bd.dynamicPenalty() > 0) n++;
        if (bd.winBonus()       > 0) n++;
        return n;
    }

    /** Draws centered breakdown lines; returns the Y below the last line drawn (or startY if nothing drawn). */
    private static float drawBreakdown(SpriteBatch batch, BitmapFont font, GlyphLayout gl,
            ScoreBreakdown bd, float panelX, float panelW, float startY) {
        if (bd == null) return startY;
        float y = startY;
        if (bd.damagePenalty() > 0) {
            font.setColor(COLOR_PENALTY);
            String text = "- Damage penalty: -" + bd.damagePenalty();
            gl.setText(font, text);
            font.draw(batch, text, panelX + (panelW - gl.width) * 0.5f, y);
            y -= LINE_SPACING;
        }
        if (bd.dynamicPenalty() > 0) {
            font.setColor(COLOR_PENALTY);
            String text = "- Path hint penalty: -" + bd.dynamicPenalty();
            gl.setText(font, text);
            font.draw(batch, text, panelX + (panelW - gl.width) * 0.5f, y);
            y -= LINE_SPACING;
        }
        if (bd.winBonus() > 0) {
            font.setColor(COLOR_WIN_BONUS);
            String text = "+ Win bonus: +" + bd.winBonus();
            gl.setText(font, text);
            font.draw(batch, text, panelX + (panelW - gl.width) * 0.5f, y);
            y -= LINE_SPACING;
        }
        return y;
    }

    public record WinOverlayContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Texture backdrop,
            boolean winScoreSaved,
            String winScoreStatus,
            String winNameInput,
            int score,
            ScoreBreakdown scoreBreakdown) {
    }

    public record WinButtons(
            float saveX,
            float saveY,
            float saveW,
            float saveH,
            float backX,
            float backY,
            float backW,
            float backH) {
    }
}
