package lunaglaxe7.thaumtraveller;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean crossMod = true;
    public static boolean gc = true;
    public static boolean gaia = true;

    public static void configurate(File targ) {
        Configuration conf = new Configuration(targ);
        conf.load();
        crossMod = conf.get("compatibility", "Cross-Mod Interaction", crossMod, "Disable to keep mods segregated.")
                .getBoolean(true);
        gc = conf.get("compatibility", "Galacticraft Interaction", gc).getBoolean(true);
        gaia = conf.get(
                "compatibility",
                "Grimoire of Gaia Interaction",
                gaia,
                "Tried to add aspects for mobs and items from Gaia.\nThe settings is from the version 1.12.2 of Grimoire of Gaia.")
                .getBoolean(true);
        conf.save();
    }
}
