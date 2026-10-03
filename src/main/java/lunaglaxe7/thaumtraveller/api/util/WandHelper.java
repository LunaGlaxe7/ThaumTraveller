package lunaglaxe7.thaumtraveller.api.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import lunaglaxe7.thaumtraveller.common.event.CapInfoEvent;
import lunaglaxe7.thaumtraveller.common.event.TravelEventManager;
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

    public static ItemStack getTopCapItem(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getCapItemFromWand(wand, 2);
    }

    public static ItemStack getBotCapItem(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getCapItemFromWand(wand, 1);
    }

    public static SpecialCap getTopSpecialCap(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCapFromWand(wand, 2);
    }

    public static SpecialCap getBotSpecialCap(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCapFromWand(wand, 1);
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

    // wand cant be not ItemWandCasting
    public static float calculateDiscount(ItemStack wand, Aspect a) {
        float out = 999f;
        if (wand.hasTagCompound()) {
            NBTTagCompound nbt = wand.getTagCompound();
            if (!nbt.hasKey("cap") && !nbt.hasKey("cap1")) out = getSpecialCap("iron").getSpecialCostModifier(a);
            if (nbt.hasKey("cap")) out = getSpecialCap(nbt.getString("cap")).getSpecialCostModifier(a);
            if (nbt.hasKey("cap1")) {
                out = (getSpecialCap(nbt.getString("cap1")).getSpecialCostModifier(a)
                        * TravelEventManager.multiplyDiscountModifiers(new CapInfoEvent.CapDiscountMul(wand, 1))
                        * TravelEventManager
                                .multiplySpecialDiscountModifiers(new CapInfoEvent.SpecialDiscountMul(wand, a, 1))
                        + getSpecialCap(nbt.getString("cap2")).getSpecialCostModifier(a)
                                * TravelEventManager.multiplyDiscountModifiers(new CapInfoEvent.CapDiscountMul(wand, 2))
                                * TravelEventManager.multiplySpecialDiscountModifiers(
                                        new CapInfoEvent.SpecialDiscountMul(wand, a, 2)))
                        / 2f;
            }
        }
        return out;
    }

    public static int calculateCraftCost(ItemStack cap1, ItemStack cap2) {
        int out = 999;
        if (cap1 != null && cap2 != null) {
            int c1 = getSpecialCapFromCap(cap1).getCraftCost();
            int c2 = getSpecialCapFromCap(cap2).getCraftCost();
            out = (int) (c1 * TravelEventManager.capCostModifiers(new CapInfoEvent.CapCraftCost(cap1))
                    + c2 * TravelEventManager.capCostModifiers(new CapInfoEvent.CapCraftCost(cap2))) / 2;
        }

        return Math.max(out, 1);
    }

    public static SpecialCap getSpecialCap(String tag) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCap(tag);
    }

    public static float getCapSpecialDiscount(ItemStack cap) {
        return getCap(cap) == null ? 999f : getCap(cap).getSpecialCostModifier();
    }

    public static List<Aspect> getCapSpecialAspect(ItemStack cap) {
        return getCap(cap) == null ? null : getCap(cap).getSpecialCostModifierAspects();
    }

}
