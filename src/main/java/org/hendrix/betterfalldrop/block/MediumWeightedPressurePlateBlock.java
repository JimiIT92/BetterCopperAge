package org.hendrix.betterfalldrop.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.hendrix.betterfalldrop.BetterFallDrop;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link WeightedPressurePlateBlock Copper Weighted Pressure Plate Block}
 */
public final class MediumWeightedPressurePlateBlock extends WeightedPressurePlateBlock implements BFDOxidizable {

    /**
     * The {@link Oxidizable.OxidationLevel button Oxidation Level}
     */
    private final Oxidizable.OxidationLevel oxidationLevel;
    /**
     * {@link Boolean Whether the button is waxed}
     */
    private final boolean isWaxed;

    /**
     * Constructor. Set the {@link Block Block} settings
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel button Oxidation Level}
     * @param isWaxed {@link Boolean Whether the button is waxed}
     * @param settings The {@link Settings Block settings}
     */
    public MediumWeightedPressurePlateBlock(final Oxidizable.OxidationLevel oxidationLevel, final boolean isWaxed, Settings settings) {
        super(67, BlockSetType.COPPER, settings);
        this.oxidationLevel = oxidationLevel;
        this.isWaxed = isWaxed;
    }

    /**
     * Tick the {@link Block Block}
     *
     * @param state The {@link BlockState current Block State}
     * @param world The {@link ServerWorld World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param random The {@link Random Random reference}
     */
    protected void randomTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    /**
     * Check if the {@link Block Block} should tick
     *
     * @param state The {@link BlockState current Block State}
     * @return {@link Boolean True if the Block should tick}
     */
    @Override
    protected boolean hasRandomTicks(final BlockState state) {
        return !isWaxed && !OxidationLevel.OXIDIZED.equals(this.oxidationLevel);
    }

    /**
     * Reset the {@link #POWER Power State} when the {@link Block Block} is
     * placed due to it being waxed or un-waxed
     *
     * @param state The {@link BlockState current Block State}
     * @param world The {@link World World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param oldState The {@link BlockState previous Block State}
     * @param notify {@link Boolean Whether the client should be notified}
     */
    @Override
    protected void onBlockAdded(final BlockState state, final World world, final BlockPos pos, final BlockState oldState, final boolean notify) {
        if(shouldResetPoweredState(isWaxed, state, oldState)) {
            world.setBlockState(pos, state.with(POWER, 0));
        }
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    /**
     * Get the {@link OxidationLevel Block Oxidation Level}
     *
     * @return The {@link OxidationLevel Block Oxidation Level}
     */
    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }

    /**
     * Check if the {@link Block Block} is waxed
     *
     * @return {@link Boolean True if the Block is waxed}
     */
    public boolean isWaxed() {
        return isWaxed;
    }

}
