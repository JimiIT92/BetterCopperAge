package org.hendrix.bettercopperage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.hendrix.bettercopperage.utils.BlockUtils;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a medium {@link WeightedPressurePlateBlock}
 */
public class MediumWeightedPressurePlateBlock extends WeightedPressurePlateBlock {

    /**
     * The {@link WeatheringCopper.WeatherState}
     */
    protected final WeatheringCopper.WeatherState weatherState;

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param properties The {@link BlockBehaviour.Properties}
     */
    public MediumWeightedPressurePlateBlock(final WeatheringCopper.WeatherState weatherState, final Properties properties) {
        super(67, BlockSetType.COPPER, properties);
        this.weatherState = weatherState;
    }

    /**
     * Reset the pressure plate after it has been waxed or un-waxed
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
            level.setBlockAndUpdate(pos, state.setValue(POWER, 0));
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    /**
     * Get the pressed time based on the {@link WeatheringCopper.WeatherState}
     *
     * @return The {@link WeatheringCopper.WeatherState}
     */
    @Override
    protected int getPressedTime() {
        return switch (weatherState) {
            case UNAFFECTED -> 20;
            case EXPOSED -> 30;
            case WEATHERED -> 40;
            case OXIDIZED -> 50;
        };
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
