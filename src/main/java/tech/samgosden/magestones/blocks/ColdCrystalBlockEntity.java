package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.BlockTags;
import tech.samgosden.magestones.MageStones;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColdCrystalBlockEntity extends MageCrystalBlockEntity {
    public ColdCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.COLD_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.COLD_MAGE_STONE);
        intensity = 2;
    }

    @Override
    public boolean isReceivingEnergy(){
        List<BlockPos> adjacentBlocks = getAdjacentBlocks(world, pos);
        for (BlockPos adjacentBlock : adjacentBlocks) {
            if (world.getBlockState(adjacentBlock).getBlock() == Blocks.ICE) {
                return true;
            }
        }
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ColdCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                if (blockEntity.notDisabledByConnector) {
                    int radius = blockEntity.effectRadius;
                    int radiusSquared = radius * radius;


                    Set<BlockPos> waterPositions = new HashSet<>();
                    Set<BlockPos> surfaceBlocks = new HashSet<>();
                    Set<BlockPos> nonWaterBlocks = new HashSet<>();

                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                if (x * x + y * y + z * z <= radiusSquared) {
                                    BlockPos currentPos = pos.add(x, y, z);
                                    BlockState blockState = world.getBlockState(currentPos);
                                    if (blockState.isOf(Blocks.WATER)) {
                                        waterPositions.add(currentPos);
                                    }
                                    else if (!blockState.isOf(Blocks.AIR)){
                                        nonWaterBlocks.add(currentPos);
                                    }
                                    //These conditions may need updating, as problems occur when played on an illegal block,
                                    //which this code may not prevent.
                                    else if (!blockState.isTransparent(world, currentPos)
                                            && world.getBlockState(currentPos).getBlock() != Blocks.AIR
                                            && world.getBlockState(currentPos.up()).getBlock() == Blocks.AIR
                                            ) {
                                        surfaceBlocks.add(currentPos.up());
                                    }

                                }
                            }
                        }
                    }

                    if  (blockEntity.intensity >= 0) {
                        LivingEntity[] entities = Util.getLivingEntitiesInRange(radius, world, pos);
                        for (LivingEntity entity : entities) {
                            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20));
                        }
                        for (BlockPos surfacePos : surfaceBlocks) {
                            if (!world.getBlockState(surfacePos.down()).isIn(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
                                world.setBlockState(surfacePos, Blocks.SNOW.getDefaultState());
                            }
                        }
                    }
                    if (blockEntity.intensity >= 1) {
                        waterPositions.forEach(currentPos -> world.setBlockState(currentPos, Blocks.ICE.getDefaultState()));
                    }
                    if (blockEntity.intensity >= 2) {
                        for (BlockPos nonWaterBlock : nonWaterBlocks) {
                            if (world.getBlockState(nonWaterBlock).getBlock() != Blocks.ICE
                                    && !world.getBlockState(nonWaterBlock).isIn(MageStones.NOT_AFFECTED_BY_CRYSTALS)) {
                                world.setBlockState(nonWaterBlock, Blocks.BLUE_ICE.getDefaultState());
                            }
                        }
                    }
                }
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(ColdCrystalBlock.ISACTIVE, false).with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
            else {
                world.setBlockState(pos, state.with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
        }
    }
}
