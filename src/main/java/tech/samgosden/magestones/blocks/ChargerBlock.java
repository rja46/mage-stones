package tech.samgosden.magestones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChargerBlock extends Block {
    boolean isPowered = false;
    boolean showAsPowered = false;

    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public ChargerBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(ACTIVE, false));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)){
            world.setBlockState(pos, state.with(ACTIVE, true));
        }
        else{
            world.setBlockState(pos, state.with(ACTIVE, false));
        }

        if (world.isReceivingRedstonePower(pos) && !isPowered) {
            isPowered = true;
            // Trigger your event here
//            if (world.getBlockState(pos.up()).getBlock() == ModBlocks.DULL_MAGE_STONE_CRYSTAL) {
//                world.setBlockState(pos.up(), ModBlocks.CHARGED_MAGE_STONE_CRYSTAL.getDefaultState());
//                isPowered = false;
//            }
        }
        else {

            isPowered = false;
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }
}
