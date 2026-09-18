package lunaglaxe7.thaumtraveller.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


import com.llamalad7.mixinextras.sugar.Local;

import baubles.api.BaublesApi;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import thaumcraft.api.IRunicArmor;
import thaumcraft.common.items.baubles.ItemAmuletRunic;
import thaumcraft.common.items.baubles.ItemGirdleRunic;
import thaumcraft.common.items.baubles.ItemRingRunic;
import thaumcraft.common.lib.events.EventHandlerRunic;

/**
 * By this class, thaumcraft can scan expended baubles that have runic
 */
@Mixin(value = EventHandlerRunic.class, remap = false)
abstract class MixinEventHandlerRunic {

    @Invoker("getFinalCharge")
    static int invGetFinalCharge(ItemStack itemStack) {
        throw new AssertionError();
    }

    // @ModifyVariable(method = "livingTick",
    //// at = @At(value = "INVOKE", target =
    // "baubles/api/BaublesApi.getBaubles(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/inventory/IInventory;",
    // name = "max", at = @At(value = "STORE")
    // )
    // private int injectIn(int ori){
    // System.out.println("--------Getting max--------");
    // System.out.println("max:"+ori);
    // return ori;
    // }

    @ModifyVariable(method = "livingTick", name = "max", at = @At(value = "STORE", ordinal = 0))
    private int addExpendedCharge(int ori, @Local EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        int len = baubles.getSizeInventory();
        for (int i = 4; i < len; i++) {
            if (baubles.getStackInSlot(i) != null && baubles.getStackInSlot(i).getItem() instanceof IRunicArmor)
                ori += invGetFinalCharge(baubles.getStackInSlot(i));
        }
        return ori;
    }

    @ModifyVariable(method = "livingTick", name = "charged", at = @At(value = "STORE", ordinal = 0))
    private int addExpendedCharged(int ori, @Local EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        int len = baubles.getSizeInventory();
        ItemStack stack;
        for (int i = 4; i < len; i++) {
            stack = baubles.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof IRunicArmor) {
                if (stack.getItem() instanceof ItemRingRunic && stack.getItemDamage() == 2) ori++;
            }
        }
        return ori;
    }

    @ModifyVariable(method = "livingTick", name = "healing", at = @At(value = "STORE", ordinal = 0))
    private int addExpendedHealing(int ori, @Local EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        int len = baubles.getSizeInventory();
        ItemStack stack;
        for (int i = 4; i < len; i++) {
            stack = baubles.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof IRunicArmor) {
                if (stack.getItem() instanceof ItemRingRunic && stack.getItemDamage() == 3) ori++;
            }
        }
        return ori;
    }

    @ModifyVariable(method = "livingTick", name = "emergency", at = @At(value = "STORE", ordinal = 0))
    private int addExpendedEmergency(int ori, @Local EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        int len = baubles.getSizeInventory();
        ItemStack stack;
        for (int i = 4; i < len; i++) {
            stack = baubles.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof IRunicArmor) {
                if (stack.getItem() instanceof ItemAmuletRunic && stack.getItemDamage() == 1) ori++;
            }
        }
        return ori;
    }

    @ModifyVariable(method = "livingTick", name = "kinetic", at = @At(value = "STORE", ordinal = 0))
    private int addExpendedKinetic(int ori, @Local EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        int len = baubles.getSizeInventory();
        ItemStack stack;
        for (int i = 4; i < len; i++) {
            stack = baubles.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof IRunicArmor) {
                if (stack.getItem() instanceof ItemGirdleRunic && stack.getItemDamage() == 1) ori++;
            }
        }
        return ori;
    }

}
