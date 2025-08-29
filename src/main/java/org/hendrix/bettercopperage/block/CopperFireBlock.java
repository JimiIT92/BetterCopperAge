package org.hendrix.bettercopperage.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.core.BCATags;

/**
 * {@link BetterCopperAge Better Copper Age} {@link AbstractFireBlock Copper Fire Block}
 */
public final class CopperFireBlock extends AbstractFireBlock {

    /**
     * The {@link MapCodec<CopperFireBlock> Block Codec}
     */
    public static final MapCodec<CopperFireBlock> CODEC = createCodec(CopperFireBlock::new);

    /**
     * Constructor. Set the {@link Settings Block Settings}
     *
     * @param settings The {@link Settings Block Settings}
     */
    public CopperFireBlock(final Settings settings) {
        super(settings, 1.5F);
    }

    /**
     * Get the {@link MapCodec<AbstractFireBlock> Block Codec}
     *
     * @return The {@link #CODEC Block Codec}
     */
    @Override
    protected MapCodec<? extends AbstractFireBlock> getCodec() {
        return CODEC;
    }

    /**
     * Get the {@link BlockState Block State} of the neighbor Block when an update occurs
     *
     * @param state The {@link BlockState current Block State}
     * @param world The {@link WorldView World reference}
     * @param tickView The {@link ScheduledTickView Tick View reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @param direction The {@link Direction updated direction }
     * @param neighborPos The {@link BlockPos neighbor Block Pos}
     * @param neighborState The {@link BlockState neighbor Block State}
     * @param random The {@link Random Random reference}
     * @return The {@link BlockState updated Block State}
     */
    protected BlockState getStateForNeighborUpdate(final BlockState state, final WorldView world, final ScheduledTickView tickView, final BlockPos pos, final Direction direction, final BlockPos neighborPos, final BlockState neighborState, final Random random) {
        return this.canPlaceAt(state, world, pos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    /**
     * Check if the {@link Block Block} can be placed at the {@link BlockPos current Block Pos}
     *
     * @param state The {@link BlockState current Block State}
     * @param world The {@link WorldView World reference}
     * @param pos The {@link BlockPos current Block Pos}
     * @return {@link Boolean True if the Block below is valid}
     */
    protected boolean canPlaceAt(final BlockState state, final WorldView world, final BlockPos pos) {
        return isCopperBase(world.getBlockState(pos.down()));
    }

    /**
     * Check if a {@link Block Block} can support this {@link AbstractFireBlock Fire Block}
     *
     * @param state The {@link BlockState Block State to check}
     * @return {@link Boolean True if is in the Copper Fire Base Blocks Tag}
     */
    public static boolean isCopperBase(final BlockState state) {
        return state.isIn(BCATags.BlockTags.COPPER_FIRE_BASE_BLOCKS);
    }

    /**
     * Check if the {@link Block Block} is flammable
     *
     * @param state The {@link BlockState current Block State}
     * @return {@link Boolean#TRUE True}
     */
    protected boolean isFlammable(final BlockState state) {
        return true;
    }

}