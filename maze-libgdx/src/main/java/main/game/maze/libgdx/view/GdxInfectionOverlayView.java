package main.game.maze.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * View renderer for the infection warning sign overlay.
 */
public final class GdxInfectionOverlayView {

    public void render(InfectionWarningContext context) {
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
}
