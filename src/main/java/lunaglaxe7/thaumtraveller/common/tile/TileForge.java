package lunaglaxe7.thaumtraveller.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import lunaglaxe7.thaumtraveller.api.UType;
import lunaglaxe7.thaumtraveller.api.util.WandHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileForge extends TileEntity implements ISidedInventory {

    public boolean working = false;
    public ItemStack item = null;
    public boolean isWand = false;
    public int capSelected = 0;
    public UType type;
    private int tickCounting = 0;
    public int typeSelected = -1;
    public AspectList totalA = new AspectList();
    public AspectList currentA = new AspectList();
    public boolean typeChanged = false;
    private int visSize;
    private boolean itemChanged = false;
    private final String TAG_WORKING = "working";
    private final String TAG_CAP = "cap_selected";
    private final String TAG_ITEM = "item";
    private final String TAG_TYPE = "utype";
    private final String TAG_COUNT = "counting";
    private final String TAG_TYPEID = "utype_selected";
    private final String TAG_CA = "current_aspects";
    private final String TAG_TA = "total_aspects";
    private final String TAG_SIZE = "vis_size";

    /**
     * 当物品被取出时重置状态
     */
    private void statReset() {
        itemChanged = false;
        working = false;
        isWand = item != null && item.getItem() instanceof ItemWandCasting;
        capSelected = 0;
        type = null;
        typeSelected = -1;
        clearAspects(totalA);
        clearAspects(currentA);
        markDirty();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (working) {
                tickCounting++;
                initTotal();

                if (tickCounting % 5 == 0) {
                    for (Aspect a : totalA.getAspects()) {
                        int drain = VisNetHandler.drainVis(
                                worldObj,
                                xCoord,
                                yCoord,
                                zCoord,
                                a,
                                Math.min(200, totalA.getAmount(a) - currentA.getAmount(a)));
                        if (drain > 0) {
                            currentA.add(a, drain);
                            markDirty();
                        }
                    }
                }

                if (visSize <= currentA.visSize()) {
                    if (isWand) {
                        NBTTagCompound nbt = item.getTagCompound().hasKey("cap" + capSelected + "#")
                                ? item.getTagCompound().getCompoundTag("cap" + capSelected + "#")
                                : new NBTTagCompound();
                        UType.addUTypeToNbt(nbt, type);
                        item.setTagInfo("cap" + capSelected + "#", nbt);
                    } else {
                        NBTTagCompound nbt = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
                        UType.addUTypeToNbt(nbt, type);
                        item.setTagCompound(nbt);
                    }

                    tickCounting = 0;
                    working = false;
                    type = null;
                    typeSelected = -1;
                    clearAspects(totalA);
                    clearAspects(currentA);
                }
            }
            initTotal();
        }
        if (itemChanged) statReset();
        else {
            markDirty();
        }
    }

    public void readCustomNbt(NBTTagCompound nbt) {
        this.working = nbt.getBoolean(TAG_WORKING);
        this.capSelected = nbt.getInteger(TAG_CAP);
        if (nbt.hasKey(TAG_TYPE)) {
            this.type = UType.getTypeById(nbt.getInteger(TAG_TYPE));
        } else this.type = null;
        this.tickCounting = nbt.getInteger(TAG_COUNT);
        this.typeSelected = nbt.getInteger(TAG_TYPEID);
        NBTTagCompound ca, ta;
        ca = nbt.getCompoundTag(TAG_CA);
        ta = nbt.getCompoundTag(TAG_TA);
        currentA = new AspectList();
        totalA = new AspectList();
        currentA.readFromNBT(ca);
        totalA.readFromNBT(ta);
        visSize = nbt.getInteger(TAG_SIZE);
    }

    public void writeCustomNbt(NBTTagCompound nbt) {
        nbt.setBoolean(TAG_WORKING, working);
        nbt.setInteger(TAG_CAP, capSelected);
        if (type != null) {
            nbt.setInteger(TAG_TYPE, type.getId());
        }
        nbt.setInteger(TAG_COUNT, tickCounting);
        nbt.setInteger(TAG_TYPEID, typeSelected);
        NBTTagCompound ca = new NBTTagCompound();
        currentA.writeToNBT(ca);
        nbt.setTag(TAG_CA, ca);
        NBTTagCompound ta = new NBTTagCompound();
        totalA.writeToNBT(ta);
        nbt.setTag(TAG_TA, ta);
        nbt.setInteger(TAG_SIZE, visSize);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNbt(nbt);
        if (nbt.hasKey(TAG_ITEM)) {
            this.item = new ItemStack((Item) null);
            this.item.readFromNBT(nbt.getCompoundTag(TAG_ITEM));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNbt(nbt);
        if (item != null) nbt.setTag(TAG_ITEM, item.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readCustomNbt(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNbt(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbt);
    }

    public void initTotal() {
        if (type == null) return;
        if (totalA.visSize() == 0 || typeChanged) {
            clearAspects(totalA);
            for (Aspect a : type.getConsume().getAspects()) {
                totalA.add(a, type.getConsume().getAmount(a) * 100);
            }
            typeChanged = false;
        }
        visSize = totalA.visSize();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[] { 0 };
    }

    // 可以通过漏斗放入吗
    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return false;
    }

    // 可以通过漏斗取出物品吗
    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return false;
    }

    // 我们的inventory中有几个格子
    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? item : null;
    }

    // 取出物品，但是我们只能存1个物品
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index == 0 && item != null) {
            ItemStack out = item;
            item = null;
            return out;
        }
        return null;
    }

    // 关闭时物品不会掉出来
    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index != 0) return;
        itemChanged = true;
        if (stack == null) {
            item = null;
            return;
        }
        ItemStack copy = stack.copy();
        if (stack.stackSize > 1) {
            copy.stackSize = 1;
        }
        item = copy;
        isWand = item.getItem() instanceof ItemWandCasting;
    }

    @Override
    public void markDirty() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        super.markDirty();
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    // slot中能放的最大size，我们只放一个杖端或者法杖
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj != null && worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index == 0 && !working
                && (stack.getItem() instanceof ItemWandCasting || WandHelper.getSpecialCapFromCap(stack) != null);
    }

    private void clearAspects(AspectList list) {
        for (Aspect a : list.getAspects()) {
            list.reduce(a, list.getAmount(a));
        }
    }
}
