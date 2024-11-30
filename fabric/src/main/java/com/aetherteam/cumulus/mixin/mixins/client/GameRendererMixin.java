package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.event.listeners.WorldPreviewListener;
import com.aetherteam.cumulus.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void render(Screen instance, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        WorldPreviewListener.onScreenRender(instance, callback);

        if (!callback.isCanceled()) original.call(instance, guiGraphics, mouseX, mouseY, partialTick);
    }
}
