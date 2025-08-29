package org.hendrix.bettercopperage.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.hendrix.bettercopperage.block.CopperButtonBlock;
import org.hendrix.bettercopperage.block.OxidizableCopperButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class for the {@link WaitTask Wait Task}
 */
@Mixin(WaitTask.class)
public final class WaitTaskMixin {

    /**
     * Make {@link CopperGolemEntity Copper Golems} interact with a {@link OxidizableCopperButtonBlock Copper Button} if they stop nearby
     *
     * @param world The {@link ServerWorld World reference}
     * @param entity The {@link LivingEntity Entity reference}
     * @param time The {@link Long run time}
     * @param callbackInfo The {@link CallbackInfo Callback Info}
     */
    @Inject(method = "stop", at = @At(value = "RETURN"))
    private void stop(final ServerWorld world, final LivingEntity entity, final long time, final CallbackInfo callbackInfo) {
        if(entity instanceof CopperGolemEntity copperGolem) {
            final BlockPos blockPos = entity.getBlockPos();
            final BlockState blockState = world.getBlockState(blockPos);
            if(blockState.getBlock() instanceof CopperButtonBlock && !blockState.get(CopperButtonBlock.POWERED) && copperGolem.getRandom().nextInt(5) == 0) {
                 ((CopperButtonBlock)blockState.getBlock()).powerOn(blockState, world, blockPos, null);
                 copperGolem.getDroppingItemAnimationState().startIfNotRunning(copperGolem.age);
            }
        }
    }

}