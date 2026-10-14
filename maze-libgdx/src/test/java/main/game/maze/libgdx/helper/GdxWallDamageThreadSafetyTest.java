package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import java.util.concurrent.atomic.AtomicReference;
import main.game.maze.common.graphics.UiScheduler;
import main.game.maze.libgdx.backend.GdxUiScheduler;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("libGDX wall-damage thread safety")
class GdxWallDamageThreadSafetyTest {

    @AfterEach
    void resetBackendState() {
        Gdx.app = null;
        UiScheduler.reset();
    }

    @Test
    @DisplayName("background damage is posted and nav-graph rewiring runs on the render thread")
    void backgroundDamagePostsRewireToRenderThread() throws InterruptedException {
        Thread renderThread = Thread.currentThread();
        Application application = mock(Application.class);
        Gdx.app = application;
        UiScheduler.set(new GdxUiScheduler());

        RecordingWorld world = new RecordingWorld();
        BreakableWall wall = world.getBreakableWalls().get(0);
        int wallsBefore = world.getMazeVectors().size();
        AtomicReference<Throwable> backgroundFailure = new AtomicReference<>();

        Thread damageThread = new Thread(() -> {
            try {
                GdxWallDamageSupport.applyProjectileDamageToWall(
                        world, wall.geometry, wall.getRemainingHp());
            } catch (Throwable failure) {
                backgroundFailure.set(failure);
            }
        }, "wall-damage-worker");
        damageThread.start();
        damageThread.join();

        assertNull(backgroundFailure.get());
        assertTrue(world.getMazeVectors().contains(wall.geometry),
                "Background callers must not mutate walls before the render-thread task runs");
        assertDoesNotThrow(() -> traverseGraph(world.getNavigationGraph()));

        ArgumentCaptor<Runnable> postedTask = ArgumentCaptor.forClass(Runnable.class);
        verify(application).postRunnable(postedTask.capture());
        assertDoesNotThrow(() -> postedTask.getValue().run());

        assertSame(renderThread, world.damageThread,
                "applyWallDamage must execute on the captured libGDX render thread");
        assertFalse(world.getMazeVectors().contains(wall.geometry));
        assertTrue(world.getMazeVectors().size() < wallsBefore);
    }

    private static void traverseGraph(MazeNavigationGraph graph) {
        for (int col = 0; col < graph.getCols(); col++) {
            for (int row = 0; row < graph.getRows(); row++) {
                MazeNavigationGraph.Node node = graph.getNode(col, row);
                if (node != null) {
                    node.getNeighbors().forEach(MazeNavigationGraph.Node::getCol);
                }
            }
        }
    }

    private static final class RecordingWorld extends GameMazeWorld {
        private volatile Thread damageThread;

        @Override
        public boolean applyWallDamage(BreakableWall wall, int damage) {
            damageThread = Thread.currentThread();
            return super.applyWallDamage(wall, damage);
        }
    }
}
