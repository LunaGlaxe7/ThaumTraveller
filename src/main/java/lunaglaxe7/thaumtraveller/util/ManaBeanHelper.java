package lunaglaxe7.thaumtraveller.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ManaBeanHelper {

    public static ItemStack beanWithAspect(Aspect aspect) {
        ItemStack bean = ItemApi.getItem("itemManaBean", 0);
        NBTTagCompound nbtTag = new NBTTagCompound();
        new AspectList().add(aspect, 1).writeToNBT(nbtTag);
        bean.setTagCompound(nbtTag);
        return bean;
    }
}
