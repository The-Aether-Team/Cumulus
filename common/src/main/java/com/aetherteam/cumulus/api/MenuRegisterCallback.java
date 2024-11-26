package com.aetherteam.cumulus.api;

import net.minecraft.resources.ResourceLocation;

/**
 * Interface for registering custom menu implementations within a mods implemented {@link MenuInitializer}
 */
@FunctionalInterface
public interface MenuRegisterCallback {
    /**
     * Register the given {@link Menu} under the given {@link ResourceLocation}
     */
    void registerMenu(ResourceLocation location, Menu menu);
}
