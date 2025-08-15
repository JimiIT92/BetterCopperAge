package org.hendrix.betterfalldrop.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.hendrix.betterfalldrop.block.BFDOxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 * Mixin class for the {@link LightningEntity Lightning Entity}
 */
@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {

    /**
     * Clean the Oxidation from some {@link BFDOxidizable Oxidizable Blocks} when a {@link LightningEntity Lightning strikes on them}
     *
     * @param world The {@link World World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param callbackInfo The {@link CallbackInfo Callback Info}
     */
    @Inject(method = "cleanOxidation", at = @At(value = "TAIL"))
    private static void cleanOxidation(final World world, final BlockPos pos, final CallbackInfo callbackInfo) {
        final BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof BFDOxidizable) {
            world.setBlockState(pos, BFDOxidizable.getUnaffectedOxidationState(world.getBlockState(pos)));
            final BlockPos.Mutable mutable = pos.mutableCopy();
            final int tries = world.random.nextInt(3) + 3;
            for (int i = 0; i < tries; i++) {
                cleanOxidationAround(world, pos, mutable, world.random.nextInt(8) + 1);
            }
        }
    }

    /**
     * Clean the oxidation from some {@link Block Blocks} in a radius
     *
     * @param world The {@link World World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param mutablePos The {@link BlockPos.Mutable Mutable Block Pos reference}
     * @param count {@link Integer How many Blocks to de-oxidize}
     */
    @Unique
    private static void cleanOxidationAround(final World world, final BlockPos pos, final BlockPos.Mutable mutablePos, final int count) {
        mutablePos.set(pos);
        for (int i = 0; i < count; i++) {
            final Optional<BlockPos> cleanedBlockPos = cleanOxidationAround(world, mutablePos);
            if (cleanedBlockPos.isEmpty()) {
                break;
            }
            mutablePos.set(cleanedBlockPos.get());
        }
    }

    /**
     * Clean the oxidation from some {@link Block Blocks} around a certain {@link BlockPos Block Pos}
     *
     * @param world The {@link World World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @return The cleaned {@link BlockPos Block Pos}
     */
    @Unique
    private static Optional<BlockPos> cleanOxidationAround(final World world, final BlockPos pos) {
        for (final BlockPos blockPos : BlockPos.iterateRandomly(world.random, 10, pos, 1)) {
            final BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof BFDOxidizable) {
                BFDOxidizable.getDecreasedOxidationState(blockState).ifPresent(state -> world.setBlockState(blockPos, state));
                world.syncWorldEvent(WorldEvents.ELECTRICITY_SPARKS, blockPos, -1);
                return Optional.of(blockPos);
            }
        }
        return Optional.empty();
    }

}