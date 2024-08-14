package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.util.ConfigHandler;

public class MageCrystalBlockEntity extends BlockEntity {
    protected int ticksLeft;
    protected int effectRadius = 5;

    public MageCrystalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        ticksLeft = ConfigHandler.config.getInt("magestones.DefaultCrystalTicksLeft");
    }


    public static void tick(World world, BlockPos pos, BlockState state, MageCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 0) {
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false));
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("EffectRadius", effectRadius);
        nbt.putInt("TicksLeft", ticksLeft);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        effectRadius = nbt.getInt("EffectRadius");
        ticksLeft = nbt.getInt("TicksLeft");
    }
}
