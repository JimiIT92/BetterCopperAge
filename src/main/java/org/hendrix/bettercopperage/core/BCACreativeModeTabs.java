package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

import java.util.Arrays;

/**
 * {@link BetterCopperAge} {@link CreativeModeTab Creative Mode Tabs}
 */
public final class BCACreativeModeTabs {

    //#region Creative Mode Tabs

    public static final CreativeModeTab BETTER_COPPER_AGE = register(
            BetterCopperAge.MOD_ID,
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(Blocks.COPPER_GOLEM_STATUE))
                    .title(Component.translatable("creativeTab." + BetterCopperAge.MOD_ID + "." + BetterCopperAge.MOD_ID))
                    .displayItems((params, output) -> {
                        addContent(
                                output,
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
                        addModdedGoatHorns(params, output);
                        addCopperHorns(params, output);
                    })
                    .build()
    );

    //#endregion

    /**
     * Add some content to a creative mode tab
     *
     * @param output The {@link CreativeModeTab.Output}
     * @param content The {@link ItemLike content to add}
     */
    private static void addContent(final CreativeModeTab.Output output, final ItemLike... content) {
        Arrays.stream(content).forEach(output::accept);
    }

    /**
     * Add modded goat horns to the creative mode tab
     *
     * @param itemDisplayParameters The {@link CreativeModeTab.ItemDisplayParameters}
     * @param output The {@link CreativeModeTab.Output}
     */
    private static void addModdedGoatHorns(final CreativeModeTab.ItemDisplayParameters itemDisplayParameters, final CreativeModeTab.Output output) {
        itemDisplayParameters.holders().lookup(Registries.INSTRUMENT).flatMap(instruments -> instruments.get(BCATags.InstrumentTags.MELODY_COPPER_HORNS)).ifPresent((tag) -> tag
                .stream()
                .map((instrument) -> InstrumentItem.create(BCAItems.COPPER_HORN, instrument))
                .forEach((stack) -> output.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)));
    }

    /**
     * Add copper horns to the creative mode tab
     *
     * @param itemDisplayParameters The {@link CreativeModeTab.ItemDisplayParameters}
     * @param output The {@link CreativeModeTab.Output}
     */
    private static void addCopperHorns(final CreativeModeTab.ItemDisplayParameters itemDisplayParameters, final CreativeModeTab.Output output) {
        itemDisplayParameters.holders().lookup(Registries.INSTRUMENT).flatMap(instruments -> instruments.get(InstrumentTags.GOAT_HORNS)).ifPresent((tag) -> tag
                .stream()
                .map((instrument) -> InstrumentItem.create(Items.GOAT_HORN, instrument))
                .filter(BCACreativeModeTabs::isModdedGoatHorn)
                .forEach((stack) -> output.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)));
    }

    /**
     * Check whether a goat horn is modded or not
     *
     * @param itemStack The {@link ItemStack to check}
     * @return {@link Boolean True} if is a modded goat horn
     */
    private static boolean isModdedGoatHorn(final ItemStack itemStack) {
        final InstrumentComponent instrument = itemStack.get(DataComponents.INSTRUMENT);
        if(instrument != null) {
            return instrument.instrument().unwrapKey().map(key -> key.identifier().getNamespace().equalsIgnoreCase(BetterCopperAge.MOD_ID)).orElse(false);
        }
        return false;
    }

    /**
     * Register a {@link CreativeModeTab}
     *
     * @param name The creative mode tab name
     * @param creativeModeTab The {@link CreativeModeTab to register}
     * @return The registered {@link CreativeModeTab}
     */
    private static CreativeModeTab register(final String name, final CreativeModeTab creativeModeTab) {
        final ResourceKey<CreativeModeTab> resourceKey = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), IdentifierUtils.modded(name));
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceKey, creativeModeTab);
    }

    /**
     * Register all creative mode tabs
     */
    public static void register() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            output.getSearchTabStacks().removeIf(BCACreativeModeTabs::isModdedGoatHorn);
            output.getDisplayStacks().removeIf(BCACreativeModeTabs::isModdedGoatHorn);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.OP_BLOCKS).register(output -> output.accept(BCAItems.MANNEQUIN_SPAWN_EGG));
    }

}