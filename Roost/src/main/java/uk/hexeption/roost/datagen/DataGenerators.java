package uk.hexeption.roost.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * DataGenerators
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 04:20 am
 */
@EventBusSubscriber(bus = Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new RecipesGenerator(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStateGenerator(generator, event.getExistingFileHelper()));
            generator.addProvider(new ItemGenerator(generator, event.getExistingFileHelper()));
        }
    }
}
