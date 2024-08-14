package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ForceCrystalBlockEntity extends BlockEntity {
    private static int ticksLeft;
    private int effectRadius = 5;
    public ForceCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.FORCE_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
    }


    public static void tick(World world, BlockPos pos, BlockState state, ForceCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                int radiusSquared = radius * radius;
                LivingEntity[] entities = world.getEntitiesByClass(LivingEntity.class, new Box(
                                pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                                pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), Entity::isAlive)
                        .stream()
                        .filter(entity -> entity.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= radiusSquared)
                        .toArray(LivingEntity[]::new);
                for (LivingEntity entity : entities) {
                        Entity player = entity;
                        Vec3d pushDirection = player.getPos().subtract(Vec3d.ofCenter(pos)).normalize().multiply(1.5);
                        player.addVelocity(pushDirection.x, pushDirection.y, pushDirection.z);
                        player.velocityModified = true;
                }


                ticksLeft -= 1;

            }
            if (ticksLeft == 0) {
                world.setBlockState(pos, state.with(ForceCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
