package uk.hexeption.roost.datagen;

import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import uk.hexeption.roost.Roost;
import uk.hexeption.roost.setup.ModBlocks;

/**
 * BlockStateGenerator
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 01:38 pm
 */
public class BlockStateGenerator extends BlockStateProvider {


    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Roost.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        roostBlock();
    }

    private void roostBlock() {
        ResourceLocation txt = new ResourceLocation(Roost.MODID, "block/roost");
        BlockModelBuilder modelFirstblock = models().cube("roost_block", txt, txt, txt, txt, txt, txt);
        orientedBlock(ModBlocks.ROOST.get(), state -> modelFirstblock);
    }

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
            .forAllStates(state -> {
                Direction dir = state.get(BlockStateProperties.FACING);
                return ConfiguredModel.builder()
                    .modelFile(modelFunc.apply(state))
                    .rotationX(dir.getAxis() == Direction.Axis.Y ? dir.getAxisDirection().getOffset() * -90 : 0)
                    .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                    .build();
            });
    }
}
