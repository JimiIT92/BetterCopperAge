package org.hendrix.bettercopperage.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.animal.golem.CopperGolemAi;
import net.minecraft.world.entity.animal.golem.CopperGolemState;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the {@link CopperGolemAi} class
 */
@Mixin(CopperGolemAi.class)
public final class CopperGolemAiMixin {

    /**
     * Make container close properly once opened by a Copper Golem
     *
     * @param state The current {@link CopperGolemState}
     * @param sound The {@link SoundEvent} to play
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At(value = "RETURN"), method = "onReachedTargetInteraction", cancellable = true)
    private static void onReachedTargetInteraction(final CopperGolemState state, final SoundEvent sound, final CallbackInfoReturnable<TransportItemsBetweenContainers.OnTargetReachedInteraction> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((body, target, ticksSinceReachingTarget) -> {
            if (body instanceof CopperGolem copperGolem) {
                Container container = target.container();
                if (ticksSinceReachingTarget == 1) {
                    container.startOpen(copperGolem);
                    copperGolem.setOpenedChestPos(target.pos());
                    copperGolem.setState(state);
                }

                if (ticksSinceReachingTarget == 9 && sound != null) {
                    copperGolem.playSound(sound);
                }

                if (ticksSinceReachingTarget == 60) {
                    if (container.getEntitiesWithContainerOpen().contains(body) || container instanceof ShulkerBoxBlockEntity) {
                        container.stopOpen(copperGolem);
                    }

                    copperGolem.clearOpenedChestPos();
                }
            }
        });
    }

}