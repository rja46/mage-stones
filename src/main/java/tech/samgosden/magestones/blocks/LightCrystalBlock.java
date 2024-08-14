package tech.samgosden.magestones.blocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightCrystalBlock extends MageCrystalBlock {

    public LightCrystalBlock(int height, int xzOffset, AbstractBlock.Settings settings) {
        super(height, xzOffset, settings);
        setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, Boolean.FALSE).with(FACING, Direction.UP).with(ISACTIVE, Boolean.TRUE));
    }



    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightCrystalBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ISACTIVE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntities.LIGHT_CRYSTAL_BLOCK_ENTITY, LightCrystalBlockEntity::tick);
    }
}
