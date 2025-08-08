package org.hendrix.betterfalldrop.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.hendrix.betterfalldrop.block.CopperFireBlock;
import org.hendrix.betterfalldrop.core.BFDBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin class for the {@link AbstractFireBlock Abstract Fire Block class}
 */
@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

    /**
     * Get the {@link BlockState Block State} to place based on the {@link Block Block} below
     *
     * @param world The {@link BlockView World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable<BlockState> Block State Callback Info Returnable}
     */
    @Inject(method = "getState", at = @At(value = "RETURN"), cancellable = true)
    private static void getState(final BlockView world, final BlockPos pos, final CallbackInfoReturnable<BlockState> callbackInfoReturnable) {
        final BlockState blockState = world.getBlockState(pos.down());
        if (CopperFireBlock.isCopperBase(blockState)) {
            callbackInfoReturnable.setReturnValue(BFDBlocks.COPPER_FIRE.getDefaultState());
        }
    }

}