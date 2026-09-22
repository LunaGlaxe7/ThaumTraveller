package lunaglaxe7.thaumtraveller.api.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

public class WandHelper {

    public static void setRod(ItemStack wand, ItemStack rod) {
        if (!rod.hasTagCompound()) {
            wand.setTagInfo("rod", new NBTTagString(getRod(rod).getTag()));
            return;
        }
        wand.setTagInfo("rod#", rod.getTagCompound());
    }

    public static String parseStringSpecialCap(SpecialCap cap) {
        return ThaumTraveller.proxy.wandPartCache.parseSpecialCap(cap);
    }

    public static void setCap(ItemStack wand, ItemStack cap) {
        if (!cap.hasTagCompound()) {
            wand.setTagInfo("cap", new NBTTagString(getCap(cap).getTag()));
            return;
        }
        wand.setTagInfo("cap#", cap.getTagCompound());
    }

    public static void setCapSpecial(ItemStack wand, ItemStack cap1, ItemStack cap2) {
        if (!cap1.hasTagCompound()) {
            wand.setTagInfo("cap1", new NBTTagString(getCap(cap1).getTag()));
        } else wand.setTagInfo("cap1#", cap1.getTagCompound());

        if (!cap2.hasTagCompound()) {
            wand.setTagInfo("cap2", new NBTTagString(getCap(cap2).getTag()));
        } else wand.setTagInfo("cap2#", cap2.getTagCompound());
    }

    public static WandCap getSpecialCap(ItemStack wand) {
        return ThaumTraveller.proxy.wandPartCache.getSpecialCap(wand);
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
