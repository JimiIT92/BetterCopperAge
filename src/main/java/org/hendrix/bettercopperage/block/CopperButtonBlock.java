package org.hendrix.bettercopperage.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.BlockUtils;

/**
 * {@link BetterCopperAge Better Copper Age} {@link ButtonBlock Copper Button Block}
 */
public class CopperButtonBlock extends ButtonBlock {

    /**
     * Constructor. Set the {@link Block Block} settings
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel button Oxidation Level}
     * @param settings The {@link Settings Block settings}
     */
    public CopperButtonBlock(final Oxidizable.OxidationLevel oxidationLevel, final Settings settings) {
        super(BlockSetType.COPPER, getPressTicks(oxidationLevel), settings);
    }

    /**
     * Get the {@link Integer button press ticks} based on its {@link Oxidizable.OxidationLevel Oxidation Level}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @return The {@link Integer button press ticks}
     */
    private static int getPressTicks(final Oxidizable.OxidationLevel oxidationLevel) {
        return switch (oxidationLevel) {
            case UNAFFECTED -> 20;
            case EXPOSED    -> 30;
            case WEATHERED  -> 40;
            case OXIDIZED   -> 50;
        };
    }

    /**
     * Reset the {@link #POWERED Powered State} when the {@link Block Block} is
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
            world.setBlockState(pos, state.with(POWERED, Boolean.FALSE));
        }
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

}