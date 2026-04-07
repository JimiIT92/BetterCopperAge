package org.hendrix.bettercopperage.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge} {@link TagKey tags}
 */
public final class BCATags {

    /**
     * {@link BetterCopperAge} instrument {@link TagKey tags}
     */
    public static class InstrumentTags {

        //#region Tags

        public static final TagKey<Instrument> BASS_COPPER_HORNS = register("bass_copper_horns");
        public static final TagKey<Instrument> HARMONY_COPPER_HORNS = register("harmony_copper_horns");
        public static final TagKey<Instrument> MELODY_COPPER_HORNS = register("melody_copper_horns");
        public static final TagKey<Instrument> COPPER_HORNS = register("copper_horns");

        //#endregion

        /**
         * Register an instrument {@link TagKey}
         *
         * @param name The tag name
         * @return The instrument {@link TagKey}
         */
        private static TagKey<Instrument> register(final String name) {
            return TagKey.create(Registries.INSTRUMENT, IdentifierUtils.modded(name));
        }
    }

}