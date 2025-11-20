# difficulty-module

EMF and OCL based model for game difficulty.  
Provides the `DifficultyGameData` meta-model, default values, sample XMI instances, and validation through OCL invariants.

## What you get

• Ecore model and generated EMF code  
• OCL constraints and derived attributes (via OCL Pivot)  
• Sample XMI files for smoke testing  
• JUnit tests that load, validate, and exercise the model


## What you get

• Ecore model and generated code
• OCL derived attributes and validations
• Sample XMI for smoke tests
• JUnit tests that load and validate the model

## Build and test

```bash
mvn clean verify
```

This compiles the bundle and runs the unit tests.

## Quick usage in code

```java
// Register EMF and XMI
Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
    .put("xmi", new XMIResourceFactoryImpl());
EPackage.Registry.INSTANCE.put(
    DifficultiesPackage.eNS_URI, DifficultiesPackage.eINSTANCE);

// Load the model
ResourceSet rs = new ResourceSetImpl();
URI uri = URI.createFileURI("src/test/resources/difficultiesBasic.xmi");
Resource r = rs.getResource(uri, true);
DifficultyGameData data = (DifficultyGameData) r.getContents().get(0);

// Validate with Diagnostician
Diagnostic diag = Diagnostician.INSTANCE.validate(data);
System.out.println(diag.getMessage());
```

## Key concepts

• Difficulty profiles such as Easy Normal Hard
• Enemy counts caps and multipliers
• A max threat constraint that guards total effective threat

## Where things live

• `model/` Ecore and OCL
• `src/main/java/` generated and hand written code
• `src/test/java/` unit tests
• `src/test/resources/` sample XMI

## Requirements

• Java 21 or newer
• Maven
• EMF and OCL are resolved through the Tycho target in the parent build

## Notes

This bundle is consumed by other game modules at runtime and inside tests.

## Key concepts

• Difficulty profiles such as Easy, Normal, Hard
• Enemy count limits, scaling factors, and multipliers
• A max-threat style constraint that guards total effective threat over all opponents
• Default values so XMI instances are usable with minimal configuration

## Where things live

• `model/` – Ecore model and OCL definitions
• `src/main/java/` – generated and hand-written EMF code
• `src/test/java/` – JUnit tests for loading and validation
• `src/test/resources/` – sample `.xmi` difficulty configurations

## Requirements

• Java 21 or newer (project root prefers JDK 21+ / 25)
• Maven
• EMF and OCL resolved via the Tycho target and local p2 mirror in `releng/local-p2` (when built as part of the MazeGame reactor)

## Role in MazeGame

This bundle defines the difficulty model used by:

• `opponents-module` for threat and balancing rules
• The Acceleo generator for producing game code from difficulty configurations
• The JavaFX game module (`maze`) at runtime through its generated JAR

