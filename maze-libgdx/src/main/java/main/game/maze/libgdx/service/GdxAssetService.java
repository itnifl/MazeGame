package main.game.maze.libgdx.service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashSet;
import java.util.Set;

/**
 * Asset boundary for libGDX resources. Keeps loading and missing-path handling
 * in one place so screens avoid direct AssetManager coupling.
 */
public class GdxAssetService {

    private final AssetManager manager;
    private final Set<String> missingTexturePaths = new HashSet<>();

    public GdxAssetService() {
        this(new AssetManager());
    }

    GdxAssetService(AssetManager manager) {
        this.manager = manager;
    }

    public void queueTexture(String classpathPath) {
        String path = normalizeInternalPath(classpathPath);
        if (path == null || missingTexturePaths.contains(path) || Gdx.files == null) {
            return;
        }
        if (!manager.isLoaded(path, Texture.class)) {
            manager.load(path, Texture.class);
        }
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public boolean updateLoading() {
        return manager.update();
    }

    public float loadingProgress() {
        return manager.getProgress();
    }

    public Texture getTexture(String classpathPath) {
        String path = normalizeInternalPath(classpathPath);
        if (path == null || missingTexturePaths.contains(path) || Gdx.files == null) {
            return null;
        }
        if (manager.isLoaded(path, Texture.class)) {
            return manager.get(path, Texture.class);
        }
        try {
            manager.load(path, Texture.class);
            manager.finishLoadingAsset(path);
            return manager.get(path, Texture.class);
        } catch (RuntimeException ex) {
            missingTexturePaths.add(path);
            if (manager.isLoaded(path, Texture.class)) {
                manager.unload(path);
            }
            return null;
        }
    }

    public void dispose() {
        manager.dispose();
    }

    static String normalizeInternalPath(String classpathPath) {
        if (classpathPath == null || classpathPath.isBlank()) {
            return null;
        }
        return classpathPath.startsWith("/") ? classpathPath.substring(1) : classpathPath;
    }
}
