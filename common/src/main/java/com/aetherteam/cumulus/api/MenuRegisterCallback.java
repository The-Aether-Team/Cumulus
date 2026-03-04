package com.aetherteam.cumulus.api;

import net.minecraft.resources.Identifier;

/**
 * Interface for registering custom menu implementations within a mods implemented {@link MenuInitializer}
 */
@FunctionalInterface
public interface MenuRegisterCallback {
    /**
     * Register the given {@link Menu} under the given {@link Identifier}
     */
    void registerMenu(Identifier location, Menu menu);
}
