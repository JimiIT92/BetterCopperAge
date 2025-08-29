package org.hendrix.bettercopperage.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Instrument;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

/**
 * Utility methods for {@link RegistryKey Registry Key}
 */
public final class RegistryKeyUtils {

    /**
     * Get the {@link RegistryKey<Block> Registry Key} for a {@link Block Block}
     *
     * @param name The {@link String Block name}
     * @return The {@link RegistryKey<Block> Block Registry Key}
     */
    public static RegistryKey<Block> block(final String name) {
        return key(RegistryKeys.BLOCK, name);
    }

    /**
     * Get the {@link RegistryKey<Item> Registry Key} for an {@link Item Item}
     *
     * @param name The {@link String Item name}
     * @return The {@link RegistryKey<Item> Item Registry Key}
     */
    public static RegistryKey<Item> item(final String name) {
        return key(RegistryKeys.ITEM, name);
    }

    /**
     * Get the {@link RegistryKey<Instrument> Registry Key} for a {@link Instrument Instrument}
     *
     * @param name The {@link String Instrument name}
     * @return The {@link RegistryKey<Instrument> Instrument Registry Key}
     */
    public static RegistryKey<Instrument> instrument(final String name) {
        return key(RegistryKeys.INSTRUMENT, name);
    }

    /**
     * Get a {@link RegistryKey Registry Key}
     *
     * @param registry The {@link RegistryKey Registry} reference
     * @param name The {@link String Registry Key name}
     * @return The {@link RegistryKey<T> Registry Key}
     * @param <T> The {@link T Key Type}
     */
    private static <T> RegistryKey<T> key(final RegistryKey<? extends Registry<T>> registry, final String name) {
        return RegistryKey.of(registry, IdentifierUtils.modIdentifier(name));
    }

}