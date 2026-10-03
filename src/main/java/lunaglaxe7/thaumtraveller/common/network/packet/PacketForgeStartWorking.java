package lunaglaxe7.thaumtraveller.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lunaglaxe7.thaumtraveller.common.tile.TileForge;

public class PacketForgeStartWorking extends PacketTile<TileForge>
        implements IMessageHandler<PacketForgeStartWorking, IMessage> {

    public PacketForgeStartWorking() {}

    public PacketForgeStartWorking(TileForge forge) {
        super(forge);
    }

    @Override
    public IMessage onMessage(PacketForgeStartWorking message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (message.tile != null) {
            TileForge forge = message.tile;
            if (forge.item == null || (forge.isWand && forge.capSelected == 0) || forge.type == null || forge.working)
                return null;
            forge.working = true;
            forge.markDirty();
        }
        return null;
    }
}
