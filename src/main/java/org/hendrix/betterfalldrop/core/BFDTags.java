package org.hendrix.betterfalldrop.core;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link TagKey Tags}
 */
public final class BFDTags {

    /**
     * {@link BetterFallDrop Better Fall Drop} {@link TagKey<Block> Block Tags}
     */
    public static class BlockTags {

        //#region Tags

        public static final TagKey<Block> COPPER_FIRE_BASE_BLOCKS = tag("copper_fire_base_blocks");

        //#endregion

        /**
         * Get a {@link TagKey<Block> Block Tag}
         *
         * @param name The {@link String Tag name}
         * @return The {@link TagKey<Block> Block Tag}
         */
        private static TagKey<Block> tag(final String name) {
            return TagKey.of(RegistryKeys.BLOCK, IdentifierUtils.modIdentifier(name));
        }
    }
}