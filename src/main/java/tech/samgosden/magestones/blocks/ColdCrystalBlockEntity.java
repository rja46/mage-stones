package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class ColdCrystalBlockEntity extends MageCrystalBlockEntity {
    public ColdCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.COLD_CRYSTAL_BLOCK_ENTITY, pos, state);
        ticksLeft = 100;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ColdCrystalBlockEntity blockEntity) {
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
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20));
                }

                Set<BlockPos> waterPositions = new HashSet<>();

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (x * x + y * y + z * z <= radiusSquared) {
                                BlockPos currentPos = pos.add(x, y, z);
                                BlockState blockState = world.getBlockState(currentPos);
                                if (blockState.isOf(Blocks.WATER)) {
                                    waterPositions.add(currentPos);
                                }
                            }
                        }
                    }
                }
                waterPositions.forEach(currentPos -> world.setBlockState(currentPos, Blocks.ICE.getDefaultState()));
                ticksLeft -= 1;
            }
            if (ticksLeft == 0) {
                world.setBlockState(pos, state.with(ColdCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
