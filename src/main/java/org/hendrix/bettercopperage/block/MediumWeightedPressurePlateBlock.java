package org.hendrix.bettercopperage.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.BlockUtils;

/**
 * {@link BetterCopperAge Better Copper Age} {@link WeightedPressurePlateBlock Copper Weighted Pressure Plate Block}
 */
public class MediumWeightedPressurePlateBlock extends WeightedPressurePlateBlock {

    /**
     * The {@link Oxidizable.OxidationLevel Pressure Plate Oxidation Level}
     */
    protected final Oxidizable.OxidationLevel oxidationLevel;

    /**
     * Constructor. Set the {@link Block Block} settings
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Pressure Plate Oxidation Level}
     * @param settings The {@link Settings Block settings}
     */
    public MediumWeightedPressurePlateBlock(final Oxidizable.OxidationLevel oxidationLevel, final Settings settings) {
        super(67, BlockSetType.COPPER, settings);
        this.oxidationLevel = oxidationLevel;
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
        if(BlockUtils.shouldResetPoweredState(state, oldState)) {
            world.setBlockState(pos, state.with(POWER, 0));
        }
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    /**
     * Get the {@link Integer Pressure Plate press ticks} based on its {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @return The {@link Integer Pressure Plate press ticks}
     */
    @Override
    public int getTickRate() {
        return switch (oxidationLevel) {
            case UNAFFECTED -> 20;
            case EXPOSED    -> 30;
            case WEATHERED  -> 40;
            case OXIDIZED   -> 50;
        };
    }

}
