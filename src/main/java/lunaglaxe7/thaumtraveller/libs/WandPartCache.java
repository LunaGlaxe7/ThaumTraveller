package lunaglaxe7.thaumtraveller.libs;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.Level;

import lunaglaxe7.thaumtraveller.LogHandler;
import lunaglaxe7.thaumtraveller.api.SpecialCap;
import thaumcraft.api.wands.WandCap;

public class WandPartCache {

    private final Map<ItemWithDamage, String> caps = new ConcurrentHashMap<>();
    private final Map<ItemWithDamage, String> rods = new ConcurrentHashMap<>();
    private final Map<String, SpecialCap> specialCaps = new ConcurrentHashMap<>();

    public void registerAllNormalSpecialCaps() {
        WandCap[] caps = WandCap.caps.values().toArray(new WandCap[0]);
        for (int i = 0; i < caps.length; i++) {
            for (int j = 0; j < caps.length; j++) {
                if (i == j) continue;
                SpecialCap s = SpecialCap.build(caps[i].getItem(), caps[j].getItem());
                LogHandler.info(parseSpecialCap(s) + " cap special registed");
            }
        }
        LogHandler.info("all normal special caps registed");
    }

    public SpecialCap registerSpecialCap(SpecialCap cap) {
        if (cap != null) {
            String s = parseSpecialCap(cap);
            if (!specialCaps.containsKey(s)) {
                return specialCaps.put(s, cap);
            }
        }
        return null;
    }

    public WandCap getSpecialCap(ItemStack wand) {
        if (wand.hasTagCompound()) {
            String s = parseSpecialCapFromNBT(wand.getTagCompound());
            if (s != null) {
                // this condition to be upgraded
                if (s.contains("|") || s.contains("#")) {
                    if (specialCaps.containsKey(s)) {
                        return specialCaps.get(s);
                    } else {
                        return SpecialCap.buildFromWand(wand);
                    }
                } else if (WandCap.caps.containsKey(s)) return WandCap.caps.get(s);
            }
        }
        return null;
    }

    public boolean hasSpecialCap(ItemStack wand) {
        if (wand.hasTagCompound()) {
            return specialCaps.containsKey(parseSpecialCapFromNBT(wand.getTagCompound()));
        }
        return false;
    }

    public String parseSpecialCapFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("cap")) {
            return nbt.getString("cap");
        }
        if (nbt.hasKey("cap1")) {
            return nbt.getString("cap1") + "|" + nbt.getString("cap2");
        }
        return null;
    }

    public String parseSpecialCap(SpecialCap cap) {
        if (cap == null) return null;
        if (cap.isDiff()) {
            ItemStack[] caps = cap.getCaps();
            String[] tags = new String[] { getCapTag(caps[0]), getCapTag(caps[1]) };
            if (caps[0].hasTagCompound()) {
                // TODO
            }
            return tags[0] + "|" + tags[1];
        }
        String tag = cap.getTag();
        if (cap.getCap().hasTagCompound()) {
            // TODO
        }
        return tag;
    }

    public void registerCap(ItemStack cap, String tag) {
        if (caps.containsValue(tag)) return;
        if (cap != null && tag != null) {
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
        if (rod != null && tag != null) {
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
