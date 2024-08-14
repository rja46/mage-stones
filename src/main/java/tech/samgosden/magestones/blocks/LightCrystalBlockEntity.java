package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightCrystalBlockEntity extends MageCrystalBlockEntity {
    public LightCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LIGHT_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
    }

    public static void tick(World world, BlockPos pos, BlockState state, LightCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(LightCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
