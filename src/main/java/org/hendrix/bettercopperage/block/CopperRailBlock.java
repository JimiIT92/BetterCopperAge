package org.hendrix.bettercopperage.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper rail block
 */
public class CopperRailBlock extends BaseRailBlock {

    /**
     * The {@link WeatheringCopper.WeatherState}
     */
    protected final WeatheringCopper.WeatherState weatherState;
    /**
     * The {@link RailShape} property
     */
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;

    /**
     * The {@link MapCodec<CopperRailBlock> Copper Rail Block} Codec
     */
    public static final MapCodec<CopperRailBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                        WeatheringCopper.WeatherState.CODEC.fieldOf("oxidationLevel").forGetter(CopperRailBlock::getWeatherState),
                        propertiesCodec()
            ).apply(instance, CopperRailBlock::new)
    );

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties}
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param properties The {@link BlockBehaviour.Properties}
     */
    public CopperRailBlock(final WeatheringCopper.WeatherState weatherState, final Properties properties) {
        super(true, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED, false));
        this.weatherState = weatherState;
    }

    /**
     * Rotate the rail
     *
     * @param state The current {@link BlockState}
     * @param rotation The applied {@link Rotation}
     * @return The rotated {@link BlockState}
     */
    @Override
    protected @NonNull BlockState rotate(final BlockState state, final @NonNull Rotation rotation) {
        return state.setValue(SHAPE, this.rotate(state.getValue(SHAPE), rotation));
    }

    /**
     * Mirror the rail
     *
     * @param state The current {@link BlockState}
     * @param mirror The applied {@link Mirror}
     * @return The rotated {@link BlockState}
     */
    @Override
    protected @NonNull BlockState mirror(final BlockState state, final @NonNull Mirror mirror) {
        return state.setValue(SHAPE, this.mirror(state.getValue(SHAPE), mirror));
    }

    /**
     * Register the block's properties
     *
     * @param builder The state builder
     */
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    /**
     * Get the block's codec
     *
     * @return The block's codec
     */
    @Override
    public @NonNull MapCodec<CopperRailBlock> codec() {
        return CODEC;
    }

    /**
     * Get the rail shape
     *
     * @return {@link BlockStateProperties#RAIL_SHAPE_STRAIGHT}
     */
    @Override
    public @NonNull Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    /**
     * Get the {@link WeatheringCopper.WeatherState}
     *
     * @return The {@link WeatheringCopper.WeatherState}
     */
    public WeatheringCopper.WeatherState getWeatherState() {
        return this.weatherState;
    }

    /**
     * Get the horizontal velocity modifier based on the current {@link WeatheringCopper.WeatherState}
     *
     * @return The horizontal velocity modifier
     */
    public double getHorizontalVelocityModifier() {
        return switch (this.weatherState) {
            case UNAFFECTED -> 0.04D;
            case EXPOSED -> 0.03D;
            case WEATHERED -> 0.02D;
            case OXIDIZED -> 0.01D;
        };
    }

    public double getSlopeVelocityMultiplier(final boolean useNewBehavior) {
        return switch (this.weatherState) {
            case UNAFFECTED -> useNewBehavior ? 0.01D : 0.015D;
            case EXPOSED -> useNewBehavior ? 0.05D : 0.01D;
            case WEATHERED -> useNewBehavior ? 0.025D : 0.05D;
            case OXIDIZED -> 0.001D;
        };
    }

}
