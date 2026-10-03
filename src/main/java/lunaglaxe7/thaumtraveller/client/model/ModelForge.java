package lunaglaxe7.thaumtraveller.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class ModelForge extends ModelBase {

    ModelRenderer base;
    ModelRenderer arm1;
    ModelRenderer arm2;
    ModelRenderer arm3;
    ModelRenderer arm4;
    ModelRenderer arm5;
    ModelRenderer arm6;

    private final ResourceLocation texture = new ResourceLocation(
            "thaumtraveller",
            "textures/models/wand_forge_base.png");
    private final ResourceLocation armTextures = new ResourceLocation(
            "thaumtraveller",
            "textures/models/wand_forge_arm.png");

    public ModelForge() {

        this.base = new ModelRenderer(this, 0, 0);
        this.base.addBox(2, 0, 2, 12, 2, 12).addBox(2, 3, 2, 12, 2, 12).addBox(2, 2, 2, 10, 1, 12)
                .addBox(12, 2, 13, 2, 1, 1).addBox(12, 2, 2, 2, 1, 1).setRotationPoint(0, 0, 0);
        this.base.setTextureSize(36, 36);

        this.arm1 = new ModelRenderer(this, 0, 9);
        this.arm1.addBox(0, 0, 0, 4, 2, 7);
        this.arm1.setRotationPoint(9, 9, -4);
        setRotationAngles(arm1, (float) Math.toRadians(45), 0, 0);

        this.arm2 = new ModelRenderer(this, 0, 0);
        this.arm2.addBox(0, 0, 0, 4, 2, 7);
        this.arm2.setRotationPoint(3, 9, -4);
        setRotationAngles(arm2, (float) Math.toRadians(45), 0, 0);

        this.arm3 = new ModelRenderer(this, 22, 9);
        this.arm3.addBox(0, 0, 0, 4, 7, 2);
        this.arm3.setRotationPoint(3, 6, 13);
        setRotationAngles(arm3, (float) Math.toRadians(45), 0, 0);

        this.arm4 = new ModelRenderer(this, 22, 0);
        this.arm4.addBox(0, 0, 0, 4, 7, 2);
        this.arm4.setRotationPoint(9, 6, 13);
        setRotationAngles(arm4, (float) Math.toRadians(45), 0, 0);

        this.arm5 = new ModelRenderer(this, 12, 18);
        this.arm5.addBox(0, 0, 0, 2, 7, 4).setRotationPoint(2, 5, 9);
        setRotationAngles(arm5, 0, 0, (float) Math.toRadians(45));

        this.arm6 = new ModelRenderer(this, 0, 18);
        this.arm6.addBox(0, 0, 0, 2, 7, 4).setRotationPoint(2, 5, 3);
        setRotationAngles(arm6, 0, 0, (float) Math.toRadians(45));
    }

    public void render(float f) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        this.base.render(f);

        Minecraft.getMinecraft().renderEngine.bindTexture(armTextures);
        this.arm1.renderWithRotation(f);
        this.arm2.renderWithRotation(f);
        this.arm3.renderWithRotation(f);
        this.arm4.renderWithRotation(f);
        this.arm5.renderWithRotation(f);
        this.arm6.renderWithRotation(f);
    }

    private void setArmsTextureSize() {
        arm1.setTextureSize(64, 64);
        arm2.setTextureSize(64, 64);
        arm3.setTextureSize(64, 64);
        arm4.setTextureSize(64, 64);
        arm5.setTextureSize(64, 64);
        arm6.setTextureSize(64, 64);
    }

    private void setRotationAngles(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
