package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MageCrystalBlockEntity extends BlockEntity {
    private static int ticksLeft;
    private int effectRadius = 5;

    public MageCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.MAGE_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

    }
}
