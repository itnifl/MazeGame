package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * View renderer for the game over overlay.
 */
public final class GdxGameOverOverlayView {

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

        float panelW = Math.min(600f, w - 80f);
        float panelH = 180f;
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
        font.draw(batch, "GAME OVER", panelX + 34f, panelY + panelH - 30f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        font.draw(batch, "Press ESC to return to start menu", panelX + 34f, panelY + panelH - 70f);
        font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
        glyphLayout.setText(font, "Score: " + context.score());
        font.draw(batch, "Score: " + context.score(), panelX + 34f, panelY + panelH - 100f);
        batch.end();
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Texture backdrop,
            int score) {
    }
}


