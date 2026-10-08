package lunaglaxe7.thaumtraveller.client.model.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import lunaglaxe7.thaumtraveller.client.model.ModelForge;
import lunaglaxe7.thaumtraveller.items.ItemForge;

public class RenderItemForge implements IItemRenderer {

    private ModelForge forge = new ModelForge();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item != null && item.getItem() instanceof ItemForge) {
            GL11.glPushMatrix();
            if (type == ItemRenderType.EQUIPPED) {
                GL11.glTranslated(0.3d, 0.5d, 0.3d);
            }
            if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslated(0, 0.5d, 0);
            }
            this.forge.render(0.0625f);
            GL11.glPopMatrix();
        }
    }
}
