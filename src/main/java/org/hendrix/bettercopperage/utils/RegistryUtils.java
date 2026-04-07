package org.hendrix.bettercopperage.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

/**
 * Utility methods for {@link Registry}
 */
public final class RegistryUtils {

    /**
     * Get a {@link HolderLookup.RegistryLookup<T> registry reference}
     *
     * @param registryAccess The {@link RegistryAccess}
     * @param registryKey The registry {@link ResourceKey}
     * @return The {@link HolderLookup.RegistryLookup<T> registry reference}
     * @param <T> The registry type
     */
    public static <T> HolderLookup.RegistryLookup<T> getRegistry(final RegistryAccess registryAccess, final ResourceKey<? extends Registry<? extends T>> registryKey) {
        return registryAccess.lookupOrThrow(registryKey);
    }

    /**
     * Get a value from a registry
     *
     * @param registryAccess The {@link RegistryAccess}
     * @param registryKey The registry {@link ResourceKey}
     * @param resourceKey The {@link ResourceKey} to get
     * @return The resource, if any
     * @param <T> The registry type
     */
    public static <T> Optional<Holder.Reference<T>> getValue(final RegistryAccess registryAccess, final ResourceKey<? extends Registry<? extends T>> registryKey, final ResourceKey<T> resourceKey) {
        final HolderLookup.RegistryLookup<T> registry = getRegistry(registryAccess, registryKey);
        return registry.get(resourceKey);
    }

}