package tech.samgosden.magestones.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import tech.samgosden.magestones.blocks.ModBlocks;

public class MageCrystalItem extends Item {
    Block block;
    public MageCrystalItem(Settings settings, Block block) {
        super(settings);
        this.block = block;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        BlockState blockState = block.getDefaultState();

        if (world.isAir(pos)) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                Direction facing = player.getHorizontalFacing();
                blockState = blockState.with(Properties.HORIZONTAL_FACING, facing);
            }

            world.setBlockState(pos, blockState);
            context.getStack().decrement(1); // Decrease item count by 1
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
