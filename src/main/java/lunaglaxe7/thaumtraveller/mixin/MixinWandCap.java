package lunaglaxe7.thaumtraveller.mixin;

import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

@Mixin(value = WandCap.class, remap = false)
abstract class MixinWandCap {

    @Inject(
            method = "<init>(Ljava/lang/String;FLnet/minecraft/item/ItemStack;I)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, float discount, ItemStack item, int craftCost, CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerCap(item, tag);
    }

    @Inject(
            method = "<init>(Ljava/lang/String;FLjava/util/List;FLnet/minecraft/item/ItemStack;I)V",
            at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void onInit(String tag, float discount, List<Aspect> specialAspects, float discountSpecial, ItemStack item,
            int craftCost, CallbackInfo c) {
        ThaumTraveller.proxy.wandPartCache.registerCap(item, tag);
    }

    @Redirect(
            method = "<init>(Ljava/lang/String;FLnet/minecraft/item/ItemStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/LinkedHashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object notPut(LinkedHashMap map, Object k, Object v) {
        if (v instanceof SpecialCap) {
            return null;
        }
        return map.put(k, v);
    }

    @Redirect(
            method = "<init>(Ljava/lang/String;FLjava/util/List;FLnet/minecraft/item/ItemStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/LinkedHashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object notPut2(LinkedHashMap map, Object k, Object v) {
        if (v instanceof SpecialCap) {
            return null;
        }
        return map.put(k, v);
    }
}
