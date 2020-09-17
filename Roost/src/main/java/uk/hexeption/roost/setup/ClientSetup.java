package uk.hexeption.roost.setup;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.client.gui.RoostScreen;

/**
 * ClientSetup
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 04:42 pm
 */
@EventBusSubscriber(modid = Roost.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registration.ROOST_CONTAINER.get(), RoostScreen::new);
    }

}
