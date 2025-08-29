package org.hendrix.bettercopperage.utils;

import net.minecraft.block.*;
import net.minecraft.item.HoneycombItem;

/**
 * Utility methods for {@link Block Blocks}
 */
public final class BlockUtils {

    /**
     * Get the {@link String Block Name} of a modded {@link Oxidizable Copper Block}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param blockName The {@link String Block base Name}
     * @return The {@link String Block Name}
     */
    public static String copperBlockName(final Oxidizable.OxidationLevel oxidationLevel, final Boolean isWaxed, final String blockName) {
        return oxidizableBlockName(oxidationLevel, isWaxed, "copper", blockName);
    }

    /**
     * Get the {@link String Block Name} of a modded {@link Oxidizable Oxidizable Block}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @param isWaxed {@link Boolean Whether the Block is waxed}
     * @param oxidizableBlockName The {@link String Oxidizable Block Name}
     * @param blockName The {@link String Block Name}
     * @return The {@link String Block Name}
     */
    public static String oxidizableBlockName(final Oxidizable.OxidationLevel oxidationLevel, final Boolean isWaxed, final String oxidizableBlockName, final String blockName) {
        return (isWaxed ? "waxed_" : "") + BlockUtils.copperOxidationLevelName(oxidationLevel) + (Oxidizable.OxidationLevel.UNAFFECTED.equals(oxidationLevel) ? "" : "_") + oxidizableBlockName + "_" + blockName;
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

    /**
     * Get the {@link MapColor Map Color} of an {@link Oxidizable Oxidizable Block} based on the {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @return The {@link MapColor Map Color}
     */
    public static MapColor oxidizableMapColor(final Oxidizable.OxidationLevel oxidationLevel) {
        return switch (oxidationLevel) {
            case UNAFFECTED -> MapColor.ORANGE;
            case EXPOSED -> MapColor.TERRACOTTA_LIGHT_GRAY;
            case WEATHERED -> MapColor.DARK_AQUA;
            case OXIDIZED -> MapColor.TEAL;
        };
    }

    /**
     * Check if the Powered State of an {@link Oxidizable Oxidizable Block} should be reset
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed or un-waxed}
     */
    public static Boolean shouldResetPoweredState(final BlockState state, final BlockState oldState) {
        return haveDifferentOxidationLevels(oldState, state) || isBeingWaxed(oldState, state) || isBeingUnwaxed(oldState, state);
    }

    /**
     * Check if two {@link Block Blocks} have different {@link Oxidizable.OxidationLevel Oxidation Levels}
     *
     * @param oldState The {@link BlockState previous Block State}
     * @param state The {@link BlockState current Block State}
     * @return {@link Boolean True if both Blocks are Oxidizable and have different Oxidation Levels}
     */
    private static boolean haveDifferentOxidationLevels(final BlockState oldState, final BlockState state) {
        if(oldState.getBlock() instanceof Oxidizable oldBlock && state.getBlock() instanceof Oxidizable newBlock) {
            return !oldBlock.getDegradationLevel().equals(newBlock.getDegradationLevel());
        }
        return false;
    }

    /**
     * Check if the {@link Block Block} is being waxed
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed}
     */
    private static Boolean isBeingWaxed(final BlockState state, final BlockState oldState) {
        return HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().getOrDefault(oldState.getBlock(), Blocks.AIR).equals(state.getBlock());
    }

    /**
     * Check if the {@link Block Block} is being un-waxed
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being un-waxed}
     */
    private static boolean isBeingUnwaxed(final BlockState state, final BlockState oldState) {
        return HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().getOrDefault(oldState.getBlock(), Blocks.AIR).equals(state.getBlock());
    }

}