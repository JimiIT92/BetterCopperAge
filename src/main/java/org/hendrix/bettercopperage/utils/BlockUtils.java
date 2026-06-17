package org.hendrix.bettercopperage.utils;

import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

/**
 * Utility methods for {@link Block blocks}
 */
public final class BlockUtils {

    /**
     * Get the {@link MapColor} of a {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @return The {@link WeatheringCopper.WeatherState} {@link MapColor}
     */
    public static MapColor weatherStateMapColor(final WeatheringCopper.WeatherState weatherState) {
        return Blocks.COPPER_BLOCK.weathering().pick(weatherState).defaultMapColor();
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