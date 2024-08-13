package tech.samgosden.magestones.blocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class LightCrystalBlock extends MageCrystalBlock {
    int ticksLeft = 100;
    public LightCrystalBlock(int height, int xzOffset, AbstractBlock.Settings settings) {
        super(height, xzOffset, settings);
    }

    public void tick(World world, BlockPos pos, BlockState state, Random random) {
        ticksLeft--;
        System.out.println("ticksLeft: " + ticksLeft);
        if (ticksLeft <= 0) {
            world.setBlockState(pos, ModBlocks.DULL_MAGE_STONE_CRYSTAL.getDefaultState());
        }
    }
}
