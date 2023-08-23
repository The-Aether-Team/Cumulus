package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SplashRenderer.class)
public interface SplashRendererAccessor {
    @Accessor("splash")
    String cumulus$getSplash();

    @Mutable
    @Accessor("splash")
    void cumulus$setSplash(String splash);
}
