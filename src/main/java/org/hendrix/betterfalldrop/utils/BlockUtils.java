package org.hendrix.betterfalldrop.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import org.hendrix.betterfalldrop.block.BFDOxidizable;
import org.hendrix.betterfalldrop.core.BFDBlocks;

import java.util.Arrays;

/**
 * Utility methods for {@link Block Blocks}
 */
public final class BlockUtils {

    /**
     * Check if a {@link Block Block} is waxed
     *
     * @param block The {@link Block Block to check}
     * @return {@link Boolean True if the Block is waxed}
     */
    public static boolean isWaxedCopperBlock(final Block block) {
        return Arrays.asList(
                BFDBlocks.WAXED_COPPER_BUTTON,
                BFDBlocks.WAXED_EXPOSED_COPPER_BUTTON,
                BFDBlocks.WAXED_WEATHERED_COPPER_BUTTON,
                BFDBlocks.WAXED_OXIDIZED_COPPER_BUTTON
        ).contains(block);
    }

    /**
     * Check if a {@link Block Block} is oxidizable
     *
     * @param block The {@link Block Block to check}
     * @return {@link Boolean True if the Block is oxidizable}
     */
    public static boolean isOxidizableBlock(final Block block) {
        return BFDOxidizable.BFD_OXIDATION_LEVEL_INCREASES.get().containsKey(block) || BFDOxidizable.BFD_OXIDATION_LEVEL_DECREASES.get().containsKey(block);
    }

    /**
     * Get the {@link String Block Name} of a modded {@link Oxidizable Copper Block}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param blockName The {@link String Block base Name}
     * @return The {@link String Block Name}
     */
    public static String copperBlockName(final Oxidizable.OxidationLevel oxidationLevel, final Boolean isWaxed, final String blockName) {
        return (isWaxed ? "waxed_" : "") + BlockUtils.copperOxidationLevelName(oxidationLevel) + (Oxidizable.OxidationLevel.UNAFFECTED.equals(oxidationLevel) ? "" : "_") + "copper_" + blockName;
    }

    /**
     * Get the {@link String Copper Oxidation Level Name} for the provided {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @return The {@link String Copper Oxidation Level Name}
     */
    public static String copperOxidationLevelName(final Oxidizable.OxidationLevel oxidationLevel) {
        return switch (oxidationLevel) {
            case UNAFFECTED -> "";
            case EXPOSED -> "exposed";
            case WEATHERED -> "weathered";
            case OXIDIZED -> "oxidized";
        };
    }

}