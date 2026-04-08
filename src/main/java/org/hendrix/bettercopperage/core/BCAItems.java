package org.hendrix.bettercopperage.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.item.CopperHornItem;
import org.hendrix.bettercopperage.item.WrenchItem;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

import java.util.function.Function;

/**
 * {@link BetterCopperAge} {@link Item items}
 */
public final class BCAItems {

    //#region Items

    public static final Item COPPER_HORN = register("copper_horn", CopperHornItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final Item WRENCH = register("wrench", WrenchItem::new, new Item.Properties().durability(512).useCooldown(1F));
    public static final Item MANNEQUIN_SPAWN_EGG = register("mannequin_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(EntityType.MANNEQUIN));

    //#endregion

    /**
     * Register an {@link Item}
     *
     * @param name The item name
     * @param itemFactory The item factory
     * @param properties The {@link Item.Properties item properties}
     * @return The registered {@link Item}
     * @param <T> The item type
     */
    public static <T extends Item> T register(final String name, final Function<Item.Properties, T> itemFactory, final Item.Properties properties) {
        final ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, IdentifierUtils.modded(name));
        final T item = itemFactory.apply(properties.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    /**
     * Register all {@link Item items}
     */
    public static void register() {

    }

}