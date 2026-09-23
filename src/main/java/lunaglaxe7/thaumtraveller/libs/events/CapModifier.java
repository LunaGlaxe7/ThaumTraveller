package lunaglaxe7.thaumtraveller.libs.events;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.TravelEvent;
import lunaglaxe7.thaumtraveller.common.event.CapInfoEvent;

public abstract class CapModifier implements IHandler {

    public static class CraftCostModifier extends CapModifier {

        private int modifier;

        @Override
        public void handle(TravelEvent event) {
            if (event instanceof CapInfoEvent.CapCraftCost) {
                CapInfoEvent.CapCraftCost c = (CapInfoEvent.CapCraftCost) event;
            }
        }
    }
}
