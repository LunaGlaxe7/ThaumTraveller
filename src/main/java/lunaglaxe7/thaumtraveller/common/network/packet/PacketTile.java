package lunaglaxe7.thaumtraveller.common.network.packet;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketTile<T extends TileEntity> implements IMessage {

    private int x, y, z, dim;
    protected T tile;

    public PacketTile() {}

    public PacketTile(T tile) {
        this.tile = tile;
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.dim = tile.getWorldObj().provider.dimensionId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        dim = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(dim);
    }

    public IMessage onMessage(PacketTile message, MessageContext ctx) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null) {
            World world = server.worldServerForDimension(message.dim);
            if (world == null) return null;
            TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
            if (tile == null) return null;
            message.tile = (T) tile;
        }
        return null;
    }
}
