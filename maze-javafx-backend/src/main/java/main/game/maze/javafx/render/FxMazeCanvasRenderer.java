package main.game.maze.javafx.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import main.game.maze.App;
import main.game.maze.common.graphics.WallLook;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.generated.WallRegistry;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Renders the static maze wall canvas from a list of {@link Vector2D} wall segments.
 * Extracted from GameController to keep all wall-drawing logic in one place.
 */
public final class FxMazeCanvasRenderer {

    private static final Logger LOGGER = Logger.getLogger(FxMazeCanvasRenderer.class.getName());

    private final MazeVisualStyleConfig visualStyle;
    private final Supplier<String> difficultyNameSupplier;
    private final Map<String, Image> wallImageCache = new HashMap<>();
    private final Map<String, Image> bgImageCache   = new HashMap<>();
    private final Map<WallLook, Image> lookImageCache = new EnumMap<>(WallLook.class);
    /** Material id of each wall, read on every drawing; null draws every wall the same (the classic look). */
    private volatile Supplier<Map<Vector2D, String>> wallMaterialLook;

    public FxMazeCanvasRenderer(MazeVisualStyleConfig visualStyle, Supplier<String> difficultyNameSupplier) {
        this.visualStyle = visualStyle;
        this.difficultyNameSupplier = difficultyNameSupplier;
    }

    /** Draws each wall with the sprite of its material (levels that show their wall materials); null: all alike. */
    public void setWallMaterialLook(Supplier<Map<Vector2D, String>> materialIds) {
        this.wallMaterialLook = materialIds;
    }

    public Canvas drawCanvas(List<Vector2D> vectors) {
        Canvas canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        renderTo(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight(), vectors);
        return canvas;
    }

    /** Clears {@code canvas} and redraws walls from {@code vectors} in-place. */
    public void redrawInPlace(Canvas canvas, List<Vector2D> vectors) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderTo(gc, canvas.getWidth(), canvas.getHeight(), vectors);
    }

    private void renderTo(GraphicsContext gc, double width, double height, List<Vector2D> vectors) {
        drawBackground(gc, width, height);

        double wallWidth  = StageConstants.WallThicknessPx;
        double wallLength = StageConstants.WallSegmentLengthPx;

        String wallTypeId = visualStyle.wallTypeIdForDifficultyName(difficultyNameSupplier.get());
        WallRegistry.WallDefinition wallType = WallRegistry.get(wallTypeId);
        if (wallType == null) {
            wallType = WallRegistry.get(MazeVisualStyleConfig.DEFAULT.wallTypeIdForDifficultyName(difficultyNameSupplier.get()));
        }

        Image classicImage = getOrLoadImage(wallType);
        var lookSupplier = wallMaterialLook;
        Map<Vector2D, String> materialIds = lookSupplier != null ? lookSupplier.get() : Map.of();

        for (Vector2D vector : vectors) {
            Image wallImage = classicImage;
            WallLook look = WallLook.of(materialIds.get(vector));
            if (look != null) {
                Image materialImage = lookImage(look);
                if (materialImage != null) {
                    wallImage = materialImage;
                }
            }
            double startX = vector.getStart().getX();
            double startY = vector.getStart().getY();
            double endX   = vector.getEnd().getX();
            double endY   = vector.getEnd().getY();

            boolean isHorizontal = Math.abs(endY - startY) < 0.001;

            if (wallImage == null) {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(wallWidth);
                gc.strokeLine(startX, startY, endX, endY);
            } else if (!isHorizontal) {
                double drawX = startX - (wallWidth / 2.0);
                double drawY = Math.min(startY, endY);
                gc.drawImage(wallImage, drawX, drawY, wallWidth, wallLength);
            } else {
                double minX    = Math.min(startX, endX);
                double centerX = minX + (Math.abs(endX - startX) / 2.0);
                double centerY = startY;
                gc.save();
                gc.translate(centerX, centerY);
                gc.rotate(90);
                gc.drawImage(wallImage, -wallWidth / 2.0, -wallLength / 2.0, wallWidth, wallLength);
                gc.restore();
            }
        }
    }

    private Image lookImage(WallLook look) {
        return lookImageCache.computeIfAbsent(look, key -> {
            var url = FxMazeCanvasRenderer.class.getResource(key.imagePath());
            if (url == null) {
                LOGGER.warning("Could not find wall image: " + key.imagePath());
                return null;
            }
            return new Image(url.toExternalForm());
        });
    }

    private void drawBackground(GraphicsContext gc, double width, double height) {
        String path = visualStyle.backgroundImageForDifficultyName(difficultyNameSupplier.get());
        Image bg = bgImageCache.computeIfAbsent(path, p -> {
            try {
                var url = FxMazeCanvasRenderer.class.getResource(p);
                if (url != null) return new Image(url.toExternalForm());
                LOGGER.warning("Background image not found: " + p);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to load background image: " + p, e);
            }
            return null;
        });
        if (bg != null && !bg.isError() && bg.getWidth() > 0 && bg.getHeight() > 0) {
            gc.setFill(new ImagePattern(bg, 0, 0, bg.getWidth(), bg.getHeight(), false));
            gc.fillRect(0, 0, width, height);
        }
    }

    private Image getOrLoadImage(WallRegistry.WallDefinition def) {
        if (def == null || def.baseImage == null) {
            return null;
        }
        return wallImageCache.computeIfAbsent(def.id, id -> {
            try {
                var url = FxMazeCanvasRenderer.class.getResource(def.baseImage);
                if (url != null) {
                    return new Image(url.toExternalForm());
                }
                LOGGER.warning("Could not find wall image: " + def.baseImage);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to load wall image: " + def.baseImage, e);
            }
            return null;
        });
    }
}
