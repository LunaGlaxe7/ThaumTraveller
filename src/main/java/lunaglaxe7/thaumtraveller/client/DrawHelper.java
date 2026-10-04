package lunaglaxe7.thaumtraveller.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;

public class DrawHelper {

    public static void setAspectColor(Aspect tag) {
        Color color = new Color(tag.getColor());
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
    }
}
