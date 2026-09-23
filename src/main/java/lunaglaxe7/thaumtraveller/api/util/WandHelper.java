package lunaglaxe7.thaumtraveller.api.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
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

    public static void setFlipped(ItemStack wand, String tag1, String tag2) {
        wand.setTagInfo("flipped", new NBTTagByte(checkFlippedByTags(tag1, tag2) ? (byte) 1 : (byte) 0));
    }

    // may be useful
    public static int checkFlippedByte(ItemStack wand) {
        return wand.getTagCompound().getByte("flipped");
    }

    public static boolean checkFlippedByTags(String tag1, String tag2) {
        return ThaumTraveller.proxy.wandPartCache.checkFlipped(tag1, tag2);
    }

    public static String parseStringSpecialCap(SpecialCap cap) {
        return ThaumTraveller.proxy.wandPartCache.parseSpecialCap(cap);
    }

    public static void setCap(ItemStack wand, ItemStack cap) {
        wand.setTagInfo("cap", new NBTTagString(getCap(cap).getTag()));
        if (cap.hasTagCompound()) wand.setTagInfo("cap#", cap.getTagCompound());
    }

    public static void setCapSpecial(ItemStack wand, ItemStack cap1, ItemStack cap2) {
        wand.setTagInfo("cap1", new NBTTagString(getCap(cap1).getTag()));
        if (cap1.hasTagCompound()) wand.setTagInfo("cap1#", cap1.getTagCompound());

        wand.setTagInfo("cap2", new NBTTagString(getCap(cap2).getTag()));
        if (cap2.hasTagCompound()) wand.setTagInfo("cap2#", cap2.getTagCompound());
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
