package main.game.maze.config;
import main.game.maze.difficulties.*;

import java.util.*;
/**
 * Implements the composition logic:
 * - Uses countsOverride if exists (rule over ratios).
 * - If not, ratios distribution is done
 * - Applies caps and redistribution to keep limits preserved 
 */
public final class CompositionResolverImpl implements CompositionResolver {

  private final Map<String, ProfileRules> profiles;

  public CompositionResolverImpl(Map<String, ProfileRules> profiles) {
    this.profiles = Objects.requireNonNull(profiles);
    if (profiles.isEmpty()) throw new IllegalStateException("Sin perfiles cargados");
  }

  /* see CompositionResolver
    * (non-Javadoc)
    * @see main.game.maze.config.CompositionResolver#resolve(java.lang.String) 
    * Given a profile name, returns the final enemy composition
    */
  @Override
  public Map<EnemyTypes, Integer> resolve(String profile) {
    ProfileRules p = Optional.ofNullable(profiles.get(profile))
        .orElseThrow(() -> new IllegalArgumentException("Perfil desconocido: " + profile));
    int total = Math.max(0, p.enemyCount());

    if (p.countsOverride()!=null && !p.countsOverride().isEmpty()) {
      return redistributeToTotal(applyCaps(clampNonNegative(p.countsOverride()), p.caps()), total);
    }

    Map<EnemyTypes,Double> ratios = normalize(sanitizeRatios(p.ratios()));
    if (ratios.isEmpty() || total == 0) return Map.of();

    Map<EnemyTypes,Integer> initial = largestRemainderAllocate(scale(ratios, total));
    Map<EnemyTypes,Integer> capped  = applyCaps(initial, p.caps());
    return redistributeToTotal(capped, total);
  }

  /* helpers */
/* helpers */

private static Map<EnemyTypes, Integer> clampNonNegative(Map<EnemyTypes, Integer> in) {
  Map<EnemyTypes, Integer> out = new EnumMap<>(EnemyTypes.class);
  in.forEach((k, v) -> out.put(k, Math.max(0, v == null ? 0 : v)));
  return out;
}

private static Map<EnemyTypes, Double> sanitizeRatios(Map<EnemyTypes, Double> m) {
  if (m == null) return Map.of();

  Map<EnemyTypes, Double> out = new EnumMap<>(EnemyTypes.class);
  m.forEach((k, v) -> out.put(k, (v == null || Double.isNaN(v) || v <= 0) ? 0.0 : v));
  return out;
}

private static Map<EnemyTypes, Double> normalize(Map<EnemyTypes, Double> m) {
  double s = m.values().stream().mapToDouble(Double::doubleValue).sum();
  if (s <= 0) return Map.of();

  Map<EnemyTypes, Double> out = new EnumMap<>(EnemyTypes.class);
  final double S = s;
  m.forEach((k, v) -> out.put(k, v / S));
  return out;
}

private static Map<EnemyTypes, Double> scale(Map<EnemyTypes, Double> r, int total) {
  Map<EnemyTypes, Double> t = new EnumMap<>(EnemyTypes.class);
  r.forEach((k, x) -> t.put(k, x * total));
  return t;
}

private static Map<EnemyTypes, Integer> largestRemainderAllocate(Map<EnemyTypes, Double> targets) {
  Map<EnemyTypes, Integer> floor = new EnumMap<>(EnemyTypes.class);
  Map<EnemyTypes, Double> frac = new EnumMap<>(EnemyTypes.class);

  int sum = 0;
  for (var e : targets.entrySet()) {
    int b = (int) Math.floor(e.getValue());
    floor.put(e.getKey(), b);
    frac.put(e.getKey(), e.getValue() - b);
    sum += b;
  }

  int total = (int) Math.round(
      targets.values().stream().mapToDouble(Double::doubleValue).sum()
  );
  int add = Math.max(0, total - sum);

  List<EnemyTypes> order = new ArrayList<>(frac.keySet());
  order.sort((a, b) -> {
    int c = Double.compare(frac.get(b), frac.get(a));
    return c != 0 ? c : a.name().compareTo(b.name());
  });

  for (int i = 0; i < add; i++) {
    EnemyTypes k = order.get(i % order.size());
    floor.put(k, floor.get(k) + 1);
  }

  return floor;
}

private static Map<EnemyTypes, Integer> applyCaps(
    Map<EnemyTypes, Integer> counts,
    Map<EnemyTypes, Integer> caps
) {
  if (caps == null || caps.isEmpty()) return counts;

  Map<EnemyTypes, Integer> out = new EnumMap<>(EnemyTypes.class);
  counts.forEach((k, v) -> {
    int cap = Math.max(0, caps.getOrDefault(k, Integer.MAX_VALUE));
    out.put(k, Math.min(v, cap));
  });
  return out;
}

private static Map<EnemyTypes, Integer> redistributeToTotal(
    Map<EnemyTypes, Integer> counts,
    int total
) {
  int cur = counts.values().stream().mapToInt(i -> i).sum();
  Map<EnemyTypes, Integer> out = new EnumMap<>(counts);

  if (cur == total) return out;

  if (cur > total) {
    // Remove extras from the largest buckets first
    List<EnemyTypes> order = new ArrayList<>(out.keySet());
    order.sort((a, b) -> Integer.compare(out.get(b), out.get(a)));

    int rm = cur - total;
    for (EnemyTypes t : order) {
      int take = Math.min(rm, out.get(t));
      out.put(t, out.get(t) - take);
      rm -= take;
      if (rm == 0) break;
    }
    out.replaceAll((k, v) -> Math.max(0, v));
    return out;
  } else {
    // Add missing units round-robin
    int add = total - cur;
    List<EnemyTypes> order = new ArrayList<>(out.keySet());

    if (order.isEmpty()) {
      order = List.of(EnemyTypes.values());
      out.put(order.get(0), 0);
    }

    int i = 0;
    while (add-- > 0) {
      EnemyTypes t = order.get(i++ % order.size());
      out.put(t, out.getOrDefault(t, 0) + 1);
    }
    return out;
  }
}

}

