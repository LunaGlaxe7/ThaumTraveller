package lunaglaxe7.thaumtraveller.client.block;

import cpw.mods.fml.common.registry.GameRegistry;
import lunaglaxe7.thaumtraveller.items.ItemForge;

public class TTRBlocks {

    public static BlockForge forge;

    public static void registerBlocks() {
        forge = new BlockForge();
        GameRegistry.registerBlock(forge, ItemForge.class, "block_wand_forge");
    }
}
