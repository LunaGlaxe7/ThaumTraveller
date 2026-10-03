package lunaglaxe7.thaumtraveller.api.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;

import lunaglaxe7.thaumtraveller.LogHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class AspectHelper {

    public static Map<Set<Aspect>, Aspect> mixCache = new HashMap<>();
    public static Aspect[] primalCache;

    public static void init() {
        Aspect[] as = Aspect.aspects.values().toArray(new Aspect[0]);
        for (int i = 0; i < as.length; i++) {
            if (as[i].getComponents() != null) {
                Set<Aspect> set = new HashSet<>();
                Collections.addAll(set, as[i].getComponents());
                mixCache.put(set, as[i]);
            }
        }
        LogHandler.log(Level.INFO, "mix cache built");
        buildPrimalAspects();
        LogHandler.info("primal cache built");
    }

    private static void buildPrimalAspects() {
        AspectList list = new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1)
                .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1);
        primalCache = list.getAspectsSorted();
    }

    public static Aspect[] getPrimalAspects() {
        return primalCache;
    }
}
