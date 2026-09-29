package lunaglaxe7.thaumtraveller.libs;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;

import org.apache.logging.log4j.Level;

import lunaglaxe7.thaumtraveller.LogHandler;
import lunaglaxe7.thaumtraveller.api.SpecialCap;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

public class WandPartCache {

    private final Map<ItemWithDamage, String> caps = new ConcurrentHashMap<>();
    private final Map<ItemWithDamage, String> rods = new ConcurrentHashMap<>();
    private final Map<String, SpecialCap> specialCaps = new ConcurrentHashMap<>();

    public void registerAllNormalSpecialCaps() {
        Map<String, WandCap> wandCap = WandCap.caps;
        String[] tags = caps.values().toArray(new String[0]);

        registerSpecialValinaCaps();
        for (String tag : tags) {
            if (specialCaps.containsKey(tag)) continue;
            SpecialCap s = SpecialCap.build(wandCap.get(tag).getItem());
            LogHandler.info(s.getTag() + " cap registed");
        }
        LogHandler.info("all special caps registed");
    }

    public void registerSpecialValinaCaps() {
        SpecialCap iron = new SpecialCap(
                "iron",
                WandCap.caps.get("iron").getItem(),
                1,
                1.1f,
                Arrays.asList(Aspect.EARTH),
                1);
        LogHandler.info(iron.getTag() + " cap normal registed");

        SpecialCap gold = new SpecialCap(
                "gold",
                WandCap.caps.get("gold").getItem(),
                3,
                1.05f,
                Arrays.asList(Aspect.WATER),
                0.95f);
        LogHandler.info(gold.getTag() + " cap normal registed");

        SpecialCap thaumium = new SpecialCap(
                "thaumium",
                WandCap.caps.get("thaumium").getItem(),
                6,
                0.92f,
                Arrays.asList(Aspect.EARTH),
                0.82f);
        LogHandler.info(thaumium.getTag() + " cap normal registed");

        SpecialCap voiD = new SpecialCap(
                "void",
                WandCap.caps.get("void").getItem(),
                9,
                0.85f,
                Arrays.asList(Aspect.AIR, Aspect.ENTROPY),
                0.73f);
        LogHandler.info(voiD.getTag() + " cap normal registed");

    }

    public void registerSpecialCap(SpecialCap cap) {
        if (cap != null) {
            String s = parseSpecialCap(cap);
            if (!specialCaps.containsKey(s)) {
                specialCaps.put(s, cap);
            }
        }
    }

    public SpecialCap getSpecialCap(String tag) {
        return specialCaps.getOrDefault(tag, null);
    }

    public SpecialCap getSpecialCapFromCap(ItemStack cap) {
        return specialCaps.getOrDefault(getCapTag(cap), null);
    }

    private void checkOldWands(ItemStack wand) {
        if (wand.getTagCompound().hasKey("cap")) {
            String tag = wand.getTagCompound().getString("cap");
            wand.setTagInfo("cap1", new NBTTagString(tag));
            wand.setTagInfo("cap2", new NBTTagString(tag));
            wand.getTagCompound().removeTag("cap");
        }
    }

    public SpecialCap getTopSpecialCap(ItemStack wand) {
        if (wand.hasTagCompound()) {
            checkOldWands(wand);
            String s = wand.getTagCompound().getString("cap2");
            if (s != null && specialCaps.containsKey(s)) return specialCaps.get(s);
        }
        return null;
    }

    public SpecialCap getBotSpecialCap(ItemStack wand) {
        if (wand.hasTagCompound()) {
            checkOldWands(wand);
            String s = wand.getTagCompound().getString("cap1");
            if (s != null && specialCaps.containsKey(s)) return specialCaps.get(s);
        }
        return null;
    }

    public String parseSpecialCap(SpecialCap cap) {
        if (cap == null) return null;
        return getCapTag(cap.getCap());
    }

    public void registerCap(ItemStack cap, String tag) {
        if (caps.containsValue(tag)) return;
        if (cap != null && tag != null &&
        // a bug when launched with thaumic tinker whose kami is falsed should be fixed by this
                cap.getItem() != null) {
            this.caps.put(new ItemWithDamage(cap), tag);
            LogHandler.log(Level.INFO, tag + " cap registed");
        }
    }

    public String getCapTag(ItemStack cap) {
        return caps.get(new ItemWithDamage(cap));
    }

    public boolean hasCap(ItemStack cap) {
        return caps.containsKey(new ItemWithDamage(cap));
    }

    public void registerRod(ItemStack rod, String tag) {
        if (rods.containsValue(tag)) return;
        if (rod != null && tag != null &&
        // a bug when launched with thaumic tinker whose kami is falsed should be fixed by this
                rod.getItem() != null) {
            this.rods.put(new ItemWithDamage(rod), tag);
            LogHandler.log(Level.INFO, tag + " rod registed");
        }
    }

    public String getRodTag(ItemStack rod) {
        return rods.get(new ItemWithDamage(rod));
    }

    public boolean hasRod(ItemStack rod) {
        return rods.containsKey(new ItemWithDamage(rod));
    }

    public static class ItemWithDamage {

        public Item item;
        public int damage;

        public ItemWithDamage(Item item, int damage) {
            this.item = item;
            this.damage = damage;
        }

        public ItemWithDamage(ItemStack s) {
            this.item = s.getItem();
            this.damage = s.getItemDamage();
        }

        public ItemWithDamage(Item item) {
            this(item, 0);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ItemWithDamage && ((ItemWithDamage) obj).item.equals(this.item)
                    && ((ItemWithDamage) obj).damage == this.damage;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.item, this.damage);
        }
    }

}
