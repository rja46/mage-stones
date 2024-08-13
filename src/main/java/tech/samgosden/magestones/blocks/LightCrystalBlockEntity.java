package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.state.State;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightCrystalBlockEntity extends BlockEntity {
    public LightCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntites.LIGHT_CRYSTAL_BLOCK_ENTITY, pos, state);
        System.out.println("LightCrystalBlockEntity");
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient) {


            int ticksLeft = state.get(LightCrystalBlock.TICKSLEFT);
            if (ticksLeft > 0) {
                world.setBlockState(pos, state.with(LightCrystalBlock.TICKSLEFT, ticksLeft-1));
            }
            if (ticksLeft == 0) {
                world.setBlockState(pos, state.with(LightCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
