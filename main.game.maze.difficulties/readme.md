# main.game.maze.difficulties

This module defines the difficulty system for MazeGame.  
It decides what Easy, Normal and Hard actually mean in terms of enemies, pacing, board size, player resources and scoring.

Where other modules describe what objects exist in the game, this module describes how demanding the game should feel on each difficulty level.

---

## Purpose

The difficulty module provides a single place to control

- how many and which opponents can appear  
- how strong, fast or aggressive opponents are  
- how large or dense the maze is  
- how generous the game is with lives, time and power ups  
- how scores and rewards scale with risk  

By centralising these rules, the game can stay consistent, and you can tune difficulty without rewriting behaviour or maze logic.

---

## Conceptual Structure

The module is typically built around three layers.

### Difficulty model

An EMF based model describes the structure of a difficulty level.  
A difficulty entry usually contains information such as

- an identifier and display name  
- minimum and maximum threat or danger level  
- per opponent type caps, for example how many ghosts or zombies may be active  
- multipliers for opponent speed, health or damage  
- maze related parameters such as target board size, corridor density or dead end preference  
- player related parameters such as lives, hit points or resource caps  
- scoring multipliers that reward higher difficulty with higher scores  

The model is the main source of truth for what a difficulty level is.

### Generated and configuration based code

From the model and accompanying configuration files, the build generates supporting code and constants.  
This typically includes types or data structures that mirror the difficulty definitions in plain Java so they are easy to use at runtime.

Hand written glue code then reads these generated definitions and exposes them as services.

### Runtime services

At runtime the rest of the game does not talk to the model directly.  
Instead it uses a small set of services, for example a DifficultyService, that provide

- the current difficulty object  
- helper methods for looking up caps and multipliers  
- convenient accessors for values such as maximum threat or maximum number of a given opponent type  

This keeps callers simple and avoids spreading model details across the game.

---

## Main Responsibilities

The difficulty module takes responsibility for several related concerns.

### Threat and composition limits

The module defines how dangerous a level is allowed to be.  
Typical values include

- global maximum threat for a level  
- threat contribution per opponent type  
- caps on how many opponents of each type may be active at once  

Generator and opponent spawning code use these numbers to decide when to stop adding enemies and how to mix opponent types so that the total threat remains within the difficulty budget.

### Opponent scaling

Each difficulty defines how opponents are scaled relative to their base configuration.  
This can include

- speed multipliers  
- health and damage multipliers  
- perception or detection radius adjustments  
- reaction time or cooldown adjustments  

The result is that the same opponent definition can feel very different on Easy and Hard while still using the same core behaviour.

### Maze and environment parameters

Difficulty also has a direct effect on the maze itself.  
Practically this can mean

- different typical board sizes  
- more or fewer dead ends  
- more open areas versus more narrow corridors  
- density of traps, power ups or special tiles  

The maze generator reads these parameters and builds levels that match the intended difficulty profile.

### Player resources and scoring

Finally, the difficulty module can influence how generous the game is towards the player and how much reward a given performance gives.  
This commonly covers

- number of lives or continues  
- amount of starting health or shield  
- limits for ammunition, keys or other consumables  
- score multipliers per difficulty  
- possible bonus rules for completing levels quickly or without taking damage  

This ensures that higher difficulty both feels more demanding and is more rewarding.

---

## Interaction With Other Modules

The difficulties module is connected to several other parts of the system.

- Maze world  
  It provides preferred board sizes and structural parameters that the maze world and generator use when constructing levels.

- Opponents  
  It defines caps and multipliers that the opponent module uses when instantiating enemies, so that the same base enemy behaves differently per difficulty.

- Behaviour  
  It can supply values such as detection range, patrol aggressiveness or chase persistence so that behaviours scale with difficulty.

- User interface  
  It provides the list of available difficulties, their names and descriptions for menus and selection screens.

The idea is that all these modules ask the difficulty module when they need to know “how hard should this be” rather than each implementing their own local rules.

---

## Choosing And Applying A Difficulty

The typical life cycle for a difficulty selection is

1. The player chooses a difficulty in a menu.  
2. The game records this as the current difficulty instance.  
3. When a new level is created, the maze generator, opponent factory and behaviour configuration read values from the current difficulty.  
4. During the game, systems that need scaled values, such as scoring or resource limits, continue to query the difficulty module.

Because all settings are derived from the current difficulty, switching difficulty simply means pointing the game at another difficulty instance.  
No further wiring is needed.

---

## Extending The Difficulty System

When you want to add or change a difficulty, the recommended workflow is

1. Update the EMF model and related configuration  
   Add a new difficulty entry or extend existing ones with new parameters.

2. Regenerate supporting code  
   Run the relevant generators so that new values become available at runtime.

3. Wire new parameters into the runtime services  
   Map the new model fields into properties or methods on the difficulty service.

4. Adopt the new parameters in the rest of the game  
   For example, let the maze generator use a new density value or let the behaviour module read a new aggression factor.

This keeps the difficulty system consistent and ensures that new settings are taken into account in a controlled way.

---

## Design Guidelines

When maintaining this module, a few guidelines help keep it robust and understandable.

- Keep difficulty definitions declarative  
  They should read like data that describes a profile, not like scripts or procedures.

- Avoid duplicating rules in other modules  
  If something is truly difficulty dependent, it should live here and be consumed elsewhere.

- Prefer relative scaling over hard coded values  
  For example, use “base value times multiplier” instead of completely separate numbers per difficulty when possible.

- Document the intent of each parameter  
  A short comment or description for each field makes tuning easier and reduces the risk of misinterpretation.

By following these ideas, `main.game.maze.difficulties` remains the central and reliable authority for how challenging MazeGame should be on each setting.
