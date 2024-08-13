package tech.samgosden.magestones.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.samgosden.magestones.blocks.ModBlocks;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Inject(method = "isSideSolidFullSquare", at = @At("HEAD"), cancellable = true)
    private void isSideSolidFullSquare(BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == ModBlocks.CHARGER_BLOCK && !(direction == Direction.UP || direction == Direction.DOWN)) {
            cir.setReturnValue(false); // or false based on your requirements
        }
    }
}