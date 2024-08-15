package tech.samgosden.magestones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.util.ConfigHandler;

public class MageCrystalBlockEntity extends BlockEntity {
    protected int ticksLeft = -1;
    protected int defaultRadius = 5;
    protected int effectRadius = defaultRadius;
    protected int increasedRadius = defaultRadius * 2;
    protected Item drop;

    public MageCrystalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Item drop) {
        super(type, pos, state);
        if(ticksLeft == -1)
        {
            ticksLeft = ConfigHandler.config.getInt("magestones.DefaultCrystalTicksLeft");
        }
        if (ticksLeft <= 0) {
            if (world != null) {
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false));
            }
        }
        this.drop = drop;
    }

    public void setTicksLeft(int ticksLeft) {
        if (ticksLeft <= 0) {
            if (world != null) {
                BlockState state = world.getBlockState(pos);
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false));
            }
        }
        this.ticksLeft = ticksLeft;
    }

    public static void tick(World world, BlockPos pos, BlockState state, MageCrystalBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.ticksLeft > 0) {
                blockEntity.ticksLeft -= 1;
            }
            if (blockEntity.ticksLeft == 1) {
                world.setBlockState(pos, state.with(MageCrystalBlock.ISACTIVE, false));
            }
            //set the ticks left on the block
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof MageCrystalBlock crystalBlock) {
                crystalBlock.ticksLeft -= blockEntity.ticksLeft;
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

    @Override
    public void markRemoved() {
        super.markRemoved();
        // Perform actions when the block entity is removed
        // For example, drop an item with the stored durability
        if (world != null && !world.isClient) {
            ItemStack dropStack = new ItemStack(drop);
            NbtCompound nbt = new NbtCompound();
            nbt.putInt("durability", ticksLeft);
            dropStack.setNbt(nbt);
            ItemScatterer.spawn( world, pos.getX(), pos.getY(), pos.getZ(), dropStack);
        }
    }
    public void resetTimeLeft(){
        ticksLeft = ConfigHandler.config.getInt("magestones.DefaultCrystalTicksLeft");
    }

    public void increaseRadius(){
        effectRadius = increasedRadius;
    }

    public void resetRadius(){
        effectRadius = defaultRadius;
    }
}
