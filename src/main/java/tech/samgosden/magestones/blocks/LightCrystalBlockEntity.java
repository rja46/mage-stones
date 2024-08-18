package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import tech.samgosden.magestones.item.ModItems;

public class LightCrystalBlockEntity extends MageCrystalBlockEntity {
    public LightCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LIGHT_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.LIGHT_MAGE_STONE);
    }
}
