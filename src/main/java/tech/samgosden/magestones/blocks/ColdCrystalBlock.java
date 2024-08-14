package tech.samgosden.magestones.blocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColdCrystalBlock extends MageCrystalBlock implements BlockEntityProvider {
    public ColdCrystalBlock(int height, int xzOffset, AbstractBlock.Settings settings) {
        super(height, xzOffset, settings);
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ColdCrystalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntities.COLD_CRYSTAL_BLOCK_ENTITY, ColdCrystalBlockEntity::tick);
    }



}
