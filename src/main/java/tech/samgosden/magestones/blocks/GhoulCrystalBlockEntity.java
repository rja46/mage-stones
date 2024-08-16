package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import tech.samgosden.magestones.item.ModItems;
import tech.samgosden.magestones.util.Util;

import java.util.Random;

public class GhoulCrystalBlockEntity extends MageCrystalBlockEntity {
    public GhoulCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.GHOUL_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.GHOUL_MAGE_STONE);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GhoulCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                int radius = blockEntity.effectRadius;
                LivingEntity[] entities = Util.getLivingEntitiesInRange(radius, world, pos);
                for (LivingEntity entity : entities) {
                    if (!entity.isUndead()){
                    entity.damage(world.getDamageSources().magic(), 1.0F);
                    }
                }
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(GhoulCrystalBlock.ISACTIVE, false).with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
            else {
                world.setBlockState(pos, state.with(MageCrystalBlock.TICKSLEFT, blockEntity.ticksLeft));
            }
        }

    }

    public void onMobDeath(LivingEntity entity) {
        // Handle mob death near this block entity
        if (ticksLeft > 0) {
            if (!(entity.isUndead()) && world != null) {
                Random random = new Random();
                Entity undead;
                if (entity instanceof VillagerEntity) {
                    undead = new ZombieEntity(EntityType.ZOMBIE_VILLAGER, entity.getEntityWorld());
                }
                else if (entity instanceof PiglinEntity) {
                    undead = new ZombieEntity(EntityType.ZOMBIFIED_PIGLIN, entity.getEntityWorld());
                }
                else {
                    if (random.nextBoolean()) {
                        if (world.getBiome(entity.getBlockPos()).matchesKey(BiomeKeys.DESERT)) {
                            undead = new ZombieEntity(EntityType.HUSK, entity.getEntityWorld());
                        } else {
                            undead = new ZombieEntity(EntityType.ZOMBIE, entity.getEntityWorld());
                        }
                    } else {
                        if (world.getBiome(entity.getBlockPos()).matchesKey(BiomeKeys.SNOWY_PLAINS)) {
                            undead = new StrayEntity(EntityType.STRAY, entity.getEntityWorld()) {
                            };
                        } else if (world.getBiome(entity.getBlockPos()).matchesKey(BiomeKeys.ICE_SPIKES)) {
                            undead = new StrayEntity(EntityType.STRAY, entity.getEntityWorld());
                        } else if (world.getBiome(entity.getBlockPos()).matchesKey(BiomeKeys.FROZEN_RIVER)) {
                            undead = new StrayEntity(EntityType.STRAY, entity.getEntityWorld());
                        } else {
                            undead = new SkeletonEntity(EntityType.SKELETON, entity.getEntityWorld());
                        }
                    }
                }

                //0.1 is added to Y to stop it spawning inside a block.
                undead.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
                world.spawnEntity(undead);
            }
        }
    }
}
