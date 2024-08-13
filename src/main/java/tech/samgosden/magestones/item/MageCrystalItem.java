package tech.samgosden.magestones.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tech.samgosden.magestones.blocks.ModBlocks;

public class MageCrystalItem extends Item {
    public MageCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        BlockState blockState =  ModBlocks.CHARGED_MAGE_STONE_CRYSTAL.getDefaultState();

        if (world.isAir(pos)) {
            world.setBlockState(pos, blockState);
            context.getStack().decrement(1); // Decrease item count by 1
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
