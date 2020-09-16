package uk.hexeption.roost.setup;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.item.ChickenCatcher;
import uk.hexeption.roost.item.ChickenItem;

/**
 * ModItems
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 05:51 pm
 */
public class ModItems {

    public static final RegistryObject<Item> CHICKEN_CATCHER = Registration.ITEMS.register("chicken_catcher", () -> new ChickenCatcher(new Properties().group(Roost.TAB).maxDamage(64)));
    public static final RegistryObject<Item> CHICKEN_ITEM = Registration.ITEMS.register("chicken", () -> new ChickenItem(new Properties().group(Roost.TAB)));

    public static void register() {

    }
}
