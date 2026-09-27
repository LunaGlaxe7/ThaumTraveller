package lunaglaxe7.thaumtraveller;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean crossMod = true;
    public static boolean gc = true;
    public static boolean alterTA = true;

    public static void configurate(File targ) {
        Configuration conf = new Configuration(targ);
        conf.load();
        crossMod = conf.get("compatibility", "Cross-Mod Interaction", crossMod, "Disable to keep mods segregated.")
                .getBoolean(true);
        gc = conf.get("compatibility", "Galacticraft Interaction", gc).getBoolean(true);
        alterTA = conf.get(
                "compatibility",
                "AlterTAResearchesPosition",
                alterTA,
                "set false to make researches of Thaumic Alchemy their origin positions").getBoolean(true);
        conf.save();
    }
}
