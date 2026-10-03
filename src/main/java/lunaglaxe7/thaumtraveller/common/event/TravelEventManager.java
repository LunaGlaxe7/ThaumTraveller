package lunaglaxe7.thaumtraveller.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.TravelEvent;

public class TravelEventManager {

    private final Map<Class<? extends TravelEvent>, List<IHandler>> manager = new ConcurrentHashMap<>();
    public static TravelEventManager instance = new TravelEventManager();

    public static void init() {
        register(CapInfoEvent.CapDiscountMul.class, new UTypeApplier.Conducting());
        register(CapInfoEvent.CapCraftCost.class, new UTypeApplier.Affinity());
    }

    public static void register(Class<? extends TravelEvent> c, IHandler handler) {
        instance.manager.computeIfAbsent(c, k -> new ArrayList<>()).add(handler);
    }

    public static List<IHandler> getHandlers(Class<? extends TravelEvent> c) {
        return instance.manager.get(c);
    }

    public static float capCostModifiers(CapInfoEvent.CapCraftCost event) {
        return applyModifiers(event).getModifier();
    }

    public static float multiplyDiscountModifiers(CapInfoEvent.CapDiscountMul event) {
        return applyModifiers(event).getModifier();
    }

    public static float multiplySpecialDiscountModifiers(CapInfoEvent.SpecialDiscountMul event) {
        return applyModifiers(event).getModifier();
    }

    private static <T extends TravelEvent> T applyModifiers(T event) {
        List<IHandler> handlers = getHandlers(event.getClass());
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event;
    }

}
