package lunaglaxe7.thaumtraveller.util;

import java.util.*;

import org.apache.logging.log4j.Level;

import lunaglaxe7.thaumtraveller.LogHandler;
import thaumcraft.api.aspects.Aspect;

public class AspectHelper {

    public static Map<Set<Aspect>, Aspect> mixCache = new HashMap<>();

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
    }
}
