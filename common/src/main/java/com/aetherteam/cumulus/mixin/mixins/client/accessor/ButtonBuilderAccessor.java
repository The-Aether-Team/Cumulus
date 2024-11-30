package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Button.Builder.class)
public interface ButtonBuilderAccessor {
    @Accessor("message") Component cumulus$message();
    @Accessor("onPress") Button.OnPress cumulus$onPress();
    @Accessor("tooltip") @Nullable Tooltip cumulus$tooltip();
    @Accessor("x") int cumulus$x();
    @Accessor("y") int cumulus$y();
    @Accessor("width") int cumulus$width();
    @Accessor("height") int cumulus$height();
    @Accessor("createNarration") Button.CreateNarration cumulus$createNarration();
}
