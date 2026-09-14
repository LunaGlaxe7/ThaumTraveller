package lunaglaxe7.thaumtraveller.common;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import lunaglaxe7.thaumtraveller.compat.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class TTRResearch {

    public static HashMap recipes = new HashMap();

    public static void addResearch() {
        ResearchCategories.registerCategory(
                "TTRUniverse",
                new ResourceLocation("textures/items/leather_boots.png"),
                new ResourceLocation("thaumtraveller", "textures/misc/travel.png"));
        new ResearchItem(
                "TTR.TRAVEL",
                "TTRUniverse",
                new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.TRAVEL, 1),
                0,
                0,
                1,
                new ItemStack(Items.leather_boots)).setAutoUnlock().setRound()
                        .setPages(new ResearchPage("tc.research_page.TTR.TRAVEL")).registerResearchItem();
        new ResearchItem(
                "TTR.BEANTRANS",
                "TTRUniverse",
                new AspectList().add(Aspect.EXCHANGE, 2).add(Aspect.ENTROPY, 2).add(Aspect.PLANT, 2),
                -2,
                -1,
                1,
                ItemApi.getItem("itemManaBean", 0))
                        .setParents("TTR.TRAVEL")
                        .setPages(
                                new ResearchPage("tc.research_page.TTR.BEANTRANS"),
                                new ResearchPage((CrucibleRecipe) recipes.get("BeanTrans")))
                        .setItemTriggers(ItemApi.getItem("itemManaBean", 0)) // now the research will be unlocked after
                                                                             // scan the manabean
                        .registerResearchItem();
    }

    public static void addResearchGC() {
        new ResearchItem(
                "TTR.MOON",
                "TTRUniverse",
                new AspectList().add(TravelAspects.VOYAGE, 1).add(Aspect.ELDRITCH, 1).add(Aspect.EARTH, 1),
                2,
                1,
                1,
                CompatItems.t1Spaceship[0]).setParents("TTR.TRAVEL").setLost().setConcealed()
                        .setItemTriggers(CompatItems.grassMoon, CompatItems.dirtMoon)
                        .setPages(new ResearchPage("tc.research_page.TTR.MOON")).registerResearchItem();
        new ResearchItem(
                "TTR.METEORIC",
                "TTRUniverse",
                new AspectList().add(Aspect.METAL, 3).add(Aspect.FIRE, 3).add(Aspect.CRYSTAL, 3),
                4,
                0,
                1,
                CompatItems.fallenMeteor).setParents("TTR.MOON").setLost().setConcealed()
                        .setItemTriggers(CompatItems.fallenMeteor, CompatItems.rawMeteo, CompatItems.ingotMeteo)
                        .setSecondary()
                        .setPages(
                                new ResearchPage("tc.research_page.TTR.METEORIC"),
                                new ResearchPage(CompatItems.rawMeteo),
                                new ResearchPage((IRecipe) recipes.get("nuggetMeteoricIron")))
                        .registerResearchItem();
        new ResearchItem(
                "TTR.TRANSIRON_METEO",
                "TTRUniverse",
                new AspectList().add(Aspect.METAL, 3).add(Aspect.EXCHANGE, 3).add(Aspect.FIRE, 3)
                        .add(Aspect.CRYSTAL, 3),
                5,
                -2,
                1,
                new ItemStack(ItemNugget.meteoricIron)).setSecondary().setParents("TTR.METEORIC", "TRANSIRON")
                        .setConcealed()
                        .setPages(
                                new ResearchPage("tc.research_page.TTR.TRANSIRON_METEO"),
                                new ResearchPage((CrucibleRecipe) recipes.get("nuggetMeteoricIronTransiron")))
                        .registerResearchItem();
    }

    public static void addResearchAE() {
        new ResearchItem(
                "TTR.CHARGE_CERTUS",
                "TTRUniverse",
                new AspectList().add(Aspect.ENERGY, 3).add(Aspect.CRYSTAL, 1),
                -2,
                2,
                1,
                CompatItems.certusCharged)
                        .setParents("TTR.TRAVEL", "CRUCIBLE")
                        .setPages(
                                new ResearchPage("tc.research_page.TTR.CHARGE_CERTUS"),
                                new ResearchPage((CrucibleRecipe) recipes.get("certusCharge")))
                        .registerResearchItem();
    }

    public static void addResearchTB() {
        ResearchPage[] pages = new ResearchPage[8];
        pages[0] = new ResearchPage("tc.research_page.TTR.VOIDSEED");
        for (int i = 1; i < 8; i++) {
            pages[i] = new ResearchPage((InfusionRecipe) recipes.get("voidSeed" + (i - 1)));
        }
        new ResearchItem(
                "TTR.VOIDSEED",
                "THAUMICBASES",
                new AspectList().add(Aspect.CROP, 1),
                17,
                4,
                1,
                CompatItems.voidSeed).setParents("TB.VoidSeed").setPages(pages).registerResearchItem();
    }
}
