package tech.samgosden.magestones;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import tech.samgosden.magestones.blocks.GhoulCrystalBlockEntity;
import tech.samgosden.magestones.blocks.ModBlocks;

public class MobDeathListener {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof LivingEntity) {
                ServerWorld world = (ServerWorld) entity.getWorld();
                BlockPos entityPos = entity.getBlockPos();


                int radius = 5;
                int radiusSquared = radius * radius;
                // Check for nearby block entities
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            if (x * x + y * y + z * z <= radiusSquared) {
                                BlockPos currentPos = entityPos.add(x, y, z);
                                BlockState blockState = world.getBlockState(currentPos);
                                if (blockState.isOf(ModBlocks.GHOUL_MAGE_STONE_CRYSTAL)) {
                                    GhoulCrystalBlockEntity ghoulCrystalBlockEntity = (GhoulCrystalBlockEntity) world.getBlockEntity(currentPos);
                                    ghoulCrystalBlockEntity.onMobDeath(entity);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
