package uk.hexeption.roost.block;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import uk.hexeption.roost.data.ChickenType;
import uk.hexeption.roost.tileentity.TileEntityRoost;

/**
 * RoostBlock
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 01:10 pm
 */
public class RoostBlock extends ContainerBlock {

    public static final EnumProperty CHICKEN = EnumProperty.create("chicken_type", ChickenType.class);

    public RoostBlock(Properties properties) {
        super(properties);

        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH).with(CHICKEN, ChickenType.NONE));
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        // Adds ChickenType and Facing to block state
        builder.add(BlockStateProperties.FACING, CHICKEN);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityRoost();
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return super.getContainer(state, worldIn, pos);
    }

}
