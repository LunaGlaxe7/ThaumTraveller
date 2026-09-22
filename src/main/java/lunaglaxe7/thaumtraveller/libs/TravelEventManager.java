package lunaglaxe7.thaumtraveller.libs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.TravelEvent;

public class TravelEventManager {

    private static final Map<TravelEvent, List<IHandler>> manager = new ConcurrentHashMap<>();

    public static void register(TravelEvent event, IHandler handler) {
        manager.computeIfAbsent(event, k -> new ArrayList<>()).add(handler);
    }

    public static List<IHandler> getHandlers(TravelEvent event) {
        return manager.get(event);
    }
}
