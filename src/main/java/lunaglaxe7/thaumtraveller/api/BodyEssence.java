package lunaglaxe7.thaumtraveller.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;

import lunaglaxe7.thaumtraveller.api.util.AspectHelper;
import lunaglaxe7.thaumtraveller.common.ThaumTraveller;
import thaumcraft.api.aspects.Aspect;

public class BodyEssence {

    public Map<String, AspectList> bodyEssencePrimal = new ConcurrentHashMap<>();
    public Map<String, AspectList> bodyEssence = new ConcurrentHashMap<>();

    // 身之源质只能自然获得元始要素
    // BodyEssence can only get primal aspects by nature
    // Just converse a player UUID string here
    public void addAspect(String player, Aspect aspect, double count) {
        bodyEssencePrimal.computeIfAbsent(player, k -> new AspectList()).add(aspect, count);

        bodyEssence.computeIfAbsent(player, k -> new AspectList()).add(aspect, count);

        checkMix(player);
    }

    public void addAspect(String player, Aspect aspect) {
        this.addAspect(player, aspect, 1);
    }

    public boolean canMix(Aspect a, Aspect b) {
        return mix(a, b) != null;
    }

    public Aspect mix(Aspect a, Aspect b) {
        Set<Aspect> set = new HashSet<>();
        set.add(a);
        set.add(b);
        return AspectHelper.mixCache.get(set);
    }

    public void checkMix(String player) {
        if (AspectHelper.mixCache.isEmpty()) AspectHelper.init();

        AspectList al = bodyEssence.computeIfAbsent(player, k -> new AspectList());
        if (al.size() > 0) {
            boolean changed;
            do {
                changed = false;
                Aspect[] as = al.getAspects();
                for (int i = 0; i < as.length; i++) {
                    for (int j = i + 1; j < as.length; j++) {
                        Aspect a = as[i];
                        Aspect b = as[j];
                        Aspect res = mix(a, b);
                        if (res != null) {
                            int c = (int) Math.min(al.getAmount(a), al.getAmount(b));
                            // mixed only when count >= 1
                            if (c > 0) {
                                al.remove(a, c);
                                al.remove(b, c);
                                al.add(res, c);
                                changed = true;
                            }
                        }
                    }
                }
            } while (changed);
        }
    }

    public void readNBTEssence(NBTTagCompound nbt, String player) {
        AspectList list = new AspectList();
        if (nbt.hasKey("ttr.essence")) {
            list = readNBTAspects(nbt.getTagList("ttr.essence", 10));
            // type 10 = compound
        }
        bodyEssence.put(player, list);

        list = new AspectList();
        if (nbt.hasKey("ttr.essence_primal")) {
            list = readNBTAspects(nbt.getTagList("ttr.essence_primal", 10));
        }
        bodyEssencePrimal.put(player, list);
    }

    public AspectList readNBTAspects(NBTTagList list) {
        AspectList al = new AspectList();
        if (list != null) {
            Aspect a = null;
            double c = 0;
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound nbt = list.getCompoundTagAt(i);
                if (nbt.hasKey("tag")) {
                    a = Aspect.getAspect(nbt.getString("tag"));
                    c = nbt.getDouble("amount");
                    al.add(a, c);
                }
            }
        }
        return al;
    }

    public NBTTagCompound writeNBTEssence(String player) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("ttr.essence", writeNBTAspects(getAspects(player)));
        nbt.setTag("ttr.essence_primal", writeNBTAspects(getAspectsPrimal(player)));
        return nbt;
    }

    public NBTTagList writeNBTAspects(AspectList list) {
        NBTTagList nbt = new NBTTagList();
        if (list != null && list.size() > 0) {
            for (Aspect a : list.getAspects()) {
                NBTTagCompound c = new NBTTagCompound();
                c.setString("tag", a.getTag());
                c.setDouble("amount", list.getAmount(a));
                nbt.appendTag(c);
            }
        }
        return nbt;
    }

    public void clear() {
        bodyEssencePrimal.clear();
        bodyEssence.clear();
    }

    public AspectList getAspectsPrimal(String player) {
        return bodyEssencePrimal.computeIfAbsent(player, k -> new AspectList());
    }

    public AspectList getAspects(String player) {
        return bodyEssence.computeIfAbsent(player, k -> new AspectList());
    }

    public void clearPlayer(String id) {
        bodyEssence.remove(id);
        bodyEssencePrimal.remove(id);
    }

    public static class BodyEssenceCommand extends CommandBase {

        private List<String> aliases = new ArrayList<>();

        public BodyEssenceCommand() {
            this.aliases.add("bodyessence");
            this.aliases.add("be");
            this.aliases.add("bodyes");
        }

        @Override
        public List<String> getCommandAliases() {
            return this.aliases;
        }

        @Override
        public String getCommandName() {
            return "bodyessence";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/bodyessence <action> [<player> [<params>]]";
        }

        @Override
        public boolean canCommandSenderUseCommand(ICommandSender sender) {
            return true;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            if (args.length == 0) {
                sender.addChatMessage(new ChatComponentTranslation("Invalid arguments", new Object[0]));
            } else {
                if (args[0].equalsIgnoreCase("clear")) {
                    if (!sender.canCommandSenderUseCommand(2, this.getCommandName())) {
                        sender.addChatMessage(new ChatComponentTranslation("Only admin can clear body essence"));
                    } else {
                        EntityPlayerMP player;
                        try {
                            player = getPlayer(sender, args[1]);
                            String id = player.getUniqueID().toString();
                            ThaumTraveller.proxy.getBodyEssence().clearPlayer(id);
                            sender.addChatMessage(
                                    new ChatComponentTranslation(args[1] + "'s body essence has been cleared!"));
                        } catch (Exception e) {
                            sender.addChatMessage(new ChatComponentTranslation("No such player exists!"));
                        }
                    }
                    return;
                }
                if (args[0].equalsIgnoreCase("view")) {
                    EntityPlayerMP player;
                    try {
                        player = getPlayer(sender, args[1]);
                        String id = player.getUniqueID().toString();
                        AspectList essence = ThaumTraveller.proxy.getBodyEssence().getAspects(id);
                        AspectList primal = ThaumTraveller.proxy.getBodyEssence().getAspectsPrimal(id);
                        sender.addChatMessage(new ChatComponentTranslation("your body essence is:"));
                        if (essence.size() > 0) {
                            for (Aspect a : essence.getAspects()) {
                                sender.addChatMessage(
                                        new ChatComponentTranslation(a.getTag() + ": " + essence.getAmount(a)));
                            }
                            sender.addChatMessage(new ChatComponentTranslation("for primal aspects:"));
                            for (Aspect a : primal.getAspects()) {
                                sender.addChatMessage(
                                        new ChatComponentTranslation(a.getTag() + ": " + primal.getAmount(a)));
                            }
                        } else sender.addChatMessage(new ChatComponentTranslation("null currently"));
                    } catch (Exception e) {
                        sender.addChatMessage(new ChatComponentTranslation("No such player exists!"));
                    }
                    return;
                }
                sender.addChatMessage(new ChatComponentTranslation("Invalid arguments"));
            }
        }
    }

}
