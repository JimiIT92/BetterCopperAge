package org.hendrix.bettercopperage.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.hendrix.bettercopperage.block.CopperFireBlock;
import org.hendrix.bettercopperage.core.BCABlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the {@link BaseFireBlock} class
 */
@Mixin(BaseFireBlock.class)
public final class BaseFireBlockMixin {

    /**
     * Place the copper fire block if the block below is a copper fire block base
     *
     * @param level The {@link BlockGetter} reference
     * @param pos The current {@link BlockPos}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At(value = "RETURN"), method = "getState", cancellable = true)
    private static void getState(final BlockGetter level, final BlockPos pos, final CallbackInfoReturnable<BlockState> callbackInfoReturnable) {
        if(CopperFireBlock.canSurviveOnBlock(level.getBlockState(pos.below()))) {
            callbackInfoReturnable.setReturnValue(BCABlocks.COPPER_FIRE.defaultBlockState());
        }
    }

}