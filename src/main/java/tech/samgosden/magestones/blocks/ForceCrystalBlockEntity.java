package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tech.samgosden.magestones.util.Util;

public class ForceCrystalBlockEntity extends MageCrystalBlockEntity {
    public ForceCrystalBlockEntity(BlockPos pos, BlockState state, Item drop) {
        super(BlockEntities.FORCE_CRYSTAL_BLOCK_ENTITY, pos, state, drop);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ForceCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                LivingEntity[] entities = Util.getEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                        Vec3d pushDirection = entity.getPos().subtract(Vec3d.ofCenter(pos)).normalize().multiply(1.5);
                        entity.addVelocity(pushDirection.x, pushDirection.y, pushDirection.z);
                        entity.velocityModified = true;
                }
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false));
            }
        }

    }
}
