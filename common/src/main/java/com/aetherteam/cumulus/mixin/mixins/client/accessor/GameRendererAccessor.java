package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Panorama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("guiRenderer")
    GuiRenderer cumulus$getGuiRenderer();

    @Mutable
    @Accessor("panorama")
    void cumulus$setPanorama(Panorama panorama);
}
