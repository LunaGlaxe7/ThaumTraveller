package lunaglaxe7.thaumtraveller.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cpw.mods.fml.common.registry.GameRegistry;
import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import lunaglaxe7.thaumtraveller.common.TTRContents;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import lunaglaxe7.thaumtraveller.libs.WandPartCache;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.lib.crafting.ArcaneWandRecipe;

@Mixin(value = ArcaneWandRecipe.class, remap = false)
abstract class MixinArcaneWandRecipe {

    @Inject(method = "getCraftingResult", cancellable = true, at = @At("HEAD"))
    // at = @At(value = "INVOKE", target =
    // "thaumcraft/api/ThaumcraftApiHelper.getStackInRowAndColumn(Ljava/lang/Object;II)Lnet/minecraft/item/ItemStack;",
    // shift = At.Shift.AFTER,ordinal = 2))
    private void diffWand(IInventory inv, CallbackInfoReturnable<ItemStack> cb) {
        ItemStack out = null;
        ItemStack cap1 = getStack(inv, 0, 2);
        ItemStack rod = getStack(inv, 1, 1);
        ItemStack cap2 = getStack(inv, 2, 0);
        if (checkWandTemplate(inv)) {
            if (!cap1.isItemEqual(cap2)) {
                String tagC1 = getCache().getCapTag(cap1);
                String tagC2 = getCache().getCapTag(cap2);
                String tagR = getCache().getRodTag(rod);
                int costC1 = 0, costC2 = 0, costR = 0;
                if (tagC1 != null && tagC2 != null && tagR != null) {
                    costC1 = WandCap.caps.get(tagC1).getCraftCost();
                    costC2 = WandCap.caps.get(tagC2).getCraftCost();
                    costR = WandRod.rods.get(tagR).getCraftCost();

                    int cost = (costC1 + costC2) * costR / 2;
                    out = new ItemStack(GameRegistry.findItem(TTRContents.TCID, "WandCasting"), 1, cost);
                    WandHelper.setCapSpecial(out, cap1, cap2);
                    WandHelper.setRod(out, rod);
                }
                cb.setReturnValue(out);
                cb.cancel();
            }
        }

    }

    @Inject(method = "getAspects", at = @At("HEAD"), cancellable = true)
    private void diffAspects(IInventory inv, CallbackInfoReturnable<AspectList> c) {
        AspectList out = new AspectList();
        ItemStack cap1 = getStack(inv, 0, 2);
        ItemStack rod = getStack(inv, 1, 1);
        ItemStack cap2 = getStack(inv, 2, 0);
        if (checkWandTemplate(inv)) {
            if (!cap1.isItemEqual(cap2)) {
                String tagC1 = getCache().getCapTag(cap1);
                String tagC2 = getCache().getCapTag(cap2);
                String tagR = getCache().getRodTag(rod);
                int costC1 = 0, costC2 = 0, costR = 0;
                if (tagC1 != null && tagC2 != null && tagR != null) {
                    costC1 = WandCap.caps.get(tagC1).getCraftCost();
                    costC2 = WandCap.caps.get(tagC2).getCraftCost();
                    costR = WandRod.rods.get(tagR).getCraftCost();

                    int cost = (costC1 + costC2) * costR / 2;
                    for (Aspect a : Aspect.getPrimalAspects()) {
                        out.add(a, cost);
                    }
                    c.setReturnValue(out);
                    c.cancel();
                }
            }
        }
    }

    @Inject(method = "checkMatch", cancellable = true, at = @At(value = "HEAD"))
    // now every two caps can make a wand
    private void diffCheck(ItemStack cap1, ItemStack cap2, ItemStack rod, EntityPlayer player,
            CallbackInfoReturnable<Boolean> c) {
        if (cap1 != null && cap2 != null && rod != null) {
            if (getCache().hasCap(cap1) && getCache().hasCap(cap2) && getCache().hasRod(rod)) {
                String[] tag = new String[] { getCache().getCapTag(cap1), getCache().getCapTag(cap2),
                        getCache().getRodTag(rod) };
                if (ThaumcraftApiHelper
                        .isResearchComplete(player.getCommandSenderName(), WandCap.caps.get(tag[0]).getResearch())
                        && ThaumcraftApiHelper.isResearchComplete(
                                player.getCommandSenderName(),
                                WandCap.caps.get(tag[1]).getResearch())
                        && ThaumcraftApiHelper.isResearchComplete(
                                player.getCommandSenderName(),
                                WandRod.rods.get(tag[2]).getResearch())
                        && ThaumcraftApiHelper
                                .isResearchComplete(player.getCommandSenderName(), TTRContents.MISMATCHEDCAPKEY)) {
                    c.setReturnValue(Boolean.TRUE);
                    c.cancel();
                }
            }
        }
    }

    // 这一大截判断也太雷霆
    private boolean checkWandTemplate(IInventory inv) {
        ItemStack cap1 = getStack(inv, 0, 2);
        ItemStack rod = getStack(inv, 1, 1);
        ItemStack cap2 = getStack(inv, 2, 0);
        return cap1 != null && cap2 != null
                && rod != null
                && getStack(inv, 0, 0) == null
                && getStack(inv, 0, 1) == null
                && getStack(inv, 1, 0) == null
                && getStack(inv, 1, 2) == null
                && getStack(inv, 2, 1) == null
                && getStack(inv, 2, 2) == null;
    }

    private WandPartCache getCache() {
        return ThaumTraveller.proxy.wandPartCache;
    }

    private ItemStack getStack(IInventory inv, int row, int col) {
        return ThaumcraftApiHelper.getStackInRowAndColumn(inv, row, col);
    }

}
