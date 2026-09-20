package lunaglaxe7.thaumtraveller.libs;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import lunaglaxe7.thaumtraveller.LogHandler;

public class WandPartCache {

    private Map<ItemWithDamage, String> caps = new ConcurrentHashMap<>();
    private Map<ItemWithDamage, String> rods = new ConcurrentHashMap<>();

    public void registerCap(ItemStack cap, String tag) {
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
