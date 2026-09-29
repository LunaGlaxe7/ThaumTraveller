package lunaglaxe7.thaumtraveller.common.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ContainerForge extends Container {

    private TileForge forge;

    public ContainerForge(TileForge forge, InventoryPlayer playerInv) {
        this.forge = forge;
        // slot从gui图指定的坐标位置开始画16x16，坐标从左上角起始
        addSlotToContainer(new Slot(forge, 0, 124, 39));
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 9; y++) {
                addSlotToContainer(new Slot(playerInv, y + x * 9 + 9, 8 + y * 18, 108 + x * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 166));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = getSlot(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack item = slot.getStack();
            stack = item.copy();
            if (index > 0) {
                // 放入forge
                if ((item.getItem() instanceof ItemWandCasting || WandHelper.getSpecialCapFromCap(item) != null)
                        && !mergeItemStack(item, 0, 1, false))
                    return null;
                else// 点击背包
                    if (index < 28) {
                        if (!mergeItemStack(item, 28, 37, false)) return null;
                    } else// 点击快捷栏
                        if (index < 37 && !mergeItemStack(item, 1, 28, false)) return null;
            } // 从forge中取出
            else if (!mergeItemStack(item, 1, 37, false)) return null;

            if (item.stackSize == 0) {
                slot.putStack(null);
            } else slot.onSlotChanged();

            if (item.stackSize == stack.stackSize) return null;

            slot.onPickupFromSlot(player, item);
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return forge.isUseableByPlayer(player);
    }
}
