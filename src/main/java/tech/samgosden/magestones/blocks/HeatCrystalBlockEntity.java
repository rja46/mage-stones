package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

public class HeatCrystalBlockEntity extends MageCrystalBlockEntity {
    public HeatCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.HEAT_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.HEAT_MAGE_STONE);
    }

    public static void tick(World world, BlockPos pos, BlockState state, HeatCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                int radiusSquared = radius * radius;
                LivingEntity[] entities = Util.getEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                     entity.setOnFire(true);
                     entity.damage(world.getDamageSources().inFire(), 2.0F);
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
