package lunaglaxe7.thaumtraveller.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import lunaglaxe7.thaumtraveller.client.gui.GuiForge;
import lunaglaxe7.thaumtraveller.common.TTRContents;

public class GuiButtonCap extends GuiButton {

    private ResourceLocation gui = new ResourceLocation(TTRContents.GUI_FORGE);
    private ItemStack cap;
    private boolean selected;
    private GuiForge parent;

    public GuiButtonCap(GuiForge parent, ItemStack cap, int id, int x, int y) {
        super(id, x, y, 18, 18, "");
        this.parent = parent;
        this.cap = cap;
        this.selected = (id == 1 && parent.cap2) || (id == 2 && parent.cap1);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!enabled) return;
        int x = 176;
        int width = 18, height = 18;
        mc.renderEngine.bindTexture(gui);
        if (selected) GL11.glColor4f(153 / 255f, 190 / 255f, 1, 1);
        drawTexturedModalRect(xPosition, yPosition, x, 0, width, height);
        if (selected) GL11.glColor4f(1, 1, 1, 1);
        IIcon icon = cap.getIconIndex();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        drawTexturedModelRectFromIcon(xPosition + 1, yPosition + 1, icon, 16, 16);
    }
}
