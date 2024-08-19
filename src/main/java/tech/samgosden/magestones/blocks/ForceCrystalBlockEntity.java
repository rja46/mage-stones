package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

public class ForceCrystalBlockEntity extends MageCrystalBlockEntity {
    public ForceCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.FORCE_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.FORCE_MAGE_STONE);
        intensity = 10;
    }

    public float getPointIntensity(float distance) {
        float pointIntensity = 0;
        if (distance > 0.001){
            pointIntensity = intensity/distance/distance;
        }
        return pointIntensity;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ForceCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                if (blockEntity.notDisabledByConnector) {
                    LivingEntity[] entities = Util.getLivingEntitiesInRange((int)blockEntity.intensity, world, pos);
                    for (LivingEntity entity : entities) {
                        Vec3d relativeVector = entity.getPos().subtract(Vec3d.ofCenter(pos));
                        Vec3d pushForce = relativeVector.normalize().multiply((blockEntity.getPointIntensity((float)relativeVector.length()))*0.1);
                        entity.addVelocity(pushForce.x, pushForce.y, pushForce.z);
                        entity.velocityModified = true;
                    }
                }
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false).with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
            else {
                world.setBlockState(pos, state.with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
        }

    }
}
