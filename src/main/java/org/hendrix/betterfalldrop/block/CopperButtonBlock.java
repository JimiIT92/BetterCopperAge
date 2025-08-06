package org.hendrix.betterfalldrop.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.utils.BlockUtils;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link ButtonBlock Copper Button Block}
 */
public final class CopperButtonBlock extends ButtonBlock implements BFDOxidizable {

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
    public CopperButtonBlock(final Oxidizable.OxidationLevel oxidationLevel, final boolean isWaxed, final Settings settings) {
        super(BlockSetType.COPPER, getPressTicks(oxidationLevel), settings);
        this.oxidationLevel = oxidationLevel;
        this.isWaxed = isWaxed;
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
        if(shouldResetButton(state, oldState)) {
            world.setBlockState(pos, state.with(POWERED, Boolean.FALSE));
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

    /**
     * Check if the {@link #POWERED Powered State} should be reset
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed or un-waxed}
     */
    public boolean shouldResetButton(final BlockState state, final BlockState oldState) {
        return isBeingWaxed(state, oldState) || isBeingUnwaxed(state, oldState);
    }

    /**
     * Check if the {@link Block Block} is being waxed
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being waxed}
     */
    private boolean isBeingWaxed(final BlockState state, final BlockState oldState) {
        return isWaxed && BlockUtils.isOxidizableBlock(oldState.getBlock()) && BlockUtils.isWaxedCopperBlock(state.getBlock());
    }

    /**
     * Check if the {@link Block Block} is being un-waxed
     *
     * @param state The {@link BlockState current Block State}
     * @param oldState The {@link BlockState previous Block State}
     *
     * @return {@link Boolean True if the Block is being un-waxed}
     */
    private boolean isBeingUnwaxed(final BlockState state, final BlockState oldState) {
        return !isWaxed && BlockUtils.isOxidizableBlock(state.getBlock()) && BlockUtils.isWaxedCopperBlock(oldState.getBlock());
    }

}