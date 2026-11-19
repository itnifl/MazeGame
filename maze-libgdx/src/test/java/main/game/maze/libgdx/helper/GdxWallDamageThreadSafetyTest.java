package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import main.game.maze.common.graphics.UiScheduler;
import main.game.maze.libgdx.backend.GdxUiScheduler;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("libGDX wall-damage thread safety")
class GdxWallDamageThreadSafetyTest {

    private static final int WORLD_W = 800;
    private static final int WORLD_H = 800;

    private Thread renderThread;
    private Application application;
    private RecordingWorld world;
    private BreakableWall wall;

    @BeforeEach
    void installRenderThreadScheduler() {
        renderThread = Thread.currentThread();
        application = mock(Application.class);
        Gdx.app = application;
        UiScheduler.set(new GdxUiScheduler());
        world = new RecordingWorld();
        wall = world.getBreakableWalls().get(0);
    }

    @AfterEach
    void resetBackendState() {
        Gdx.app = null;
        UiScheduler.reset();
    }

    @Test
    @DisplayName("background damage is posted and nav-graph rewiring runs on the render thread")
    void backgroundDamagePostsRewireToRenderThread() throws InterruptedException {
        int wallsBefore = world.getMazeVectors().size();

        runOnWorker(() -> GdxWallDamageSupport.applyProjectileDamageToWall(
                world, wall.geometry, wall.getRemainingHp()));

        assertTrue(world.getMazeVectors().contains(wall.geometry),
                "Background callers must not mutate walls before the render-thread task runs");
        assertDoesNotThrow(() -> traverseGraph(world.getNavigationGraph()));

        List<Runnable> posted = capturePostedTasks(1);
        assertDoesNotThrow(() -> posted.get(0).run());

        assertSame(renderThread, world.damageThread,
                "applyWallDamage must execute on the captured libGDX render thread");
        assertFalse(world.getMazeVectors().contains(wall.geometry));
        assertTrue(world.getMazeVectors().size() < wallsBefore);
    }

    @Test
    @DisplayName("render-thread damage runs inline without posting to Gdx.app")
    void renderThreadDamageRunsInline() {
        int hpBefore = wall.getRemainingHp();

        GdxWallDamageSupport.applyProjectileDamageToWall(world, wall.geometry, 1);

        verify(application, never()).postRunnable(any());
        assertSame(renderThread, world.damageThread);
        assertEquals(hpBefore - 1, wall.getRemainingHp(),
                "Render-thread callers must apply damage immediately");
    }

    @Test
    @DisplayName("non-lethal background hit changes HP only after the posted task runs")
    void nonLethalBackgroundHitDefersHpChange() throws InterruptedException {
        int hpBefore = wall.getRemainingHp();
        assumeTrue(hpBefore > 1, "Wall must survive a 1-HP hit");
        int wallsBefore = world.getMazeVectors().size();

        runOnWorker(() -> GdxWallDamageSupport.applyProjectileDamageToWall(world, wall.geometry, 1));

        assertEquals(hpBefore, wall.getRemainingHp(),
                "HP must not change on the worker thread");
        assertNull(world.damageThread);

        capturePostedTasks(1).get(0).run();

        assertSame(renderThread, world.damageThread);
        assertEquals(hpBefore - 1, wall.getRemainingHp());
        assertTrue(world.getMazeVectors().contains(wall.geometry),
                "A non-lethal hit must leave the wall in place");
        assertEquals(wallsBefore, world.getMazeVectors().size());
    }

    @Test
    @DisplayName("repeated queued hits destroy the wall once and later hits are no-ops")
    void repeatedQueuedHitsAreIdempotent() throws InterruptedException {
        int hp = wall.getRemainingHp();
        int queuedHits = hp + 2;

        runOnWorker(() -> {
            for (int i = 0; i < queuedHits; i++) {
                GdxWallDamageSupport.applyProjectileDamageToWall(world, wall.geometry, 1);
            }
        });

        assertEquals(hp, wall.getRemainingHp(), "No queued hit may run before the render thread drains");
        List<Runnable> posted = capturePostedTasks(queuedHits);

        posted.get(0).run();
        int wallsAfterFirstHit = world.getMazeVectors().size();
        for (int i = 1; i < hp; i++) {
            posted.get(i).run();
        }
        assertFalse(world.getMazeVectors().contains(wall.geometry),
                "The wall must be destroyed once its HP is exhausted");
        int wallsAfterDestroy = world.getMazeVectors().size();
        assertTrue(wallsAfterDestroy < wallsAfterFirstHit);

        for (int i = hp; i < queuedHits; i++) {
            assertDoesNotThrow(posted.get(i)::run);
        }
        assertEquals(wallsAfterDestroy, world.getMazeVectors().size(),
                "Hits queued after destruction must not remove further walls");
        assertEquals(hp, world.damageCalls.get(),
                "applyWallDamage must not be called for an already-destroyed wall");
        assertDoesNotThrow(() -> traverseGraph(world.getNavigationGraph()));
    }

    @Test
    @DisplayName("public wallHitCallback invoked from a worker posts damage to the render thread")
    void wallHitCallbackFromWorkerPostsToRenderThread() throws InterruptedException {
        RealMaze arena = new RealMaze(world, WORLD_W, WORLD_H);
        Consumer<float[]> callback = GdxWallDamageSupport.wallHitCallback(arena, 1);
        assertNotNull(callback);
        int totalHpBefore = totalBreakableHp();
        int wallsBefore = world.getMazeVectors().size();

        runOnWorker(() -> callback.accept(midpoint(wall.geometry)));

        assertEquals(totalHpBefore, totalBreakableHp(),
                "The callback must not mutate walls on the worker thread");
        assertNull(world.damageThread);

        capturePostedTasks(1).get(0).run();

        assertSame(renderThread, world.damageThread);
        assertEquals(totalHpBefore - 1, totalBreakableHp());
        assertEquals(wallsBefore, world.getMazeVectors().size());
    }

    private List<Runnable> capturePostedTasks(int expected) {
        ArgumentCaptor<Runnable> postedTask = ArgumentCaptor.forClass(Runnable.class);
        verify(application, times(expected)).postRunnable(postedTask.capture());
        return postedTask.getAllValues();
    }

    private static void runOnWorker(Runnable work) throws InterruptedException {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Thread worker = new Thread(() -> {
            try {
                work.run();
            } catch (Throwable t) {
                failure.set(t);
            }
        }, "wall-damage-worker");
        worker.start();
        worker.join();
        assertNull(failure.get(), "Worker thread must not fail");
    }

    private int totalBreakableHp() {
        return world.getBreakableWalls().stream().mapToInt(BreakableWall::getRemainingHp).sum();
    }

    private static float[] midpoint(Vector2D segment) {
        return new float[]{
                (float) ((segment.getStart().getX() + segment.getEnd().getX()) / 2.0),
                (float) ((segment.getStart().getY() + segment.getEnd().getY()) / 2.0)};
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
        private final AtomicInteger damageCalls = new AtomicInteger();

        @Override
        public boolean applyWallDamage(BreakableWall wall, int damage) {
            damageThread = Thread.currentThread();
            damageCalls.incrementAndGet();
            return super.applyWallDamage(wall, damage);
        }
    }
}
