package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.SystemReport;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "fillSystemReport", at = @At(value = "HEAD"))
    private void fillSystemReport(SystemReport systemReport, CallbackInfoReturnable<SystemReport> cir) {
        WorldDisplayHelper.FAIL_RUN.run();
    }
}
