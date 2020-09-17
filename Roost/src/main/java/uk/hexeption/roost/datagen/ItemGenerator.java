package uk.hexeption.roost.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.setup.ModBlocks;

/**
 * ItemGenerator
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 02:38 pm
 */
public class ItemGenerator extends ItemModelProvider {

    public ItemGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Roost.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModBlocks.ROOST.get().asItem().getRegistryName().getPath(), new ResourceLocation(Roost.MODID, "block/roost_block"));
    }
}
