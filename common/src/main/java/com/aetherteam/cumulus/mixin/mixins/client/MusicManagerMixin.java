package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.CumulusClient;
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
     * @param music The original {@link Music} variable.
     * @return The modified {@link Music} variable.
     */
    @ModifyVariable(method = "tick()V", at = @At(value = "STORE"))
    public Music injected(Music music) {
        if (music == Musics.MENU && CumulusClient.MENU_HELPER.getActiveMusic() != null) {
            return CumulusClient.MENU_HELPER.getActiveMusic();
        }
        return music;
    }
}
