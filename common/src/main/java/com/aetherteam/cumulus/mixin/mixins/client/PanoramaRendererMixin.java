package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.Panorama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Panorama.class)
public class PanoramaRendererMixin {
    /**
     * Used by the world preview system.<br>
     * Prevents the {@link net.minecraft.client.gui.screens.TitleScreen} panorama from rendering when a world preview is active.
     *
     * @param guiGraphics The rendering {@link GuiGraphicsExtractor}.
     * @param width The {@link Integer} for the screen width.
     * @param height The {@link Integer} for the screen height.
     * @param spin A {@link Boolean} for whether the panorama spins.
     * @param ci        The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     */
    @Inject(at = @At(value = "HEAD"), method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIZ)V", cancellable = true)
    public void render(GuiGraphicsExtractor guiGraphics, int width, int height, boolean spin, CallbackInfo ci) {
        if (Minecraft.getInstance().level != null && WorldDisplayHelper.isActive()) {
            ci.cancel();
        }
    }
}
