package lunaglaxe7.thaumtraveller.libs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.TravelEvent;
import lunaglaxe7.thaumtraveller.common.event.CapInfoEvent;

public class TravelEventManager {

    private final Map<Class<TravelEvent>, List<IHandler>> manager = new ConcurrentHashMap<>();
    public static TravelEventManager instance = new TravelEventManager();

    public static void register(Class<TravelEvent> c, IHandler handler) {
        instance.manager.computeIfAbsent(c, k -> new ArrayList<>()).add(handler);
    }

    public static List<IHandler> getHandlers(Class<? extends TravelEvent> c) {
        return instance.manager.get(c);
    }

    // 结果用来除原本的craft cost
    public static int capCostModifiers(CapInfoEvent.CapCraftCost event) {
        List<IHandler> handlers = getHandlers(CapInfoEvent.CapCraftCost.class);
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event.getModifier();
    }

    public static float addingDiscountModifiers(CapInfoEvent.CapDiscountAdding event) {
        List<IHandler> handlers = getHandlers(CapInfoEvent.CapDiscountAdding.class);
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event.getModifier();
    }

    public static float multiplyDiscountModifiers(CapInfoEvent.CapDiscountMul event) {
        List<IHandler> handlers = getHandlers(CapInfoEvent.CapDiscountMul.class);
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event.getModifier();
    }

    public static float addingSpecialDiscountModifiers(CapInfoEvent.SpecialDiscountAdding event) {
        List<IHandler> handlers = getHandlers(CapInfoEvent.SpecialDiscountAdding.class);
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event.getModifier();
    }

    public static float multiplySpecialDiscountModifiers(CapInfoEvent.SpecialDiscountMul event) {
        List<IHandler> handlers = getHandlers(CapInfoEvent.SpecialDiscountMul.class);
        if (handlers != null && !handlers.isEmpty()) {
            for (IHandler handler : handlers) {
                handler.handle(event);
            }
        }
        return event.getModifier();
    }

}
