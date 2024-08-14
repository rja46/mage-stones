package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightCrystalBlockEntity extends BlockEntity {
    private static int ticksLeft;
    public LightCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntites.LIGHT_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
        System.out.println("LightCrystalBlockEntity");
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient) {
            if (ticksLeft > 0) {
                ticksLeft -= 1;
            }
            if (ticksLeft == 0) {
                world.setBlockState(pos, state.with(LightCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
