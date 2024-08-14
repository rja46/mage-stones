package tech.samgosden.magestones.blocks;
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

public class ForceCrystalBlock extends MageCrystalBlock {
    public static final BooleanProperty ISACTIVE = BooleanProperty.of("isactive");
    public ForceCrystalBlock(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
        setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, Boolean.FALSE).with(FACING, Direction.UP).with(ISACTIVE, Boolean.TRUE));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ISACTIVE);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForceCrystalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntities.FORCE_CRYSTAL_BLOCK_ENTITY, ForceCrystalBlockEntity::tick);
    }
}
