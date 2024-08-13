package tech.samgosden.magestones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChargerBlock extends Block {
    public ChargerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            // Trigger your event here
            if (world.getBlockState(pos.up()).getBlock() == ModBlocks.DULL_MAGE_STONE_CRYSTAL) {
                world.setBlockState(pos.up(), ModBlocks.CHARGED_MAGE_STONE_CRYSTAL.getDefaultState());
            }
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }
}
