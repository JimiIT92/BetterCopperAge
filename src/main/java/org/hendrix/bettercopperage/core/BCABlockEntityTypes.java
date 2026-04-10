package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.entity.block.CopperCampfireBlockEntity;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge} {@link BlockEntityType Block Entity Types}
 */
public final class BCABlockEntityTypes {

    //#region Block Entity Types

    public static final BlockEntityType<CopperCampfireBlockEntity> COPPER_CAMPFIRE = register("campfire", CopperCampfireBlockEntity::new, BCABlocks.COPPER_CAMPFIRE);

    //#endregion

    /**
     * Register a {@link BlockEntityType}
     *
     * @param name The block entity name
     * @param entityFactory The {@link FabricBlockEntityTypeBuilder.Factory}
     * @param blocks The block entity valid blocks
     * @return The registered {@link BlockEntityType}
     * @param <T> The block entity type
     */
    private static <T extends BlockEntity> BlockEntityType<T> register(final String name, final FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, final Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IdentifierUtils.modded(name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    /**
     * Register all {@link BlockEntityType}
     */
    public static void register() {

    }

}