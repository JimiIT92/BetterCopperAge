package org.hendrix.betterfalldrop.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.state.property.Properties;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.core.BFDBlocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Interface for a {@link BetterFallDrop Better Fall Drop} {@link Oxidizable Oxidizable Block}
 */
public interface BFDOxidizable extends Oxidizable {

    /**
     * The {@link Supplier Oxidizable Blocks Oxidation Level Increases Map}
     */
    Supplier<BiMap<Block, Block>> BFD_OXIDATION_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(BFDBlocks.COPPER_BUTTON, BFDBlocks.EXPOSED_COPPER_BUTTON)
            .put(BFDBlocks.EXPOSED_COPPER_BUTTON, BFDBlocks.WEATHERED_COPPER_BUTTON)
            .put(BFDBlocks.WEATHERED_COPPER_BUTTON, BFDBlocks.OXIDIZED_COPPER_BUTTON)
            .put(BFDBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE, BFDBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
            .put(BFDBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, BFDBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
            .put(BFDBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, BFDBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE)
            .build()
    );

    /**
     * The {@link Supplier Oxidizable Blocks Oxidation Level Decreases Map}
     */
    Supplier<BiMap<Block, Block>> BFD_OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> BFD_OXIDATION_LEVEL_INCREASES.get().inverse());

    /**
     * The {@link Supplier Waxed Blocks}
     */
    Supplier<List<Block>> WAXED_BLOCKS = Suppliers.memoize(() -> Arrays.asList(
        BFDBlocks.WAXED_COPPER_BUTTON,
        BFDBlocks.WAXED_EXPOSED_COPPER_BUTTON,
        BFDBlocks.WAXED_WEATHERED_COPPER_BUTTON,
        BFDBlocks.WAXED_OXIDIZED_COPPER_BUTTON,
        BFDBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
        BFDBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
        BFDBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
        BFDBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
    ));

    /**
     * Get the {@link Optional <Block> Block} decreased {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param block The {@link Block Block to check}
     * @return The {@link Optional<Block> Block} decreased {@link Oxidizable.OxidationLevel Oxidation Level}
     */
    static Optional<Block> getDecreasedOxidationBlock(final Block block) {
        return Optional.ofNullable(BFD_OXIDATION_LEVEL_DECREASES.get().get(block));
    }

    /**
     * Get the {@link Block Unaffected Block} for the provided {@link Block Block}
     *
     * @param block The {@link Block Block to check}
     * @return The {@link Block Unaffected Block}
     */
    static Block getUnaffectedOxidationBlock(final Block block) {
        Block unaffectedBlock = block;

        for(Block decreasedOxidationBlock = BFD_OXIDATION_LEVEL_DECREASES.get().get(block); decreasedOxidationBlock != null; decreasedOxidationBlock = BFD_OXIDATION_LEVEL_DECREASES.get().get(decreasedOxidationBlock)) {
            unaffectedBlock = decreasedOxidationBlock;
        }

        return unaffectedBlock;
    }

    /**
     * Get the {@link Optional<BlockState> decreased Oxidation Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @return The {@link Optional<BlockState> decreased Oxidation Block State}
     */
    static Optional<BlockState> getDecreasedOxidationState(final BlockState state) {
        return BFDOxidizable.getDecreasedOxidationBlock(state.getBlock()).map((block) -> {
            if(state.contains(Properties.POWERED)) {
                return block.getStateWithProperties(state.with(Properties.POWERED, Boolean.FALSE));
            }
            if(state.contains(Properties.POWER)) {
                return block.getStateWithProperties(state.with(Properties.POWER, 0));
            }
            return block.getStateWithProperties(state);
        });
    }

    /**
     * Get the {@link Block Increased Block} for the provided {@link Block Block}
     *
     * @param block The {@link Block Block to check}
     * @return The {@link Block Increased Block}
     */
    static Optional<Block> getIncreasedOxidationBlock(final Block block) {
        return Optional.ofNullable(BFD_OXIDATION_LEVEL_INCREASES.get().get(block));
    }

    /**
     * Get the {@link BlockState unaffected Oxidation Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @return The {@link BlockState unaffected Oxidation Block State}
     */
    static BlockState getUnaffectedOxidationState(BlockState state) {
        if(state.contains(Properties.POWERED)) {
            state = state.with(Properties.POWERED, Boolean.FALSE);
        }
        if(state.contains(Properties.POWER)) {
            state = state.with(Properties.POWER, 0);
        }
        return BFDOxidizable.getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
    }

    /**
     * Get the {@link Optional<BlockState> degradation result Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @return The {@link Optional<BlockState> degradation result Block State}
     */
    default Optional<BlockState> getDegradationResult(final BlockState state) {
        return BFDOxidizable.getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
            if(state.contains(Properties.POWERED)) {
                return block.getStateWithProperties(state.with(Properties.POWERED, Boolean.FALSE));
            }
            if(state.contains(Properties.POWER)) {
                return block.getStateWithProperties(state.with(Properties.POWER, 0));
            }
            return block.getStateWithProperties(state);
        });
    }

    /**
     * Check if the Powered State of an {@link Oxidizable Oxidizable Block} should be reset
     *
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed or un-waxed}
     */
    default Boolean shouldResetPoweredState(final Boolean isWaxed, final BlockState state, final BlockState oldState) {
        return isBeingWaxed(isWaxed, state, oldState) || isBeingUnwaxed(isWaxed, state, oldState);
    }

    /**
     * Check if the {@link Block Block} is being waxed
     *
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed}
     */
    default Boolean isBeingWaxed(final boolean isWaxed, final BlockState state, final BlockState oldState) {
        return isWaxed && isOxidizableBlock(oldState) && isWaxedCopperBlock(state);
    }

    /**
     * Check if the {@link Block Block} is being un-waxed
     *
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being un-waxed}
     */
    default boolean isBeingUnwaxed(final boolean isWaxed, final BlockState state, final BlockState oldState) {
        return !isWaxed && isOxidizableBlock(state) && isWaxedCopperBlock(oldState);
    }

    /**
     * Check if a {@link Block Block} is oxidizable
     *
     * @param blockState The {@link Block Block State to check}
     * @return {@link Boolean True if the Block is oxidizable}
     */
    static boolean isOxidizableBlock(final BlockState blockState) {
        final Block block = blockState.getBlock();
        return BFDOxidizable.BFD_OXIDATION_LEVEL_INCREASES.get().containsKey(block) || BFDOxidizable.BFD_OXIDATION_LEVEL_DECREASES.get().containsKey(block);
    }

    /**
     * Check if a {@link Block Block} is waxed
     *
     * @param blockState The {@link BlockState Block State to check}
     * @return {@link Boolean True if the Block is waxed}
     */
    static boolean isWaxedCopperBlock(final BlockState blockState) {
        return WAXED_BLOCKS.get().contains(blockState.getBlock());
    }
}