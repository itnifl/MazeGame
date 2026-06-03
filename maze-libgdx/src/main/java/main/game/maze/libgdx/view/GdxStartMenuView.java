package main.game.maze.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;
import main.game.maze.libgdx.view.layout.MenuLayout;

/**
 * Renders the start menu and returns the per-frame layout snapshot for input hit-testing.
 */
public final class GdxStartMenuView {

    public MenuLayout render(RenderContext context) {
        SpriteBatch batch = context.batch();
        ShapeRenderer shapes = context.shapes();
        BitmapFont font = context.font();
        GlyphLayout glyphLayout = context.glyphLayout();
        OrthographicCamera hudCamera = context.hudCamera();

        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float panelW = Math.min(760f, w - 80f);
        float panelH = 420f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f - 20f;

        float titleY = h - 60f;

        float comboW = Math.min(430f, panelW - 120f);
        float comboH = 52f;
        float comboX = panelX + (panelW - comboW) * 0.5f;
        float comboY = panelY + panelH - 132f;

        float buttonW = 250f;
        float buttonH = 52f;
        float buttonX = panelX + (panelW - buttonW) * 0.5f;
        float buttonY = comboY - 78f;

        float highScoresButtonW = buttonW;
        float highScoresButtonH = 40f;
        float highScoresButtonX = buttonX;
        float highScoresButtonY = buttonY - highScoresButtonH - 12f;

        MenuLayout menuLayout = new MenuLayout(
                comboX,
                comboY,
                comboW,
                comboH,
                buttonX,
                buttonY,
                buttonW,
                buttonH,
                highScoresButtonX,
                highScoresButtonY,
                highScoresButtonW,
                highScoresButtonH);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.11f, 0.05f, 0.18f, 1f);
        shapes.rect(0f, h * 0.45f, w, h * 0.55f);
        shapes.setColor(0.05f, 0.10f, 0.18f, 1f);
        shapes.rect(0f, h * 0.18f, w, h * 0.27f);
        shapes.setColor(0.07f, 0.16f, 0.12f, 1f);
        shapes.rect(0f, 0f, w, h * 0.18f);

        shapes.setColor(0f, 0f, 0f, 0.44f);
        shapes.rect(panelX, panelY, panelW, panelH);

        shapes.setColor(0.06f, 0.13f, 0.22f, 1f);
        shapes.rect(comboX, comboY, comboW, comboH);
        shapes.setColor(1f, 0.90f, 0.43f, 1f);
        shapes.rect(buttonX, buttonY, buttonW, buttonH);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        shapes.rect(highScoresButtonX, highScoresButtonY, highScoresButtonW, highScoresButtonH);
        if (context.startMenuDropdownOpen() && !context.difficultyNames().isEmpty()) {
            float optH = comboH;
            shapes.setColor(0.03f, 0.09f, 0.15f, 1f);
            shapes.rect(comboX, comboY - context.difficultyNames().size() * optH, comboW, context.difficultyNames().size() * optH);
            for (int i = 0; i < context.difficultyNames().size(); i++) {
                float oy = comboY - (i + 1) * optH;
                if (i == context.selectedDifficultyIndex()) {
                    shapes.setColor(0.08f, 0.20f, 0.34f, 1f);
                } else {
                    shapes.setColor(0.04f, 0.11f, 0.19f, 1f);
                }
                shapes.rect(comboX, oy, comboW, optH);
            }
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.rect(comboX, comboY, comboW, comboH);
        if (context.startMenuDropdownOpen() && !context.difficultyNames().isEmpty()) {
            float optH = comboH;
            for (int i = 0; i < context.difficultyNames().size(); i++) {
                float oy = comboY - (i + 1) * optH;
                shapes.rect(comboX, oy, comboW, optH);
            }
        }
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.getData().setScale(2.8f);
        glyphLayout.setText(font, "Maze Game");
        float iconSize = context.menuIconTexture() != null ? 42f : 0f;
        float iconGap = context.menuIconTexture() != null ? 16f : 0f;
        float blockWidth = glyphLayout.width + iconSize + iconGap;
        float blockX = panelX + (panelW - blockWidth) * 0.5f;
        if (context.menuIconTexture() != null) {
            batch.draw(context.menuIconTexture(), blockX, titleY - 35f, iconSize, iconSize);
        }
        font.draw(batch, "Maze Game", blockX + iconSize + iconGap, titleY);
        font.getData().setScale(1.0f);

        font.setColor(Color.WHITE);
        font.getData().setScale(1.55f);
        glyphLayout.setText(font, "Select Difficulty");
        font.draw(batch, "Select Difficulty", panelX + (panelW - glyphLayout.width) * 0.5f, panelY + panelH - 52f);
        font.getData().setScale(1.0f);

        String selectedText = context.selectedDifficultyIndex() >= 0
                && context.selectedDifficultyIndex() < context.difficultyNames().size()
                ? context.difficultyNames().get(context.selectedDifficultyIndex())
                : "Easy";
        font.setColor(new Color(0.95f, 0.98f, 1f, 1f));
        font.draw(batch, selectedText, comboX + 18f, comboY + 33f);
        font.setColor(new Color(0.95f, 0.98f, 1f, 1f));
        font.draw(batch, context.startMenuDropdownOpen() ? "^" : "v", comboX + comboW - 20f, comboY + 33f);

        if (context.startMenuDropdownOpen() && !context.difficultyNames().isEmpty()) {
            float optH = comboH;
            for (int i = 0; i < context.difficultyNames().size(); i++) {
                float oy = comboY - (i + 1) * optH;
                font.setColor(i == context.selectedDifficultyIndex()
                        ? new Color(0.95f, 1f, 0.98f, 1f)
                        : new Color(0.84f, 0.94f, 0.98f, 1f));
                font.draw(batch, context.difficultyNames().get(i), comboX + 18f, oy + 33f);
            }
        }

        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        font.draw(batch, context.pausedFromGame() ? "Restart Mission" : "Start Mission", buttonX + 56f, buttonY + 33f);

        font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
        glyphLayout.setText(font, "High Scores");
        font.draw(batch, "High Scores", highScoresButtonX + (highScoresButtonW - glyphLayout.width) * 0.5f, highScoresButtonY + 26f);

        float hintY = highScoresButtonY - 22f;
        font.setColor(new Color(0.93f, 0.97f, 1f, 1f));
        font.draw(batch, "Arrow keys to move, P path hint, O spanning tree, H high score, ESC", panelX + 38f, hintY);
        font.draw(batch, context.pausedFromGame() ? "return to game" : "restart menu", panelX + 38f, hintY - 24f);

        font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
        font.draw(batch, "Collect the heart, avoid enemies, and maximize your score", panelX + 92f, panelY - 38f);

        if (context.selectedDifficultySummary() != null && !context.selectedDifficultySummary().isBlank()) {
            font.setColor(new Color(0.80f, 1f, 0.94f, 0.95f));
            font.draw(batch, context.selectedDifficultySummary(), panelX + 20f, panelY + 26f);
        }

        if (context.statusMessage() != null && !context.statusMessage().isBlank()) {
            font.setColor(new Color(1f, 0.35f, 0.30f, 1f));
            font.draw(batch, context.statusMessage(), panelX + 16f, panelY + 44f);
        }
        if (context.loadingPending()) {
            font.setColor(Color.GOLD);
            font.getData().setScale(2.0f);
            glyphLayout.setText(font, "Loading ...");
            float lx = (w - glyphLayout.width) * 0.5f;
            float ly = titleY - 70f;
            font.draw(batch, "Loading ...", lx, ly);
            font.getData().setScale(1.0f);
        }
        batch.end();

        if (context.startMenuDropdownOpen() && !context.difficultyNames().isEmpty()) {
            drawStartMenuDropdownOverlay(context, menuLayout);
        }

        return menuLayout;
    }

    private void drawStartMenuDropdownOverlay(RenderContext context, MenuLayout layout) {
        float optH = layout.comboH();
        float optionsHeight = context.difficultyNames().size() * optH;

        ShapeRenderer shapes = context.shapes();
        SpriteBatch batch = context.batch();
        BitmapFont font = context.font();

        shapes.setProjectionMatrix(context.hudCamera().combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.03f, 0.09f, 0.15f, 1f);
        shapes.rect(layout.comboX(), layout.comboY() - optionsHeight, layout.comboW(), optionsHeight);
        for (int i = 0; i < context.difficultyNames().size(); i++) {
            float oy = layout.comboY() - (i + 1) * optH;
            if (i == context.selectedDifficultyIndex()) {
                shapes.setColor(0.08f, 0.20f, 0.34f, 1f);
            } else {
                shapes.setColor(0.04f, 0.11f, 0.19f, 1f);
            }
            shapes.rect(layout.comboX(), oy, layout.comboW(), optH);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        for (int i = 0; i < context.difficultyNames().size(); i++) {
            float oy = layout.comboY() - (i + 1) * optH;
            shapes.rect(layout.comboX(), oy, layout.comboW(), optH);
        }
        shapes.end();

        batch.setProjectionMatrix(context.hudCamera().combined);
        batch.begin();
        for (int i = 0; i < context.difficultyNames().size(); i++) {
            float oy = layout.comboY() - (i + 1) * optH;
            font.setColor(i == context.selectedDifficultyIndex()
                    ? new Color(0.95f, 1f, 0.98f, 1f)
                    : new Color(0.84f, 0.94f, 0.98f, 1f));
            font.draw(batch, context.difficultyNames().get(i), layout.comboX() + 18f, oy + 33f);
        }
        batch.end();
    }

    public record RenderContext(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout,
            OrthographicCamera hudCamera,
            Texture menuIconTexture,
            List<String> difficultyNames,
            int selectedDifficultyIndex,
            boolean startMenuDropdownOpen,
            boolean pausedFromGame,
            boolean loadingPending,
            String selectedDifficultySummary,
            String statusMessage) {
    }
}
