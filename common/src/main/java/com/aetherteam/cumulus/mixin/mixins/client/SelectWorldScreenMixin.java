package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.WorldDisplayHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin {
    @Shadow
    private @Nullable Button editButton;

    @Shadow
    private @Nullable Button deleteButton;

    @Shadow
    private @Nullable Button recreateButton;

    @Inject(method = "updateButtonStatus(Lnet/minecraft/world/level/storage/LevelSummary;)V", at = @At("RETURN"))
    private void updateButtonStatus(@Nullable LevelSummary summary, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && summary != null && WorldDisplayHelper.sameSummaries(summary)) {
            if (this.editButton != null && this.deleteButton != null && this.recreateButton != null) {
                this.editButton.active = false;
                this.deleteButton.active = false;
                this.recreateButton.active = false;
                this.editButton.setTooltip(Tooltip.create(Component.translatable("gui.cumulus_menus.selectWorld.previewActive.edit")));
                this.deleteButton.setTooltip(Tooltip.create(Component.translatable("gui.cumulus_menus.selectWorld.previewActive.delete")));
                this.recreateButton.setTooltip(Tooltip.create(Component.translatable("gui.cumulus_menus.selectWorld.previewActive.recreate")));
            }
        }
    }
}
