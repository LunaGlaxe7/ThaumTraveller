package lunaglaxe7.thaumtraveller.items.wands;

import net.minecraft.item.ItemStack;

import lunaglaxe7.thaumtraveller.api.UType;
import thaumcraft.api.wands.WandCap;

// try to realize upgradable covalent cap
public class TravelCap extends WandCap {

    public TravelCap(String tag, float discount, ItemStack item, int craftCost) {
        super(tag, discount, item, craftCost);
    }

    @Override
    public float getBaseCostModifier() {
        float dis = super.getBaseCostModifier();

        String[] tags = this.getTag().split("_");
        if (tags.length > 1) {
            for (int i = 1; i < tags.length; i++) {
                if (tags[i].equals(UType.CONDUCTING.getName())) {
                    dis *= 0.9f;
                }
            }
        }
        return dis;
    }
}
