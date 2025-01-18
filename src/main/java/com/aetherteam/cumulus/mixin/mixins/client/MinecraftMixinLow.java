package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.event.hooks.MenuHooks;
import com.aetherteam.cumulus.client.event.listeners.MenuListener;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 2000)
public class MinecraftMixinLow {
    /**
     * @see MenuHooks#setLastSplash(Screen, MenuHelper)
     * @see MenuHooks#trackFallbacks(Screen)
     */
    @Inject(method = "setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;added()V", shift = At.Shift.BEFORE))
    public void setScreen(Screen guiScreen, CallbackInfo ci, @Local(argsOnly = true) LocalRef<Screen> guiScreenRef) {
        Screen newScreen = MenuListener.onGuiOpenLow(guiScreen);
        if (newScreen != null) {
            guiScreenRef.set(newScreen);
        }
    }
}
