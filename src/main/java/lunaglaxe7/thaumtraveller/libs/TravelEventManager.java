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

    public static List<IHandler> getHandlers(Class<TravelEvent> c) {
        return instance.manager.get(c);
    }

    // 结果用来除原本的craft cost
    public static int capCostModifiers(CapInfoEvent.CapCraftCost event) {
        IHandler[] handlers = instance.manager.get(CapInfoEvent.CapCraftCost.class).toArray(new IHandler[0]);
        for (IHandler handler : handlers) {
            handler.handle(event);
        }
        return event.getModifier();
    }

    public static float addingDiscountModifiers(CapInfoEvent.CapDiscountAdding event) {
        IHandler[] handlers = instance.manager.get(CapInfoEvent.CapDiscountAdding.class).toArray(new IHandler[0]);
        for (IHandler handler : handlers) {
            handler.handle(event);
        }
        return event.getModifier();
    }

    public static float multiplyDiscountModifiers(CapInfoEvent.CapDiscountMul event) {
        IHandler[] handlers = instance.manager.get(CapInfoEvent.CapDiscountMul.class).toArray(new IHandler[0]);
        for (IHandler handler : handlers) {
            handler.handle(event);
        }
        return event.getModifier();
    }

    public static float addingSpecialDiscountModifiers(CapInfoEvent.SpecialDiscountAdding event) {
        IHandler[] handlers = instance.manager.get(CapInfoEvent.SpecialDiscountAdding.class).toArray(new IHandler[0]);
        for (IHandler handler : handlers) {
            handler.handle(event);
        }
        return event.getModifier();
    }

    public static float multiplySpecialDiscountModifiers(CapInfoEvent.SpecialDiscountMul event) {
        IHandler[] handlers = instance.manager.get(CapInfoEvent.SpecialDiscountMul.class).toArray(new IHandler[0]);
        for (IHandler handler : handlers) {
            handler.handle(event);
        }
        return event.getModifier();
    }

}
