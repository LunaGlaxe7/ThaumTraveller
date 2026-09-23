package lunaglaxe7.thaumtraveller.items.wands;

import net.minecraft.item.Item;

import lunaglaxe7.thaumtraveller.common.TTRContents;

public class ItemCovalentCap extends Item {

    public ItemCovalentCap() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
        this.setUnlocalizedName("WandCap");
        this.setTextureName(TTRContents.MODID + ":" + "covalentCap");
        // creative tab to be added
    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName() + ".covalent";
    }
}
