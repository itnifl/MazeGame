# MazeGame DSL Tests Module

Automated test suite for the MazeGame Domain-Specific Language.

## Purpose

This module contains **comprehensive tests** for the MazeGame DSL, covering parsing, validation, scoping, formatting, and code generation. Tests ensure the DSL behaves correctly and catches regressions when the grammar or generators change.

## Test Categories

| Category | Description | Example |
|----------|-------------|---------|
| **Parsing Tests** | Verify valid DSL files parse without errors | `MazeDslParsingTest` |
| **Validation Tests** | Verify constraints produce expected errors/warnings | `MazeDslValidationTest` |
| **Scoping Tests** | Verify cross-references resolve correctly | Reference resolution |
| **Formatting Tests** | Verify formatter produces consistent output | Whitespace, indentation |
| **Generator Tests** | Verify code generation produces expected output | Factory class generation |
| **Quickfix Tests** | Verify quick fixes apply correctly | `MazeDslQuickfixTest` |

## Module Structure

```
main.game.maze.dsl.tests/
├── META-INF/
│   └── MANIFEST.MF          # OSGi bundle manifest
├── src/
│   └── test/
│       ├── java/            # Test classes
│       │   └── main/game/maze/dsl/tests/
│       │       ├── MazeDslParsingTest.java
│       │       ├── MazeDslValidationTest.java
│       │       ├── MazeDslQuickfixTest.java
│       │       └── MazeDslGeneratorTest.java
│       └── xtext-gen/       # Generated test infrastructure
├── build.properties         # Tycho build configuration
├── plugin.properties        # Bundle metadata
└── pom.xml                  # Maven/Tycho build file
```

## Running Tests

### From Maven

Run all DSL tests:

```bash
mvn -f main.game.maze.dsl.tests/pom.xml test
```

Or as part of the full build:

```bash
mvn clean verify
```

### From IDE

Tests use JUnit 5 and can be run directly from Eclipse or VS Code using the standard test runners.

## Writing New Tests

### Parsing Test Example

```java
@ExtendWith(InjectionExtension.class)
@InjectWith(MazeDslInjectorProvider.class)
class MazeDslParsingTest {
    
    @Inject
    ParseHelper<GameConfiguration> parseHelper;
    
    @Test
    void parseSimpleGame() throws Exception {
        var model = parseHelper.parse("""
            game TestLevel {
                difficulty { level normal }
            }
            """);
        assertNotNull(model);
        assertEquals("TestLevel", model.getName());
    }
}
```

### Validation Test Example

```java
@Test
void validateHealthMustBePositive() throws Exception {
    var model = parseHelper.parse("""
        game Test {
            opponent Bad { type zombie health -10 }
        }
        """);
    validationHelper.assertError(model, 
        MazeDslPackage.Literals.OPPONENT_DEFINITION,
        MazeDslValidator.INVALID_HEALTH);
}
```

## Test Fixtures

Test fixture files (`.mazegame` examples) are located in:
- `src/test/resources/` - Sample DSL files for parsing tests
- Inline strings in test methods for focused unit tests

## Dependencies

- `main.game.maze.dsl` - Core grammar and runtime
- Xtext testing libraries (2.42.0)
- JUnit 5
- AssertJ (for fluent assertions)

## Related Documentation

| Document | Description |
|----------|-------------|
| [DSL Core Module](../main.game.maze.dsl/readme.md) | Core grammar and runtime |
| [DSL Reference](../docs/dsl-reference.md) | Complete syntax documentation |
| [Xtext Setup Guide](../docs/xtext-readme.md) | Build and development setup |
