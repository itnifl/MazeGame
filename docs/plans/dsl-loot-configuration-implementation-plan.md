# Implementation Plan — F21. DSL Loot Configuration

**Status:** PLANNED
**ID:** `F21`
**Source:** `MazeDsl.xtext` — `LootTableConfig`, `LootItemConfig`, `LootItemTypeEnum {food, bomb, trap, weapon}`, opponent `loot` reference.
**Backend:** both (JavaFX and libGDX)
**Target:** Shared Backend (`maze-common-backend`), JavaFX (`maze-javafx-backend`) & libGDX (`maze-libgdx`)
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

This plan details the implementation of **F21. DSL Loot Configuration** and partially overlaps with **F2. Loot drops on enemy death**. In the Xtext DSL grammar, the language allows modellers to define loot tables (`LootTableConfig`), distinct loot items (`LootItemConfig`), and link those loot tables to specific opponent definitions via a `loot` reference.

Currently:
1. The DSL grammar can parse these structures, but the model bridging code (the loader) does not convert or pass this data to the runtime game configurations.
2. At runtime, the enemies (zombies, etc.) have no concept of dropping items when killed. There is no concept of a "Loot Item" entity in the physical game world.
3. The player does not interact with (pick up) dropped loot, and there is no UI element representing inventory or applying the effects of `FOOD`, `BOMB`, `TRAP`, or `WEAPON`.

Implementing this feature requires establishing new runtime logic across almost every layer of the architecture, from the DSL loader to the rendering pipeline, and the physics/collision systems. This makes it **the hardest missing feature to implement**.

### Analysis of the code state
- **EMF metamodels & DSL:** `opponents.ecore` has `Zombie.zombieLootTable`, `LootTable.weightCapacity`, `LootItem`, etc. `MazeDsl.xtext` has `LootTableConfig` and `LootItemConfig`.
- **JavaFX side:** No loot rendering. No "LootItem" node. No logic on enemy death to spawn loot. No collision logic for player walking over items. No system for loot item effect application.
- **libGDX side:** Similar. No entities for loot. No sprite logic. No game loop code tracking items dropped on the floor.

---

## 2. Goals & Non-Goals

### Goals
- Update `MazeConfigLoader` and `RuntimeVisualModelLoader` / `OpponentRuntimeFactory` to parse and load Loot Tables and Loot Items, mapping them to enemies.
- Implement runtime data models (`RuntimeLootTable`, `RuntimeLootItem`) to hold this data.
- Add an "Enemy Death Drop" system to the game loops. When a zombie with a loot table reaches 0 HP, calculate drops based on the weight budget and probabilities, and spawn `LootItemEntity` objects at the death coordinates.
- Implement rendering for dropped loot items on the floor in both JavaFX and libGDX backends.
- Add collision/pickup logic: when the player bounding box intersects a loot item, the item is consumed.
- Implement the effects of consumed items: `FOOD` restores HP. (`BOMB`, `TRAP`, `WEAPON` effects can be stubbed or minimally implemented as score bonuses for this initial feature, as complex inventory systems are not fully specified yet).
- Provide comprehensive test coverage across both backends to guarantee parity.

### Non-Goals
- Full player inventory UI with item selection/dropping (only immediate consumption/effect application is required for initial feature acceptance).
- Complex physics for dropped items (they will just spawn static on the floor tile).
- Custom animations for item pickups.

---

## 3. Detailed Technical Approach

### 3.1 DSL Parsing & Backend Model Mapping
1. **Update XmiRulesLoader / DSL Loader:** Ensure the loader traverses `zombieLootTable` and correctly instantiates the mapping between a `CharacterType` and its `LootTable`.
2. **DTO Expansion:** Create shared DTOs:
   ```java
   public record LootDropTable(int weightCapacity, List<LootDropItem> items) {}
   public record LootDropItem(String name, String type, int value, int weight, String imageBase) {}
   ```
3. **Attach to Spawns:** Update `EnemySpawn` (libGDX) and the generated runtime registries (JavaFX) to carry a `LootDropTable` reference for each spawned enemy.

### 3.2 Loot Drop Calculation & Spawning
1. **Loot Roller Service (`maze-common-backend`):**
   - Create `LootRollService.java`.
   - Method: `List<LootDropItem> rollLoot(LootDropTable table, Random random)`.
   - Logic: Iterate through available items. Pick items based on weights until the sum of the rolled items' weights reaches or exceeds the table's `weightCapacity`.
2. **Game Loop Integration (JavaFX - `FxPlayingModeController.java` / `GameController.java`):**
   - When an enemy's HP reaches 0 and death animation finishes, query its `LootDropTable`.
   - Invoke `LootRollService`. For each dropped item, create a new `LootItemCharacter` (implementing `ICanSubscribeAndNotifyPosition`).
   - Register the item with `FxEnemyCoordinator` / the main game canvas to be rendered at the enemy's last coordinates.
3. **Game Loop Integration (libGDX - `GdxGameCombatAndEnemyFlowSupport.java`):**
   - In `updateCombat()`, when an enemy is marked dead, roll loot.
   - Add the dropped items to a new list in `GameWorldModel.java` (e.g., `List<RuntimeLootItemEntity> droppedLoot`).

### 3.3 Collision & Pickup Logic
1. **JavaFX:**
   - The `LootItemCharacter` registers the `PlayerCharacter` as a position subscriber.
   - In `doPositionEvaluation`, if bounding boxes intersect, apply the effect to the player (e.g., `player.addHitPoints(item.getValue())`), play a sound, and deregister/remove the `LootItemCharacter` from the game board.
2. **libGDX:**
   - In `PlayingModeController` -> `updateCombat()`, iterate over `droppedLoot`. Check distance squared between the player and the loot item.
   - If distance < pickup radius, apply effect to `PlayerCombatStateService`, play sound, and remove item from `droppedLoot` list.

### 3.4 Rendering
1. **JavaFX:**
   - Modify `OpponentRuntimeFactory` or create `LootRuntimeFactory` to generate JavaFX `ImageView` nodes for the dropped items using `LootItem.graphicBase`.
2. **libGDX:**
   - Update `GdxGameWorldView.java` to draw `droppedLoot` before drawing enemies but after the background/walls. Load textures via `GdxAssetService`.

---

## 4. Verification & Testing Plan (TDD)

### 4.1 Unit Testing
- **`LootRollServiceTest.java` (Shared):**
  - Verify that rolling with an empty table returns an empty list.
  - Verify that rolling respects the `weightCapacity` limit (it shouldn't drop 5 items of weight 10 if capacity is 20).
  - Use deterministic mock Randoms to ensure specific probability drops succeed.
- **`FxLootCollisionTest.java` (JavaFX):**
  - Spawn a mock Player and a mock `LootItemCharacter` (type FOOD, value 20). Set player HP to 50.
  - Force intersection.
  - Verify player HP increases to 70 and the item is marked for removal.
- **`GdxLootCombatSupportTest.java` (libGDX):**
  - Inject an enemy death event with a valid loot table into `GdxGameCombatAndEnemyFlowSupport`.
  - Verify that `GameWorldModel.droppedLoot()` contains the expected items post-tick.
  - Move player coordinates over the loot item in the next tick. Verify player HP updates and the list is empty.
- **`DSLParserLootTest.java` (DSL):**
  - Provide a valid `.mazedsl` string defining a loot table.
  - Assert that the resulting AST/Runtime Config accurately populates the `LootDropTable` DTOs.

### 4.2 Manual Verification
1. Create a `loot_test.xmi` / `loot_test.mazedsl` level. Give Zombies a 100% drop rate of a "FOOD" item using a green apple sprite.
2. Start the game on JavaFX. Let the player get hit once by a ghost, then kill a Zombie (assuming weapons/killing mechanics exist; if not, spoof enemy death via console command or timer).
3. Walk over the spawned apple. Observe HP bar replenish.
4. Repeat process in libGDX to ensure exact visual and logic parity.

---

## 5. Summary Table (WR, CRR, DOD)

| ID | Goal / Requirement | Status |
|----|--------------------|--------|
| **WR-1** | Read requirements and plan carefully | Done |
| **WR-2** | Design for SOLID, modularity, testability | Done |
| **WR-3** | TDD: plan tests alongside the code | Done |
| **CRR-1** | MVC pattern for views and controller | Done |
| **CRR-5** | Parity between JavaFX and libGDX | Done (identical spawning and pickup logic) |
| **CRR-16**| DRY | Done (shared roll service) |
| **DOD-1** | Present this table | Done |
| **DOD-3** | Update suggested requirements / RTM as needed | Planned |
