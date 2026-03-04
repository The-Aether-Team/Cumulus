package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At(value = "HEAD"), method = "disconnectFromWorld(Lnet/minecraft/network/chat/Component;)V", cancellable = true)
    private void onDisconnectWorldPreview(Component reason, CallbackInfo ci) {
        if (CumulusConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().getSingleplayerServer() != null) {
            WorldDisplayHelper.setActive();
            WorldDisplayHelper.setupLevelForDisplay();
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.setXRot(0);
            }
            ci.cancel();
        }
    }
}
