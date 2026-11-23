package main.game.maze.libgdx.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.function.IntPredicate;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.libgdx.view.GdxShopOverlayView;

/**
 * Input of the shop between levels (#164) in libGDX, opened from the win overlay; the counterpart of the JavaFX
 * {@code ShopController}. The model is the shared {@link ShopMenu}. Up/Down (or W/S) choose, Enter or Space buy, Esc or
 * Done closes; a click selects a row, a click on the selected row (or on Buy) buys it.
 */
public final class GdxShopOverlayController {

    private final ShopMenu menu;
    private boolean open;
    private GdxShopOverlayView.ShopLayout layout;

    public GdxShopOverlayController(ShopMenu menu) {
        this.menu = menu;
    }

    public ShopMenu menu() {
        return menu;
    }

    /** Opens the shop (first item selected, no message). */
    public void open() {
        menu.reset();
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    /** Where the view drew the rows and buttons this frame. */
    public void onRendered(GdxShopOverlayView.ShopLayout layout) {
        this.layout = layout;
    }

    /** One frame while the shop is open: keys, then the mouse. Esc closes the shop (not the win screen). */
    public void update(GdxModeInputController modeInputController, OrthographicCamera hudCamera) {
        if (!open) {
            return;
        }
        if (modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            close();
            return;
        }
        handleKeys(Gdx.input::isKeyJustPressed);
        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            handleClick(Gdx.input.getX(), hudCamera.viewportHeight - Gdx.input.getY());
        }
    }

    /** The keys pressed this frame ({@code justPressed} tells whether a libGDX key code was). */
    void handleKeys(IntPredicate justPressed) {
        if (justPressed.test(Input.Keys.UP) || justPressed.test(Input.Keys.W)) {
            menu.moveUp();
        }
        if (justPressed.test(Input.Keys.DOWN) || justPressed.test(Input.Keys.S)) {
            menu.moveDown();
        }
        if (justPressed.test(Input.Keys.ENTER) || justPressed.test(Input.Keys.SPACE)) {
            buy();
        }
    }

    /** A click in HUD space (y up). */
    void handleClick(float x, float y) {
        if (layout == null) {
            return;
        }
        if (layout.done().contains(x, y)) {
            close();
            return;
        }
        if (layout.buy().contains(x, y)) {
            buy();
            return;
        }
        for (int i = 0; i < layout.rows().size(); i++) {
            if (layout.rows().get(i).contains(x, y)) {
                if (menu.selectedIndex() == i) {
                    buy();   // a click on the selected row buys it
                } else {
                    menu.select(i);
                }
                return;
            }
        }
    }

    private void buy() {
        LevelShop.Result result = menu.buySelected();
        AudioEngine.get().play(ShopMenu.soundFor(result));
    }
}
