package com.aetherteam.cumulus.mixin;

import com.aetherteam.cumulus.mixin.mixins.common.accessor.MinecraftServerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;

import java.nio.file.Path;

public class MixinHooks {
    /**
     * Checks whether the {@link SelectWorldScreen} is open and the level that the lock belongs to is the same one as the level loaded by the world preview.
     *
     * @param basePath The {@link Path} for the level directory.
     * @return Whether the level can be unlocked, as a {@link Boolean}.
     * @see com.aetherteam.cumulus.mixin.mixins.common.DirectoryLockMixin
     */
    public static boolean canUnlockLevel(Path basePath) {
        if (Minecraft.getInstance().screen != null && Minecraft.getInstance().screen instanceof SelectWorldScreen && Minecraft.getInstance().getSingleplayerServer() != null) {
            return basePath.getFileName().toString().equals(((MinecraftServerAccessor) Minecraft.getInstance().getSingleplayerServer()).cumulus$getStorageSource().getLevelId());
        }
        return false;
    }
}
