package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ColdCrystalBlockEntity extends BlockEntity {
    private static int ticksLeft;
    private static int effectRadius = 5;
    public ColdCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntites.COLD_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient) {
            if (ticksLeft > 0) {
                ticksLeft -= 1;
            }
            if (ticksLeft == 0) {
                world.setBlockState(pos, state.with(ColdCrystalBlock.ISACTIVE, false));
            }
            PlayerEntity[] players = world.getPlayers().toArray(new PlayerEntity[0]);
            for (PlayerEntity player : players) {
                if (player.getPos().x < pos.getX() + effectRadius && player.getPos().x > pos.getX() - effectRadius
                && player.getPos().y < pos.getY() + effectRadius && player.getPos().y > pos.getY() - effectRadius
                        && player.getPos().z < pos.getZ() + effectRadius && player.getPos().z > pos.getZ() - effectRadius) {
                    player.kill();
                }
            }
        }
    }
}
