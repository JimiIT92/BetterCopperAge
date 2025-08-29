package org.hendrix.bettercopperage.entity.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.core.BCABlockEntityTypes;

/**
 * {@link BetterCopperAge Better Copper Age} {@link CampfireBlockEntity Campfire Block Entity}
 */
public final class BCACampfireBlockEntity extends CampfireBlockEntity {

    /**
     * Constructor. Set the Block Entity {@link BlockPos Block Pos} and {@link BlockState Block State}
     *
     * @param pos The {@link BlockPos Entity Block Pos}
     * @param state The {@link BlockState Block State}
     */
    public BCACampfireBlockEntity(final BlockPos pos, final BlockState state) {
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
     * @return The {@link BCABlockEntityTypes#CAMPFIRE Campfire Block Entity Type}
     */
    @Override
    public BlockEntityType<?> getType() {
        return BCABlockEntityTypes.CAMPFIRE;
    }

}