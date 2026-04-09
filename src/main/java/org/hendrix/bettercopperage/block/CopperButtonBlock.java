package org.hendrix.bettercopperage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.hendrix.bettercopperage.utils.BlockUtils;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper button
 */
public class CopperButtonBlock extends ButtonBlock {

    /**
     * The {@link WeatheringCopper.WeatherState}
     */
    private final WeatheringCopper.WeatherState weatherState;

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties} and the press ticks
     * based on the {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param properties The {@link BlockBehaviour.Properties}
     */
    public CopperButtonBlock(final WeatheringCopper.WeatherState weatherState, final Properties properties) {
        super(BlockSetType.COPPER, getPressTicks(weatherState), properties);
        this.weatherState = weatherState;
    }

    /**
     * Get the press ticks based on the {@link WeatheringCopper.WeatherState}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @return The button press ticks
     */
    private static int getPressTicks(final WeatheringCopper.WeatherState weatherState) {
        return switch (weatherState) {
            case UNAFFECTED -> 20;
            case EXPOSED -> 30;
            case WEATHERED -> 40;
            case OXIDIZED -> 50;
        };
    }

    /**
     * Reset the button after it has been waxed or un-waxed
     *
     * @param state The current {@link BlockState}
     * @param level The {@link Level} reference
     * @param pos The current {@link BlockPos}
     * @param oldState The previous {@link BlockState}
     * @param movedByPiston Whether the block has been moved by a piston
     */
    @Override
    protected void onPlace(final @NonNull BlockState state, final @NonNull Level level, final @NonNull BlockPos pos, final @NonNull BlockState oldState, final boolean movedByPiston) {
        if(BlockUtils.shouldResetPoweredState(state, oldState)) {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, false));
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    /**
     * Get the {@link WeatheringCopper.WeatherState}
     *
     * @return The {@link WeatheringCopper.WeatherState}
     */
    public WeatheringCopper.WeatherState getWeatherState() {
        return this.weatherState;
    }

}