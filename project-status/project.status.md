# Project Instructions
## Objective

The goal is to practice the techniques and tools covered during the semester by working on a more complex application. Please read these instructions carefully. The submission link will be available once you have joined a group.

## Groups

You have been added to Blackboard groups based on the agreed group composition. If you need to add or remove a member, contact the instructor (Leonardo). You can use the Blackboard group feature to communicate with your teammates.

## What to do

1. **Select a project.**
   Together with your group, choose one project to develop from the individual assignments submitted by group members.
   - Ideally, some proposals will be better suited than others, so selection should be straightforward. Reach out if you need help.

2. **Define a variability aspect.**
   From the selected project, choose a variability aspect to develop using model driven engineering.
   - You do not need to build new functionality from scratch. Expand and generalize what exists. Focus on behavior that can be abstracted in a model.

3. **Develop a DSL and its infrastructure.**
   Include the following:
   - An Ecore metamodel for the DSL. Instances of this metamodel (models) represent the configuration of your product or part of it.
   - Constraints in OCL or regular code to ensure model validity.
   - At least three representative model instances that demonstrate different properties. Models with identical structure and only renamed elements do not satisfy this requirement.
   - A mapping from model instances to application behavior, using one or more of these alternatives:
   - Code generation for the application (for example with Xtend or Acceleo), or parts of it. Typically you will generate or modify specific files and resources while leaving other parts unchanged. This does not mean simply generating getters and setters from Ecore models.
   - Interpretation of the model (for example with the Java APIs generated from the model) to alter application behavior based on model content.
   - Build process configuration that includes specific components, libraries, or files based on model content.
   - A concrete syntax for the DSL and an editor, either textual (for example with Xtext) or graphical (for example with Sirius).
   - Packaging configuration for the developed components as a set of Eclipse plugins.

4. **Document the work.**
   Provide documentation in the repository. It is fine to use a README.md or another format. We expect a README.md that points to the rest of the documentation in whichever format you prefer.

## Scope and focus

The aim is to gain experience with DSLs and model driven engineering on a real problem, not to deliver a complete production solution. Focus on a small but complete end to end slice, from the concrete syntax of the DSL to the generated or interpreted application, and expand from there.

## Constraints

• The generator and DSL infrastructure must be based on EMF and Ecore and related extensions.
• Alternatives may be considered if they are well justified and approved by the course staff beforehand.

<br/>
<br/>
<br/>
<br/>

# Requirements:


| №  | Requirement                                                                                                                                                           | Status | Evidence from repo                                                                                                                                                                                                                                                                             | Comment                                                                                                                                                                                                                                      |
| -- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----: | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1  | Choose the scope and variability to model                                                                                                                             |   🟢   | Opponents, behaviour and difficulties are the chosen variability focus.                                                                                                                                                                       |                                                                                                                                                                                                                                              |
| 2  | Define a DSL with an EMF Ecore metamodel                                                                                                                              |   🟢   | `opponents-module/src/main/resources/opponents.ecore`, `movements-module/src/main/resources/movements/movements.ecore`, `difficulty-module/src/main/resources/difficulty-module.ecore` and their `.genmodel` files.                                                                            |                                                                                                                                                                                                                                              |
| 3  | Add constraints in OCL or in code                                                                                                                                     |   🟢   | Custom validator with `@generated NOT`: `opponents-module/src/main/java/main/game/maze/opponents/util/OpponentsValidator.java`. OCL annotations present in `.../impl/OpponentsPackageImpl.java`. Test: `opponents-module/src/test/java/main/game/maze/opponents/MaxThreatValidationTest.java`. |                                                                                                                                                                                                                                              |
| 4  | Provide at least three representative model instances                                                                                                                 |   🔴   | Multiple `.xmi` samples: opponents, difficulties, movements. Examples: `maze/src/main/resources/xmi/opponents/opponentModel.xmi`, `maze/src/main/resources/xmi/difficulties/difficulties.xmi`, `movements-module/src/main/resources/movements/*.xmi` - but opponentModel.xmi is from Assignment 2, therefore not counting it and we should dicuss one more.                                          |                                                                                                                                                                                                                                              |
| 5  | Map models to application behavior by interpretation or generation or build time configuration                                                                        |   🟢   | Runtime interpretation and loading: `maze/.../runtime/opponents/OpponentRuntimeFactory.java`, `maze/.../service/DifficultyService.java`, `maze/.../config/PatrolHelper.java`.                                                                                                                  |                                                                                                                                                                                                                                              |
| 6  | Define a concrete syntax and an editor using Xtext or Sirius                                                                                                          |   🔴   | No `.xtext` grammar or Sirius `.odesign` found. Only a `.aird` session: `opponents-module/src/main/resources/opponents.aird`.                                                                                                                                                                  | Suggest Xtext textual DSL first: new `dsl.opponents/` with `*.xtext` grammar referencing the existing Ecore, generate Eclipse editor, add validator rules in the Xtext validator. Sirius can follow later if you want a graphical viewpoint. |
| 7  | Package the solution as Eclipse plugins and optionally an update site                                                                                                 |   🔴   | `plugin.xml` and `MANIFEST.MF` exist for `movements-module` and `difficulty-module`, but Maven `packaging` is `jar` and there is no Tycho parent or update site.                                                                                     | Convert to Tycho build. Set `packaging` to `eclipse-plugin` for plugin projects, add a `feature` project and a `repository` project. Add plugin metadata for `opponents-module`.                                                             |
| 8  | Base the solution on EMF Ecore and related extensions                                                                                                                 |   🟢   | EMF and OCL used across modules, generated EMF code present.                                                                                                                                                                                                                                   |                                                                                                                                                                                                                                              |
| 9  | Provide a single command build that parses models, validates them, runs tests, performs generation or interpretation, and packages plugins, failing on invalid models |   🔴   | Multi-module Maven build exists, but no headless parse and validate of the sample `.xmi` and no Tycho packaging.                                                                                                                                                                               | Add a headless validation test that loads each `.xmi` via EMF in JUnit and fails on diagnostics. If you add Xtext, also run `mwe2` or Xtext codegen in the build. Migrate to Tycho for plugin packaging.                                     |
| 10 | Ensure that all sample models load without unresolved proxies                                                                                                         |   🟢   | Runtime loaders register packages and XMI factories before load. See `OpponentRuntimeFactory.java`, `DifficultyService.java`.                                                                                                                                                                  |                                                                                                                                                                                                                                              |
| 11 | Deliver a small but complete end to end slice from editor to model to running application                                                                             |   🟢   | End to end by interpretation is present and exercised through the game runtime with the `.xmi` files.                                                                                                                                                                                          |                                                                                                                                                                                                                                              |
| 12 | Include a README that explains how to build, run, and demonstrate the result                                                                                          |   🔴   | `readme.md` and `opponents-module/readme.md` include how to run and reference the model usage. But we are missing for other modules.                                                                                                                                                                                                 | Consider expanding with a short “demo steps” section that names the three sample models and what changes to expect.                                                                                                                          |
| 13 | Add a short demo script that shows the effect of each sample model and the behavior on validation errors                                                              |   🔴   | No dedicated, numbered demo script found.                                                                                                                                                                                                                                                      | Add a one page demo script in the root README. Include a failing `.xmi` example to show the validator message.                                                                                                                               |

<br/>
<br/>
<br/>
<br/>

# Criteria:

<br/><br/>
<img src="./evaluation.png" alt="Evaluation" width="190%" />
<br/><br/>
