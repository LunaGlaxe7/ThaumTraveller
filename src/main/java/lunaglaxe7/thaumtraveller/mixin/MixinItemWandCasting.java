package lunaglaxe7.thaumtraveller.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;
import thaumcraft.common.items.wands.ItemWandCasting;

@Mixin(value = ItemWandCasting.class, remap = false)
public class MixinItemWandCasting {

    @Inject(method = "getCap", at = @At("HEAD"), cancellable = true)
    private void getSpecialCap(ItemStack item, CallbackInfoReturnable<WandCap> c) {
        if (item.hasTagCompound() && ((item.getTagCompound().hasKey("cap") || item.getTagCompound().hasKey("cap1")))) {
            c.setReturnValue(WandHelper.getTopSpecialCap(item));
            c.cancel();
        }
    }

    @ModifyVariable(
            method = "getConsumptionModifier",
            name = "consumptionModifier",
            at = @At(value = "STORE", shift = At.Shift.AFTER, ordinal = 2))
    private float costBetter(float ori, @Local ItemStack wand, @Local Aspect a) {
        return WandHelper.calculateDiscount(wand, a);
    }

    @Redirect(
            method = "getItemStackDisplayName",
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;"))
    private String specialName(String ori, CharSequence c1, CharSequence c2, @Local ItemStack wand) {
        String tag1 = WandHelper.getBotSpecialCap(wand).getTag();
        String tag2 = WandHelper.getTopSpecialCap(wand).getTag();

        if (!tag1.equals(tag2)) {
            ori = ori.replace(
                    "%CAP",
                    StatCollector.translateToLocal("item.Wand." + tag1 + ".cap") + "-"
                            + StatCollector.translateToLocal("item.Wand." + tag2 + ".cap")
                            + StatCollector.translateToLocal("item.Wand.mismatched.cap"));
        } else ori = ori.replace(c1, c2);
        return ori;
    }

}
