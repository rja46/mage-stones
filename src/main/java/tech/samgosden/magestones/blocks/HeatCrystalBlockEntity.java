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
                LivingEntity[] entities = Util.getLivingEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                     entity.setOnFire(true);
                     entity.damage(world.getDamageSources().inFire(), 2.0F);
                }

                ItemEntity[] items = Util.getDroppedItemEntitiesInRange(radiusSquared, world, pos);



                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(HeatCrystalBlock.ISACTIVE, false));
            }
        }
    }
}
