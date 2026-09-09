package lunaglaxe7.thaumtraveller;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import lunaglaxe7.thaumtraveller.items.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
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
                "TRAVEL",
                "TTRUniverse",
                new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.TRAVEL, 1),
                0,
                0,
                1,
                new ItemStack(Items.leather_boots)).setAutoUnlock().setRound()
                        .setPages(new ResearchPage[] { new ResearchPage("TRAVEL", "tc.research_page.TRAVEL") })
                        .registerResearchItem();
        new ResearchItem(
                "BEANTRANS",
                "TTRUniverse",
                new AspectList().add(Aspect.EXCHANGE, 2).add(Aspect.ENTROPY, 2).add(Aspect.PLANT, 2),
                -2,
                -1,
                1,
                ItemApi.getItem("itemManaBean", 0))
                        .setParents("TRAVEL")
                        .setPages(
                                new ResearchPage("tc.research_page.BEANTRANS"),
                                new ResearchPage((CrucibleRecipe) recipes.get("BeanTrans")))
                        .registerResearchItem();
    }

    public static void addResearchGC() {
        new ResearchItem(
                "MOON",
                "TTRUniverse",
                new AspectList().add(TravelAspects.VOYAGE, 1).add(Aspect.ELDRITCH, 1).add(Aspect.EARTH, 1),
                2,
                1,
                1,
                CompatItems.t1Spaceship[0]).setParents("TRAVEL").setLost().setConcealed()
                        .setItemTriggers(CompatItems.grassMoon, CompatItems.dirtMoon)
                        .setPages(new ResearchPage("tc.research_page.MOON")).registerResearchItem();
        new ResearchItem(
                "METEORIC",
                "TTRUniverse",
                new AspectList().add(Aspect.METAL, 3).add(Aspect.FIRE, 3).add(Aspect.CRYSTAL, 3),
                4,
                0,
                1,
                CompatItems.fallenMeteor).setParents("MOON").setLost().setConcealed()
                        .setItemTriggers(CompatItems.fallenMeteor, CompatItems.rawMeteo, CompatItems.ingotMeteo)
                        .setSecondary()
                        .setPages(
                                new ResearchPage("tc.research_page.METEORIC"),
                                new ResearchPage(CompatItems.rawMeteo),
                                new ResearchPage((IRecipe) recipes.get("nuggetMeteoricIron")))
                        .registerResearchItem();
        new ResearchItem(
                "TRANSIRON_METEO",
                "TTRUniverse",
                new AspectList().add(Aspect.METAL, 3).add(Aspect.EXCHANGE, 3).add(Aspect.FIRE, 3)
                        .add(Aspect.CRYSTAL, 3),
                5,
                -2,
                1,
                new ItemStack(ItemNugget.meteoricIron)).setSecondary().setParents("METEORIC", "TRANSIRON")
                        .setConcealed()
                        .setPages(
                                new ResearchPage("tc.research_page.TRANSIRON_METEO"),
                                new ResearchPage((CrucibleRecipe) recipes.get("nuggetMeteoricIronTransiron")))
                        .registerResearchItem();
    }

    public static void addResearchAE() {
        new ResearchItem(
                "CHARGE_CERTUS",
                "TTRUniverse",
                new AspectList().add(Aspect.ENERGY, 3).add(Aspect.CRYSTAL, 1),
                -2,
                2,
                1,
                CompatItems.certusCharged)
                        .setParents("TRAVEL", "CRUCIBLE")
                        .setPages(
                                new ResearchPage("tc.research_page.CHARGE_CERTUS"),
                                new ResearchPage((CrucibleRecipe) recipes.get("certusCharge")))
                        .registerResearchItem();
    }
}
