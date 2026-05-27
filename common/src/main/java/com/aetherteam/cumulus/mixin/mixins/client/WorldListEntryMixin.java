package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {
    @Final
    @Shadow
    LevelSummary summary;

    /**
     * Used by the world preview system.<br>
     * Stops the world join behavior when the world preview is active, and instead enters directly into the loaded level.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#sameSummaries(LevelSummary)
     * @see WorldDisplayHelper#enterLoadedLevel()
     */
    @Inject(at = @At(value = "HEAD"), method = "joinWorld()V", cancellable = true)
    public void joinWorld(CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && WorldDisplayHelper.sameSummaries(this.summary)) {
            WorldDisplayHelper.enterLoadedLevel();
            ci.cancel();
        }
    }
}
