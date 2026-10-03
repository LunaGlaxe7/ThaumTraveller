package lunaglaxe7.thaumtraveller.common.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import lunaglaxe7.thaumtraveller.api.IHandler;
import lunaglaxe7.thaumtraveller.api.UType;

public abstract class UTypeApplier {

    public UType type;

    public UTypeApplier(UType t) {
        this.type = t;
    }

    public static class Conducting extends UTypeApplier implements IHandler<CapInfoEvent.CapDiscountMul> {

        public Conducting() {
            super(UType.CONDUCTING);
        }

        public void handle(CapInfoEvent.CapDiscountMul event) {
            String key = "cap" + event.index + "#";
            ItemStack wand = event.stack;
            float mod = 1f;
            if (wand == null || !wand.hasTagCompound() || !wand.getTagCompound().hasKey(key)) return;
            NBTTagCompound nbt = wand.getTagCompound().getCompoundTag(key);
            NBTTagList list = nbt.getTagList(UType.UTYPE_KEY, 8);
            for (int i = 0; i < list.tagCount(); i++) {
                if (type.getName().equals(list.getStringTagAt(i))) mod *= 0.95f;
            }
            event.setModifier(event.getModifier() * mod);
        }
    }

    public static class Affinity extends UTypeApplier implements IHandler<CapInfoEvent.CapCraftCost> {

        public Affinity() {
            super(UType.AFFINITY);
        }

        public void handle(CapInfoEvent.CapCraftCost event) {
            ItemStack cap = event.stack;
            float mod = 1f;
            if (cap == null || !cap.hasTagCompound() || !cap.getTagCompound().hasKey(UType.UTYPE_KEY)) return;
            NBTTagList list = cap.getTagCompound().getTagList(UType.UTYPE_KEY, 8);
            for (int i = 0; i < list.tagCount(); i++) {
                if (type.getName().equals(list.getStringTagAt(i))) mod *= 0.8f;
            }
            event.setModifier(event.getModifier() * mod);
        }
    }
}
