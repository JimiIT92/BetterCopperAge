package org.hendrix.betterfalldrop.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.core.BFDBlockEntityTypes;
import org.hendrix.betterfalldrop.entity.block.BFDCampfireBlockEntity;
import org.jetbrains.annotations.Nullable;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link CampfireBlock Copper Campfire Block}
 */
public final class CopperCampfireBlock extends CampfireBlock {

    /**
     * Constructor. Set the {@link Settings Block Settings}
     *
     * @param settings The {@link Settings Block Settings}
     */
    public CopperCampfireBlock(final Settings settings) {
        super(false, 1, settings);
    }

    /**
     * Create the {@link BlockEntity Block Entity}
     *
     * @param pos The {@link BlockPos current Block Pos}
     * @param state The {@link BlockState current Block State}
     * @return The {@link BlockEntity Block Entity}
     */
    @Override
    public BlockEntity createBlockEntity(final BlockPos pos, final BlockState state) {
        return new BFDCampfireBlockEntity(pos, state);
    }

    /**
     * Get the {@link BlockEntityTicker<T> Block Entity Ticker}
     *
     * @param world The {@link World World reference}
     * @param state The {@link BlockState current Block State}
     * @param type The {@link BlockEntityType<T> Block Entity Type}
     * @return The {@link BlockEntityTicker<T> Block Entity Ticker}
     * @param <T> The {@link T Block Entity Type class}
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final World world, final BlockState state, final BlockEntityType<T> type) {
        final BlockEntityType<BFDCampfireBlockEntity> campfireBlockEntityType = BFDBlockEntityTypes.CAMPFIRE;
        if (world instanceof ServerWorld serverWorld) {
            if (state.get(LIT)) {
                final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, CampfireCookingRecipe> recipeMatchGetter = ServerRecipeManager.createCachedMatchGetter(RecipeType.CAMPFIRE_COOKING);
                return validateTicker(
                        type,
                        campfireBlockEntityType,
                        (w, pos, blockState, blockEntity) ->
                                BFDCampfireBlockEntity.litServerTick(serverWorld, pos, blockState, blockEntity, recipeMatchGetter)
                );
            }
            return validateTicker(type, campfireBlockEntityType, BFDCampfireBlockEntity::unlitServerTick);
        }
        return state.get(LIT) ? validateTicker(type, campfireBlockEntityType, BFDCampfireBlockEntity::clientTick) : null;
    }

}