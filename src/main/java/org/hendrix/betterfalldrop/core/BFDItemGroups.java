package org.hendrix.betterfalldrop.core;

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
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.item.CopperHornItem;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;
import org.hendrix.betterfalldrop.utils.RegistryUtils;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link ItemGroup Item Groups}
 */
public final class BFDItemGroups {

    //#region Item Groups

    public static ItemGroup BETTER_FALL_DROP = Registry.register(
            Registries.ITEM_GROUP,
            IdentifierUtils.modIdentifier(BetterFallDrop.MOD_ID),
            FabricItemGroup.builder()
                    .icon(Suppliers.memoize(() -> new ItemStack(Blocks.COPPER_GOLEM_STATUE)))
                    .displayName(Text.translatable("itemgroup." + BetterFallDrop.MOD_ID + "." + BetterFallDrop.MOD_ID))
                    .build()
    );

    //#endregion

    /**
     * Register all {@link ItemGroup Item Groups}
     */
    public static void register() {
        addItems(
                BFDBlocks.COPPER_BUTTON,
                BFDBlocks.EXPOSED_COPPER_BUTTON,
                BFDBlocks.WEATHERED_COPPER_BUTTON,
                BFDBlocks.OXIDIZED_COPPER_BUTTON,
                BFDBlocks.WAXED_COPPER_BUTTON,
                BFDBlocks.WAXED_EXPOSED_COPPER_BUTTON,
                BFDBlocks.WAXED_WEATHERED_COPPER_BUTTON,
                BFDBlocks.WAXED_OXIDIZED_COPPER_BUTTON,
                BFDBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                BFDBlocks.COPPER_CAMPFIRE,
                BFDBlocks.CHISELED_IRON,
                BFDBlocks.IRON_GRATE,
                BFDBlocks.CUT_IRON,
                BFDBlocks.CUT_IRON_SLAB,
                BFDBlocks.CUT_IRON_STAIRS,
                BFDBlocks.IRON_BUTTON,
                BFDBlocks.CHISELED_GOLD,
                BFDBlocks.GOLDEN_GRATE,
                BFDBlocks.CUT_GOLD,
                BFDBlocks.CUT_GOLDEN_SLAB,
                BFDBlocks.CUT_GOLDEN_STAIRS,
                BFDBlocks.GOLDEN_BARS,
                BFDBlocks.GOLDEN_CHAIN,
                BFDBlocks.GOLD_BUTTON,
                BFDBlocks.GOLDEN_DOOR,
                BFDBlocks.GOLDEN_TRAPDOOR,
                BFDBlocks.GOLDEN_LANTERN
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
        final Predicate<? super ItemStack> filter = BFDItemGroups::isModdedGoatHorn;

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((entries) -> {
            entries.getSearchTabStacks().removeIf(filter);
            entries.getDisplayStacks().removeIf(filter);
        });

        Registries.ITEM_GROUP.getKey(BETTER_FALL_DROP)
                .ifPresent((itemGroupKey) -> ItemGroupEvents.modifyEntriesEvent(itemGroupKey)
                        .register((entries) -> RegistryUtils.getValuesFromTag(entries.getContext().lookup(), RegistryKeys.INSTRUMENT, InstrumentTags.GOAT_HORNS)
                                .ifPresent(instruments -> instruments.stream()
                                        .map(instrument -> GoatHornItem.getStackForInstrument(Items.GOAT_HORN, instrument))
                                        .filter(BFDItemGroups::isModdedGoatHorn)
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
                        .register((entries) -> RegistryUtils.getValuesFromTag(entries.getContext().lookup(), RegistryKeys.INSTRUMENT, BFDTags.InstrumentTags.MELODY_COPPER_HORNS)
                                .ifPresent(instruments ->
                                        instruments.stream()
                                                .map(instrument -> CopperHornItem.getStackForInstrument(BFDItems.COPPER_HORN, instrument))
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
            return instrument.instrument().getKey().map(key -> key.getValue().getNamespace().equalsIgnoreCase(BetterFallDrop.MOD_ID)).orElse(false);
        }
        return false;
    }

}