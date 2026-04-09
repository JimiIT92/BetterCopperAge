package org.hendrix.bettercopperage.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import org.hendrix.bettercopperage.block.CopperRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the {@link OldMinecartBehavior} class
 */
@Mixin(OldMinecartBehavior.class)
public final class OldMinecartBehaviorMixin {

    /**
     * Make the minecart move faster or slower on copper rails
     *
     * @param level The {@link ServerLevel} reference
     * @param callbackInfo The {@link CallbackInfo}
     */
    @Inject(at = @At(value = "TAIL"), method = "moveAlongTrack")
    private void moveAlongTrack(final ServerLevel level, final CallbackInfo callbackInfo) {
        final OldMinecartBehavior behavior = (OldMinecartBehavior) (Object) this;
        final AbstractMinecart minecart = behavior.minecart;
        final BlockPos railPos = minecart.getCurrentBlockPosOrRailBelow();
        final BlockState railBlockState = level.getBlockState(railPos);
        if(railBlockState.getBlock() instanceof CopperRailBlock copperRail) {
            final RailShape railShape = railBlockState.getValue(copperRail.getShapeProperty());
            final Vec3 velocity = behavior.getDeltaMovement();
            final double speedLength = velocity.horizontalDistance();
            if(speedLength > 0.01) {
                final double velocityModifier = copperRail.getHorizontalVelocityModifier();
                behavior.setDeltaMovement(velocity.add(velocity.x / speedLength * velocityModifier, 0, velocity.z / speedLength * velocityModifier));
            } else {
                final double speedMultiplier = copperRail.getSlopeVelocityMultiplier(false);
                double velocityX = velocity.x;
                double velocityZ = velocity.z;
                if(RailShape.EAST_WEST.equals(railShape)) {
                    if (minecart.isRedstoneConductor(railPos.west())) {
                        velocityX = speedMultiplier;
                    } else if (minecart.isRedstoneConductor(railPos.east())) {
                        velocityX = -speedMultiplier;
                    }
                } else {
                    if (railShape != RailShape.NORTH_SOUTH) {
                        return;
                    }

                    if (behavior.minecart.isRedstoneConductor(railPos.north())) {
                        velocityZ = speedMultiplier;
                    } else if (behavior.minecart.isRedstoneConductor(railPos.south())) {
                        velocityZ = -speedMultiplier;
                    }
                }
                behavior.setDeltaMovement(velocityX, velocity.y, velocityZ);
            }
        }
    }

}