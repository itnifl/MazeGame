# MazeGame Documentation

Welcome to the MazeGame documentation. This folder contains detailed guides and references for the project.

## Documentation Index

### Getting Started

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](technology-laymans-guide.md) | Simple explanations of Xtext, metamodels, and FreeMarker with practical scenarios |
| [DSL Tutorial](dsl-tutorial.md) | Step-by-step guide to creating your first game level |
| [Xtext Setup Guide](xtext-readme.md) | Build and development setup for the DSL |

### Reference

| Document | Description |
|----------|-------------|
| [DSL Reference Guide](dsl-reference.md) | Complete language reference with all syntax, validation rules, and generation details |
| [Metamodel Architecture](metamodel-architecture.md) | Technical details about metamodels and the Xtext integration |

### Quick Links

- **New to MDD?** Start with the [Technology Layman's Guide](technology-laymans-guide.md)
- **Want to write levels?** Start with the [DSL Tutorial](dsl-tutorial.md)
- **Need syntax help?** Check the [DSL Reference Guide](dsl-reference.md)
- **Examples:** See `maze/src/main/resources/levels/*.mazegame`

## Related Documentation

| Location | Description |
|----------|-------------|
| [Main README](../readme.md) | Project overview and module index |
| [DSL Module README](../main.game.maze.dsl/readme.md) | DSL project structure and technical details |
| [FreeMarker Guide](../freemarker.readme.md) | Code generation with FreeMarker |
| [Model-Driven Code Generation Plan](../readme-mddcodegeneration.md) | Architecture for generating code from models |
| [Eclipse Modules](../eclipse.modules.md) | Eclipse plugin architecture |

## Documentation Structure

```text
docs/
├── readme.md           # This file
├── dsl-reference.md    # Complete DSL syntax reference
└── dsl-tutorial.md     # Step-by-step tutorial
```

## Contributing to Documentation

When adding new documentation:

1. Use Markdown format
2. Include a table of contents for long documents
3. Provide code examples
4. Link to related documentation
5. Update this index

## Feedback

Found an issue with the documentation? Please report it in the project issue tracker.
