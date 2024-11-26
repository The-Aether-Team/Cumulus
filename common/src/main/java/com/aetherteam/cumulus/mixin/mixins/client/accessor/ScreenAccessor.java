package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor("CUBE_MAP")
    static CubeMap cumulus$getCubeMap() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("CUBE_MAP")
    static void cumulus$setCubeMap(CubeMap cubeMap) {
        throw new UnsupportedOperationException();
    }

    @Accessor("PANORAMA")
    static PanoramaRenderer cumulus$getPanorama() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("PANORAMA")
    static void cumulus$setPanorama(PanoramaRenderer panorama) {
        throw new UnsupportedOperationException();
    }
}
