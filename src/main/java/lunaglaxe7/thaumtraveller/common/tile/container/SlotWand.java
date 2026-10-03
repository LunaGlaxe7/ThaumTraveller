package lunaglaxe7.thaumtraveller.common.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class SlotWand extends Slot {

    private TileForge forge;

    public SlotWand(TileForge inv, int id, int xPosition, int yPosition) {
        super(inv, id, xPosition, yPosition);
        this.forge = inv;
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        return !forge.working;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
    }
}
