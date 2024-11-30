package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.events.LivingEntityRenderEvents;
import com.aetherteam.cumulus.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapMethod(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        LivingEntityRenderEvents.BEFORE_RENDER.invoker().beforeRendering(entity, (LivingEntityRenderer<LivingEntity, ?>) (Object) this, partialTicks, poseStack, buffer, packedLight, callback);

        if (callback.isCanceled()) return;

        original.call(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
