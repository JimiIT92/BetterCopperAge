package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.entity.block.BCACampfireBlockEntity;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge Better Copper Age} {@link BlockEntityType Block Entity Types}
 */
public final class BCABlockEntityTypes {

    //#region Block Entity Types

    public static final BlockEntityType<BCACampfireBlockEntity> CAMPFIRE = registerBlockEntityType("campfire", BCACampfireBlockEntity::new, BCABlocks.COPPER_CAMPFIRE);

    //#endregion

    /**
     * Register a {@link BlockEntityType Block Entity Type}
     *
     * @param name The {@link String Block Entity Type Name}
     * @param factory The {@link FabricBlockEntityTypeBuilder.Factory Block Entity Type Builder Factory}
     * @param blocks The {@link Block Blocks related to this Block Entity Type}
     * @return The {@link BlockEntityType<T> registered Block Entity Type}
     * @param <T> The {@link T Block Entity Type class}
     */
    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(final String name, final FabricBlockEntityTypeBuilder.Factory<T> factory, final Block... blocks) {
        final Identifier identifier = IdentifierUtils.modIdentifier(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    /**
     * Register all {@link BlockEntityType Block Entity Types}
     */
    public static void register() {

    }

}