package lunaglaxe7.thaumtraveller.libs.events;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.TravelEvent;
import lunaglaxe7.thaumtraveller.common.event.CapInfoEvent;

public class CapModifier implements IHandler {

    @Override
    public void handle(TravelEvent event) {
        if (event instanceof CapInfoEvent.CapCraftCost) {
            Integer cost = ((CapInfoEvent.CapCraftCost) event).cost;
        }
    }
}
