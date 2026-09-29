package lunaglaxe7.thaumtraveller.api.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

public class WandHelper {

    public static void setRod(ItemStack wand, ItemStack rod) {
        wand.setTagInfo("rod", new NBTTagString(getRod(rod).getTag()));
        if (rod.hasTagCompound()) wand.setTagInfo("rod#", rod.getTagCompound());
    }

    public static void setCapsNbt(ItemStack wand, ItemStack cap1, ItemStack cap2) {
        if (cap1.hasTagCompound()) wand.setTagInfo("cap1#", cap1.getTagCompound());
        if (cap2.hasTagCompound()) wand.setTagInfo("cap2#", cap2.getTagCompound());
    }

    public static void setCaps(ItemStack wand, SpecialCap cap1, SpecialCap cap2) {
        wand.setTagInfo("cap1", new NBTTagString(cap1.getTag()));
        wand.setTagInfo("cap2", new NBTTagString(cap2.getTag()));
    }

    public static SpecialCap getTopSpecialCap(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getTopSpecialCap(wand);
    }

    public static SpecialCap getBotSpecialCap(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getBotSpecialCap(wand);
    }

    public static SpecialCap getSpecialCapFromCap(ItemStack cap) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCapFromCap(cap);
    }

    public static WandCap getCap(ItemStack cap) {
        if (cap != null && ThaumTraveller.proxy.wandPartCache.hasCap(cap)) {
            return WandCap.caps.get(ThaumTraveller.proxy.wandPartCache.getCapTag(cap));
        }
        return null;
    }

    public static WandRod getRod(ItemStack rod) {
        if (rod != null && ThaumTraveller.proxy.wandPartCache.hasRod(rod)) {
            return WandRod.rods.get(ThaumTraveller.proxy.wandPartCache.getRodTag(rod));
        }
        return null;
    }

    public static float calculateDiscount(ItemStack wand, Aspect a) {
        if (wand.hasTagCompound()) {
            NBTTagCompound nbt = wand.getTagCompound();
            if (nbt.hasKey("cap")) return getSpecialCap(nbt.getString("cap")).getSpecialCostModifier(a);
            if (nbt.hasKey("cap1")) {
                return (getSpecialCap(nbt.getString("cap1")).getSpecialCostModifier(a)
                        + getSpecialCap(nbt.getString("cap2")).getSpecialCostModifier(a)) / 2f;
            }
        }
        return 999f;
    }

    // dont pass a null here
    public static int calculateCraftCost(SpecialCap cap1, SpecialCap cap2) {
        return (cap1.getCraftCost() + cap2.getCraftCost()) / 2;
    }

    public static SpecialCap getSpecialCap(String tag) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCap(tag);
    }

    public static int getCapCost(ItemStack cap) {
        return getCap(cap) == null ? 999 : getCap(cap).getCraftCost();
    }

    public static float getCapDiscount(ItemStack cap) {
        return getCap(cap) == null ? 999f : getCap(cap).getBaseCostModifier();
    }

    public static float getCapSpecialDiscount(ItemStack cap) {
        return getCap(cap) == null ? 999f : getCap(cap).getSpecialCostModifier();
    }

    public static List<Aspect> getCapSpecialAspect(ItemStack cap) {
        return getCap(cap) == null ? null : getCap(cap).getSpecialCostModifierAspects();
    }

}
