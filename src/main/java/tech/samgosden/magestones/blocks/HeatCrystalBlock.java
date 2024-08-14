package tech.samgosden.magestones.blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HeatCrystalBlock extends MageCrystalBlock {
    public HeatCrystalBlock(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
    }



    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HeatCrystalBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntities.HEAT_CRYSTAL_BLOCK_ENTITY, HeatCrystalBlockEntity::tick);
    }
}
