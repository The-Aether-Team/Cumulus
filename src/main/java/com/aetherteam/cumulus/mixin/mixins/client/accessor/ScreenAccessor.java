package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Mutable
    @Accessor("BACKGROUND_LOCATION")
    static void cumulus$setBackgroundLocation(ResourceLocation location) {
        throw new AssertionError();
    }
}
