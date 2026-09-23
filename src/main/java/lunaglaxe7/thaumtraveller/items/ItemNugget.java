package lunaglaxe7.thaumtraveller.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

import cpw.mods.fml.common.Optional;
import lunaglaxe7.thaumtraveller.common.TTRContents;

public class ItemNugget extends Item {

    public static List<ItemNugget> nuggets = new ArrayList<ItemNugget>();
    public String name;
    public static ItemNugget meteoricIron;

    public ItemNugget(String name) {
        this.name = "nugget" + name;
        // creative tab to be added
        this.setMaxDamage(0).setUnlocalizedName(this.name).setTextureName(TTRContents.MODID + ":" + this.name);
        nuggets.add(this);
    }

    public static void addNuggets() {
        addNuggetsGC();
    }

    @Optional.Method(modid = TTRContents.GCID)
    public static void addNuggetsGC() {
        meteoricIron = new ItemNugget("MeteoricIron");
    }
}
