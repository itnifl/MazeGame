package main.game.maze.libgdx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EnemyAnimationSpecTest {

    @Test
    void defaultsHaveSingleFrameAndUnitScale() {
        EnemyAnimationSpec spec = EnemyAnimationSpec.defaults();
        assertEquals(1, spec.animationFrameCount());
        assertEquals(1.0f, spec.spriteScale(), 1e-6f);
        assertNull(spec.imageTurnLeft());
        assertNull(spec.imageTurnRight());
        assertNull(spec.imageTurnUp());
        assertNull(spec.imageTurnDown());
    }

    @Test
    void staticDirectionalPreservesPathsAndDefaultsAnimation() {
        EnemyAnimationSpec spec = EnemyAnimationSpec.staticDirectional(
                "/l.png", "/r.png", "/u.png", "/d.png");
        assertEquals(1, spec.animationFrameCount());
        assertEquals(1.0f, spec.spriteScale(), 1e-6f);
        assertEquals("/l.png", spec.imageTurnLeft());
        assertEquals("/r.png", spec.imageTurnRight());
        assertEquals("/u.png", spec.imageTurnUp());
        assertEquals("/d.png", spec.imageTurnDown());
    }

    @Test
    void canonicalConstructorPreservesAllFields() {
        EnemyAnimationSpec spec = new EnemyAnimationSpec("/l.png", "/r.png", "/u.png", "/d.png", 3, 1.5f);
        assertEquals(3, spec.animationFrameCount());
        assertEquals(1.5f, spec.spriteScale(), 1e-6f);
        assertEquals("/l.png", spec.imageTurnLeft());
        assertEquals("/r.png", spec.imageTurnRight());
    }

    @Test
    void enemySpawnDefaultsToSingleFrameAnimation() {
        EnemySpawn spawn = new EnemySpawn("id", "/img.png", 10f, 20f, 32f, 1f,
                1, 0, "", main.game.maze.opponents.BehaviorType.WANDER, 1f);
        assertEquals(1, spawn.animationSpec().animationFrameCount());
        assertEquals(1.0f, spawn.animationSpec().spriteScale(), 1e-6f);
    }

    @Test
    void enemySpawnWithCustomSpecPreservesAnimationData() {
        EnemyAnimationSpec spec = new EnemyAnimationSpec("/l.png", "/r.png", "/u.png", "/d.png", 3, 1.0f);
        EnemySpawn spawn = new EnemySpawn("id", "/img.png", 10f, 20f, 32f, 1f,
                1, 0, "", main.game.maze.opponents.BehaviorType.WANDER, 1f,
                0.0, 100,
                main.game.maze.opponents.ProjectileType.STRAIGHT, 0f, 0f, 0f, 0, 0f,
                0, 100, spec);
        assertEquals(3, spawn.animationSpec().animationFrameCount());
        assertEquals("/r.png", spawn.animationSpec().imageTurnRight());
    }
}
