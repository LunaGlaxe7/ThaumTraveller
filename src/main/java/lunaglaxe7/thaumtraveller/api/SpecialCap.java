package lunaglaxe7.thaumtraveller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        SpecialCap out = null;
        ItemStack cap = null;

        // normal cap
        if (nbt.hasKey("cap")) {
            return caps.get(nbt.getString("cap"));
        }

        // special cap with diff
        if (nbt.hasKey("cap1")) {
            return build(nbt.getString("cap1"), nbt.getString("cap2"));
        }
        // 用#来指定这是有附加信息的
        // '#' used to mark a special cap
        if (nbt.hasKey("cap#")) {
            NBTTagCompound capNbt = nbt.getCompoundTag("cap#");
            // TODO
        }
        if (nbt.hasKey("cap1#")) {
            NBTTagCompound cap1Nbt = nbt.getCompoundTag("cap1#");
            NBTTagCompound cap2Nbt = nbt.getCompoundTag("cap2#");
            // TODO
        }
        return out;
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
        int cost = diff ? (WandHelper.getCapCost(cap1) + WandHelper.getCapCost(cap2)) / 2 : WandHelper.getCapCost(cap);
        Integer c = cost;

        return cost;
    }

    /**
     *
     * @return
     */
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

    private float getCapBaseDiscount() {
        return diff ? (WandHelper.getCapDiscount(cap1) + WandHelper.getCapDiscount(cap2)) / 2
                : WandHelper.getCapDiscount(cap);
    }

    @Override
    public float getBaseCostModifier() {
        return this.discount;
    }

    @Override
    public float getSpecialCostModifier() {
        if (cap == null && cap1 == null && cap2 == null) return super.getSpecialCostModifier();
        if (!diff) {
            return super.getSpecialCostModifier();
        } else {
            if (WandHelper.getCapSpecialAspect(cap1) != null && WandHelper.getCapSpecialAspect(cap2) != null) {
                return (WandHelper.getCapSpecialDiscount(cap1) + WandHelper.getCapSpecialDiscount(cap2)) / 2f;
            } else if (WandHelper.getCapSpecialAspect(cap1) == null) {
                return (WandHelper.getCapDiscount(cap1) + WandHelper.getCapSpecialDiscount(cap2)) / 2f;
            } else return (WandHelper.getCapSpecialDiscount(cap1) + WandHelper.getCapDiscount(cap2)) / 2f;
        }
    }

    public float getSpecialCostModifier(Aspect a) {
        return specialDiscount.get(a.getTag());
    }

    @Override
    public List<Aspect> getSpecialCostModifierAspects() {
        return listSpecialDiscount(specialDiscount);
    }

    public static List<Aspect> listSpecialDiscount(Map<String, Float> specialDiscount) {
        if (specialDiscount != null) {
            Set<String> tags = specialDiscount.keySet();
            List<Aspect> out = new ArrayList<>();
            for (String tag : tags) {
                out.add(Aspect.getAspect(tag));
            }
            return out;
        }
        return null;
    }

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
    private static Map<String, Float> buildSpecialDiscountSpecial(SpecialCap cap) {
        Map<String, Float> specialDiscount = new HashMap<>();
        ItemStack cap1 = cap.getCaps()[0];
        ItemStack cap2 = cap.getCaps()[1];
        for (Aspect a : Aspect.getPrimalAspects()) {
            specialDiscount.put(a.getTag(), cap.discount);
        }
        List<Aspect> s1 = WandHelper.getCapSpecialAspect(cap1);
        List<Aspect> s2 = WandHelper.getCapSpecialAspect(cap2);

        if (s1 == null && s2 == null) return specialDiscount;

        float f1 = s1 == null ? 0f : WandHelper.getCapSpecialDiscount(cap1);
        float f2 = s2 == null ? 0f : WandHelper.getCapSpecialDiscount(cap2);

        if (s1 != null) {
            for (Aspect a : s1) {
                if (s2 != null && s2.contains(a)) {
                    // both caps have special discount so do average
                    specialDiscount.put(a.getTag(), (f1 + f2) / 2f);
                    continue;
                }
                // only cap1 , so f1
                // but cap2 has base discount , so do average
                specialDiscount.put(a.getTag(), (WandHelper.getCapDiscount(cap2) + f1) / 2f);
            }
        }

        if (s2 != null) {
            for (Aspect a : s2) {
                if (s1 != null && s1.contains(a)) continue;
                // only cap2, so f2
                // but cap1 has base discount , so do average
                specialDiscount.put(a.getTag(), (WandHelper.getCapDiscount(cap1) + f2) / 2f);
            }
        }

        return specialDiscount;
    }

}
