package org.hendrix.betterfalldrop.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.AxeItem;
import org.hendrix.betterfalldrop.block.BFDOxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/**
 * Mixin class for the {@link AxeItem Axe Item class}
 */
@Mixin(AxeItem.class)
public class AxeItemMixin {

    /**
     * Scrape an {@link Oxidizable Oxidizable Block}
     * when interacted with an {@link AxeItem Axe}
     *
     * @param state The {@link BlockState current Block State}
     */
    @Redirect(method = "tryStrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Oxidizable;getDecreasedOxidationState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"))
    private Optional<BlockState> getDecreasedOxidationState(final BlockState state) {
        if(state.getBlock() instanceof BFDOxidizable) {
            return BFDOxidizable.getDecreasedOxidationState(state);
        }
        return Oxidizable.getDecreasedOxidationState(state);
    }

}