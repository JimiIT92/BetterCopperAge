package org.hendrix.bettercopperage.core;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.InstrumentComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.InstrumentTags;
import net.minecraft.text.Text;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.item.CopperHornItem;
import org.hendrix.bettercopperage.utils.IdentifierUtils;
import org.hendrix.bettercopperage.utils.RegistryUtils;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * {@link BetterCopperAge Better Copper Age} {@link ItemGroup Item Groups}
 */
public final class BCAItemGroups {

    //#region Item Groups

    public static ItemGroup BETTER_FALL_DROP = Registry.register(
            Registries.ITEM_GROUP,
            IdentifierUtils.modIdentifier(BetterCopperAge.MOD_ID),
            FabricItemGroup.builder()
                    .icon(Suppliers.memoize(() -> new ItemStack(Blocks.COPPER_GOLEM_STATUE)))
                    .displayName(Text.translatable("itemgroup." + BetterCopperAge.MOD_ID + "." + BetterCopperAge.MOD_ID))
                    .build()
    );

    //#endregion

    /**
     * Register all {@link ItemGroup Item Groups}
     */
    public static void register() {
        addItems(
                BCABlocks.COPPER_BUTTON,
                BCABlocks.EXPOSED_COPPER_BUTTON,
                BCABlocks.WEATHERED_COPPER_BUTTON,
                BCABlocks.OXIDIZED_COPPER_BUTTON,
                BCABlocks.WAXED_COPPER_BUTTON,
                BCABlocks.WAXED_EXPOSED_COPPER_BUTTON,
                BCABlocks.WAXED_WEATHERED_COPPER_BUTTON,
                BCABlocks.WAXED_OXIDIZED_COPPER_BUTTON,
                BCABlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BCABlocks.COPPER_RAIL,
                BCABlocks.EXPOSED_COPPER_RAIL,
                BCABlocks.WEATHERED_COPPER_RAIL,
                BCABlocks.OXIDIZED_COPPER_RAIL,
                BCABlocks.WAXED_COPPER_RAIL,
                BCABlocks.WAXED_EXPOSED_COPPER_RAIL,
                BCABlocks.WAXED_WEATHERED_COPPER_RAIL,
                BCABlocks.WAXED_OXIDIZED_COPPER_RAIL,
                BCABlocks.COPPER_CAMPFIRE,
                BCABlocks.CHISELED_IRON,
                BCABlocks.IRON_GRATE,
                BCABlocks.CUT_IRON,
                BCABlocks.CUT_IRON_SLAB,
                BCABlocks.CUT_IRON_STAIRS,
                BCABlocks.IRON_BUTTON,
                BCABlocks.CHISELED_GOLD,
                BCABlocks.GOLDEN_GRATE,
                BCABlocks.CUT_GOLD,
                BCABlocks.CUT_GOLDEN_SLAB,
                BCABlocks.CUT_GOLDEN_STAIRS,
                BCABlocks.GOLDEN_BARS,
                BCABlocks.GOLDEN_CHAIN,
                BCABlocks.GOLD_BUTTON,
                BCABlocks.GOLDEN_DOOR,
                BCABlocks.GOLDEN_TRAPDOOR,
                BCABlocks.GOLDEN_LANTERN,
                BCAItems.WRENCH
        );
        addGoatHorns();
        addCopperHorns();
    }

    /**
     * Add some {@link ItemStack Items} to the {@link #BETTER_FALL_DROP Mod's Item Group}
     *
     * @param items The {@link ItemConvertible Items to add}
     */
    private static void addItems(final ItemConvertible... items) {
        if (items != null && items.length > 0) {
            Registries.ITEM_GROUP.getKey(BETTER_FALL_DROP)
                    .ifPresent((itemGroupKey) -> ItemGroupEvents.modifyEntriesEvent(itemGroupKey)
                            .register((entries) -> Arrays.asList(items).forEach(entries::add)));
        }

    }

    /**
     * Add the {@link GoatHornItem modded Goat Horns} to the Creative Inventory
     */
    private static void addGoatHorns() {
        final Predicate<? super ItemStack> filter = BCAItemGroups::isModdedGoatHorn;

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((entries) -> {
            entries.getSearchTabStacks().removeIf(filter);
            entries.getDisplayStacks().removeIf(filter);
        });

        Registries.ITEM_GROUP.getKey(BETTER_FALL_DROP)
                .ifPresent((itemGroupKey) -> ItemGroupEvents.modifyEntriesEvent(itemGroupKey)
                        .register((entries) -> RegistryUtils.getValuesFromTag(entries.getContext().lookup(), RegistryKeys.INSTRUMENT, InstrumentTags.GOAT_HORNS)
                                .ifPresent(instruments -> instruments.stream()
                                        .map(instrument -> GoatHornItem.getStackForInstrument(Items.GOAT_HORN, instrument))
                                        .filter(BCAItemGroups::isModdedGoatHorn)
                                        .forEach(itemStack -> entries.add(itemStack, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS))
                                )
                        )
        );
    }

    /**
     * Add the {@link CopperHornItem Copper Horns} to the Creative Inventory
     */
    private static void addCopperHorns() {
        Registries.ITEM_GROUP.getKey(BETTER_FALL_DROP)
                .ifPresent((itemGroupKey) -> ItemGroupEvents.modifyEntriesEvent(itemGroupKey)
                        .register((entries) -> RegistryUtils.getValuesFromTag(entries.getContext().lookup(), RegistryKeys.INSTRUMENT, BCATags.InstrumentTags.MELODY_COPPER_HORNS)
                                .ifPresent(instruments ->
                                        instruments.stream()
                                                .map(instrument -> CopperHornItem.getStackForInstrument(BCAItems.COPPER_HORN, instrument))
                                                .forEach(itemStack -> entries.add(itemStack, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS))
                                )
                        )
        );
    }

    /**
     * Check if a {@link GoatHornItem Goat Horn} is a modded one
     *
     * @param itemStack The {@link ItemStack Item Stack to check}
     * @return {@link Boolean True} if is a modded {@link GoatHornItem Goat Horn}
     */
    private static boolean isModdedGoatHorn(final ItemStack itemStack) {
        final InstrumentComponent instrument = itemStack.get(DataComponentTypes.INSTRUMENT);
        if(instrument != null) {
            return instrument.instrument().getKey().map(key -> key.getValue().getNamespace().equalsIgnoreCase(BetterCopperAge.MOD_ID)).orElse(false);
        }
        return false;
    }

}