package main.game.maze.config;
import java.util.Map;

// Interface of the Resolver
// given the key profile returns final composition
public interface CompositionResolver {
  Map<EnemyType, Integer> resolve(String profile);
}

