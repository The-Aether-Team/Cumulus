package com.aetherteam.cumulus.client.gui.screen;

import com.aetherteam.cumulus.mixin.mixins.client.accessor.ButtonBuilderAccessor;
import net.minecraft.client.gui.components.Button;

public class BuilderMadeButton extends Button {
    protected BuilderMadeButton(Builder builder) {
        super(
            ((ButtonBuilderAccessor) builder).cumulus$x(),
            ((ButtonBuilderAccessor) builder).cumulus$y(),
            ((ButtonBuilderAccessor) builder).cumulus$width(),
            ((ButtonBuilderAccessor) builder).cumulus$height(),
            ((ButtonBuilderAccessor) builder).cumulus$message(),
            ((ButtonBuilderAccessor) builder).cumulus$onPress(),
            ((ButtonBuilderAccessor) builder).cumulus$createNarration());
        this.setTooltip(((ButtonBuilderAccessor) builder).cumulus$tooltip());
    }
}
