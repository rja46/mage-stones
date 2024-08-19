package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import tech.samgosden.magestones.util.ConfigHandler;

public class ChargeMeterEntity extends BlockEntity {
    public ChargeMeterEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.CHARGE_METER_ENTITY, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, ChargeMeterEntity blockEntity) {
        if (!world.isClient) {
           Direction direction = state.get(CrystalConnector.FACING);
           BlockEntity crystalEntity = null;
           if (direction == Direction.NORTH) {
                crystalEntity = world.getBlockEntity(pos.south());
           }
           else if (direction == Direction.SOUTH) {
                crystalEntity = world.getBlockEntity(pos.north());
           }
           else if (direction == Direction.EAST) {
                crystalEntity = world.getBlockEntity(pos.west());
           }
           else if (direction == Direction.WEST) {
                crystalEntity = world.getBlockEntity(pos.east());
           }

           int max = ConfigHandler.config.getInt("magestones.DefaultCrystalTicksLeft");
           if (crystalEntity instanceof MageCrystalBlockEntity){
               if (((MageCrystalBlockEntity) crystalEntity).ticksLeft == 0){
                   world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 0));
               }
               else if (((MageCrystalBlockEntity) crystalEntity).ticksLeft == max){
                   world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 4));
               }
               else if (((MageCrystalBlockEntity) crystalEntity).ticksLeft < max && ((MageCrystalBlockEntity) crystalEntity).ticksLeft > (max / 4) * 3){
                   world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 3));
               }
               else if (((MageCrystalBlockEntity) crystalEntity).ticksLeft < (max / 5) * 4 && ((MageCrystalBlockEntity) crystalEntity).ticksLeft > (max / 4) * 2){
                   world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 2));
               }
               else if (((MageCrystalBlockEntity) crystalEntity).ticksLeft < (max / 5) * 3 && ((MageCrystalBlockEntity) crystalEntity).ticksLeft > (max / 4)){
                   world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 1));
               }
           }
           else {world.setBlockState(pos, state.with(ChargeMeter.CHARGE_LEVEL, 4));
           }
        }
    }
}
