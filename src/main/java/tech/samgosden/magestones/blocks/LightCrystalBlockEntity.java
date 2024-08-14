package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LightCrystalBlockEntity extends MageCrystalBlockEntity {
    public LightCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LIGHT_CRYSTAL_BLOCK_ENTITY, pos, state);
    }
}
