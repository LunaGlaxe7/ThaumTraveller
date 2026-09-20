package lunaglaxe7.thaumtraveller.items.wands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import lunaglaxe7.thaumtraveller.api.IUpgradable;
import lunaglaxe7.thaumtraveller.api.UType;

public class ItemCovalentCap extends Item implements IUpgradable {

    public ItemCovalentCap() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
        this.setUnlocalizedName("WandCap");
        // creative tab to be added
    }

    // TODO
    @Override
    public List<UType> getUpgrade(ItemStack item) {
        // all nbt types count 12
        // type 0=END,1=BYTE,2=SHORT,3=INT,4=LONG,
        // 5=FLOAT,6=DOUBLE,7=BYTE[],8=STRING,9=LIST
        // 10=COMPOUND,11=INT[]
        ArrayList<UType> list = new ArrayList();
        if (item.hasTagCompound()) {
            NBTTagList nbt = item.getTagCompound().getTagList("TTR.Upgrade", 8);
            for (int i = 0; i < nbt.tagCount(); i++) {
                list.add(UType.getTypeByName(nbt.getStringTagAt(i)));
            }
        }
        return list;
    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName() + ".covalent";
    }
}
