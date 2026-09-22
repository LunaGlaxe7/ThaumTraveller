package lunaglaxe7.thaumtraveller.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import thaumcraft.api.wands.WandCap;
import thaumcraft.client.renderers.models.gear.ModelWand;

@Mixin(value = ModelWand.class, remap = false)
abstract class MixinModelWand {

    @Inject(
            method = "render(Lnet/minecraft/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 1))
    private void bindTopFirst(ItemStack wand, CallbackInfo c) {
        WandCap cap = WandHelper.getSpecialCap(wand);
        if (cap instanceof SpecialCap) {
            SpecialCap s = (SpecialCap) cap;
            if (s.isDiff()) {
                WandCap cap2 = WandHelper.getCap(s.getCaps()[1]);
                Minecraft.getMinecraft().renderEngine.bindTexture(cap2.getTexture());
            }
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelRenderer;render(F)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 5))
    private void bindBottomSecond(ItemStack wand, CallbackInfo c) {
        WandCap cap = WandHelper.getSpecialCap(wand);
        if (cap instanceof SpecialCap) {
            SpecialCap s = (SpecialCap) cap;
            // s can't be null
            if (s.isDiff()) {
                WandCap cap1 = WandHelper.getCap(s.getCaps()[0]);
                Minecraft.getMinecraft().renderEngine.bindTexture(cap1.getTexture());
            }
        }
    }

}
