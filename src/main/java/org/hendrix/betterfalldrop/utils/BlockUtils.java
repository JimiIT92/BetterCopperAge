package org.hendrix.betterfalldrop.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;

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