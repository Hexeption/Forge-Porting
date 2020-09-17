package uk.hexeption.roost.tileentity;

import uk.hexeption.roost.setup.Registration;

/**
 * TileEntityRoost
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 03:44 pm
 */
public class TileEntityRoost extends TileEntityChickenContainer {

    public TileEntityRoost() {
        super(Registration.ROOST_TILE_ENTITY.get());
    }

    @Override
    protected void spawnChickenDrop() {

    }

    @Override
    protected int getSizeChickenInventory() {
        return 0;
    }

    @Override
    protected int requiredSeedsForDrop() {
        return 0;
    }

    @Override
    protected double speedMultiplier() {
        return 0;
    }
}
