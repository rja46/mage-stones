package tech.samgosden.magestones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransmitterBlock extends Block {
    boolean isPowered = false;

    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public TransmitterBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(ACTIVE, false));
    }

    public List<BlockPos> getAdjacentCrystals(BlockPos pos, World world) {
        List<BlockPos> adjacentBlocks = new ArrayList<>();
        if (world.getBlockEntity(pos.north()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.north());
        }
        if (world.getBlockEntity(pos.south()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.south());
        }
        if (world.getBlockEntity(pos.east()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.east());
        }
        if (world.getBlockEntity(pos.west()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.west());
        }
        if (world.getBlockEntity(pos.up()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.up());
        }
        if (world.getBlockEntity(pos.down()) instanceof MageCrystalBlockEntity){
            adjacentBlocks.add(pos.down());
        }
        return adjacentBlocks;
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
            for (BlockPos adjacentPos : getAdjacentCrystals(pos, world)) {
                if (world.getBlockEntity(adjacentPos) != null) {
                    if (world.getBlockEntity(adjacentPos) instanceof MageCrystalBlockEntity) {
                        ((MageCrystalBlockEntity) world.getBlockEntity(adjacentPos)).increaseRadius();

                    }
                }
            }
        }

        if (!world.isReceivingRedstonePower(pos) && isPowered) {
            isPowered = false;
            // Trigger your event here
            for (BlockPos adjacentPos : getAdjacentCrystals(pos, world)) {
                if (world.getBlockEntity(adjacentPos) != null) {
                    if (world.getBlockEntity(adjacentPos) instanceof MageCrystalBlockEntity) {
                        ((MageCrystalBlockEntity) world.getBlockEntity(adjacentPos)).resetRadius();

                    }
                }
            }
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }
}
