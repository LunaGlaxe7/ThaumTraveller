package lunaglaxe7.thaumtraveller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

public class SpecialCap extends WandCap {

    private ItemStack cap;
    private float discount;
    private Map<String, Float> specialDiscount;

    public static SpecialCap build(ItemStack cap) {
        return new SpecialCap(cap, WandHelper.getCap(cap));
    }

    public SpecialCap(String tag, ItemStack cap, int craftCost, float baseDiscount, List<Aspect> specialAspects,
            float specialDiscount) {
        super(tag, baseDiscount, specialAspects, specialDiscount, cap, craftCost);
        this.cap = cap;
        discount = baseDiscount;
        this.specialDiscount = new HashMap<>();
        if (specialAspects != null) {
            for (Aspect a : specialAspects) {
                this.specialDiscount.put(a.getTag(), specialDiscount);
            }
        }
        for (Aspect a : Aspect.getPrimalAspects()) {
            if (this.specialDiscount.containsKey(a.getTag())) continue;
            this.specialDiscount.put(a.getTag(), this.discount);
        }
        ThaumTraveller.proxy.wandPartCache.registerSpecialCap(this);
    }

    private SpecialCap(ItemStack cap, WandCap fake) {
        super(fake.getTag(), fake.getBaseCostModifier(), fake.getItem(), fake.getCraftCost());
        this.cap = cap;
        discount = getCapBaseDiscount();
        specialDiscount = buildSpecialDiscount(this);
        ThaumTraveller.proxy.wandPartCache.registerSpecialCap(this);
    }

    public ItemStack getCap() {
        return cap;
    }

    private float getCapBaseDiscount() {
        return super.getBaseCostModifier();
    }

    @Override
    public float getBaseCostModifier() {
        return this.discount;
    }

    public float getSpecialCostModifier(Aspect a) {
        return specialDiscount.get(a.getTag());
    }

    @Override
    public List<Aspect> getSpecialCostModifierAspects() {
        return Aspect.getPrimalAspects();
    }

    private static Map<String, Float> buildSpecialDiscount(SpecialCap c) {
        Map<String, Float> specialDiscount = new HashMap<>();
        for (Aspect a : Aspect.getPrimalAspects()) {
            specialDiscount.put(a.getTag(), c.discount);
        }
        ItemStack cap = c.getCap();
        if (WandHelper.getCapSpecialAspect(cap) != null) {
            List<Aspect> s = WandHelper.getCapSpecialAspect(cap);
            float f = WandHelper.getCapSpecialDiscount(cap);
            for (Aspect a : s) {
                specialDiscount.put(a.getTag(), f);
            }
        }
        return specialDiscount;
    }

}
