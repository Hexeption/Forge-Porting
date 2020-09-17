package uk.hexeption.roost.setup;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.gui.RoostContainer;
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
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Roost.MODID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);
        CONTAINERS.register(modEventBus);

        ModItems.register();
        ModBlocks.register();
    }

    // Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityRoost>> ROOST_TILE_ENTITY = TILES
        .register("roost", () -> TileEntityType.Builder.create(TileEntityRoost::new, ModBlocks.ROOST.get()).build(null));

    // Containers
    public static final RegistryObject<ContainerType<RoostContainer>> ROOST_CONTAINER = CONTAINERS.register("roost_gui", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new RoostContainer(windowId, world, pos, inv, inv.player);
    }));
}
