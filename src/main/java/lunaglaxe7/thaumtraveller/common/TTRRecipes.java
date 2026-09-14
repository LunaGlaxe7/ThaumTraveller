package lunaglaxe7.thaumtraveller;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import lunaglaxe7.thaumtraveller.items.CompatItems;
import lunaglaxe7.thaumtraveller.items.ItemManaBeanHelper;
import lunaglaxe7.thaumtraveller.items.ItemNugget;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;

public class TTRRecipes {

    public static CraftingManager cm = CraftingManager.getInstance();

    public static void addRecipes() {
        Aspect[] aspects;
        for (Aspect aspectNeed : aspects = new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1)
                .add(Aspect.EARTH, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1).getAspects()) {
            for (Aspect aspectBean : aspects) {
                if (aspectBean.getTag().equals(aspectNeed.getTag())) continue;
                ThaumcraftApi.addCrucibleRecipe(
                        "BEANTRANS",
                        ItemManaBeanHelper.beanWithAspect(aspectNeed),
                        ItemManaBeanHelper.beanWithAspect(aspectBean),
                        new AspectList().add(Aspect.ENTROPY, 2).add(aspectNeed, 1));
            }
        }
        TTRResearch.recipes.put(
                "BeanTrans",
                new CrucibleRecipe(
                        "BEANTRANS",
                        ItemManaBeanHelper.beanWithAspect(Aspect.WATER),
                        ItemManaBeanHelper.beanWithAspect(Aspect.AIR),
                        new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.WATER, 1)));
    }

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
                        "TRANSIRON_METEO",
                        new ItemStack(ItemNugget.meteoricIron, 3),
                        "nuggetMeteoricIron",
                        new AspectList().add(Aspect.METAL, 2).add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2)));
    }

    public static void addRecipesAE() {
        TTRResearch.recipes.put(
                "certusCharge",
                ThaumcraftApi.addCrucibleRecipe(
                        "CHARGE_CERTUS",
                        CompatItems.certusCharged,
                        CompatItems.certus,
                        new AspectList().add(Aspect.ENERGY, 3)));
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
