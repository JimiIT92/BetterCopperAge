package org.hendrix.betterfalldrop.entity.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.core.BFDBlockEntityTypes;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link CampfireBlockEntity Campfire Block Entity}
 */
public final class BFDCampfireBlockEntity extends CampfireBlockEntity {

    /**
     * Constructor. Set the Block Entity {@link BlockPos Block Pos} and {@link BlockState Block State}
     *
     * @param pos The {@link BlockPos Entity Block Pos}
     * @param state The {@link BlockState Block State}
     */
    public BFDCampfireBlockEntity(final BlockPos pos, final BlockState state) {
        super(pos, state);
    }

    /**
     * Check whether the provided {@link BlockState Block State} is supported by the {@link BlockEntity Block Entity}
     *
     * @param state The {@link BlockState current Block State}
     * @return {@link Boolean True if the Block State is supported}
     */
    @Override
    public boolean supports(final BlockState state) {
        return this.getType().supports(state);
    }

    /**
     * Get the {@link BlockEntityType Block Entity Type}
     *
     * @return The {@link BFDBlockEntityTypes#CAMPFIRE Campfire Block Entity Type}
     */
    @Override
    public BlockEntityType<?> getType() {
        return BFDBlockEntityTypes.CAMPFIRE;
    }

}