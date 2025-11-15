package main.game.maze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.game.maze.config.CompositionResolverImpl;
import main.game.maze.difficulties.*;
import main.game.maze.config.ProfileRules;
import main.game.maze.config.XmiRulesLoader;


/**
 * Tests del resolver: redondeo justo, precedencia overrides, caps y no-negativos.
 */
@Disabled("Skipped due to OS dependency")
class CompositionResolverImplTest {

  static Map<String, ProfileRules> profilesFromXmi;

  @BeforeAll
  static void loadXmiOnce() {
    profilesFromXmi = new XmiRulesLoader().load();
  }

  @Test
  void resolveFromXmiProfiles_totalsExactAndCapsRespected() {
    CompositionResolverImpl resolver = new CompositionResolverImpl(profilesFromXmi);

    for (String profile : new String[]{"easy", "normal", "hard"}) {
      var rules = profilesFromXmi.get(profile);
      var comp  = resolver.resolve(profile);

      // 1) suma exacta
      int sum = comp.values().stream().mapToInt(Integer::intValue).sum();
      assertEquals(rules.enemyCount(), sum, "la suma debe ser exactamente enemyCount");

      // 2) no negativos
      assertTrue(comp.values().stream().allMatch(c -> c >= 0), "no debe haber negativos");

      // 3) no excede caps
      for (var e : comp.entrySet()) {
        int cap = rules.caps().getOrDefault(e.getKey(), Integer.MAX_VALUE);
        assertTrue(e.getValue() <= cap, "no debe exceder cap para " + e.getKey());
      }
    }
  }

  @Test
  void precedence_countsOverrideOverridesRatios_andRedistributesToTotal() {
    // Perfil artificial con override: PUMPKINBOMBER=3, total=10, cap alto
    Map<EnemyTypes, Integer> countsOverride = new EnumMap<>(EnemyTypes.class);
    countsOverride.put(EnemyTypes.PUMPKINBOMBER, 3);

    ProfileRules custom = new ProfileRules(
        "custom",
        10, // total
        Map.of(EnemyTypes.GHOST, 0.6, EnemyTypes.ZOMBIE, 0.4), // debería ignorarse
        countsOverride,
        Map.of(EnemyTypes.GHOST, 100, EnemyTypes.ZOMBIE, 100, EnemyTypes.PUMPKINBOMBER, 100)
    );
    Map<String, ProfileRules> map = Map.of("custom", custom);

    CompositionResolverImpl resolver = new CompositionResolverImpl(map);
    var comp = resolver.resolve("custom");

    // Debe respetar override (>=3) y rellenar el resto hasta total=10
    assertTrue(comp.getOrDefault(EnemyTypes.PUMPKINBOMBER, 0) >= 3, "override debe respetarse");
    int sum = comp.values().stream().mapToInt(Integer::intValue).sum();
    assertEquals(10, sum, "la suma debe ser exactamente 10");
  }

  @Test
  void rounding_largestRemainder_isFair() {
    // ratios 0.6 / 0.4 con total 5 -> esperamos 3 y 2 (con cualquier orden)
    ProfileRules simple = new ProfileRules(
        "simple", 5,
        Map.of(EnemyTypes.GHOST, 0.6, EnemyTypes.ZOMBIE, 0.4),
        Map.of(),
        Map.of(EnemyTypes.GHOST, 100, EnemyTypes.ZOMBIE, 100)
    );
    CompositionResolverImpl resolver = new CompositionResolverImpl(Map.of("simple", simple));
    var comp = resolver.resolve("simple");

    assertEquals(5, comp.values().stream().mapToInt(Integer::intValue).sum(), "total exacto");
    assertEquals(3, comp.getOrDefault(EnemyTypes.GHOST, 0) + comp.getOrDefault(EnemyTypes.PUMPKINBOMBER, 0), 
      "ghost debería llevarse ~60% ≈ 3 (si no hay pumpkin, es todo ghost)");
    assertEquals(2, comp.getOrDefault(EnemyTypes.ZOMBIE, 0), "zombie ~40% ≈ 2");
  }

  @Test
  void capsCanReduceAndRedistribute_toMaintainTotal() {
    // ratios 0.5/0.5 total 6, pero cap de ZOMBIE=1 → el resto debe redistribuirse a GHOST
    ProfileRules capped = new ProfileRules(
        "cap", 6,
        Map.of(EnemyTypes.GHOST, 0.5, EnemyTypes.ZOMBIE, 0.5),
        Map.of(),
        Map.of(EnemyTypes.GHOST, 100, EnemyTypes.ZOMBIE, 1)
    );
    CompositionResolverImpl resolver = new CompositionResolverImpl(Map.of("cap", capped));
    var comp = resolver.resolve("cap");

    assertEquals(6, comp.values().stream().mapToInt(Integer::intValue).sum(), "total exacto");
    assertTrue(comp.getOrDefault(EnemyTypes.ZOMBIE, 0) <= 1, "no debe exceder cap de ZOMBIE");
    assertTrue(comp.getOrDefault(EnemyTypes.GHOST, 0) >= 5, "GHOST absorbe el sobrante");
  }
}
