package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.MageStones;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

import java.util.*;

public class HeatCrystalBlockEntity extends MageCrystalBlockEntity {
    public HeatCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.HEAT_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.HEAT_MAGE_STONE);
    }

    @Override
    public boolean isReceivingEnergy(){
        List<BlockPos> adjacentBlocks = getAdjacentBlocks(world, pos);
        for (BlockPos adjacentBlock : adjacentBlocks) {
            if (world.getBlockState(adjacentBlock).getBlock() == Blocks.LAVA
                    || world.getBlockState(adjacentBlock).getBlock() == Blocks.MAGMA_BLOCK){
                return true;
            }
        }
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, HeatCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                if (blockEntity.notDisabledByConnector) {
                    int radius = blockEntity.effectRadius;
                    int radiusSquared = radius * radius;
                    if (blockEntity.intensity >= 0) {
                        LivingEntity[] entities = Util.getLivingEntitiesInRange(radius, world, pos);
                        for (LivingEntity entity : entities) {
                            entity.setOnFire(true);
                            entity.damage(world.getDamageSources().inFire(), 2.0F);
                        }
                    }

                    if (blockEntity.intensity >= 1) {
                        ItemEntity[] items = Util.getDroppedItemEntitiesInRange(radius, world, pos);
                        for (ItemEntity item : items) {
                            RecipeManager recipeManager = world.getRecipeManager();

                            // Check if the item can be smelted
                            Optional<SmeltingRecipe> smeltingRecipe = recipeManager.getFirstMatch(RecipeType.SMELTING, new SimpleInventory(item.getStack()), world);

                            if (smeltingRecipe.isPresent()) {
                                ItemStack itemStack = smeltingRecipe.get().getOutput(world.getRegistryManager());
                                itemStack.setCount(item.getStack().getCount());
                                world.spawnEntity(new ItemEntity(world, item.getX(), item.getY(), item.getZ(), itemStack));
                                item.kill();
                            }
                        }
                    }

                    if (blockEntity.intensity >= 2) {
                        Set<BlockPos> waterPositions = new HashSet<>();
                        Set<BlockPos> nonAirBlocks = new HashSet<>();
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    if (x * x + y * y + z * z <= radiusSquared) {
                                        BlockPos currentPos = pos.add(x, y, z);
                                        BlockState blockState = world.getBlockState(currentPos);
                                        if (blockState.isOf(Blocks.WATER)) {
                                            waterPositions.add(currentPos);
                                        }
                                        else if (!blockState.isAir()) {
                                            if (!blockState.isIn(MageStones.NOT_AFFECTED_BY_CRYSTALS)) {
                                                nonAirBlocks.add(currentPos);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        waterPositions.forEach(currentPos -> world.setBlockState(currentPos, Blocks.LAVA.getDefaultState()));
                        nonAirBlocks.forEach(currentPos -> world.setBlockState(currentPos, Blocks.MAGMA_BLOCK.getDefaultState()));
                    }
                }

                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(HeatCrystalBlock.ISACTIVE, false));
            }
            else{
                world.setBlockState(pos, state.with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft).with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
        }
    }
}
