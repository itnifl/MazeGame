package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** A big word in the middle of the screen (for example "Teleported"), fading with the alpha it is given. */
public final class GdxBannerView {

    /** Text size relative to the HUD font. */
    static final float SCALE = 3.4f;
    /** Height of the text on the screen as a share of the window height. */
    static final float HEIGHT_SHARE = 0.62f;

    private final GlyphLayout layout = new GlyphLayout();

    public void render(SpriteBatch batch, BitmapFont font, OrthographicCamera hudCamera, String text, float alpha) {
        if (text == null || text.isBlank() || alpha <= 0f) {
            return;
        }
        float oldScaleX = font.getData().scaleX;
        float oldScaleY = font.getData().scaleY;
        Color oldColor = font.getColor().cpy();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.getData().setScale(SCALE);
        layout.setText(font, text);
        float x = (hudCamera.viewportWidth - layout.width) * 0.5f;
        float y = hudCamera.viewportHeight * HEIGHT_SHARE + layout.height * 0.5f;
        font.setColor(0f, 0f, 0f, alpha * 0.7f);   // a dark shadow keeps it readable over anything
        font.draw(batch, text, x + 3f, y - 3f);
        font.setColor(0.72f, 0.92f, 1f, alpha);    // light blue, like the portal light
        font.draw(batch, text, x, y);
        batch.end();
        font.getData().setScale(oldScaleX, oldScaleY);
        font.setColor(oldColor);
    }
}
