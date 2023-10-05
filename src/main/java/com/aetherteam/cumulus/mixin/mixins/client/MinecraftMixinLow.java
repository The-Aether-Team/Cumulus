package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.event.hooks.MenuHooks;
import com.aetherteam.cumulus.client.event.listeners.MenuListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = Minecraft.class, priority = 2000)
public class MinecraftMixinLow {
    /**
     * @see MenuHooks#setLastSplash(Screen, MenuHelper)
     * @see MenuHooks#trackFallbacks(Screen)
     */
    @ModifyVariable(method = "setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At("STORE"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;shouldShowDeathScreen()Z"),
                    to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;reset()V")))
    public Screen setScreen(Screen screen) {
        Screen newScreen = MenuListener.onGuiOpenLow(screen);
        if (newScreen != null) {
            return newScreen;
        }
        return screen;
    }
}
