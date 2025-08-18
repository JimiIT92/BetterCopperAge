package org.hendrix.betterfalldrop.mixin;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.hendrix.betterfalldrop.block.CopperRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class for the {@link DefaultMinecartController Default Minecart Controller}
 */
@Mixin(DefaultMinecartController.class)
public final class DefaultMinecartControllerMixin {

    /**
     * Make {@link MinecartEntity Minecart} move faster or slower on {@link CopperRailBlock Copper Rails}
     *
     * @param world The {@link ServerWorld World reference}
     * @param callbackInfo The {@link CallbackInfo Callback Info}
     */
    @Inject(method = "moveOnRail", at = @At(value = "TAIL"))
    private void moveOnRail(final ServerWorld world, final CallbackInfo callbackInfo) {
        final DefaultMinecartController controller = (DefaultMinecartController) (Object) this;
        final BlockPos blockPos = controller.minecart.getRailOrMinecartPos();
        final BlockState blockState = controller.getWorld().getBlockState(blockPos);
        if (blockState.getBlock() instanceof CopperRailBlock copperRailBlock) {
            final RailShape railShape = blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty());
            final Oxidizable.OxidationLevel oxidationLevel = copperRailBlock.getDegradationLevel();
            final Vec3d velocity = controller.getVelocity();
            final double horizontalLength = velocity.horizontalLength();
            if (horizontalLength > 0.01) {
                final double velocityModifier = getHorizontalVelocityModifier(oxidationLevel);
                controller.setVelocity(velocity.add(velocity.x / horizontalLength * velocityModifier, 0.0, velocity.z / horizontalLength * velocityModifier));
            } else {
                final double speedMultiplier = getSlopeVelocityMultiplier(oxidationLevel);
                double velocityX = velocity.x;
                double velocityZ = velocity.z;
                if (railShape == RailShape.EAST_WEST) {
                    if (controller.minecart.willHitBlockAt(blockPos.west())) {
                        velocityX = speedMultiplier;
                    } else if (controller.minecart.willHitBlockAt(blockPos.east())) {
                        velocityX = -speedMultiplier;
                    }
                } else {
                    if (railShape != RailShape.NORTH_SOUTH) {
                        return;
                    }

                    if (controller.minecart.willHitBlockAt(blockPos.north())) {
                        velocityZ = speedMultiplier;
                    } else if (controller.minecart.willHitBlockAt(blockPos.south())) {
                        velocityZ = -speedMultiplier;
                    }
                }

                controller.setVelocity(velocityX, velocity.y, velocityZ);
            }
        }
    }

    /**
     * Get the {@link Double horizontal velocity modifier} based on the {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel rail Oxidation Level}
     * @return The {@link Double rail horizontal velocity modifier}
     */
    @Unique
    private double getHorizontalVelocityModifier(final Oxidizable.OxidationLevel oxidationLevel) {
        return switch(oxidationLevel) {
            case UNAFFECTED -> 0.04D;
            case EXPOSED -> 0.03D;
            case WEATHERED -> 0.02D;
            case OXIDIZED -> 0.01D;
        };
    }

    /**
     * Get the {@link Double slope velocity modifier} based on the {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel rail Oxidation Level}
     * @return The {@link Double rail slope velocity modifier}
     */
    @Unique
    private double getSlopeVelocityMultiplier(final Oxidizable.OxidationLevel oxidationLevel) {
        return switch(oxidationLevel) {
            case UNAFFECTED -> 0.015D;
            case EXPOSED -> 0.01D;
            case WEATHERED -> 0.005D;
            case OXIDIZED -> 0.001D;
        };
    }

}