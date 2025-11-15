package main.game.maze;
import java.util.Map;
import main.game.maze.config.*;
public class RunResolver {
  public static void main(String[] args) {
    XmiRulesLoader loader = new XmiRulesLoader();
    Map<String, ProfileRules> profiles = loader.load();

    CompositionResolver resolver = new CompositionResolverImpl(profiles);
    System.out.println("easy   -> " + resolver.resolve("easy"));
    System.out.println("normal -> " + resolver.resolve("normal"));
    System.out.println("hard   -> " + resolver.resolve("hard"));
  }
}
