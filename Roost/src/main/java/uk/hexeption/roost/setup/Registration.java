package uk.hexeption.roost.setup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.tileentity.TileEntityRoost;

/**
 * Registration
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 16/09/2020 - 05:48 pm
 */
public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Roost.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Roost.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Roost.MODID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);

        ModItems.register();
        ModBlocks.register();
    }

    public static final RegistryObject<TileEntityType<TileEntityRoost>> ROOST_TILE_ENTITY = TILES.register("roost", () -> TileEntityType.Builder.create(TileEntityRoost::new, ModBlocks.ROOST.get()).build(null));

}
