package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.mixin.extensions.EntityRenderStateExtension;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Inject(at = @At(value = "HEAD"), method = "extractRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V")
    public void render(T entity, S entityRenderState, float f, CallbackInfo ci) {
        ((EntityRenderStateExtension) entityRenderState).cumulus$setUUID(entity.getUUID());
    }
}
