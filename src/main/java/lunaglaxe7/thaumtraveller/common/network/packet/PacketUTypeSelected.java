package lunaglaxe7.thaumtraveller.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lunaglaxe7.thaumtraveller.api.UType;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class PacketUTypeSelected extends PacketTile<TileForge>
        implements IMessageHandler<PacketUTypeSelected, IMessage> {

    private int typeIndex;
    private int displayIndex;

    public PacketUTypeSelected() {}

    public PacketUTypeSelected(TileForge forge, int id, int displayIndex) {
        super(forge);
        this.typeIndex = id;
        this.displayIndex = displayIndex;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.typeIndex = buf.readInt();
        this.displayIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(this.typeIndex);
        buf.writeInt(this.displayIndex);
    }

    @Override
    public IMessage onMessage(PacketUTypeSelected message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (message.tile == null) return null;
        TileForge forge = message.tile;
        if (forge.working || forge.item == null) return null;
        forge.type = UType.getTypeById(message.typeIndex);
        forge.typeSelected = message.displayIndex;
        forge.typeChanged = true;
        forge.initTotal();
        forge.getWorldObj().markBlockForUpdate(forge.xCoord, forge.yCoord, forge.zCoord);
        return null;
    }
}
