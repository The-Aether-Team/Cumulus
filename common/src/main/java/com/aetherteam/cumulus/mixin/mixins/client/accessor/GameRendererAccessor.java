package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("cubeMap")
    CubeMap cumulus$getCubeMap();

    @Mutable
    @Accessor("cubeMap")
    void cumulus$setCubeMap(CubeMap cubeMap);

    @Mutable
    @Accessor("panorama")
    void cumulus$setPanorama(PanoramaRenderer panorama);
}
