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

    /** Text size of a title card relative to the HUD font. */
    static final float TITLE_SCALE = 6.2f;
    /** How far the outline reaches, in pixels at the title size. */
    private static final float OUTLINE = 5f;

    /**
     * A fighting-game title card: big, in gold with a thick dark red outline and a black shadow, slammed on to the
     * screen at the scale and place the shared {@link main.game.maze.game.status.TitleCard} gives.
     */
    public void renderTitleCard(SpriteBatch batch, BitmapFont font, OrthographicCamera hudCamera, String text,
                                main.game.maze.game.status.TitleCard.Frame frame) {
        if (text == null || text.isBlank() || frame.alpha() <= 0f) {
            return;
        }
        float oldScaleX = font.getData().scaleX;
        float oldScaleY = font.getData().scaleY;
        Color oldColor = font.getColor().cpy();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.getData().setScale(TITLE_SCALE * frame.scale());
        layout.setText(font, text);
        float x = (hudCamera.viewportWidth - layout.width) * 0.5f + frame.shakeX() * hudCamera.viewportHeight;
        float y = hudCamera.viewportHeight * 0.60f + layout.height * 0.5f + frame.shakeY() * hudCamera.viewportHeight;
        float a = frame.alpha();
        float reach = OUTLINE * frame.scale();
        font.setColor(0f, 0f, 0f, a * 0.75f);                   // the shadow, down and to the right
        font.draw(batch, text, x + reach * 2.2f, y - reach * 2.2f);
        font.setColor(0.45f, 0.02f, 0.02f, a);                  // the thick dark red outline: copies all round
        for (int i = 0; i < 16; i++) {
            double angle = i * Math.PI / 8;
            font.draw(batch, text, x + (float) Math.cos(angle) * reach, y + (float) Math.sin(angle) * reach);
        }
        font.setColor(1f, 0.82f, 0.12f, a);                     // the gold face
        font.draw(batch, text, x, y);
        font.setColor(1f, 0.97f, 0.7f, a * 0.55f);              // a light streak across the top of the letters
        font.draw(batch, text, x, y + reach * 0.5f);
        batch.end();
        font.getData().setScale(oldScaleX, oldScaleY);
        font.setColor(oldColor);
    }

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
