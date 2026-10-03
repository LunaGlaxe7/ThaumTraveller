package lunaglaxe7.thaumtraveller.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class UType {

    private final String name;
    private final List<ApplyType> applyTypes = new ArrayList<>();
    private final Aspect icon;
    private final AspectList consume;
    private final int id;
    private final static List<UType> types = new ArrayList<>();
    private final static Map<String, UType> map = new HashMap<>();
    public final static String UTYPE_KEY = "upgrading";
    public final static String UTYPE_COUNT_KEY = "upgrades";

    // 传导升级，每层使vis消耗减少5%
    // upgrade of conducting, every level reduces 5% consuming of vis
    public static UType CONDUCTING = new UType(
            "conducting",
            Aspect.ENERGY,
            new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 10).add(Aspect.FIRE, 10),
            new ApplyType[] { ApplyType.CAP, ApplyType.WAND });
    // 亲和升级，减少制作消耗
    public static UType AFFINITY = new UType(
            "affinity",
            Aspect.MAGIC,
            new AspectList().add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10),
            new ApplyType[] { ApplyType.CAP });

    public UType(String name, Aspect icon, AspectList consume, ApplyType[] applyTypes) {
        this.name = name;
        this.id = types.size();
        types.add(this);
        map.put(name, this);
        this.icon = icon;
        this.consume = consume;
        this.applyTypes.addAll(Arrays.asList(applyTypes));
    }

    public List<ApplyType> getApplyTypes() {
        return applyTypes;
    }

    public Aspect getIcon() {
        return icon;
    }

    public AspectList getConsume() {
        return consume;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return "utype." + name + ".name";
    }

    public String getDescription() {
        return "utype." + name + ".desc";
    }

    public static UType getTypeByName(String name) {
        return map.get(name);
    }

    /**
     * @param nbt nbt contains upgrading, the key name formatted as "cap1#" "cap2#", or the nbt on cap or rod
     */
    public static UType[] getTypeByNbt(NBTTagCompound nbt) {
        if (!nbt.hasKey(UTYPE_KEY)) return new UType[0];
        NBTTagList list = nbt.getTagList(UTYPE_KEY, 8);// type 8 = string
        UType[] types = new UType[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) {
            types[i] = getTypeByName(list.getStringTagAt(i));
        }
        return types;
    }

    /**
     * @param key key for wand to get nbt contains upgrades
     */
    public static boolean checkCanUpgrade(ItemStack stack, boolean isWand, String key) {
        boolean out = false;
        if (stack != null) {
            if (!isWand) {
                out = !stack.hasTagCompound() || // 升级次数不多于5
                        stack.getTagCompound().getInteger(UTYPE_COUNT_KEY) < 5;
            } else {
                // wand cant has not nbt
                out = !stack.getTagCompound().hasKey(key)
                        || stack.getTagCompound().getCompoundTag(key).getInteger(UTYPE_COUNT_KEY) < 5;
            }
        }
        return out;
    }

    public static void addUTypeToNbt(NBTTagCompound nbt, UType type) {
        if (nbt.hasKey(UTYPE_KEY)) {
            nbt.getTagList(UTYPE_KEY, 8).appendTag(new NBTTagString(type.getName()));
        } else {
            NBTTagList list = new NBTTagList();
            list.appendTag(new NBTTagString(type.getName()));
            nbt.setTag(UTYPE_KEY, list);
        }
        nbt.setInteger(UTYPE_COUNT_KEY, nbt.getTagList(UTYPE_KEY, 8).tagCount());
    }

    public static UType[] getTypesCanApply(ApplyType type) {
        UType[] src = getTypes();
        List<UType> out = new ArrayList<>();
        if (src != null && src.length > 0) {
            for (UType t : src) {
                List<ApplyType> a = t.getApplyTypes();
                if (a.contains(type)) out.add(t);
            }
        }
        return out.toArray(new UType[0]);
    }

    public static UType[] getTypes() {
        return types.toArray(new UType[0]);
    }

    public static UType getTypeById(int id) {
        return types.get(id);
    }

    public static Map<String, UType> getTypesMap() {
        return map;
    }

    public enum ApplyType {
        CAP,
        ROD,
        WAND
    }
}
