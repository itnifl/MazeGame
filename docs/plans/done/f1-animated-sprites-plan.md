# Implementation Plan — F1. Animated Character Sprites

**Status:** PLANNED
**ID:** `F1`
**Source:** `opponents.ecore` — `CharacterType.animationFrameCount`, `CharacterType.spriteScale`
**Backend:** both (JavaFX and libGDX)
**Target:** JavaFX (`maze-javafx-backend`) & libGDX (`maze-libgdx`)
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

This plan details the implementation of **F1. Animated character sprites**. The opponent metamodel specifies that any `CharacterType` can have an `animationFrameCount` (default 1) and a `spriteScale` (default 1.0). Currently, these fields are parsed but completely ignored at runtime on both backends. Characters render using a static, single `ImageView` or Texture.

### Goals
- Read `animationFrameCount` and `spriteScale` during opponent loading.
- On JavaFX, replace the static `ImageView` setup with an `ImageView` that updates its viewport (or swaps images) based on a tick-driven animation loop.
- On libGDX, load the base texture as a sprite sheet, apply `spriteScale` to the rendered size, and use the existing `enemyAnimationClock` to pick the right frame from `animationFrameCount`.
- Provide robust unit testing covering frame calculation and scale application.

### Detailed Approach
1. **Model Expansion**: Update `EnemySpawn` and related DTOs to carry `animationFrameCount` and `spriteScale`.
2. **libGDX Rendering**: Modify `GdxGameWorldView.java` in the `drawEnemies` method to split the enemy texture into regions (using `TextureRegion`) if `frameCount > 1`. Compute the active frame using `(int)(enemyAnimationClock * framesPerSecond) % animationFrameCount`. Multiply render `size` by `spriteScale`.
3. **JavaFX Rendering**: Modify `OpponentRuntimeFactory.createCharacterGraphics`. If `animationFrameCount > 1`, set up a JavaFX `Viewport` bound to a timeline or bind it to the main `AnimationTimer` in `FxEnemyCoordinator` to shift the viewport across the sprite sheet. Apply `scaleX`/`scaleY` using `spriteScale`.
4. **Testing**: Add `AnimatedSpriteRenderTest` in both backends to verify that clock advances correctly switch the viewport/texture region.