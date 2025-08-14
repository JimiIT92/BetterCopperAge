package org.hendrix.betterfalldrop.core;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.item.CopperHornItem;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;
import org.hendrix.betterfalldrop.utils.RegistryKeyUtils;

import java.util.function.Supplier;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link Item Items}
 */
public final class BFDItems {

    //#region Items

    public static final Item COPPER_HORN = registerItem("copper_horn", Suppliers.memoize(() -> new CopperHornItem(
            new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).useItemPrefixedTranslationKey().registryKey(RegistryKeyUtils.item("copper_horn"))))
    );

    //#endregion

    /**
     * Register a {@link BlockItem Block Item}
     *
     * @param identifier The {@link Identifier Block Identifier}
     * @param blockSupplier The {@link Supplier < Block > Block Supplier}
     */
    public static void registerBlockItem(final Identifier identifier, final Supplier<Block> blockSupplier) {
        Item.Settings itemSettings = new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)).useBlockPrefixedTranslationKey();
        registerItem(identifier.getPath(), Suppliers.memoize(() -> new BlockItem(blockSupplier.get(), itemSettings)));
    }

    /**
     * Register an {@link Item Item}
     *
     * @param name The {@link String Item name}
     * @param itemSupplier The {@link Supplier<Item> Item Supplier}
     * @return The {@link Item registered Item}
     */
    private static Item registerItem(final String name, final Supplier<Item> itemSupplier) {
        final Identifier identifier = IdentifierUtils.modIdentifier(name);
        return Registry.register(Registries.ITEM, identifier, itemSupplier.get());
    }

    /**
     * Register all {@link Item Items}
     */
    public static void register() {

    }

}