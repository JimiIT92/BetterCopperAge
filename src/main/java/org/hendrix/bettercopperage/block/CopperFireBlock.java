package org.hendrix.bettercopperage.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.hendrix.bettercopperage.core.BCATags;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper fire block
 */
public final class CopperFireBlock extends BaseFireBlock {

    /**
     * The {@link MapCodec<CopperFireBlock> Copper Fire Block} Codec
     */
    public static final MapCodec<CopperFireBlock> CODEC = simpleCodec(CopperFireBlock::new);

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties}
     *
     * @param properties The {@link BlockBehaviour.Properties}
     */
    public CopperFireBlock(final Properties properties) {
        super(properties, 1.5F);
    }

    /**
     * Get the block's codec
     *
     * @return The block's codec
     */
    @Override
    protected @NonNull MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    /**
     * Check whether the copper fire should extinguish
     *
     * @param state The current {@link BlockState}
     * @param level The {@link LevelReader} reference
     * @param scheduledTickAccess The {@link ScheduledTickAccess} reference
     * @param pos The current {@link BlockPos}
     * @param directionToNeighbour The {@link Direction} to the neighbor block
     * @param neighbourPos The neighbour {@link BlockPos}
     * @param neighbourState The neighbour {@link BlockState}
     * @param random The {@link RandomSource}
     * @return The updated {@link BlockState}
     */
    @Override
    protected @NonNull BlockState updateShape(final @NonNull BlockState state, final @NonNull LevelReader level, final @NonNull ScheduledTickAccess scheduledTickAccess, final @NonNull BlockPos pos, final @NonNull Direction directionToNeighbour, final @NonNull BlockPos neighbourPos, final @NonNull BlockState neighbourState, final @NonNull RandomSource random) {
        return this.canSurvive(state, level, pos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    /**
     * Check whether the copper fire block can survive at the current location
     *
     * @param state The current {@link BlockState}
     * @param level The {@link LevelReader} reference
     * @param pos The current {@link BlockPos}
     * @return {@link Boolean True} if the copper fire block can survive
     */
    @Override
    protected boolean canSurvive(final @NonNull BlockState state, final LevelReader level, final BlockPos pos) {
        return canSurviveOnBlock(level.getBlockState(pos.below()));
    }

    /**
     * Check whether the provided {@link BlockState} is valid for copper fire block placement
     *
     * @param state The {@link BlockState} to check
     * @return {@link Boolean True} if the block is valid for copper fire block placement
     */
    public static boolean canSurviveOnBlock(final BlockState state) {
        return state.is(BCATags.BlockTags.COPPER_FIRE_BASE_BLOCKS);
    }

    /**
     * Whether this fire block can burn
     *
     * @param state The current {@link BlockState}
     * @return {@link Boolean True}
     */
    @Override
    protected boolean canBurn(final @NonNull BlockState state) {
        return true;
    }
}
