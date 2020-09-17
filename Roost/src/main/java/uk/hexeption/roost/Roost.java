package uk.hexeption.roost;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.hexeption.roost.config.Config;
import uk.hexeption.roost.setup.ModItems;
import uk.hexeption.roost.setup.Registration;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Roost.MODID)
public class Roost {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "roost";
    public static final String NAME = "Roost";
    public static final String VERSION = "@VERSION@";

    public static final ItemGroup TAB = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CHICKEN_CATCHER.get());
        }
    };

    public Roost() {
        Registration.register();
        Config.init();
    }
}
