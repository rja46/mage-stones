package tech.samgosden.magestones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.MageStones;

public class ChargerBlock extends Block {
    boolean isPowered = false;
    boolean showAsPowered = false;

    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public ChargerBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(ACTIVE, false));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)){
            world.setBlockState(pos, state.with(ACTIVE, true));
        }
        else{
            world.setBlockState(pos, state.with(ACTIVE, false));
        }

        if (world.isReceivingRedstonePower(pos) && !isPowered) {
          isPowered = true;
          // Trigger your event here
            if (world.getBlockState(pos.up()).isIn(MageStones.CHARGEABLE_CRYSTALS)) {
                ((MageCrystalBlockEntity) world.getBlockEntity(pos.up())).resetTimeLeft();
                world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(MageCrystalBlock.ISACTIVE, true));
                isPowered = false;
            }
        }
        else {

            isPowered = false;
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }
}
