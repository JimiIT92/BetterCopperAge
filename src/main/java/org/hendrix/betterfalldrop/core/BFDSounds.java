package org.hendrix.betterfalldrop.core;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link SoundEvent Sounds}
 */
public final class BFDSounds {

    //#region Sounds

    public static final RegistryEntry.Reference<SoundEvent> GOAT_HORN_FLY = registerGoatHorn("fly");
    public static final RegistryEntry.Reference<SoundEvent> GOAT_HORN_RESIST = registerGoatHorn("resist");

    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_GREAT_SKY_FALLING = registerCopperHorn("great_sky_falling");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_GREAT_SKY_FALLING_BASS = registerCopperHorn("great_sky_falling_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_GREAT_SKY_FALLING_HARMONY = registerCopperHorn("great_sky_falling_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_OLD_HYMN_RESTING = registerCopperHorn("old_hymn_resting");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_OLD_HYMN_RESTING_BASS = registerCopperHorn("old_hymn_resting_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_OLD_HYMN_RESTING_HARMONY = registerCopperHorn("old_hymn_resting_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_PURE_WATER_DESIRE = registerCopperHorn("pure_water_desire");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_PURE_WATER_DESIRE_BASS = registerCopperHorn("pure_water_desire_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_PURE_WATER_DESIRE_HARMONY = registerCopperHorn("pure_water_desire_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_MUMBLE_FIRE_MEMORY = registerCopperHorn("mumble_fire_memory");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_MUMBLE_FIRE_MEMORY_BASS = registerCopperHorn("mumble_fire_memory_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_MUMBLE_FIRE_MEMORY_HARMONY = registerCopperHorn("mumble_fire_memory_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_DRY_URGE_ANGER = registerCopperHorn("dry_urge_anger");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_DRY_URGE_ANGER_BASS = registerCopperHorn("dry_urge_anger_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_DRY_URGE_ANGER_HARMONY = registerCopperHorn("dry_urge_anger_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_CLEAR_TEMPER_JOURNEY = registerCopperHorn("clear_temper_journey");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_CLEAR_TEMPER_JOURNEY_BASS = registerCopperHorn("clear_temper_journey_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_CLEAR_TEMPER_JOURNEY_HARMONY = registerCopperHorn("clear_temper_journey_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FRESH_NEST_THOUGHT = registerCopperHorn("fresh_nest_thought");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FRESH_NEST_THOUGHT_BASS = registerCopperHorn("fresh_nest_thought_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FRESH_NEST_THOUGHT_HARMONY = registerCopperHorn("fresh_nest_thought_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SECRET_LAKE_TEAR = registerCopperHorn("secret_lake_tear");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SECRET_LAKE_TEAR_BASS = registerCopperHorn("secret_lake_tear_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SECRET_LAKE_TEAR_HARMONY = registerCopperHorn("secret_lake_tear_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FEARLESS_RIVER_GIFT = registerCopperHorn("fearless_river_gift");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FEARLESS_RIVER_GIFT_BASS = registerCopperHorn("fearless_river_gift_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_FEARLESS_RIVER_GIFT_HARMONY = registerCopperHorn("fearless_river_gift_harmony");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SWEET_MOON_LOVE = registerCopperHorn("sweet_moon_love");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SWEET_MOON_LOVE_BASS = registerCopperHorn("sweet_moon_love_bass");
    public static final RegistryEntry.Reference<SoundEvent> COPPER_HORN_SWEET_MOON_LOVE_HARMONY = registerCopperHorn("sweet_moon_love_harmony");

    //#endregion

    /**
     * Register a {@link SoundEvent Goat Horn Sound}
     *
     * @param name The {@link String Goat Horn Sound name}
     * @return The {@link RegistryEntry.Reference<SoundEvent> registered Goat Horn Sound}
     */
    private static RegistryEntry.Reference<SoundEvent> registerGoatHorn(final String name) {
        return registerSoundReference("goat_horn_" + name);
    }

    /**
     * Register a {@link SoundEvent Copper Horn Sound}
     *
     * @param name The {@link String Copper Horn Sound name}
     * @return The {@link RegistryEntry.Reference<SoundEvent> registered Copper Horn Sound}
     */
    private static RegistryEntry.Reference<SoundEvent> registerCopperHorn(final String name) {
        return registerSoundReference("copper_horn_" + name);
    }

    /**
     * Register a {@link RegistryEntry.Reference<SoundEvent> Sound Reference}
     *
     * @param name The {@link String Sound name}
     * @return The {@link RegistryEntry.Reference<SoundEvent> registered Sound Reference}
     */
    private static RegistryEntry.Reference<SoundEvent> registerSoundReference(final String name) {
        final Identifier identifier = IdentifierUtils.modIdentifier(name);
        return Registry.registerReference(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    /**
     * Register all {@link SoundEvent Sounds}
     */
    public static void register() {

    }

}