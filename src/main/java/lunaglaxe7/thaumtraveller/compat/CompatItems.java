package lunaglaxe7.thaumtraveller.compat;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import lunaglaxe7.thaumtraveller.Config;
import lunaglaxe7.thaumtraveller.common.TTRContents;

// Items from other mods, to simplify codes
public class CompatItems {

    // items from galacticraft
    public static ItemStack fallenMeteor;
    public static ItemStack ingotMeteo;
    public static ItemStack rawMeteo;
    public static ItemStack grassMoon;
    public static ItemStack dirtMoon;
    public static ItemStack stoneMoon;
    public static ItemStack brickMoon;
    public static ItemStack[] t1Spaceship;
    public static ItemStack oreAluminum;
    public static ItemStack ingotAluminum;
    public static ItemStack plateCopper;
    public static ItemStack plateTin;
    public static ItemStack plateAluminum;
    public static ItemStack plateMeteo;
    public static ItemStack plateSteel;
    public static ItemStack plateBronze;
    public static ItemStack plateIron;
    public static ItemStack torch;
    public static ItemStack[] wireAluminum;
    public static ItemStack waferSolar;
    public static ItemStack waferBasic;
    public static ItemStack waferAdvanced;
    public static ItemStack rawSilicon;
    public static ItemStack schematicSpaceship;
    public static ItemStack schematicCar;
    public static ItemStack curdCheese;
    public static ItemStack oreCheese;

    // items from AppliedEnergistics2
    public static ItemStack certus;
    public static ItemStack certusCharged;

    // items from thaumic bases
    public static ItemStack voidSeed;

    public static void importItems() {
        if (Config.crossMod) {
            importGCItems();
            importAEItems();
            importTBItems();
        }
    }

    @Optional.Method(modid = TTRContents.GCID)
    public static void importGCItems() {
        fallenMeteor = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.fallenMeteor"));
        ingotMeteo = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.meteoricIronIngot"));
        rawMeteo = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.meteoricIronRaw"));
        grassMoon = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.moonBlock"), 1, 5);
        dirtMoon = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.moonBlock"), 1, 3);
        stoneMoon = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.moonBlock"), 1, 4);
        brickMoon = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.moonBlock"), 1, 14);
        t1Spaceship = new ItemStack[] {
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.spaceship"), 1, 0),
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.spaceship"), 1, 1),
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.spaceship"), 1, 2),
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.spaceship"), 1, 3) };
        oreAluminum = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.gcBlockCore"), 1, 7);
        ingotAluminum = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 5);
        plateCopper = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 6);
        plateTin = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 7);
        plateIron = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 11);
        plateBronze = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 10);
        plateAluminum = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 8);
        plateSteel = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 9);
        plateMeteo = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.meteoricIronIngot"), 1, 1);
        torch = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.glowstoneTorch"));
        wireAluminum = new ItemStack[] {
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.aluminumWire"), 1, 0),
                new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.aluminumWire"), 1, 1) };
        waferSolar = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 12);
        waferBasic = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 13);
        waferAdvanced = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 14);
        rawSilicon = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.basicItem"), 1, 2);
        schematicCar = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.schematic"), 1, 0);
        schematicSpaceship = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.schematic"), 1, 1);
        curdCheese = new ItemStack(GameRegistry.findItem("GalacticraftCore", "item.cheeseCurd"));
        oreCheese = new ItemStack(GameRegistry.findItem("GalacticraftCore", "tile.moonBlock"), 1, 2);
    }

    @Optional.Method(modid = TTRContents.AEID)
    public static void importAEItems() {
        certus = new ItemStack(GameRegistry.findItem("appliedenergistics2", "item.ItemMultiMaterial"), 1, 0);
        certusCharged = new ItemStack(GameRegistry.findItem("appliedenergistics2", "item.ItemMultiMaterial"), 1, 1);
    }

    @Optional.Method(modid = TTRContents.TBID)
    public static void importTBItems() {
        voidSeed = new ItemStack(GameRegistry.findItem("thaumicbases", "voidSeed"), 1, 0);
    }
}
