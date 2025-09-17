package org.hendrix.bettercopperage.core;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.item.CopperHornItem;
import org.hendrix.bettercopperage.item.WrenchItem;
import org.hendrix.bettercopperage.utils.IdentifierUtils;
import org.hendrix.bettercopperage.utils.RegistryKeyUtils;

import java.util.function.Supplier;

/**
 * {@link BetterCopperAge Better Copper Age} {@link Item Items}
 */
public final class BCAItems {

    //#region Items

    public static final Item COPPER_HORN = registerItem("copper_horn", Suppliers.memoize(() -> new CopperHornItem(
            new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).useItemPrefixedTranslationKey().registryKey(RegistryKeyUtils.item("copper_horn"))))
    );
    public static final Item WRENCH = registerItem("wrench", Suppliers.memoize(() -> new WrenchItem(new Item.Settings().maxDamage(512).useItemPrefixedTranslationKey().registryKey(RegistryKeyUtils.item("wrench")))));
    public static final Item MANNEQUIN_SPAWN_EGG = registerItem("mannequin_spawn_egg", Suppliers.memoize(() -> new SpawnEggItem(new Item.Settings().spawnEgg(EntityType.MANNEQUIN).useItemPrefixedTranslationKey().registryKey(RegistryKeyUtils.item("mannequin_spawn_egg")))));

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