package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import com.mojang.blaze3d.systems.TimerQuery;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor("isLocalServer")
    void cumulus$setIsLocalServer(boolean isLocalServer);

    @Accessor("currentFrameProfile")
    TimerQuery.FrameProfile cumulus$getCurrentFrameProfile();

    @Accessor("currentFrameProfile")
    void cumulus$setCurrentFrameProfile(TimerQuery.FrameProfile currentFrameProfile);
}
