package lunaglaxe7.thaumtraveller.common.event;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.api.TravelEvent;

public class CapInfoEvent extends TravelEvent {

    public SpecialCap cap;

    public CapInfoEvent(SpecialCap cap) {
        this.cap = cap;
    }

    public static class CapCraftCost extends CapInfoEvent {

        public Integer cost;

        public CapCraftCost(SpecialCap cap, int cost) {
            super(cap);
            this.cost = cost;
        }
    }
}
