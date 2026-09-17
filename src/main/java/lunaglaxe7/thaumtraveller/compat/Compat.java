package lunaglaxe7.thaumtraveller.compat;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Optional;
import lunaglaxe7.thaumtraveller.Config;
import lunaglaxe7.thaumtraveller.LogHandler;
import lunaglaxe7.thaumtraveller.common.TTRContents;
import lunaglaxe7.thaumtraveller.common.TravelAspects;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

// resolve compatation with other mods
// mainly add aspects
public class Compat {

    // public static boolean gc = false;
    // public static boolean gaia = false;
    // public static boolean fm = false;
    // public static boolean ae2 = false;
    // public static boolean tb = false;
    public static AspectList list;

    // public static void initiate() {
    // if (!Config.crossMod) {
    // return;
    // }
    // gc = Config.gc && Loader.isModLoaded("GalacticraftCore");
    // gaia = Config.gaia && Loader.isModLoaded("GrimoireOfGaia");
    // fm = Loader.isModLoaded("ForbiddenMagic");
    // ae2 = Loader.isModLoaded("appliedenergistics2");
    // tb = Loader.isModLoaded("thaumicbases");
    // }

    // add aspects for other mods
    public static void compatify() {
        if (Config.crossMod) {
            compatifyGC();
            // compatifyGaia();
        }
    }

    // regist aspects for items and mobs from Galacticraft, not all of them
    @Optional.Method(modid = TTRContents.GCID)
    public static void compatifyGC() {
        try {
            list = new AspectList().add(TravelAspects.VOYAGE, 3);
            ThaumcraftApi.registerObjectTag(CompatItems.t1Spaceship[0], list);
            list = new AspectList().add(TravelAspects.VOYAGE, 3).add(Aspect.VOID, 3);
            ThaumcraftApi.registerObjectTag(CompatItems.t1Spaceship[1], list);
            list = new AspectList().add(TravelAspects.VOYAGE, 3).add(Aspect.VOID, 6);
            ThaumcraftApi.registerObjectTag(CompatItems.t1Spaceship[2], list);
            list = new AspectList().add(TravelAspects.VOYAGE, 3).add(Aspect.VOID, 9);
            ThaumcraftApi.registerObjectTag(CompatItems.t1Spaceship[3], list);
            list = new AspectList().add(Aspect.EARTH, 2).add(Aspect.DEATH, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.grassMoon, list);
            list = new AspectList().add(Aspect.EARTH, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.dirtMoon, list);
            list = new AspectList().add(Aspect.EARTH, 3).add(Aspect.FIRE, 3);
            ThaumcraftApi.registerObjectTag(CompatItems.brickMoon, list);
            list = new AspectList().add(Aspect.EARTH, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.stoneMoon, list);
            list = new AspectList().add(Aspect.FIRE, 4).add(Aspect.ENERGY, 2).add(Aspect.EARTH, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.fallenMeteor, list);
            list = new AspectList().add(Aspect.FIRE, 3).add(Aspect.METAL, 2).add(Aspect.EARTH, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.rawMeteo, list);
            list = new AspectList().add(Aspect.FIRE, 4).add(Aspect.METAL, 3).add(Aspect.DARKNESS, 3);
            ThaumcraftApi.registerObjectTag(CompatItems.ingotMeteo, list);
            list = new AspectList().add(Aspect.ENERGY, 1).add(Aspect.METAL, 2).add(Aspect.EARTH, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.oreAluminum, list);
            list = new AspectList().add(Aspect.METAL, 3).add(Aspect.ENERGY, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.ingotAluminum, list);
            list = new AspectList().add(Aspect.METAL, 1).add(Aspect.ARMOR, 1).add(Aspect.EXCHANGE, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateCopper, list);
            list = new AspectList().add(Aspect.METAL, 1).add(Aspect.ARMOR, 1).add(Aspect.CRYSTAL, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateTin, list);
            list = new AspectList().add(Aspect.METAL, 2).add(Aspect.ARMOR, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateIron, list);
            list = new AspectList().add(Aspect.METAL, 2).add(Aspect.ARMOR, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateSteel, list);
            list = new AspectList().add(Aspect.METAL, 1).add(Aspect.ARMOR, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.plateBronze, list);
            list = new AspectList().add(Aspect.METAL, 1).add(Aspect.ARMOR, 1).add(Aspect.ENERGY, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateAluminum, list);
            list = new AspectList().add(Aspect.METAL, 1).add(Aspect.ARMOR, 1).add(Aspect.FIRE, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.plateMeteo, list);
            list = new AspectList().add(Aspect.LIGHT, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.torch, list);
            list = Compat.getObjectAspects(CompatItems.wireAluminum[0]).add(Aspect.ENERGY, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.wireAluminum[0], list);
            list = Compat.getObjectAspects(CompatItems.wireAluminum[1]).add(Aspect.ENERGY, 4);
            ThaumcraftApi.registerObjectTag(CompatItems.wireAluminum[1], list);
            list = new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ENERGY, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.rawSilicon, list);
            list = new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.SENSES, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.waferSolar, list);
            list = new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.MECHANISM, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.waferBasic, list);
            list = new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MECHANISM, 2);
            ThaumcraftApi.registerObjectTag(CompatItems.waferAdvanced, list);
            list = new AspectList().add(Aspect.MIND, 8).add(Aspect.MECHANISM, 8);
            ThaumcraftApi.registerObjectTag(CompatItems.schematicCar, list);
            list = new AspectList().add(Aspect.MIND, 8).add(Aspect.MECHANISM, 8);
            ThaumcraftApi.registerObjectTag(CompatItems.schematicSpaceship, list);
            list = new AspectList().add(Aspect.SLIME, 2).add(Aspect.HUNGER, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.curdCheese, list);
            list = new AspectList().add(Aspect.SLIME, 2).add(Aspect.EARTH, 1);
            ThaumcraftApi.registerObjectTag(CompatItems.oreCheese, list);
            AspectList creeperList = new AspectList();
            AspectList zombieList = new AspectList();
            AspectList skeletonList = new AspectList();
            AspectList spiderList = new AspectList();
            AspectList villagerList = new AspectList();
            for (ThaumcraftApi.EntityTags et : ThaumcraftApi.scanEntities) {
                if ("Creeper".equals(et.entityName) && et.nbts.length == 0) {
                    creeperList = et.aspects;
                }
                if ("Spider".equals(et.entityName)) {
                    spiderList = et.aspects;
                }
                if ("Zombie".equals(et.entityName)) {
                    zombieList = et.aspects;
                }
                if ("Skeleton".equals(et.entityName) && et.nbts.length == 0) {
                    skeletonList = et.aspects;
                }
                if (!"Villager".equals(et.entityName)) continue;
                villagerList = et.aspects;
            }
            ThaumcraftApi.registerEntityTag("GalacticraftCore.EvolvedCreeper", creeperList);
            ThaumcraftApi.registerEntityTag("GalacticraftCore.EvolvedSpider", spiderList);
            ThaumcraftApi.registerEntityTag("GalacticraftCore.EvolvedZombie", zombieList);
            ThaumcraftApi.registerEntityTag("GalacticraftCore.EvolvedSkeleton", skeletonList);
            ThaumcraftApi.registerEntityTag("GalacticraftCore.AlienVillager", villagerList);
            list = new AspectList().add(Aspect.DEATH, 10).add(Aspect.UNDEAD, 10);
            ThaumcraftApi.registerEntityTag("GalacticraftCore.EvolvedSkeletonBoss", list);
        } catch (Exception e) {
            LogHandler.log(Level.INFO, e, "ThaumTraveller tried to travel to the Moon but failed.");
        }
    }

    // regist aspects for mobs from GrimoireOfGaia, maybe not all
    // @Optional.Method(modid = TTRContents.GAIAID)
    // public static void compatifyGaia() {
    // list = new AspectList().add(Aspect.BEAST, 3).add(Aspect.DARKNESS, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Anubis", list);
    // list = new AspectList().add(Aspect.SOUL, 2).add(Aspect.ENTROPY, 2).add(Aspect.FIRE, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Banshee", list);
    // list = new AspectList().add(Aspect.BEAST, 2).add(Aspect.ENTROPY, 2).add(Aspect.FIRE, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Baphomet", list);
    // list = new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 2).add(Aspect.ARMOR, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Bone Knight", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Centaur", list);
    // list = new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Cobble Golem", list);
    // list = new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.EARTH, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Cobblestone Golem", list);
    // list = new AspectList().add(Aspect.BEAST, 3).add(Aspect.FLIGHT, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Cockatrice", list);
    // list = new AspectList().add(Aspect.FIRE, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Creep", list);
    // list = new AspectList().add(Aspect.ENTROPY, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Cyclops", list);
    // list = new AspectList().add(Aspect.PLANT, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Cyan Flower", list);
    // list = new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 2).add(Aspect.ENTROPY, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Dhampir", list);
    // list = new AspectList().add(Aspect.PLANT, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Dryad", list);
    // list = new AspectList().add(Aspect.SOUL, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Dullahan", list);
    // list = new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DARKNESS, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Ender Dragon Girl", list);
    // list = new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.DARKNESS, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Ender Eye", list);
    // list = new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 2).add(Aspect.FIRE, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Flesh Lich", list);
    // list = new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 2);
    // if (fm) {
    // list.add(DarkAspects.GLUTTONY, 2);
    // }
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.FutakuchiOnna", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.FLIGHT, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Gryphon", list);
    // list = new AspectList().add(Aspect.AIR, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Harpy", list);
    // list = new AspectList().add(Aspect.MAN, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Hunter", list);
    // list = new AspectList().add(Aspect.BEAST, 2).add(Aspect.MAN, 2).add(TravelAspects.TEMPTATION, 2);
    // if (fm) {
    // list.add(DarkAspects.LUST, 2);
    // }
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Jorogumo", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.COLD, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Kobold", list);
    // list = new AspectList().add(Aspect.ARMOR, 2).add(Aspect.WATER, 4);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Mermaid", list);
    // list = new AspectList().add(Aspect.TREE, 1).add(Aspect.VOID, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Mimic", list);
    // list = new AspectList().add(Aspect.BEAST, 4).add(Aspect.MAN, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Minotaur", list);
    // list = new AspectList().add(Aspect.BEAST, 3).add(Aspect.MAN, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Minotaurus", list);
    // list = new AspectList().add(Aspect.ARMOR, 2).add(Aspect.WATER, 2).add(Aspect.EARTH, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Naga", list);
    // list = new AspectList().add(Aspect.BEAST, 3).add(Aspect.FIRE, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.NineTails", list);
    // list = new AspectList().add(Aspect.BEAST, 3).add(Aspect.WATER, 3);
    // if (fm) {
    // list.add(DarkAspects.GLUTTONY, 3);
    // } else {
    // list.add(Aspect.HUNGER, 3);
    // }
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Sahuagin", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Satyr", list);
    // list = new AspectList().add(Aspect.BEAST, 2).add(Aspect.COLD, 4);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Selkie", list);
    // list = new AspectList().add(Aspect.MAN, 3).add(Aspect.SOUL, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Shaman", list);
    // list = new AspectList().add(Aspect.WATER, 6);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Sharko", list);
    // list = new AspectList().add(Aspect.WATER, 2).add(TravelAspects.TEMPTATION, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Siren", list);
    // list = new AspectList().add(Aspect.SLIME, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Sludge Girl", list);
    // list = new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 2).add(Aspect.MIND, 2).add(Aspect.ORDER, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Sphinx", list);
    // list = new AspectList().add(Aspect.PLANT, 3).add(Aspect.EARTH, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Spriggan", list);
    // list = new AspectList().add(Aspect.FIRE, 2).add(TravelAspects.TEMPTATION, 4);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Succubus", list);
    // list = new AspectList().add(Aspect.SLIME, 3).add(Aspect.ENTROPY, 3);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Swamper", list);
    // list = new AspectList().add(Aspect.MAN, 2).add(Aspect.MOTION, 2).add(Aspect.FLIGHT, 2).add(Aspect.ORDER, 2)
    // .add(Aspect.WEAPON, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Valkyrie", list);
    // list = new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 2).add(Aspect.SENSES, 2).add(Aspect.ENTROPY, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Vampire", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Werecat", list);
    // list = new AspectList().add(Aspect.MAN, 2).add(Aspect.AURA, 2).add(Aspect.FIRE, 2).add(Aspect.MAGIC, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Witch", list);
    // list = new AspectList().add(Aspect.UNDEAD, 1).add(Aspect.BEAST, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Wither Cow", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.COLD, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Yeti", list);
    // list = new AspectList().add(Aspect.SOUL, 2).add(Aspect.COLD, 2).add(Aspect.ORDER, 2);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Yuki-Onna", list);
    // list = new AspectList().add(Aspect.FIRE, 1).add(Aspect.MAN, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Creeper Girl", list);
    // list = new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Ender Girl", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.LIFE, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Holstaurus", list);
    // list = new AspectList().add(Aspect.SLIME, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Slime Girl", list);
    // list = new AspectList().add(Aspect.BEAST, 1).add(Aspect.GREED, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Trader", list);
    // list = new AspectList().add(Aspect.PLANT, 1).add(Aspect.EARTH, 1);
    // ThaumcraftApi.registerEntityTag("GrimoireOfGaia.Mandragora", list);
    // }

    // not like nollPointerException
    public static AspectList getObjectAspects(ItemStack is) {
        return ThaumcraftApiHelper.getObjectAspects(is) == null ? new AspectList()
                : ThaumcraftApiHelper.getObjectAspects(is);
    }
}
