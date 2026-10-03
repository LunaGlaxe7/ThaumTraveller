package lunaglaxe7.thaumtraveller.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class PacketCapSelectedToServer extends PacketTile<TileForge>
        implements IMessageHandler<PacketCapSelectedToServer, IMessage> {

    private int index;

    public PacketCapSelectedToServer() {}

    public PacketCapSelectedToServer(TileForge tile, int index) {
        super(tile);
        this.index = index;
        this.tile = tile;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(index);
    }

    @Override
    public IMessage onMessage(PacketCapSelectedToServer message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer() || message.tile == null) return null;
        if (message.tile.working) return null;
        message.tile.capSelected = message.index;
        message.tile.getWorldObj().markBlockForUpdate(message.tile.xCoord, message.tile.yCoord, message.tile.zCoord);
        return null;
    }
}
