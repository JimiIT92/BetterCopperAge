package org.hendrix.bettercopperage.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.level.block.state.BlockState;
import org.hendrix.bettercopperage.block.CopperButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the {@link DoNothing} class
 */
@Mixin(DoNothing.class)
public final class DdoNothingMixin {

    /**
     * Make a copper golem interact with a {@link CopperButtonBlock} if it stops nearby
     *
     * @param level The {@link ServerLevel} reference
     * @param body The {@link LivingEntity} reference
     * @param timestamp How long the task has been running
     * @param callbackInfo The {@link CallbackInfo}
     */
    @Inject(at = @At(value = "RETURN"), method = "doStop")
    private void doStop(final ServerLevel level, final LivingEntity body, final long timestamp, final CallbackInfo callbackInfo) {
        if(body instanceof CopperGolem copperGolem) {
            final BlockState state = copperGolem.getInBlockState();
            if(state.getBlock() instanceof CopperButtonBlock copperButton && !state.getValue(CopperButtonBlock.POWERED) && copperGolem.getRandom().nextInt(5) == 0) {
                copperButton.press(state, level, copperGolem.blockPosition(), null);
                copperGolem.getInteractionDropItemAnimationState().start(copperGolem.tickCount);
            }
        }
    }

}