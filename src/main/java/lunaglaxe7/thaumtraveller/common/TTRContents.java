package lunaglaxe7.thaumtraveller.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;
import lunaglaxe7.thaumtraveller.client.gui.GuiForge;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;
import lunaglaxe7.thaumtraveller.common.tile.container.ContainerForge;

public class TTRContents implements IGuiHandler {

    public static final String MODID = "ThaumTraveller";
    public static final String TCID = "Thaumcraft";
    public static final String GCID = "GalacticraftCore";
    public static final String GCMID = "GalacticraftMars";
    public static final String GAIAID = "GrimoireOfGaia";
    public static final String AEID = "appliedenergistics2";
    public static final String TBID = "thaumicbases";

    public static final String MISMATCHEDCAPKEY = "TTR.MISMATCHEDCAP";

    public static final int GUIID_FORGE = 0;
    public static final String GUI_FORGE = "thaumtraveller:textures/gui/wand_forge.png";

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        switch (ID) {
            case GUIID_FORGE:
                return new ContainerForge((TileForge) tile, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        switch (ID) {
            case GUIID_FORGE:
                return new GuiForge((TileForge) tile, player.inventory);
        }
        return null;
    }
}
