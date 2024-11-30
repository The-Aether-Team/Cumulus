package com.aetherteam.cumulus.client.events;

import com.aetherteam.cumulus.events.CancellableCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityRenderEvents {
    public static final Event<PreMain> BEFORE_RENDER = EventFactory.createArrayBacked(PreMain.class, invokers -> (livingEntity, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(livingEntity, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback);
    });

    public interface PreMain {
        void beforeRendering(LivingEntity livingEntity, LivingEntityRenderer<LivingEntity, ? extends EntityModel<LivingEntity>> renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CancellableCallback callback);
    }
}
