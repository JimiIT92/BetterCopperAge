package org.hendrix.bettercopperage.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge} {@link Instrument Instruments}
 */
public final class BCAInstruments {

    //#region Instruments

    public static final ResourceKey<Instrument> FLY_GOAT_HORN = goatHorn("fly");
    public static final ResourceKey<Instrument> RESIST_GOAT_HORN = goatHorn("resist");

    public static final ResourceKey<Instrument> GREAT_SKY_FALLING_COPPER_HORN = copperHorn("great_sky_falling");
    public static final ResourceKey<Instrument> GREAT_SKY_FALLING_COPPER_HORN_BASS = bassCopperHorn("great_sky_falling");
    public static final ResourceKey<Instrument> GREAT_SKY_FALLING_COPPER_HORN_HARMONY = harmonyCopperHorn("great_sky_falling");
    public static final ResourceKey<Instrument> OLD_HYMN_RESTING_COPPER_HORN = copperHorn("old_hymn_resting");
    public static final ResourceKey<Instrument> OLD_HYMN_RESTING_COPPER_HORN_BASS = bassCopperHorn("old_hymn_resting");
    public static final ResourceKey<Instrument> OLD_HYMN_RESTING_COPPER_HORN_HARMONY = harmonyCopperHorn("old_hymn_resting");
    public static final ResourceKey<Instrument> PURE_WATER_DESIRE_COPPER_HORN = copperHorn("pure_water_desire");
    public static final ResourceKey<Instrument> PURE_WATER_DESIRE_COPPER_HORN_BASS = bassCopperHorn("pure_water_desire");
    public static final ResourceKey<Instrument> PURE_WATER_DESIRE_COPPER_HORN_HARMONY = harmonyCopperHorn("pure_water_desire");
    public static final ResourceKey<Instrument> MUMBLE_FIRE_MEMORY_COPPER_HORN = copperHorn("mumble_fire_memory");
    public static final ResourceKey<Instrument> MUMBLE_FIRE_MEMORY_COPPER_HORN_BASS = bassCopperHorn("mumble_fire_memory");
    public static final ResourceKey<Instrument> MUMBLE_FIRE_MEMORY_COPPER_HORN_HARMONY = harmonyCopperHorn("mumble_fire_memory");
    public static final ResourceKey<Instrument> DRY_URGE_ANGER_COPPER_HORN = copperHorn("dry_urge_anger");
    public static final ResourceKey<Instrument> DRY_URGE_ANGER_COPPER_HORN_BASS = bassCopperHorn("dry_urge_anger");
    public static final ResourceKey<Instrument> DRY_URGE_ANGER_COPPER_HORN_HARMONY = harmonyCopperHorn("dry_urge_anger");
    public static final ResourceKey<Instrument> CLEAR_TEMPER_JOURNEY_COPPER_HORN = copperHorn("clear_temper_journey");
    public static final ResourceKey<Instrument> CLEAR_TEMPER_JOURNEY_COPPER_HORN_BASS = bassCopperHorn("clear_temper_journey");
    public static final ResourceKey<Instrument> CLEAR_TEMPER_JOURNEY_COPPER_HORN_HARMONY = harmonyCopperHorn("clear_temper_journey");
    public static final ResourceKey<Instrument> FRESH_NEST_THOUGHT_COPPER_HORN = copperHorn("fresh_nest_thought");
    public static final ResourceKey<Instrument> FRESH_NEST_THOUGHT_COPPER_HORN_BASS = bassCopperHorn("fresh_nest_thought");
    public static final ResourceKey<Instrument> FRESH_NEST_THOUGHT_COPPER_HORN_HARMONY = harmonyCopperHorn("fresh_nest_thought");
    public static final ResourceKey<Instrument> SECRET_LAKE_TEAR_COPPER_HORN = copperHorn("secret_lake_tear");
    public static final ResourceKey<Instrument> SECRET_LAKE_TEAR_COPPER_HORN_BASS = bassCopperHorn("secret_lake_tear");
    public static final ResourceKey<Instrument> SECRET_LAKE_TEAR_COPPER_HORN_HARMONY = harmonyCopperHorn("secret_lake_tear");
    public static final ResourceKey<Instrument> FEARLESS_RIVER_GIFT_COPPER_HORN = copperHorn("fearless_river_gift");
    public static final ResourceKey<Instrument> FEARLESS_RIVER_GIFT_COPPER_HORN_BASS = bassCopperHorn("fearless_river_gift");
    public static final ResourceKey<Instrument> FEARLESS_RIVER_GIFT_COPPER_HORN_HARMONY = harmonyCopperHorn("fearless_river_gift");
    public static final ResourceKey<Instrument> SWEET_MOON_LOVE_COPPER_HORN = copperHorn("sweet_moon_love");
    public static final ResourceKey<Instrument> SWEET_MOON_LOVE_COPPER_HORN_BASS = copperHorn("sweet_moon_love", "bass");
    public static final ResourceKey<Instrument> SWEET_MOON_LOVE_COPPER_HORN_HARMONY = harmonyCopperHorn("sweet_moon_love");

    //#endregion

    /**
     * Register a goat horn {@link ResourceKey}
     *
     * @param name The goat horn name
     * @return The goat horn {@link ResourceKey}
     */
    private static ResourceKey<Instrument> goatHorn(final String name) {
        return register(name + "_goat_horn");
    }

    /**
     * Register a base copper horn {@link ResourceKey}
     *
     * @param name The base copper horn name
     * @return The base copper horn {@link ResourceKey}
     */
    private static ResourceKey<Instrument> copperHorn(final String name) {
        return copperHorn(name, "");
    }

    /**
     * Register a bass copper horn {@link ResourceKey}
     *
     * @param name The bass copper horn name
     * @return The bass copper horn {@link ResourceKey}
     */
    private static ResourceKey<Instrument> bassCopperHorn(final String name) {
        return copperHorn(name, "bass");
    }

    /**
     * Register a harmony copper horn {@link ResourceKey}
     *
     * @param name The harmony copper horn name
     * @return The harmony copper horn {@link ResourceKey}
     */
    private static ResourceKey<Instrument> harmonyCopperHorn(final String name) {
        return copperHorn(name, "harmony");
    }

    /**
     * Register a copper horn {@link ResourceKey}
     *
     * @param name The copper horn name
     * @return The copper horn {@link ResourceKey}
     */
    private static ResourceKey<Instrument> copperHorn(final String name, final String variant) {
        return register(name + "_copper_horn" + (variant.isEmpty() ? "" : "_") + variant);
    }

    /**
     * Register an instrument {@link ResourceKey}
     *
     * @param name The instrument name
     * @return The instrument {@link ResourceKey}
     */
    private static ResourceKey<Instrument> register(final String name) {
        return ResourceKey.create(Registries.INSTRUMENT, IdentifierUtils.modded(name));
    }

    /**
     * Register all {@link Instrument instruments}
     */
    public static void register() {

    }

}