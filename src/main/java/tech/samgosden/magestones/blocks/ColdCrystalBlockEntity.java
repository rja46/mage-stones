package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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
            LivingEntity[] entities = world.getEntitiesByClass(LivingEntity.class, new Box(pos.getX() + effectRadius, pos.getY() +  effectRadius, pos.getZ() + effectRadius,
                    pos.getX() - effectRadius, pos.getY() - effectRadius, pos.getZ() - effectRadius), Entity::isAlive).toArray(new LivingEntity[0]);
            for (LivingEntity entity : entities) {
                    entity.kill();
                }
            }
        }
    }
