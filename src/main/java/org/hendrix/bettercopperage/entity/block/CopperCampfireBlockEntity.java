package org.hendrix.bettercopperage.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.hendrix.bettercopperage.core.BCABlockEntityTypes;
import org.jspecify.annotations.NonNull;

/**
 * Implementation class for a copper {@link CampfireBlockEntity}
 */
public final class CopperCampfireBlockEntity extends CampfireBlockEntity {

    /**
     * Constructor. Set the block entity properties
     *
     * @param worldPosition The block entity {@link BlockPos}
     * @param blockState The block entity {@link BlockState}
     */
    public CopperCampfireBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        super(worldPosition, blockState);
    }

    /**
     * Check whether the current {@link BlockState} is valid for the block entity
     *
     * @param blockState The current {@link BlockState}
     * @return {@link Boolean True} if the current {@link BlockState} is valid
     */
    @Override
    public boolean isValidBlockState(final @NonNull BlockState blockState) {
        return this.getType().isValid(blockState);
    }

    /**
     * Get the block entity type
     *
     * @return {@link BCABlockEntityTypes#COPPER_CAMPFIRE}
     */
    @Override
    public @NonNull BlockEntityType<?> getType() {
        return BCABlockEntityTypes.COPPER_CAMPFIRE;
    }

}