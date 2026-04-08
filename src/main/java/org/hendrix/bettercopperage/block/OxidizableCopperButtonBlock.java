package org.hendrix.bettercopperage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper button that can oxidize
 */
public final class OxidizableCopperButtonBlock extends CopperButtonBlock implements WeatheringCopper {

    /**
     * The {@link WeatherState}
     */
    private final WeatherState weatherState;

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties} and the press ticks
     * based on the {@link WeatherState}
     *
     * @param weatherState The {@link WeatherState}
     * @param properties   The {@link BlockBehaviour.Properties}
     */
    public OxidizableCopperButtonBlock(WeatherState weatherState, Properties properties) {
        super(weatherState, properties);
        this.weatherState = weatherState;
    }

    /**
     * Make the block oxidize over time
     *
     * @param state The current {@link BlockState}
     * @param level The {@link ServerLevel} reference
     * @param pos The current {@link BlockPos}
     * @param random The {@link RandomSource}
     */
    @Override
    protected void randomTick(final @NonNull BlockState state, final @NonNull ServerLevel level, final @NonNull BlockPos pos, final @NonNull RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    /**
     * Check if the block should tick
     *
     * @param state The current {@link BlockState}
     * @return {@link Boolean True} if the block is not fully oxidized
     */
    @Override
    protected boolean isRandomlyTicking(final @NonNull BlockState state) {
        return !WeatherState.OXIDIZED.equals(this.weatherState);
    }

    /**
     * Get the {@link WeatherState}
     *
     * @return The {@link WeatherState}
     */
    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }

}