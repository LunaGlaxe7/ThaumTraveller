package lunaglaxe7.thaumtraveller.client.gui.button;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import lunaglaxe7.thaumtraveller.client.gui.GuiForge;
import lunaglaxe7.thaumtraveller.common.TTRContents;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class GuiButtonForge extends GuiButton {

    private final ResourceLocation gui = new ResourceLocation(TTRContents.GUI_FORGE);
    private GuiForge parent;
    private TileForge forge;

    public GuiButtonForge(GuiForge parent, TileForge forge, int id, int x, int y) {
        super(id, x, y, 10, 10, "");
        this.parent = parent;
        this.forge = forge;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!enabled) return;
        int x = 176;
        int y = forge.working ? 63 : 53;
        int width = 10;
        int height = 10;
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(xPosition, yPosition, x, y, width, height);

        if (mouseX >= xPosition && mouseX < xPosition + width
                && mouseY >= yPosition
                && mouseY < yPosition + height
                && !forge.working) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("ttr.forgestart"));
            parent.tooltip = tooltip;
        }

    }
}
