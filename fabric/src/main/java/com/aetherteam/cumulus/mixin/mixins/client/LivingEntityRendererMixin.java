package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.events.LivingEntityRenderEvents;
import com.aetherteam.cumulus.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> {
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapMethod(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void render(S entityRenderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        LivingEntityRenderEvents.BEFORE_RENDER.invoker().beforeRendering(entityRenderState, (LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>) (Object) this, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true), poseStack, buffer, packedLight, callback);

        if (callback.isCanceled()) return;

        original.call(entityRenderState, poseStack, buffer, packedLight);
    }
}
