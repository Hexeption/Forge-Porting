package uk.hexeption.roost;

import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.hexeption.roost.setup.ModItems;
import uk.hexeption.roost.setup.Registration;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("roost")
public class Roost {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "roost";
    public static final String NAME = "Roost";
    public static final String VERSION = "@VERSION@";

    public static final ItemGroup TAB = new ItemGroup("roost") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CHICKEN_CATCHER.get());
        }
    };

    public Roost() {
        Registration.register();
    }
}
