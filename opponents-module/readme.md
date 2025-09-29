TDT4250 – Assignment 2: Variability Modeling for Maze Game Opponents
====================================================================

This repo contains the Ecore metamodel, generated code, runtime wiring, and example configurations (XMI) for introducing **variability in opponents** (e.g., Zombies) for the Maze game from Assignment 1.

Contents
-------------------------------------

*   **opponents-module/** – EMF **metamodel project** (Ecore + GenModel) and **generated Java code** for the variability domain (OpponentModel, CharacterType, Zombie, BehaviorType, …). Also exports the model’s runtime API.
    
*   **maze/** (a.k.a. _maze-base_ in pom.xml) – the JavaFX game. Contains:
    
    *   The **runtime factory** that **loads XMI** and spawns opponents into the game (OpponentRuntimeFactory).
        
    *   Resources (FXML, images, and **XMI instances** under src/main/resources/opponents/instances/).
        
*   **Example XMI instances** – configurations of the SPL feature “Opponents” (e.g., **classic\_zombie.xmi**) NB! Please set maxThreat to less then the total threat of the characters in **classic\_zombie.xmi** to test that the game actually uses the constraints created in the opponents-module - for instance st it to 1. Then start the game in Visual Studio Code and observe that the game will not load. Then set maxThreat back to 10 in **classic\_zombie.xmi** and start the game in Visual Studio Code and observe that the game will load.

How to run the game is described in /readme.md at the base of this repo.
    
*   **Maven multi-module build** with JPMS module-info.java for both modules.
    
<br>

A short summary of the **Assignment requirements** and where they are satisfied:
---------------------------------------------------------------------------------

| Requirement | Where / How |
|---|---|
| Ecore-metamodell for one variability aspect | `opponents-module/src/main/resources/opponents/opponents.ecore` |
| Generated Java-kode | `opponents-module/src/main/java/main/game/maze/opponents/**` (+ `…/impl`, `…/util`) |
| ≥1 **derived property** | `CharacterType.effectiveThreat : EDouble` (derived, transient, changeable=false) |
| ≥1 **non-triviell constraint** | Invariant `validateMaxThreat` on `OpponentModel` (OCL; sum(effectiveThreat) ≤ maxThreat) |
| Representative model examples (XMI) | `maze/src/main/resources/opponents/instances/*.xmi` |
| Short domain description | See below |
| Repo-structure + import/run | This README |

The model examples are two different implemetations of zombies, with different game properties and different graphics.

<br>

Domain description (ties back to Assignment 1)
----------------------------------------------

**Game domain**: A 2D **Maze game** with non-player **opponents**. Opponents move autonomously, can damage the player on contact, and have tunable properties (speed, health, behavior, damage).

**Variability aspect** (SPL feature): **Opponent types** and **their parameters** (e.g., enabled/disabled, health, speed, threat level, behavior, images). Different products/configurations can **combine and tune** opponents to create different difficulty modes.

**Metamodel focus**: We model the **configuration space** (domain engineering), not the actual game classes (application engineering) who are already created. The metamodel captures:

*   A **product configuration**: OpponentModel with a name, a maxThreat budget, and a set of characterTypes.
    
*   **Abstract type**: CharacterType (id, displayName, enabled, health, speed, threatLevel, **derived** effectiveThreat).
    
*   **Concrete subtype**: Zombie (adds attackDamage, behavior : BehaviorType, and image attributes used by the runtime for graphics).
    
<br>

Metamodel overview
------------------

**Main classes and relationships**

*   OpponentModel
    
    *   name : EString
        
    *   maxThreat : EDouble
        
    *   characterTypes : CharacterType\[\*\] (composition)
        
*   CharacterType (abstract)
    
    *   id : EString (unique logical id)
        
    *   displayName : EString
        
    *   enabled : EBoolean
        
    *   health : EInt
        
    *   speed : EDouble
        
    *   threatLevel : EDouble
        
    *   effectiveThreat : EDouble **(derived)**
        
*   Zombie extends CharacterType
    
    *   attackDamage : EInt
        
    *   behavior : BehaviorType (enum: e.g., WANDER, AGGRESSIVE, runtime code is not using these properties yet). 
        
    *   imageBase, imageTurnLeft, imageTurnRight, imageTurnUp, imageTurnDown : EString (resource paths used by the game)
        
*   BehaviorType (enum)
    

**Derived property**

*   CharacterType.effectiveThreat (EDouble) is **derived** with an OCL body (package delegates enabled):
    
    *   Properties: derived=true, transient=true, changeable=false (optionally volatile=true)
        
    *   if self.enabled then self.threatLevel \* (self.health.toReal() / 100.0)else 0.0endif
        
    *   Intuition: **effective** contribution to the level difficulty. Higher health → higher effective threat. Disabled types contribute 0.
        

**Constraint (non-trivial invariant)**

*   self.characterTypes->collect(ct | ct.effectiveThreat)->sum() <= self.maxThreat
    
    *   Meaning: the **sum of effective threats** must fit within the **budget** maxThreat for that configuration.
        
        
<br>

Repository structure
--------------------

```
MazeGame/
├─ pom.xml                          # parent (packaging=pom)
├─ opponents-module/
│  ├─ pom.xml
│  ├─ src/
│  │  ├─ main/java/module-info.java             # module main.game.maze.opponents
│  │  ├─ main/java/main/game/maze/opponents/**  # generert modellkode
│  │  │   ├─ impl/**, util/**, …
│  │  └─ main/resources/opponents/
│  │      ├─ opponents.ecore                    # metamodell
│  │      └─ opponents.genmodel                 # codegen-oppsett
│  └─ target/opponents-module-1.jar
└─ maze/
   ├─ pom.xml                       # artifactId: maze-base
   ├─ src/
   │  ├─ main/java/module-info.java # module main.game.maze
   │  ├─ main/java/main/game/maze/**            # JavaFX-spill
   │  │   └─ runtime/opponents/OpponentRuntimeFactory.java
   │  ├─ main/resources/
   │  │  ├─ main/game/maze/*.fxml, *.png        # UI + sprites
   │  │  └─ opponents/instances/*.xmi           # XMI-instansene
   │  └─ test/java/**                           # tester
   └─ target/maze-base-1.jar

```

<br>

Build & run
-----------

### Prerequisites

*   JDK **24**
    
*   Maven **3.9+**
    
*   JavaFX 21 (resolved via Maven in maze/pom.xml)
    

### Build everything

```   # from repo root  mvn -U clean package   ```

### Run the game (Maven)

See readme.md at the root of this repo.

<br>

Using the model at runtime
--------------------------

### Where the XMI instances live

Runtime looks up instances on the classpath at:/opponents/instances/*.xmi (e.g., classic\_zombie.xmi) in the maze-base module at /maze.
They are placed them in the **game** module (so they are always available at runtime):maze/src/main/resources/opponents/instances/classic\_zombie.xmi

### Loading + validation (runtime)

maze/src/main/java/main/game/maze/runtime/opponents/OpponentRuntimeFactory.java:

*   Loads the XMI from classpath (/opponents/instances/classic\_zombie.xmi).
    
*   **Validates** the model **before** spawning opponents (using OpponentsValidator or Diagnostician).
    
*   Spawns each enabled Zombie with sprite images and mapped movement behavior.
    
<BR>

Generated code
----------------------------------

From opponents.genmodel (model directory set to src/main/java, basePackage = main.game.maze.opponents) EMF generated:

*   **API**: main.game.maze.opponents.\*
    
*   **Impl**: main.game.maze.opponents.impl.\*
    
*   **Factory & Package**: OpponentsFactory, OpponentsPackage
    
*   **Validator**: main.game.maze.opponents.util.OpponentsValidator
    
 <br>       

How the game uses the model
---------------------------

*   The game loads an OpponentModel XMI at startup.
    
*   For each Zombie configuration, the factory:
    
    *   Builds a JavaFX Node (ImageView) with paths from the model (imageTurnLeft/Right/Up/Down).
        
    *   Instantiates a ZombieCharacter (mapped from model speed, attackDamage, behavior).
        
    *   Registers it with the game loop and collision system (same pattern as GhostCharacter that came with the code from before).
        

This **decouples** **configuration** (metamodel/XMI) from **implementation** (game code). Changing the product is a matter of editing XMI (or building a small editor) rather than changing Java.

<br>

Testing & validation
--------------------

*  Please see unit tests and run this command at root (mvn must be installed):
``` 
mvn test
```

For non-generated code see:

CharacterTypeImpl.java
java```
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getEffectiveThreat() {
		// Defensive defaults
		int baseThreatLevel = (int) Math.round(this.getThreatLevel());
		int healthPercent = Math.clamp(getHealth(), 0, 100);
		double behaviorMultiplier = 1.0; //There is no implementation of behavior here.		
		double computed = (baseThreatLevel * (healthPercent / 100.0)) * behaviorMultiplier;
		return Math.max(0, (int)Math.round(computed));
	}
```

ZombieImpl.java
java```
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getEffectiveThreat() {
		// Defensive defaults
		int baseThreatLevel = (int) Math.round(this.getThreatLevel());
		int healthPercent = Math.clamp(getHealth(), 0, 100);

		int behaviorMultiplier = 1;
		var currentBehavior = this.getBehavior();
		behaviorMultiplier = currentBehavior.getValue();
		double computed = (baseThreatLevel * (healthPercent / 100.0)) * behaviorMultiplier;
		return Math.max(0, (int)Math.round(computed));
	}
```
For usage of the MDD Models created here in this assignment (Zombie Opponents and the Opponent domain), see:
[OpponentRuntimeFactory.java](/maze/src/main/java/main/game/maze/runtime/opponents/OpponentRuntimeFactory.java)
