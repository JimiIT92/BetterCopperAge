package org.hendrix.bettercopperage.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.hendrix.bettercopperage.BetterCopperAge;

/**
 * {@link BetterCopperAge Better Copper Age} {@link WeightedPressurePlateBlock Copper Weighted Pressure Plate Block}
 */
public final class OxidizableMediumWeightedPressurePlateBlock extends MediumWeightedPressurePlateBlock implements Oxidizable {

    /**
     * Constructor. Set the {@link Block Block} settings
     *
     * @param oxidationLevel The {@link OxidationLevel medium weighted Pressure Plate Oxidation Level}
     * @param settings The {@link Settings Block settings}
     */
    public OxidizableMediumWeightedPressurePlateBlock(final OxidationLevel oxidationLevel, final Settings settings) {
        super(oxidationLevel, settings);
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
        return !OxidationLevel.OXIDIZED.equals(this.oxidationLevel);
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

}