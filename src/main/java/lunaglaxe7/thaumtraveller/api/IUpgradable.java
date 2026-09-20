package lunaglaxe7.thaumtraveller.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * To make your item upgradable, mainly for my caps and rods now.
 */
public interface IUpgradable {

    List<UType> getUpgrade(ItemStack item);
}
