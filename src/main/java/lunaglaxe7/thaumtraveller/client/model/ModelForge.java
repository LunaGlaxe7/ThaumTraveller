package lunaglaxe7.thaumtraveller.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import lunaglaxe7.thaumtraveller.client.DrawHelper;
import thaumcraft.api.aspects.Aspect;

public class ModelForge extends ModelBase {

    ModelRenderer base;
    ModelRenderer arm;

    private final ResourceLocation texture = new ResourceLocation(
            "thaumtraveller",
            "textures/models/wand_forge_base.png");
    private final ResourceLocation armTextures = new ResourceLocation(
            "thaumtraveller",
            "textures/models/wand_forge_arm.png");

    public ModelForge() {

        this.base = new ModelRenderer(this, 0, 0);
        this.base.setTextureSize(64, 64);
        this.base.setTextureOffset(0, 14).addBox(2, 0, 2, 12, 2, 12).setTextureOffset(0, 0).addBox(2, 3, 2, 12, 2, 12)
                .setTextureOffset(0, 28).addBox(2, 2, 2, 10, 1, 12).setTextureOffset(0, 41).addBox(12, 2, 13, 2, 1, 1)
                .setTextureOffset(6, 41).addBox(12, 2, 2, 2, 1, 1).setRotationPoint(0, 0, 0);

        this.arm = new ModelRenderer(this, 0, 0);
        this.arm.setTextureSize(64, 64);
        this.arm.addBox(0, 0, 0, 4 * 4, 7 * 4, 2 * 4);
        this.arm.setRotationPoint(0, 0, 0);

    }

    public void render(float f) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.base.render(f);

        GL11.glPushMatrix();
        translatef(9, 5, 2);
        GL11.glRotatef(-45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        GL11.glDisable(GL11.GL_LIGHTING);
        Minecraft.getMinecraft().renderEngine.bindTexture(armTextures);
        DrawHelper.setAspectColor(Aspect.AIR);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        translatef(3, 5, 2);
        GL11.glRotatef(-45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        DrawHelper.setAspectColor(Aspect.EARTH);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        translatef(3, 6, 12);
        GL11.glRotatef(45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        DrawHelper.setAspectColor(Aspect.FIRE);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        translatef(9, 6, 12);
        GL11.glRotatef(45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        DrawHelper.setAspectColor(Aspect.WATER);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        translatef(2, 5, 7);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(-45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        DrawHelper.setAspectColor(Aspect.ORDER);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        translatef(2, 5, 13);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(-45, 1, 0, 0);
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        DrawHelper.setAspectColor(Aspect.ENTROPY);
        this.arm.render(f);
        GL11.glPopMatrix();

        GL11.glColor4f(1, 1, 1, 1);
    }

    public void translatef(float x, float y, float z) {
        float f = 0.0625f;
        GL11.glTranslatef(x * f, y * f, z * f);
    }

}
