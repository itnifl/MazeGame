package main.game.maze;

import java.util.Map;

import main.game.maze.config.CompositionResolver;
import main.game.maze.config.CompositionResolverImpl;
import main.game.maze.config.EnemyType;
import main.game.maze.config.ProfileRules;
import main.game.maze.config.XmiRulesLoader;

public class RunComposition {

  public static void main(String[] args) {
    final String profile = (args.length > 0) ? args[0] : "normal";

    System.out.println("[RunComposition] Expecting classpath resources:");
    System.out.println(" - xmi/difficulties/difficulties.xmi");
    System.out.println(" - xmi/opponents/opponentModel.xmi");

    try {
      // 1. Cargar reglas desde los XMI (classpath)
      XmiRulesLoader loader = new XmiRulesLoader();
      Map<String, ProfileRules> profiles = loader.load();
      System.out.println("[RunComposition] Loaded profiles: " + profiles.keySet());

      if (!profiles.containsKey(profile)) {
        System.err.println("Unknown profile: " + profile + " (available: " + profiles.keySet() + ")");
        System.exit(2);
      }

      // 2. Resolver composición para el perfil pedido
      CompositionResolver resolver = new CompositionResolverImpl(profiles);
      ProfileRules rules = profiles.get(profile);
      Map<EnemyType, Integer> comp = resolver.resolve(profile);

      // 3) Diagnóstico breve
      int sum = comp.values().stream().mapToInt(Integer::intValue).sum();
      int capsSum = rules.caps().values().stream().mapToInt(Integer::intValue).sum();
      boolean feasible = capsSum >= rules.enemyCount();

      System.out.println();
      System.out.println("=== Composition for profile: " + profile + " ===");
      System.out.println("enemyCount (target): " + rules.enemyCount());
      System.out.println("sum(caps): " + capsSum + "  -> feasible? " + feasible);
      System.out.println("ratios: " + rules.ratios());
      System.out.println("caps:   " + rules.caps());
      System.out.println("result: " + comp);
      System.out.println("sum(result): " + sum + (feasible ? " (== enemyCount)" : " (== max feasible under caps)"));

      // Vista rápida de todos si no pasas argumentos
      if (args.length == 0) {
        System.out.println();
        System.out.println("[All profiles quick view]");
        System.out.println("easy   -> " + resolver.resolve("easy"));
        System.out.println("normal -> " + resolver.resolve("normal"));
        System.out.println("hard   -> " + resolver.resolve("hard"));
      }

    } catch (Throwable t) {
      System.err.println();
      System.err.println("[RunComposition] ERROR:");
      t.printStackTrace();
      System.err.println();
      System.err.println("Checklist:");
      System.err.println(" 1) XMI en src/main/resources/xmi/... ?");
      System.err.println(" 2) EMF deps en pom (ecore + ecore.xmi)?");
      System.err.println(" 3) Paquetes EMF generados (difficulties/opponents) en el classpath?");
      System.exit(1);
    }
  }
}
