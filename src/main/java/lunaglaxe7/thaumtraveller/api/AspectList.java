package lunaglaxe7.thaumtraveller.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import thaumcraft.api.aspects.Aspect;

public class AspectList {

    private Map<Aspect, Double> aspects = new ConcurrentHashMap<>();

    public AspectList() {}

    public AspectList add(Aspect a, double amount) {
        if (aspects.containsKey(a)) aspects.compute(a, (as, c) -> c + amount);
        else aspects.put(a, amount);
        return this;
    }

    public AspectList remove(Aspect a, double amount) {
        double res = getAmount(a) - amount;
        if (res <= 0) aspects.remove(a);
        else aspects.put(a, res);
        return this;
    }

    public double getAmount(Aspect a) {
        return aspects.containsKey(a) ? aspects.get(a) : 0;
    }

    public int size() {
        return aspects.size();
    }

    public Aspect[] getAspects() {
        return aspects.keySet().toArray(new Aspect[0]);
    }

}
