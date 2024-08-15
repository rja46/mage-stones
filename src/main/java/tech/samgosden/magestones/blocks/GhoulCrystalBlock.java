package tech.samgosden.magestones.blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tech.samgosden.magestones.item.ModItems;

public class GhoulCrystalBlock extends MageCrystalBlock {
    public GhoulCrystalBlock(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
    }

    @Override
    public Item asItem(){
        return ModItems.GHOUL_MAGE_STONE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GhoulCrystalBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntities.GHOUL_CRYSTAL_BLOCK_ENTITY, GhoulCrystalBlockEntity::tick);
    }
}
