# maze-generator.freemarker

> **Model-Driven Code Generation for MazeGame**

This project generates Java code from EMF (Eclipse Modeling Framework) models using **FreeMarker** templates. Instead of writing repetitive boilerplate by hand, we define models once and let the generator produce the implementation.

---

## ⚡ Quick Start

### Prerequisites

| Tool | Version | Purpose |
|------|---------|---------|
| **Java** | 21+ | Runtime |
| **Maven** | 3.9+ | Build system |
| **VS Code** or **IntelliJ** | Latest | Recommended IDE (see [Tool Recommendations](#-recommended-tools-for-freemarker)) |

### Generate Code (30 seconds)

```bash
# From repository root - regenerate all code from models
mvn -pl maze-module-generator -am clean verify
```

### Verify Generated Output

```bash
# Check generated files (PowerShell)
Get-ChildItem maze-module-generator/src-gen/main/game/maze/generated/

# Or on Unix/macOS
ls maze-module-generator/src-gen/main/game/maze/generated/

# Run tests to validate
mvn -pl maze-module-generator test
```

### Add a New Enemy Type (5 minutes)

1. Edit `main.game.maze.opponents/src/main/resources/opponents.ecore`
2. Add your new class extending `CharacterType`
3. Regenerate: `mvn -pl maze-module-generator -am clean verify`
4. **Done!** — All registrars, factories, and dispatchers are updated automatically

### Edit a Template

1. Open a `.ftl` file in `src/main/resources/templates/`
2. Make changes (syntax highlighting available in VS Code/IntelliJ)
3. Regenerate: `mvn -pl maze-module-generator -am clean verify`
4. Check output in `maze-module-generator/src-gen/`

### Key Directories

| Path | Contains |
|------|----------|
| `src/main/resources/templates/` | FreeMarker templates (`.ftl`) |
| `src/main/java/main/game/maze/gen/` | Generator Java classes |
| `lib/freemarker.jar` | FreeMarker template engine |
| `maze-module-generator/src-gen/` | Generated output |

---

## 🔄 Migration Journey: Acceleo → FreeMarker

### Historical Note

This module was originally named `maze-generator.acceleo` and was designed to use **Acceleo** (Eclipse's M2T framework) for code generation. It has been renamed to `maze-generator.freemarker` to reflect the actual template engine in use.

**Note**: Some Java class names (`RunAcceleo`, `RunWallsAcceleo`) retain the legacy naming for OSGi application ID compatibility.

### The Evolution

| Phase | Template Engine | Status |
|-------|-----------------|--------|
| **Phase 1** | Acceleo 3 (Eclipse-based) | ❌ Abandoned — required Eclipse workspace, complex setup |
| **Phase 2** | Acceleo 4 (standalone) | ❌ Abandoned — unstable releases, missing from stable p2 repos |
| **Phase 3** | PrintWriter (hardcoded Java) | ❌ Abandoned — not true M2T, templates embedded in Java |
| **Phase 4** | **FreeMarker** | ✅ **Current** — true templates, no Eclipse dependency |

### Why Acceleo 4 Was Abandoned

We initially attempted to migrate from Acceleo 3 to **Acceleo 4**, which promised standalone execution without Eclipse workspace dependencies. However, we encountered critical blockers:

1. **Missing from Stable Releases**
   - Acceleo 4 IUs (`org.eclipse.acceleo.aql`, `org.eclipse.acceleo.aql.ide`) were **not available** in Eclipse stable release repositories (2024-03, 2024-06, 2024-09, 2024-12)
   - Only available in unstable nightly/integration builds (`https://download.eclipse.org/acceleo/updates/nightly/latest/`)
   - Using nightly builds in production is not viable — they can break without notice

2. **Tycho/p2 Resolution Failures**
   - Target platform resolution failed when mixing stable Eclipse releases with Acceleo 4 nightly repos
   - Version conflicts between OCL, EMF, and Acceleo dependencies
   - Error example: `Cannot resolve project dependencies... org.eclipse.acceleo.aql`

3. **Incomplete Documentation**
   - Acceleo 4 documentation was sparse and often referenced Acceleo 3 concepts
   - Standalone execution examples were minimal
   - Migration guide from Acceleo 3 was incomplete

4. **API Instability**
   - The Acceleo 4 API changed between nightly builds
   - Code that worked one week might fail the next

**Decision**: Rather than depend on unstable nightly builds or wait for Acceleo 4 to mature, we chose **FreeMarker** — a battle-tested, Apache-licensed template engine available from Maven Central with excellent documentation.

### Why FreeMarker Won

| Criterion | Acceleo | PrintWriter | FreeMarker |
|-----------|---------|-------------|------------|
| True templates | ✅ `.mtl` files | ❌ Java strings | ✅ `.ftl` files |
| Eclipse dependency | ❌ Required | ✅ None | ✅ None |
| Maven Central | ❌ Tycho/p2 only | ✅ N/A | ✅ Available |
| Learning curve | Steep (OCL) | Low | Low |
| Debugging | Difficult | Easy | Easy |
| Community/docs | Small | N/A | Large |

---

## 📊 Detailed Comparison: Acceleo vs FreeMarker

### Feature-by-Feature Comparison

| Feature | Acceleo 3/4 | FreeMarker |
|---------|-------------|------------|
| **Template Language** | MTL (Model-to-Text Language) | FTL (FreeMarker Template Language) |
| **Model Support** | Native EMF integration | Any Java object (incl. EMF via Maps) |
| **Query Language** | OCL (Object Constraint Language) | Built-in expressions + Java methods |
| **IDE Support** | Eclipse only | VS Code, IntelliJ, Eclipse, any text editor |
| **Debugging** | Limited Eclipse debugger | Standard Java debugging |
| **Error Messages** | Often cryptic | Clear with line numbers |
| **Hot Reload** | Requires rebuild | Templates loaded at runtime |
| **Build Integration** | Tycho/p2 required | Standard Maven/Gradle |
| **License** | EPL (Eclipse) | Apache 2.0 |
| **Active Development** | Slow (Acceleo 4 incomplete) | Active, stable releases |

### When Would Acceleo Still Be Better?

| Scenario | Recommendation |
|----------|---------------|
| Deep EMF integration with OCL constraints | Acceleo (if stable release available) |
| Eclipse-only development environment | Acceleo |
| Cross-platform, CI/CD pipelines | **FreeMarker** ✅ |
| Team with mixed IDE preferences | **FreeMarker** ✅ |
| Quick prototyping and iteration | **FreeMarker** ✅ |
| Long-term maintainability | **FreeMarker** ✅ |

### Template Syntax Comparison

**Looping over a collection:**

```
# Acceleo (MTL)
[for (enemy : CharacterType | model.characterTypes)]
    case "[enemy.eClass().name/]": ...
[/for]

# FreeMarker (FTL)
<#list enemies as enemy>
    case "${enemy.typeName}": ...
</#list>
```

**Conditional logic:**

```
# Acceleo (MTL)
[if (enemy.health > 100)]
    // high health
[else]
    // normal health
[/if]

# FreeMarker (FTL)
<#if (enemy.health > 100)>
    // high health
<#else>
    // normal health
</#if>
```

**String manipulation:**

```
# Acceleo (MTL)
[enemy.name.toUpperFirst()/]
[enemy.name.toLower()/]

# FreeMarker (FTL)
${enemy.name?cap_first}
${enemy.name?lower_case}
```

---

## 🛠️ Recommended Tools for FreeMarker

### IDE Comparison for FreeMarker Development

| IDE | FreeMarker Support | Recommendation |
|-----|-------------------|----------------|
| **VS Code** | ✅ Excellent (via extensions) | ⭐ **Recommended** |
| **IntelliJ IDEA** | ✅ Built-in (Ultimate) / Plugin (Community) | ⭐ **Recommended** |
| **Eclipse** | ⚠️ Limited | Not recommended for FreeMarker |
| **Sublime Text** | ✅ Good (via package) | Acceptable |
| **Vim/Neovim** | ✅ Good (via plugins) | For experienced users |

### VS Code Setup (Recommended)

**Required Extensions:**

| Extension | Marketplace ID | Purpose |
|-----------|---------------|---------|
| **FreeMarker** | `niclasgrunau.freemarker` | Syntax highlighting, snippets |
| **Java Extension Pack** | `vscjava.vscode-java-pack` | Java development |
| **XML Tools** | `dotjoshjohnson.xml` | For `.ecore` and `.xmi` files |

**Install via command line:**
```bash
code --install-extension niclasgrunau.freemarker
code --install-extension vscjava.vscode-java-pack
code --install-extension dotjoshjohnson.xml
```

**VS Code Features for FreeMarker:**
- ✅ Syntax highlighting for `.ftl` files
- ✅ Auto-completion for FreeMarker directives
- ✅ Bracket matching
- ✅ Code folding
- ✅ Integrated terminal for Maven commands
- ✅ Java debugging for generator code

### IntelliJ IDEA Setup

**For IntelliJ Ultimate:**
- FreeMarker support is built-in
- Enable: `Settings → Plugins → Search "FreeMarker"` (should be bundled)

**For IntelliJ Community:**
- Install: `FreeMarker Support` plugin from JetBrains Marketplace

**IntelliJ Features:**
- ✅ Syntax highlighting
- ✅ Auto-completion
- ✅ Error detection in templates
- ✅ Refactoring support
- ✅ Navigation to data model classes

### Eclipse Evaluation for FreeMarker

> **⚠️ Eclipse is NOT recommended for FreeMarker development**

| Aspect | Assessment |
|--------|------------|
| **FreeMarker plugin** | ❌ Outdated, unmaintained (last update 2018) |
| **Syntax highlighting** | ⚠️ Basic, via JBoss Tools (heavyweight) |
| **Auto-completion** | ❌ Poor or non-existent |
| **Error detection** | ❌ None |
| **Template debugging** | ❌ Not supported |

**Why Eclipse Falls Short:**
1. **No dedicated FreeMarker plugin** — The only option is JBoss Tools, which is a large suite designed for JBoss/WildFly development
2. **EMF-centric** — Eclipse excels at EMF/Ecore but doesn't integrate FreeMarker with EMF
3. **Heavy overhead** — Installing JBoss Tools adds significant IDE bloat for minimal FreeMarker benefit
4. **Irony** — We moved away from Acceleo (Eclipse-based) partly to escape Eclipse dependency, so using Eclipse for FreeMarker defeats the purpose

**If You Must Use Eclipse:**
1. Install JBoss Tools: `Help → Eclipse Marketplace → Search "JBoss Tools"`
2. Select only "FreeMarker IDE" component (if available separately)
3. Expect limited functionality compared to VS Code/IntelliJ

**Recommendation:** Use VS Code or IntelliJ for FreeMarker templates, and Eclipse only for EMF model editing if needed.

### Recommended Development Workflow

```
┌─────────────────────────────────────────────────────────────┐
│  EMF Models (.ecore)          │  FreeMarker Templates (.ftl) │
│  ─────────────────────────    │  ──────────────────────────  │
│  Edit in: Eclipse or VS Code  │  Edit in: VS Code or IntelliJ│
│  (EMF tools available)        │  (Better FreeMarker support) │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Generator Java Code (RunAcceleo.java)                      │
│  ───────────────────────────────────────                    │
│  Edit in: VS Code or IntelliJ (standard Java)               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  Build & Test                                               │
│  ───────────────                                            │
│  Terminal: mvn -pl maze-module-generator -am clean verify   │
└─────────────────────────────────────────────────────────────┘
```

### Online Tools

| Tool | URL | Purpose |
|------|-----|---------|
| **FreeMarker Online Tester** | https://try.freemarker.apache.org/ | Test templates without local setup |
| **FreeMarker Manual** | https://freemarker.apache.org/docs/ | Official documentation |
| **FreeMarker Cheat Sheet** | (search online) | Quick reference |

### What Changed

- **Templates**: `.mtl` (Acceleo) → `.ftl` (FreeMarker)
- **Syntax**: `[for (x | collection)]` → `<#list collection as x>`
- **Output**: `[x.property/]` → `${x.property}`
- **Location**: Templates now in `src/main/resources/templates/`
- **Engine**: `freemarker.jar` bundled in `lib/`

### Migration Guide (for existing Acceleo users)

| Acceleo Syntax | FreeMarker Equivalent |
|----------------|----------------------|
| `[module name('uri')]` | N/A (handled in Java) |
| `[template public name(param : Type)]` | N/A (Java method) |
| `[for (x : Type \| collection)]...[/for]` | `<#list collection as x>...</#list>` |
| `[x.property/]` | `${x.property}` |
| `[if (condition)]...[/if]` | `<#if condition>...</#if>` |
| `[comment text /]` | `<#-- text -->` |
| `[file ('path', false, 'UTF-8')]` | Java `FileWriter` |
| `collection->size()` | `${collection?size}` |
| `string.toUpperCase()` | `${string?upper_case}` |

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

### When to Use Code Generation vs. Manual Code

| Scenario | Use Code Generation? | Reason |
|----------|---------------------|--------|
| Adding a new enemy type | ✅ Yes | Automatically updates registrar, factory, dispatcher |
| Changing enemy base stats | ✅ Yes | Model is the source of truth for data |
| Adding complex game logic | ❌ No | Hand-written code is more flexible for unique logic |
| Creating one-off utility class | ❌ No | No repetitive pattern to automate |
| Adding new attribute to all enemies | ✅ Yes | Template updates all generated code at once |
| Debugging runtime behavior | ❌ No | Debug hand-written code that uses generated APIs |

**Rule of thumb**: If you're about to copy-paste similar code for each type, consider generating it instead.

---

## 📁 Project Structure

```
maze-generator.freemarker/
├── src/main/java/main/game/maze/gen/
│   ├── RunAcceleo.java           # FreeMarker-based opponent generator
│   └── RunWallsAcceleo.java      # FreeMarker-based wall generator
├── src/main/resources/templates/
│   ├── opponents/                 # Opponent templates (.ftl)
│   │   ├── OpponentRegistry.ftl
│   │   ├── CharacterRegistrar.ftl
│   │   ├── CharacterAttributeSetter.ftl
│   │   └── CharacterGraphicsFactory.ftl
│   └── walls/                     # Wall templates (.ftl)
│       ├── WallRegistry.ftl
│       ├── WallMaterialRenderer.ftl
│       └── WallCollisionHandler.ftl
├── lib/
│   └── freemarker.jar            # FreeMarker template engine
├── META-INF/
│   └── MANIFEST.MF               # Eclipse plugin manifest
├── plugin.xml                    # Plugin configuration
└── pom.xml                       # Maven build file
```

**Note**: The generators (`RunAcceleo.java`, `RunWallsAcceleo.java`) use **FreeMarker** as a true template engine. They load EMF models and process `.ftl` templates to produce Java source files.

### Key File Types

| Extension | Purpose |
|-----------|---------|
| `.ecore` | EMF model definitions (the "schema" for your data) |
| `.xmi` | Model instances (actual data conforming to the schema) |
| `.ftl` | FreeMarker template files (in `src/main/resources/templates/`) |

---

## 🔧 What Gets Generated

The templates generate Java classes that would otherwise be hand-written boilerplate.

### Current Generated Files (in `maze-module-generator/src-gen/`)

These files are currently generated and tested:

| Generated File | Purpose | EMF Methods Used |
|---------------|---------|------------------|
| `CharacterRegistrar.java` | Registers and looks up character types | `eClass().getName()` |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers per type | `getHealth()`, `setHealth()`, `getThreatLevel()`, `setThreatLevel()`, `getSpeed()`, `setSpeed()` |
| `CharacterGraphicsFactory.java` | Creates sprites for each character type | `getImageBase()`, `getAnimationFrameCount()`, `getSpriteScale()` |
| `OpponentRegistry.java` | Lists all enemy types with their stats | Model iteration |
| `WallRegistry.java` | Lists all wall material types | Model iteration |

### From Opponents Model (`opponents.ecore`)

| Generated File | Purpose | Status |
|---------------|---------|--------|
| `CharacterRegistrar.java` | Type-safe switch dispatch for character handling | ✅ Generated |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers per type | ✅ Generated |
| `CharacterGraphicsFactory.java` | Creates sprites for each character type | ✅ Generated |
| `OpponentRegistry.java` | Lists all enemy types with their stats | ✅ Generated |

### From Walls Model (`walls.ecore`)

| Generated File | Purpose | Status |
|---------------|---------|--------|
| `WallRegistry.java` | Lists all wall material types | ✅ Generated |
| `WallMaterialRenderer.java` | Renders walls by material type | ✅ Generated |
| `WallCollisionHandler.java` | Handles wall collision logic | ✅ Generated |

### FreeMarker Templates

The following FreeMarker templates provide model-to-text generation:

| Template File | Domain | Generated Output |
|---------------|--------|------------------|
| `opponents/OpponentRegistry.ftl` | Opponents | OpponentRegistry.java |
| `opponents/CharacterRegistrar.ftl` | Opponents | CharacterRegistrar.java |
| `opponents/CharacterAttributeSetter.ftl` | Opponents | CharacterAttributeSetter.java |
| `opponents/CharacterGraphicsFactory.ftl` | Opponents | CharacterGraphicsFactory.java |
| `walls/WallRegistry.ftl` | Walls | WallRegistry.java |
| `walls/WallMaterialRenderer.ftl` | Walls | WallMaterialRenderer.java |
| `walls/WallCollisionHandler.ftl` | Walls | WallCollisionHandler.java |

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

### Option 1: Via Maven (Automated)

Best for CI/CD and reproducible builds:

```bash
# From the repository root
mvn clean verify -DskipTests
```

The Maven build:
1. Compiles all EMF models
2. Builds the `maze-generator.freemarker` plugin with FreeMarker generators
3. Runs `maze-generator.freemarker-runner` which invokes `RunAcceleo` and `RunWallsAcceleo`
4. Generated sources appear in `maze-module-generator/src-gen/`

---

## 📝 Understanding FreeMarker Templates

### Basic Template Anatomy

```ftl
<#-- FreeMarker template for generating Java code -->
package ${packageName};

import java.util.logging.Logger;

/**
 * Generated from EMF model: ${modelName}
 */
public class ${className} {
    private static final Logger LOGGER = Logger.getLogger(${className}.class.getName());

    <#list enemies as enemy>                              <#-- Loop over model elements -->
    public static final String ${enemy.name?upper_case} = "${enemy.displayName}";
    </#list>
}
```

### Key FreeMarker Syntax

| Syntax | Meaning | Example |
|--------|---------|---------|
| `<#list items as item>` | Loop | `<#list enemies as enemy>` |
| `${property}` | Output value | `${enemy.health}` |
| `<#if condition>` | Conditional | `<#if (enemy.health > 100)>` |
| `${x?upper_case}` | Built-in function | `${enemy.name?upper_case}` |
| `<#-- text -->` | Comment | `<#-- This is ignored -->` |

---

## 🎓 Tutorials and Learning Resources

### FreeMarker Documentation
- [FreeMarker Manual](https://freemarker.apache.org/docs/index.html) - Comprehensive official guide
- [FreeMarker Template Language Reference](https://freemarker.apache.org/docs/ref.html) - Language reference
- [FreeMarker FAQ](https://freemarker.apache.org/docs/app_faq.html) - Common questions

### EMF (Eclipse Modeling Framework)
- [EMF Tutorial](https://eclipsesource.com/blogs/tutorials/emf-tutorial/) - Learn EMF basics
- [EMF Documentation](https://www.eclipse.org/modeling/emf/) - Official EMF site
- [Ecore Metamodel](https://wiki.eclipse.org/Ecore) - Understanding `.ecore` files

### Video Tutorials
- [FreeMarker Templates (YouTube)](https://www.youtube.com/results?search_query=freemarker+template+tutorial) - Search for tutorials
- [Model Driven Engineering Basics](https://www.youtube.com/results?search_query=model+driven+engineering+tutorial) - MDE concepts

### Books
- *Model-Driven Software Engineering in Practice* by Brambilla, Cabot, Wimmer
- *Eclipse Modeling Framework* by Steinberg et al.

---

## ✏️ Customizing Templates

### Step 1: Locate the Template

Templates are in `src/main/resources/templates/`:
- `opponents/*.ftl` - Opponent templates (OpponentRegistry, CharacterRegistrar, AttributeSetter, GraphicsFactory)
- `walls/*.ftl` - Walls material templates (WallRegistry, WallMaterialRenderer, WallCollisionHandler)

### Step 2: Make Changes

Edit the `.ftl` file. For example, to add a new method:

```ftl
<#-- Add this inside the class generation -->
public static int getTotalEnemyCount() {
    return ${enemies?size};
}
```

### Step 3: Test

1. Run the generator: `mvn -pl maze-module-generator -am clean verify`
2. Check the generated output in `maze-module-generator/src-gen/`
3. Run tests: `mvn -pl maze-module-generator test`

**Note**: FreeMarker templates don't need pre-compilation - they are processed at runtime.

---

## ➕ Adding a New Model

To generate code from a new EMF model:

### 1. Register the EMF Package

In your generator Java class, register the new package:
```java
EPackage.Registry.INSTANCE.put(NewModelPackage.eNS_URI, NewModelPackage.eINSTANCE);
```

### 2. Add Dependency in MANIFEST.MF

```
Require-Bundle: ...,
 main.game.maze.newmodel
```

### 3. Create Templates

Write FreeMarker templates that use your model data:
```ftl
<#-- NewOutput.ftl -->
package ${packageName};

/**
 * Generated from NewModel
 */
public class NewOutput {
    <#list items as item>
    public static final String ${item.name?upper_case} = "${item.value}";
    </#list>
}
```

### 4. Update the Generator

In `maze-generator.freemarker`, add a new generator class or update an existing one to:
1. Load the XMI model file
2. Transform EMF objects to a FreeMarker data model (Map)
3. Process the template and write output

---

## ✅ Development Workflow & Best Practices

This section documents critical steps learned from implementing MDE in this project.

### Step 1: Verify EMF Model Interfaces First

**⚠️ Critical**: Before writing templates that call model methods, verify the actual method names in the EMF-generated interfaces.

```java
// ❌ WRONG - Assumed method names without checking
character.getDamage()    // Does not exist!
character.getImage()     // Does not exist!

// ✅ CORRECT - Actual EMF-generated methods
character.getThreatLevel()   // From opponents.ecore
character.getImageBase()     // From opponents.ecore
```

**How to verify:**
1. Open the generated EMF interface (e.g., `CharacterType.java` in `main.game.maze.opponents`)
2. Check the actual getter/setter method names
3. Use these exact names in your templates

| Model Attribute | EMF Generated Method |
|-----------------|---------------------|
| `threatLevel` | `getThreatLevel()` / `setThreatLevel()` |
| `imageBase` | `getImageBase()` |
| `health` | `getHealth()` / `setHealth()` |
| `attackDamage` | `getAttackDamage()` / `setAttackDamage()` |
| `speed` | `getSpeed()` / `setSpeed()` |
| `animationFrameCount` | `getAnimationFrameCount()` / `setAnimationFrameCount()` |
| `spriteScale` | `getSpriteScale()` / `setSpriteScale()` |

### Step 2: Write Unit Tests for Generated Code

Generated code should be validated with JUnit tests to catch issues early.

**Test location**: `maze-module-generator/src/test/java/`

**Example test structure**:
```java
@Test
void testCharacterAttributeSetter() {
    // Test that generated code uses correct EMF methods
    double baseThreat = CharacterAttributeSetter.getBaseThreatLevel("Zombie");
    assertThat(baseThreat).isGreaterThan(0);
    
    int baseHealth = CharacterAttributeSetter.getBaseHealth("Ghost");
    assertThat(baseHealth).isEqualTo(50);
}
```

**Run tests**:
```bash
mvn -pl maze-module-generator test
```

### Step 3: Build and Verify

Always run a full build after template changes:

```bash
# Full build with tests
mvn clean verify

# Quick rebuild of generator module only
mvn -pl maze-module-generator -am clean verify
```

**Check for**:
- Compilation errors in generated code
- Test failures
- Missing imports or incorrect method calls

### Step 4: Integrate with Existing Runtime

After generating code, integrate it with the existing runtime:

| Generated Class | Integrates With | Purpose |
|-----------------|-----------------|---------|
| `CharacterRegistrar` | `OpponentRuntimeFactory` | Delegate registration logic |
| `CharacterAttributeSetter` | Difficulty system | Apply multipliers |
| `BehaviorDispatcher` | `PatrolHelper` | Route behaviour calls |

**Example integration** (in `OpponentRuntimeFactory.java`):
```java
// Before: Manual registration
registerCharacter("zombie", new ZombieConfig(...));

// After: Delegate to generated code
CharacterRegistrar.registerAll(this);
```

### Common Pitfalls

| Pitfall | Symptom | Solution |
|---------|---------|----------|
| Wrong method names | `method not found` compilation error | Check EMF interface for actual method names |
| Missing template | `Template not found` error | Check templates are in `src/main/resources/templates/` |
| Stale generated code | Tests pass but runtime fails | Delete `src-gen/` and regenerate |
| Type mismatches | `incompatible types` error | Verify data model passes correct types |

---

## 🔍 Troubleshooting

### No Files Generated

**Symptom**: Generator runs but no files appear.

**Solutions**:
- Check that the model file path is correct
- Verify the root element type matches expected EMF package
- Look for errors in the Maven output
- Ensure FreeMarker templates exist in `src/main/resources/templates/`

### Template Not Found

**Symptom**: `TemplateNotFoundException: Template not found`

**Solutions**:
- Verify templates are in the correct directory under `src/main/resources/templates/`
- Check the template path in the Java generator code
- Ensure templates are included in `build.properties` as `bin.includes`

### Compilation Errors in Generated Code

**Symptom**: Generated `.java` files have syntax errors.

**Solutions**:
- Review the FreeMarker template for missing imports
- Check that data model properties exist and have expected names
- Verify FreeMarker expressions use correct syntax (e.g., `${property}` not `$property`)

### Model Loading Errors

**Symptom**: `Resource not found` or `Unknown package URI`

**Solutions**:
- Register the EPackage in the generator:
  ```java
  EPackage.Registry.INSTANCE.put(MyPackage.eNS_URI, MyPackage.eINSTANCE);
  ```
- Ensure `.xmi` files reference the correct namespace URI

### Model Validation Errors

**Symptom**: Generator fails with `IllegalStateException` about missing required fields.

The FreeMarker generators validate models before generation and fail fast with clear error messages:

| Error Message | Cause | Solution |
|---------------|-------|----------|
| `Wall model has no materials defined` | Empty `WallModel` | Add at least one `WallMaterial` to the model |
| `WallMaterial at index N has null or blank 'id'` | Missing wall material ID | Set the `id` attribute on all materials |
| `Expected OpponentModel, got: X` | Wrong model type | Ensure XMI file contains correct root element |

**Warnings** (generation continues with defaults):
- `Material 'X' has null wallBaseType` → Uses `STEEL` as default
- `Material 'X' has null/blank baseImage` → Uses `/images/walls/default_wall.png`
- `Material 'X' has null/blank displayName` → Uses `"Unknown Wall"`
- `OpponentModel has null/blank 'name'` → Uses `"MazeGame"`
- `CharacterType has null/blank displayName` → Uses `"Unknown Enemy"`

These warnings appear in build output and indicate the model should be fixed, but generation completes successfully.

### Null-Safety and Default Values

The generators use null-safe defaults to prevent NPEs when model fields are missing:

**Walls Generator (`RunWallsAcceleo.java`):**
| Field | Default Value |
|-------|---------------|
| `wallBaseType` | `WallMaterialBaseType.STEEL` |
| `baseImage` | `/images/walls/default_wall.png` |
| `displayName` | `"Unknown Wall"` |

**Opponents Generator (`RunAcceleo.java`):**
| Field | Default Value |
|-------|---------------|
| `model.name` | `"MazeGame"` |
| `displayName` | `"Unknown Enemy"` |
| `imageBase` | Type-specific default (e.g., `/images/zombie_default.png`) |

Special characters in model strings are escaped to prevent invalid Java output.

---

## 📊 Domain Models Overview

| Model | Namespace URI | Contains |
|-------|--------------|----------|
| **Opponents** | `http://main.game.maze/opponents` | CharacterType, Zombie, Ghost, PumpkinBomber |
| **Difficulties** | `http://main.game.maze/difficulties` | Difficulty, EasyDifficulty, NormalDifficulty, HardDifficulty |
| **Walls** | `http://main.game.maze/walls` | WallMaterial, WallMaterialBaseType (GLASS, DIRT, WOOD, STONE, STEEL) |
| **Behaviour** | `http://main.game.maze/behaviour` | MovementBehavior, PatrolBehavior, ChaseBehavior, RandomBehavior |

---

## 🎮 Domain Models In-Depth

This section provides detailed documentation of each EMF domain model, how they relate to game mechanics, and how Acceleo templates generate code from them.

### Opponents Model (`opponents.ecore`)

**Location**: `main.game.maze.opponents/src/main/resources/opponents.ecore`  
**Namespace URI**: `http://main.game.maze/opponents`

The opponents model defines all enemy types in the game. It uses an inheritance hierarchy with a common `CharacterType` abstract base class.

#### Class Hierarchy

```
CharacterType (abstract)
├── Zombie          - Melee attacker with infection mechanics
├── Ghost           - Phasing enemy that can pass through walls
└── RangedEnemy (abstract)
    └── PumpkinBomber - Throws explosive projectiles
```

#### CharacterType (Base Class)

All enemies share these attributes:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `id` | String | — | Unique identifier |
| `displayName` | String | — | Human-readable name |
| `enabled` | boolean | `true` | Whether this enemy spawns |
| `health` | int | `100` | Hit points |
| `speed` | double | `1.0` | Movement speed multiplier |
| `threatLevel` | double | `0.0` | Danger rating (affects difficulty) |
| `ImageBase` | String | `/main/game/maze/zombie.png` | Main sprite |
| `ImageTurnLeft/Right/Up/Down` | String | — | Directional sprites |
| `animationFrameCount` | int | `1` | Number of animation frames |
| `spriteScale` | double | `1.0` | Sprite scale factor |
| `behavior` | BehaviorType | `WANDER` | AI behavior pattern |

**BehaviorType Enum**: `PASSIVE`, `WANDER`, `AGGRESSIVE`, `PATROL`

#### Zombie

Melee attacker that can infect and resurrect.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackDamage` | int | `10` | Damage per hit |
| `infectionLevel` | int | `1` | Infection strength (1-10) |
| `resurrectionTime` | int | `0` | Seconds until respawn (0 = no respawn) |
| `touchSound` | String | `.../zombieScream.mp3` | Sound on player contact |
| `zombieLootTable` | LootTable | — | Items dropped on death |

#### Ghost

Phasing enemy with variable visibility.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackDamage` | int | `1` | Damage per hit |
| `visibilityLevel` | int | `100` | Opacity percentage (0 = invisible) |
| `nonTangibilityEnergy` | double | `100` | Energy for wall phasing |

#### PumpkinBomber (extends RangedEnemy)

Ranged attacker with explosive projectiles.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackRange` | double | `50` | Maximum attack distance |
| `attackCooldownMs` | int | `10000` | Milliseconds between attacks |
| `attackDamage` | int | `1` | Base damage |
| `projectileSpeed` | double | `0` | Projectile velocity |
| `projectileType` | ProjectileType | — | `STRAIGHT`, `LOB`, or `BEAM` |
| `splashRadius` | double | — | Explosion area of effect |
| `arcHeight` | double | — | Arc height for `LOB` projectiles |

#### LootTable System

Enemies can drop items on death:

```
LootTable
├── weightCapacity: int (1-100)
└── items: LootItem[]
    ├── name: String
    ├── type: LootItemType (FOOD, BOMB, TRAP, WEAPON)
    ├── value: int
    └── weight: int (1-10)
```

#### Generated Code from Opponents

The FreeMarker templates in `templates/opponents/` produce:

| Generated Class | Template | Purpose |
|-----------------|----------|----------|
| `OpponentRegistry` | `OpponentRegistry.ftl` | Lists all enemies, provides counts |
| `CharacterRegistrar` | `CharacterRegistrar.ftl` | Type-safe switch dispatch for registration |
| `CharacterAttributeSetter` | `CharacterAttributeSetter.ftl` | Applies difficulty multipliers |
| `CharacterGraphicsFactory` | `CharacterGraphicsFactory.ftl` | Creates sprites from model attributes |

**Example: How CharacterRegistrar eliminates instanceof**

```ftl
<#-- From CharacterRegistrar.ftl - generates switch cases for each type -->
switch (typeName) {
    <#list uniqueEnemies as enemy>
    case "${enemy.typeName}" -> ${enemy.typeName?uncap_first}Handler.register((${enemy.typeName}) character);
    </#list>
    default -> LOGGER.warning("Unknown type: " + typeName);
}
```

---

### Walls Model (`walls.ecore`)

**Location**: `main.game.maze.walls/model/walls.ecore`  
**Namespace URI**: `http://main.game.maze/walls`

The walls model defines maze wall materials with varying durability and visual properties.

#### WallMaterialBaseType Enum

| Value | Ordinal | Characteristics |
|-------|---------|-----------------|
| `GLASS` | 0 | Fragile, transparent |
| `DIRT` | 1 | Very weak, easily broken |
| `WOOD` | 2 | Burnable, moderate strength |
| `STONE` | 3 | Strong, standard wall |
| `STEEL` | 4 | Unbreakable (default) |

#### WallMaterial Class

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `id` | String | (derived) | Unique identifier |
| `displayName` | String | — | Human-readable name |
| `wallBaseType` | WallMaterialBaseType | `STEEL` | Material category |
| `breakable` | boolean | `false` | Can be destroyed |
| `hitPoints` | int | `0` | Damage to break (0 if unbreakable) |
| `baseImage` | String | `/main/game/maze/baseWall.png` | Visual texture |

**OCL Constraint** (enforced at model level):
```ocl
(breakable = false implies hitPoints = 0) and
(breakable = true implies hitPoints > 0)
```

#### Generated Code from Walls

The FreeMarker templates in `templates/walls/` produce:

| Generated Class | Template | Purpose |
|-----------------|----------|----------|
| `WallRegistry` | `WallRegistry.ftl` | Static registry of all wall definitions |
| `WallMaterialRenderer` | `WallMaterialRenderer.ftl` | Visual properties per material |
| `WallCollisionHandler` | `WallCollisionHandler.ftl` | Collision/damage logic per material |

**Example: WallRegistry generation**

```ftl
<#-- From WallRegistry.ftl - loops over all materials -->
static {
    <#list materials as m>
    register(new WallDefinition(
        "${m.id}",
        "${m.displayName}",
        "${m.wallBaseType}", 
        ${m.breakable?c},
        ${m.hitPoints},
        "${m.baseImage}"
    ));
    </#list>
}
```

---

### Difficulties Model (`difficulty-module.ecore`)

**Location**: `main.game.maze.difficulties/src/main/resources/difficulty-module.ecore`  
**Namespace URI**: `http://main.game.maze/difficulties`

The difficulties model defines game difficulty levels with multipliers affecting enemy stats.

#### Difficulty Class Hierarchy

```
Difficulty (abstract)
├── EasyDifficulty
├── NormalDifficulty
└── HardDifficulty
```

#### Difficulty (Base Class)

| Attribute | Type | Description |
|-----------|------|-------------|
| `instantDeath` | boolean | One-hit kills player |
| `monstersMovementSpeedMultiplier` | double | Speed modifier (1.0 = normal) |
| `monstersDamageMultiplier` | double | Damage modifier |
| `maxThreat` | int | Max combined enemy threat allowed |
| `enemyMaxCount` | EnemyMaxCount[] | Per-type spawn limits |

#### EnemyMaxCount

| Attribute | Type | Description |
|-----------|------|-------------|
| `type` | EnemyTypes | `ZOMBIE`, `GHOST`, or `PUMPKINBOMBER` |
| `maxCount` | int | Maximum simultaneous spawns |

#### Generated Code from Difficulties

FreeMarker templates can produce (planned):

| Generated Class | Purpose |
|-----------------|---------|
| `DifficultyConfigurator` | Applies multipliers based on difficulty |
| `DifficultySettings` | Immutable record of applied settings |
| `EnemySpawnLimits` | Per-enemy spawn limits by difficulty |

**Example: Difficulty key resolution (FreeMarker)**

```ftl
<#-- Example difficulties template -->
public static String getDifficultyKey(Difficulty difficulty) {
    return switch (difficulty.eClass().getName()) {
        <#list difficulties as diff>
        case "${diff.className}" -> "${diff.className?lower_case}";
        </#list>
        default -> "normal";
    };
}
```

---

### Behaviour Model (`movements.ecore`)

**Location**: `main.game.maze.behaviour/src/main/resources/movements/movements.ecore`  
**Namespace URI**: `http://main.game.maze/behaviour`

The behaviour model defines enemy AI movement patterns. It references the opponents model for character binding.

#### MovementBehavior Class Hierarchy

```
MovementBehavior (abstract)
├── RandomBehavior   - Wanders aimlessly with HP regen
├── PatrolBehavior   - Follows predefined patrol points
└── ChaseBehavior    - Pursues the player
```

#### MovementBehavior (Base Class)

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `charactertype` | CharacterType | — | Linked enemy instance |
| `ignoreWalls` | boolean | (derived) | True if Ghost with energy |
| `instantKillOnCollision` | boolean | (derived) | True if threatLevel > 100 |
| `baseVisionRange` | double | `100` | Base sight distance |
| `additionalVisionRange` | double | `100` | Bonus sight distance |
| `visionRangeMultiplier` | double | `1` | Vision scaling factor |
| `visionRange` | double | (derived) | `base * mult + additional` |
| `position` | Position | — | Current location |
| `direction` | Direction | — | Current heading |
| `nextPositions` | Position[] | — | Planned movement path |

**OCL-Derived `ignoreWalls`** (Ghost phasing):
```ocl
if self.charactertype.oclIsKindOf(Ghost) then
    ghostCharacter.nonTangibilityEnergy >= 0
else false endif
```

#### RandomBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `regenPerSecond` | double | HP recovered per second while wandering |

#### PatrolBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `path` | PatrolPoint[] | Ordered patrol waypoints |
| `currentIndex` | int | Current position in path |
| `pathcalculator` | PathCalculator | Navigation algorithm |
| `behavior` | PatrolPathBehavior | Loop/pingpong/once |
| `patrolZone` | PatrolZone | Optional bounding area |

**PatrolPoint** contains:
- `point`: Position (target coordinates)
- `events`: Event[] (things that happen at this waypoint)

**Event Types**: `HealthEvent`, `SpeedEvent`, `TimeEvent`, `AttackEvent`, `VisionEvent`

#### ChaseBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `relativePositionTarget` | Position | Offset from player to target |
| `pathcalculator` | PathCalculator | Navigation algorithm |

#### PathCalculator Types

```
PathCalculator (abstract)
├── BFSPathCalculator     - Breadth-first search
├── DijkstraPathCalculator - Weighted shortest path
└── AStarPathCalculator   - Heuristic-based pathfinding
```

#### Generated Code from Behaviour

FreeMarker templates can produce (planned):

| Generated Class | Purpose |
|-----------------|---------|
| `BehaviorDispatcher` | Routes to type-specific movement executors |
| `PathCalculatorFactory` | Creates path calculators by type |

**Example: BehaviorDispatcher (FreeMarker)**

```ftl
<#-- Example behaviour template -->
public static void dispatch(
        MovementBehavior behavior,
        double deltaTime,
        BehaviorExecutor randomExecutor,
        BehaviorExecutor patrolExecutor,
        BehaviorExecutor chaseExecutor) {
    
    switch (behavior.eClass().getName()) {
        <#list behaviorTypes as bt>
        case "${bt.name}" -> ${bt.name?uncap_first}Executor.execute(behavior, deltaTime);
        </#list>
        default -> LOGGER.warning("Unknown behavior: " + behavior);
    }
}
```

---

## 🔧 FreeMarker Template Architecture

### Template Organization

```
maze-generator.freemarker/src/main/resources/templates/
├── opponents/
│   ├── OpponentRegistry.ftl           # Lists all enemy types
│   ├── CharacterRegistrar.ftl         # Type dispatch with handlers
│   ├── CharacterAttributeSetter.ftl   # Difficulty multipliers
│   └── CharacterGraphicsFactory.ftl   # Sprite/graphics factory
└── walls/
    ├── WallRegistry.ftl               # Wall material registry
    ├── WallMaterialRenderer.ftl       # Color, sound, transparency
    └── WallCollisionHandler.ftl       # Damage, resistance
```

### Common Template Patterns

#### Pattern 1: Type-Safe Switch Generation

Eliminates `instanceof` chains:

```ftl
switch (typeName) {
    <#list types as type>
    case "${type.name}" -> handle${type.name}((${type.name}) obj);
    </#list>
    default -> handleUnknown(obj);
}
```

#### Pattern 2: Registry Population

Creates static lookup tables:

```ftl
static {
    <#list items as item>
    register("${item.id}", new Definition(
        ${item.property1},
        "${item.property2}",
        ${item.booleanProperty?c}
    ));
    </#list>
}
```

#### Pattern 3: Constant Generation

Exports model values as Java constants:

```ftl
<#list constants as constant>
public static final ${constant.type} ${constant.name?upper_case} = ${constant.value};
</#list>
```

### Generator Architecture

The Java generators transform EMF models to FreeMarker data models:

```java
// In RunAcceleo.java
Map<String, Object> dataModel = new HashMap<>();
dataModel.put("gameName", model.getName());
dataModel.put("enemies", transformEnemies(model.getCharacterTypes()));
dataModel.put("uniqueEnemies", getUniqueEnemyTypes(model));

Template template = cfg.getTemplate("opponents/CharacterRegistrar.ftl");
template.process(dataModel, writer);
```

This allows the behaviour model to access opponent types, enabling constraints like:
- Ghost's `ignoreWalls` derived from `nonTangibilityEnergy`
- RangedEnemy's attack range validation against vision range

---

## 🔗 Related Projects

- **[Model-Driven Code Generation Plan](../readme-mddcodegeneration.md)** - Comprehensive plan for MDE code generation across all domains
- [maze-generator.freemarker-runner](../maze-generator.freemarker-runner/readme.md) - Headless runner for Maven builds
- [maze-module-generator](../maze-module-generator/readme.md) - Contains generated sources and **unit tests** for MDE validation
- [main.game.maze.opponents](../main.game.maze.opponents/readme.md) - Opponents EMF model (`CharacterType`, `Zombie`, `Ghost`, `PumpkinBomber`)
- [main.game.maze.walls](../main.game.maze.walls/readme.md) - Walls EMF model (`WallMaterial`, `WallMaterialBaseType`)
- [main.game.maze.difficulties](../main.game.maze.difficulties/readme.md) - Difficulties EMF model (`Difficulty`, `EasyDifficulty`, etc.)
- [main.game.maze.behaviour](../main.game.maze.behaviour/readme.md) - Behaviour EMF model (`MovementBehavior`, `PatrolBehavior`, etc.)

---

## 📚 Glossary

| Term | Definition |
|------|------------|
| **FreeMarker** | Apache template engine for generating text output (HTML, code, etc.) |
| **EMF** | Eclipse Modeling Framework - foundation for defining models |
| **Ecore** | The metamodel used by EMF (like a schema for models) |
| **XMI** | XML format for storing model instances |
| **FTL** | FreeMarker Template Language - `.ftl` file extension |
| **MDE** | Model-Driven Engineering - using models as primary artifacts |
| **M2T** | Model-to-Text transformation (what FreeMarker does) |
| **Tycho** | Maven plugin for building Eclipse plugins |
| **Acceleo** | (Historical) Eclipse-based M2T framework - predecessor to current FreeMarker approach |

---

## 🔄 End-to-End Workflow Summary

Here's the complete workflow for making model-driven changes:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  1. PLAN: Decide what to change                                             │
│     • New enemy type? → Edit opponents.ecore                                │
│     • New wall material? → Edit walls.ecore                                 │
│     • New attribute for all types? → Edit .ecore + update .ftl template     │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  2. VERIFY: Check EMF interface methods                                     │
│     • Open generated interface (e.g., CharacterType.java)                   │
│     • Note exact method names (getThreatLevel, NOT getDamage)               │
│     • Update FreeMarker template data model if needed                       │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  3. GENERATE: Run the code generator                                        │
│     mvn -pl maze-module-generator -am clean verify                          │
│     • Output appears in maze-module-generator/src-gen/                      │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  4. TEST: Validate generated code                                           │
│     mvn -pl maze-module-generator test                                      │
│     • Fix any compilation errors (wrong method names?)                      │
│     • Fix any test failures                                                 │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  5. INTEGRATE: Wire generated code into runtime                             │
│     • Update OpponentRuntimeFactory to delegate to CharacterRegistrar       │
│     • Update PatrolHelper to use BehaviorDispatcher                         │
│     • Run full build: mvn clean verify                                      │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  6. COMMIT: Save all changes                                                │
│     • Commit .ecore model changes                                           │
│     • Commit .ftl template files (if modified)                              │
│     • Commit generated src-gen/ files (for builds without generation)       │
│     • Commit updated tests                                                  │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

*By keeping all model-to-code generation logic in `maze-generator.freemarker`, the MazeGame project maintains a clean, reproducible, and model-driven build pipeline using FreeMarker templates.*

