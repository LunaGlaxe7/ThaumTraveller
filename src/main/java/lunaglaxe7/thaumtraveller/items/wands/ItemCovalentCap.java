package lunaglaxe7.thaumtraveller.items.wands;

import net.minecraft.item.Item;

import lunaglaxe7.thaumtraveller.api.IUpgradable;

public class ItemCovalentCap extends Item implements IUpgradable {

    public ItemCovalentCap() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
        this.setUnlocalizedName("WandCap");
        // creative tab to be added
    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName() + ".covalent";
    }
}
