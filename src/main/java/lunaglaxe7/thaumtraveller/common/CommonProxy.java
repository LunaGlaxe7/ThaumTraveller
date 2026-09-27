package lunaglaxe7.thaumtraveller.common;

import lunaglaxe7.thaumtraveller.api.AspectList;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lunaglaxe7.thaumtraveller.api.BodyEssence;
import lunaglaxe7.thaumtraveller.api.util.AspectHelper;
import lunaglaxe7.thaumtraveller.libs.WandPartCache;
import thaumcraft.api.aspects.Aspect;

public class CommonProxy {

    BodyEssence bodyEssence = new BodyEssence();

    public WandPartCache wandPartCache = new WandPartCache();

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {
        AspectHelper.init();
        wandPartCache.registerAllNormalSpecialCaps();
    }

    public BodyEssence getBodyEssence() {
        return bodyEssence;
    }
}
