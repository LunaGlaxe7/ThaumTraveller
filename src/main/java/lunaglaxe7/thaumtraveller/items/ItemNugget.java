package lunaglaxe7.thaumtraveller.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class ItemNugget extends Item {

    public static List<ItemNugget> nuggets = new ArrayList<ItemNugget>();
    public String name;
    public static ItemNugget meteoricIron;

    public ItemNugget(String name) {
        this.name = "nugget" + name;
        this.setMaxDamage(0).setUnlocalizedName(this.name).setTextureName("ThaumTraveller:" + this.name);
        nuggets.add(this);
    }

    public static void addNuggetsGC() {
        meteoricIron = new ItemNugget("MeteoricIron");
    }
}
