package lunaglaxe7.thaumtraveller.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lunaglaxe7.thaumtraveller.client.model.render.RenderForge;
import lunaglaxe7.thaumtraveller.common.CommonProxy;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileForge.class, new RenderForge());
    }

    @SideOnly(Side.CLIENT)
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
