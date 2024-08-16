package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CrystalConnectorEntity extends BlockEntity {
    public CrystalConnectorEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.CRYSTAL_CONNECTOR_ENTITY, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, CrystalConnectorEntity blockEntity) {
        if (!world.isClient) {
            Direction direction = state.get(CrystalConnector.FACING);
            MageCrystalBlockEntity crystalBlock = null;
            if (direction == Direction.NORTH) {
                if (world.getBlockEntity(pos.north()) instanceof MageCrystalBlockEntity) {
                    crystalBlock = (MageCrystalBlockEntity) world.getBlockEntity(pos.north());
                }
            }
            else if (direction == Direction.EAST) {
                if (world.getBlockEntity(pos.east()) instanceof MageCrystalBlockEntity) {
                    crystalBlock = (MageCrystalBlockEntity) world.getBlockEntity(pos.east());
                }
            }
            else if (direction == Direction.WEST) {
                if (world.getBlockEntity(pos.west()) instanceof MageCrystalBlockEntity) {
                    crystalBlock = (MageCrystalBlockEntity) world.getBlockEntity(pos.west());
                }
            }
            else if (direction == Direction.SOUTH) {
                if (world.getBlockEntity(pos.south()) instanceof MageCrystalBlockEntity) {
                    crystalBlock = (MageCrystalBlockEntity) world.getBlockEntity(pos.south());
                }
            }
            if (crystalBlock != null){
                if (crystalBlock.isReceivingEnergy()){
                    MageCrystalBlockEntity crystalToPower = null;
                    if (direction == Direction.NORTH) {
                        if (world.getBlockEntity(pos.south()) instanceof MageCrystalBlockEntity) {
                            crystalToPower = (MageCrystalBlockEntity) world.getBlockEntity(pos.south());
                        }
                    }
                    else if (direction == Direction.EAST) {
                        if (world.getBlockEntity(pos.west()) instanceof MageCrystalBlockEntity) {
                            crystalToPower = (MageCrystalBlockEntity) world.getBlockEntity(pos.west());
                        }
                    }
                    else if (direction == Direction.WEST) {
                        if (world.getBlockEntity(pos.east()) instanceof MageCrystalBlockEntity) {
                            crystalToPower = (MageCrystalBlockEntity) world.getBlockEntity(pos.east());
                        }
                    }
                    else {
                        if (world.getBlockEntity(pos.north()) instanceof MageCrystalBlockEntity) {
                            crystalToPower = (MageCrystalBlockEntity) world.getBlockEntity(pos.north());
                        }
                    }
                    if (crystalToPower != null) {
                        if (crystalBlock.ticksLeft > 0) {
                            crystalToPower.increaseRadius();
                            if (crystalToPower.ticksLeft == 0) {
                                //I'm not sure why this doesn't update visually
                                world.setBlockState(crystalToPower.getPos(), world.getBlockState(crystalToPower.getPos()).with(MageCrystalBlock.ISACTIVE, true));
                            }
                            crystalToPower.ticksLeft++;
                        }
                    }
                }
            }
        }
    }
}
