package main.game.maze.libgdx.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.SpriteAnimationUtil;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.constants.ImageResourceConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.audio.GameAudioDirector;
import main.game.maze.libgdx.GdxGameContext;
import main.game.maze.libgdx.audio.GdxGameAudioCoordinator;
import main.game.maze.libgdx.helper.DifficultyBoardConfig;
import main.game.maze.libgdx.helper.GdxVisualStyleSupport;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.libgdx.view.GdxStartMenuView;
import main.game.maze.libgdx.view.layout.MenuLayout;
import main.game.maze.service.DifficultyPresentationSupport;
import main.game.maze.service.DifficultyService;

/**
 * Dedicated start menu screen introduced during gradual screen extraction.
 */
public final class MenuScreenController extends AbstractGdxScreenController {

    private static final long START_MENU_LOADING_DELAY_NANOS = 1_000_000_000L;

    private final DifficultyService difficultyService = new DifficultyService();
    private final MazeVisualStyleConfig visualStyle = GdxVisualStyleSupport.loadOrDefault();
    private final GdxStartMenuView startMenuView = new GdxStartMenuView();
    private final GdxStartMenuInputController startMenuInputController = new GdxStartMenuInputController();
    private final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final Consumer<Difficulty> startGameAction;
    private final Runnable openLegacyMenuAction;
    private final GdxGameAudioCoordinator audioCoordinator =
            new GdxGameAudioCoordinator(new GameAudioDirector(AudioEngine::get), visualStyle);

    private final List<Difficulty> difficulties = new ArrayList<>();
    private int selectedDifficultyIndex;
    private boolean startMenuDropdownOpen;
    private boolean loadingPending;
    private long loadingStartedAtNanos;
    private String loadingStatusText = "";

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private OrthographicCamera hudCamera;
    private MenuLayout menuLayout = MenuLayout.zero();
    private Texture menuIconTexture;

    public MenuScreenController(GdxGameContext context, Consumer<Difficulty> startGameAction, Runnable openLegacyMenuAction) {
        super(context);
        this.startGameAction = startGameAction;
        this.openLegacyMenuAction = openLegacyMenuAction;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();
        hudCamera = new OrthographicCamera();
        font.setColor(Color.WHITE);

        menuIconTexture = context().assets().getTexture(visualStyle.menuIconImagePath());

        difficulties.clear();
        difficulties.addAll(difficultyService.list());
        Difficulty current = difficultyService.getCurrent();
        selectedDifficultyIndex = 0;
        if (current != null) {
            for (int i = 0; i < difficulties.size(); i++) {
                if (displayName(difficulties.get(i)).equals(displayName(current))) {
                    selectedDifficultyIndex = i;
                    break;
                }
            }
        }

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        audioCoordinator.switchToMenuMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);

        handleInput();

        if (loadingPending && TimeUtils.timeSinceNanos(loadingStartedAtNanos) >= START_MENU_LOADING_DELAY_NANOS) {
            loadingStatusText = String.format(Locale.ROOT, "Loading %.0f%%", context().assets().loadingProgress() * 100f);
            if (context().assets().updateLoading()) {
                loadingPending = false;
                loadingStatusText = "";
                if (!difficulties.isEmpty()) {
                    Difficulty selected = difficulties.get(Math.max(0, Math.min(selectedDifficultyIndex, difficulties.size() - 1)));
                    difficultyService.setCurrent(selected);
                    startGameAction.accept(selected);
                }
            }
        }

        draw();
    }

    @Override
    public void resize(int width, int height) {
        if (hudCamera != null) {
            hudCamera.setToOrtho(false, Math.max(1, width), Math.max(1, height));
            hudCamera.update();
        }
    }

    @Override
    public void dispose() {
        audioCoordinator.stopAll();
        if (batch != null) batch.dispose();
        if (shapes != null) shapes.dispose();
        if (font != null) font.dispose();
    }

    private void handleInput() {
        if (loadingPending) {
            return;
        }

        handleMouseInput();

        var keyboardResult = startMenuInputController.handleKeyboard(
                Gdx.input.isKeyPressed(Input.Keys.UP),
                Gdx.input.isKeyPressed(Input.Keys.DOWN),
                Gdx.input.isKeyPressed(Input.Keys.ENTER),
                selectedDifficultyIndex,
                difficulties.size());
        selectedDifficultyIndex = keyboardResult.selectedDifficultyIndex();
        if (keyboardResult.playSelectSound()) {
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (keyboardResult.startRequested()) {
            beginStartLoading();
        }
    }

    private void handleMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }

        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();
        var mouseResult = startMenuInputController.handleLeftClick(
                mx,
                my,
                new GdxStartMenuInputController.MenuLayoutValues(
                        menuLayout.comboX(),
                        menuLayout.comboY(),
                        menuLayout.comboW(),
                        menuLayout.comboH(),
                        menuLayout.buttonX(),
                        menuLayout.buttonY(),
                        menuLayout.buttonW(),
                        menuLayout.buttonH(),
                        menuLayout.highScoresButtonX(),
                        menuLayout.highScoresButtonY(),
                        menuLayout.highScoresButtonW(),
                        menuLayout.highScoresButtonH()),
                difficulties.size(),
                selectedDifficultyIndex,
                startMenuDropdownOpen);

        selectedDifficultyIndex = mouseResult.selectedDifficultyIndex();
        startMenuDropdownOpen = mouseResult.dropdownOpen();
        if (mouseResult.playSelectSound()) {
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (mouseResult.startRequested()) {
            beginStartLoading();
        }
        if (mouseResult.highScoresRequested()) {
            openLegacyMenuAction.run();
        }
    }

    private void beginStartLoading() {
        Difficulty selected = difficulties.isEmpty()
                ? null
                : difficulties.get(Math.max(0, Math.min(selectedDifficultyIndex, difficulties.size() - 1)));
        queueMenuAndGameplayAssets(selected);
        loadingPending = true;
        loadingStartedAtNanos = TimeUtils.nanoTime();
        loadingStatusText = "Loading 0%";
    }

    private void queueMenuAndGameplayAssets(Difficulty selected) {
        context().assets().queueTexture(visualStyle.menuIconImagePath());
        context().assets().queueTexture(ImageResourceConstants.WinOverlayBackdropImage);
        context().assets().queueTexture(ImageResourceConstants.GameOverOverlayBackdropImage);

        if (selected == null) {
            return;
        }

        int width = DifficultyBoardConfig.boardWidth(selected);
        int height = DifficultyBoardConfig.boardHeight(selected);
        RuntimeVisualModel runtimeModel = runtimeModelLoader.load(width, height, selected);
        context().assets().queueTexture(runtimeModel.playerImagePath());
        context().assets().queueTexture(runtimeModel.playerDeathImagePath());
        context().assets().queueTexture(runtimeModel.backgroundImagePath());
        context().assets().queueTexture(runtimeModel.wallImagePath());
        context().assets().queueTexture(runtimeModel.goalImagePath());
        for (var enemy : runtimeModel.enemies()) {
            Set<String> paths = new LinkedHashSet<>();
            paths.add(enemy.imagePath());
            var spec = enemy.animationSpec();
            if (spec != null && spec.animationFrameCount() > 1) {
                for (String dir : List.of(
                        spec.imageTurnLeft(), spec.imageTurnRight(),
                        spec.imageTurnUp(), spec.imageTurnDown())) {
                    if (dir != null && !dir.isBlank()) {
                        for (int f = 0; f < spec.animationFrameCount(); f++) {
                            paths.add(SpriteAnimationUtil.deriveAnimationFramePath(dir, f));
                        }
                    }
                }
            }
            for (String path : paths) {
                context().assets().queueTexture(path);
            }
        }
    }

    private void draw() {
        List<String> difficultyNames = new ArrayList<>(difficulties.size());
        for (Difficulty difficulty : difficulties) {
            difficultyNames.add(displayName(difficulty));
        }

        String selectedDifficultySummary = "";
        if (selectedDifficultyIndex >= 0 && selectedDifficultyIndex < difficulties.size()) {
            Difficulty selectedDifficulty = difficulties.get(selectedDifficultyIndex);
            selectedDifficultySummary = "Difficulty: " + displayName(selectedDifficulty)
                    + "  " + DifficultyBoardConfig.boardSizeLabel(selectedDifficulty);
        }

        menuLayout = startMenuView.render(new GdxStartMenuView.RenderContext(
                batch,
                shapes,
                font,
                glyphLayout,
                hudCamera,
                menuIconTexture,
                difficultyNames,
                selectedDifficultyIndex,
                startMenuDropdownOpen,
                false,
                loadingPending,
                selectedDifficultySummary,
                loadingStatusText));
    }

    private static String displayName(Difficulty difficulty) {
        return DifficultyPresentationSupport.displayNameOr(difficulty, "Easy");
    }

}
