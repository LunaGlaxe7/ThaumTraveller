package lunaglaxe7.thaumtraveller.mixin;

import java.util.regex.Pattern;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import lunaglaxe7.thaumtraveller.api.SpecialCap;
import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;
import thaumcraft.common.items.wands.ItemWandCasting;

@Mixin(value = ItemWandCasting.class, remap = false)
public class MixinItemWandCasting {

    @Inject(method = "getCap", at = @At("HEAD"), cancellable = true)
    private void getSpecialCap(ItemStack item, CallbackInfoReturnable<WandCap> c) {
        if (item.hasTagCompound() && ((item.getTagCompound().hasKey("cap") || item.getTagCompound().hasKey("cap1")))) {
            c.setReturnValue(WandHelper.getSpecialCap(item));
            c.cancel();
        }
    }

    @ModifyVariable(
            method = "getConsumptionModifier",
            name = "consumptionModifier",
            at = @At(value = "STORE", shift = At.Shift.AFTER, ordinal = 2))
    private float costBetter(float ori, @Local ItemStack wand, @Local Aspect a) {
        return WandHelper.getSpecialCap(wand) instanceof SpecialCap
                ? ((SpecialCap) (WandHelper.getSpecialCap(wand))).getSpecialCostModifier(a)
                : ori;
    }

    @Redirect(
            method = "getItemStackDisplayName",
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;"))
    private String specialName(String ori, CharSequence c1, CharSequence c2, @Local ItemStack wand) {
        SpecialCap cap;
        if (WandHelper.getSpecialCap(wand) instanceof SpecialCap) {
            cap = (SpecialCap) WandHelper.getSpecialCap(wand);
            String tag = cap.getTag();
            if (tag.contains("|")) {
                // "|" in regex has special meaning...
                String[] tags = tag.split(Pattern.quote("|"));
                ori = ori.replace(
                        "%CAP",
                        // normally 0 is the bottom, 1 is the top
                        // 1 is the bottom and 0 is the top when flipped
                        StatCollector.translateToLocal("item.Wand." + tags[WandHelper.checkFlippedByte(wand)] + ".cap")
                                + "-"
                                + StatCollector.translateToLocal(
                                        "item.Wand." + tags[1 - WandHelper.checkFlippedByte(wand)] + ".cap")
                                + StatCollector.translateToLocal("item.Wand.mismatched.cap"));
            }
        } else ori = ori.replace(c1, c2);
        return ori;
    }

}
