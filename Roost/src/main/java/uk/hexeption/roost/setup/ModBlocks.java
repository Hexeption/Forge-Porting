package uk.hexeption.roost.setup;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.block.RoostBlock;

/**
 * ModBlocks
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 06:48 pm
 */
public class ModBlocks {

    public static final RegistryObject<Block> ROOST = register("roost_block", () -> new RoostBlock(Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 5.0f).sound(SoundType.WOOD)));

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
