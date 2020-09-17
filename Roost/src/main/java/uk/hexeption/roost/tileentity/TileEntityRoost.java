package uk.hexeption.roost.tileentity;

import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import uk.hexeption.roost.block.RoostBlock;
import uk.hexeption.roost.config.Config.Common;
import uk.hexeption.roost.data.ChickenType;
import uk.hexeption.roost.data.DataChicken;
import uk.hexeption.roost.setup.Registration;

/**
 * TileEntityRoost
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 03:44 pm
 */
public class TileEntityRoost extends TileEntityChickenContainer {

    private static final String CHICKEN_KEY = "Chicken";
    private static final String COMPLETE_KEY = "Complete";
    private static int CHICKEN_SLOT = 0;

    public TileEntityRoost() {
        super(Registration.ROOST_TILE_ENTITY.get());
    }

    @Override
    protected void spawnChickenDrop() {
        DataChicken chicken = getChickenData(0);
        if (chicken != null) {
            putStackInOutput(chicken.createDropStack());
        }
    }


    public DataChicken createChickenData() {
        return createChickenData(0);
    }

    public boolean putChickenIn(ItemStack newStack) {
        ItemStack oldStack = getStackInSlot(CHICKEN_SLOT);

        if (!isItemValidForSlot(CHICKEN_SLOT, newStack)) {
            return false;
        }

        if (oldStack.isEmpty()) {
            setInventorySlotContents(CHICKEN_SLOT, newStack.split(16));
            markDirty();
            world.setBlockState(pos, getBlockState().with(RoostBlock.CHICKEN, ChickenType.VANILLA), 2);
            playPutChickenInSound();
            return true;
        }

        if (oldStack.isItemEqual(newStack) && ItemStack.areItemStackTagsEqual(oldStack, newStack)) {
            int itemsAfterAdding = Math.min(oldStack.getCount() + newStack.getCount(), 16);
            int itemsToAdd = itemsAfterAdding - oldStack.getCount();
            if (itemsToAdd > 0) {
                newStack.split(itemsToAdd);
                oldStack.grow(itemsToAdd);
                markDirty();
                playPutChickenInSound();
                world.setBlockState(pos, getBlockState().with(RoostBlock.CHICKEN, ChickenType.VANILLA), 2);
                return true;
            }
        }

        return false;
    }

    public boolean pullChickenOut(PlayerEntity playerIn) {
        ItemStack spawnStack = getStackInSlot(CHICKEN_SLOT);

        if (spawnStack.isEmpty()) {
            return false;
        }

        playerIn.inventory.addItemStackToInventory(spawnStack);
        setInventorySlotContents(CHICKEN_SLOT, ItemStack.EMPTY);

        markDirty();
        playPullChickenOutSound();

        return true;
    }


    private void playPutChickenInSound() {
        getWorld().playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private void playPullChickenOutSound() {
        getWorld().playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }


    public void addInfoToTooltip(List<String> tooltip, CompoundNBT tag) {
        if (tag.contains(CHICKEN_KEY)) {
            DataChicken chicken = DataChicken.getDataFromTooltipNBT(tag.getCompound(CHICKEN_KEY));
            tooltip.add(chicken.getDisplaySummary().getString());
        }

        if (tag.contains(COMPLETE_KEY)) {
            tooltip.add(new TranslationTextComponent("container.roost.progress", formatProgress(tag.getDouble(COMPLETE_KEY))).getKey());
        }
    }

    public void storeInfoForTooltip(CompoundNBT tag) {
        DataChicken chicken = getChickenData(CHICKEN_SLOT);
        if (chicken == null) {
            return;
        }
        tag.put(CHICKEN_KEY, chicken.buildTooltipNBT());
        tag.putDouble(COMPLETE_KEY, getProgress());
    }

    @Override
    protected int getSizeChickenInventory() {
        return 1;
    }

    @Override
    protected int requiredSeedsForDrop() {
        return 0;
    }

    @Override
    protected double speedMultiplier() {
        return Common.roostSpeed.get();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("test");
    }
}
