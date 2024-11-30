package com.aetherteam.cumulus.client.events;

import com.aetherteam.cumulus.events.CancellableCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;

public class PlayerRenderEvents {
    public static final Event<PreMain> BEFORE_RENDER = EventFactory.createArrayBacked(PreMain.class, invokers -> (player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback);
    });

    public interface PreMain {
        void beforeRendering(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CancellableCallback callback);
    }
}
