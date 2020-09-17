package uk.hexeption.roost.tileentity;

import java.text.DecimalFormat;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import uk.hexeption.roost.block.data.RoostStateData;
import uk.hexeption.roost.data.DataChicken;
import uk.hexeption.roost.gui.RoostContainer;

/**
 * TileEntityChickenContainer
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 03:43 pm
 */
public abstract class TileEntityChickenContainer extends TileEntity implements ISidedInventory, INamedContainerProvider, ITickableTileEntity {

    private static final DecimalFormat FORMATTER = new DecimalFormat("0.0%");

    public NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
    private boolean mightNeedToUpdateChickenInfo = true;
    private boolean skipNextTimerReset = false;
    private int timeUntilNextDrop = 0;
    private int timeElapsed = 0;

    private DataChicken[] chickenData = new DataChicken[getSizeChickenInventory()];
    private boolean fullOfChickens = false;
    private boolean fullOfSeeds = false;

    private final RoostStateData roostStateData = new RoostStateData();

    public TileEntityChickenContainer(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }


    @Override
    public void tick() {
        if (!getWorld().isRemote) {
            updateChickenInfoIfNeeded();
            updateTimerIfNeeded();
            spawnChickenDropIfNeeded();
            updateProgress();
            skipNextTimerReset = false;
        }
    }

    public void willNeedToUpdateChickenInfo() {
        mightNeedToUpdateChickenInfo = true;
    }

    private void updateChickenInfoIfNeeded() {
        if (!mightNeedToUpdateChickenInfo) {
            return;
        }

        if (fullOfChickens != isFullOfChickens()) {
            fullOfChickens = !fullOfChickens;
            notifyBlockUpdate();
        }

        if (fullOfSeeds != isFullOfSeeds()) {
            fullOfSeeds = !fullOfSeeds;
            notifyBlockUpdate();
        }

        mightNeedToUpdateChickenInfo = false;
    }

    private void updateChickenInfoIfNeededForSlot(int slot) {
        DataChicken oldChicken = chickenData[slot];
        DataChicken newChicken = createChickenData(slot);

        boolean wasCreated = oldChicken == null && newChicken != null;
        boolean wasDeleted = oldChicken != null && newChicken == null;
        boolean wasChanged = oldChicken != null && newChicken != null && !oldChicken.isEqual(newChicken);

        if (wasCreated || wasChanged || wasDeleted) {
            chickenData[slot] = newChicken;
            if (!skipNextTimerReset) {
                resetTimer();
            }
        }
        if (wasChanged) {
            notifyBlockUpdate();
        }
    }

    protected DataChicken getChickenData(int slot) {
        if (slot >= chickenData.length || slot < 0) {
            return null;
        }
        return chickenData[slot];
    }

    protected DataChicken createChickenData(int slot) {
        return DataChicken.getDataFromStack(getStackInSlot(slot));
    }

    private void updateTimerIfNeeded() {
        if (fullOfChickens && fullOfSeeds && !outputIsFull()) {
            timeElapsed += getTimeElapsed();
            markDirty();
        }
    }

    private void updateProgress() {
        roostStateData.set(0, timeUntilNextDrop == 0 ? 0 : (timeElapsed * 1000 / timeUntilNextDrop));
    }

    private int getTimeElapsed() {
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < chickenData.length; i++) {
            if (chickenData[i] == null) {
                return 0;
            }
            time = Math.min(time, chickenData[i].getAddedTime(getStackInSlot(i)));
        }
        return time;
    }

    public double getProgress() {
        return roostStateData.timePassed / 1000.0;
    }

    public String getFormattedProgress() {
        return formatProgress(getProgress());
    }

    public String formatProgress(double progress) {
        return FORMATTER.format(progress);
    }

    private void spawnChickenDropIfNeeded() {
        if (fullOfChickens && fullOfSeeds && (timeElapsed >= timeUntilNextDrop)) {
            if (timeUntilNextDrop > 0) {
                decrStackSize(getSizeChickenInventory(), requiredSeedsForDrop());
                spawnChickenDrop();
            }
            resetTimer();
        }
    }

    private void resetTimer() {
        timeElapsed = 0;
        timeUntilNextDrop = 0;
        for (int i = 0; i < chickenData.length; i++) {
            if (chickenData[i] != null) {
                timeUntilNextDrop = Math.max(timeUntilNextDrop, chickenData[i].getLayTime());
                timeUntilNextDrop /= speedMultiplier();
            }
        }
        markDirty();
    }


    public boolean isFullOfChickens() {
        for (int i = 0; i < chickenData.length; i++) {
            updateChickenInfoIfNeededForSlot(i);
            if (chickenData[i] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isFullOfSeeds() {
        int needed = requiredSeedsForDrop();
        if (needed == 0) {
            return true;
        }
        ItemStack stack = getStackInSlot(getSizeChickenInventory());
        return stack.getCount() >= needed;
    }

    private boolean outputIsFull() {
        int max = getSizeInventory();

        for (int i = getOutputStackIndex(); i < max; i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack.getCount() < stack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    private int getOutputStackIndex() {
        if (requiredSeedsForDrop() > 0) {
            return getSizeChickenInventory() + 1;
        }
        return getSizeChickenInventory();
    }

    protected ItemStack putStackInOutput(ItemStack stack) {
        int max = getSizeInventory();

        for (int i = getOutputStackIndex(); i < max && !stack.isEmpty(); i++) {
            stack = insertStack(stack, i);
        }

        markDirty();

        return stack;
    }

    private ItemStack insertStack(ItemStack stack, int index) {
        int max = Math.min(stack.getMaxStackSize(), getInventoryStackLimit());

        ItemStack outputStack = getStackInSlot(index);
        if (outputStack.isEmpty()) {
            if (stack.getCount() >= max) {
                setInventorySlotContents(index, stack);
                stack = ItemStack.EMPTY;
            } else {
                setInventorySlotContents(index, stack.split(max));
            }
        } else if (canCombine(outputStack, stack)) {
            if (outputStack.getCount() < max) {
                int itemsToMove = Math.min(stack.getCount(), max - outputStack.getCount());
                stack.shrink(itemsToMove);
                outputStack.grow(itemsToMove);
            }
        }

        return stack;
    }

    private boolean canCombine(ItemStack a, ItemStack b) {
        if (a.getItem() != b.getItem()) {
            return false;
        }
        if (a.getDamage() != b.getDamage()) {
            return false;
        }
        if (a.getCount() > a.getMaxStackSize()) {
            return false;
        }
        return ItemStack.areItemStackTagsEqual(a, b);
    }

    private void notifyBlockUpdate() {
        final BlockState state = getWorld().getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, state, state, 2);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int count = getSizeInventory();
        int[] itemSlots = new int[count];

        for (int i = 0; i < count; i++) {
            itemSlots[i] = i;
        }

        return itemSlots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index >= getOutputStackIndex();
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < getOutputStackIndex()) {
            willNeedToUpdateChickenInfo();
        }
        return ItemStackHelper.getAndSplit(inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < getOutputStackIndex()) {
            willNeedToUpdateChickenInfo();
        }
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.set(index, stack);

        if (stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        if (index < getOutputStackIndex()) {
            willNeedToUpdateChickenInfo();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (getWorld().getTileEntity(pos) != this) {
            return false;
        } else {
            return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    protected abstract void spawnChickenDrop();

    protected abstract int getSizeChickenInventory();

    protected abstract int requiredSeedsForDrop();

    protected abstract double speedMultiplier();

    @Override
    public void func_230337_a_(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.func_230337_a_(p_230337_1_, p_230337_2_);
        clear();
        ItemStackHelper.loadAllItems(p_230337_2_, inventory);
        timeUntilNextDrop = p_230337_2_.getInt("TimeUntilNextChild");
        timeElapsed = p_230337_2_.getInt("TimeElapsed");
        roostStateData.readFromNBT(p_230337_2_);

        skipNextTimerReset = true;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, inventory);
        compound.putInt("TimeUntilNextChild", timeUntilNextDrop);
        compound.putInt("TimeElapsed", timeElapsed);
        roostStateData.putIntoNBT(compound);
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT updateTagDescribingTileEntityState = getUpdateTag();
        final int METADATA = 42; // arbitrary.
        return new SUpdateTileEntityPacket(this.pos, METADATA, updateTagDescribingTileEntityState);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT updateTagDescribingTileEntityState = pkt.getNbtCompound();
        handleUpdateTag(world.getBlockState(pkt.getPos()), updateTagDescribingTileEntityState);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = new CompoundNBT();
        write(compoundNBT);
        return compoundNBT;

    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return RoostContainer.createContainerServerSide(p_createMenu_1_, world, pos, p_createMenu_2_, roostStateData);
    }
}
