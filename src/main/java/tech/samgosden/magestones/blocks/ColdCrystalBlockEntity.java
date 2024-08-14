package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(ColdCrystalBlock.ISACTIVE, false));
            }
            LivingEntity[] entities = world.getEntitiesByClass(LivingEntity.class, new Box(pos.getX() + blockEntity.effectRadius, pos.getY() + blockEntity.effectRadius, pos.getZ() + blockEntity.effectRadius,
                    pos.getX() - blockEntity.effectRadius, pos.getY() - blockEntity.effectRadius, pos.getZ() - blockEntity.effectRadius), Entity::isAlive).toArray(new LivingEntity[0]);
            for (LivingEntity entity : entities) {
                Vec3d slowVector = new Vec3d(0, 0, 0); // Adjust the values as needed
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1));
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
