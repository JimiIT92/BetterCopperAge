package org.hendrix.betterfalldrop.core;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;

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
                    .entries((displayContext, entries) -> {
                        entries.add(BFDBlocks.COPPER_BUTTON);
                        entries.add(BFDBlocks.EXPOSED_COPPER_BUTTON);
                        entries.add(BFDBlocks.WEATHERED_COPPER_BUTTON);
                        entries.add(BFDBlocks.OXIDIZED_COPPER_BUTTON);
                        entries.add(BFDBlocks.WAXED_COPPER_BUTTON);
                        entries.add(BFDBlocks.WAXED_EXPOSED_COPPER_BUTTON);
                        entries.add(BFDBlocks.WAXED_WEATHERED_COPPER_BUTTON);
                        entries.add(BFDBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
                        entries.add(BFDBlocks.MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
                        entries.add(BFDBlocks.CHISELED_IRON);
                        entries.add(BFDBlocks.IRON_GRATE);
                        entries.add(BFDBlocks.CUT_IRON);
                        entries.add(BFDBlocks.CUT_IRON_SLAB);
                        entries.add(BFDBlocks.CUT_IRON_STAIRS);
                        entries.add(BFDBlocks.IRON_BUTTON);
                        entries.add(BFDBlocks.CHISELED_GOLD);
                        entries.add(BFDBlocks.GOLDEN_GRATE);
                        entries.add(BFDBlocks.CUT_GOLD);
                        entries.add(BFDBlocks.CUT_GOLDEN_SLAB);
                        entries.add(BFDBlocks.CUT_GOLDEN_STAIRS);
                        entries.add(BFDBlocks.GOLD_BUTTON);
                    })
                    .build()
    );

    //#endregion

    /**
     * Register all {@link ItemGroup Item Groups}
     */
    public static void register() {

    }

}