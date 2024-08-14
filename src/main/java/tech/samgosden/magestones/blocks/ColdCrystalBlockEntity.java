package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ColdCrystalBlockEntity extends BlockEntity {
    private int ticksLeft;
    private int effectRadius = 5;

    public ColdCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.COLD_CRYSTAL_BLOCK_ENTITY, pos, state);
        this.ticksLeft = 100;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ColdCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
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
                List<BlockPos> positionsToUpdate = new ArrayList<>();


                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (x * x + y * y + z * z <= radiusSquared) {
                                BlockPos currentPos = pos.add(x, y, z);
                                BlockState blockState = world.getBlockState(currentPos);
                                if (blockState.isOf(Blocks.WATER)) {
                                    positionsToUpdate.add(currentPos);
                                }
                            }
                        }
                    }
                }
                positionsToUpdate.forEach(currentPos -> world.setBlockState(currentPos, Blocks.ICE.getDefaultState()));
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(ColdCrystalBlock.ISACTIVE, false));
            }


        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("EffectRadius", effectRadius);
        nbt.putInt("TicksLeft", ticksLeft);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        effectRadius = nbt.getInt("EffectRadius");
        ticksLeft = nbt.getInt("TicksLeft");
    }
}
