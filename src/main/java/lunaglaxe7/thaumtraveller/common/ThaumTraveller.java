/**
 * This document is created by <lunaglaxe7>. I recreate it from decompile of ThaumTraveller.class because the origin
 * ThaumTraveller.java by lunaglaxe7 at 2021 has lost.
 */
package lunaglaxe7.thaumtraveller.common;

import net.minecraftforge.common.MinecraftForge;

import baubles.api.expanded.BaubleExpandedSlots;
import baubles.common.BaublesExpanded;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lunaglaxe7.thaumtraveller.Config;
import lunaglaxe7.thaumtraveller.api.BodyEssence;
import lunaglaxe7.thaumtraveller.client.block.TTRBlocks;
import lunaglaxe7.thaumtraveller.compat.Compat;
import lunaglaxe7.thaumtraveller.compat.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import lunaglaxe7.thaumtraveller.items.TTRItems;
import lunaglaxe7.thaumtraveller.libs.events.EventHandlerBodyEssence;

@Mod(
        modid = "ThaumTraveller",
        name = "ThaumTraveller",
        dependencies = "required-after:" + BaublesExpanded.MODID
                + ";"
                + "required-after:Thaumcraft;after:GalacticraftCore;"
                + // "after:GrimoireOfGaia;" +
                "after:ForbiddenMagic;"
                + "after:appliedenergistics2;after:thaumicbases")
public class ThaumTraveller {

    @Mod.Instance(value = "ThaumTraveller")
    public static ThaumTraveller instance;
    @SidedProxy(
            clientSide = "lunaglaxe7.thaumtraveller.client.ClientProxy",
            serverSide = "lunaglaxe7.thaumtraveller.common.CommonProxy")
    public static CommonProxy proxy;
    public EventHandlerBodyEssence bodyEssenceHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        Config.configurate(event.getSuggestedConfigurationFile());
        BaubleExpandedSlots.tryAssignSlotOfType(BaubleExpandedSlots.beltType);
        // Compat.initiate();
        TTRBlocks.registerBlocks();
        GameRegistry.registerTileEntity(TileForge.class, "ttr_forge_tile");
        ItemNugget.addNuggets();
        TTRItems.itemRegister(event);
        TravelAspects.initAspects();

        bodyEssenceHandler = new EventHandlerBodyEssence();

        MinecraftForge.EVENT_BUS.register(this.bodyEssenceHandler);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CompatItems.importItems();
        TravelAspects.addAspects();
        Compat.compatify();
        TTRRecipes.addRecipes();
        TTRResearch.addResearch();
        // if (Compat.gc) {
        // CompatItems.importGCItems();
        // Compat.compatifyGC();
        // TTRRecipes.addRecipesGC();
        // TTRResearch.addResearchGC();
        // }
        // if (Compat.gaia) {
        // Compat.compatifyGaia();
        // }
        // if (Compat.ae2) {
        // CompatItems.importAEItems();
        // TTRRecipes.addRecipesAE();
        // TTRResearch.addResearchAE();
        // }
        // if (Compat.tb) {
        // CompatItems.importTBItems();
        // TTRRecipes.addRecipesTB();
        // TTRResearch.addResearchTB();
        // }
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void registerCommand(FMLServerStartingEvent event) {
        event.registerServerCommand(new BodyEssence.BodyEssenceCommand());
    }
}
