package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

import java.util.Optional;

public class HeatCrystalBlockEntity extends MageCrystalBlockEntity {
    public HeatCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.HEAT_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.HEAT_MAGE_STONE);
    }

    public static void tick(World world, BlockPos pos, BlockState state, HeatCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                LivingEntity[] entities = Util.getLivingEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                     entity.setOnFire(true);
                     entity.damage(world.getDamageSources().inFire(), 2.0F);
                }

                ItemEntity[] items = Util.getDroppedItemEntitiesInRange(radius, world, pos);
                for (ItemEntity item : items) {
                    RecipeManager recipeManager = world.getRecipeManager();

                    // Check if the item can be smelted
                    Optional<SmeltingRecipe> smeltingRecipe = recipeManager.getFirstMatch(RecipeType.SMELTING, new SimpleInventory(item.getStack()), world);

                    if (smeltingRecipe.isPresent()) {
                        ItemStack itemStack = smeltingRecipe.get().getOutput(world.getRegistryManager());
                        itemStack.setCount(item.getStack().getCount());
                        world.spawnEntity(new ItemEntity(world, item.getX(), item.getY(), item.getZ(), itemStack ));
                        item.kill();
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
