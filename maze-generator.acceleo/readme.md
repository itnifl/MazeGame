# maze-generator.acceleo

> **Model-Driven Code Generation for MazeGame**

This project uses [Acceleo](https://eclipse.dev/acceleo/) to automatically generate Java code from EMF (Eclipse Modeling Framework) models. Instead of writing repetitive boilerplate code by hand, we define models once and let Acceleo generate the implementation.

---

## 🎯 What is This Project?

### The Problem It Solves

In game development, you often have many similar types of objects (enemies, walls, difficulty levels) that need:
- Switch statements to handle each type differently
- Factory methods to create instances
- Registration code to track all types

**Without code generation**, every time you add a new enemy type, you must manually update 5-10 different files. This is error-prone and tedious.

**With code generation**, you add the new enemy to the model file, run the generator, and all the boilerplate code is created automatically.

### The Value of Model-Driven Engineering (MDE)

| Approach | Adding a New Enemy Type |
|----------|------------------------|
| **Manual** | Edit `OpponentRuntimeFactory.java`, `CharacterRegistrar.java`, `GraphicsFactory.java`, etc. (5+ files) |
| **MDE** | Add to `opponents.ecore` model → Run generator → Done! |

This is called **Model-Driven Engineering** - the model is the single source of truth.

---

## 📁 Project Structure

```
maze-generator.acceleo/
├── src/main/java/main/game/maze/gen/
│   ├── templates/
│   │   ├── Generate.mtl          # Main template for opponents
│   │   ├── GenerateWalls.mtl     # Template for walls
│   │   ├── Generate.emtl         # Compiled template (binary)
│   │   └── GenerateWalls.emtl    # Compiled template (binary)
│   └── RunAcceleo.java           # Standalone runner class
├── META-INF/
│   └── MANIFEST.MF               # Eclipse plugin manifest
├── plugin.xml                    # Plugin configuration
└── pom.xml                       # Maven build file
```

### Key File Types

| Extension | Purpose |
|-----------|---------|
| `.mtl` | **M**odel **T**o **L**anguage - Acceleo template source files |
| `.emtl` | Compiled Acceleo templates (like `.class` for `.java`) |
| `.ecore` | EMF model definitions (the "schema" for your data) |
| `.xmi` | Model instances (actual data conforming to the schema) |

---

## 🔧 What Gets Generated

The templates generate Java classes that would otherwise be hand-written boilerplate:

### From Opponents Model (`opponents.ecore`)

| Generated File | Purpose |
|---------------|---------|
| `OpponentRegistry.java` | Lists all enemy types with their stats |
| `CharacterRegistrar.java` | Type-safe switch dispatch for character handling |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers per type |
| `CharacterGraphicsFactory.java` | Creates sprites for each character type |

### From Walls Model (`walls.ecore`)

| Generated File | Purpose |
|---------------|---------|
| `WallRegistry.java` | Lists all wall material types |
| `WallMaterialRenderer.java` | Visual properties per material |
| `WallCollisionHandler.java` | Damage/collision behavior per material |

### Example: Generated Switch Statement

Instead of writing this by hand:

```java
// ❌ Hand-written - must update for every new enemy
if (character instanceof Zombie) {
    handleZombie((Zombie) character);
} else if (character instanceof Ghost) {
    handleGhost((Ghost) character);
} else if (character instanceof PumpkinBomber) {
    handlePumpkinBomber((PumpkinBomber) character);
}
```

The generator creates:

```java
// ✅ Generated - automatically includes all types from model
switch (character.eClass().getName()) {
    case "Zombie" -> handleZombie((Zombie) character);
    case "Ghost" -> handleGhost((Ghost) character);
    case "PumpkinBomber" -> handlePumpkinBomber((PumpkinBomber) character);
    default -> LOGGER.warning("Unknown type: " + typeName);
}
```

---

## 🚀 How to Run the Generator

### Option 1: In Eclipse (Interactive)

Best for development and experimenting:

1. **Open the template file**
   - Navigate to `src/main/java/main/game/maze/gen/templates/Generate.mtl`

2. **Run as Acceleo Application**
   - Right-click the `.mtl` file
   - Select `Run As` → `Launch Acceleo Application`
   - Choose or create a launch configuration

3. **Configure the launch**
   - **Model**: Path to your `.xmi` file (e.g., `maze/src/main/resources/xmi/opponents/opponentModel.xmi`)
   - **Target Folder**: Where to write output (e.g., `maze-module-generator/src-gen`)

4. **Run and check output**
   - Generated files appear in the target folder
   - Check for any errors in the Console view

### Option 2: Via Maven (Automated)

Best for CI/CD and reproducible builds:

```bash
# From the repository root
mvn clean verify -DskipTests
```

The Maven build:
1. Compiles all EMF models
2. Builds the `maze-generator.acceleo` plugin
3. Runs `maze-generator.acceleo-runner` which invokes the templates
4. Generated sources appear in `maze-module-generator/src-gen/`

---

## 📝 Understanding Acceleo Templates

### Basic Template Anatomy

```mtl
[comment encoding = UTF-8 /]
[module Generate('http://main.game.maze/opponents')]    ← Declares which model to use

[template public generate(root : OpponentModel)]        ← Entry point template
[comment @main /]                                       ← Marks this as the main template

[file ('Output.java', false, 'UTF-8')]                  ← Creates an output file
package generated;

public class Output {
    [for (enemy : CharacterType | root.characterTypes)] ← Loop over model elements
    public static final String [enemy.name/] = "[enemy.displayName/]";
    [/for]
}
[/file]

[/template]
```

### Key Acceleo Syntax

| Syntax | Meaning | Example |
|--------|---------|---------|
| `[for (x : Type | collection)]` | Loop | `[for (e : Enemy | model.enemies)]` |
| `[x.property/]` | Access property | `[enemy.health/]` |
| `[if (condition)]` | Conditional | `[if (enemy.health > 100)]` |
| `[file ('path', ...)]` | Create file | `[file ('Enemy.java', false, 'UTF-8')]` |
| `[comment text /]` | Comment | `[comment This is ignored /]` |

---

## 🎓 Tutorials and Learning Resources

### Official Acceleo Documentation
- [Acceleo User Guide](https://wiki.eclipse.org/Acceleo/User_Guide) - Comprehensive official guide
- [Acceleo Getting Started](https://wiki.eclipse.org/Acceleo/Getting_Started) - Quick start tutorial
- [Acceleo OCL Reference](https://wiki.eclipse.org/Acceleo/OCL_Operations_Reference) - OCL operations in templates

### EMF (Eclipse Modeling Framework)
- [EMF Tutorial](https://eclipsesource.com/blogs/tutorials/emf-tutorial/) - Learn EMF basics
- [EMF Documentation](https://www.eclipse.org/modeling/emf/) - Official EMF site
- [Ecore Metamodel](https://wiki.eclipse.org/Ecore) - Understanding `.ecore` files

### Video Tutorials
- [Acceleo Code Generation (YouTube)](https://www.youtube.com/results?search_query=acceleo+code+generation) - Search for tutorials
- [Model Driven Engineering Basics](https://www.youtube.com/results?search_query=model+driven+engineering+tutorial) - MDE concepts

### Books
- *Model-Driven Software Engineering in Practice* by Brambilla, Cabot, Wimmer
- *Eclipse Modeling Framework* by Steinberg et al.

---

## ✏️ Customizing Templates

### Step 1: Locate the Template

Templates are in `src/main/java/main/game/maze/gen/templates/`:
- `Generate.mtl` - Main opponents/characters template
- `GenerateWalls.mtl` - Walls material template

### Step 2: Make Changes

Edit the `.mtl` file. For example, to add a new method:

```mtl
[comment Add this inside the class generation /]
public static int getTotalEnemyCount() {
    return [root.characterTypes->size()/];
}
```

### Step 3: Recompile the Template

**Important**: After editing `.mtl`, you must recompile it to `.emtl`:
- In Eclipse: Save the file (auto-compiles with Acceleo builder)
- Or: Run a full Maven build

### Step 4: Test

1. Run the generator on a test model
2. Check the generated output for correctness
3. Commit both `.mtl` and `.emtl` files

---

## ➕ Adding a New Model

To generate code from a new EMF model:

### 1. Register the Model URI

In your `.mtl` template header:
```mtl
[module Generate('http://main.game.maze/opponents', 'http://main.game.maze/newmodel')]
```

### 2. Add Dependency in MANIFEST.MF

```
Require-Bundle: ...,
 main.game.maze.newmodel
```

### 3. Create Templates

Write templates that navigate your new model:
```mtl
[template public generateFromNewModel(root : NewModelRoot)]
[file ('NewOutput.java', false, 'UTF-8')]
// Generated from NewModel
[/file]
[/template]
```

### 4. Update the Runner

In `maze-generator.acceleo-runner`, ensure your model is loaded and passed to the generator.

---

## 🔍 Troubleshooting

### No Files Generated

**Symptom**: Generator runs but no files appear.

**Solutions**:
- Check that the model file path is correct
- Verify the root element type matches the template parameter
- Look for errors in the Eclipse Console or Maven output
- Ensure the `.emtl` file exists and is up-to-date

### "Cannot find module" Error

**Symptom**: `Cannot find Acceleo module: /path/Generate.emtl`

**Solutions**:
- Rebuild the project in Eclipse to compile `.mtl` → `.emtl`
- Check that `.emtl` is in the classpath
- Verify the module path in `RunAcceleo.java` matches the actual location

### Compilation Errors in Generated Code

**Symptom**: Generated `.java` files have syntax errors.

**Solutions**:
- Review the template for missing imports
- Check that model properties exist and have expected types
- Use `[protected]` blocks for code that shouldn't be overwritten

### Model Loading Errors

**Symptom**: `Resource not found` or `Unknown package URI`

**Solutions**:
- Register the EPackage in `RunAcceleo.java`:
  ```java
  EPackage.Registry.INSTANCE.put(MyPackage.eNS_URI, MyPackage.eINSTANCE);
  ```
- Ensure `.xmi` files reference the correct namespace URI

---

## 📊 Domain Models Overview

| Model | Namespace URI | Contains |
|-------|--------------|----------|
| **Opponents** | `http://main.game.maze/opponents` | CharacterType, Zombie, Ghost, PumpkinBomber |
| **Difficulties** | `http://main.game.maze/difficulties` | Difficulty, EasyDifficulty, NormalDifficulty, HardDifficulty |
| **Walls** | `http://main.game.maze/walls` | WallMaterial, WallMaterialBaseType (GLASS, DIRT, WOOD, STONE, STEEL) |
| **Behaviour** | `http://main.game.maze/behaviour` | MovementBehavior, PatrolBehavior, ChaseBehavior, RandomBehavior |

---

## 🔗 Related Projects

- **[Model-Driven Code Generation Plan](../readme-mddcodegeneration.md)** - Comprehensive plan for MDE code generation across all domains
- [maze-generator.acceleo-runner](../maze-generator.acceleo-runner/readme.md) - Headless runner for Maven builds
- [maze-module-generator](../maze-module-generator/readme.md) - Contains the generated sources
- [main.game.maze.opponents](../main.game.maze.opponents/readme.md) - Opponents EMF model
- [main.game.maze.walls](../main.game.maze.walls/readme.md) - Walls EMF model
- [main.game.maze.difficulties](../main.game.maze.difficulties/readme.md) - Difficulties EMF model

---

## 📚 Glossary

| Term | Definition |
|------|------------|
| **Acceleo** | Eclipse-based code generation framework using templates |
| **EMF** | Eclipse Modeling Framework - foundation for defining models |
| **Ecore** | The metamodel used by EMF (like a schema for models) |
| **XMI** | XML format for storing model instances |
| **MTL** | Model-to-Text Language - Acceleo's template language |
| **EMTL** | Compiled (binary) MTL template |
| **MDE** | Model-Driven Engineering - using models as primary artifacts |
| **Tycho** | Maven plugin for building Eclipse plugins |

---

*By keeping all model-to-code generation logic in `maze-generator.acceleo`, the MazeGame project maintains a clean, reproducible, and model-driven build pipeline.*

