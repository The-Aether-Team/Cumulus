package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.WorldDisplayHelper;
import com.aetherteam.cumulus.mixin.MixinHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @ModifyVariable(method = "readLevelSummary(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;Z)Lnet/minecraft/world/level/storage/LevelSummary;", at = @At(value = "HEAD"), argsOnly = true)
    private boolean readLevelSummary(boolean locked, @Local(argsOnly = true) LevelStorageSource.LevelDirectory levelDirectory) {
        if (WorldDisplayHelper.isActive() && MixinHooks.canUnlockLevel(levelDirectory.path())) {
            return false;
        }
        return locked;
    }
}
