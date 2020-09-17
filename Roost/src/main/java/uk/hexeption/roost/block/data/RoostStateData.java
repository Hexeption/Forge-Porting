package uk.hexeption.roost.block.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

/**
 * RoostStateData
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 08:16 pm
 */
public class RoostStateData implements IIntArray {

    public int timePassed;

    public void putIntoNBT(CompoundNBT nbtTagCompound) {
        nbtTagCompound.putInt("timePassed", timePassed);
    }

    public void readFromNBT(CompoundNBT nbtTagCompound) {
        timePassed = nbtTagCompound.getInt("timePassed");
    }

    private final int TIME_INDEX = 0;
    private final int END_OF_DATA_INDEX_PLUS_ONE = TIME_INDEX + 1;

    @Override
    public int get(int index) {
        validateIndex(index);
        if (index == TIME_INDEX) {
            return timePassed;
        } else {
            return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        validateIndex(index);
        if (index == TIME_INDEX) {
            timePassed = value;
        }
    }

    @Override
    public int size() {
        return END_OF_DATA_INDEX_PLUS_ONE;
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds:" + index);
        }
    }
}
