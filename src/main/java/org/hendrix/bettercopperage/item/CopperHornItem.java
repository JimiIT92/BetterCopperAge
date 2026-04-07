package org.hendrix.bettercopperage.item;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.hendrix.bettercopperage.core.BCAInstruments;
import org.hendrix.bettercopperage.core.BCATags;
import org.hendrix.bettercopperage.utils.RegistryUtils;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Implementation class for a copper horn
 */
public final class CopperHornItem extends InstrumentItem {

    /**
     * The {@link Supplier<BiMap> Map Supplier} for upgradable instruments
     */
    private static final Supplier<BiMap<ResourceKey<Instrument>, ResourceKey<Instrument>>> UPGRADABLE_INSTRUMENTS = Suppliers.memoize(() -> ImmutableBiMap.<ResourceKey<Instrument>, ResourceKey<Instrument>>builder()
            .put(Instruments.PONDER_GOAT_HORN, BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN)
            .put(Instruments.SING_GOAT_HORN, BCAInstruments.OLD_HYMN_RESTING_COPPER_HORN)
            .put(Instruments.SEEK_GOAT_HORN, BCAInstruments.PURE_WATER_DESIRE_COPPER_HORN)
            .put(Instruments.FEEL_GOAT_HORN, BCAInstruments.MUMBLE_FIRE_MEMORY_COPPER_HORN)
            .put(Instruments.ADMIRE_GOAT_HORN, BCAInstruments.DRY_URGE_ANGER_COPPER_HORN)
            .put(Instruments.CALL_GOAT_HORN, BCAInstruments.CLEAR_TEMPER_JOURNEY_COPPER_HORN)
            .put(Instruments.YEARN_GOAT_HORN, BCAInstruments.FRESH_NEST_THOUGHT_COPPER_HORN)
            .put(Instruments.DREAM_GOAT_HORN, BCAInstruments.SECRET_LAKE_TEAR_COPPER_HORN)
            .put(BCAInstruments.FLY_GOAT_HORN, BCAInstruments.FEARLESS_RIVER_GIFT_COPPER_HORN)
            .put(BCAInstruments.RESIST_GOAT_HORN, BCAInstruments.SWEET_MOON_LOVE_COPPER_HORN)
            .build());

    /**
     * The {@link Supplier<BiMap> Map Supplier} for mapping {@link ResourceKey<Instrument> Instruments} to their bass counterpart
     */
    private static final Supplier<BiMap<ResourceKey<Instrument>, ResourceKey<Instrument>>> INSTRUMENT_TO_BASS_INSTRUMENT = Suppliers.memoize(() -> ImmutableBiMap.<ResourceKey<Instrument>, ResourceKey<Instrument>>builder()
            .put(BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN, BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN_BASS)
            .put(BCAInstruments.OLD_HYMN_RESTING_COPPER_HORN, BCAInstruments.OLD_HYMN_RESTING_COPPER_HORN_BASS)
            .put(BCAInstruments.PURE_WATER_DESIRE_COPPER_HORN, BCAInstruments.PURE_WATER_DESIRE_COPPER_HORN_BASS)
            .put(BCAInstruments.MUMBLE_FIRE_MEMORY_COPPER_HORN, BCAInstruments.MUMBLE_FIRE_MEMORY_COPPER_HORN_BASS)
            .put(BCAInstruments.DRY_URGE_ANGER_COPPER_HORN, BCAInstruments.DRY_URGE_ANGER_COPPER_HORN_BASS)
            .put(BCAInstruments.CLEAR_TEMPER_JOURNEY_COPPER_HORN, BCAInstruments.CLEAR_TEMPER_JOURNEY_COPPER_HORN_BASS)
            .put(BCAInstruments.FRESH_NEST_THOUGHT_COPPER_HORN, BCAInstruments.FRESH_NEST_THOUGHT_COPPER_HORN_BASS)
            .put(BCAInstruments.SECRET_LAKE_TEAR_COPPER_HORN, BCAInstruments.SECRET_LAKE_TEAR_COPPER_HORN_BASS)
            .put(BCAInstruments.FEARLESS_RIVER_GIFT_COPPER_HORN, BCAInstruments.FEARLESS_RIVER_GIFT_COPPER_HORN_BASS)
            .put(BCAInstruments.SWEET_MOON_LOVE_COPPER_HORN, BCAInstruments.SWEET_MOON_LOVE_COPPER_HORN_BASS)
            .build());

    /**
     * The {@link Supplier<BiMap> Map Supplier} for mapping {@link ResourceKey<Instrument> Instruments} to their harmony counterpart
     */
    private static final Supplier<BiMap<ResourceKey<Instrument>, ResourceKey<Instrument>>> INSTRUMENT_TO_HARMONY_INSTRUMENT = Suppliers.memoize(() -> ImmutableBiMap.<ResourceKey<Instrument>, ResourceKey<Instrument>>builder()
            .put(BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN, BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN_HARMONY)
            .put(BCAInstruments.OLD_HYMN_RESTING_COPPER_HORN, BCAInstruments.OLD_HYMN_RESTING_COPPER_HORN_HARMONY)
            .put(BCAInstruments.PURE_WATER_DESIRE_COPPER_HORN, BCAInstruments.PURE_WATER_DESIRE_COPPER_HORN_HARMONY)
            .put(BCAInstruments.MUMBLE_FIRE_MEMORY_COPPER_HORN, BCAInstruments.MUMBLE_FIRE_MEMORY_COPPER_HORN_HARMONY)
            .put(BCAInstruments.DRY_URGE_ANGER_COPPER_HORN, BCAInstruments.DRY_URGE_ANGER_COPPER_HORN_HARMONY)
            .put(BCAInstruments.CLEAR_TEMPER_JOURNEY_COPPER_HORN, BCAInstruments.CLEAR_TEMPER_JOURNEY_COPPER_HORN_HARMONY)
            .put(BCAInstruments.FRESH_NEST_THOUGHT_COPPER_HORN, BCAInstruments.FRESH_NEST_THOUGHT_COPPER_HORN_HARMONY)
            .put(BCAInstruments.SECRET_LAKE_TEAR_COPPER_HORN, BCAInstruments.SECRET_LAKE_TEAR_COPPER_HORN_HARMONY)
            .put(BCAInstruments.FEARLESS_RIVER_GIFT_COPPER_HORN, BCAInstruments.FEARLESS_RIVER_GIFT_COPPER_HORN_HARMONY)
            .put(BCAInstruments.SWEET_MOON_LOVE_COPPER_HORN, BCAInstruments.SWEET_MOON_LOVE_COPPER_HORN_HARMONY)
            .build());

    /**
     * Constructor. Set the item {@link Properties}
     *
     * @param properties The item {@link Properties}
     */
    public CopperHornItem(final Properties properties) {
        super(properties);
    }

    /**
     * Use the item
     *
     * @param level The {@link Level} reference
     * @param player The {@link Player} using the item
     * @param hand The {@link InteractionHand} the player is using
     * @return The {@link InteractionResult}
     */
    @Override
    public @NonNull InteractionResult use(final @NonNull Level level, final Player player, final @NonNull InteractionHand hand) {
        final ItemStack itemStack = player.getItemInHand(hand);
        final Optional<? extends Holder<Instrument>> instrumentHolder = getInstrument(itemStack, player.registryAccess(), this.getInstrumentTagKey(player));
        if (instrumentHolder.isPresent()) {
            final Instrument instrument = (Instrument)((Holder<?>)instrumentHolder.get()).value();
            player.startUsingItem(hand);
            play(level, player, instrument);
            player.getCooldowns().addCooldown(itemStack, Mth.floor(instrument.useDuration() * 20.0F));
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    /**
     * Get the appropriate {@link Instrument} based on the provided instrument {@link TagKey}
     *
     * @param itemStack The current {@link ItemStack}
     * @param registryAccess The {@link RegistryAccess}
     * @param instrumentTagKey The instrument {@link TagKey}
     * @return The {@link Instrument}, if any
     */
    private Optional<Holder<Instrument>> getInstrument(final ItemStack itemStack, final RegistryAccess registryAccess, final TagKey<Instrument> instrumentTagKey) {
        final InstrumentComponent instrumentComponent = itemStack.get(DataComponents.INSTRUMENT);
        Holder<Instrument> instrumentHolder = null;
        if(instrumentComponent != null) {
             instrumentHolder = instrumentComponent.instrument();
            final Optional<ResourceKey<Instrument>> optionalInstrumentResourceKey = getInstrumentResourceKey(instrumentHolder, instrumentTagKey);
            if(optionalInstrumentResourceKey.isPresent()) {
                final Optional<Holder.Reference<Instrument>> instrument = RegistryUtils.getValue(registryAccess, Registries.INSTRUMENT, optionalInstrumentResourceKey.get());
                if(instrument.isPresent()) {
                    return Optional.of(instrument.get());
                }
            }
        }
        return Optional.ofNullable(instrumentHolder);
    }

    /**
     * Get the instrument {@link TagKey} to use based
     * on the {@link Player}'s pose
     *
     * @param player The {@link Player} using the item
     * @return The instrument {@link TagKey}
     */
    private TagKey<Instrument> getInstrumentTagKey(final Player player) {
        return player.isShiftKeyDown() ? BCATags.InstrumentTags.BASS_COPPER_HORNS :
                isPlayerLookingUp(player) ?
                        BCATags.InstrumentTags.HARMONY_COPPER_HORNS :
                        BCATags.InstrumentTags.MELODY_COPPER_HORNS;
    }

    /**
     * Check whether the {@link Player} is looking up
     *
     * @param player The {@link Player}
     * @return {@link Boolean True} if the {@link Player} is looking up
     */
    private boolean isPlayerLookingUp(final Player player) {
        final float headRotation = player.getRotationVector().x;
        return headRotation <= -85F && headRotation >= -90F;
    }

    /**
     * Get the appropriate instrument {@link ResourceKey} based on the current {@link Instrument}
     * , the instrument {@link TagKey} and the {@link Player} properties
     *
     * @param instrumentHolder The current {@link Instrument}
     * @param instrumentTagKey The instrument {@link TagKey}
     * @return The instrument {@link ResourceKey}
     */
    private Optional<ResourceKey<Instrument>> getInstrumentResourceKey(final Holder<Instrument> instrumentHolder, final TagKey<Instrument> instrumentTagKey) {
        return instrumentHolder.unwrapKey().flatMap(instrument -> {
           if(instrumentTagKey.equals(BCATags.InstrumentTags.BASS_COPPER_HORNS)) {
               return Optional.ofNullable(INSTRUMENT_TO_BASS_INSTRUMENT.get().get(instrument));
           }
           if(instrumentTagKey.equals(BCATags.InstrumentTags.HARMONY_COPPER_HORNS)) {
               return Optional.ofNullable(INSTRUMENT_TO_HARMONY_INSTRUMENT.get().get(instrument));
           }
           return Optional.of(instrument);
        });
    }

    /**
     * Play the sound
     *
     * @param level The {@link Level} reference
     * @param player The {@link Player} using the item
     * @param instrument The copper horn {@link Instrument}
     */
    private static void play(final Level level, final Player player, final Instrument instrument) {
        level.playSound(player, player, instrument.soundEvent().value(), SoundSource.RECORDS, instrument.range() / 16.0F, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }

    /**
     * Upgrade a goat horn to a copper horn
     *
     * @param itemStack The goat horn {@link ItemStack}
     * @param registryAccess The {@link RegistryAccess}
     * @param baseInstrument The goat horn {@link Instrument}
     */
    public static void upgradeInstrument(final ItemStack itemStack, final RegistryAccess registryAccess, final InstrumentComponent baseInstrument) {
        baseInstrument.instrument().unwrapKey().ifPresent(baseInstrumentKey -> {
            final ResourceKey<Instrument> instrument = Optional.ofNullable(UPGRADABLE_INSTRUMENTS.get().get(baseInstrumentKey)).orElse(BCAInstruments.GREAT_SKY_FALLING_COPPER_HORN);
            RegistryUtils.getValue(registryAccess, Registries.INSTRUMENT, instrument).ifPresent(upgradedInstrument ->
                    itemStack.set(DataComponents.INSTRUMENT, new InstrumentComponent(upgradedInstrument)));
        });
    }

}