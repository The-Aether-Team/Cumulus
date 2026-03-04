package com.aetherteam.cumulus.client.events;

import com.aetherteam.cumulus.events.CancellableCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityRenderEvents {
    public static final Event<PreMain> BEFORE_RENDER = EventFactory.createArrayBacked(PreMain.class, invokers -> (entityRenderState, renderer, partialTick, poseStack, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(entityRenderState, renderer, partialTick, poseStack, callback);
    });

    public interface PreMain {
        void beforeRendering(LivingEntityRenderState entityRenderState, LivingEntityRenderer<LivingEntity, ? extends LivingEntityRenderState, ? extends EntityModel<? super LivingEntityRenderState>> renderer, float partialTick, PoseStack poseStack, CancellableCallback callback);
    }
}
