package org.hendrix.bettercopperage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper rail that can oxidize
 */
public final class OxidizableCopperRailBlock extends CopperRailBlock implements WeatheringCopper {

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties}
     *
     * @param weatherState The {@link WeatherState}
     * @param properties   The {@link BlockBehaviour.Properties}
     */
    public OxidizableCopperRailBlock(final WeatherState weatherState, final Properties properties) {
        super(weatherState, properties);
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
        return !WeatherState.OXIDIZED.equals(this.getWeatherState());
    }

    /**
     * Get the {@link WeatherState}
     *
     * @return The {@link WeatherState}
     */
    @Override
    public WeatherState getAge() {
        return this.getWeatherState();
    }

}
