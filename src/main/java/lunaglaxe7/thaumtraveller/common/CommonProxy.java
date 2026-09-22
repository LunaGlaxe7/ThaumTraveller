package lunaglaxe7.thaumtraveller.common;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lunaglaxe7.thaumtraveller.api.BodyEssence;
import lunaglaxe7.thaumtraveller.api.util.AspectHelper;
import lunaglaxe7.thaumtraveller.libs.WandPartCache;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class CommonProxy {

    BodyEssence bodyEssence = new BodyEssence();

    public WandPartCache wandPartCache = new WandPartCache();

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {
        AspectHelper.init();
        wandPartCache.registerAllNormalSpecialCaps();
    }

    public AspectList getEssence(String player) {
        return bodyEssence.getAspects(player);
    }

    public AspectList getEssencePrimal(String player) {
        return bodyEssence.getAspectsPrimal(player);
    }

    public void addEssence(String player, Aspect aspect) {
        bodyEssence.addAspect(player, aspect);
    }

    public NBTTagCompound writeEssenceNBT(String player) {
        return bodyEssence.writeNBTEssence(player);
    }

    public void readEssenceNBT(NBTTagCompound nbt, String player) {
        bodyEssence.readNBTEssence(nbt, player);
    }

    public BodyEssence getBodyEssence() {
        return bodyEssence;
    }
}
