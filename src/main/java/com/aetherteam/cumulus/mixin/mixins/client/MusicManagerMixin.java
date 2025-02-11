package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.CumulusClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MusicManager.class)
public class MusicManagerMixin {
    /**
     * This mixin modifies the return of {@link Minecraft#getSituationalMusic()} as it is given to the music variable in {@link MusicManager}.
     * @return The modified {@link Music} variable.
     */
    @WrapOperation(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getSituationalMusic()Lnet/minecraft/sounds/Music;"))
    public Music injected(Minecraft instance, Operation<Music> original) {
        Music music = original.call(instance);
        if (music == Musics.MENU && CumulusClient.MENU_HELPER.getActiveMusic() != null) {
            return CumulusClient.MENU_HELPER.getActiveMusic();
        }
        return music;
    }
}
