package uk.hexeption.roost.data;

import net.minecraft.util.IStringSerializable;

/**
 * ChickenType
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 02:18 pm
 */
public enum ChickenType implements IStringSerializable {
    VANILLA("vanilla"),
    NONE("na");

    private String name;

    ChickenType(String name) {
        this.name = name;
    }

    @Override
    public String func_176610_l() {
        return name;
    }
}
