package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import java.util.List;
import java.util.Locale;
import main.game.maze.dto.Score;

/**
 * Renders game overlays such as high scores, end state panels, and warning signs.
 */
public final class GdxOverlayView {

    public void renderHighScoresOverlay(RenderContext context, List<Score> highScoreRows) {
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

    public WinButtons renderCenteredStateOverlay(CenteredOverlayContext context) {
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

        float panelW = Math.min(600f, w - 80f);
        float panelH = context.wonMode() ? 280f : 180f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        float saveBtnW = 160f;
        float saveBtnH = 36f;
        float backBtnW = 160f;
        float backBtnH = 36f;
        float btnGap = 16f;
        float btnRowY = panelY + 18f;
        float saveBtnX = panelX + (panelW - saveBtnW - backBtnW - btnGap) * 0.5f;
        float backBtnX = saveBtnX + saveBtnW + btnGap;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.88f);
        shapes.rect(panelX, panelY, panelW, panelH);
        if (context.wonMode()) {
            if (!context.winScoreSaved()) {
                shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
            } else {
                shapes.setColor(0.30f, 0.40f, 0.38f, 1f);
            }
            shapes.rect(saveBtnX, btnRowY, saveBtnW, saveBtnH);
            shapes.setColor(1f, 0.90f, 0.43f, 1f);
            shapes.rect(backBtnX, btnRowY, backBtnW, backBtnH);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(context.titleColor());
        font.getData().setScale(2.0f);
        font.draw(batch, context.title(), panelX + 34f, panelY + panelH - 30f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        font.draw(batch, context.subtitle(), panelX + 34f, panelY + panelH - 70f);

        if (context.wonMode()) {
            font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
            font.draw(batch, "Score: " + context.score(), panelX + 34f, panelY + panelH - 100f);
            if (context.winScoreSaved()) {
                font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
                font.draw(batch, context.winScoreStatus(), panelX + 34f, panelY + panelH - 130f);
            } else {
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, "Name: " + context.winNameInput() + "_", panelX + 34f, panelY + panelH - 130f);
                if (context.winScoreStatus() != null && !context.winScoreStatus().isBlank()) {
                    font.setColor(new Color(1f, 0.55f, 0.45f, 1f));
                    font.draw(batch, context.winScoreStatus(), panelX + 34f, panelY + panelH - 158f);
                }
            }
            font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
            glyphLayout.setText(font, "Save Score");
            font.draw(batch, "Save Score", saveBtnX + (saveBtnW - glyphLayout.width) * 0.5f, btnRowY + 24f);
            font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
            glyphLayout.setText(font, "Back to Menu");
            font.draw(batch, "Back to Menu", backBtnX + (backBtnW - glyphLayout.width) * 0.5f, btnRowY + 24f);
        }
        batch.end();

        return new WinButtons(saveBtnX, btnRowY, saveBtnW, saveBtnH, backBtnX, btnRowY, backBtnW, backBtnH);
    }

    public void renderInfectionWarningSign(InfectionWarningContext context) {
        ShapeRenderer shapes = context.shapes();
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();
        GlyphLayout glyphLayout = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;
        float triangleW = context.triangleWidth();
        float triangleH = context.triangleHeight();
        float cx = w * 0.5f;
        float cy = h * 0.5f;

        shapes.setProjectionMatrix(hudCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        float pulse = 0.5f + 0.5f * (float) Math.sin(context.animationClock() * context.pulseSpeed());
        int glowLayers = context.glowLayers();
        for (int i = glowLayers; i >= 1; i--) {
            float spread = 6f + i * 4f + pulse * 4f;
            float alpha = (0.10f + 0.10f * pulse) * (i / (float) glowLayers) * 0.55f;
            shapes.setColor(0.20f, 1.0f, 0.45f, alpha);
            shapes.triangle(
                    cx,
                    cy + triangleH * 0.5f + spread,
                    cx - triangleW * 0.5f - spread,
                    cy - triangleH * 0.5f - spread * 0.5f,
                    cx + triangleW * 0.5f + spread,
                    cy - triangleH * 0.5f - spread * 0.5f);
        }

        shapes.setColor(1f, 0.84f, 0.30f, 0.96f);
        shapes.triangle(
                cx,
                cy + triangleH * 0.5f,
                cx - triangleW * 0.5f,
                cy - triangleH * 0.5f,
                cx + triangleW * 0.5f,
                cy - triangleH * 0.5f);
        shapes.setColor(0.22f, 0.14f, 0.00f, 0.98f);
        shapes.rect(cx - 5f, cy - 22f, 10f, 44f);
        shapes.circle(cx, cy - 35f, 6f);
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.setColor(new Color(0.20f, 1.0f, 0.45f, 1f));
        font.getData().setScale(1.3f);
        glyphLayout.setText(font, context.warningText());
        float tx = cx - glyphLayout.width * 0.5f;
        float ty = cy - triangleH * 0.5f - 18f;
        font.draw(batch, context.warningText(), tx, ty);
        font.getData().setScale(1.0f);
        batch.end();
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            OrthographicCamera hudCamera) {
    }

    public record CenteredOverlayContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            String title,
            String subtitle,
            Texture backdrop,
            Color titleColor,
            boolean wonMode,
            boolean winScoreSaved,
            String winScoreStatus,
            String winNameInput,
            int score) {
    }

    public record InfectionWarningContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            float animationClock,
            float pulseSpeed,
            float triangleWidth,
            float triangleHeight,
            int glowLayers,
            String warningText) {
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
