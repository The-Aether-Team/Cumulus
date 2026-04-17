package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.events.LivingEntityRenderEvents;
import com.aetherteam.cumulus.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> {
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapMethod(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
    private void render(S livingEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        LivingEntityRenderEvents.BEFORE_RENDER.invoker().beforeRendering(livingEntityRenderState, (LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>) (Object) this, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true), poseStack, callback);

        if (callback.isCanceled()) return;

        original.call(livingEntityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
