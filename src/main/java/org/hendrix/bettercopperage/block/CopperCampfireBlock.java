package org.hendrix.bettercopperage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.hendrix.bettercopperage.core.BCABlockEntityTypes;
import org.hendrix.bettercopperage.entity.block.CopperCampfireBlockEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Implementation class for a copper {@link CampfireBlock}
 */
public final class CopperCampfireBlock extends CampfireBlock {

    /**
     * Constructor. Set the {@link BlockBehaviour.Properties}
     *
     * @param properties The {@link BlockBehaviour.Properties}
     */
    public CopperCampfireBlock(final Properties properties) {
        super(false, 1, properties);
    }

    /**
     * Create the {@link BlockEntity} for this campfire
     *
     * @param worldPosition The current {@link BlockPos}
     * @param blockState The current {@link BlockState}
     * @return The campfire {@link BlockEntity}
     */
    @Override
    public @NonNull BlockEntity newBlockEntity(final @NonNull BlockPos worldPosition, final @NonNull BlockState blockState) {
        return new CopperCampfireBlockEntity(worldPosition, blockState);
    }

    /**
     * Get the campfire {@link BlockEntityTicker}
     *
     * @param level The {@link Level} reference
     * @param blockState The current {@link BlockState}
     * @param type The {@link BlockEntityType}
     * @return The campfire {@link BlockEntityTicker}
     * @param <T> The block entity type
     */
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        final BlockEntityType<CopperCampfireBlockEntity> blockEntityType = BCABlockEntityTypes.COPPER_CAMPFIRE;
        if (level instanceof ServerLevel serverLevel) {
            if (blockState.getValue(LIT)) {
                final RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
                return createTickerHelper(
                        type,
                        blockEntityType,
                        (_, pos, state, entity) -> CampfireBlockEntity.cookTick(serverLevel, pos, state, entity, quickCheck));
            }
            return createTickerHelper(type, blockEntityType, CampfireBlockEntity::cooldownTick);
        }
        return blockState.getValue(LIT) ? createTickerHelper(type, blockEntityType, CampfireBlockEntity::particleTick) : null;
    }

}