package org.hendrix.bettercopperage.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.hendrix.bettercopperage.block.CopperRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin class for the {@link ExperimentalMinecartController Experimental Minecart Controller}
 */
@Mixin(ExperimentalMinecartController.class)
public final class ExperimentalMinecartControllerMixin {

    /**
     * Make {@link MinecartEntity Minecart} move faster or slower on {@link CopperRailBlock Copper Rails}
     *
     * @param world The {@link ServerWorld World reference}
     * @param horizontalVelocity The {@link Vec3d current horizontal velocity}
     * @param iteration The {@link ExperimentalMinecartController.MoveIteration Move Iteration instance}
     * @param pos The {@link BlockPos current Block Pos}
     * @param railState The {@link BlockState current Block State}
     * @param railShape The {@link RailShape current Rail Shape}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable<Vec3d> Vec3d Callback Info Returnable}
     */
    @Inject(method = "calcNewHorizontalVelocity", at = @At(value = "TAIL"), cancellable = true)
    private void calcNewHorizontalVelocity(final ServerWorld world, final Vec3d horizontalVelocity, final ExperimentalMinecartController.MoveIteration iteration, final BlockPos pos, final BlockState railState, final RailShape railShape, final CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        final ExperimentalMinecartController controller = (ExperimentalMinecartController) (Object) this;
        if(railState.getBlock() instanceof CopperRailBlock copperRailBlock && !iteration.accelerated) {
            final Oxidizable.OxidationLevel oxidationLevel = copperRailBlock.getDegradationLevel();
            final Vec3d acceleratedVelocity = this.accelerateFromCopperRail(controller, oxidationLevel, horizontalVelocity, pos);
            if (acceleratedVelocity.horizontalLengthSquared() != horizontalVelocity.horizontalLengthSquared()) {
                iteration.accelerated = true;
                callbackInfoReturnable.setReturnValue(acceleratedVelocity);
            }
        }
    }

    /**
     * Accelerate the {@link MinecartEntity minecart} when moving on a {@link CopperRailBlock Copper Rail}
     *
     * @param controller The {@link ExperimentalMinecartController Minecart Controller reference}
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel rail Oxidation level}
     * @param velocity The {@link Vec3d current velocity}
     * @param pos The {@link BlockPos current Block Pos}
     * @return The {@link Vec3d modified velocity}
     */
    @Unique
    private Vec3d accelerateFromCopperRail(final ExperimentalMinecartController controller, final Oxidizable.OxidationLevel oxidationLevel, final Vec3d velocity, final BlockPos pos) {
        if (velocity.length() > 0.01) {
            return velocity.normalize().multiply(velocity.length() + getHorizontalVelocityModifier(oxidationLevel));
        } else {
            final Vec3d launchDirection = controller.minecart.getLaunchDirection(pos);
            return launchDirection.lengthSquared() <= 0.0 ? velocity : launchDirection.multiply(velocity.length() + getSlopeVelocityMultiplier(oxidationLevel));
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
            case UNAFFECTED -> 0.1D;
            case EXPOSED -> 0.05D;
            case WEATHERED -> 0.025D;
            case OXIDIZED -> 0.001D;
        };
    }

}