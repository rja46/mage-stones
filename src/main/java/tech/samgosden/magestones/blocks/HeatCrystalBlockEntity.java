package tech.samgosden.magestones.blocks;

import com.mojang.datafixers.util.Either;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.util.Util;

import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class HeatCrystalBlockEntity extends MageCrystalBlockEntity {
    public HeatCrystalBlockEntity(BlockPos pos, BlockState state, Item drop) {
        super(BlockEntities.HEAT_CRYSTAL_BLOCK_ENTITY, pos, state, drop);
    }

    public static void tick(World world, BlockPos pos, BlockState state, HeatCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                int radiusSquared = radius * radius;
                LivingEntity[] entities = Util.getEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                     entity.setOnFire(true);
                     //entity.damage(new DamageSource());
                }

//                Set<BlockPos> waterPositions = new HashSet<>();
//
//                for (int x = -radius; x <= radius; x++) {
//                    for (int y = -radius; y <= radius; y++) {
//                        for (int z = -radius; z <= radius; z++) {
//                            if (x * x + y * y + z * z <= radiusSquared) {
//                                BlockPos currentPos = pos.add(x, y, z);
//                                BlockState blockState = world.getBlockState(currentPos);
//                                if (blockState.isOf(Blocks.WATER)) {
//                                    waterPositions.add(currentPos);
//                                }
//                            }
//                        }
//                    }
//                }
//                waterPositions.forEach(currentPos -> world.setBlockState(currentPos, Blocks.ICE.getDefaultState()));
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(HeatCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
