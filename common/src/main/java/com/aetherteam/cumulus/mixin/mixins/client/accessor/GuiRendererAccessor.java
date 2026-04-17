package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiRenderer.class)
public interface GuiRendererAccessor {
    @Accessor("cubeMap")
    CubeMap cumulus$getCubeMap();

    @Mutable
    @Accessor("cubeMap")
    void cumulus$setCubeMap(CubeMap cubeMap);
}
