package lunaglaxe7.thaumtraveller.common.event;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.api.TravelEvent;
import thaumcraft.api.aspects.Aspect;

public class CapInfoEvent extends TravelEvent {

    public SpecialCap cap;

    public CapInfoEvent(SpecialCap cap) {
        this.cap = cap;
    }

    /**
     * On getting cap craft cost Calculate your modifier here, only multiply with the original modifier DO NOT use
     * getCraftCost here
     */
    public static class CapCraftCost extends CapInfoEvent {

        private int modifier = 1;

        public CapCraftCost(SpecialCap cap) {
            super(cap);
        }

        public void setModifier(int modifier) {
            this.modifier = modifier;
        }

        public int getModifier() {
            return modifier;
        }
    }

    public static class SpecialDiscountAdding extends CapDiscountAdding {

        private Aspect a;

        public SpecialDiscountAdding(SpecialCap cap, Aspect a) {
            super(cap);
            this.a = a;
        }

        public Aspect getAspect() {
            return a;
        }

        public void setAspect(Aspect a) {
            this.a = a;
        }
    }

    public static class SpecialDiscountMul extends CapDiscountMul {

        private Aspect a;

        public SpecialDiscountMul(SpecialCap cap, Aspect a) {
            super(cap);
            this.a = a;
        }

        public Aspect getAspect() {
            return a;
        }

        public void setAspect(Aspect a) {
            this.a = a;
        }
    }

    public static class CapDiscountAdding extends CapInfoEvent {

        private float modifier = 0f;

        public CapDiscountAdding(SpecialCap cap) {
            super(cap);
        }

        public float getModifier() {
            return modifier;
        }

        public void setModifier(float modifier) {
            this.modifier = modifier;
        }
    }

    public static class CapDiscountMul extends CapInfoEvent {

        private float modifier = 0f;

        public CapDiscountMul(SpecialCap cap) {
            super(cap);
        }

        public float getModifier() {
            return modifier;
        }

        public void setModifier(float modifier) {
            this.modifier = modifier;
        }
    }
}
