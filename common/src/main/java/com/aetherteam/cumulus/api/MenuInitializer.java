package com.aetherteam.cumulus.api;

import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public interface MenuInitializer {
    default void registerMenus(BiConsumer<ResourceLocation, Menu> registerCallback) {}

    @interface Initializer {}
}
