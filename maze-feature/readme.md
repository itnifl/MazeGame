# maze-feature

This project defines the Eclipse feature for MazeGame.  
It bundles the plug in modules into a single logical unit that can be installed in Eclipse  
or published as part of a p two update site.

Where the individual plug in projects contain code and models,  
the feature project describes “what belongs together” from an installation point of view.

---

## Purpose

The maze feature project is responsible for

- grouping the MazeGame plug ins into one installable feature  
- providing a versioned unit that can be referenced by products and target platforms  
- defining which plug ins are required and which are optional  
- contributing the feature to the p two repository built by Tycho

In short, if you want “MazeGame tools” or “MazeGame runtime” to appear as one installable entry in Eclipse,  
this feature is what makes that possible.

---

## What the feature contains

The exact list depends on the current configuration,  
but typically the feature includes plug ins such as

- core domain models and editors for opponents, difficulties and walls  
- the maze world and behaviour related plug ins that are needed inside Eclipse  
- Acceleo generator and runner plug ins if they are intended to be installed in Eclipse  
- any Xtext or Sirius based tooling projects associated with MazeGame

Each included plug in is referenced by its identifier and version range in the feature definition.  
When the feature is installed, all required plug ins are installed automatically.

---

## Role in the build

In the Tycho build and releng setup, the feature plays several roles.

- It is the main unit that is assembled into the p two repository alongside the plug ins.  
- It can be used in target platform definitions to pull in all MazeGame tools in one step.  
- It can be referenced by an Eclipse product definition if you later create a dedicated MazeGame product.

The releng projects and the mirror build make sure that the feature and its plug ins  
are available in the local p two repository used for offline or reproducible builds.

---

## When to change the feature

You should update the maze feature project whenever you

- add a new plug in that should be part of the public MazeGame toolset  
- remove or deprecate a plug in so that it is no longer shipped  
- split the tooling into separate features, for example “runtime”, “developer tools” or “examples”  
- change licensing or descriptive metadata that should appear in the Eclipse installation dialog

After such changes, rebuild the Tycho reactor so that the updated feature is included in the p two repository.

---

## Design guidelines

When maintaining this project, keep these ideas in mind.

- Treat the feature as the public face of the MazeGame Eclipse tooling  
  Only include plug ins that you expect users or developers to install.

- Keep dependencies clean  
  Each included plug in should build and run correctly when installed through this feature.

- Use clear names and descriptions  
  The label, description and provider text are shown in the Eclipse installation dialogs  
  and should make it obvious what MazeGame offers.

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](../docs/technology-laymans-guide.md) | Simple explanation of the technologies in everyday terms |
| [Eclipse Modules](../eclipse.modules.md) | Eclipse plugin architecture and build worlds |
| [Releng Documentation](../releng/readme.md) | Build infrastructure and target platform |
| [Repository Module](../maze-module-repository/readme.md) | P2 update site |
| [Main README](../readme.md) | Project overview and module index |

By doing this, the `maze feature` project remains a clear and stable entry point  
for installing and distributing the MazeGame plug ins through Eclipse and Tycho.