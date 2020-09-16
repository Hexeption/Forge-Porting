package uk.hexeption.roost.setup;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import uk.hexeption.roost.Roost;

/**
 * ModBlocks
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 06:48 pm
 */
public class ModBlocks {

    public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () -> new Block(Properties.create(Material.WOOD)));

    public static void register() {

    }

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(Roost.TAB)));
        return ret;
    }

}
