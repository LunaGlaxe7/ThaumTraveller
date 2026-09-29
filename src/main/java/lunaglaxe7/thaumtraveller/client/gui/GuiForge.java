package lunaglaxe7.thaumtraveller.client.gui;

import lunaglaxe7.thaumtraveller.common.tile.TileForge;
import lunaglaxe7.thaumtraveller.common.tile.container.ContainerForge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GuiForge extends GuiContainer {
    private TileForge forge;
    private ItemStack stack;
    private boolean isWand;

    public GuiForge(TileForge forge, InventoryPlayer playerInv) {
        super(new ContainerForge(forge, playerInv));
        this.forge = forge;
        stack = forge.getStackInSlot(0);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
