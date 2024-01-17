package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.components.WidgetSprites;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TabButton.class)
public interface TabButtonAccessor {
    @Mutable
    @Accessor("SPRITES")
    static void cumulus$setSprites(WidgetSprites sprite) {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("SPRITES")
    static WidgetSprites cumulus$getSprites() {
        throw new AssertionError();
    }
}
