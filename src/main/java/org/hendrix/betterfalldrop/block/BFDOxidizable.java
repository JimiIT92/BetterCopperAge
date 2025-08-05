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
            .build()
    );

    /**
     * The {@link Supplier Oxidizable Blocks Oxidation Level Decreases Map}
     */
    Supplier<BiMap<Block, Block>> BFD_OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> BFD_OXIDATION_LEVEL_INCREASES.get().inverse());

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
     * Get the {@link Optional< BlockState > decreased Oxidation Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @return The {@link Optional<BlockState> decreased Oxidation Block State}
     */
    static Optional<BlockState> getDecreasedOxidationState(final BlockState state) {
        return BFDOxidizable.getDecreasedOxidationBlock(state.getBlock()).map((block) -> {
            if(state.contains(Properties.POWERED)) {
                return block.getStateWithProperties(state.with(Properties.POWERED, Boolean.FALSE));
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
            return block.getStateWithProperties(state);
        });
    }

}