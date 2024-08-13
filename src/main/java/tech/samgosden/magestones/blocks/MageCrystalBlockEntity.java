package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MageCrystalBlockEntity extends BlockEntity implements BlockEntityTicker {
    public MageCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntites.MAGE_CRYSTAL_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

    }
}
