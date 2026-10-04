package lunaglaxe7.thaumtraveller.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import lunaglaxe7.thaumtraveller.api.UType;
import lunaglaxe7.thaumtraveller.api.util.AspectHelper;
import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import lunaglaxe7.thaumtraveller.client.DrawHelper;
import lunaglaxe7.thaumtraveller.client.gui.button.GuiButtonCap;
import lunaglaxe7.thaumtraveller.client.gui.button.GuiButtonForge;
import lunaglaxe7.thaumtraveller.common.TTRContents;
import lunaglaxe7.thaumtraveller.common.network.NetHandler;
import lunaglaxe7.thaumtraveller.common.network.packet.PacketCapSelectedToServer;
import lunaglaxe7.thaumtraveller.common.network.packet.PacketForgeStartWorking;
import lunaglaxe7.thaumtraveller.common.network.packet.PacketUTypeSelected;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;
import lunaglaxe7.thaumtraveller.common.tile.container.ContainerForge;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class GuiForge extends GuiContainer {

    private final ResourceLocation gui = new ResourceLocation(TTRContents.GUI_FORGE);
    private TileForge forge;
    private ItemStack stack;
    private boolean isWand;
    public List<String> tooltip = new ArrayList<>();
    int x, y;
    public boolean cap1;
    public boolean cap2;
    int utypeWH = 12;
    // 选择的utype在显示界面上的位置
    private int indexSelected = -1;

    public GuiForge(TileForge forge, InventoryPlayer playerInv) {
        super(new ContainerForge(forge, playerInv));
        this.forge = forge;
        stack = forge.getStackInSlot(0);
        this.isWand = false;
        this.ySize = 190;
        cap1 = false;
        cap2 = false;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        setButtons();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        boolean currentWand = forge.isWand;
        if (currentWand != this.isWand) {
            this.isWand = currentWand;
        }
        this.stack = forge.item;
        cap1 = forge.capSelected == 1;
        cap2 = forge.capSelected == 2;
        int currentType = forge.typeSelected;
        if (currentType != indexSelected) {
            this.indexSelected = forge.typeSelected;
        }
        setButtons();
    }

    public void setButtons() {
        buttonList.clear();
        GuiButton startButton = new GuiButtonForge(this, forge, 0, x + 127, y + 69);
        buttonList.add(startButton);
        startButton.enabled = true;

        if (isWand && stack != null) {
            ItemStack cap = WandHelper.getTopCapItem(stack);
            GuiButton capTop = new GuiButtonCap(this, cap, 1, x + 74, y + 6);
            buttonList.add(capTop);
            capTop.enabled = true;
            cap = WandHelper.getBotCapItem(stack);
            GuiButton capBot = new GuiButtonCap(this, cap, 2, x + 74, y + 67);
            buttonList.add(capBot);
            capBot.enabled = true;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int xx = mouseX - x;
        int yy = mouseY - y;

        if (stack != null) {
            // select available types on when can be up
            if ((!isWand || cap1 || cap2) && UType.checkCanUpgrade(stack, isWand, "cap" + (cap1 ? 1 : 2) + "#")) {
                if (xx >= 31 && xx < 31 + utypeWH) {
                    if (yy >= 12 && yy < 12 + 70) {
                        int y1 = yy - 12 + 1;
                        if (y1 % 13 != 0) {
                            int index = y1 / 13;
                            UType[] u;
                            if (!isWand) {
                                u = UType.getTypesCanApply(UType.ApplyType.CAP);
                                if (index < u.length) {
                                    indexSelected = index;
                                    NetHandler.instance
                                            .sendToServer(new PacketUTypeSelected(forge, u[index].getId(), index));
                                }
                            }
                            if (isWand && (cap1 || cap2)) {
                                u = UType.getTypesCanApply(UType.ApplyType.WAND);
                                if (index < u.length) {
                                    indexSelected = index;
                                    NetHandler.instance
                                            .sendToServer(new PacketUTypeSelected(forge, u[index].getId(), index));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (stack == null) {
            indexSelected = -1;
        }

    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button != null) {
            switch (button.id) {
                case 0: {
                    NetHandler.instance.sendToServer(new PacketForgeStartWorking(forge));
                    return;
                }
                case 1: {
                    // top button
                    NetHandler.instance.sendToServer(new PacketCapSelectedToServer(forge, 2));
                    break;
                }
                case 2: {
                    // bottom button
                    NetHandler.instance.sendToServer(new PacketCapSelectedToServer(forge, 1));
                    break;
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // drawing aspects
        RenderHelper.enableStandardItemLighting();
        if (forge.type != null && this.indexSelected > -1) {
            AspectList con = forge.type.getConsume();
            Aspect[] l = con.getAspects();
            for (int i = 0; i < l.length; i++) {
                drawTag(l[i], x + 49, y + 10 + i * 17, 16);
                drawString(
                        this.fontRendererObj,
                        "x" + con.getAmount(l[i]),
                        x + 49 + 17,
                        y + 10 + i * 17 - 1 + 8,
                        l[i].getColor());
            }
        }

        // drawing aspect bars
        Aspect[] aspects = AspectHelper.getPrimalAspects();
        mc.renderEngine.bindTexture(gui);
        for (int i = 0; i < aspects.length; i++) {
            GL11.glColor4f(1, 1, 1, 1);
            drawTexturedModalRect(x + 96 + 9 * i + 19 * (i / 3), y + 59, 176, 48, 8, 5);
            if (forge.totalA.getAmount(aspects[i]) > 0) {
                float s = forge.currentA.getAmount(aspects[i]) / (float) forge.totalA.getAmount(aspects[i]);
                int p = (int) (s * 27);
                if (s > 0) {
                    setAspectColor(aspects[i]);
                    drawTexturedModalRect(x + 98 + 9 * i + 19 * (i / 3), y + 59 - p, 186, 18, 4, p);
                }
                GL11.glColor4f(1, 1, 1, 1);
                drawTexturedModalRect(x + 96 + 9 * i + 19 * (i / 3), y + 29, 176, 18, 8, 30);
            } else {
                GL11.glColor4f(1, 1, 1, 1);
                drawTexturedModalRect(x + 96 + 9 * i + 19 * (i / 3), y + 54, 176, 18, 8, 5);
            }

        }
        RenderHelper.disableStandardItemLighting();

        // drawing tooltips
        GL11.glColor4f(1, 1, 1, 1);
        // 绘制在最上层，不会被遮挡
        int xx = mouseX - x;
        int yy = mouseY - y;
        if (xx >= 127 && yy >= 69 && xx < 127 + 10 && yy < 69 + 10) {
            if (!forge.working) {
                drawTooltips(mouseX, mouseY);
            }
        }
        if (this.isWand && xx >= 75 && xx < 75 + 16) {
            if (yy >= 7 && yy < 7 + 16) {
                ItemStack cap = WandHelper.getTopCapItem(stack);
                drawWandCap(cap, mouseX, mouseY, "cap2#");
            }
            if (yy >= 68 && yy < 68 + 16) {
                ItemStack cap = WandHelper.getBotCapItem(stack);
                drawWandCap(cap, mouseX, mouseY, "cap1#");
            }
        }
        if (stack != null) {
            // tooltips applied utypes
            if (xx >= 10 && xx < 10 + utypeWH) {
                if (yy >= 12 && y < 12 + 70) {
                    int y1 = yy - 12 + 1;
                    if (y1 % 13 != 0) {
                        int index = y1 / 13;
                        if (!isWand && stack.hasTagCompound()) {
                            UType[] types = UType.getTypeByNbt(stack.getTagCompound());
                            drawUTypeTooltip(mouseX, mouseY, index, types);
                        }
                        if (isWand && (cap1 || cap2)) {
                            ItemStack cap;
                            if (cap1) {
                                cap = WandHelper.getBotCapItem(stack);
                                drawAppliedWhenWand(cap, mouseX, mouseY, "cap1#", index);
                            }
                            if (cap2) {
                                cap = WandHelper.getTopCapItem(stack);
                                drawAppliedWhenWand(cap, mouseX, mouseY, "cap2#", index);
                            }
                        }
                    }
                }
            }
            // tooltips available utypes
            if (xx >= 31 && xx < 31 + utypeWH) {
                if (yy >= 12 && yy < 12 + 70) {
                    int y1 = yy - 12 + 1;
                    if (y1 % 13 != 0) {
                        int index = y1 / 13;
                        UType[] u;
                        if (!isWand) {
                            if (UType.checkCanUpgrade(stack, false, "")) {
                                u = UType.getTypesCanApply(UType.ApplyType.CAP);
                                if (index < u.length) drawUTypeTooltip(mouseX, mouseY, index, u);
                            }
                        }
                        if (isWand && (cap1 || cap2)) {
                            if (UType.checkCanUpgrade(stack, true, "cap" + (cap1 ? 1 : 2) + "#")) {
                                u = UType.getTypesCanApply(UType.ApplyType.WAND);
                                if (index < u.length) drawUTypeTooltip(mouseX, mouseY, index, u);
                            }
                        }
                    }
                }
            }
            // aspects amount working
            if (forge.working && forge.currentA != null && forge.totalA != null) {
                if (yy >= 29 && yy < 29 + 35) {
                    if (xx >= 96 && xx < 96 + 26) {
                        int x1 = xx - 96 + 1;
                        if (x1 % 9 != 0) {
                            int index = x1 / 9;
                            drawAspectTooltips(mouseX, mouseY, index);
                        }
                    }
                    if (xx >= 142 && xx < 142 + 26) {
                        int x2 = xx - 142 + 1;
                        if (x2 % 9 != 0) {
                            int index = x2 / 9 + 3;
                            drawAspectTooltips(mouseX, mouseY, index);
                        }
                    }
                }
            }
        }
    }

    private void drawAspectTooltips(int mouseX, int mouseY, int index) {
        Aspect a = AspectHelper.getPrimalAspects()[index];
        List<String> t = new ArrayList<>();
        t.add(a.getTag().toUpperCase());
        t.add(
                String.format("%.2f", forge.currentA.getAmount(a) / 100f) + "/"
                        + String.format("%.2f", forge.totalA.getAmount(a) / 100f));
        this.tooltip = t;
        drawTooltips(mouseX, mouseY);
    }

    private void drawAppliedWhenWand(ItemStack cap, int mouseX, int mouseY, String key, int index) {
        if (cap == null || !cap.hasTagCompound() || !cap.getTagCompound().hasKey(key)) return;
        UType[] u = UType.getTypeByNbt(cap.getTagCompound().getCompoundTag(key));
        drawUTypeTooltip(mouseX, mouseY, index, u);
    }

    private void drawUTypeTooltip(int mouseX, int mouseY, int index, UType[] u) {
        if (index < u.length) {
            Color color = new Color(1, 1, 1, 0.7f);
            if (mouseX >= 10 + x && mouseX < 10 + x + utypeWH) {
                drawRect(10 + x, y + 13 * index + 12, x + 10 + utypeWH, y + 12 + 13 * index + utypeWH, color.getRGB());
            }
            if (mouseX >= 31 + x && mouseX < 31 + x + utypeWH) {
                drawRect(x + 31, y + 13 * index + 12, x + 31 + utypeWH, y + 12 + 13 * index + utypeWH, color.getRGB());
            }
            List<String> text = new ArrayList<>();
            text.add(StatCollector.translateToLocal(u[index].getDisplayName()));
            text.add(StatCollector.translateToLocal(u[index].getDescription()));
            this.tooltip = text;
            drawTooltips(mouseX, mouseY);
        }
    }

    // wand cap tooltips
    private void drawWandCap(ItemStack cap, int mouseX, int mouseY, String key) {
        if (cap == null) return;
        List<String> text = new ArrayList<>();
        text.add(StatCollector.translateToLocal(cap.getDisplayName()));
        if (cap.hasTagCompound() && cap.getTagCompound().hasKey(key)) {
            UType[] u = UType.getTypeByNbt(cap.getTagCompound().getCompoundTag(key));
            if (u.length > 0) {
                text.add(StatCollector.translateToLocal("utype.upgrades"));
                for (UType t : u) {
                    text.add(StatCollector.translateToLocal(t.getDisplayName()));
                }
            }
        }
        this.tooltip = text;
        drawTooltips(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawUTypes();
        // original size maybe too large
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 8, y + 4, 0);
        GL11.glScalef(0.5f, 0.5f, 1);
        drawString(
                this.fontRendererObj,
                StatCollector.translateToLocal("utype.applied"),
                0,
                0,
                new Color(78 / 255f, 68 / 255f, 1f, 1f).getRGB());
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x + 29, y + 4, 0);
        GL11.glScalef(0.5f, 0.5f, 1);
        drawString(
                this.fontRendererObj,
                StatCollector.translateToLocal("utype.available"),
                0,
                0,
                new Color(78 / 255f, 68 / 255f, 1f, 1f).getRGB());
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    // drawing types applied and available
    private void drawUTypes() {
        if (stack != null) {
            if (this.isWand && (cap1 || cap2)) {
                drawUTypesApplied(stack, true, "cap" + (cap1 ? 1 : 2) + "#");
                if (UType.checkCanUpgrade(stack, true, "cap" + (cap1 ? 1 : 2) + "#")) {
                    drawUTypesAvailable(UType.ApplyType.WAND);
                }
            }
            if (!this.isWand) {
                drawUTypesApplied(stack, false, "");
                if (UType.checkCanUpgrade(stack, false, "")) {
                    drawUTypesAvailable(UType.ApplyType.CAP);
                }
            }
        }
    }

    private void drawUTypesAvailable(UType.ApplyType type) {
        UType[] types = UType.getTypesCanApply(type);
        if (types != null && types.length > 0) {
            for (int i = 0; i < types.length; i++) {
                if (i == indexSelected) {
                    drawRect(
                            x + 31,
                            y + 12 + i * 13,
                            x + 31 + utypeWH,
                            y + 12 + i * 13 + utypeWH,
                            new Color(1, 1, 1, 0.3f).getRGB());
                }
                drawTag(types[i].getIcon(), x + 31, y + 12 + i * 13);
            }
        }
    }

    private void drawUTypesApplied(ItemStack stack, boolean isWand, String key) {
        if (stack == null) return;
        // 无升级的cap
        if (!isWand && !stack.hasTagCompound()) return;
        if (isWand && !stack.getTagCompound().hasKey(key)) return;

        NBTTagCompound nbt = isWand ? stack.getTagCompound().getCompoundTag(key) : stack.getTagCompound();
        UType[] types = UType.getTypeByNbt(nbt);
        if (types.length > 0) {
            for (int i = 0; i < types.length; i++) {
                drawTag(types[i].getIcon(), x + 10, y + 12 + i * 13);
            }
        }

    }

    private void drawTooltips(int x, int y) {
        func_146283_a(tooltip, x, y);
    }

    private void drawTag(Aspect tag, int xPos, int yPos) {
        drawTag(tag, xPos, yPos, utypeWH);
    }

    private void drawTag(Aspect tag, int xPos, int yPos, int width) {
        float xScale = width / 32f;
        float yScale = width / 32f;
        GL11.glPushMatrix();
        GL11.glTranslated(xPos, yPos, 0);
        GL11.glScalef(xScale, yScale, 1);
        setAspectColor(tag);
        mc.renderEngine.bindTexture(tag.getImage());
        func_146110_a(0, 0, 0, 0, 32, 32, 32, 32);
        GL11.glPopMatrix();
    }

    private void setAspectColor(Aspect tag) {
        DrawHelper.setAspectColor(tag);
    }
}
