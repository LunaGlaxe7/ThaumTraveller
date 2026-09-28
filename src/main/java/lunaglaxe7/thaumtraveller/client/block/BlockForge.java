package lunaglaxe7.thaumtraveller.client.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class BlockForge extends BlockContainer {

    private IIcon icon;
    private float f = 0.0625f;

    protected BlockForge() {
        super(Material.iron);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + 2 * f, y, z + 2 * f, x + 14 * f, y + 10 * f, z + 14 * f);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + 2 * f, y, z + 2 * f, x + 14 * f, y + 10 * f, z + 14 * f);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.icon = reg.registerIcon("thaumtraveller:cap_forge");
    }

    @Override
    public String getItemIconName() {
        return icon.getIconName();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileForge();
    }
}
