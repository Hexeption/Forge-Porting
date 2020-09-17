package uk.hexeption.roost.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import uk.hexeption.roost.block.data.RoostStateData;
import uk.hexeption.roost.data.DataChicken;
import uk.hexeption.roost.setup.ModBlocks;
import uk.hexeption.roost.setup.Registration;
import uk.hexeption.roost.tileentity.TileEntityRoost;

/**
 * RoostContainer
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 04:26 pm
 */
public class RoostContainer extends Container {

    public TileEntity tileEntity;
    private IItemHandler playerInventory;

    private RoostStateData roostStateData;

    public static RoostContainer createContainerServerSide(int windowId, World world, BlockPos blockPos, PlayerInventory playerInventory, RoostStateData roostStateData) {
        return new RoostContainer(windowId, world, blockPos, playerInventory, roostStateData);
    }

    public static RoostContainer createContainerClientSide(int windowId, World world, BlockPos blockPos, PlayerInventory playerInventory, PacketBuffer extraData) {

        return new RoostContainer(windowId, world, blockPos, playerInventory, new RoostStateData());
    }

    public RoostContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, RoostStateData stateData) {
        super(Registration.ROOST_CONTAINER.get(), windowId);
        tileEntity = world.getTileEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);
        this.roostStateData = stateData;

        trackIntArray(stateData);

        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 64, 150));
            });
        }

        addSlot(new ChickenSlot((TileEntityRoost) tileEntity, 0, 26, 20));

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotReadOnly((TileEntityRoost) tileEntity, i + 1, 80 + i * 18, 20));
        }
        layoutPlayerInventorySlots(8, 51);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, ModBlocks.ROOST.get());
    }

    public double getProgress() {
        return roostStateData.timePassed / 1000.0;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
}

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private class ChickenSlot extends Slot {

        public ChickenSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return DataChicken.isChicken(stack);
        }
    }

    private class SlotReadOnly extends Slot {

        public SlotReadOnly(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }

}
