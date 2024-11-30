package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor("isLocalServer")
    void cumulus$setIsLocalServer(boolean isLocalServer);
}
