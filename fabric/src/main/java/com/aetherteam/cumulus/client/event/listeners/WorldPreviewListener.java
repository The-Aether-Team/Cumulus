package com.aetherteam.cumulus.client.event.listeners;

import com.aetherteam.cumulus.client.OpeningScreenEvents;
import com.aetherteam.cumulus.client.event.hooks.WorldPreviewHooks;
import com.aetherteam.cumulus.client.events.LivingEntityRenderEvents;
import com.aetherteam.cumulus.client.events.PlayerRenderEvents;
import com.aetherteam.cumulus.events.CancellableCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;

public class WorldPreviewListener {
    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    public static void onGuiOpenLowest(Screen newScreen) {
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    public static void onScreenRender(Screen screen, CancellableCallback callback) {
        if (WorldPreviewHooks.hideScreen(screen)) {
            callback.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld()
     */
    public static void onRenderLevelLast() {
        WorldPreviewHooks.renderMenuWithWorld();
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    public static void onClientTick() {
        WorldPreviewHooks.tickMenuWhenPaused();
    }

//
//    /**
//     * @see WorldPreviewHooks#hideOverlays() //todo
//     */
//    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
//        if (WorldPreviewHooks.hideOverlays()) {
//            event.setCanceled(true);
//        }
//    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderPlayer(PlayerRenderer renderer, CancellableCallback callback) {
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        if (hide) {
            callback.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

    /**
     * @see WorldPreviewHooks#shouldHideEntity(Entity)
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderEntity(Entity entity, EntityRenderer<?> renderer, CancellableCallback callback) {
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        if (hide) {
            callback.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

    public static void initEvents() {
        OpeningScreenEvents.POST.register((oldScreen, newScreen) -> {
            onGuiOpenLowest(newScreen);
            return null;
        });
        WorldRenderEvents.LAST.register(context -> onRenderLevelLast());
        ClientTickEvents.END_CLIENT_TICK.register(client -> WorldPreviewListener.onClientTick());
        PlayerRenderEvents.BEFORE_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> WorldPreviewListener.onRenderPlayer(renderer, callback));
        LivingEntityRenderEvents.BEFORE_RENDER.register((livingEntity, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> onRenderEntity(livingEntity, renderer, callback));
    }
}
