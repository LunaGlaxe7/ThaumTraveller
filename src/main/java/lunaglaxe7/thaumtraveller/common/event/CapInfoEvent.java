package lunaglaxe7.thaumtraveller.common.event;

import net.minecraft.item.ItemStack;

import lunaglaxe7.thaumtraveller.api.TravelEvent;
import thaumcraft.api.aspects.Aspect;

public class CapInfoEvent extends TravelEvent {

    public ItemStack stack;

    public CapInfoEvent(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * On getting cap craft cost Calculate your modifier here, only multiply with the original modifier DO NOT use
     * getCraftCost here
     */
    public static class CapCraftCost extends CapInfoEvent {

        private float modifier = 1;

        /**
         * @param cap the cap item may contain nbt
         */
        public CapCraftCost(ItemStack cap) {
            super(cap);
        }

        public void setModifier(float modifier) {
            this.modifier = modifier;
        }

        public float getModifier() {
            return modifier;
        }
    }

    public static class SpecialDiscountMul extends CapDiscountMul {

        private Aspect a;

        public SpecialDiscountMul(ItemStack wand, Aspect a, int id) {
            super(wand, id);
            this.a = a;
        }

        public Aspect getAspect() {
            return a;
        }

        public void setAspect(Aspect a) {
            this.a = a;
        }
    }

    public static class CapDiscountMul extends CapInfoEvent {

        private float modifier = 1f;
        public int index;

        /**
         * @param wand the wand item stack
         * @param id   the cap 1 or 2
         */
        public CapDiscountMul(ItemStack wand, int id) {
            super(wand);
            this.index = id;
        }

        public float getModifier() {
            return modifier;
        }

        public void setModifier(float modifier) {
            this.modifier = modifier;
        }
    }
}
