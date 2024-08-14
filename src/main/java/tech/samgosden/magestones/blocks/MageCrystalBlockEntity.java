package tech.samgosden.magestones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MageCrystalBlockEntity extends BlockEntity {
    protected int ticksLeft;
    protected int effectRadius = 5;

    public MageCrystalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        ticksLeft = 100;
    }


    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
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
