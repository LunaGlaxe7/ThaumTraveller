package lunaglaxe7.thaumtraveller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

public class SpecialCap extends WandCap {

    private final boolean diff;
    private ItemStack cap;
    private ItemStack cap1;
    private ItemStack cap2;
    private float discount;
    private Map<String, Float> specialDiscount;

    public static WandCap buildFromWand(ItemStack wand) {
        if (!wand.hasTagCompound()) return null;
        return buildFromNbt(wand.getTagCompound());
    }

    public static WandCap buildFromNbt(NBTTagCompound nbt) {
        // normal cap
        if (nbt.hasKey("cap")) {
            return caps.get(nbt.getString("cap"));
        }

        // special cap with diff
        if (nbt.hasKey("cap1")) {
            return build(nbt.getString("cap1"), nbt.getString("cap2"));
        }
        return null;
    }

    public static SpecialCap build(String tag1, String tag2) {
        return build(caps.get(tag1).getItem(), caps.get(tag2).getItem());
    }

    public static SpecialCap build(ItemStack cap1, ItemStack cap2) {
        return new SpecialCap(cap1, cap2, WandHelper.getCap(cap1));
    }

    public static SpecialCap build(ItemStack cap) {
        return new SpecialCap(cap, WandHelper.getCap(cap));
    }

    public SpecialCap(String tag, ItemStack cap, int craftCost, float baseDiscount, List<Aspect> specialAspects,
            float specialDiscount) {
        super(tag, baseDiscount, specialAspects, specialDiscount, cap, craftCost);
        this.diff = false;
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

    private SpecialCap(ItemStack cap1, ItemStack cap2, WandCap fake) {
        super(fake.getTag(), fake.getBaseCostModifier(), fake.getItem(), fake.getCraftCost());
        this.diff = true;
        this.cap1 = cap1;
        this.cap2 = cap2;
        discount = getCapBaseDiscount();
        specialDiscount = buildSpecialDiscount(this);
        ThaumTraveller.proxy.wandPartCache.registerSpecialCap(this);
    }

    private SpecialCap(ItemStack cap, WandCap fake) {
        super(fake.getTag(), fake.getBaseCostModifier(), fake.getItem(), fake.getCraftCost());
        this.diff = false;
        this.cap = cap;
        discount = getCapBaseDiscount();
        specialDiscount = buildSpecialDiscount(this);
        ThaumTraveller.proxy.wandPartCache.registerSpecialCap(this);
    }

    @Override
    public String getTag() {
        // constructing
        if (cap == null && cap1 == null && cap2 == null) return super.getTag();
        // others
        return WandHelper.parseStringSpecialCap(this);
    }

    @Override
    public ResourceLocation getTexture() {
        if (cap == null && cap1 == null && cap2 == null) return super.getTexture();
        return diff ? WandHelper.getCap(cap1).getTexture() : WandHelper.getCap(cap).getTexture();
    }

    @Override
    public int getCraftCost() {
        return diff ? (WandHelper.getCapCost(cap1) + WandHelper.getCapCost(cap2)) / 2 : WandHelper.getCapCost(cap);
    }

    public boolean isDiff() {
        return diff;
    }

    public ItemStack getCap() {
        return cap;
    }

    /**
     * normally 0 is the bottom, 1 is the top. 1 is the bottom and 0 is the top when flipped
     */
    public ItemStack[] getCaps() {
        return new ItemStack[] { cap1, cap2 };
    }

    // 只在不同cap或单一cap但没有重新设置数值时调用
    private float getCapBaseDiscount() {
        return diff
                ? (WandHelper.getSpecialCapFromCap(cap1).getBaseCostModifier()
                        + WandHelper.getSpecialCapFromCap(cap2).getBaseCostModifier()) / 2
                : WandHelper.getCapDiscount(cap);
    }

    @Override
    public float getBaseCostModifier() {
        return this.discount;
    }

    @Override
    public float getSpecialCostModifier() {
        if (cap == null && cap1 == null && cap2 == null) return super.getSpecialCostModifier();
        // I don't know now when this will be used
        if (!diff) {
            return super.getSpecialCostModifier();
        } else {
            if (WandHelper.getCapSpecialAspect(cap1) != null && WandHelper.getCapSpecialAspect(cap2) != null) {
                return (WandHelper.getCapSpecialDiscount(cap1) + WandHelper.getCapSpecialDiscount(cap2)) / 2f;
            } else if (WandHelper.getCapSpecialAspect(cap1) == null) {
                return (WandHelper.getCapDiscount(cap1) + WandHelper.getCapSpecialDiscount(cap2)) / 2f;
            } else if (WandHelper.getCapSpecialAspect(cap2) == null)
                return (WandHelper.getCapSpecialDiscount(cap1) + WandHelper.getCapDiscount(cap2)) / 2f;
            else return this.getBaseCostModifier();
        }
    }

    public float getSpecialCostModifier(Aspect a) {
        return specialDiscount.get(a.getTag());
    }

    @Override
    public List<Aspect> getSpecialCostModifierAspects() {
        return Aspect.getPrimalAspects();
    }

    // deprecated for specialDiscount field contains all primal Aspects now
    // public static List<Aspect> listSpecialDiscount(Map<String, Float> specialDiscount) {
    // if (specialDiscount != null) {
    // Set<String> tags = specialDiscount.keySet();
    // List<Aspect> out = new ArrayList<>();
    // for (String tag : tags) {
    // out.add(Aspect.getAspect(tag));
    // }
    // return out;
    // }
    // return null;
    // }

    private static Map<String, Float> buildSpecialDiscount(SpecialCap cap) {
        return cap.diff ? buildSpecialDiscountSpecial(cap) : buildSpecialDiscountNormal(cap);
    }

    // only used when not diff
    private static Map<String, Float> buildSpecialDiscountNormal(SpecialCap c) {
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

    // only used when diff
    // not diff cache is already built so use special cap data directly
    private static Map<String, Float> buildSpecialDiscountSpecial(SpecialCap cap) {
        Map<String, Float> specialDiscount = new HashMap<>();
        // ItemStack cap1 = cap.getCaps()[0];
        //// ItemStack cap2 = cap.getCaps()[1];
        // for (Aspect a : Aspect.getPrimalAspects()) {
        // specialDiscount.put(a.getTag(), cap.discount);
        // }

        SpecialCap cap1 = WandHelper.getSpecialCapFromCap(cap.getCaps()[0]);
        SpecialCap cap2 = WandHelper.getSpecialCapFromCap(cap.getCaps()[1]);

        for (Aspect a : Aspect.getPrimalAspects()) {
            specialDiscount.put(a.getTag(), (cap1.getSpecialCostModifier(a) + cap2.getSpecialCostModifier(a)) / 2f);
        }

        // List<Aspect> s1 = WandHelper.getCapSpecialAspect(cap1);
        // List<Aspect> s2 = WandHelper.getCapSpecialAspect(cap2);
        //
        // if (s1 == null && s2 == null) return specialDiscount;
        //
        // float f1 = s1 == null ? 0f : WandHelper.getCapSpecialDiscount(cap1);
        // float f2 = s2 == null ? 0f : WandHelper.getCapSpecialDiscount(cap2);
        //
        // if (s1 != null) {
        // for (Aspect a : s1) {
        // if (s2 != null && s2.contains(a)) {
        // // both caps have special discount so do average
        // specialDiscount.put(a.getTag(), (f1 + f2) / 2f);
        // continue;
        // }
        // // only cap1 , so f1
        // // but cap2 has base discount , so do average
        // specialDiscount.put(a.getTag(), (WandHelper.getCapDiscount(cap2) + f1) / 2f);
        // }
        // }
        //
        // if (s2 != null) {
        // for (Aspect a : s2) {
        // if (s1 != null && s1.contains(a)) continue;
        // // only cap2, so f2
        // // but cap1 has base discount , so do average
        // specialDiscount.put(a.getTag(), (WandHelper.getCapDiscount(cap1) + f2) / 2f);
        // }
        // }

        return specialDiscount;
    }

}
