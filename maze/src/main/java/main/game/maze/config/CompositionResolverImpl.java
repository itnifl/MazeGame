package main.game.maze.config;

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

  @Override
  public Map<EnemyType, Integer> resolve(String profile) {
    ProfileRules p = Optional.ofNullable(profiles.get(profile))
        .orElseThrow(() -> new IllegalArgumentException("Perfil desconocido: " + profile));
    int total = Math.max(0, p.enemyCount());

    if (p.countsOverride()!=null && !p.countsOverride().isEmpty()) {
      return redistributeToTotal(applyCaps(clampNonNegative(p.countsOverride()), p.caps()), total);
    }

    Map<EnemyType,Double> ratios = normalize(sanitizeRatios(p.ratios()));
    if (ratios.isEmpty() || total == 0) return Map.of();

    Map<EnemyType,Integer> initial = largestRemainderAllocate(scale(ratios, total));
    Map<EnemyType,Integer> capped  = applyCaps(initial, p.caps());
    return redistributeToTotal(capped, total);
  }

  /* helpers */
  private static Map<EnemyType,Integer> clampNonNegative(Map<EnemyType,Integer> in){
    Map<EnemyType,Integer> out=new EnumMap<>(EnemyType.class);
    in.forEach((k,v)-> out.put(k, Math.max(0, v==null?0:v))); return out;
  }
  private static Map<EnemyType,Double> sanitizeRatios(Map<EnemyType,Double> m){
    if(m==null) return Map.of(); Map<EnemyType,Double> out=new EnumMap<>(EnemyType.class);
    m.forEach((k,v)-> out.put(k, (v==null||Double.isNaN(v)||v<=0)?0.0:v)); return out;
  }
  private static Map<EnemyType,Double> normalize(Map<EnemyType,Double> m){
    double s=m.values().stream().mapToDouble(Double::doubleValue).sum(); if(s<=0) return Map.of();
    Map<EnemyType,Double> out=new EnumMap<>(EnemyType.class); final double S=s; m.forEach((k,v)-> out.put(k, v/S)); return out;
  }
  private static Map<EnemyType,Double> scale(Map<EnemyType,Double> r,int total){
    Map<EnemyType,Double> t=new EnumMap<>(EnemyType.class); r.forEach((k,x)-> t.put(k, x*total)); return t;
  }
  private static Map<EnemyType,Integer> largestRemainderAllocate(Map<EnemyType,Double> targets){
    Map<EnemyType,Integer> floor=new EnumMap<>(EnemyType.class);
    Map<EnemyType,Double> frac=new EnumMap<>(EnemyType.class);
    int sum=0; for(var e:targets.entrySet()){
      int b=(int)Math.floor(e.getValue()); floor.put(e.getKey(),b); frac.put(e.getKey(), e.getValue()-b); sum+=b;
    }
    int total=(int)Math.round(targets.values().stream().mapToDouble(Double::doubleValue).sum());
    int add=Math.max(0,total-sum);
    List<EnemyType> order=new ArrayList<>(frac.keySet());
    order.sort((a,b)->{int c=Double.compare(frac.get(b),frac.get(a)); return c!=0?c:a.name().compareTo(b.name());});
    for(int i=0;i<add;i++){EnemyType k=order.get(i%order.size()); floor.put(k,floor.get(k)+1);}
    return floor;
  }
  private static Map<EnemyType,Integer> applyCaps(Map<EnemyType,Integer> counts, Map<EnemyType,Integer> caps){
    if(caps==null||caps.isEmpty()) return counts; Map<EnemyType,Integer> out=new EnumMap<>(EnemyType.class);
    counts.forEach((k,v)-> out.put(k, Math.min(v, Math.max(0, caps.getOrDefault(k, Integer.MAX_VALUE))))); return out;
  }
  private static Map<EnemyType,Integer> redistributeToTotal(Map<EnemyType,Integer> counts, int total){
    int cur=counts.values().stream().mapToInt(i->i).sum(); Map<EnemyType,Integer> out=new EnumMap<>(counts);
    if(cur==total) return out;
    if(cur>total){
      List<EnemyType> order=new ArrayList<>(out.keySet()); order.sort((a,b)->Integer.compare(out.get(b), out.get(a)));
      int rm=cur-total; for(EnemyType t:order){int take=Math.min(rm,out.get(t)); out.put(t,out.get(t)-take); rm-=take; if(rm==0)break;}
      out.replaceAll((k,v)->Math.max(0,v)); return out;
    } else {
      int add=total-cur; List<EnemyType> order=new ArrayList<>(out.keySet());
      if(order.isEmpty()){order=List.of(EnemyType.values()); out.put(order.get(0),0);}
      int i=0; while(add-- >0){EnemyType t=order.get(i++%order.size()); out.put(t, out.getOrDefault(t,0)+1);} return out;
    }
  }
}

