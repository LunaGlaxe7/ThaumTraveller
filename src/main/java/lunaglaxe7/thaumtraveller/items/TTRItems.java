package lunaglaxe7.thaumtraveller.items;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

// regist items of thaumtraveller
public class TTRItems {

    public static boolean isOreDicRegisted;

    public static void itemRegister(FMLPreInitializationEvent event) {
        if (ItemNugget.nuggets == null) {
            return;
        }
        for (ItemNugget nugget : ItemNugget.nuggets) {
            GameRegistry.registerItem((Item) nugget, (String) nugget.name, (String) "ThaumTraveller");
            isOreDicRegisted = OreDictionary.doesOreNameExist((String) nugget.name);
            if (isOreDicRegisted) continue;
            OreDictionary.registerOre((String) nugget.name, (Item) nugget);
        }
    }
}
