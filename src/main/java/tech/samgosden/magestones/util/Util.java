package tech.samgosden.magestones.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class Util {
    public static LivingEntity[] getLivingEntitiesInRange(int radius, World world, BlockPos pos) {
        int radiusSquared = radius * radius;
        return  world.getEntitiesByClass(LivingEntity.class, new Box(
                        pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                        pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), Entity::isAlive)
                .stream()
                .filter(entity -> entity.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= radiusSquared)
                .toArray(LivingEntity[]::new);
    }

    public static ItemEntity[] getDroppedItemEntitiesInRange(int radius, World world, BlockPos pos) {
        int radiusSquared = radius * radius;
        return  world.getEntitiesByClass(ItemEntity.class, new Box(
                        pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                        pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), Entity::isAlive)
                .stream()
                .filter(entity -> entity.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= radiusSquared)
                .toArray(ItemEntity[]::new);
    }
}
