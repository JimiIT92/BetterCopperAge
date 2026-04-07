package org.hendrix.bettercopperage.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge} {@link SoundEvent sounds}
 */
public final class BCASounds {

    //#region Sounds

    public static final SoundEvent GOAT_HORN_FLY = registerGoatHorn("fly");
    public static final SoundEvent GOAT_HORN_RESIST = registerGoatHorn("resist");

    public static final SoundEvent COPPER_HORN_GREAT_SKY_FALLING = registerCopperHorn("great_sky_falling");
    public static final SoundEvent COPPER_HORN_GREAT_SKY_FALLING_BASS = registerCopperHorn("great_sky_falling_bass");
    public static final SoundEvent COPPER_HORN_GREAT_SKY_FALLING_HARMONY = registerCopperHorn("great_sky_falling_harmony");
    public static final SoundEvent COPPER_HORN_OLD_HYMN_RESTING = registerCopperHorn("old_hymn_resting");
    public static final SoundEvent COPPER_HORN_OLD_HYMN_RESTING_BASS = registerCopperHorn("old_hymn_resting_bass");
    public static final SoundEvent COPPER_HORN_OLD_HYMN_RESTING_HARMONY = registerCopperHorn("old_hymn_resting_harmony");
    public static final SoundEvent COPPER_HORN_PURE_WATER_DESIRE = registerCopperHorn("pure_water_desire");
    public static final SoundEvent COPPER_HORN_PURE_WATER_DESIRE_BASS = registerCopperHorn("pure_water_desire_bass");
    public static final SoundEvent COPPER_HORN_PURE_WATER_DESIRE_HARMONY = registerCopperHorn("pure_water_desire_harmony");
    public static final SoundEvent COPPER_HORN_MUMBLE_FIRE_MEMORY = registerCopperHorn("mumble_fire_memory");
    public static final SoundEvent COPPER_HORN_MUMBLE_FIRE_MEMORY_BASS = registerCopperHorn("mumble_fire_memory_bass");
    public static final SoundEvent COPPER_HORN_MUMBLE_FIRE_MEMORY_HARMONY = registerCopperHorn("mumble_fire_memory_harmony");
    public static final SoundEvent COPPER_HORN_DRY_URGE_ANGER = registerCopperHorn("dry_urge_anger");
    public static final SoundEvent COPPER_HORN_DRY_URGE_ANGER_BASS = registerCopperHorn("dry_urge_anger_bass");
    public static final SoundEvent COPPER_HORN_DRY_URGE_ANGER_HARMONY = registerCopperHorn("dry_urge_anger_harmony");
    public static final SoundEvent COPPER_HORN_CLEAR_TEMPER_JOURNEY = registerCopperHorn("clear_temper_journey");
    public static final SoundEvent COPPER_HORN_CLEAR_TEMPER_JOURNEY_BASS = registerCopperHorn("clear_temper_journey_bass");
    public static final SoundEvent COPPER_HORN_CLEAR_TEMPER_JOURNEY_HARMONY = registerCopperHorn("clear_temper_journey_harmony");
    public static final SoundEvent COPPER_HORN_FRESH_NEST_THOUGHT = registerCopperHorn("fresh_nest_thought");
    public static final SoundEvent COPPER_HORN_FRESH_NEST_THOUGHT_BASS = registerCopperHorn("fresh_nest_thought_bass");
    public static final SoundEvent COPPER_HORN_FRESH_NEST_THOUGHT_HARMONY = registerCopperHorn("fresh_nest_thought_harmony");
    public static final SoundEvent COPPER_HORN_SECRET_LAKE_TEAR = registerCopperHorn("secret_lake_tear");
    public static final SoundEvent COPPER_HORN_SECRET_LAKE_TEAR_BASS = registerCopperHorn("secret_lake_tear_bass");
    public static final SoundEvent COPPER_HORN_SECRET_LAKE_TEAR_HARMONY = registerCopperHorn("secret_lake_tear_harmony");
    public static final SoundEvent COPPER_HORN_FEARLESS_RIVER_GIFT = registerCopperHorn("fearless_river_gift");
    public static final SoundEvent COPPER_HORN_FEARLESS_RIVER_GIFT_BASS = registerCopperHorn("fearless_river_gift_bass");
    public static final SoundEvent COPPER_HORN_FEARLESS_RIVER_GIFT_HARMONY = registerCopperHorn("fearless_river_gift_harmony");
    public static final SoundEvent COPPER_HORN_SWEET_MOON_LOVE = registerCopperHorn("sweet_moon_love");
    public static final SoundEvent COPPER_HORN_SWEET_MOON_LOVE_BASS = registerCopperHorn("sweet_moon_love_bass");
    public static final SoundEvent COPPER_HORN_SWEET_MOON_LOVE_HARMONY = registerCopperHorn("sweet_moon_love_harmony");

    //#endregion

    /**
     * Register a {@link SoundEvent} for a goat horn
     *
     * @param name The sound name
     * @return The registered {@link SoundEvent}
     */
    private static SoundEvent registerGoatHorn(final String name) {
        return registerSound("goat_horn_" + name);
    }

    /**
     * Register a {@link SoundEvent} for a copper horn
     *
     * @param name The sound name
     * @return The registered {@link SoundEvent}
     */
    private static SoundEvent registerCopperHorn(final String name) {
        return registerSound("copper_horn_" + name);
    }

    /**
     * Register a {@link SoundEvent}
     *
     * @param name The sound name
     * @return The registered {@link SoundEvent}
     */
    private static SoundEvent registerSound(final String name) {
        final Identifier identifier = IdentifierUtils.modded(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    /**
     * Register all {@link SoundEvent sounds}
     */
    public static void register() {

    }

}