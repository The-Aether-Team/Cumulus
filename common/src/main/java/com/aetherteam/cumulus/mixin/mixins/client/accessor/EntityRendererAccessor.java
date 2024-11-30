package com.aetherteam.cumulus.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface EntityRendererAccessor {
    @Accessor("shadowRadius")
    float cumulus$getShadowRadius();

    @Accessor("shadowRadius")
    void cumulus$setShadowRadius(float shadowRadius);
}
