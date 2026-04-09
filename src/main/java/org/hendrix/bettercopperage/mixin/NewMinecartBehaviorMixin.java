package org.hendrix.bettercopperage.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import org.hendrix.bettercopperage.block.CopperRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the {@link NewMinecartBehavior} class
 */
@Mixin(NewMinecartBehavior.class)
public final class NewMinecartBehaviorMixin {

    /**
     * Make the minecart move faster or slower on copper rails
     *
     * @param level The {@link ServerLevel} reference
     * @param deltaMovement The {@link Vec3 delta movement}
     * @param trackIteration The {@link NewMinecartBehavior.TrackIteration}
     * @param currentPos The current {@link BlockPos}
     * @param currentState The current {@link BlockState}
     * @param shape The {@link RailShape}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At(value = "TAIL"), method = "calculateTrackSpeed", cancellable = true)
    private void calculateTrackSpeed(final ServerLevel level, final Vec3 deltaMovement, final NewMinecartBehavior.TrackIteration trackIteration, final BlockPos currentPos, final BlockState currentState, final RailShape shape, final CallbackInfoReturnable<Vec3> callbackInfoReturnable) {
        final NewMinecartBehavior behavior = (NewMinecartBehavior) (Object) this;
        if(currentState.getBlock() instanceof CopperRailBlock railBlock && !trackIteration.hasBoosted) {
            final Vec3 velocity = this.getVelocity(behavior, deltaMovement, railBlock, currentPos);
            if(velocity.horizontalDistanceSqr() != deltaMovement.horizontalDistanceSqr()) {
                trackIteration.hasBoosted = true;
                callbackInfoReturnable.setReturnValue(velocity);
            }
        }
    }

    /**
     * Get the new {@link Vec3 velocity} based on the current {@link CopperRailBlock}
     *
     * @param behavior The current {@link NewMinecartBehavior}
     * @param deltaMovement The {@link Vec3 delta movement}
     * @param railBlock The {@link CopperRailBlock} that the Minecart is standing on
     * @param currentPos The current {@link BlockPos}
     * @return The {@link Vec3 velocity}
     */
    @Unique
    private Vec3 getVelocity(final NewMinecartBehavior behavior, final Vec3 deltaMovement, final CopperRailBlock railBlock, final BlockPos currentPos) {
        if(deltaMovement.length() > 0.01) {
            return deltaMovement.normalize().scale(deltaMovement.length() + railBlock.getHorizontalVelocityModifier());
        }
        final Vec3 launchDirection = behavior.minecart.getRedstoneDirection(currentPos);
        return launchDirection.lengthSqr() <= 0 ? deltaMovement : launchDirection.scale(deltaMovement.length() + railBlock.getSlopeVelocityMultiplier(true));
    }

}