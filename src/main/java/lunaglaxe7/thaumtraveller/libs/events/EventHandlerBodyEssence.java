package lunaglaxe7.thaumtraveller.libs.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lunaglaxe7.thaumtraveller.LogHandler;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;

public class EventHandlerBodyEssence {

    private float slowProbability = 0.1f;

    private float randFloat(EntityPlayer player) {
        return player.getEntityWorld().rand.nextFloat();
    }

    private void addAerEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.AIR);
    }

    private void addEarthEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.EARTH);
    }

    private void addAquaEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.WATER);
    }

    private void addOrdoEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.ORDER);
    }

    private void addIgnEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.FIRE);
    }

    private void addPerEssence(String player) {
        ThaumTraveller.proxy.addEssence(player, Aspect.ENTROPY);
    }

    @SubscribeEvent
    public void damageEssence(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayer) {
            String id = event.entity.getUniqueID().toString();
            addEarthEssence(id);
            if (event.source.isExplosion()) addEarthEssence(id);
            if (event.source.isFireDamage() && (randFloat((EntityPlayer) event.entity) < slowProbability))
                addIgnEssence(id);
        }
    }

    @SubscribeEvent
    public void healEssence(LivingHealEvent event) {
        if (event.entity instanceof EntityPlayer) {
            addAquaEssence(event.entity.getUniqueID().toString());
        }
    }

    @SubscribeEvent
    public void loadEssence(PlayerEvent.LoadFromFile event) {
        ThaumTraveller.proxy.getBodyEssence().clear();
        File file = event.getPlayerFile("ttr");
        String id = event.playerUUID;
        NBTTagCompound data = new NBTTagCompound();
        if (file != null && file.exists()) try {
            FileInputStream in = new FileInputStream(file);
            data = CompressedStreamTools.readCompressed(in);
            in.close();

            ThaumTraveller.proxy.readEssenceNBT(data, id);
            LogHandler.log(Level.INFO, "Now body essence loaded.");
        } catch (Exception e) {
            LogHandler.log(Level.WARN, "Load body essence failed");
        }

    }

    @SubscribeEvent
    public void saveEssence(PlayerEvent.SaveToFile event) {
        File file = event.getPlayerFile("ttr");
        String id = event.playerUUID;
        NBTTagCompound data = ThaumTraveller.proxy.writeEssenceNBT(id);

        if (file != null) try {
            FileOutputStream out = new FileOutputStream(file);
            CompressedStreamTools.writeCompressed(data, out);
            out.close();
            LogHandler.log(Level.INFO, "body essence saved");
        } catch (Exception e) {
            LogHandler.log(Level.WARN, "failed to save body essence");
            if (file.exists()) try {
                file.delete();
            } catch (Exception e1) {}
        }
        LogHandler.log(Level.INFO, "your body essence now :\n" + data.toString());
    }
}
