package org.hendrix.betterfalldrop.utils;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

/**
 * Utility methods for {@link Registry Registry}
 */
public final class RegistryUtils {

    /**
     * Get a {@link RegistryWrapper.Impl<T> Registry reference}
     *
     * @param registryWrapperLookup The {@link RegistryWrapper.WrapperLookup Registry Wrapper Lookup}
     * @param registryKey The {@link RegistryKey Registry Key}
     * @return The {@link RegistryWrapper.Impl<T> Registry reference}
     * @param <T> The {@link Registry<T> Registry Type}
     */
    public static <T> RegistryWrapper.Impl<T> getRegistry(final RegistryWrapper.WrapperLookup registryWrapperLookup, final RegistryKey<? extends Registry<? extends T>> registryKey) {
        return registryWrapperLookup.getOrThrow(registryKey);
    }

    /**
     * Get a {@link T value} from a {@link Registry Registry}
     *
     * @param registryWrapperLookup The {@link RegistryWrapper.WrapperLookup Registry Wrapper Lookup}
     * @param registryKey The {@link RegistryKey Registry Key}
     * @param resourceKey The {@link RegistryKey Resource Key}
     * @return The {@link Optional<T> Registry value}
     * @param <T> The {@link Registry<T> Registry Type}
     */
    public static <T> Optional<T> getValue(final RegistryWrapper.WrapperLookup registryWrapperLookup, final RegistryKey<? extends Registry<? extends T>> registryKey, final RegistryKey<T> resourceKey) {
        RegistryWrapper.Impl<T> registry = getRegistry(registryWrapperLookup, registryKey);
        Optional<RegistryEntry.Reference<T>> optionalRegistryValue = registry.getOptional(resourceKey);
        return optionalRegistryValue.map(RegistryEntry.Reference::value);
    }

    /**
     * Get a {@link T value} from a {@link Registry Registry} given its {@link TagKey<T> Tag}
     *
     * @param registryWrapperLookup The {@link RegistryWrapper.WrapperLookup Registry Wrapper Lookup}
     * @param registryKey The {@link RegistryKey Registry Key}
     * @param tag The {@link TagKey<T> Tag Key}
     * @param identifier The {@link Identifier Resource Identifier}
     * @return The {@link Optional<T> Registry value}
     * @param <T> The {@link Registry<T> Registry Type}
     */
    public static <T> Optional<RegistryEntry<T>> getValueFromTag(final RegistryWrapper.WrapperLookup registryWrapperLookup, final RegistryKey<? extends Registry<? extends T>> registryKey, final TagKey<T> tag, final Identifier identifier) {
        return getValuesFromTag(registryWrapperLookup, registryKey, tag).flatMap((registryEntries) -> registryEntries.stream().filter((registryEntry) -> registryEntry.getIdAsString().equals(identifier.toString())).findFirst());
    }

    /**
     * Get all {@link T values} from a {@link Registry Registry} given its {@link TagKey<T> Tag}
     *
     * @param registryWrapperLookup The {@link RegistryWrapper.WrapperLookup Registry Wrapper Lookup}
     * @param registryKey The {@link RegistryKey Registry Key}
     * @param tag The {@link TagKey<T> Tag Key}
     * @return The {@link Optional<T> Registry values}
     * @param <T> The {@link Registry<T> Registry Type}
     */
    public static <T> Optional<RegistryEntryList.Named<T>> getValuesFromTag(final RegistryWrapper.WrapperLookup registryWrapperLookup, final RegistryKey<? extends Registry<? extends T>> registryKey, final TagKey<T> tag) {
        RegistryWrapper.Impl<T> registry = getRegistry(registryWrapperLookup, registryKey);
        return registry.getOptional(tag);
    }

}