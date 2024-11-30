package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Unloads the currently loaded world preview level if a new level is being created.
     *
     * @param ci        The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#stopLevel(Screen)
     */
    @Inject(at = @At(value = "HEAD"), method = "onCreate()V")
    private void onCreate(CallbackInfo ci) {
        if (WorldDisplayHelper.isActive()) {
            Minecraft minecraft = Minecraft.getInstance();
            WorldDisplayHelper.stopLevel(null);
            minecraft.managedBlock(() -> Cumulus.SERVER_INSTANCE == null);
        }
    }
}
