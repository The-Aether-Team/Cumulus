package com.aetherteam.cumulus.client.event.listeners;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.client.event.hooks.WorldPreviewHooks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = Cumulus.MODID, value = Dist.CLIENT)
public class WorldPreviewListener {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        Cumulus.SERVER_INSTANCE = ServerLifecycleHooks.getCurrentServer();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        Cumulus.SERVER_INSTANCE = null;
    }

    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiOpenLowest(ScreenEvent.Opening event) {
        Screen newScreen = event.getNewScreen();
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Pre event) {
        Screen screen = event.getScreen();
        if (WorldPreviewHooks.hideScreen(screen)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld()
     */
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            WorldPreviewHooks.renderMenuWithWorld();
        }
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        WorldPreviewHooks.tickMenuWhenPaused();
    }

    /**
     * @see WorldPreviewHooks#angleCamera(double)
     */
    @SubscribeEvent
    public static void onCameraView(ViewportEvent.ComputeCameraAngles event) {
        double partialTick = event.getPartialTick();

        WorldPreviewHooks.angleCamera(partialTick);
    }

    /**
     * @see WorldPreviewHooks#hideOverlays()
     */
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
        if (WorldPreviewHooks.hideOverlays()) {
            event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PlayerRenderer renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

    /**
     * @see WorldPreviewHooks#shouldHideEntity(Entity)
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    @SubscribeEvent
    public static void onRenderEntity(RenderLivingEvent.Pre<?, ?> event) {
        Entity entity = event.getEntity();
        EntityRenderer<?> renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }
}
