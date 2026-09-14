/**
 * This document is created by <lunaglaxe7>. I recreate it from decompile of ThaumTraveller.class because the origin
 * ThaumTraveller.java by lunaglaxe7 at 2021 has lost.
 */
package lunaglaxe7.thaumtraveller.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lunaglaxe7.thaumtraveller.Config;
import lunaglaxe7.thaumtraveller.compat.Compat;
import lunaglaxe7.thaumtraveller.compat.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import lunaglaxe7.thaumtraveller.items.TTRItems;

@Mod(
        modid = "ThaumTraveller",
        name = "ThaumTraveller",
        dependencies = "required-after:Thaumcraft;after:GalacticraftCore;"
                + "after:GrimoireOfGaia;after:ForbiddenMagic;"
                + "after:appliedenergistics2;after:thaumicbases")
public class ThaumTraveller {

    @Mod.Instance(value = "ThaumTraveller")
    public static ThaumTraveller instance;
    @SidedProxy(
            clientSide = "lunaglaxe7.thaumtraveller.client.ClientProxy",
            serverSide = "lunaglaxe7.thaumtraveller.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        Config.configurate(event.getSuggestedConfigurationFile());
        Compat.initiate();
        if (Compat.gc) {
            ItemNugget.addNuggetsGC();
        }
        TTRItems.itemRegister(event);
        TravelAspects.initAspects();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        TravelAspects.addAspects();
        TTRRecipes.addRecipes();
        TTRResearch.addResearch();
        if (Compat.gc) {
            CompatItems.importGCItems();
            Compat.compatifyGC();
            TTRRecipes.addRecipesGC();
            TTRResearch.addResearchGC();
        }
        if (Compat.gaia) {
            Compat.compatifyGaia();
        }
        if (Compat.ae2) {
            CompatItems.importAEItems();
            TTRRecipes.addRecipesAE();
            TTRResearch.addResearchAE();
        }
        if (Compat.tb) {
            CompatItems.importTBItems();
            TTRRecipes.addRecipesTB();
            TTRResearch.addResearchTB();
        }
        proxy.postInit(event);
    }
}
