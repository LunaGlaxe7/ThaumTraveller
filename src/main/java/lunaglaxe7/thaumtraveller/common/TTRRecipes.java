package lunaglaxe7.thaumtraveller.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import lunaglaxe7.thaumtraveller.Config;
import lunaglaxe7.thaumtraveller.client.block.TTRBlocks;
import lunaglaxe7.thaumtraveller.compat.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import lunaglaxe7.thaumtraveller.util.ManaBeanHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;

public class TTRRecipes {

    public static CraftingManager cm = CraftingManager.getInstance();

    public static void addRecipes() {
        addRecipesBasic();

        if (Config.crossMod) {
            addRecipesGC();
            addRecipesAE();
            addRecipesTB();
        }
    }

    public static void addRecipesBasic() {
        Aspect[] aspects;
        for (Aspect aspectNeed : aspects = new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1)
                .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1).getAspects()) {
            for (Aspect aspectBean : aspects) {
                if (aspectBean.getTag().equals(aspectNeed.getTag())) continue;
                ThaumcraftApi.addCrucibleRecipe(
                        "TTR.BEANTRANS",
                        ManaBeanHelper.beanWithAspect(aspectNeed),
                        ManaBeanHelper.beanWithAspect(aspectBean),
                        new AspectList().add(Aspect.ENTROPY, 2).add(aspectNeed, 1));
            }
        }
        TTRResearch.recipes.put(
                "BeanTrans",
                new CrucibleRecipe(
                        "TTR.BEANTRANS",
                        ManaBeanHelper.beanWithAspect(Aspect.WATER),
                        ManaBeanHelper.beanWithAspect(Aspect.AIR),
                        new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.WATER, 1)));
        TTRResearch.recipes.put(
                "WandForge",
                ThaumcraftApi.addArcaneCraftingRecipe(
                        "TTR.WANDFORGE",
                        new ItemStack(TTRBlocks.forge),
                        new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10)
                                .add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10),
                        " # ",
                        "$%$",
                        "&&&",
                        '#',
                        CompatItems.balanceShard,
                        '$',
                        Items.gold_ingot,
                        '%',
                        CompatItems.greatBlank,
                        '&',
                        CompatItems.thaumIngot));
    }

    @Optional.Method(modid = TTRContents.GCID)
    public static void addRecipesGC() {
        cm.addShapelessRecipe(new ItemStack(ItemNugget.meteoricIron, 9), CompatItems.ingotMeteo);
        cm.addRecipe(CompatItems.ingotMeteo, "###", "###", "###", '#', new ItemStack(ItemNugget.meteoricIron));
        TTRResearch.recipes.put(
                "nuggetMeteoricIron",
                new ShapedRecipes(
                        1,
                        1,
                        new ItemStack[] { CompatItems.ingotMeteo },
                        new ItemStack(ItemNugget.meteoricIron, 9)));
        TTRResearch.recipes.put(
                "nuggetMeteoricIronTransiron",
                ThaumcraftApi.addCrucibleRecipe(
                        "TTR.TRANSIRON_METEO",
                        new ItemStack(ItemNugget.meteoricIron, 3),
                        "nuggetMeteoricIron",
                        new AspectList().add(Aspect.METAL, 2).add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2)));
    }

    @Optional.Method(modid = TTRContents.AEID)
    public static void addRecipesAE() {
        TTRResearch.recipes.put(
                "certusCharge",
                ThaumcraftApi.addCrucibleRecipe(
                        "TTR.CHARGE_CERTUS",
                        CompatItems.certusCharged,
                        CompatItems.certus,
                        new AspectList().add(Aspect.ENERGY, 3)));
    }

    @Optional.Method(modid = TTRContents.TBID)
    public static void addRecipesTB() {
        ItemStack[] wheat = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            wheat[i] = new ItemStack(Items.wheat_seeds, 1, 0);
        }
        ItemStack[] carrot = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            carrot[i] = new ItemStack(Items.carrot, 1, 0);
        }
        ItemStack[] melon = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            melon[i] = new ItemStack(Items.melon_seeds, 1, 0);
        }
        ItemStack[] potato1 = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            potato1[i] = new ItemStack(Items.potato, 1, 0);
        }
        ItemStack[] potato2 = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            potato2[i] = new ItemStack(Items.poisonous_potato, 1, 0);
        }
        ItemStack[] wart = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            wart[i] = new ItemStack(Items.nether_wart, 1, 0);
        }
        ItemStack[] pumpkin = new ItemStack[6];
        for (int i = 0; i < 6; i++) {
            pumpkin[i] = new ItemStack(Items.pumpkin_seeds, 1, 0);
        }
        ItemStack[][] seeds = new ItemStack[][] { wheat, carrot, melon, potato1, potato2, wart, pumpkin };

        for (int i = 0; i < 7; i++) {
            TTRResearch.recipes.put(
                    "voidSeed" + i,
                    ThaumcraftApi.addInfusionCraftingRecipe(
                            "TTR.VOIDSEED",
                            CompatItems.voidSeed,
                            3,
                            new AspectList().add(Aspect.ELDRITCH, 16).add(Aspect.DARKNESS, 16).add(Aspect.CROP, 32),
                            new ItemStack(GameRegistry.findItem(TTRContents.TCID, "ItemResource"), 1, 17),
                            seeds[i]));
        }

    }

    private static void addShapelessWithResearch(String tag, ItemStack out, ItemStack[] in) {
        cm.addShapelessRecipe(out, (Object[]) in);
        int j = (in.length - 1) / 3 + 1;
        int i = j > 1 ? 3 : in.length;
        ItemStack[] recipe = new ItemStack[i * j];
        System.arraycopy(in, 0, recipe, 0, in.length);
        for (int k = in.length; k < i * j; ++k) {
            recipe[k] = null;
        }
        TTRResearch.recipes.put(tag, new ShapedRecipes(i, j, recipe, out));
    }
}
