package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import tech.samgosden.magestones.item.ModItems;

public class GravityCrystalBlockEntity extends MageCrystalBlockEntity {
    public GravityCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.GRAVITY_CRYSTAL_BLOCK_ENTITY, pos, state, ModItems.GRAVITY_MAGE_STONE);
    }
}
