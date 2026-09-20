package lunaglaxe7.thaumtraveller.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.WandRod;

@Mixin(value = WandRod.class, remap = false)
abstract class MixinWandRod {

    @Inject(
            method = "<init>(Ljava/lang/String;ILnet/minecraft/item/ItemStack;ILnet/minecraft/util/ResourceLocation;)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, int capacity, ItemStack item, int craftCost, ResourceLocation texture,
            CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerRod(item, tag);
    }

    @Inject(
            method = "<init>(Ljava/lang/String;ILnet/minecraft/item/ItemStack;ILthaumcraft/api/wands/IWandRodOnUpdate;Lnet/minecraft/util/ResourceLocation;)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate,
            ResourceLocation texture, CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerRod(item, tag);
    }

    @Inject(
            method = "<init>(Ljava/lang/String;ILnet/minecraft/item/ItemStack;I)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, int capacity, ItemStack item, int craftCost, CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerRod(item, tag);
    }

    @Inject(
            method = "<init>(Ljava/lang/String;ILnet/minecraft/item/ItemStack;ILthaumcraft/api/wands/IWandRodOnUpdate;)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate,
            CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerRod(item, tag);
    }
}
