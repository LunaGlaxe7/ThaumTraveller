package lunaglaxe7.thaumtraveller.common.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class ContainerForge extends Container {

    private TileForge forge;

    public ContainerForge(TileForge forge, InventoryPlayer playerInv) {
        this.forge = forge;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return forge.isUseableByPlayer(player);
    }
}
