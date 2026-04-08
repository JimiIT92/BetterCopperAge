package org.hendrix.bettercopperage.utils;

import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;

/**
 * Utility methods for {@link Block blocks}
 */
public final class BlockUtils {

    /**
     * Get the name of a copper block based on its {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @param blockName The base block name
     * @return The copper block name
     */
    public static String copperBlockName(final WeatheringCopper.WeatherState weatherState, final Boolean isWaxed, final String blockName) {
        return oxidizableBlockName(weatherState, isWaxed, "copper", blockName);
    }

    /**
     * Get the name of a copper block based on its {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @param oxidizableBlockName The base oxidizable block name
     * @param blockName The base block name
     * @return The copper block name
     */
    public static String oxidizableBlockName(final WeatheringCopper.WeatherState weatherState, final Boolean isWaxed, final String oxidizableBlockName, final String blockName) {
        return (isWaxed ? "waxed_" : "") + weatherStateName(weatherState) + (WeatheringCopper.WeatherState.UNAFFECTED.equals(weatherState) ? "" : "_") + oxidizableBlockName + "_" + blockName;
    }

    /**
     * Get the name of a {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @return The {@link WeatheringCopper.WeatherState} name
     */
    public static String weatherStateName(final WeatheringCopper.WeatherState weatherState) {
        if(WeatheringCopper.WeatherState.UNAFFECTED.equals(weatherState)) {
            return "";
        }
        return weatherState.getSerializedName().toLowerCase(Locale.ROOT);
    }

    /**
     * Get the {@link MapColor} of a {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @return The {@link WeatheringCopper.WeatherState} {@link MapColor}
     */
    public static MapColor weatherStateMapColor(final WeatheringCopper.WeatherState weatherState) {
        return switch (weatherState) {
            case UNAFFECTED -> Blocks.COPPER_BLOCK.defaultMapColor();
            case EXPOSED -> Blocks.EXPOSED_COPPER.defaultMapColor();
            case WEATHERED -> Blocks.WEATHERED_COPPER.defaultMapColor();
            case OXIDIZED -> Blocks.OXIDIZED_COPPER.defaultMapColor();
        };
    }

    /**
     * Check whether the powered state of a button or a pressure plate
     * should be reset after being waxed or un-waxed
     *
     * @param blockState The current {@link BlockState}
     * @param oldState The previous {@link BlockState}
     * @return {@link Boolean True} if the block's power state should be reset
     */
    public static Boolean shouldResetPoweredState(final BlockState blockState, final BlockState oldState) {
        return haveDifferentWeatherState(blockState, oldState) || isBeingWaxed(blockState, oldState) || isBeingUnwaxed(blockState, oldState);
    }

    /**
     * Check whether two blocks have different {@link WeatheringCopper.WeatherState}
     *
     * @param blockState The current {@link BlockState}
     * @param oldState The previous {@link BlockState}
     * @return {@link Boolean True} if the blocks have different {@link WeatheringCopper.WeatherState}
     */
    private static Boolean haveDifferentWeatherState(final BlockState blockState, final BlockState oldState) {
        if(blockState.getBlock() instanceof WeatheringCopper block && oldState.getBlock() instanceof WeatheringCopper oldBlock) {
            return !block.getAge().equals(oldBlock.getAge());
        }
        return false;
    }

    /**
     * Check whether a block is being waxed
     *
     * @param blockState The current {@link BlockState}
     * @param oldState The previous {@link BlockState}
     * @return {@link Boolean True} if the block is being waxed
     */
    private static Boolean isBeingWaxed(final BlockState blockState, final BlockState oldState) {
        return HoneycombItem.WAXABLES.get().getOrDefault(blockState.getBlock(), Blocks.AIR).equals(oldState.getBlock());
    }

    /**
     * Check whether a block is being un-waxed
     *
     * @param blockState The current {@link BlockState}
     * @param oldState The previous {@link BlockState}
     * @return {@link Boolean True} if the block is being un-waxed
     */
    private static Boolean isBeingUnwaxed(final BlockState blockState, final BlockState oldState) {
        return HoneycombItem.WAX_OFF_BY_BLOCK.get().getOrDefault(blockState.getBlock(), Blocks.AIR).equals(oldState.getBlock());
    }

}