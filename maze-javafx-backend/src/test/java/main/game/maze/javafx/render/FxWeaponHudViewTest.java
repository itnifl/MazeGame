package main.game.maze.javafx.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.WeaponHud;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX weapons HUD (top-left)")
class FxWeaponHudViewTest {

    private static final PickupCatalog CATALOG = PickupCatalogLoader.loadDefault();

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    private static void onFx(Runnable action) throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
        if (failure.get() instanceof AssertionError ae) {
            throw ae;
        }
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }
    }

    @Test
    @DisplayName("one framed icon per weapon in stock, the one in hand in gold, then the lives; Tab moves the frame")
    void showsWeaponsAndLives() throws Exception {
        onFx(() -> {
            AnchorPane root = new AnchorPane();
            FxWeaponHudView view = new FxWeaponHudView(() -> root);
            PlayerArsenal arsenal = new PlayerArsenal(3);
            arsenal.add(Weapon.SLEDGEHAMMER, 5);

            view.show(WeaponHud.model(arsenal, CATALOG, 2));
            HBox panel = view.panel();
            assertNotNull(panel);
            assertSame(root, panel.getParent());
            assertEquals(6.0, AnchorPane.getLeftAnchor(panel), "top-left corner");
            assertEquals(3, panel.getChildren().size(), "bombs, sledgehammer, lives");
            assertEquals(FxWeaponHudView.SELECTED_STYLE, panel.getChildren().get(0).getStyle(), "bombs in hand");
            assertEquals(FxWeaponHudView.SLOT_STYLE, panel.getChildren().get(1).getStyle());
            assertTrue(labels(panel.getChildren().get(2)).contains("x2"), "two lives");

            arsenal.cycle();
            view.show(WeaponHud.model(arsenal, CATALOG, 2));
            assertEquals(FxWeaponHudView.SLOT_STYLE, panel.getChildren().get(0).getStyle());
            assertEquals(FxWeaponHudView.SELECTED_STYLE, panel.getChildren().get(1).getStyle(), "Tab moved the frame");
            assertTrue(labels(panel.getChildren().get(1)).contains("5"));

            Node before = panel.getChildren().get(0);
            view.show(WeaponHud.model(arsenal, CATALOG, 2));
            assertSame(before, panel.getChildren().get(0), "unchanged model: not rebuilt");

            view.clear();
            assertTrue(root.getChildren().isEmpty());
        });
    }

    private static String labels(Node node) {
        StringBuilder text = new StringBuilder();
        if (node instanceof Label label) {
            text.append(label.getText()).append(' ');
        }
        if (node instanceof javafx.scene.Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                text.append(labels(child));
            }
        }
        return text.toString();
    }
}
