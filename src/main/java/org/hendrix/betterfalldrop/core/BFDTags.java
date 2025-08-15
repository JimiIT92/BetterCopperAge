package org.hendrix.betterfalldrop.core;

import net.minecraft.block.Block;
import net.minecraft.item.Instrument;
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
        public static final TagKey<Block> COPPER_GOLEM_OUTPUT_CONTAINERS = tag("copper_golem_output_containers");
        public static final TagKey<Block> INVALID_FOR_WRENCH = tag("invalid_for_wrench");

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

    /**
     * {@link BetterFallDrop Better Fall Drop} {@link TagKey<Instrument> Instrument Tags}
     */
    public static class InstrumentTags {

        //#region Tags

        public static final TagKey<Instrument> BASS_COPPER_HORNS = tag("bass_copper_horns");
        public static final TagKey<Instrument> HARMONY_COPPER_HORNS = tag("harmony_copper_horns");
        public static final TagKey<Instrument> MELODY_COPPER_HORNS = tag("melody_copper_horns");
        public static final TagKey<Instrument> COPPER_HORNS = tag("copper_horns");

        //#endregion

        /**
         * Get a {@link TagKey<Instrument> Instrument Tag}
         *
         * @param name The {@link String Tag name}
         * @return The {@link TagKey<Instrument> Instrument Tag}
         */
        private static TagKey<Instrument> tag(final String name) {
            return TagKey.of(RegistryKeys.INSTRUMENT, IdentifierUtils.modIdentifier(name));
        }
    }

}