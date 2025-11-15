package main.game.maze.config;
import java.util.Map;
import main.game.maze.difficulties.*;

// Interface of the Resolver
// given the key profile returns final composition
public interface CompositionResolver {
  Map<EnemyTypes, Integer> resolve(String profile);
}

