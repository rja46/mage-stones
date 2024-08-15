package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import tech.samgosden.magestones.item.ModItems;

public class GhoulCrystalBlockEntity extends MageCrystalBlockEntity {
    public GhoulCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.GHOUL_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.GHOUL_MAGE_STONE);
    }

    public void onMobDeath(LivingEntity entity) {
        // Handle mob death near this block entity
        if (!(entity instanceof ZombieEntity)&&world!=null) {
            ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, entity.getEntityWorld());
            //0.1 is added to Y to stop it spawning inside a block.
            zombie.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
            world.spawnEntity(zombie);
        }
    }
}
