package lunaglaxe7.thaumtraveller.libs.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lunaglaxe7.thaumtraveller.LogHandler;
import lunaglaxe7.thaumtraveller.api.AspectList;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;

public class EventHandlerBodyEssence {

    private float slowProbability = 0.1f;

    private float randFloat(EntityPlayer player) {
        return player.getEntityWorld().rand.nextFloat();
    }

    private void addAerEssence(String player) {
        addEssence(player, Aspect.AIR);
    }

    private void addEarthEssence(String player) {
        addEssence(player, Aspect.EARTH);
    }

    private void addAquaEssence(String player) {
        addEssence(player, Aspect.WATER);
    }

    private void addOrdoEssence(String player) {
        addEssence(player, Aspect.ORDER);
    }

    private void addIgnEssence(String player) {
        addEssence(player, Aspect.FIRE);
    }

    private void addPerEssence(String player) {
        addEssence(player, Aspect.ENTROPY);
    }

    @SubscribeEvent
    public void killEnemyEssence(LivingDeathEvent event) {
        if ((event.entity instanceof IMob) && event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            addOrdoEssence(player.getUniqueID().toString());
        }
    }

    @SubscribeEvent
    public void orderEssenceEnough(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            String id = player.getUniqueID().toString();
            AspectList list = getEssencePrimal(id);
            double amount = list.getAmount(Aspect.ORDER);
            if (amount > 500) {
                IInventory inv = player.inventory;
                int times = (int) ((amount - 500) / 400);
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    if (inv.getStackInSlot(i) != null && (inv.getStackInSlot(i).getItem() instanceof ItemWandCasting)) {
                        ItemStack wand = inv.getStackInSlot(i);
                        for (Aspect a : Aspect.getPrimalAspects())
                            ((ItemWandCasting) wand.getItem()).addRealVis(wand, a, 1 + times, true);
                    }
                }
            }
        }
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

            readEssenceNBT(data, id);
            LogHandler.log(Level.INFO, "Now body essence loaded.");
        } catch (Exception e) {
            LogHandler.log(Level.WARN, "Load body essence failed");
        }

    }

    @SubscribeEvent
    public void saveEssence(PlayerEvent.SaveToFile event) {
        File file = event.getPlayerFile("ttr");
        String id = event.playerUUID;
        NBTTagCompound data = writeEssenceNBT(id);

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

    public void readEssenceNBT(NBTTagCompound nbt, String id) {
        ThaumTraveller.proxy.getBodyEssence().readNBTEssence(nbt, id);
    }

    public NBTTagCompound writeEssenceNBT(String id) {
        return ThaumTraveller.proxy.getBodyEssence().writeNBTEssence(id);
    }

    public void addEssence(String player, Aspect a) {
        ThaumTraveller.proxy.getBodyEssence().addAspect(player, a);
    }

    private AspectList getEssencePrimal(String id) {
        return ThaumTraveller.proxy.getBodyEssence().getAspectsPrimal(id);
    }

    public AspectList getEssence(String id) {
        return ThaumTraveller.proxy.getBodyEssence().getAspects(id);
    }
}
