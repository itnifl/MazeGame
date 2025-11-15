package main.game.maze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.game.maze.config.ProfileRules;
import main.game.maze.config.XmiRulesLoader;

/**
 * Verifica que el loader lee los XMI y construye reglas coherentes.
 * Estos tests no dependen de números exactos, sino de propiedades.
 */
@Disabled("Skipped due to OS dependency")
class XmiRulesLoaderTest {

  @Test
  void loadsProfilesFromClasspath() {
    XmiRulesLoader loader = new XmiRulesLoader();
    Map<String, ProfileRules> profiles = loader.load();

    assertNotNull(profiles, "profiles no debe ser null");
    assertFalse(profiles.isEmpty(), "debe haber al menos un perfil");
    // dado tu XMI, esperamos easy/normal/hard
    assertTrue(profiles.containsKey("easy"), "falta perfil easy");
    assertTrue(profiles.containsKey("normal"), "falta perfil normal");
    assertTrue(profiles.containsKey("hard"), "falta perfil hard");
  }

  @Test
  void eachProfileHasPositiveTotalAndRatiosOrCaps() {
    XmiRulesLoader loader = new XmiRulesLoader();
    Map<String, ProfileRules> profiles = loader.load();

    for (ProfileRules p : profiles.values()) {
      assertNotNull(p.enemyCount(), "enemyCount no debe ser null");
      assertTrue(p.enemyCount() > 0, "enemyCount debe ser > 0");

      // si hay ratios, que sean no negativos y sumen aprox 1
      if (p.ratios() != null && !p.ratios().isEmpty()) {
        double sum = p.ratios().values().stream().mapToDouble(Double::doubleValue).sum();
        assertTrue(sum > 0.99 && sum < 1.01, "ratios deben estar normalizados (sum~1)");
        assertTrue(p.ratios().values().stream().allMatch(x -> x >= 0), "ratios no negativos");
      }

      // caps presentes (por enemyMaxCount del XMI)
      assertNotNull(p.caps(), "caps no debe ser null");
      assertTrue(p.caps().values().stream().allMatch(c -> c >= 0), "caps no negativos");
    }
  }
}

