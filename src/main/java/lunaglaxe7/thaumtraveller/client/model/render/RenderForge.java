package lunaglaxe7.thaumtraveller.client.model.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import lunaglaxe7.thaumtraveller.client.model.ModelForge;

public class RenderForge extends TileEntitySpecialRenderer {

    private final ModelForge model = new ModelForge();

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float p_147500_8_) {
        int dir = entity.getBlockMetadata();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslated(0.5d, 0, 0.5d);
        GL11.glRotatef(90 * (1 - dir), 0, 1, 0);
        GL11.glTranslated(-0.5d, 0, -0.5d);
        model.render(0.0625f);
        GL11.glPopMatrix();
    }
}
