package com.aetherteam.cumulus.mixin.mixins.common.accessor;

import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.server.LanServerPinger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(IntegratedServer.class)
public interface IntegratedServerAccessor {
    @Accessor("lanPinger")
    LanServerPinger cumulus$getLanPinger();

    @Accessor("lanPinger")
    void cumulus$setLanPinger(LanServerPinger lanPinger);

    @Accessor("uuid")
    UUID cumulus$getUUID();
}
